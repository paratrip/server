package paratrip.paratrip.core.utils;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.member.repository.MemberRepository;
import paratrip.paratrip.member.util.Gender;

@Component
@RequiredArgsConstructor
public class DataLoader {
	private final BCryptPasswordEncoder encoder;

	@Bean
	ApplicationRunner init(MemberRepository memberRepository) {
		MemberEntity memberEntity = MemberEntity.builder()
			.email("mj9457@naver.com")
			.encodedPassword(encoder.encode("qwer1234")) // 실제로는 암호화된 비밀번호를 사용
			.phoneNumber("010-8639-9457")
			.userId("mj9457")
			.birth("19971128")
			.gender(Gender.MALE)
			.profileImage(null) // 이미지가 없는 경우 null 처리
			.build();
		return args -> {
			memberRepository.saveMemberEntity(memberEntity);
		};
	}
}