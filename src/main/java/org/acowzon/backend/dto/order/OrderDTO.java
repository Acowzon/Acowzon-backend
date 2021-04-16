package org.acowzon.backend.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.entity.order.OrderEntity;
import org.acowzon.backend.enums.OrderStatusEnum;
import org.acowzon.backend.enums.PaymentStatusEnum;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderDTO {

    private UUID id; // 订单id

    private UUID customerId; // 客户id

    private UUID shopId; // 商家id

    private String shopName; // 商家名称

    private double orderPrice;  // 订单价格

    private OrderStatusEnum orderStatus; // 订单状态 0未支付 1已支付 2取消订单

    private PaymentStatusEnum paymentStatus; // 支付状态

    private Date createTime; // 订单创建的时间

    private Date updateTime;

    static public OrderDTO parseDTO(OrderEntity entity) {
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setShopId(entity.getShop().getId());
        dto.setShopName(entity.getShop().getName());
        dto.setCustomerId(entity.getCustomer().getId());
        return dto;
    }
}
