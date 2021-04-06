package org.acowzon.backend.service.good;

import org.acowzon.backend.dto.good.GoodDTO;
import org.acowzon.backend.entity.good.Goods;
import org.acowzon.backend.exception.BusinessException;

public interface GoodMgnService {

    GoodDTO getGoodById(String id) throws BusinessException;

    GoodDTO[] getAllGoods();

    int addGood(Goods goods);
}
