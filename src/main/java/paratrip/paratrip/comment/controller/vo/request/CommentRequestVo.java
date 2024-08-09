package paratrip.paratrip.comment.controller.vo.request;

import static paratrip.paratrip.comment.service.dto.request.CommentRequestDto.*;

public class CommentRequestVo {
	public record AddCommentRequest(
		Long memberSeq,
		Long boardSeq,
		String comment
	) {
		public AddCommentRequestDto toAddCommentRequestDto() {
			return new AddCommentRequestDto(
				this.memberSeq,
				this.boardSeq,
				this.comment
			);
		}
	}
}
