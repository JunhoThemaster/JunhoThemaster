package project.ticketlink.model.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ticketlink.model.member.Member;
import project.ticketlink.model.travel.reservation.Reservation;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> getReservationByMember(Member member);

    Reservation getReservationByRvNo(Long id);




}
