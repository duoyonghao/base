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
	String isyx = request.getParameter("isyx");// 1 营销中心进入   2网电中心  3客服
	if (isyx == null) {
		isyx = "0";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>咨询统计表</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bootstrap-table-jumpto.css"/>
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<style type="text/css">
	.a{
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
		border-bottom:1px solid #ddd;
		border-radius: 6px;
		border-top-left-radius: 6px;
		border-top-right-radius: 6px; 
		overflow: hidden;
	}
	/* 搜索框select */
.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
       width: 110px;   
      }  /* 搜索框 */
.searchSelect>.btn { 
     width: 110px; 
     height:26px; 
	 border: solid 1px #e5e5e5; 
 }  /* 下拉框 */
.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
     color: #999;
     height: 26px;
 }
.pull-left {
    float: left !important;
    color: #333;
 }
.searchWrap .text {
     position: static !important; 
     left: 0px;
     top: 9px;
     color: rgb(31, 32, 33);
 }
/*  解決标签查询中下拉框悬浮 */
.searchWrap{
   overflow: visible;
 }
.formBox{
  overflow: visible;
 }
.searchWrap .formBox>section>ul.conditions {
     overflow: visible;
     height: 30px;
     position: relative;
     display: inline-block;
}
.dropdown-menu > li > a {
    padding: 0px 20px;
    box-sizing:border-box;
    font-size: 12px;
}
.btn {
	 padding: 4px 12px;
}
input[type="text"]:focus, select:focus, textarea:focus {
    box-shadow: 0 0 8px #00A6C0; 
}
.dropdown-menu > .active > a, .dropdown-menu > .active > a:hover, .dropdown-menu > .active > a:focus {
    background-color: #00A6C0;
}
	
</style>
</head>
<body>
<div id="container">
   <!--查询条件-->
     <div class="searchWrap" style="margin-bottom: 7px;">
            <div class="cornerBox">查询条件</div>
            <div class="btnBar">
           		<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
           		<a href="javascript:void(0);" class="kqdsCommonBtn hide" onclick="exportTable()" id="sjbb_scbb">生成报表</a>
                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
            </div>
            <div class="formBox">
			<section style="border:0px;padding-bottom: 0px;">
		    		<ul class="conditions">
		    			
	    				<li class="unitsDate">
		    				<span>建档日期</span>
    						<input type="text" placeholder="开始日期" id="jdtime1" readonly>
                        </li>
	    				<li class="unitsDate">
		    				<span>到</span>
                            <input type="text"  placeholder="结束日期" id="jdtime2" readonly>
                        </li>
                        <li class="toggleTr unitsDate">
		    				<span>到诊日期</span>
    						<input type="text" placeholder="开始日期" id="dztime1" readonly>
                        </li>
	    				<li class="toggleTr unitsDate">
		    				<span>到</span>
                            <input type="text"  placeholder="结束日期" id="dztime2" readonly>
                        </li>
                        <li class="toggleTr">
	    					<div class="blue_inp hide" id="sjbb_jdr">
	    						<span style="display:inline-block;margin-left: 1em;">建档人</span>
		    					<input type="hidden" name="yewu" id="yewu" value="" title="建档人" class="form-control"/>
								<!-- <input type="text" id="yewuDesc" name="yewuDesc" style="width: 110px;" value="" onClick="findcreateUser()"  readonly></input> -->
								<select id="yewuDesc" class="searchSelect" name="yewuDesc" style="width: 110px;" value=""  readonly title="请选择" data-live-search="true">
								</select>
	    					</div>
	    				</li>
	    				 <li>
		    				<span>建档门诊</span>
	    					<select id="organization" name="organization"></select>
    					</li>
	               		
						<li class="toggleTr">
		    				<span>咨询项目</span>
    					 	<select class="dict searchSelect" tig="ZXXM" name="xiangmu" id="xiangmu" title="请选择" data-live-search="true"></select>
	    				</li> 														    				
	    			</ul>
	    			<ul class="conditions" style="margin-top: 7px;">
	    				<li class="toggleTr unitsDate">
		    				<span>预约日期</span>
    						<input type="text" placeholder="开始日期" id="yytime1" readonly>
                        </li>
	    				<li class="toggleTr unitsDate">
		    				<span>到</span>
                            <input type="text"  placeholder="结束日期" id="yytime2" readonly>
                        </li>
		            <%
					if(SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK).indexOf(SessionUtil.getLoginPerson(request).getUserPriv()) == -1) {
					%>
					
					<%}else{ %>
                        <li class="toggleTr">
	    					<span>患者来源</span>
	    					<select class="patients-source select2 dict searchSelect" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelectSearch('devchannel','nexttype', 'add');" title="请选择" data-live-search="true">
							</select>
	    				</li>

	    				<li class="toggleTr">
	    					<span>子分类</span>
		    				<select class="select2 dict searchSelect" name="nexttype" id="nexttype" style="width: 84px;" title="请选择" data-live-search="true">
								<option value="">请选择</option>
							</select>
						</li> 
                    <%} %>
    					<li class="toggleTr hidden">
	    					<div class="blue_inp" id="sjbb_sllx">
	    						<span>受理类型</span>
    					 		<select class="dict" tig="SLLX" id="shouli" style="height: 26px;width: 110px;" onchange="initDept()"></select>
	    					</div>
	    				</li>
						<%
					if(SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK).indexOf(SessionUtil.getLoginPerson(request).getUserPriv()) == -1) {
					%>

					<%}else{ %>
	    				<li class="toggleTr">
	    					<span>受理工具</span>
    						<select class="dict searchSelect" tig="SLGJ" id="gongju" title="请选择" data-live-search="true"></select>
	    				</li>
	    			<%} %>
						<li id="dep">
							<span>部门类别</span>
							<select id="deptCategory" name="deptCategory" onchange="findDeptPerson(this.value)"></select>
						</li>
						<li id="per">
							<span>人员</span>
							<select id="personid" name="personid"  data-live-search="true" title=""></select>
						</li>
	    			</ul>
	    		</section>
            </div>
           <!--  <div class="formBox">
           		 <div class="kv">
	            	<div class="kv">
							<label>所属门诊</label>
							<div class="kv-v">
								<select id="organization" name="organization"></select>
							</div>
					</div>
				</div>
               	<div class="kv">
               		<div class="kv">
						<label>时间</label>
						<div class="kv-v">
							<div class="unitsDate" style="width:300px;">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</div>
						</div>
					</div>
                </div>
            </div> -->
        </div> 
    <div class="tableBox" id="divkdxm" style="overflow: hidden;border-radius:8px;border-top-left-radius: 6px;border-top-right-radius: 6px;border-left: 1px solid #ddd;border-right: 1px solid #ddd;border-bottom:1px solid #ddd;">
        <table id="dykdxm" class="table-striped table-condensed table-bordered" data-height="450"></table>
    </div>
    <div id="buttonBar"> 
        <!-- <table style="width:95%;text-align: left;"> 
    	</table>  -->
   </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table-jumpto.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var personPriv = "<%=person.getSeqId()%>";
