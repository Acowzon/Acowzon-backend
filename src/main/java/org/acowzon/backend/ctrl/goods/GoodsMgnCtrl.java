package org.acowzon.backend.ctrl.goods;

import org.acowzon.backend.ctrl.DefaultWebResponse;
import org.acowzon.backend.ctrl.UUIDParamRequest;
import org.acowzon.backend.ctrl.goods.request.*;
import org.acowzon.backend.dto.goods.GoodsDetailDTO;
import org.acowzon.backend.dto.goods.GoodsTypeDTO;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.service.goods.GoodsMgnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("goods")
public class GoodsMgnCtrl {

    Logger logger = LoggerFactory.getLogger(GoodsMgnCtrl.class);

    @Autowired
    GoodsMgnService goodsMgnService;

    /**
     * @apiDefine ErrorResponse
     * @apiError {json} data='""' 响应体
     * @apiError {String} status="error" 响应状态
     * @apiError {String} msg 错误信息
     * @apiErrorExample  {json} 错误响应样例
     * {
     *   "data": "",
     *   "status": "error”,
     *   "msg": "错误信息“
     * }
     */

    /**
     * @apiDefine SuccessResponse
     * @apiSuccess {json} data='""' 响应体
     * @apiSuccess {String} status="success" 响应状态
     * @apiSuccess {String} msg 错误信息
     * @apiErrorExample  {json} 错误响应样例
     * {
     *   "data": "",
     *   "status": "success”,
     *   "msg": "响应消息”
     * }
     */

    /**
     * @api {POST} goods/detail 获取商品的详细信息
     * @apiGroup 商品管理
     * @apiDescription 获取商品的详细信息
     * @apiParam {UUID} goodsId 商品id
     * @apiParamExample {json} 请求样例
     * {
     * "goodsId": "7b51cd91-a06c-43a7-b689-f419df1ef441"
     * }
     * @apiSuccess {json} data 响应体
     * @apiSuccess {UUID} data.goodsId 商品id
     * @apiSuccess {String} data.goodsName 商品名称
     * @apiSuccess {UUID} data.goodsTypeId 商品类型id
     * @apiSuccess {Double} data.goodsPrice 商品单价
     * @apiSuccess {String} data.imageUrl 商品图片地址
     * @apiSuccess {UUID} data.goodsId 商品id
     * @apiSuccess {String} data.goodsSimpleDes 商品简单描述
     * @apiSuccess {String} data.goodsDescription 商品完整描述
     * @apiSuccess {UUID} data.goodsTypeId 商品类型id
     * @apiSuccess {Integer} data.goodsInventory 商品库存
     * @apiSuccess {Integer} data.soldCount 销售量
     * @apiSuccess {UUID} data.retailerId 所属商家Id
     * @apiSuccess {Integer} data.goodsStarsCount 收藏量
     * @apiSuccess {Integer} data.views 浏览量
     * @apiSuccess {String} status="success" 响应状态
     * @apiSuccess {String} msg 响应消息
     * @apiSuccessExample {json} 成功响应样例
     * {
     * "data": {
     * "goodsId": "7b51cd91-a06c-43a7-b689-f419df1ef448",
     * "goodsName": "男士牛仔裤",
     * "goodsTypeId": "4a1c24ad-6d3f-4953-ad9f-f2dde87fd9cc",
     * "goodsPrice": 211.32,
     * "imageUrl": "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fgw3.alicdn.com%2Fbao%2Fuploaded%2Fi4%2FTB1hMgaFVXXXXbcXFXXXXXXXXXX_%21%210-item_pic.jpg_600x600.jpg&refer=http%3A%2F%2Fgw3.alicdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1620457937&t=35f5c721454338f02e64b6b030ade8dc",
     * "goodsSimpleDes": "牛仔裤",
     * "goodsDescription": "这是一条用来展示的牛仔裤",
     * "goodsInventory": 12,
     * "soldCount": 2,
     * "retailerId": "4a1c24ad-6d3f-4953-ad9f-f2dde87899cc",
     * "goodsStarsCount": 1,
     * "views": 1
     * },
     * "status": "success",
     * "msg": ""
     * }
     * @apiUse ErrorResponse
     */
    @PostMapping("detail")
    public DefaultWebResponse getGoodsDetail(@RequestBody UUIDParamRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        return DefaultWebResponse.Builder.success(goodsMgnService.getGoodsById(request.getId()));
    }

