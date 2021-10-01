package com.ssafy.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/counter")
public class CounterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 당신은 XXXX번째 방문자입니다.
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int cnt = 0;
		int totalLen = 8;
		int zeroLen = 0;
//		1. data get

//		2. logic
//			2-2. DB Connection (conn 생성)
//			2-3. SQL 실행 준비
//				SQL문
//				pstmt 생성 (치환변수 값 세팅)
//			2-4. SQL 실행
//				a. DML (I U D) : int cnt = pstmt.executeUpdate();
//				b. Query (S) : ResultSet rs = pstmt.executeQuery();
//								rs.next() [단독, if, while]
//								rs.getXXX("col_name");
//			2-5. DB close (역순)

		
		
		// DB Logic
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/ssafyweb?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8",
					"ssafy", "ssafy");
			String sql = "select cnt from counter";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			cnt = rs.getInt(1);
			pstmt.close();

			sql = "update counter set cnt = cnt + 1";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Business Logic
		String cntStr = cnt + ""; // "1 3 8"
		int cntLen = cntStr.length(); // 3
		zeroLen = totalLen - cntLen;

//		3. response page
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("	<body>");
		out.println("		<div align=\"center\">");
//		out.println("당신은 ");
		for(int i = 0;i<zeroLen;i++) {
		out.print("<img src=\"/bw/img/0.png\" width=\"100\">");
		}
		for(int i = 0;i<cntLen;i++) {
			out.print("<img src=\"/bw/img/"+cntStr.charAt(i)+".png\" width=\"100\">");
		}
//		out.println(cnt);
//		out.println("번째 방문자입니다.");
		out.println("		</div>");
		out.println("	</body>");
		out.println("</html>");
//		
	}
}
