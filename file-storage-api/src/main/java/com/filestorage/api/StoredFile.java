package com.filestorage.api;

/** 파일 저장 또는 이동 작업 후 반환되는 결과 정보를 나타냅니다. */
public final class StoredFile {
	/** 파일을 다시 찾거나 삭제할 때 서버에 알려줘야 하는 식별자 */
	private final String id;
	/** 사용자가 내 컴퓨터에서 올릴 때의 실제 이름 (예: 증명사진.jpg, 보고서.pdf) */
	private final String originalName;
	/** 파일의 정체 (예: image/jpeg, application/pdf) */
	private final String contentType;
	/** 실제 저장된 파일 크기 (바이트) */
	private final long size;

	public StoredFile(String id, String originalName, String contentType, long size) {
		this.id = id;
		this.originalName = originalName;
		this.contentType = contentType;
		this.size = size;
	}

	public String getId() {
		return id;
	}
	public String getOriginalName() {
		return originalName;
	}
	public String getContentType() {
		return contentType;
	}
	public long getSize() {
		return size;
	}
}
