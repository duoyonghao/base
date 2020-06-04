<%@ page import="java.util.*,java.util.List,net.sf.json.JSONObject" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	List<JSONObject> list=(List<JSONObject>)request.getAttribute("list");
	String status=request.getParameter("status");
	String parentId=(String)request.getAttribute("parentId");
	String parentName=(String)request.getAttribute("parentName");
	String frameSelfindex = request.getParameter("frameSelfindex");
	String labelLayerIndex = request.getParameter("labelLayerIndex");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>价目详情</title><!-- 从右侧个人中心  中间 图标 进入 -->
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
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/costOrder/add_cost_price.js"></script>
	
    
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
			<label class="sfbz"><span  name="treetype" id="sfbz" checked="checked">收费标准</span></label>
			<label class="sftc"><span  name="treetype" id="sftc">收费套餐</span></label>
			<label class="quankou"><input type="radio" name="button" id="button">全口</label>
			<ul>
				<li>明细</li>
			</ul>
		</div>
		<div class="costBd" style="position:relative;"><!-- Body -->
			<div class="ztreeWrap">
				<div class="searchWrap" style="display: none;">
		            <input type="text" placeholder="搜索" name="search" id="search" class="searchInput" style="width: 192px;">
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
		<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="save()">保存</a>
		<a href="javascript:void(0);" class="kqdsCommonBtn" id="qxhj">取消</a>
	</div>
  </div> <!-- ###费用添加end -->
