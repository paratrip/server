package paratrip.paratrip.member.controller.vo.response;

import paratrip.paratrip.member.util.Gender;

public class MemberResponseVo {
	public record LoginMemberResponse(
		Long memberSeq,
		String accessToken,
		String refreshToken
	) {

	}

	public record FindMemberEmailResponse(
		String email
	) {

	}

	public record ReIssueTokenResponse(
		String email,
		String accessToken,
		String refreshToken
	) {

	}

	public record GetMemberInfoResponse(
		Long memberSeq,
		String email,
		String phoneNumber,
		String userId,
		String birth,
		Gender gender,
		String profileImage,
		Boolean kakao
	) {

	}
}
