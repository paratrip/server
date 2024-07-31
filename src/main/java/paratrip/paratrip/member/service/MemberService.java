package paratrip.paratrip.member.service;

import static paratrip.paratrip.member.dto.request.MemberRequestDto.*;
import static paratrip.paratrip.member.dto.response.MemberResponseDto.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.member.domain.MemberDomain;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.member.mapper.MemberMapper;
import paratrip.paratrip.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	private final MemberMapper memberMapper;

	private final BCryptPasswordEncoder encoder;

	private final MemberDomain memberDomain;

	@Transactional(readOnly = true)
	public void verifyMemberEmail(VerifyEmailMemberRequestDto verifyEmailMemberRequestDto) {
		/*
		 1. Email 중복 검사
		*/
		memberRepository.isDuplicatedEmail(verifyEmailMemberRequestDto.email());
	}

	@Transactional(readOnly = true)
	public void verifyMemberUserId(VerifyUserIdMemberRequestDto verifyUserIdMemberRequest) {
		/*
		 1. UserId 중복 검사
		*/
		memberRepository.isDuplicatedUserId(verifyUserIdMemberRequest.userId());
	}

	@Transactional(readOnly = true)
	public void verifyMemberPhoneNumber(VerifyPhoneNumberMemberRequestDto verifyPhoneNumberMemberRequestDto) {
		/*
		 1. PhoneNumber 중복 검사
		*/
		memberRepository.isDuplicatedPhoneNumber(verifyPhoneNumberMemberRequestDto.phoneNumber());
	}

	@Transactional
	public void joinMember(JoinMemberRequestDto joinMemberRequestDto) {
		/*
		 1. Email 중복 검사
		 2. UserId 중복 검사
		*/
		memberRepository.isDuplicatedEmail(joinMemberRequestDto.email());
		memberRepository.isDuplicatedUserId(joinMemberRequestDto.userId());

		// Member 저장
		memberRepository.saveMemberEntity(memberMapper.toMemberEntity(
			joinMemberRequestDto,
			encoder.encode(joinMemberRequestDto.password()))
		);
	}

	@Transactional(readOnly = true)
	public LoginMemberResponseDto loginMember(LoginMemberRequestDto loginMemberRequestDto) {
		/*
		 1. Email 존재 확인
		 2. 비밀번호 검증
		*/
		MemberEntity memberEntity = memberRepository.findByEmail(loginMemberRequestDto.email());
		memberDomain.checkPassword(loginMemberRequestDto.password(), memberEntity.getEncodedPassword());

		// JWT Token 생성
		String accessToken = memberDomain.generateAccessToken(loginMemberRequestDto.email());
		String refreshToken = memberDomain.generateRefreshToken(loginMemberRequestDto.email());

		// RefreshToken 저장
		memberDomain.saveRefreshToken(refreshToken, loginMemberRequestDto.email());

		return new LoginMemberResponseDto(memberEntity.getMemberSeq(), accessToken, refreshToken);
	}

	@Transactional
	public void logoutMember(LogoutMemberRequestDto logoutMemberRequestDto) {
		/*
		 1. Member 존재 확인
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(logoutMemberRequestDto.memberSeq());

		// RefreshToken 삭제
		memberDomain.deleteRefreshToken(memberEntity.getEmail());
	}

	@Transactional(readOnly = true)
	public FindMemberEmailResponseDto findMemberEmail(FindMemberEmailRequestDto findMemberEmailRequestDto) {
		/*
		 1. PhoneNumber 존재 확인
		*/
		MemberEntity memberEntity = memberRepository.findByPhoneNumber(findMemberEmailRequestDto.phoneNumber());

		return new FindMemberEmailResponseDto(memberEntity.getEmail());
	}
}
