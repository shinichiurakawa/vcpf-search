package com.example.terminator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableBinding(Sink.class)
public class TerminatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerminatorApplication.class, args);
	}

	@StreamListener(Sink.INPUT)
	public void terminate(Integer session_id) {
		System.out.println("Terminator Received : " + session_id.toString());
	}

}
