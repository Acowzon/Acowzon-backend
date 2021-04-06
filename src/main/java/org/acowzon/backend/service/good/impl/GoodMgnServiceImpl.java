package org.acowzon.backend.service.good.impl;

import org.acowzon.backend.dto.good.GoodDTO;
import org.acowzon.backend.entity.good.Goods;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.mapper.good.GoodsMapper;
import org.acowzon.backend.service.good.GoodMgnService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodMgnServiceImpl implements GoodMgnService {
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public GoodDTO getGoodById(String id) throws BusinessException {
        Goods goods = goodsMapper.queryGoodsById(id);
        GoodDTO goodDTO = new GoodDTO();
        if (goods == null) {
            throw new BusinessException("no_such_good");
        } else {
            BeanUtils.copyProperties(goods, goodDTO);
            return goodDTO;
        }
    }

    @Override
    public GoodDTO[] getAllGoods() {
        return goodsMapper.queryAllGoods().stream().map(goods -> {
            GoodDTO goodDTO = new GoodDTO();
            BeanUtils.copyProperties(goods, goodDTO);
            return goodDTO;
        }).toArray(GoodDTO[]::new);
    }

    @Override
    public int addGood(Goods goods) {
        return this.goodsMapper.addGoods(goods);
    }
}
