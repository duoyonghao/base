<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>报表-综合查询</title>
   
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reception_center.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yiliao/bingli_search.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<style>
.kqds_table td input[type=text]{
    padding: 0 5px;
    width: 100px;
    height: 28px;
    line-height: 28px;
    border: solid 1px #e5e5e5;
    border-radius: 3px;
    -webkit-transition: all .3s;
    transition: all .3s;
}
.kqds_table td select{ 
	height: 28px;
	width:100px;
	border: solid 1px #e5e5e5;
	border-radius: 3px;
}
@media screen and (max-width:1252px){
	.kqds_table td {
		font-size:12px;
	}
	.kqds_table td select,.kqds_table td input[type=text]{
		font-size:12px;width:94px;
	}
}
@media screen and (max-width:1100px){
	.centerWrap .columnWrap .columnBd ul li{
		margin-left:20px;
	}
	.lookInfoWrap .columnWrap .menu li{
		width:60px;
	}
	.kqds_table td {
		font-size:11px;
	}
	.kqds_table td select,.kqds_table td input[type=text]{
		font-size:11px;width:74px;
	}
}

/*查询条件中的样式  */
.searchWrap .formBox>section>ul.conditions>li{
	padding: 3px 0;
}
.searchWrap .formBox>section>ul.conditions>li>span{
	width:62px;
	text-align:right;
}
.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
.searchWrap .formBox>section>ul.conditions>li>select{
	width:94px;
}
@media screen and (max-width:1252px){
	.searchWrap .formBox>section>ul.conditions>li>span{
		width:60px;
		text-align:right;
		font-size:12px;
		height:24px;
		line-height:24px;
	}
	.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
	.searchWrap .formBox>section>ul.conditions>li>select{
		width:82px;
		font-size:12px;
		padding:0 3px 0 5px;
		height:24px;
		line-height:24px;
	}
}
@media screen and (max-width:1100px){
	.searchWrap .formBox>section>ul.conditions>li>span{
		width:51px;
		text-align:right;
		font-size:10px;
		height:22px;
		line-height:22px;
	}
	.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
	.searchWrap .formBox>section>ul.conditions>li>select{
		width:73px;
		font-size:10px;
		padding:0 3px 0 5px;
		height:22px;
		line-height:22px;
	}
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

/* 搜索框select */
.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
       width: 94px;   
    }  
.searchSelect>.btn { 
	    width: 94px; 
	 	height:26px;
	 	border: solid 1px #e5e5e5; 
	}
.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
	    color: #999;
	    height: 26px;
	}
.pull-left {
	    float: left !important;
	    color: #333;
	    margin-top: -2px;
	    margin-left: -4px;
	}
.btn-group > .btn:first-child:hover {
	    background: white;
	}
.searchWrap .text {
	    position: static !important; 
	    left: 0px;
	    top: 9px;
	    color: rgb(31, 32, 33);
	}
.searchWrap{
		 overflow: visible;
	}
.formBox{
		overflow: visible;
	}
.searchWrap .formBox>section {
		 height: 100px;
	}
.searchWrap .formBox>section>ul.conditions {
	    overflow: visible;
	    height: 100%;
	    position: relative;
	}
