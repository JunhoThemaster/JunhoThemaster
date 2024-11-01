package project.ticketlink.model.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.ticketlink.model.travel.product.Flight;
import project.ticketlink.model.travel.product.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll(); // 모든 상품을 조회하는 메서드
    List<Product> findBySucNo_SucNo(Long sucNo);
    Product findByProdNo(Long prodNo);


}
