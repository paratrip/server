package paratrip.paratrip.comment.service;

import static paratrip.paratrip.comment.service.dto.request.CommentRequestDto.*;
import static paratrip.paratrip.comment.service.dto.response.CommentResponseDto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.alarm.mapper.AlarmMapper;
import paratrip.paratrip.alarm.repository.AlarmRepository;
import paratrip.paratrip.alarm.utils.Type;
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
	private final AlarmRepository alarmRepository;

	private final CommentMapper commentMapper;
	private final AlarmMapper alarmMapper;

	@Transactional
	public AddCommentResponseDto addComment(AddCommentRequestDto addCommentRequestDto) {
		/*
		 1. Member 유효성 검사
		 2. Board 유효성 감사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(addCommentRequestDto.memberSeq());
		BoardEntity boardEntity = boardRepository.findByBoardSeq(addCommentRequestDto.boardSeq());

		/*
		 1. CommentEntity 저장
		 2. AlarmEntity 저장
		*/
		CommentEntity commentEntity = commentRepository.saveCommentEntity(
			commentMapper.toCommentEntity(boardEntity, memberEntity, addCommentRequestDto.comment())
		);
		alarmRepository.saveAlarmEntity(alarmMapper.toAlarmEntity(boardEntity, memberEntity, Type.COMMENT));

		return new AddCommentResponseDto(commentEntity.getCommentSeq());
	}

	@Transactional
	public void modifyComment(ModifyCommentRequestDto modifyCommentRequestDto) {
		/*
		 1. Member 유효성 검사
		 2. Comment 유효성 검사
		 3. Comment 작성자 오류
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(modifyCommentRequestDto.memberSeq());
		commentRepository.findByCommentSeq(modifyCommentRequestDto.commentSeq());
		CommentEntity commentEntity
			= commentRepository.findByCommentSeqAndMemberEntity(modifyCommentRequestDto.commentSeq(), memberEntity);

		// Comment 업데이트
		CommentEntity newCommentEntity = commentEntity.updateCommentEntity(modifyCommentRequestDto.comment());

		// 저장
		commentRepository.saveCommentEntity(newCommentEntity);
	}

	@Transactional
	public void deleteComment(DeleteCommentRequestDto deleteCommentRequestDto) {
		/*
		 1. Member 유효성 검사
		 2. Comment 유효성 검사
		 3. Comment 작성자 오류
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(deleteCommentRequestDto.memberSeq());
		commentRepository.findByCommentSeq(deleteCommentRequestDto.commentSeq());
		CommentEntity commentEntity
			= commentRepository.findByCommentSeqAndMemberEntity(deleteCommentRequestDto.commentSeq(), memberEntity);

		commentRepository.deleteCommentEntity(commentEntity);
	}
}
