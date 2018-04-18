package com.galaxy.bbs.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.bbs.dao.BoardDao;
import com.galaxy.bbs.dao.BoardManageDao;
import com.galaxy.bbs.dao.Page;
import com.galaxy.bbs.dao.PostDao;
import com.galaxy.bbs.dao.TopicDao;
import com.galaxy.bbs.dao.UserDao;
import com.galaxy.bbs.domain.Board;
import com.galaxy.bbs.domain.MainPost;
import com.galaxy.bbs.domain.Post;
import com.galaxy.bbs.domain.Topic;
import com.galaxy.bbs.domain.User;

/**
 * 论坛管理服务类，对论坛版块、话题、帖子进行管理
 */
@Service
public class ForumService {
	
	private static final Logger logger = Logger.getLogger(ForumService.class);

	@Autowired
	private TopicDao topicDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private BoardManageDao boardManageDao;

	/**
	 * 发表一个主题帖子,用户积分加10，论坛版块的主题帖数加1
	 * @param topic
	 */
	public void addTopic(Topic topic) {
		// 新增主题
		topicDao.save(topic);
		
		// 新增主题帖
		MainPost post = topic.getMainPost();
		post.setTopicId(topic.getTopicId());
		post.setCreateTime(topic.getCreateTime());
		post.setUserId(topic.getUserId());
		post.setPostTitle(topic.getTopicTitle());
		post.setBoardId(topic.getBoardId());
		postDao.save(post);
		
		// 板块数据更新
		Board board = boardDao.query(topic.getBoardId());
		board.setTopicNum(board.getTopicNum() + 1);	
		boardDao.update(board);
		
		// 用户积分更新
		User user = new User(topic.getUserId());	
		user.setCredit(user.getCredit() + 10);
		userDao.updateUserInfo(user);
	}
	
    
	/**
	 * 删除一个主题帖，用户积分减50，论坛版块主题帖数减1，删除
	 * 主题帖所有关联的帖子。
	 * @param topicId 要删除的主题帖ID
	 */
	public void removeTopic(int topicId) {   
		Topic topic = topicDao.query(topicId);

		// 将论坛版块的主题帖数减1
		Board board = boardDao.query(topic.getBoardId());
		board.setTopicNum(board.getTopicNum() - 1);
		boardDao.update(board);

		// 发表该主题帖用户扣除50积分
		User user = new User(topic.getUserId());
		user.setCredit(user.getCredit() - 50);
		userDao.updateUserInfo(user);

		// 删除主题帖及其关联的帖子
		postDao.deleteTopicPosts(topicId);
		topicDao.remove(topicId);
	}
	
	/**
	 * 添加一个回复帖子，用户积分加5分，主题帖子回复数加1并更新最后回复时间
	 * @param post
	 */
	public void addPost(Post post){
		postDao.save(post);
		
		User user = new User(post.getUserId());
		user.setCredit(user.getCredit() + 5);
		userDao.updateUserInfo(user);
		
		Topic topic = topicDao.query(post.getTopicId());
		topic.setReplies(topic.getReplies() + 1);
		topic.setLastPost(post.getCreateTime());
		topicDao.update(topic);
	}
	
	/**
	 * 删除一个回复的帖子，发表回复帖子的用户积分减20，主题帖的回复数减1
	 * @param postId
	 */
	public void removePost(int postId){
		Post post = postDao.getPost(postId);
		
		Topic topic = topicDao.query(post.getTopicId());
		topic.setReplies(topic.getReplies() - 1);
		topicDao.update(topic);
		
		User user = new User(post.getUserId());
		user.setCredit(user.getCredit() - 20);
		userDao.updateUserInfo(user);
		
		postDao.remove(postId);
	}
	
	

	/**
	 * 创建一个新的论坛版块
	 * 
	 * @param board
	 */
	public void addBoard(Board board) {
		boardDao.save(board);
	}
	
	/**
	 * 删除一个版块
	 * @param boardId
	 */
	public void removeBoard(int boardId){
		boardDao.remove(boardId);
	}
	
	/**
	 * 将帖子置为精华主题帖
	 * @param topicId 操作的目标主题帖ID
	 * @param digest 精华级别 可选的值为1，2，3
	 */
	public void markTopicDigest(int topicId){
		Topic topic = topicDao.query(topicId);
		topic.setDigest(Topic.DIGEST_TOPIC);
		User user = new User(topic.getUserId());
		user.setCredit(user.getCredit() + 50);
		
		topicDao.update(topic);
		userDao.updateUserInfo(user);
	}
	
    /**
     * 获取所有的论坛版块
     * @return
     */
    public List<Board> getAllBoards(){
        return boardDao.queryAll();
    }	
	
	/**
	 * 获取论坛版块某一页主题帖，以最后回复时间降序排列
	 * @param boardId
	 * @return
	 */
    public Page getPagedTopics(int boardId, int pageNo, int pageSize){
		return topicDao.getPagedTopics(boardId, pageNo, pageSize);
    }
    
    /**
     * 获取同主题每一页帖子，以最后回复时间降序排列
     * @param boardId
     * @return
     */
    public Page getPagedPosts(int topicId,int pageNo,int pageSize){
        return postDao.getPagedPosts(topicId,pageNo,pageSize);
    }    
    

	/**
	 * 查找出所有包括标题包含title的主题帖
	 * 
	 * @param title
	 *            标题查询条件
	 * @return 标题包含title的主题帖
	 */
	public Page queryTopicByTitle(String title,int pageNo,int pageSize) {
		return topicDao.queryTopicByTitle(title,pageNo,pageSize);
	}
	
	/**
	 * 根据boardId获取Board对象
	 * 
	 * @param boardId
	 */
	public Board getBoardById(int boardId) {
		return boardDao.query(boardId);
	}

	/**
	 * 根据topicId获取Topic对象
	 * @param topicId
	 * @return Topic
	 */
	public Topic getTopicByTopicId(int topicId) {
		return topicDao.query(topicId);
	}
	
	/**
	 * 获取回复帖子的对象
	 * @param postId
	 * @return 回复帖子的对象
	 */
	public Post getPostByPostId(int postId){
		return postDao.getPost(postId);
	}
    
	/**
	 * 将用户设为论坛版块的管理员
	 * @param boardId  论坛版块ID
	 * @param userName 设为论坛管理的用户名
	 */
	public void addBoardManager(int boardId, String userName){
	   	User user = userDao.queryUserByUserName(userName);
	   	if(user == null){
	   		throw new RuntimeException("用户名为" + userName + "的用户不存在。");
	   	} else {
	   		List<Integer> boardIds = boardManageDao.queryBoardsByUserId(user.getUserId());
	   		if (boardIds.contains(boardId)) {
	   			logger.warn("用户已是该版块版主！");
	   		} else {
	   			boardManageDao.save(boardId, user.getUserId());
	   			logger.info("设置成功！");
	   		}
	   	}
	}
	
	/**
	 * 更改主题帖
	 * @param topic
	 */
	public void updateTopic(Topic topic){
		topicDao.update(topic);
	}
	
	/**
	 * 更改回复帖子的内容
	 * @param post
	 */
	public void updatePost(Post post){
//		postDao.update(post);
	}
	
}
