package paratrip.paratrip.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ConflictException extends RuntimeException {
	private final ErrorResult errorResult;
}
