package com.filestorage.api;

/**
 * 파일 저장 요청 시 전달하는 메타데이터를 표현합니다.
 */
public final class FileMetadata {

	private final String originalName;
	private final String contentType;

	/**
	 * 파일 메타데이터 객체를 생성합니다.
	 *
	 * @param originalName 업로드 당시의 원본 파일명
	 * @param contentType 파일의 MIME 타입 (http 헤더 타입)
	 */
	public FileMetadata(String originalName, String contentType) {
		this.originalName = originalName;
		this.contentType = contentType;
	}

	/**
	 * 업로드 당시의 원본 파일명을 반환합니다.
	 *
	 * @return 원본 파일명
	 */
	public String getOriginalName() {
		return originalName;
	}

	/**
	 * 파일의 MIME 타입을 반환합니다.
	 *
	 * @return MIME 타입
	 */
	public String getContentType() {
		return contentType;
	}
}
