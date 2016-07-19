<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	String path = request.getContextPath();  
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	pageContext.setAttribute("basePath",basePath); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ext登录</title>
<link rel="stylesheet" type="text/css"
	href="${basePath}/Plugin/ExtJS/resources/css/ext-all.css" />
<script type="text/javascript" src="${pageScope.basePath}/Plugin/ExtJS/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${pageScope.basePath}/Plugin/ExtJS/ext-all.js"></script>
<script type="text/javascript" src="${pageScope.basePath}/scripts/extLogin.js"></script>
<style type="text/css">
*{
	margin:0;
	padding:0;
}
</style>
</head>
<body>
</body>
</html>