// 日历上部的 日期选择控件，点击弹框显示
function show_minical() {
    if (scheduler.isCalendarVisible()) {
        scheduler.destroyCalendar();
    } else {
        scheduler.renderCalendar({
            position: "dhx_minical_icon",
            date: scheduler._date,
            navigation: true,
            handler: function(date, calendar) {
                scheduler.setCurrentView(date);
                scheduler.destroyCalendar();
            }
        });
    }
}
//查询下拉选项
function getSelect(type,organization) {
    var sections = [];
    if(type!='SHOUSS'){
    	var attachmr = {};
		attachmr.key = '';
		attachmr.label = '请选择';
		sections.push(attachmr);
    }
   
    var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=" + type;
    if(organization){
    	url += "&organization=" + organization;
    }
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null && list.length > 0) {
            for (var j = 0; j < list.length; j++) {
                var attach = {};
                attach.key = list[j].seqId;
                if(list[j].remark!=''){
                	attach.label = list[j].dictName+'【'+list[j].remark+'M】';
                }else{
                	attach.label = list[j].dictName;
                }
                sections.push(attach);
            }
        }
    },
    function() {

    });
    return sections;
}
// 供select页面调用
function setSelectUserValue(usercode,username,totalcost){
	$("#usercode").val(usercode);
    $("#usercodeDesc").val(username);
    if(totalcost!= "undefined"){
    	$("#totalcost").val(totalcost);
    }else{
    	$("#totalcost").val("");
    }
}
//供select页面调用---病人信息多展示
function setSelectUserValue2(usercode,username,sex,age,tel){
 $("#usercode").val(usercode);
 $("#username").val(username);
    $("#usercodeDesc").val(usercode+"    "+username+"    "+sex+"    "+age+"    "+tel);
}
/**
 * 判断医生是否下班 或者休息  
 * @param starttime
 * @param endtime
 * @param askperson
 * @returns {Boolean}  ture 该医生在班，可以预约   false 不可以预约
 */
function checkPb(starttime, endtime, askperson) {
    var flag = false;
    if (starttime != "" && endtime != "") {
        var url = contextPath + '/KQDS_Hospital_OrderAct/checkYy2.act?starttime=' + starttime + '&endtime=' + endtime + '&askperson=' + askperson;
        $.axse(url, null,
        function(data) {
            if (data.data != "0") { // 0 代表查到0条记录
                flag = true;
            }
        },
        function() {});
    }
    return flag;
}
//-----------------------------------------------门诊预约-------------------------------
//判断医生预约
function checkYy(seqId, starttime, endtime, askperson) {
    var flag = false;
    if (starttime != "" && endtime != "") {
        //判断医生 该时间段是否已存在预约
        var url = contextPath + '/KQDS_Hospital_OrderAct/checkYy.act?seqId=' + seqId + '&starttime=' + starttime + '&endtime=' + endtime + '&askperson=' + askperson;
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                flag = true;
            } else if (data.retState == "1") {
            	flag = false;
                layer.alert(data.retMsrg, {
                      
                });
            }
        },
        function() {});
    }
    return flag;
}
//判断病人预约
function checkYyUser(seqId, starttime, endtime, usercode) {
    var flag = false;
    if (starttime != "" && endtime != "") {
        //判断病人 该时间段是否已存在预约
        var url = contextPath + '/KQDS_Hospital_OrderAct/checkYyUser.act?seqId=' + seqId + '&usercode=' + usercode + '&starttime=' + starttime + '&endtime=' + endtime;
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                flag = true;
            } else if (data.retState == "1") {  // 同一时间段有预约
            	flag = false;
                layer.alert(data.retMsrg, {
                      
                });
            }
        },
        function() {});
    }
    return flag;
}
/**
 * 查询该患者 当天是否存在预约
 * @param starttime
 * @param endtime
 * @param askperson
 * @returns {Boolean}  ture 存在   false 不存在
 */
function checkHz(starttime, usercode) {
    var flag = false;
    if (starttime != "") {
        var url = contextPath + '/KQDS_Hospital_OrderAct/checkHz.act?starttime=' + starttime + '&usercode=' + usercode;
        $.axse(url, null,
        function(data) {
            if (data.nums != "0") { // 0 代表查到0条记录
                flag = true;
                flagdata = data.data;
            }
        },
        function() {});
    }
    return flag;
}
/**
 * 判断该时间段患者手术是否存在预约（同一医生和患者，存在手术预约也可以门诊预约）
 * @param starttime
 * @param endtime
 * @param askperson
 * @returns {Boolean}  1< 存在预约 0不存在预约
 */
