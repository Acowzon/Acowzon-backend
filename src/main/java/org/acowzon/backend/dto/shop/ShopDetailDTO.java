package org.acowzon.backend.dto.shop;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.entity.goods.GoodsEntity;
import org.acowzon.backend.entity.address.AddressEntity;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ShopDetailDTO {

    private UUID id;  // 商铺id

    private String name; // 商家名称

    private String imageUrl; // 图片url

    private Set<GoodsEntity> goodsSet; // 上架商品

    private Set<AddressEntity> address; // 商铺地址

}
