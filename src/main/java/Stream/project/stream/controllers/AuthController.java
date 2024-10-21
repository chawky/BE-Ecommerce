package Stream.project.stream.controllers;

import Stream.project.stream.configs.TokenRefreshException;
import Stream.project.stream.models.LoginRequest;
import Stream.project.stream.models.RefreshToken;
import Stream.project.stream.models.TokenRefreshRequest;
import Stream.project.stream.models.TokenRefreshResponse;
import Stream.project.stream.models.User;
import Stream.project.stream.models.security.JwtResponse;
import Stream.project.stream.common.JwtUtils;
import Stream.project.stream.repositories.RoleRepo;
import Stream.project.stream.repositories.UserRepo;
import Stream.project.stream.services.RefreshTokenService;
import Stream.project.stream.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
public class AuthController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserServices userSerive;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> autheticateUser(@RequestBody LoginRequest loginRequest)  {
        JwtResponse jwt = userSerive.loginUser(loginRequest);

        if (Objects.isNull(jwt)){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Bad credentials");
        }else {
            return ResponseEntity.ok(new JwtResponse(jwt.getToken(),jwt.getRefreshToken(), jwt.getId(), jwt.getUsername(),
                    jwt.getEmail(), jwt.getRole()));
        }

    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        RefreshToken refreshToken=  refreshTokenService.findByToken(requestRefreshToken);
        if(refreshTokenService.verifyExpiration(refreshToken)){
            User   user = refreshToken.getUser();
            String token = jwtUtils.generateJwtToken(user.getUserName());
            return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
        }
        return ResponseEntity.internalServerError().body("error refreshing the token");
    }

}
