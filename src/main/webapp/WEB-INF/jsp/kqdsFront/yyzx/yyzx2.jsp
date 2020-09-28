<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/taglib.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
	// 初诊的字典Id值，从配置文件中读取
	String recesort = SysParaUtil.getSysValueByName(request, SysParaUtil.YYFL_CHUZHEN_SEQID);
	if (recesort == null) {
		recesort = "";
	}

	// 获取是否打开排班 和预约关联的 功能
	String IS_OPEN_PAIBAN_ORDER_FUNC = SysParaUtil.getSysValueByName(request, SysParaUtil.IS_OPEN_PAIBAN_ORDER_FUNC);
	if (IS_OPEN_PAIBAN_ORDER_FUNC == null) {
		IS_OPEN_PAIBAN_ORDER_FUNC = "0"; // 0 不打开  1打开
	}

	String yyxm = request.getParameter("yyxm");
	if (yyxm == null) {
		yyxm = "";
	}
	String doctors = request.getParameter("doctors");
	if (doctors == null) {
		doctors = "";
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
	String order_number = request.getParameter("order_number");
	if (order_number == null) {
		order_number = "";
	}
	String nodeId = request.getParameter("nodeId");
	if (nodeId == null) {
		nodeId = "";
	}

	String organization = request.getParameter("organization");
	if (organization == null) {
		organization = "";
	}
%>
<!doctype html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<title>预约日历查询</title><!-- 从预约日历页面 点击  预约查询按钮进入  -->
</head>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yyzx/yyzx.css?v=${version}"  title="no title" charset="utf-8">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/dhtmlxscheduler/dhtmlxscheduler.css"  title="no title" charset="utf-8">

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/dhtmlxscheduler.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/locale_cn.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/locale_recurring_cn.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_minical.js"></script> <!-- 选择日期 -->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_units.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_tooltip.js"></script><!-- tips-->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_limit.js"></script><!-- 鼠标移上去变色 -->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_readonly.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_pdf.js"></script> <!-- 导出 -->

<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js?v=${version}"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/yyzx/yyzxSearch2.js?v=${version}"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/yyzx/check.js?v=${version}"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>

<style type="text/css" media="screen">
.dhx_scale_bar {
    font: 11px/16px Arial;
    color: #767676;
    padding-top: 2px;
    background-color: white;
    font-size: 16px;
}
.dhx_wrap_section select{/* 新建预约中的select的样式 高度 */
	height:23px;
	border-radius:3px;
	cursor:pointer;
	outline:none;
}
.yyinput{ /* 病人和预约医生 的 input*/
	height:18px;
	border-radius:3px;
	border:1px solid #ddd;
	cursor:pointer;
	outline:none;
	vertical-align:middle;
	padding-left:5px;

}
.dhx_wrap_section textarea{
	background:#fff;
	border:1px solid #ddd;
}
.dhx_section_time{/*创建预约见面的  时间范围  */
	float: left;
    margin-left: 10px;
}
								/* input textarea select 用户体验效果  */
input[type="text"]:focus,select:focus,textarea:focus{
   /*  box-shadow: 0 0 8px rgb(49, 165, 247); */
    border-color:#00A6C0 ;
}
input[readonly]:focus,
input[disabled]:focus,
select[disabled]:focus,
select[disabled]:focus{
    box-shadow: none;
    border-color:#ddd;
    
}
input[readonly],input[disabled],select[disabled],select[disabled]{/*只读的input*/
	background:rgba(246, 246, 246, 0.42);
	cursor:not-allowed;
	color:#777;
}
.tooltip{
	max-width:400px;
}
.dhx_wrap_section textarea{
	overflow-y: auto;
	overflow-x:hidden;
}

/* bootstrap与日程插件冲突   */
* {
    -webkit-box-sizing: content-box;
    -moz-box-sizing: content-box;
    box-sizing: content-box;
}
body {
    line-height: inherit;
}
/*************/
/* select */
.btn-default{
	width: 130px !important;
	/* height: 28px; */
	height:30px;
}
.btn {
	padding: 3px 12px;
	box-sizing:border-box;
	font-size: 12px;
	line-height: inherit;
}
.filters_wrapper span{
	padding-right: 0px;
}
input[type="text"]:focus, select:focus, textarea:focus {
    box-shadow: 0 0 8px #00A6C0; 
}
.dropdown-menu > .active > a, .dropdown-menu > .active > a:hover, .dropdown-menu > .active > a:focus {
    background-color: #00A6C0;
}
.bootstrap-select:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {
    width: auto;
}
.dropdown-menu > li > a {
    padding: 0px 20px;
    box-sizing:border-box;
    font-size: 12px;
}
.form-control {
    height: auto;
    padding: 0px 12px;
    box-sizing:border-box;
}
.bootstrap-select.btn-group .dropdown-menu li a span.text {
    font-weight: normal;
}
.pull-left {
    color: black;
    font-weight: normal !important;
}
/* 解决悬浮不显示问题（与bootstrap.css冲突） */
.tooltip {
	opacity: 1;
}

.dhx_readonly{
    color: #333;
    font-size: 12px;
    height: 25px;
    width: 100px;
    text-align: center;
    border: 1px solid #e5e5e5;
}
.dhx_readonly{/*时间日历的input*/
	cursor:pointer !important;
  	color:#2e2e2e !important;
}
 .dhx_wrap_section select:nth-child(1) { 
     width: 98%!important; 
 } 
</style>
<script type="text/javascript" charset="utf-8">
var usercode = "<%=usercode%>";
var username = "<%=username%>";
var order_number = "<%=order_number%>";
var nodeId = "<%=nodeId%>";
var openPaiban = "<%=IS_OPEN_PAIBAN_ORDER_FUNC%>";
var yyxm = "<%=yyxm%>"; // 查询条件，项目分类，Url传值 
var regdept = "<%=regdept%>"; // 查询条件，部门
var doctors = "<%=doctors%>"; // 查询条件，人员
var loginuser = "<%=person.getSeqId()%>";
var order_number;
var nodeId;
var flag = true; // ## 这个变量的作用是，当一个患者当天预约多次时，通过此变量的配合，可以使用layer.confirm方法，该方法是非阻塞的
var flagdata = ""; // 仅为了全局存值，用于alert展示
var initnum = 0; // 用于点击左右按钮选择日期时，触发加载数据请求
var recesort = "<%=recesort%>"; // 初诊的字典Id值，从配置文件中读取
var canOrderOther = "<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag6_canOrderOther, request)%>"; //是否具备给他人预约患者的权限 0不可以 1 可以

// ### 当天不上班的医生不能预约  17-5-26 add  ys
var static_doctor_list = null;  // 全局变量，存储展示的人员列表  key label
var static_perSeqIdList = null; // 全局变量，存储不在班的人员seqId
// 拖拽事件传值，用于判断拖拽的时候，能把预约拖拽到 休息的医生那里去
var static_before_dragged_askperson = null;

var static_organization = null;

//是否具备给他人预约患者的权限 0不可以 1 可以
function orderOtherValidate() {
    if (canOrderOther == 1) {
    	single_select_user(['askperson', 'askpersonDesc'],'single');
    } else {
        layer.alert('没有为其他用户进行患者预约的权限！' );
    }
}
//初始化 
function init() {
    
	if('<%=organization%>' != ''){
		static_organization = '<%=organization%>';
	}else{
		static_organization = '<%=ChainUtil.getCurrentOrganization(request)%>';
	}
	setHeight()//设置日历高度
	initHosSelectListNoCheck('organization',static_organization); // 加载门诊列表
	getAllDeptByOrganization(static_organization); //加载科室
	//$('.dept').selectpicker("refresh");//部门下拉框添加搜索功能 lutian
	getAllDeptByOrganizationTwo("organization","regdept"); //监听门诊下拉框加载部门数据
	selectChangeTwodept("regdept", "doctors"); //监听 onchange事件       根据部门选择人员
	
    /* $("#organization").change(function(){
    	console.log("门诊下拉框change---------");
		var currSelect = $(this).val();
		getAllDeptByOrganization(currSelect);
	}); */  
	
    initDictSelectByClass(); //预约项目分类
    $('.dict').selectpicker("refresh");//预约项目分类下拉框添加搜索功能 lutian
    $("#yyxm").val(yyxm);
    $("#regdept").val(regdept);
    $("#regdept").change();
    if(doctors){
    	$('#doctors').selectpicker('val', doctors);
    }
    //$("#doctors").val(doctors);

    scheduler.locale.labels.unit_tab = "人员";
    scheduler.config.xml_date = "%Y-%m-%d %H:%i";
    scheduler.config.prevent_cache = true;
    scheduler.config.first_hour = 8;
    scheduler.config.last_hour = 22;
    scheduler.config.time_step = 15;
    //默认事件持续事件 可以在更改开始日期后自动更改结束事件日期
    scheduler.config.event_duration = 15;
    scheduler.config.auto_end_date = true;
    //可以通过双击创建事件
    scheduler.config.details_on_create = true;
    scheduler.config.details_on_dblclick = true;
    //将灯箱中时间选择器的最大值和最小值设置为'last_hour'和'first_hour'选项的值
    scheduler.config.limit_time_select = true;
    scheduler.config.active_link_view = "week";
    //存储位于灯箱左下角的一组按钮
    scheduler.config.buttons_left = ["dhx_delete_btn"];
    //存储位于灯箱右下角的一组按钮
    scheduler.config.buttons_right = ["dhx_save_btn", "dhx_cancel_btn"];
    scheduler.config.icons_select = ['icon_details'];
    
    // scheduler.config.drag_create = false;
    // scheduler.config.drag_move = false;
    // scheduler.config.drag_out = false;//这个属性要花钱购买企业版才行
    

    
    //在动态加载的情况下 ，将服务器请求参数的格式
    //scheduler.config.load_date = "%Y-%m-%d";
    var step = 15;
    var format = scheduler.date.date_to_str("%H:%i");
	var yyfl= getSelect("YYFL");
    scheduler.config.hour_size_px = (60 / step) * 22;
    scheduler.templates.hour_scale = function(date) {
        html = "";
        for (var i = 0; i < 60 / step; i++) {
            html += "<div style='height:22px;line-height:22px;'>" + format(date) + "</div>";
            date = scheduler.date.add(date, step, "minute");
        }
        return html;
    };
    
    

    // 共用的两个方法，临时取消共用，拷贝为两份
    function block_readonly_onBeforeDrag(id, mode, e) {
        if (id) {
        	// 存拖拽之前的 医生Id
        	if(this.getEvent(id).askperson){
        		static_before_dragged_askperson = this.getEvent(id).askperson;
        	}
        	
            var flag = true;
            var urlsearch = contextPath + '/KQDS_Hospital_OrderAct/selectDetail.act?seqId=' + id;
            $.axse(urlsearch, null,
            function(data) {
                if (data.retState == "0") {
                    //当天之前 打开readonly页面
                    var starttime = getNowDay(data.data.starttime);
                    var nowdate = getNowFormatDate();
                    var checkdatez = checkdate(starttime, nowdate); // 0 预约开始时间 大于 当前时间  1预约开始时间 小于当前时间 2 预约开始时间等于当前时间
                    if (checkdatez == 1) { // 即打开的时候，过了预约时间的，只读，不能编辑了
                        flag = false; // 此时设置为 readonly
                    }
                    //已上门 打开readonly页面
                    if (data.data.orderstatus == 1) {
                        flag = false;
                    }
                    //不是本人创建不可改 打开readonly页面
                    if (data.data.createuser != loginuser) {
                        flag = false;
                    }
                }
            },
            function() {

			});
            
            // 非预约视图,都不可以拖拽
            var mode = scheduler.getState().mode;
    		if(mode != 'unit'){
    			flag = false;
    		}
            
            if (!flag) {
                this.getEvent(id).readonly = true;
            }
            return ! this.getEvent(id).readonly;
        }  
        return true;
    }
    
    function block_readonly_onClick(id, mode, e) {
        if (id) {
            var flag = true;
            var urlsearch = contextPath + '/KQDS_Hospital_OrderAct/selectDetail.act?seqId=' + id;
            $.axse(urlsearch, null,
            function(data) {
            	order_number = data.data.order_number;
            	nodeId = data.data.nodeId;
                if (data.retState == "0") {
                    //当天之前 打开readonly页面
                    var starttime = getNowDay(data.data.starttime);
                    var nowdate = getNowFormatDate();
                    var checkdatez = checkdate(starttime, nowdate); // 0 预约开始时间 大于 当前时间  1预约开始时间 小于当前时间 2 预约开始时间等于当前时间
                    if (checkdatez == 1) { // 即打开的时候，过了预约时间的，只读，不能编辑了
                        flag = false; // 此时设置为 readonly
                    }
                    //已上门 打开readonly页面
                    if (data.data.orderstatus == 1) {
                        flag = false;
                    }
                    //不是本人创建不可改 打开readonly页面
                    if (data.data.createuser != loginuser) {
                        flag = false;
                    }
                }
            },
            function() {

			});
            if (!flag) {
                this.getEvent(id).readonly = true;
            }
            return ! this.getEvent(id).readonly;
        } 
        return true;
    }
    
    scheduler.attachEvent("onBeforeDrag", block_readonly_onBeforeDrag);
    scheduler.attachEvent("onClick", block_readonly_onClick);
    
    // 非值班人员，无法预约
    scheduler.attachEvent("onBeforeLightbox",
  	    function(id) {
    		// 只在unit视图下才可新建预约，月视图和周视图不可新建编辑
	    	var mode = scheduler.getState().mode;
			if(mode != 'unit'){
				// this.getEvent(id).readonly = true;
				layer.alert('请在"人员"的页面进行操作。', { /** 周、月页面，不弹框 **/
 		              
   		        });
				this._lightbox_id = id; // 权限不足时，删掉展示的 "新建"
				this.cancel_lightbox();
				return false;
			}
    		if(id.length == 36){ // 编辑或查看，直接跳过，不做检查
    			return true;
    		}
    		var timestamp = Date.parse(new Date());
   		    var starttime = Date.parse(this.getEvent(id).start_date);
   		    if (timestamp > starttime) { // 新增预约时，判断所选的时间段是否超时
   		        layer.alert('预约时间已过', {
   		              
   		        });
   		   		this._lightbox_id = id; // 权限不足时，删掉展示的 "新建"
	            this.cancel_lightbox();
   		        return false;
   		    }
   		    //没有预约权限
            if (canOrderOther !=1 && loginuser != this.getEvent(id).askperson ) {
            	 layer.alert('没有为其他用户进行患者预约的权限！', {
                       
                 });
            	this._lightbox_id = id; // 权限不足时，删掉展示的 "新建"
	            this.cancel_lightbox();
   		        return false;
            }
    		// 空白区域，不允许操作
    		if((this.getEvent(id).askperson + '').indexOf('empty') >= 0 ){
    			layer.alert('请在合适范围内操作！', {
                      
                });
    			
    		   this._lightbox_id = id; // 权限不足时，删掉展示的 "新建"
   	           this.cancel_lightbox();
   	           return false;
    		}
    		
    		if(static_perSeqIdList == null){
    			return true; // 页面展示的人员都在班，不做判断
    		}
    		//console.log(this.getEvent(id).askperson+"--------------"+static_perSeqIdList);
            if (isStrInArray(this.getEvent(id).askperson,static_perSeqIdList)) {
                layer.alert('非值班人员，无法预约！', {
                      
            	});
                this._lightbox_id = id; // 权限不足时，删掉展示的 "新建"
   	            this.cancel_lightbox();
   	            return false;
            }    
           
        return true;
	});
    
    
    scheduler.templates.tooltip_text = function(start, end, ev) {
        var orderstatusstr = "";
        if (ev.orderstatus == 1) {
            orderstatusstr = "已上门";
        } else {
            orderstatusstr = "未上门";
        }
        return "<b>病人:</b> " + ev.username + "<br/>"+"<b>病历号:</b> " + ev.blcode + "<br/>" + "<b>开始时间:</b> " + scheduler.templates.tooltip_date_format(start) + "<br/><b>结束时间:</b> " + scheduler.templates.tooltip_date_format(end) + "<br/><b>创建人:</b> " + ev.createusername + "<br/><b>预约分类:</b> " + ev.ordertypename + // ### 优化，不从前台取值
        "<br/><b>预约状态:</b> " + orderstatusstr + "<br/><b>项目分类:</b> " + ev.orderitemtypename + // ### 优化，不从前台取值
        "<br/><b>预约医生:</b> " + ev.askpersonname + // ### 优化，不从前台取值
        "<br/><b>项目详情:</b> " + ev.orderitemdetail + "<br/><b>预约详情:</b> " + ev.orderremark + "<br/><b>备注:</b> " + ev.remark;
    };
    scheduler.templates.event_class = function(start, end, event) { // ## 页面加载完成前，根据section数量循环执行
        var color = "";
       /*  for(var i=0;i<yyfl.length;i++){
        	if(event.ordertype==yyfl[i].key){
        		if(yyfl[i].label=="初诊"){
        			 color = "employee_event_cz";
        		}else if(yyfl[i].label=="复诊"){
        			 color = "employee_event_fz";
        		}else if(yyfl[i].label=="复查"){
        			 color = "employee_event_fc";
        		}else if(yyfl[i].label=="再消费"){
        			 color = "employee_event_zxf";
        		}else if(yyfl[i].label=="其他"){
        			 color = "employee_event_qt";
        		}
        	}
        }
        if (event.orderstatus == "1") { //已上门
            color = "employee_event_sm";
        } */
        if (event.createuser != event.askperson) { //他人预约
            color = "employee_event_fz";
        }else{//自己预约
        	color = "employee_event_fc";
        }
        
        if (isStrInArrayStringEach(event.ordertype,recesort)) { //初诊
            color = "employee_event_cz";
        }
        if (event.orderstatus == "1") { //已上门
            color = "employee_event_sm";
        }
        return color;
    };
    scheduler.templates.event_text = function(start, end, ev) { // ## 页面加载完成前，根据section数量循环执行
        return ev.text;
    };


    //根据查询 条件 获取医生列表  第一次进一面 默认展示 权限下 所有当天有预约的人
    static_doctor_list = getSelectper(regdept, doctors,null,static_organization); //## 创建日程时显示，展示医生/咨询
    
/*  scheduler.attachEvent("onAfterLightbox",
    function() { // fires after the user has closed the lightbox (edit form)
        flag = true;
    }); */

    scheduler.locale.labels.section_usercode = "病人<span style='color:red;' id='patientname'>*</span>";
    scheduler.locale.labels.section_ordertype = "预约分类<span style='color:red;'>*</span>";
    scheduler.locale.labels.section_orderstatus = "预约状态<span style='color:red;'>*</span>";
    scheduler.locale.labels.section_orderitemtype = "项目分类<span style='color:red;'>*</span>";
    scheduler.locale.labels.section_askperson = "预约医生<span style='color:red;'>*</span>";
    scheduler.locale.labels.section_orderitemdetail = "项目详情";
    scheduler.locale.labels.section_orderremark = "预约详情";
    scheduler.locale.labels.section_remark = "备注";

    // ### 单独针对表单中my_usercode这一项做的特殊处理
    scheduler.form_blocks["my_usercode"] = {
        render: function(sns) {
            return '<div class="dhx_cal_ltext" style="height:28px;"><input type="hidden" name="usercode" id="usercode" placeholder="选择患者" title="选择患者" class="form-control" style="width: 115px;" value=""/><input type="hidden" name="username" id="username" placeholder="选择患者" title="选择患者" class="form-control" style="width: 115px;" value=""/><input class="form-control yyinput"  id="usercodeDesc" name="usercodeDesc" placeholder="选择患者" style="padding-left: 2px;width:99%;font-family: Tahoma;font-size: 9pt;margin: 0;border: solid 1px #e5e5e5;border-radius: 3px;height: 28px;" onclick="getuser(\'yyzx\')"></input></div>';
        },
        set_value: function(node, value, ev) {
            if (value != "" && value != null && value != "undefined") {
            	//患者赋值增加sex/age/phone 20200428 licc
              	var url = contextPath + '/KQDS_UserDocumentAct/findByUsercode.act';
        		$.ajax({
        		    type: "POST",
        		    url: url,
        		    dataType: "json",
        		    data:{usercode:ev.usercode},
        		    success: function(r){
                        $('#usercodeDesc').val(ev.usercode+'    '+ev.username+'    '+r.sex+'    '+r.age+'    '+r.phonenumber1);
                        $('#usercode').val(value);
                        $('#username').val(ev.username);
        		    }
        		}); 
//                 $('#usercodeDesc').val(ev.username);
//                 $('#usercode').val(value);
            } else {
            	//患者赋值增加sex/age/phone 20200428 licc
				if(usercode != "" && usercode != null && usercode != "undefined"){
					var url = contextPath + '/KQDS_UserDocumentAct/findByUsercode.act';
	        		$.ajax({
	        		    type: "POST",
	        		    url: url,
	        		    dataType: "json",
	        		    data:{usercode:usercode},
	        		    success: function(r){
	                        $('#usercodeDesc').val(usercode+'    '+username+'    '+r.sex+'    '+r.age+'    '+r.phonenumber1);
	                        $('#usercode').val(usercode);
	                        $('#username').val(username);
	        		    }
	        		}); 
				}else{
					$('#usercodeDesc').val("");
                    $('#usercode').val("");
                    $('#username').val("");
				}

//                 if (usercode == null || usercode == "") {
// //                    $('#usercodeDesc').val("");
// 					$('#usercodeDesc').val(username);
//                     $('#usercode').val("");
//                 } else {
//                     $('#usercodeDesc').val(username);
//                     $('#usercode').val(usercode);
//                 }
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
    }; // ### 单独针对表单中my_usercode这一项做的特殊处理 END
    scheduler.form_blocks["my_askperson"] = {
        render: function(sns) {
            return '<div class="dhx_cal_ltext" style="height:28px;"><input type="hidden" name="askperson" id="askperson" placeholder="预约医生" title="预约医生" style="width: 80%;" value=""/>' + '<input type="text" class="form-control yyinput" id="askpersonDesc" name="askpersonDesc" placeholder="预约医生" style="padding-left: 2px;width:99%;font-family: Tahoma;font-size: 9pt;margin: 0;border: solid 1px #e5e5e5;border-radius: 3px;height: 28px;" onClick="javascript:orderOtherValidate();"></input></div>';
        },
        set_value: function(node, value, ev) {
        	
        	
            if (value != "" && value != null && value != "undefined" ) { // && !isNaN(value)
                $('#askperson').val(value);
                bindPerUserNameBySeqIdTB("askpersonDesc", value);
            } else {
                // 新增时，预约医生默认为当前登录用户
                $('#askperson').val("<%=person.getSeqId()%>");
                bindPerUserNameBySeqIdTB("askpersonDesc", "<%=person.getSeqId()%>");
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

    
    var ordertypeSections = yyfl; //## 创建日程时显示，预约分类
/*     var orderstatusSections = [ //## 创建日程时显示
    {
        key: 0,
        label: '未上门'
    },
    {
        key: 1,
        label: '已上门'
    }]; */
    var orderitemtypeSections = getSelect("YYXM"); //## 创建日程时显示，预约项目
    
    scheduler.config.lightbox.sections = [{
        name: "usercode",
        height: 40,
        type: "my_usercode",
        map_to: "usercode"
    },
    {
        name: "ordertype",
        height: 28,
        type: "select",
        options: ordertypeSections,
        map_to: "ordertype"
    },
/*     {
        name: "orderstatus",
        height: 23,
        type: "select",
        options: orderstatusSections,
        map_to: "orderstatus"
    }, */
    {
        name: "orderitemtype",
        height: 28,
        type: "select",
        options: orderitemtypeSections,
        onchange:orderitemtypeChange,
        map_to: "orderitemtype"
    },
    {
        name: "askperson",
        height: 40,
        type: "my_askperson",
        map_to: "askperson"
    },
    {
        name: "orderitemdetail",
        height: 40,
        type: "textarea",
        default_value: "",
        map_to: "orderitemdetail"
    },
    {
        name: "orderremark",
        height: 40,
        type: "textarea",
        default_value: "",
        map_to: "orderremark"
    },
    {
        name: "remark",
        height: 40,
        type: "textarea",
        default_value: "",
        map_to: "remark"
    },
//     {
//         name: "time",
//         height: 72,
//         type: "time",
//         map_to: "auto",
//         time_format: ["%Y", "%m", "%d", "%H:%i"]
//     },
    {
		name:"time", 
		height:72,
		type:"calendar_time",
		map_to:"auto"
	}    
    ];
    scheduler.attachEvent("onBeforeViewChange", function(old_mode,old_date,mode,date){
        //在用户将当前视图更改为其他视图之前触发
        if(old_date){
          	 //当前页面的日期
         	 var nowTime = new Date(old_date.getTime()) ;
         	 nowTime = getNowFormatDate(nowTime);
         	 //切换页面的日期
         	 var changeTime = new Date(date.getTime()) ;
         	 changeTime = getNowFormatDate(changeTime);
         	 //切换页面更新显示的单位列表：
         	 if(nowTime != changeTime){
         		 static_doctor_list = getSelectper(regdept, doctors,date.getTime(),static_organization); //## 创建日程时显示，展示医生/咨询
                 scheduler.updateCollection("units", static_doctor_list);
         	 }
        }
        return true;
    });
    scheduler.attachEvent("onViewChange",
    function(new_mode, new_date) {
        if (initnum > 0) {
            loaddata();
        }
        return true;
    });
    scheduler.createUnitsView({
        name: "unit",
        property: "askperson",
        size:10,
		step:10,
        //the mapped data property
        skip_incorrect: true,
        list: scheduler.serverList("units", static_doctor_list)
    });
    /***
	 * 初始日期和模式
	 * 后台的日期和模式参数，这里进行解析，并且将根据该模式，来设定日历的数据读取范围
	 * 例如日期为 2010-05-05，模式为月模式，那么现实给用户的应该是2010-05-01至2010-05-30范围的数据将这些参数传给dhtmlxScheduler的init函数
	 */
	 
    scheduler.init('scheduler_here', new Date(), "unit");
    loaddata();
}

/**
 * 获取当前门诊的所有部门列表
 */
function getAllDeptByOrganization(organization) {
	 //console.log("getAllDeptByOrganization门诊触发change------------------"+organization);
   /*  for (var i = 0; i < $(".dept").length; i++) { */ 
        var dict = $(".testDept");
        var url = contextPath + "/YZDeptAct/getAllDeptByOrganization.act?1=1";
        if (organization) {
            url += '&organization=' + organization;
        }
        $.axse(url, null,
        function(data) {
            var list = data.list;
            if (list != null && list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.deptName + "</option>");
                }
                dict.selectpicker("refresh"); //部门下拉框添加搜索功能 lutian
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
     /* } */ 
}

function getAllDeptByOrganizationTwo(firstId, twoId,showleave) {
    var dict = $("#" + twoId);
    dict.append("<option value=''>请选择</option>");
    $(document).on("change", "#" + firstId,
     function() {
        var value = $('#' + firstId).find("option:selected").val();
        if (!value) {
            dict.empty();
            return; // 终止执行
        }
        var url = contextPath + "/YZDeptAct/getAllDeptByOrganization.act?1=1";
        var currSelect = $("#" + firstId).val();
        url += '&organization=' + currSelect;
        $.axse(url, null,
        function(data) {
            if (data) {
            	dict.empty();
                dict.append("<option value=''>请选择</option>");
                dict.selectpicker('destroy');
                var list = data.list;
                if (list.length > 0) {
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.deptName + "</option>");
                    }
                    dict.selectpicker("refresh");//人员下拉框添加搜索功能 lutian
                }
            } else {
                layer.alert('查询失败', {
                      
                });
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });

    } 
    );
}


/**
 * 设定项目默认时间
 */
function orderitemtypeChange(){
	var dict = getDictByDictSeqId($(this).val());
	//console.log(dict.remark);
	if(dict.remark){
		var startdata = $(".dhx_section_time select:eq(3)").val();
		var enddata = Number(startdata)+Number(dict.remark);
		$(".dhx_section_time select:last option").each(function(){
			    if($(this).val() >= enddata){
					$(this).attr('selected',true);
					return false;
				} 
		});
	}
}

//点击查询
function loaddata() {
    var data = {
        type: 1,
        organization: static_organization,
        yyxm: yyxm,
        doctors: doctors,
        regdept: regdept,
        starttime: scheduler._min_date.getTime(),
        endtime: scheduler._max_date.getTime()
    };
//     if('<%=IS_OPEN_PAIBAN_ORDER_FUNC%>' == '1'){ 
//    	 getRestPersonListByDate();
//     } 
   
    
    var urlsearch = contextPath + '/KQDS_Hospital_OrderAct/showXML.act';
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
    function() {

	});
}

// 根据日期获取当天休息的人员列表
/* function getRestPersonListByDate(){
	
	var mode = scheduler.getState().mode;
	if(mode != 'unit'){
		return;
	}
	
	
	var perIds = "";
	
	if(static_doctor_list == null){
		return;
	}
		
	for(var i=0;i<static_doctor_list.length;i++){
		var id = static_doctor_list[i].key + '';
		if(id.indexOf('empty') >= 0 ){
			continue;
		}
		perIds += id + ',';
	}
	
	if(perIds == ""){
		return false;
	}
	
	var data = {
		perIds: perIds,
		organization: static_organization,
        starttime: scheduler._min_date.getTime(),
        endtime: scheduler._max_date.getTime()
    };
	
    var requrl = contextPath + '/KQDS_PaibanAct/getRestPersonListByDate.act';
    $.axse(requrl, data,  // 同步请求
    function(data) {
        if (data.retState == '0') {
            // scheduler.blockTime(new Date(scheduler._min_date.getTime()), "fullday", { unit: data.perIndexList });
            static_perSeqIdList = "";
            for(var i=0;i<data.perIndexList.length; i ++){
            	var pindex = data.perIndexList[i];
            	
            	static_perSeqIdList = data.perSeqIdList;// 给页面全局变量赋值【休息】
            	var pName = $(".dhx_scale_bar:eq("+pindex+")").html();
            		//console.log(pName);
            	if(!pName){
            		if(pName.indexOf("【")<0){
	            		var orderstatus = data.orderStatusList[i];
	            		if(orderstatus=="" || orderstatus=="休息"){
	            			pName += "<span style='color:red;font-size: 10px;'>【休息】</span>";
	            		}else{
	            			pName += "<span style='color:blue;font-size: 10px;'>【"+orderstatus+"】</span>";
	            		}
            		} 
            	}
            	
            	$(".dhx_scale_bar:eq("+pindex+")").html(pName);
            	
            }
            

        }
    },
    function() {

	});
	
} */

//二级联动，根据前一个select 获取后一个select  lutian
function selectChangeTwodept(firstId, twoId,showleave) {
    var dict = $("#" + twoId);
    dict.append("<option value=''>请选择</option>");
    $(document).on("change", "#" + firstId,
    function() {
        var value = $('#' + firstId).find("option:selected").val();
        if (!value) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            return; // 终止执行
        }
        var url = projectName + '/YZPersonAct/getUserListByDeptId.act?deptId=' + value+'&showleave='+showleave+'&organization=' + $("#organization").val();
        $.axse(url, null,
        function(data) {
            if (data) {
            	//console.log(JSON.stringify(data)+"----------查询返回数据");
            	dict.empty();
            	dict.append("<option value=''>请选择</option>");
            	$('#'+twoId).selectpicker('destroy');
                var list = data;
                if (list.length > 0) {
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.userName + "</option>");
                    }
                    $('#'+twoId).selectpicker("refresh");//人员下拉框添加搜索功能 lutian
                }
            } else {
                layer.alert('查询失败', {
                      
                });
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });

    });
}


//点击查询
function shuaxin() {
    yyxm = $("#yyxm").val();
    doctors = $("#doctors").val();
    if (doctors == null) {
        doctors = '';
    }

    dept = $("#regdept").val();
    organization = $("#organization").val();
    window.location.href = contextPath + "/KQDS_Net_OrderAct/toYyzx2.act?yyxm=" + yyxm + "&doctors=" + doctors + "&regdept=" + dept+ "&organization=" + organization;
}

//点击清空
function qingkong() {
	$('#yyxm').selectpicker('val', '');
	$('#doctors').selectpicker('val', '');
	$('#regdept').selectpicker('val', '');
	$('#doctors').selectpicker('val', '');
    /* $("#yyxm").val("");
    $("#doctors").val("");
    $("#regdept").val("");
    $("#doctors").val(""); */
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
                $("#doctors").val(checkerson);
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}
function exportToPDF (){
	var path = contextPath + "/yz/act/pdf/PDFGenerator/ExportPdf.act";
	var color = 'color';
	scheduler.toPDF(path, color);
}
function setHeight(){
	$("#scheduler_here").outerHeight($(window).outerHeight()-70);
}
</script>

<body onload="init();">
<div style='height:45px; padding:5px;'>
	<div class="filters_wrapper" id="filters_wrapper">
		<span>项目分类:</span>
		<label>
			<select class="dict selectInit" tig="YYXM" name="yyxm" id="yyxm" data-live-search="true"></select>
		</label>
		<span>门诊:</span>
		<label>
			<select name="organization" id="organization" ></select>
		</label>
		<span>部门:</span>
		<label>		
			<select class="dept testDept" name="regdept" id="regdept" data-live-search="true" title="请选择" style="width:130px;height:28px;"></select>
		</label>
		<span>人员:</span>
		<label>
	      <select style="width: 130px;" class="doctors"  name="doctors" id="doctors" data-live-search="true"></select>
		</label>
		<!-- <label>
			<input type="hidden" name="checkperson" id="checkperson" placeholder="咨询人员" title="咨询人员" class="form-control" /> 
			 <input type="hidden"  onchange="changeAskPerson()"   id="checkpersonDesc" name="checkpersonDesc" placeholder="咨询人员" readonly style="width: 120px;" ></input>	
		     <a onClick="javascript:select()">高级选择</a>
		</label> -->
		<label>
			<button type="button" class="search" id="searchButton" onclick="qingkong()">清空</button>
			<button type="button" style="background:#00a6c0;color:#fff;" class="search" id="searchButton" onclick="shuaxin()">查询</button>
		</label>
		<div style="float: right;">
			颜色定义：
			<!-- <button class="btn btn-cz" type="button">初诊</button>	
			<button class="btn btn-fz" type="button"> 复诊</button>
			<button class="btn btn-fc" type="button">复查</button>
			<button class="btn btn-zxf" type="button">再消费</button>
			<button class="btn btn-qt" type="button">其他</button>
			<button class="btn btn-sm" type="button">已上门</button> -->
			<button class="btn btn-fc" type="button">自己预约</button>
			<button class="btn btn-fz" type="button">他人预约</button>
			<button class="btn btn-sm" type="button">已上门</button>
			<button class="btn btn-cz" type="button">初诊</button>	
			
	</div>
	</div>
</div>

<div id="scheduler_here" class="dhx_cal_container" style='width:100%;height:400px;'>
	<div class="dhx_cal_navline">
		<!-- <div class='dhx_cal_export pdf' id='export_pdf' title='导出PDF' onclick='exportToPDF()'>&nbsp;</div> -->
		<div class="dhx_cal_prev_button">&nbsp;</div>
		<div class="dhx_cal_next_button">&nbsp;</div>
		<div class="dhx_cal_today_button"></div>
		<div class="dhx_cal_date"></div>
	    <!-- <div class="dhx_cal_tab" name="day_tab" style="right:204px;"></div> -->
		<div class="dhx_cal_tab dhx_cal_tab_first" name="week_tab" style="right:140px;"></div>
		<div class="dhx_minical_icon" id="dhx_minical_icon" style="left:288px;" onclick="show_minical()">&nbsp;</div> 
		<div class="dhx_cal_tab" name="unit_tab" style="left:211px;"></div>
		<div class="dhx_cal_tab" name="month_tab" style="right:76px;"></div>
	</div>
	<div class="dhx_cal_header">
	</div>
	<div class="dhx_cal_data">
	</div>
</div>
</body>