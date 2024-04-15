<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.HashMap" %>
<%@page import="repositories.history_repository" %>
<%@page import="services.history_service" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="/css/main.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script type="text/javascript">
	function deleteHistory(id) {
		if (confirm("정말 " + id + "번 데이터를 삭제하시겠습니까?")) {
            $.ajax({
                url: "http://localhost:8080/history.jsp",
                data: {id : id},
                success: function () {
                    document.location.reload();
                },
                
                error: function (request, status, error) {
                    alert("ERROR: " + error);
                }
            })
        }
	}
	</script>
<title>와이파이 정보 구하기</title>
</head>
<body>
	<h1>위치 히스토리 목록</h1>
	<a href="index.jsp">홈</a> | <a href="history.jsp">위치 히스토리 목록</a> | <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> | <a href="bookmark-list.jsp">북마크 보기</a> | <a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br><br>
	
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>조회일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<%
					history_service historyService = new history_service();
					ArrayList<Map<String, Object>> historyList = historyService.getHistoryInfos();
					if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
						historyService.deleteHistoryInfo(Integer.parseInt(request.getParameter("id")));
					}

					if (historyList.isEmpty()) {
			%>
                <tr>
                    <td colspan="100%" class="center">위치 정보를 조회한 기록이 없습니다.</td>
                </tr>
            <% } else {
            for (Map<String, Object> history: historyList) {
            %>
                <tr>
                    <td><%=history.get("id")%></td>
                    <td><%=history.get("latitude")%></td>
                    <td><%=history.get("longitude")%></td>
                    <td><%=history.get("execTime")%></td>
                    <td class="center"><button type="button" onclick="deleteHistory(<%=history.get("id")%>)">삭제</button></td>
                </tr>
            <% }} %>
		</tbody>
	</table>
</body>
</html>