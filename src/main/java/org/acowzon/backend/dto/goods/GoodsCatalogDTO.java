package org.acowzon.backend.dto.goods;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.entity.goods.GoodsEntity;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
@NoArgsConstructor
public class GoodsCatalogDTO {

    private UUID id;
    
    private String name;

    private GoodsTypeDTO type;

    private double price;

    private String imageUrl;

    private String simpleDes;

    private int soldCount;

    private UUID shopId;

    private int goodsStarsCount;

    private int views;

    static public GoodsCatalogDTO parseDTO(GoodsEntity entity) {
        GoodsCatalogDTO dto = new GoodsCatalogDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setType(GoodsTypeDTO.parseDTO(entity.getType()));
        dto.setShopId(entity.getShop().getId());
        return dto;
    }
}
