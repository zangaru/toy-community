package com.toy.community.domain.enums;

public enum BoardCategory {
    FREE;

    public static BoardCategory of(String category) {
        if (category.equalsIgnoreCase("free")) return BoardCategory.FREE;
        else return null;
    }
}
