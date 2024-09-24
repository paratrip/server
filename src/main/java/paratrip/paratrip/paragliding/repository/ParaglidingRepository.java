package paratrip.paratrip.paragliding.repository;

import paratrip.paratrip.paragliding.entity.Paragliding;

public interface ParaglidingRepository {
	Paragliding findByParaglidingSeq(Long paraglidingSeq);
}
