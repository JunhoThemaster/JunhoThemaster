package project.ticketlink.controller.travel;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import project.ticketlink.Util.JwtUtil;

@RestController
public class SessionController {

    @Autowired
    private JwtUtil jwtUtil;


    @GetMapping("/api/session-info")
    public ResponseEntity<SessionInfo> getSessionInfo(HttpServletRequest request) {
        String AccessToken = null;
        String userId = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("AccessToken")) {
                    AccessToken = cookie.getValue();

                    userId = jwtUtil.extractId(AccessToken);
                }
            }
        }
        return ResponseEntity.ok(new SessionInfo(AccessToken, userId));
    }


    static class SessionInfo {
        private String accessToken;
        private String userId;

        public SessionInfo(String accessToken, String userId) {
            this.accessToken = accessToken;
            this.userId = userId;
        }

        public String getAccessToken() {
            return accessToken;
        }
        public String getUserId() {
            return userId;
        }
    }

}
