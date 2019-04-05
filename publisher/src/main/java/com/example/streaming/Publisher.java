package com.example.streaming;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class Publisher {
    private final TransactionStreams transactionStreams;

    public Publisher(TransactionStreams transactionStreams) {
        this.transactionStreams = transactionStreams;
    }

    public void send(final byte[] messageBytes) {
        MessageChannel messageChannel = transactionStreams.output();

        messageChannel.send(MessageBuilder
            .withPayload(messageBytes)
            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.TEXT_PLAIN)
            .build());
    }
}
