package project.ticketlink.controller.travel;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.ticketlink.model.member.Member;
import project.ticketlink.model.travel.category.MiddleCategory;
import project.ticketlink.model.travel.category.SubCategory;
import project.ticketlink.model.travel.company.Aircraft;
import project.ticketlink.model.travel.company.Seat;
import project.ticketlink.model.travel.product.Flight;
import project.ticketlink.model.travel.product.Product;
import project.ticketlink.model.travel.reservation.Reservation;
import project.ticketlink.service.travel.CateService;
import project.ticketlink.service.travel.MemberService;
import project.ticketlink.service.travel.ProductService;
import project.ticketlink.service.travel.ReserveService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/travel")
public class ProdController {


    @Autowired
    private final ProductService productService;
    private final ReserveService reserveService;
    private final CateService cateService;
    @Autowired
    private MemberService memberService;

    public ProdController(ProductService productService
    , ReserveService reserveService,CateService cateService) {
        this.cateService = cateService;
        this.reserveService = reserveService;
        this.productService = productService;
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


    @GetMapping("/home")
    public String travelHome(Model model ,HttpSession session) {
        addMemberToModel(model, session);

        return "travel/home";
    }
    @GetMapping("/package")
    public String packages(@RequestParam("no") Long no, Model model,HttpSession session) {

        addMemberToModel(model, session);


        List<MiddleCategory> middleCategories = cateService.getMiddleCategoriesByCategory(no);
        List<Product> products = productService.getAllProducts();

        model.addAttribute("products", products);
        model.addAttribute("middlecategories",middleCategories);
        return "travel/list";
    }

    @GetMapping("/{micPk}")
    public ResponseEntity<List<SubCategory>> getProductsByCity(@PathVariable Long micPk) {
        List<SubCategory> subCategories =cateService.findAllSubCategoriesByCategory(micPk);

        return ResponseEntity.ok(subCategories);
    }

    @GetMapping("/product/{sucId}")
    public ResponseEntity<List<Product>> getProductById(@PathVariable Long sucId) {
        List<Product> products = productService.getProductsBySubCategory(sucId);
        System.out.println(products);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/round/{prodNo}")
    @ResponseBody
    public ResponseEntity<List<Flight>> getProductByRound(@PathVariable Long prodNo) {
        List<Flight> flights = productService.getFlightByProduct(prodNo);

        System.out.println(flights);
        flights.forEach(flight -> {
            System.out.println(flight.toString());

        });
        return ResponseEntity.ok(flights);


    }



    @GetMapping("/flight/details/prod")

    public String getProductDetails(@RequestParam Long no,
                                    @RequestParam Long roundNo, Model model, HttpSession session) {
        addMemberToModel(model, session);
        Product product = productService.getProduct(no);
        Flight flight = productService.getFlightById(roundNo);
        model.addAttribute("product", product);
        model.addAttribute("flight", flight);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm");

        model.addAttribute("formatter", formatter);


        System.out.println(product);

        return "travel/details";

    }




}
