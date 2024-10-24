package project.ticketlink.model.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.ticketlink.model.travel.category.MainCategory;
import project.ticketlink.model.travel.category.MiddleCategory;
import project.ticketlink.model.travel.category.SubCategory;

import java.util.List;

public interface CateRepository extends JpaRepository<MiddleCategory,Long> {

    List<MiddleCategory> findByMainCategory(MainCategory mainCategory);
    @Query("SELECT sc FROM SubCategory sc WHERE sc.middleCategory.micNO = :micNo")
    List<SubCategory> findSubCategoriesByMicNo(@Param("micNo") Long micNo);
    MiddleCategory findByMicNO(Long micNo);



}
