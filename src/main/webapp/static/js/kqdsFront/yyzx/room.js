// 新增或修改操作前，都会先调用此方法   
scheduler.attachEvent("onEventSave",
function(id, e) {
    //新增
    if (id.toString() != null && id.toString().length < 36) {
        id = "";
    }
    if(id == ""){//新增
    	 if (e.roomstatus != "0") {
    	        layer.alert('新建预约手术状态必须为手术前', {
    	              
    	        });
    	        return;
    	 }
    }
    var starttime = getNowFormatDate(e.start_date);
    var nowdate = getNowFormatDate();
    var checkdatez = checkdate(starttime, nowdate); // 0 预约开始时间 大于 当前时间  1预约开始时间 小于当前时间 2 预约开始时间等于当前时间
    if (checkdatez == 1) { // 即打开的时候，过了预约时间的，只读，不能编辑了
    	 layer.alert('预约时间已过', {
		              
		        });
		 return false;
    }
    if (e.usercode == "") {
        layer.alert('请选择患者' );
        return;
    }
    if (e.doctor == "") {
        layer.alert('请选择医生' );
        return;
    }
    if (e.zzxt == "") {
        layer.alert('请选择种植系统' );
        return;
    }
    /*console.log(e.totalcost);
    return;
    if (e.totalcost == "") {
        layer.alert('请选择种植系统' );
        return;
    }*/
    //验证该时间段内 手术室、医生、护士 是否存在预约
    var B = e.start_date; 
    var sortstart =  B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate();
    var ss = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate() + " " + B.getHours() + ":" + B.getMinutes() + ":" + B.getSeconds();
    var B = e.end_date;
    var sortend = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate();
    var ss1 = B.getFullYear() + "-" + (B.getMonth() + 1) + "-" + B.getDate() + " " + B.getHours() + ":" + B.getMinutes() + ":" + B.getSeconds();
    if(sortstart != sortend){
    	layer.alert('不能跨天预约' );
        return false;
    }
    if(openPaiban == '1'){
	    var checkYspb = checkPb(ss, ss1, e.doctor); 
	    if (!checkYspb) {
	    	layer.alert('非值班人员，无法预约！' );
	        return;
	    }
    }
    if (!checkRoom(ss,ss1,e.roomid,"","","",id)) {
        layer.alert(flagdata, {
              
            end: function() {
            	loaddata();
            }
        });
        return false;  
     }
    if (!checkRoom(ss,ss1,"",e.doctor,"","",id)) {
        layer.alert(flagdata, {
              
            end: function() {
            	loaddata();
            }
        });
        return false;  
     }
    if(e.nurse != ""){
    	if (!checkRoom(ss,ss1,"","",e.nurse,"",id)) {
            layer.alert(flagdata, {
                  
                end: function() {
                	loaddata();
                }
            });
            return false;  
         }
    }
    if (!checkRoom(ss,ss1,"","","",e.usercode,id)) {
        layer.alert(flagdata, {
              
            end: function() {
            	loaddata();
            }
        });
        return false;  
     }
    //判断患者该时间段内是否存在预约的手术
    //判断门诊预约情况 （防止医生\护士存在门诊预约 还进行手术预约）
	/* if (!checkOrder(ss,ss1,e.doctor,"")) {*/
	if(!checkOrderDoctor(ss,ss1,"",e.doctor,e.usercode)){//判断 该医生门诊预约的患者 是不是 现在预约的患者
	 	layer.alert(flagdata, {
              
            end: function() {
            	loaddata();
            }
        });
        return false;  
	}
	/*}*/
	if(e.nurse!=""){
		/*if (!checkOrder(ss,ss1,"",e.nurse)) {*/
			if(!checkOrderDoctor(ss,ss1,e.nurse,"",e.usercode)){//判断 该护士门诊预约的患者 是不是 现在预约的患者
			    layer.alert(flagdata, {
			          
			        end: function() {
			        	loaddata();
			        }
			    });
			    return false; 
			}
		/*}*/
	}
	//判断患者门诊预约
	if (!checkOrderUsercode(ss,ss1,e.doctor,e.usercode)) {
	    layer.alert(flagdata, {
	          
	        end: function() {
	        	loaddata();
	        }
	    });
	    return false;  
	}
    return true; 
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
    	roomid:e.roomid,
    	tooth:e.tooth,
        usercode: e.usercode,
        doctor: e.doctor,
        nurse: e.nurse,
        phasedoctor: e.phasedoctor,
        askperson: e.askperson,
        remark: e.remark,
        zzxt: e.zzxt,
        ks: e.ks,
        roomstatus: e.roomstatus,
        starttime: ss,
        endtime: ss1  // ###新增和编辑的区别是，没有传regdept 和seqId
    };
    var url = contextPath + '/KQDS_RoomAct/insertOrUpdate.act';
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
    //---------------------------------修改的时候还是要验证 直接拖拽预约记录 是不执行onEventSave方法的---------------------------------------
    //验证该时间段内 手术室、医生、护士 是否存在预约
    if (!checkRoom(ss,ss1,e.roomid,"","","",e.id)) {
        layer.alert(flagdata, {
              
            end: function() {
            	loaddata();
            }
        });
        return false;  
     }
    if (!checkRoom(ss,ss1,"",e.doctor,"","",e.id)) {
        layer.alert(flagdata, {
              
            end: function() {
            	loaddata();
            }
        });
        return false;  
     }
    if(e.nurse != ""){
	    if (!checkRoom(ss,ss1,"","",e.nurse,"",e.id)) {
	        layer.alert(flagdata, {
	              
	            end: function() {
	            	loaddata();
	            }
	        });
	        return false;  
	     }
    }
    if (!checkRoom(ss,ss1,"","","",e.usercode,id)) {
        layer.alert(flagdata, {
              
            end: function() {
            	loaddata();
            }
        });
        return false;  
     }
    //判断门诊预约情况 （防止医生\护士存在门诊预约 还进行手术预约）
	/*if (!checkOrder(ss,ss1,e.doctor,"")) {*/
		 if(!checkOrderDoctor(ss,ss1,"",e.doctor,e.usercode)){//判断 该医生门诊预约的患者 是不是 现在预约的患者
		 	layer.alert(flagdata, {
	              
	            end: function() {
	            	loaddata();
	            }
	        });
	        return false;  
		 }
	/*}*/
	if(e.nurse!=""){
		/*if (!checkOrder(ss,ss1,"",e.nurse)) {*/
			if(!checkOrderDoctor(ss,ss1,e.nurse,"",e.usercode)){//判断 该医生门诊预约的患者 是不是 现在预约的患者
			    layer.alert(flagdata, {
			          
			        end: function() {
			        	loaddata();
			        }
			    });
			    return false; 
			}
		/*}*/
	}
	//判断患者门诊预约
	if (!checkOrderUsercode(ss,ss1,e.doctor,e.usercode)) {
	    layer.alert(flagdata, {
	          
	        end: function() {
	        	loaddata();
	        }
	    });
	    return false;  
	}
    var param = {
        seqId: e.id,
        tooth:e.tooth,
        roomid:e.roomid,
        usercode: e.usercode,
        doctor: e.doctor,
        nurse: e.nurse,
        phasedoctor: e.phasedoctor,
        askperson: e.askperson,
        remark: e.remark,
        zzxt: e.zzxt,
        ks: e.ks,
        roomstatus: e.roomstatus,
        starttime: ss,
        endtime: ss1 // ###新增和编辑的区别是，没有传regdept 和seqId

    };
    var url = contextPath + '/KQDS_RoomAct/insertOrUpdate.act';
    var msg;
    $.axseSubmit(url, param,
    function() {
        msg = layer.msg('加载中', {
            icon: 16
        });
    },
    function(r) {
        if (r.retState == "0") {
            layer.alert('保存成功', {
                  
                end: function() {
                	loaddata();
                }
            });
        } else {
        	if(r.retMsrg == "noPriv") {
        		layer.alert('禁止修改他人手术室预约', {
                    
                    end: function() {
                    	loaddata();
                    }
                });
        	}else {
        		layer.alert('保存失败', {
                    
                    end: function() {
                    	loaddata();
                    }
                });
        	}
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
        var url = contextPath + '/KQDS_RoomAct/deleteObj.act?seqId=' + id + '&delreason=' + delreason;
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                layer.alert('取消预约成功', {
                      
                    end: function() {
                    	loaddata();
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
