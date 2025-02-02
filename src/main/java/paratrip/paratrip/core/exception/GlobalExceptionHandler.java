package paratrip.paratrip.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> handleException(
		final Exception exception,
		HttpServletResponse response
	) {
		log.warn("Server Exception occur: ", exception);

		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return this.makeErrorResponseEntity(ErrorResult.UNKNOWN_EXCEPTION);
	}

	@ExceptionHandler({BadRequestException.class})
	public ResponseEntity<ErrorResponse> handleBadRequestException(
		final BadRequestException exception,
		HttpServletResponse response
	) {
		log.warn("BadRequest Exception occur: ", exception);

		response.setStatus(exception.getErrorResult().getHttpStatus());
		return this.makeErrorResponseEntity(exception.getErrorResult());
	}

	@ExceptionHandler({NotFoundRequestException.class})
	public ResponseEntity<ErrorResponse> handleNotFoundException(
		final NotFoundRequestException exception,
		HttpServletResponse response
	) {
		log.warn("Not Found Exception occur: ", exception);

		response.setStatus(exception.getErrorResult().getHttpStatus());
		return this.makeErrorResponseEntity(exception.getErrorResult());
	}

	@ExceptionHandler({ConflictException.class})
	public ResponseEntity<ErrorResponse> handleConflictException(
		final ConflictException exception,
		HttpServletResponse response
	) {
		log.warn("Conflict Exception occur: ", exception);

		response.setStatus(exception.getErrorResult().getHttpStatus());
		return this.makeErrorResponseEntity(exception.getErrorResult());
	}

	@ExceptionHandler({UnAuthorizedException.class})
	public ResponseEntity<ErrorResponse> handleUnAuthorizedException(
		final UnAuthorizedException exception,
		HttpServletResponse response
	) {
		log.warn("UnAuthorized Exception occur: ", exception);

		response.setStatus(exception.getErrorResult().getHttpStatus());
		return this.makeErrorResponseEntity(exception.getErrorResult());
	}

	private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final ErrorResult errorResult) {
		return ResponseEntity.status(errorResult.getHttpStatus())
			.body(
				new ErrorResponse(
					errorResult.getHttpStatus(),
					errorResult.getMessage(),
					errorResult.getCode())
			);
	}

	@Getter
	@RequiredArgsConstructor
	public static class ErrorResponse {
		private final int status;
		private final String message;
		private final String code;
	}
}
