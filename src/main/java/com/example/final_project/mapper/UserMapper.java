package com.example.final_project.mapper;

import com.example.final_project.config.MapperConfiguration;
import com.example.final_project.dto.user.UserRegistrationRequestDto;
import com.example.final_project.dto.user.UserResponseDto;
import com.example.final_project.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    User toModel(UserRegistrationRequestDto requestDto);

    UserResponseDto toDto(User user);
}
