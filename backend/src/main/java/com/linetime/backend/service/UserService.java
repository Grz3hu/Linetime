package com.linetime.backend.service;

import com.linetime.backend.exception.TimelineNotFoundException;
import com.linetime.backend.exception.UserNotFoundException;
import com.linetime.backend.model.User;
import com.linetime.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: "+ usernameOrEmail));

        Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                authorities);
    }

    public User getUserById(Integer id){
        return userRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException(id));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User newUser, Integer id, String username) throws AccessDeniedException, UserNotFoundException {
        User owner = userRepository.findById(id).get();
        User requestUser = userRepository.findByUsernameOrEmail(username,username).get();
        if(!owner.equals(requestUser)) {
            throw new AccessDeniedException("User has no permission to update");
        }
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setUsername(newUser.getUsername());
                    user.setEmail(newUser.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException(id));
    }

    public void deleteUser(Integer id, String username) throws UserNotFoundException, AccessDeniedException {
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
        User owner = userRepository.findById(id).get();
        User requestUser = userRepository.findByUsernameOrEmail(username,username).get();
        if(!owner.equals(requestUser)) {
            throw new AccessDeniedException("User has no permission to delete");
        }
        userRepository.deleteById(id);
    }
}