package edu.global.ex.dto;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 모든 url 들은 webServlet에서 받아내겠다는 뜻
@WebServlet("*.do")
public class BController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BController() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet메소드를 탈 시에 console에 기록하기 위해 넣었다.(디버깅위해)
		System.out.println("doGet() ..");
		actionDo(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doPost메소드를 탈 시에 console에 기록하기 위해 넣었다.(디버깅위해)
		System.out.println("doPost() ..");
		actionDo(request,response);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {
		System.out.println("actionDo() ..");
		
		request.setCharacterEncoding("UTF-8");
		
		String viewPage = null;
		BCommand command = null;
		
		//위의 세 줄은 http://localhost:8282/jsp_mvc_board/list.do 에서 list.do를 꺼내기 위한 코드들.
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String com = uri.substring(conPath.length());
		
		System.out.println("경로 확인:" + uri + ":" + conPath + ":" + com );
	
		if(com.equals("/List.do")) {
			command = new BListCommand();
			command.execute(request, response);
			viewPage ="List.jsp";
		}
		else if(com.equals("/write_view.do")) {
			System.out.println("/write_view.do ..");
			viewPage ="write_view.jsp";
		}else if(com.equals("/write.do")) {
			
			command = new BWriteCommand();
			command.execute(request, response);
			viewPage = "List.do";
		}else if(com.equals("/content_view.do")) {
			
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "content_view.jsp";
		}else if(com.equals("/reply_view.do")) {
			System.out.println("reply_view.do ..");
			
			command = new BReplyViewCommand();
			command.execute(request, response);
			viewPage = "reply_view.jsp";
		}else if(com.equals("/reply.do")) {
			System.out.println("/reply.do ..");
			
			command = new BReplyCommand();
			command.execute(request, response);
			viewPage = "List.do";
		}else if(com.equals("/delete.do")) {
			System.out.println("/delete.do ..");
			
			command = new BDeleteContentCommand();
			command.execute(request, response);
			viewPage = "List.do";
		}
		
		
		/*
	        클라이언트에게 list 는 list.jsp로 forwarding을 시키겠다는 의미이다.
	        forwarding은 BListCommand클래스에서 메모리에 올린 request, response 객체 정보를 
	        list.jsp에서 사용가능하다는 의미다. forwarding될때까지 정보가 살아있기 때문이다. 
	        고로 list.jsp에서 forEach문을 사용하여 데이터를 꺼낼 수 있다.
       */
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		
	}

}
