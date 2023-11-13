package com.example.theatre;

import com.example.theatre.controller.ClientsRegistrationController;
import com.example.theatre.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin") // входит за пользователя
//@TestPropertySource("application.yaml")
public class MainControllerTest {

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ClientsRegistrationController clientsRegistrationController;

    @Test
    public void mainPageTest() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated());
    }
}