</style>
</head>
<body>
<div id="container" style="padding:0 0 0 20px;">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
	            <div class="columnHd">
				    <span class="title">综合查询</span>
				</div>
               
                <div class="columnBd">
                    <div class="tableBox">
                        <table id="table" class="table-striped table-condensed table-bordered" data-height="450"></table>
                    </div>
                    <div id="gongzuol">
		                <div class="columnBd">
		               		<ul class="dataCountUl" id="dataCount">
			               		<li>小计:<span id="xiaoji">0</span></li>
			               		<li>免除:<span id="mian">0</span></li>
			               		<li>应收:<span id="ying">0</span></li>
			               		<li>欠款:<span id="qian">0</span></li>
			               		<li>收款:<span id="xian">0</span></li>
			               		<li>预交金使用:<span id="yjjshiyong">0</span></li>
			               		<li>预交金充值:<span id="yjjchongzhi">0</span></li>
			               		<li>预交金退费:<span id="yjjtuifei">0</span></li>
			               	</ul>
		                </div>
		            </div>
                </div>
            </div>
            
              <div class="searchWrap">
	                <!-- <div class="cornerBox">查询条件</div> -->
	                <div class="searchToggleBtnGroup">
	                	<span class="ptcx checked">
	                		通用查询
	                	</span>
	                	<span>
	                		高级查询
	                	</span>
	                </div>
	                <div class="formBox">
	                	<section>
				    		<ul class="conditions">
				    			<li>
				    				<span>所属门诊</span>
									<select id="organization" name="organization"></select>
								</li>
				    			<li>
				    				<span>收费时间</span>
		                            <input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly class="birthDate">
				                </li>
				    			<li>
				    				<span>到</span>
		                            <input type="text" id="endtime" name="endtime" placeholder="结束日期" readonly class="birthDate">
				                </li>     
								<li>
				    				<span>模糊查询</span>
				    				<input type="text" placeholder="患者编号/姓名/手机号" id="queryinput" name="queryinput" >
				    			</li>
				    			<li class="toggleTr">
				    				<span>接诊咨询</span>
			    					<input type="hidden" name="askperson" id="askperson"  class="form-control"  value=""/>
									<input type="text"  id="askpersonDesc" name="askpersonDesc" placeholder="咨询" readonly  onClick="javascript:single_select_user(['askperson', 'askpersonDesc'],'',1);">	
				    			</li>
				    			<li class="toggleTr">
				    				<span>就诊科室</span>
			    					<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1 %>" name="regdept" id="regdept" ></select>
				    			</li>
				    			<li class="toggleTr">
				    				<span>医生</span>
			    					<input type="hidden" name="doctor" id="doctor"  class="form-control"  value=""/>
								    <input type="text"  id="doctorDesc" name="doctorDesc" placeholder="医生" readonly  onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'',1);">	
				    			</li>
				    			<li class="toggleTr">
				    				<span>护士</span>
				    				<input type="hidden" name="nurse" id="nurse"  class="form-control"  value=""/>
									<input  type="text"  id="nurseDesc" name="nurseDesc" placeholder="护士" readonly  onClick="javascript:single_select_user(['nurse', 'nurseDesc'],'',1);"></input>	
				    			</li>
						    <%
							if(SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK).indexOf(SessionUtil.getLoginPerson(request).getUserPriv()) == -1) {
							%>
							
							<%}else{ %>
				    			<li class="toggleTr">
			    					<span>患者来源</span>
			    					<select id="devchannel" name="devchannel" tig="hzly" class="patients-source select2 dict searchSelect"  data-live-search="true" title="请选择" onchange="getSubDictSelectSearch('devchannel','nexttype');"></select>
<!-- 				    				<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype');"> -->
									</select>
				    			</li>
				    			<li class="toggleTr">
			    					<span>子分类</span>			    					
									<select id="nexttype" name="nexttype" tig="" class="select2 dict searchSelect"  data-live-search="true" title="请选择"></select>
<!-- 				    				<select class="select2 dict" name="nexttype" id="nexttype"> -->
<!-- 										<option value="">请选择</option> -->
<!-- 									</select> -->
				    			</li>
				             <%} %>  			
				    			<li class="toggleTr">
			    					<span>就诊分类</span>
				    				<select class=" dict" tig="jzfl" name="recesort" id="recesort"  data-bv-notempty data-bv-notempty-message="就诊分类不能为空">
			                		</select>
				    			</li>
				    			<li class="toggleTr">
			    					<span>挂号分类</span>
				    				<select class=" dict" tig="ghfl" name="regsort" id="regsort">
			                		</select>
				    			</li>
				    			<li class="toggleTr">
			    					<span>建档人</span>
			    					<input type="hidden" name="createuser" id="createuser"  class="form-control"  value=""/>
								    <input  type="text"  id="createuserDesc" name="createuserDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['createuser', 'createuserDesc'],'',1);"></input>	
				    			</li>
				    			<li class="toggleTr">
			    					<span>收款备注</span>
				    				<input type="text" placeholder="收款备注" id="remark" name="remark" >
				    			</li>	
				    			<li class="toggleTr">
			    					<span>成交情况</span>
			    					<select  name="cjStatus" id="cjStatus" >
			                          <option value="">请选择</option>
			                          <option value="1">已成交</option>
			                          <option value="0">未成交</option>
									</select>
				    			</li>
			    			</ul>
			    		</section>
	                	<div class="btnBar" style="text-align: left;">
				    	 	 <a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
			                 <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			            </div>
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
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/bbzx/kqds_makeInvoice.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script> --%>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript">
var listbutton;
var contextPath = "<%=contextPath%>";
var pageurl = '<%=contextPath%>/KQDS_CostOrderAct/getAll.act'; // 进行门诊条件过滤
var nowday;
var onclickrowOobj = "";
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
var menuid = "<%=menuid%>";
var qxnameArr = ['bbzx_zhcx_scbb','bbzx_zhcx_kaipiao'];
var func = ['exportTable','kaipiao'];

