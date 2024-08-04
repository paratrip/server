package paratrip.paratrip.board.heart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.board.heart.entity.BoardHeartEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Repository
public interface BoardHeartJpaRepository extends JpaRepository<BoardHeartEntity, Long> {
	Optional<BoardHeartEntity> findByBoardHeartSeq(Long boardHeartSeq);

	Optional<BoardHeartEntity> findByBoardHeartSeqAndMemberEntity(Long boardHeartSeq, MemberEntity memberEntity);
}
