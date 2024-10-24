package project.ticketlink.model.member;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.ticketlink.model.travel.reservation.Reservation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "mem_seq")
    @SequenceGenerator(name="mem_seq" ,sequenceName = "MEM_SEQ" ,allocationSize = 1)
    @Column(name = "mem_no")
    private Long member_no;

    @Column(name = "mem_mail")
    private String memMail;

    @Column(name = "mem_name")
    private String memName;

    @Column(name = "mem_age")
    private int memAge;

    @Column(name = "mem_tel")
    private BigInteger memTel;

    @Column(name = "mem_id")
    private String memId;

    @Column(name = "mem_pw")
    private String memPw;

    @Column(name = "mem_isAdmin", nullable = true)
    private boolean memisAdmin;


    @OneToMany(mappedBy = "member" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reservation> reservationList;

//
//    @Column(name = "mem_type",nullable = true)
//    private String memType;











}
