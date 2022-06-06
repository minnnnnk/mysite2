package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
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
	
	//회원가입 --> (db)insert
	public int insert(UserVo userVo) {
		int count = -1;
		try {
			this.getConnecting();
			
			
			// 3. SQL문 준비 / 바인딩 / 실행
			//SQl문 준비
			String query = "";
			query += " insert into users ";
			query += " values(SEQ_USERS_NO.nextval,?,?,?,?) ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
			
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
	
	public UserVo getUser(UserVo userVo) {
		UserVo authUser = null;
		try {
			this.getConnecting();
			
			
			// 3. SQL문 준비 / 바인딩 / 실행
			//SQl문 준비
			String query = "";
			query += " select  no ";
			query += "         ,id	";
			query += "         ,name  ";
			query += "         ,password  ";
			query += "         ,gender  ";
			query += " from users  ";
			query += " where id = ?  ";
			query += " and password = ?  ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			//실행
			rs = pstmt.executeQuery();
			
			//결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String id = rs.getString("id");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String gender = rs.getString("gender");
				
				authUser = new UserVo();
				authUser.setId(id);
				authUser.setNo(no);
				authUser.setName(name);
				authUser.setPassword(password);
				authUser.setGender(gender);
			}
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.Close();
		return authUser;
	}
	
	public UserVo getPerson(int no) {
		UserVo userVo = null;
		try {
			this.getConnecting();
			
			
			// 3. SQL문 준비 / 바인딩 / 실행
			//SQl문 준비
			String query = "";
			query += " select  no ";
			query += "         ,id	";
			query += "         ,name  ";
			query += "         ,password  ";
			query += "         ,gender  ";
			query += " from users  ";
			query += " where no = ?  ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			//실행
			rs = pstmt.executeQuery();
			
			//결과처리
			while(rs.next()) {
				int userno = rs.getInt("no");
				String id = rs.getString("id");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String gender = rs.getString("gender");
				
				userVo = new UserVo(userno,id,password,name,gender);
				
				
			}
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.Close();
		return userVo;
	}
	public int update(UserVo userVo) {
		int count = -1;
		try {
			this.getConnecting();
			
			
			// 3. SQL문 준비 / 바인딩 / 실행
			//SQl문 준비
			String query = "";
			query += " update users ";
			query += " set password = ? ";
			query += "     ,name = ? ";
			query += "     ,gender = ? ";
			query += " where no = ? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getPassword());
			pstmt.setString(2, userVo.getName());
			pstmt.setString(3, userVo.getGender());
			pstmt.setInt(4, userVo.getNo());
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
	
}
