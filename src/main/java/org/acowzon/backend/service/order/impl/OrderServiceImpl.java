package org.acowzon.backend.service.order.impl;

import org.acowzon.backend.dto.order.OrderDTO;
import org.acowzon.backend.entity.order.Order;
import org.acowzon.backend.mapper.order.OrderMapper;
import org.acowzon.backend.service.order.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public int createOrder(Order order) {
        return this.orderMapper.addOrder(order);
    }

    @Override
    public int deleteOrder(String orderID) {
        return this.orderMapper.delOrderById(orderID);
    }

    @Override
    public int updateOrderStatus(String orderID, int status) {
        return this.orderMapper.updateOrderState(orderID, status);
    }

    @Override
    public int updateOrderItem(String orderID, String itemID, int num) {
        return 0;
    }


    @Override
    public OrderDTO[] showOrderListByBuyer(String userID) {
        return this.orderMapper.queryOrderByBuyer(userID).stream().map(order -> {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(order, orderDTO);
            return orderDTO;
        }).toArray(OrderDTO[]::new);
    }

    @Override
    public OrderDTO[] showOrderListBySeller(String sellerID) {
        return this.orderMapper.queryOrderByRetailer(sellerID).stream().map(order -> {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(order, orderDTO);
            return orderDTO;
        }).toArray(OrderDTO[]::new);
    }

    @Override
    public OrderDTO showOrderDetails(String orderID) {
        Order order = this.orderMapper.queryOrderById(orderID);
        //System.out.println(order.toString());
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        return orderDTO;
    }
}
