package paratrip.paratrip.member.repository;

import java.util.Optional;

import paratrip.paratrip.member.entity.MemberEntity;

public interface MemberRepository {
	MemberEntity saveMemberEntity(MemberEntity memberEntity);

	void isDuplicatedEmail(String email);

	void isDuplicatedUserId(String userId);

	void isDuplicatedPhoneNumber(String phoneNumber);

	MemberEntity findByEmail(String email);

	MemberEntity findByMemberSeq(Long memberSeq);

	MemberEntity findByPhoneNumber(String phoneNumber);
}
