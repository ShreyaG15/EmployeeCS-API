package com.Sg.companyemployeecasestudy.Mapper;

import com.Sg.companyemployeecasestudy.entity.Company;
import openapi.model.CreateCompanyRequestDto;
import openapi.model.CreateCompanyResponseDto;
import openapi.model.GetCompanyResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface CompanyMapper {
    Company mapToEntity(CreateCompanyRequestDto createCompanyRequestDto);

    @Mapping(source = "savedCompany.id",target = "companyId")
    CreateCompanyResponseDto mapEntityToResponseDto(Company savedCompany);

    GetCompanyResponseDto mapToEntity1(Company company);
}
