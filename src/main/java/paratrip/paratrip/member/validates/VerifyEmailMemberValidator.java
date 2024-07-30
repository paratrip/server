package paratrip.paratrip.member.validates;

import static paratrip.paratrip.member.vo.request.MemberRequestVo.*;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class VerifyEmailMemberValidator {
	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	public void validate(VerifyEmailMemberRequest request) {
		validateEmail(request.email());
	}

	private void validateEmail(String email) {
		if (email == null || email.isEmpty()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
		if (!EMAIL_PATTERN.matcher(email).matches()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}
}
