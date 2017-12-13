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
import com.calculator.model.OperationVO;
import com.calculator.model.UserSession;
import com.calculator.service.OperationService;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = CalculatorApplication.class)
public class OperationServiceTest {

//	@MockBean
//	private UserSession userSession;
//
//	@MockBean
//	private OperationDAO operationDAO;
//
//	@MockBean
//	private OperationCache operationServiceCache;
//
//	@Autowired
//	private OperationService operationService;
//
//	@Test
//	public void testCalcalateWithDivideFail() throws Exception {
//		OperationRequest op = new OperationRequest(1, 2, "ADD");
//		OperationVO result = operationService.calculate(op);
//		assertNull(result);
//	}
//
//	@Test
//	public void testCalcalateWithOutCache() throws Exception {
//		
//		given(operationServiceCache.getAllOperationType()).willReturn(value);
//		given(operationServiceCache.getAllOperation()).willReturn(null);
//		given(operationDAO.saveOperation(Mockito.any())).willReturn(true);
//		OperationRequest op = new OperationRequest(1, 2, "ADD");
//		OperationVO result = operationService.calculate(op);
//		assertNotNull(result);
//		assertEquals(1, result.getFirstNumber());
//		assertEquals(2, result.getSecondNumber());
//		assertEquals("ADD", result.getOperationType());
//		assertEquals(3, result.getResult());
//
//	}
//
//	@Test
//	public void testCalculateWithCacheWithOutSaveDB() {
//		OperationDTO operation = new OperationDTO(1, 2, "SUBTRACT", 10, 2, 8);
//		String keyStore = "1:2:ADD";
//		Map<String, Map<Integer, OperationDTO>> operationCache = new HashMap<>();
//		Map<Integer, OperationDTO> operationU = new HashMap<>();
//		operationU.put(1, operation);
//		operationCache.put(keyStore, operationU);
//		given(operationServiceCache.getAllOperation()).willReturn(operationCache);
//		given(userSession.getUserId()).willReturn(1);
//
//		OperationRequest or = new OperationRequest(10, 2, "SUBTRACT");
//
//		OperationVO result = operationService.calculate(or);
//		assertNotNull(result);
//		assertEquals(10, result.getFirstNumber());
//		assertEquals(2, result.getSecondNumber());
//		assertEquals("SUBTRACT", result.getOperationType());
//		assertEquals(8, result.getResult());
//
//	}
//
//	@Test
//	public void testCalculateWithCacheAndSaveDB() {
//		OperationDTO operation = new OperationDTO(2, 2, "SUBTRACT", 10, 2, 8);
//		String keyStore = "1:2:ADD";
//		Map<String, Map<Integer, OperationDTO>> operationCache = new HashMap<>();
//		Map<Integer, OperationDTO> operationU = new HashMap<>();
//		operationU.put(1, operation);
//		operationCache.put(keyStore, operationU);
//		given(operationServiceCache.getAllOperation()).willReturn(operationCache);
//		given(userSession.getUserId()).willReturn(1);
//		given(operationDAO.saveOperation(Mockito.any())).willReturn(true);
//
//		OperationRequest or = new OperationRequest(10, 2, "SUBTRACT");
//		OperationVO result = operationService.calculate(or);
//		assertNotNull(result);
//		assertEquals(10, result.getFirstNumber());
//		assertEquals(2, result.getSecondNumber());
//		assertEquals("SUBTRACT", result.getOperationType());
//		assertEquals(8, result.getResult());
//
//		verify(operationDAO, times(1)).saveOperation(Mockito.any());
//		verify(operationServiceCache, times(1)).updateCache(Mockito.any());
//
//	}
//
//	@Test
//	public void testCalculateButCannotSaveDB() throws Exception {
//		OperationDTO operation = new OperationDTO(2, 2, "SUBTRACT", 10, 2, 8);
//		String keyStore = "1:2:ADD";
//		Map<String, Map<Integer, OperationDTO>> operationCache = new HashMap<>();
//		Map<Integer, OperationDTO> operationU = new HashMap<>();
//		operationU.put(1, operation);
//		operationCache.put(keyStore, operationU);
//		given(operationServiceCache.getAllOperation()).willReturn(operationCache);
//		given(userSession.getUserId()).willReturn(1);
//		given(operationDAO.saveOperation(Mockito.any())).willThrow(new RuntimeException());
//
//		OperationRequest or = new OperationRequest(10, 2, "SUBTRACT");
//		OperationVO result = operationService.calculate(or);
//		assertNull(result);
//
//		verify(operationDAO, times(1)).saveOperation(Mockito.any());
//		verify(operationServiceCache, times(0)).updateCache(Mockito.any());
//	}
//
//	@Test
//	public void testCalculateWithCacheEmpty() throws Exception {
//		Map<String, Map<Integer, OperationDTO>> operationCache = new HashMap<>();
//		given(operationServiceCache.getAllOperation()).willReturn(operationCache);
//		given(userSession.getUserId()).willReturn(1);
//		given(operationDAO.saveOperation(Mockito.any())).willReturn(true);
//
//		OperationRequest or = new OperationRequest(10, 2, "DIVIDE");
//
//		OperationVO result = operationService.calculate(or);
//		assertNotNull(result);
//		assertEquals(10, result.getFirstNumber());
//		assertEquals(2, result.getSecondNumber());
//		assertEquals("DIVIDE", result.getOperationType());
//		assertEquals(5, result.getResult());
//
//		verify(operationDAO, times(1)).saveOperation(Mockito.any());
//		verify(operationServiceCache, times(1)).updateCache(Mockito.any());
//	}
//
//	@Test
//	public void testGetHistory() throws Exception {
//		List<OperationVO> operationList = new ArrayList<>();
//		OperationDTO operationDto = new OperationDTO(1, 1, "ADD", 1, 1, 2);
//		OperationVO operation = new OperationVO(operationDto);
//		operationList.add(operation);
//		given(operationServiceCache.getOperationByUser()).willReturn(operationList);
//		List<OperationVO> operationH = operationService.operationHistory();
//		assertNotNull(operationH);
//		assertEquals(1, operationH.size());
//		OperationVO operationCache = operationH.get(0);
//		assertEquals(1, operationCache.getFirstNumber());
//		assertEquals(1, operationCache.getSecondNumber());
//		assertEquals(2, operationCache.getResult());
//		assertEquals("ADD", operationCache.getOperationType());
//
//	}

}
