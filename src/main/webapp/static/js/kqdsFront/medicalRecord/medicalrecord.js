/* 选择牙位界面------暂未使用----start------ */
/*
 * 打开选择牙位界面
 */
function openSelectTooth(obj) {
    var name = $($(obj).parent().find("input")[1]).attr("name");
    layer.open({
        type: 2,
        title: '牙位选择',
        shadeClose: true,
        shade: 0.6,
        area: ['410px', '465px'],
        content: contextPath + '/KQDS_MedicalRecordAct/toTooth_Select.act?inputTdName=' + name,
        end: function(index, layero) {
		},
    });
}

// 供子页面调用父页面此方法，赋值
function fillSelectTooth(name, up1, up2, down1, down2, usertype) {
   /*if ($("input[name='usertype']",window.parent.document).val() != usertype) {
        layer.alert('同一病历不能同时选择成人及儿童的牙位！' );
    } else {
        $("input[name='" + name + "']:eq(0)").val(up1);
        $("input[name='" + name + "']:eq(1)").val(up2);
        $("input[name='" + name + "']:eq(2)").val(down1);
        $("input[name='" + name + "']:eq(3)").val(down2);
        $("input[name='usertype']").val(usertype);
    }*/
	 $("input[name='" + name + "']:eq(0)").val(up1);
     $("input[name='" + name + "']:eq(1)").val(up2);
     $("input[name='" + name + "']:eq(2)").val(down1);
     $("input[name='" + name + "']:eq(3)").val(down2);
}
/* 选择牙位界面------暂未使用----end------ */


/* wl 功能点：病历资料 特殊字符 随页面滚动 */
function scrollBody(){
   	if($("#jbxx").hasClass("current")){/* 当前是否处在病历资料 */
   		var marTop=0;
       	var bodyScrollTopValue=	document.body.scrollTop;/* body滚动的高度*/
       	/* 固定区域距离顶端的距离  是#topmenu 按钮组的高度+ body提供给的5px边距 */
       	//marTop=$("#topmenu").height()+5;	/* 获得固定区域距离顶端的距离 */
       	fixDivWidth=$("#isjbxx").width();	/* 获得病历表的宽度 */
       	if(bodyScrollTopValue>marTop){/* 当body滚动到固定区域时，固定区域固定定位在当前页面 添加固定的相关样式fixedDivStyle */
       		$("#fixDiv").addClass("fixedDivStyle").width(fixDivWidth); /* 设置当前固定块  固定设置宽度与下面的病历表格一样宽 */
       		
       	}else{
       		$("#fixDiv").removeClass("fixedDivStyle").width(fixDivWidth);/* 取消固定宽度 */
       	}
   	}else{
   		return;							/* 当前如果不是处在病历资料则不固定 */
   	}
}
/* wl 功能点结束 */ 


/*---------------------------------病历保存-----------start------------------------ */
function saveform2(status) {
   if (status == "2") { // #############如果是提交病历
        if ($("#blfl").val() == "") {
            layer.alert('病历分类不能为空'  );
            return false;
        }
        if ($("#bc").val() == "") {
            layer.alert('病程不能为空'  );
            return false;
        }
        layer.confirm('是否已签署知情同意书？', {
            btn: ['是', '否'] //按钮
        },
        function() {
            status = 3; // 3 签署同意书
            baocun(status);
        },
        function() {
            baocun(2); // 2 没签署同意书
        });
    } else {
        baocun(status);
    } 
}
function getParam() {
    var urldata  = {
            seqId: $("#form2 input[name=seqId]").val(),
            usercode: $("#form2 input[name=usercode]").val(),
            regno: $("#form2 input[name=regno]").val(),
            usertype: $("#form2 input[name=usertype]").val(),
            mtype: $('#form2  input[name="mtype"]:checked ').val(),
            blfl: $("#blfl").val(),
            doctor: $("#doctor").val(),
            bc: $("#bc").val()
        };
    //调用子页面方法
    window.tabIframeMedical.getParamMedical(urldata);
    return urldata;
}
/**
 * status 3 签署同意书    2 没签署同意书
 */
