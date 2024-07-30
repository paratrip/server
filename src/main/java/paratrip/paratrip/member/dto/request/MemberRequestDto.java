package paratrip.paratrip.member.dto.request;

public class MemberRequestDto {
	public record VerifyEmailMemberRequestDto(
		String email
	) {

	}

	public record VerifyPasswordMemberRequestDto(
		String password
	) {

	}

	public record VerifyUserIdMemberRequestDto(
		String userId
	) {

	}
}
