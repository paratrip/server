package paratrip.paratrip.sms.service;

import static paratrip.paratrip.sms.dto.UserDto.*;
import static paratrip.paratrip.sms.vo.SmsSendRequestVo.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;
import paratrip.paratrip.sms.dao.SmsCertificationDao;
import paratrip.paratrip.sms.util.SmsCertificationUtil;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SmsService {
	private final SmsCertificationUtil smsUtil;
	private final SmsCertificationDao smsCertificationDao;

	public void sendSms(SendSmsRequest requestDto) {
		String to = requestDto.phoneNumber();
		int randomNumber = (int)(Math.random() * 9000) + 1000;
		String certificationNumber = String.valueOf(randomNumber);
		smsUtil.sendSms(to, certificationNumber);
		smsCertificationDao.createSmsCertification(to, certificationNumber);
	}

	public void verifySms(SmsCertificationRequest requestDto) {
		if (isVerify(requestDto)) {
			throw new BadRequestException(ErrorResult.CERTIFICATION_NUMBER_BAD_REQUEST_EXCEPTION);
		}
		smsCertificationDao.removeSmsCertification(requestDto.getPhone());
	}

	public boolean isVerify(SmsCertificationRequest requestDto) {
		return !(smsCertificationDao.hasKey(requestDto.getPhone()) &&
			smsCertificationDao.getSmsCertification(requestDto.getPhone())
				.equals(requestDto.getCertificationNumber()));
	}
}
