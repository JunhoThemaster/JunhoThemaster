package project.ticketlink.controller.travel;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.ticketlink.Util.JwtUtil;
import project.ticketlink.model.member.Member;
import project.ticketlink.model.travel.company.Aircraft;
import project.ticketlink.model.travel.company.Seat;
import project.ticketlink.model.travel.product.Flight;
import project.ticketlink.model.travel.product.Product;
import project.ticketlink.model.travel.reservation.Reservation;
import project.ticketlink.model.travel.reservation.ReservationRequest;
import project.ticketlink.service.travel.FlightService;
import project.ticketlink.service.travel.MemberService;
import project.ticketlink.service.travel.ProductService;
import project.ticketlink.service.travel.ReserveService;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReserveController {

    private final ProductService productService;
    private final ReserveService reserveService;
    private final MemberService memberService;
    private final FlightService flightService;
    private final JwtUtil jwtUtil;

    @Autowired
    public ReserveController(ProductService productService, ReserveService reserveService, MemberService memberService, FlightService flightService, JwtUtil jwtUtil) {
        this.productService = productService;
        this.reserveService = reserveService;
        this.memberService = memberService;
        this.flightService = flightService;
        this.jwtUtil = jwtUtil;
    }



    @GetMapping("/flight/detail")
    public String showFlighttDetails(@RequestParam("fid") Long flightId,
                                     @RequestParam("pid") Long productNo,
                                     Model model ) {



        Product product = productService.getProduct(productNo);
        Aircraft aircraft = productService.getAircraft(flightId);
        model.addAttribute("aircraft", aircraft);
        model.addAttribute("flightId", flightId);
        model.addAttribute("product", product);


        return "travel/reservation/select";
    }

    @GetMapping("/status")

    public String getSeatByAircraftId(@RequestParam("aid") Long craftId,@RequestParam("pid") Long prodNo,@RequestParam("fid") Long flightId,Model model,
                                      @RequestHeader(value = "Authorization") String auth) {

        String userId = null;
        Member member = null;
        if (auth != null && auth.startsWith("Bearer ")){
            String token = auth.replace("Bearer ", "");
            try {
                boolean isValid = jwtUtil.verifyToken(token);
                System.out.println(isValid);
                if (isValid) {
                    userId = jwtUtil.extractId(token);
                    member = memberService.getmemberById(userId);
                }
            }catch (Exception e) {
                return "/member/login";
            }
        }
        System.out.println(userId);
        System.out.println(member);
        Product product = productService.getProduct(prodNo);
        List<Seat> seats = productService.getSeatsByAircraftId(craftId);

        Flight flight = productService.getFlightById(flightId);

        int reservedSeat = productService.remainSeats(flight.getDepartureTime());
        int remainSeats  = seats.size() - reservedSeat;
        model.addAttribute("member",member);
        model.addAttribute("flight", flight);
        model.addAttribute("product", product);
        model.addAttribute("seats",seats);
        model.addAttribute("flightId" ,flightId);
        model.addAttribute("remainSeats",remainSeats);

        return  "travel/reservation/detail";
    }


    //    @RequestMapping(value = "/result/{id}", produces = "application/json", method = RequestMethod.POST)
    @PostMapping(value = "/result", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequest request) {

        try {

            Reservation createdReservation = reserveService.saveReservation(request);
            System.out.println(request.getSeats());

            return ResponseEntity.ok(createdReservation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create reservation: " + e.getMessage());
        }
    }


}
