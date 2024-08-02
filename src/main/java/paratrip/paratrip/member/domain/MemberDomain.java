package paratrip.paratrip.member.domain;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;
import paratrip.paratrip.core.exception.UnAuthorizedException;

@Component
@RequiredArgsConstructor
public class MemberDomain {
	private final RedisTemplate<String, String> redisTemplate;
	private final BCryptPasswordEncoder encoder;
	@Value("${jwt.secret}")
	private String jwtSecret;
	@Value("${jwt.accessTokenExpiration}")
	private long accessTokenExpiration;
	@Value("${jwt.refreshTokenExpiration}")
	private long refreshTokenExpiration;

	public String generateAccessToken(String email) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + accessTokenExpiration);

		return Jwts.builder()
			.setSubject(email)
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(SignatureAlgorithm.HS512, jwtSecret)
			.compact();
	}

	public String generateRefreshToken(String email) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + refreshTokenExpiration);

		return Jwts.builder()
			.setSubject(email)
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.signWith(SignatureAlgorithm.HS512, jwtSecret)
			.compact();
	}

	public void saveRefreshToken(String refreshToken, String email) {
		redisTemplate.opsForValue()
			.set(
				email,
				refreshToken,
				refreshTokenExpiration,
				TimeUnit.MILLISECONDS
			);
	}

	public void deleteRefreshToken(String email) {
		redisTemplate.delete(email);
	}

	public void checkPassword(String password, String encodedPassword) {
		if (!encoder.matches(password, encodedPassword)) {
			throw new BadRequestException(ErrorResult.PASSWORD_BAD_REQUEST_EXCEPTION);
		}
	}

	public void checkRefreshToken(String refreshToken) {
		try {
			Jws<Claims> claimsJws = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(refreshToken);
			Claims claims = claimsJws.getBody();

			// RefreshToken 에서 email 추출
			String email = claims.getSubject();

			// email 로 refreshToken 값 확인
			String storedRefreshToken = redisTemplate.opsForValue().get(email);
			if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
				redisTemplate.delete(email);
				throw new UnAuthorizedException(ErrorResult.REFRESH_TOKEN_UNAUTHORIZED_EXCEPTION);
			}
		} catch (JwtException e) {
			throw new UnAuthorizedException(ErrorResult.REFRESH_TOKEN_UNAUTHORIZED_EXCEPTION);
		}
	}
}
