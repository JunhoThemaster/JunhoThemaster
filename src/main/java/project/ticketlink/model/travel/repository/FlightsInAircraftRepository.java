package project.ticketlink.model.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.ticketlink.model.travel.company.FlightsInAircraft;


public interface FlightsInAircraftRepository extends JpaRepository<FlightsInAircraft, Long> {
    // 추가적인 쿼리 메서드가 필요하다면 여기에서 정의할 수 있습니다.
}
