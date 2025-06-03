package com.example.jsonplaceholder.core.mapper;

import com.example.jsonplaceholder.core.model.Geo;
import com.example.jsonplaceholder.core.dto.GeoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Component  
@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface GeoMapper {
    GeoDto toDto(Geo geo);

    @Mapping(target = "id", ignore = true)
    Geo toEntity(GeoDto geoDto);

    @Mapping(target = "id", ignore = true)
    void update(GeoDto geoDto, @MappingTarget Geo geo);
} 