package com.epam.esm.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The type Tag entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagEntity {
    private long id;
    private String name;
}
