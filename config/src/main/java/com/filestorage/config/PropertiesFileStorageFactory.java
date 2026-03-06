package com.filestorage.config;

import com.filestorage.api.FileStorage;
import com.filestorage.core.config.FileStorageConfig;
import com.filestorage.core.exception.FileStorageErrorCode;
import com.filestorage.core.exception.FileStorageException;
import com.filestorage.core.local.LocalFileStorage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * {@link Properties} 기반으로 {@link FileStorage} 구현체를 생성하는 팩토리입니다.
 */
public final class PropertiesFileStorageFactory {

	/**
	 * 유틸리티 클래스의 인스턴스 생성을 막습니다.
	 */
	private PropertiesFileStorageFactory() {
	}

	/**
	 * 프로퍼티 값을 읽어 로컬 파일 저장소 구현체를 생성합니다.
	 *
	 * @param props 파일 저장소 설정을 담은 프로퍼티 객체
	 * @return 프로퍼티 기반으로 초기화된 {@link LocalFileStorage} 인스턴스
	 * @throws IllegalArgumentException 전달된 {@code props}가 {@code null}인 경우
	 * @throws FileStorageException 필수 프로퍼티가 누락된 경우
	 */
	public static FileStorage createLocalFromProperties(Properties props) {
		if (props == null) {
			throw new IllegalArgumentException("props must not be null");
		}

		String rootDir = props.getProperty(FileStorageConfigKeys.PROP_ROOT_DIR);
		if (rootDir == null || rootDir.isBlank()) {
			throw new FileStorageException(FileStorageErrorCode.REQUIRED_PROPERTY_MISSING, FileStorageConfigKeys.PROP_ROOT_DIR);
		}

		String autoCreate = props.getProperty(FileStorageConfigKeys.PROP_AUTO_CREATE_DIR, "true");

		Path rootPath = Paths.get(rootDir);
		boolean autoCreateDir = Boolean.parseBoolean(autoCreate);

		FileStorageConfig config = FileStorageConfig
			.builder(rootPath)
			.createDirectoriesIfNotExist(autoCreateDir)
			.build();

		return new LocalFileStorage(config);
	}
}
