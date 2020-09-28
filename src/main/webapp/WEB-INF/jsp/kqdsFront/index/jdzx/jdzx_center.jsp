<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
		
// 	SysParaUtil.getSysValueByName(request, SysParaUtil.RIGHT_YCHF);
// 	SysParaUtil.getSysValueByName(request, SysParaUtil.RIGHT_YCHF);	
// 	person.getUserPriv()
%>
<!DOCTYPE html>
<html>
<!-- 该页面已优化  ====鲍==03-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>接待中心</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>


<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js?v=${version}"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/indexTab.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/btnfuc.js?v=${version}"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
</head>
<style>

.collectBtn{	/*关闭右侧功能块按钮  */
	width:10px;
	background:#0E7CC9;
	text-align:center;
	float:left;
	height:100px;
	line-height:100px;
	cursor:pointer;
}
.collectBtn:hover{	/*关闭右侧功能块按钮  */
	background:#1289DB;
}
.collectBtn:hover span{	/*关闭右侧功能块按钮  */
	border-left-color:#ddd;
}
.collectBtn:hover span.closed{	/*关闭右侧功能块按钮  */
	border-right-color:#ddd;
}
.collectBtn span{/* 按钮上的箭头  右*/
	border:6px solid transparent;
	border-left-color:#fff;
	float:left;
	margin:47px 0 0 2px;
}
.collectBtn span.closed{/*  关闭时箭头的样式  左*/
	border-left-color:transparent;
	border-right-color:#fff;
	margin:47px 0 0 -5px;
}
#main .centerWrap{
	margin-right:0;
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
.fixed-table-container{
	background-color: white;
	padding-bottom: 90px!important;
}
.centerWrap .columnWrap .columnBd .pagination ul li.current{
	color: #00a6c0;
	border-bottom:0px solid #00a6c0;
}
</style>
<body>
	<div id="container">
		<div id="main">
			<!--左侧中心-->
			<div class="centerWrap">
				<div class="columnWrap">
					<div class="columnHd">
						<span class="title">接待中心</span>
						<div class="searchDiv" style="overflow:hidden;">
							<input type="text" placeholder="患者姓名/手机" id="searchValue"/>
							<a style="" href="javascript:void(0);" onclick="toSearchValue()" class="icon16 searchBtn"></a>
						</div>
					</div>
					<div class="columnBd">
						<ul id="menuul">
							<li onclick="netOrderTable('all','1-1');" class="hide" id="jdzx_wdyy">网电预约&nbsp;&nbsp;
								<span id="wdataCount" class="notRead">0
								</span>
							</li>
							<li onclick="hospitalOrderTable('all','1-2');" class="hide" id="jdzx_mzyy">门诊预约&nbsp;&nbsp;
								<span id="mdataCount" class="notRead">0
								</span>
							</li>
							<li onclick="initTableB(0,'all','1-3');" class="hide" id="jdzx_shyy">手术预约&nbsp;&nbsp;
								<span id="ssdataCount" class="notRead">0
								</span>
							</li>
							<li onclick="initTable(0,'all');" class="hide" id="jdzx_ddzl">等待治疗&nbsp;&nbsp;
								<span id="tdataCountd" class="notRead">0
								</span>
							</li>
							<li onclick="getOrderlist(1,'all');" class="hide" id="jdzx_ddjz">等待结账&nbsp;&nbsp;
								<span id="ddataCount" class="notRead">0
								</span>
							</li>
							<li onclick="getPayOrderlist('all');" class="hide" id="jdzx_yjz">已结账&nbsp;&nbsp;
								<span id="ydataCount" class="notRead">0
								</span>
							</li>
							<li onclick="initTable(5,'all');" class="hide" id="jdzx_jrhz">今日患者&nbsp;&nbsp;
								<span id="tdataCountj" class="notRead">0
								</span>
							</li>
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
					<!-- <button class="kqdsCommonBtn" onclick="dzcx()">到诊查询</button> -->
				</div>
				
			</div>
			
			<!--右侧信息浏览-->
			<div class="lookInfoWrap">
				<%@include file="//inc/rightPartInfo.jsp" %>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
// 查询每个tab的待办数量，并显示
var wquerycounturl = '<%=contextPath%>/KQDS_Net_OrderAct/getCountIndex1.act?querytype=all';

// 从parent页面，获取到该页面的所有按钮，并转换成Object对象
var listbutton;

// 存储表格中点击的某一个行的对象,在zhcx.js的 initTable 方法中赋值
var onclickrowOobj = "";

