package edu.global.ex.dto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BDeleteContentCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("삭제 프로세스 실행");
		
		String bid = request.getParameter("bid");
		
		BDao dao = new BDao();
		
		dao.delete(bid);
		
		System.out.println("삭제 완료");
		
	}

}
