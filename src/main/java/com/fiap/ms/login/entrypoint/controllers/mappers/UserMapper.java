package com.fiap.ms.login.entrypoint.controllers.mappers;

import com.fiap.ms.login.domain.model.User;
import com.fiap.ms.login.infrastructure.dataproviders.database.entities.JpaUserEntity;
import com.fiap.ms.login.domain.model.Role;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoRequest;
import com.fiap.ms.login.entrypoint.controllers.dto.UserDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User entityToDomain(JpaUserEntity user) {
        return new User(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }


    // TODO: UNNECESSARY?
    public static JpaUserEntity domainToEntity(User user) {
        return new JpaUserEntity(user);
    }

    public static UserDtoResponse domainToDto(User user) {
        return new UserDtoResponse(
                user.getId().toString(),
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getRole().toString(),
                user.getCreatedAt(),
                user.getUpdatedAt()
                );
    }

    public static User dtoToDomain(UserDtoRequest userDto) {
        return new User(
                userDto.id() == null ? null : Long.valueOf(userDto.id()),
                userDto.name(),
                userDto.email(),
                userDto.username(),
                userDto.password(),
                Role.valueOf(userDto.role())
        );
    }
}
