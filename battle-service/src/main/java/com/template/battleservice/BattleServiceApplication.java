package com.template.battleservice;

import com.template.battleservice.config.BotConfigProperties;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@EnableDiscoveryClient
@EnableConfigurationProperties(BotConfigProperties.class)
@SpringBootApplication
public class BattleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattleServiceApplication.class, args);
	}

	private final DiscoveryClient discoveryClient;

	public BattleServiceApplication(DiscoveryClient discoveryClient) {
		this.discoveryClient = discoveryClient;
	}

	@PostConstruct
	public void logServices() {
		System.out.println("💡 Services in Eureka:");
		discoveryClient.getServices().forEach(service ->
				discoveryClient.getInstances(service).forEach(instance ->
						System.out.printf("➡️  %s ↳ %s%n", service, instance.getUri())
				)
		);
	}
}
