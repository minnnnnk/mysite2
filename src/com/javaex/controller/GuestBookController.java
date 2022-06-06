package com.javaex.controller;

import java.io.IOException;
import java.util.List;

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
			request.setCharacterEncoding("UTF-8");
		 String action = request.getParameter("action");
		 
		
		 if("list".equals(action)) {
			 System.out.println("guestbook>list");
			 GuestBookDao gDao = new GuestBookDao();
			 
			 List<GuestBookVo> gList = gDao.getGuestList();
			 
			 request.setAttribute("gList", gList);
			 
			 WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		 
		 }if("add".equals(action)) {
			 System.out.println("guestbook>add");
			 String name = request.getParameter("name");
			 String password = request.getParameter("password");
			 String content = request.getParameter("content");
			 
			 GuestBookVo guestBookVo = new GuestBookVo(name,password,content);
			 
			 GuestBookDao gDao = new GuestBookDao();
			 
			 gDao.add(guestBookVo);
			 
			 WebUtil.redirect(request, response, "/mysite2/guestbook?action=list");
		 }if("deleteForm".equals(action)) {
			 System.out.println("guestbook>deleteForm");
				
			 int no =Integer.parseInt(request.getParameter("no"));
			 GuestBookDao gDao = new GuestBookDao();
			 GuestBookVo gVo = gDao.getGuest(no);
			 request.setAttribute("gVo", gVo);
					 
			 
			 WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
		 }if("delete".equals(action)) {
			 System.out.println("guestbook>delete");
			 
			 int no =Integer.parseInt(request.getParameter("no"));
			 String password = request.getParameter("password");
			 GuestBookDao gDao = new GuestBookDao();
			 GuestBookVo gVo = new GuestBookVo(no,password);
			 System.out.println(gVo);
			 gDao.delete(gVo);

			 WebUtil.redirect(request, response, "/mysite2/guestbook?action=list");
		 }
	
	} 	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doGet(request, response);
	}

}
