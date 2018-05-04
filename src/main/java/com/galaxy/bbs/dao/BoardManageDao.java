package com.galaxy.bbs.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.galaxy.bbs.domain.Board;

@Repository
public class BoardManageDao extends BaseDao<Board>{
	
	private static final String UPDATE_BOARD_MANAGER_SQL = "UPDATE t_board SET user_id=? WHERE board_id=?";
	
	private static final String REMOVE_BOARD_MANAGER_SQL = "DELETE from t_board_manager WHERE board_id=? AND user_id=?";
	
	private static final String REMOVE_ALL_BOARD_MANAGER_SQL = "DELETE from t_board_manager WHERE user_id=?";
	
	private static final String INSERT_BOARD_MANAGER_SQL = "INSERT INTO t_board_manager(board_id, user_id) VALUES(?,?)";
	
	private static final String GET_USERS_MANAGE_BOARD_SQL = "SELECT user_id FROM t_board_manager WHERE board_id=?";
	
	private static final String GET_BOARD_MANAGERED_BY_USER_SQL = "SELECT board_id FROM t_board_manager WHERE user_id=?";
	
	public void save(int board_id, int user_id) {
		getJdbcTemplate().update(INSERT_BOARD_MANAGER_SQL, new Object[] { board_id, user_id });
	}
	
	public void remove(int board_id, int user_id) {
		getJdbcTemplate().update(REMOVE_BOARD_MANAGER_SQL, new Object[] { board_id, user_id });
	}
	
	public void removeAll(int user_id) {
		getJdbcTemplate().update(REMOVE_ALL_BOARD_MANAGER_SQL, new Object[] { user_id });
	}
	
	public void update(int board_id, int user_id) {
		getJdbcTemplate().update(UPDATE_BOARD_MANAGER_SQL, new Object[] { user_id, board_id });
	}
	
	public List<Integer> queryUsersByBoardId(int board_id) {
		return getJdbcTemplate().queryForObject(GET_USERS_MANAGE_BOARD_SQL, new Object[] { board_id },
				new BeanPropertyRowMapper<List<Integer>>());
	}
	
	public List<Integer> queryBoardsByUserId(int user_id) {
		return getJdbcTemplate().queryForList(GET_BOARD_MANAGERED_BY_USER_SQL, new Object[] { user_id }, Integer.class);
	}
	
}
