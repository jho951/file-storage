package com.filestorage.core.exception;

/** 파일 저장소 모듈에서 사용하는 오류 코드를 정의합니다.*/
public enum FileStorageErrorCode {

	/** 필수 환경 변수가 누락된 경우를 나타냅니다. */
	REQUIRED_ENV_MISSING("FILE001", "Required environment variable is missing"),
	/** 환경 변수 값이 유효하지 않은 경우를 나타냅니다. */
	INVALID_ENV_VALUE("FILE002", "Invalid environment variable value"),
	/** 디렉토리를 사용할 수 없는 경우를 나타냅니다. */
	INVALID_ROOT_DIRECTORY("FILE003", "Root directory is not a directory"),
	/** 루트 디렉토리 초기화에 실패한 경우를 나타냅니다. */
	ERROR_ROOT_DIRECTORY("FILE004", "Failed to initialize root directory"),
	/** 파일 저장에 실패한 경우를 나타냅니다. */
	FILE_STORE_FAILED("FILE005", "Failed to store file"),
	/** 파일 로딩에 실패한 경우를 나타냅니다. */
	FILE_LOAD_FAILED("FILE006", "Failed to load file"),
	/** 파일 삭제에 실패한 경우를 나타냅니다. */
	FILE_DELETE_FAILED("FILE007", "Failed to delete file"),
	/** 파일 이동에 실패한 경우를 나타냅니다. */
	FILE_MOVE_FAILED("FILE008", "Failed to move file"),
	/** 파일을 찾지 못한 경우를 나타냅니다. */
	FILE_NOT_FOUND("FILE009", "File not found"),
	/** 필수 프로퍼티가 누락된 경우를 나타냅니다. */
	REQUIRED_PROPERTY_MISSING("FILE010", "Required property is missing");

	/** 오류 코드 문자열입니다. */
	private final String code;
	/** 오류 기본 메시지입니다. */
	private final String defaultMessage;

	/**
	 * 오류 코드를 생성합니다.
	 *
	 * @param code 오류 식별 코드
	 * @param defaultMessage 오류 기본 메시지
	 */
	FileStorageErrorCode(String code, String defaultMessage) {
		this.code = code;
		this.defaultMessage = defaultMessage;
	}

	/**
	 * 심볼릭 오류 코드를 반환합니다.
	 *
	 * @return 오류 코드
	 */
	public String code() {
		return code;
	}

	/**
	 * 기본 오류 메시지를 반환합니다.
	 *
	 * @return 기본 오류 메시지
	 */
	public String defaultMessage() {
		return defaultMessage;
	}

	/**
	 * 코드와 메시지를 함께 표현한 문자열을 반환합니다.
	 *
	 * @return "코드 - 메시지" 형식 문자열
	 */
	@Override
	public String toString() {
		return code + " - " + defaultMessage;
	}
}
