package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Entity {
    private long id;
    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private List<CertificateEntity> certificates;
}
