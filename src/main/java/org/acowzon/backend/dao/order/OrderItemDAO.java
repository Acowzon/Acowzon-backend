package org.acowzon.backend.dao.order;

import org.acowzon.backend.entity.order.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface OrderItemDAO extends JpaRepository<OrderItemEntity, UUID>, JpaSpecificationExecutor<OrderItemEntity> {
}
