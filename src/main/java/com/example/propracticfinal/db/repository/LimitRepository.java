package com.example.propracticfinal.db.repository;

import com.example.propracticfinal.db.entity.LimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface LimitRepository extends JpaRepository<LimitEntity,Long> {
    LimitEntity findByUserId(Long userid);
    @Query(value = "SELECT l FROM LimitEntity l where l.amount <> ?1 or l.lockAmount > 0 ")
    List<LimitEntity> findAllByForReset (BigDecimal amount);
}
