package com.example.jsonplaceholder.core.mapper;

import com.example.jsonplaceholder.core.model.Address;
import com.example.jsonplaceholder.core.dto.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component  
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {GeoMapper.class}
)
public interface AddressMapper {
    AddressDto toDto(Address address);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "geo", ignore = true)
    Address toEntity(AddressDto addressDto);

    @Mapping(target = "id", ignore = true)
    void update(AddressDto addressDto, @MappingTarget Address address);
} 