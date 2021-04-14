package org.acowzon.backend.dto.goods;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.entity.goods.GoodsTypeEntity;

import java.util.UUID;

@Data
@NoArgsConstructor
public class GoodsCatalogDTO {

    private UUID id;
    
    private String name;

    private GoodsTypeEntity type;

    private double price;

    private String imageUrl;

    private String simpleDes;

    private int soldCount;

    private UUID shopId;

    private int goodsStarsCount;

    private int views;


}
