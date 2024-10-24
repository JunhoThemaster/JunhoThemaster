package project.ticketlink.model.travel.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.ticketlink.model.member.Member;

import java.util.Map;

public interface MemberRepository extends JpaRepository<Member,Long> {

    boolean existsByMemId(String memId);

    Member findByMemId(String memId);




    @Query("SELECT m.memMail FROM Member m where m.memId = :id ")
    String getEmailById(@Param("id") String id);




}
