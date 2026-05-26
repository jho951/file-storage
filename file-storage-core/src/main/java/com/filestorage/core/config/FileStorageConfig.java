package com.filestorage.core.config;

import java.nio.file.Path;

/** 파일 저장소 초기화에 필요한 설정 값을 나타냅니다.*/
public final class FileStorageConfig {
	/** 파일이 저장될 루트 디렉토리 */
	private final Path rootDirectory;
	/** 루트 디렉토리가 없을 때 자동 생성할지 여부 */
	private final boolean createDirectoriesIfNotExist;

	private FileStorageConfig(Builder builder) {
		this.rootDirectory = builder.rootDirectory;
		this.createDirectoriesIfNotExist = builder.createDirectoriesIfNotExist;
	}

	public Path getRootDirectory() {
		return rootDirectory;
	}
	public boolean isCreateDirectoriesIfNotExist() {
		return createDirectoriesIfNotExist;
	}
	public static Builder builder(Path rootDirectory) {
		return new Builder(rootDirectory);
	}

	public static final class Builder {
		/** 파일이 저장될 루트 디렉토리 */
		private final Path rootDirectory;
		/** 루트 디렉토리가 없을 때 자동 생성할지 여부 */
		private boolean createDirectoriesIfNotExist = true;

		public Builder(Path rootDirectory) {
			this.rootDirectory = rootDirectory;
		}

		public Builder createDirectoriesIfNotExist(boolean value) {
			this.createDirectoriesIfNotExist = value;
			return this;
		}

		public FileStorageConfig build() {
			return new FileStorageConfig(this);
		}
	}
}
