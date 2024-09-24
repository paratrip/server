package paratrip.paratrip.scrap.paragliding.repository;

import java.util.List;

import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.paragliding.entity.Paragliding;
import paratrip.paratrip.scrap.paragliding.entity.ParaglidingScrapEntity;

public interface ParaglidingScrapRepository {
	ParaglidingScrapEntity saveParaglidingScrapEntity(ParaglidingScrapEntity paraglidingScrapEntity);

	ParaglidingScrapEntity findByParaglidingScrapSeq(Long paraglidingScrapSeq);

	void deleteParaglidingScrapEntity(ParaglidingScrapEntity paraglidingScrapEntity);

	List<Paragliding> findAllByMemberEntity(MemberEntity memberEntity);

	void existsMemberEntityParaglidingScrapEntity(
		MemberEntity memberEntity,
		Paragliding paraglidingScrapEntity
	);
}
