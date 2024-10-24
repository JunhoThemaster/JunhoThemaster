package project.ticketlink.model.travel.reservation;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.ticketlink.model.member.Member;
import project.ticketlink.model.travel.product.Flight;
import project.ticketlink.model.travel.product.Product;
import project.ticketlink.model.travel.company.Seat;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity

@Getter @Setter
@Table(name="reservation")

public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "rev_seq")
    @SequenceGenerator(name = "rev_seq",sequenceName = "REV_SEQ")
    @Column(name="rv_no")
    private Long rvNo;


    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "reservation_seat",
            joinColumns = @JoinColumn(name = "rv_no"),
            inverseJoinColumns = @JoinColumn(name = "seat_id")
    )
    private List<Seat> seats;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_no", nullable = false)
    private Product product;




    @Column(name="rv_cnt1")
    private Integer rvCnt1;

    @Column(name="rv_cnt2")
    private Integer rvCnt2;

    @Column(name="rv_cnt3")
    private Integer rvCnt3;

    @Column(name="rv_cnt4")
    private Integer rvCnt4;

    @Column(name="add_req")
    private String addReq;

    @Column(name="rv_totpr")
    private BigInteger rvTot;

    @Column(name="rv_date")
    private LocalDateTime rv_date;



}
