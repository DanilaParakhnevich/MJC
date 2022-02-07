package com.epam.esm.mapper;

import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * The interface Tag client model mapper.
 */
@Component
@Mapper(componentModel = "spring")
public interface TagModelMapper {


    TagClientModel tagToTagClientModel(TagEntity tag);

    TagEntity tagClientModelToTag(TagClientModel tag);
}
