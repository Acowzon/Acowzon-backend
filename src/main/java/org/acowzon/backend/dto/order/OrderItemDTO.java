package org.acowzon.backend.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.entity.order.OrderEntity;
import org.acowzon.backend.entity.order.OrderItemEntity;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderItemDTO {
    private UUID id; // 商品订单id

    private UUID goodsId; // 商品id

    private String goodsName; // 商品名称

    private String imageUrl; // 商品图片地址

    private double price; // 商品单价

    private int amount; // 商品数量

    static public OrderItemDTO parseDTO(OrderItemEntity entity) {
        OrderItemDTO dto = new OrderItemDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setGoodsId(entity.getGoods().getId());
        dto.setGoodsName(entity.getGoods().getName());
        dto.setImageUrl(entity.getGoods().getImageUrl());
        dto.setPrice(entity.getGoods().getPrice());
        return dto;
    }
}
