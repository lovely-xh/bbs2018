package com.galaxy.bbs.domain;


/**
 * 论坛用户登陆日志PO类
 * 
 * @author user
 *
 */
public class LoginLog extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1636736637156550711L;

	private int loginLogId;
	
	private String loginDate;
	
    private int userId;
	
    private String ip;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getLoginLogId() {
		return loginLogId;
	}

	public void setLoginLogId(int loginLogId) {
		this.loginLogId = loginLogId;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

}
