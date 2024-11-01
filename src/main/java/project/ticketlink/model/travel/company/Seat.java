package project.ticketlink.model.travel.company;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_seq")
    @SequenceGenerator(name = "seat_seq", sequenceName = "SEAT_SEQ", allocationSize = 1)
    @Column(name = "seat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(name = "is_available")
    private boolean isAvailable;


    @ElementCollection
    @CollectionTable(name="reservedDates",joinColumns = @JoinColumn(name = "seat_id"))
    @Column(name = "reservedDate")
    private List<LocalDateTime> reservedDate = new ArrayList<>();

    public void setDate(LocalDateTime date) {
        this.reservedDate.add(date);
    }

    public boolean isSeatReserved(LocalDateTime date) {
        return reservedDate.stream()
                .anyMatch(reservedDate -> reservedDate.toLocalDate().isEqual(date.toLocalDate()));
    }


}