package dev.enrico.tutorial;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import dev.enrico.tutorial.run.Location;
import dev.enrico.tutorial.run.Run;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		// ConfigurableApplicationContext context =
		// SpringApplication.run(Application.class, args);

		// log.info("Application started successfully");

		// WelcomeMessage welcomeMessage = (WelcomeMessage)
		// context.getBean("welcomeMessage");
		// log.info(welcomeMessage.getWelcomeMessage());
		// System.out.println(welcomeMessage);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			Run run = new Run(1, "First run", LocalDateTime.now(), LocalDateTime.now().plus(1, ChronoUnit.HOURS), 5,
					Location.OUTDOOR);
			log.info("Run: " + run);
		};
	}

}
