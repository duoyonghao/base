<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/inc/header.jsp" %>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7">
<meta charset="utf-8" />
<title>未登录错误</title>
<style>
    *{margin: 0;padding: 0;}
    input[type="button"], input[type="submit"], input[type="reset"] {-webkit-appearance: none;border: none;outline: none;}
    .tip_con{
        width: 403px;
        height: 300px;
        margin: 150px auto 0 auto;
    }
    .tip_pic{
        width: 403px;
        height: 185px;
        background: url("<%=contextPath %>/static/image/image/ts_bg_nologin.png") no-repeat;
    }
    .tip_con input{
        display: block;
        width: 140px;
        height: 40px;
        background: url("<%=contextPath %>/static/image/image/know_icon.png") no-repeat;
        cursor: pointer;
        margin: 60px auto;
    }
</style>
<script language="JavaScript"> 
//跳出 iframe 
if (window != top) {
    top.location.href = location.href;
}

function openFuc() {
    //获取页面打开凡是 isapp=1  chrome app的方式打开	 
    var isapp = localStorage.getItem("isapp");

    localStorage.setItem("isapp", '0'); // 要清空才行
    if (isapp == "1") {
        window.open('<%=contextPath %>/login.jsp?isapp=1', "_self");
    } else {
        window.open('<%=contextPath %>/login.jsp', "_blank");
    }
}
</script>
</head>
<body>
  <div class="tip_con">
       <div class="tip_pic"></div>
       <input type="button" onclick="openFuc();">
   </div>
</body>
</html>
