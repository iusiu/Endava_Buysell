package com.example.buysell.service;

import com.example.buysell.models.Product;
import com.example.buysell.models.User;
import com.example.buysell.repositories.ProductRepository;
import com.example.buysell.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProductService productService;

    @Mock
    private Principal principal;

    @Test
    public void listProductsByTitleTest(){
        Product product = new Product();
        product.setId(1L);
        product.setCity("Chisinau");
        List<Product> products = new ArrayList<>();
        products.add(product);

        Mockito.when(productRepository.findByTitle(Mockito.any())).thenReturn(products);

        List<Product> result = productService.listProducts("Title");
        assertEquals(result.get(0).getId(), product.getId());
        assertEquals(result.get(0).getCity(), product.getCity());
    }

    @Test
    public void getProductByIdTest(){
        Product product = new Product();
        product.setId(1L);
        product.setCity("Chisinau");

        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);
        assertEquals( product.getId(), result.getId());
        assertEquals(product.getCity(), result.getCity());
    }

    @Test
    public void getProductByIdUnsuccessfulTest(){

        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        assertNull(productService.getProductById(1L));

    }

    @Test
    public void listAllProductsTest(){
        Product product = new Product();
        product.setId(1L);
        product.setCity("Chisinau");
        List<Product> products = new ArrayList<>();
        products.add(product);

        Mockito.when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.listProducts(null);
        assertEquals(product.getId(), result.get(0).getId());
        assertEquals(product.getCity(), result.get(0).getCity());
    }

    @Test
    public void getUserByPrincipalTest(){
        User user = new User();
        user.setId(1L);
        user.setName("Vasea");
        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(user);

        User result = productService.getUserByPrincipal(principal);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    public void getNewUserByPrincipalTest(){
        User result = productService.getUserByPrincipal(null);
        assertNotNull(result);
    }
}
