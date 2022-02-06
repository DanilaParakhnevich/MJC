package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Tag client model.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagClientModel implements ClientModel {
    private long id;
    private String name;
}
