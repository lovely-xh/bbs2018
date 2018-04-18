package com.galaxy.bbs.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.galaxy.bbs.domain.User;
import com.galaxy.bbs.exception.UserExistException;
import com.galaxy.bbs.service.UserService;

/**
 * 用户注册控制器
 */
@Controller                   
public class RegisterController extends BaseController {

	@Autowired
	private UserService userService;
	
	private static final Logger logger = Logger.getLogger(RegisterController.class);
	
	/**
	 * 用户注册
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(HttpServletRequest request, User user) {
		
		logger.info(String.format("注册信息：uname[%s], utype[%d]", user.getUserName(), user.getUserType()));
		
		ModelAndView view = new ModelAndView();
		view.setViewName("/success");
		try {
			userService.register(user);
		} catch (UserExistException e) {
			logger.error("用户注册出错，用户名已存在！");
			view.addObject("errorMsg", "用户名已经存在！");
			view.setViewName("forward:/register.jsp");
		}

		setSessionUser(request, user);
		
		logger.info(String.format("用户:%s 注册成功.", user.getUserName()));
		return view;
	}
	
}
