package project.ticketlink.controller.travel;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Controller
@RequestMapping("/api")
public class OauthController {

    private static final String KAKAO_AUTH_URL = "https://kauth.kakao.com/oauth/authorize";
    private static final String CLIENT_ID = "6c5793171c6df658bf6c2d42b586ace9";
    private static final String REDIRECT_URI = "https://localhost:8080/oauth/callback";



    @GetMapping("/kakao/login")
    public String KakaoLogin(){

        String authUrl = KAKAO_AUTH_URL + "?response_type=code&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI;
        return "redirect:" + authUrl;
    }
    public String encodeRedirectUri(String uri) throws UnsupportedEncodingException {
        return URLEncoder.encode(uri, "UTF-8");
    }





}
