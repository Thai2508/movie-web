package com.identity.repository;

import com.identity.entity.OtpEmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpEmailRepository extends JpaRepository<OtpEmailEntity, String> {
}
