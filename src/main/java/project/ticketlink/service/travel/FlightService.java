package project.ticketlink.service.travel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.ticketlink.model.travel.company.Aircraft;
import project.ticketlink.model.travel.product.Flight;
import project.ticketlink.model.travel.repository.AircraftRepository;
import project.ticketlink.model.travel.repository.FlightRepository;

import java.util.List;


@Service
public class FlightService {

     private final AircraftRepository aircraftRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(AircraftRepository aircraftRepository, FlightRepository flightRepository) {
         this.aircraftRepository = aircraftRepository;
        this.flightRepository = flightRepository;
    }


     public List<Aircraft> getAircraft(){

          return aircraftRepository.findAll();
     }



     @Transactional
     public void setFlight(Flight flight){
            flightRepository.save(flight);
     }


     public Aircraft getAircraftById(Long id){
        return aircraftRepository.getAircraftById(id);
     }
}
