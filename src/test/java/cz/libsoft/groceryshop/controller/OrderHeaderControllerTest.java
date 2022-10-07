package cz.libsoft.groceryshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.libsoft.groceryshop.request.OrderLineRequest;
import cz.libsoft.groceryshop.request.OrderRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderHeaderControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createOrder() throws Exception {
        Set<OrderLineRequest> orderLineRequestSet = new HashSet<>();
        orderLineRequestSet.add(OrderLineRequest.builder()
                .productId(1L)
                .quantity(10)
                .build());

        mockMvc.perform(MockMvcRequestBuilders.put("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderRequest.builder()
                                .customerAddress("Planet Earth")
                                .orderLines(orderLineRequestSet)
                                .build())))
                .andExpect(status().isOk());
    }

}
