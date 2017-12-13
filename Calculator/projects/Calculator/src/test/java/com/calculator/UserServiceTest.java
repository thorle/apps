package com.calculator;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.calculator.CalculatorApplication;
import com.calculator.dao.UserDAO;
import com.calculator.model.UserDTO;
import com.calculator.model.UserSession;
import com.calculator.model.UserVO;
import com.calculator.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalculatorApplication.class)
public class UserServiceTest {

	@MockBean
	public UserDAO userDAO;

	@MockBean
	private UserSession userSession;

	@Autowired
	private UserService userService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testUserInvalid() throws Exception {
		UserVO userInfo = new UserVO("ad", "admin1");
		String result = userService.saveUser(userInfo);
		assertEquals("username or password is invalid", result);
		verify(userDAO, times(0)).findUserByUsername("ad");
	}

	@Test
	public void testPasswordInvalid() throws Exception {
		UserVO userInfo = new UserVO("admin", "ad");
		String result = userService.saveUser(userInfo);
		assertEquals("username or password is invalid", result);
		verify(userDAO, times(0)).findUserByUsername("admin");
	}

	@Test
	public void testSaveUser() throws Exception {
		UserVO userInfo = new UserVO("admin", "admin1");
		given(this.userDAO.findUserByUsername("admin")).willReturn(null);
		given(userDAO.saveUser(Mockito.any(UserVO.class))).willReturn(true);
		String username = userService.saveUser(userInfo);
		assertEquals("admin", username);
	}

	@Test
	public void testUserExisted() throws Exception {
		UserVO userInfo = new UserVO("admin", "admin1");
		UserDTO userDTO = new UserDTO(1, "admin", "admin");
		given(this.userDAO.findUserByUsername("admin")).willReturn(userDTO);
		String result = userService.saveUser(userInfo);
		assertEquals("username existed", result);
		verify(userDAO, times(0)).saveUser(userInfo);
	}

	@Test
	public void testCannotRegister() throws Exception {
		UserVO userInfo = new UserVO("admin", "admin1");
		given(this.userDAO.findUserByUsername("admin")).willReturn(null);
		given(userDAO.saveUser(Mockito.any(UserVO.class))).willReturn(false);
		String result = userService.saveUser(userInfo);
		assertEquals("cannot register username", result);
		verify(userDAO, times(1)).saveUser(userInfo);
		verify(userDAO, times(1)).findUserByUsername("admin");
	}

	@Test
	public void testLogout() throws Exception {
		given(this.userSession.getUserId()).willReturn(0);
		verify(userSession, times(0)).setUserId(0);
		verify(userSession, times(0)).setUsername("");
		String result = userService.logout();
		assertEquals("OK", result);
		
		given(this.userSession.getUserId()).willReturn(1);
		verify(userSession, times(0)).setUserId(0);
		verify(userSession, times(0)).setUsername("");
		String result2 = userService.logout();
		assertEquals("OK", result2);
	}

	@Test
	public void testLoginWithUserInvalid() throws Exception {
		String result = userService.login("ad", "admin1");
		assertEquals("username or password is invalid", result);
	}

	@Test
	public void testLoginWithInvalidPassword() throws Exception {
		String result = userService.login("admin", "adm");
		assertEquals("username or password is invalid", result);
	}

	@Test
	public void testLoginWithUserLogined() throws Exception {
		given(this.userSession.getUserId()).willReturn(1);
		given(this.userSession.getUsername()).willReturn("admin");
		String result = userService.login("admin", "admin1");
		assertEquals("The user logged in to system", result);

		given(this.userSession.getUserId()).willReturn(1);
		given(this.userSession.getUsername()).willReturn("admin2");
		String result2 = userService.login("admin", "admin1");
		assertEquals("Other user is logining", result2);
	}

	@Test
	public void testLoginWithUserDoesNotExist() throws Exception {
		given(this.userSession.getUserId()).willReturn(0);
		given(this.userDAO.findUserByUsername("admin")).willReturn(null);
		String result2 = userService.login("admin", "admin1");
		assertEquals("The user does not exist", result2);
	}

	@Test
	public void testLoginWithWrongPassword() throws Exception {
		UserDTO userDTO = new UserDTO(1, "admin", "admin2");
		given(this.userSession.getUserId()).willReturn(0);
		given(this.userDAO.findUserByUsername("admin")).willReturn(userDTO);
		String result = userService.login("admin", "admin1");
		assertEquals("Wrong password", result);
	}

	@Test
	public void testLoginSuccess() throws Exception {
		UserDTO userDTO = new UserDTO(1, "admin", "admin1");
		given(this.userSession.getUserId()).willReturn(0);
		given(this.userDAO.findUserByUsername("admin")).willReturn(userDTO);
		String result = userService.login("admin", "admin1");
		assertEquals("OK", result);
	}

}
