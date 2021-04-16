package org.acowzon.backend.dao.order;

import org.acowzon.backend.entity.order.OrderEntity;
import org.acowzon.backend.entity.shop.ShopEntity;
import org.acowzon.backend.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface OrderDAO extends JpaRepository<OrderEntity, UUID>, JpaSpecificationExecutor<OrderEntity> {
    List<OrderEntity> findAllByCustomer(UserEntity customer);

    List<OrderEntity> findAllByShop(ShopEntity shop);

    List<OrderEntity> findAllByShopAndCustomer(ShopEntity shop,UserEntity customer);
}