var contextPath = "<%=contextPath%>";
var personseqid = "<%=person.getSeqId()%>";
var personrole  = "<%=person.getDeptId()%>";
var personroleother  = "<%=person.getUserPrivOther()%>";
var canKd = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag2_canKd, request)%>';
var usercodechild = "";
var usernamechild = "";
var menuid = "<%=menuid%>";
var toptableclickval = 0;
var username;

var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
var allowed=allowedperson.split(",");//允许权限

$(function(){
	//获取当前页面所有按钮
	getButtonAllCurPage(menuid);
	/* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */
 	
 	// 加载该页面的所有可操作按钮，每个页面一共有多少按钮是相对固定的，在此基础上，通过获取当前登录用户的权限按钮，来进行界面展示
	//getButtonPower();
 	
	

    // layer打开弹窗，不同操作按钮，打开不同的弹出层
    layerBtnOper();
    
    togglemodel.initial("jdzx");/*wl 初始化 开关模块 */
 	// 表格初始化，每个主界面的默认tab选中事件，如果是收费的角色，则默认打开等待结账，其他角色，默认打开等待治疗tab
	initclick(); 
    //鼠标移入底部操作区 图标变蓝色
 	/* $(".optUl").on("mouseenter",".pointer",function(){
 		
 		$(this).find("img").attr("src",contextPath+"/static/image/kqdsFront/img/icon/book2.png");
 	}); */
 	//鼠标移出底部操作区 图标变灰黑色
 	/* $(".optUl").on("mouseleave",".pointer",function(){
 		
 		$(this).find("img").attr("src",contextPath+"/static/image/kqdsFront/img/icon/book.png");
 	}); */
    //计算主体的宽度，这里包含该页面特有的高度计算代码 -----------注意！！！
    // setWidth() setHeight()，需要页面加载完成后再计算，所以此方法放到 getButtonPower() 方法的末尾执行
    
	$(window).resize(function () {
		setWidth();
        setHeight(); // 接待中心 医疗中心  咨询中心  统一调用indexTab.js中的对应方法
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

// layer打开弹窗，不同操作按钮，打开不同的弹出层
function layerBtnOper(){
    // 点击关系图打开弹窗
    $('.addRealation').click(function () {
    	// [Static]调用全局方法进行处理
    	static_btnfucDeal('addRealation');
    });
	
	//layer打开弹窗
    $('.operateModel .optBox').on('click','div',function () {
        var name = $(this).parent().attr('name');
     	// [Static]调用全局方法进行处理
        if(name != null && name != ''){
        	if(name == 'hykbl'){ // 会员卡办理，需要自己传requrl
        		hykbl_localFuc();
        	}else if(name == 'yjj'){
        		
        		if(onclickrowOobj.usercode == null || onclickrowOobj.usercode == ''){
        			layer.alert('请选择患者' );
					  return false;
        		}
        		
        		var flag = false;
        		var url = '<%=contextPath%>/KQDS_MemberAct/checkIsMemberByUsercode.act?usercode=' + onclickrowOobj.usercode;
        		$.axse(url,null, 
        	          function(data){
        				  if(data.data == 0){
        					  flag = true;
        		       	  }
        	          },
        	          function(){
      	      	});
        		
        		if(flag){
        			layer.alert('请先办理会员卡' );
					return ;
        		}
        		
        		static_btnfucDeal(name);
        	}
        	else{
        		static_btnfucDeal(name);
        	}
        }else{
        	layer.alert('权限不足！', {
				  
			});
        }
       
    });
	
}
// 会员卡办理
function hykbl_localFuc(){
	var requrl = contextPath + '/KQDS_MemberAct/toMemberAdd.act';
 	if(onclickrowOobj != null && onclickrowOobj != ""){ // 如果没选中，也让其可以到跳转页面中取选
 		requrl += '?usercode='+onclickrowOobj.usercode+'&username='+onclickrowOobj.username;
 	}
	static_btnfucDeal('hykbl',requrl); // 调用btnfuc.js中的方法
}

	// 每个页面一共有多少按钮是相对固定的，在此基础上，通过获取当前登录用户的权限按钮，来进行界面展示
	function getButtonPower() {
		
		var menubutton1 = "", menubutton2 = "";
		
		// 创建一个数组，存放listbutton的qxName
		var listbuttonArray = new Array();
		for ( var i = 0; i < listbutton.length; i++) {
			listbuttonArray[i] = listbutton[i].qxName;
		}
		
		/* 左侧按钮 */
		var btnList = '[';
			btnList	+='{"qx":"hzjd","name":"患者建档"},';
			btnList	+='{"qx":"ghcz","name":"挂号操作"},';
			btnList	+='{"qx":"ghxg","name":"挂号修改"},';
			btnList	+='{"qx":"zlxg","name":"资料修改"},';
			btnList	+='{"qx":"yyrl","name":"预约日历"},';
			btnList	+='{"qx":"jzcx","name":"接诊查询"},';
			btnList	+='{"qx":"xxcx","name":"信息查询"},';
			btnList	+='{"qx":"hfzx","name":"回访管理"},';
			btnList	+='{"qx":"rcjs","name":"日常结帐"},';
			btnList	+='{"qx":"qxjs","name":"取消结账"},';
			btnList	+='{"qx":"rjmx","name":"日结查询"},';
			/* 右侧按钮 */
			btnList	+='{"qx":"hykbl","name":"会员卡办理"},';
			btnList	+='{"qx":"yjj","name":"预交金充值"},';
			btnList	+='{"qx":"jz","name":"结账"},';
			btnList	+='{"qx":"tk","name":"退款"},';
			btnList	+='{"qx":"bdsj","name":"补打单据"},';
			btnList	+='{"qx":"fycx","name":"费用查询"},';
			btnList	+='{"qx":"mxcx","name":"明细查询"},';
			btnList	+='{"qx":"dzcx","name":"到诊查询"},';
			btnList	+='{"qx":"rsktj","name":"日收款查询"},';
			btnList	+='{"qx":"kpxxcx","name":"开票信息查询"}';
		    btnList	+=']';
		var jsonbtnList = jQuery.parseJSON( btnList );
		var listbuttonArrayjson = JSON.stringify(listbuttonArray);
		/* 判断首页按钮是否显示 后加单独判断防止底部生成按钮 */
		if(listbuttonArrayjson.indexOf("jdzx_wdyy") != -1 ) {
			$("#jdzx_wdyy").removeClass("hide");
		}
		if(listbuttonArrayjson.indexOf("jdzx_jrhz") != -1 ) {
			$("#jdzx_jrhz").removeClass("hide");
		}
		if(listbuttonArrayjson.indexOf("jdzx_mzyy") != -1 ) {
			$("#jdzx_mzyy").removeClass("hide");
		}
		if(listbuttonArrayjson.indexOf("jdzx_shyy") != -1 ) {
			$("#jdzx_shyy").removeClass("hide");
		}
		if(listbuttonArrayjson.indexOf("jdzx_ddjz") != -1 ) {
			$("#jdzx_ddjz").removeClass("hide");
		}
		
		if(listbuttonArrayjson.indexOf("jdzx_ddzl") != -1 ) {
			$("#jdzx_ddzl").removeClass("hide");
		}
		if(listbuttonArrayjson.indexOf("jdzx_yjz") != -1 ) {
			$("#jdzx_yjz").removeClass("hide")	;
		}
		
		for( var i = 0; i < jsonbtnList.length; i++){
			// 判断当前用户具备的按钮权限
			var index = jQuery.inArray(jsonbtnList[i].qx, listbuttonArray);
			// index = -1 时，表示当前用户没有此按钮的操作权限
			if(index==-1){//无权限的展示 先注释掉保证把没有权限的按钮不显示
				/* menubutton1+="<li name=''>";
				menubutton1+="<div class='not-allowed'>";//鼠标移入禁用效果
				menubutton1+='<img src="'+ contextPath +'/static/image/kqdsFront/img/icon/book3.png">';//无权限 浅灰色
				menubutton1+='<span >'+ jsonbtnList[i].name +'</span>' //浅灰色字体
				menubutton1+="</div>";
				menubutton1+="</li>"; */
			}else{//有权限的展示
				menubutton1+="<li name='"+ jsonbtnList[i].qx +"'>";
				menubutton1+="<div class='pointer'>";//鼠标移入小手效果
				menubutton1+='<img src="'+ contextPath +'/static/image/kqdsFront/img/icon/book2.png">';//有权限 深灰色
				menubutton1+='<span class="normal" >'+ jsonbtnList[i].name +'</span>'; //深灰色字体 鼠标移入 蓝色
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
	
	function dzcx(){
		 layer.open({
	       type: 2,
	       title: '到诊查询',
	       shadeClose: false,
	       shade: 0.6,
	       area: ['98%', '95%'],
	       content: contextPath + '/KQDS_REGAct/toDzQuery.act'
	   });
	}
</script>
</html>
