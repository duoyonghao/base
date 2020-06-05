<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	//获取是否打开排班 和预约关联的 功能
	String IS_OPEN_PAIBAN_ORDER_FUNC = SysParaUtil.getSysValueByName(request, SysParaUtil.IS_OPEN_PAIBAN_ORDER_FUNC);
	if (IS_OPEN_PAIBAN_ORDER_FUNC == null) {
		IS_OPEN_PAIBAN_ORDER_FUNC = "0"; // 0 不打开  1打开
	}
	// 初诊的字典Id值，从配置文件中读取
	String roomid = request.getParameter("roomid");
	if (roomid == null) {
		roomid = "";
	}
	String zzxt = request.getParameter("zzxt");
	if (zzxt == null) {
		zzxt = "";
	}
	String nurse = request.getParameter("nurse");
	if (nurse == null) {
		nurse = "";
	}
	String nurseDesc = request.getParameter("nurseDesc");
	if (nurseDesc == null) {
		nurseDesc = "";
	}
	String doctor = request.getParameter("doctor");
	if (doctor == null) {
		doctor = "";
	}
	String doctorDesc = request.getParameter("doctorDesc");
	if (doctorDesc == null) {
		doctorDesc = "";
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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/dhtmlxscheduler/dhtmlxscheduler.css"  title="no title" charset="utf-8">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yyzx/yyzx.css"  title="no title" charset="utf-8">
<style type="text/css" media="screen">
.dhx_cal_light select {
    font-size: 9pt;
}
.dhx_cal_ltext textarea{
	background:#fff;
}
input[type="text"]:disabled{
	background:#fefefe;
}
.tooltip{
	max-width:400px;
}
.dhx_wrap_section textarea{
	overflow-y: auto;
	overflow-x:hidden;
}
.dhx_readonly {
    color: #333;
    font-size: 12px;
    height: 24px;
    width: 100px;
    text-align: center;
    border: 1px solid #e5e5e5;
}
.dhx_section_time {
    text-align: left;
    padding-left: 120px;
}

	.tooth_map{
 		width: 100%; 
	}
	.tooth_map>li{
		width:34%;
		height:30px;
		float:left;
		list-style: none;
	}
	.tooth_map>li:FIRST-CHILD{
		border-bottom: 1px solid black;
		border-right: 1px solid black;
	}
	.tooth_map>li:FIRST-CHILD+li{
		border-bottom: 1px solid black;
	}
	.tooth_map>li:FIRST-CHILD+li+li{
		border-right: 1px solid black;
	}
	.tooth_map>li>.tooth_input{
		display:block;
		width:100%;
		height:100%;
		padding:0px;
		border:0px;
		text-align: center;
		color: #00a6c0;
		font-size: 16px;
	}
</style>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/dhtmlxscheduler.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/locale_cn.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/locale_recurring_cn.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_minical.js"></script> <!-- 选择日期 -->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_units.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_tooltip.js"></script><!-- tips-->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_limit.js"></script><!-- 鼠标移上去变色 -->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/dhtmlxscheduler/ext/dhtmlxscheduler_readonly.js" charset="utf-8"></script>

<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/yyzx/room.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/yyzx/check.js"></script>
<script type="text/javascript" charset="utf-8">
var roomid = "<%=roomid%>"; // 查询条件，手术室 
var zzxt = "<%=zzxt%>"; // 查询条件，种植系统
var doctor = "<%=doctor%>"; // 查询条件，医生
var fzdoctor = "<%=doctor%>"; // 查询条件，辅助医生
var nurse = "<%=nurse%>"; // 查询条件，护士
var nurseDesc ="<%=nurseDesc%>";
var doctorDesc ="<%=doctorDesc%>";
var fzdoctorDesc ="<%=doctorDesc%>";
var loginuser = "<%=person.getSeqId()%>";
var openPaiban = "<%=IS_OPEN_PAIBAN_ORDER_FUNC%>";
var flag = true; // ## 这个变量的作用是，当一个患者当天预约多次时，通过此变量的配合，可以使用layer.confirm方法，该方法是非阻塞的
var flagdata = ""; // 仅为了全局存值，用于alert展示
var initnum = 0; // 用于点击左右按钮选择日期时，触发加载数据请求
var canRoom = "<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag5_canRoom, request)%>"; //允许预约手术室 0不可以 1 可以
var static_organization = null; //门诊

// ### 当天不上班的医生不能预约  17-5-26 add  ys
var static_rom_list = null;  // 全局变量，存储展示的人员列表  key label

//初始化
function init() {
	if('<%=organization%>' != ''){
		static_organization = '<%=organization%>';
	}else{
		static_organization = '<%=ChainUtil.getCurrentOrganization(request)%>';
	}
	
	initHosSelectListNoCheck('organization',static_organization); // 加载门诊列表
	$("#organization").change(function() {
	    var currSelect = $(this).val();
	    qingkong();
	    $("#organization").val(currSelect);
	    initDictSelectByClassOrg1(currSelect);
	});
	 
	initDictSelectByClassOrg1(static_organization); //加载下拉
    $("#roomid").val(roomid);
    $("#doctor").val(doctor);
    $("#fzdoctor").val(doctor);
    $("#nurse").val(nurse);
    $("#nurseDesc").val(nurseDesc);
    $("#doctorDesc").val(doctorDesc);
    $("#fzdoctorDesc").val(fzdoctorDesc);
    $("#zzxt").val(zzxt);
    setHeight();//设置日历高度
    scheduler.locale.labels.unit_tab = "预约";
    scheduler.config.xml_date = "%Y-%m-%d %H:%i";
    scheduler.config.prevent_cache = true;
    scheduler.config.first_hour = 8; //预约表开始时间
    scheduler.config.last_hour = 20; //预约表结束时间
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
    
    //在动态加载的情况下 ，将服务器请求参数的格式
    //scheduler.config.load_date = "%Y-%m-%d";
    var step = 15;
    var format = scheduler.date.date_to_str("%H:%i");
    scheduler.config.hour_size_px = (60 / step) * 22;
    scheduler.templates.hour_scale = function(date) {
        html = "";
        for (var i = 0; i < 60 / step; i++) {
            html += "<div style='height:22px;line-height:22px;'>" + format(date) + "</div>";
            date = scheduler.date.add(date, step, "minute");
        }
        return html;
    };
    
    function block_readonly_onBeforeDrag(id, mode, e) {
        if (id) {
            var flag = true;
            var urlsearch = contextPath + '/KQDS_RoomAct/selectDetail.act?seqId=' + id;
            $.axse(urlsearch, null,
            function(data) {
                if (data.retState == "0") {
                    //当天之前 打开readonly页面
                    var starttime = data.data.starttime.substring(0,10);
                    var nowdate = getNowFormatDate();
                    var checkdatez = checkdate(starttime, nowdate); // 0 预约开始时间 大于 当前时间  1预约开始时间 小于当前时间 2 预约开始时间等于当前时间
                    if (checkdatez == 1) { // 即打开的时候，过了预约时间的，只读，不能编辑了
                        flag = false; // 此时设置为 readonly
                    }
                    //手术后 不能改
                    if (data.data.roomstatus == 2) {
                        flag = false;
                    }
                    //取消预约
                    if (data.data.isdelete == 1) {
                        flag = false;
                    }
                    //没有预约权限
                    if (canRoom!=1) {
                        flag = false;
                    }
                }
            },
            function() {

			}); 
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
    scheduler.attachEvent("onBeforeDrag", block_readonly_onBeforeDrag);
    scheduler.attachEvent("onClick", block_readonly_onBeforeDrag);
    
    // 非空闲手术室，无法预约
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
			//没有预约权限
            if (canRoom!=1) {
            	layer.alert('手术室预约，请联系手术室管理人员进行预约。', {
                      
                });
            	this._lightbox_id = id; // 权限不足时，删掉展示的 "新建"
	            this.cancel_lightbox();
   		        return false;
            }
    		if(id.length == 36){ // 编辑或查看，直接跳过，不做检查
    			return true;
    		}
	   		var starttime = getNowFormatDate(this.getEvent(id).start_date);
	        var nowdate = getNowFormatDate();
	        var checkdatez = checkdate(starttime, nowdate); // 0 预约开始时间 大于 当前时间  1预约开始时间 小于当前时间 2 预约开始时间等于当前时间
	        if (checkdatez == 1) { // 即打开的时候，过了预约时间的，只读，不能编辑了
	        	 layer.alert('预约时间已过', {
	   		              
	   		        });
	   		   		this._lightbox_id = id; // 权限不足时，删掉展示的 "新建"
		            this.cancel_lightbox();
	   		        return false;
	        }
    		// 空白区域，不允许操作
    		if((this.getEvent(id).roomid + '').indexOf('empty') >= 0 ){
    			layer.alert('请在合适范围内操作！', {
                      
                });
    		   this._lightbox_id = id; // 权限不足时，删掉展示的 "新建"
   	           this.cancel_lightbox();
   	           return false;
    		}
    		//验证该时间段内 手术室、医生、护士 是否存在预约
    		var B = this.getEvent(id).start_date; 
    		var ss = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate() + " " + B.getHours() + ":" + B.getMinutes() + ":" + B.getSeconds();
		    var B = this.getEvent(id).end_date;
		    var ss1 = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate() + " " + B.getHours() + ":" + B.getMinutes() + ":" + B.getSeconds();
    		if (!checkRoom(ss,ss1,this.getEvent(id).roomid,"","","")) {
                layer.alert(flagdata, {
                      
            	});
                this._lightbox_id = id; // 权限不足时，删掉展示的 "新建"
   	            this.cancel_lightbox();
   	            return false;
            }
           
        return true;
	});
    
    
    scheduler.templates.tooltip_text = function(start, end, ev) {
        var orderstatusstr = "";
        if (ev.roomstatus==0) { 
        	orderstatusstr = "<span>手术前</span>";
        }else if(ev.roomstatus==1){
        	orderstatusstr = "<span style='color:red;'>手术中</span>";
        }else{
        	orderstatusstr = "<span>手术后</span>";
        }
        var tooth=ev.tooth.split(";");
       	if(tooth.length==1){
       		for (var i = 0; i < 3; i++) {
       			tooth.push("");
			}
       	}
        //鼠标指到悬浮提示信息
        return  "<b>手术状态:</b> " + orderstatusstr + 
       			"<br/><br/><b>手术室:</b> " + ev.roomname + 
		        "<br/><b>患者:</b> " +ev.usercode+'  '+ev.username+'  '+ev.sex+'  '+ev.age+'  '+ev.phonenumber1 + 
		        "<br/><b>开始时间:</b> " + scheduler.templates.tooltip_date_format(start) + 
		        "<br/><b>结束时间:</b> " + scheduler.templates.tooltip_date_format(end) + 
		        "<br/><b>医生:</b> " + ev.doctorname + 
		        /*"<br/><b>辅助医生:</b> " + ev.fzdoctorname +*/
		        "<br/><b>护士:</b> " + ev.nursename + 
		        "<br/><b>二期医生:</b> " + ev.phasedoctorname + 
		        "<br/><b>咨询:</b> " + ev.askpersonname + 
		        "<br/><b>种植系统:</b> " + ev.zzxtname + 
		        "<br/><b>牙位:</b> " +'<div><ul class="tooth_map">'+
		    	'<li><input value="'+tooth[0]+'" class="tooth_input" type="text" readonly="readonly"/></li>'+
		    	'<li><input value="'+tooth[1]+'" class="tooth_input" type="text" readonly="readonly"/></li>'+
		    	'<li><input value="'+tooth[2]+'" class="tooth_input" type="text" readonly="readonly"/></li>'+
		    	'<li><input value="'+tooth[3]+'" class="tooth_input" type="text" readonly="readonly"/></li>'+
		    	'</ul></div><br/><br/><br/>' + 
		        "<br/><b>颗数:</b> " + ev.ks + 
		        "<br/><b>创建人:</b> " + ev.createusername + 
		        "<br/><b>备注:</b> " + ev.remark ;
    };
    scheduler.templates.event_class = function(start, end, ev) { // ## 页面加载完成前，根据section数量循环执行
        var color = "";
        if (ev.roomstatus==0) { //手术前
            color = "employee_event_cz";
        }else if(ev.roomstatus==1){
        	color = "employee_event_fz";
        }else{//自己预约
        	color = "employee_event_sm";
        }
        return color;
    };
    scheduler.templates.event_text = function(start, end, ev) { // ## 页面加载完成前，根据section数量循环执行
        return ev.text;
    };


    //根据查询 条件 获取医生列表  第一次进一面 默认展示 权限下 所有当天有预约的人
    static_rom_list =  getSelect("SHOUSS", static_organization); //## 创建日程时显示，展示手术室
    //种植系统
    var zzxtSections = getSelect("ZZXT"); //## 创建日程时显示，预约项目
    //
    var roomstatusSections = [ //## 手术状态
    {
        key: 0,
        label: '手术前'
    },
    {
        key: 1,
        label: '手术中'
    },
    {
        key: 2,
        label: '手术后'
    }];
    scheduler.locale.labels.section_roomid = "手术室";
    scheduler.locale.labels.section_roomstatus = "手术状态<span style='color:red;'>*</span>";
    scheduler.locale.labels.section_usercode = "病人<span style='color:red;'>*</span>";
    scheduler.locale.labels.section_doctor = "预约医生<span style='color:red;'>*</span>";
    /*scheduler.locale.labels.section_fzdoctor = "辅助医生<span style='color:red;'>*</span>";*/
    scheduler.locale.labels.section_nurse = "预约护士";
    scheduler.locale.labels.section_phasedoctor = "二期医生";
    scheduler.locale.labels.section_askperson = "咨询";
    scheduler.locale.labels.section_zzxt = "种植系统<span style='color:red;'>*</span>";
    scheduler.locale.labels.section_tooth = "牙位图<span style='color:red;'>*</span>";
    scheduler.locale.labels.section_ks = "颗数";
    scheduler.locale.labels.section_remark = "备注";

    // ### 单独针对表单中my_usercode这一项做的特殊处理
    scheduler.form_blocks["my_usercode"] = {
        render: function(sns) {
            return '<div class="dhx_cal_ltext" style="height:30px;">'+
            '<input type="hidden" name="usercode" id="usercode"/>'+
            '<input type="text" id="usercodeDesc" name="usercodeDesc"  style="padding-left: 2px;width:99%;font-family: Tahoma;font-size: 9pt;margin: 0;border: solid 1px #e5e5e5;border-radius: 3px;height: 28px;" onclick="getuser()"></input>'+
            '<input type="hidden" name="totalcost" id="totalcost"/></div>';
        },
        set_value: function(node, value, ev) {
            if (value != "" && value != null && value != "undefined") {
                $('#usercodeDesc').val(ev.usercode+'  '+ev.username+'  '+ev.sex+'  '+ev.age+'  '+ev.phonenumber1);
                $('#usercode').val(value);
            } else {
                $('#usercodeDesc').val("");
                $('#usercode').val("");
            }
        },
        get_value: function(node, ev) {
            ev.details = node.childNodes[1].value;
            ev.totalcost=node.childNodes[2].value;
            return node.childNodes[0].value;
        },
        focus: function(node) {
            var a = node.childNodes[1];
            a.select();
            a.focus();
        }
    }; // ### 单独针对表单中my_usercode这一项做的特殊处理 END
    scheduler.form_blocks["my_doctor"] = {
        render: function(sns) {
            return '<div class="dhx_cal_ltext" style="height:30px;">'+
            '<input type="hidden" name="doctor" id="doctor"/>' + 
            '<input type="text" id="doctorDesc" name="doctorDesc" style="padding-left: 2px;width:99%;font-family: Tahoma;font-size: 9pt;margin: 0;border: solid 1px #e5e5e5;border-radius: 3px;height: 28px;" onClick="javascript:single_select_user([\'doctor\', \'doctorDesc\']);"></input></div>';
        },
        set_value: function(node, value, ev) {
            if (value != "" && value != null && value != "undefined" ) {
                $('#doctor').val(value);
                bindPerUserNameBySeqIdTB("doctorDesc", value);
            } else {
                $('#doctor').val("");
                $('#doctorDesc').val("");
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
    //辅助医生
    /*scheduler.form_blocks["my_fzdoctor"] = {
            render: function(sns) {
                return '<div class="dhx_cal_ltext" style="height:30px;">'+
                '<input type="hidden" name="fzdoctor" id="fzdoctor"/>' + 
                '<input type="text" id="fzdoctorDesc" name="fzdoctorDesc" style="padding-left: 2px;width:99%;font-family: Tahoma;font-size: 9pt;margin: 0;border: solid 1px #e5e5e5;border-radius: 3px;height: 28px;" onClick="javascript:single_select_user([\'fzdoctor\', \'fzdoctorDesc\']);"></input></div>';
            },
            set_value: function(node, value, ev) {
            	//console.log(value+"--------辅助医生值");
            	//console.log(node+"---------辅助医生节点");
                if (value != "" && value != null && value != "undefined" ) {
                    $('#fzdoctor').val(value);
                    bindPerUserNameBySeqIdTB("fzdoctorDesc", value);
                } else {
                    $('#fzdoctor').val("");
                    $('#fzdoctorDesc').val("");
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
        };*/
    //护士
    scheduler.form_blocks["my_nurse"] = {
            render: function(sns) {
                return '<div class="dhx_cal_ltext" style="height:30px;">'+
                '<input type="hidden" name="nurse" id="nurse" value=""/>' + 
                '<input type="text" id="nurseDesc" name="nurseDesc" style="padding-left: 2px;width:99%;font-family: Tahoma;font-size: 9pt;margin: 0;border: solid 1px #e5e5e5;border-radius: 3px;height: 28px;" onClick=" single_select_user([\'nurse\', \'nurseDesc\']);"></input></div>';
            },
            set_value: function(node, value, ev) {
                if (value != "" && value != null && value != "undefined") {
                    $('#nurse').val(value);
                    bindPerUserNameBySeqIdTB("nurseDesc", value);
                } else {
                	$('#nurse').val("");
                    $('#nurseDesc').val("");
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
    //咨询
    scheduler.form_blocks["my_askperson"] = {
            render: function(sns) {
                return '<div class="dhx_cal_ltext" style="height:30px;">'+
                '<input type="hidden" name="askperson" id="askperson" value=""/>' + 
                '<input type="text" id="askpersonDesc" name="askpersonDesc"  autocomplete="off" style="padding-left: 2px;width:99%;font-family: Tahoma;font-size: 9pt;margin: 0;border: solid 1px #e5e5e5;border-radius: 3px;height: 28px;" onClick=" single_select_user([\'askperson\', \'askpersonDesc\']);"></input></div>';
            },
            set_value: function(node, value, ev) {
                if (value != "" && value != null && value != "undefined") {
                    $('#askperson').val(value);
                    bindPerUserNameBySeqIdTB("askpersonDesc", value);
                } else {
                	$('#askperson').val("");
                    $('#askpersonDesc').val("");
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
    //二期医生
    scheduler.form_blocks["my_phasedoctor"] = {
            render: function(sns) {
                return '<div class="dhx_cal_ltext" style="height:30px;">'+
                '<input type="hidden" name="phasedoctor" id="phasedoctor" value=""/>' + 
                '<input type="text" id="phasedoctorDesc" name="phasedoctorDesc"  autocomplete="off" style="padding-left: 2px;width:99%;font-family: Tahoma;font-size: 9pt;margin: 0;border: solid 1px #e5e5e5;border-radius: 3px;height: 28px;" onClick=" single_select_user([\'phasedoctor\', \'phasedoctorDesc\']);"></input></div>';
            },
            set_value: function(node, value, ev) {
                 if (value != "" && value != null && value != "undefined") {
                    $('#phasedoctor').val(value);
                    bindPerUserNameBySeqIdTB("phasedoctorDesc", value);
                } else {
                	$('#phasedoctor').val("");
                    $('#phasedoctorDesc').val("");
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
  //牙位图
    scheduler.form_blocks["my_tooth"] = {
            render: function(sns) {
            	return '<div class="dhx_cal_ltext" style="height:80px;">'+
            	'<ul class="tooth_map">'+
            	'<li><input id="uplefttoothbit" class="tooth_input" type="text"/></li>'+
            	'<li><input id="uperrighttoothbit" class="tooth_input" type="text"/></li>'+
            	'<li><input id="leftlowertoothbit" class="tooth_input" type="text"/></li>'+
            	'<li><input id="lowrighttoothbit" class="tooth_input" type="text"/></li>'+
            	'</ul></div>';
            },
            set_value: function(node, value, ev) {
                if (value != "" && value != null && value != "undefined") {
                    var tooth=value.split(";");
                    $('#uplefttoothbit').val(tooth[0]);
                    $('#uperrighttoothbit').val(tooth[1]);
                    $('#leftlowertoothbit').val(tooth[2]);
                    $('#lowrighttoothbit').val(tooth[3]);
                }
            },
            get_value: function(node, ev) {
            	var tooth=[];
            	for (var i = 0; i < node.firstChild.childNodes.length; i++) {
            		tooth.push(node.firstChild.childNodes[i].firstChild.value);
				}
                ev.tooth = tooth[0]+";"+tooth[1]+";"+tooth[2]+";"+tooth[3];
                return tooth[0]+";"+tooth[1]+";"+tooth[2]+";"+tooth[3];
            },
            focus: function(node) {
                var a = node.childNodes[1];
                a.select();
                a.focus();
            }
        };
    scheduler.config.lightbox.sections = [
    {
        name: "roomid",
        height: 30,
        type: "select",
        options: static_rom_list,
        map_to: "roomid"
    },
    {
        name: "roomstatus",
        height: 30,
        type: "select",
        options: roomstatusSections,
        map_to: "roomstatus"
    },
    {
        name: "usercode",
        height: 30,
        type: "my_usercode",
        map_to: "usercode"
    },
    {
        name: "doctor",
        height: 30,
        type: "my_doctor",
        map_to: "doctor"
    },
    /*{
        name: "fzdoctor",
        height: 30,
        type: "my_fzdoctor",
        map_to: "fzdoctor"
    },*/
    {
        name: "nurse",
        height: 30,
        type: "my_nurse",
        map_to: "nurse"
    },
    {
        name: "phasedoctor",
        height: 30,
        type: "my_phasedoctor",
        map_to: "phasedoctor"
    },
    {
        name: "askperson",
        height: 30,
        type: "my_askperson",
        map_to: "askperson"
    },
    {
        name: "zzxt",
        height: 30,
        type: "select",
        options: zzxtSections,
        map_to: "zzxt"
    },
    {
        name: "tooth",
        height: 80,
        type: "my_tooth",
        default_value: "",
        map_to: "tooth"
    },
    {
        name: "ks",
        height: 30,
        type: "textarea",
        default_value: "",
        map_to: "ks"
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
// //         time_format: ["%Y", "%m", "%d", "%H:%i"],
//     },
    {
		name:"time", 
		height:72,
		type:"calendar_time",
		map_to:"auto"
	}    
    ];

    scheduler.attachEvent("onViewChange",
    function(new_mode, new_date) {
        if (initnum > 0) {
            loaddata();
        }
        return true;
    });
    scheduler.createUnitsView({
        name: "unit",
        property: "roomid",
        skip_incorrect: true,
        list: static_rom_list
    });
    scheduler.init('scheduler_here', new Date(), "unit");
    loaddata();
}
//点击查询
function loaddata() {
    var data = {
        type: 1,
        organization: static_organization,
        roomid: roomid,
        zzxt:zzxt,
        nurse: nurse,
        doctor: doctor,
        fzdoctor: fzdoctor,
        starttime: scheduler._min_date.getTime(),
        endtime: scheduler._max_date.getTime()
    };
    
    var urlsearch = contextPath + '/KQDS_RoomAct/showXML.act';
    $.axse(urlsearch, data,
    function(data) {
        if (data.result == 'ok') {
            var json = data.path;
            //json = eval('(' + json + ')');

            scheduler.clearAll(); // 先清掉  再加载
            scheduler.parse(json, "json");
            initnum++;
        }
    },
    function() {

	});
}

//点击查询
function shuaxin() {
    roomid = $("#roomid").val();
    zzxt = $("#zzxt").val();
    nurse = $("#nurse").val();
    doctor = $("#doctor").val();
    fzdoctor = $("#fzdoctor").val();
    nurseDesc = $("#nurseDesc").val();
    doctorDesc = $("#doctorDesc").val();
    /*fzdoctorDesc = $("#fzdoctorDesc").val();*/
    organization = $("#organization").val();
    window.location.href = contextPath + "/KQDS_RoomAct/toRoom.act"+
    "?roomid=" + roomid + "&zzxt=" + zzxt + "&nurse=" + nurse + "&doctor=" + doctor+"&nurseDesc="+nurseDesc+"&doctorDesc="+doctorDesc+"&organization="+organization;
}

//点击清空
function qingkong() {
    $(".filters_wrapper :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心

}
function setHeight(){
	$("#scheduler_here").outerHeight($(window).outerHeight()-55);
}
function initDictSelectByClassOrg1(organization) {
    if ($(".dict").length > 0) {
        for (var i = 0; i < $(".dict").length; i++) {
            var dict = $(".dict").eq(i);
            // :eq() 选择器选取带有指定 index 值的元素。index值从 0 开始，所有第一个元素的 index 值是 0（不是1）。
            var type = dict.attr("tig");
            var url = contextPath + "/YZDictAct/getListByParentCodeOrg.act?parentCode=" + type;
            url += "&organization=" + organization;
            $.axse(url, null,
            function(data) {
                var list = data.list;
                if (list != null && list.length > 0) {
                    dict.empty();
                    dict.append("<option value=''>请选择</option>");
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                    }
                }else{
                	dict.empty();
                    dict.append("<option value=''>请选择</option>");
                }
            },
            function() {
                layer.alert('查询出错！', {
                });
            });
        }
    }
}
</script>

<body onload="init();">
<div style='height:45px; padding:5px;'>
	<div class="filters_wrapper" id="filters_wrapper">
		<span>门诊:</span>
		<label>
			<select name="organization" id="organization" ></select>
		</label>
		<span>手术室:</span>
		<label>
			<select class="dict" tig="SHOUSS" name="roomid" id="roomid"></select>
		</label>
		<span>种植系统:</span>
		<label>
			<select class="dict" tig="ZZXT" name="zzxt" id="zzxt" ></select>
		</label>
		<span>医生:</span>
		<label>
			<input type="hidden" name="doctor" id="doctor"/> 
			<input type="text" style="width: 80px;border: solid 1px #e5e5e5;border-radius: 3px;height: 24px;" id="doctorDesc" name="doctorDesc" placeholder="医生" readonly
			onClick="javascript:single_select_user(['doctor', 'doctorDesc']);"></input>
		</label>
		<!--<span>辅助医生:</span>
		<label>
			<input type="hidden" name="fzdoctor" id="fzdoctor"/> 
			<input type="text" style="width: 80px;border: solid 1px #e5e5e5;border-radius: 3px;height: 24px;" id="fzdoctorDesc" name="fzdoctorDesc" placeholder="辅助医生" readonly
			onClick="javascript:single_select_user(['fzdoctor', 'fzdoctorDesc']);"></input>
		</label>-->
		<span>护士:</span>
		<label>
	     	<input type="hidden" name="nurse" id="nurse"/> 
			<input type="text" style="width: 80px;border: solid 1px #e5e5e5;border-radius: 3px;height: 24px;" id="nurseDesc" name="nurseDesc" placeholder="护士" readonly
			onClick="javascript:single_select_user(['nurse', 'nurseDesc']);"></input>
		</label>
		<label>
			<button type="button" class="search" id="searchButton" onclick="qingkong()">清空</button>
			<button type="button" style="background:#00a6c0;color:#fff;" class="search search" id="searchButton" onclick="shuaxin()">查询</button>
		</label>
		<div style="float: right;">
			颜色定义：
			<button class="btn btn-cz" type="button">手术前</button>
			<button class="btn btn-fz" type="button">手术中</button>
			<button class="btn btn-sm" type="button">手术后</button>
	</div>
	</div>
</div>

<div id="scheduler_here" class="dhx_cal_container" style='width:100%; height:90%;'>
	<div class="dhx_cal_navline">
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