package com.galaxy.bbs.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 论坛用户
 * 
 * @author user
 *
 */
public class User extends BaseDomain {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8972179420790822579L;
	/**
     *锁定用户对应的状态值 
     */
    public static final int USER_LOCK = 1;
    /**
     * 用户解锁对应的状态值
     */
    public static final int USER_UNLOCK = 0;
    /**
     * 管理员类型
     */
    public static final int FORUM_ADMIN = 2;
    /**
     * 普通用户类型
     */
    public static final int NORMAL_USER = 1;
    
    private int userId;

	private String userName;
    
    private int userType = NORMAL_USER;

	private String lastIp;
	
	/** 最新的访问时间 */
	private String lastVisit;
    
	private String password;

	private int locked ;

	private int credit;

	private Set<Board> manBoards = new HashSet<Board>();

	public User() {
	}

	public User(int uid) {
		this.userId = uid;
	}
	
	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public int getLocked() {
		return locked;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}
	
	public Set<Board> getManBoards()
    {
        return manBoards;
    }

    public void setManBoards(Set<Board> manBoards)
    {
        this.manBoards = manBoards;
    }

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

    public int getUserType()
    {
        return userType;
    }

    public void setUserType(int userType)
    {
        this.userType = userType;
    }

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public String getLastVisit() {
		return lastVisit;
	}

	public void setLastVisit(String lastVisit) {
		this.lastVisit = lastVisit;
	}

}
