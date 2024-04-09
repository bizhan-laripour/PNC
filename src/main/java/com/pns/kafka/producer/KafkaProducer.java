package com.pns.kafka.producer;

import com.pns.dto.KafkaTransferDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final KafkaTemplate<String, KafkaTransferDto> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, KafkaTransferDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void send(String topic, KafkaTransferDto kafkaTransferDto) {
        kafkaTemplate.send(topic, kafkaTransferDto);
    }
}