</div>
</body>
<script type="text/javascript">
var list=<%=list%>;
var status="<%=status%>";
var parentId="<%=parentId%>";
var parentName="<%=parentName%>";
var contextPath = "<%=contextPath%>";
// 判断是否为 前台进行打折操作，如果是，则值为1
var perseqId = "<%=person.getSeqId()%>";
var maxDiscount = "<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag0_maxDiscount, request) %>";
var maxVoidmoney = "<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag19_maxVoidmoney, request) %>"; // 最大免除金额
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var tdindex = 1000; //表格里各类标签的id 动态添加时使用 数字随便从几开始
var developArr=[];
var developSpaceArr=[];
var frameSelfindex = "<%=frameSelfindex %>"; 
var frameSelfIdName="layui-layer-iframe"+frameSelfindex; //父页面layui的name  2019/8/25 lutian
var labelLayerIndex = "<%=labelLayerIndex %>";
var labelLayerIdName="layui-layer-iframe"+labelLayerIndex; //标签弹窗页面layui的name  2019/8/27 lutian
var c;
var isDefualtAdd = 0; //0不选中，1选中
$(function() {
	$(parent.window.frames[frameSelfIdName].document).find("#needShow").find("li").each(function(i,ele){
		//可开发项目(可组合)
		if($(ele).attr("slid")=="61223947-c0da-4d0f-a1ff-429232d10a3c"){
			developArr.push($(ele).attr("privelist"));
			
		}
		//可开发空间(可组合）
		if($(ele).attr("slid")=="3d6b7239-c964-4714-8020-4dcc15ed1f5d"){
			developSpaceArr.push($(ele).attr("privelist"));
			c=$(ele).attr("value");
		}
	});  
	if(developArr.length>0&&status==1){
		tdindex=tdindex+developArr.length;
	}
	if(developSpaceArr.length>0&&status==2){
		tdindex=tdindex+developSpaceArr.length;
	}
	if(status==2){
		$(".sfbz").css("display","none");
	}else{
		$(".sftc").css("display","none");
		$(".quankou").css("display","none");
	}
    // 隐藏 项目介绍和优惠信息
    $("#xmjsDiv").hide();
    $("#yhxxDiv").hide();
    if(status==2){
    	zTreeInitTc();    	
    }else{
	    zTreeInit();
    }
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
    //TODO 添加展示页面
    /* if(list!=null){
	addPriceListDetail(list);
    } */
    if(developArr.length>0&&status==1){
    	addPriceListDetail(developArr);
	}
	if(developSpaceArr.length>0&&status==2){
		addPriceListDetail(developSpaceArr);
	}
	if(c=="全口"){
		var $radio = $('input:radio');
		$radio.prop('checked', true); $radio.data('waschecked', true);
		isDefualtAdd = 1;
	}
});
$('input:radio').click(function(){
	var $radio = $(this);
	if ($radio.data('waschecked') == true){
		$radio.prop('checked', false); $radio.data('waschecked', false);
		isDefualtAdd = 0;
	} else {
		$radio.prop('checked', true); $radio.data('waschecked', true);
		isDefualtAdd = 1;
	}
});
function addPriceListDetail(list){
	tdindex = 1000;
	 //显示条数
	var tablehtml = "";
	 var lists=eval(list[0]);
	 if(list!=null){
		for (var i = 0; i < lists.length; i++) {
	        tdindex++;
	        var tablehtml = "";
	        var tabledata = lists[i];
	       // console.log(tabledata);
			var item="";
			var name="";
			if(tabledata.itemname!=''){
				item=tabledata.itemname;
				name="itemname";
			}
			if(tabledata.treatitemname!=''){
				item=tabledata.treatitemname;
				name="treatitemname";
			}
			tablehtml += "<tr style='' tcnameid="+ tabledata.tcnameid +">";
	        //删除按钮0
	        tablehtml += '<td style=""><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this,0,\'\')"><span style="color:red;">删除</span></a></td>';
	     	//项目编号1
            tablehtml += '<td style="display:none;">' + tabledata.itemid + '</td>';
	        //开发/正常2
	        tablehtml += '<td style=""><select id="kaifasel' + tdindex + '" style="width:60px;" onchange="changekaifa(\'' + tdindex + '\',\'kaifasel\')"><option value="正常" selected>正常</option></select></td>';
	        //特殊项目3
	        var tsxm = tabledata.istsxm == 0 ? "否": "是";
	        tablehtml += '<td style="">' + tsxm + '</td>';
	      	//治疗项目4
	        tablehtml += '<td style="display:none;"><span >'+ name + '</span></td>';
	        //治疗项目5
	        tablehtml += '<td style="text-align:left;"><span style="float:left; text-align:left;" class="time" title=\''+item+'\'>' + item + '</span></td>';
	        //单位6
	        tablehtml += '<td style="">' + tabledata.unit + '</td>';
	        //单价7
	        tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  readonly="readonly"  id="unitprice' + tdindex + '" value="' + tabledata.unitprice + '" oldvalue="' + tabledata.unitprice + '"></td>';
	        //数量8
	        tablehtml += '<td style=""><input  type="text" min="1" style="width:60px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'num\')"  id="num' + tdindex + '" value="' + tabledata.num + '" oldvalue="' + tabledata.num + '"></td>';
	        //折扣9
	        tablehtml += '<td style=""><input  type="text" min="1" style="width:75px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'discount\')"  id="discount' + tdindex + '" value="' + tabledata.discount + '" oldvalue="' + tabledata.discount + '"></td>';
	        //小计10
	        tablehtml += '<td style=""><span  id="subtotal' + tdindex + '">' + tabledata.subtotal + '</span></td>';
	        //欠费金额11
	        tablehtml += '<td style=""><span  id="arrearmoney' + tdindex + '">' + tabledata.arrearmoney + '</span></td>';
	        //缴费金额12
	        tablehtml += '<td style=""><input  type="text" min="1" style="width:90px; text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'paymoney\')"  id="paymoney' + tdindex + '" value="' + tabledata.paymoney + '" oldvalue="' + tabledata.paymoney + '"></td>';
	        //免除13
	        tablehtml += '<td style=""><input  type="text" min="1" style="width:90px;text-align:center;" onfocus="this.select()"  onchange="edithz(\'' + tdindex + '\',\'voidmoney\')"  id="voidmoney' + tdindex + '" value="' + tabledata.voidmoney + '" oldvalue="' + tabledata.voidmoney + '"></td>';
	        //节点名称14
	        tablehtml += '<td style="display:none;"><span  id="nodename' + tdindex + '">' + tabledata.nodename + '</span></td>';
	     	//折扣额15
            tablehtml += '<td style="display:none;"><span  id="zke' + tdindex + '">0</span></td>';
            //ID16
            tablehtml += '<td style="display:none;"></td>';
            //欠费ID17
            tablehtml += '<td style="display:none;"></td>';
            //setIsqfreal 18
            tablehtml += '<td style="display:none;"></td>';
            //欠费编号19
            tablehtml += '<td style="display:none;"></td>';
            //预交金开单20
            tablehtml += '<td style="display:none;">' + tabledata.isyjjitem + '</td>';
	        tablehtml += "</tr>";
	
	        $("#table").find('tbody').append(tablehtml);
	    }
	 }
    //每次table中有数据变动都要重新计算固定表头的宽度 防止错位。
    setFixedHeaderWidth();
    //刷新金额
    getAlljs();
}

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
function save() {
	
	
	var list1=[];
   var list = [];
   var name="";
   $('#table').find('tbody').each(function() {
       $(this).find('tr').each(function(i,ele) {
           var paramDetail = {};
           var node={};
	       paramDetail.tcnameid=$(ele).attr("tcnameid");
           $(this).find('td').each(function() {
               if ($(this).index() == 1) {
                   //开发
                   paramDetail.itemid = $(this).text();
               }else if ($(this).index() == 2) {
	               	//特殊项目
            	   paramDetail.exploit = $(this).text();
               }else if ($(this).index() == 3) {
	               	//特殊项目
	               	var tsxm=$(this).html()== "是" ? 1: 0;
                    paramDetail.istsxm = tsxm;
               }else if ($(this).index() == 4){
            	   name=$(this).find("span").html();
               }else if ($(this).index() == 5) {
	               	//治疗项目
	               var item = $(this).find("span").html();
	               	if(name=="itemname"){
                   		paramDetail.itemname = item;
	               	}
	               	if(name=="treatitemname"){
	               		paramDetail.treatitemname = item;
	               	}
              }else if ($(this).index() == 6) {
	               	//单位
                 	paramDetail.unit = $(this).text();
              }else if ($(this).index() == 7) {
	               	//单价
                 	paramDetail.unitprice = $(this).find("input").val();
              }else if ($(this).index() == 8) {
                  //有效期
               		paramDetail.num = $(this).find("input").val();
              }else if ($(this).index() == 9) {
	               	//折扣
	                paramDetail.discount =$(this).find("input").val();
              }else if ($(this).index() == 10) {
                 	//小计
	                paramDetail.subtotal = $(this).find("span").html();
              }else if ($(this).index() == 11) {
              		//欠费金额
               		paramDetail.arrearmoney = $(this).find("span").html();
              }else if ($(this).index() == 12) {
	               	//缴费金额
                    paramDetail.paymoney = $(this).find("input").val();
              }else if ($(this).index() == 13) {
	               	//免除金额
                  	paramDetail.voidmoney = $(this).find("input").val();
              }else if ($(this).index() == 14) {
	               	//节点名称
                	paramDetail.nodename = $(this).find("span").html();
                	node.nodename=$(this).find("span").html();
           	  }
           });
           list.push(paramDetail);
           list1.push(node);
       });
   });
   var data = JSON.stringify(list);
   data = encodeURIComponent(data); //编码 参数里有特殊符号时需要编码
   var data1 = JSON.stringify(list1);
   data1 = encodeURIComponent(data1); 
   if(isDefualtAdd==0){
	   isDefualtAdd='';
   }
   //console.log(JSON.stringify(data));
   var param = {
		   paramDetail:data,
		   nodeList:data1,
		   status:status,
		   parentId:parentId,
		   parentName:parentName,
		   button:isDefualtAdd
		   };
	   var url = contextPath + '/KQDS_LabelAct/findHzLabel.act';
	   $.axseSubmit(url, param,
	   function() {},
	   function(r) {
	       if (r.retState == "0") {
	           layer.alert('保存成功', { 
	        	   	//leveLabel: "种植+正畸"
	        	   end: function() {
	        		   //console.log(JSON.stringify(r)+"----------r");
	        		   
		   				//如果标签存在，修改标签选择页面标签数据
	   					$(parent.window.frames[labelLayerIdName].document).find("#threeLabelUl").find("li").each(function(i,ele){
	   						// 可开发项目(可组合)
	   						if($(ele).attr("slid")=='61223947-c0da-4d0f-a1ff-429232d10a3c' && r.status==1){
	   							$(ele).attr("id",r.seqid);//标签id
	   							$(ele).text(r.leveLabel);// 标签中文名称
	   						}
	   						//可开发空间（可组合）
	   						if($(ele).attr("slid")=='3d6b7239-c964-4714-8020-4dcc15ed1f5d' && r.status==2){
	   							$(ele).attr("id",r.seqid);//标签id
	   							$(ele).text(r.leveLabel);// 标签中文名称
	   						}
	   						//删除当前项目标签页面标签也删掉判断
	   						//可开发项目
	   						if(r.leveLabel=="" && $(ele).attr("slid")=='61223947-c0da-4d0f-a1ff-429232d10a3c' && r.status==1){
	   							$(ele).remove();
	   						}
	   						//可开发空间
	   						if(r.leveLabel=="" && $(ele).attr("slid")=='3d6b7239-c964-4714-8020-4dcc15ed1f5d' && r.status==2){
	   							$(ele).remove();
	   						}
	   						
	   					}); 
	        		   
	        		 //如果标签存在，修改患者建档页面标签数据
	        		 var developPIndex; 
	        		   var needAlreadyHtml=$(parent.window.frames[frameSelfIdName].document).find("#needShow").html();
		   				if(needAlreadyHtml.indexOf("li")!=-1){
		   					$(parent.window.frames[frameSelfIdName].document).find("#needShow").find("li").each(function(i,ele){
		   						//可开发项目(可组合)
		   						if($(ele).attr("slid")=='61223947-c0da-4d0f-a1ff-429232d10a3c' && r.status==1){
		   							developPIndex=i; 
		   							if(r.leveLabel==""){
		   								$(ele).remove();
		   							}
		   							var newLabelValue=r.leveLabel+'<span onclick="cleatBtn(this)" id="cleatBtn">X</span>';
		   							$(parent.window.frames[frameSelfIdName].document).find("#needShow").find("li").eq(i).attr("value",r.leveLabel);//标签名称
		   							$(parent.window.frames[frameSelfIdName].document).find("#needShow").find("li").eq(i).html(newLabelValue);//标签内text值
		   							$(parent.window.frames[frameSelfIdName].document).find("#needShow").find("li").eq(i).attr("id",r.seqid);//标签id
		   							$(parent.window.frames[frameSelfIdName].document).find("#needShow").find("li").eq(i).attr("privelist",JSON.stringify(r.priveListDetails));//标签存储数据privelist
		   							var index=parent.layer.getFrameIndex(window.name);
					   				parent.layer.close(index);
		   						}
		   						//可开发空间(可组合）
		   						if($(ele).attr("slid")=='3d6b7239-c964-4714-8020-4dcc15ed1f5d' && r.status==2){
		   							developPIndex=i; 
		   							if(r.leveLabel==""){
		   								$(ele).remove();
		   							}
		   							var newLabelValue=r.leveLabel+'<span onclick="cleatBtn(this)" id="cleatBtn">X</span>';
		   							$(parent.window.frames[frameSelfIdName].document).find("#needShow").find("li").eq(i).attr("value",r.leveLabel);//标签名称
		   							$(parent.window.frames[frameSelfIdName].document).find("#needShow").find("li").eq(i).html(newLabelValue);//标签内text值
		   							$(parent.window.frames[frameSelfIdName].document).find("#needShow").find("li").eq(i).attr("id",r.seqid);//标签id
		   							$(parent.window.frames[frameSelfIdName].document).find("#needShow").find("li").eq(i).attr("privelist",JSON.stringify(r.priveListDetails));//标签存储数据privelist
		   							var index=parent.layer.getFrameIndex(window.name);
					   				parent.layer.close(index);
		   						}
		   					}); 
		   						
		   				}
		   				
		   				
		   				// 还未添加组合项目/空间的判断
		   				if(developPIndex>=0){
		   					//console.log(developPIndex+"---------已经存在下标");
		   				}else{
		   					//直接添加并删除为空时不追加标签
		   					if(r.leveLabel==""){
		   						var index=parent.layer.getFrameIndex(window.name);
				   				parent.layer.close(index);
		   						return;
		   					}
		   					//向患者建档页面追加标签
		   					var needHtml=needAlreadyHtml+'<li id='+r.seqid+' slid='+parentId+' slname='+parentName+' class="aa" value='+r.leveLabel+' privelist='+JSON.stringify(r.priveListDetails)+'>'+r.leveLabel+'<span onclick="cleatBtn(this)" id="cleatBtn">X</span></li>';
			   				$(parent.window.frames[frameSelfIdName].document).find("#needShow").html(needHtml);
			   				//向标签页面三级追加标签
			   				var developProjectHtml='<li id='+r.seqid+' slid='+parentId+' slname='+parentName+' onclick="thirdLevel(this.id);">'+ r.leveLabel +'</li>';
			   				$(parent.window.frames[labelLayerIdName].document).find("#threeLabelUl").append(developProjectHtml);
			   				var index=parent.layer.getFrameIndex(window.name);
			   				parent.layer.close(index);
		   				}
	        		   
	               }
	           });
	       }else{
	    	   layer.alert('保存失败', { 
	           });
	       }
	   },
	   function() {
	       layer.alert('保存失败', {
	             
	       });
	   });
}
</script>
</html>
