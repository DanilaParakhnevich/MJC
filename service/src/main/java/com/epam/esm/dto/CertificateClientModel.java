package com.epam.esm.dto;

import com.epam.esm.entity.TagEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CertificateClientModel implements ClientModel{
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm'Z'";
    private long id;
    private String name;
    private String description;
    private double price;
    private long duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<TagEntity> tags;

    @JsonFormat(pattern = DATE_FORMAT_PATTERN)
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @JsonFormat(pattern = DATE_FORMAT_PATTERN)
    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }
}
