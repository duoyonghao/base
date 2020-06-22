<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	// 获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>医疗中心</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reception_center.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/layer-v2.4/layer/skin/blueSkin/skin_style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js 与css-->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/indexTab.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/btnfuc.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<style>
	.fixed-table-container{/*不可删 否则左侧高度会变高*/
		box-sizing:border-box;
	}
	.pagination li{
	margin-left: 0px!important;
	height:auto!important;
	}
	.dropdown-menu{
		min-width: auto!important;
	    padding: 0px 0!important;
	}
	.dropdown-menu li{
		margin-left: 0px!important;
	}
	.clearfix{
		border-bottom: 1px solid #ddd;
	}
	.centerWrap .columnWrap .columnBd .pagination ul li.current {
    	color: #00a6c0;
   	 	border-bottom: 0px solid #00a6c0;
    }
    .page-input{
    	height:30px !important;
    }
     #searchValue{ 
     	box-sizing: border-box; 
     } 
</style>


</head>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
                <div class="columnHd">
				    <span class="title">医疗中心</span>
				    <div class="searchDiv" style="overflow:hidden;">
							<input type="text" placeholder="患者姓名/手机" id="searchValue"/>
							<a style="" href="javascript:void(0);" onclick="toSearchValue()" class="icon16 searchBtn"></a>
					</div>
<!--                	 	<input type="hidden" placeholder="患者姓名/手机" id="searchValue"/> -->
                </div>
                <div class="columnBd">
                    <ul id="menuul">
                        <li onclick="netOrderTable();" id="ylhz_wdyy" class="hide">网电预约&nbsp;&nbsp;<span id="wdataCount" class="notRead">0</span></li>
                        <li onclick="hospitalOrderTable();" id="ylhz_mzyy" class="hide">门诊预约&nbsp;&nbsp;<span id="mdataCount" class="notRead">0</span></li>
                        <li onclick="initTable(0);" id="ylhz_ddzl" class="hide">等待治疗&nbsp;&nbsp;<span id="tdataCountd" class="notRead">0</span></li>
                        <li onclick="getOrderlist(1);" id="ylhz_ddjz" class="hide">等待结账&nbsp;&nbsp;<span id="ddataCount" class="notRead">0</span></li>
                        <li onclick="getPayOrderlist();" id="ylhz_yjz" class="hide">已结账&nbsp;&nbsp;<span id="ydataCount" class="notRead">0</span></li>
                        <li onclick="initTable(5);" id="ylhz_jrhz" class="hide">今日患者&nbsp;&nbsp;<span id="tdataCountj" class="notRead">0</span></li>
                    </ul>
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered"></table>
                    </div>
                </div>
                <div id="gongzuol">
	               <div class="columnBd">
		               	<ul class="dataCountUl" id="dataCount">
		               		
		               	</ul>
	               </div>
	            </div>
	        </div>
            
            
            <div class="operateModel">
                <div class="optBox">
                   <ul id="ul1" class="optUl">
                   </ul>
                </div>
            </div>
        </div>
        <!--右侧信息浏览-->
        <div class="lookInfoWrap">
			<%@include file="/inc/rightPartInfo.jsp" %>
		</div>
    </div>
</div>
</body>
<script type="text/javascript">
var listbutton;
var contextPath = "<%=contextPath%>";
var personseqid = "<%=person.getSeqId()%>";
var personrole = "<%=person.getUserPriv()%>";
var personroleother  = "<%=person.getUserPrivOther()%>";
var onclickrowOobj="";
var wquerycounturl = '<%=contextPath%>/KQDS_Net_OrderAct/getCountIndex1.act';
var toptableclickval = 0;
var selectOrderNumber;
var menuid = "<%=menuid%>";
var canKd = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag2_canKd, request)%>';
var consultSelect;

var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.RIGHT_YCHF)%>';
var allowed=allowedperson.split(",");//允许权限

