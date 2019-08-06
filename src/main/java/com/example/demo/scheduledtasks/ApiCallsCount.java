package com.example.demo.scheduledtasks;

import com.example.demo.commonfunctions.SharedVariables;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ApiCallsCount {

    @Scheduled(fixedRate = 60000)
    public static void resetApiCallsCount() {
        SharedVariables.setCount(0);
    }
}
