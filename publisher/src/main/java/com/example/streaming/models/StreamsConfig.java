package com.example.streaming.models;

import com.example.streaming.TransactionStreams;

import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(TransactionStreams.class)
public class StreamsConfig {
}
