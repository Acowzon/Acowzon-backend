package org.acowzon.backend.service.goods.impl;

import org.acowzon.backend.dto.goods.GoodsDTO;
import org.acowzon.backend.entity.goods.Goods;
import org.acowzon.backend.entity.user.User;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.mapper.goods.GoodsMapper;
import org.acowzon.backend.mapper.user.UserMapper;
import org.acowzon.backend.service.goods.GoodsMgnService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GoodsMgnServiceImpl implements GoodsMgnService {
    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public GoodsDTO getGoodsById(String id) throws BusinessException {
        Goods goods = goodsMapper.queryGoodsById(id);
        GoodsDTO goodsDTO = new GoodsDTO();
        if (goods == null) {
            throw new BusinessException("no_such_goods");
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
    public GoodsDTO[] getGoodsByRetailerId(String retailerId) throws BusinessException {
        User user = userMapper.queryUserById(retailerId);
        if(user == null || user.getUserType() == 0) {
            throw new BusinessException("no_such_retailer");
        }
        return goodsMapper.queryGoodsByRetailerId(retailerId).stream().map(goods -> {
            GoodsDTO goodsDTO = new GoodsDTO();
            BeanUtils.copyProperties(goods, goodsDTO);
            return goodsDTO;
        }).toArray(GoodsDTO[]::new);
    }

    @Override
    public GoodsDTO[] queryGoods(Map map) {
        return goodsMapper.queryGoodsByMap(map).stream().map(goods -> {
            GoodsDTO goodsDTO = new GoodsDTO();
            BeanUtils.copyProperties(goods, goodsDTO);
            return goodsDTO;
        }).toArray(GoodsDTO[]::new);
    }

    @Override
    public int addGoods(Goods goods) {
        return this.goodsMapper.addGoods(goods);
    }

    @Override
    public int updateGoods(Goods goods) throws BusinessException {
        Goods oldGoods = goodsMapper.queryGoodsById(goods.getGoodsId());
        if (oldGoods == null) {
            throw new BusinessException("no_such_goods");
        } else {
            return goodsMapper.updateGoods(goods);
        }
    }

    @Override
    public int deleteGoods(String id) throws BusinessException {
        Goods goods = goodsMapper.queryGoodsById(id);
        if (goods == null) {
            throw new BusinessException("no_such_goods");
        } else {
            return goodsMapper.delGoods(id);
        }
    }
}
