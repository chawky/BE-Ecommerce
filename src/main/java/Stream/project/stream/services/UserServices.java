package Stream.project.stream.services;

import Stream.project.stream.models.DTOs.UserDto;

import lombok.RequiredArgsConstructor;
import Stream.project.stream.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import Stream.project.stream.repositories.UserRepo;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static Stream.project.stream.models.mappers.UserMapper.getUserMapper;
import static org.apache.logging.log4j.Level.INFO;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServices implements UserDetailsService {
   Logger logger = LogManager.getLogger(UserServices.class);
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo ;
    ModelMapper usermapper = getUserMapper();
    public List<UserDto> getAllUsers() {

        logger.log(INFO,"the users are  "+ userRepo.findAll() );
        return userRepo.findAll().stream().map(e -> usermapper.map(e,UserDto.class)).collect(Collectors.toList());
    }

    public User save(UserDto userDto) {
        User usetDto = usermapper.map(userDto, User.class);

        if (userRepo.findByUserName(usetDto.getUserName()).isPresent()) {
            throw new RuntimeException("User already registered. Please use different username.");
        }
        usetDto.setPassword(passwordEncoder.encode(usetDto.getPassword()));
        return userRepo.save(usermapper.map(userDto, User.class));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException(username));
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(), Collections.emptyList());

    }
}
