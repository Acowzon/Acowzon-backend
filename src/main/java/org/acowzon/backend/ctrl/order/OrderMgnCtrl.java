package org.acowzon.backend.ctrl.order;


import org.acowzon.backend.ctrl.DefaultWebResponse;
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

    @PostMapping(value = "add")
    public DefaultWebResponse addOrder(@RequestBody OrderDetailDTO request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        return DefaultWebResponse.Builder.success("add_order_success",orderService.addOrder(request));
    }
}
