<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<%@ page import="services.bookmark_service" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/css/main.css">
	<title>와이파이 정보 구하기</title>
</head>
<body>
	<h1>북마크 그룹 삭제</h1>
	<a href="index.jsp">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmark-list.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br><br>
	
	<%
	    String id = request.getParameter("id");
	
		bookmark_service bookmarkService = new bookmark_service();
	    int result = bookmarkService.deleteBookmarkGroup(Integer.parseInt(id));
	%>
</body>
<script>
    <%
        String text = (result > 0) ? "북마크 그룹을 삭제했습니다." : "북마크 그룹 삭제에 실패했습니다.";
    %>
    alert("<%= text %>");
    location.href = "bookmark-group.jsp";
</script>
</html>