package paratrip.paratrip.board.main.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Repository
public interface BoardJpaRepository extends JpaRepository<BoardEntity, Long> {
	Optional<BoardEntity> findByBoardSeq(Long boardSeq);

	Optional<BoardEntity> findByCreatorMemberEntityAndBoardSeq(MemberEntity memberEntity, Long boardSeq);

	Page<BoardEntity> findAllByOrderByUpdatedAtDesc(Pageable pageable);
}
