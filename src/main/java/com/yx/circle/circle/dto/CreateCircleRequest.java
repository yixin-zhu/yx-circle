package com.yx.circle.circle.dto;

public class CreateCircleRequest {

    private Long categoryId;
    private String name;
    private String cover;
    private String description;
    private Long joinPrice;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getJoinPrice() {
        return joinPrice;
    }

    public void setJoinPrice(Long joinPrice) {
        this.joinPrice = joinPrice;
    }
}
