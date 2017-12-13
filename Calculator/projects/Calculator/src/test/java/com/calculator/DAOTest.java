package com.calculator;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import com.calculator.CalculatorApplication;
import com.calculator.dao.OperationDAO;
import com.calculator.dao.OperationDAOImpl;
import com.calculator.dao.UserDAO;
import com.calculator.dao.UserDAOImpl;
import com.calculator.model.OperationDTO;
import com.calculator.model.UserDTO;
import com.calculator.model.UserVO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CalculatorApplication.class })
public class DAOTest {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private OperationDAO operationDAO;

	@MockBean
	private JdbcTemplate jdbc;

	@Test
	public void testCreateUser() {
		UserVO userSave = getUser();
		given(jdbc.update(UserDAOImpl.SAVE_USER, new Object[] { userSave.getUsername(), userSave.getPassword() }))
				.willReturn(1);
		boolean hasSave = userDAO.saveUser(userSave);
		assertTrue(hasSave);
	}

//	@SuppressWarnings({ "unchecked", "deprecation" })
//	@Test
//	public void testFindUserByUsername() {
//		String username = "admin";
//		List<UserDTO> userList = new ArrayList<>();
//		UserDTO userDB = new UserDTO(1, "admin", "admin1");
//		userList.add(userDB);
//		Object[] object = new Object[] { username };
//		RowMapper<UserDTO> rowMapper = (ResultSet rs, int numRow) -> {
//			return new UserDTO(rs, numRow);
//		};
//		given(jdbc.query(eq(UserDAOImpl.FIND_USER_BY_USERNAME), eq(rowMapper), eq(object))).willReturn(userList);
//		given(jdbc.query(anyString(), Matchers.<RowMapper<UserDTO>>any())).willReturn(userList);
//		UserDTO user = userDAO.findUserByUsername(username);
//		assertNotNull(user);
//		assertEquals("admin", userDB.getUsername());
//	}

	@Test
	public void testSaveOperation() {
		OperationDTO operationDTO = getOperation();
		given(jdbc.update(OperationDAOImpl.SAVE_OPERATION,
				new Object[] { operationDTO.getUserId(), operationDTO.getOperationId(), operationDTO.getFirstNumber(),
						operationDTO.getSecondNumber(), operationDTO.getResult() })).willReturn(1);
		boolean hasSave = operationDAO.saveOperation(operationDTO);
		assertTrue(hasSave);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllOperation() {
		List<OperationDTO> operationList = new ArrayList<>();
		OperationDTO operationDTO = getOperation();
		operationList.add(operationDTO);
		given(jdbc.query(anyString(), Matchers.<RowMapper<OperationDTO>>any())).willReturn(operationList);
		List<OperationDTO> operations = operationDAO.getAllOperation();
		assertNotNull(operations);
		assertEquals(1, operations.size());
		OperationDTO operation = operations.get(0);
		assertEquals(1, operation.getFirstNumber());
		assertEquals(2, operation.getSecondNumber());
		assertEquals(3, operation.getResult());
		assertEquals("ADD", operation.getOperationName());
		assertEquals(1, operation.getUserId());
	}

	private OperationDTO getOperation() {
		OperationDTO operationDTO = new OperationDTO();
		operationDTO.setUserId(1);
		operationDTO.setOperationId(1);
		operationDTO.setOperationName("ADD");
		operationDTO.setFirstNumber(1);
		operationDTO.setSecondNumber(2);
		operationDTO.setResult(3);
		return operationDTO;

	}

	private UserVO getUser() {
		UserVO user = new UserVO();
		user.setUsername("admin");
		user.setPassword("admin1");
		return user;
	}

}
