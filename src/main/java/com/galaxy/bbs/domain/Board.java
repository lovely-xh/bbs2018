package com.galaxy.bbs.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 论坛版块
 */
public class Board extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1377934755966421572L;

	private int boardId;

	private String boardName;

	private String boardDesc;

	private int topicNum ;
	
	private Set<User> users = new HashSet<User>();

	public int getTopicNum() {
		return topicNum;
	}

	public void setTopicNum(int topicNum) {
		this.topicNum = topicNum;
	}

	public String getBoardDesc() {
		return boardDesc;
	}

	public void setBoardDesc(String boardDesc) {
		this.boardDesc = boardDesc;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
