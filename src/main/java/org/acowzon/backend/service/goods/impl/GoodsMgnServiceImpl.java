package org.acowzon.backend.service.goods.impl;

import org.acowzon.backend.dto.goods.GoodsDTO;
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
    public GoodsDTO getGoodsById(String id) throws BusinessException {
        Goods goods = goodsMapper.queryGoodsById(id);
        GoodsDTO goodsDTO = new GoodsDTO();
        if (goods == null) {
            throw new BusinessException("no_such_good");
        } else {
            BeanUtils.copyProperties(goods, goodsDTO);
            return goodsDTO;
        }
    }

    @Override
    public GoodsDTO[] getAllGoods() {
        return goodsMapper.queryAllGoods().stream().map(goods -> {
            GoodsDTO goodsDTO = new GoodsDTO();
            BeanUtils.copyProperties(goods, goodsDTO);
            return goodsDTO;
        }).toArray(GoodsDTO[]::new);
    }

    @Override
    public int addGoods(Goods goods) {
        return this.goodsMapper.addGoods(goods);
    }
}
