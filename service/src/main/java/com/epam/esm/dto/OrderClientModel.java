package com.epam.esm.dto;

import com.epam.esm.entity.CertificateEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderClientModel implements ClientModel {
    private long id;
    private BigDecimal price;
    private LocalDateTime purchaseDate;
    private CertificateEntity certificate;
}
