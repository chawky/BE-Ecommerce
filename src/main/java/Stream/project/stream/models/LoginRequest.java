package Stream.project.stream.models;

public class LoginRequest {
    private String username;
    private String pw;

    public LoginRequest() {
    }

    public LoginRequest(String username, String pw) {
        super();
        this.username = username;
        this.pw = pw;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
