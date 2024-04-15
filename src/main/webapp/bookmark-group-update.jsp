<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<%@page import="java.util.Map" %>
<%@page import="java.util.HashMap" %>

<%@ page import="services.bookmark_service" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="css/main.css">
	<title>와이파이 정보 구하기</title>
</head>
<body>
	<h1>북마크 그룹 수정</h1>
	<a href="index.jsp">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmark-list.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br><br>
	
	<%
	    String id = request.getParameter("id");
	
		bookmark_service bookmarkService = new bookmark_service();
		Map<String, Object> bookmarkGroup = bookmarkService.getBookmarkGroup(Integer.parseInt(id));
	%>
	
	<form method="post" action="bookmark-group-update-submit.jsp" >
	    <table>
	        <tr>
	            <th>북마크 이름</th>
	            <td><input type="text" name="groupName" value="<%=bookmarkGroup.get("groupName")%>"></td>
	        </tr>
	        <tr>
	            <th>순서</th>
	            <td><input type="text" name="seqNum" value="<%=bookmarkGroup.get("seqNum")%>"></td>
	        </tr>
	        <tr>
	            <td colspan="2">
	                <a href="bookmark-group.jsp">돌아가기</a>
	                <input type="submit" name="update" value="수정">
	                <input type="hidden" name="id" value="<%=bookmarkGroup.get("id")%>">
	            </td>
	        </tr>
	    </table>
	</form>
</body>
</html>