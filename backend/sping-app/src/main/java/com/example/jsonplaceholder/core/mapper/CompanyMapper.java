package com.example.jsonplaceholder.core.mapper;

import com.example.jsonplaceholder.core.model.Company;
import com.example.jsonplaceholder.core.dto.CompanyDto;
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
public interface CompanyMapper {
    CompanyDto toDto(Company company);

    @Mapping(target = "id", ignore = true)
    Company toEntity(CompanyDto companyDto);

    @Mapping(target = "id", ignore = true)
    void update(CompanyDto companyDto, @MappingTarget Company company);
} 