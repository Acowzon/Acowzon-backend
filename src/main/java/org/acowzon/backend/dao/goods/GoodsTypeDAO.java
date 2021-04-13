package org.acowzon.backend.dao.goods;

import org.acowzon.backend.entity.goods.GoodsTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GoodsTypeDAO extends JpaRepository<GoodsTypeEntity, UUID> {
}
