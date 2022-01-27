package com.epam.esm.dto;

import com.epam.esm.entity.TagEntity;
import lombok.Data;

import java.util.List;

@Data
public class CertificateClientModel implements ClientModel{
    private long id;
    private String name;
    private String description;
    private double price;
    private long duration;
    private String createDate;
    private String lastUpdateDate;
    private List<TagEntity> tags;
}