    @PostMapping("list/all")
    public DefaultWebResponse listAllGoods() {
        return DefaultWebResponse.Builder.success(goodsMgnService.getAllGoods());
    }

    @PostMapping("list/shop")
    public DefaultWebResponse listShopGoods(@RequestBody UUIDParamRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        return DefaultWebResponse.Builder.success(goodsMgnService.getGoodsByShopId(request.getId()));
    }

    @PostMapping("query")
    public DefaultWebResponse queryGoods(@RequestBody Map<String, Object> map) {
        Assert.notNull(map, "request can not be null");
        //todo:分页查询，多条件查询
        return DefaultWebResponse.Builder.success(goodsMgnService.queryGoods(map));
    }

    @PostMapping("add")
    public DefaultWebResponse addGoods(@RequestBody GoodsDetailDTO request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());

        return DefaultWebResponse.Builder.success("add_goods_success", goodsMgnService.addGoods(request));
    }

    @PostMapping("update")
    public DefaultWebResponse updateGoods(@RequestBody GoodsDetailDTO request) throws BusinessException {
        Assert.notNull(request, "update goods request can not be null");
        logger.info(request.toString());
        goodsMgnService.updateGoods(request);
        return DefaultWebResponse.Builder.success("update_goods_success");
    }

    @PostMapping("delete")
    public DefaultWebResponse deleteGoods(@RequestBody UUIDParamRequest request) throws BusinessException {
        Assert.notNull(request, "delete goods request can not be null");
        logger.info(request.toString());
        goodsMgnService.deleteGoods(request.getId());
        return DefaultWebResponse.Builder.success("delete_goods_success");
    }

    @PostMapping("soldCount/update")
    public DefaultWebResponse updateGoodsSoldCount(@RequestBody UpdateSoldCountRequest request) throws BusinessException {
        Assert.notNull(request, "update goods sold count request can not be null");
        logger.info(request.toString());
        goodsMgnService.updateSoldCount(request.getId(), request.getSoldCount(), request.isAbsolute());
        return DefaultWebResponse.Builder.success("update_sold_count_success");
    }

    @PostMapping("inventory/update")
    public DefaultWebResponse updateGoodsInventoryCount(@RequestBody UpdateInventoryRequest request) throws BusinessException {
        Assert.notNull(request, "update goods inventory request can not be null");
        logger.info(request.toString());
        goodsMgnService.updateInventory(request.getId(), request.getInventory(), request.isAbsolute());
        return DefaultWebResponse.Builder.success("update_inventory_success");
    }

    @PostMapping("type/add")
    public DefaultWebResponse addGoodType(@RequestBody GoodsTypeDTO request) {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());

        return DefaultWebResponse.Builder.success("add_goods_type_success", goodsMgnService.addGoodsType(request.getName()));
    }

    @PostMapping("type/update")
    public DefaultWebResponse updateGoodType(@RequestBody GoodsTypeDTO request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        this.goodsMgnService.updateGoodsType(request);
        return DefaultWebResponse.Builder.success("update_goods_type_success");
    }

    @PostMapping("type/delete")
    public DefaultWebResponse deleteGoodType(@RequestBody UUIDParamRequest request) throws BusinessException {
        Assert.notNull(request, "request can not be null");
        logger.info(request.toString());
        this.goodsMgnService.deleteGoodsType(request.getId());
        return DefaultWebResponse.Builder.success("delete_goods_type_success");
    }
}
