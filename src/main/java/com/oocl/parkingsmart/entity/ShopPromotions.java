package com.oocl.parkingsmart.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class ShopPromotions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private Long startTime;
    @NotNull
    private Long endTime;
    private Integer type=0;
    @NotNull
    private String shopMallName;
    @NotNull
    private Double amount;
    @Column(name = "redemptionCode",nullable = false,unique = true)
    private Long redemptionCode;

    public ShopPromotions() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getShopMallName() {
        return shopMallName;
    }

    public void setShopMallName(String shopMallName) {
        this.shopMallName = shopMallName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getRedemptionCode() {
        return redemptionCode;
    }

    public void setRedemptionCode(Long redemptionCode) {
        this.redemptionCode = redemptionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShopPromotions)) return false;
        ShopPromotions that = (ShopPromotions) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
