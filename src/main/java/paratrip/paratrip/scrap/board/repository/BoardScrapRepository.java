package paratrip.paratrip.scrap.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.scrap.board.entity.BoardScrapEntity;
import paratrip.paratrip.member.entity.MemberEntity;

public interface BoardScrapRepository {
	BoardScrapEntity saveBoardScrapEntity(BoardScrapEntity boardScrapEntity);

	BoardScrapEntity findByBoardScrapSeq(Long boardScrapSeq);

	BoardScrapEntity findByMemberEntityAndBoardScrapSeq(MemberEntity memberEntity, Long boardScrapSeq);

	void deleteBoardScrapEntity(BoardScrapEntity boardScrapEntity);

	long countByBoardEntity(BoardEntity boardEntity);

	void duplicateBoardScrap(MemberEntity memberEntity, BoardEntity boardEntity);

	boolean existsByBoardEntityAndMemberEntity(MemberEntity memberEntity, BoardEntity boardEntity);

	Page<BoardScrapEntity> findAllByMemberEntity(MemberEntity memberEntity, Pageable pageable);
}
