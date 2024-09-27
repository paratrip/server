package paratrip.paratrip.scrap.paragliding.service;

import static paratrip.paratrip.scrap.paragliding.service.dto.request.ParaglidingScrapRequestDto.*;
import static paratrip.paratrip.scrap.paragliding.service.dto.response.ParaglidingScrapResponseDto.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.member.repository.MemberRepository;
import paratrip.paratrip.paragliding.entity.Paragliding;
import paratrip.paratrip.paragliding.repository.ParaglidingRepository;
import paratrip.paratrip.scrap.paragliding.entity.ParaglidingScrapEntity;
import paratrip.paratrip.scrap.paragliding.mapper.ParaglidingScrapMapper;
import paratrip.paratrip.scrap.paragliding.repository.ParaglidingScrapRepository;

@Service
@RequiredArgsConstructor
public class ParaglidingScrapService {
	private final MemberRepository memberRepository;
	private final ParaglidingRepository paraglidingRepository;
	private final ParaglidingScrapRepository paraglidingScrapRepository;

	private final ParaglidingScrapMapper paraglidingScrapMapper;

	@Transactional
	public SaveParaglidingScrapResponseDto saveParaglidingScrap(SaveParaglidingScrapRequestDto request) {
		/*
		 1. Member 유효성 검사
		 2. Paragliding 유효성 검사
		 3. 이미 SCRAP 존재 여부
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(request.memberSeq());
		Paragliding paraglidingEntity = paraglidingRepository.findByParaglidingSeq(request.paraglidingSeq());
		paraglidingScrapRepository.existsMemberEntityParaglidingScrapEntity(memberEntity, paraglidingEntity);

		ParaglidingScrapEntity paraglidingScrapEntity = paraglidingScrapRepository.saveParaglidingScrapEntity(
			paraglidingScrapMapper.toParaglidingScrapEntity(memberEntity, paraglidingEntity)
		);

		return new SaveParaglidingScrapResponseDto(paraglidingScrapEntity.getParaglidingScrapSeq());
	}

	@Transactional
	public void deleteParaglidingScrap(DeleteParaglidingScrapRequestDto request) {
		/*
		 1. Member 유효성 검사
		 2. Paragliding Scrap 유효성 검사
		 3. 작성자 여부 확인
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(request.memberSeq());
		paraglidingScrapRepository.findByParaglidingScrapSeq(request.paraglidingScrapSeq());
		ParaglidingScrapEntity paraglidingScrapEntity = paraglidingScrapRepository.findByMemberEntity(memberEntity);

		paraglidingScrapRepository.deleteParaglidingScrapEntity(paraglidingScrapEntity);
	}

	@Transactional(readOnly = true)
	public List<GetParaglidingScrapResponseDto> getParaglidingScrap(Long memberSeq) {
		/*
		 1. Member 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(memberSeq);
		List<ParaglidingScrapEntity> paraglidingScrapEntities
			= paraglidingScrapRepository.findAllByMemberEntity(memberEntity);

		return paraglidingScrapEntities.stream()
			.map(scrap -> {
				Paragliding paragliding = scrap.getParaglidingEntity();  // ParaglidingScrapEntity에서 Paragliding 가져오기
				return new GetParaglidingScrapResponseDto(
					paragliding.getParaglidingSeq(),
					paragliding.getName(),
					paragliding.getHeart(),
					paragliding.getCost(),
					paragliding.getRegion(),
					paragliding.getImageUrl()
				);
			})
			.collect(Collectors.toList());
	}
}
