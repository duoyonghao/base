//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
var curWwwPath = window.document.location.href;
// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
var pathName = window.document.location.pathname;
var pos = curWwwPath.indexOf(pathName);
// 获取主机地址，如： http://localhost:8083
var localhostPaht = curWwwPath.substring(0, pos);
// 获取带"/"的项目名，如：/uimcardprj
var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
//获取主机地址，如： http://localhost:8083
var localhostPaht = curWwwPath.substring(0, pos);

/*杨森 add 17-8-16*/
var contextPath = localhostPaht + projectName;

//不带http   localhost:8083
var localhostPahtless = localhostPaht.substring(localhostPaht.lastIndexOf("/") + 1);

/** jquery 扩展 **/
$.extend({
    getUrlVars: function() {
        var vars = [],
        hash;
        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
        for (var i = 0; i < hashes.length; i++) {
            hash = hashes[i].split('=');
            vars.push(hash[0]);
            vars[hash[0]] = hash[1];
        }
        return vars;
    },
    getUrlVar: function(name) {
        return $.getUrlVars()[name];
    }
});

/**
 * 上传附件，回调
 */
document.write('<script src="' + contextPath + '/static/js/app/plugin/jquery.form.js"><\/script>');


/**************************************************开发环境引用以下JS**********************************/
// 下面的顺序不能调整,后面升级使用require.js node.js
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_request.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_select.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_date.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_util.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_websocket.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/tool_binddata.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/tool_checkvalue.js"><\/script>'); // 正则校验
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/tool_other.js"><\/script>');
// 系统 js
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/sys_person.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/sys_dept.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/sys_dict.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/sys_picker.js"><\/script>'); // 用户、部门弹框选择
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/sys_priv.js"><\/script>');
// 业务js
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_layeropen.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_chain.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_userdoc.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_reg.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_paycost.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_system.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_image.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_print.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_factory.js"><\/script>'); // 加工厂
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_openwindow.js"><\/script>'); // 弹框
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_button.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_cost.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_chufang.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_hospital.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_jzmd.js"><\/script>');


/**************************************************END!!!!!!!!!!**********************************/

/** 微信相关js **/
document.write('<script src="' + contextPath + '/static/js/kqdsFront/weixin/wx_account.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/weixin/wx_menu.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/weixin/wx_template.js"><\/script>');
/** 表格宽度拖拽 **/
document.write('<script src="' + contextPath + '/static/js/kqdsFront/colResizable-1.6.min.js"><\/script>');
document.write('<script src="' + contextPath + '/static/js/kqdsFront/colresizable.js"><\/script>');
/** 付款方式 **/
document.write('<script src="' + contextPath + '/static/js/kqdsFront/kqds/kqds_fkfs.js"><\/script>');

//获取页面打开凡是 isapp=1  chrome app的方式打开	 
var isapp = localStorage.getItem("isapp");
if (isapp == "1") {
    //禁用右键菜单
    document.oncontextmenu = DisableRightClick;
    function DisableRightClick() {
        return false;
    }
}

//禁止页面选择，防止数据拷贝导出
$(function() {
    document.onselectstart = function() {
        return event.srcElement.type == "text";
    };
});

function readonly() {
    $("input").attr("readonly", "readonly");
    $("textarea").attr("readonly", "readonly");
    $("select").attr("disabled", true);
}

//解析树 返回数据
function ajaxDataFilter(treeId, parentNode, responseData) {
    var tree;
    if (responseData.retState == "0") {
        tree = responseData.retData;
    }
    return tree;
};

