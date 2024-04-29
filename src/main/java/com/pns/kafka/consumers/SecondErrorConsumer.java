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
public class SecondErrorConsumer {

    private static final String TOPIC_NAME = "ERROR_TOPIC_2";
    private static final String ERROR_TOPIC = "ERROR_TOPIC_3";
    private List<MessageDto> messages = new ArrayList<>();
    private final KafkaProducer kafkaProducer;
    private final FireBaseSender fireBaseSender;
    private final String FIREBASE_TOPIC = "FIREBASE_TOPIC";

    public SecondErrorConsumer(KafkaProducer kafkaProducer, FireBaseSender fireBaseSender) {
        this.kafkaProducer = kafkaProducer;
        this.fireBaseSender = fireBaseSender;
    }

    @KafkaListener(topics = SecondErrorConsumer.TOPIC_NAME, containerFactory = "kafkaListenerContainerFactory")
    public void consumer(ConsumerRecord<String, KafkaTransferDto> kafka) {
        try {
            Gson gson = new Gson();
            String message = gson.toJson(kafka.value().getObject().toString());
            MessageDto messageDto = gson.fromJson(message, MessageDto.class);
            messages.add(messageDto);
        }catch (Exception ex){
            KafkaTransferDto kafkaTransferDto = kafka.value();
            kafkaTransferDto.setLogLevel(LogLevel.THIRD);
            kafkaProducer.send(ERROR_TOPIC , kafkaTransferDto);
        }
    }


    @Scheduled(fixedRate = 120000)
    public void sendToFireBase(){
        List<MessageDto> list = new ArrayList<>(messages);
        for(int i = 0 ; i < messages.size() ; i++){
            if(i != 10) {
                list.add(messages.get(i));
                list.remove(i);
            }else{
                break;
            }
        }
        try {
            fireBaseSender.sendMessage(list , FIREBASE_TOPIC);
        } catch (Exception ex) {
            for (MessageDto messageDto : messages) {
                KafkaTransferDto kafkaTransferDto = new KafkaTransferDto();
                kafkaTransferDto.setLogLevel(LogLevel.THIRD);
                kafkaTransferDto.setObject(messageDto);
                kafkaProducer.send(ERROR_TOPIC, kafkaTransferDto);
            }
        }
    }
}
