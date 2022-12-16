package edu.global.ex.dto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BContentCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("BContentCommand ... ");
		
		String bid = request.getParameter("bid");
		
		//request에 담긴 데이터를 B컨트롤러에서 forwarding으로 넘김으로써
		//dto객체를 content_view.jsp에서 사용할수 있게 한다.
		BDao dao = new BDao();
		//테이블에 있는 모든 데이터를 끌고온다는 뜻
		BDto dto = dao.contentView(bid);
		request.setAttribute("content_view", dto);
	}


	
}