function isJSON(str) {
  if (typeof str == 'string') {
      try {
          JSON.parse(str);
          return true;
      } catch(e) {
          return false;
      }
  }
}
var togglemodel={/* wl 自定义开关功能 */
	state:1,		//目前右侧div的开关状态 1 初始为显示 
	url:"",			//table刷新 需要的请求地址
	paramName:"",	//localStorage中存储状态变量名
	initial:function(paramName,url){/*初始时为按钮绑定事件*/
		this.url=url;
		this.paramName=paramName;
		localStorage.removeItem(this.paramName);//lutian 添加？？？
		localStorage.setItem(this.paramName,this.state);//lutian 添加？？？
		$("#blueArrow").on("click",this.switchDiv.bind(this));/*按钮绑定事件*/
		this.showDiv();
		/*$("#collectBtn").css("visibility","hidden"); 隐藏所有折叠按钮*/
	},
	resetTable:function(){/*接待中心 ，咨询中心，医疗中心 触发左边 6个按钮的事件            刷新 Table */
		$("#menuul li").each(function(i,dom){
			if($(dom).hasClass("current")){
				dom.onclick();
			};
			return;
		});
	},
	showDiv:function(){		/*折叠切换的样式*/
		var windowWidth=$(window).width();
		var width=windowWidth-44;
		this.state=localStorage.getItem(this.paramName);
		if(this.state==1){ /*如果当前状态为打开(state=1) 则执行关闭操作 */
			$(".centerWrap").outerWidth(Math.floor(width/2));/*左侧模块*/
			$(".lookInfoWrap").outerWidth(Math.floor(width/2));/*右侧模块*/
			$(".lookInfoWrap .columnBd").css("display","block");/*右侧模块显示*/
			$("#blueArrow span").removeClass("leftArrowIcon");/*按钮上 右箭头*/
			//------2019/7/15 lutian 自适应修改-------
			//$(".centerWrap").outerWidth(Math.floor(width));/*左侧模块*/
			//$(".lookInfoWrap").outerWidth(Math.floor(width+25)).css("transition","1s");/*右侧模块*/
			//$(".lookInfoWrap .subSideBar").css("display","block");//右侧左边导航模块关闭
			//$(".lookInfoWrap .columnBd").css("display","block");/*右侧模块显示*/
			//$("#blueArrow span").removeClass("leftArrowIcon");/*按钮上 右箭头*/
			//-------2019/7/15 lutian 自适应修改-------
		}else if(this.state==0){
			$(".centerWrap").outerWidth(width-72);/*左侧模块 宽度*/
			$(".lookInfoWrap").outerWidth(70);/*右侧模块*/
			$(".lookInfoWrap .columnBd").css("display","none");/*右侧模块关闭*/
			$("#blueArrow span").addClass("leftArrowIcon");/*按钮上 左箭头*/
			//------2019/7/15 lutian 自适应修改-------
			//$(".centerWrap").outerWidth(width);//左侧模块 宽度
			//$(".lookInfoWrap").outerWidth(0).css("transition","1s");//右侧模块
			//$("#blueArrow span").addClass("leftArrowIcon");//按钮上 左箭头
			//-------2019/7/15 lutian 自适应修改-------
		}
		if(this.url){/*如果 传入的请求地址不为空  通过请求地址刷新table*/
			loadedData = [];
			//设置下表头的宽度
			$("#table").bootstrapTable("refresh",{
				'url':this.url
			})
		}else{ /*如果为空，则为 接待中心 咨询中心 医疗中心界面  通过触发左模块6个按钮的onclick事件 刷新table*/
			this.resetTable();
		}
	},
	switchDiv:function(){	/*按钮被点击时，修改按钮的状态*/
		/*if(!window.onclickrowOobj){
			layer.alert("请选择患者！");
			return;
		}*/
		this.state=localStorage.getItem(this.paramName); /*修改每个页面对应的 状态参数  1为打开右侧模块 0为关闭右侧模块*/
		if(this.state==1){/* 如果当前状态为打开(state=1) 则执行关闭操作 */
			//this.state=0;
			localStorage.setItem(this.paramName,"0");
		}else if(this.state==0){
			//this.state=1;
			localStorage.setItem(this.paramName,"1");
		}
		//console.log(this.state+"---------状态");
		this.showDiv();
		//this.resetTable();
	}
};
//查询条件  通用查询  高级查询切换按钮
function initSearchToggleBtnGroup(){
	$(".label").css("display","none");
	$(".searchToggleBtnGroup").on("click","span",function(){
		if($(this).hasClass("ptcx")){//点击的是通用查询按钮
			$(".toggleTr").css("display","none");
			$(".label").css("display","none");
			$(".label-danger").css("display","block");
			$(".commonUse").css("display","block");
			$("#table .label-success").css("display","block");
		}else if($(this).hasClass("bqcx")){
			$(".label-danger").css("display","block");
			$(".toggleTr").css("display","none");
			$(".commonUse").css("display","none");
			$(".label").css("display","block");
			$("#table .label-success").css("display","block");
		}else{
			$(".label").css("display","none");
			$(".label-danger").css("display","block");
			$(".commonUse").css("display","none");
			$(".toggleTr").css("display","block");
			$("#table .label-success").css("display","block");
		}
		//重置高度
		setHeight();
		//触发清空按钮 事件   清除所有查询条件
		$('.clean').trigger("click");
		
		if($(this).hasClass("checked")){//按钮样式的切换
			return;
		}else{
			$(this).addClass("checked").siblings().removeClass("checked");
		}
	});	
}
//验证查询条件内容是否为空
function YzSearchValue(classname){
	var flag = false;
	$("."+classname+" input").each(function(){
		  if($(this).val()!=''){
			  flag = true;
		  }
	});
	$("."+classname+" select").each(function(){
		  if($(this).val()!='' && $(this).attr("id") != "organization"){
			  flag = true;
		  }
	});
	return flag;
}

