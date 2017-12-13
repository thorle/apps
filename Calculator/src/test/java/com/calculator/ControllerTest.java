package com.calculator;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.calculator.model.OperationRequest;
import com.calculator.model.UserVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalculatorApplication.class)
public class ControllerTest {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

	}

	// UserController Test

	@Test
	public void testURLNotFound() throws Exception {
		mockMvc.perform(
				post("/home").content(TestUtil.asJsonString(new UserVO("admin", "admin"))).contentType(contentType))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testLogin() throws Exception {
		mockMvc.perform(
				post("/login").content(TestUtil.asJsonString(new UserVO("admin", "admin"))).contentType(contentType))
				.andExpect(status().isOk());
	}

	@Test
	public void testSignup() throws Exception {
		mockMvc.perform(
				post("/signup").content(TestUtil.asJsonString(new UserVO("admin", "admin"))).contentType(contentType))
				.andExpect(status().isOk());
	}

	@Test
	public void testLogout() throws Exception {
		mockMvc.perform(get("/logout").contentType(contentType)).andExpect(status().isOk());
	}

	// OperationController test
	@Test
	public void testCalculate() throws Exception {
		mockMvc.perform(post("/operation/calculate").content(TestUtil.asJsonString(new OperationRequest(1, 2, "ADD")))
				.contentType(contentType)).andExpect(status().isOk());
	}

	@Test
	public void testHistory() throws Exception {
		mockMvc.perform(get("/operation/history").content("").contentType(contentType)).andExpect(status().isOk());
	}

}
