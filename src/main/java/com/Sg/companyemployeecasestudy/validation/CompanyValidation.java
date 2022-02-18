package com.Sg.companyemployeecasestudy.validation;

import com.Sg.companyemployeecasestudy.entity.Company;
import com.Sg.companyemployeecasestudy.exception.*;
import com.Sg.companyemployeecasestudy.repository.CompanyRepository;
import openapi.model.CreateCompanyRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CompanyValidation {

    private CompanyRepository companyRepository;

    @Autowired
    public CompanyValidation(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company validateCompanyByShortcode(String shortCode) {
        if (Objects.isNull(shortCode) || shortCode.isEmpty()) {
            throw new MandatoryFieldRequired();
        }
        List<Company> company = companyRepository.findByCompanyShortCode(shortCode);
        if (Objects.isNull(company) || company.isEmpty()) {
            throw new CompanyNotFound();
        }
        return company.get(0);
    }

    public void validateCompany(CreateCompanyRequestDto createCompanyRequestDto) {
        if (Objects.isNull(createCompanyRequestDto) ||
                createCompanyRequestDto.getCompanyName().isEmpty() ||
                createCompanyRequestDto.getCompanyShortCode().isEmpty() ||
                createCompanyRequestDto.getAddress().isEmpty() ||
                createCompanyRequestDto.getContactNumber().isEmpty()) {
            throw new FieldCantBeNullOrEmpty();
        }
        if (createCompanyRequestDto.getContactNumber().length() != 10) {
            throw new ContactNumberNotValid();
        }
        if (Boolean.TRUE.equals(companyRepository.existsByCompanyShortCode(createCompanyRequestDto.getCompanyShortCode()))){
            throw new CompanyAlreadyExists();
        }
    }
}
