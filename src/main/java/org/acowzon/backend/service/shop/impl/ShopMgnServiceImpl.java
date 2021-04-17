package org.acowzon.backend.service.shop.impl;

import org.acowzon.backend.dao.address.AddressDAO;
import org.acowzon.backend.dao.goods.GoodsDAO;
import org.acowzon.backend.dao.shop.ShopDAO;
import org.acowzon.backend.dao.user.UserDAO;
import org.acowzon.backend.dto.address.AddressDTO;
import org.acowzon.backend.dto.shop.ShopCatalogDTO;
import org.acowzon.backend.dto.shop.ShopDetailDTO;
import org.acowzon.backend.entity.address.AddressEntity;
import org.acowzon.backend.entity.goods.GoodsEntity;
import org.acowzon.backend.entity.shop.ShopEntity;
import org.acowzon.backend.entity.user.UserEntity;
import org.acowzon.backend.exception.BusinessException;
import org.acowzon.backend.service.shop.ShopMgnService;
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
public class ShopMgnServiceImpl implements ShopMgnService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ShopDAO shopDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    GoodsDAO goodsDAO;

    @Autowired
    AddressDAO addressDAO;

    /**
     * 查询商家
     *
     * @param map 查询条件
     * @return ShopCatalogDTO[]
     */
    @Override
    public ShopCatalogDTO[] queryShop(Map map) {
        return new ShopCatalogDTO[0];
    }

    /**
     * 列出所有商家
     *
     * @return ShopCatalogDTO[]
     */
    @Override
    public ShopCatalogDTO[] listAllShop() {
        return shopDAO.findAll().stream().map(ShopCatalogDTO::parseDTO).toArray(ShopCatalogDTO[]::new);
    }

    /**
     * 获取商铺详细信息
     *
     * @param id 商铺id
     * @return ShopDetailDTO
     * @throws BusinessException 业务相关信息
     */
    @Override
    public ShopDetailDTO getShopDetail(UUID id) throws BusinessException {
        Optional<ShopEntity> shopEntity = shopDAO.findById(id);
        if (shopEntity.isPresent()) {
            return ShopDetailDTO.parseDTO(shopEntity.get());
        } else {
            throw new BusinessException("no_such_shop");
        }
    }

    /**
     * 添加商铺
     *
     * @param shop 商铺定义DTO
     * @return 生成的商铺UUID
     * @throws BusinessException 业务相关异常
     */
    @Override
    public UUID addShop(ShopDetailDTO shop) throws BusinessException {
        Assert.notNull(shop, "shop can not be null");

        if (shopDAO.findAllByOwner(new UserEntity(shop.getOwnerId())).isPresent()) {
            throw new BusinessException("user_already_owned_a_shop");
        }

        Optional<UserEntity> ownerOptional = userDAO.findById(shop.getOwnerId());
        if (!ownerOptional.isPresent()) {
            throw new BusinessException("no_such_user");
        }
        if (!ownerOptional.get().isSeller()) {
            throw new BusinessException("invalid_user_type");
        }

        ShopEntity shopEntity = new ShopEntity();
        BeanUtils.copyProperties(shop, shopEntity, "id", "adminId", "addressSet");//忽略id字段，让后端自动生成

        shopEntity.setOwner(ownerOptional.get());

        shopEntity.setCreateTime(new Date());
        shopEntity.setUpdateTime(new Date());
        return shopDAO.save(shopEntity).getId();
    }

    /**
     * 修改商铺信息
     *
     * @param shop 商铺定义DTO
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void updateShop(ShopDetailDTO shop) throws BusinessException {
        Assert.notNull(shop, "shop can not be null");
        Optional<ShopEntity> shopEntityOptional = shopDAO.findById(shop.getId());

        if (!shopEntityOptional.isPresent()) {
            throw new BusinessException("no_such_shop");
        }

        ArrayList<String> ignoredPropertyList = new ArrayList(Arrays.asList(PublicBeanUtils.getNullPropertyNames(shop)));
        ignoredPropertyList.add("adminId");
        ignoredPropertyList.add("addressSet");
        BeanUtils.copyProperties(shop, shopEntityOptional.get(), ignoredPropertyList.toArray(new String[0]));

        shopEntityOptional.get().setUpdateTime(new Date());
        shopDAO.save(shopEntityOptional.get());
    }

    /**
     * 删除商铺
     *
     * @param id 商铺id
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void deleteShop(UUID id) throws BusinessException {
        Assert.notNull(id, "uuid can not be null.");
        Optional<ShopEntity> shopEntityOptional = shopDAO.findById(id);
        if (!shopEntityOptional.isPresent()) {
            throw new BusinessException("no_such_shop");
        }

        //shop 和 goods 存在双向依赖，其中shop对goods存在级联操作，会出现无限循环，因此应该先删除绑定的goods，再删除goods
        //并且这里不能用stream.map来操作，可能和多线程有关？
        List<GoodsEntity> goodsEntityList = goodsDAO.findAllByShop(shopEntityOptional.get());
        for (GoodsEntity goods : goodsEntityList) {
            goodsDAO.delete(goods);
        }

        shopDAO.deleteById(id);
    }

    /**
     * 添加商铺管理员
     *
     * @param id      店铺id
     * @param adminId 管理员id
     * @throws BusinessException 业务相关异常
     */
    @Override
    @Transactional
    public void addShopAdmin(UUID id, UUID adminId) throws BusinessException {
        Assert.notNull(id, "uuid can not be null.");
        Optional<ShopEntity> shopEntityOptional = shopDAO.findById(id);
        if (!shopEntityOptional.isPresent()) {
            throw new BusinessException("no_such_shop");
        }
        Optional<UserEntity> userEntityOptional = userDAO.findById(adminId);
        if (!userEntityOptional.isPresent()) {
            throw new BusinessException("no_such_admin");
        }
        if (shopEntityOptional.get().getOwner().getId().equals(adminId)) {
            throw new BusinessException("try_to_set_owner_as_admin");
        }
        if (shopEntityOptional.get().getAdmin().contains(userEntityOptional.get())) {
            throw new BusinessException("admin_already_exist");
        }
        Set<UserEntity> adminList = shopEntityOptional.get().getAdmin();
        adminList.add(userEntityOptional.get());
        shopEntityOptional.get().setUpdateTime(new Date());
        shopDAO.save(shopEntityOptional.get());
    }

    /**
     * 删除商铺管理员
     *
     * @param id      店铺id
     * @param adminId 管理员id
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void deleteShopAdmin(UUID id, UUID adminId) throws BusinessException {
        Assert.notNull(id, "uuid can not be null.");
        Optional<ShopEntity> shopEntityOptional = shopDAO.findById(id);
        if (!shopEntityOptional.isPresent()) {
            throw new BusinessException("no_such_shop");
        }
        if (shopEntityOptional.get().getAdmin().isEmpty()) {
            throw new BusinessException("shop_admin_list_is_empty");
        }

        Optional<UserEntity> admin = userDAO.findById(adminId);
        if (!admin.isPresent()) {
            throw new BusinessException("no_such_admin");
        }

        if (!shopEntityOptional.get().getAdmin().contains(admin.get())) {
            throw new BusinessException("admin_not_in_this_shop");
        }

        Set<UserEntity> adminList = shopEntityOptional.get().getAdmin();
        adminList.remove(admin.get());
        shopEntityOptional.get().setUpdateTime(new Date());
        shopDAO.save(shopEntityOptional.get());
    }

    /**
     * 更新店主
     *
     * @param id      店铺id
     * @param ownerId 店主id
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void updateShopOwner(UUID id, UUID ownerId) throws BusinessException {
        Assert.notNull(id, "id can not be null");
        Optional<ShopEntity> shopEntityOptional = shopDAO.findById(id);
        if (!shopEntityOptional.isPresent()) {
            throw new BusinessException("no_such_shop");
        }
        Optional<UserEntity> owner = userDAO.findById(ownerId);
        if (!owner.isPresent()) {
            throw new BusinessException("no_such_user");
        }
        if (!owner.get().isSeller()) {
            throw new BusinessException("invalid_user_type");
        }
        if (shopDAO.findAllByOwner(owner.get()).isPresent()) {
            throw new BusinessException("user_already_assigned_shop");
        }
        shopEntityOptional.get().setOwner(owner.get());
        shopEntityOptional.get().setUpdateTime(new Date());
        shopDAO.save(shopEntityOptional.get());
    }

    /**
     * 更新商铺地址信息
     *
     * @param id         店铺id
     * @param addressDTO 地址信息表
     * @throws BusinessException 业务相关异常
     */
    @Override
    @Transactional
    public void addShopAddress(UUID id, AddressDTO addressDTO) throws BusinessException {
        Assert.notNull(id, "id can not be null");
        Assert.notNull(addressDTO, "addressDTO can not be null");
        Optional<ShopEntity> shopEntityOptional = shopDAO.findById(id);
        if (!shopEntityOptional.isPresent()) {
            throw new BusinessException("no_such_shop");
        }

        Set<AddressEntity> addressEntitySet = shopEntityOptional.get().getAddress();

        AddressEntity newAddr = new AddressEntity();

        BeanUtils.copyProperties(addressDTO, newAddr, "id");//忽略id字段，让后端自动生成
        addressEntitySet.add(newAddr);
        shopEntityOptional.get().setUpdateTime(new Date());
        shopDAO.save(shopEntityOptional.get());
    }

    /**
     * 删除商铺地址信息
     *
     * @param id        店铺id
     * @param addressId 地址信息表
     * @throws BusinessException 业务相关异常
     */
    @Override
    public void deleteShopAddress(UUID id, UUID addressId) throws BusinessException {
        Assert.notNull(id, "id can not be null");
        Assert.notNull(addressId, "addressId can not be null");
        Optional<ShopEntity> shopEntityOptional = shopDAO.findById(id);
        if (!shopEntityOptional.isPresent()) {
            throw new BusinessException("no_such_shop");
        }

        if (shopEntityOptional.get().getAddress().isEmpty()) {
            throw new BusinessException("shop_address_list_is_empty");
        }

        Optional<AddressEntity> addressEntityOptional = addressDAO.findById(addressId);
        if (!addressEntityOptional.isPresent()) {
            throw new BusinessException("no_such_address");
        }
        //todo 这里需要改成双向级联操作，不用每次都先从shop表中提取再删除再保存
        Set<AddressEntity> addressEntitySet = shopEntityOptional.get().getAddress();
        addressEntitySet.remove(addressEntityOptional.get());
        shopEntityOptional.get().setUpdateTime(new Date());
        shopDAO.save(shopEntityOptional.get());
    }
}
