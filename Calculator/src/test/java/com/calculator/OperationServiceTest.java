package com.calculator;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.calculator.cache.OperationCache;
import com.calculator.dao.OperationDAO;
import com.calculator.model.OperationDTO;
import com.calculator.model.OperationRequest;
import com.calculator.model.OperationTypeDTO;
import com.calculator.model.OperationVO;
import com.calculator.model.UserSession;
import com.calculator.service.OperationService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalculatorApplication.class)
public class OperationServiceTest {

	@MockBean
	private UserSession userSession;

	@MockBean
	private OperationDAO operationDAO;

	@MockBean
	private OperationCache operationServiceCache;

	@Autowired
	private OperationService operationService;

	@Test
	public void testCalcalateWithDivideFail() throws Exception {
		OperationRequest op = new OperationRequest(1, 2, "ADD");
		OperationVO result = operationService.calculate(op);
		assertNull(result);
	}

	@Test
	public void testCalcalateWithOutCache() throws Exception {
		Map<String, OperationTypeDTO> allOperationType = getAllOperationType();

		given(operationServiceCache.getAllOperationType()).willReturn(allOperationType);
		given(operationServiceCache.generateKeyStore(1, 2, "ADD")).willReturn("1:2:ADD");
		given(operationServiceCache.getAllOperation()).willReturn(null);
		given(operationDAO.saveOperation(Mockito.any())).willReturn(true);

		OperationRequest op = new OperationRequest(1, 2, "ADD");
		OperationVO result = operationService.calculate(op);
		assertNotNull(result);
		assertEquals(1, result.getFirstNumber());
		assertEquals(2, result.getSecondNumber());
		assertEquals("ADD", result.getOperationType());
		assertEquals(3, result.getResult());

	}

	@Test
	public void testCalculateWithCacheWithOutSaveDB() {
		Map<String, OperationTypeDTO> allOperationType = getAllOperationType();
		int userId = 1;

		OperationDTO operation = new OperationDTO(userId, 2, "SUBTRACT", 10, 2, 8);
		String keyStore = "10:2:SUBTRACT";
		Map<String, Map<Integer, OperationDTO>> operationCache = new HashMap<>();
		Map<Integer, OperationDTO> operationU = new HashMap<>();
		operationU.put(userId, operation);
		operationCache.put(keyStore, operationU);

		given(operationServiceCache.generateKeyStore(10, 2, "SUBTRACT")).willReturn(keyStore);
		given(operationServiceCache.getAllOperationType()).willReturn(allOperationType);
		given(operationServiceCache.getAllOperation()).willReturn(operationCache);
		given(userSession.getUserId()).willReturn(userId);

		OperationRequest or = new OperationRequest(10, 2, "SUBTRACT");
		OperationVO result = operationService.calculate(or);
		assertNotNull(result);
		assertEquals(10, result.getFirstNumber());
		assertEquals(2, result.getSecondNumber());
		assertEquals("SUBTRACT", result.getOperationType());
		assertEquals(8, result.getResult());

		verify(operationDAO, times(0)).saveOperation(Mockito.any());
		verify(operationServiceCache, times(0)).updateCache(Mockito.any());

	}

