# petApet
> Team Project - 반려동물을 위한 쇼핑 & 커뮤니티 웹사이트
<img src="https://user-images.githubusercontent.com/67586183/219962991-8dcad516-5377-42e1-8bc9-2fc3f7a36df9.png" width="60%" height="60%">

### 프로젝트 요약

- 개발 기간 : 2022.09.22~배포 수정 중
- 사용 대상
    - 반려동물을 키우거나 관심있는 사람
- 문제 의식
    - 반려동물 인구 1500만 시대, 반려돌물 양육 상황이 다양해지면서 정보 수요도 늘어나고 있다.
    - 반려인과 반려동물 상황에 따라 다양한 정보가 필요하지만 필요 정보를 구하거나 적합한 정보인지 판단하기 어렵다.
- 제공 서비스
    - 플랫폼 내에서 커뮤니티를 통해 반려동물의 일상생활부터 제품 추천까지 다양한 정보를 서로 공유할 수 있는 장을 마련하고, 더불어 반려동물을 위한 실용적인 제품을 제공하고 구입할 수 있다.
    
### 개발환경 및 개발언어
- IDE : IntelliJ
- Framework : SpringBoot 2.7.3
- Database : MySQL 8.0
- WAS : Apache Tomcat 9.0
- Platform : OpenJDK11
- OS : MAC
- 개발언어 : JAVA, SQL, HTML5, CSS3, JavaScript, Jquery
- ETC : Spring Data JPA, Spring Security, JWT
- 배포 : AWS EC2, AWS RDS

### 상세내용
<details>
  <summary>ERD</summary>
  <div markdown="1">
    <img src = "https://user-images.githubusercontent.com/67586183/219963405-76970429-c7af-48b2-a287-b1b134884bae.png"> 
  </div>
</details> 

<details>
  <summary>담당 기능</summary> 
  
  -----------
<details>
  <summary>회원가입</summary>
  <div markdown="1">
  <img src="https://user-images.githubusercontent.com/67586183/219965815-6ed4b483-dd26-4347-a566-910e8f6c498e.png">
<!--     <video src="https://user-images.githubusercontent.com/67586183/219942243-bcf7c4c8-2055-4197-9c38-37f57b12edcf.mov"> <br/> -->
  </div>
</details>  

<details>
  <summary>로그인</summary>
  <div markdown="1">
  <img src="https://user-images.githubusercontent.com/67586183/219966407-499a9ca3-5f5d-418e-baaa-033a25adc648.png">
  </div>
</details> 

<details>
  <summary>아이디, 비밀번호 찾기</summary>
  <div markdown="1">
  <img src="https://user-images.githubusercontent.com/67586183/219967653-11c023c1-5018-4ece-909c-d61855518ae6.png">
  </div>
</details> 
  
<details>
  <summary>회원 정보 수정 + 회원 탈퇴</summary>
  <div markdown="1">
  <img src="https://user-images.githubusercontent.com/67586183/219967435-46f98c7c-2537-4a24-8be9-ec189826bee3.png">
  </div>
</details>
  
<details>
  <summary>커뮤니티 메인</summary>
  <div markdown="1">
  <img src="https://user-images.githubusercontent.com/67586183/219968116-7e01caf1-4862-4f09-a003-af7c3d6ecf2e.png">
  <img src="https://user-images.githubusercontent.com/67586183/219968118-eb0a02e4-79ff-494e-abe1-6a6b2cb46017.png">
  </div>
</details>

<details>
  <summary>커뮤니티 - 게시글</summary>
  <div markdown="1">
  <img src="https://user-images.githubusercontent.com/67586183/219968539-70a4cdf3-0241-480c-bb52-db91542c86cf.png">
  </div>
</details>

<details>
  <summary>커뮤니티 - 댓글</summary>
  <div markdown="1">
  <img src="https://user-images.githubusercontent.com/67586183/219968913-dfd4cddc-c0cf-4c5c-9465-adc20e6d6f9d.png">
  </div>
</details>
  
<details>
  <summary>쪽지 보내기</summary>
  <div markdown="1">
  <img src="https://user-images.githubusercontent.com/67586183/219969153-33983ff1-10b1-4aca-8171-a18255d33088.png">
  </div>
</details>

</details>


### 배포 버전 관리 

<details>
  <summary>수정 내역</summary>
  <div markdown="1">
    v 0. 1. 1 2023/02/07

- Product 관련 html <hidden> 태그 제거 및 js 추출
- ProductController 클래스 updateProduct() 리팩터링
- CorsConfig 클래스 수정 (https 장애 대응)\
  addAllowedOrigin(“\*”) -> addAllowedOriginPattern(“*”)
- Product 장바구니 담기, 바로구매 수정
- 상품 등록 시 이미지 파일 검증 추가 및 사이즈 제한
  src/main/resources/application.properties
  
v 0. 2. 0 2023/02/13

- 모든 장바구니 추가, 바로 구매 버튼 수정
  로그인 상태 및 role 검증, 상황별 예외 처리
- 장바구니에 이미 같은 상품이 있는지 검증
  존재하면 새로 추가 안되고 수정 되게 변경
- 장바구니 전체 삭제 개선
- 쿠폰 적용하기 기능 구현 (정가 할인만 가능, 백분율 할인은 보류)
- Buy와 Product 사이에 BuyProduct 엔티티 추가
- 찜하기 기능 개선

** Buy에 있는 Product와 quantity는 더 이상 쓰면 안 됩니다.
    기존에 구현된 기능은 정상 작동하게 바꿨습니다.

v 0. 2. 1 2023/02/14
- 신고와 관련된 DTO에서 발생하는 InvalidDefinitionException 처리
- 사업자 승인, 신고 승인 관련 IllegalStateException 처리
- 신고 승인 관련 Controller에서 예외 처리 (현재 커뮤니티 신고 승인 컨트롤러에만 구현)

v 0. 2. 3 2023/02/14
- 회원, 커뮤니티 관련 IllegalStateException 처리 
- 게시글 작성한 회원이, 게시글 비밀 댓글 볼 수 있도록 수정

v 0. 2. 4 2023/02/17
- 문의하기 수정
- html 필요없는 태그 삭제
  </div>
</details> 
