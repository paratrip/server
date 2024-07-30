package paratrip.paratrip.member.validates;

import static paratrip.paratrip.member.vo.request.MemberRequestVo.*;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class JoinMemberValidator {
	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	private static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,}$";
	private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

	public void validate(JoinMemberRequest request) {
		validateEmail(request.email());
		validatePassword(request.password());
		validateUserId(request.userId());
	}

	private void validateEmail(String email) {
		if (email == null || email.isEmpty()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
		if (!EMAIL_PATTERN.matcher(email).matches()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}

	private void validatePassword(String password) {
		if (password == null || password.isEmpty()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
		if (!PASSWORD_PATTERN.matcher(password).matches()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}

	private void validateUserId(String userId) {
		if (userId == null || userId.isEmpty()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}
}
