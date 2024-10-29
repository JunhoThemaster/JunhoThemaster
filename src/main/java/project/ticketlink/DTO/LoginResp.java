package project.ticketlink.DTO;

public class LoginResp {

    private final String accessToken;
    private final String refreshToken;
    private final String userId;
    private final Boolean isAdmin;

    public LoginResp(String accessToken, String refreshToken, String userId,Boolean isAdmin) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.isAdmin = isAdmin;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public String getUserId() {
        return  userId;
    }
    public Boolean getIsAdmin() {
        return isAdmin;
    }
}
