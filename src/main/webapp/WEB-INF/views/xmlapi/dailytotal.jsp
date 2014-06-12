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
    <style type="text/css">

        .tbl {
            display: table;
            border-collapse: collapse;
            border-spacing: 2px;
            width: 100%;
            table-layout: fixed;
            font-family: Arial;
            font-size: 11px;
        }
        .tbl .num {
            text-align: right;
        }

        .tbl tfoot td {
            border: 1px solid #A1A1A1;
            background: #ddd;
            font-weight: bold;
        }
        .tbl th {
            border: 1px solid #a1a1a1;
            background: #DDDDDD;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
            height: 25px;
            padding: 0 5px;
        }

        .tbl td {
            border: 1px solid #A1A1A1;
            background: #F5F5F5;
            padding: 0 5px;
            height: 25px;
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
        }
        .tbl .date {
            text-align: center;
        }
    </style>
</head>
<body>
<div>
    <h2>Daily Total</h2>
<table  class="tbl">
    <tr>
        <th>Target Date</th>
        <th>Api Call Count</th>
        <th>Total Count</th>
        <th>Max Request Count</th>
    </tr>
    <c:forEach items="${dailytotal}" var="row">
        <tr>
            <td class="date">${row.target_date.year + 1900}-${row.target_date.month + 1}-${row.target_date.date}</td>
            <td class="num">${row.api_call_count}</td>
            <td class="num">${row.total_count}</td>
            <td class="num">${row.max_request_count}</td>
        </tr>
    </c:forEach>
</table>

    <h2>Daily Call Change</h2>
    <table  class="tbl">
        <tr>
            <th>Target Date</th>
            <th>Api Type</th>
            <th>Request Count</th>
            <th>Distinct Site</th>
            <th>Distinct User</th>
        </tr>
        <c:forEach items="${dailyaddcall}" var="row">
            <tr>
                <td class="date">${row.targetDate.year + 1900}-${row.targetDate.month + 1}-${row.targetDate.date}</td>
                <td>${row.apiType}</td>
                <td class="num">${row.requestCount}</td>
                <td class="num">${row.distinctSite}</td>
                <td class="num">${row.distinctUser}</td>
            </tr>
        </c:forEach>
    </table>

    <img src="chart/dailyTotal/api_call_count" alt=""/>
    <hr>
    <img src="chart/dailyTotal/api_call_total" alt=""/>
</div>
</body>
</html>