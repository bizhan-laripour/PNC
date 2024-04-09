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
public class MainTopic {


    private static final String TOPIC_NAME = "MAIN_TOPIC";
    private static final String ERROR_TOPIC = "ERROR_TOPIC_1";

    private final KafkaProducer kafkaProducer;

//    private static List<MessageDto> list;

    public MainTopic(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }


    @KafkaListener(topics = MainTopic.TOPIC_NAME, containerFactory = "kafkaListenerContainerFactory")
    public void consumeFromDirectTopic(ConsumerRecord<String, KafkaTransferDto> kafka) {

        try {
            Gson gson = new Gson();
            String message = gson.toJson(kafka.value().getObject().toString());
            MessageDto messageDto = gson.fromJson(message, MessageDto.class);
            // todo send to firebase
        }catch (Exception exception){
            KafkaTransferDto dto = kafka.value();
            dto.setLogLevel(LogLevel.FIRST);
            kafkaProducer.send(ERROR_TOPIC , dto);
        }
    }


}
