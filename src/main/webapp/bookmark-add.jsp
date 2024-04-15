<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
    
<%@ page import="services.bookmark_service" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>와이파이 정보 구하기</title>
	<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
<h1>북마크 추가</h1>
<%
    String managementId = request.getParameter("managementId");
    String bookmarkId = request.getParameter("bookmarkId");
    int id = Integer.parseInt(request.getParameter("id"));

    // 값이 none일 경우 이전 페이지로 이동
    if (bookmarkId == "") {
        response.sendRedirect(request.getHeader("Referer"));
        return;
    }
	
    bookmark_service bookmarkService = new bookmark_service();
	String message = bookmarkService.addBookmark(Integer.parseInt(bookmarkId), id);
%>
</body>
<script>
    <%
        String text = message;
    %>
    alert("<%= text %>");
    location.href = "bookmark-list.jsp";
</script>
</html>