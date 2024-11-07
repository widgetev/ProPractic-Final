package com.example.propracticfinal.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@Setter @Getter
@Entity
@Table(name = "limits")
public class LimitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false, insertable=false, updatable=false)
    private Long id;

    @Column(name = "userid")
    private Long userId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "lock_amount")
    private BigDecimal lockAmount;
}
