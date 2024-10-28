package project.ticketlink.controller.travel;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.Banner;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import project.ticketlink.DTO.LoginResp;
import project.ticketlink.Util.JwtUtil;

import java.net.http.HttpResponse;

@Controller
public class OauthRestController {


    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    // 애플리케이션의 Client ID (카카오 개발자 사이트에서 확인)
    private static final String CLIENT_ID = "6c5793171c6df658bf6c2d42b586ace9";
    // 애플리케이션의 Client Secret (카카오 개발자 사이트에서 확인, 필요 시 설정)
    private static final String CLIENT_SECRET = "VVYCRxOpHjscln7xpfK8nzi1YofAXpz8";
    // 리디렉션 URI (인증 후 카카오가 리디렉션할 URI)
    private static final String REDIRECT_URI = "https://localhost:8080/oauth/callback";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    private final JwtUtil jwtUtil;

    public OauthRestController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/oauth/callback")
    public String callback(@RequestParam String code, HttpServletResponse  response) {
        String AccesstokenK = getAccessToken(code);
        String userId = getUserId(AccesstokenK);
        String AccessToken = jwtUtil.generateToken(userId);


        Cookie cookie = new Cookie("AccessToken", AccessToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return "redirect:/none";


    }
    @GetMapping("/none")
    public String none() {
        return "layouts/None";
    }



    private String getAccessToken(String code) {
        String url = KAKAO_TOKEN_URL;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            return parseAccessToken(response.getBody());
        } catch (HttpClientErrorException e) {
            System.err.println("Error Response: " + e.getResponseBodyAsString());
            throw new RuntimeException("Failed to get access token");
        }
    }

    private String parseAccessToken(String responseBody) {
        // JSON 파싱
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        // 액세스 토큰 추출
        return jsonObject.get("access_token").getAsString();
    }

    private String getUserId(String accessToken) {
        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        return parseUserId(response.getBody()); // 사용자 ID 추출
    }



    private String parseUserId(String responseBody) {
        // JSON 파싱
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        // 사용자 ID 추출
        return jsonObject.get("id").getAsString();
    }

}
