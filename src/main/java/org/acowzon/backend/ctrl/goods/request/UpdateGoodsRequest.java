package org.acowzon.backend.ctrl.goods.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UpdateGoodsRequest {

    private UUID id; // 商品id
    private String name; // 商品名称
    private double price; // 商品价格
    private String imageUrl; // 商品图片的存储地址
    private String simpleDes; // 商品的简单描述
    private String description; // 商品的描述

}

