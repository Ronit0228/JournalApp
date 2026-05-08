package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import static org.mockito.Mockito.*;

// Mockito Testing

@SpringBootTest
public class UserDetailsServiceImplementationTest {

    @Autowired
    private UserDetailsServiceImplementation userDetailsServiceImplementation;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void loadUserByUsernameTest(){
        when(userRepository.findUserByUsername(ArgumentMatchers.anyString()))
                .thenReturn(
                        User.builder()
                                .username("Aradhya")
                                .password("skljdhfkjahskdfjhkasdjkfhjks")
                                .journalEntry(null)
                                .roles(Arrays.asList("ADMIN", "USER"))
                                .build()
                );

        UserDetails user = userDetailsServiceImplementation.loadUserByUsername("Abhishek");

        if(user != null) System.out.println(user);
        else System.out.println("not find");
    }
}
