package com.epam.esm.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagEntity implements Entity{
    private long id;
    private String name;
}
