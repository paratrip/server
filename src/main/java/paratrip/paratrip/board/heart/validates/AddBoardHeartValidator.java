package paratrip.paratrip.board.heart.validates;

import static paratrip.paratrip.board.heart.controller.vo.request.BoardHeartRequestVo.*;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class AddBoardHeartValidator {
	public void validate(AddBoardHeartRequest request) {
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
