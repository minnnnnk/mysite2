package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
   

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("UserController");
		
		String action = request.getParameter("action");
		
		if("joinForm".equals(action)) {//회원가입폼
			System.out.println("UserController>joinForm");
			//회원가입 폼 포어드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		}else if ("join".equals(action)) {//회원가입
			System.out.println("UserController>join");
			
			//파라미터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			System.out.println(id);
			//vo만들기
			UserVo userVo = new UserVo(id,password,name,gender);
			System.out.println(userVo);
			//dao에 넣기
			
			UserDao userDao = new UserDao();
			
			userDao.insert(userVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
		} else if ("loginForm".equals(action)) {
			System.out.println("UserController>loginForm");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		} else if("login".equals(action)) {
			System.out.println("UserController>login");
			
			//파라미터 꺼내기
			String id = request.getParameter("id");;
			String password = request.getParameter("password");
			
			//VO만들기
			UserVo userVo = new UserVo();
			userVo.setId(id);
			userVo.setPassword(password);
			
			//DAO
			UserDao userDao = new UserDao();
			
			UserVo authUser = userDao.getUser(userVo);
			
			// 
			if(authUser == null) {
				System.out.println("로그인실패");
			}else {
				System.out.println("로그인성공");
				
				HttpSession session =	request.getSession();
				session.setAttribute("authUser",authUser);
				
				//메인 리다이렉트
				
				WebUtil.redirect(request, response, "/mysite2/main");
			}	
			
		}else if("logout".equals(action)) {
			System.out.println("UserController>logout");
			
			//세션값 지운다
			HttpSession session= request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			
			WebUtil.redirect(request, response, "/mysite2/main");
		}else if("modifyForm".equals(action)){
			System.out.println("UserController>modifyForm");
			
			HttpSession session =	request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			int no = authUser.getNo();
			
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getPerson(no);
			
			
			request.setAttribute("userVo", userVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		}else if("modify".equals(action)) {
			System.out.println("UserController>modify");
			
			
			
			//세션에서 no
			HttpSession session =	request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			//파라미터 꺼내기
			String password =  request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			
			//VO에 담기
			UserVo userVo = new UserVo();
			System.out.println(userVo);
			userVo.setNo(no);
			userVo.setPassword(password);
			userVo.setName(name);
			userVo.setGender(gender);
			
			
			
			//다오만들어서 바꿔주기
			UserDao userDao = new UserDao();
			
			userDao.update(userVo);
			
			
			WebUtil.redirect(request, response, "/mysite2/main");
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
