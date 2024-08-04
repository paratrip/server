package paratrip.paratrip.member.validates;

import static paratrip.paratrip.member.controller.vo.request.MemberRequestVo.*;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class VerifyUserIdMemberValidator {
	public void validate(VerifyUserIdMemberRequest request) {
		validateUserId(request.userId());
	}

	private void validateUserId(String userId) {
		if (userId == null || userId.isEmpty()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}
}
