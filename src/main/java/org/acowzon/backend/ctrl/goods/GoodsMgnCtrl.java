package org.acowzon.backend.ctrl.goods;

import org.acowzon.backend.ctrl.DefaultWebResponse;
import org.acowzon.backend.ctrl.goods.request.GetGoodsDetailRequest;
import org.acowzon.backend.ctrl.goods.request.UploadGoodsRequest;
import org.acowzon.backend.entity.goods.Goods;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.service.goods.impl.GoodsMgnServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("goods")
public class GoodsMgnCtrl {
    @Autowired
    GoodsMgnServiceImpl goodsMgnService;

    @PostMapping("detail")
    public DefaultWebResponse getGoodDetail(@RequestBody GetGoodsDetailRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        return DefaultWebResponse.Builder.success(goodsMgnService.getGoodsById(request.getGoodsId()));
    }

    @PostMapping("list")
    public DefaultWebResponse findGood() {
        return DefaultWebResponse.Builder.success(goodsMgnService.getAllGoods());
    }

    @PostMapping("uploadGoods")
    public DefaultWebResponse uploadGood(@RequestBody UploadGoodsRequest request) {
        Goods goods = new Goods(
                request.getGoodsName(), request.getGoodsTypeId(),
                request.getGoodsPrice(), request.getGoodsImage(),
                request.getGoodsSimpleDes(), request.getGoodsDescription(),
                request.getGoodsInventory(), 0, request.getRetailerId(),
                0, 0
        );
        int res = this.goodsMgnService.addGoods(goods);
        if (res == 0) {
            return DefaultWebResponse.Builder.fail("add goods failed");
        } else {
            return DefaultWebResponse.Builder.success("add goods success");
        }
    }
    
}
