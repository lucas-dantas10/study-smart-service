package br.com.study_smart_service.utils.mapper.user;

import br.com.study_smart_service.adapter.outbound.user.entity.UserEntity;
import br.com.study_smart_service.domain.user.dto.CreateUserDto;
import br.com.study_smart_service.domain.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "userJpa.id", target = "id"),
            @Mapping(source = "userJpa.name", target = "name"),
            @Mapping(source = "userJpa.email", target = "email"),
            @Mapping(source = "userJpa.password", target = "password"),
            @Mapping(source = "userJpa.createdAt", target = "createdAt"),
            @Mapping(source = "userJpa.updatedAt", target = "updatedAt")
    })
    User jpaToDomain(UserEntity userJpa);

    @Mappings({
            @Mapping(source = "user.id", target = "id"),
            @Mapping(source = "user.name", target = "name"),
            @Mapping(source = "user.email", target = "email"),
            @Mapping(source = "user.password", target = "password"),
            @Mapping(source = "user.createdAt", target = "createdAt"),
            @Mapping(source = "user.updatedAt", target = "updatedAt")
    })
    UserEntity domainToJpa(User user);

    @Mappings({
            @Mapping(source = "dto.name", target = "name"),
            @Mapping(source = "dto.email", target = "email"),
            @Mapping(source = "dto.password", target = "password")
    })
    User dtoToDomain(CreateUserDto dto);
}
