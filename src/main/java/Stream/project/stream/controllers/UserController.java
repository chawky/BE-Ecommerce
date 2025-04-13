package Stream.project.stream.controllers;

import Stream.project.stream.models.DTOs.UserDto;
import Stream.project.stream.models.LoginRequest;
import Stream.project.stream.models.security.JwtResponse;
import io.swagger.annotations.ApiOperation;
import Stream.project.stream.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Stream.project.stream.services.UserServices;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserServices userServices;
    @GetMapping("/users")
    @ApiOperation(value = "Returns a list of users")
    List<UserDto> getAllUsers(){
        return userServices.getAllUsers();

    }
    @PostMapping("/signup")
    public ResponseEntity createUser(@RequestBody UserDto userDto) {
        JwtResponse jwt = userServices.save(userDto);
    
        if (Objects.isNull(jwt)){
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Bad credentials");
        }else {
            return ResponseEntity.ok(new JwtResponse(jwt.getToken(),jwt.getRefreshToken(), jwt.getId(), jwt.getUsername(),
                jwt.getEmail(), jwt.getRole(), jwt.getUser()));
        }
    }
}
