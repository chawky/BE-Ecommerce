package Stream.project.stream.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String userName;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String pw) {
        super();
        this.userName = username;
        this.password = pw;
    }

}
