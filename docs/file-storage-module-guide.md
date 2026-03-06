# File Storage Module Guide

## 1) 모듈 역할
> 애플리케이션에서 파일 저장소를 일관된 방식으로 다루기 위해 사용하는 라이브러리입니다.
> 
> 현재 저장소 구현은 **로컬 디스크 기반(`LocalFileStorage`)** 입니다.

### 목적
- 서비스 코드는 `FileStorage` 인터페이스만 사용하고, 실제 저장소 구현(Local/S3/NFS 등)은 교체 가능하게 만든다.
- 파일 저장, 조회, 삭제, 이동 기능을 공통 API로 제공한다.

## 2) 모듈 구조
> 멀티 모듈 Gradle 프로젝트이며 역할이 분리되어 있습니다.

- `api`
  - 외부에서 사용할 인터페이스/모델 정의
  - `FileStorage`, `FileMetadata`, `StoredFile`
- `core`
  - 실제 동작 구현
  - `LocalFileStorage`, 설정 객체(`FileStorageConfig`), 공통 예외(`FileStorageException`), 경로 유틸(`PathUtils`)
- `config`
  - 설정 객체와 생성 팩토리
  - `EnvFileStorageFactory`, `PropertiesFileStorageFactory`, 설정 키(`FileStorageConfigKeys`)

의존 관계:
- `core` -> `api`
- `config` -> `api`, `core`

## 3) 핵심 API
> `FileStorage` 인터페이스는 아래 4개 동작을 제공합니다.
- [StoredFile store(InputStream input, FileMetadata metadata)](../api/src/main/java/com/filestorage/api/FileStorage.java)
- [InputStream load(String fileId)](../api/src/main/java/com/filestorage/api/StoredFile.java)
- [void delete(String fileId)](../core/src/main/java/com/filestorage/core/local/LocalFileStorage.java)
- [StoredFile move(String fileId, String targetDirectory)](../core/src/main/java/com/filestorage/core/local/LocalFileStorage.java)

### 데이터 모델
*`FileMetadata`*
  - 저장 요청 시 전달하는 메타데이터
  - `originalName`, `contentType`

*`StoredFile`*
  - 저장/이동 결과 정보
  - `id`, `originalName`, `contentType`, `size`, `absolutePath`

## 4) 현재 구현체(LocalFileStorage) 동작
### store
- UUID 기반 파일 ID 생성
- 루트 디렉토리 하위에 파일 복사
- 저장된 파일 크기와 경로를 포함한 `StoredFile` 반환

### load
- `fileId`로 파일 경로를 계산 후 `InputStream` 반환

### delete
- `fileId`에 해당하는 파일 삭제 (`deleteIfExists`)

### move
- 루트 하위 `targetDirectory`로 파일 이동
- 대상 디렉토리가 없으면 생성

## 5) 설정 방법
> `LocalFileStorage`는 `core` 모듈의 `FileStorageConfig`를 통해 루트 디렉토리와 자동 디렉토리 생성 옵션을 받습니다.

- `rootDirectory` (필수)
- `createDirectoriesIfNotExist` (기본값 `true`)

### 5.1 환경 변수 기반 생성
`EnvFileStorageFactory.createLocalFromEnv()` 사용

필수:
- `FILESTORAGE_ROOT_DIR`

선택:
- `FILESTORAGE_AUTO_CREATE_DIR` (기본 `true`)

### 5.2 Properties 기반 생성
`PropertiesFileStorageFactory.createLocalFromProperties(props)` 사용

필수:
- `filestorage.rootDir`

선택:
- `filestorage.autoCreateDir` (기본 `true`)

## 6) 사용 예시

### 6.1 환경 변수로 생성
```java
FileStorage storage = EnvFileStorageFactory.createLocalFromEnv();
```

### 6.2 Properties로 생성
```java
Properties props = new Properties();
props.setProperty("filestorage.rootDir", "/tmp/files");
props.setProperty("filestorage.autoCreateDir", "true");

FileStorage storage = PropertiesFileStorageFactory.createLocalFromProperties(props);
```

### 6.3 파일 저장/조회/삭제
```java
FileMetadata metadata = new FileMetadata("a.png", "image/png");
StoredFile stored = storage.store(inputStream, metadata);

InputStream in = storage.load(stored.getId());
storage.delete(stored.getId());
```

## 7) 예외 처리
- 공통 런타임 예외는 `FileStorageException`을 사용
- 대표 발생 상황
  - 루트 디렉토리 초기화 실패
  - 파일 저장/조회/삭제/이동 실패
  - 필수 설정 누락

## 8) 확장 포인트
새 저장소를 추가하려면 `FileStorage`를 구현하면 됩니다.

예시:
- `S3FileStorage implements FileStorage`
- `NfsFileStorage implements FileStorage`

서비스 레이어는 구현체가 바뀌어도 동일한 API를 사용합니다.

## 9) 현재 구현의 주의사항
- `PathUtils.resolveSafe`는 상대 경로 정리를 수행하지만, 보안 검증을 더 강화할 수 있습니다.
  - 예: 최종 경로가 루트 디렉토리 밖으로 벗어나지 않는지 명시 검증
- `move`는 메타데이터(`originalName`, `contentType`)를 보존하지 않고 `null`로 반환합니다.
- 충돌 정책(동일 파일명/동일 ID), 파일 잠금, 대용량 스트리밍 정책은 현재 단순 구현입니다.

---