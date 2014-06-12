<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Search data</title>
</head>
<body>
<h1>Search Conditions</h1>
<form>
<table>
    <tr>
        <th>Api Type</th>
        <td><input type="text" name="apiType"/></td>
    </tr>
    <tr>
        <th>Target Date</th>
        <td><input type="text" name="targetDate"/></td>
    </tr>
    <tr>
        <td colspan="2"><input type="button" value="search"></td>
    </tr>
</table>
</form>

<a href="<%=basePath %>/XMLApi/report.html">Main Page</a>
</body>
</html>