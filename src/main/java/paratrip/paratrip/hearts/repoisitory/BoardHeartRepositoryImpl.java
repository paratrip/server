package paratrip.paratrip.hearts.repoisitory;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.core.exception.NotFoundRequestException;
import paratrip.paratrip.hearts.entity.BoardHeartEntity;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;
import paratrip.paratrip.hearts.entity.QBoardHeartEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Component
@RequiredArgsConstructor
public class BoardHeartRepositoryImpl implements BoardHeartRepository {
	private final BoardHeartJpaRepository boardHeartJpaRepository;
	private final JPAQueryFactory queryFactory;

	@Override
	public BoardHeartEntity saveBoardHeartEntity(BoardHeartEntity boardHeartEntity) {
		return boardHeartJpaRepository.save(boardHeartEntity);
	}

	@Override
	public void deleteBoardHeartEntity(BoardHeartEntity boardHeartEntity) {
		boardHeartJpaRepository.delete(boardHeartEntity);
	}

	@Override
	public BoardHeartEntity findByBoardHeartSeq(Long boardHeartSeq) {
		return boardHeartJpaRepository.findById(boardHeartSeq)
			.orElseThrow(() -> new BadRequestException(ErrorResult.BOARD_HEART_SEQ_BAD_REQUEST_EXCEPTION));
	}

	@Override
	public boolean existsByBoardEntityAndMemberEntity(BoardEntity boardEntity, MemberEntity memberEntity) {
		return boardHeartJpaRepository.existsByBoardEntityAndMemberEntity(boardEntity, memberEntity);
	}

	@Override
	public void deleteByBoardEntity(BoardEntity boardEntity) {
		boardHeartJpaRepository.deleteByBoardEntity(boardEntity);
	}

	@Override
	public BoardHeartEntity findByMemberEntityAndBoardEntity(MemberEntity memberEntity, BoardEntity boardEntity) {
		QBoardHeartEntity qBoardHeartEntity = QBoardHeartEntity.boardHeartEntity;

		return Optional.ofNullable(
			queryFactory
				.selectFrom(qBoardHeartEntity)
				.where(
					qBoardHeartEntity.boardEntity.eq(boardEntity)
						.and(qBoardHeartEntity.memberEntity.eq(memberEntity))
				).fetchOne()
		).orElseThrow(() -> new NotFoundRequestException(ErrorResult.BOARD_SCRAP_NOT_FOUND_EXCEPTION));
	}
}
