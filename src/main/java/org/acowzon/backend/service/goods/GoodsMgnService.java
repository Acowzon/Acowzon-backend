package org.acowzon.backend.service.goods;

import org.acowzon.backend.dto.goods.GoodsDTO;
import org.acowzon.backend.entity.goods.Goods;
import org.acowzon.backend.exception.BusinessException;

public interface GoodsMgnService {

    GoodsDTO getGoodsById(String id) throws BusinessException;

    GoodsDTO[] getAllGoods();

    int addGoods(Goods goods);
}
