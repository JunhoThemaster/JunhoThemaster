package project.ticketlink.controller.travel;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.ticketlink.model.member.Member;
import project.ticketlink.model.travel.company.Aircraft;
import project.ticketlink.model.travel.company.Seat;
import project.ticketlink.model.travel.product.Product;
import project.ticketlink.model.travel.reservation.Reservation;
import project.ticketlink.model.travel.reservation.ReservationRequest;
import project.ticketlink.service.travel.MemberService;
import project.ticketlink.service.travel.ProductService;
import project.ticketlink.service.travel.ReserveService;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping("/reservation")
public class ReserveController {

    private final ProductService productService;
    private final ReserveService reserveService;
    private final MemberService memberService;

    @Autowired
    public ReserveController(ProductService productService, ReserveService reserveService, MemberService memberService) {
        this.productService = productService;
        this.reserveService = reserveService;
        this.memberService = memberService;
    }

    private void addMemberToModel(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("user");
        if (userId != null) {
            model.addAttribute("userId", userId);
            Member member = memberService.getmemberId(userId);
            model.addAttribute("member", member);
        } else {
            model.addAttribute("userId", null);
        }
    }

    @GetMapping("/{flightId}/aircraft")
    public String showFlighttDetails(@PathVariable("flightId") Long flightId,
                                     @RequestParam("productNo") Long productNo,
                                     Model model , HttpSession session) {


        addMemberToModel(model, session);

        Product product = productService.getProduct(productNo);
        Aircraft aircraft = productService.getAircraft(flightId);
        model.addAttribute("aircraft", aircraft);
        model.addAttribute("flightId", flightId);
        model.addAttribute("product", product);


        return "travel/reservation/select";
    }

    @GetMapping("/status/{crid}/{prid}/{flightid}")

    public String getSeatByAircraftId(@PathVariable("crid") Long craftId,@PathVariable("prid") Long prodNo,@PathVariable("flightid") Long flightId,Model model,HttpSession session) {


        addMemberToModel(model, session);

        Product product = productService.getProduct(prodNo);
        List<Seat> seats = productService.getSeatsByAircraftId(craftId);
        int remainSeats = productService.remainSeats(craftId);
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
