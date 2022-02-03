package com.epam.esm.mapper;

import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-03T17:03:33+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 1.8.0_301 (Oracle Corporation)"
)
public class TagClientModelMapperImpl implements TagClientModelMapper {

    @Override
    public TagClientModel tagToTagClientModel(TagEntity tag) {
        if ( tag == null ) {
            return null;
        }

        TagClientModel tagClientModel = new TagClientModel();

        tagClientModel.setId( tag.getId() );
        tagClientModel.setName( tag.getName() );

        return tagClientModel;
    }
}
