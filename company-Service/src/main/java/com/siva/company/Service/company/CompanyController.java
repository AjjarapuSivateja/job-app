package com.siva.company.Service.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
   Logger log = LoggerFactory.getLogger(CompanyController.class);
   private  CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
          log.info("Starting to get all Companies");
        return  new ResponseEntity<>(companyService.getAllCompanies(),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id,@RequestBody Company company ){
       boolean updated= companyService.updateCompany(company,id);
       if(updated) {
           return new ResponseEntity<>("Company Updated Successfully!", HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<String> addCompany(@RequestBody Company company){
        companyService.createCompany(company);
        return  new ResponseEntity("Company Added Successfully",HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
       boolean isdeleted= companyService.deleteById(id);
        if(isdeleted){
            return new ResponseEntity<>("Deleted company Successfulluy!",HttpStatus.OK);
        }
        return new  ResponseEntity(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById (@PathVariable Long id){
        Company company = companyService.getById(id);
        if(company!=null){
            return new ResponseEntity<>(company,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
