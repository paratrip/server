package paratrip.paratrip.board.main.service;

import static paratrip.paratrip.board.main.service.dto.request.BoardRequestDto.*;
import static paratrip.paratrip.board.main.service.dto.response.BoardResponseDto.*;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.domain.BoardDomain;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.entity.BoardImageEntity;
import paratrip.paratrip.board.main.mapper.BoardImageMapper;
import paratrip.paratrip.board.main.mapper.BoardMapper;
import paratrip.paratrip.board.main.repository.BoardImageRepository;
import paratrip.paratrip.board.main.repository.BoardRepository;
import paratrip.paratrip.board.search.mapper.BoardDocumentsMapper;
import paratrip.paratrip.board.search.repository.BoardDocumentsRepository;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.member.repository.MemberRepository;
import paratrip.paratrip.s3.domain.S3Domain;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	private final BoardImageRepository boardImageRepository;
	private final BoardDocumentsRepository boardDocumentsRepository;

	private final S3Domain s3Domain;
	private final BoardDomain boardDomain;

	private final BoardMapper boardMapper;
	private final BoardImageMapper boardImageMapper;
	private final BoardDocumentsMapper boardDocumentsMapper;

	@Transactional
	public AddBoardResponseDto saveBoard(AddBoardRequestDto addBoardRequestDto) throws IOException {
		/*
		 1. MemberSeq 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(addBoardRequestDto.memberSeq());

		BoardEntity boardEntity = boardMapper.toBoardEntity(
			addBoardRequestDto.title(),
			addBoardRequestDto.content(),
			addBoardRequestDto.location(),
			0L,
			memberEntity
		);

		boardEntity = boardRepository.saveBoardEntity(boardEntity);

		// BoardDocumentsEntity 저장
		boardDocumentsRepository.saveBoardDocuments(boardDocumentsMapper.toBoardDocuments(
			boardEntity.getBoardSeq(),
			boardEntity.getTitle()
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

	@Transactional
	public void modifyBoard(ModifyBoardRequestDto modifyBoardRequestDto) throws IOException {
		/*
		 1. MemberSeq 유효성 검사
		 2. BoardSeq 유효성 검사
		 3. 작성자 확인
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(modifyBoardRequestDto.memberSeq());
		boardRepository.findByBoardSeq(modifyBoardRequestDto.boardSeq());
		BoardEntity boardEntity
			= boardRepository.findByCreatorMemberEntityAndBoardSeq(memberEntity, modifyBoardRequestDto.boardSeq());

		// Board Image Entity 전체 삭제 (Update 불가능)
		List<BoardImageEntity> boardImageEntities = boardImageRepository.findAllByBoardEntity(boardEntity);
		boardImageRepository.deleteAll(boardImageEntities);

		// Update BoardEntity
		BoardEntity updateBoardEntity = boardEntity.updateBoardEntity(
			modifyBoardRequestDto.title(),
			modifyBoardRequestDto.content(),
			modifyBoardRequestDto.location()
		);
		boardRepository.saveBoardEntity(updateBoardEntity);

		// Board Image Entity 저장
		if (boardDomain.checkImages(modifyBoardRequestDto.images())) {
			for (MultipartFile multipartFile : modifyBoardRequestDto.images()) {
				String imageURL = s3Domain.uploadMultipartFile(multipartFile);
				boardImageRepository.saveBoardImageEntity(boardImageMapper.toBoardImageEntity(boardEntity, imageURL));
			}
		}
	}
}
