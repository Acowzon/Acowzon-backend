package org.acowzon.backend.ctrl.shop;

import org.acowzon.backend.ctrl.DefaultWebResponse;
import org.acowzon.backend.ctrl.UUIDParamRequest;
import org.acowzon.backend.ctrl.shop.request.UpdateShopAddressRequest;
import org.acowzon.backend.ctrl.shop.request.UpdateShopAdminRequest;
import org.acowzon.backend.ctrl.shop.request.UpdateShopOwnerRequest;
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

    @PostMapping("list/all")
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

    @PostMapping("update")
    public DefaultWebResponse updateShop(@RequestBody ShopDetailDTO request) throws BusinessException {
        Assert.notNull(request,"request can not be null");
        logger.info(request.toString());
        shopMgnService.updateShop(request);
        return DefaultWebResponse.Builder.success("update_shop_success");
    }

    @PostMapping("delete")
    public DefaultWebResponse deleteShop(@RequestBody UUIDParamRequest request) throws BusinessException {
        Assert.notNull(request,"request can not be null");
        logger.info(request.toString());
        shopMgnService.deleteShop(request.getId());
        return DefaultWebResponse.Builder.success("delete_shop_success");
    }

    @PostMapping("updateOwner")
    public DefaultWebResponse updateShopOwner(@RequestBody UpdateShopOwnerRequest request) throws BusinessException {
        Assert.notNull(request,"request can not be null");
        logger.info(request.toString());
        shopMgnService.updateShopOwner(request.getShopId(),request.getOwnerId());
        return DefaultWebResponse.Builder.success("update_shop_owner_success");
    }

    @PostMapping("addAdmin")
    public DefaultWebResponse addShopAdmin(@RequestBody UpdateShopAdminRequest request) throws BusinessException {
        Assert.notNull(request,"request can not be null");
        logger.info(request.toString());
        shopMgnService.addShopAdmin(request.getShopId(),request.getAdminId());
        return DefaultWebResponse.Builder.success("add_shop_admin_success");
    }

    @PostMapping("deleteAdmin")
    public DefaultWebResponse deleteShopAdmin(@RequestBody UpdateShopAdminRequest request) throws BusinessException {
        Assert.notNull(request,"request can not be null");
        logger.info(request.toString());
        shopMgnService.deleteShopAdmin(request.getShopId(),request.getAdminId());
        return DefaultWebResponse.Builder.success("delete_shop_admin_success");
    }

    @PostMapping("addAddr")
    public DefaultWebResponse addShopAddr(@RequestBody UpdateShopAddressRequest request) throws BusinessException {
        Assert.notNull(request,"request can not be null");
        logger.info(request.toString());
        shopMgnService.addShopAddress(request.getShopId(),request.getAddress());
        return DefaultWebResponse.Builder.success("add_shop_admin_success");
    }

    @PostMapping("deleteAddr")
    public DefaultWebResponse deleteShopAddr(@RequestBody UpdateShopAddressRequest request) throws BusinessException {
        Assert.notNull(request,"request can not be null");
        logger.info(request.toString());
        shopMgnService.deleteShopAddress(request.getShopId(),request.getAddress().getId());
        return DefaultWebResponse.Builder.success("delete_shop_admin_success");
    }
}
