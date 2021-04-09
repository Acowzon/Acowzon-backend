package org.acowzon.backend.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDTO {
    private String goodsId;
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
}
