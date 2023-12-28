package com.linetime.backend.service;

import com.linetime.backend.exception.EmailTakenException;
import com.linetime.backend.exception.RoleNotFoundException;
import com.linetime.backend.exception.UsernameTakenException;
import com.linetime.backend.jwt.JwtTokenUtil;
import com.linetime.backend.model.Role;
import com.linetime.backend.model.User;
import com.linetime.backend.payload.LoginDto;
import com.linetime.backend.payload.SignUpDto;
import com.linetime.backend.repository.RoleRepository;
import com.linetime.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(SignUpDto signUpDto) throws UsernameTakenException, EmailTakenException {
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            throw new UsernameTakenException(signUpDto.getUsername());
        }
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            throw new EmailTakenException(signUpDto.getEmail());
        }

        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_USER").orElseThrow( () -> new RoleNotFoundException("ROLE_USER"));
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);
    }

    public String authenticateUser(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userService
                .loadUserByUsername(loginDto.getUsernameOrEmail());

        return jwtTokenUtil.generateToken(userDetails);
    }

    public boolean isAdmin(SecurityContextHolderAwareRequestWrapper request){
        return request.isUserInRole("ROLE_ADMIN");
    }
}
