package paratrip.paratrip.board.heart.repository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.heart.entity.BoardHeartEntity;
import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;
import paratrip.paratrip.member.entity.MemberEntity;

@Component
@RequiredArgsConstructor
public class BoardHeartRepositoryImpl implements BoardHeartRepository {
	private final BoardHeartJpaRepository boardHeartJpaRepository;

	@Override
	public BoardHeartEntity saveBoardHeartEntity(BoardHeartEntity boardHeartEntity) {
		return boardHeartJpaRepository.save(boardHeartEntity);
	}

	@Override
	public BoardHeartEntity findByBoardHeartSeq(Long boardHeartSeq) {
		return boardHeartJpaRepository.findByBoardHeartSeq(boardHeartSeq)
			.orElseThrow(() -> new BadRequestException(ErrorResult.BOARD_HEART_SEQ_BAD_REQUEST_EXCEPTION));
	}

	@Override
	public BoardHeartEntity findByBoardHeartSeqAndMemberEntity(Long boardHeartSeq, MemberEntity memberEntity) {
		return boardHeartJpaRepository.findByBoardHeartSeqAndMemberEntity(boardHeartSeq, memberEntity)
			.orElseThrow(() -> new BadRequestException(ErrorResult.BOARD_HEART_NOT_FOUND_EXCEPTION));
	}

	@Override
	public void deleteBoardHeartEntity(BoardHeartEntity boardHeartEntity) {
		boardHeartJpaRepository.delete(boardHeartEntity);
	}
}
