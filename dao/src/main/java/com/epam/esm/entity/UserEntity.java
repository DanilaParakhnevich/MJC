package com.epam.esm.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserEntity {
    private long id;
    private String mail;
    private String nickname;
    private String password;
    private BigDecimal balance;
    private List<OrderEntity> orders;
}
