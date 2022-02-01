package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.TagEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-01T23:12:21+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 1.8.0_301 (Oracle Corporation)"
)
public class CertificateClientModelMapperImpl implements CertificateClientModelMapper {

    @Override
    public CertificateClientModel certificateToCertificateClientModel(CertificateEntity certificate) {
        if ( certificate == null ) {
            return null;
        }

        CertificateClientModel certificateClientModel = new CertificateClientModel();

        certificateClientModel.setId( certificate.getId() );
        certificateClientModel.setName( certificate.getName() );
        certificateClientModel.setDescription( certificate.getDescription() );
        certificateClientModel.setDuration( certificate.getDuration() );
        certificateClientModel.setPrice( certificate.getPrice() );
        certificateClientModel.setCreateDate( certificate.getCreateDate() );
        certificateClientModel.setLastUpdateDate( certificate.getLastUpdateDate() );
        List<TagEntity> list = certificate.getTags();
        if ( list != null ) {
            certificateClientModel.setTags( new ArrayList<TagEntity>( list ) );
        }

        return certificateClientModel;
    }
}
