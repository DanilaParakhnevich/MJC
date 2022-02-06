package com.epam.esm.mapper;

import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * The interface Tag client model mapper.
 */
@Mapper
@Component
public interface TagClientModelMapper {

    /**
     * The constant INSTANCE.
     */
    TagClientModelMapper INSTANCE = Mappers.getMapper(TagClientModelMapper.class);

    /**
     * Tag to tag client model tag client model.
     *
     * @param tag the tag
     * @return the tag client model
     */
    @Mappings( {
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "name",target = "name"),
    } )
    TagClientModel tagToTagClientModel(TagEntity tag);
}
