package paratrip.paratrip.board.service;

import static paratrip.paratrip.board.service.dto.request.BoardRequestDto.*;
import static paratrip.paratrip.board.service.dto.response.BoardResponseDto.*;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.domain.BoardDomain;
import paratrip.paratrip.board.entity.BoardEntity;
import paratrip.paratrip.board.mapper.BoardImageMapper;
import paratrip.paratrip.board.mapper.BoardMapper;
import paratrip.paratrip.board.repository.BoardImageRepository;
import paratrip.paratrip.board.repository.BoardRepository;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.member.repository.MemberRepository;
import paratrip.paratrip.s3.domain.S3Domain;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	private final BoardImageRepository boardImageRepository;

	private final S3Domain s3Domain;
	private final BoardDomain boardDomain;

	private final BoardMapper boardMapper;
	private final BoardImageMapper boardImageMapper;

	@Transactional
	public AddBoardResponseDto saveBoard(AddBoardRequestDto addBoardRequestDto) throws IOException {
		/*
		 1. MemberSeq 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(addBoardRequestDto.memberSeq());

		// Board Entity 저장
		BoardEntity boardEntity = boardRepository.saveBoardEntity(boardMapper.toBoardEntity(
			addBoardRequestDto.title(),
			addBoardRequestDto.content(),
			addBoardRequestDto.location(),
			memberEntity
		));

		// Board Image Entity 저장
		if (boardDomain.checkImages(addBoardRequestDto.images())) {
			for (MultipartFile multipartFile : addBoardRequestDto.images()) {
				String imageURL = s3Domain.uploadMultipartFile(multipartFile);
				boardImageRepository.saveBoardImageEntity(boardImageMapper.toBoardImageEntity(boardEntity, imageURL));
			}
		}

		return new AddBoardResponseDto(boardEntity.getBoardSeq());
	}
}
