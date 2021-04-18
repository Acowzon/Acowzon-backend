package org.acowzon.backend.ctrl.order;


import org.acowzon.backend.ctrl.DefaultWebResponse;
import org.acowzon.backend.ctrl.UUIDParamRequest;
import org.acowzon.backend.ctrl.order.request.QueryOrderByShopAndCustomerRequest;
import org.acowzon.backend.ctrl.order.request.UpdateOrderStatusRequest;
import org.acowzon.backend.ctrl.order.request.UpdatePaymentStatusRequest;
import org.acowzon.backend.dto.order.OrderDetailDTO;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("order")
public class OrderMgnCtrl {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderService orderService;

    @PostMapping(value = "list/all")
    private DefaultWebResponse listAllOrder() {
        return DefaultWebResponse.Builder.success(orderService.listAllOrder());
    }

    @PostMapping(value = "list/byCustomer")
    private DefaultWebResponse listAllOrderByCustomer(@RequestBody UUIDParamRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        return DefaultWebResponse.Builder.success(orderService.listAllOrderByCustomerId(request.getId()));
    }

    @PostMapping(value = "list/byShop")
    private DefaultWebResponse listAllOrderByShop(@RequestBody UUIDParamRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        return DefaultWebResponse.Builder.success(orderService.listAllOrderByShopId(request.getId()));
    }

//    @PostMapping(value = "list/byShopAndCustomer")
//    private DefaultWebResponse listAllOrderByShopAndCustomer(@RequestBody QueryOrderByShopAndCustomerRequest request) throws BusinessException {
//        Assert.notNull(request, "request can not be null");
//        logger.info(request.toString());
//        return DefaultWebResponse.Builder.success(orderService.listAllOrderByShopIdAndCustomerId(request.getShopId(),request.getCustomerId()));
//    }

    @PostMapping(value = "basicInfo")
    private DefaultWebResponse getOrderBasicInfo(@RequestBody UUIDParamRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        return DefaultWebResponse.Builder.success(orderService.getOrderBasicInfo(request.getId()));
    }

    @PostMapping(value = "detail")
    private DefaultWebResponse getOrderDetail(@RequestBody UUIDParamRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        return DefaultWebResponse.Builder.success(orderService.getOrderDetail(request.getId()));
    }

    @PostMapping(value = "items")
    private DefaultWebResponse getOrderItems(@RequestBody UUIDParamRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        return DefaultWebResponse.Builder.success(orderService.getOrderItems(request.getId()));
    }

    @PostMapping(value = "add")
    public DefaultWebResponse addOrder(@RequestBody OrderDetailDTO request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        return DefaultWebResponse.Builder.success("add_order_success",orderService.addOrder(request));
    }

    @PostMapping(value = "delete")
    public DefaultWebResponse deleteOrder(@RequestBody UUIDParamRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        orderService.deleteOrder(request.getId());
        return DefaultWebResponse.Builder.success("delete_order_success");
    }

    @PostMapping(value = "update/orderStatus")
    public DefaultWebResponse updateOrderStatus(@RequestBody UpdateOrderStatusRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        orderService.updateOrderStatus(request.getId(),request.getOrderStatus());
        return DefaultWebResponse.Builder.success("update_order_status_success");
    }

    @PostMapping(value = "update/paymentStatus")
    public DefaultWebResponse updatePaymentStatus(@RequestBody UpdatePaymentStatusRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        orderService.updatePaymentStatus(request.getId(),request.getPaymentStatus());
        return DefaultWebResponse.Builder.success("update_payment_status_success");
    }
}
