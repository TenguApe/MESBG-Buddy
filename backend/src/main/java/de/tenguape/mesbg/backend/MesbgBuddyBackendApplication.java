package de.tenguape.mesbg.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class MesbgBuddyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MesbgBuddyBackendApplication.class, args);
	}

}
