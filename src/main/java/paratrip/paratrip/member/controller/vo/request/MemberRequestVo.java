package paratrip.paratrip.member.controller.vo.request;

import static paratrip.paratrip.member.service.dto.request.MemberRequestDto.*;

import org.springframework.web.multipart.MultipartFile;

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
		Long memberSeq,
		String accessToken,
		String refreshToken
	) {
		public LogoutMemberRequestDto toLogoutMemberRequestDto() {
			return new LogoutMemberRequestDto(
				this.memberSeq,
				this.accessToken,
				this.refreshToken
			);
		}
	}

	public record FindMemberEmailRequest(
		String phoneNumber
	) {
		public FindMemberEmailRequestDto toFindMemberEmailRequestDto() {
			return new FindMemberEmailRequestDto(
				this.phoneNumber
			);
		}
	}

	public record ResetMemberPasswordRequest(
		String phoneNumber,
		String password
	) {
		public ResetMemberPasswordRequestDto toResetMemberPasswordRequestDto() {
			return new ResetMemberPasswordRequestDto(
				this.phoneNumber,
				this.password
			);
		}
	}

	public record ReIssueTokenRequest(
		String email,
		String refreshToken
	) {
		public ReIssueTokenRequestDto toReIssueTokenRequestDto() {
			return new ReIssueTokenRequestDto(
				this.email,
				this.refreshToken
			);
		}
	}

	public record ModifyMemberRequest(
		Long memberSeq,
		String userId,
		String birth,
		Gender gender,
		MultipartFile profileImage
	) {
		public ModifyMemberRequestDto toModifyMemberRequestDto() {
			return new ModifyMemberRequestDto(
				this.memberSeq,
				this.userId,
				this.birth,
				this.gender,
				this.profileImage
			);
		}
	}
}
