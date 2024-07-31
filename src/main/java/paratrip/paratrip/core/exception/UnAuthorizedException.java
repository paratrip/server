package paratrip.paratrip.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UnAuthorizedException extends RuntimeException {
	private final ErrorResult errorResult;
}
