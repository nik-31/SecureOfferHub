package com.project_nikhil.SecureOfferHub.batch;

import jakarta.activation.DataSource;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig extends DefaultBatchConfiguration {

    public void setDataSource(DataSource dataSource) {
        // Do not set a data source, use a Map-based JobRepository
        // Spring Batch will now use a map-based repository instead of a database
    }
}
