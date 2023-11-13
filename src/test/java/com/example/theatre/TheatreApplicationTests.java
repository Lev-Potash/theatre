package com.example.theatre;

import com.example.theatre.controller.ClientsRegistrationController;
import com.example.theatre.entity.Client;
import com.example.theatre.repository.ClientRepository;
import com.example.theatre.service.ClientServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// SpringRunner получает ApplicationContext и подгружает все наши контроллеры, сервисы, репозитории
//@RunWith(SpringRunner.class)
//@WebMvcTest(ClientsRegistrationController.class)
// так тесты не проходят возвращая статус 401
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebMvcTest(ClientsRegistrationController.class)

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc

// так тесты проходят и возвращают статус 200
@SpringBootTest
@AutoConfigureMockMvc
class TheatreApplicationTests {


	@MockBean
	private ClientRepository clientRepository;

	@Autowired
	MockMvc mockMvc;


	@Autowired
	private ClientsRegistrationController clientsRegistrationController;


	@Test
	void testController() {
		assertThat(clientsRegistrationController).isNotNull();
	}

	@Test
	void testReturn() throws Exception {
		given(clientRepository.findById(any())) // any() - получить любое значение
				.willReturn(Optional.of(new Client("Alex", "Frolov", "lex@ru.ru"))); // то что хотим получить


		mockMvc.perform(get("/registration"))
				.andDo(print()) // чтобы по логам предпположить что у нас сломалось
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
				.andExpect(content().string(containsString("Фамилия")))
				/*.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))*/
		;
	}

	/**
	 * Показывает тест на переход на форму бронирования и возвращения 302 статуса
	 * @throws Exception
	 */
	@Test
	void testRedirectionPlaces() throws Exception {

		mockMvc.perform(get("/registration/places"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
//				.andExpect(redirectedUrl("http://localhost:9090/login"));
				.andExpect(redirectedUrl("/registration"));
	}

	@Test
	void testRepository() {

		given(clientRepository.findById(1L/*any()*/)) // any() - получить любое значение
				.willReturn(Optional.of(new Client("Alex", "Frolov", "lex@ru.ru")));

		assertEquals(clientRepository.findById(1L).get().getName(), "Alex");
	}

	@Test
	void testEqualsToNumber() {
		assertEquals(42,42);
	}

	@Test
	void boolTest() {
		assertTrue(true);
		assertFalse(false);
	}

	@Test
	void nul() {
		Client client = null;
//		assertNull(client);
//		assertNotNull(client);

		Client client1 = new Client("Mark", "Petrov", "petrov@mark.ru");
		assertEquals(client1.getName(), "Mark");

	}

	@Test
	void testHomePage() throws Exception {

		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("home"))
				.andExpect(MockMvcResultMatchers.content().string(
						Matchers.containsString("Welcome to theatre")));


	}

}
