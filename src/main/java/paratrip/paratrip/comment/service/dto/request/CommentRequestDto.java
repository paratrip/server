package paratrip.paratrip.comment.service.dto.request;

public class CommentRequestDto {
	public record AddCommentRequestDto(
		Long memberSeq,
		Long boardSeq,
		String comment
	) {

	}

	public record ModifyCommentRequestDto(
		Long memberSeq,
		Long commentSeq,
		String comment
	) {

	}
}
