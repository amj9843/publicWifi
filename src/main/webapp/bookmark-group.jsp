<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.HashMap" %>

<%@ page import="services.bookmark_service" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/main.css">
<title>와이파이 정보 구하기</title>
</head>
<body>
	<h1>북마크 그룹</h1>
	<a href="index.jsp">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmark-list.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br><br>
	
	<button onclick="location.href = 'bookmark-group-add.jsp'">북마크 그룹 이름 추가</button>
	<br>
	
	<table>
        <thead>
            <tr>
                <th>ID</th>
                <th>북마크 이름</th>
                <th>순서</th>
                <th>등록일자</th>
                <th>수정일자</th>
                <th>비고</th>
            </tr>
        </thead>
        <tbody>
        	<%
					bookmark_service bookmarkService = new bookmark_service();
					ArrayList<Map<String, Object>> bookmarkGroups = bookmarkService.getBookmarkGroups();
					
					if (bookmarkGroups.isEmpty()) {
			%>
                <tr>
                    <td colspan="100%" class="center">생성된 북마크 그룹이 없습니다.</td>
                </tr>
            <% } else {
            for (Map<String, Object> bookmarkGroup: bookmarkGroups) {
            %>
                <tr>
                    <td><%=bookmarkGroup.get("id")%></td>
                    <td><%=bookmarkGroup.get("groupName")%></td>
                    <td><%=bookmarkGroup.get("seqNum")%></td>
                    <td><%=bookmarkGroup.get("createTime")%></td>
                    <td><%=bookmarkGroup.get("modifyTime")%></td>
                    <td class="center">
						<a href="bookmark-group-update.jsp?id=<%=bookmarkGroup.get("id")%>">수정</a>
                    	<a href="bookmark-group-delete.jsp?id=<%=bookmarkGroup.get("id")%>">삭제</a>
					</td>
                </tr>
            <% }} %>
        </tbody>
    </table>
</body>
</html>