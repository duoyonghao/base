<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String canPaiban = UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag4_canPaiban, request);
	YZPerson person = SessionUtil.getLoginPerson(request);
	String stepnum = request.getParameter("stepnum");
	if (stepnum == null) {
		stepnum = "7";
	}
	String personid = request.getParameter("personid");
	if (personid == null) {
		personid = "";
	}
	String regdept = request.getParameter("regdept");
	if (regdept == null) {
		regdept = "";
	}
	String usercode = request.getParameter("usercode");
	if (usercode == null) {
		usercode = "";
	}
	String username = request.getParameter("username");
	if (username == null) {
		username = "";
	}
%>
<!doctype html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<title>排班</title><!-- 从预约日历页面 点击  预约查询按钮进入  -->
</head>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/dhtmlxscheduler/dhtmlxscheduler.css"  title="no title" charset="utf-8">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/dhtmlxscheduler/dhtmlx.css"  title="no title" charset="utf-8">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yyzx/paiban.css"  title="no title" charset="utf-8">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/dhtmlxscheduler.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/dhtmlx.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/locale_cn.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/locale_recurring_cn.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_minical.js"></script> <!-- 选择日期 -->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_timeline.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_tooltip.js"></script> <!-- tips-->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_limit.js"></script> <!-- 鼠标移上去变色 -->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_pdf.js"></script> <!-- 导出 -->

<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/yyzx/paiban.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>

<style type="text/css" media="screen">
.dhx_cal_container{
	font-family: arial;
    font-size: 12px;
}
html,body{
	height:100%;
}
body{
	overflow:hidden;
	position:relative;
}
.dhx_cal_ltext textarea{
	overflow-x:hidden;
}
.webuploader-pick{
	padding:0px;
}
/* 搜索框select */
.bootstrap-select:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
    width: 120px;   
}
.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
     color: #999;
     height: 24px;
     line-height: 24px;
 }
.btn {
 	color: #333 !important;
    font-size: 12px !important;
    padding:0px;
    padding-left: 3px;
}
.filters_wrapper span{
	padding:0px;
}
.dropdown-menu > li > a {
    padding: 0px 20px;
    box-sizing:border-box;
    font-size: 12px;
}
.bs-searchbox .form-control{
	height: 26px;
}
.bootstrap-select.btn-group .dropdown-toggle .filter-option {
	line-height: 24px;
}
.filters_wrapper label {
	padding-right: 0px; 
}
.search {
	height: 24px;
	text-align: center;
}
</style>
<script type="text/javascript" charset="utf-8">
var usercodechild = "";
var usernamechild = "";
var personid = "<%=personid%>";
var regdept = "<%=regdept%>";
var usercode = "<%=usercode%>";
var username = "<%=username%>";
var loginuser = "<%=person.getSeqId()%>";
var stepnum = "<%=stepnum%>";
var frameindex ="";
var initnum = 0; // 用于点击左右按钮选择日期时，触发加载数据请求

