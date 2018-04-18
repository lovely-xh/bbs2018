package com.galaxy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.galaxy.bbs.domain.Topic;

/**
 * topic 的DAO类
 *
 */
@Repository
public class TopicDao extends BaseDao<Topic> {
	
	private static final String GET_PAGED_TOPICS = "from t_topic where board_id=? order by last_post desc";
	
	private static final String GET_TOPICS_BY_TITLE = "from t_topic where topic_title like %?% order by last_post desc";
	
	private static final String GET_TOPIC_SQL = "SELECT * FROM t_topic WHERE topic_id=?";

	private static final String INSERT_TOPIC_SQL = "INSERT INTO t_topic(board_id, topic_title, user_id, create_time, "
			+ "last_post, topic_views, topic_replies, digest) VALUES(?,?,?,?,?,?,?,?)";
	
	private static final String REMOVE_TOPIC_SQL = "DELETE FROM t_topic WHERE topic_id=?";
	
    private static final String UPDATE_TOPIC_SQL = "UPDATE set last_post=?, topic_views=?, topic_replies=?, digest=? WHERE topic_id=?";
	
	public int save(final Topic topic) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ppst = con.prepareStatement(INSERT_TOPIC_SQL, new String[] { "topic_id"} );
				ppst.setInt(1, topic.getBoardId());
				ppst.setString(2, topic.getTopicTitle());
				ppst.setInt(3, topic.getUserId());
				ppst.setString(4, topic.getCreateTime());
				ppst.setString(5, topic.getLastPost());
				ppst.setInt(6, topic.getViews());
				ppst.setInt(7, topic.getReplies());
				ppst.setInt(8, topic.getDigest());
				return ppst;
			}
		}, keyHolder);
		
		if (keyHolder.getKey() == null) {
			return -1;
		} else {
			return keyHolder.getKey().intValue();
		}
	}
	
	public void remove(int topic_id) {
		getJdbcTemplate().update(REMOVE_TOPIC_SQL, new Object[] { topic_id });
	}
	
	public void update(Topic topic) {
		Object[] args = { topic.getLastPost(), topic.getViews(), topic.getReplies(), topic.getDigest(), topic.getTopicId() };
		getJdbcTemplate().update(UPDATE_TOPIC_SQL, args);
	}

	/**
	 * 获取论坛版块分页的主题帖子
	 * @param boardId 论坛版块ID
	 * @param pageNo 页号，从1开始。
	 * @param pageSize 每页的记录数
	 * @return 包含分页信息的Page对象
	 */
	public Page getPagedTopics(int boardId, int pageNo, int pageSize) {
		return pagedQuery(GET_PAGED_TOPICS, pageNo, pageSize, boardId);
	}
   
	/**
	 * 根据主题帖标题查询所有模糊匹配的主题帖
	 * @param title 标题的查询条件
	 * @param pageNo 页号，从1开始。
	 * @param pageSize 每页的记录数
	 * @return 包含分页信息的Page对象
	 */
	public Page queryTopicByTitle(String title, int pageNo, int pageSize) {
		return pagedQuery(GET_TOPICS_BY_TITLE, pageNo, pageSize, title);
	}
	
	public Topic query(int topic_id) {
		Object[] args = { topic_id };
		return getJdbcTemplate().queryForObject(GET_TOPIC_SQL, args, new BeanPropertyRowMapper<Topic>(Topic.class));
	}
	
}
