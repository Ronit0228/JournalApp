package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> allUser(){
        List<User> userList = userService.allUser();

        if(userList != null){
            return ResponseEntity.ok(userList);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/by-username/{username}")
    public ResponseEntity<User> findUserByUsername(@PathVariable String username){
        try {
            return new ResponseEntity<>(userService.findUserByUsername(username), HttpStatus.FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-admin")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try {
            return new ResponseEntity<>(userService.saveAdmin(user), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
