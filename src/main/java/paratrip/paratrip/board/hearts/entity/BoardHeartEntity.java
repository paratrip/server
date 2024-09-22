package paratrip.paratrip.board.hearts.entity;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Entity
@Table(name = "BOARD_HEART")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Builder(toBuilder = true)
public class BoardHeartEntity {
	@Id
	@Column(name = "board_heart_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardHeartSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_seq")
	private MemberEntity memberEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_seq")
	private BoardEntity boardEntity;
}