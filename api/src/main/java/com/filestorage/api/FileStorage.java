package com.filestorage.api;

import java.io.InputStream;

/**
 * 파일을 저장소에 저장하고 관리하기 위한 공통 인터페이스입니다.
 * <p>
 * 구현체는 로컬 디스크, 오브젝트 스토리지(S3), 네트워크 파일 시스템(NFS) 등
 * 다양한 백엔드를 대상으로 동작할 수 있습니다.
 */
public interface FileStorage {

	/**
	 * 입력 스트림의 파일 데이터를 저장합니다.
	 *
	 * @param input 저장할 파일 데이터 스트림
	 * @param metadata 파일 원본명, 콘텐츠 타입 등 부가 메타데이터
	 * @return 저장된 파일 식별자와 실제 경로를 포함한 결과 정보
	 */
	StoredFile store(InputStream input, FileMetadata metadata);

	/**
	 * 파일 식별자에 해당하는 파일을 조회합니다.
	 *
	 * @param fileId 조회할 파일 식별자
	 * @return 파일 내용을 읽을 수 있는 입력 스트림
	 */
	InputStream load(String fileId);

	/**
	 * 파일 식별자에 해당하는 파일을 삭제합니다.
	 *
	 * @param fileId 삭제할 파일 식별자
	 */
	void delete(String fileId);

	/**
	 * 파일을 지정된 하위 디렉토리로 이동합니다.
	 *
	 * @param fileId 이동할 파일 식별자
	 * @param targetDirectory 루트 디렉토리 기준 대상 하위 경로
	 * @return 이동된 파일의 결과 정보
	 */
	StoredFile move(String fileId, String targetDirectory);
}
