package com.example.streaming;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

import com.example.streaming.avro.messages.Message;
import com.example.streaming.avro.messages.headers.MessageHeader;
import com.example.streaming.avro.serializers.AvroSerializer;
import com.example.streaming.models.ValidationCompleted;
import org.springframework.http.HttpStatus;

@RestController
public class TransactionController {
    private final Publisher publisher;

    public TransactionController(Publisher publisher) {
        this.publisher = publisher;
    }
    
    @GetMapping("/test")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void test() {
        // Payload
        ValidationCompleted payload = new ValidationCompleted();
        payload.setTransactionId(UUID.randomUUID().toString());
        payload.setCorrelationId(UUID.randomUUID().toString());
        payload.setAccountFromId(UUID.randomUUID().toString());
        payload.setAccountToId(UUID.randomUUID().toString());
        payload.setAmount(120.00);

        // Header
        MessageHeader header = new MessageHeader();
        header.setTransactionId(payload.getTransactionId());
        header.setCorrelationId(payload.getCorrelationId());
        header.setEventType("ValidationCompleted");
        header.setSource("Sample");
        
        // Message
        Message<ValidationCompleted> message = new Message<>(header, payload);
        byte[] messageBytes = AvroSerializer.serialize(message);

        publisher.send(messageBytes);
    }
}
