package com.galaxy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.galaxy.bbs.domain.Board;

@Repository
public class BoardDao extends BaseDao<Board>{
	
	private static final String GET_BOARD_SQL = "select * from t_board where board_id=?";
	
	private static final String GET_ALL_BOARDS_SQL = "select * from t_board order by board_id";
	
	private static final String INSERT_BOARD_SQL = "INSERT INTO t_board(board_name, board_desc, topic_num) VALUES(?,?,?)";
	
	private static final String UPDATE_BOARD_SQL = "UPDATE t_board SET board_name=?, board_desc=?, topic_num=?";
	
	private static final String REMOVE_BOARD_SQL = "DELETE from t_board WHERE board_id=?";
	
	public int save(final Board board) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ppst = con.prepareStatement(INSERT_BOARD_SQL, new String[] { "board_id"} );
				ppst.setString(1, board.getBoardName());
				ppst.setString(2, board.getBoardDesc());
				ppst.setInt(3, board.getTopicNum());
				return ppst;
			}
		}, keyHolder);
		
		if (keyHolder.getKey() == null) {
			return -1;
		} else {
			return keyHolder.getKey().intValue();
		}
	}
	
	public Board query(int boardId) {
		return getJdbcTemplate().queryForObject(GET_BOARD_SQL, new Object[] { boardId },
				new BeanPropertyRowMapper<Board>(Board.class));
	}
	
	public List<Board> queryAll() {
		return getJdbcTemplate().query(GET_ALL_BOARDS_SQL, new BeanPropertyRowMapper<Board>(Board.class));
	}
	
	public void update(Board board) {
		Object[] args = { board.getBoardName(), board.getBoardDesc(), board.getTopicNum() };
		getJdbcTemplate().update(UPDATE_BOARD_SQL, args);
	}
	
	public void remove(int boardId) {
		getJdbcTemplate().update(REMOVE_BOARD_SQL, new Object[] { boardId });
	}
	
}
