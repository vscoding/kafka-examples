package org.iproute.kafka.consumer.servcies;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * KafkaConsumeService
 *
 * @author tech@intellij.io
 * @since 2025-02-28
 */
@Service
@Slf4j
public class KafkaConsumeService {

    @KafkaListener(topics = "${spring.kafka.template.default-topic:test}", groupId = "${spring.kafka.consumer.group-id:test}")
    public void consume(String msg) {
        log.info("consume msg: {}", msg);
    }

}
