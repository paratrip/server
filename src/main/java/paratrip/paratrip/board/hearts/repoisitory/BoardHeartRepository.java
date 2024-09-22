package paratrip.paratrip.board.hearts.repoisitory;

import paratrip.paratrip.board.hearts.entity.BoardHeartEntity;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.member.entity.MemberEntity;

public interface BoardHeartRepository {
	BoardHeartEntity saveBoardHeartEntity(BoardHeartEntity boardHeartEntity);

	void deleteBoardHeartEntity(BoardHeartEntity boardHeartEntity);

	BoardHeartEntity findByBoardHeartSeq(Long boardHeartSeq);

	boolean existsByBoardEntityAndMemberEntity(BoardEntity boardEntity, MemberEntity memberEntity);
}
