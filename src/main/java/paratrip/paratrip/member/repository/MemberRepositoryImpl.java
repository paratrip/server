package paratrip.paratrip.member.repository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ConflictException;
import paratrip.paratrip.core.exception.ErrorResult;
import paratrip.paratrip.core.exception.NotFoundRequestException;
import paratrip.paratrip.member.entity.MemberEntity;

@Component
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
	private final MemberJpaRepository memberJpaRepository;

	@Override
	public MemberEntity saveMemberEntity(MemberEntity memberEntity) {
		return memberJpaRepository.save(memberEntity);
	}

	@Override
	public void isDuplicatedEmail(String email) {
		memberJpaRepository.findByEmail(email)
			.ifPresent(entity -> {
				throw new ConflictException(ErrorResult.EMAIL_DUPLICATION_CONFLICT_EXCEPTION);
			});
	}

	@Override
	public void isDuplicatedUserId(String userId) {
		memberJpaRepository.findByUserId(userId)
			.ifPresent(entity -> {
				throw new ConflictException(ErrorResult.USER_ID_DUPLICATION_CONFLICT_EXCEPTION);
			});
	}

	@Override
	public MemberEntity findByEmail(String email) {
		return memberJpaRepository.findByEmail(email)
			.orElseThrow(() -> new NotFoundRequestException(ErrorResult.EMAIL_NOT_FOUND_EXCEPTION));
	}

	@Override
	public MemberEntity findByMemberSeq(Long memberSeq) {
		return memberJpaRepository.findByMemberSeq(memberSeq)
			.orElseThrow(() -> new BadRequestException(ErrorResult.MEMBER_SEQ_BAD_REQUEST_EXCEPTION));
	}
}
