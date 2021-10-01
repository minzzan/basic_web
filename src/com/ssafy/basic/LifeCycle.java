package com.ssafy.basic;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/lifecycle")
public class LifeCycle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	// 초기화작업
	@Override
	public void init() throws ServletException {
		System.out.println("init() method call");
	}

	//반복작업
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doGet method call");
	}

	// 마무리 작업 (거의 해줄일 없음)
	@Override
	public void destroy() {
		System.out.println("destroy() method call");
	} 

}
