package com.yx.circle.circle.dto;

public class UpdateCircleRequest {

    private String name;
    private String cover;
    private String description;
    private Long joinPrice;
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
