package com.Sg.companyemployeecasestudy.service;

import com.Sg.companyemployeecasestudy.Mapper.EmployeeMapperImpl;
import com.Sg.companyemployeecasestudy.entity.Company;
import com.Sg.companyemployeecasestudy.entity.Employee;
import com.Sg.companyemployeecasestudy.exception.CompanyNotFound;
import com.Sg.companyemployeecasestudy.repository.CompanyRepository;
import com.Sg.companyemployeecasestudy.repository.EmployeeRepository;
import com.Sg.companyemployeecasestudy.validation.EmployeeValidation;
import openapi.model.CreateEmployeeRequestDto;
import openapi.model.CreateEmployeeResponseDto;
import openapi.model.GetEmployeeResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class EmployeeService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeValidation employeeValidation;
    private final EmployeeMapperImpl employeeMapperImpl;
    Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, EmployeeValidation employeeValidation, CompanyRepository companyRepository, EmployeeMapperImpl employeeMapperImpl) {
        this.employeeRepository = employeeRepository;
        this.employeeValidation = employeeValidation;
        this.companyRepository = companyRepository;
        this.employeeMapperImpl = employeeMapperImpl;
    }

    public ResponseEntity<CreateEmployeeResponseDto> createEmployee(CreateEmployeeRequestDto createEmployeeRequestDto) throws IOException {
        employeeValidation.validateEmployee(createEmployeeRequestDto);
        Optional<Company> company = companyRepository.findById(createEmployeeRequestDto.getCompanyId());
        Employee employee = employeeMapperImpl.mapToEntity(createEmployeeRequestDto);
        if (company.isPresent()) {
            employee.setCompany(company.get());
        } else throw new CompanyNotFound();
        employee.setCreatedBy("System");
        employee.setCreatedOn(LocalDateTime.now());
        employee.setUpdatedBy("System");
        employee.setUpdatedOn(LocalDateTime.now());
        Employee savedEmployee = employeeRepository.save(employee);
        CreateEmployeeResponseDto createEmployeeResponseDto = employeeMapperImpl.mapEntityToResponseDto(savedEmployee);
        fetchAgeFromExternalApi();
        return new ResponseEntity<>(createEmployeeResponseDto, HttpStatus.CREATED);
    }

    public void fetchAgeFromExternalApi() throws IOException {
        String name = "Sg";
        URL url = new URL("https://api.agify.io/?name=" + name);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        logger.info("{}", content);
        in.close();
    }

    public ResponseEntity<GetEmployeeResponseDto> getEmployee(BigDecimal employeeId) {
        Employee employee = employeeValidation.validateEmployeeByEmployeeId(employeeId);
        GetEmployeeResponseDto getEmployeeResponseDto = employeeMapperImpl.mapToEntity1(employee);
        getEmployeeResponseDto.setCompanyId(employee.getCompany().getId());
        return new ResponseEntity<>(getEmployeeResponseDto, HttpStatus.OK);
    }
}
