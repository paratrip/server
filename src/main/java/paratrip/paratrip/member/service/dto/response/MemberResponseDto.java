package paratrip.paratrip.member.service.dto.response;

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
}
