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
public class FirstErrorConsumer {


    private static final String TOPIC_NAME = "ERROR_TOPIC_1";
    private static final String ERROR_TOPIC = "ERROR_TOPIC_2";

    private final KafkaProducer kafkaProducer;

    public FirstErrorConsumer(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }


    @KafkaListener(topics = FirstErrorConsumer.TOPIC_NAME, containerFactory = "kafkaListenerContainerFactory")
    public void consumeFromDirectTopic(ConsumerRecord<String, KafkaTransferDto> kafka) {
        try {
            Gson gson = new Gson();
            String message = gson.toJson(kafka.value().getObject().toString());
            MessageDto messageDto = gson.fromJson(message, MessageDto.class);
            // todo  send  for firebase
        }catch (Exception ex){
            KafkaTransferDto kafkaTransferDto = kafka.value();
            kafkaTransferDto.setLogLevel(LogLevel.SECOND);
            kafkaProducer.send(ERROR_TOPIC , kafkaTransferDto);
        }
    }

}
