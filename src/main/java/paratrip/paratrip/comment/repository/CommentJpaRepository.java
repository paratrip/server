package paratrip.paratrip.comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.comment.entity.CommentEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Repository
public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {
	Optional<CommentEntity> findByCommentSeq(Long commentSeq);

	Optional<CommentEntity> findByCommentSeqAndMemberEntity(Long commentSeq, MemberEntity memberEntity);

	long countByBoardEntity(BoardEntity boardEntity);

	List<CommentEntity> findByBoardEntity(BoardEntity boardEntity);

	void deleteByBoardEntity(BoardEntity boardEntity);
}
