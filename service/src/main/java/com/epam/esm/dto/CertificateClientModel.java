package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Certificate client model.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateClientModel implements ClientModel {
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm'Z'";
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private long duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<TagClientModel> tags;

    /**
     * Gets create date.
     *
     * @return the create date
     */
    @JsonFormat(pattern = DATE_FORMAT_PATTERN)
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Gets last update date.
     *
     * @return the last update date
     */
    @JsonFormat(pattern = DATE_FORMAT_PATTERN)
    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }
}
