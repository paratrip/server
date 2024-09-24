package paratrip.paratrip.core.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

@Component
public class LocalDateTimeConverter {
	public ZonedDateTime convertToKoreanTime(LocalDateTime localDateTime) {
		LocalDateTime adjustedDateTime = localDateTime.plusHours(9);
		return adjustedDateTime.atZone(ZoneId.of("Asia/Seoul"));
	}
}
