package com.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.calculator.model.ErrorCode;
import com.calculator.model.OperationRequest;
import com.calculator.model.ResultVO;
import com.calculator.model.UserSession;
import com.calculator.service.OperationService;

@RestController
@RequestMapping("/operation")
public class OperationController {

	@Autowired
	private OperationService operationService;

	@Autowired
	private UserSession userSession;

	@RequestMapping(value = "/calculate", method = RequestMethod.POST)
	public Object add(@RequestBody OperationRequest operation) {
		if (userSession.getUserId() > 0) {
			return operationService.calculate(operation);
		}
		return new ResultVO(ErrorCode.OPERATION_PERMISSION.getErrorCode(), ErrorCode.OPERATION_PERMISSION.getMessage());

	}

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public Object history() {
		if (userSession.getUserId() > 0) {
			return operationService.operationHistory();
		}
		return new ResultVO(ErrorCode.OPERATION_PERMISSION.getErrorCode(), ErrorCode.OPERATION_PERMISSION.getMessage());
	}

}
