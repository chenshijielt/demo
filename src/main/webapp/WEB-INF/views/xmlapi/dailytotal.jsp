<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Daily Total</title>
</head>
<body>
<div>
    <h2>Daily Total</h2>
<table border="1">
    <tr>
        <th>api_call_count</th>
        <th>total count</th>
        <th>max request count</th>
        <th>target date</th>
    </tr>
    <c:forEach items="${dailytotal}" var="row">
        <tr>
            <td>${row.api_call_count}</td>
            <td>${row.total_count}</td>
            <td>${row.max_request_count}</td>
            <td>${row.target_date}</td>
        </tr>
    </c:forEach>
</table>
    <img src="chart/dailyTotal/api_call_count" alt=""/>
</div>
<a href="<%=basePath %>/XMLApi/report.html">Main Page</a>
</body>
</html>