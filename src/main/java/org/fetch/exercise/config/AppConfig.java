package org.fetch.exercise.config;

import org.fetch.exercise.service.ReceiptService;
import org.fetch.exercise.util.PointsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public PointsUtil pointsUtil() {
        return new PointsUtil();
    }

    @Bean
    public ReceiptService receiptService(final PointsUtil pointsUtil) {
        return new ReceiptService(pointsUtil);
    }
}
