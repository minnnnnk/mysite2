package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestBookVo;

public class GuestBookDao {
	//필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	//생성자
	
	//메소드 gs
	
	//메도스 일반
	public void getConnecting() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 	
	}
	
	public void Close() {
		try {
			if (rs != null) {
				rs.close();
			} 
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
		
	public int add(GuestBookVo guestBookVo) {
		
		int count = -1;
		try {
			this.getConnecting();
			
			
			// 3. SQL문 준비 / 바인딩 / 실행
			//SQl문 준비
			String query = "";
			query += " insert into guestbook ";
			query += " values(seq_guestbook_no.nextval,?,?,?,sysdate) ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestBookVo.getName());
			pstmt.setString(2, guestBookVo.getPassword());
			pstmt.setString(3, guestBookVo.getContent());
			
			//실행
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println(count+ "건 등록되었습니다");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.Close();
		return count;
		
	}
	
	public List<GuestBookVo> getGuestList() {
		List<GuestBookVo> gList = new ArrayList<GuestBookVo>();
		
		try {
			this.getConnecting();
			
			
			// 3. SQL문 준비 / 바인딩 / 실행
			//SQl문 준비
			String query = "";
			query += " select  no ";
			query += "         ,name ";
			query += "         ,password ";
			query += "         ,content ";
			query += "         ,reg_date ";
			query += " from guestbook ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			
			//실행
			rs= pstmt.executeQuery();
			//결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");
				
				GuestBookVo gVo = new GuestBookVo(no,name,password,content,regDate);
				
				gList.add(gVo);
				
				System.out.println(gVo.toString());
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.Close();
		
		
		
		
		return gList;
	}
	 
	
	
}

