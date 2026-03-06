package com.filestorage.core.util;

import java.nio.file.Path;
import java.util.UUID;

/** 경로 및 파일명 관련 유틸리티. */
public final class PathUtils {

	/**
	 * 유틸리티 클래스의 인스턴스 생성을 막습니다.
	 */
	private PathUtils() {}

	/**
	 * 루트 경로 기준으로 세그먼트를 안전하게 결합합니다.
	 * <p>
	 * 단순 문자열 치환으로 역슬래시 및 상위 디렉토리 참조(`..`)를 정리한 뒤
	 * normalize 처리한 경로를 반환합니다.
	 *
	 * @param root 기준 루트 경로
	 * @param segments 결합할 하위 경로 세그먼트들
	 * @return 정규화된 결과 경로
	 */
	public static Path resolveSafe(Path root, String... segments) {
		Path result = root;
		for (String segment : segments) {
			if (segment == null || segment.isBlank()) { continue; }
			String clean = segment.replace("\\", "/").replace("..", "");
			result = result.resolve(clean);
		}
		return result.normalize();
	}

	/**
	 * 랜덤 파일명을 생성합니다.
	 *
	 * @return UUID 기반 파일명
	 */
	public static String randomFileName() {
		return UUID.randomUUID().toString();
	}
}