var personUsername = "<%=person.getUserName()%>";
var userPriv = "<%=person.getUserPriv()%>";
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var pageurl = '<%=contextPath%>/KQDS_ScbbAct/getWdOrdertj.act';
var nowday;
var menuid = "<%=menuid%>";
var qxnameArr = ['yytj_scbb'];
var func = ['exportTable'];
var isyx = "<%=isyx%>";
var static_organization = '<%=ChainUtil.getCurrentOrganization(request)%>';
$(function() {
	$("input[type='text']").attr("autocomplete","off");  //去掉input输入框的历史记录  lutian
	// 连锁门诊下拉框
    initHosSelectListNoCheckWithNull('organization', static_organization);
    // 咨询项目、受理类型、受理工具
    initDictSelectByClass('triggerChange');

	initDept();

	OrderDetail();

    //获取当前页面所有按钮
    //getButtonPowerByPriv(qxnameArr, func, menuid);
    getButtonAllCurPage(menuid);
    //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });
    nowday = getNowFormatDate();
  	/* $("#jdtime1").val(nowday);
 	$("#jdtime2").val(nowday);
    $("#jdtime1,#jdtime2").change(function() {
        timeCompartAndFz("jdtime1", "jdtime2");
    }); */
    $('.searchSelect').selectpicker("refresh");//搜索初始化刷新2019.10.31--lutian
});

//初始化部门
function initDept(){
	$.ajax({
		url:contextPath+"/KQDS_ScbbAct/getOrdertjDept.act",    //请求的url地址
		data:{isyx:isyx,shouli:$('#shouli').val()},
		dataType:"json",   //返回格式为json
		type:"post",   //请求方式
		async:false,
		success:function(data){
			//console.log(JSON.stringify(data));
			if (data.length>0){
				$("#deptCategory").html("");
				for (var i = 0; i < data.length; i++) {
					$("#deptCategory").append("<option value ="+data[i].seqId+">"+data[i].deptName+"</option>")
				}
				findDeptPerson(data[0].seqId);
			}
		}
	});
}

