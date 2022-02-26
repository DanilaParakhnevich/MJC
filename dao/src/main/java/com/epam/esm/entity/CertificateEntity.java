package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Certificate entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity (name = "gift_certificate")
public class CertificateEntity {
    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm'Z'";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private long duration;
    @Column (name = "create_date")
    private LocalDateTime createDate;
    @Column (name = "last_update_date")
    private LocalDateTime lastUpdateDate;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "certificate_by_tag",
            joinColumns = {@JoinColumn(name = "id_certificate")},
            inverseJoinColumns = {@JoinColumn(name = "id_tag")}
    )
    private List<TagEntity> tags;

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
