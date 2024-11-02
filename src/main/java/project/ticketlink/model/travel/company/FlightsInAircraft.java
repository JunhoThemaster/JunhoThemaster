package project.ticketlink.model.travel.company;


import jakarta.persistence.*;
import lombok.Getter;
import project.ticketlink.model.travel.product.Flight;

@Entity
@Getter
@Table(name = "flights_in_aircraft")
public class FlightsInAircraft {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "flandair_seq")
    @SequenceGenerator(name = "flandair_seq" ,sequenceName = "flandair_seq",allocationSize = 1)
    @Column(name = "fl_id")
    private Long flid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;


    public void setFlight(Flight flight) {
        this.flight = flight;
        if (flight != null) {
            flight.getFlightInAircrafts().add(this); // Flight에 이 FlightsInAircraft를 추가
        }
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
        if (aircraft != null) {
            aircraft.getFlightInAircrafts().add(this); // Aircraft에 이 FlightsInAircraft를 추가
        }
    }



}
