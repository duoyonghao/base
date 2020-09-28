<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<!DOCTYPE  html  PUBLIC  "-//W3C//DTD  HTML  4.01  Transitional//EN"  "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
//获取页面打开凡是 isapp=1  chrome app的方式打开	 
var isapp = localStorage.getItem("isapp");
if (isapp == "1") {
    //禁用右键菜单
    document.oncontextmenu = DisableRightClick;
    function DisableRightClick() {
        return false;
    }
}
</script>
</head>
<body>
  <div align="center" style="margin:60px 0px;color:#000000;filter:dropshadow(color=#FFFFFF,offx=1,offy=1,positive=1); WIDTH: 100%; FONT-SIZE: 30pt;"><i>欢迎使用UNIC管理系统</i></div>
</body>