package com.calculator.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;

import com.calculator.dao.OperationDAO;
import com.calculator.model.OperationDTO;
import com.calculator.model.OperationVO;

public class OperationCache {
	private Map<Integer, Map<Integer, List<OperationDTO>>> operationCache = new ConcurrentHashMap<>();
	private Map<Integer, List<OperationDTO>> operationMethodCache = new ConcurrentHashMap<>();

	@Autowired
	private OperationDAO operationDAO;

	@Autowired
	private UserSession userSession;

	@PostConstruct
	private void loadData() {
		List<OperationDTO> operationList = operationDAO.getAll();
		Map<Integer, Map<Integer, List<OperationDTO>>> operationGroup = operationList.stream().collect(
				Collectors.groupingBy(OperationDTO::getUserId, Collectors.groupingBy(OperationDTO::getOperationId)));
		operationCache.putAll(operationGroup);
		Map<Integer, List<OperationDTO>> operationMethod = operationList.stream()
				.collect(Collectors.groupingBy(OperationDTO::getOperationId));
		operationMethodCache.putAll(operationMethod);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<OperationVO> getOperationByUser() {
		int userId = getUserId();
		Map<Integer, List<OperationDTO>> operationMap = operationCache.get(userId);
		List<OperationDTO> operationList = new ArrayList(operationMap.values());
		return operationList.stream().map(OperationVO::new).collect(Collectors.toList());

	}

	public Map<Integer, List<OperationDTO>> getAllOperation() {
		return operationMethodCache;
	}

	private int getUserId() {
		return userSession.getUserId();
	}
	
	public void updateCache() {
		
	}

	@PreDestroy
	private void clearCache() {
		operationCache = null;
	}
}
