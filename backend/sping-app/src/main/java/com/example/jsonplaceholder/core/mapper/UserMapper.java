package com.example.jsonplaceholder.core.mapper;

import com.example.jsonplaceholder.core.model.User;
import com.example.jsonplaceholder.core.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {AddressMapper.class, CompanyMapper.class}
)
public interface UserMapper {
    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "company", ignore = true)
    User toEntity(UserDto userDto);

    @Mapping(target = "id", ignore = true)
    void update(UserDto userDto, @MappingTarget User user);
}