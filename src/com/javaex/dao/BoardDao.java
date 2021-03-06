package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	//필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url ="jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	//생성자
	
	//메소드 gs
	
	//메소드 일반
	public void getConnecting() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
		}catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 	
		
	}
	
	public void close() {
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
	
	

	public int write(BoardVo boardVo) {
		int count = -1;
		
		try {
			this.getConnecting();
			// 3. SQL문 준비 / 바인딩 / 실행
			
			//SQL문 준비
			String query = "";
			query += " insert into board ";
			query += " values(seq_board_no.nextval,?,?,0,sysdate,?) ";
			//바인딩
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());
			
			//실행
			count = pstmt.executeUpdate();
			// 4.결과처리
			System.out.println(count+"건 등록되었습니다.");
		}catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		this.close();
		return count;
	}
	
	   public BoardVo getBoard(int no) {
		      BoardVo bVo = null;
		      try {
		         this.getConnecting();
		         // 3. SQL문 준비 / 바인딩 / 실행
		         //SQl문 준비
		         String query = "";
		         query += " select  b.no ";
		         query += "         ,title ";
		         query += "         ,content ";
		         query += "         ,name ";
		         query += "         ,hit ";
		         query += "         ,reg_date ";
		         query += "         ,user_no ";
		         query += " from board b , users u ";
		         query += " where b.user_no = u.no ";
		         query += " and b.no= ? ";

		         //바인딩
		         pstmt = conn.prepareStatement(query);
		         pstmt.setInt(1,no);
		         //실행
		         rs= pstmt.executeQuery();
		         //결과처리
		         if(rs.next()) {
		            String title = rs.getString("title");
		            String content = rs.getString("content");
		            String name = rs.getString("name");
		            int hit = rs.getInt("hit");
		            String regDate = rs.getString("reg_date");
		            int userNo = rs.getInt("user_no");
		            
		            bVo = new BoardVo(no,title,content,name,hit,regDate,userNo);
		            
		         }
		      } catch (SQLException e) {
		         System.out.println("error:" + e);
		      } 
		      
		      this.close();
		      return bVo;
		   }
		public List<BoardVo> searchList(String title) {
			List<BoardVo> bList = new ArrayList<BoardVo>();
			this.getConnecting();
			try {
			
				// 3. SQL문 준비 / 바인딩 / 실행
				
				//SQL문 준비
		         String query = "";
		         query += " select  b.no ";
		         query += "         ,title ";
		         query += "         ,content ";
		         query += "         ,name ";
		         query += "         ,hit ";
		         query += "         ,to_char(reg_date,'YY-MM-DD HH24:MM') reg_date ";
		         query += "         ,user_no ";
		         query += " from board b , users u ";
		         query += " where b.user_no = u.no ";
		         query += " and title like '%'||?||'%' ";
		         query += " order by b.no desc ";
				
				//바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, title);
				//실행
				rs = pstmt.executeQuery();
				// 4.결과처리
		         while(rs.next()) {
		        	int no = rs.getInt("no");
		        	String titlee = rs.getString("title");
		            String content = rs.getString("content");
		            String name = rs.getString("name");
		            int hit = rs.getInt("hit");
		            String regDate = rs.getString("reg_date");
		            int userNo = rs.getInt("user_no");
		            
		            BoardVo bVo = new BoardVo(no,titlee,content,name,hit,regDate,userNo);
					
					System.out.println(bVo);
					
					bList.add(bVo);
					
					
				}
			}catch (SQLException e) {
				System.out.println("error:" + e);
			} 
			this.close();
			return bList;
		}
	   public int modify(BoardVo boardVo) {
			int count = -1;
			
			try {
				this.getConnecting();
				// 3. SQL문 준비 / 바인딩 / 실행
				
				//SQL문 준비
				String query = "";
				query += " update board ";
				
				query += " set title = ?,";
				query += "     content = ? ";
				query += " where no = ? ";
				//바인딩
				
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, boardVo.getTitle());
				pstmt.setString(2, boardVo.getContent());
				pstmt.setInt(3, boardVo.getNo());
				
				//실행
				count = pstmt.executeUpdate();
				// 4.결과처리
				System.out.println(count+"건 변경되었습니다.");
			}catch (SQLException e) {
				System.out.println("error:" + e);
			} 
			
			this.close();
			return count;
		}
	   
	   public int delete(int no) {
			int count = -1;
			
			try {
				this.getConnecting();
				// 3. SQL문 준비 / 바인딩 / 실행
				
				//SQL문 준비
				String query = "";
				query += " delete from board ";
				query += " where no = ? ";
				//바인딩
				
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, no);
				
				//실행
				count = pstmt.executeUpdate();
				// 4.결과처리
				System.out.println(count+"건 삭제되었습니다.");
			}catch (SQLException e) {
				System.out.println("error:" + e);
			} 
			
			this.close();
			return count;
		}
	   
	   public int hitUpdate(int no) {
		   int count = -1;
			
			try {
				this.getConnecting();
				// 3. SQL문 준비 / 바인딩 / 실행
				
				//SQL문 준비
				String query = "";
				query += " update board ";
				query += " set hit = hit+1 ";
				query += " where no = ? ";
				//바인딩
				
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, no);
				
				//실행
				count = pstmt.executeUpdate();
				// 4.결과처리
				System.out.println(count+"건 변경되었습니다.");
			}catch (SQLException e) {
				System.out.println("error:" + e);
			} 
			
			this.close();
			return count;
	   }
	
}
