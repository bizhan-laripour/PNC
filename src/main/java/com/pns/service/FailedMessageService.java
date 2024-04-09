package com.pns.service;

import com.pns.entity.FailedMessages;
import com.pns.repository.FailedMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FailedMessageService {


    private final FailedMessageRepository failedMessageRepository;

    public FailedMessageService(FailedMessageRepository failedMessageRepository) {
        this.failedMessageRepository = failedMessageRepository;
    }


    public FailedMessages save(FailedMessages failedMessages) {
        return failedMessageRepository.save(failedMessages);
    }


    public List<FailedMessages> findAll(){
        return failedMessageRepository.findAll();
    }
}
