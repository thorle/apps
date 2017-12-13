package com.calculator.service;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.calculator.cache.OperationCache;
import com.calculator.calculate.Calculate;
import com.calculator.dao.OperationDAO;
import com.calculator.model.OperationDTO;
import com.calculator.model.OperationRequest;
import com.calculator.model.OperationTypeDTO;
import com.calculator.model.OperationVO;
import com.calculator.model.UserSession;
import com.calculator.validator.OperationValidator;

@Service
public class OperationServiceImpl implements OperationService {

	private static final Logger LOGGER = Logger.getLogger(OperationServiceImpl.class.toString());
	public static final String DIVIDE = "DIVIDE";

	@Autowired
	private UserSession userSession;

	@Autowired
	private OperationDAO operationDAO;

	@Autowired
	private OperationCache operationServiceCache;

	@Autowired
	private ApplicationContext context;

	@Override
	public OperationVO calculate(OperationRequest operationRq) {
		int firstNumber = operationRq.getFirstNumber();
		int secondNumber = operationRq.getSecondNumber();
		String operationName = operationRq.getOperationName();
		OperationVO operationVO = null;
		Map<String, OperationTypeDTO> allOperationType = operationServiceCache.getAllOperationType();
		OperationTypeDTO operationType = allOperationType.get(operationRq.getOperationName());
		if ((DIVIDE.equals(operationRq.getOperationName()) && OperationValidator.isNumberInValid(secondNumber))
				|| operationType == null) {
			return operationVO;
		}
		Map<Integer, OperationDTO> operationsCached = retrieveCache(firstNumber, secondNumber, operationName);
		if (operationsCached != null && !operationsCached.isEmpty()) {
			OperationDTO operation = operationsCached.get(getUserId());
			if (operation != null) {
				operationVO = new OperationVO(operation);
				return operationVO;
			}
			OperationDTO operationToUpdate = operationsCached.entrySet().iterator().next().getValue();
			return saveAndUpdateCache(operationToUpdate);

		}

		int result = getResult(operationRq);
		OperationDTO operationUpdate = new OperationDTO(getUserId(), operationType.getOperationId(), operationName,
				firstNumber, secondNumber, result);
		return saveAndUpdateCache(operationUpdate);
	}

	private OperationVO saveAndUpdateCache(OperationDTO operation) {
		OperationVO operationResult = null;
		LOGGER.log(Level.SEVERE, "OperationServiceImpl:saveAndUpdateCache --> save and update cache for operation: "
				+ operation.toString());
		try {

			if (operationDAO.saveOperation(operation)) {
				operationServiceCache.updateCache(operation);
				operationResult = new OperationVO(operation);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "OperationServiceImpl:saveAndUpdateCache --> An error during save operation: "
					+ operation.toString());
			e.printStackTrace();
		}
		return operationResult;
	}

	private Map<Integer, OperationDTO> retrieveCache(int firstNumber, int secondNumber, String operationType) {
		Map<String, Map<Integer, OperationDTO>> allOperation = operationServiceCache.getAllOperation();
		String keyStore = operationServiceCache.generateKeyStore(firstNumber, secondNumber, operationType);
		return allOperation.get(keyStore);
	}

	private int getResult(OperationRequest operation) {
		Calculate calculate = (Calculate) context.getBean(operation.getOperationName());
		return calculate.calculate(operation.getFirstNumber(), operation.getSecondNumber());
	}

	private int getUserId() {
		return userSession.getUserId();
	}

	@Override
	public List<OperationVO> operationHistory() {
		return operationServiceCache.getOperationByUser();
	}

}
