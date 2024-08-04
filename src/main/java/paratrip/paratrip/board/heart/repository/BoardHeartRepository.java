package paratrip.paratrip.board.heart.repository;

import paratrip.paratrip.board.heart.entity.BoardHeartEntity;
import paratrip.paratrip.member.entity.MemberEntity;

public interface BoardHeartRepository {
	BoardHeartEntity saveBoardHeartEntity(BoardHeartEntity boardHeartEntity);
	BoardHeartEntity findByBoardHeartSeq(Long boardHeartSeq);
	BoardHeartEntity findByBoardHeartSeqAndMemberEntity(Long boardHeartSeq, MemberEntity memberEntity);
	void deleteBoardHeartEntity(BoardHeartEntity boardHeartEntity);
}
