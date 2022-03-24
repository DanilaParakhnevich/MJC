package com.epam.esm.mapper;

import com.epam.esm.dto.UserClientModel;
import com.epam.esm.entity.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserModelMapper {
    UserClientModel toClientModel(UserEntity user);

    UserEntity toUserEntity(UserClientModel user);
}
