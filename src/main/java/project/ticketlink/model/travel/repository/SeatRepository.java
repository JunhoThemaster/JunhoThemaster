package project.ticketlink.model.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ticketlink.model.travel.company.Seat;

import java.util.List;

public interface SeatRepository  extends JpaRepository<Seat,Long> {
    List<Seat> findAllByAircraftId(Long craftId);
    List<Seat> findByAircraftIdAndIsAvailable(Long aircraftId, boolean isAvailable);
    List<Seat> findAllByIdIn(List<Long> seatIds);


}