/**
 * 判断str 是否以 endstr 结尾
 * @param str
 * @param endstr
 * @returns
 */
function endWidth(str, endstr) {
    var reg = new RegExp(endstr + "$");
    return reg.test(str);
}


/**************************************从kqds.js中拷贝的方法******************************************/

/**
 * 获取部门树下拉框
 * @param id
 * @param dictType
 */
function getSelectDeptTree(id) {
    var dict = $("#" + id);
    var url = contextPath + "/YZDeptAct/getSelectDeptTree.act";
    $.axse(url, null,
    function(data) {
        var list = data.retData;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.value + "'>" + optionStr.text + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

function checknumber(StrInput) {
    var Letters = "1234567890";
    var i;
    var c;
    for (i = 0; i < StrInput.length; i++) { //Letters.length() ->>>>取字符长度
    	c = StrInput.charAt(i);
        if (Letters.indexOf(c) == -1) { //在"Letters"中找不到"c"   见下面的此函数的返回值
            return true;
        }
    }
    return false;
}


/**************************************20180307 新增******************************************/

/**************************************20200507 新增******************************************/
var oldCaseResult; //老病历查询结果
var newCaseResult;  //新病例查询结果
/**临床路径电子病历弹窗 2020/05/07 lutian 
 * num:病历循环序号
   casenum:要打开的病历act  1:主诉及既往病历  2.检查及诊断 3.诊疗方案 4.修复方案 5.新版主诉即既往病史**/
function addDProject(thi,num,casenum){
	var seqid=$(thi).attr("id");  //当前选中的病历seqid
	var contentUrl="";  //要打开的弹窗路径
	var layerTitle="";
	if(casenum==1 || casenum=="1"){
		layerTitle='主诉及既往病史';
		if(consultAddBtn){
			contentUrl=contextPath + "/ZzblViewAct/toAnamnesisInfor.act?seqidFather="+seqid;
		}else{
			contentUrl=contextPath + "/ZzblViewAct/toZzllAnammesis.act?seqidFather="+seqid;
		}
	}else if(casenum==2){
		layerTitle='检查及诊断';
		if(consultAddBtn){
			contentUrl=contextPath + "/ZzblViewAct/toExamineDiagnoseInfor.act?seqidFather="+seqid;
		}else{
			contentUrl=contextPath + "/ZzblViewAct/toZzllExamineDiagnose.act?seqidFather="+seqid;
		}
	}
	else if(casenum==3){
		layerTitle='诊疗方案';
		if(consultAddBtn){
			contentUrl=contextPath + "/ZzblViewAct/toDiagnosisProjectInfor.act?seqidFather="+seqid;
		}else{
			contentUrl=contextPath + "/ZzblViewAct/toZzllDiagnosisProject.act?seqidFather="+seqid;
		}
	}else if(casenum==4){
		layerTitle='修复方案';
		if(consultAddBtn){
			contentUrl=contextPath + "/ZzblViewAct/toRepairProjectInfor.act?seqidFather="+seqid;
		}else{
			contentUrl=contextPath + "/ZzblViewAct/toZzllRepairProject.act?seqidFather="+seqid;
		}
	}else if(casenum==5){
		layerTitle='新版主诉即既往病史';
		if(consultAddBtn){
			contentUrl=contextPath + "/ZzblViewAct/toAnamnesisThirdInfor.act?seqidFather="+seqid;
		}else{
			contentUrl=contextPath + "/ZzblViewAct/toAnamnesisThirdInfor.act?seqidFather="+seqid;
		}
	}else if(casenum==6){
		layerTitle='新版口腔专科检查';
		if(consultAddBtn){
			contentUrl=contextPath+"/ZzblViewAct/toDentalExamination.act?seqidFather="+seqid;
		}else{
			contentUrl=contextPath+"/ZzblViewAct/toDentalExamination.act?seqidFather="+seqid;
		}
	}else if(casenum==7){
		layerTitle='新版诊疗方案';
		if(consultAddBtn){
			contentUrl=contextPath + "/ZzblViewAct/toExamineDiagnoseCase.act?seqidFather="+seqid;
		}else{
			contentUrl=contextPath + "/ZzblViewAct/toExamineDiagnoseCase.act?seqidFather="+seqid;
		}
	}
	var nameNum="";  //记录病历序号，null为新增病历
	if(num){
		nameNum=num;
	}
	if(window.parent.consultSelectPatient) {
		window.parent.setOldForm(oldCaseResult, num - 1);
		window.parent.setNewForm(newCaseResult, num - 1);
	}else{
		window.parent.parent.setOldForm(oldCaseResult, num - 1);
		window.parent.parent.setNewForm(newCaseResult, num - 1);
	}
	parent.layer.open({
		title:layerTitle+nameNum,
		type:2,
		closeBtn:1,
		content:contentUrl,
		area:['100%','95%'],
		cancel: function(){
		},
  		end:function(){
  			window.location.reload();//刷新本页面
  		}
	});
}
//术前准备节点,点击其他隐藏选项框
$(function(){
	if(typeof consultAddBtn != 'undefined'){
		if(!consultAddBtn){
			document.addEventListener("click",function(event){
		    	event=event||window.event;
		        var eve=event.target||eve.elementSrc;
		    	if(eve.className=='ask_Previous' || eve.className=='examine_diagnose' || eve.className=='diagnosis_case' || eve.className=='xiufu_test' || eve.id=='allCases' || eve.className=='btnStyle'){
		    	}else{
		    		$(".caseContiner").each(function(i,obj){
		    			$(this).css("display","none");
		    		}); 
		    	}
		    });//所有组件添加点击事件
		}
	}
});

//点击选项显示隐藏病历列表  father:父布局 li的class
function showHiddenClick(thi,father){
	//展示自己
	var showVal = $(thi).parents("."+father).find(".caseContiner").css("display");
	if(showVal=="none"){
		//先隐藏所有
		$(".caseContiner").each(function(i,obj){
			$(this).css("display","none");
		});
		$(thi).parents("."+father).find(".caseContiner").css("display","block");
        var initWhich=thi.className;
        if(initWhich=="ask_Previous"){//询问既往史
            var anamnesisUrl = contextPath + '/HUDH_ZzblAskAct/findCaseHistoryById.act';
            initCaseHistory(anamnesisUrl,1);  //初始化主诉及既往病史，默认先展示老病历
        }else if(initWhich=="examine_diagnose"){//口内检查
            var jczdUrl  = contextPath + '/HUDH_ZzblCheckAct/findZzblOprationById.act';  //老病历url
            initZzblOpration(jczdUrl,2); //初始化检查及诊断
        }else if(initWhich=="diagnosis_case"){//制定手术方案和治疗计划
            var zlfaurl = contextPath + '/HUDH_ZzblAct/findZzblOprationById.act';//诊疗方案老url
            initDiagnosisProject(zlfaurl,3); //初始化诊疗方案
        }else{//修复
            initRepairProject(); //初始化修复方案
        }
	}else{
		$(thi).parents("."+father).find(".caseContiner").css("display","none");
	}
	var e=window.event || arguments.callee.caller.arguments[0];
	e.preventDefault();
	e.stopPropagation() ;
}

//初始化病历列表  result:初始化结果   father:父布局li的class btn:选中btn的class casenum：更新选中的病历act 1:主诉及既往病历  2.检查及诊断 3.诊疗方案 4.修复方案 5.新版主诉即既往病史
/*function initCaseList(result,father,btn,casenum){
	var itemName="";
	if(casenum==1){
		itemName="旧病史";
	}else if(casenum==2){
		itemName="诊断";
	}else if(casenum==3){
		itemName="方案";
	}else if(casenum==4){
		itemName="方案";
	}else if(casenum==5){
		itemName="新病史";
	}
	if(result.length>=1){
		//$("."+father).find("."+btn).prev().attr("checked","checked").attr("disabled","disabled");
		$("."+father).find("input[name='Consultation']").attr("checked","checked").attr("disabled","disabled");
	}
	var caseStyleHtml="";
	var allCasesHtml='<option value="">请选择</option>';
	//var allCasesHtml='';
	for (var i =0;i<result.length;i++ ) {
		if(result[i].status=="1"){
			if(casenum==1){
				alreadySelectZSBSId=result[i].seq_id;  //记录上一个选中主诉及既往病历病历seq_id
			}else if(casenum==2){
				alreadySelectJCZDId=result[i].seq_id;  //记录上一个选中检查及诊断病历seq_id
			}else if(casenum==3){
				alreadySelectZLFAId=result[i].seq_id;  //记录上一个选中诊疗方案病历seq_id
			}else if(casenum==4){
				alreadySelectXFFAId=result[i].seq_id;  //记录上一个选中修复方案病历seq_id
			}
			
			caseStyleHtml+="<span class='caseStyle' style='border-bottom:2px solid #00a6c0;' onclick='addDProject(this,"+(i+1)+","+casenum+");' id="+result[i].seqId+">"+itemName+"<label>"+(i+1)+"</label></span>";
			allCasesHtml+='<option selected value='+result[i].seqId+'>'+itemName+''+(i+1)+'</option>';
		}else{
			caseStyleHtml+="<span class='caseStyle' onclick='addDProject(this,"+(i+1)+","+casenum+");' id="+result[i].seqId+">"+itemName+"<label>"+(i+1)+"</label></span>";
			allCasesHtml+='<option value='+result[i].seqId+'>'+itemName+''+(i+1)+'</option>';
		}
	}
	if(consultAddBtn){
		caseStyleHtml+="<span class='caseStyle addCaseBtn' onclick='addDProject(this,null,"+casenum+");' id='addCaseBtnOld'>新增<label>+</label></span>";

		//caseStyleHtml+="<span class='caseStyle addCaseBtnNew' onclick='addDProject(this,null,"+casenum+");' id='addCaseBtnNew'>新增新版<label>+</label></span>";
	}
	$("."+father).find(".zlCases").html(caseStyleHtml);   //所有方案的初始化展示
	$("."+father).find("#allCases").html(allCasesHtml);   //初始化方案下拉框
}*/
//遍历展示所有病历
function initCaseList(result,father,btn,casenum){
	var itemName="";
	if(casenum==1){
		itemName="旧病史";
	}else if(casenum==2){
		itemName="诊断";
	}else if(casenum==3){
		itemName="方案";
	}else if(casenum==4){
		itemName="方案";
	}else if(casenum==5){
		itemName="新病史";
	}else if(casenum==6){
		itemName="新诊断";
	}else if(casenum==7){
		itemName="新诊疗";
	}
	// if(result.length>=1){
	// 	$("."+father).find("input[name='Consultation']").attr("checked","checked").attr("disabled","disabled");
	// }
	var caseStyleHtml="";
	for (var i =0;i<result.length;i++ ) {
		if(result[i].seqId){
			if(result[i].status=="1"){
				caseStyleHtml+="<span class='caseStyle' style='border-bottom:2px solid #00a6c0;' onclick='addDProject(this,"+(i+1)+","+casenum+");' id="+result[i].seqId+">"+itemName+"<label>"+(i+1)+"</label></span>";
			}else{
				caseStyleHtml+="<span class='caseStyle' onclick='addDProject(this,"+(i+1)+","+casenum+");' id="+result[i].seqId+">"+itemName+"<label>"+(i+1)+"</label></span>";
			}
		}else {
			if(result[i].status=="1"){
				caseStyleHtml+="<span class='caseStyle' style='border-bottom:2px solid #00a6c0;' onclick='addDProject(this,"+(i+1)+","+casenum+");' id="+result[i].id+">"+itemName+"<label>"+(i+1)+"</label></span>";
			}else{
				caseStyleHtml+="<span class='caseStyle' onclick='addDProject(this,"+(i+1)+","+casenum+");' id="+result[i].id+">"+itemName+"<label>"+(i+1)+"</label></span>";
			}
		}

	}
	if(consultAddBtn){
		caseStyleHtml+="<span class='caseStyle addCaseBtn' onclick='addDProject(this,null,"+casenum+");' id='addCaseBtnOld'>新增<label>+</label></span>";
	}
	$("."+father).find(".zlCases").html(caseStyleHtml);   //所有方案的初始化展示
	//initSelectList(father,btn,casenum); //初始化病历下拉框 新病历+老病历
}
//初始化病历下拉框 新病历+老病历
function initSelectList(father,casenum){
	var allResult;
	//var oldCaseUrl;  //老病历url
	var newCaseUrl;  //新病例url
	var itemName="";
	if(casenum==1){
		itemName="病史";
		//oldCaseUrl = contextPath + '/HUDH_ZzblAskAct/findCaseHistoryById.act';  //老病历url
		//oldCaseResult = getResult(oldCaseUrl); //老病历查询结果
		newCaseUrl = contextPath + '/HUDH_ZzblAdviceAct/findCaseHistoryById.act';  //新病例url
		newCaseResult = getResult(newCaseUrl);  //新病例查询结果
	}else if(casenum==2){
		itemName="诊断";
		//oldCaseUrl = contextPath + '/HUDH_ZzblCheckAct/findZzblOprationById.act';  //老病历url
		//oldCaseResult = getResult(oldCaseUrl); //老病历查询结果
		newCaseUrl = contextPath + '/HUDH_MedicalRecordsAct/selectdata.act';//新病例url
		newCaseResult = getResult(newCaseUrl);  //新病例查询结果
	}else if(casenum==3){
		itemName="方案";
		//oldCaseUrl =contextPath + '/HUDH_ZzblAct/findZzblOprationById.act';  //老病历url
		//oldCaseResult = getResult(oldCaseUrl); //老病历查询结果
		newCaseUrl = contextPath + '/HUDH_LcljCaseAct/select.act'; //新病例url
		newCaseResult = getResult(newCaseUrl);  //新病例查询结果
	}else if(casenum==4){
		itemName="方案";
		//oldCaseUrl = contextPath + '/HUDH_RepairSchemeConfirmAct/findRepairInforById.act';  //老病历url
		//oldCaseResult = getResult(oldCaseUrl); //老病历查询结果
		newCaseResult = "";
	}

	// if(oldCaseResult.length>=1 || newCaseResult.length>=1){
	// 	$("."+father).find("input[name='Consultation']").attr("checked","checked").attr("disabled","disabled");
	// }
	var allCasesHtml='<option value="">请选择</option>';
	//老病历遍历  mark：用来标记新老病历 0-->老病历  1-->新病历
	if(oldCaseResult&&oldCaseResult.length>0){
		for (var i =0;i<oldCaseResult.length;i++ ) {
			if(oldCaseResult[i].status=="1"){
				if(casenum==1){
					alreadySelectZSBSId=oldCaseResult[i].seq_id;  //记录上一个选中主诉及既往病历病历seq_id
					alreadySelectZSBSMark=0;  //新老病历标记
				}else if(casenum==2){
					alreadySelectJCZDId=oldCaseResult[i].seq_id;  //记录上一个选中检查及诊断病历seq_id
					alreadySelectJCZDMark=0;  //新老病历标记
				}else if(casenum==3){
					alreadySelectZLFAId=oldCaseResult[i].seq_id;  //记录上一个选中诊疗方案病历seq_id
					alreadySelectZLFAMark=0;  //新老病历标记
				}else if(casenum==4){
					alreadySelectXFFAId=oldCaseResult[i].seq_id;  //记录上一个选中修复方案病历seq_id
				}
				/*if(newCaseResult.length>0){
					itemName='旧'+itemName;
				}*/
				allCasesHtml+='<option selected value='+oldCaseResult[i].seqId+' mark="0">'+itemName+''+(i+1)+'</option>';
			}else{
				allCasesHtml+='<option value='+oldCaseResult[i].seqId+' mark="0">'+itemName+''+(i+1)+'</option>';
			}
		}
	}

	//新病例遍历
	if(newCaseResult.length>0){
		for (var j =0;j<newCaseResult.length;j++ ) {
			if(newCaseResult[j].status=="1"){
				if(casenum==1){
					alreadySelectZSBSId=newCaseResult[j].seq_id;  //记录上一个选中主诉及既往病历病历seq_id
					alreadySelectZSBSMark=1;  //新老病历标记
				}else if(casenum==2){
					alreadySelectJCZDId=newCaseResult[j].seq_id;  //记录上一个选中检查及诊断病历seq_id
					alreadySelectJCZDMark=1;  //新老病历标记
				}else if(casenum==3){
					alreadySelectZLFAId=newCaseResult[j].seq_id;  //记录上一个选中诊疗方案病历seq_id
					alreadySelectZLFAMark=1;//新老病例标记
				}else if(casenum==4){
					alreadySelectXFFAId=newCaseResult[j].seq_id;  //记录上一个选中修复方案病历seq_id
				}
				allCasesHtml+='<option selected value='+newCaseResult[j].seqId+' mark="1">新'+itemName+''+(j+1)+'</option>';
			}else{
				allCasesHtml+='<option value='+newCaseResult[j].seqId+' mark="1">新'+itemName+''+(j+1)+'</option>';
			}
		}
	}

	$("."+father).find("#allCases").html(allCasesHtml);   //初始化方案下拉框
}

//选择方案
// father:父布局li的class casenum：更新选中的病历act 1:主诉及既往病历  2.检查及诊断 3.诊疗方案 4.修复方案 5.新版主诉即既往病史 6.新诊疗方案  alreadySeqId：已经选中的病历
function selectCase(father,casenum){
	if(casenum==1){
		if(alreadySelectZSBSId){
			updateCaseStatus(casenum,alreadySelectZSBSId,"0",alreadySelectZSBSMark);  //改变之前已经选中的方案
		}
	}else if(casenum==2){
		if(alreadySelectJCZDId){
			updateCaseStatus(casenum,alreadySelectJCZDId,"0",alreadySelectJCZDMark);  //改变之前已经选中的方案
		}
	}else if(casenum==3){
		if(alreadySelectZLFAId){
			updateCaseStatus(casenum,alreadySelectZLFAId,"0",alreadySelectZLFAMark);  //改变之前已经选中的方案
		}
	}else if(casenum==4){
		if(alreadySelectXFFAId){
			updateCaseStatus(casenum,alreadySelectXFFAId,"0","0");  //改变之前已经选中的方案
		}
	}
	var selectCasesId=$("."+father).find("#allCases").val();
	var mark=$("."+father+" option:selected").attr("mark");  //主诉新老病历的标记属性
	var status=1;
	updateCaseStatus(casenum,selectCasesId,status,mark);  //选中当前方案
}

//诊疗方案选中方案（病历），改变状态
//casenum:用来更新选中的病历act 1:主诉及既往病历  2.检查及诊断 3.诊疗方案 4.修复方案 5.新版主诉即既往病史 6.新诊疗方案 selectCasesId：病历seq_id status:要修改的状态值
//mark:标记新老病历  0-->老病历  1-->新病例
function updateCaseStatus(casenum,selectCasesId,status,mark){
	var url="";
	if(casenum==1){
		if(mark=="0"){
			url = contextPath + '/HUDH_ZzblAct/selectedAnamnesisInfor.act';  //主诉老病历选中接口
		}else if(mark=="1"){
			url = contextPath + '/HUDH_ZzblAdviceAct/updateCaseHistoryById.act'; //主诉新病历选中接口
		}
	}else if(casenum==2){
		if(mark=="0"){
			url = contextPath + '/HUDH_ZzblAct/selectedExamineDiagnoseInfor.act';//口腔专科检查选中--老版接口
		}else if(mark=="1"){
			url = contextPath + '/HUDH_MedicalRecordsAct/installData.act';//口腔专科检查选中--新版接口
		}
	}else if(casenum==3){
		if(mark=="0"){
			url = contextPath + '/HUDH_ZzblAct/selectedScheme.act';  //诊疗老病历选中接口
		}else if(mark=="1"){
			url = contextPath + '/HUDH_LcljCaseAct/update.act'; //诊疗新病历选中接口
		}
	}else if(casenum==4){
		url = contextPath + '/HUDH_ZzblAct/selectedRepairProjectInfor.act';
	}
    var param = {
			SEQ_ID:selectCasesId,
			seqId:selectCasesId,
    		id : selectCasesId,
    		status : status
    };
    //console.log(JSON.stringify(param)+"-------方案选中参数");
    $.ajax({
		url: url,
		type:"POST",
		dataType:"json",
		data : param,
		success:function(result){
			layer.alert("选择此方案成功！", {
	            end: function() {
	                window.location.reload();//刷新本页面
	            }
	      	});
		}
  	});
}

//新旧病历切换 thi:当前点击按钮  num:选中的病历 1:主诉及既往病历  2.检查及诊断 3.诊疗方案 4.修复方案 5.新版主诉即既往病史 6.新版口腔专科检查 casetype:0-->老病历  1-->新病历
function toggleCase(thi,num,casetype){
	var toggleId=$(thi).attr("id");  //当前选中的病历seqid
	var url="";
	//主诉老病历
	if(num==1 && casetype==0){
		$(thi).text("切换新版");
		$(thi).attr("onclick","toggleCase(this,1,1)");
		//url = contextPath + '/HUDH_ZzblAskAct/findCaseHistoryById.act';
		//initCaseHistory(url,1);
        initCaseList(oldCaseResult,"jwsLi","ask_Previous",1);
	}
	//主诉新病历
	if(num==1 && casetype==1){
		$(thi).text("切换旧版");
		$(thi).attr("onclick","toggleCase(this,1,0)");
		//url = contextPath + '/HUDH_ZzblAdviceAct/findCaseHistoryById.act';
		//initCaseHistory(url,5);
        initCaseList(newCaseResult,"jwsLi","ask_Previous",5);
	}
	//口腔专科检查
	if(num==2 && casetype==0){
		$(thi).text("切换新版");
		$(thi).attr("onclick","toggleCase(this,2,1)");
		//url = contextPath + '/HUDH_ZzblCheckAct/findZzblOprationById.act';  //老病历url
		//initZzblOpration(url,2);
        initCaseList(oldCaseResult,"jczdLi","examine_diagnose",2);
	}
	//口腔专科检查（新）
	if(num==2 && casetype==1){
		$(thi).text("切换旧版");
		$(thi).attr("onclick","toggleCase(this,2,0)");
		//url =  contextPath + '/HUDH_MedicalRecordsAct/selectdata.act';
		//initZzblOpration(url,6);
        initCaseList(newCaseResult,"jczdLi","examine_diagnose",6);
	}

	//诊疗方案
	if(num==3 && casetype==0){
		$(thi).text("切换新版");
		$(thi).attr("onclick","toggleCase(this,3,1)");
		// url =contextPath + '/HUDH_ZzblAct/findZzblOprationById.act';  //老病历url
		//initDiagnosisProject(url,3);
        initCaseList(oldCaseResult,"zlCasesLi","diagnosis_case",3);
	}
	//诊疗方案（新）
	if(num==3 && casetype==1){
		$(thi).text("切换旧版");
		$(thi).attr("onclick","toggleCase(this,3,0)");
		//var url = contextPath + '/HUDH_LcljCaseAct/select.act';
		//initDiagnosisProject(url,7);
        initCaseList(newCaseResult,"zlCasesLi","diagnosis_case",7);
	}
}

//查询数据
function getResult(url){
	var value;
	$.ajax({
		url: url,
		type:"POST",
		dataType:"json",
		async:false,
		data : {
			id :  id, //临床路径ID
			order_number : order_number,
			lcljId:id, //临床路径ID(新版口腔专科检查参数)
		},
		success:function(result){
			value = result;
		}
	});
	return value;
}
//多方案布局初始化
//初始化主诉及既往病史 (新老病历都有) casenum：1-->主诉老版 5-->主诉新版
function initCaseHistory(url,casenum){
	//var url = contextPath + '/HUDH_ZzblAskAct/findCaseHistoryById.act';
	$.ajax({
		url: url,
		type:"POST",
		dataType:"json",
		data : {
			id :  id, //临床路径ID
			order_number : order_number
		},
		success:function(result){
			// console.log(JSON.stringify(result)+"--------------主诉及既往病史初始化result");
            oldCaseResult=result;
            initSelectList("jwsLi",1); //初始化既往病史下拉框
			initCaseList(result,"jwsLi","ask_Previous",casenum); //初始化诊疗方案列表
		}
	});
}
/*function initCaseHistory(){
	var url = contextPath + '/HUDH_ZzblAskAct/findCaseHistoryById.act';
	$.ajax({
		url: url,
		type:"POST",
		dataType:"json",
		data : {
			 id :  id, //临床路径ID
			 order_number : order_number
		},
		success:function(result){
			//console.log(JSON.stringify(result)+"--------------主诉及既往病史初始化result");
			initCaseList(result,"jwsLi","ask_Previous",1); //初始化诊疗方案列表
		}
  });
}*/

//初始化检查及诊断
//初始化检查及诊断 (新老病历都有) casenum：2-->老版 6-->新版
function initZzblOpration(url,casenum){
	// var url = contextPath + '/HUDH_ZzblCheckAct/findZzblOprationById.act';
	$.ajax({
		url: url,
		type:"POST",
		dataType:"json",
		data : {
			 id :  id, //临床路径ID
			 order_number : order_number,
			 lcljId:id, //临床路径ID(新版参数)
		},
		success:function(result){
            oldCaseResult=result;
            initSelectList("jczdLi",2); //检查即诊断下拉框
			// console.log(JSON.stringify(result)+"--------------诊疗方案---result");
			initCaseList(result,"jczdLi","examine_diagnose",casenum); //初始化诊疗方案列表
		}
  });
}

//初始化诊疗方案
function initDiagnosisProject(url,casenum){
	//var url = contextPath + '/HUDH_ZzblAct/findZzblOprationById.act';
	$.ajax({
		url: url,
		type:"POST",
		dataType:"json",
		data : {
			id :  id, //临床路径ID
			order_number : order_number
		},
		success:function(result){
            oldCaseResult=result;
            initSelectList("zlCasesLi",3); //诊疗方案下拉框
			// console.log(url,JSON.stringify(result)+"1--------------诊疗方案result");
			initCaseList(result,"zlCasesLi","diagnosis_case",casenum); //初始化诊疗方案列表
		}
	});
}
/*function initDiagnosisProject(){
	var url = contextPath + '/HUDH_ZzblAct/findZzblOprationById.act';
	$.ajax({
		url: url,
		type:"POST",
		dataType:"json",
		data : {
			 id :  id, //临床路径ID
			 order_number : order_number
		},
		success:function(result){
			//console.log(JSON.stringify(result)+"--------------诊疗方案初始化result");
			initCaseList(result,"zlCasesLi","diagnosis_case",3); //初始化诊疗方案列表
		}
  });
}*/

//初始化修复方案
function initRepairProject(){
	var url = contextPath + '/HUDH_RepairSchemeConfirmAct/findRepairInforById.act';
	$.ajax({
		url: url,
		type:"POST",
		dataType:"json",
		data : {
			 id :  id, //临床路径ID
			 order_number : order_number
		},
		success:function(result){
            oldCaseResult=result;
            initSelectList("xffaLi",4); //修复方案下拉框
			//console.log(JSON.stringify(result)+"--------------修复方案初始化result");
			initCaseList(result,"xffaLi","xiufu_test",4); //初始化诊疗方案列表
		}
  });
}

/**************************************************/
