package com.galaxy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.galaxy.bbs.domain.LoginLog;

/**
 * Post的DAO类
 *
 */
@Repository
public class LoginLogDao extends BaseDao<LoginLog> {

	//保存登陆日志SQL
	private final static String INSERT_LOGIN_LOG_SQL= "INSERT INTO t_login_log(user_id,ip,login_datetime) VALUES(?,?,?)";
	
	public int save(final LoginLog loginLog) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ppst = con.prepareStatement(INSERT_LOGIN_LOG_SQL, new String[] { "login_log_id"} );
				ppst.setInt(1, loginLog.getUserId());
				ppst.setString(2, loginLog.getIp());
				ppst.setString(3, loginLog.getLoginDate());
				return ppst;
			}
		}, keyHolder);
		
		if (keyHolder.getKey() == null) {
			return -1;
		} else {
			return keyHolder.getKey().intValue();
		}
	}

}
