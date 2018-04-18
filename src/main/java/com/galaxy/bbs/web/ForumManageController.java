package com.galaxy.bbs.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.galaxy.bbs.domain.Board;
import com.galaxy.bbs.domain.User;
import com.galaxy.bbs.service.ForumService;
import com.galaxy.bbs.service.UserService;

/**
 * 论坛管理，这部分功能由论坛管理员操作，包括：创建论坛版块、指定论坛版块管理员、用户锁定/解锁。
 */
@Controller
public class ForumManageController extends BaseController {
	
	@Autowired
	private ForumService forumService;

	@Autowired
	private UserService userService;

	/**
	 * 添加一个版块
	 * @return
	 */
	@RequestMapping(value = "/forum/addBoardPage", method = RequestMethod.GET)
	public String addBoardPage() {
		return "/addBoard";
	}

	/**
	 * 添加一个版块
	 * @param board
	 * @return
	 */
	@RequestMapping(value = "/forum/addBoard", method = RequestMethod.POST)
	public String addBoard(Board board) {
		forumService.addBoard(board);
		return "/addBoardSuccess";
	}

	/**
	 * 列出所有的论坛版块
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView listAllBoards() {
		ModelAndView view =new ModelAndView();
		List<Board> boards = forumService.getAllBoards();
		view.addObject("boards", boards);
		view.setViewName("/listAllBoards");
		return view;
	}

	/**
	 * 指定论坛管理员的页面
	 * @return
	 */
	@RequestMapping(value = "/forum/setBoardManagerPage", method = RequestMethod.GET)
	public ModelAndView setBoardManagerPage() {
		ModelAndView view =new ModelAndView();
		List<Board> boards = forumService.getAllBoards();
		List<User> users = userService.getAllUsers();
		view.addObject("boards", boards);
		view.addObject("users", users);
		view.setViewName("/setBoardManager");
		return view;
	}
	
    /**
     * 设置版块管理
     * @return
     */
	@RequestMapping(value = "/forum/setBoardManager", method = RequestMethod.POST)
	public ModelAndView setBoardManager(@RequestParam("userName") String userName ,@RequestParam("boardId") String boardId) {
		ModelAndView view =new ModelAndView();
		User user = userService.queryUserByUserName(userName);
		if (user == null) {
			view.addObject("errorMsg", "用户名(" + userName + ")不存在");
			view.setViewName("/fail");
		} else {
			forumService.addBoardManager(Integer.parseInt(boardId), userName);
			view.setViewName("/success");
		}

		return view;
	}

	/**
	 * 用户锁定及解锁管理页面
	 * @return
	 */
	@RequestMapping(value = "/forum/userLockManagePage", method = RequestMethod.GET)
	public ModelAndView userLockManagePage() {
		ModelAndView view =new ModelAndView();
		List<User> users = userService.getAllUsers();
		view.setViewName("/userLockManage");
		view.addObject("users", users);
		return view;
	}

	/**
	 * 用户锁定及解锁设定
	 * @return
	 */
	@RequestMapping(value = "/forum/userLockManage", method = RequestMethod.POST)
	public ModelAndView userLockManage(@RequestParam("userName") String userName ,@RequestParam("locked") String locked) {
		ModelAndView view =new ModelAndView();
        User user = userService.queryUserByUserName(userName);
		if (user == null) {
			view.addObject("errorMsg", "用户名(" + userName + ")不存在");
			view.setViewName("/fail");
		} else {
			user.setLocked(Integer.parseInt(locked));
			userService.lockUser(user.getUserName());
			view.setViewName("/success");
		}
		return view;
	}
}
