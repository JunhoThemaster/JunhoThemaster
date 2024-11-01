package project.ticketlink.service.travel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.ticketlink.model.travel.company.Aircraft;
import project.ticketlink.model.travel.company.FlightsInAircraft;
import project.ticketlink.model.travel.company.Seat;
import project.ticketlink.model.travel.product.Flight;
import project.ticketlink.model.travel.repository.AircraftRepository;
import project.ticketlink.model.travel.repository.FlightRepository;
import project.ticketlink.model.travel.repository.SeatRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class FlightSchedule {


    @Autowired
    private FlightRepository flightRepository;
    private SeatRepository seatRepository;
    private FlightsInAircraft flightsInAircraft;
    private AircraftRepository aircraftRepository;


//    @Transactional
//    @Scheduled(cron = "0 */5 * * * ?")  // 매일 자정에 실행
//    public void cleanUpExpiredFlights(){
//        LocalDateTime now = LocalDateTime.now();
//        List<Flight> flightToDel = flightRepository.findByDepartureTimeBefore(now);
//
//        for (Flight flight : flightToDel) {
//            List<FlightsInAircraft> flightsInAircrafts = flight.getFlightInAircrafts();
//            System.out.println("Deleting flight: " + flight.getId() + ", Arrival Time: " + flight.getArrivalTime());
//            for(FlightsInAircraft flightsInAircraft: flightsInAircrafts) {
//                Aircraft aircraft = flightsInAircraft.getAircraft();
//                for (Seat seat : aircraft.getSeats()) {
//                    seat.setReservedDate(null);
//                }
//                aircraftRepository.save(aircraft); // 항공기 상태를 저장합니다
//                System.out.println("Updated aircraft availability for Aircraft " + aircraft.getId());
//            }
//
//            flightRepository.delete(flight); // 항공편을 삭제합니다
//            System.out.println("Flight " + flight.getId() + " has been deleted.");
//        }
//    }
}
