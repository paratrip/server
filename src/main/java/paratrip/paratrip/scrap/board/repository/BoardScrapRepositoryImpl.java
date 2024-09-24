package paratrip.paratrip.scrap.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.scrap.board.entity.BoardScrapEntity;
import paratrip.paratrip.scrap.board.entity.QBoardScrapEntity;
import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ConflictException;
import paratrip.paratrip.core.exception.ErrorResult;
import paratrip.paratrip.member.entity.MemberEntity;

@Component
@RequiredArgsConstructor
public class BoardScrapRepositoryImpl implements BoardScrapRepository {
	private final BoardScrapJpaRepository boardScrapJpaRepository;
	private final JPAQueryFactory queryFactory;

	@Override
	public BoardScrapEntity saveBoardScrapEntity(BoardScrapEntity boardScrapEntity) {
		return boardScrapJpaRepository.save(boardScrapEntity);
	}

	@Override
	public BoardScrapEntity findByBoardScrapSeq(Long boardScrapSeq) {
		return boardScrapJpaRepository.findByBoardScrapSeq(boardScrapSeq)
			.orElseThrow(() -> new BadRequestException(ErrorResult.BOARD_SCRAP_SEQ_BAD_REQUEST_EXCEPTION));
	}

	@Override
	public BoardScrapEntity findByMemberEntityAndBoardScrapSeq(MemberEntity memberEntity, Long boardScrapSeq) {
		return boardScrapJpaRepository.findByMemberEntityAndBoardScrapSeq(memberEntity, boardScrapSeq)
			.orElseThrow(() -> new BadRequestException(ErrorResult.BOARDS_SCRAP_NOT_FOUND_EXCEPTION));
	}

	@Override
	public void deleteBoardScrapEntity(BoardScrapEntity boardScrapEntity) {
		boardScrapJpaRepository.delete(boardScrapEntity);
	}

	@Override
	public long countByBoardEntity(BoardEntity boardEntity) {
		return boardScrapJpaRepository.countByBoardEntity(boardEntity);
	}

	@Override
	public void duplicateBoardScrap(MemberEntity memberEntity, BoardEntity boardEntity) {
		boardScrapJpaRepository.findByMemberEntityAndBoardEntity(memberEntity, boardEntity)
			.ifPresent(scrap -> {
				throw new ConflictException(ErrorResult.SCRAP_BOARD_DUPLICATION_CONFLICT_EXCEPTION);
			});
	}

	@Override
	public boolean existsByBoardEntityAndMemberEntity(MemberEntity memberEntity, BoardEntity boardEntity) {
		return boardScrapJpaRepository.existsByBoardEntityAndMemberEntity(boardEntity, memberEntity);
	}

	@Override
	public Page<BoardScrapEntity> findAllByMemberEntity(MemberEntity memberEntity, Pageable pageable) {
		QBoardScrapEntity boardScrap = QBoardScrapEntity.boardScrapEntity;

		List<BoardScrapEntity> results = queryFactory
			.selectFrom(boardScrap)
			.where(
				boardScrap.memberEntity.eq(memberEntity)
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long total = queryFactory
			.select(boardScrap.count())
			.from(boardScrap)
			.fetchOne();

		return new PageImpl<>(results, pageable, total);
	}

	@Override
	public void deleteByBoardEntity(BoardEntity boardEntity) {
		boardScrapJpaRepository.deleteByBoardEntity(boardEntity);
	}

}
