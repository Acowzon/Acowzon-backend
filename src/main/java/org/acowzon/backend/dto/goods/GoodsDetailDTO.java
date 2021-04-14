package org.acowzon.backend.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.entity.goods.GoodsTypeEntity;
import org.acowzon.backend.entity.shop.ShopEntity;

import java.util.UUID;

@Data
@NoArgsConstructor
public class GoodsDetailDTO {

    private UUID id;

    private String name;

    private GoodsTypeDTO type;

    private double price;

    private String imageUrl;

    private String simpleDes;

    private String description;

    private int inventory;

    private int soldCount;

    private int goodsStarsCount;

    private int views;

    private UUID shopId;

    private String shopName;

}
