package org.acowzon.backend.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.enums.OrderStatusEnum;
import org.acowzon.backend.enums.PaymentStatusEnum;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderDTO {

    private UUID id; // 订单id

    private UUID customerId; // 客户id

    private String customerName; //客户账户名称

    private UUID shopId; // 商家id

    private String shopName; // 商家名称

    private double orderPrice;  // 订单价格

    private OrderStatusEnum orderState; // 订单状态 0未支付 1已支付 2取消订单

    private PaymentStatusEnum paymentState; // 支付状态

    private Date createTime; // 订单创建的时间
}
