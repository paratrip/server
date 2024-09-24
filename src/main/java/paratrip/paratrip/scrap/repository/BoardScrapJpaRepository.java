package paratrip.paratrip.scrap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.scrap.entity.BoardScrapEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Repository
public interface BoardScrapJpaRepository extends JpaRepository<BoardScrapEntity, Long> {
	Optional<BoardScrapEntity> findByBoardScrapSeq(Long boardScrapSeq);

	Optional<BoardScrapEntity> findByMemberEntityAndBoardScrapSeq(MemberEntity memberEntity, Long boardScrapSeq);

	long countByBoardEntity(BoardEntity boardEntity);

	Optional<BoardScrapEntity> findByMemberEntityAndBoardEntity(MemberEntity memberEntity, BoardEntity boardEntity);

	Boolean existsByBoardEntityAndMemberEntity(BoardEntity boardEntity, MemberEntity memberEntity);
}
