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

	// NOT FOUND

	// CONFLICT
	EMAIL_DUPLICATION_CONFLICT_EXCEPTION(
		HttpStatus.CONFLICT.value(),
		"EMAIL_DUPLICATION_CONFLICT_EXCEPTION",
		"EDC001"
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
