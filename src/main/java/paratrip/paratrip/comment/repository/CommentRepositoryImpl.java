package paratrip.paratrip.comment.repository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.comment.entity.CommentEntity;
import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;
import paratrip.paratrip.member.entity.MemberEntity;

@Component
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
	private final CommentJpaRepository commentJpaRepository;

	@Override
	public CommentEntity saveCommentEntity(CommentEntity commentEntity) {
		return commentJpaRepository.save(commentEntity);
	}

	@Override
	public CommentEntity findByCommentSeq(Long commentSeq) {
		return commentJpaRepository.findByCommentSeq(commentSeq)
			.orElseThrow(() -> new BadRequestException(ErrorResult.COMMENT_SEQ_BAD_REQUEST_EXCEPTION));
	}

	@Override
	public CommentEntity findByCommentSeqAndMemberEntity(Long commentSeq, MemberEntity memberEntity) {
		return commentJpaRepository.findByCommentSeqAndMemberEntity(commentSeq, memberEntity)
			.orElseThrow(() -> new BadRequestException(ErrorResult.COMMENT_NOT_CREATED_BY_MEMBER_BAD_REQUEST_EXCEPTION));
	}

	@Override
	public long countByBoardEntity(BoardEntity boardEntity) {
		return commentJpaRepository.countByBoardEntity(boardEntity);
	}
}
