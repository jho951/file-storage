package com.filestorage.core.exception;

import java.util.Objects;

/** 파일 저장소 처리 과정에서 발생하는 공통 런타임 예외입니다.*/
public class FileStorageException extends RuntimeException {
	/** 에러 코드 */
	private final FileStorageErrorCode errorCode;

	public FileStorageException(String message) {
		super(message);
		this.errorCode = null;
	}
	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = null;
	}
	public FileStorageException(FileStorageErrorCode errorCode) {
		super(formatMessage(errorCode, null));
		this.errorCode = errorCode;
	}
	public FileStorageException(FileStorageErrorCode errorCode, String detailMessage) {
		super(formatMessage(errorCode, detailMessage));
		this.errorCode = errorCode;
	}
	public FileStorageException(FileStorageErrorCode errorCode, Throwable cause) {
		super(formatMessage(errorCode, null), cause);
		this.errorCode = errorCode;
	}
	public FileStorageException(FileStorageErrorCode errorCode, String detailMessage, Throwable cause) {
		super(formatMessage(errorCode, detailMessage), cause);
		this.errorCode = errorCode;
	}
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
