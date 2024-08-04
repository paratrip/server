package paratrip.paratrip.core.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.core.filter.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	private final RedisTemplate<String, String> blackListTemplate;
	@Value("${jwt.secret}")
	private String jwtSecret;

	@Bean
	@ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
	public WebSecurityCustomizer configureH2ConsoleEnable() {
		return web -> web.ignoring()
			.requestMatchers(PathRequest.toH2Console());
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/**").permitAll()
				.anyRequest().authenticated())
			.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtTokenFilter jwtTokenFilter() {
		List<String> permitAllEndpoints = Arrays.asList(
			// 토큰 검사가 필요 없는 경로 목록
			// Swagger
			"/swagger-ui/**",
			"/v3/api-docs/**",

			// Health Check
			"/actuator/health",

			// H2-Console
			"/h2-console/**",

			// 개방 URL
			"/**"
			// "/sms-certification/**",
			// "/member/verify-userId",
			// "/member/verify-phone",
			// "/member/verify-password",
			// "/member/verify-email",
			// "/member/reset-password",
			// "/member/join",
			// "/member/login",
			// "/member/find-email"
		);
		return new JwtTokenFilter(jwtSecret, permitAllEndpoints, blackListTemplate);
	}

}
