package org.acowzon.backend.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.entity.goods.GoodsEntity;
import org.acowzon.backend.entity.goods.GoodsTypeEntity;
import org.acowzon.backend.entity.shop.ShopEntity;
import org.springframework.beans.BeanUtils;

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

    static public GoodsDetailDTO parseDTO(GoodsEntity entity) {
        GoodsDetailDTO dto = new GoodsDetailDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setType(GoodsTypeDTO.parseDTO(entity.getType()));
        dto.setShopId(entity.getShop().getId());
        dto.setShopName(entity.getShop().getName());
        return dto;
    }

}
