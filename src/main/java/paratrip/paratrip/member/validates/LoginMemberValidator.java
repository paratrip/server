package paratrip.paratrip.member.validates;

import static paratrip.paratrip.member.controller.vo.request.MemberRequestVo.*;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class LoginMemberValidator {
	public void validate(LoginMemberRequest request) {
		validateEmail(request.email());
		validatePassword(request.password());
	}

	private void validateEmail(String email) {
		if (email == null || email.isEmpty()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}

	private void validatePassword(String password) {
		if (password == null || password.isEmpty()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}
}
