package com.linetime.backend.controller;

import com.linetime.backend.exception.EmailTakenException;
import com.linetime.backend.exception.UsernameTakenException;
import com.linetime.backend.jwt.JwtTokenUtil;
import com.linetime.backend.jwt.JwtResponse;
import com.linetime.backend.model.Role;
import com.linetime.backend.model.User;
import com.linetime.backend.payload.IsAdminDto;
import com.linetime.backend.payload.LoginDto;
import com.linetime.backend.payload.SignUpDto;
import com.linetime.backend.repository.RoleRepository;
import com.linetime.backend.repository.UserRepository;
import com.linetime.backend.service.AuthService;
import com.linetime.backend.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        try{
            authService.registerUser(signUpDto);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        }
        catch(UsernameTakenException|EmailTakenException e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto){
        final String token = authService.authenticateUser(loginDto);
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }

    @GetMapping("/isadmin")
    public ResponseEntity<?> isAdmin(SecurityContextHolderAwareRequestWrapper request){
        boolean result = authService.isAdmin(request);
        return new ResponseEntity<>(new IsAdminDto(result), HttpStatus.OK);
    }
}