package paratrip.paratrip.member.service.dto.request;

import org.springframework.web.multipart.MultipartFile;

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

	public record ModifyMemberRequestDto(
		Long memberSeq,
		String userId,
		String birth,
		Gender gender,
		MultipartFile profileImage
	) {

	}
}
