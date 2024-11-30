package com.hexplosif.optimod;

import com.hexplosif.optimod.controller.OptimodController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class OptimodApplication implements CommandLineRunner {

	@Autowired
	private CustomProperties customProperties;

	@Autowired
	private OptimodController optimodController;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(OptimodApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("API URL: " + customProperties.getApiUrl());

		// Appeler la méthode sur le contrôleur géré par Spring
		optimodController.loadNode("petitPlan.xml");
		optimodController.loadSegment("petitPlan.xml");
		optimodController.loadDeliveryRequest("demandePetit1.xml");
	}
}