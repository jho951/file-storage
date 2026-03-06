package com.filestorage.core.local;

import com.filestorage.api.FileMetadata;
import com.filestorage.api.FileStorage;
import com.filestorage.api.StoredFile;
import com.filestorage.core.config.FileStorageConfig;
import com.filestorage.core.exception.FileStorageErrorCode;
import com.filestorage.core.exception.FileStorageException;
import com.filestorage.core.util.PathUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/** 로컬 디스크 기반 {@link FileStorage} 구현체입니다.*/
public final class LocalFileStorage implements FileStorage {

	private final FileStorageConfig config;

	/**
	 * 로컬 파일 저장소를 생성하고 루트 디렉토리를 초기화합니다.
	 *
	 * @param config 파일 저장소 설정
	 */
	public LocalFileStorage(FileStorageConfig config) {
		this.config = config;
		initRootDirectory();
	}

	/** 루트 디렉토리 존재 여부와 유효성을 검사하고 필요 시 생성합니다. */
	private void initRootDirectory() {
		Path root = config.getRootDirectory();
		try {
			if (config.isCreateDirectoriesIfNotExist() && !Files.exists(root)) Files.createDirectories(root);
			if (!Files.isDirectory(root)) throw new FileStorageException(FileStorageErrorCode.INVALID_ROOT_DIRECTORY);
		} catch (IOException e) {
			throw new FileStorageException(FileStorageErrorCode.ERROR_ROOT_DIRECTORY, "root=" + root, e);
		}
	}

	/**
	 * 파일 데이터를 루트 디렉토리 하위에 저장합니다.
	 *
	 * @param input 저장할 파일 데이터 스트림
	 * @param metadata 파일 메타데이터
	 * @return 저장 결과 정보
	 * @throws IllegalArgumentException 입력 스트림이 {@code null}인 경우
	 * @throws FileStorageException 파일 저장에 실패한 경우
	 */
	@Override
	public StoredFile store(InputStream input, FileMetadata metadata) {
		if (input == null) {
			throw new IllegalArgumentException("InputStream must not be null");
		}
		String fileId = PathUtils.randomFileName();
		Path target = PathUtils.resolveSafe(config.getRootDirectory(), fileId);

		try {
			Files.copy(input, target);
			long size = Files.size(target);
			return new StoredFile(
				fileId,
				metadata != null ? metadata.getOriginalName() : null,
				metadata != null ? metadata.getContentType() : null,
				size,
				target
			);
		} catch (IOException e) {
			throw new FileStorageException(FileStorageErrorCode.FILE_STORE_FAILED, "target=" + target, e);
		}
	}

	/**
	 * 파일 식별자로 파일을 조회합니다.
	 *
	 * @param fileId 파일 식별자
	 * @return 파일 입력 스트림
	 * @throws FileStorageException 파일을 찾을 수 없거나 열기에 실패한 경우
	 */
	@Override
	public InputStream load(String fileId) {
		Path path = getFilePath(fileId);
		try {
			return Files.newInputStream(path);
		} catch (IOException e) {
			throw new FileStorageException(FileStorageErrorCode.FILE_LOAD_FAILED, "path=" + path, e);
		}
	}

	/**
	 * 파일 식별자에 해당하는 파일을 삭제합니다.
	 *
	 * @param fileId 파일 식별자
	 * @throws FileStorageException 삭제 과정에서 I/O 오류가 발생한 경우
	 */
	@Override
	public void delete(String fileId) {
		Path path = getFilePath(fileId);
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new FileStorageException(FileStorageErrorCode.FILE_DELETE_FAILED, "path=" + path, e);
		}
	}

	/**
	 * 파일을 지정된 대상 하위 디렉토리로 이동합니다.
	 *
	 * @param fileId 이동할 파일 식별자
	 * @param targetDirectory 대상 하위 디렉토리
	 * @return 이동된 파일 결과 정보
	 * @throws FileStorageException 파일 이동 또는 대상 디렉토리 생성에 실패한 경우
	 */
	@Override
	public StoredFile move(String fileId, String targetDirectory) {
		Path source = getFilePath(fileId);
		Path targetDir = PathUtils.resolveSafe(config.getRootDirectory(), targetDirectory);
		try {
			if (!Files.exists(targetDir)) {
				Files.createDirectories(targetDir);
			}
			Path target = targetDir.resolve(source.getFileName());
			Files.move(source, target);
			long size = Files.size(target);
			return new StoredFile(
				fileId,
				null,
				null,
				size,
				target
			);
		} catch (IOException e) {
			throw new FileStorageException(FileStorageErrorCode.FILE_MOVE_FAILED, "source=" + source, e);
		}
	}

	/**
	 * 파일 식별자를 실제 경로로 변환하고 존재 여부를 검증합니다.
	 *
	 * @param fileId 파일 식별자
	 * @return 파일의 실제 경로
	 * @throws IllegalArgumentException 식별자가 비어 있는 경우
	 * @throws FileStorageException 파일이 존재하지 않는 경우
	 */
	private Path getFilePath(String fileId) {
		if (fileId == null || fileId.isBlank()) {
			throw new IllegalArgumentException("fileId must not be blank");
		}
		Path path = PathUtils.resolveSafe(config.getRootDirectory(), fileId);
		if (!Files.exists(path)) {
			throw new FileStorageException(FileStorageErrorCode.FILE_NOT_FOUND, "path=" + path);
		}
		return path;
	}
}
