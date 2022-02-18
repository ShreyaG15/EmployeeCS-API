package com.Sg.companyemployeecasestudy.service;

import com.Sg.companyemployeecasestudy.Mapper.CompanyMapperImpl;
import com.Sg.companyemployeecasestudy.entity.Company;
import com.Sg.companyemployeecasestudy.repository.CompanyRepository;
import com.Sg.companyemployeecasestudy.validation.CompanyValidation;
import openapi.model.CreateCompanyRequestDto;
import openapi.model.CreateCompanyResponseDto;
import openapi.model.GetCompanyResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CompanyService {

    private CompanyMapperImpl companyMapperImpl;
    private CompanyRepository companyRepository;
    private CompanyValidation companyValidation;

    @Autowired
    public CompanyService(CompanyMapperImpl companyMapperImpl, CompanyRepository companyRepository, CompanyValidation companyValidation) {
        this.companyMapperImpl = companyMapperImpl;
        this.companyRepository = companyRepository;
        this.companyValidation = companyValidation;
    }

    public ResponseEntity<CreateCompanyResponseDto> createCompany(CreateCompanyRequestDto createCompanyRequestDto) {
        companyValidation.validateCompany(createCompanyRequestDto);
        Company company = companyMapperImpl.mapToEntity(createCompanyRequestDto);
        company.setCreatedBy("System");
        company.setCreatedOn(LocalDateTime.now());
        company.setUpdatedBy("System");
        company.setUpdatedOn(LocalDateTime.now());
        Company savedCompany = companyRepository.save(company);
        CreateCompanyResponseDto createCompanyResponseDto = companyMapperImpl.mapEntityToResponseDto(savedCompany);
        return new ResponseEntity<>(createCompanyResponseDto, HttpStatus.CREATED);
    }

    public ResponseEntity<GetCompanyResponseDto> getCompany(String companyShortCode) {
        Company company = companyValidation.validateCompanyByShortcode(companyShortCode);
        GetCompanyResponseDto getCompanyResponseDto = companyMapperImpl.mapToEntity1(company);
        return new ResponseEntity<>(getCompanyResponseDto, HttpStatus.OK);
    }
}
