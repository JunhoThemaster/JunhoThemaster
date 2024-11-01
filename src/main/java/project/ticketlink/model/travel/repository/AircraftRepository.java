package project.ticketlink.model.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.ticketlink.model.travel.company.Aircraft;

import java.util.List;
import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft,Long> {



    List<Aircraft> findAll();

    Aircraft findById(long id);

    Aircraft getAircraftById(long id);


    @Query("SELECT fia.aircraft FROM FlightsInAircraft fia WHERE fia.flight.id = :flightId")
    Aircraft findAircraftByFlightId(@Param("flightId") Long flightId);

}
