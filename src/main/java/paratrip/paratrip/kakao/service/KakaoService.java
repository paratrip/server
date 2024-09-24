package paratrip.paratrip.kakao.service;

import static paratrip.paratrip.member.service.dto.response.MemberResponseDto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import paratrip.paratrip.kakao.domain.KakaoDomain;
import paratrip.paratrip.member.domain.MemberDomain;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class KakaoService {
	private final MemberRepository memberRepository;

	private final KakaoDomain kakaoDomain;
	private final MemberDomain memberDomain;

	@Transactional
	public LoginMemberResponseDto loginKakaoMember(String code) throws Exception {
		/*
		 1. Kakao 회원가입 여부 확인
		  만약 예외가 안터지면 이미 회원가입 된 사용자
		  예외가 터지면 회원가입 필요
		*/
		JsonNode rootNode = kakaoDomain.getUserFromCode(code);
		String email = kakaoDomain.getEmail(rootNode);
		MemberEntity memberEntity = memberRepository.findByEmail(email);

		// 이미지 수정
		String profileImage = kakaoDomain.getProfileImage(rootNode);
		MemberEntity updateMemberEntity = memberEntity.updateKakaoProfileImage(profileImage);
		memberRepository.saveMemberEntity(updateMemberEntity);

		// Token 발급
		String accessToken = memberDomain.generateAccessToken(email);
		String refreshToken = memberDomain.generateRefreshToken(email);

		// RefreshToken 저장
		memberDomain.saveRefreshToken(refreshToken, email);

		return new LoginMemberResponseDto(memberEntity.getMemberSeq(), accessToken, refreshToken);
	}
}
