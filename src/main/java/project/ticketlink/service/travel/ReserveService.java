package project.ticketlink.service.travel;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.ticketlink.model.member.Member;
import project.ticketlink.model.travel.company.Seat;
import project.ticketlink.model.travel.product.Flight;
import project.ticketlink.model.travel.product.Product;
import project.ticketlink.model.travel.repository.*;
import project.ticketlink.model.travel.reservation.Reservation;
import project.ticketlink.model.travel.reservation.ReservationRequest;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hibernate.internal.CoreLogging.logger;

@Service
public class ReserveService {


    private static final Logger logger = LoggerFactory.getLogger(ReserveService.class);
    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final ProductRepository productRepository;
    private final SeatRepository seatRepository;
    private final MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager entityManager;



    @Autowired
    public ReserveService(ReservationRepository reservationRepository, FlightRepository flightRepository, ProductRepository productRepository, SeatRepository seatRepository, MemberRepository memberRepository) {
        this.reservationRepository = reservationRepository;
        this.flightRepository = flightRepository;
        this.productRepository = productRepository;
        this.seatRepository = seatRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Reservation saveReservation(ReservationRequest request) {

        logger.info("Starting saveReservation");

        Reservation reservation = new Reservation();



        Member member = memberRepository.findByMemId(request.getMemId());


        reservation.setMember(member);

        Flight flight = flightRepository.findFlightById(request.getFlightId());

        reservation.setFlight(flight);



        Product product = productRepository.findByProdNo(request.getProductId());

        reservation.setProduct(product);

        List<Long> seatIds = request.getSeats();

        List<Seat> seats = seatRepository.findAllByIdIn(seatIds);
        System.out.println(seats.size());

        for(Seat seat : seats){

            seat.setDate(flight.getDepartureTime());
        };


        reservation.setRvCnt1(request.getRvCnt1());
        reservation.setRvCnt2(request.getRvCnt2());
        reservation.setRvCnt3(request.getRvCnt3());
        reservation.setRvCnt4(reservation.getRvCnt4());
        reservation.setAddReq(request.getAddReq());
        reservation.setRvTot(request.getRvTot());
        reservation.setRv_date(LocalDateTime.now());
        reservation.setSeats(seats);


        logger.info("Reservation saved successfully");

        return reservationRepository.save(reservation);

    }


    public List<Reservation> getReservations(Member member) {
        return reservationRepository.getReservationByMember(member);
    }
    @Transactional
    public void deleteReservation(Long rvNo) {
        logger.debug("Attempting to delete reservation with ID: {}", rvNo);

        Reservation reservation = entityManager.find(Reservation.class, rvNo);
        if (reservation == null) {
            logger.error("Reservation not found with ID: {}", rvNo);
            throw new IllegalArgumentException("Invalid reservation Id: " + rvNo);
        }

        if (reservation.getFlight() == null) {
            logger.error("Reservation with ID: {} has null flight field.", rvNo);
            throw new IllegalStateException("Reservation with ID: " + rvNo + " has null flight field.");
        }

        List<Seat> seats = reservation.getSeats();
        for (Seat seat : seats) {
            logger.debug("Updating seat with ID: {}", seat.getId());
            seat.setReservedDate(null);
            seat.setAvailable(true);
            entityManager.merge(seat);
            logger.debug("Updated seat with ID: {}", seat.getId());
        }

        try {
            entityManager.remove(reservation);
            logger.debug("Deleted reservation with ID: {}", rvNo);
        } catch (Exception e) {
            logger.error("Failed to delete reservation with ID: {}", rvNo, e);
        }
    }


}
