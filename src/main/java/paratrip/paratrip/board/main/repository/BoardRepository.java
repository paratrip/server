package paratrip.paratrip.board.main.repository;

import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.member.entity.MemberEntity;

public interface BoardRepository {
	BoardEntity saveBoardEntity(BoardEntity boardEntity);
	BoardEntity findByBoardSeq(Long boardSeq);
	BoardEntity findByMemberEntity(MemberEntity memberEntity);
}
