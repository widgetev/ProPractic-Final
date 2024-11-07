package com.example.propracticfinal.db.repository;

import com.example.propracticfinal.db.entity.LimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LimitRepository extends JpaRepository<LimitEntity,Long> {
    LimitEntity findByUserId(Long userid);
}
