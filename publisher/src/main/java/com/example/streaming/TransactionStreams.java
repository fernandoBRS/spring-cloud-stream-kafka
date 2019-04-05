package com.example.streaming;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface TransactionStreams {
    String OUTPUT = "mytopic";

    @Output(OUTPUT)
    MessageChannel output();
}
