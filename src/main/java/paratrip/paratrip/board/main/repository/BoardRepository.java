package paratrip.paratrip.board.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.member.entity.MemberEntity;

public interface BoardRepository {
	BoardEntity saveBoardEntity(BoardEntity boardEntity);

	BoardEntity findByBoardSeq(Long boardSeq);

	BoardEntity findByCreatorMemberEntityAndBoardSeq(MemberEntity memberEntity, Long boardSeq);

	Page<BoardEntity> findAllBoardEntity(Pageable pageable);

	Page<BoardEntity> findByPopularity(Pageable pageable);

	Page<BoardEntity> findAllMyBoardEntity(MemberEntity memberEntity, Pageable pageable);

	void deleteBoardEntity(BoardEntity boardEntity);
}
