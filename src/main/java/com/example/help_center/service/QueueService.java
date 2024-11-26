package com.example.help_center.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class QueueService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(3); // 3 staff

    public void addRequest(String customerId) {
//        CustomerRequest request = new CustomerRequest();
//        request.setCustomerId(customerId);
//        request.setProcessed(false);
//        requestRepository.save(request); // Save the request to the database
//        processNextRequest(); // Process the next request
    }

    private void processNextRequest() {
//        List<CustomerRequest> unprocessedRequests = requestRepository.findByIsProcessedFalse();
//
//        if (unprocessedRequests.isEmpty()) return;
//
//        executorService.submit(() -> {
//            CustomerRequest request = unprocessedRequests.get(0); // Get the first unprocessed request
//            handleRequest(request); // Handle the request
//            request.setProcessed(true); // Mark as processed
//            requestRepository.save(request); // Update the request in the database
//        });
    }

//    public void handleRequest(CustomerRequest request) {
//        // Simulate handling the request
//        System.out.println("Handling request for customer: " + request.getCustomerId());
//        try {
//            // Simulate variable time taken to handle the request
//            Thread.sleep((long) (Math.random() * 10000)); // Random time between 0-10 seconds
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        System.out.println("Completed request for customer: " + request.getCustomerId());
//    }

}