function checkSSSUsercode(starttime, endtime,doctor,usercode) {
    var flag = false;
    var param = {
    		starttime:starttime,
    		endtime:endtime,
    		usercode:usercode,
    		doctor:doctor
    }
    if (starttime != "" && endtime != "") {
        var url = contextPath + '/KQDS_RoomAct/checkSSSUsercode.act';
        $.axse(url, param,
        function(data) {
            if (data.data == "0") { // 0 代表查到0条记录
                flag = true;
            }else{
            	flagdata = data.retMsrg;
            }
        },
        function() {});
    }
    return flag;
}
/**
 * 判断该时间段医生手术是否存在预约（同一医生和患者，存在手术预约也可以门诊预约）
 * @param starttime
 * @param endtime
 * @param askperson
 * @returns {Boolean}  1< 存在预约 0不存在预约
 */
function checkSSSDoctor(starttime, endtime,doctor,usercode) {
    var flag = false;
    var param = {
    		starttime:starttime,
    		endtime:endtime,
    		usercode:usercode,
    		doctor:doctor
    }
    if (starttime != "" && endtime != "") {
        var url = contextPath + '/KQDS_RoomAct/checkSSSDoctor.act';
        $.axse(url, param,
        function(data) {
            if (data.data == "0") { // 0 代表查到0条记录
                flag = true;
            }else{
            	flagdata = data.retMsrg;
            }
        },
        function() {});
    }
    return flag;
}
//-----------------------------------------手术室预约---------------------------------------
/**
 * 判断该时间段手术室是否存在预约
 * @param starttime
 * @param endtime
 * @param askperson
 * @returns {Boolean}  1 存在预约 0不存在预约
 */
function checkRoom(starttime, endtime, roomid,doctor,nurse,usercode,seqId) {
    var flag = false;
    var param = {
    		starttime:starttime,
    		endtime:endtime,
    		roomid:roomid,
    		doctor:doctor,
    		nurse:nurse,
    		usercode:usercode,
    		seqId:seqId
    }
    if (starttime != "" && endtime != "") {
        var url = contextPath + '/KQDS_RoomAct/checkRoom.act';
        $.axse(url, param,
        function(data) {
            if (data.data == "0") { // 0 代表查到0条记录
                flag = true;
            }else{
            	flagdata = data.retMsrg;
            }
        },
        function() {});
    }
    return flag;
}
/**
 * 判断该时间段患者门诊是否存在预约（同一医生和患者，存在门诊预约也可以手术预约）
 * @param starttime
 * @param endtime
 * @param askperson
 * @returns {Boolean}  1< 存在预约 0不存在预约
 */
function checkOrderUsercode(starttime, endtime,doctor,usercode) {
    var flag = false;
    var param = {
    		starttime:starttime,
    		endtime:endtime,
    		usercode:usercode,
    		doctor:doctor
    }
    if (starttime != "" && endtime != "") {
        var url = contextPath + '/KQDS_RoomAct/checkOrderUsercode.act';
        $.axse(url, param,
        function(data) {
            if (data.data == "0") { // 0 代表查到0条记录
                flag = true;
            }else{
            	flagdata = data.retMsrg;
            }
        },
        function() {});
    }
    return flag;
}
/**
 * 判断该时间段医生门诊是否存在预约（同一医生和患者，存在门诊预约也可以手术预约）
 * @param starttime
 * @param endtime
 * @param askperson
 * @returns {Boolean}  1< 存在预约 0不存在预约
 */
function checkOrderDoctor(starttime, endtime,nurse,doctor,usercode) {
    var flag = false;
    var param = {
    		starttime:starttime,
    		endtime:endtime,
    		usercode:usercode,
    		nurse:nurse,
    		doctor:doctor
    }
    if (starttime != "" && endtime != "") {
        var url = contextPath + '/KQDS_RoomAct/checkOrderDoctor.act';
        $.axse(url, param,
        function(data) {
            if (data.data == "0") { // 0 代表查到0条记录
                flag = true;
            }else{
            	flagdata = data.retMsrg;
            }
        },
        function() {});
    }
    return flag;
}