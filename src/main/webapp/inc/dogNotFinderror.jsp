<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ include file="/inc/header.jsp" %>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7">
<meta charset="utf-8" />
<title>未插入加密狗！</title>
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
        background: url("<%=contextPath %>/static/image/image/ts_bg_noDog.png") no-repeat;
    }
</style>
<script language="JavaScript"> 
//跳出 iframe 
	if (window != top){
	    top.location.href = location.href; 
	}
</script>
</head>
<body>

  <div class="tip_con">
  <h1 style="color:red;width:600px;">未检测到加密狗，请与管理员联系！</h1>
       <div class="tip_pic"></div>
       
       
   </div>
</body>
</html>