//排班权限  0 有权限  1 无权限
//无权限的只能给自己排班 可以看所有人的排班情况 ；有权限的可以排班所有人 及查看所有人
var canPaiban = "<%=canPaiban%>";
//初始化 
$(function(){
	$('.searchSelect').selectpicker("refresh");//搜索初始化刷新2019.11.4--lutian
	
});
function init() {
	if(canPaiban == "0"){
		$("#mbxzid").css("display","none");
		$("#pbdrid").css("display","none");
	}
	uploadfileNotImg(contextPath + "/FileUploadAct/uploadFile.act?module=evidence",loaddata); // 回调
//  getAllDeptByOrganization(); //加载科室
    getAllDeptByOrganizationSearch();//加载部门
    //监听 onchange事件       根据部门选择人员
    //selectChangeTwo("regdept", "personid");
    selectChangeTwoSearch("regdept", "personid");
//     $("#regdept").val(regdept);
    $("#regdept").selectpicker('val', regdept);
    $("#regdept").change();    
//     $("#personid").val(personid);
    $("#personid").selectpicker('val', personid);
    scheduler.config.xml_date = "%Y-%m-%d %H:%i";
    scheduler.config.xml_date = "%Y-%m-%d %H:%i";
    scheduler.config.prevent_cache = true;
    scheduler.config.buttons_left = ["dhx_delete_btn"];
    scheduler.config.buttons_right = ["dhx_save_btn", "dhx_cancel_btn"];
    scheduler.config.icons_select = ['icon_details', 'icon_delete'];

    //定义弹出 层标题
    scheduler.templates.lightbox_header = function(start, end, event) {
        return "排班";
    };
    var listPer = getSelectper(regdept, personid); // 获取人员列表
    scheduler.createTimelineView({
        name: "matrix",
        dx: 100,
        section_autoheight: false,
        dy: 40,
        x_unit: "day",
        x_date: "%M %d",
        x_step: 1,
        x_size: Number(stepnum),
        y_unit: listPer,
        y_property: "personid"
    });
    scheduler.templates.matrix_cell_class = function(ev, x, y) {
        if (ev) {
            if (ev[0].startdate == ev[0].enddate) {
                return "green_cell";
            } else {
                return "yellow_cell";
            } 
            /* else if (ev[0].orderstatus == "晚班") {
                return "gray_cell";
            } */
        }
        return "white_cell";
    };
    scheduler.templates.matrix_cell_value = function(ev, x, y) {
        if (ev) {
        	if(ev[0] && ev[0].id.length == 36){ // 后台的uuid长度为36
        		return ev[0].orderstatus;
        	}else{
        		return "新建";
        	}
        }else{
        	return "";
        }
    };
    scheduler.attachEvent("onBeforeLightbox",
    function(id) {
    	//查询 该用户是否已排过班
   	    var B = this.getEvent(id).start_date;
   	    var ss = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate();
    	var ispaiban = checkPaiban(ss, this.getEvent(id).personid);
        if (ispaiban && id.toString().length<20) {
        	loaddata();
            return false;
        }
        if (id) {
            if (canPaiban == 0 && this.getEvent(id).personid != loginuser) {
                layer.alert('权限不足！', {
                      
                });
                
                this._lightbox_id = id; // 权限不足时，删掉展示的 "新建"
                this.cancel_lightbox();
                return false;
            }
        }
        return true;
    });
    //===============
    //Data loading
    //===============
    scheduler.locale.labels.section_personid = "人员";
    scheduler.locale.labels.section_orderstatus = "排班类型";
    scheduler.locale.labels.section_remark = "备注";
    var orderstatus = getSelect(); //查询排班类型下拉选项,paiban.js中定义
    scheduler.form_blocks["my_personid"] = {
        render: function(sns) {
            return '<div class="dhx_cal_ltext" style="height:20px;"><input type="hidden" name="personid" id="personid" placeholder="排班医生" title="排班医生" style="width: 80%;height: 18px;" value="<%=person.getSeqId()%>"/>' + '<input type="text" class="form-control" id="personidDesc" name="personidDesc" placeholder="排班医生" style="width:115px;height:18px;" readonly="readonly" value="<%=person.getUserName()%>" onClick="javascript:single_select_user([\'personid\', \'personidDesc\'],\'single\');"></input></div>';
        },
        set_value: function(node, value, ev) {
            if (value != "" && value != null && value != "undefined") {
                $('#personid').val(value);
                bindPerUserNameBySeqIdTB("personidDesc", value);
            }
        },
        get_value: function(node, ev) {
            ev.details = node.childNodes[1].value;
            return node.childNodes[0].value;
        },
        focus: function(node) {
            var a = node.childNodes[1];
            a.select();
            a.focus();
        }
    };
    scheduler.config.lightbox.sections = [{
        name: "personid",
        height: 40,
        type: "my_personid",
        map_to: "personid"
    },
    {
        name: "orderstatus",
        height: 23,
        type: "select",
        options: orderstatus,
        map_to: "orderstatus"
    },
    {
        name: "remark",
        height: 40,
        type: "textarea",
        default_value: "",
        map_to: "remark"
    }];
    scheduler.attachEvent("onViewChange", function( new_mode, new_date){
    	if(initnum>0){
    		loaddata();
    	}
        return true;
    });
    scheduler.init('scheduler_here', new Date(), "matrix");
    loaddata();
}
//点击查询
function loaddata() {
    var data = {
        type: 0,
        personid: personid,
        regdept: regdept,
        starttime: scheduler._min_date.getTime(),
        endtime: scheduler._max_date.getTime()
    };
    var urlsearch = contextPath + '/KQDS_PaibanAct/showXML.act';
    $.axse(urlsearch, data,
    function(data) {
        if (data.result == 'ok') {
            var json = data.path;
            // json = eval('(' + json + ')');
            scheduler.clearAll(); // 先清掉  再加载
            scheduler.parse(json, "json");
            initnum++;
        }
    },
    function() {});
    
    setHeight();//重置排班展示区的高度
}

//点击查询
function shuaxin() {
    personid = $("#personid").val();
    if(!personid){
    	personid = "";
    }
    
    dept = $("#regdept").val();
    window.location.href = contextPath + "/KQDS_PaibanAct/toPaiBan.act?personid=" + personid + "&regdept=" + dept + "&stepnum=" + stepnum;

}

//点击清空
function qingkong() {
//     $("#regdept").val("");
//     $("#personid").val("");
	   $("#regdept").selectpicker("val","");
	   $("#personid").selectpicker("val","");
}

//页面人员高级选择
function select() {
    //获取部门的所有id
    var jzks = $("#regdept option").map(function() {
        return $(this).val();
    }).get().join(",");
    jzks = jzks.replace(/^,+|,+$/g, '');
    deptid_select_user(['checkperson', 'checkpersonDesc'], jzks);
}

