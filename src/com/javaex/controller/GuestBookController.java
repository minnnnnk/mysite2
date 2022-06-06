package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVo;


@WebServlet("/guestbook")
public class GuestBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String action = request.getParameter("action");
		 
		 WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		 
		 if("add".equals(action)) {
			 
			 String name = request.getParameter("name");
			 String password = request.getParameter("password");
			 String content = request.getParameter("content");
			 
			 GuestBookVo guestBookVo = new GuestBookVo(name,password,content);
			 
			 GuestBookDao gDao = new GuestBookDao();
			 
			 gDao.add(guestBookVo);
			 
			 
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		 }else if ("list".equals(action)) {
			 
			 GuestBookDao gDao = new GuestBookDao();
			 
			 gDao.getGuestList();
		 }
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
