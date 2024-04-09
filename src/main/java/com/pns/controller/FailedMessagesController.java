package com.pns.controller;

import com.pns.service.FailedMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("failed-messaged")
public class FailedMessagesController {



    private final FailedMessageService failedMessageService;

    public FailedMessagesController(FailedMessageService failedMessageService) {
        this.failedMessageService = failedMessageService;
    }


    @GetMapping("/find-all")
    public ResponseEntity<?> findAllFailedMessages() {
        return ResponseEntity.ok(failedMessageService.findAll());
    }

}
