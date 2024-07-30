package paratrip.paratrip.member.vo.request;

import static paratrip.paratrip.member.dto.request.MemberRequestDto.*;

public class MemberRequestVo {
	public record VerifyEmailMemberRequest(
		String email
	) {
		public VerifyEmailMemberRequestDto toVerifyEmailMemberRequestDto() {
			return new VerifyEmailMemberRequestDto(
				this.email
			);
		}
	}

	public record VerifyPasswordMemberRequest(
		String password
	) {
		public VerifyPasswordMemberRequestDto toVerifyPasswordMemberRequestDto() {
			return new VerifyPasswordMemberRequestDto(
				this.password
			);
		}
	}
}
