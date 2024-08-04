package paratrip.paratrip.board.heart.validates;

import static paratrip.paratrip.board.heart.controller.vo.request.BoardHeartRequestVo.*;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class DeleteBoardHeartValidator {
	public void validate(DeleteBoardHeartRequest request) {
		validateMemberSeq(request.memberSeq());
		validateBoardHeartSeq(request.boardHeartSeq());
	}

	private void validateMemberSeq(Long memberSeq) {
		if (memberSeq == null) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}

	private void validateBoardHeartSeq(Long boardHeartSeq) {
		if (boardHeartSeq == null) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}
}
