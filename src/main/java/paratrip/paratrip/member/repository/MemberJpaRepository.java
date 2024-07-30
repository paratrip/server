package paratrip.paratrip.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.member.entity.MemberEntity;

@Repository
public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
	Optional<MemberEntity> findByEmail(String email);

	Optional<MemberEntity> findByUserId(String userId);

	Optional<MemberEntity> findByMemberSeq(Long memberSeq);
}
