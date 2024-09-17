package com.project_nikhil.SecureOfferHub.batch;

import com.project_nikhil.SecureOfferHub.model.User;
import com.project_nikhil.SecureOfferHub.repository.UserRepository;
import com.project_nikhil.SecureOfferHub.service.MailService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Collections;

@EnableBatchProcessing
@Configuration
public class EmailBatchConfig {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MailService mailService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    JobRepository jobRepository;

    @Bean
    public RepositoryItemReader<User> reader() {
        RepositoryItemReader<User> reader = new RepositoryItemReader<>();
        reader.setRepository(userRepository);
        reader.setMethodName("findByActiveTrue"); // Adjust method name as per your repository
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC)); // Sorting by ID
        reader.setPageSize(10); // Adjust the page size as needed
        return reader;
    }
    @Bean
    public ItemProcessor<User, String> processor() {
        return user -> {
            // Create email content
            String content = "Hello " + user.getName() + ",\nCheck out the latest offers for your cards.";
            return content;
        };
    }

    @Bean
    public EmailItemWriter writer() {
        return new EmailItemWriter(mailService);
    }
    @Bean
    public Step emailStep() {
        return new StepBuilder("emailStep", jobRepository)
                .<User, String>chunk(10, new MongoTransactionManager())
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(new SimpleAsyncTaskExecutor()) // Multithreading support
                .build();
    }

    @Bean
    public Job emailJob() {
        return new JobBuilder("emailJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(emailStep())
                .end()
                .build();
    }

}
