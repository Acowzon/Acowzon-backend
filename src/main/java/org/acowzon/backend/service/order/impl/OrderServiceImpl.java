package org.acowzon.backend.service.order.impl;

import org.acowzon.backend.dao.address.AddressDAO;
import org.acowzon.backend.dao.goods.GoodsDAO;
import org.acowzon.backend.dao.order.OrderDAO;
import org.acowzon.backend.dao.order.OrderItemDAO;
import org.acowzon.backend.dao.shop.ShopDAO;
import org.acowzon.backend.dao.user.UserDAO;
import org.acowzon.backend.dto.order.OrderDTO;
import org.acowzon.backend.dto.order.OrderDetailDTO;
import org.acowzon.backend.dto.order.OrderItemDTO;
import org.acowzon.backend.entity.address.AddressEntity;
import org.acowzon.backend.entity.order.OrderEntity;
import org.acowzon.backend.entity.order.OrderItemEntity;
import org.acowzon.backend.entity.shop.ShopEntity;
import org.acowzon.backend.entity.user.UserEntity;
import org.acowzon.backend.enums.OrderStatusEnum;
import org.acowzon.backend.enums.PaymentStatusEnum;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    OrderItemDAO orderItemDAO;

    @Autowired
    AddressDAO addressDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    ShopDAO shopDAO;

    @Autowired
    GoodsDAO goodsDAO;

    /**
     * 列出所有订单
     *
     * @return OrderDTO[]
     */
    @Override
    public OrderDTO[] listAllOrder() {
        return orderDAO.findAll().stream().map(OrderDTO::parseDTO).toArray(OrderDTO[]::new);
    }

    /**
     * 列出用户的全部订单
     *
     * @param id 用户id
     * @return OrderDTO[]
     */
    @Override
    public OrderDTO[] listAllOrderByCustomerId(UUID id) {
        return orderDAO.findAllByCustomer(new UserEntity(id)).stream().map(OrderDTO::parseDTO).toArray(OrderDTO[]::new);
    }

    /**
     * 列出商铺的全部订单
     *
     * @param id 用户id
     * @return OrderDTO[]
     */
    @Override
    public OrderDTO[] listAllOrderByShopId(UUID id) {
        return orderDAO.findAllByShop(new ShopEntity(id)).stream().map(OrderDTO::parseDTO).toArray(OrderDTO[]::new);
    }

    /**
     * 按商铺id和用户id列出订单
     *
     * @param shopId     商铺id
     * @param customerId 用户id
     * @return OrderDTO[]
     */
    @Override
    public OrderDTO[] listAllOrderByShopIdAndCustomerId(UUID shopId, UUID customerId) {
        return orderDAO.findAllByShopAndCustomer(new ShopEntity(shopId), new UserEntity(customerId)).stream().map(OrderDTO::parseDTO).toArray(OrderDTO[]::new);
    }

    /**
     * 获取订单基本信息
     *
     * @param id 订单id
     * @return OrderDTO
     * @throws BusinessException 业务相关异常
     */
    @Override
    public OrderDTO getOrderBasicInfo(UUID id) throws BusinessException {
        Optional<OrderEntity> orderEntityOptional = orderDAO.findById(id);
        if (orderEntityOptional.isPresent()) {
            return OrderDTO.parseDTO(orderEntityOptional.get());
        }
        throw new BusinessException("no_such_order");
    }

    /**
     * 获取订单详细信息
     *
     * @param id 订单id
     * @return OrderDetailDTO
     * @throws BusinessException 业务相关异常
     */
    @Override
    public OrderDetailDTO getOrderDetail(UUID id) throws BusinessException {
        Optional<OrderEntity> orderEntityOptional = orderDAO.findById(id);
        if (orderEntityOptional.isPresent()) {
            return OrderDetailDTO.parseDTO(orderEntityOptional.get());
        }
        throw new BusinessException("no_such_order");
    }

    /**
     * 获取子订单信息
     *
     * @param id 订单id
     * @return OrderItemDTO[]
     * @throws BusinessException 业务相关异常
     */
    @Override
    public OrderItemDTO[] getOrderItems(UUID id) throws BusinessException {
        Optional<OrderEntity> orderEntityOptional = orderDAO.findById(id);
        if (orderEntityOptional.isPresent()) {
            return orderEntityOptional.get().getItems().stream().map(OrderItemDTO::parseDTO).toArray(OrderItemDTO[]::new);
        }
        throw new BusinessException("no_such_order");
    }

    /**
     * 创建订单
     *
     * @param orderDetailDTO 订单信息
     * @return 订单id
     * @throws BusinessException 业务相关异常
     */
    @Override
    @Transactional
    @Modifying
    public UUID addOrder(OrderDetailDTO orderDetailDTO) throws BusinessException {
        OrderEntity orderEntity = new OrderEntity();

        Optional<UserEntity> customerOptional = userDAO.findById(orderDetailDTO.getCustomerId());
        if(!customerOptional.isPresent()) {
            throw new BusinessException("no_such_customer");
        }
        Optional<ShopEntity> shopOptional = shopDAO.findById(orderDetailDTO.getShopId());
        if(!shopOptional.isPresent()) {
            throw new BusinessException("no_such_shop");
        }

        orderEntity.setCustomer(customerOptional.get());
        orderEntity.setShop(shopOptional.get());

        orderEntity.setOrderStatus(OrderStatusEnum.NEW);
        orderEntity.setPaymentStatus(PaymentStatusEnum.WAITING);
        orderEntity.setCreateTime(new Date());
        orderEntity.setUpdateTime(new Date());

        //Todo 订单的地址需要和地址实体绑定么？还是仅仅保存一个说明性的字符串即可？
        if (orderDetailDTO.getOriginAddr().getId() != null) {
            addressDAO.findById(orderDetailDTO.getOriginAddr().getId()).ifPresent(orderEntity::setOriginAddress);
        }
        AddressEntity newOriginAddr = new AddressEntity();
        BeanUtils.copyProperties(orderDetailDTO.getOriginAddr(), newOriginAddr, "id");//忽略id字段，让后端自动生成
        orderEntity.setOriginAddress(newOriginAddr);

        if (orderDetailDTO.getDestAddr().getId() != null ) {
            addressDAO.findById(orderDetailDTO.getDestAddr().getId()).ifPresent(orderEntity::setDestAddress);
        }
        AddressEntity newDestAddr = new AddressEntity();
        BeanUtils.copyProperties(orderDetailDTO.getDestAddr(), newDestAddr, "id");//忽略id字段，让后端自动生成
        orderEntity.setDestAddress(newDestAddr);

        //todo 这里把非法商品id的异常吞了
        Set<OrderItemEntity> itemSet = orderDetailDTO.getItems().stream().map(orderItemDTO -> {
            OrderItemEntity entity = new OrderItemEntity();
            if (orderItemDTO.getGoodsId() != null) {
                goodsDAO.findByShopAndId(new ShopEntity(orderDetailDTO.getShopId()),orderItemDTO.getGoodsId()).ifPresent(
                        goodsEntity -> {
                            entity.setGoods(goodsEntity);
                            BeanUtils.copyProperties(orderItemDTO, entity);
                        }
                );
            }
            return entity;
        }).collect(Collectors.toSet());

        Double orderPrice = itemSet.stream().mapToDouble(orderItemEntity -> orderItemEntity.getGoods().getPrice() * orderItemEntity.getAmount()).sum();

        orderEntity.setItems(itemSet);
        orderEntity.setOrderPrice(orderPrice);

        orderEntity.setId(UUID.randomUUID());

        return orderDAO.save(orderEntity).getId();
    }

    /**
     * 删除订单
     *
     * @param id 订单id
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void removeOrder(UUID id) throws BusinessException {
        Optional<OrderEntity> orderEntityOptional = orderDAO.findById(id);
        if (orderEntityOptional.isPresent()) {
            orderDAO.deleteById(id);
        }
        throw new BusinessException("no_such_order");
    }

    /**
     * 更新订单状态
     *
     * @param id          订单id
     * @param orderStatus 订单状态
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void updateOrderStatus(UUID id, OrderStatusEnum orderStatus) throws BusinessException {
        Optional<OrderEntity> orderEntityOptional = orderDAO.findById(id);
        if (orderEntityOptional.isPresent()) {
            orderEntityOptional.get().setOrderStatus(orderStatus);
            orderEntityOptional.get().setUpdateTime(new Date());
            orderDAO.save(orderEntityOptional.get());
        }
        throw new BusinessException("no_such_order");
    }

    /**
     * 更新支付状态
     *
     * @param id            订单id
     * @param paymentStatus 支付状态
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void updatePaymentStatus(UUID id, PaymentStatusEnum paymentStatus) throws BusinessException {
        Optional<OrderEntity> orderEntityOptional = orderDAO.findById(id);
        if (orderEntityOptional.isPresent()) {
            orderEntityOptional.get().setPaymentStatus(paymentStatus);
            orderEntityOptional.get().setUpdateTime(new Date());
            orderDAO.save(orderEntityOptional.get());
        }
        throw new BusinessException("no_such_order");
    }
}
