package paratrip.paratrip.sms.vo;

public class SmsSendRequestVo {
	public record SendSmsRequest(
		String phoneNumber
	) {

	}
}
