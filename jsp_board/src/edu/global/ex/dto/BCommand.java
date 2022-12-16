package edu.global.ex.dto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BCommand {
	//인터페이스이므로 메소드선언만 가능하다. 구현은 자손이 한다.
	void execute(HttpServletRequest request, HttpServletResponse response);
}
