package com.Sg.companyemployeecasestudy.exceptionHandler;

import com.Sg.companyemployeecasestudy.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomisedResponseEntityHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CompanyNotFound.class)
    public final ResponseEntity<Object> companyNotFoundException() {
        ExceptionResponse exceptionResponse = new ExceptionResponse("404", "COMPANY NOT FOUND");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeNotFound.class)
    public final ResponseEntity<Object> employeeNotFoundException() {
        ExceptionResponse exceptionResponse = new ExceptionResponse("404", "EMPLOYEE NOT FOUND");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MandatoryFieldRequired.class)
    public final ResponseEntity<Object> mandatoryFieldRequired() {
        ExceptionResponse exceptionResponse = new ExceptionResponse("400", "Mandatory field is missing");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FieldCantBeNullOrEmpty.class)
    public final ResponseEntity<Object> fieldCantBeNullOrEmpty() {
        ExceptionResponse exceptionResponse = new ExceptionResponse("400", "Field can't be Null or Empty");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ContactNumberNotValid.class)
    public final ResponseEntity<Object> contactNumberNotValid() {
        ExceptionResponse exceptionResponse = new ExceptionResponse("400", "Contact Number should be of 10 digits");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("400", "Null value Invalid");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
