<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
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
<title>咨询项目统计表</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-3d.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/exporting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<!-- jquery 导出excel -->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.table2excel.js"></script>
<style>
	.showTable> tbody > tr>td{
		padding: 0 5px;
	    font-size: 12px;
	    height: 24px;
	    line-height:24px;
	    vertical-align:bottom;
	    border:none;
	    box-sizing:border-box;
	}
	.showTable> thead > tr{
		background:#00A6C0;
		color:#fff;
	}
	.showTable> thead > tr>th{
	    font-size: 12px;
	    height: 28px;
	    vertical-align:bottom;
	    border:none;
	}
	.showTable> tbody > tr>th>span{
	    display: inline-block;
	    white-space: nowrap;
	    overflow: hidden;
	    text-overflow: ellipsis;
	}
	.showTable> thead > tr>th>span{
		display: inline-block;
	    white-space: nowrap;
	    overflow: hidden;
	    text-overflow: ellipsis;
	}
	#table tr:hover{
		background-color: #a9ecc7;
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
	 display: inline-block;
     overflow: visible;
     height: 30px;
     position: relative;
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
    					<li class="toggleTr">
	    					<div class="blue_inp hide" id="sjbb_sllx">
	    						<span>受理类型</span>
    					 		<select class="dict" tig="SLLX" id="shouli" style="height: 26px;width: 110px;" onchange="findcreateUser()"></select>
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
      <!-- jquery 生成excel table-responsive table2excel -->
    <div class="table-responsive table2excel tableBox"  style="overflow: auto;">
     	 <table class="tableBody showTable table-striped table-condensed table-bordered" style="width: 100%">
     	 		<thead>
	     	 		<tr>
				 		<th  style="text-align: center" ><span>咨询项目</span></th>
				 		<th  style="text-align: center" ><span>咨询人次</span></th>
				 		<th  style="text-align: center" ><span>咨询人次占比(%)</span></th>
				 		<th  style="text-align: center" ><span>预约人次</span></th>
				 		<th  style="text-align: center" ><span>到诊人次</span></th>
				 		<th  style="text-align: center" ><span>预约成功率(%)</span></th>
				 		<!-- <th  style="text-align: center" ><span>成交人次</span></th>
				 		<th  style="text-align: center" ><span>到诊成交率(%)</span></th>
				 		<th  style="text-align: center" ><span>成交人次占比</span></th> -->
			 		</tr>
		 		</thead>
		 		<tbody  id="table" >
		 		
		 		</tbody>
         </table>
         <div id="containerhh" style="width:100%" ></div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var pageurl = contextPath + '/KQDS_ScbbAct/getWdOrderItemtj.act';
var nowday;
var menuid = "<%=menuid%>";
var qxnameArr = ['yytj_scbb'];
var func = ['exportTable'];
var isyx = "<%=isyx%>";
var static_organization = '<%=ChainUtil.getCurrentOrganization(request)%>';
$(function() {
    initHosSelectListNoCheckWithNull('organization', static_organization);
 	// 咨询项目、受理类型、受理工具
    initDictSelectByClass('triggerChange');
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
    /* $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    }); */
    gettabledata(); //表格
    $('.searchSelect').selectpicker("refresh");//搜索初始化刷新2019.10.31--lutian
});
$("#query").click(function() {
    gettabledata(); //表格
});
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
        	    dztime1: $('#dztime1').val(),//到诊时间开始
        	    dztime2: $('#dztime2').val(),//到诊时间结束
    };
    
    temp.isyx = isyx; // 1 是营销  2是网电 3客服
    return temp;
}
function exportTable() {
    $(".table2excel .tableBody").table2excel({
        exclude: ".noExl",
        //带noExlclass的行不会被输出到excel中
        name: "Excel Document Name",
        filename: "咨询项目统计表",
        exclude_img: true,
        exclude_links: true,
        exclude_inputs: true
    });
}
function gettabledata() {
	var param = JsontoUrldata(queryParams());
    var url = pageurl + "?"+param;
    $.axse(url, null,
    function(data) {
        data = data.rows;
        if (data != null) {
            if (data.length > 0) {
                $('#table').html("");
                var content = "";
                for (var i = 0; i < data.length; i++) {
                    if (data[i].zxxm) {
                        content += '<tr>';
                        content += '<td style="text-align: center"><span>' + data[i].zxxm + '</span></td>';
                        content += '<td style="text-align: center"><span>' + data[i].ldnums + '</span></td>';
                        content += '<td style="text-align: center"><span>' + data[i].zzzb + '</span></td>';

                        content += '<td style="text-align: center"><span>' + data[i].yynums + '</span></td>';
                        content += '<td style="text-align: center"><span>' + data[i].yysmnums + '</span></td>';
                        content += '<td style="text-align: center"><span>' + data[i].dzl + '</span></td>';
                       /*  content += '<td style="text-align: center"><span>' + data[i].cjnums + '</span></td>';
                        content += '<td style="text-align: center"><span>' + data[i].cjl + '</span></td>';

                        content += '<td style="text-align: center"><span>' + data[i].cjlzb + '</span></td>'; */
                        content += '</tr>';
                    } else {
                        content += '<tr>';
                        content += '<td style="text-align: center"><span>合计</span></td>';
                        content += '<td style="text-align: center"><span>' + data[i].ldnums + '</span></td>';
                        content += '<td style="text-align: center"><span>' + data[i].zzzb + '</span></td>';

                        content += '<td style="text-align: center"><span>' + data[i].yynums + '</span></td>';
                        content += '<td style="text-align: center"><span>' + data[i].yysmnums + '</span></td>';
                        content += '<td style="text-align: center"><span>' + data[i].dzl + '</span></td>';
                        /* content += '<td style="text-align: center"><span>' + data[i].cjnums + '</span></td>';
                        content += '<td style="text-align: center"><span>' + data[i].cjl + '</span></td>';

                        content += '<td style="text-align: center"><span>' + data[i].cjlzb + '</span></td>'; */
                        content += '</tr>';
                    }

                }
                $('#table').append(content);
            }
        }
    },
    function() {
        layer.alert('查询统计失败！');
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
	        	$("#yewuDesc").append("<option value ="+data[i].seqId+">"+data[i].userName+"</option>")
			}
	        $("#yewuDesc").selectpicker("refresh");
	    }
	});
}
$('#clean').on('click',
		function() {
		    var rgvalue = '<%=ChainUtil.getCurrentOrganization(request)%>';
		    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
		    $("#organization").val(rgvalue);
		});
</script>
</html>