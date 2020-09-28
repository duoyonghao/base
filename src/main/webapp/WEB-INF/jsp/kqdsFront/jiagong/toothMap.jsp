<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	//获取从左侧菜单点击带过来的菜单id
	String index = request.getParameter("index");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/hudh/lclj/flowdetail/css/base.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/toothMap.css" />
</head>
<body>
	<div class="continer">
		<div class="toothMap">
			<ul class="toothMapItem upTooth">
				<li>
					<input class="leftUpCbox" type="checkbox">
				</li>
				<li>
					<font class="toothText leftUpText">8</font>
				</li>
				<li>
					<font class="toothText leftUpText">7</font>
				</li>
				<li>
					<font class="toothText leftUpText">6</font>
				</li>
				<li>
					<font class="toothText leftUpText">5</font>
				</li>
				<li>
					<font class="toothText leftUpText">4</font>
				</li>
				<li>
					<font class="toothText leftUpText">3</font>
				</li>
				<li>
					<font class="toothText leftUpText">2</font>
				</li>
				<li>
					<font class="toothText leftUpText">1</font>
				</li>
				<li>
					<font class="toothText rightUpText">1</font>
				</li>
				<li>
					<font class="toothText rightUpText">2</font>
				</li>
				<li>
					<font class="toothText rightUpText">3</font>
				</li>
				<li>
					<font class="toothText rightUpText">4</font>
				</li>
				<li>
					<font class="toothText rightUpText">5</font>
				</li>
				<li>
					<font class="toothText rightUpText">6</font>
				</li>
				<li>
					<font class="toothText rightUpText">7</font>
				</li>
				<li>
					<font class="toothText rightUpText">8</font>
				</li>
				<li>
					<input class="rightUpCbox" type="checkbox">
				</li>
			</ul>
			<div class="verticalLine"></div>
			<span class="leIcon"> ＜	</span><span class="rgIcon"> ＞ </span>
			<font class="leftUpFont">左</font><font class="leftDownFont">左</font>
			<font class="rightUpFont">右</font><font class="rightDownFont">右</font>
			<ul class="toothMapItem downTooth">
				<li>
					<input class="leftButtomCbox" type="checkbox">
				</li>
				<li>
					<font class="toothText leftButtomText">8</font>
				</li>
				<li>
					<font class="toothText leftButtomText">7</font>
				</li>
				<li>
					<font class="toothText leftButtomText">6</font>
				</li>
				<li>
					<font class="toothText leftButtomText">5</font>
				</li>
				<li>
					<font class="toothText leftButtomText">4</font>
				</li>
				<li>
					<font class="toothText leftButtomText">3</font>
				</li>
				<li>
					<font class="toothText leftButtomText">2</font>
				</li>
				<li>
					<font class="toothText leftButtomText">1</font>
				</li>
				<li>
					<font class="toothText rightButtomText">1</font>
				</li>
				<li>
					<font class="toothText rightButtomText">2</font>
				</li>
				<li>
					<font class="toothText rightButtomText">3</font>
				</li>
				<li>
					<font class="toothText rightButtomText">4</font>
				</li>
				<li>
					<font class="toothText rightButtomText">5</font>
				</li>
				<li>
					<font class="toothText rightButtomText">6</font>
				</li>
				<li>
					<font class="toothText rightButtomText">7</font>
				</li>
				<li>
					<font class="toothText rightButtomText">8</font>
				</li>
				<li>
					<input  class="rightButtomCbox" type="checkbox">
				</li>
			</ul>
		</div>
		<!-- 按钮 -->
		<div class="btns">
			<button class="resetBtn" onclick="resetbtns();">复位</button>
			<button class="affirmBtn" onclick="affirmBtns();">确认</button>
		</div>
	</div>
	
	<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script> 
	<script type="text/javascript">
		var parentIndex = '<%=index%>';
		var parentId="";
		$(function(){
			// 牙位初始化
			/* 左上选中牙位 */
			$(window.parent.document).find(".toothMapIndex"+parentIndex).parents(".toothMap").find(".leftUpTooth").each(function(i,obj){
				if($(this).hasClass("current")){
					$(".upTooth").find(".leftUpText").eq(i).addClass("toothCurrent");
				}
			}); 
			/* 全选中 */
			if($(window.parent.document).find(".toothMapIndex"+parentIndex).parents(".toothMap").find(".leftUpTooth.current").length==8){
				$(".upTooth").find(".leftUpCbox").prop("checked","checked");
			} 
			/* 左下选中牙位 */
			$(window.parent.document).find(".toothMapIndex"+parentIndex).parents(".toothMap").find(".leftDownTooth").each(function(i,obj){
				if($(this).hasClass("current")){
					$(".downTooth").find(".leftButtomText").eq(i).addClass("toothCurrent");
				}
			}); 
			/* 全选中 */
			if($(window.parent.document).find(".toothMapIndex"+parentIndex).parents(".toothMap").find(".leftDownTooth.current").length==8){
				$(".downTooth").find(".leftButtomCbox").prop("checked","checked");
			} 
			/* 右上选中牙位 */
			$(window.parent.document).find(".toothMapIndex"+parentIndex).parents(".toothMap").find(".rightUpTooth").each(function(i,obj){
				if($(this).hasClass("current")){
					$(".upTooth").find(".rightUpText").eq(i).addClass("toothCurrent");
				}
			}); 
			/* 全选中 */
			if($(window.parent.document).find(".toothMapIndex"+parentIndex).parents(".toothMap").find(".rightUpTooth.current").length==8){
				$(".upTooth").find(".rightUpCbox").prop("checked","checked");
			} 
			/* 右下选中牙位 */
			$(window.parent.document).find(".toothMapIndex"+parentIndex).parents(".toothMap").find(".rightDownTooth").each(function(i,obj){
				if($(this).hasClass("current")){
					$(".downTooth").find(".rightButtomText").eq(i).addClass("toothCurrent");
				}
			}); 
			/* 全选中 */
			if($(window.parent.document).find(".toothMapIndex"+parentIndex).parents(".toothMap").find(".rightDownTooth.current").length==8){
				$(".downTooth").find(".rightButtomCbox").prop("checked","checked");
			}
			
		});
		
		//牙位图复位 
		function resetbtns(){
			$(".toothMap").find(".toothText").each(function(i,obj){
				$(this).removeClass("toothCurrent");
			});
			$(".toothMap").find("input[type='checkbox']").each(function(i,obj){
				$(this).removeAttr("checked");
			});
		};
		//牙位图确认
		function affirmBtns(){
			// 删除上一次选中的牙位
			$(window.parent.document).find(".toothMapIndex"+parentIndex).parents(".toothMap").find("li").each(function(i,obj){
				$(this).removeClass("current");
			});
			/* 左上选中牙位 */
			var leftUpToothArr=[];
			$(".upTooth").find(".leftUpText").each(function(i,obj){
				if($(this).hasClass("toothCurrent")){
					leftUpToothArr.push(i);
				}
			});
			// console.log(leftUpToothArr+"--------左上选中牙位");
			for (var i = 0; i < leftUpToothArr.length; i++) {
				$(window.parent.document).find(".toothMapIndex"+parentIndex).parents(".toothMap").find(".leftUpTooth").eq(leftUpToothArr[i]).addClass("current");
			}
			/* 右上选中牙位 */
			var rightUpToothArr=[];
			$(".upTooth").find(".rightUpText").each(function(i,obj){
				if($(this).hasClass("toothCurrent")){
					rightUpToothArr.push(i);
				}
			});
			//console.log(rightUpToothArr+"--------右上选中牙位");
			for (var j = 0; j < rightUpToothArr.length; j++) {
				$(window.parent.document).find(".toothMapIndex"+parentIndex).parents(".toothMap").find(".rightUpTooth").eq(rightUpToothArr[j]).addClass("current");
			}
			/* 左下选中牙位 */
			var leftDownToothArr=[];
			$(".downTooth").find(".leftButtomText").each(function(i,obj){
				if($(this).hasClass("toothCurrent")){
					leftDownToothArr.push(i);
				}
			});
			//console.log(leftDownToothArr+"--------左下选中牙位");
			for (var k = 0; k < leftDownToothArr.length; k++) {
				$(window.parent.document).find(".toothMapIndex"+parentIndex).parents(".toothMap").find(".leftDownTooth").eq(leftDownToothArr[k]).addClass("current");
			}
			/* 右下选中牙位 */
			var rightDownToothArr=[];
			$(".downTooth").find(".rightButtomText").each(function(i,obj){
				if($(this).hasClass("toothCurrent")){
					rightDownToothArr.push(i);
				}
			});
			//console.log(rightDownToothArr+"--------右下选中牙位");
			for (var g = 0; g < rightDownToothArr.length; g++) {
				$(window.parent.document).find(".toothMapIndex"+parentIndex).parents(".toothMap").find(".rightDownTooth").eq(rightDownToothArr[g]).addClass("current");
			}
            //console.log($(window.parent.document).find(".aa").text()+"-----------");
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
		};
		
		//牙位图选中
		$(".toothMap").find("li").each(function(i,obj){
			$(this).click(function(){
				$(this).find(".toothText").toggleClass("toothCurrent");
				//复选联动
				//左上
				if($(".toothMap").find(".leftUpText.toothCurrent").length==8){
					$(".toothMap").find(".leftUpCbox").prop("checked","checked");
				}else if($(".toothMap").find(".leftUpText.toothCurrent").length==0){
					$(".toothMap").find(".leftUpCbox").removeAttr("checked");
				}
				//左下
				if($(".toothMap").find(".leftButtomText.toothCurrent").length==8){
					$(".toothMap").find(".leftButtomCbox").prop("checked","checked");
				}else if($(".toothMap").find(".leftButtomText.toothCurrent").length==0){
					$(".toothMap").find(".leftButtomCbox").removeAttr("checked");
				}
				//右上
				if($(".toothMap").find(".rightUpText.toothCurrent").length==8){
					$(".toothMap").find(".rightUpCbox").prop("checked","checked");
				}else if($(".toothMap").find(".rightUpText.toothCurrent").length==0){
					$(".toothMap").find(".rightUpCbox").removeAttr("checked");
				}
				//右下
				if($(".toothMap").find(".rightButtomText.toothCurrent").length==8){
					$(".toothMap").find(".rightButtomCbox").prop("checked","checked");
				}else if($(".toothMap").find(".rightButtomText.toothCurrent").length==0){
					$(".toothMap").find(".rightButtomCbox").removeAttr("checked");
				}
			});
		});
		/* 左上 */
		$(".leftUpCbox").click(function(){
			if($(this).is(":checked")){
				$(".leftUpText").addClass("toothCurrent");
			}else{
				$(".leftUpText").removeClass("toothCurrent");
			}
		});
		/* 右上 */
		$(".rightUpCbox").click(function(){
			if($(this).is(":checked")){
				$(".rightUpText").addClass("toothCurrent");
			}else{
				$(".rightUpText").removeClass("toothCurrent");
			}
		});
		/* 左下 */
		$(".leftButtomCbox").click(function(){
			if($(this).is(":checked")){
				$(".leftButtomText").addClass("toothCurrent");
			}else{
				$(".leftButtomText").removeClass("toothCurrent");
			}
		});
		/* 右下 */
		$(".rightButtomCbox").click(function(){
			if($(this).is(":checked")){
				$(".rightButtomText").addClass("toothCurrent");
			}else{
				$(".rightButtomText").removeClass("toothCurrent");
			}
		});
		
	</script>
</body>
</html>