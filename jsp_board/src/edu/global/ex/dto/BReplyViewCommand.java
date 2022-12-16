package edu.global.ex.dto;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BReplyViewCommand implements BCommand {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		
		System.out.println("BReplyViewCommand entry");  // 디버깅 문구
		
		String bid = request.getParameter("bid");
		
		BDao dao = new BDao();
		BDto dto = dao.reply_view(bid);
		
		request.setAttribute("reply_view", dto);
		System.out.println("BReplyViewCommand Over");
	}

}
