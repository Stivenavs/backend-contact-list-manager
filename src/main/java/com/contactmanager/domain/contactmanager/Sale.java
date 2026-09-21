package com.contactmanager.domain.contactmanager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

    private Long saleId;
    private Long userId;
    private BigDecimal totalValue;
    private String paymentStatus;
    private LocalDateTime saleDate;
}
