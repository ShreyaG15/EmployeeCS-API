package com.Sg.companyemployeecasestudy.service;

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
import java.time.ZoneOffset;

@Component
public class CompanyService {

    CompanyRepository companyRepository;
    CompanyValidation companyValidation;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, CompanyValidation companyValidation) {
        this.companyRepository = companyRepository;
        this.companyValidation = companyValidation;
    }

    public ResponseEntity<CreateCompanyResponseDto> createCompany(CreateCompanyRequestDto createCompanyRequestDto) {
        companyValidation.validateCompany(createCompanyRequestDto);
        Company company = new Company();
        company.setCompanyName(createCompanyRequestDto.getCompanyName());
        company.setCompanyShortCode(createCompanyRequestDto.getCompanyShortCode());
        company.setAddress(createCompanyRequestDto.getAddress());
        company.setContactNumber(createCompanyRequestDto.getContactNumber());
        company.setCreatedBy("System");
        company.setCreatedOn(LocalDateTime.now());
        company.setUpdatedBy("System");
        company.setUpdatedOn(LocalDateTime.now());
        Company savedCompany = companyRepository.save(company);
        CreateCompanyResponseDto createCompanyResponseDto = new CreateCompanyResponseDto();
        createCompanyResponseDto.setCompanyId(savedCompany.getId());
        return new ResponseEntity<>(createCompanyResponseDto, HttpStatus.CREATED);
    }

    public ResponseEntity<GetCompanyResponseDto> getCompany(String companyShortCode) {
        Company company = companyValidation.validateCompanyByShortcode(companyShortCode);
        GetCompanyResponseDto getCompanyResponseDto = new GetCompanyResponseDto();
        getCompanyResponseDto.setCompanyName(company.getCompanyName());
        getCompanyResponseDto.setCompanyShortCode(company.getCompanyShortCode());
        getCompanyResponseDto.setAddress(company.getAddress());
        getCompanyResponseDto.setContactNumber(company.getContactNumber());
        getCompanyResponseDto.setCreatedOn(company.getCreatedOn().atOffset(ZoneOffset.UTC));
        getCompanyResponseDto.setUpdatedOn(company.getUpdatedOn().atOffset(ZoneOffset.UTC));
        return new ResponseEntity<>(getCompanyResponseDto, HttpStatus.OK);
    }
}
