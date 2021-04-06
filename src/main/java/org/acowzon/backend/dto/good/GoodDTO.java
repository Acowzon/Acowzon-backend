package org.acowzon.backend.dto.good;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodDTO {
    private String goodsName;
    private String goodsTypeId;
    private double goodsPrice;
    private String goodsImage;
    private String goodsSimpleDes;
    private String goodsDescription;
    private int goodsInventory;
    private int soldCount;
    private int retailerId;
    private int goodsStarsCount;
    private int views;

    @Override
    public String toString() {
        return "GoodDTO{" +
                "goodsName='" + goodsName + '\'' +
                ", goodsTypeId='" + goodsTypeId + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsImage='" + goodsImage + '\'' +
                ", goodsSimpleDes='" + goodsSimpleDes + '\'' +
                ", goodsDescription='" + goodsDescription + '\'' +
                ", goodsInventory=" + goodsInventory +
                ", soldCount=" + soldCount +
                ", retailerId=" + retailerId +
                ", goodsStarsCount=" + goodsStarsCount +
                ", views=" + views +
                '}';
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(String goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsSimpleDes() {
        return goodsSimpleDes;
    }

    public void setGoodsSimpleDes(String goodsSimpleDes) {
        this.goodsSimpleDes = goodsSimpleDes;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public int getGoodsInventory() {
        return goodsInventory;
    }

    public void setGoodsInventory(int goodsInventory) {
        this.goodsInventory = goodsInventory;
    }

    public int getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(int soldCount) {
        this.soldCount = soldCount;
    }

    public int getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(int retailerId) {
        this.retailerId = retailerId;
    }

    public int getGoodsStarsCount() {
        return goodsStarsCount;
    }

    public void setGoodsStarsCount(int goodsStarsCount) {
        this.goodsStarsCount = goodsStarsCount;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
