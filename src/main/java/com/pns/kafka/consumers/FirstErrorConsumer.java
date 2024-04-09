package com.pns.kafka.consumers;


import com.google.gson.Gson;
import com.pns.dto.KafkaTransferDto;
import com.pns.dto.MessageDto;
import com.pns.enums.LogLevel;
import com.pns.firebase.FireBaseSender;
import com.pns.kafka.producer.KafkaProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FirstErrorConsumer {


    private static final String TOPIC_NAME = "ERROR_TOPIC_1";
    private static final String ERROR_TOPIC = "ERROR_TOPIC_2";

    private List<MessageDto> messages;

    private final FireBaseSender fireBaseSender;

    private final KafkaProducer kafkaProducer;

    public FirstErrorConsumer(FireBaseSender fireBaseSender, KafkaProducer kafkaProducer) {
        this.fireBaseSender = fireBaseSender;
        this.kafkaProducer = kafkaProducer;
    }


    @KafkaListener(topics = FirstErrorConsumer.TOPIC_NAME, containerFactory = "kafkaListenerContainerFactory")
    public void consumer(ConsumerRecord<String, KafkaTransferDto> kafka) {
        Gson gson = new Gson();
        String message = gson.toJson(kafka.value().getObject().toString());
        MessageDto messageDto = gson.fromJson(message, MessageDto.class);
        messages.add(messageDto);
    }


    @Scheduled(fixedRate = 60000)
    public void sendToFireBase() {
        List<MessageDto> list = new ArrayList<>(messages);
        for (int i = 0; i < messages.size(); i++) {
            if (i != 10) {
                list.add(messages.get(i));
                list.remove(i);
            } else {
                break;
            }
        }
        try {
            fireBaseSender.sendMessage(list);
        } catch (Exception ex) {
            for (MessageDto messageDto : messages) {
                KafkaTransferDto kafkaTransferDto = new KafkaTransferDto();
                kafkaTransferDto.setLogLevel(LogLevel.SECOND);
                kafkaTransferDto.setObject(messageDto);
                kafkaProducer.send(ERROR_TOPIC, kafkaTransferDto);
            }
        }
    }


}
