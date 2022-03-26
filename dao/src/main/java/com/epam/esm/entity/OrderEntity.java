package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OrderEntity {
    private long id;
    private CertificateEntity certificate;
    private LocalDateTime purchaseDate;
    private BigDecimal price;
}
