package paratrip.paratrip.hearts.repoisitory;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.hearts.entity.BoardHeartEntity;
import paratrip.paratrip.board.main.entity.BoardEntity;
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
}
