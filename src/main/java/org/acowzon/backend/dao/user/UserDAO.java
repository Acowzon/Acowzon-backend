package org.acowzon.backend.dao.user;

import org.acowzon.backend.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UserDAO extends JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {
}
