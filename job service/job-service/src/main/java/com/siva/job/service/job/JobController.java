package com.siva.job.service.job;

import com.siva.job.service.job.dto.JobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {

   private JobService jobservice;

    public JobController(JobService jobservice) {
        this.jobservice = jobservice;
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobDTO>> findall(){
        return ResponseEntity.ok(jobservice.findall());
    }

    @PostMapping("/jobs")
    public ResponseEntity<String> createjob(@RequestBody Job job){
       jobservice.createJob(job);
       return new ResponseEntity<>("Job added successfully",HttpStatus.CREATED);
    }
    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobDTO> jobById(@PathVariable Long id){

        JobDTO jobDTO = jobservice.jobById(id);
        if(jobDTO!=null)
            return new ResponseEntity<>(jobDTO,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        boolean delete = jobservice.deleteById(id);
        if(delete)
            return new ResponseEntity<>("Job Deleted Successfully",HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //@PutMapping("/jobs/{id}")
    @RequestMapping(value = "/jobs/{id}",method = RequestMethod.PUT)
    public ResponseEntity<String> updateById(@PathVariable Long id,@RequestBody Job updatedjob){
       boolean updated = jobservice.updatejob(id,updatedjob);
       if(updated)
           return new ResponseEntity<>("Job updated Successfully",HttpStatus.OK);
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
