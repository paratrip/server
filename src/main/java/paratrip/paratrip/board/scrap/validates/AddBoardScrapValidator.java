package paratrip.paratrip.board.scrap.validates;

import static paratrip.paratrip.board.scrap.controller.vo.request.BoardScrapRequestVo.*;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class AddBoardScrapValidator {
	public void validate(AddBoardScrapRequest request) {
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
