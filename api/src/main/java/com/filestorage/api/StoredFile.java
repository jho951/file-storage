package com.filestorage.api;

import java.nio.file.Path;

/**
 * 파일 저장 또는 이동 작업 후 반환되는 결과 정보를 나타냅니다.
 */
public final class StoredFile {
	// 스토리지 내부에서 이 파일을 구분하기 위한 파일 식별자
	private final String id;
	// 사용자가 업로드했던 원래 파일 이름
	private final String originalName;
	// MIME 타입 (HTTP Content-Type)
	private final String contentType;
	// 실제 저장된 파일 크기 (바이트 단위)
	private final long size;
	// 실제 파일이 저장된 절대 경로
	private final Path absolutePath;

	/**
	 * 저장 파일 결과 객체를 생성합니다.
	 *
	 * @param id 스토리지 내부 파일 식별자
	 * @param originalName 업로드 당시 원본 파일명
	 * @param contentType 파일 MIME 타입
	 * @param size 실제 저장된 파일 크기(바이트)
	 * @param absolutePath 실제 저장 경로(절대 경로)
	 */
	public StoredFile(String id, String originalName, String contentType, long size, Path absolutePath) {
		this.id = id;
		this.originalName = originalName;
		this.contentType = contentType;
		this.size = size;
		this.absolutePath = absolutePath;
	}

	/**
	 * 파일 식별자를 반환합니다.
	 *
	 * @return 파일 식별자
	 */
	public String getId() {
		return id;
	}

	/**
	 * 원본 파일명을 반환합니다.
	 *
	 * @return 원본 파일명
	 */
	public String getOriginalName() {
		return originalName;
	}

	/**
	 * MIME 타입을 반환합니다.
	 *
	 * @return MIME 타입
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * 저장된 파일 크기를 반환합니다.
	 *
	 * @return 파일 크기(바이트)
	 */
	public long getSize() {
		return size;
	}

	/**
	 * 파일의 절대 경로를 반환합니다.
	 *
	 * @return 절대 경로
	 */
	public Path getAbsolutePath() {
		return absolutePath;
	}
}
