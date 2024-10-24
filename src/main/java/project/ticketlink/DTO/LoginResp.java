package project.ticketlink.DTO;

public class LoginResp {

    private final String accessToken;
    private final String refreshToken;
    private final String userId;

    public LoginResp(String accessToken, String refreshToken, String userId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public String getUserId() {return  userId; }

}
