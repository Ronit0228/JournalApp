package net.engineeringdigest.journalApp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            return userRepository.save(user);
        } catch (RuntimeException e) {
            log.error("Error occurred for {} :", user.getUsername(), e);
            throw new RuntimeException(e);
        }
    }

    public User saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("ADMIN"));
        return userRepository.save(user);
    }

    public List<User> allUser(){
        return userRepository.findAll();
    }

    public User findUserByUsername(String username){
        return userRepository.findUserByUsername(username);
    }

    public User deleteUser(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String username = authentication.getName();

       return userRepository.deleteUserByUsername(username);
    }

    public User updateUser(User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User userInDB = userRepository.findUserByUsername(username);


        userInDB.setUsername(user.getUsername());
        userInDB.setPassword(passwordEncoder.encode(user.getPassword()));
        userInDB.setRoles(Arrays.asList("USER"));
        return userRepository.save(userInDB);
    }
}
