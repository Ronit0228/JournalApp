package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @DeleteMapping("/delete-user")
    public ResponseEntity<User> deleteUserById(){
        return new ResponseEntity<>(userService.deleteUser(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update-user")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.NO_CONTENT);
    }
}
