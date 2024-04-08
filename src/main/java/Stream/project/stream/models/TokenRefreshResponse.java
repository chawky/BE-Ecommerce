package Stream.project.stream.models;

import lombok.Data;

@Data
public class TokenRefreshResponse {
    private String token;
    private String refreshToken;
    private String tokenType = "Bearer";

    public TokenRefreshResponse(String accessToken, String refreshToken) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
    }
}
