package com.example.buysell.controller;

import com.example.buysell.controllers.ProductController;
import com.example.buysell.models.Product;
import com.example.buysell.models.User;
import com.example.buysell.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@ExtendWith(MockitoExtension.class)
public class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Test
    public void testProducts() throws Exception {
        Mockito.when(productService.listProducts(Mockito.any())).thenReturn(new ArrayList<>());
        Mockito.when(productService.getUserByPrincipal(Mockito.any())).thenReturn(new User());

        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("products"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    public void testProductsInfo() throws Exception {
        Product product = new Product();
        product.setUser(new User());
        Mockito.when(productService.getProductById(1L)).thenReturn(product);
        Mockito.when(productService.getUserByPrincipal(Mockito.any())).thenReturn(new User());

        this.mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product-info"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("authorProduct"));
    }

    @Test
    public void testProductsDelete() throws Exception {
        Mockito.doNothing().when(productService).deleteProduct(Mockito.any());
        Mockito.when(productService.getUserByPrincipal(Mockito.any())).thenReturn(new User());

        this.mockMvc.perform(post("/product/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testProductsCreateBadRequest() throws Exception {
        this.mockMvc.perform(get("/product/create"))
                .andExpect(status().isBadRequest());
    }

}
