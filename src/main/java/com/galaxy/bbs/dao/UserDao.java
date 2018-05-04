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

import com.galaxy.bbs.domain.User;

/**
 * User对象Dao
 */
@Repository
public class UserDao extends BaseDao<User> {
	
	private static final String GET_USER_BY_UNAME_SQL = "SELECT * FROM t_user WHERE user_name = ?";
	
	private static final String GET_USER_LIKE_UNAME_SQL = "SELECT * FROM t_user WHERE user_name like %?%";

	private static final String GET_ALL_USER_SQL = "SELECT * FROM t_user order by user_id";
	
	private static final String UPDATE_LOGIN_INFO_SQL = "UPDATE t_user SET last_visit=?, last_ip=?, credit=? WHERE user_id=?";
	
	private static final String UPDATE_USER_INFO_SQL = "UPDATE t_user SET user_type=?, locked=?, credit=? WHERE user_id=?";
	
	private static final String MATCH_COUNT_SQL = "SELECT COUNT(*) FROM t_user WHERE user_name = ?";
	
	private static final String INSERT_USER_SQL = "INSERT INTO t_user(user_name, password, user_type, locked, credit, last_visit, "
			+ "last_ip) VALUES (?,?,?,?,?,?,?)";
	
	public int save(final User user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ppst = con.prepareStatement(INSERT_USER_SQL, new String[] { "user_id"} );
				ppst.setString(1, user.getUserName());
				ppst.setString(2, user.getPassword());
				ppst.setInt(3, user.getUserType());
				ppst.setInt(4, user.getLocked());
				ppst.setInt(5, user.getCredit());
				ppst.setString(6, user.getLastVisit());
				ppst.setString(7, user.getLastIp());
				return ppst;
			}
		}, keyHolder);

		if (keyHolder.getKey() == null) {
			return -1;
		} else {
			return keyHolder.getKey().intValue();
		}
	}
	
	// TODO: 考虑需要删除账户的场景
	
	/**
	 * 更新用户记录
	 * 
	 * @param user
	 */
	public void updateLoginInfo(User user) {
		getJdbcTemplate().update(UPDATE_LOGIN_INFO_SQL, new Object[] { user.getLastVisit(),
				user.getLastIp(), user.getCredit(), user.getUserId() });
	}

	public void updateUserInfo(User user) {
		getJdbcTemplate().update(UPDATE_USER_INFO_SQL, new Object[] { user.getUserType(),
				user.getLocked(), user.getCredit(), user.getUserId() });
	}
	
	public void updateUserPwd(User user) {
	}
	
	public User queryUserByUserName(String userName){
		User user = getJdbcTemplate().queryForObject(GET_USER_BY_UNAME_SQL, new Object[] { userName }, 
				new BeanPropertyRowMapper<User>(User.class));
		return user;
    }
	
	public List<User> queryUserLikeUserName(String name) {
		return getJdbcTemplate().query(GET_USER_LIKE_UNAME_SQL, new Object[] { name }, 
				new BeanPropertyRowMapper<User>(User.class));
	}
	
	public List<User> queryAllUsers() {
		return getJdbcTemplate().query(GET_ALL_USER_SQL, new BeanPropertyRowMapper<User>(User.class));
	}

	/**
	 * 获取该用户名的计数
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public int queryMatchCount(String userName) {
		return getJdbcTemplate().queryForObject(MATCH_COUNT_SQL, new Object[] { userName }, Integer.class);
	}

}
