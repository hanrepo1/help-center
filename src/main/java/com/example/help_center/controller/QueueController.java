package com.example.help_center.controller;

import com.example.help_center.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/queue")
public class QueueController {

    @Autowired
    private QueueService queueService;

    @PostMapping("/request")
    public ResponseEntity<Object> requestHelp(@RequestParam String customerId) {
//        customerService.addRequest(customerId);
//        return "Your request has been added to the queue. You are number " + customerService.getQueuePosition(customerId) + " in line.";
        return null;
    }

    @GetMapping("/queue-position")
    public ResponseEntity<Object> getQueuePosition(@RequestParam String customerId) {
//        return customerService.getQueuePosition(customerId);
        return null;
    }
}