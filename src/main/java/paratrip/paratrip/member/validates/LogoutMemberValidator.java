package paratrip.paratrip.member.validates;

import static paratrip.paratrip.member.vo.request.MemberRequestVo.*;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class LogoutMemberValidator {
	public void validate(LogoutMemberRequest request) {
		validateMemberSeq(request.memberSeq());
	}

	private void validateMemberSeq(Long memberSeq) {
		if (memberSeq == null) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}
}
