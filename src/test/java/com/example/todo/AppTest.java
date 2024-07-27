package com.example.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest
public class AppTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void homeShouldReturnOk() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // @Test
    // public void welcomeShouldReturnOk() throws Exception {
    //     mockMvc.perform(get("/welcome"))
    //             .andExpect(MockMvcResultMatchers.status().isOk());
    // }
}
