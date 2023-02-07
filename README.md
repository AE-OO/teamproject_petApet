# teamproject_petApet
team project - pet site (20220927~)

v 0. 1. 1 2023/02/07

- Product 관련 html <hidden> 태그 제거 및 js 추출
- ProductController 클래스 updateProduct() 리팩터링
- CorsConfig 클래스 수정 (https 장애 대응)\
  addAllowedOrigin(“\*”) -> addAllowedOriginPattern(“*”)
- Product 장바구니 담기, 바로구매 수정
- 상품 등록 시 이미지 파일 검증 추가 및 사이즈 제한