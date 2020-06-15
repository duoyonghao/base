<!--wl整理  -->
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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>
 <!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<title>短信发送</title>
</head>
<style type="text/css">
/*工作量表格 ，单独写*/

#gongzuol{margin-bottom: 15px;}
#gongzuol .columnHd{padding:0 15px;border-bottom:solid 1px #0e7ec6;font-size:16px;color: #373737;font-family: "Microsoft YaHei";line-height:36px;}
 	
	.kqds_table  td { 
		color: #666;
		padding: 2px 3px 3px 3px;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
		font-weight: normal;
		line-height: 28px;
	}
	
	.kqds_table  select { 
		height:28px;
		width:90px;
		border:solid 1px #e5e5e5;
		border-radius: 3px;
	}
	input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:90px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
	#table tr td.bs-checkbox {
		padding:0px 11px;
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
@media screen and (max-width:1390px){
	.searchWrap .formBox>section>ul.conditions>li>span{
		width:55px;
		text-align:right;
		font-size:11px;
		height:24px;
		line-height:24px;
	}
	.searchWrap .formBox>section>ul.conditions>li>input[type=text], 
	.searchWrap .formBox>section>ul.conditions>li>select{
		width:82px;
		font-size:11px;
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
</style>
<body>
<div id="container">
    <div id="main">
        <!--左侧中心-->
        <div class="centerWrap">
            <div class="columnWrap">
                <div class="columnHd"><span class="title">短信发送</span></div>
                <div class="columnBd">
                    <div class="tableBox" >
                        <table id="table" class="table-striped table-condensed table-bordered"></table>
                    </div>
                    <div id="gongzuol">
		                <div class="columnBd">
		                	<ul class="dataCountUl" id="dataCount">
				                <li><span style="color:#00a6c0;">总记录数:<lable id="zong">0</lable></span></li>
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
				    			<span>创建时间</span>
				    			<input type="text" placeholder="开始日期" id="starttime" name="starttime" class="birthDate" readonly>
				    		</li>
				    		<li>
				    			<span>到</span>
				    			<input type="text" placeholder="结束日期" id="endtime" name="endtime" class="birthDate" readonly>
				    		</li>	
				    		<li>
			    				<span>模糊查询</span>
			    				<input style="padding:0 5px;font-size:11px;" type="text" id="username" class="username" placeholder="客户姓名/手机号" > 
			    			</li>
			    			<li class="toggleTr">
			    				<span>医生</span>
		    					<input type="hidden" name="doctor" id="doctor"  class="form-control"  value=""/>
							    <input  type="text"  id="doctorDesc" name="doctorDesc" placeholder="医生" readonly  onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'',1);"></input>	
			    			</li>
			    			<li class="toggleTr">
				    			<span>到院时间</span>
				    			<input type="text" placeholder="开始日期" id="dystarttime" name="dystarttime" class="birthDate" readonly>
				    		</li>
				    		<li class="toggleTr">
				    			<span>到</span>
				    			<input type="text" placeholder="结束日期" id="dyendtime" name="dyendtime" class="birthDate" readonly>
				    		</li>	
				    		<li class="toggleTr">
			    				<span>第一咨询</span>
		    					<input type="hidden" name="askperson" id="askperson"  class="form-control"  value=""/>
							    <input  type="text"  id="askpersonDesc" name="askpersonDesc" placeholder="第一咨询" readonly  onClick="javascript:single_select_user(['askperson', 'askpersonDesc'],'',1);"></input>	
			    			</li>
			    			<li class="toggleTr">
			    				<span>患者来源</span>
			    				<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype');">
								</select>
			    			</li>
			    			<li class="toggleTr">
			    				<span>子分类</span>
			    				<select class="select2 dict" name="nexttype" id="nexttype">
									<option value="">请选择</option>
								</select>
			    			</li>
			    			<li class="toggleTr">
			    				<span>有无回访</span>
			    				<select name="ywhf" id="ywhf" >
                                 	<option value="">请选择</option>
									<option value="0">无</option>
									<option value="1">有</option>
		                        </select>
			    			</li>
							<li class="toggleTr">
			    				<span>建档人</span>
		    					<input type="hidden" name="jdr" id="jdr"  class="form-control"  value=""/>
							    <input  type="text"  id="jdrDesc" name="jdrDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['jdr', 'jdrDesc'],'',1);"></input>	
			    			</li>	
				    	</section>
                   <div class="btnBar" id="bottomBarDdiv" style="text-align: left;">
                    </div>
                </div>
            </div>
        </div>
        <!--中间模块开关按钮  -->
        <!-- <div class="middleWrap">
			<div id="collectBtn" class="collectBtn">
				<span id="trangle"></span>
			</div>	
		</div> -->
        <!--右侧信息浏览-->
        <div class="lookInfoWrap">
			<%@include file="/inc/rightPartInfo.jsp" %>
		</div>
    </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script>
</body>
<script type="text/javascript">
var listbutton;
var contextPath = "<%=contextPath%>";
var selectedrows = "";
var nowday;
var onclickrowOobj ="";
var pageurl = '<%=contextPath%>/KQDS_UserDocumentAct/selectWithNopage2.act';
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
var loc = new Location();
$(function() {
	initHosSelectList4Front('organization'); // 连锁门诊下拉框
	
    initDictSelectByClass(); // 患者来源
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //获取当前页面所有按钮
    getButtonAllCurPage("<%=menuid%>");
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

    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    togglemodel.initial("xxcx",pageurl);
    //4、表格初始化
    initTable();
    
    $(window).resize(function() {
        setWidth();
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
    /* 常用查询 按钮 高级查询  按钮*/
    initSearchToggleBtnGroup();
});
function initTable() {
    //初始化表格所在div
    $('#table').bootstrapTable({
        url: pageurl,
        dataType: "json",
        singleSelect: false,
        queryParams: queryParams,
        onLoadSuccess: function(data) { //加载成功时执行
        	if(nowpage == 0 && data.total>0){
        		maxpage = Math.floor(data.total/pagesize)+1; 
        		$("#zong").html(data.total);
        	 }
        	//分页加载
        	showdata("table",data.rows);
        	$("#table").bootstrapTable("resetView",{
        		height:$(window).outerHeight()-$(".searchWrap").outerHeight()-$("#gongzuol").outerHeight()-$(".columnWrap .columnHd").outerHeight()-35
        	});
        	//计算主体的宽度
            setWidth();
            setHeight();
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        columns: [
        {field: ' ',checkbox: true,formatter: stateFormatter},
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            
            visible: false,
            switchable: false
        },
        {
            title: '序号',
            field: 'Number',
            align: 'center',
            formatter:function(value, row, index){
            	return index+1;
            }
        },  
        {
            title: '建档时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                	return '<span>' + value + '</span>';
                }else{
                	return "";
                }
            }
        },
        {
            title: '最近到院时间',
            field: 'lasttime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                	return '<span>' + value + '</span>';
                }else{
                	return "";
                }
            }
        },
        {
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span>' + value + '</span>';
                    return html;
                }
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name">' + value + '</span>';
                    return html;
                }
            }
        },
        {
            title: '性别',
            field: 'sex',
            align: 'center',
            
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '年龄',
            field: 'age',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (value == "0") {
                    return "<span>-</span>";
                } else {
                    return '<span>'+value+'</span>';
                }
            }
        },
        {
            title: '第一咨询',
            field: 'askperson',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name">' + value + '</span>';
                    return html;
                }else{
                	return "<span>-</span>";
                }
            }
        },
        {
            title: '医生',
            field: 'doctor',
            align: 'center',
            
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name">' + value + '</span>';
                    return html;
                }
            }
        },
        {
            title: '手机号码1',
            field: 'phonenumber1',
            align: 'center',
            formatter: function(value, row, index) {
                if (value != null || value != "") {
                    if (canlookphone == 0) {
                        return '<span title="' + value + '">' + value + '</span>';
                    } else {
                        return '<span>-</span>';
                    }
                } else {
                    return '<span>-</span>';
                }
            }
        },
        {
            title: '手机号码2',
            field: 'phonenumber2',
            align: 'center',
            formatter: function(value, row, index) {
                if (value != null || value != "") {
                    if (canlookphone == 0) {
                        return '<span title="' + value + '">' + value + '</span>';
                    } else {
                        return '<span>-</span>';
                    }
                } else {
                    return '<span>-</span>';
                }
            }
        },
        {
            title: '患者来源',
            field: 'devchannelname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return "<span class='source'>"+value+"</span>";
            }
        },
        {
            title: '来源子分类',
            field: 'nexttypename',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span class="source">'+value+'</span>'
            }
        },
        {
            title: '省',
            field: 'provincename',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '市',
            field: 'cityname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '区',
            field: 'townname',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return "<span class='name'>"+value+"</span>";
            }
        },
        {
            title: '建档人',
            field: 'createuser',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>';
            }
        }, {
            title: '有无回访',
            field: 'ywhf',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value == "无回访") {
                  	return "<span class='name' style='color:red;'>无回访</span>";
                }else{
                	return "<span>有回访</span>";
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
    });
}
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	offset : 0,
    	limit :	 pagesize,
    	ispaging : 1,
   		organization: $('#organization').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        dystarttime: $('#dystarttime').val(),
        dyendtime: $('#dyendtime').val(),
        username: $('#username').val(),
        askperson: $('#askperson').val(),
        doctor: $('#doctor').val(),
        devchannel: $('#devchannel').val(),
        nexttype: $('#nexttype').val(),
        //province: $('#province').val(),
        //city: $('#city').val(),
        ywhf: $('#ywhf').val(),
        jdr: $('#jdr').val()
    };
    return temp;
}
function searchHzda() {
	loadedData = [];
	nowpage = 0;
    var starttime = $('#starttime').val();
    var endtime = $('#endtime').val();
    var dystarttime = $('#dystarttime').val();
    var dyendtime = $('#dyendtime').val();
    var username = $('#username').val();
    var askperson = $('#askperson').val();
    var doctor = $('#doctor').val();
    var devchannel = $('#devchannel').val();
    var nexttype = $('#nexttype').val();
    //var province = $('#province').val();
    //var city = $('#city').val();
    var ywhf = $('#ywhf').val();
    var jdr = $('#jdr').val();

    if (starttime == "" && endtime == "" && dystarttime == "" && dyendtime == "" && username == "" && askperson == "" && doctor == "" && devchannel == "" && nexttype == ""  && jdr == "" && ywhf == "") {
        layer.alert('请选择查询条件!' );
        return false;
    }
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}
function clean() {
	//定位滚动条位置
    /* $(".fixed-table-body").animate({ 
    	scrollTop: 400
    	}, 1000); */
    $(".fixed-table-body").scrollTop(400); 
 	var rgvalue = $("#organization").val();
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val(rgvalue); 
}
//获取选中行的usercode
function getIdSelections() {
    return $.map($('#table').bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}
//转诊咨询
function tosend(){
	selectedrows = getIdSelections();
	if (selectedrows.length == 0) {//单个患者转诊咨询
		 layer.alert('请勾选复选框，选择患者(可多选)' );
		 return false;
	}
	layer.open({
	       type: 2,
	       title: '短信发送',
	       shadeClose: false,
	       shade: 0.6,
	       area: ['750px', '450px'],
	       content: contextPath + '/KQDS_SmsAct/toSendSms.act'
	});
}
//计算主体的宽度


//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height() - 15,
    optAreaHeight = $('.searchWrap').outerHeight();

    var columnHdHeight = $('.columnWrap .columnHd').height();
    $('.lookInfoWrap .columnWrap .columnBd').height(baseHeight - 40);
    $('.lookInfoWrap .columnWrap').height(baseHeight);
    // $('#table').bootstrapTable('resetView',{height:baseHeight - optAreaHeight - 123});
    $("#table").bootstrapTable("resetView",{
  		height:$(window).outerHeight()-$(".searchWrap").outerHeight()-$("#gongzuol").outerHeight()-$(".columnWrap .columnHd").outerHeight()-28
  	});
    $('.tabIframe').height(baseHeight - 50);
  	//wl 设置 关闭右侧模块按钮"collectBtn"
    $(".middleWrap").outerHeight(baseHeight-2);//"collectBtn"按钮的容器
    $("#collectBtn").css("marginTop",baseHeight/2-50);//wl 垂直位置
}
function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "SMS_TOSEND") {
        	menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="tosend()">短信发送</a>';
        }
    }
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn clean" onclick="clean()">清空</a>';
    menubutton1 += '<a href="javascript:void(0);" class="kqdsCommonBtn search" onclick="searchHzda()">查询</a>';
    
    $("#bottomBarDdiv").append(menubutton1);

    setHeight();
}

/*****#########################################系统数据清理 START#########################################****/
//获取选中行的usercode
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}

//复选框
function stateFormatter(value, row, index) {
    return value;
}
/*****#########################################系统数据清理  END#########################################****/
</script>
</html>
