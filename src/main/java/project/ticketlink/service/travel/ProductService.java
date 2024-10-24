package project.ticketlink.service.travel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.ticketlink.model.travel.company.Aircraft;
import project.ticketlink.model.travel.company.Seat;
import project.ticketlink.model.travel.product.Flight;
import project.ticketlink.model.travel.product.Product;
import project.ticketlink.model.travel.repository.AircraftRepository;
import project.ticketlink.model.travel.repository.FlightRepository;
import project.ticketlink.model.travel.repository.ProductRepository;
import project.ticketlink.model.travel.repository.SeatRepository;


import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final FlightRepository flightRepository;
    private final AircraftRepository aircraftRepository;
    private final SeatRepository seatRepository;
    @Autowired
    public ProductService(ProductRepository productRepository , FlightRepository flightRepository,AircraftRepository aircraftRepository,SeatRepository seatRepository) {
        this.aircraftRepository =aircraftRepository;
        this.productRepository = productRepository;
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;

    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsBySubCategory(Long sucNo) {
        return productRepository.findBySucNo_SucNo(sucNo);
    }

    public List<Flight> getFlightByProduct(Long prodNo){
        return flightRepository.findByProductProdNo(prodNo);
    }

    public Flight getFlightById(Long roundNo) {
        return  flightRepository.findFlightById(roundNo);
    }



    public Product getProduct(Long prodNo) {

        return productRepository.findByProdNo(prodNo);
    }


    public Aircraft getAircraft(Long flightId) {

        return aircraftRepository.findAircraftByFlightId(flightId);
    }

    public List<Seat> getSeatsByAircraftId(Long craftId) {
        return seatRepository.findAllByAircraftId(craftId);
    }

    public int remainSeats(Long aircraftId){
        List<Seat> availableSeats = seatRepository.findByAircraftIdAndIsAvailable(aircraftId, true);
        return  availableSeats.size();

    }
    @Transactional(readOnly = true)
    public List<Seat> findSeatById(List<Long> seatId) {
        return seatRepository.findAllByIdIn(seatId);
    }


    @Transactional
    public void deleteProduct(Long Id){

        productRepository.deleteById(Id);


    }


    @Transactional
    public void addProduct(Product product){
        productRepository.save(product);
    }





}
