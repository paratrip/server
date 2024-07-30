package paratrip.paratrip.member.dto.request;

public class MemberRequestDto {
	public record VerifyEmailMemberRequestDto(
		String email
	) {

	}
}
