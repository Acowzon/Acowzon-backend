package org.acowzon.backend.dao.address;

import org.acowzon.backend.entity.address.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface AddressDAO extends JpaRepository<AddressEntity, UUID>, JpaSpecificationExecutor<AddressEntity> {
}