$(function(){
	/* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */
    
 //获取当前页面所有按钮
	getButtonAllCurPage(menuid);
	togglemodel.initial("ylzx");/*wl 初始化 开关模块 */
 	// layer打开弹窗，不同操作按钮，打开不同的弹出层
    layerBtnOper();
  	//鼠标移入底部操作区 图标变蓝色
    /* $(".optUl").on("mouseenter",".pointer",function(){
 		
 		$(this).find("img").attr("src",contextPath+"/static/image/kqdsFront/img/icon/book2.png");
 	}); */
  	//鼠标移出底部操作区 图标变灰黑色
 	/* $(".optUl").on("mouseleave",".pointer",function(){
 		
 		$(this).find("img").attr("src",contextPath+"/static/image/kqdsFront/img/icon/book.png");
 	}); */
 	// 默认打开等待治疗列表
	var li = $(".columnBd").children("ul").children("li").eq(5);
	li.attr({
	    "class" : "current"
	  });
	li.trigger("click");
    
    // 计算主体的宽度，这里包含该页面特有的高度计算代码 -----------注意！！！
    // setWidth() setHeight()，需要页面加载完成后再计算，所以此方法放到 getButtonPower() 方法的末尾执行
    $(window).resize(function () {
    	setWidth();
        setHeight();// 接待中心 医疗中心  咨询中心  统一调用indexTab.js中的对应方法
    });
    
});

//判断当前人员权限是否为前台
var total=load.concat(allowed);
function isExist(total) {
    var exist = {};
    for(var i in total) {
        if(exist[total[i]]) {
            return true;
        }
        exist[total[i]] = true;
    }
    return false;
}

function toSearchValue(){
	var li = $("#menuul").children("li");
	for(var i = 0;i<li.length;i++){
		if(li[i].className == "current"){
			li[i].click();
		}
	}
}


//layer打开弹窗，不同操作按钮，打开不同的弹出层
function layerBtnOper(){
    // 点击关系图打开弹窗
    $('.addRealation').click(function () {
    	// [Static]调用全局方法进行处理
    	static_btnfucDeal('addRealation');
    });
	
	//layer打开弹窗
    $('.operateModel .optBox').on('click','div',function () {
 		var name = $(this).parent().attr('name');
        if(name != null && name != ''){
        	// [Static]调用全局方法进行处理
            static_btnfucDeal(name);
        }else{
        	layer.alert('权限不足！', {  	
			});
        }
    });
	
}

