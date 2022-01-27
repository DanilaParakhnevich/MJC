package com.epam.esm.entity;

import lombok.Data;

import java.util.List;

@Data
public class CertificateEntity implements Entity{
    private long id;
    private String name;
    private String description;
    private double price;
    private long duration;
    private String createDate;
    private String lastUpdateDate;
    private List<TagEntity> tags;
}
