package org.acowzon.backend.service.goods;

import org.acowzon.backend.dto.goods.GoodDTO;
import org.acowzon.backend.entity.goods.Goods;
import org.acowzon.backend.exception.BusinessException;

public interface GoodsMgnService {

    GoodDTO getGoodsById(String id) throws BusinessException;

    GoodDTO[] getAllGoods();

    int addGoods(Goods goods);
}
