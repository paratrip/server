package paratrip.paratrip.board.hearts.repoisitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.board.hearts.entity.BoardHeartEntity;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Repository
public interface BoardHeartJpaRepository extends JpaRepository<BoardHeartEntity, Long> {
	Boolean existsByBoardEntityAndMemberEntity(BoardEntity boardEntity, MemberEntity memberEntity);
}
