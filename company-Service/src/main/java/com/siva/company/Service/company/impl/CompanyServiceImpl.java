package com.siva.company.Service.company.impl;


import com.siva.company.Service.company.Company;
import com.siva.company.Service.company.CompanyRepository;
import com.siva.company.Service.company.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class CompanyServiceImpl implements CompanyService {
   private Logger log =  LoggerFactory.getLogger(CompanyServiceImpl.class);
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
       log.info("Getting all Companies");
        return companyRepository.findAll();


    }

    @Override
    public boolean updateCompany(Company updatedcompany, Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            company.setDescription(updatedcompany.getDescription());
            company.setName(updatedcompany.getName());
            companyRepository.save(company);
           log.info("Updating Company");
            return true;
        }
        return false;
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteById(Long id) {
        if (companyRepository.existsById(id)) {
           log.info("Deleting Company By Id");
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Company getById(Long id) {
        log.info("Getting Company By id");
        return companyRepository.findById(id).orElse(null);

    }


}
