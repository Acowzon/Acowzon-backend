package org.acowzon.backend.ctrl.goods.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AddGoodsRequest {

    private String goodsName; // 商品名称
    private UUID goodsTypeId; // 商品类型
    private double goodsPrice; // 商品价格
    private String goodsImage; // 商品图片的存储地址
    private String goodsSimpleDes; // 商品的简单描述
    private String goodsDescription; // 商品的描述
    private int goodsInventory; // 商品库存
    private UUID retailerId; // 商品上架商家的id

}
