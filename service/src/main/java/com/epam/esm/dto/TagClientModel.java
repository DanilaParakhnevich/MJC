package com.epam.esm.dto;

import lombok.Data;

@Data
public class TagClientModel implements ClientModel{
    private long id;
    private String name;
}
