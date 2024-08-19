package paratrip.paratrip.member.validates;

import static paratrip.paratrip.member.controller.vo.request.MemberRequestVo.*;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;
import paratrip.paratrip.member.util.Gender;

@Component
public class ModifyMemberValidator {
	public void validate(ModifyMemberRequest request) {
		validateMemberSeq(request.memberSeq());
		validateUserId(request.userId());
		validateBirth(request.birth());
		validateGender(request.gender());
	}

	private void validateMemberSeq(Long memberSeq) {
		if (memberSeq == null) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}

	private void validateUserId(String userId) {
		if (userId == null || userId.isEmpty()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}

	private void validateBirth(String birth) {
		if (birth == null || birth.isEmpty()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}

	private void validateGender(Gender gender) {
		if (gender == null) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}
}
