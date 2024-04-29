package com.pns.firebase;

import com.google.firebase.messaging.Message;
import com.pns.dto.MessageDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FireBaseSender {


    public void sendMessage(List<MessageDto> messages , String topic) {
        messages.forEach(obj -> {
            Message msg = Message.builder()
                    .setTopic(topic)
                    .putData("body", obj.getMessage())
                    .build();
        });

    }
}
