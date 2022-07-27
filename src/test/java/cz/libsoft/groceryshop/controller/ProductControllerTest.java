package cz.libsoft.groceryshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.libsoft.groceryshop.request.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ProductRequest.builder()
                        .name("new product")
                        .price("123")
                        .build())))
                .andExpect(status().isOk());
    }

    @Test
    void updateProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/product/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductRequest.builder()
                                .name("New product name")
                                .price("110")
                                .id(1L)
                                .build())))
                .andExpect(status().isOk());
    }

    @Test
    void updateProductQty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/product/update-quantity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductRequest.builder()
                                .quantity(200)
                                .id(1L)
                                .build())))
                .andExpect(status().isOk());
    }

    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ProductRequest.builder()
                                .id(3L)
                                .build())))
                .andExpect(status().isOk());
    }
}
