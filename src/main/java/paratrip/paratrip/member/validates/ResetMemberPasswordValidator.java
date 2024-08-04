package paratrip.paratrip.member.validates;

import static paratrip.paratrip.member.controller.vo.request.MemberRequestVo.*;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class ResetMemberPasswordValidator {
	private static final String PHONE_NUMBER_REGEX = "^010-\\d{4}-\\d{4}$";
	private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);
	private static final String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,}$";
	private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

	public void validate(ResetMemberPasswordRequest request) {
		validatePhoneNumber(request.phoneNumber());
	}

	private void validatePhoneNumber(String phoneNumber) {
		if (phoneNumber == null || phoneNumber.isEmpty()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
		if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
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
}
