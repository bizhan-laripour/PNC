package com.pns.kafka.consumers;

import com.google.gson.Gson;
import com.pns.dto.KafkaTransferDto;
import com.pns.dto.MessageDto;
import com.pns.firebase.FireBaseSender;
import com.pns.mapper.MessageMapper;
import com.pns.service.FailedMessageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ThirdErrorConsumer {

    private static final String TOPIC_NAME = "ERROR_TOPIC_3";
    private final FailedMessageService failedMessageService;
    private List<MessageDto> messages;
    private final FireBaseSender fireBaseSender;

    public ThirdErrorConsumer(FailedMessageService failedMessageService, FireBaseSender fireBaseSender) {
        this.failedMessageService = failedMessageService;
        this.fireBaseSender = fireBaseSender;
    }


    @KafkaListener(topics = ThirdErrorConsumer.TOPIC_NAME, containerFactory = "kafkaListenerContainerFactory")
    public void consumer(ConsumerRecord<String, KafkaTransferDto> kafka) {

        Gson gson = new Gson();
        String message = gson.toJson(kafka.value().getObject().toString());
        MessageDto messageDto = gson.fromJson(message, MessageDto.class);
        messages.add(messageDto);

    }


    @Scheduled(fixedRate = 60000)
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
            fireBaseSender.sendMessage(list);
        }catch (Exception ex){
            for(MessageDto obj : messages){
                failedMessageService.save(MessageMapper.getInstance().dtoToEntity(obj));
            }
        }
    }



}
