package paratrip.paratrip.board.main.validates;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class GetBoardValidator {
	public void validate(Long memberSeq, Long boardSeq) {
		validateMemberSeq(memberSeq);
		validateBoardSeq(boardSeq);
	}

	private void validateMemberSeq(Long memberSeq) {
		if (memberSeq == null) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}

	private void validateBoardSeq(Long boardSeq) {
		if (boardSeq == null) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}
}
