package paratrip.paratrip.board.main.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import paratrip.paratrip.core.base.BaseEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Entity
@Table(name = "BOARD")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Builder(toBuilder = true)
public class BoardEntity extends BaseEntity {
	@Id
	@Column(name = "board_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardSeq;

	@Column(name = "title", nullable = false)
	private String title;

	@Lob
	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "location", nullable = true)
	private String location;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_seq")
	private MemberEntity memberEntity;

	public BoardEntity updateBoardEntity(String title, String content, String location) {
		this.title = title;
		this.content = content;
		this.location = location;
		return this;
	}
}
