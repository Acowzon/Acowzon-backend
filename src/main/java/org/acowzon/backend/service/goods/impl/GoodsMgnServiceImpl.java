package org.acowzon.backend.service.goods.impl;

import org.acowzon.backend.dao.goods.GoodsDAO;
import org.acowzon.backend.dao.goods.GoodsTypeDAO;
import org.acowzon.backend.dao.shop.ShopDAO;
import org.acowzon.backend.dto.goods.GoodsCatalogDTO;
import org.acowzon.backend.dto.goods.GoodsDetailDTO;
import org.acowzon.backend.dto.goods.GoodsTypeDTO;
import org.acowzon.backend.elaticsearch.Goods;
import org.acowzon.backend.elaticsearch.dao.GoodsRepository;
import org.acowzon.backend.entity.goods.GoodsEntity;
import org.acowzon.backend.entity.goods.GoodsTypeEntity;
import org.acowzon.backend.entity.shop.ShopEntity;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.service.goods.GoodsMgnService;
import org.acowzon.backend.utils.PublicBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class GoodsMgnServiceImpl implements GoodsMgnService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GoodsDAO goodsDAO;

    @Autowired
    GoodsTypeDAO goodsTypeDAO;

    @Autowired
    ShopDAO shopDAO;

    @Autowired
    GoodsRepository goodsRepository;

    @Override
    public GoodsDetailDTO getGoodsById(UUID goodsId) throws BusinessException {
        Assert.notNull(goodsId, "goodsId can not be null");
        Optional<GoodsEntity> goods = goodsDAO.findById(goodsId);
        if (goods.isPresent()) {
            return GoodsDetailDTO.parseDTO(goods.get());
        } else {
            throw new BusinessException("no_such_goods");
        }
    }

    @Override
    public GoodsCatalogDTO[] getAllGoods() {
        return goodsDAO.findAll().stream().map(GoodsCatalogDTO::parseDTO).toArray(GoodsCatalogDTO[]::new);
    }

    @Override
    public GoodsCatalogDTO[] getGoodsByShopId(UUID shopId) throws BusinessException {
        Assert.notNull(shopId, "shopId can not be null");
        return goodsDAO.findAllByShop(new ShopEntity(shopId)).stream().map(GoodsCatalogDTO::parseDTO).toArray(GoodsCatalogDTO[]::new);
    }

    @Override
    public GoodsCatalogDTO[] queryGoods(Map map) {
        //todo:??????????????????????????????
        return null;
    }

    @Override
    public UUID addGoods(GoodsDetailDTO goods) throws BusinessException {
        Assert.notNull(goods, "GoodsDTO can not be null");
        GoodsEntity goodsEntity = new GoodsEntity();

        BeanUtils.copyProperties(goods, goodsEntity,"id","soldCount","goodsStarsCount","views","shopName");//??????id??????????????????????????????

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
        // ??????mysql??????????????????es????????????
        goodsRepository.save(Goods.parse(goodsEntity));
        return goodsDAO.save(goodsEntity).getId();
    }

    @Override
    public void updateGoods(GoodsDetailDTO goodsDetailDTO) throws BusinessException {
        Assert.notNull(goodsDetailDTO, "GoodsDTO can not be null");
        Optional<GoodsEntity> goodsOptional = goodsDAO.findById(goodsDetailDTO.getId());
        if (goodsOptional.isPresent()) {
            /**
             * DTO???????????????Entity??????????????????copyProperties???????????????
             */
            ArrayList<String> ignoredPropertyList = new ArrayList(Arrays.asList(PublicBeanUtils.getNullPropertyNames(goodsDetailDTO)));
            ignoredPropertyList.add("inventory");
            ignoredPropertyList.add("soldCount");
            ignoredPropertyList.add("goodsStarsCount");
            ignoredPropertyList.add("views");
            ignoredPropertyList.add("shopId");
            ignoredPropertyList.add("shopName");
            ignoredPropertyList.add("price");
            BeanUtils.copyProperties(goodsDetailDTO, goodsOptional.get(), ignoredPropertyList.toArray(new String[0]));
            goodsOptional.get().setUpdateTime(new Date());
            // ????????????????????????????????????es???
            goodsRepository.save(Goods.parse(goodsOptional.get()));
            goodsDAO.save(goodsOptional.get());
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
            // ??????????????????es???????????????
            goodsRepository.delete(new Goods().setId(goodsId));
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
        // ????????????????????????????????????es???
        goodsRepository.save(Goods.parse(goods));
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
        // ????????????????????????????????????es???
        goodsRepository.save(Goods.parse(goods));
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
    public void updateGoodsType(GoodsTypeDTO goodsType) throws BusinessException {
        Assert.notNull(goodsType, "goodsType can not be null");
        if (!goodsTypeDAO.findById(goodsType.getId()).isPresent()) {
            throw new BusinessException("no_such_goods_type");
        }
        GoodsTypeEntity goodsTypeEntity = new GoodsTypeEntity();
        BeanUtils.copyProperties(goodsType,goodsTypeEntity);
        goodsTypeDAO.save(goodsTypeEntity);
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
