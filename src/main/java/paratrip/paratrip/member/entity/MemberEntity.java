package paratrip.paratrip.member.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import paratrip.paratrip.member.util.Gender;

@Entity
@Table(name = "MEMBER")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Builder(toBuilder = true)
public class MemberEntity {
	@Id
	@Column(name = "member_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberSeq;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String encodedPassword;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "birth", nullable = false)
	private String birth;

	@Column(name = "gender", nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(name = "profile_image", nullable = true)
	private String profileImage;

	public MemberEntity updatePassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
		return this;
	}

	public MemberEntity updateMemberEntity(String userId, String birth, Gender gender) {
		this.userId = userId;
		this.birth = birth;
		this.gender = gender;
		return this;
	}

	public MemberEntity updateMemberEntity(String userId, String birth, Gender gender, String profileImage) {
		this.userId = userId;
		this.birth = birth;
		this.gender = gender;
		this.profileImage = profileImage;
		return this;
	}

	public MemberEntity deleteMemberProfileImage() {
		this.profileImage = null;
		return this;
	}

	public MemberEntity updateKakaoProfileImage(String profileImage) {
		this.profileImage = profileImage;
		return this;
	}
}
