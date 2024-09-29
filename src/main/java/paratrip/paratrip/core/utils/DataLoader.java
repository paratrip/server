package paratrip.paratrip.core.utils;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.repository.BoardRepository;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.member.repository.MemberRepository;
import paratrip.paratrip.member.util.Gender;

@Component
@RequiredArgsConstructor
public class DataLoader {
	private final BCryptPasswordEncoder encoder;

	@Bean
	ApplicationRunner init(MemberRepository memberRepository, BoardRepository boardRepository) {
		return args -> {
			MemberEntity memberEntity1 = MemberEntity.builder()
				.email("mj9457@naver.com")
				.encodedPassword(encoder.encode("qwer1234"))
				.phoneNumber("010-8639-9457")
				.userId("mj9457")
				.birth("19971128")
				.gender(Gender.MALE)
				.profileImage(null)
				.build();

			MemberEntity memberEntity2 = MemberEntity.builder()
				.email("admin1234@naver.com")
				.encodedPassword(encoder.encode("admin1234"))
				.phoneNumber("010-1234-5678")
				.userId("admin1234")
				.birth("000123")
				.gender(Gender.MALE)
				.profileImage(null)
				.build();

			// 두 개의 엔티티를 저장
			memberRepository.saveMemberEntity(memberEntity1);
			MemberEntity savedMemberEntity = memberRepository.saveMemberEntity(memberEntity2);

			String[] titles = {
				"용인 정광산에서의 패러글라이딩 체험",
				"보령의 바다를 배경으로 한 패러글라이딩 체험",
				"제주도 서귀포에서 하늘을 날다",
				"가평에서 즐긴 패러글라이딩의 짜릿한 경험",
				"태백산 자락에서의 패러글라이딩 도전기"
			};

			String[] locations = {
				"경기도 용인", "충청남도 보령", "제주도 서귀포", "경기도 가평", "강원도 태백"
			};

			String contentTemplate = """
				안녕하세요! 최근에 %s에서 패러글라이딩을 체험해보았습니다. 멋진 자연경관 속에서 잊지 못할 경험을 했어요.
				특히 하늘에서 바라본 주변의 풍경이 정말 아름다웠습니다. 비행 중에는 전문 강사님이 안전하게 안내해 주셔서 걱정 없이 즐길 수 있었습니다.
				비용은 기본 코스가 10만원으로, 공중 촬영이 포함된 메모리 코스는 13만원이었습니다. 비행복과 헬멧은 현장에서 제공되니 편안한 복장으로 가시면 됩니다. 꼭 한 번 도전해보세요!
				""";

			for (int i = 1; i <= 30; i++) {
				String title = titles[i % titles.length];
				String location = locations[i % locations.length];
				String content = String.format(contentTemplate, location);

				BoardEntity boardEntity = BoardEntity.builder()
					.title(title)
					.content(content)
					.location(location)
					.creatorMemberEntity(savedMemberEntity)
					.hearts(0L)
					.build();

				boardRepository.saveBoardEntity(boardEntity);
			}
		};
	}
}