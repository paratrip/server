package paratrip.paratrip.board.scrap.validates;

import static paratrip.paratrip.board.scrap.controller.vo.request.BoardScrapRequestVo.*;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class DeleteBoardScrapValidator {
	public void validate(DeleteBoardScrapRequest request) {
		validateMemberSeq(request.memberSeq());
		validateBoardScarpSeq(request.boardScrapSeq());
	}

	private void validateMemberSeq(Long memberSeq) {
		if (memberSeq == null) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}

	private void validateBoardScarpSeq(Long boardScarpSeq) {
		if (boardScarpSeq == null) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}
}
