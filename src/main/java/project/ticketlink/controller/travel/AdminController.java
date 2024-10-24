package project.ticketlink.controller.travel;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.ticketlink.model.member.Member;
import project.ticketlink.model.travel.category.MainCategory;
import project.ticketlink.model.travel.category.MiddleCategory;
import project.ticketlink.model.travel.category.SubCategory;
import project.ticketlink.model.travel.company.Aircraft;
import project.ticketlink.model.travel.director.Director;
import project.ticketlink.model.travel.product.Flight;
import project.ticketlink.model.travel.product.Product;
import project.ticketlink.service.travel.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final MemberService memberService;
    private final ProductService  productService;
    private final CateService cateService;
    private final DirService dirService;
    private final FlightService flightService;

    @Autowired
    public AdminController(MemberService memberService,ProductService productService,CateService cateService,DirService dirService,FlightService flightService) {
        this.flightService = flightService;
        this.cateService = cateService;
        this.productService = productService;
        this.memberService = memberService;
        this.dirService = dirService;


    }





    private Member addMemberToModel(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("user");
        if (userId != null) {
            model.addAttribute("userId", userId);
            Member member = memberService.getmemberId(userId);
            model.addAttribute("member", member);
            return member;
        } else {
            model.addAttribute("userId", null);
            return null;
        }
    }

    @GetMapping("")
    public String adminpage(Model model, HttpSession session) {

        Member member = addMemberToModel(model, session);
        String userId = (String) session.getAttribute("user");
        if (userId == null) {
            return "redirect:/member/login";
        }


        if( member == null || !member.isMemisAdmin() ){

            return "redirect:/member/login";

        }







        return "/member/adminpage";
    }


    @GetMapping("/update")
    public String updatemember(Model model , HttpSession session) {


        Member member = addMemberToModel(model, session);
        String userId = (String) session.getAttribute("user");
        if (userId == null) {
            return "redirect:/member/login";
        }


        if( member == null || !member.isMemisAdmin() ){

            return "redirect:/member/login";

        }



        List<Product> products = productService.getAllProducts();

        model.addAttribute("products", products);


        return "/member/update";
    }


    @PostMapping("/product/delete")
    public String deleteProduct(@RequestParam("prodno") Long id, Model model) {
        try {
            productService.deleteProduct(id);
            model.addAttribute("message", "Product deleted successfully.");
        } catch (Exception e) {
            model.addAttribute("message", "Failed to delete product.");
        }
        return "redirect:/admin/update"; // 삭제 후 리다이렉트할 페이지



    }

    @GetMapping("/add")
    public String addProduct(Model model ,HttpSession session) {
        //페이지
        List<MainCategory> mainCategory = cateService.findAllMainCategories();
        model.addAttribute("mainCategory", mainCategory);

        List<Director> directors = dirService.getDirectors();

        model.addAttribute("directors", directors);

        Member member = addMemberToModel(model, session);
        String userId = (String) session.getAttribute("user");
        if (userId == null) {
            return "redirect:/member/login";
        }


        if( member == null || !member.isMemisAdmin() ){

            return "redirect:/member/login";

        }
        return "/member/add";

    }


    @GetMapping("/product/add")
    public String addPr(@RequestParam("packname") String packname,
                        @RequestParam("packprice1") BigInteger packprice1,
                        @RequestParam("packprice4") BigInteger packprice4,
                        @RequestParam("packperiod") String packperiod,
                        @RequestParam("numppl") BigInteger numppl,
                        @RequestParam("maincategory") Long maincate,
                        @RequestParam("subcategory") Long subcate,
                        @RequestParam("packType") Product.PackType packType,
                        @RequestParam("city") Long city,
                        @RequestParam("chk") String chk,
                        @RequestParam("direc") Long direc
            , Model model , HttpSession session) {

        Member member = addMemberToModel(model, session);

        String userId = (String) session.getAttribute("user");

        if (userId == null) {
            return "redirect:/member/login";
        }

        if( member == null || !member.isMemisAdmin() ){
            return "redirect:/member/login";
        }

        Product product = new Product();

        MainCategory maincate1 = cateService.findMainCate(maincate);
        product.setMacNo(maincate1);

        MiddleCategory midcate =cateService.findMiddleCate(subcate);
        product.setMicNo(midcate);

        SubCategory subcate1 = cateService.findSubCate(city);
        product.setSucNo(subcate1);

        Director director = dirService.getDirbyId(direc);

        product.setDirecNo(director);

        product.setNumPeople(numppl);
        product.setPackPr1(packprice1);
        product.setPackPr2(packprice4);
        product.setPackPeriod(packperiod);
        product.setPackType(packType);
        product.setPackName(packname);
        product.setChk(chk);



        productService.addProduct(product);

        return  "redirect:/admin/add";





    }


    @GetMapping("/flight/add")

    public String addFlightPage(Model model ,HttpSession session) {

        Member member = addMemberToModel(model, session);

        String userId = (String) session.getAttribute("user");



        if (userId == null) {
            return "redirect:/member/login";
        }

        if( member == null || !member.isMemisAdmin() ){
            return "redirect:/member/login";
        }

        List<Product> products = productService.getAllProducts();

        model.addAttribute("products", products);

        return "member/addfllist";


    }

    @GetMapping("/product/addfl")
    public String addFlight(@RequestParam("pdn") Long prodNo,Model model ,HttpSession session) {

        Member member = addMemberToModel(model, session);

        String userId = (String) session.getAttribute("user");

        List<Aircraft> aircrafts = flightService.getAircraft();

        Product product = productService.getProduct(prodNo);

        model.addAttribute("product" , product);
        model.addAttribute("aircrafts", aircrafts);

        if (userId == null) {
            return "redirect:/member/login";
        }


        if( member == null || !member.isMemisAdmin() ){
            return "redirect:/member/login";
        }


        return "member/addflpage";

    }

    @GetMapping("/product/assignfl")

    public String assignFlight(@RequestParam("pdn") Long prodNo,
                               @RequestParam("dptime")LocalDateTime dptime,
                               @RequestParam("artime") LocalDateTime artime,
                               @RequestParam("aircrafts") Long id,
                               Model model , HttpSession session) {

        try {
            // 제품 조회
            Product product = productService.getProduct(prodNo);
            if (product == null) {
                model.addAttribute("message", "Product not found.");
                return "member/errorPage"; // 에러 페이지로 리다이렉트
            }

            // 비행 정보 설정
            Flight flight = new Flight();
            flight.setProduct(product);
            flight.setArrivalTime(artime);
            flight.setDepartureTime(dptime);

            // 항공기 조회
            Aircraft aircraft = flightService.getAircraftById(id);
            if (aircraft == null) {
                model.addAttribute("message", "Aircraft not found.");
                return "member/errorPage"; // 에러 페이지로 리다이렉트
            }
            flight.setAircraft(aircraft);

            // 비행 설정
            flightService.setFlight(flight);

            // 성공 메시지 추가
            model.addAttribute("message", "회차 추가 완료.");
            return "member/succes"; // 성공 페이지로 리다이렉트

        } catch (Exception e) {
            // 예외 발생 시 처리
            model.addAttribute("message", "An error occurred: " + e.getMessage());
            return "member/errorPage"; // 에러 페이지로 리다이렉트
        }




    }




    @GetMapping("/product/cate")

    public ResponseEntity <List<MiddleCategory>> getmidcateByMain(@RequestParam("maincateId") Long maincateId){

        List<MiddleCategory> midcate = cateService.getMiddleCategoriesByCategory(maincateId);

        return ResponseEntity.ok(midcate);



    }

    @GetMapping("/product/subcate")
    public ResponseEntity <List<SubCategory>> getSubCateByMid(@RequestParam("midcateId") Long midcateId){

        List<SubCategory> subcate = cateService.findAllSubCategoriesByCategory(midcateId);

        return ResponseEntity.ok(subcate);
    }




}
