package org.acowzon.backend.dto.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ShopCatlogDTO {

    private UUID id;  // 商铺id

    private String name; // 商家名称

    private String imageUrl; // 图片url
}
