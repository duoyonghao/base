<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>模拟划价</title><!-- 从右侧个人中心  中间 图标 进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiedai/add_cost.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script	type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/costOrder/add_cost4test.js"></script>
	
    
<style type="text/css">
a:hover{text-decoration:none;}
	li{cursor: pointer;}
	.aBtn1{display: inline-block;width: 115px;height: 30px; margin-right: 10px;line-height: 30px; border: 1px solid #00A6C0 ; color: #00A6C0 ; border-radius: 15px;text-align: center; }
	.aBtn1:hover{color: #fff;background: #117cca;text-decoration: none;}
	.aBtn1.gray{border-color: #a5a195;color: #a5a195;}
	input[type=number] {
		padding: 0 10px;
		width: 212px;
		height: 28px;
		line-height: 28px;
		border: solid 1px #e5e5e5;
		border-radius: 3px;
		-webkit-transition: all .3s;
		transition: all .3s;
	}
	#table td span.time{display:inline-block;width: 250px;white-space: nowrap;overflow: hidden;text-overflow:ellipsis; }
	#table td span.name{display:inline-block;width: 80px;white-space: nowrap;overflow: hidden;text-overflow:ellipsis; }
	
	#table td,#table th,#tableHeader th{
		border:1px solid #ddd;
		text-align:center;
		white-space: nowrap;
	    overflow: hidden;
	    text-overflow: ellipsis;
	}
	#tableHeader th{
		height:30px;
	}
	input[type="text"]{
		text-align:center;
	}
	#table input,#table select{
		height:22px;
		font-size:12px;
		padding:0 5px;
	}
	
	
</style>
</head>
<body>
<div id="container" >
  <div id = "fytjDiv"><!-- ###费用添加start -->	
	<div class="costWrap">
		<div class="costHd"> <!-- Header -->
			<label><input type="radio" name="treetype" id="sfbz" checked="checked">收费标准</label>
			<label><input type="radio" name="treetype" id="sftc">收费套餐</label>
			<ul>
				<li>明细</li>
			</ul>
		</div>
		<div class="costBd" style="position:relative;"><!-- Body -->
			<div class="ztreeWrap">
				 <div class="searchWrap">
		            <!-- <input type="text" placeholder="搜索" name="search" id="search" class="searchInput" style="width: 192px;"> -->
		            <input type="text" placeholder="搜索" name="search" id="search" class="searchInput">
 					<a href="javascript:void(0);" id="infosearch" class="hc-icon icon16 search-icon searchBtn" onclick="zTreeInit()"></a>
 				</div>
				<div class="ztree" style="overflow-y:auto;">
					<ul id="treeDemo" class="ztree">
					</ul>
				</div>
			</div>
			<div class="tableBody" style="position:relative;/* overflow-y:auto; */border:1px solid #ddd;border-radius:8px;">
				<div id="tableHeader" style="z-index:1;margin-right:15px;overflow:hidden;height:31px;position:relative;">
					<table id="fixedHeadertable" class="tableHeader" style="width:100%;">
						<thead style="background: #00A6C0 ;color:white;">
							<tr>
								<th style="width:30px;">操作</th>
								<th style="width:70px;">开发</th>
								<th style="width:70px;">特殊项目</th>
								<th style="width:160px;">治疗项目</th>
								<th style="width:30px;">单位</th>
								<th style="width:100px;">单价</th>
								<th style="width:70px;">数量</th>
								<th style="width:70px;">折扣%</th>
								<th style="width:100px;">小计</th>
								<th style="width:100px;">欠费金额</th>
								<th style="width:100px;">缴费金额</th>
								<th style="width:100px;">免除</th>
							</tr>
						</thead>
					</table>
				</div>
				<div id="tableContent" style="margin-top:-31px;overflow-x:auto;overflow-y:scroll;">
					<table id="table" style="width:100%;" class="table-striped table-condensed table-bordered" data-height="250" style="width: 100%;text-align: center;">
							<thead style="background: #00A6C0 ;color:white;">
								<tr>
									<th style="width:30px;height:30px;">操作</th>
									<th style="width:70px;">开发</th>
									<th style="width:70px;">特殊项目</th>
									<th style="width:160px;">治疗项目</th>
									<th style="width:30px;">单位</th>
									<th style="width:100px;">单价</th>
									<th style="width:70px;">数量</th>
									<th style="width:70px;">折扣%</th>
									<th style="width:100px;">小计</th>
									<th style="width:100px;">欠费金额</th>
									<th style="width:100px;">缴费金额</th>
									<th style="width:100px;">免除</th>
								</tr>
							</thead>
							<tbody id="costitemlistBody" style="background-color: #F0FFFF;text-align: center;"></tbody>
					</table>
				</div>
			</div>
			<div class="formBox money" style="clear:both;">
				<section>
					<ul class="conditions">
						<li>
							<span>折扣额</span>
							<input type="text" id="discountmoney" readonly="readonly">
						</li>
						<li>
							<span>合计</span>
							<input type="text" id="totalcost" readonly="readonly">
						</li>
						<li>
							<span>免除</span>
							<input type="text" id="voidmoney" readonly="readonly">
						</li>
						<li>
							<span>应收</span>
							<input type="text" id="shouldmoney" readonly="readonly">
						</li>
						<li>
							<span>欠费</span>
							<input type="text" id="arrearmoney" readonly="readonly">
						</li>
						<li>
							<span>缴费</span>
							<input type="text" id="actualmoney" readonly="readonly">
						</li> 
					</ul>
				</section>
			</div>
		</div>
	</div>
	
	
	<div class="footBar">
		<a href="javascript:void(0);" class="kqdsCommonBtn"  id="tydz">统一打折</a>
		<a href="javascript:void(0);" class="kqdsCommonBtn" id="qxhj">取消</a>
	</div>
  </div> <!-- ###费用添加end -->
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
// 判断是否为 前台进行打折操作，如果是，则值为1
var perseqId = "<%=person.getSeqId()%>";
var maxDiscount = "<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag0_maxDiscount, request) %>";
var maxVoidmoney = "<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag19_maxVoidmoney, request) %>"; // 最大免除金额

