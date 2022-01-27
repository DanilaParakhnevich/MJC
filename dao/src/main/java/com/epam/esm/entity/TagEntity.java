package com.epam.esm.entity;


import lombok.Data;


@Data
public class TagEntity implements Entity{
    private long id;
    private String name;
}
