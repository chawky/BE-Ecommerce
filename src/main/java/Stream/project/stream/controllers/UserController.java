package Stream.project.stream.controllers;

import Stream.project.stream.models.DTOs.UserDto;
import io.swagger.annotations.ApiOperation;
import Stream.project.stream.models.User;
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
    @PostMapping("/create-user")
    public ResponseEntity createUser(@RequestBody UserDto userDto) {
        User user = userServices.save(userDto);
        if(Objects.isNull(user)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user was not create");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("user with this id : " + user.getId()+ "was created ");
    }
}
