package paratrip.paratrip.comment.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import paratrip.paratrip.board.main.service.dto.response.BoardResponseDto;
import paratrip.paratrip.comment.entity.CommentEntity;

@Component
public class CommentDomain {
	public List<BoardResponseDto.GetBoardResponseDto.CommentInfo> convertToCommentInfos(
		List<CommentEntity> commentEntities
	) {
		// Comment Info 생성
		return commentEntities.stream()
			.map(commentEntity -> new BoardResponseDto.GetBoardResponseDto.CommentInfo(
				commentEntity.getCommentSeq(),
				commentEntity.getComment(),
				commentEntity.getUpdatedAt(),
				commentEntity.getMemberEntity().getMemberSeq(),
				commentEntity.getMemberEntity().getUserId(),
				commentEntity.getMemberEntity().getProfileImage()
			))
			.collect(Collectors.toList());
	}
}
