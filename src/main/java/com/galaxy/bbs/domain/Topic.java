package com.galaxy.bbs.domain;

/**
 * 论坛主题
 * 
 * @author user
 *
 */
public class Topic extends BaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1598544394030640118L;
	/**
	 * 精华主题帖子
	 */
	public static final int DIGEST_TOPIC = 1;
	/**
	 * 普通的主题帖子
	 */
	public static final int NOT_DIGEST_TOPIC = 0;

	private int topicId;

	private String topicTitle;

	private int userId;

	private int boardId;

	private MainPost mainPost = new MainPost();

	/** 最后一次跟帖时间 */
	private String lastPost;
	
	private int replies;

	private String createTime;

	private int views;

	private int digest = NOT_DIGEST_TOPIC;

	public int getDigest() {
		return digest;
	}

	public void setDigest(int digest) {
		this.digest = digest;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getReplies() {
		return replies;
	}

	public void setReplies(int replies) {
		this.replies = replies;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public MainPost getMainPost() {
		return mainPost;
	}

	public void setMainPost(MainPost mainPost) {
		this.mainPost = mainPost;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLastPost() {
		return lastPost;
	}

	public void setLastPost(String lastPost) {
		this.lastPost = lastPost;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
