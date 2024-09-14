package paratrip.paratrip.board.main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.entity.QBoardEntity;
import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;
import paratrip.paratrip.member.entity.MemberEntity;

@Component
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {
	private final BoardJpaRepository boardJpaRepository;
	private final JPAQueryFactory queryFactory;

	@Override
	public BoardEntity saveBoardEntity(BoardEntity boardEntity) {
		return boardJpaRepository.save(boardEntity);
	}

	@Override
	public BoardEntity findByBoardSeq(Long boardSeq) {
		return boardJpaRepository.findByBoardSeq(boardSeq)
			.orElseThrow(() -> new BadRequestException(ErrorResult.BOARD_SEQ_BAD_REQUEST_EXCEPTION));
	}

	@Override
	public BoardEntity findByCreatorMemberEntityAndBoardSeq(MemberEntity memberEntity, Long boardSeq) {
		return boardJpaRepository.findByCreatorMemberEntityAndBoardSeq(memberEntity, boardSeq)
			.orElseThrow(() -> new BadRequestException(ErrorResult.BOARD_NOT_CREATED_BY_MEMBER_BAD_REQUEST_EXCEPTION));
	}

	@Override
	public Page<BoardEntity> findAllBoardEntity(Pageable pageable) {
		return boardJpaRepository.findAllByOrderByUpdatedAtDesc(pageable);
	}

	@Override
	public Page<BoardEntity> findByPopularity(Pageable pageable) {
		QBoardEntity board = QBoardEntity.boardEntity;

		List<BoardEntity> result = queryFactory
			.selectFrom(board)
			.orderBy(board.hearts.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 전체 개수 계산
		long total = queryFactory
			.select(board.count())
			.from(board)
			.fetchOne();

		return new PageImpl<>(result, pageable, total);
	}

	@Override
	public Page<BoardEntity> findAllMyBoardEntity(MemberEntity memberEntity, Pageable pageable) {
		QBoardEntity board = QBoardEntity.boardEntity;

		List<BoardEntity> result = queryFactory
			.selectFrom(board)
			.where(
				board.creatorMemberEntity.eq(memberEntity)
			)
			.orderBy(board.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 전체 개수 계산
		long total = queryFactory
			.select(board.count())
			.from(board)
			.fetchOne();

		return new PageImpl<>(result, pageable, total);
	}
}
