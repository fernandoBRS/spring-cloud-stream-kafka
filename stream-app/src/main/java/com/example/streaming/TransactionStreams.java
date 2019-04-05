package com.example.streaming;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;  

public interface TransactionStreams {
    String INPUT = "mytopic";

    @Input(INPUT)
    SubscribableChannel input();
}
