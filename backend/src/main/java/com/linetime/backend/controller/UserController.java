package com.linetime.backend.controller;

import com.linetime.backend.exception.TimelineNotFoundException;
import com.linetime.backend.exception.UserNotFoundException;
import com.linetime.backend.model.Timeline;
import com.linetime.backend.model.User;
import com.linetime.backend.repository.UserRepository;
import com.linetime.backend.service.AuthService;
import com.linetime.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.nio.file.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}")
    ResponseEntity<?> getUserById(@PathVariable Integer id){
        try{
            User user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch ( UserNotFoundException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable Integer id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.updateUser(newUser, id, username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch ( UserNotFoundException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch ( AccessDeniedException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Integer id){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            userService.deleteUser(id, username);
            return new ResponseEntity<>("User with id "+id+" has been deleted successfully.", HttpStatus.OK);
        }
        catch ( UserNotFoundException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch ( AccessDeniedException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

}
