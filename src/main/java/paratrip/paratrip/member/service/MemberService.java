package paratrip.paratrip.member.service;

import static paratrip.paratrip.member.dto.request.MemberRequestDto.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.member.mapper.MemberMapper;
import paratrip.paratrip.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	private final MemberMapper memberMapper;
	private final BCryptPasswordEncoder encoder;

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
}
