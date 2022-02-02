package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.entity.CertificateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CertificateClientModelMapper {
    CertificateClientModelMapper INSTANCE = Mappers.getMapper(CertificateClientModelMapper.class);
    @Mappings( {
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "name",target = "name"),
            @Mapping(source = "description",target = "description"),
            @Mapping(source = "duration",target = "duration"),
            @Mapping(source = "price",target = "price"),
            @Mapping(source = "createDate",target = "createDate"),
            @Mapping(source = "lastUpdateDate",target = "lastUpdateDate"),
            @Mapping(source = "tags", target = "tags")
    } )
    CertificateClientModel certificateToCertificateClientModel(CertificateEntity certificate);
}
