package org.acowzon.backend.ctrl.shop;

import org.acowzon.backend.ctrl.DefaultWebResponse;
import org.acowzon.backend.ctrl.goods.request.UUIDParamRequest;
import org.acowzon.backend.dto.shop.ShopDetailDTO;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.service.shop.ShopMgnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("shop")
public class ShopMgnCtrl {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ShopMgnService shopMgnService;

    @PostMapping("listAll")
    public DefaultWebResponse listAllShops() {
        return DefaultWebResponse.Builder.success(shopMgnService.listAllShop());
    }

    @PostMapping("detail")
    public DefaultWebResponse listAllShop(@RequestBody UUIDParamRequest request) throws BusinessException {
        Assert.notNull(request,"request can not be null");
        logger.info(request.toString());
        return DefaultWebResponse.Builder.success(shopMgnService.getShopDetail(request.getId()));
    }

    @PostMapping("add")
    public DefaultWebResponse addShop(@RequestBody ShopDetailDTO request) throws BusinessException {
        Assert.notNull(request,"request can not be null");
        logger.info(request.toString());
        return DefaultWebResponse.Builder.success("add_shop_success",shopMgnService.addShop(request));
    }
}
