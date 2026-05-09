package net.engineeringdigest.journalApp.controller;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.dto.QuoteWithGreetingResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.QuotesService;
import net.engineeringdigest.journalApp.service.UserService;
//import net.engineeringdigest.journalApp.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final QuotesService quotesService;

    @DeleteMapping("/delete-user")
    public ResponseEntity<User> deleteUserById(){
        return new ResponseEntity<>(userService.deleteUser(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update-user")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/greeting")
    public ResponseEntity<QuoteWithGreetingResponse> greetingToUser(){
        return ResponseEntity.ok(quotesService.getQuote());
    }
}
