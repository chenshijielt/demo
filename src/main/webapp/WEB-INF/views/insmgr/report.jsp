<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Report</title>
</head>
<body>
<c:url var="exportEmpty" value="/insmgr/exportEmpty.html" />
<c:url var="exportUrl" value="/insmgr/export.html" />
<c:url var="readUrl" value="/insmgr/read.html" />

<h3><a href="${exportEmpty}">导出空表</a> &nbsp;<a href="${exportUrl}">导出数据</a></h3>
<br />
<form  id="readReportForm" action="${readUrl }" method="post" enctype="multipart/form-data"  >
    <label for="file">File</label>
    <input id="file" type="file" name="file" />
    <p><button type="submit">导入数据</button></p>
</form>

</body>
</html>