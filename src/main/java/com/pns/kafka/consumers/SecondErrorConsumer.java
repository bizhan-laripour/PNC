package com.pns.kafka.consumers;

import com.google.gson.Gson;
import com.pns.dto.KafkaTransferDto;
import com.pns.dto.MessageDto;
import com.pns.enums.LogLevel;
import com.pns.kafka.producer.KafkaProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SecondErrorConsumer {

    private static final String TOPIC_NAME = "ERROR_TOPIC_2";
    private static final String ERROR_TOPIC = "ERROR_TOPIC_3";


    private final KafkaProducer kafkaProducer;

    public SecondErrorConsumer(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }


    @KafkaListener(topics = SecondErrorConsumer.TOPIC_NAME, containerFactory = "kafkaListenerContainerFactory")
    public void consumeFromDirectTopic(ConsumerRecord<String, KafkaTransferDto> kafka) {
        try {
            Gson gson = new Gson();
            String message = gson.toJson(kafka.value().getObject().toString());
            MessageDto messageDto = gson.fromJson(message, MessageDto.class);
            // todo  send  for firebase
        }catch (Exception ex){
            KafkaTransferDto kafkaTransferDto = kafka.value();
            kafkaTransferDto.setLogLevel(LogLevel.THIRD);
            kafkaProducer.send(ERROR_TOPIC , kafkaTransferDto);
        }
    }
}
