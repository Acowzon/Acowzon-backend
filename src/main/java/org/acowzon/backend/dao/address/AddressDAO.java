package org.acowzon.backend.dao.address;

import org.acowzon.backend.entity.address.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface AddressDAO extends JpaRepository<AddressEntity, UUID>, JpaSpecificationExecutor<AddressEntity> {
}
