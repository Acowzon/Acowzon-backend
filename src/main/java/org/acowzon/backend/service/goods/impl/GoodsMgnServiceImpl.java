package org.acowzon.backend.service.goods.impl;

import org.acowzon.backend.dao.goods.GoodsDAO;
import org.acowzon.backend.dao.goods.GoodsTypeDAO;
import org.acowzon.backend.dto.goods.GoodsDTO;
import org.acowzon.backend.entity.goods.GoodsEntity;
import org.acowzon.backend.entity.goods.GoodsTypeEntity;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.service.goods.GoodsMgnService;
import org.acowzon.backend.utils.PublicBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class GoodsMgnServiceImpl implements GoodsMgnService {
    @Autowired
    GoodsDAO goodsDAO;

    @Autowired
    GoodsTypeDAO goodsTypeDAO;

    @Override
    public GoodsDTO getGoodsById(UUID goodsId) throws BusinessException {
        Assert.notNull(goodsId, "goodsId can not be null");
        Optional<GoodsEntity> goods = goodsDAO.findById(goodsId);
        if (goods.isPresent()) {
            GoodsDTO goodsDTO = new GoodsDTO();
            BeanUtils.copyProperties(goods.get(), goodsDTO);
            return goodsDTO;
        }
        throw new BusinessException("no_such_goods");
    }

    @Override
    public GoodsDTO[] getAllGoods() {
        return goodsDAO.findAll().stream().map(goods -> {
            GoodsDTO goodsDTO = new GoodsDTO();
            BeanUtils.copyProperties(goods, goodsDTO);
            return goodsDTO;
        }).toArray(GoodsDTO[]::new);
    }

    @Override
    public GoodsDTO[] getGoodsByRetailerId(UUID retailerId) throws BusinessException {
        Assert.notNull(retailerId, "retailerId can not be null");
        return goodsDAO.findAllByRetailerId(retailerId).stream().map(goods -> {
            GoodsDTO goodsDTO = new GoodsDTO();
            BeanUtils.copyProperties(goods, goodsDTO);
            return goodsDTO;
        }).toArray(GoodsDTO[]::new);
    }

    @Override
    public GoodsDTO[] queryGoods(Map map) {
        //todo:分页查询，多条件查询
        return null;
    }

    @Override
    public GoodsEntity addGoods(GoodsDTO goods) throws BusinessException {
        Assert.notNull(goods, "GoodsDTO can not be null");
        GoodsEntity goodsEntity = new GoodsEntity();
        BeanUtils.copyProperties(goods, goodsEntity);
        Optional<GoodsTypeEntity> goodsTypeEntity = goodsTypeDAO.findById(goods.getGoodsType().getGoodsTypeId());
        if (goodsTypeEntity.isPresent()) {
            goodsEntity.setGoodsType(goodsTypeEntity.get());
            goodsEntity.setCreateTime(new Date());
            goodsEntity.setUpdateTime(new Date());
            return goodsDAO.save(goodsEntity);

        }
        throw new BusinessException("no_such_goods_type");
    }

    @Override
    public void updateGoods(GoodsDTO goodsDTO) throws BusinessException {
        Assert.notNull(goodsDTO, "GoodsDTO can not be null");
        Optional<GoodsEntity> goods = goodsDAO.findById(goodsDTO.getGoodsId());
        if (goods.isPresent()) {
            BeanUtils.copyProperties(goodsDTO, goods, PublicBeanUtils.getNullPropertyNames(goodsDTO));
            goods.get().setUpdateTime(new Date());
            goodsDAO.save(goods.get());

        }
        throw new BusinessException("no_such_goods");
    }

    @Override
    public void deleteGoods(UUID goodsId) throws BusinessException {
        Assert.notNull(goodsId, "goodsId can not be null");
        Optional<GoodsEntity> goods = goodsDAO.findById(goodsId);
        if (goods.isPresent()) {
            goodsDAO.deleteById(goodsId);
        }
        throw new BusinessException("no_such_goods");
    }

    @Override
    public void updateInventory(UUID goodsId, int inventory, boolean isAbsolute) throws BusinessException {
        Assert.notNull(goodsId, "goodsId can not be null");
        Optional<GoodsEntity> goodsEntityOptional = goodsDAO.findById(goodsId);
        if (!goodsEntityOptional.isPresent()) {
            throw new BusinessException("no_such_goods");
        }
        GoodsEntity goods = goodsEntityOptional.get();
        if (isAbsolute && inventory >= 0) {
            goods.setGoodsInventory(inventory);
        } else {
            if (goods.getGoodsInventory() + inventory >= 0) {
                goods.setGoodsInventory(goods.getGoodsInventory() + inventory);
            } else {
                throw new BusinessException("invalid_inventory_value");
            }
        }
        goodsDAO.save(goods);
    }

    @Override
    public void updateInventory(UUID goodsId, int inventory) throws BusinessException {
        this.updateInventory(goodsId, inventory, true);
    }

    @Override
    public void updateSoldCount(UUID goodsId, int soldCount, boolean isAbsolute) throws BusinessException {
        Assert.notNull(goodsId, "goodsId can not be null");
        Optional<GoodsEntity> goodsEntityOptional = goodsDAO.findById(goodsId);
        if (!goodsEntityOptional.isPresent()) {
            throw new BusinessException("no_such_goods");
        }
        GoodsEntity goods = goodsEntityOptional.get();
        if (isAbsolute && soldCount >= 0) {
            goods.setSoldCount(soldCount);
        } else {
            if (goods.getSoldCount() + soldCount >= 0) {
                goods.setSoldCount(goods.getSoldCount() + soldCount);
            } else {
                throw new BusinessException("invalid_soldCount_value");
            }
        }
        goodsDAO.save(goods);
    }

    @Override
    public void updateSoldCount(UUID goodsId, int soldCount) throws BusinessException {
        this.updateSoldCount(goodsId, soldCount, true);
    }

    @Override
    public GoodsTypeEntity addGoodsType(String goodsType) {
        Assert.notNull(goodsType, "goodsType can not be null");
        GoodsTypeEntity goodsTypeEntity = new GoodsTypeEntity();
        goodsTypeEntity.setGoodsType(goodsType);
        return goodsTypeDAO.save(goodsTypeEntity);
    }

    @Override
    public void updateGoodsType(GoodsTypeEntity goodsType) throws BusinessException {
        Assert.notNull(goodsType, "goodsType can not be null");
        if (!goodsTypeDAO.findById(goodsType.getGoodsTypeId()).isPresent()) {
            throw new BusinessException("no_such_goods_type");
        }
        goodsTypeDAO.save(goodsType);
    }

    @Override
    public void deleteGoodsType(UUID id) throws BusinessException {
        Assert.notNull(id, "goodsTypeId can not be null");
        Optional<GoodsTypeEntity> goodsType = goodsTypeDAO.findById(id);
        if (goodsType.isPresent()) {
            goodsTypeDAO.deleteById(id);
        }
        throw new BusinessException("no_such_goods_type");
    }
}
