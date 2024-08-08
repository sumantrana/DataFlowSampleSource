package io.spring.dataflow.sample.usagedetailsender;

import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.CompositeMessageConverter;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class DataFlowSampleApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testUsageDetailsSender(){

        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration.getCompleteConfiguration(DataFlowSampleApplication.class))
                .web(WebApplicationType.NONE)
                .run()) {

            OutputDestination target = context.getBean(OutputDestination.class);
            Message<byte[]> sourceMessage = target.receive(1000, "usage-detail");

            CompositeMessageConverter converter = context.getBean(CompositeMessageConverter.class);
            UsageDetail usageDetail = (UsageDetail) converter.fromMessage(sourceMessage, UsageDetail.class);

            assertThat(usageDetail.userId()).isBetween("user1", "user5");
            assertThat(usageDetail.data()).isBetween(0L, 700L);
            assertThat(usageDetail.duration()).isBetween(0L, 300L);
        }
    }

}
