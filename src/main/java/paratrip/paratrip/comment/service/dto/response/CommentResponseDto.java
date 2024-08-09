package paratrip.paratrip.comment.service.dto.response;

import static paratrip.paratrip.comment.controller.vo.response.CommentResponseVo.*;

public class CommentResponseDto {
	public record AddCommentResponseDto(
		Long commentSeq
	) {
		public AddCommentResponseVo toAddCommentResponseVo() {
			return new AddCommentResponseVo(
				this.commentSeq
			);
		}
	}
}
