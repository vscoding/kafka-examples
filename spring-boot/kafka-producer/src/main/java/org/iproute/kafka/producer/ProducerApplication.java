package org.iproute.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * ProducerApplication
 *
 * @author tech@intellij.io
 * @since 2025-02-28
 */
@SpringBootApplication
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @RequiredArgsConstructor(onConstructor = @__(@Autowired))
    @RequestMapping("/producer")
    @RestController
    @Slf4j
    public static class Producer {
        private final KafkaTemplate<String, String> kafkaTemplate;
        private final ObjectMapper objectMapper;

        @PostMapping("/createMsg")
        public void createMsg(@RequestBody Map<String, Object> msgBody) {
            // String defaultTopic = kafkaProperties.getTemplate().getDefaultTopic();
            try {
                String msg = objectMapper.writeValueAsString(msgBody);
                GenericMessage<String> message = new GenericMessage<>(msg);
                CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send(message);
                send.thenAccept(result -> log.info("Message sent successfully: {}", result.getProducerRecord().value()));

            } catch (JsonProcessingException e) {
                log.error("Error while processing message: {}", e.getMessage());
            }

        }
    }

}