	@Test
	public void testCalculateWithCacheAndSaveDB() {
		Map<String, OperationTypeDTO> allOperationType = getAllOperationType();
		int userId = 1;
		int userCached = 2;

		OperationDTO operation = new OperationDTO(userCached, 2, "SUBTRACT", 10, 2, 8);
		String keyStore = "10:2:SUBTRACT";
		Map<String, Map<Integer, OperationDTO>> operationCache = new HashMap<>();
		Map<Integer, OperationDTO> operationU = new HashMap<>();
		operationU.put(userCached, operation);
		operationCache.put(keyStore, operationU);

		given(operationServiceCache.generateKeyStore(10, 2, "SUBTRACT")).willReturn(keyStore);
		given(operationServiceCache.getAllOperationType()).willReturn(allOperationType);
		given(operationServiceCache.getAllOperation()).willReturn(operationCache);
		given(operationDAO.saveOperation(Mockito.any())).willReturn(true);
		given(userSession.getUserId()).willReturn(userId);

		OperationRequest or = new OperationRequest(10, 2, "SUBTRACT");
		OperationVO result = operationService.calculate(or);
		assertNotNull(result);
		assertEquals(10, result.getFirstNumber());
		assertEquals(2, result.getSecondNumber());
		assertEquals("SUBTRACT", result.getOperationType());
		assertEquals(8, result.getResult());

		verify(operationDAO, times(1)).saveOperation(Mockito.any());
		verify(operationServiceCache, times(1)).updateCache(Mockito.any());

	}

	@Test
	public void testCalculateButCannotSaveDB() throws Exception {
		Map<String, OperationTypeDTO> allOperationType = getAllOperationType();
		int userId = 1;
		int userCached = 2;

		OperationDTO operation = new OperationDTO(userCached, 2, "SUBTRACT", 10, 2, 8);
		String keyStore = "10:2:SUBTRACT";
		Map<String, Map<Integer, OperationDTO>> operationCache = new HashMap<>();
		Map<Integer, OperationDTO> operationU = new HashMap<>();
		operationU.put(userCached, operation);
		operationCache.put(keyStore, operationU);

		given(operationServiceCache.generateKeyStore(10, 2, "SUBTRACT")).willReturn(keyStore);
		given(operationServiceCache.getAllOperationType()).willReturn(allOperationType);
		given(operationServiceCache.getAllOperation()).willReturn(operationCache);
		given(userSession.getUserId()).willReturn(userId);
		given(operationDAO.saveOperation(Mockito.any())).willThrow(new RuntimeException());

		OperationRequest or = new OperationRequest(10, 2, "SUBTRACT");
		OperationVO result = operationService.calculate(or);
		assertNull(result);

		verify(operationDAO, times(1)).saveOperation(Mockito.any());
		verify(operationServiceCache, times(0)).updateCache(Mockito.any());
	}

	@Test
	public void testGetHistory() throws Exception {
		List<OperationVO> operationList = new ArrayList<>();
		OperationDTO operationDto = new OperationDTO(1, 1, "ADD", 1, 1, 2);
		OperationVO operation = new OperationVO(operationDto);
		operationList.add(operation);
		given(operationServiceCache.getOperationByUser()).willReturn(operationList);

		List<OperationVO> operationH = operationService.operationHistory();
		assertNotNull(operationH);
		assertEquals(1, operationH.size());
		OperationVO operationCache = operationH.get(0);
		assertEquals(1, operationCache.getFirstNumber());
		assertEquals(1, operationCache.getSecondNumber());
		assertEquals(2, operationCache.getResult());
		assertEquals("ADD", operationCache.getOperationType());

	}

	private Map<String, OperationTypeDTO> getAllOperationType() {
		Map<String, OperationTypeDTO> operationType = new HashMap<>();
		OperationTypeDTO ot1 = new OperationTypeDTO();
		ot1.setOperationId(1);
		ot1.setOperationName("ADD");
		operationType.put("ADD", ot1);

		OperationTypeDTO ot2 = new OperationTypeDTO();
		ot2.setOperationId(1);
		ot2.setOperationName("SUBTRACT");
		operationType.put("SUBTRACT", ot2);

		OperationTypeDTO ot3 = new OperationTypeDTO();
		ot3.setOperationId(1);
		ot3.setOperationName("MULTIPY");
		operationType.put("MULTIPY", ot3);

		OperationTypeDTO ot4 = new OperationTypeDTO();
		ot4.setOperationId(1);
		ot4.setOperationName("DIVIDE");
		operationType.put("DIVIDE", ot4);

		return operationType;
	}

}
