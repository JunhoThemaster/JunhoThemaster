package project.ticketlink.model.travel.company;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.ticketlink.model.travel.product.Flight;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "aircraft")
public class Aircraft {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aircraft_seq")
    @SequenceGenerator(name = "aircraft_seq", sequenceName = "AIRCRAFT_SEQ", allocationSize = 1)
    @Column(name = "aircraft_id")
    private Long id;

    @Column(name = "company", nullable = false)
    private String company;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "available")
    private boolean available;

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<FlightsInAircraft> flightInAircrafts = new ArrayList<>();


    // Constructor, getters, setters, toString()...
}

