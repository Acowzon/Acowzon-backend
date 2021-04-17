package org.acowzon.backend.dao.goods;

import org.acowzon.backend.entity.goods.GoodsTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface GoodsTypeDAO extends JpaRepository<GoodsTypeEntity, UUID> {
}
