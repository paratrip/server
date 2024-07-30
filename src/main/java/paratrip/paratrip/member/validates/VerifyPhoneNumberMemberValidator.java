package paratrip.paratrip.member.validates;

import static paratrip.paratrip.member.vo.request.MemberRequestVo.*;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class VerifyPhoneNumberMemberValidator {
	private static final String PHONE_NUMBER_REGEX = "^010-\\d{4}-\\d{4}$";
	private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);

	public void validate(VerifyPhoneNumberMemberRequest request) {
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
}
