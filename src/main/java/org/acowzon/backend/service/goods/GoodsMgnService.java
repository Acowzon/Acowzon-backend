package org.acowzon.backend.service.goods;

import org.acowzon.backend.dto.goods.GoodsDTO;
import org.acowzon.backend.entity.goods.Goods;
import org.acowzon.backend.exception.BusinessException;

import java.util.Map;

public interface GoodsMgnService {

    GoodsDTO getGoodsById(String id) throws BusinessException;

    GoodsDTO[] getAllGoods();

    GoodsDTO[] getGoodsByRetailerId(String retailerId) throws BusinessException;

    GoodsDTO[] queryGoods(Map map);

    int addGoods(Goods goods) ;

    int updateGoods(Goods goods) throws BusinessException;

    int deleteGoods(String id) throws BusinessException;
}
