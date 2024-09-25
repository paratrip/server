package paratrip.paratrip.comment.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.service.dto.response.BoardResponseDto;
import paratrip.paratrip.comment.entity.CommentEntity;
import paratrip.paratrip.core.utils.LocalDateTimeConverter;

@Component
@RequiredArgsConstructor
public class CommentDomain {
	private final LocalDateTimeConverter converter;

	public List<BoardResponseDto.GetBoardResponseDto.CommentInfo> convertToCommentInfos(
		List<CommentEntity> commentEntities
	) {
		// Comment Info 생성
		return commentEntities.stream()
			.map(commentEntity -> new BoardResponseDto.GetBoardResponseDto.CommentInfo(
				commentEntity.getCommentSeq(),
				commentEntity.getComment(),
				converter.convertToKoreanTime(commentEntity.getUpdatedAt()),
				commentEntity.getMemberEntity().getMemberSeq(),
				commentEntity.getMemberEntity().getUserId(),
				commentEntity.getMemberEntity().getProfileImage()
			))
			.collect(Collectors.toList());
	}
}
