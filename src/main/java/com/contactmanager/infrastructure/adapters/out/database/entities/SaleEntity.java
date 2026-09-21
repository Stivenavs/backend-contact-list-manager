package com.contactmanager.infrastructure.adapters.out.database.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ven_sale", schema = "contactmanager")
public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long saleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "total_value", nullable = false, precision = 18, scale = 2)
    private BigDecimal totalValue;

    @Column(name = "payment_status", nullable = false, length = 50)
    private String paymentStatus;

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate;

    @PrePersist
    protected void onCreate() {
        if (this.paymentStatus == null) {
            this.paymentStatus = "pending";
        }
        if (this.saleDate == null) {
            this.saleDate = LocalDateTime.now();
        }
    }
}
