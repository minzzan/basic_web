<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*"%>
    
<%!
@Override
public void init() throws ServletException {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
} 
%>

<%
int cnt = 0;
int totalLen = 8;
int zeroLen = 0;
//1. data get

//2. logic
//	2-2. DB Connection (conn 생성)
//	2-3. SQL 실행 준비
//		SQL문
//		pstmt 생성 (치환변수 값 세팅)
//	2-4. SQL 실행
//		a. DML (I U D) : int cnt = pstmt.executeUpdate();
//		b. Query (S) : ResultSet rs = pstmt.executeQuery();
//						rs.next() [단독, if, while]
//						rs.getXXX("col_name");
//	2-5. DB close (역순)

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
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div align = "center">
	<h2>JSP로 만든 카운터</h2>
<%
for(int i = 0;i<zeroLen;i++) {
%>
<img src="/bw/img/0.png" width="80">
<%
} 
%>
<%
for(int i = 0;i<cntLen;i++){
%>
<img src="/bw/img/<%=cntStr.charAt(i) %>.png" width="80">
<%
} 
%>
</div>
</body>
</html>