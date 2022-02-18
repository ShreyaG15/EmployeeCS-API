package com.Sg.companyemployeecasestudy.Mapper;


import com.Sg.companyemployeecasestudy.entity.Employee;
import openapi.model.CreateEmployeeRequestDto;
import openapi.model.CreateEmployeeResponseDto;
import openapi.model.GetEmployeeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "Spring")
public interface EmployeeMapper {

    @Mapping(source = "createEmployeeRequestDto.dateOfBirth",target = "dob")
    Employee mapToEntity(CreateEmployeeRequestDto createEmployeeRequestDto);

    CreateEmployeeResponseDto mapEntityToResponseDto(Employee savedEmployee);

    @Mapping(source = "employee.dob",target = "dateOfBirth")
    GetEmployeeResponseDto mapToEntity1(Employee employee);
}
