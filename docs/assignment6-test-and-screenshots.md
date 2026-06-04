# 6단계 테스트 + 스크린샷 정리

- 작성자: 이정환
- 학번: 2071146
- 실행일: 2026-06-05
- GitHub: https://github.com/jwlee1008/webframework2

## 테스트 결과

```bash
mvn test
```

- 결과: BUILD SUCCESS
- 전체 테스트: 23개
- 실패: 0개
- 오류: 0개
- 스킵: 0개

테스트는 `src/test/resources/application.properties` 설정에 따라 H2 인메모리 DB로 실행했습니다.

## 스크린샷 목록

모든 스크린샷에는 하단 푸터의 실습 수행 일시, 학번, 성명이 포함되어 있습니다.

| 번호 | 파일 | 확인 내용 |
|---|---|---|
| 1 | [01-home.jpg](screenshots/01-home.jpg) | 로그인 후 홈 화면 |
| 2 | [02-products-page.jpg](screenshots/02-products-page.jpg) | 상품 목록 페이징, 페이지 네비게이션 |
| 3 | [03-products-search-samsung.jpg](screenshots/03-products-search-samsung.jpg) | `삼성전자` 키워드 검색 결과 |
| 4 | [04-products-search-empty.jpg](screenshots/04-products-search-empty.jpg) | 검색 결과 없음 화면 |
| 5 | [05-product-edit-form.jpg](screenshots/05-product-edit-form.jpg) | ADMIN 상품 수정 폼, 기존 데이터 표시 |
| 6 | [06-product-edit-success.jpg](screenshots/06-product-edit-success.jpg) | 상품 수정 완료 후 목록 화면 |
| 7 | [07-product-edit-forbidden.jpg](screenshots/07-product-edit-forbidden.jpg) | ROLE_USER 상품 수정 접근 시 403 화면 |
| 8 | [08-password-form.jpg](screenshots/08-password-form.jpg) | 비밀번호 변경 폼 |
| 9 | [09-password-error.jpg](screenshots/09-password-error.jpg) | 현재 비밀번호 불일치 오류 |
| 10 | [10-password-success-home.jpg](screenshots/10-password-success-home.jpg) | 비밀번호 변경 완료 후 홈 리다이렉트 |

## 제출 보고서에 넣을 순서

1. 홈 화면과 상품 목록 화면
2. 페이징 화면, 검색 결과 화면, 검색 결과 없음 화면
3. 상품 수정 폼, 수정 완료 화면, 일반 사용자 403 화면
4. 비밀번호 변경 폼, 오류 화면, 변경 완료 화면

이 순서대로 넣으면 과제 문서의 제출물 1~4 요구사항을 모두 설명할 수 있습니다.
