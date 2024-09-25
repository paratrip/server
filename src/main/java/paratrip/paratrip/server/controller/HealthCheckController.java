package paratrip.paratrip.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import paratrip.paratrip.core.base.BaseResponse;

@RestController
public class HealthCheckController {
	@GetMapping("/actuator/health")
	public ResponseEntity<BaseResponse> healthCheck() {
		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SERVER HEALTH"));
	}
}