function queryParams() {
	 var whf ="",qf="";
	 if ($("#whf").is(':checked')) {
		 whf=1;
     }
	 if ($("#qf").is(':checked')) {
		 qf=1;
     }
	 var temp = {  //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			  regdeptSearch:$("#regdeptSearch").val(),
			  doctorSearch:$('#doctorSearch').val(),
			  starttime:$('#starttime').val(),
			  endtime:$('#endtime').val(),
			  searchValue:$('#searchvalue').val(),
			  bqSearch:$('#bqSearch').val(),
			  importantSearch:$('#importantSearch').val(),
			  devchannelSearch:$('#devchannelSearch').val(),
			  ageSearch:$('#ageSearch').val(),
			  regsort:$('#regsort').val(),
			  failreason1:$('#failreason1').val(),
			  askstatus:$('#askstatus').val(),
			  isprint:$('#isprint').val(),
			  whf:whf,
			  qf:qf
	    };
  return temp;
}
$('#export').on('click', function(){
	var li = $(".dropdown-menu").children("li").last();
	li.trigger("click");
});
$('#clean').on('click', function(){
	$(".formBox :input").not(":button, :submit, :reset, :hidden").val("").removeAttr("checked").remove("selected");//核心
});
function getButtonPower(){
	var menubutton1 = "";
// 	console.log(JSON.stringify(listbutton)+"--------------");
	// 创建一个数组，存放listbutton的qxName 
	var listbuttonArray = new Array();
	for ( var i = 0; i < listbutton.length; i++) {
		listbuttonArray[i] = listbutton[i].qxName;
//  		console.log(listbuttonArray[i]);
	}
	/* 按钮 */
	var btnList =  '[';
		btnList	+= '{"qx":"xgzl","name":"修改资料"},';
		btnList	+= '{"qx":"yyrl","name":"预约日历"},';
		btnList	+= '{"qx":"hfzx","name":"回访管理"},';
		btnList	+= '{"qx":"fytj","name":"费用添加"},';
// 		btnList	+= '{"qx":"tgjh","name":"推广计划"},';
		btnList	+= '{"qx":"jgcs","name":"创建加工单"},';
		btnList	+= '{"qx":"tjjgd","name":"添加加工单"},';
		btnList	+= '{"qx":"lltj","name":"领料统计"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"llcx","name":"领料查询"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"xjbl","name":"新建病历"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"xxcx","name":"信息查询"},';
		btnList	+= '{"qx":"jzcx","name":"接诊查询"},';
		btnList	+= '{"qx":"mxcx","name":"明细查询"},';
		btnList	+= '{"qx":"fycx","name":"费用查询"},';
		btnList	+= '{"qx":"ckbl","name":"查看病历"},';
		btnList	+= '{"qx":"ylhz_ksll","name":"科室领料"},';
		btnList	+= '{"qx":"ylhz_lclj","name":"临床路径"}';//#########添加临床路径按钮##########
	    btnList	+= ']';
	    var jsonbtnList = jQuery.parseJSON( btnList );
//网电预约等6个按钮显示权限	
		var listbuttonArrayjson = JSON.stringify(listbuttonArray);
		/* 判断首页按钮是否显示 后加单独判断防止底部生成按钮 */
		if(listbuttonArrayjson.indexOf("ylhz_wdyy") != -1 ) {
			$("#ylhz_wdyy").removeClass("hide");
		}
		if(listbuttonArrayjson.indexOf("ylhz_mzyy") != -1 ) {
			$("#ylhz_mzyy").removeClass("hide");
		}		
		if(listbuttonArrayjson.indexOf("ylhz_ddzl") != -1 ) {
			$("#ylhz_ddzl").removeClass("hide");
		}
		if(listbuttonArrayjson.indexOf("ylhz_ddjz") != -1 ) {
			$("#ylhz_ddjz").removeClass("hide");
		}
		if(listbuttonArrayjson.indexOf("ylhz_yjz") != -1 ) {
			$("#ylhz_yjz").removeClass("hide")	;
		}
		if(listbuttonArrayjson.indexOf("ylhz_jrhz") != -1 ) {
			$("#ylhz_jrhz").removeClass("hide")	;
		}
		for( var i = 0; i < jsonbtnList.length; i++){
			// 判断当前用户具备的按钮权限
			var index = jQuery.inArray(jsonbtnList[i].qx, listbuttonArray); 
			// index = -1 时，表示当前用户没有此按钮的操作权限
			if(index==-1){//无权限的展示
				menubutton1+="<li name='' style='display:none;'>";//没权限的设置display:none隐藏
				menubutton1+="<div class='not-allowed'>";//鼠标移入禁用效果
				menubutton1+='<img src="'+ contextPath +'/static/image/kqdsFront/img/icon/book3.png">';//无权限 浅灰色
				//menubutton1+='<span class="imgSpan noAuthority"></span>';//无权限 浅灰色
				menubutton1+='<span >'+ jsonbtnList[i].name +'</span>' //浅灰色字体
				menubutton1+="</div>";
				menubutton1+="</li>";
			}else{//有权限的展示
				menubutton1+="<li name='"+ jsonbtnList[i].qx +"'>";
				menubutton1+="<div class='pointer'>";//鼠标移入小手效果
				menubutton1+='<img src="'+ contextPath +'/static/image/kqdsFront/img/icon/book2.png">';//有权限 深灰色
				//menubutton1+='<span class="imgSpan"></span>';//有权限 深灰色
				menubutton1+='<span class="normal">'+ jsonbtnList[i].name +'</span>'; //深灰色字体 鼠标移入 蓝色
				menubutton1+="</div>";
				menubutton1+="</li>";
			}
			//isHasQx  defaultClsName jsonbtnList[i].name
			
		}
	
    $("#ul1").append(menubutton1);
	// 计算主体的宽度，这里包含该页面特有的高度计算代码 -----------注意！！！
	// setWidth() setHeight()，需要页面加载完成后再计算，所以此方法放到 getButtonPower() 方法的末尾执行
	setWidth();
	setHeight();
	
}
</script>
</html>
