package paratrip.paratrip.member.dto.response;

public class MemberResponseDto {
	public record LoginMemberResponseDto(
		Long memberSeq,
		String accessToken,
		String refreshToken
	) {

	}
}
