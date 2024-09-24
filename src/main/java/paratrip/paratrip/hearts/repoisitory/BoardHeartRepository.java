package paratrip.paratrip.hearts.repoisitory;

import paratrip.paratrip.hearts.entity.BoardHeartEntity;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.member.entity.MemberEntity;

public interface BoardHeartRepository {
	BoardHeartEntity saveBoardHeartEntity(BoardHeartEntity boardHeartEntity);

	void deleteBoardHeartEntity(BoardHeartEntity boardHeartEntity);

	BoardHeartEntity findByBoardHeartSeq(Long boardHeartSeq);

	boolean existsByBoardEntityAndMemberEntity(BoardEntity boardEntity, MemberEntity memberEntity);
}
