package paratrip.paratrip.board.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;
import paratrip.paratrip.member.entity.MemberEntity;

@Component
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {
	private final BoardJpaRepository boardJpaRepository;

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
}
