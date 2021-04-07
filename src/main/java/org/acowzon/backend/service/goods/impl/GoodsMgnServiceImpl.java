package org.acowzon.backend.service.goods.impl;

import org.acowzon.backend.dto.goods.GoodDTO;
import org.acowzon.backend.entity.goods.Goods;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.mapper.goods.GoodsMapper;
import org.acowzon.backend.service.goods.GoodsMgnService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsMgnServiceImpl implements GoodsMgnService {
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public GoodDTO getGoodsById(String id) throws BusinessException {
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
    public int addGoods(Goods goods) {
        return this.goodsMapper.addGoods(goods);
    }
}