//登录权限licc--2020-1-8
var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
var allowed=allowedperson.split(","); //允许权限

//判断当前人员权限是否有查看患者来源和子分类等着资源
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


$(function() {
	initHosSelectListNoCheck('organization');// 连锁门诊下拉框
	//initHosSelectList4Front('organization');// 连锁门诊下拉框	
	var tmpOrganization = $("#organization").val();
    initDeptSelectByTypesAndClass(tmpOrganization);
    $("#organization").change(function() {
        var currSelect = $(this).val();
        initDeptSelectByTypesAndClass(currSelect);
    });
    //当前日期
    nowday = getNowFormatDate();

    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    initDictSelectByClass(); // 患者来源、就诊分类、挂号分类
    //获取当前页面所有按钮
    getButtonAll(menuid);
    getButtonPowerByPriv(qxnameArr,func,menuid);
    /* 左侧个人中心的按钮样式控制js已经被抽取到rightPartInfo.jsp页面中 */

    //时间选择
    $(".birthDate").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "top-right"
    });
    togglemodel.initial('bb_zhcx',pageurl);/*wl 初始化 开关模块 */
    //4、表格初始化
    getPayOrderlist(pageurl);
    //计算主体的宽度
    
    setHeight();
    
    $(window).resize(function() {
        setWidth();
        setHeight();
        /* 滚动条出现时 调整表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
    /* 常用查询 按钮 高级查询  按钮*/
    initSearchToggleBtnGroup();
    $('.searchSelect').selectpicker("refresh");//初始化刷新--2019-10-29 licc
});
//带参数刷新表格11
function refreshTable(){
	$('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
//计算界面宽高的设置
//setWidth() ,setHeight()函数移动到tableHeaderWidth.js

//已结账
function getPayOrderlist(pageurl) {
	
    //初始化表格所在div
    
    /* 根据当前页面 计算出table需要的高度 */
	
	var tableHeight=0;/* 计算table需要的高度 */
	
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParamsB,
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
        paginationShowPageGo: true,
        singleSelect: true,
        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
        paginationShowPageGo: true,
        
        onLoadSuccess: function(data) { //加载成功时执行
//         	隐藏患者来源子分类
			var existornot=isExist(total);//资源隐藏判断条件ZY_LYCK
        	if(!existornot){
        		$('#table').bootstrapTable('hideColumn', 'devchannel');
        		$('#table').bootstrapTable('hideColumn', 'nexttype');
        	}else{
        	}  
        	 if(nowpage == 0 && data.total>0){
       			maxpage = Math.floor(data.total/pagesize)+1; 
       		 	$("#xiaoji").html(data.totalcost);//小计:
                $("#mian").html(data.voidmoney);//免除:
                $("#ying").html(data.shouldmoney);//应收:
                $("#xian").html(data.actualmoney);//缴费:
                $("#qian").html(data.y2); //欠款:
                $("#yjjshiyong").html(data.yjjshiyong); //预交金使用:
                $("#yjjchongzhi").html(data.yjjchongzhi); //预交金充值:
                $("#yjjtuifei").html(data.cmoney); //预交金退费:
                setHeight();
                /* 滚动条出现时 调整表头的宽度 */
                setTableHeaderWidth();
       		 }
        	 if(data.total == 0){
        		 $("#xiaoji").html("0");
                 $("#mian").html("0");
                 $("#ying").html("0");
                 $("#xian").html("0");
                 $("#qian").html("0"); 
                 $("#yjjshiyong").html("0"); 
                 $("#yjjchongzhi").html("0");
                 $("#yjjtuifei").html("0");
        	 }
         	//分页加载
         	/* showdata("table",data.rows); */
         	//计算主体的宽度
             setWidth();
             setHeight();
         	/*表格载入时，设置表头的宽度 */
             setTableHeaderWidth(".tableBox");
        },
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (Number(row.actualmoney) < 0 || row.actualmoney.indexOf("-") >= 0) {
                strclass = 'danger'; //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        columns: [
		{
            field: ' ',
            checkbox: true,
        },{
		    title: '序号',
		    field: 'Number',
		    align: 'center',
            formatter: function (value, row, index) {
                /* return index + 1; */
                var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                if(row.voidmoney=='' || row.voidmoney==null){
  		    		 return '<span>'+'Y_'+(pageSize * (pageNumber - 1) + index + 1)+'</span>';
  		    	}else{
  		    		 return '<span>'+(pageSize * (pageNumber - 1) + index + 1)+'</span>';
  		    	}
            }
		},
        {
            title: '消费门诊',
            field: 'organizationname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },{
            title: '收费时间',
            field: 'sftime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value.indexOf("null") > -1) {
                    return "";
                } else {
                    var sftime = value.substring(0, 16);
                    return '<span class="time">' + sftime + '</span>';
                }
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="time" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '病历号',
            field: 'blcode',
            align: 'center',
            valign: 'middle',
            sortable: true,
            visible: false,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '电话',
            field: 'phonenumber1',
            align: 'center',
            formatter: function(value, row, index) {
                if (canlookphone == 0) {
                    return '<span class="time phone" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span>-</span>';
                }

            }
        },
        {
            title: '小计',
            field: 'totalcost',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '免除',
            field: 'voidmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if(value){
	                return '<span class="money">' + value + '</span>';            		
            	}else{
            		return '';
            	}
            }
        },
        {
            title: '应收',
            field: 'shouldmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '欠费',
            field: 'y2',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if(value){
	                return '<span class="money">' + value + '</span>';            		
            	}else{
            		return '';
            	}
            }
        },
        {
            title: '缴费',
            field: 'actualmoney',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '就诊分类',
            field: 'recesort',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '挂号分类',
            field: 'regsort',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '成交情况',
            field: 'cjstatus',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "已成交") {
                    return '<span class="label label-success">已成交</span>';
                } else {
                    return '<span class="label label-danger">未成交</span>';
                }
            }
        },
        {
            title: '第一咨询',
            field: 'faskperson',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '接诊咨询',
            field: 'askperson',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '就诊科室',
            field: 'regdept',
            align: 'center',
		    formatter:function(value){
		    	if(value){
			    	return '<span>'+value+'</span>'
		    	}else{
		    		return '';
		    	}
		    }
        },
        {
            title: '医生',
            field: 'doctorname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '护士1',
            field: 'nurse',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '护士2',
            field: 'techperson',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '患者来源',
            field: 'devchannel',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '来源子分类',
            field: 'nexttype',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '开单人',
            field: 'createuser',
            align: 'center',
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '开单时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                html = '<span>' + value.substring(0) + '</span>';
                return html;
            }
        },
        {
            title: '介绍人',
            field: 'introducer',
            align: 'center',
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '开发人',
            field: 'developer',
            align: 'center',
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '建档人',
            field: 'jduser',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '建档导医',
            field: 'jddy',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '建档时间',
            field: 'jdtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    html = '<span>' + value + '</span>';
                    return html;
                }
            }
        },
        {
            title: '收费人',
            field: 'sfuser',
            align: 'center',
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return html;
                } else {
                    return "";
                }
            }
        },
        {
            title: '收款备注',
            field: 'remark',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    html = '<span class="remark">' + value + '</span>';
                    return html;
                }else{
                	return "<span>-</span>";
                }
            }
        },
        {
            title: '备注',
            field: 'costlevel',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	
                if (value == "1") {
                    return "K";
                }else{
                	return "<span>-</span>";
                }
            }
        }

        ]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        var tab = $("#rightmenu").children("ul").children("li").eq(4);
        tab.attr({
            "class": "current"
        });
        var src = contextPath + "/KQDS_CostOrder_DetailAct/toCostDetail2.act?costno=" + onclickrowOobj.costno;
        tab.attr('src', src);
        tab.trigger("click");
    });
}
function queryParams() {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的	
    	organization: $('#organization').val(),
        cjStatus: $('#cjStatus').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        askperson: $('#askperson').val(),
        isyjjitem:'',
        //咨询
        regdept:$('#regdept').val(),
        doctor: $('#doctor').val(),
        //医生
        createuser: $('#createuser').val(),
        nurse:$('#nurse').val(),//护士1
        //建档人
        devchannel: $('#devchannel').val(),
        //患者来源
        nexttype: $('#nexttype').val(),
        organization : $("#organization").val(),
        //子分类
        recesort: $('#recesort').val(),
        //就诊分类
        regsort: $('#regsort').val(),
        //挂号分类
        remark : $('#remark').val(),
        queryinput: $('#queryinput').val()
    };
    if(nowday!=null){
    	temp.starttime = nowday;
    	temp.endtime = nowday;
    }
    return temp;
}

