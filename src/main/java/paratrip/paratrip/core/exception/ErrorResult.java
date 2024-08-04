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

	CERTIFICATION_NUMBER_BAD_REQUEST_EXCEPTION(
		HttpStatus.BAD_REQUEST.value(),
		"CERTIFICATION_NUMBER_BAD_REQUEST_EXCEPTION",
		"CNB004"
	),

	BOARD_SEQ_BAD_REQUEST_EXCEPTION(
		HttpStatus.BAD_REQUEST.value(),
		"BOARD_SEQ_BAD_REQUEST_EXCEPTION",
		"BSB005"
	),

	BOARD_NOT_CREATED_BY_MEMBER_BAD_REQUEST_EXCEPTION(
		HttpStatus.BAD_REQUEST.value(),
		"BOARD_NOT_CREATED_BY_MEMBER_BAD_REQUEST_EXCEPTION",
		"BNCBMB006"
	),

	BOARD_HEART_SEQ_BAD_REQUEST_EXCEPTION(
		HttpStatus.BAD_REQUEST.value(),
		"BOARD_HEART_SEQ_BAD_REQUEST_EXCEPTION",
		"BHSB007"
	),

	BOARD_SCRAP_SEQ_BAD_REQUEST_EXCEPTION(
		HttpStatus.BAD_REQUEST.value(),
		"BOARD_SCRAP_SEQ_BAD_REQUEST_EXCEPTION",
		"BSSB008"
	),


	// NOT FOUND
	EMAIL_NOT_FOUND_EXCEPTION(
		HttpStatus.NOT_FOUND.value(),
		"EMAIL_NOT_FOUND_EXCEPTION",
		"ENF001"
	),

	PHONE_NUMBER_NOT_FOUND_EXCEPTION(
		HttpStatus.NOT_FOUND.value(),
		"PHONE_NUMBER_NOT_FOUND_EXCEPTION",
		"PNNF002"
	),

	BOARD_HEART_NOT_FOUND_EXCEPTION(
		HttpStatus.NOT_FOUND.value(),
		"BOARD_HEART_NOT_FOUND_EXCEPTION",
		"BHNF003"
	),

	BOARDS_SCRAP_NOT_FOUND_EXCEPTION(
		HttpStatus.NOT_FOUND.value(),
		"BOARDS_SCRAP_NOT_FOUND_EXCEPTION",
		"BSNF004"
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

	PHONE_NUMBER_DUPLICATION_CONFLICT_EXCEPTION(
		HttpStatus.CONFLICT.value(),
		"PHONE_NUMBER_DUPLICATION_CONFLICT_EXCEPTION",
		"PNDC003"
	),

	// UnAuthorized
	TOKEN_UNAUTHORIZED_EXCEPTION(
		HttpStatus.UNAUTHORIZED.value(),
		"REFRESH_TOKEN_UNAUTHORIZED_EXCEPTION",
		"TU001"
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
