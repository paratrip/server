package paratrip.paratrip.board.repository;

import paratrip.paratrip.board.entity.BoardEntity;
import paratrip.paratrip.member.entity.MemberEntity;

public interface BoardRepository {
	BoardEntity saveBoardEntity(BoardEntity boardEntity);
	BoardEntity findByBoardSeq(Long boardSeq);
	BoardEntity findByMemberEntity(MemberEntity memberEntity);
}
