package project.ticketlink.model.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.ticketlink.model.travel.company.Aircraft;
import project.ticketlink.model.travel.product.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight,Long> {

    List<Flight> findByProductProdNo(Long prodNo);

    Flight findFlightById(Long roundNo);


    List<Flight> findByDepartureTimeBefore(LocalDateTime dateTime);
}
