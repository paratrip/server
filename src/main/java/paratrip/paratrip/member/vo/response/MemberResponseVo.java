package paratrip.paratrip.member.vo.response;

public class MemberResponseVo {
	public record LoginMemberResponse(
		Long memberSeq,
		String accessToken,
		String refreshToken
	) {

	}
}
