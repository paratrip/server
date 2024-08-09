package paratrip.paratrip.comment.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.core.base.BaseEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Entity
@Table(name = "COMMENT")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Builder(toBuilder = true)
public class CommentEntity extends BaseEntity {
	@Id
	@Column(name = "comment_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_seq")
	private BoardEntity boardEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_seq")
	private MemberEntity memberEntity;

	@Column(name = "comment", nullable = false)
	private String comment;

	public CommentEntity updateCommentEntity(String comment) {
		this.comment = comment;
		return this;
	}
}
