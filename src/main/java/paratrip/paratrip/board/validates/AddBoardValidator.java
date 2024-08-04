package paratrip.paratrip.board.validates;

import static paratrip.paratrip.board.controller.vo.request.BoardRequestVo.*;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class AddBoardValidator {
	public void validate(AddBoardRequest request) {
		validateMemberSeq(request.memberSeq());
		validateTitle(request.title());
		validateContent(request.content());
		validateImages(request.images());
	}

	private void validateMemberSeq(Long memberSeq) {
		if (memberSeq == null) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}

	private void validateTitle(String title) {
		if (title == null || title.isEmpty()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}

	private void validateContent(String content) {
		if (content == null || content.isEmpty()) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}

	private void validateImages(List<MultipartFile> images) {
		if (images != null && images.size() > 10) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}
}
