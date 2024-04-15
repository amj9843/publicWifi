<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.HashMap" %>

<%@ page import="services.wifi_service" %>
<%@ page import="services.history_service" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/css/main.css">
	<script type="text/javascript">
	function getUserLocation() { //현재 사용자 좌표값 가져오기
		if ("geolocation" in navigator) {
			navigator.geolocation.getCurrentPosition(insertPosition, positionError);
		} else {
			alert("위치정보를 가져올 수 있는 환경이 아닙니다. 직접 입력해주세요.");
		}
	}
	
	function insertPosition(position) { //빈칸에 자동 입력
        document.forms.location.latitude.value = position.coords.latitude;
        document.forms.location.longitude.value = position.coords.longitude;
	}
	
	function positionError(err) { //오류 시 메세지 출력
		alert("위치정보를 가져오는 데 실패했습니다.");
	}
	</script>

	<title>와이파이 정보 구하기</title>
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	<a href="index.jsp">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmark-list.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br><br>
	
	<form name="location" action="index.jsp" method="post">
	LAT: <input type="text" name="latitude" size="10">, LNT: <input type="text" name="longitude" size="10">
	
	<button type="button" onclick="getUserLocation()">내 위치 가져오기</button>
	<button type="submit">근처 WIFI 정보 보기</button>
	</form>
	<br>
	<table>
		<thead>
			<tr>
				<th>거리(Km)</th>
				<th>관리번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명주소</th>
				<th>상세주소</th>
				<th>설치위치(층)</th>
				<th>설치유형</th>
				<th>설치기관</th>
				<th>서비스구분</th>
				<th>망종류</th>
				<th>설치년도</th>
				<th>실내외구분</th>
				<th>WIFI접속 환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업일자</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty param.latitude || empty param.longitude}">
					<tr>
						<td class="center" colspan="100%">위치 정보를 입력한 후에 조회해 주세요.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<%
						double latitude = Double.parseDouble(request.getParameter("latitude"));
						double longitude = Double.parseDouble(request.getParameter("longitude"));
						history_service.insertHistoryInfo(latitude, longitude);
						
						wifi_service wifiService = new wifi_service();
						ArrayList<Map<String, Object>> nearWifiList = wifiService.getNearWifiInfos(latitude, longitude);
						
						for (Map<String, Object> wifi: nearWifiList) {
		            %>
		                <tr>
		                    <td><%=wifi.get("distance")%></td>
		                    <td><%=wifi.get("managementId")%></td>
		                    <td><%=wifi.get("state")%></td>
		                    <td><a href="detail.jsp?mgrNo=<%=wifi.get("managementId") %>&distance=<%=wifi.get("distance") %>"><%=wifi.get("wifiName")%></a></td>
		                    <td><%=wifi.get("address")%></td>
		                    <td><%=wifi.get("addressDetail")%></td>
		                    <td><%=wifi.get("installFloor")%></td>
		                    <td><%=wifi.get("installType")%></td>
		                    <td><%=wifi.get("installAgency")%></td>
		                    <td><%=wifi.get("serviceType")%></td>
		                    <td><%=wifi.get("networkType")%></td>
		                    <td><%=wifi.get("installYear")%></td>
		                    <td><%=wifi.get("indoorOrOutdoor")%></td>
		                    <td><%=wifi.get("connectionEnvironment")%></td>
		                    <td><%=wifi.get("longitude")%></td>
		                    <td><%=wifi.get("latitude")%></td>
		                    <td><%=wifi.get("workedTime")%></td>
		                </tr>
		            <% } %>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
</body>
</html>