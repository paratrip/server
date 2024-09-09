package paratrip.paratrip.home.paragliding.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import paratrip.paratrip.core.base.BaseEntity;

import java.util.List;

/**
 * packageName    : paratrip.paratrip.paraglidiing.entity
 * fileName       : Paragliding
 * author         : tlswl
 * date           : 2024-09-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-09-09        tlswl       최초 생성
 */
@Entity
@Table(name = "paragliding")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
public class Paragliding extends BaseEntity {
    @Id
    @Column(name = "paragliding_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paraglidingSeq;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "region", nullable = false)
    @Enumerated(EnumType.STRING)
    private Region region;

    @Column(name = "tell_number", nullable = false)
    private String tellNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "page_url", nullable = false)
    private String pageUrl;

    @Column(name = "opening_hour", nullable = false)
    private String openingHour;

    @Column(name = "cost", nullable = false)
    private Double cost;

    @Column(name="closed_days", nullable = false)
    private String closedDays;

    @Builder.Default
    @Column(name="parking_lot", nullable = false)
    private boolean parkingLot=false;

    //유모차
    @Builder.Default
    @Column(name="stroller" , nullable = false)
    private boolean stroller=false;

    @Builder.Default
    @Column(name="credit_card", nullable = false)
    private boolean creditCard=false;

    @Column(name="description",nullable = false, columnDefinition = "LONGTEXT")
    @Lob
    private String description;

    @Column(name="heart",nullable = false)
    private int heart;

    @Column(name="latitude",nullable = false)
    private long latitude;

    @Column(name="longitude",nullable = false)
    private long longitude;

    @Column(name="ticket", nullable = false)
    @ElementCollection(fetch=FetchType.LAZY)
    private List<String> tickets;

}