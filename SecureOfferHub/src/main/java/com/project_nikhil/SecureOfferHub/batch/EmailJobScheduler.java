package com.project_nikhil.SecureOfferHub.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class EmailJobScheduler {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job emailJob;

    // Scheduled to run every Sunday at 8:00 AM
    @Scheduled(cron = "0 0 8 ? * SUN")
    public void runEmailJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(emailJob, jobParameters);
    }
}
