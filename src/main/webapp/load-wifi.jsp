<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<%@ page import="services.wifi_service" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/main.css">

<title>와이파이 정보 구하기</title>
</head>
<body class="center">
	<c:set var="count">
		<c:catch>
			<%
				int cnt = services.wifi_service.insertWifiInfos();
				out.print(cnt);
			%>
		</c:catch>
	</c:set>
	<h1>${count}개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
	<a href="index.jsp">홈 으로 가기</a>
</body>
</html>