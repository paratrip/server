package paratrip.paratrip.scrap.paragliding.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ConflictException;
import paratrip.paratrip.core.exception.ErrorResult;
import paratrip.paratrip.core.exception.NotFoundRequestException;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.paragliding.entity.Paragliding;
import paratrip.paratrip.paragliding.entity.QParagliding;
import paratrip.paratrip.scrap.paragliding.entity.ParaglidingScrapEntity;
import paratrip.paratrip.scrap.paragliding.entity.QParaglidingScrapEntity;

@Component
@RequiredArgsConstructor
public class ParaglidingScrapRepositoryImpl implements ParaglidingScrapRepository {
	private final ParaglidingScrapJpaRepository paraglidingScrapJpaRepository;
	private final JPAQueryFactory queryFactory;

	@Override
	public ParaglidingScrapEntity saveParaglidingScrapEntity(ParaglidingScrapEntity paraglidingScrapEntity) {
		return paraglidingScrapJpaRepository.save(paraglidingScrapEntity);
	}

	@Override
	public ParaglidingScrapEntity findByParaglidingScrapSeq(Long paraglidingScrapSeq) {
		return paraglidingScrapJpaRepository.findById(paraglidingScrapSeq)
			.orElseThrow(() -> new BadRequestException(ErrorResult.PARAGLIDING_SCRAP_SEQ_BAD_REQUEST_EXCEPTION));
	}

	@Override
	public void deleteParaglidingScrapEntity(ParaglidingScrapEntity paraglidingScrapEntity) {
		paraglidingScrapJpaRepository.delete(paraglidingScrapEntity);
	}

	@Override
	public ParaglidingScrapEntity findByMemberEntity(MemberEntity memberEntity) {
		QParaglidingScrapEntity qParaglidingScrapEntity = QParaglidingScrapEntity.paraglidingScrapEntity;

		return Optional.ofNullable(
			queryFactory
				.selectFrom(qParaglidingScrapEntity)
				.where(
					qParaglidingScrapEntity.memberEntity.eq(memberEntity)
				).fetchOne()
		).orElseThrow(() -> new BadRequestException(ErrorResult.PARAGLIDING_SCRAP_NOT_FOUND_EXCEPTION));
	}

	@Override
	public List<ParaglidingScrapEntity> findAllByMemberEntity(MemberEntity memberEntity) {
		QParaglidingScrapEntity qParaglidingScrapEntity = QParaglidingScrapEntity.paraglidingScrapEntity;

		return queryFactory.
			select(qParaglidingScrapEntity)
			.from(qParaglidingScrapEntity)
			.where(
				qParaglidingScrapEntity.memberEntity.eq(memberEntity)
			).fetch();
	}

	@Override
	public void existsMemberEntityParaglidingScrapEntity(
		MemberEntity memberEntity,
		Paragliding paraglidingEntity
	) {
		QParaglidingScrapEntity qParaglidingScrapEntity = QParaglidingScrapEntity.paraglidingScrapEntity;

		Optional.ofNullable(
			queryFactory
				.selectFrom(qParaglidingScrapEntity)
				.where(
					qParaglidingScrapEntity.memberEntity.eq(memberEntity)
						.and(qParaglidingScrapEntity.paraglidingEntity.eq(paraglidingEntity))
				).fetchOne()
		).ifPresent(scrap -> {
			throw new ConflictException(ErrorResult.SCRAP_PARAGLIDING_DUPLICATION_CONFLICT_EXCEPTION);
		});
	}

	@Override
	public ParaglidingScrapEntity findByMemberEntityAndParagliding(MemberEntity memberEntity, Paragliding paragliding) {
		QParaglidingScrapEntity qParaglidingScrapEntity = QParaglidingScrapEntity.paraglidingScrapEntity;

		return Optional.ofNullable(
			queryFactory
				.selectFrom(qParaglidingScrapEntity)
				.where(
					qParaglidingScrapEntity.memberEntity.eq(memberEntity)
						.and(qParaglidingScrapEntity.paraglidingEntity.eq(paragliding))
				).fetchOne()
		).orElseThrow(() -> new NotFoundRequestException(ErrorResult.PARAGLIDING_SCRAP_NOT_FOUND_EXCEPTION));
	}
}