//查询部门人员
function findDeptPerson(seqId){
    $.ajax({
        url:contextPath+"/YZPersonAct/findVisualPersonnel.act",    //请求的url地址
        data:{deptId:seqId},
        dataType:"json",   //返回格式为json
        type:"post",   //请求方式
        success:function(data){
            //请求成功时处理
            $("#personid").html("");
            $("#personid").html("<option value=''>全部员工</option>");
            for (var i = 0; i < data.length; i++) {
                $("#personid").append("<option personname='"+data[i].userName+"' value ="+data[i].seqId+">"+data[i].userName+"</option>")
            }
            $('.searchSelect').selectpicker("refresh");//初始化刷新--2019-10-29 licc
        }
    });
}
function queryParams(params) {
    var temp = {
                organization: $('#organization').val(),
        	    jdtime1: $('#jdtime1').val(),//建档时间开始
        	    jdtime2: $('#jdtime2').val(),//建档时间结束
        	    xiangmu: $('#xiangmu').val(),//咨询项目
        	    yytime1: $('#yytime1').val(),//预约时间开始
        	    yytime2: $('#yytime2').val(),//预约时间结束
        	    devchannel: $('#devchannel').val(),//来源渠道
        	    nexttype: $('#nexttype').val(),//子分类
        	    shouli: $('#shouli').val(),//受理类型
        	    gongju: $('#gongju').val(),//受理工具
        	    yewu: $('#yewuDesc').val(),//建档人
        		yewuname: $('#yewuDesc').find("option:selected").attr("yewuname"),//建档人名称
        	    dztime1: $('#dztime1').val(),//到诊时间开始
        	    dztime2: $('#dztime2').val(),//到诊时间结束
        		deptid:$('#deptCategory').val(),//部门id
				personid:$('#personid').val(),//人员id
        		personname:$('#personid').find("option:selected").attr("personname")//人员名称
    };
    if (temp.deptid==null&&temp.personid==null){
        $("#per").hide();
        $("#dep").hide();
        temp.personid=personPriv;
        temp.personname=personUsername;
	}
    temp.isyx = isyx; // 1 是营销  2是网电 3客服
    return temp;
}
$('#query').on('click',
function() {
	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(this).text("查询中");
    $('#dykdxm').bootstrapTable('refresh', {
        'url': pageurl
    });
});
$('#clean').on('click',
function() {
    var rgvalue = '<%=ChainUtil.getCurrentOrganization(request)%>';
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val(rgvalue);
});
//导出
function exportTable() {
    var fieldArr = [];
    var fieldnameArr = [];
    $('#dykdxm thead tr th').each(function() {
        var field = $(this).attr("data-field");
        if (field != "") {
            fieldArr.push(field); //获取字段
            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
        }
    });
    var param = JsontoUrldata(queryParams());
    location.href = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
}
function OrderDetail() {
    $("#dykdxm").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        cache: false,
        striped: true,
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
        mergeCells: {
            index: 4,
            field: 'xh',
            colspan: 0,
            rowspan: 3
        },
        paginationShowPageGo: true,
        onLoadSuccess: function(data) { //加载成功时执行
			//解除查询按钮禁用 lutian
            //console.log(JSON.stringify(data)+"-=-=-=-=-=-=-=-=-=-");
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
				$("#query").text("查询");
			}
        	//console.log("网电统计=="+JSON.stringify(data));
            var tableList = data;
            var hj = {};
            var ldxj = 0,
            yyxj = 0,
            yyl = 0,
            dyrsxj = 0,
            dzl = 0,
            skjexj = 0,
            cjrsxj = 0,
            cjl = 0,
            rjxf = 0;
            xmjexj=0;
            ysjexj=0;
            for (var i = 0; i < tableList.length; i++) {
                ldxj += Number(tableList[i].ldnums);
                yyxj += Number(tableList[i].yynums);
                dyrsxj += Number(tableList[i].yysmnums);
                skjexj += Number(tableList[i].skje);
                cjrsxj += Number(tableList[i].cjnums);
                xmjexj += Number(tableList[i].xmje);
                ysjexj += Number(tableList[i].ysje);
            }
            hj.username = "合计";
            hj.ldnums = ldxj;
            hj.yynums = yyxj;
            hj.yysmnums = dyrsxj;
            hj.skje = skjexj.toFixed(2);
            hj.cjnums = cjrsxj.toFixed(2);
            hj.xmje=xmjexj.toFixed(2);
            hj.ysje=ysjexj.toFixed(2);
            //预约率 = 预约人数/录单量
            if (Number(ldxj) == 0) {
                hj.yyl = "0.00%";
            } else {
                hj.yyl = Number(yyxj * 100 / ldxj).toFixed(2) + "%";
            }
            //到诊率 = 到院人数/录单量
            if (Number(ldxj) == 0) {
                hj.dzl = "0.00%";
            } else {
                hj.dzl = Number(dyrsxj * 100 / ldxj).toFixed(2) + "%";
            }
            //成交率 = 成交人数/到院人数
            if (Number(dyrsxj) == 0) {
                hj.cjl = "0.00%";
            } else {
                hj.cjl = Number(cjrsxj * 100 / dyrsxj).toFixed(2) + "%";
            }
            //人均消费 = 收款金额/到院人数
            if (Number(dyrsxj) == 0) {
                hj.rjxf = "0.00";
            } else {
                hj.rjxf = Number(skjexj / dyrsxj).toFixed(2);
            }
            data.push(hj);
            //tableList.push(hj);
            //$("#dykdxm").bootstrapTable("load", tableList);
            $("#dykdxm").bootstrapTable("load", data);
            setHeight();
        },
        columns: [
		{
		    title : '序号',
		    align: "center",
		    width: 40,
		    formatter: function (value, row, index) {
		     return index + 1; 
		     
		     /* var pageSize = $('#dykdxm').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
		        var pageNumber = $('#dykdxm').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
		        console.log(pageSize+"---"+pageNumber+"---"+index);
		        return pageSize * (pageNumber - 1) + index + 1;  */   // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
		    }
		   },       
        {
            title: '网电咨询',
            field: 'username',
            align: 'center',

            formatter: function(value, row, index) {
                return '<span class="">' + value + '</span>';
            }
        },
        {
            title: '录单量',
            field: 'ldnums',
            align: 'center',

            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '预约人数',
            field: 'yynums',
            align: 'center',
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '预约率',
            field: 'yyl',
            align: 'center',
            visible: false,
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '到院人数',
            field: 'yysmnums',
            align: 'center',
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '到诊率',
            field: 'dzl',
            align: 'center',
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '项目金额',
            field: 'xmje',
            align: 'center',
            formatter: function(value) {
                return '<span>' + Number(value).toFixed(2) + '</span>';
            }
        },
        {
            title: '预收金额',
            field: 'ysje',
            align: 'center',
            formatter: function(value) {
                return '<span>' + Number(value).toFixed(2) + '</span>';
            }
        },
        {
            title: '总计(项目+预收)',
            field: 'skje',
            align: 'center',
            formatter: function(value) {
                return '<span>' + Number(value).toFixed(2) + '</span>';
            }
        },
        {
            title: '成交人数',
            field: 'cjnums',
            align: 'center',
            visible: false,
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '成交率',
            field: 'cjl',
            align: 'center',
            visible: false,
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        {
            title: '人均消费',
            field: 'rjxf',
            align: 'center',
            formatter: function(value) {
                return '<span>' + value + '</span>';
            }
        },
        ]
    }).on('click-row.bs.table',
    	function(e, row, element) {
       		$('.success').removeClass('success'); //去除之前选中的行的，选中样式
        	$(element).addClass('success'); //添加当前选中的 success样式用于区别
    });
}

