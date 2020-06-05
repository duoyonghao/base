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
<title>咨询中心</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/layer-v2.4/layer/skin/blueSkin/skin_style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<style type="text/css">
/* 页面自定义  yangsen  17-04-02 操作按钮区域改为固定四列*/
.operateModel table tr td{height:31px;text-align:left;vertical-align:middle;font-size:14px;font-family: "Microsoft YaHei";color: #3f3f3f;cursor: pointer;-webkit-transition: all .3s; transition: all .3s;}
.operateModel table tr td:hover{color: #0e7cc9;}


.tableBox{
	margin-top:0px;
}
#gongzuol{
	margin-bottom:12px;
}
*{
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
.centerWrap .columnWrap .columnBd .pagination ul li.current{
	color: #00a6c0;
	border-bottom:0px solid #00a6c0;
}
</style>
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
</head>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
                <div class="columnHd">
				    <span class="title">咨询中心</span>
				    <div class="searchDiv" style="overflow:hidden;">
							<input type="text" placeholder="患者姓名/手机" id="searchValue"/>
							<a style="" href="javascript:void(0);" onclick="toSearchValue()" class="icon16 searchBtn"></a>
					</div>
<!-- 				    <input type="hidden" placeholder="患者姓名/手机" id="searchValue"/> -->
				</div>
                <div class="columnBd">
                    <ul id="menuul">
                    	<li onclick="netOrderTable();" id="zxjz_wdyy" class="hide">网电预约&nbsp;&nbsp;<span id="wdataCount" class="notRead">0</span></li>
                        <li onclick="hospitalOrderTable();" id="zxjz_mzyy" class="hide">门诊预约&nbsp;&nbsp;<span id="mdataCount" class="notRead">0</span></li>
                        <li onclick="initTable(0);" id="zxjz_ddzl" class="hide">等待治疗&nbsp;&nbsp;<span id="tdataCountd" class="notRead">0</span></li>
                        <li onclick="getOrderlist(1);" id="zxjz_ddjz" class="hide">等待结账&nbsp;&nbsp;<span id="ddataCount" class="notRead">0</span></li>
                        <li onclick="getPayOrderlist();" id="zxjz_yjz" class="hide">已结账&nbsp;&nbsp;<span id="ydataCount" class="notRead">0</span></li>
                        <li onclick="initTable(5);" id="zxjz_jrhz" class="hide">今日患者&nbsp;&nbsp;<span id="tdataCountj" class="notRead">0</span></li>
                    </ul>
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered"></table>
                    </div>
                    <div id="gongzuol">
		                <div class="columnBd">
		                	<ul class="dataCountUl" id="dataCount">
		                		
		                	</ul>
		                </div>
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
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/zxindexTab.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/btnfuc.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<script type="text/javascript">
var listbutton;
var contextPath = "<%=contextPath%>";
var personseqid = "<%=person.getSeqId()%>";
var personrole = "<%=person.getUserPriv()%>";
var personroleother  = "<%=person.getUserPrivOther()%>";
var canKd = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag2_canKd, request)%>';
var menuid = "<%=menuid%>";
var onclickrowOobj="";
var wquerycounturl = '<%=contextPath%>/KQDS_Net_OrderAct/getCountIndex1.act';
var toptableclickval = 0;

var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
var allowed=allowedperson.split(",");//允许权限

$(function(){
	/* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */
    
  //获取当前页面所有按钮
	getButtonAllCurPage(menuid);
	
 	// layer打开弹窗，不同操作按钮，打开不同的弹出层
    layerBtnOper();
  	//鼠标移入底部操作区 图标变蓝色
    /* $(".optUl").on("mouseenter",".pointer",function(){
 		console.log("aa");
 		$(this).find("img").attr("src",contextPath+"/static/image/kqdsFront/img/icon/book2.png");
 	}); */
  	//鼠标移出底部操作区 图标变灰黑色
 	/* $(".optUl").on("mouseleave",".pointer",function(){
 		console.log("bbb");
 		$(this).find("img").attr("src",contextPath+"/static/image/kqdsFront/img/icon/book.png");
 	}); */
    togglemodel.initial("zxzx");/*wl 初始化 开关模块 */
 	// 默认打开等待治疗列表
	var li = $(".columnBd").children("ul").children("li").eq(2);
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

//判断当前人员权限
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
        	 if(name == 'hykbl'){ // 会员卡办理，需要自己传requrl
         		hykbl_localFuc();
         	}else{
         		static_btnfucDeal(name); // 调用btnfuc.js中的方法
         	}
         }else{
        		layer.alert('权限不足！', {
    				  
    			});
         }
    });
	
}


