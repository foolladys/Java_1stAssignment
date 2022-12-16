package edu.global.ex.dto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BDao {

	///커넥션풀을 사용하기 위한 소스코드 (DataSource를 import할 시 java.sql로 해야한다.
	private DataSource dataSource = null;
	
	//기존에driver를 설정하고 Class.forName(driver)를 넣는 방식과는 달리 context.xml에 
    //리소스를 설정해주었다. context.xml에 있는 소스를 읽기 위해 context객체 생성한다
	public BDao() {
		try {
			//jdbc/oracle : context.xml에 들어간 Resource에서 name에 해당하는 부분 
            //위의 식은 context.xml에서 name을 lookup 찾으라는 뜻이다. 
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/oracle");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<BDto> list(){
		ArrayList<BDto> dtos = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			String query= "select * from mvc_board order by bgroup desc, bstep asc";
			// desc => 내림차순, asc => 오름차순
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				int bid = rs.getInt("bid");
				String bname = rs.getString("bname");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				Timestamp bdate = rs.getTimestamp("bdate");
				int bhit = rs.getInt("bhit");
				int bgroup = rs.getInt("bgroup");
				int bstep = rs.getInt("bstep");
				int bindent = rs.getInt("bindent"); 
				
				BDto dto = new BDto(bid, bname, btitle, bcontent,
						bdate, bhit, bgroup, bstep, bindent);
				
				dtos.add(dto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
				if(rs != null) rs.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}	
		
		return dtos;
		
	}
	
	public void write(String bname, String btitle, String bcontent){
		
		System.out.println("write() ..");
		//디버깅 문구
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			String query = "insert into mvc_board "
		               + "(bid, bname, btitle, bcontent, bhit, bgroup, bstep, bindent)"
		               + "values (mvc_board_seq.nextval,?,?,?,0, mvc_board_seq.currval,0,0)";
			
			// 이때 query 안의 ?,?,? 는 각각 bname, btitle, bcontent에 해당된다.
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, bname);
			preparedStatement.setString(2, btitle);
			preparedStatement.setString(3, bcontent);
			
			int rn = preparedStatement.executeUpdate();
			System.out.println("업데이트 갯수 : " + rn);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
				
			} catch (Exception e2) {
			}
		}	
	}
	
	/* 리턴타입이 BDto인 이유 : 글 목록을 다 표시하려면 ArrayList를 써줘야하지만
	 * 글 하나의 내용만 표시하기 때문에 테이블의 한 행만 들고오면 되기때문에 BDto로 타입을 지정해주는것.
	 
	 	파라미터가  String bid만 있는 이유 : list
	 */
	public BDto contentView(String bid){

		BDto dto = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		hitUpper(bid);
		//쿼리문 작성시 찾으려는 글번호에 ?로 넘기고 아래서 preparedStatement로 set해준다.
		try {
			String query= "select * from mvc_board where bid=?";
			//String query2= "update mvc_board set bhit = bhit + 1 where bgroup = ?";
			
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			//preparedStatement = connection.prepareStatement(query2);
			
			//int형으로 setInt해야하는데, bid의 자료형은 String이므로 Integer로 바꿔준다. 
			preparedStatement.setInt(1, Integer.valueOf(bid));
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("bid");
				String bname = rs.getString("bname");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				Timestamp bdate = rs.getTimestamp("bdate");
				int bhit = rs.getInt("bhit");
				int bgroup = rs.getInt("bgroup");
				int bstep = rs.getInt("bstep");
				int bindent = rs.getInt("bindent"); 
				
				dto = new BDto(id, bname, btitle, bcontent,
		                  bdate, bhit, bgroup, bstep, bindent);

			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
				if(rs != null) rs.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}	
		
		return dto;
		
	}
	
	public BDto reply_view(String bid){

		BDto dto = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		//쿼리문 작성시 찾으려는 글번호에 ?로 넘기고 아래서 preparedStatement로 set해준다.
		try {
			String query= "select * from mvc_board where bid=?";
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			
			//int형으로 setInt해야하는데, bid의 자료형은 String이므로 Integer로 바꿔준다. 
			preparedStatement.setInt(1, Integer.valueOf(bid));
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("bid");
				String bname = rs.getString("bname");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				Timestamp bdate = rs.getTimestamp("bdate");
				int bhit = rs.getInt("bhit");
				int bgroup = rs.getInt("bgroup");
				int bstep = rs.getInt("bstep");
				int bindent = rs.getInt("bindent"); 
				
				dto = new BDto(id, bname, btitle, bcontent,
		                  bdate, bhit, bgroup, bstep, bindent);

			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
				if(rs != null) rs.close();
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
				
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}	
		
		return dto;
		
	}
	
	public void replyShape(String group, String step){
	      System.out.println("replyShape() ..");
	            
	      Connection connection = null;
	      PreparedStatement preparedStatement = null;
	      
	      try {

	         String query = "update mvc_board set bstep = bstep + 1 "
	               + "where bgroup = ? and bstep > ?";
	         
	         connection = dataSource.getConnection();
	         preparedStatement = connection.prepareStatement(query);
	         
	         preparedStatement.setInt(1, Integer.parseInt(group));
	         preparedStatement.setInt(2, Integer.parseInt(step));
	      
	         int rn = preparedStatement.executeUpdate();
	         System.out.println("업데이트 갯수 :"+rn);
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }finally {
	         try {         
	         
	            if(preparedStatement != null) preparedStatement.close();
	            if(connection != null) connection.close();
	            
	         } catch (Exception e2) {
	         }
	      }
	   }
	
	
	public void reply(String bid, String bname, String btitle, String bcontent,
		 String group, String step, String bindent){
	      System.out.println("reply() .."); // 답글 위치 잡기, step 한칸씩 밀어내기
	            
	      replyShape(group, step);
	      
	      Connection connection = null;
	      PreparedStatement preparedStatement = null;
	      
	      try {

	    	  String query = "insert into mvc_board(bid,bname,btitle,bcontent, bgroup,bstep,bindent) values (mvc_board_seq.nextval, ?, ?, ?, ?, ?, ?)";
	         
	         connection = dataSource.getConnection();
	         preparedStatement = connection.prepareStatement(query);
	         
	         preparedStatement.setString(1, bname);
	         preparedStatement.setString(2, btitle);
	         preparedStatement.setString(3, bcontent);
	         preparedStatement.setInt(4, Integer.parseInt(group));
	         preparedStatement.setInt(5, Integer.parseInt(step) + 1);
	         preparedStatement.setInt(6, Integer.parseInt(bindent) + 1);
	      
	         int rn = preparedStatement.executeUpdate();
	         System.out.println("업데이트 갯수 :"+rn);
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      }finally {
	         try {         
	         
	            if(preparedStatement != null) preparedStatement.close();
	            if(connection != null) connection.close();
	            
	         } catch (Exception e2) {
	         }
	      }
	   }
	
	public void delete(String bgroup){

		System.out.println("delete().. ");
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		//쿼리문 작성시 찾으려는 글번호에 ?로 넘기고 아래서 preparedStatement로 set해준다.
		try {
			String query= "delete from mvc_board where bgroup=?";
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, Integer.valueOf(bgroup));
			int rn = preparedStatement.executeUpdate();
			System.out.println("삭제된 개수 : " + rn);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}	
	}
	
	public void hitUpper(String bgroup){

		System.out.println("hit up().. ");
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		//쿼리문 작성시 찾으려는 글번호에 ?로 넘기고 아래서 preparedStatement로 set해준다.
		try {
			String query= "update mvc_board set bhit = bhit + 1 where bgroup = ?";
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, Integer.valueOf(bgroup));
			int rn = preparedStatement.executeUpdate();
			System.out.println("업데이트 개수 : " + rn);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
				if(preparedStatement != null) preparedStatement.close();
				if(connection != null) connection.close();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}	
	}
}
