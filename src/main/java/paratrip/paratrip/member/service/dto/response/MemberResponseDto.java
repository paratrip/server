package paratrip.paratrip.member.service.dto.response;

import static paratrip.paratrip.member.controller.vo.response.MemberResponseVo.*;

import paratrip.paratrip.member.util.Gender;

public class MemberResponseDto {
	public record LoginMemberResponseDto(
		Long memberSeq,
		String accessToken,
		String refreshToken
	) {

	}

	public record FindMemberEmailResponseDto(
		String email
	) {

	}

	public record ReIssueTokenResponseDto(
		String email,
		String accessToken,
		String refreshToken
	) {

	}

	public record GetMemberInfoResponseDto(
		Long memberSeq,
		String email,
		String phoneNumber,

		String userId,

		String birth,

		Gender gender,
		String profileImage
	) {
		public GetMemberInfoResponse toGetMemberInfoResponse() {
			return new GetMemberInfoResponse(
				this.memberSeq,
				this.email,
				this.phoneNumber,
				this.userId,
				this.birth,
				this.gender,
				profileImage
			);
		}
	}
}
