package Stream.project.stream.controllers;

import Stream.project.stream.models.LoginRequest;
import Stream.project.stream.models.security.JwtResponse;
import Stream.project.stream.models.security.JwtUtils;
import Stream.project.stream.repositories.RoleRepo;
import Stream.project.stream.repositories.UserRepo;
import Stream.project.stream.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/signin")
    public ResponseEntity<?> autheticateUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwt = userSerive.loginUser(loginRequest);

        return ResponseEntity.ok(new JwtResponse(jwt.getToken(), jwt.getId(), jwt.getUsername(),
                jwt.getEmail(), jwt.getRole()));
    }

}
