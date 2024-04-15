<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/css/main.css">
	<title>와이파이 정보 구하기</title>
</head>
<body>
	<h1>북마크 그룹 추가</h1>
	<a href="index.jsp">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmark-list.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br><br>
	
	<form action="bookmark-group-add-submit.jsp" method="post">
	    <table>
	        <tr>
	            <th>북마크 이름</th>
	            <td><input type="text" name="groupName"></td>
	        </tr>
	        <tr>
	            <th>순서</th>
	            <td><input type="text" name="seqNum"></td>
	        </tr>
	        <tr>
	            <td style="text-align: center;" colspan="2">
	                <input type="submit" value="추가">
	            </td>
	        </tr>
	    </table>
	</form>

</body>
</html>