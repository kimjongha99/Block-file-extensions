# 파일 확장자 차단 시스템 과제

## 도메인
> [FLOW_파일 확장자 차단 시스템](http://3.34.183.32:8080//)

---

## 목차

1. [프로젝트 개요](#프로젝트-개요)
2. [기능](#기능)
3. [설치 및 실행 방법](#설치-및-실행-방법)
4. [트러블슈팅](#트러블슈팅)
5. [기타 정보](#기타-정보)

---

## 프로젝트 개요

이 프로젝트는 파일 업로드 시 특정 확장자명을 차단하기 위한 시스템입니다. 사용자는 고정 확장자와 커스텀 확장자를 설정하여 차단할 파일 확장자를 지정할 수 있습니다. 또한, 사용자가 업로드하는 파일의 확장자를 검사하여 허용 여부를 판단합니다.

---

## 기능

- **고정 확장자 관리**
    - 고정 확장자 리스트 조회
    - 고정 확장자 체크 상태 업데이트
    - 모든 고정 확장자 체크 상태 초기화

- **커스텀 확장자 관리**
    - 커스텀 확장자 추가
    - 커스텀 확장자 리스트 조회
    - 커스텀 확장자 체크 상태 업데이트
    - 커스텀 확장자 삭제
    - 모든 커스텀 확장자 체크 상태 초기화

- **파일 업로드 및 확장자 검사**
    - 사용자가 업로드하는 파일의 확장자를 검사하여 허용 여부를 판단

---

## 설치 및 실행 방법

### 요구 사항

- Java 17
- MySQL

### 설치 과정

1. **프로젝트 클론**
    ```bash
    git clone https://github.com/kimjongha99/Block-file-extensions.git
    cd Block-file-extensions
    ```

2. **빌드**
    ```bash
    ./gradlew build
    ```

3. **서버 실행**
    ```bash
    nohup java -Djasypt.encryptor.password=<your-encryption-password> -jar build/libs/block-file-extensions-0.0.1-SNAPSHOT.jar > application.log &
    ```


---

## 트러블슈팅

### 1. 환경 변수 설정 문제
- **문제**: `JASYPT_ENCRYPTOR_PASSWORD` 환경 변수가 제대로 설정되지 않아 애플리케이션이 시작되지 않음.
- **해결**: 서버에서 환경 변수를 설정하고 이를 확인하는 절차를 추가함.
    ```bash
    echo 'export JASYPT_ENCRYPTOR_PASSWORD=yourpassword' >> ~/.bashrc
    source ~/.bashrc
    ```

- **코드 변경 사항**: `JasyptConfig` 클래스에서 `System.getenv("JASYPT_ENCRYPTOR_PASSWORD")`를 통해 환경 변수를 가져오도록 설정.

    


### 2. 데이터베이스 연결 문제
- **문제**: MySQL 서버에 연결할 수 없다는 오류 발생.
- **해결**: MySQL 서버의 사용자 권한 설정을 수정하여 AWS EC2 인스턴스에서 접근할 수 있도록 함.
    ```sql
    CREATE USER 'newuser'@'%' IDENTIFIED BY 'yourpassword';
    GRANT ALL PRIVILEGES ON *.* TO 'newuser'@'%';
    FLUSH PRIVILEGES;
    ```

- **코드 변경 사항**: `application.yml` 파일에서 데이터베이스 연결 정보를 암호화하여 설정.
    ```yaml
    spring:
      datasource:
        url: ENC(encrypted-url)
        username: newuser
        password: ENC(encrypted-password)
        driver-class-name: com.mysql.cj.jdbc.Driver
    ```

### 3. 인바운드 규칙 문제
- **문제**: 서버에 접근할 수 없는 문제 발생.
- **해결**: AWS 보안 그룹의 인바운드 규칙을 수정하여 HTTP(포트 8080) 접근을 허용함.
    - AWS 관리 콘솔에서 보안 그룹을 열고 인바운드 규칙에 포트 8080을 추가.

### 4. 파일 업로드 시 확장자 검증 문제
- **문제**: 파일 업로드 시 확장자 검증이 제대로 이루어지지 않음.
- **해결**: 클라이언트 측 JavaScript 코드를 수정하여 업로드된 파일의 확장자를 올바르게 검증함.


---

## 기타 정보

- **사용 기술**: Java, Spring Boot, MySQL, Jasypt, AWS EC2