/* //会员卡办理
function hykbl_localFuc(){
	var requrl = contextPath + '/kqdsFront/member/hyzx_index.jsp';
 	if(onclickrowOobj != null && onclickrowOobj != ""){ // 如果没选中，也让其可以到跳转页面中取选
 		requrl += '?usercode='+onclickrowOobj.usercode+'&username='+onclickrowOobj.username;
 	}
	static_btnfucDeal('hykbl',requrl); // 调用btnfuc.js中的方法
} */

//会员卡办理
function hykbl_localFuc(){
	var requrl = contextPath + '/KQDS_MemberAct/toMemberAdd.act';
 	if(onclickrowOobj != null && onclickrowOobj != ""){ // 如果没选中，也让其可以到跳转页面中取选
 		requrl += '?usercode='+onclickrowOobj.usercode+'&username='+onclickrowOobj.username;
 	}
	static_btnfucDeal('hykbl',requrl); // 调用btnfuc.js中的方法
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
	
	// 创建一个数组，存放listbutton的qxName 
	var listbuttonArray = new Array();
	for ( var i = 0; i < listbutton.length; i++) {
		listbuttonArray[i] = listbutton[i].qxName;
	}
	
	/* 按钮 */
	var btnList =  '[';
		btnList	+= '{"qx":"xgzl","name":"修改资料"},';
		btnList	+= '{"qx":"hykbl","name":"会员卡办理"},';
		btnList	+= '{"qx":"yyrl","name":"预约日历"},';
		btnList	+= '{"qx":"hfzx","name":"回访管理"},';
		btnList	+= '{"qx":"fytj","name":"费用添加"},';
		btnList	+= '{"qx":"zz","name":"转诊咨询"},';
// 		btnList	+= '{"qx":"tgjh","name":"推广计划"},';
		btnList	+= '{"qx":"tk","name":"退款申请"},';
		btnList	+= '{"qx":"ghxg","name":"挂号修改"},';
		btnList	+= '{"qx":"xxcx","name":"信息查询"},';
		btnList	+= '{"qx":"jzcx","name":"接诊查询"},';
		btnList	+= '{"qx":"fycx","name":"费用查询"},';
		btnList	+= '{"qx":"mxcx","name":"明细查询"},';
		btnList	+= '{"qx":"rsktj_ask","name":"日收款查询"},';
		btnList	+= '{"qx":"xfmx_all_ask","name":"日收款明细"},';
		btnList	+= '{"qx":"zxzx_zsxm","name":"赠送项目"},';
		btnList	+= '{"qx":"syzs","name":"使用赠送"},';
		btnList	+= '{"qx":"cjlclj","name":"创建临床路径"}';
		
	    btnList	+= ']';
	    var jsonbtnList = jQuery.parseJSON( btnList );
	    var listbuttonArrayjson = JSON.stringify(listbuttonArray);
		/* 判断首页按钮是否显示 后加单独判断防止底部生成按钮 */
		if(listbuttonArrayjson.indexOf("zxjz_wdyy") != -1 ) {
			$("#zxjz_wdyy").removeClass("hide");
		}
		if(listbuttonArrayjson.indexOf("zxjz_mzyy") != -1 ) {
			$("#zxjz_mzyy").removeClass("hide");
		}
		if(listbuttonArrayjson.indexOf("zxjz_ddzl") != -1 ) {
			$("#zxjz_ddzl").removeClass("hide");
		}
		if(listbuttonArrayjson.indexOf("zxjz_ddjz") != -1 ) {
			$("#zxjz_ddjz").removeClass("hide");
		}
		if(listbuttonArrayjson.indexOf("zxjz_yjz") != -1 ) {
			$("#zxjz_yjz").removeClass("hide");
		}
		if(listbuttonArrayjson.indexOf("zxjz_jrhz") != -1 ) {
			$("#zxjz_jrhz").removeClass("hide");
		}
		for( var i = 0; i < jsonbtnList.length; i++){
			// 判断当前用户具备的按钮权限
			var index = jQuery.inArray(jsonbtnList[i].qx, listbuttonArray); 
			// index = -1 时，表示当前用户没有此按钮的操作权限
			if(index==-1){//无权限的展示
				menubutton1+="<li name=''>";
				menubutton1+="<div class='not-allowed' style='display:none;'>";//鼠标移入禁用效果，隐藏按钮
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
</body>
</html>
