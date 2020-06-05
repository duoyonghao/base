<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在线人员</title>
<style>
	.tbodyborder td{
		border:1px solid #ddd;
	}
	#tableAll{
		width:100%;
	}
	.TableHeader td{
		height:30px;
		background:#0E7CC9;
		color:#fff;
		border:1px solid #ddd;
	}
	.tbodyborder td{
		height:30px;
		border:1px solid #ddd;
	}
</style>
<script type="text/Javascript">	
var menuFlag = 0; // 用于控制展开、收缩
var deptStr = ""; // 存放部门ID字符串数组
function doInit() {
	$("#tableAll").html("");
    var onlineRef = 120;
    setInterval(reload, onlineRef * 1000);
    $("#menuFlag").val(0);
    var data = getDataFromServer('YZOnlineAct/getOnlineTree.act');
    if (data) {
    	deptStr = data.deptStr;
        $("#tableAll").append(data.retData);
    } else {
        alert(rtJson.rtMsrg);
    }
}

function deptExpand(deptId) {
    if ($("#tbody_" + deptId).css('display') == 'none') {
        $("#tbody_" + deptId).css('display', '');
    } else {
        $("#tbody_" + deptId).css('display', 'none');
    }
}

function listExpand() {
    var deptFunc = deptStr.split(',');
    for (var i = 0; i < deptFunc.length; i++) {
        if ($("#menuFlag").val() == 1) {
            $("#tbody_" + deptFunc[i]).css('display', '');
        } else {
            $("#tbody_" + deptFunc[i]).css('display', 'none');
        }
    }
    if ($("#menuFlag").val() == 1) {
        $("#expendText").html("全部收缩");
    } else {
        $("#expendText").html("全部展开");
    }
    $("#menuFlag").val(1 - $("#menuFlag").val());
}

function reload() {
    window.location.reload();
}
</script>
</head>
<body onload="doInit()" style="background:transparent; position: relative; top: -10px;">
<input type="hidden" name="menuFlag" id="menuFlag" value="">
<table id="tableAll" class="TableBlock" align="center" style="margin-top: 10px;">
</table>
</div>
</body>
</html>