package paratrip.paratrip.core.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorResult {

	// BAD_REQUEST
	DTO_BAD_REQUEST_EXCEPTION(
		HttpStatus.BAD_REQUEST.value(),
		"BTO Bad Request Exception",
		"B001"
	),

	PASSWORD_BAD_REQUEST_EXCEPTION(
		HttpStatus.BAD_REQUEST.value(),
		"PASSWORD_BAD_REQUEST_EXCEPTION",
		"PB002"
	),

	MEMBER_SEQ_BAD_REQUEST_EXCEPTION(
		HttpStatus.BAD_REQUEST.value(),
		"MEMBER_SEQ_BAD_REQUEST_EXCEPTION",
		"MSB003"
	),

	// NOT FOUND
	EMAIL_NOT_FOUND_EXCEPTION(
		HttpStatus.NOT_FOUND.value(),
		"EMAIL_NOT_FOUND_EXCEPTION",
		"ENF001"
	),

	// CONFLICT
	EMAIL_DUPLICATION_CONFLICT_EXCEPTION(
		HttpStatus.CONFLICT.value(),
		"EMAIL_DUPLICATION_CONFLICT_EXCEPTION",
		"EDC001"
	),

	USER_ID_DUPLICATION_CONFLICT_EXCEPTION(
		HttpStatus.CONFLICT.value(),
		"USER_ID_DUPLICATION_CONFLICT_EXCEPTION",
		"UIDC002"
	),

	// SERVER
	UNKNOWN_EXCEPTION(
		HttpStatus.INTERNAL_SERVER_ERROR.value(),
		"Unknown Exception",
		"S500"
	),

	;

	private final int httpStatus;
	private final String message;
	private final String code;
}
