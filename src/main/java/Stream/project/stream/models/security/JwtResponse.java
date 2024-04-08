package Stream.project.stream.models.security;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String refreshToken;
    private String email;
    private List<String> role;
    public JwtResponse(String token,String refreshToken,  Long id, String username, String email, List<String> role) {
        super();
        this.token = token;
        this.id = id;
        this.username = username;
        this.refreshToken = refreshToken;
        this.email = email;
        this.role = role;
    }
    public JwtResponse() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<String> getRole() {
        return role;
    }
    public void setRole(List<String> role) {
        this.role = role;
    }


}