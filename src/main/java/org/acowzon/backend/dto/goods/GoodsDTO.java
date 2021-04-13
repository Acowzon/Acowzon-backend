package org.acowzon.backend.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.entity.goods.GoodsTypeEntity;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDTO {

    private UUID goodsId;
    private String goodsName;
    private GoodsTypeEntity goodsType;
    private double goodsPrice;
    private String goodsImage;
    private String goodsSimpleDes;
    private String goodsDescription;
    private int goodsInventory;
    private int soldCount;

    private UUID retailerId;
    private int goodsStarsCount;
    private int views;

}
