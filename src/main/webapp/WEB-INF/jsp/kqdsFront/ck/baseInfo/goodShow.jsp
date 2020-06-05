<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	// 获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<style type="text/css">
	#goodInfo{
		padding: 10px 0px 0px 15px;
	}
	.infoDetail{
		overflow: hidden;
    	padding: 3px 0px;
    	/* border:1px solid yellow; */
    	margin:0px;
	}
	.infoDetail>li{
		/* border:1px solid blue; */
		width:100%;
		overflow: hidden;
		margin-bottom: 15px;
	}
	.infoDetail>li>.item{
		width:45%;
		float:left;
		/* border:1px solid red; */
	}
	.infoDetail>li>.item .itemName{
		font-size: 13px;
	}
	.infoDetail>li>.item .itemValue{
		padding-left: 5px;
		width: 200px;
		height: 28px;
    	line-height: 28px;
	    box-sizing: border-box;
	    color: #333;
	    border: solid 1px #e5e5e5;
    	border-radius: 3px;
	}
</style>

<body>
	<div id="goodInfo">
		<ul class="infoDetail">
			<li>
				<div class="item">
					<span class="itemName">商品编号：</span>
					<input class="itemValue" id="goodscode"/>
				</div>
				<div class="item">
					<span class="itemName">商品名称：</span>
					<input class="itemValue" id="goodsname" style="width:225px;"/>
				</div>
			</li>
			<li>
				<div class="item">
					<span class="itemName">一级类别：</span>
					<input class="itemValue" id="firsttype"/>
				</div>
				<div class="item">
					<span class="itemName">二级类别：</span>
					<input class="itemValue" id="goodstypename" style="width:225px;"/>
				</div>
			</li>
			<li>
				<div class="item">
					<span class="itemName">商品规格：</span>
					<input class="itemValue" id="goodsnorms"/>
				</div>
				<div class="item">
					<span class="itemName">单&emsp;&emsp;位：</span>
					<input class="itemValue" id="goodsunit" style="width:225px;"/>
				</div>
			</li>
			<li>
				<div class="item">
					<span class="itemName">单&emsp;&emsp;价：</span>
					<input class="itemValue" id="goodsprice"/>
				</div>
				<div class="item">
					<span class="itemName">当前库存金额：</span>
					<input class="itemValue" id="kcmoney"/>
				</div>
			</li>
			<li>
				<div class="item">
					<span class="itemName">当前库存：</span>
					<input class="itemValue" id="nums"/>
				</div>
			</li>
		</ul>
	</div>

</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript">
	var onclickrowOobj = window.parent.onclickrowOobj; //商品详情对象
	$(function(){
		//console.log(JSON.stringify(onclickrowOobj)+"-------选中的商品对象");
		initGoods(onclickrowOobj);
	});
	
	/* 初始化商品资料  2020/03/28 lutian*/
	function initGoods(clickObj){
		if(clickObj){
			$(".infoDetail").find(".item").find(".itemValue").each(function(i,obj){
				for(var item in clickObj){
					if(obj.id==item){
						$("#"+obj.id).val(clickObj[item]);
					} 
				}
			});
		}
	}
</script>
</html>