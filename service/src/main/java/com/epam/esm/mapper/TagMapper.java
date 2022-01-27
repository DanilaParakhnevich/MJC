package com.epam.esm.mapper;

import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);
    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "name",target = "name"),
    })
    TagClientModel tagToTagClientModel(TagEntity tag);
}
