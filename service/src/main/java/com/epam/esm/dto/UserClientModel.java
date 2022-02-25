package com.epam.esm.dto;

import com.epam.esm.entity.CertificateEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClientModel implements ClientModel {
    private long id;
    private String firstName;
    private String lastName;
    private String mail;
    private String password;
    private List<CertificateEntity> certificates;
}
