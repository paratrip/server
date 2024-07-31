package paratrip.paratrip.member.vo.request;

import static paratrip.paratrip.member.dto.request.MemberRequestDto.*;

import paratrip.paratrip.member.util.Gender;

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

	}

	public record VerifyUserIdMemberRequest(
		String userId
	) {
		public VerifyUserIdMemberRequestDto toVerifyUserIdMemberRequestDto() {
			return new VerifyUserIdMemberRequestDto(
				this.userId
			);
		}
	}

	public record VerifyPhoneNumberMemberRequest(
		String phoneNumber
	) {
		public VerifyPhoneNumberMemberRequestDto toVerifyPhoneNumberMemberRequestDto() {
			return new VerifyPhoneNumberMemberRequestDto(
				this.phoneNumber
			);
		}
	}

	public record JoinMemberRequest(
		String email,
		String password,
		String phoneNumber,
		String userId,
		String birth,
		Gender gender
	) {
		public JoinMemberRequestDto toJoinMemberRequestDto() {
			return new JoinMemberRequestDto(
				this.email,
				this.password,
				this.phoneNumber,
				this.userId,
				this.birth,
				this.gender
			);
		}
	}

	public record LoginMemberRequest(
		String email,
		String password
	) {
		public LoginMemberRequestDto toLoginMemberRequestDto() {
			return new LoginMemberRequestDto(
				this.email,
				this.password
			);
		}
	}

	public record LogoutMemberRequest(
		Long memberSeq
	) {
		public LogoutMemberRequestDto toLogoutMemberRequestDto() {
			return new LogoutMemberRequestDto(
				this.memberSeq
			);
		}
	}
}
