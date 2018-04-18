package com.galaxy.bbs.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.galaxy.bbs.dao.LoginLogDao;
import com.galaxy.bbs.dao.UserDao;
import com.galaxy.bbs.domain.LoginLog;
import com.galaxy.bbs.domain.User;
import com.galaxy.bbs.exception.UserExistException;
import com.galaxy.bbs.utils.DateFormatUtil;

/**
 * 用户管理服务类，负责查询用户、注册用户、锁定用户等操作
 */
@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LoginLogDao loginLogDao;

	/**
	 * 注册一个新用户,如果用户名已经存在此抛出UserExistException的异常
	 * @param user 
	 */
	public void register(User user) throws UserExistException{
		int count = userDao.queryMatchCount(user.getUserName());
		if(count > 0){
		    throw new UserExistException("用户名已经存在");
		} else {
			// 首次注册送积分100
		    user.setCredit(100);
            user.setUserType(User.NORMAL_USER);
            // TODO：增加注册日期信息
            userDao.save(user);
		}
	}
	
	/**
	 * 将用户锁定，锁定的用户不能够登录
	 * @param userName 锁定目标用户的用户名
	 */
	public void lockUser(String userName){
		User user = userDao.queryUserByUserName(userName);
		user.setLocked(User.USER_LOCK);
	    userDao.updateUserInfo(user);
	}
	
	/**
	 * 解除用户的锁定
	 * @param userName 解除锁定目标用户的用户名
	 */
	public void unlockUser(String userName){
		User user = userDao.queryUserByUserName(userName);
		user.setLocked(User.USER_UNLOCK);
		userDao.updateUserInfo(user);
	}
	
	/**
	 * 根据用户名为条件，执行模糊查询操作 
	 * @param userName 查询用户名
	 * @return 所有用户名前导匹配的userName的用户
	 */
	public List<User> queryUserLikeUserName(String userName){
		return userDao.queryUserLikeUserName(userName);
	}
	
	public User queryUserByUserName(String userName){
		return userDao.queryUserByUserName(userName);
	}
	
	/**
	 * 获取所有用户
	 * @return 所有用户
	 */
	public List<User> getAllUsers(){
		return userDao.queryAllUsers();
	}
	
	/**
	 * 登陆成功
	 * @param user
	 */
	public void loginSuccess(User user) {
		user.setCredit(5 + user.getCredit());
		LoginLog loginLog = new LoginLog();
		loginLog.setUserId(user.getUserId());
		loginLog.setIp(user.getLastIp());
		loginLog.setLoginDate(DateFormatUtil.getSimpleDateFormat(new Date()));
        userDao.updateLoginInfo(user);
        loginLogDao.save(loginLog);
	}
	
}
