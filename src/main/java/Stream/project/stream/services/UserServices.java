package Stream.project.stream.services;

import Stream.project.stream.ERole;
import Stream.project.stream.models.DTOs.UserDto;

import Stream.project.stream.models.LoginRequest;
import Stream.project.stream.models.Role;
import Stream.project.stream.models.security.JwtResponse;
import Stream.project.stream.models.security.JwtUtils;
import Stream.project.stream.models.security.UserDetailsImpl;
import Stream.project.stream.repositories.RoleRepo;
import lombok.RequiredArgsConstructor;
import Stream.project.stream.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import Stream.project.stream.repositories.UserRepo;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static Stream.project.stream.models.mappers.UserMapper.getUserMapper;
import static org.apache.logging.log4j.Level.INFO;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServices implements UserDetailsService {
    @Autowired
    @Lazy
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    Logger logger = LogManager.getLogger(UserServices.class);
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    ModelMapper usermapper = getUserMapper();

    public List<UserDto> getAllUsers() {

        logger.log(INFO, "the users are  " + userRepo.findAll());
        List<User> allusers = userRepo.findAll();
        return userRepo.findAll().stream().map(e -> usermapper.map(e, UserDto.class)).collect(Collectors.toList());
    }

    public User save(UserDto userDto) {
        User user = usermapper.map(userDto, User.class);

        if (userRepo.findByUserName(user.getUserName()).isPresent()) {
            throw new RuntimeException("User already registered. Please use different username.");
        }
        Set<String> strRoles = userDto.getRole();
        Set<Role> roles = new HashSet<>();
        if (!Objects.nonNull(strRoles)) {
            Role userRole = roleRepo.findByRoleName(ERole.USER_ROLE)
                    .orElseThrow(() -> new RuntimeException("role not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepo.findByRoleName(ERole.ADMIN_ROLE)
                                .orElseThrow(() -> new RuntimeException("role not found"));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepo.findByRoleName(ERole.MODERATOR_ROLE)
                                .orElseThrow(() -> new RuntimeException("role not found"));
                        roles.add(modRole);
                        break;

                    default:
                        Role userRole = roleRepo.findByRoleName(ERole.USER_ROLE)
                                .orElseThrow(() -> new RuntimeException("role not found"));
                        roles.add(userRole);
                        break;
                }
            });
        }
        user.setRole(roles);
        userRepo.save(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(usermapper.map(user, User.class));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username).orElseThrow(() ->new UsernameNotFoundException("userNotFound"+username));
        return UserDetailsImpl.build(user);
    }

    public JwtResponse loginUser(LoginRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPw()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(loginRequest.getUsername());
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = new ArrayList<>();
        if(Objects.nonNull(userDetailsImpl.getAuthorities()) && userDetailsImpl.getAuthorities().isEmpty()) {
            roles=	userDetailsImpl.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        }

        jwtResponse.setToken(jwt);
        jwtResponse.setRole(roles);
        jwtResponse.setId(userDetailsImpl.getId());
        jwtResponse.setUsername(userDetailsImpl.getUsername());
        jwtResponse.setEmail(userDetailsImpl.getEmail());
        return jwtResponse;
    }
}
