package com.filestorage.core.config;

import java.nio.file.Path;

/** 파일 저장소 초기화에 필요한 설정 값을 나타냅니다.*/
public final class FileStorageConfig {

	private final Path rootDirectory;
	private final boolean createDirectoriesIfNotExist;

	/**
	 * 빌더를 통해 생성된 설정 객체를 초기화합니다.
	 *
	 * @param builder 설정 값을 담고 있는 빌더
	 */
	private FileStorageConfig(Builder builder) {
		this.rootDirectory = builder.rootDirectory;
		this.createDirectoriesIfNotExist = builder.createDirectoriesIfNotExist;
	}

	/**
	 * 파일이 저장될 루트 디렉토리를 반환합니다.
	 *
	 * @return 루트 디렉토리 경로
	 */
	public Path getRootDirectory() {
		return rootDirectory;
	}

	/**
	 * 루트 디렉토리가 없을 때 자동 생성할지 여부를 반환합니다.
	 *
	 * @return 자동 생성 여부
	 */
	public boolean isCreateDirectoriesIfNotExist() {
		return createDirectoriesIfNotExist;
	}

	/**
	 * 설정 객체 생성을 위한 빌더를 반환합니다.
	 *
	 * @param rootDirectory 파일 저장 루트 디렉토리
	 * @return {@link Builder} 인스턴스
	 */
	public static Builder builder(Path rootDirectory) {
		return new Builder(rootDirectory);
	}

	/**
	 * {@link FileStorageConfig} 생성용 빌더입니다.
	 */
	public static final class Builder {
		private final Path rootDirectory;
		private boolean createDirectoriesIfNotExist = true;

		/**
		 * 빌더를 생성합니다.
		 *
		 * @param rootDirectory 파일 저장 루트 디렉토리
		 */
		public Builder(Path rootDirectory) {
			this.rootDirectory = rootDirectory;
		}

		/**
		 * 루트 디렉토리 자동 생성 여부를 설정합니다.
		 *
		 * @param value 자동 생성 여부
		 * @return 현재 빌더 인스턴스
		 */
		public Builder createDirectoriesIfNotExist(boolean value) {
			this.createDirectoriesIfNotExist = value;
			return this;
		}

		/**
		 * 설정 객체를 생성합니다.
		 *
		 * @return 생성된 {@link FileStorageConfig}
		 */
		public FileStorageConfig build() {
			return new FileStorageConfig(this);
		}
	}
}
