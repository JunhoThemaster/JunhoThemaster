package project.ticketlink.controller.travel;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.ticketlink.model.member.Member;
import project.ticketlink.model.member.MemberRequest;
import project.ticketlink.model.travel.category.MainCategory;
import project.ticketlink.model.travel.category.MiddleCategory;
import project.ticketlink.model.travel.category.SubCategory;
import project.ticketlink.model.travel.product.Flight;
import project.ticketlink.model.travel.product.Product;
import project.ticketlink.model.travel.repository.MemberRepository;
import project.ticketlink.service.travel.CateService;
import project.ticketlink.service.travel.MemberService;
import project.ticketlink.service.travel.ProductService;

import java.math.BigInteger;
import java.util.List;

@Controller
public class HomeController {



    private final MemberService memberService;

    public HomeController( MemberService memberService) {

        this.memberService = memberService;
    }
    @GetMapping("/")
    public String home(Model model, HttpSession session, HttpServletRequest request) {

        Boolean isLoggedIn = (Boolean) request.getAttribute("isLoggedIn");
        String userId = (String) request.getAttribute("userId");
        String kuserId = (String) session.getAttribute("member");

        // 로그인 상태 및 사용자 정보를 모델에 추가합니다.
        model.addAttribute("isLoggedIn", isLoggedIn != null && isLoggedIn);
        model.addAttribute("userId", userId);
        model.addAttribute("kuserId", kuserId);

        // 사용자 ID가 존재할 경우, 추가 정보를 조회하고 모델에 추가합니다.
        if (userId != null) {
            Member member = memberService.getmemberId(userId);
            model.addAttribute("member", member);
        }


        return "index";

    }






}
