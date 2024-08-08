package io.spring.dataflow.sample.usagedetailsender;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.function.Supplier;

@Configuration
public class UserDetailSender {

    private final String[] users = {"user1", "user2", "user3", "user4", "user5"};

    @Bean
    public Supplier<UsageDetail> sendEvents(){
        return () -> {
          String userId = this.users[new Random().nextInt(5)];
          long duration = new Random().nextInt(300);
          long data = new Random().nextInt(700);

          return new UsageDetail(userId, duration, data);
        };
    }
}
