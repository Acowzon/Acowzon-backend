package org.acowzon.backend.dao.shop;

import org.acowzon.backend.entity.shop.ShopEntity;
import org.acowzon.backend.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Transactional
public interface ShopDAO extends JpaRepository<ShopEntity, UUID>, JpaSpecificationExecutor<ShopEntity> {
    Optional<ShopEntity> findAllByOwner(UserEntity owner);
    void deleteByOwner(UserEntity owner);
}
