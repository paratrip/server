package paratrip.paratrip.sms.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

import jakarta.annotation.PostConstruct;

@Component
public class SmsCertificationUtil {
	DefaultMessageService messageService;

	@Value("${spring.coolsms.senderNumber}")
	private String senderNumber;

	@Value("${spring.coolsms.apiKey}")
	private String apiKey;

	@Value("${spring.coolsms.apiSecret}")
	private String apiSecret;

	@PostConstruct
	public void init() {
		this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
	}

	public SingleMessageSentResponse sendSms(String to, String verificationCode) {
		Message message = new Message();
		message.setFrom(senderNumber);
		message.setTo(to);
		message.setText("[ParaTrip] 본인 확인 인증번호는 " + verificationCode + "입니다.");

		SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
		System.out.println(response);

		return response;
	}
}
