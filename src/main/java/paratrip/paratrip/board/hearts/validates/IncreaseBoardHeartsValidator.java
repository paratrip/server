package paratrip.paratrip.board.hearts.validates;

import static paratrip.paratrip.board.hearts.controller.vo.BoardHeartsRequestVo.*;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class IncreaseBoardHeartsValidator {
	public void validate(IncreaseBoardHeartsRequest request) {
		validateMemberSeq(request.memberSeq());
		validateBoardSeq(request.boardSeq());
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
