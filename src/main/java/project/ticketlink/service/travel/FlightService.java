package project.ticketlink.service.travel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.ticketlink.model.travel.company.Aircraft;
import project.ticketlink.model.travel.company.FlightsInAircraft;
import project.ticketlink.model.travel.product.Flight;
import project.ticketlink.model.travel.repository.AircraftRepository;
import project.ticketlink.model.travel.repository.FlightRepository;
import project.ticketlink.model.travel.repository.FlightsInAircraftRepository;

import java.util.List;


@Service
public class FlightService {

    private final AircraftRepository aircraftRepository;
    private final FlightRepository flightRepository;
    private final FlightsInAircraftRepository flightsInAircraftRepository;
    @Autowired
    public FlightService(AircraftRepository aircraftRepository, FlightRepository flightRepository,FlightsInAircraftRepository flightsInAircraftRepository) {
        this.aircraftRepository = aircraftRepository;
        this.flightRepository = flightRepository;
        this.flightsInAircraftRepository = flightsInAircraftRepository;
    }


     public List<Aircraft> getAircraft(){

          return aircraftRepository.findAll();
     }



     @Transactional
     public void addFlight(Flight flight){
            flightRepository.save(flight);

            for(FlightsInAircraft flightsInAircraft : flight.getFlightInAircrafts()){
                flightsInAircraftRepository.save(flightsInAircraft);

            }
     }


     public Aircraft getAircraftById(Long id){
        return aircraftRepository.getAircraftById(id);
     }

}
