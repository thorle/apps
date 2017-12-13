package com.calculator.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calculator.dao.OperationDAO;
import com.calculator.model.OperationDTO;
import com.calculator.model.OperationTypeDTO;
import com.calculator.model.OperationVO;
import com.calculator.model.UserSession;

@Component
public class OperationCache {
	public static final String COLON = ":";
	private Map<Integer, List<OperationDTO>> operationUserCache = new ConcurrentHashMap<>();
	private Map<String, Map<Integer, OperationDTO>> operationCache = new ConcurrentHashMap<>();
	private Map<String, OperationTypeDTO> operationTypeCache = new ConcurrentHashMap<>();

	private final static Logger LOGGER = Logger.getLogger(OperationCache.class.toString());

	@Autowired
	private OperationDAO operationDAO;

	@Autowired
	private UserSession userSession;

	@PostConstruct
	private void loadData() {
		List<OperationDTO> operationList = operationDAO.getAllOperation();
		Map<Integer, List<OperationDTO>> operationUserGroup = operationList.stream()
				.collect(Collectors.groupingBy(OperationDTO::getUserId));
		operationUserCache.putAll(operationUserGroup);

		Map<String, Map<Integer, OperationDTO>> operationMap = new HashMap<>();
		operationList.forEach(o -> {
			String keyStore = generateKeyStore(o.getFirstNumber(), o.getSecondNumber(), o.getOperationName());
			if (operationMap.get(keyStore) == null) {
				Map<Integer, OperationDTO> operationU = new HashMap<>();
				operationU.put(o.getUserId(), o);
				operationMap.put(keyStore, operationU);
			} else {
				operationMap.get(keyStore).put(o.getUserId(), o);
			}
		});
		List<OperationTypeDTO> operationTypeList = operationDAO.getAllOperationType();
		operationTypeList.forEach(ot -> {
			operationTypeCache.put(ot.getOperationName(), ot);
		});
	}

	public String generateKeyStore(int firstNumber, int secondNumber, String operationType) {
		StringBuilder builder = new StringBuilder();
		builder.append(firstNumber).append(COLON).append(secondNumber).append(COLON).append(operationType);
		return builder.toString();
	}

	public List<OperationVO> getOperationByUser() {
		int userId = getUserId();
		List<OperationDTO> operationList = operationUserCache.get(userId);
		if (operationList == null) {
			return Collections.emptyList();
		}
		return operationList.stream().map(OperationVO::new).collect(Collectors.toList());

	}

	public Map<String, Map<Integer, OperationDTO>> getAllOperation() {
		return Collections.unmodifiableMap(operationCache);
	}

	public Map<String, OperationTypeDTO> getAllOperationType() {
		return Collections.unmodifiableMap(operationTypeCache);
	}

	private int getUserId() {
		return userSession.getUserId();
	}

	public void updateCache(OperationDTO operationDTO) {
		LOGGER.log(Level.SEVERE,
				"OperationCache:updateCache --> Updating cache with operatation info:" + operationDTO.toString());
		List<OperationDTO> operationList = operationUserCache.get(getUserId());
		if (operationList == null) {
			List<OperationDTO> operations = new ArrayList<>();
			operations.add(operationDTO);
			operationUserCache.put(getUserId(), operations);
		} else {
			operationList.add(operationDTO);
		}

		String keyStore = generateKeyStore(operationDTO.getFirstNumber(), operationDTO.getSecondNumber(),
				operationDTO.getOperationName());
		Map<Integer, OperationDTO> operationU = operationCache.get(keyStore);
		if (operationU == null) {
			Map<Integer, OperationDTO> operationNew = new HashMap<>();
			operationNew.put(getUserId(), operationDTO);
			operationCache.put(keyStore, operationNew);

		} else {
			operationU.put(getUserId(), operationDTO);
		}

	}

	@PreDestroy
	private void clearCache() {
		operationUserCache = null;
	}
}
