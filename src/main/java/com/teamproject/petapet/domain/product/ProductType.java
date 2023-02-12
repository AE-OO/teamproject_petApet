package com.teamproject.petapet.domain.product;

public enum ProductType {
    ALL("전체상품"),
    FOOD("사료"),
    SNACK("간식"),
    WALKING("산책용품"),
    TOY("장난감"),
    FASHION("패션/잡화"),
    STROLLER("유모차"),
    ETC("기타");

    private final String productCategory;

    ProductType(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductCategory() {
        return productCategory;
    }
}

