package com.Thai.CRUD.repository;

import com.Thai.CRUD.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, String> {
}
