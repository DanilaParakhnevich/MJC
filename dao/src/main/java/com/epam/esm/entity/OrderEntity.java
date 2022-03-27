package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "\"order\"")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_certificate", nullable = false)
    private CertificateEntity certificate;
    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;
    private BigDecimal price;
    @Column(name = "id_user")
    private long userId;
}
