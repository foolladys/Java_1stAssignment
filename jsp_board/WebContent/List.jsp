<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table width="500" border="1" border-style="solid" >
	<tr>
		<td>번호</td>
		<td>이름</td>
		<td>제목</td>
		<td>날짜</td>
		<td>히트</td>
	</tr>
<!-- 
	
		--이때 List는 BListCommand에서 메모리에 올린 list를 뜻함.
		포워딩시 살아있으므로 forEach문을 이용해 데이터를 꺼낼 수 있다.

 -->
 <c:forEach var="board" items="${list}">
 	<tr>
 		<td>${board.bid}</td>
 		<td>${board.bname}</td>
 		<td>
 		<c:forEach begin="1" end="${board.bindent}">[Re]</c:forEach>
 		<a href="content_view.do?bid=${board.bid }">${board.btitle}</a>
 		</td>
 		<td>${board.bdate}</td>
 		<td>${board.bhit}</td>
 	</tr>
 </c:forEach>
 <tr>
 	<td colspan="5"><a href="write_view.do">글 작성</a></td>
 </tr>
 
</table>
</body>
</html>