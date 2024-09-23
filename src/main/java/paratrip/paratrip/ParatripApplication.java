package paratrip.paratrip;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class ParatripApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParatripApplication.class, args);
	}
}
