package project.ticketlink.model.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ticketlink.model.travel.company.Aircraft;

import java.util.List;
import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft,Long> {

    Aircraft findAircraftByFlightId(Long flightId);

    List<Aircraft> findAll();

    Aircraft findById(long id);

    Aircraft getAircraftById(long id);


}
