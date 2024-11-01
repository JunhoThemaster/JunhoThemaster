package project.ticketlink.model.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ticketlink.model.travel.company.Seat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SeatRepository  extends JpaRepository<Seat,Long> {
    List<Seat> findAllByAircraftId(Long craftId);
    List<Seat> findAllByIdIn(List<Long> seatIds);
    List<Seat> findByReservedDate(LocalDateTime reservedDate);

}
