package paratrip.paratrip.member.validates;

import static paratrip.paratrip.member.vo.request.MemberRequestVo.*;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class VerifyPasswordMemberValidator {
	private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
	private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

	public void validate(VerifyPasswordMemberRequest request) {
		validatePassword(request.password());
	}

	private void validatePassword(String password) {
		if (password == null || password.isEmpty()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
		if (!PASSWORD_PATTERN.matcher(password).matches()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}
}
