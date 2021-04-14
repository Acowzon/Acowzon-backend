package org.acowzon.backend.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;

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

    private UUID orderId; // 所属订单id
}
