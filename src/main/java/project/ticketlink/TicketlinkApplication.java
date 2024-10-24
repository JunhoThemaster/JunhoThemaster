package project.ticketlink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "project.ticketlink")
@EnableScheduling
public class TicketlinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketlinkApplication.class, args);
	}

}
