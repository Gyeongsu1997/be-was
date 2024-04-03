# java-was-2023

Java Web myapplication.Application Server 2023

## 구현 내용
1. java.nio 패키지를 사용한 부분을 java.io 패키지를 사용하도록 수정했습니다.
2. GET방식의 회원가입을 POST방식의 회원가입으로 변경하였습니다.
   (GET방식으로 요청을 하면 405 Method Not Allowed 상태 코드를 반환하도록 구현했습니다.)
3. 쿠키와 세션을 이용한 로그인을 구현하였습니다.
4. 세션 정보를 바탕으로 주어진 요청에 대해 동적인 HTML을 응답하도록 구현하였습니다.
5. 게시글 작성, 조회, 수정, 삭제 기능을 구현했습니다.
6. 게시글에 댓글 작성 기능과 댓글 삭제 기능을 구현했습니다.

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.
