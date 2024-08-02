package paratrip.paratrip.member.dto.request;

import paratrip.paratrip.member.util.Gender;

public class MemberRequestDto {
	public record VerifyEmailMemberRequestDto(
		String email
	) {

	}

	public record VerifyUserIdMemberRequestDto(
		String userId
	) {

	}

	public record VerifyPhoneNumberMemberRequestDto(
		String phoneNumber
	) {

	}

	public record JoinMemberRequestDto(
		String email,
		String password,
		String phoneNumber,
		String userId,
		String birth,
		Gender gender
	) {

	}

	public record LoginMemberRequestDto(
		String email,
		String password
	) {

	}

	public record LogoutMemberRequestDto(
		Long memberSeq,
		String accessToken,
		String refreshToken
	) {

	}

	public record FindMemberEmailRequestDto(
		String phoneNumber
	) {

	}

	public record ResetMemberPasswordRequestDto(
		String phoneNumber,
		String password
	) {

	}

	public record ReIssueTokenRequestDto(
		String email,
		String refreshToken
	) {

	}
}
