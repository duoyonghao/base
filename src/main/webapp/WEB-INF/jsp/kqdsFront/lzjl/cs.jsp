<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/kqds";
  }
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流转记录</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/lzjl/history.css">
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/lzjl/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<style>
	body:before{
		content:"";
		display:table;
	}
</style>
</head>
<body>
<div id="history">
	<div class="title">
		<!-- <h2>流转记录</h2> -->
		<div id="circle">
			<div class="cmsk"></div>
			<div class="circlecontent">
				<div thisyear="2018" class="timeblock">
					<span class="numf"></span>
					<span class="nums"></span>
					<span class="numt"></span>
					<span class="numfo"></span>
					<div class="clear"></div>
				</div>
				<div class="timeyear">YEAR</div>
			</div>
			<a href="#" class="clock"></a>
		</div>
	</div>
	
	<div id="content">
		<ul class="list" id="contentul">
		</ul>
	</div>
	<div id="arrow">
		<ul>
			<li class="arrowup"></li>
			<li class="arrowdown"></li>
		</ul>
	</div>
</div>

</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var mryear = "";
$(function() {
    var onclickrowOobj = window.parent.onclickrowOobj;
    if (onclickrowOobj != null && onclickrowOobj != "" && onclickrowOobj != "null" && onclickrowOobj != "undefined") {
        getlzjl(onclickrowOobj);
    }
    $(".timeblock .numf").css("background-position","0px "+(0-Number(mryear.substring(0,1))*24)+"px");
    $(".timeblock .nums").css("background-position","0px "+(0-Number(mryear.substring(1,2))*24)+"px");
    $(".timeblock .numt").css("background-position","0px "+(0-Number(mryear.substring(2,3))*24)+"px");
    $(".timeblock .numfo").css("background-position","0px "+(0-Number(mryear.substring(3,4))*24)+"px");
    /* setHeight(); */
});
function getlzjl(onclickrowOobj) {
    var pageurl1 = '<%=contextPath%>/KQDS_BCJLAct/selectPageLz.act';
    var url = pageurl1 + '?usercode='+onclickrowOobj.usercode;
    $.axse(url, null,
    function(data) {
        var content = "";
        for (var i = 0; i < data.length; i++) {
        	if(i==0){
        		mryear = data[i].createtime.substring(0,4);
        	}
       		content += ' <li>'+
       		     '<div class="liwrap">'+
       			 '<div class="lileft">'+
       			 '<div class="date">'+
       			 '<span class="year">' + data[i].createtime.substring(0,4) + '</span>'+
       			 '<span class="yearmd">' + data[i].createtime.substring(4,10) + '</span>'+
       			 '</div>'+
       			 '<div class="">'+
       			 '<span class="md">' + data[i].createtime.substring(10) + '</span>'+
       			 '</div>'+
       			 '</div>'+
				
       			 '<div class="point"><b></b></div>'+
				
       			 '<div class="liright">'+
       			 '<div class="histt">'+
       			 '<span style="font-size:14px;">'+ data[i].bcmc + '</span><br>'+
       			 '</div>'+
       			 '<div class="hisct">'+
       			 '<span>操作人：<span style="font-size:14px;">' + data[i].createusername + '</span></span>' +
       			 '</div>'+
       			 '</div>'+
       			 '</div>'+
       			 '</li>';
        }
        $("#contentul").append(content);
    },
    function() {
        layer.alert('查询出错！' );
    });
}
</script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/lzjl/jquery.mousewheel.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/lzjl/jquery.easing.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/lzjl/history.js"></script>
</html>