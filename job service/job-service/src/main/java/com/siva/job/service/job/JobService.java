package com.siva.job.service.job;

import com.siva.job.service.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findall();
   void createJob(Job job);

    JobDTO jobById(Long id);

    boolean deleteById(Long id);

    boolean updatejob(Long id, Job updatedjob);
}
