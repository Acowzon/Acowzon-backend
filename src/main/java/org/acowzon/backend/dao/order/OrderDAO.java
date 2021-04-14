package org.acowzon.backend.dao.order;

import org.acowzon.backend.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface OrderDAO extends JpaRepository<OrderEntity, UUID>, JpaSpecificationExecutor<OrderEntity> {
}
