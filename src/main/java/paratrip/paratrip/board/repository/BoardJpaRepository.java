package paratrip.paratrip.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.board.entity.BoardEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Repository
public interface BoardJpaRepository extends JpaRepository<BoardEntity, Long> {
	Optional<BoardEntity> findByBoardSeq(Long boardSeq);

	Optional<BoardEntity> findByMemberEntity(MemberEntity memberEntity);
}
