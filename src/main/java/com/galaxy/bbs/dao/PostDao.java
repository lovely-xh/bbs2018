package com.galaxy.bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.galaxy.bbs.domain.Post;

/**
 * Post的DAO类
 *
 */
@Repository
public class PostDao extends BaseDao<Post> {

	private static final String GET_PAGED_POSTS = "from t_post where topic_id=? order by createTime desc";

	private static final String DELETE_TOPIC_POSTS = "delete from t_post where topic_id=?";
	
	private static String INSERT_POST_SQL = "INSERT INTO t_post(board_id, topic_id, user_id, post_type, "
			+ "post_title, post_text, create_time) VALUES(?,?,?,?,?,?,?)";
	
	private static String REMOVE_POST_SQL = "DELETE FROM t_post WHERE post_id=?";
	
	private static String GET_POST_SQL = "SELECT * FROM t_post WHERE post_id=?";
	
	public int save(final Post post) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ppst = con.prepareStatement(INSERT_POST_SQL, new String[] { "post_id"} );
				ppst.setInt(1, post.getBoardId());
				ppst.setInt(2, post.getTopicId());
				ppst.setInt(3, post.getUserId());
				ppst.setInt(4, post.getPostType());
				ppst.setString(5, post.getPostTitle());
				ppst.setString(6, post.getPostText());
				ppst.setString(7, post.getCreateTime());
				return ppst;
			}
		}, keyHolder);

		if (keyHolder.getKey() == null) {
			return -1;
		} else {
			return keyHolder.getKey().intValue();
		}
	}
	
	public Page getPagedPosts(int topicId, int pageNo, int pageSize) {
		return pagedQuery(GET_PAGED_POSTS, pageNo, pageSize, topicId);
	}
	
	public Post getPost(int post_id) {
		return getJdbcTemplate().queryForObject(GET_POST_SQL, Post.class);
	}
    
	/**
	 * 删除主题下的所有帖子
	 * @param topicId 主题ID
	 */
	public void deleteTopicPosts(int topicId) {
		getJdbcTemplate().update(DELETE_TOPIC_POSTS, topicId);
	}
	
	public void remove(int postId) {
		getJdbcTemplate().update(REMOVE_POST_SQL, new Object[] { postId });
	}

}
