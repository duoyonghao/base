// 新增或修改操作前，都会先调用此方法   
scheduler.attachEvent("onEventSave",
function(id, e) {
    //新增
    if (id.toString() != null && id.toString().length < 36) {
        id = "";
    }
    if (canOrderOther == 0 && e.askperson != loginuser) {
        layer.alert('权限不足！无法给他人预约！' );
        return false;
    }
    if (e.orderstatus == "1") { // 1为已上门
        layer.alert('新建预约不能选择已上门' );
        return false;
    }
    if (e.usercode == "") {
        layer.alert('请选择患者' );
        return false;
    }
    if (e.ordertype == "") {
        layer.alert('请选择预约分类' );
        return false;
    }
/*    if (e.orderstatus == "") {
        layer.alert('请选择预约状态' );
        return false;
    }*/
    if (e.orderitemtype == "") {
        layer.alert('请选择预约项目分类' );
        return false;
    }
    if (e.askperson == "") {
        layer.alert('请选择预约医生' );
        return false;
    }
    var timestamp = Date.parse(new Date());
    var starttime = Date.parse(e.start_date);
    if (timestamp > starttime) { // 新增预约时，判断所选的时间段是否超时
        layer.alert('预约时间已过' );
        return false;
    }
    
    var B = e.start_date; 
    var sortstart =  B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate();
    var ss = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate() + " " + B.getHours() + ":" + B.getMinutes() + ":" + B.getSeconds();
    var B = e.end_date;
    var sortend = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate();
    var ss1 = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate() + " " + B.getHours() + ":" + B.getMinutes() + ":" + B.getSeconds();
    var timestamp4 =new Date(starttime)
   //    var time = timestamp4.toLocaleDateString().replace(/\//g, "-") + " " + timestamp4.toTimeString().substr(0, 5)
   //    console.log(timestamp4,"==========sortstart")
    
    function Appendzero(obj) {
            if (obj < 10) return "0" + obj; 
            else return obj
        }
        var myDate = timestamp4
        var getyear = myDate.getFullYear()
        var getMonth = myDate.getMonth() + 1
        var getDate = myDate.getDate()
        var getHours = myDate.getHours()
        var getMinutes = myDate.getMinutes()

        var riqi =Appendzero(getyear) + '-' + Appendzero(getMonth) + '-' +Appendzero(getDate)
        var shijian = Appendzero(getHours) + ':' + Appendzero(getMinutes)
        var alltime = riqi + '  ' + shijian
//        console.log(alltime)
      
    
    //向父页面传值：预约时间  2019/7/18
    if(parent.reloadVal=="0"){
      if($("#username").val() != username){
    	 layer.alert('不是当前患者' );
		 return;
	 }
	}
   
    if(window.parent.frames["myiframe"]){
        //向父页面传值：预约时间  2019/7/18
           $(window.parent.frames["myiframe"].document).find("#next_hospital_time").val(alltime);
           $(window.parent.frames["myiframe"].document).find("#review_time").val(alltime);
       }    
    if(sortstart != sortend){
    	layer.alert('不能跨天预约' );
        return false;
    }
    var checkbool = checkYy(id, ss, ss1, e.askperson);
    if (!checkbool) {
        layer.alert('该时间段医生已有预约' );
        return false;
    }
    var checkuser = checkYyUser(id, ss, ss1, e.usercode); // 提示信息放到方法里面了
    if (!checkuser) {
        return false;
    }
    if(openPaiban == '1'){
	    var checkYspb = checkPb(ss, ss1, e.askperson); 
	    if (!checkYspb) {
	    	layer.alert('非值班人员，无法预约！' );
	        return false;
	    }
    }
    //判断该时间段医生手术是否存在预约（同一医生和患者，存在手术预约也可以门诊预约）
    if(!checkSSSDoctor(ss,ss1,e.askperson,e.usercode)){
	 	layer.alert(flagdata, {
              
            end: function() {
            	loaddata();
            }
        });
        return false;  
	 }
    //判断该时间段患者手术是否存在预约（同一医生和患者，存在手术预约也可以门诊预约）
    if(!checkSSSUsercode(ss,ss1,e.askperson,e.usercode)){
	 	layer.alert(flagdata, {
              
            end: function() {
            	loaddata();
            }
        });
        return false;  
	 }
    if (id == "") {
        var B = e.start_date; 
        var ss = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate() + " " + B.getHours() + ":" + B.getMinutes() + ":" + B.getSeconds();
        var hzyy = checkHz(ss, e.usercode);
        if (hzyy && flag) {
        	layer.confirm('该患者当天已有预约，是否继续预约？<br>' + flagdata, {
        		btn: ['确定','取消'] //按钮
        	}, function(){
        		flag = false;
        		scheduler.save_lightbox();
         	});
        }else{
        	return true; // 当天没有预约过的，直接保存
        }
    }else{
    	return true; // 编辑时，直接保存
    }
});

