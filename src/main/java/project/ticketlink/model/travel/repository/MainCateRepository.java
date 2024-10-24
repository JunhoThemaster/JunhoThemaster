package project.ticketlink.model.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ticketlink.model.travel.category.MainCategory;

import java.util.List;

public interface MainCateRepository extends JpaRepository<MainCategory, Integer> {
    List<MainCategory> findAll();
    MainCategory findByMacNo(Long id);
}
