package Stream.project.stream.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
public class TokenRefreshRequest {
    @NotBlank
    private String refreshToken;
}
