package project.ticketlink.controller.travel;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.Banner;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class OauthRestController {


    private static final String KAKAO_TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    // 애플리케이션의 Client ID (카카오 개발자 사이트에서 확인)
    private static final String CLIENT_ID = "6c5793171c6df658bf6c2d42b586ace9";
    // 애플리케이션의 Client Secret (카카오 개발자 사이트에서 확인, 필요 시 설정)
    private static final String CLIENT_SECRET = "VVYCRxOpHjscln7xpfK8nzi1YofAXpz8";
    // 리디렉션 URI (인증 후 카카오가 리디렉션할 URI)
    private static final String REDIRECT_URI = "http://localhost:8080/oauth/callback";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
    @GetMapping("/oauth/callback")
    public RedirectView callback(@RequestParam String code, HttpSession session, Model model) {
        try {
            String url = KAKAO_TOKEN_URL + "?grant_type=authorization_code" +
                    "&client_id=" + CLIENT_ID +
                    "&client_secret=" + CLIENT_SECRET +
                    "&redirect_uri=" + REDIRECT_URI +
                    "&code=" + code;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/x-www-form-urlencoded");

            HttpEntity<String> request = new HttpEntity<>(null, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                String accessToken = jsonNode.get("access_token").asText();


                HttpHeaders userInfoheaders = new HttpHeaders();
                userInfoheaders.set("Authorization", "Bearer " + accessToken);

                HttpEntity<String> userInfoRequest = new HttpEntity<>(userInfoheaders);
                ResponseEntity<String> userInfoResponse = restTemplate.exchange(KAKAO_USER_INFO_URL, HttpMethod.GET, userInfoRequest, String.class);


                if(userInfoResponse.getStatusCode() == HttpStatus.OK){
                    String userInfoResponseBody = userInfoResponse.getBody();
                    JsonNode userInfoJsonNode = objectMapper.readTree(userInfoResponseBody);

                    String Id = userInfoJsonNode.get("id").asText();
                    JsonNode properties = userInfoJsonNode.get("properties");
                    String email = userInfoJsonNode.path("kakao_account").path("email").asText();
                    String nickname =  properties != null ? properties.get("nickname").asText() : "N/A";

                    session.setAttribute("member", Id);
                    session.setAttribute("userNickname", nickname);
                    System.out.println(Id);
                    model.addAttribute("member",Id);
                }

                session.setAttribute("access_token", accessToken);

                return new RedirectView("/");
            } else {
                return new RedirectView("/member/login");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new RedirectView("/login?error=true");
        }
    }

    private String extractToken(String responsebody){
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(responsebody);
            return jsonNode.get("access_token").asText();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getUserInfo(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    private JsonNode parseUserInfo(String userInfo) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
