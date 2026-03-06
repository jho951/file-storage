package com.filestorage.config;


import com.filestorage.api.FileStorage;
import com.filestorage.core.config.FileStorageConfig;
import com.filestorage.core.exception.FileStorageErrorCode;
import com.filestorage.core.exception.FileStorageException;
import com.filestorage.core.local.LocalFileStorage;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 환경 변수 기반 FileStorage 생성기.
 * 필수: FILESTORAGE_ROOT_DIR
 * 선택: FILESTORAGE_AUTO_CREATE_DIR (default: true)
 */
public final class EnvFileStorageFactory {

	/**
	 * 유틸리티 클래스의 인스턴스 생성을 막습니다.
	 */
	private EnvFileStorageFactory() {
	}

	/**
	 * 환경 변수 값을 읽어 로컬 파일 저장소 구현체를 생성합니다.
	 *
	 * @return 환경 변수 기반으로 초기화된 {@link LocalFileStorage} 인스턴스
	 * @throws FileStorageException 필수 환경 변수가 없을 때 발생
	 */
	public static FileStorage createLocalFromEnv() {
		String rootDir = System.getenv(FileStorageConfigKeys.ENV_ROOT_DIR);
		if (rootDir == null || rootDir.isBlank()) {
			throw new FileStorageException(FileStorageErrorCode.REQUIRED_ENV_MISSING, FileStorageConfigKeys.ENV_ROOT_DIR);
		}
		String autoCreate = System.getenv(FileStorageConfigKeys.ENV_AUTO_CREATE_DIR);

		Path rootPath = Paths.get(rootDir);
		boolean autoCreateDir = autoCreate == null || Boolean.parseBoolean(autoCreate);

		FileStorageConfig config = FileStorageConfig
			.builder(rootPath)
			.createDirectoriesIfNotExist(autoCreateDir)
			.build();

		return new LocalFileStorage(config);
	}
}
