package paratrip.paratrip.board.main.validates;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class GetAllBoardValidator {
	public void validate(Long memberSeq) {
		validateMemberSeq(memberSeq);
	}

	private void validateMemberSeq(Long memberSeq) {
		if (memberSeq == null) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}
}
