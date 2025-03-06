package ru.service.utilities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UtilitiesServiceByUndefinedBehaviorTeamApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtilitiesServiceByUndefinedBehaviorTeamApplication.class, args);
	}

}
