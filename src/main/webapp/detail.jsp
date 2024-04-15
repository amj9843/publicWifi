<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.HashMap" %>

<%@ page import="services.wifi_service" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/main.css">
<title>와이파이 정보 구하기</title>
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	<a href="index.jsp">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmark-list.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br><br>
	<%
		String distance = request.getParameter("distance");
		String managementId = request.getParameter("mgrNo");
		
		wifi_service wifiService = new wifi_service();
		Map<String, Object> wifiDetail = wifiService.getWifiInfo(managementId);
	%>
	
	<table>
		<tr>
			<th>거리(Km)</th>
			<td><%=distance%></td>
		</tr>
		<tr>
			<th>관리번호</th>
			<td><%=wifiDetail.get("managementId")%></td>
		</tr>
		<tr>
			<th>자치구</th>
			<td><%=wifiDetail.get("state")%></td>
		</tr>
		<tr>
			<th>와이파이명</th>
			<td><a href="detail.jsp?mgrNo=<%=wifiDetail.get("managementId") %>&distance=<%=distance %>"><%=wifiDetail.get("wifiName")%></a></td>
		</tr>
		<tr>
			<th>도로명주소</th>
			<td><%=wifiDetail.get("address")%></td>
		</tr>
		<tr>
			<th>상세주소</th>
			<td><%=wifiDetail.get("addressDetail")%></td>
		</tr>
		<tr>
			<th>설치위치(층)</th>
			<td><%=wifiDetail.get("installFloor")%></td>
		</tr>
		<tr>
			<th>설치유형</th>
			<td><%=wifiDetail.get("installType")%></td>
		</tr>
		<tr>
			<th>설치기관</th>
			<td><%=wifiDetail.get("installAgency")%></td>
		</tr>
		<tr>
			<th>서비스구분</th>
			<td><%=wifiDetail.get("serviceType")%></td>
		</tr>
		<tr>
			<th>망종류</th>
			<td><%=wifiDetail.get("networkType")%></td>
		</tr>
		<tr>
			<th>설치년도</th>
			<td><%=wifiDetail.get("installYear")%></td>
		</tr>
		<tr>
			<th>실내외구분</th>
			<td><%=wifiDetail.get("indoorOrOutdoor")%></td>
		</tr>
		<tr>
			<th>WIFI접속 환경</th>
			<td><%=wifiDetail.get("connectionEnvironment")%></td>
		</tr>
		<tr>
			<th>X좌표</th>
			<td><%=wifiDetail.get("longitude")%></td>
		</tr>
		<tr>
			<th>Y좌표</th>
			<td><%=wifiDetail.get("latitude")%></td>
		</tr>
		<tr>
			<th>작업일자</th>
			<td><%=wifiDetail.get("workedTime")%></td>
		</tr>
	</table>
</body>
</html>