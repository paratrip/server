package paratrip.paratrip.paragliding.repository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.core.exception.BadRequestException;
import paratrip.paratrip.core.exception.ErrorResult;
import paratrip.paratrip.paragliding.entity.Paragliding;

@Component
@RequiredArgsConstructor
public class ParaglidingRepositoryImpl implements ParaglidingRepository {
	private final ParaglidingJpaRepository paraglidingJpaRepository;

	@Override
	public Paragliding findByParaglidingSeq(Long paraglidingSeq) {
		return paraglidingJpaRepository.findById(paraglidingSeq)
			.orElseThrow(() -> new BadRequestException(ErrorResult.PARAGLIDING_SEQ_BAD_REQUEST_EXCEPTION));
	}
}
