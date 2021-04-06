package org.acowzon.backend.ctrl.good;

import org.acowzon.backend.ctrl.DefaultWebResponse;
import org.acowzon.backend.ctrl.good.request.GetGoodDetailRequest;
import org.acowzon.backend.ctrl.good.request.UploadGoodRequest;
import org.acowzon.backend.entity.good.Goods;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.service.good.impl.GoodMgnServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("goods")
public class GoodMgnCtrl {
    @Autowired
    GoodMgnServiceImpl goodMgnService;

    @PostMapping("detail")
    public DefaultWebResponse getGoodDetail(@RequestBody GetGoodDetailRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        return DefaultWebResponse.Builder.success(goodMgnService.getGoodById(request.getGoodId()));
    }

    @PostMapping("list")
    public DefaultWebResponse findGood() {
        return DefaultWebResponse.Builder.success(goodMgnService.getAllGoods());
    }

    @PostMapping("uploadGood")
    public DefaultWebResponse uploadGood(@RequestBody UploadGoodRequest request) {
        Goods goods = new Goods(
                request.getGoodsName(), request.getGoodsTypeId(),
                request.getGoodsPrice(), request.getGoodsImage(),
                request.getGoodsSimpleDes(), request.getGoodsDescription(),
                request.getGoodsInventory(), 0, request.getRetailerId(),
                0, 0
        );
        int res = this.goodMgnService.addGood(goods);
        if (res == 0) {
            return DefaultWebResponse.Builder.fail("add good failed");
        } else {
            return DefaultWebResponse.Builder.success("add good success");
        }
    }
    
}