function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
    	organization: $('#organization').val(),
        cjStatus: $('#cjStatus').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        askperson: $('#askperson').val(),
        isyjjitem:'',
        //咨询
        regdept:$('#regdept').val(),
        doctor: $('#doctor').val(),
        //医生
        createuser: $('#createuser').val(),
        nurse:$('#nurse').val(),//护士1
        //建档人
        devchannel: $('#devchannel').val(),
        //患者来源
        nexttype: $('#nexttype').val(),
        organization : $("#organization").val(),
        //子分类
        recesort: $('#recesort').val(),
        //就诊分类
        regsort: $('#regsort').val(),
        //挂号分类
        remark : $('#remark').val(),
        queryinput: $('#queryinput').val(),
        sortName:this.sortName,
    	sortOrder:this.sortOrder
    };
    if(nowday != null){
    	temp.starttime = nowday;
    	temp.endtime = nowday;
    }
    return temp;
}


$('#query').on('click',
function() {
	loadedData = [];
	nowpage = 0;	
	nowday = null;
    var askperson = $('#askperson').val();
    var regdept = $('#regdept').val();
    var doctor = $('#doctor').val();
    var createuser = $('#createuser').val();
    var devchannel = $('#devchannel').val();
    var nexttype = $('#nexttype').val();
    var recesort = $('#recesort').val();
    var regsort = $('#regsort').val();
    var cjStatus = $('#cjStatus').val();
    var starttime = $('#starttime').val();
    var endtime = $('#endtime').val();
    var remark = $('#remark').val();
    var queryinput = $('#queryinput').val();
    if (regdept == "" && cjStatus == "" && starttime == "" && endtime == "" && queryinput == "" && askperson == "" 
    		&& doctor == "" && createuser == "" && devchannel == "" && nexttype == "" && recesort == "" && regsort == "" && remark == "") {
        layer.alert('请选择查询条件!' );
        return false;
    }
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");

	$(".searchSelect li.selected").empty();//清空
	$('.searchSelect').selectpicker("refresh");//初始化刷新--2019.10.29--licc
});
//导出
function exportTable() {
    var fieldArr = [];
    var fieldnameArr = [];
    $('#table thead tr th').each(function() {
        var field = $(this).attr("data-field");
        if (field != "") {
            fieldArr.push(field); //获取字段
            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
        }
    });
    var param = JsontoUrldata(queryParams());
    location.href = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
}
//获取选中行的usercode
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}
function kaipiao(){
	var selectedrows = getIdSelections();
    if (selectedrows.length == 0) {
        layer.alert('请勾选复选框，选择需要开票的患者' );
        return false;
    } 
    var usercodes = "";
    for(var i=0;i<selectedrows.length;i++){
    	usercodes += selectedrows[i].usercode+",";
    }
    if(usercodes.indexOf(",")>=0){
    	usercodes = usercodes.substring(0,usercodes.length-1);
    }
	makeInvoice(usercodes);
}
</script>
</html>