// 新增操作  
scheduler.attachEvent("onEventAdded",
function(id, e, is_new) {
    var B = e.start_date; //alert(dateStart);
    var ss = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate() + " " + B.getHours() + ":" + B.getMinutes() + ":" + B.getSeconds();
    var B = e.end_date; //alert(dateStart);
    var ss1 = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate() + " " + B.getHours() + ":" + B.getMinutes() + ":" + B.getSeconds();
    addyy(e, ss, ss1);
    return true;
});
function addyy(e, ss, ss1) {
    var param = {
    	order_number : order_number,
    	nodeId : nodeId,
        usercode: e.usercode,
        username: $('#username').val(),
        ordertype: e.ordertype,
        orderstatus: e.orderstatus,
        orderitemtype: e.orderitemtype,
        askperson: e.askperson,
        orderitemdetail: e.orderitemdetail,
        orderremark: e.orderremark,
        remark: e.remark,
        starttime: ss,
        endtime: ss1  // ###新增和编辑的区别是，没有传regdept 和seqId
    };
    
    if(static_organization){ // 直接从页面全局变量取值
    	param.organization = static_organization;
    }
    
    var url = contextPath + '/KQDS_Hospital_OrderAct/insertOrUpdate.act';
    var msg;
    $.axseSubmit(url, param,
    function() {
        msg = layer.msg('加载中', {
            icon: 16
        });
    },
    function(r) {
        if (r.retState == "0") {
            layer.alert('预约成功', {
                  
                end: function() {
                	loaddata();
                	  var frameindex = parent.layer.getFrameIndex(window.name);
                      window.parent.layer.close(frameindex); //再执行关闭
                }
            });

        } else {
            layer.alert('预约失败', {
                  
                end: function() {
                	loaddata();
                }
            });
        }
    },
    function() {
        layer.alert('预约失败', {
              
            end: function() {
            	loaddata();
            }
        });
    });
}
//修改处理 得到填写的数据，然后利用ajax提交到后台 
scheduler.attachEvent("onEventChanged",
function(id, e) {
    var B = e.start_date; //alert(dateStart);
    var ss = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate() + " " + B.getHours() + ":" + B.getMinutes() + ":" + B.getSeconds();
    var B = e.end_date; //alert(dateStart);
    var ss1 = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate() + " " + B.getHours() + ":" + B.getMinutes() + ":" + B.getSeconds();
    var checkbool = checkYy(id, ss, ss1, e.askperson);
    if (!checkbool) {
        layer.alert('该时间段医生已有预约' );
        return;
    }
    var timestamp = Date.parse(new Date());
    var starttime = Date.parse(e.start_date);
    if (timestamp > starttime) { // 新增预约时，判断所选的时间段是否超时
        layer.alert('预约时间已过', {
              
            end: function() {
            	loaddata();
            }
        });
        return false;
    }
    if(openPaiban == '1'){
	    var checkYspb = checkPb(ss, ss1, e.askperson); 
	    if (!checkYspb) {
	    	layer.alert('非值班人员，无法预约！', {
	              
	            end: function() {
	            	loaddata();
	            }
	        });
	        return;
	    }
    }
    //判断该时间段医生手术是否存在预约（同一医生和患者，存在手术预约也可以门诊预约）
    if(!checkSSSDoctor(ss,ss1,"",e.doctor,e.usercode)){
	 	layer.alert(flagdata, {
              
            end: function() {
            	loaddata();
            }
        });
        return false;  
	 }
    //判断该时间段患者手术是否存在预约（同一医生和患者，存在手术预约也可以门诊预约）
    if(!checkSSSUsercode(ss,ss1,"",e.doctor,e.usercode)){
	 	layer.alert(flagdata, {
              
            end: function() {
            	loaddata();
            }
        });
        return false;  
	 }
    var param = {
    	order_number : order_number,//传值用于修改预约时间
        nodeId : nodeId,
        seqId: e.id,
        usercode: e.usercode,
        username: $('#username').val(),
        ordertype: e.ordertype,
        orderstatus: e.orderstatus,
        orderitemtype: e.orderitemtype,
        askperson: e.askperson,
        orderitemdetail: e.orderitemdetail,
        orderremark: e.orderremark,
        regdept: e.regdept,
        remark: e.remark,
        starttime: ss,
        endtime: ss1 // ###新增和编辑的区别是，没有传regdept 和seqId
    };
    
    if(static_organization){ // 直接从页面全局变量取值
    	param.organization = static_organization;
    }
    
    var url = contextPath + '/KQDS_Hospital_OrderAct/insertOrUpdate.act';
    var msg;
    $.axseSubmit(url, param,
    function() {
        msg = layer.msg('加载中', {
            icon: 16
        });
    },
    function(r) {
    	if($("#username").val() != username){
    		return;
    	}
        if (r.retState == "0") {
            layer.alert('保存成功', {
                  
                end: function() {
                	loaddata();
                	 var frameindex = parent.layer.getFrameIndex(window.name);
                     window.parent.layer.close(frameindex); //再执行关闭
                }
            });
        } else {
            layer.alert('保存失败', {
                  
                end: function() {
                	loaddata();
                }
            });
        }
    },
    function() {
        layer.alert('保存失败', {
              
            end: function() {
            	loaddata();
            }
        });
    });
    return true;
});

