package com.hexplosif.optimod;

import com.hexplosif.controller.OptimodController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class OptimodApplication {

	public static void main(String[] args) {

		SpringApplication.run(OptimodApplication.class, args);

		OptimodController controller = new OptimodController();

		// Call the function
		controller.loadDeliveryRequest("demandePetit2.xml");

	}
}
