package com.example.buysell.service;

import com.example.buysell.models.User;
import com.example.buysell.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private Principal principal;

    @Test
    public void createUser(){
        User user = new User();
        user.setId(1L);
        user.setName("Vasea");
        user.setEmail("vasea@email.ru");
        user.setPassword("12345");

        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(null);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(null);
        Mockito.when(passwordEncoder.encode(Mockito.any())).thenReturn("encoded");

        assertTrue(userService.createUser(user));
    }

    @Test
    public void unsuccessfulCreateUser(){
        User user = new User();
        user.setId(1L);
        user.setName("Vasea");
        user.setEmail(null);
        user.setPassword("12345");

        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(user);

        assertFalse(userService.createUser(user));
    }

    @Test
    public void banUser(){
        User user = new User();
        user.setId(1L);
        user.setName("Vasea");
        user.setEmail("vasea@email.ru");
        user.setPassword("12345");
        user.setActive(true);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(null);

        userService.banUser(1L);
    }

    @Test
    public void getUserByPrincipal(){
        User user = new User();
        user.setId(1L);
        user.setName("Vasea");
        user.setPassword("12345");

        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(user);
        Mockito.when(principal.getName()).thenReturn("Vasea");

        User result = userService.getUserByPrincipal(principal);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
    }


}
