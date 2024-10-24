package project.ticketlink.controller.travel;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.ticketlink.DTO.LoginResp;
import project.ticketlink.Util.JwtUtil;
import project.ticketlink.model.member.Member;
import project.ticketlink.model.travel.product.Product;
import project.ticketlink.model.travel.repository.MemberRepository;
import project.ticketlink.model.travel.reservation.Reservation;
import project.ticketlink.service.travel.MemberService;
import project.ticketlink.service.travel.ProductService;
import project.ticketlink.service.travel.ReserveService;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final ReserveService reserveService;
    private final ProductService productService;
    private final JwtUtil jwtUtil;


    @Autowired
    public MemberController(MemberService memberService, ReserveService reserveService, ProductService productService, JwtUtil jwtUtil) {
        this.productService = productService;
        this.memberService = memberService;
        this.reserveService = reserveService;
        this.jwtUtil = jwtUtil;
    }


    @GetMapping("/register")
    public String register() {

        return "member/register";
    }

    @GetMapping("/login")
    public String login() {

        return "member/login";
    }

    @PostMapping("/logresult")
    public ResponseEntity<LoginResp> logresult(@RequestParam("ID") String id,
                                               @RequestParam("Pw") String pw) {
        try {
            Member member = memberService.login(id, pw);
            if (member != null) {
                String accessToken = memberService.genToken(member);
                String refreshToken = memberService.refreshToken(member);

                return ResponseEntity.ok(new LoginResp(accessToken, refreshToken,id));
            } else {
                // 로그인 실패
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResp(null, null ,null));
            }
        } catch (Exception e) {
            // 예외 발생 시
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LoginResp(null, null , null));
        }
    }


    @PostMapping("/join")
    public String registerResult(@RequestParam("Email") String email,
                                 @RequestParam("PW") String pw,
                                 @RequestParam("ID") String id,
                                 @RequestParam("NAME") String name,
                                 @RequestParam("TEL") BigInteger tel,
                                 @RequestParam("AGE") int age) {



        boolean isRegistered = memberService.joinmember(id, pw, name, tel, email, age);

        if (isRegistered) {
            // 가입 성공 시, 다른 페이지로 리다이렉트
            return "/member/login"; // 가입 성공 후 이동할 페이지 URL
        } else {
            // 가입 실패 시, 에러 메시지와 함께 원래 페이지로 리다이렉트

            return "redirect:/register"; // 가입 실패 시 이동할 페이지 URL
        }

    }





    @GetMapping("/mypage")
    public String memberPage(@RequestHeader(value = "Authorization") String auth, Model model) {


        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.replace("Bearer ", "");
            try {
                System.out.println("유효성" + jwtUtil.verifyToken(token));
                String userId = jwtUtil.extractId(token);
                String email = memberService.getMemberinfo(userId);
                model.addAttribute("email", email);
                System.out.println("이메일"+email);
                model.addAttribute("userId", userId);
                return "member/mypage1"; // 정상적인 경우
            } catch (Exception e) {
                // 토큰 검증 실패나 사용자 정보 조회 실패
                model.addAttribute("error", "유효하지 않은 토큰입니다. 다시 로그인 해주세요.");
                return "error"; // 에러 페이지 또는 로그인 페이지로 리다이렉션
            }
        }

        model.addAttribute("error", "로그인이 필요합니다.");
        return "/member/login"; // 로그인 페이지로 리다이렉션

    }


    @GetMapping("/mypage/reservelist")
    public String myPage(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("user");
        model.addAttribute("userId",userId);

        if(userId != null) {
            Member member = memberService.getmemberId(userId);
            model.addAttribute("member",member);

            List<Reservation> reservations = reserveService.getReservations(member);
            model.addAttribute("reservations", reservations);

        }else{
            return "member/login";
        }



        return "member/mypage";
    }

    @PostMapping("/mypage/cancelReservation")
    public String cancelReservation(@RequestParam("reservationId") Long rvNo, Model model, HttpSession session) {

        String userId = (String) session.getAttribute("user");
        model.addAttribute("userId",userId);



        try {
            reserveService.deleteReservation(rvNo);

        } catch (Exception e){
            System.out.println("예매취소중 오류 발생");

        }


        if(userId != null) {
            Member member = memberService.getmemberId(userId);
            model.addAttribute("member", member);
        };

        return "redirect:/member/mypage";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }




}