var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引

var tdindex = 1000; //表格里各类标签的id 动态添加时使用 数字随便从几开始

$(function() {
    // 隐藏 项目介绍和优惠信息
    $("#xmjsDiv").hide();
    $("#yhxxDiv").hide();
    
    zTreeInit();

    $('li').on('click',
    function() {
        $(this).addClass('current'); // 设置被点击元素为红色
        $(this).siblings('li').removeClass('current'); // 去除所有同胞元素的红色样式
        //切换对应栏目下的表格
    });
    
    //数据表table滚动时  固定的表头也要跟着滚动
    $("#tableContent").scroll(function(){
    	/* console.log("aaa");
    	console.log("滚动："+$("#tableHeader").scrollLeft()); */
    	$("#tableHeader").scrollLeft($(this).scrollLeft());
    })
    setHeight();
    $(window).resize(function() {
        setHeight();
    });
    

});


$("#sfbz").click(function() { // 收费项目和收费套餐，共用一个div
	$('#treeDemo li').remove();
	$("#search").show();//展示搜索框
	$("#infosearch").show();//展示放大镜图片
	$(".ztree").outerHeight($(".ztreeWrap").outerHeight()-24);//树的高度设置
    zTreeInit();
});
$("#sftc").click(function() {
	$('#treeDemo li').remove();
	$("#search").hide();	//隐藏搜索框
	$("#infosearch").hide();//隐藏放大镜图片
	$(".ztree").outerHeight($(".ztreeWrap").outerHeight());//树的高度设置
    zTreeInitTc();
});
$('#qxhj').on('click', // 取消划价
function() {
    parent.layer.close(frameindex); //再执行关闭 */
});
//统一打折
$('#tydz').on('click',
function() {
    layer.prompt({
        title: '输入统一折扣，并确认',
        formType: 0
    },
    function(discount, index) {
        layer.close(index);
        //循环获取表格中项目
        $('#table').find('tbody').each(function() {
            $(this).find('tr').each(function() {
                $(this).find('td').each(function() {
                    if ($(this).index() == 8) {
                        if ($(this).find("input").attr("readonly") != "readonly") { /** 欠费等特殊项目，不进行打折 **/
                            //触发change事件
                            $(this).find("input").val(discount);
                            $(this).find("input").change();
                        }
                    }
                });
            });
        });
    });
});
function expandTreeNode() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");

    var nodes = $.fn.zTree.getZTreeObj("treeDemo").getNodes();
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].open) {
            zTree.expandNode(nodes[i]);
        }
    }
}
$(".ztreeWrap").on("click",
function() {
    event.stopPropagation();
    return false;
});
$(document.body).on("click", expandTreeNode);

</script>
</html>
