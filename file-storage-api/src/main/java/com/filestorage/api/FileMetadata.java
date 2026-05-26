package com.filestorage.api;

/** 파일 저장 요청 시 전달하는 메타데이터를 표현합니다. */
public final class FileMetadata {
	/** 사용자가 내 컴퓨터에서 올릴 때의 실제 이름 (예: 증명사진.jpg, 보고서.pdf) */
	private final String originalName;
	/** 파일의 정체 (예: image/jpeg, application/pdf) */
	private final String contentType;

	public FileMetadata(String originalName, String contentType) {
		this.originalName = originalName;
		this.contentType = contentType;
	}

	public String getOriginalName() {
		return originalName;
	}
	public String getContentType() {
		return contentType;
	}
}
