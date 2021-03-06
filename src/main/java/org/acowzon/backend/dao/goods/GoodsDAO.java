package org.acowzon.backend.dao.goods;

import org.acowzon.backend.entity.goods.GoodsEntity;
import org.acowzon.backend.entity.shop.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public interface GoodsDAO extends JpaRepository<GoodsEntity, UUID>, JpaSpecificationExecutor<GoodsEntity> {
    List<GoodsEntity> findAllByShop(ShopEntity shopEntity);
    Optional<GoodsEntity> findByShopAndId(ShopEntity shopEntity, UUID id);
}
