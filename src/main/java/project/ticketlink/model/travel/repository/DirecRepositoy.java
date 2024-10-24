package project.ticketlink.model.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ticketlink.model.travel.director.Director;

import java.util.List;

public interface DirecRepositoy extends JpaRepository<Director, Integer> {

    List<Director> findAll();

    Director findByDirNo(Long no);
}
