package paratrip.paratrip.comment.repository;

import java.util.List;

import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.comment.entity.CommentEntity;
import paratrip.paratrip.member.entity.MemberEntity;

public interface CommentRepository {
	CommentEntity saveCommentEntity(CommentEntity commentEntity);

	CommentEntity findByCommentSeq(Long commentSeq);

	CommentEntity findByCommentSeqAndMemberEntity(Long commentSeq, MemberEntity memberEntity);

	long countByBoardEntity(BoardEntity boardEntity);

	void deleteCommentEntity(CommentEntity commentEntity);

	List<CommentEntity> findByBoardEntity(BoardEntity boardEntity);

	void deleteByBoardEntity(BoardEntity boardEntity);
}
