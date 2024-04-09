package com.pns.mapper;

import com.pns.dto.MessageDto;
import com.pns.entity.FailedMessages;

public class MessageMapper {

    private static MessageMapper messageMapper = new MessageMapper();

    private MessageMapper() {}

    public FailedMessages dtoToEntity(MessageDto dto) {
        FailedMessages failedMessages = new FailedMessages();
        failedMessages.setMessage(dto.getMessage());
        failedMessages.setReceiver(dto.getReceiver());
        failedMessages.setSender(dto.getSender());
        return failedMessages;
    }


    public static MessageMapper getInstance(){
        return messageMapper;
    }
}
