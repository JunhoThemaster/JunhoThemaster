package project.ticketlink.controller.travel;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.ticketlink.DTO.LoginResp;
import project.ticketlink.Util.JwtUtil;
import project.ticketlink.model.member.Member;
import project.ticketlink.service.travel.MemberService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private  final JwtUtil jwtUtil;
    private final MemberService memberService;

    @Autowired
    public AuthController(JwtUtil jwtUtil, MemberService memberService) {
        this.jwtUtil = jwtUtil;
        this.memberService = memberService;
    }


    @PostMapping("/api/checkLogin")
    public ResponseEntity<LoginResp> checkLogin(
            @RequestHeader(value = "Authorization", required = false) String auth,
            @RequestBody Map<String, String> request) { // 요청 본문을 Map으로 받음

        String refreshToken = request.get("refreshToken");
        String newAccessToken = null;
        boolean loggedIn = false;
        String userId = null;
        boolean isAdmin = false;
        // 액세스 토큰 유효성 검사
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.replace("Bearer ", "");
            try {
                boolean isValid = jwtUtil.verifyToken(token);
                System.out.println(isValid);
                if (isValid) {
                    loggedIn = true;
                    userId = jwtUtil.extractId(token);
                    Member member = memberService.getmemberById(userId);
                    isAdmin = member.isMemisAdmin();
                    System.out.println(isAdmin);
                    System.out.println("userid is " + userId);
                    newAccessToken = token; // 기존 액세스 토큰 사용
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        }

        // 리프레시 토큰 유효성 검사
        if (!loggedIn && refreshToken != null) {
            try {
                if (jwtUtil.verifyToken(refreshToken)) {
                    userId = jwtUtil.extractId(refreshToken);
                    newAccessToken = jwtUtil.generateToken(userId);
                    loggedIn = true; // 리프레시 토큰이 유효하면 로그인 상태로 설정
                    Member member = memberService.getmemberById(userId);
                    isAdmin = member.isMemisAdmin();

                    System.out.println("리프레시 토큰이 유효합니다. 새로운 액세스 토큰 발급: " + newAccessToken);
                } else {
                    System.out.println("리프레시 토큰이 만료되었습니다.");
                }
            } catch (Exception e) {
                System.out.println("리프레시 토큰 검증 중 오류 발생: " + e.getMessage());
            }
        }



        // LoginResp 객체 생성하여 응답
        return ResponseEntity.ok(new LoginResp(newAccessToken, refreshToken, userId,isAdmin));

    }

//    @PostMapping("/api/refresh")
//    public ResponseEntity<Map<String, String>> refresh(@RequestBody Map<String, String> request ) {
//        String refTok = request.get("refreshToken");
//        Map<String, String> response = new HashMap<>();
//        System.out.println("ref = " + refTok);
//        if(refTok != null && jwtUtil.verifyToken(refTok)) {
//            String userId = jwtUtil.extractId(refTok);
//            String newAcTok = jwtUtil.generateToken(userId);
//            System.out.println("new ac" + newAcTok);
//            response.put("refreshToken", newAcTok);
//            return ResponseEntity.ok(response);
//        } else {
//            response.put("error", "Invalid refresh token");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//        }
//    }



//    @GetMapping("/{pageName}")
//    public String showPage(@PathVariable String pageName, @RequestHeader(value = "Authorization", required = false) String auth, Model model) {
//        // 로그인 상태 확인
//        boolean isLoggedIn = false;
//
//        if (auth != null && auth.startsWith("Bearer ")) {
//            String token = auth.replace("Bearer ", "");
//            try {
//                isLoggedIn = jwtUtil.verifyToken(token);
//            } catch (Exception e) {
//                // 예외 처리
//            }
//        }
//
//        model.addAttribute("isLoggedIn", isLoggedIn);
//        return pageName; // 페이지 이름에 해당하는 뷰를 반환
//    }

}
