package org.acowzon.backend.service.goods;

import org.acowzon.backend.dto.goods.GoodsDTO;
import org.acowzon.backend.entity.goods.GoodsEntity;
import org.acowzon.backend.entity.goods.GoodsTypeEntity;
import org.acowzon.backend.exception.BusinessException;

import java.util.Map;
import java.util.UUID;

public interface GoodsMgnService {

    GoodsDTO getGoodsById(UUID id) throws BusinessException;

    GoodsDTO[] getAllGoods();

    GoodsDTO[] getGoodsByRetailerId(UUID retailerId) throws BusinessException;

    GoodsDTO[] queryGoods(Map map);

    GoodsEntity addGoods(GoodsDTO goodsDTO) throws BusinessException;

    void updateGoods(GoodsDTO goodsDTO) throws BusinessException;

    void deleteGoods(UUID id) throws BusinessException;

    void updateInventory(UUID goodsId,int inventory) throws BusinessException;

    void updateSoldCount(UUID goodsId,int soldCount) throws BusinessException;

    void updateInventory(UUID goodsId,int inventory,boolean isAbsolute) throws BusinessException;

    void updateSoldCount(UUID goodsId,int soldCount,boolean isAbsolute) throws BusinessException;

    GoodsTypeEntity addGoodsType(String goodsType);

    void updateGoodsType(GoodsTypeEntity goodsType) throws BusinessException;

    void deleteGoodsType(UUID id) throws BusinessException;
}
