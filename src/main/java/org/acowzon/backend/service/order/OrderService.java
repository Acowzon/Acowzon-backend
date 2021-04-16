package org.acowzon.backend.service.order;

import org.acowzon.backend.dto.order.OrderDTO;
import org.acowzon.backend.dto.order.OrderDetailDTO;
import org.acowzon.backend.dto.order.OrderItemDTO;
import org.acowzon.backend.enums.OrderStatusEnum;
import org.acowzon.backend.enums.PaymentStatusEnum;
import org.acowzon.backend.exception.BusinessException;

import java.util.UUID;

public interface OrderService {

    /**
     * 列出所有订单
     * @return OrderDTO[]
     */
    OrderDTO[] listAllOrder();

    /**
     * 列出用户的全部订单
     * @param id 用户id
     * @return OrderDTO[]
     * @throws BusinessException 业务相关异常
     */
    OrderDTO[] listAllOrderByCustomerId(UUID id) throws BusinessException;

    /**
     * 列出商铺的全部订单
     * @param id 用户id
     * @return OrderDTO[]
     * @throws BusinessException 业务相关异常
     */
    OrderDTO[] listAllOrderByShopId(UUID id) throws BusinessException;

    /**
     * 按商铺id和用户id列出订单
     * @param shopId 商铺id
     * @param customerId 用户id
     * @return OrderDTO[]
     * @throws BusinessException 业务相关异常
     */
    OrderDTO[] listAllOrderByShopIdAndCustomerId(UUID shopId,UUID customerId) throws BusinessException;

    /**
     * 获取订单基本信息
     * @param id 订单id
     * @return OrderDTO
     * @throws BusinessException 业务相关异常
     */
    OrderDTO getOrderBasicInfo(UUID id) throws BusinessException;

    /**
     * 获取订单详细信息
     * @param id 订单id
     * @return OrderDetailDTO
     * @throws BusinessException 业务相关异常
     */
    OrderDetailDTO getOrderDetail(UUID id) throws BusinessException;

    /**
     * 获取子订单信息
     * @param id 订单id
     * @return OrderItemDTO[]
     * @throws BusinessException 业务相关异常
     */
    OrderItemDTO[] getOrderItems(UUID id) throws BusinessException;

    /**
     * 创建订单
     * @param orderDetailDTO 订单信息
     * @return 订单id
     * @throws BusinessException 业务相关异常
     */
    UUID addOrder(OrderDetailDTO orderDetailDTO) throws BusinessException;

    /**
     * 删除订单
     * @param id 订单id
     * @throws BusinessException 业务相关异常
     */
    void removeOrder(UUID id) throws BusinessException;

    /**
     * 更新订单状态
     * @param id 订单id
     * @param orderStatus 订单状态
     * @throws BusinessException 业务相关异常
     */
    void updateOrderStatus(UUID id, OrderStatusEnum orderStatus) throws BusinessException;

    /**
     * 更新支付状态
     * @param id 订单id
     * @param paymentStatus 支付状态
     * @throws BusinessException 业务相关异常
     */
    void updatePaymentStatus(UUID id, PaymentStatusEnum paymentStatus) throws BusinessException;
}