//删除操作
scheduler.attachEvent("onBeforeEventDelete",
function(id, e) {
    if (e.createuser == null || e.createuser == "undefined" || e.createuser == "") {
        scheduler.cancel_lightbox();
        return;
    }
    layer.prompt({
        title: '取消预约原因',
        formType: 0
    },
    function(delreason, index) {
        layer.close(index);
        var url = contextPath + '/KQDS_Hospital_OrderAct/deleteObj.act?seqId=' + id + '&delreason=' + delreason + '&order_number=' + order_number + '&nodeId=' + nodeId;
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                layer.alert('取消预约成功', {
                      
                    end: function() {
                    	loaddata();
                    	if(parent.reloadVal=="0"){
                    		parent.location.reload();
                    	}else{
                    		location.reload();
                    	}
                    }
                });
            }
        },
        function() {
            layer.alert('取消预约出错！', {
                  
                end: function() {
                	loaddata();
                }
            });
        });
    });
});

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

// 供select页面调用
function setSelectUserValue(usercode,username){
	$("#usercode").val(usercode);
    $("#usercodeDesc").val(username);
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

//查询部门下人员
function getSelectper(deptId, perId,yydate,organization) {
    var sections = [];
    var url = contextPath + "/KQDS_Hospital_OrderAct/getPersonBydeptId.act?deptId=" + deptId + "&seqId=" + perId;
    if(organization){
    	url += '&organization=' + organization;
    }
    if(yydate){
    	url += '&yydate=' + yydate;
    }
    $.axse(url, null,
    function(data) {
        var list = data.list;
        static_perSeqIdList = data.perSeqIdList;
        if (list != null && list.length > 0) {
            for (var j = 0; j < list.length; j++) {
                var attach = {};
                attach.key = list[j].seqId;
                attach.label = list[j].userName;
                sections.push(attach);
            }
            if (list.length < 10) { //默认展示10列  查询结果不足10列  补空值
                var len = 10 - list.length;
                for (var k = 0; k < len; k++) {
                    var attach = {};
                    attach.key = k + 'empty';
                    attach.label = '';
                    sections.push(attach);
                }
            }
            var tem,index;
            for (var i = 0, len = sections.length; i < len; i++) {
                if (sections[i]['key'] == loginuser) {
                    tem = sections[i];
                    index=i;
                }
            }
            if(tem){ // 如果当前页面，存在当前登录用户
            	sections.splice(index, 1);
                sections.unshift(tem);
            }
            
        }
    },
    function() {});
    return sections;
}