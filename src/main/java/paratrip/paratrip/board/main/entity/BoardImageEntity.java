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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BOARD_IMAGE")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Builder(toBuilder = true)
public class BoardImageEntity {
	@Id
	@Column(name = "board_image_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardImageSeq;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_seq")
	private BoardEntity boardEntity;

	@Column(name = "image_url")
	private String imageURL;
}
