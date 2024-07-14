package com.example.buysell.service;

import com.example.buysell.models.User;
import com.example.buysell.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void loadUserByUsernameTest(){
        User user = new User();
        user.setName("Vasea");

        Mockito.when(customUserDetailsService.loadUserByUsername(Mockito.any())).thenReturn(user);

        UserDetails result = customUserDetailsService.loadUserByUsername("email");
        assertEquals(((UserDetails) user).getUsername(), result.getUsername());
    }
}
