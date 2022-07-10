# 트리플여행자 클럽 마일리지 서비스
트리플 사용자들이 장소에 리뷰를 작성할 때 포인트를 부여하고, 전체/개인에 대한 포인트 부여 히스토리와 개인별
누적 포인트를 관리하고자 합니다.
## 요구사항 분석
리뷰 작성이 이뤄질때마다 리뷰 작성 이벤트가 발생하고, 아래 API로 이벤트를 전달합니다.
```
POST /events
{
"type": "REVIEW",
"action": "ADD", /* "MOD", "DELETE" */
"reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
"content": "좋아요!",
"attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-
851d-4a50-bb07-9cc15cbdc332"],
"userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
"placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}

```
+ 한 사용자는 장소마다 리뷰를 1개만 작성할 수 있고, 리뷰는 수정 또는 삭제할 수 있습니다.
+ 포인트 증감에 따른 CRUD 구현 및 포인트 부여 API 구현
  + 1자 이상 텍스트 작성 : 1점
  + 1자 이상 사진 첨부 : 1점
  + 특정 장소에 첫 리뷰 작성 : 1점
+ 포인트 증감이 있을 때마다 이력
+ 테스트 케이스 작성
--------------------------------------------------------------------------------------
## SQL 스키마
![image](https://user-images.githubusercontent.com/71069665/178139329-ce97e1ee-f43c-49d0-a7ec-71380a195160.png)
<br/>SQL DDL파일은 과제파일과 함께 포함되어 있습니다.
## 실행환경
+ Spring Boot 2.7.1
+ MariaDB 10.5
+ JDK 17
+ Junit5
## 실행방법
URL: localhost:8080
1. Intellij로 서버 실행
2. API 요청에 Postman 사용
3. http://localhost:8080/events (Post)요청 으로 Review CRUD 요청
4. http://localhost:8080/{userId} (Get)요청 으로 사용자 포인트 조회
## Test코드
Service 별로 단위 테스트 진행
* Review service Test
![image](https://user-images.githubusercontent.com/71069665/178142085-668d710e-73fc-4637-9897-f6ccfa916761.png)
* UserPointService Test
![image](https://user-images.githubusercontent.com/71069665/178142112-827c514b-0e03-4266-98a8-be36b26b3e6e.png)


