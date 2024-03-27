package com.siva.job.service.job.impl;


import com.siva.job.service.job.client.CompanyClient;
import com.siva.job.service.job.client.ReviewClient;
import com.siva.job.service.job.dto.JobDTO;
import com.siva.job.service.job.external.Company;
import com.siva.job.service.job.Job;
import com.siva.job.service.job.JobRepository;
import com.siva.job.service.job.JobService;
import com.siva.job.service.job.external.Review;
import com.siva.job.service.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    JobRepository jobrepository;

    @Autowired
    RestTemplate restTemplate;
    private CompanyClient companyClient;
    private ReviewClient reviewClient;
    int attempt =0;

    public JobServiceImpl(JobRepository jobrepository,CompanyClient companyClient,ReviewClient reviewClient) {
        this.jobrepository = jobrepository;
        this.companyClient=companyClient;
        this.reviewClient = reviewClient;
    }

    private JobDTO convertToDto(Job job) {
        //RestTemplate restTemplate = new RestTemplate();
        Company company = companyClient.getCompany(job.getCompanyId());
//        with rest template:
//        Company company = restTemplate.getForObject(
//                "http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(),
//                Company.class);
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
//        ResponseEntity<List<Review>> reviewResponse=restTemplate.exchange("http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<Review>>() {
//                });
//               List<Review> reviews = reviewResponse.getBody();
        JobDTO jobDTO = JobMapper.mapToDto(job,company,reviews);
//        jobDTO.setCompany(company);
        return jobDTO;


    }

    @Override
//    @CircuitBreaker(name="companyBreaker",
//            fallbackMethod = "companyBreakerFallback")
    @Retry(name="companyBreaker",
            fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findall() {
        System.out.println("Attempt: "+ ++attempt);

        List<Job> jobs = jobrepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    public List<String>companyBreakerFallback(Exception e){
        List<String> list = new ArrayList<>();
        list.add("Dummy");
        return list;
    }

    @Override
    public void createJob(Job job) {
        jobrepository.save(job);
    }

    @Override
    public JobDTO jobById(Long id) {
        Job job= jobrepository.findById(id).orElse(null);
        return convertToDto(job);


    }

    @Override
    public boolean deleteById(Long id) {
        try {
            jobrepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updatejob(Long id, Job updatedjob) {
        Optional<Job> jobOptional = jobrepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedjob.getTitle());
            job.setDescription(updatedjob.getDescription());
            job.setMinsalary(updatedjob.getMinsalary());
            job.setMaxsalary(updatedjob.getMaxsalary());
            job.setLocation(updatedjob.getLocation());
            jobrepository.save(job);
            return true;
        }

        return false;
    }

}