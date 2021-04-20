package org.acowzon.backend.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acowzon.backend.dto.address.AddressDTO;
import org.acowzon.backend.entity.order.OrderEntity;
import org.acowzon.backend.entity.order.OrderItemEntity;
import org.acowzon.backend.enums.OrderStatusEnum;
import org.acowzon.backend.enums.PaymentStatusEnum;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OrderDetailDTO {

    private UUID id; // 订单id

    private Set<OrderItemDTO> items; // 订单内容

    private UUID customerId; // 客户id

    private UUID shopId; // 商家id

    private String shopName; // 商家名称

    private double orderPrice;  // 订单价格

    private AddressDTO originAddr;

    private AddressDTO destAddr;

    private OrderStatusEnum orderStatus; // 订单状态 0未支付 1已支付 2取消订单

    private PaymentStatusEnum paymentStatus; // 支付状态

    private Date createTime; // 订单创建的时间

    private Date updateTime;

    static public OrderDetailDTO parseDTO(OrderEntity entity) {
        OrderDetailDTO dto = new OrderDetailDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setShopId(entity.getShop().getId());
        dto.setShopName(entity.getShop().getName());
        dto.setCustomerId(entity.getCustomer().getId());
        dto.setItems(entity.getItems().stream().map(OrderItemDTO::parseDTO).collect(Collectors.toSet()));
        dto.setOriginAddr(AddressDTO.parseDTO(entity.getOriginAddress()));
        dto.setDestAddr(AddressDTO.parseDTO(entity.getDestAddress()));
        return dto;
    }

}
