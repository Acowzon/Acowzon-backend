package org.acowzon.backend.dto.shop;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.entity.shop.ShopEntity;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ShopCatalogDTO {

    private UUID id;  // 商铺id

    private String name; // 商家名称

    private String imageUrl; // 图片url

    static public ShopCatalogDTO parseDTO(ShopEntity entity) {
        ShopCatalogDTO dto = new ShopCatalogDTO();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }
}
