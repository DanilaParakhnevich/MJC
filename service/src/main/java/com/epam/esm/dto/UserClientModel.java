package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClientModel implements ClientModel {
    private long id;
    private String nickname;
    private String mail;
    private String password;
    private BigDecimal balance;
}
