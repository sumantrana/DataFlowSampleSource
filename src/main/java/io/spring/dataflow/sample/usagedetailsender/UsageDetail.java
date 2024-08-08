package io.spring.dataflow.sample.usagedetailsender;

public record UsageDetail (String userId, long duration, long data) {
}
