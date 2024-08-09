package paratrip.paratrip.comment.service;

import static paratrip.paratrip.comment.service.dto.request.CommentRequestDto.*;
import static paratrip.paratrip.comment.service.dto.response.CommentResponseDto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.repository.BoardRepository;
import paratrip.paratrip.comment.entity.CommentEntity;
import paratrip.paratrip.comment.mapper.CommentMapper;
import paratrip.paratrip.comment.repository.CommentRepository;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	private final CommentRepository commentRepository;

	private final CommentMapper commentMapper;

	@Transactional
	public AddCommentResponseDto addComment(AddCommentRequestDto addCommentRequestDto) {
		/*
		 1. Member 유효성 검사
		 2. Board 유효성 감사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(addCommentRequestDto.memberSeq());
		BoardEntity boardEntity = boardRepository.findByBoardSeq(addCommentRequestDto.boardSeq());

		// 저장
		CommentEntity commentEntity = commentRepository.saveCommentEntity(
			commentMapper.toCommentEntity(boardEntity, memberEntity, addCommentRequestDto.comment())
		);

		return new AddCommentResponseDto(commentEntity.getCommentSeq());
	}
}