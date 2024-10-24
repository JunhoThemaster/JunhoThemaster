package project.ticketlink.model.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.ticketlink.model.travel.category.SubCategory;

public interface SubCateRepository extends JpaRepository<SubCategory,Long> {

    SubCategory findBySucNo(Long no);
}
