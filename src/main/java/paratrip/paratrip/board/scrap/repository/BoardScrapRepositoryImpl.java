package paratrip.paratrip.board.scrap.repository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.scrap.entity.BoardScrapEntity;
import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ConflictException;
import paratrip.paratrip.core.exception.ErrorResult;
import paratrip.paratrip.member.entity.MemberEntity;

@Component
@RequiredArgsConstructor
public class BoardScrapRepositoryImpl implements BoardScrapRepository {
	private final BoardScrapJpaRepository boardScrapJpaRepository;

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
}
