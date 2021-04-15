package org.acowzon.backend.service.shop;

import org.acowzon.backend.dao.address.AddressDAO;
import org.acowzon.backend.dto.shop.ShopCatalogDTO;
import org.acowzon.backend.dto.shop.ShopDetailDTO;
import org.acowzon.backend.entity.address.AddressEntity;
import org.acowzon.backend.exception.BusinessException;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface ShopMgnService {

    /**
     * 查询商家
     * @param map 查询条件
     * @return ShopCatalogDTO[]
     */
    ShopCatalogDTO[] queryShop(Map map);

    /**
     * 列出所有商家
     * @return ShopCatalogDTO[]
     */
    ShopCatalogDTO[] listAllShop();

    /**
     * 获取商铺详细信息
     * @param id 商铺id
     * @return ShopDetailDTO
     * @throws BusinessException 业务相关信息
     */
    ShopDetailDTO getShopDetail(UUID id) throws BusinessException;

    /**
     * 添加商铺
     * @param shop 商铺定义DTO
     * @return 生成的商铺UUID
     * @throws BusinessException 业务相关异常
     */
    UUID addShop(ShopDetailDTO shop) throws BusinessException;

    /**
     * 修改商铺信息
     * @param shop 商铺定义DTO
     * @throws BusinessException 业务相关异常
     */
    void updateShop(ShopDetailDTO shop) throws BusinessException;

    /**
     * 删除商铺
     * @param id 商铺id
     * @throws BusinessException 业务相关异常
     */
    void deleteShop(UUID id) throws BusinessException;

    /**
     * 添加商铺管理员
     * @param id 店铺id
     * @param adminId 管理员id
     * @throws BusinessException 业务相关异常
     */
    void addShopAdmin(UUID id, UUID adminId) throws BusinessException;

    /**
     * 删除商铺管理员
     * @param id 店铺id
     * @param adminId 管理员id
     * @throws BusinessException 业务相关异常
     */
    void deleteShopAdmin(UUID id, UUID adminId) throws BusinessException;

    /**
     * 更新店主
     * @param id 店铺id
     * @param ownerId 店主id
     * @throws BusinessException 业务相关异常
     */
    void updateShopOwner(UUID id, UUID ownerId) throws BusinessException;

    /**
     * 添加商铺地址信息
     * @param id 店铺id
     * @param addressEntity 地址信息表
     * @throws BusinessException 业务相关异常
     */
    void addShopAddress(UUID id, AddressEntity addressEntity) throws BusinessException;

    /**
     * 删除商铺地址信息
     * @param id 店铺id
     * @param addressId 地址信息表
     * @throws BusinessException 业务相关异常
     */
    void deleteShopAddress(UUID id, UUID addressId) throws BusinessException;
}