//人员树选择后 改变 两级下拉选
function changeAskPerson() {
    var checkerson = $("#checkperson").val();
    if (checkerson) {
        //获取用户部门id
        var url = contextPath + "/YZPersonAct/getDeptByUserSeqId.act?seqId=" + checkerson;
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                var val = data.retData;
                //就诊科室赋值
                $("#regdept").val(val);
                //手动触发 下拉选change事件
                $("#regdept").trigger("change");
                //人员下拉选赋值
                $("#personid").val(checkerson);
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}
function onloadsx(stepnum) {
    personid = $("#personid").val();
    dept = $("#regdept").val();
    window.location.href = contextPath + "/KQDS_PaibanAct/toPaiBan.act?personid=" + personid + "&regdept=" + dept + "&stepnum=" + stepnum;
}

// 模板下载
function mbxz() {
	frameindex = layer.open({
        type: 2,
        title: '排班模板下载',
        maxmin: true,
        shadeClose: true,
        area: ['520px', '380px'],
        content: contextPath + "/KQDS_PaibanAct/toPaiBanExport.act?personid="+personid+"&regdept="+regdept
    });
}
//导入排班  返回值
function fz() {
    jQuery(".file-item").hide();
}

function exportToPDF (){
	var path = contextPath + "/yz/act/pdf/PDFGenerator/ExportPdf.act";
	var color = 'fullcolor';
	scheduler.toPDF(path, color);
}
function setHeight(){/* 设置高度 */
	$("#scheduler_here").outerHeight($(window).outerHeight()-40);
	
}
function downLoadEx(regdept,personid,mbstartdate,mbenddate){
	 layer.close(frameindex);
	 var downUrl = contextPath+"/KQDS_PaibanAct";
	 location.href = downUrl + "/excelStandardTemplateOut.act?deptId=" + regdept + "&seqId=" + personid + "&startdate=" + mbstartdate + "&enddate=" + mbenddate;
}
</script>

<body onload="init();">
<div style='height:30px; padding:5px;'>
	<div class="filters_wrapper" id="filters_wrapper">
		<span>部门:</span>
		<label>
			<select class="dept" name="regdept" id="regdept" style="width: 120px;" title="请选择" data-live-search="true"></select>
<!-- 			<select class="dept" name="regdept" id="regdept" style="width: 120px;"></select> -->
		</label>
		<span>人员:</span>
		<label>
			<select class="searchSelect" style="width: 120px;" name="personid" id="personid" data-bv-notempty data-bv-notempty-message="主治医生不能为空" title="请选择" data-live-search="true">
	      	</select>
<!-- 	      <select  style="width: 120px;"  name="personid" id="personid" data-bv-notempty data-bv-notempty-message="主治医生不能为空"> -->
<!-- 	      </select> -->
		</label>
		<label>
			<input type="hidden" name="checkperson" id="checkperson" placeholder="咨询人员" title="咨询人员" class="form-control" /> 
			 <input type="hidden"  onchange="changeAskPerson()"   id="checkpersonDesc" name="checkpersonDesc" placeholder="咨询人员" readonly style="width: 120px;" ></input>	
		     <a onClick="javascript:select()" style="cursor:pointer;color: #00a6c0; ">高级选择</a>
		</label>
		<label>
			<button type="button" class="search" id="searchButton" onclick="qingkong()">清空</button>
			<button type="button" class="search" id="searchButton" onclick="shuaxin()">查询</button>
		</label>
		<label id="mbxzid">
			<button type="button" class="search" id="searchButton" onclick="mbxz()">模板下载</button>
		</label>
		<label id="pbdrid">
				<input type="hidden" placeholder="" id="imgtype" name="imgtype" value="paiban">
				<div id="uploader-demo" class="search" style="margin-top: 2px;">  
					<div id="fileList" class="uploader-list"></div>
					<div id="filePicker" style="height: 16px;    position: relative;top: 2px;">排班导入</div>
				</div>
		</label>
	</div>
</div>
<div id="scheduler_here" class="dhx_cal_container" style='width:100%; height:100%;margin-top:5px;'>
	<div class="dhx_cal_navline">
        <!-- <div class='dhx_cal_export pdf' id='export_pdf' title='导出PDF' onclick='exportToPDF()'>&nbsp;</div> -->
		<div class="dhx_cal_prev_button">&nbsp;</div>
		<div class="dhx_cal_next_button">&nbsp;</div>
		<div class="dhx_cal_today_button"></div>
		<div class="dhx_cal_date"></div>
		<div class="dhx_minical_icon" id="dhx_minical_icon" style="left:170px;" onclick="show_minical()">&nbsp;</div>
		<div style="top:14px;">
			<button type="button" class="search"  onclick="onloadsx(7)">周</button> 
			<button type="button" class="search" name="matrix" onclick="onloadsx(31)">月</button> 
		</div>
	</div>
	<div class="dhx_cal_header">
	</div>
	<div class="dhx_cal_data">
	</div>
</div>
</body>