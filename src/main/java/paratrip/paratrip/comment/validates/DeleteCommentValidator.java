package paratrip.paratrip.comment.validates;

import static paratrip.paratrip.comment.controller.vo.request.CommentRequestVo.*;

import org.springframework.stereotype.Component;

import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
public class DeleteCommentValidator {
	public void validate(DeleteCommentRequest request) {
		validateMemberSeq(request.memberSeq());
		validateCommentSeq(request.commentSeq());
	}

	private void validateMemberSeq(Long memberSeq) {
		if (memberSeq == null) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}

	private void validateCommentSeq(Long commentSeq) {
		if (commentSeq == null) {
			throw new BadRequestException(ErrorResult.DTO_BAD_REQUEST_EXCEPTION);
		}
	}
}
