package com.filestorage.core.exception;

import java.util.Objects;

/** 파일 저장소 처리 과정에서 발생하는 공통 런타임 예외입니다.*/
public class FileStorageException extends RuntimeException {

	private final FileStorageErrorCode errorCode;

	/**
	 * 메시지만 포함하는 예외를 생성합니다.
	 *
	 * @param message 예외 메시지
	 */
	public FileStorageException(String message) {
		super(message);
		this.errorCode = null;
	}

	/**
	 * 메시지와 원인 예외를 포함하는 예외를 생성합니다.
	 *
	 * @param message 예외 메시지
	 * @param cause 원인 예외
	 */
	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = null;
	}

	/**
	 * 에러코드만 포함하는 예외를 생성합니다.
	 *
	 * @param errorCode 에러코드
	 */
	public FileStorageException(FileStorageErrorCode errorCode) {
		super(formatMessage(errorCode, null));
		this.errorCode = errorCode;
	}

	/**
	 * 에러코드와 상세 메시지를 포함하는 예외를 생성합니다.
	 *
	 * @param errorCode 에러코드
	 * @param detailMessage 상세 메시지
	 */
	public FileStorageException(FileStorageErrorCode errorCode, String detailMessage) {
		super(formatMessage(errorCode, detailMessage));
		this.errorCode = errorCode;
	}

	/**
	 * 에러코드와 원인 예외를 포함하는 예외를 생성합니다.
	 *
	 * @param errorCode 에러코드
	 * @param cause 원인 예외
	 */
	public FileStorageException(FileStorageErrorCode errorCode, Throwable cause) {
		super(formatMessage(errorCode, null), cause);
		this.errorCode = errorCode;
	}

	/**
	 * 에러코드, 상세 메시지, 원인 예외를 포함하는 예외를 생성합니다.
	 *
	 * @param errorCode 에러코드
	 * @param detailMessage 상세 메시지
	 * @param cause 원인 예외
	 */
	public FileStorageException(FileStorageErrorCode errorCode, String detailMessage, Throwable cause) {
		super(formatMessage(errorCode, detailMessage), cause);
		this.errorCode = errorCode;
	}

	/**
	 * 에러코드를 반환합니다.
	 *
	 * @return 에러코드, 없으면 {@code null}
	 */
	public FileStorageErrorCode getErrorCode() {
		return errorCode;
	}

	private static String formatMessage(FileStorageErrorCode errorCode, String detailMessage) {
		FileStorageErrorCode safeErrorCode = Objects.requireNonNull(errorCode, "errorCode must not be null");
		String baseMessage = safeErrorCode.toString();
		if (detailMessage == null || detailMessage.isBlank()) {
			return baseMessage;
		}
		return baseMessage + " (" + detailMessage + ")";
	}
}
