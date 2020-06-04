<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp"%>
<%
	//获取从左侧菜单点击带过来的菜单id
	String index = request.getParameter("index");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="/base/static/css/hudh/lclj/flowdetail/css/base.css"/>
<link type="text/css" rel="stylesheet" href="/base/static/css/hudh/lclj/flowdetail/css/toothMap.css"/>
</head>
<body>
	<div class="continer">
		<div class="toothMap">
			<ul class="toothMapItem upTooth">
				<li>
					<input class="leftUpCbox" type="checkbox">
				</li>
				<li>
					<font class="toothText leftUpText">18</font>
				</li>
				<li>
					<font class="toothText leftUpText">17</font>
				</li>
				<li>
					<font class="toothText leftUpText">16</font>
				</li>
				<li>
					<font class="toothText leftUpText">15</font>
				</li>
				<li>
					<font class="toothText leftUpText">14</font>
				</li>
				<li>
					<font class="toothText leftUpText">13</font>
				</li>
				<li>
					<font class="toothText leftUpText">12</font>
				</li>
				<li>
					<font class="toothText leftUpText">11</font>
				</li>
				<li>
					<font class="toothText rightUpText">21</font>
				</li>
				<li>
					<font class="toothText rightUpText">22</font>
				</li>
				<li>
					<font class="toothText rightUpText">23</font>
				</li>
				<li>
					<font class="toothText rightUpText">24</font>
				</li>
				<li>
					<font class="toothText rightUpText">25</font>
				</li>
				<li>
					<font class="toothText rightUpText">26</font>
				</li>
				<li>
					<font class="toothText rightUpText">27</font>
				</li>
				<li>
					<font class="toothText rightUpText">28</font>
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
					<font class="toothText leftButtomText">48</font>
				</li>
				<li>
					<font class="toothText leftButtomText">47</font>
				</li>
				<li>
					<font class="toothText leftButtomText">46</font>
				</li>
				<li>
					<font class="toothText leftButtomText">45</font>
				</li>
				<li>
					<font class="toothText leftButtomText">44</font>
				</li>
				<li>
					<font class="toothText leftButtomText">43</font>
				</li>
				<li>
					<font class="toothText leftButtomText">42</font>
				</li>
				<li>
					<font class="toothText leftButtomText">41</font>
				</li>
				<li>
					<font class="toothText rightButtomText">31</font>
				</li>
				<li>
					<font class="toothText rightButtomText">32</font>
				</li>
				<li>
					<font class="toothText rightButtomText">33</font>
				</li>
				<li>
					<font class="toothText rightButtomText">34</font>
				</li>
				<li>
					<font class="toothText rightButtomText">35</font>
				</li>
				<li>
					<font class="toothText rightButtomText">36</font>
				</li>
				<li>
					<font class="toothText rightButtomText">37</font>
				</li>
				<li>
					<font class="toothText rightButtomText">38</font>
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
	
	<!-- <script type="text/javascript" src="/base/static/css/hudh/lclj/flowdetail/js/jquery-2.1.4.js"></script> -->
	<script type="text/javascript">
		var parentIndex = '0';
		var parentId="";
		$(function(){
			//牙位初始化
			if(parentIndex=="0"){
				parentId="zhongzhi";
			}else if(parentIndex=="1"){
				parentId="xiufu";
			}
			/* 左上选中牙位 */
			$(window.parent.document).find("#"+parentId).find(".leftUpTooth").each(function(i,obj){
				if($(this).hasClass("current")){
					$(".upTooth").find(".leftUpText").eq(i).addClass("toothCurrent");
				}
			});
			/* 全选中 */
			if($(window.parent.document).find("#"+parentId).find(".leftUpTooth.current").length==8){
				$(".upTooth").find(".leftUpCbox").prop("checked","checked");
			}
			/* 左下选中牙位 */
			$(window.parent.document).find("#"+parentId).find(".leftDownTooth").each(function(i,obj){
				if($(this).hasClass("current")){
					$(".downTooth").find(".leftButtomText").eq(i).addClass("toothCurrent");
				}
			});
			/* 全选中 */
			if($(window.parent.document).find("#"+parentId).find(".leftDownTooth.current").length==8){
				$(".downTooth").find(".leftButtomCbox").prop("checked","checked");
			}
			/* 右上选中牙位 */
			$(window.parent.document).find("#"+parentId).find(".rightUpTooth").each(function(i,obj){
				if($(this).hasClass("current")){
					$(".upTooth").find(".rightUpText").eq(i).addClass("toothCurrent");
				}
			});
			/* 全选中 */
			if($(window.parent.document).find("#"+parentId).find(".rightUpTooth.current").length==8){
				$(".upTooth").find(".rightUpCbox").prop("checked","checked");
			}
			/* 右下选中牙位 */
			$(window.parent.document).find("#"+parentId).find(".rightDownTooth").each(function(i,obj){
				if($(this).hasClass("current")){
					$(".downTooth").find(".rightButtomText").eq(i).addClass("toothCurrent");
				}
			});
			/* 全选中 */
			if($(window.parent.document).find("#"+parentId).find(".rightDownTooth.current").length==8){
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
			$(window.parent.document).find("#"+parentId).find("li").each(function(i,obj){
				$(this).removeClass("current");
			});
			/* 左上选中牙位 */
			var leftUpToothArr=[];
			$(".upTooth").find(".leftUpText").each(function(i,obj){
				if($(this).hasClass("toothCurrent")){
					leftUpToothArr.push(i);
				}
			});
			//console.log(leftUpToothArr+"--------左上选中牙位");
			for (var i = 0; i < leftUpToothArr.length; i++) {
				$(window.parent.document).find("#"+parentId).find(".leftUpTooth").eq(leftUpToothArr[i]).addClass("current");
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
				$(window.parent.document).find("#"+parentId).find(".rightUpTooth").eq(rightUpToothArr[j]).addClass("current");
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
				$(window.parent.document).find("#"+parentId).find(".leftDownTooth").eq(leftDownToothArr[k]).addClass("current");
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
				$(window.parent.document).find("#"+parentId).find(".rightDownTooth").eq(rightDownToothArr[g]).addClass("current");
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