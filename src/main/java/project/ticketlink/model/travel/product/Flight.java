package project.ticketlink.model.travel.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.ticketlink.model.travel.company.Aircraft;
import project.ticketlink.model.travel.reservation.Reservation;

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

    @OneToOne(mappedBy = "flight", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private Aircraft aircraft;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;


    @PreRemove
    public void preRemove() {
        if (aircraft != null) {
            aircraft.setFlight(null);
        }
    }

    public void setAircraft(Aircraft aircraft) {
        if (this.aircraft != null) {
            this.aircraft.setFlight(null); // 기존 항공기의 Flight 참조를 제거
        }
        this.aircraft = aircraft;
        if (aircraft != null) {
            aircraft.setFlight(this); // 새로운 항공기에 현재 Flight 설정
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