package com.example.theatre;

import com.example.theatre.controller.ClientsRegistrationController;
import com.example.theatre.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ClientsRegistrationController clientsRegistrationController;


    @Test
    void correctLogin() throws Exception {
        mockMvc.perform(formLogin().user("admin").password("password"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void badCredentials() throws Exception { // плохие подписки
        mockMvc.perform(post("/login").param("bla", "blabla"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}
