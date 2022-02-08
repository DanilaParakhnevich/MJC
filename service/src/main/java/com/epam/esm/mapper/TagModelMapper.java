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
    /**
     * Tag to tag client model.
     *
     * @param tag the tag
     * @return the tag client model
     */
    TagClientModel tagToTagClientModel(TagEntity tag);

    /**
     * Tag client model to tag client model.
     *
     * @param tag the tag
     * @return the tag entity
     */
    TagEntity tagClientModelToTag(TagClientModel tag);
}
