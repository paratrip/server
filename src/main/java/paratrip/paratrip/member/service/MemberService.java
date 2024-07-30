package paratrip.paratrip.member.service;

import static paratrip.paratrip.member.dto.request.MemberRequestDto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public void verifyMemberEmail(VerifyEmailMemberRequestDto verifyEmailMemberRequestDto) {
		/*
		 1. Email 유효성 검사
		*/
		memberRepository.isDuplicatedEmail(verifyEmailMemberRequestDto.email());
	}
}
