package org.acowzon.backend.service.goods;

import org.acowzon.backend.dto.goods.GoodsDetailDTO;
import org.acowzon.backend.entity.goods.GoodsEntity;
import org.acowzon.backend.entity.goods.GoodsTypeEntity;
import org.acowzon.backend.exception.BusinessException;

import java.util.Map;
import java.util.UUID;

public interface GoodsMgnService {

    GoodsDetailDTO getGoodsById(UUID id) throws BusinessException;

    GoodsDetailDTO[] getAllGoods();

    GoodsDetailDTO[] getGoodsByShopId(UUID shopId) throws BusinessException;

    GoodsDetailDTO[] queryGoods(Map map);

    UUID addGoods(GoodsDetailDTO goodsDetailDTO) throws BusinessException;

    void updateGoods(GoodsDetailDTO goodsDetailDTO) throws BusinessException;

    void deleteGoods(UUID id) throws BusinessException;

    void updateInventory(UUID goodsId,int inventory) throws BusinessException;

    void updateSoldCount(UUID goodsId,int soldCount) throws BusinessException;

    void updateInventory(UUID goodsId,int inventory,boolean isAbsolute) throws BusinessException;

    void updateSoldCount(UUID goodsId,int soldCount,boolean isAbsolute) throws BusinessException;

    UUID addGoodsType(String goodsType);

    void updateGoodsType(GoodsTypeEntity goodsType) throws BusinessException;

    void deleteGoodsType(UUID id) throws BusinessException;
}
