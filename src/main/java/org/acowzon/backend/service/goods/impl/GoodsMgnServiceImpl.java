package org.acowzon.backend.service.goods.impl;

import org.acowzon.backend.dao.goods.GoodsDAO;
import org.acowzon.backend.dao.goods.GoodsTypeDAO;
import org.acowzon.backend.dao.shop.ShopDAO;
import org.acowzon.backend.dto.goods.GoodsDetailDTO;
import org.acowzon.backend.dto.goods.GoodsTypeDTO;
import org.acowzon.backend.entity.goods.GoodsEntity;
import org.acowzon.backend.entity.goods.GoodsTypeEntity;
import org.acowzon.backend.entity.shop.ShopEntity;
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

    @Autowired
    ShopDAO shopDAO;

    @Override
    public GoodsDetailDTO getGoodsById(UUID goodsId) throws BusinessException {
        Assert.notNull(goodsId, "goodsId can not be null");
        Optional<GoodsEntity> goods = goodsDAO.findById(goodsId);
        if (goods.isPresent()) {
            GoodsDetailDTO goodsDetailDTO = new GoodsDetailDTO();
            BeanUtils.copyProperties(goods.get(), goodsDetailDTO);
            return goodsDetailDTO;
        } else {
            throw new BusinessException("no_such_goods");
        }
    }

    @Override
    public GoodsDetailDTO[] getAllGoods() {
        return goodsDAO.findAll().stream().map(goods -> {
            GoodsDetailDTO goodsDetailDTO = new GoodsDetailDTO();
            BeanUtils.copyProperties(goods, goodsDetailDTO);
            return goodsDetailDTO;
        }).toArray(GoodsDetailDTO[]::new);
    }

    @Override
    public GoodsDetailDTO[] getGoodsByShopId(UUID shopId) throws BusinessException {
        Assert.notNull(shopId, "shopId can not be null");
        return goodsDAO.findAllByShop(new ShopEntity(shopId)).stream().map(goods -> {
            GoodsDetailDTO goodsDetailDTO = new GoodsDetailDTO();
            BeanUtils.copyProperties(goods, goodsDetailDTO);
            GoodsTypeDTO goodsTypeDTO = new GoodsTypeDTO();
            BeanUtils.copyProperties(goods.getType(),goodsTypeDTO);
            goodsDetailDTO.setType(goodsTypeDTO);
            return goodsDetailDTO;
        }).toArray(GoodsDetailDTO[]::new);
    }

    @Override
    public GoodsDetailDTO[] queryGoods(Map map) {
        //todo:分页查询，多条件查询
        return null;
    }

    @Override
    public UUID addGoods(GoodsDetailDTO goods) throws BusinessException {
        Assert.notNull(goods, "GoodsDTO can not be null");
        GoodsEntity goodsEntity = new GoodsEntity();
        BeanUtils.copyProperties(goods, goodsEntity);
        Optional<GoodsTypeEntity> goodsTypeEntity = goodsTypeDAO.findById(goods.getType().getId());

        if (goodsTypeEntity.isPresent()) {
            goodsEntity.setType(goodsTypeEntity.get());
        } else {
            throw new BusinessException("no_such_goods_type");
        }

        Optional<ShopEntity> shopEntity = shopDAO.findById(goods.getShopId());
        if (shopEntity.isPresent()) {
            goodsEntity.setShop(shopEntity.get());
        } else {
            throw new BusinessException("no_such_shop");
        }

        goodsEntity.setCreateTime(new Date());
        goodsEntity.setUpdateTime(new Date());
        return goodsDAO.save(goodsEntity).getId();
    }

    @Override
    public void updateGoods(GoodsDetailDTO goodsDetailDTO) throws BusinessException {
        Assert.notNull(goodsDetailDTO, "GoodsDTO can not be null");
        Optional<GoodsEntity> goods = goodsDAO.findById(goodsDetailDTO.getId());
        if (goods.isPresent()) {
            BeanUtils.copyProperties(goodsDetailDTO, goods, PublicBeanUtils.getNullPropertyNames(goodsDetailDTO));
            goods.get().setUpdateTime(new Date());
            goodsDAO.save(goods.get());
        } else {
            throw new BusinessException("no_such_goods");
        }
    }

    @Override
    public void deleteGoods(UUID goodsId) throws BusinessException {
        Assert.notNull(goodsId, "goodsId can not be null");
        Optional<GoodsEntity> goods = goodsDAO.findById(goodsId);
        if (goods.isPresent()) {
            goodsDAO.deleteById(goodsId);
        } else {
            throw new BusinessException("no_such_goods");
        }
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
            goods.setInventory(inventory);
        } else {
            if (goods.getInventory() + inventory >= 0) {
                goods.setInventory(goods.getInventory() + inventory);
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
    public UUID addGoodsType(String typeName) {
        Assert.notNull(typeName, "typeName can not be null");
        GoodsTypeEntity goodsTypeEntity = new GoodsTypeEntity();
        goodsTypeEntity.setName(typeName);
        return goodsTypeDAO.save(goodsTypeEntity).getId();
    }

    @Override
    public void updateGoodsType(GoodsTypeEntity goodsType) throws BusinessException {
        Assert.notNull(goodsType, "goodsType can not be null");
        if (!goodsTypeDAO.findById(goodsType.getId()).isPresent()) {
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
        } else {
            throw new BusinessException("no_such_goods_type");
        }
    }
}