function baocun(status) {
    var isprint = 0;
    if (status == "3") { // 签署同意书
        status = 2;
        isprint = 1;
    } else if (status == "2") {
        status = 2;
        isprint = 0; // 没签署同意书
    } else { // 暂存病历
        status = 1;
        isprint = 0; // 没签署同意书
    }
    var url = contextPath + '/KQDS_MedicalRecordAct/insertOrUpdate.act';
    var urldata = getParam();
    urldata.isprint = isprint;
    urldata.status = status;
    $.axseSubmit(url, urldata,
    function() {},
    function(r) {
        if (r.retState == "0") {
            layer.alert('保存成功', {
                  
                end: function() {
                    window.location.reload();
                }
            });
        } else {
            layer.alert('保存失败'  );
        }
    },
    function() {
        layer.alert('保存失败' );
    });
}
/*---------------------------------病历保存--------------end--------------------- */

//编辑病历
function bzblkEdit(seqId, mtype, createuser) {

	if ((perPriv != yuanzhang) && (!isStrInArrayStringEach(yuanzhang, personroleother))) {
     layer.alert('没有编辑权限');
     return;
 }
 parent.layer.open({
     type: 2,
     title: '编辑病历',
     maxmin: true,
     shadeClose: true,
     //点击遮罩关闭层
     zIndex: 211111,
     area: ['80%', '90%'],
     content: contextPath + '/KQDS_MedicalRecordAct/toMedicalRecord_Edit.act?seqId=' + seqId + '&mtype=' + mtype
 });
}

//详情
function detail(seqId, usercode) {
 parent.layer.open({
     type: 2,
     title: '详情',
     shadeClose: true,
     //点击遮罩关闭层
     area: ['90%', '90%'],
     content: contextPath + '/KQDS_MedicalRecordAct/toMedicalRecord_Detail.act?seqId=' + seqId + '&usercode=' + usercode
 });
}
//查看图片
function openAttach(usercode, regno) {
 parent.layer.open({
     type: 2,
     title: '附件列表',
     shadeClose: true,
     shade: false,
     area: ['800px', '550px'],
     content: contextPath + '/KQDS_MedicalRecordAct/toAttachList.act?usercode=' + usercode
 });
}
//调整表格高度
function adjustTable() {
 var height = $('body').height();
}
/* 按钮权限 */ 
function getButtonPower() {
 $("#czlabel").hide();
 $("#fzlabel").hide();
 $(".clearRow").hide();
 $(".addRow").hide();
 var status = $("#status").val();
 var menubutton = "",
 menubutton1 = "",
 menubutton2 = "";
 for (var i = 0; i < listbutton.length; i++) {
     if (status != "2") {
         if (listbutton[i].qxName == "bl_bczl" && isdelreg == 0) {
             menubutton += ' <a href="javascript:void(0);" onclick="saveform1()" class="aBtn">保存资料</a>';
         }
     }
     if (listbutton[i].qxName == "bl_cyblk" && isdelreg == 0) {
         menubutton1 += ' <a onclick="bzblk()" href="javascript:void(0);" style="padding:0 5px;" class="kqdsCommonBtn cyBliK">常用病案库</a>&nbsp;&nbsp;';
     } else if (listbutton[i].qxName == "bl_tjbzblk" && isdelreg == 0) {
     	menubutton1 += ' <select class="dict dictBliK" tig="blkfl" name="blkfl" id="blkfl" style="width:100px;"></select>&nbsp;&nbsp;';
         menubutton1 += ' <a onclick="addbzk()" href="javascript:void(0);" style="padding:0 5px;" class="kqdsCommonBtn bzBliK">添加标准病历库</a>'; 
     } else if (listbutton[i].qxName == "bl_tjzyblk" && isdelreg == 0) {
         menubutton1 += ' <a onclick="addzyk()" href="javascript:void(0);" style="padding:0 5px;" class="kqdsCommonBtn zyBliK">添加自用病历库</a>';
     }
     if (listbutton[i].qxName == "bl_tjbl" && isdelreg == 0) {
         $("#filePicker").show();
         $("#czlabel").show();
         $("#fzlabel").show();
         $(".clearRow").show();
         $(".addRow").show();
         menubutton2 += '  <a href="javascript:void(0);" onclick="saveform2(1)" class="kqdsCommonBtn" id="tjbl">暂存病历</a>';
         menubutton2 += '  <a href="javascript:void(0);" onclick="saveform2(2)" class="kqdsSearchBtn" id="tjbl">提交病历</a>';
     }
 } // 第一个if结束 
 $("#bczl").append(menubutton);
 $("#topmenu").append(menubutton1);
 $("#buttommenu").append(menubutton2);
}


