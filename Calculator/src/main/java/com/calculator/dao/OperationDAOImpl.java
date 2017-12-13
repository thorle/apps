package com.calculator.dao;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.calculator.model.OperationDTO;
import com.calculator.model.OperationTypeDTO;

@Transactional
@Repository
public class OperationDAOImpl implements OperationDAO {

	@Autowired
	private JdbcTemplate jdbc;

	public static final String SAVE_OPERATION = "INSERT INTO calculation (user_id, operation_id, first_number, second_number, result)"
			+ "VALUES(?,?,?,?,?)";
	public static final String GET_ALL = "SELECT user_id, c.operation_id, operation_name, first_number, second_number, result \r\n"
			+ "FROM calculation c " + "INNER JOIN operation o ON c.operation_id = o.operation_id";
	public static final String GET_ALL_OPERATION = "SELECT operation_id, operation_name FROM operation";

	@Override
	public boolean saveOperation(OperationDTO operation) {
		return jdbc.update(SAVE_OPERATION, new Object[] { operation.getUserId(), operation.getOperationId(),
				operation.getFirstNumber(), operation.getSecondNumber(), operation.getResult() }) > 0;
	}

	@Override
	public List<OperationDTO> getAllOperation() {
		return jdbc.query(GET_ALL, (ResultSet row, int rowNum) -> {
			return new OperationDTO(row, rowNum);
		});
	}

	@Override
	public List<OperationTypeDTO> getAllOperationType() {
		return jdbc.query(GET_ALL_OPERATION, (ResultSet rs, int row) -> {
			return new OperationTypeDTO(rs, row);
		});
	}

}
