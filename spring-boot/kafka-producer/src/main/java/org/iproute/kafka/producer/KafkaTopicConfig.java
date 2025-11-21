package org.iproute.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * TopicConfig
 *
 * @author tech@intellij.io
 * @since 2025-02-28
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Configuration
public class KafkaTopicConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }


    @Bean
    public NewTopic defaultTopic() {
        return TopicBuilder.name(kafkaProperties.getTemplate().getDefaultTopic())
                .partitions(1).replicas(1).build();
    }

}
