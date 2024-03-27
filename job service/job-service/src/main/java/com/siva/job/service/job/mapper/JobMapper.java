package com.siva.job.service.job.mapper;

import com.siva.job.service.job.Job;
import com.siva.job.service.job.dto.JobDTO;
import com.siva.job.service.job.external.Company;
import com.siva.job.service.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToDto(Job job, Company company, List<Review> reviews){
        JobDTO jobDTO =new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setMaxsalary(job.getMaxsalary());
        jobDTO.setMinsalary(job.getMinsalary());
        jobDTO.setCompany(company);
        jobDTO.setReview(reviews);

        return jobDTO;
    }
}
