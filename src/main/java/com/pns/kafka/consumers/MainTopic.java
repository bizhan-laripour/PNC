package com.pns.kafka.consumers;


import com.google.gson.Gson;
import com.pns.dto.KafkaTransferDto;
import com.pns.dto.MessageDto;
import com.pns.enums.LogLevel;
import com.pns.firebase.FireBaseSender;
import com.pns.kafka.producer.KafkaProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MainTopic {


    private static final String TOPIC_NAME = "MAIN_TOPIC";
    private static final String ERROR_TOPIC = "ERROR_TOPIC_1";

    private final KafkaProducer kafkaProducer;

    private final FireBaseSender fireBaseSender;

    private List<MessageDto> messages;


    public MainTopic(KafkaProducer kafkaProducer, FireBaseSender fireBaseSender) {
        this.kafkaProducer = kafkaProducer;
        this.fireBaseSender = fireBaseSender;
    }


    @KafkaListener(topics = MainTopic.TOPIC_NAME, containerFactory = "kafkaListenerContainerFactory")
    public void consumer(ConsumerRecord<String, KafkaTransferDto> kafka) {


        Gson gson = new Gson();
        String message = gson.toJson(kafka.value().getObject().toString());
        MessageDto messageDto = gson.fromJson(message, MessageDto.class);
        try {
            checkList(messageDto);
        } catch (Exception exception) {
            for(MessageDto obj : messages){
                KafkaTransferDto kafkaTransferDto = new KafkaTransferDto();
                kafkaTransferDto.setObject(obj);
                kafkaTransferDto.setLogLevel(LogLevel.FIRST);
                kafkaTransferDto.setTopic(ERROR_TOPIC);
                kafkaProducer.send(ERROR_TOPIC, kafkaTransferDto);
            }

        }
    }


    private void checkList(MessageDto messageDto) {
        if (messages.size() == 10) {
            fireBaseSender.sendMessage(messages);
            messages.clear();
        }else {
            messages.add(messageDto);
        }
    }


}
