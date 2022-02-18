package com.Sg.companyemployeecasestudy.repository;

import com.Sg.companyemployeecasestudy.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String> {
  List<Company> findByCompanyShortCode(String shortCode);
  Optional<Company> findById(String companyId);
  Boolean existsByCompanyShortCode(String shortCode);
}
