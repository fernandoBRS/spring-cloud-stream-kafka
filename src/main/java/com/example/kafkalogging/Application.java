package com.example.kafkalogging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

@SpringBootApplication
@EnableBinding(Sink.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	// Receive incoming messages of type Person
	// It tries to automatically convert incoming message payloads to type Person
	@StreamListener(TransactionStreams.INPUT)
      public void process(Message<byte[]> message) {
		try {
			System.out.println("Received: " + message.getPayload());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		commit(message);
	}
	
	private void commit(Message<byte[]> message) {
		Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
		
		if (acknowledgment != null) {
           System.out.println("Acknowledgment provided");
           acknowledgment.acknowledge();
        }
	}
}
