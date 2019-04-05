package com.example.streaming;

import java.util.Optional;

import com.example.streaming.avro.messages.headers.HeaderConfig;
import com.example.streaming.avro.messages.headers.Headers;
import com.example.streaming.avro.serializers.AvroSerializer;
import com.example.streaming.models.ValidationCompleted;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@SpringBootApplication
@EnableBinding(Sink.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	// Receive incoming messages
	@StreamListener(TransactionStreams.INPUT)
      public void process(Message<byte[]> message) {
		try {
			Headers headers = AvroSerializer.deserializeHeaders(message.getPayload());
			Optional<String> transactionId = headers.get(HeaderConfig.TransactionId);
			
			System.out.println("Received transaction id: " + transactionId.get());

			// ValidationCompleted event = AvroSerializer.deserializePayload(message.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		commit(message);
	}
	
	private void commit(Message<byte[]> message) {
		MessageHeaders test = message.getHeaders();
		Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
		
		if (acknowledgment != null) {
           System.out.println("Acknowledgment provided");
           acknowledgment.acknowledge();
        }
	}
}