/**
* 恢复病历、病历库使用（给病历内容赋值时 验证）
*/
function Yzbingli(mtype){
	var flag = true;
	var mtypeReal = $('#form2  input[name="mtype"]:checked ').val();
	var flowName = "";
	if (mtypeReal == "0") {
		flowName = '新诊病历';
	} else {
	    flowName = '复诊病历';
	}
	if (mtypeReal != mtype) {
	     layer.alert('当前病历为' + flowName + ",与所选病历类型不一致。", {
	           
	     });
	     flag = false;
	}
	return flag;
}
/**
* 恢复病历#############################################################
*/
function huifubingli(seqId, mtype) {
	if(Yzbingli(mtype)){
		//调用子页面方法
		window.tabIframeMedical.getblcontent4HuiFu(seqId, mtype);
		$("#seqId").val(seqId); // 加这个是编辑，不加就新增了。
	}
}
//子页面调用，赋值
function setBcfl(blfl, bc) {
	 $("#blfl").val(blfl);
  $("#blfl").trigger("change");
  $("#bc").val(bc);
}
/**
* 恢复病历#############################################################  END ！！！
*/


/* 病历库  start */ 
//添加标准库
function addbzk() {
    if ($("select[name=blkfl]").val() == null || $("select[name=blkfl]").val() == "") {
        layer.alert('请选择分类名' );
        return;
    }
    addblk(0);
}
//添加字用库
function addzyk() {
    if ($("select[name=blkfl]").val() == null || $("select[name=blkfl]").val() == "") {
        layer.alert('请选择分类名' );
        return;
    }
    addblk(1);
}
function addblk(type) {
    layer.prompt({
        title: '输入病历名称，并确认',
        formType: 0
    },
    function(pass, index) {
        layer.close(index);
        var url = contextPath+'/KQDS_BLKAct/insertOrUpdate.act';
        var dataparam = getParam();
        dataparam.blname = pass;
        dataparam.blkfl = $("select[name=blkfl]").val();
        dataparam.type = type;
        msg = layer.msg('加载中', {
            icon: 16
        });
        $.axse(url, dataparam,
        function(data) {
            if (data.retState == "0") {
                layer.alert('保存成功', {
                      
                    end: function() {
                    	if(parent.refresh){
                    		parent.refresh()
                    		parent.layer.close(frameindex); //再执行关闭 
                    	}
                    }
                });
            }
        },
        function() {
            layer.alert('保存失败！'  );
        });
    });
}

//弹出一个iframe层
function bzblk() {
    parent.layer.open({
        type: 2,
        title: '病历标准库',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        zIndex: 211111,
        area: ['1000px', '520px'],
        content: contextPath + '/KQDS_BLKAct/toBlkTree.act'
    });
}

//弹出一个iframe层
function addblct() {
  parent.layer.open({
      type: 2,
      title: '病历词条',
      maxmin: true,
      shadeClose: true,
      //点击遮罩关闭层
      zIndex: 211111,
      area: ['1000px', '520px'],
      content: contextPath + '/YZBlctAct/toListBlctQt.act'
  });
}
function setct(ctname){
	$("#tabIframeMedical")[0].contentWindow.setctChild(ctname);
}
//病历库 使用时 调用
function setcontent(mtype, content) {
	if(Yzbingli(mtype)){
		 //调用子页面方法
	    window.tabIframeMedical.setMedicalcontent(content);
	}
}
/* 按钮权限  end */ 