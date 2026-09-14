package com.edigest.journalApp.Service;

import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


//@SpringbootApplication  ---> main class   @Autowired  -----> respository @Mockitobean
// nothing ---> main class   @InjectMocks  -----> respository @Mock
@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    void loadUserByUsername() {

        User user = new User();
        user.setUserName("Ram");
        user.setPassword("1234");
        user.setRoles(List.of("USER"));

        when(userRepository.findByUserName("Ram")).thenReturn(user);

        UserDetails result = userDetailsService.loadUserByUsername("Ram");
        assertEquals("Ram", result.getUsername());
    }
}

