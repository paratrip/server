package paratrip.paratrip.member.repository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.core.exception.ConflictException;
import paratrip.paratrip.core.exception.ErrorResult;

@Component
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
	private final MemberJpaRepository memberJpaRepository;

	@Override
	public void isDuplicatedEmail(String email) {
		memberJpaRepository.findByEmail(email)
			.ifPresent(entity -> {
				throw new ConflictException(ErrorResult.EMAIL_DUPLICATION_CONFLICT_EXCEPTION);
			});
	}
}
