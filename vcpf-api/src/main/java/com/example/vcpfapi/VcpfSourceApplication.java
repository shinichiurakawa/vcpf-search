package com.example.vcpfapi;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@SpringBootApplication
@Configuration
@ImportResource("beans-vcpfapi-service.xml")
public class VcpfSourceApplication {

	@Component
	@ApplicationPath("/vcpf")
	public class JerseyConfig extends ResourceConfig {

		public JerseyConfig() {
			packages("com.example.resource");
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(VcpfSourceApplication.class, args);
	}
}
