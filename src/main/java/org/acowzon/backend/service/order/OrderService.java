package org.acowzon.backend.service.order;


import org.acowzon.backend.dto.order.OrderDTO;
import org.acowzon.backend.entity.order.Order;

public interface OrderService {

    public int createOrder(Order order);

    int deleteOrder(String orderID);

    int updateOrderStatus(String orderID, int status);

    int updateOrderItem(String orderID, String itemID, int num);

    OrderDTO[] showOrderListByBuyer(String userID);

    OrderDTO[] showOrderListBySeller(String sellerID);

    OrderDTO showOrderDetails(String orderID);

}
