package project.ticketlink.model.travel.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.ticketlink.model.travel.company.Aircraft;
import project.ticketlink.model.travel.company.FlightsInAircraft;
import project.ticketlink.model.travel.reservation.Reservation;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_seq")
    @SequenceGenerator(name = "flight_seq", sequenceName = "FLIGHT_SEQ", allocationSize = 1)
    @Column(name = "flight_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_no")
    @JsonIgnore
    private Product product;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<FlightsInAircraft> flightInAircrafts = new ArrayList<>();


    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;


    @Column(name = "pack_pr1", nullable = false)
    private BigInteger packPr1;

    @Column(name = "pack_pr4")
    private BigInteger packPr4;

    @Column(name = "pack_pr3")
    private BigInteger packPr3;

    @Column(name = "pack_pr2")
    private BigInteger packPr2;



    @PreRemove
    public void preRemove() {
        for (FlightsInAircraft flightInAircraft : flightInAircrafts) {
            flightInAircraft.setFlight(null); // 비행이 삭제될 때 관련된 FlightAircraft에서 Flight 참조를 제거
        }
    }




    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                '}';
    }
    // Constructor, getters, setters, toString()...
}