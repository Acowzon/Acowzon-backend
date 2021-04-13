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

    private UUID id;
    private String name;
    private GoodsTypeEntity type;
    private double price;
    private String imageUrl;
    private String simpleDes;
    private String description;
    private int inventory;
    private int soldCount;

    private UUID retailerId;
    private int goodsStarsCount;
    private int views;

}
