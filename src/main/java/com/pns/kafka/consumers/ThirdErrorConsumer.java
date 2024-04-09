package com.pns.kafka.consumers;

import com.google.gson.Gson;
import com.pns.dto.KafkaTransferDto;
import com.pns.dto.MessageDto;
import com.pns.mapper.MessageMapper;
import com.pns.service.FailedMessageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ThirdErrorConsumer {

    private static final String TOPIC_NAME = "ERROR_TOPIC_3";

    private final FailedMessageService failedMessageService;

    public ThirdErrorConsumer(FailedMessageService failedMessageService) {
        this.failedMessageService = failedMessageService;
    }


    @KafkaListener(topics = ThirdErrorConsumer.TOPIC_NAME, containerFactory = "kafkaListenerContainerFactory")
    public void consumeFromDirectTopic(ConsumerRecord<String, KafkaTransferDto> kafka) {
        MessageDto messageDto;
        Gson gson = new Gson();
        String message = gson.toJson(kafka.value().getObject().toString());
        messageDto = gson.fromJson(message, MessageDto.class);
        try {
            // todo  send  for firebase
        }catch (Exception ex){
                failedMessageService.save(MessageMapper.getInstance().dtoToEntity(messageDto));
        }
    }
}
