

추가 목록

1. 소셜 로그인 사용자들을 db에저장 이유는 단순구분을 위함이 아니라 소셜로그인 이용자들의 상품 예매시 유저의 정보를 담아 예약정보가 생성되는데 문제는 부모되는 member에서는 소셜로그인 이용자들의 정보가 없기 때문에 참조 오류가 발생할것을 염려해 추가함



2. common.js 에서의 CheckLogin 메소드는 매 페이지마다 호출되며 로그인 검증을 한다. 허나 관리자인지 아닌지 구분할수 있는 응답 데이터가 없었기에 서버에서 어드민인지 아닌지를 dto를  통해 전달해주었음.
