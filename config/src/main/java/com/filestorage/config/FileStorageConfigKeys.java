package com.filestorage.config;

/**
 * 파일 저장소 설정에 사용되는 환경 변수 및 프로퍼티 키 상수 모음입니다.
 */
public final class FileStorageConfigKeys {

	/**
	 * 유틸리티 클래스의 인스턴스 생성을 막습니다.
	 */
	private FileStorageConfigKeys() {}

	/** 파일 저장 루트 디렉토리 환경 변수 키입니다. */
	public static final String ENV_ROOT_DIR = "FILESTORAGE_ROOT_DIR";
	/** 루트 디렉토리 자동 생성 여부 환경 변수 키입니다. */
	public static final String ENV_AUTO_CREATE_DIR = "FILESTORAGE_AUTO_CREATE_DIR";

	/** 파일 저장 루트 디렉토리 프로퍼티 키입니다. */
	public static final String PROP_ROOT_DIR = "filestorage.rootDir";
	/** 루트 디렉토리 자동 생성 여부 프로퍼티 키입니다. */
	public static final String PROP_AUTO_CREATE_DIR = "filestorage.autoCreateDir";
}
