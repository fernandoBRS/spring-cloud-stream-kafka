package com.example.kafkalogging;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(TransactionStreams.class)
public class StreamsConfig {
}
