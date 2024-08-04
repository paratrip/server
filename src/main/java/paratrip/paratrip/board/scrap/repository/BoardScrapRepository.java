package paratrip.paratrip.board.scrap.repository;

import paratrip.paratrip.board.scrap.entity.BoardScrapEntity;
import paratrip.paratrip.member.entity.MemberEntity;

public interface BoardScrapRepository {
	BoardScrapEntity saveBoardScrapEntity(BoardScrapEntity boardScrapEntity);
	BoardScrapEntity findByBoardScrapSeq(Long boardScrapSeq);
	BoardScrapEntity findByMemberEntityAndBoardScrapSeq(MemberEntity memberEntity, Long boardScrapSeq);
	void deleteBoardScrapEntity(BoardScrapEntity boardScrapEntity);
}
