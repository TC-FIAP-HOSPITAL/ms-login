package com.fiap.ms.login.entrypoint.controllers.mappers;

import com.fiap.ms.login.domain.enums.RoleEnum;
import com.fiap.ms.login.domain.model.UserDomain;
import com.fiap.ms.login.infrastructure.database.entities.JpaUserEntity;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoRequest;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDomain entityToDomain(JpaUserEntity user) {
        return new UserDomain(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getRoleEnum(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }


    // TODO: UNNECESSARY?
    public static JpaUserEntity domainToEntity(UserDomain userDomain) {
        return new JpaUserEntity(userDomain);
    }

    public static UserDtoResponse domainToDto(UserDomain userDomain) {
        return new UserDtoResponse(
                userDomain.getId().toString(),
                userDomain.getName(),
                userDomain.getEmail(),
                userDomain.getUsername(),
                userDomain.getRole().toString(),
                userDomain.getCreatedAt(),
                userDomain.getUpdatedAt()
                );
    }

    public static UserDomain dtoToDomain(UserDtoRequest userDto) {
        return new UserDomain(
                userDto.id() == null ? null : Long.valueOf(userDto.id()),
                userDto.name(),
                userDto.email(),
                userDto.username(),
                userDto.password(),
                RoleEnum.valueOf(userDto.role())
        );
    }
}
