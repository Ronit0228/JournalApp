package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// JUnit Testing

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    // Normal Test
    @Test
    public void testFindUserByUsername(){
        User user = userService.findUserByUsername("Ritik");
        System.out.println(user);
    }

    // Parameterized test
    @ParameterizedTest
    @CsvSource({
            "Ronit",
            "Nitin Kumar",
            "Ritik",
            "Tushar",
            "Rajesh",
            "Ishita",
            "Nitish",
            "Harish",
            "Sachin"
    })
    public void parameterizedTestFindUserByUsername(String name){
        User user = userService.findUserByUsername(name);
        if(user != null) System.out.println(user);
        else System.out.println("Not Present");
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    public void createUsers(User user){
        userService.saveNewUser(user);
    }
}
