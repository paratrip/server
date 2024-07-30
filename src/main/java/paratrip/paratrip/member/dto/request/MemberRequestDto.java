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
		Long memberSeq
	) {

	}
}
