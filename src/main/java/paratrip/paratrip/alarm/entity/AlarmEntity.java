package paratrip.paratrip.alarm.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import paratrip.paratrip.alarm.utils.Type;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.core.base.BaseEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Entity
@Table(name = "ALARM")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Builder(toBuilder = true)
public class AlarmEntity extends BaseEntity {
	@Id
	@Column(name = "alarm_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long alarmSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_member_seq")
	private MemberEntity memberEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_member_seq")
	private MemberEntity ownerEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_seq")
	private BoardEntity boardEntity;

	@Enumerated(EnumType.STRING)
	private Type type;
}