/**
 *  设置按钮权限操作 
 */
function getButtonPower(){
	var menubutton1 = "";
	// 创建一个数组，存放listbutton的qxName 
	var listbuttonArray = new Array();
	for ( var i = 0; i < listbutton.length; i++) {
		listbuttonArray[i] = listbutton[i].qxName;
        if (listbutton[i].qxName=="sjbb_jdr"){
            $("#per").hide();
            findcreateUser();
        }
	}
	/* 按钮 */
	var btnList =  '[';
		btnList	+= '{"qx":"sjbb_sllx","name":"受理类型"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"sjbb_jdr","name":"建档人"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"sjbb_scbb","name":"生成报表"}'; // 最后一个不要逗号
	    btnList	+= ']';
	    var jsonbtnList = jQuery.parseJSON( btnList );
		for( var i = 0; i < jsonbtnList.length; i++){
			// 判断当前用户具备的按钮权限
			var index = jQuery.inArray(jsonbtnList[i].qx, listbuttonArray);
			// index = -1 时，表示当前用户没有此按钮的操作权限
			if( index == -1 ){//无权限的展示
			} else {//有权限的展示
				$("#"+jsonbtnList[i].qx).removeClass("hide");
			}

		}

}

function findcreateUser(){
	$.ajax({
	    url:contextPath+"/KQDS_ScbbAct/findCreateUser.act",    //请求的url地址
	    data:{
	    isyx:isyx,
	    shouli: $('#shouli').val()
	    },
	    dataType:"json",   //返回格式为json
	    type:"post",   //请求方式
	    success:function(data){
	        //请求成功时处理
	        //console.log(data);
	        $("#yewuDesc").html("<option value=''>请选择</option>");
	       // $("#yewuDesc").append("<option value=''>请选择</option>");
	        for (var i = 0; i < data.length; i++) {
	        $("#yewuDesc").append("<option yewuname='"+data[i].userName+"' value ="+data[i].seqId+">"+data[i].userName+"</option>")
			}
	        $("#yewuDesc").selectpicker("refresh");
	    }
	});
}

function setHeight() {
    $("#dykdxm").bootstrapTable("resetView", {
        //height: $(window).outerHeight() - 80
    	height: $(window).outerHeight() - 137
    });
}
</script>
</html>
