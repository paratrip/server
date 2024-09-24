package paratrip.paratrip;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "https://euics.co.kr", description = "관광데이터 공모전 SERVER URI")})
public class ParatripApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParatripApplication.class, args);
	}

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
}
