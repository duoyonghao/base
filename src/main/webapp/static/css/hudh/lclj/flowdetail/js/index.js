var apppath = apppath();
var orderNumber = getUrlParam("orderNumber");
var hasPass;
var select_imageological_examination;
var veUrl;
var ordNumber;
var seqId;//节点信息表id
var nodeId;
var nodeStas;
var nodeName;//节点名称
var nodeId1;//节点点击nodeId
var registrationStatus=0;
var staticVar = {
		tooth_bitmap_color : "#FF6100", //右上角牙位图选中颜色
		
		flow_node_0 : "#b5b5b5", //流程节点  未完成  颜色
		flow_node_1 : "#58bb35", //流程节点  进行中  颜色
		flow_node_2 : "#00a6c0", //流程节点  已完成  颜色
		flow_node_3 : "#d10c0c", //流程节点  超期      颜色
		flow_node_other : "#f0e91e" // 路径退回的颜色
}
$(function(){
	//获取当前页面所有按钮
	getButtonAllCurPage(menuid);
	
	//初始化患者信息及流程图
	initFlow();
	
	if (veUrl) {
		//初始化iframe
		$('#myiframe').attr('src', apppath + veUrl);
	} else {
		layer.alert("veUrl为空！");
	}
	
	getNodeId();//退回上一节点时获取该节点对应节点信息表id
	
	//获取初始化备注列表数据
	getTabledata(apppath + "/HUDH_FlowAct/findOptRecodeList.act?orderNumber=" + orderNumber);
	
	//初始化挂号列表
	getRegList(apppath + "/HUDH_FlowAct/findRegListByBlcode.act?orderNumber=" + orderNumber);
	
	//初始化备注列表
	getRemarkList();//添加方法
	
	//初始化搜索按钮方法
	$('#search_btn').bind('click',function (){
		var search_flowlink = $('#search_flowlink').val();
		getTabledataBySearch(apppath + "/HUDH_FlowAct/findOptRecodeList.act?orderNumber=" + orderNumber + '&searchFlowink=' + search_flowlink);
	});
	
	//初始化tab页切换
	$('li[name="tab_li"]').on('click',function (){
		$('li[name="tab_li"]').removeClass('current-li');
		$(this).addClass('current-li');
		tab_id = $(this).attr('tab-id');
		$('div[name="tab_div"]').css('display','none');
		$('#div-context').find('div[tab-id="'+tab_id+'"]').css('display','block');
	});
});
//	初始化备注table数据展示
function getTabledata(url){
	$.ajax({
		url: url,
		type:"POST",
		dataType:"json",
		success:function(result){
			var operateObj=eval(result.remarks);
			hasPass = result.hasPass;
			var htmlStr="";
			for (var i = 0; i < operateObj.length; i++) {
				htmlStr+="<tr>";
				htmlStr+="<td>"+operateObj[i].createtime+"</td>";
				htmlStr+="<td>"+operateObj[i].user_name+"</td>";
				htmlStr+="<td>"+operateObj[i].flow_link+"</td>";
				htmlStr+="<td>"+operateObj[i].remarks+"</td>";
				htmlStr+="<td>"+operateObj[i].variation+"</td>";
				
				//var stus = operateObj[i].status == "0" ? "<span style='cursor: pointer;' value='1'>通过</span><span style='margin-left:20px;cursor: pointer;' value='2'>不通过</span>" : "<font style='color:#17da47'>审核通过</font>"; 
				if(operateObj[i].status == "0") {
					stus = "<span style='cursor: pointer;' status='"+operateObj[i].status+"' value='1' onclick='updateRemarkSuts(this)'>√</span><span status='"+operateObj[i].status+"' onclick='updateRemarkSuts(this)' style='margin-left:20px;cursor: pointer;' value='2'>×</span>";
				}else if(operateObj[i].status == "1") {
					stus = "<font style='color:#17da47' status='"+operateObj[i].status+"'>审核通过</font>";
				}else if(operateObj[i].status == "2"){
					stus = "<font style='color:#FF0000' status='"+operateObj[i].status+"'>审核未通过</font>";
				}
				
				
				if(hasPass == "YES" && operateObj[i].status == "0") {
					htmlStr+="<td id='"+operateObj[i].id+"' >"+stus+"</td>";
				}else {
					htmlStr+="<td>"+stus+"</td>";
				}
				htmlStr+="</tr>";
				$("#operate_tbody").html(htmlStr);
			}

			//判断是否需要显示退回按钮
			/*if(hasPass == "YES") {
				$("#bottom_btn").find("#reject")[0].style.display="inline";
			}*/
		}
	});
}

//初始化挂号table数据展示
function getRegList(url){
	$.ajax({
		url: url,
		type:"POST",
		dataType:"json",
		success:function(result){
			var operateObj=eval(result.regs);
			var htmlStr="";
			for (var i = 0; i < operateObj.length; i++) {
				htmlStr+="<tr>";
				htmlStr+="<td style='width:10%'>"+(i+1)+"</td>";
				htmlStr+="<td style='width:25%'>"+operateObj[i].createtime+"</td>";
				htmlStr+="<td style='width:15%'>"+operateObj[i].recesort+"</td>";
				htmlStr+="<td style='border-right: 1px solid #cdcccc;width:15%'>"+operateObj[i].regsort+"</td>";
				//htmlStr+="<td>"+(operateObj[i].ifcost=='0'?'未缴费':'已缴费')+"</td>";
				htmlStr+="<td style='border-right:none;width:20%'>"+(operateObj[i].doctor==null?'':operateObj[i].doctor)+"</td>";
				//htmlStr+="<td>"+operateObj[i].repairdoctor+"</td>";
				htmlStr+="</tr>";
				$("#register_tbody").html(htmlStr);
			}
		}
	});
}
//初始化备注table数据展示
function getRemarkList(){
	$.ajax({
		url: apppath + "/HUDH_LcljOperationNodeRemarkAct/findNodeRemarkByNodeId.act",
		type:"POST",
		dataType:"json",
		data : {
			"order_number" : patientObj.order_number,
			"nodeId" : patientObj.nodeid
		},
		success:function(result){
//			console.log(result,+"@*@*@");
//			var operateObj = eval(result.regs);
			var htmlStr="";
			for (var i = 0; i < result.length; i++) {
				htmlStr+="<tr>";
				htmlStr+="<td style='width:5%;'>"+(i+1)+"</td>";
				htmlStr+="<td style='border-right: 1px solid #cdcccc;width:20%;'>"+result[i].createtime+"</td>";
				htmlStr+="<td style='border-right: 1px solid #cdcccc;width:15%;'>"+result[i].user_name+"</td>";
				htmlStr+="<td style='border-right: 1px solid #cdcccc;width:35%;'>"+result[i].remark+"</td>";
				htmlStr+="<td style='width:15%;'>"+result[i].nodename+"</td>";
				htmlStr+="</tr>";
				$("#remark_tbody").html(htmlStr);
			}
		}
	});
}
//初始化备注table数据展示
function getRemarkList1(nodeid){
	$.ajax({
		url: apppath + "/HUDH_LcljOperationNodeRemarkAct/findNodeRemarkByNodeId.act",
		type:"POST",
		dataType:"json",
		data : {
			"order_number" : patientObj.order_number,
			"nodeId" : nodeid
		},
		success:function(result){
//			console.log(result,+"@*@*@");
//			var operateObj = eval(result.regs);
			var htmlStr="";
			for (var i = 0; i < result.length; i++) {
				htmlStr+="<tr>";
				htmlStr+="<td style='width:5%;'>"+(i+1)+"</td>";
				htmlStr+="<td style='border-right: 1px solid #cdcccc;width:20%;'>"+result[i].createtime+"</td>";
				htmlStr+="<td style='border-right: 1px solid #cdcccc;width:15%;'>"+result[i].user_name+"</td>";
				htmlStr+="<td style='border-right: 1px solid #cdcccc;width:35%;'>"+result[i].remark+"</td>";
				htmlStr+="<td style='width:15%;'>"+result[i].nodename+"</td>";
				htmlStr+="</tr>";
				$("#remark_tbody").html(htmlStr);
			}
		}
	});
}
function updateRemarkSuts(thi){
	var id = $(thi).parent("td").attr("id");
	var currentStatus = $(thi).attr("status"); //当前点击备注的状态
	//var remarkInfo = $(thi).html();
	if(hasPass == "NO") {
		layer.alert("当前人员无操作权限");
		return;
	}
	if(currentStatus == "1" || currentStatus == "2"){
		layer.alert("当前记录已审核");
		return;
	}
	var status = $(thi).attr("value"); //当前需要改变的状态
	var msg = (status == "1" ? "通过审核":"该条备注信息有误，未通过审核！");
	var icon = (status == "1" ? 6 : 5);
	layer.alert(msg, {
		  closeBtn: 1    // 是否显示关闭按钮
		  ,anim: 1 //动画类型
		  ,icon : icon //文字前方表情类型
		  ,btn: ['确认','关闭'] //按钮
		  ,yes:function(index){
			 $.ajax({
					url: apppath + "/HUDH_FlowAct/updateRemarkStus.act",
					type:"POST",
					dataType:"json",
					data : {"status" : status,"id" : id},
					success:function(result){
						layer.alert('审核成功', function(index) {
							layer.close(index);
							//获取初始化备注列表数据
							getTabledata(apppath + "/HUDH_FlowAct/findOptRecodeList.act?orderNumber=" + orderNumber);
						})
					}
				});
		  }
		  ,btn2:function(){}
	})
}


//点击搜索获取备注信息展示
function getTabledataBySearch(url){
	$.ajax({
		url: url,
		type:"POST",
		dataType:"json",
		success:function(result){
			var operateObj=eval(result.remarks);
			var hasPass = result.hasPass;
			var length = operateObj.length;
			if(length > 0) {
				var htmlStr="";
				for (var i = 0; i < length; i++) {
					htmlStr+="<tr>";
					htmlStr+="<td>"+operateObj[i].createtime+"</td>";
					htmlStr+="<td>"+operateObj[i].user_name+"</td>";
					htmlStr+="<td>"+operateObj[i].flow_link+"</td>";
					htmlStr+="<td>"+operateObj[i].remarks+"</td>";
					htmlStr+="<td>"+operateObj[i].variation+"</td>";

					if(operateObj[i].status == "0") {
						stus = "<span style='cursor: pointer;' status='"+operateObj[i].status+"' value='1' onclick='updateRemarkSuts(this)'>√</span><span status='"+operateObj[i].status+"' onclick='updateRemarkSuts(this)' style='margin-left:20px;cursor: pointer;' value='2'>×</span>";
					}else if(operateObj[i].status == "1") {
						stus = "<font style='color:#17da47' status='"+operateObj[i].status+"'>审核通过</font>";
					}else if(operateObj[i].status == "2"){
						stus = "<font style='color:#FF0000' status='"+operateObj[i].status+"'>审核未通过</font>";
					}
					
					if(hasPass == "YES" && operateObj[i].status == "0") {
						htmlStr+="<td id='"+operateObj[i].id+"' >"+stus+"</td>";
					}else {
						htmlStr+="<td>"+stus+"</td>";
					}
					htmlStr+="</tr>";
					$("#operate_tbody").html(htmlStr);
				}
			}else {
				layer.alert('该环节未录入备注信息');
				return;
			}
			
		}
	});
}

//初始化流程
var nodeLists;
var lcljInfo;
function initFlow(){
	$.ajax({
		url: apppath + "/HUDH_FlowAct/findOrderTrackInfo.act?orderNumber=" + orderNumber,
		type:"POST",
		dataType:"json",
		async:false,
		success:function(result){
			lcljInfo=result;  //存储整个临床路径信息对象
			//console.log(JSON.stringify(result)+"----------整个页面返回数据");
			//判断当前节点有无下一节点
			//console.log(result.nodes);
			nodeLists = result.nodes;
			for (var i = 0; i < nodeLists.length; i++) {	
				if(nodeLists[i].stus=="1"){
					//判断下一个节点是否为0
					if(i==nodeLists.length-1){
						//判断是否挂号
						var url = contextPath + '/KQDS_REGAct/selectExistByUsercode.act';
						$.ajax({
					         type: "post",
					         url: url,
					         data: {usercode : result.blcode},
					         dataType: "json",
					         async:false,
					         success: function(data){
					           	if(data.status=="未挂号"){
					           		registrationStatus+=1;
								    layer.alert("患者未挂号，请挂号后操作当前节点！");
								    $("#lclj_tj").attr("disabled",true).css("background","#b0b0b0").css("cursor","not-allowed");
									$("#lclj_by").attr("disabled",true).css("background","#b0b0b0").css("cursor","not-allowed");
								    return false;
								}
					         }
					    });
					}else if(nodeLists[i+1].stus=="0"){
						//判断是否挂号
						var url = contextPath + '/KQDS_REGAct/selectExistByUsercode.act';
						$.ajax({
					         type: "post",
					         url: url,
					         data: {usercode : result.blcode},
					         dataType: "json",
					         async:false,
					         success: function(data){
					           	if(data.status=="未挂号"){
					           		registrationStatus+=1;
								    layer.alert("患者未挂号，请挂号后操作当前节点！");
								    $("#lclj_tj").attr("disabled",true).css("background","#b0b0b0").css("cursor","not-allowed");
									$("#lclj_by").attr("disabled",true).css("background","#b0b0b0").css("cursor","not-allowed");
								    return false;
								}
					         }
					    });
					}
				}
			}		
			if(registrationStatus==0){
				$("#lclj_tj").attr("disabled",false).css("background","#00A6C0").css("cursor","pointer");
				$("#lclj_by").attr("disabled",false).css("background","#00A6C0").css("cursor","pointer");
			}
//			console.log(result);
			veUrl = result.viewurl;
			ordNumber = result.orderNumber;
			nodeId = result.nodeid;
//			alert(nodeId);
//			患者信息
			patientObj=eval(result);
			
			$("#p_details").find("#patient_id").text(patientObj.order_number);
			$("#p_details").find("#patient_count").text(patientObj.tooth+"颗");
			$("#p_details").find("#patient_creatime").text(patientObj.createtime);
			$("#p_details").find("#patient_flowlink").text(patientObj.nodename);
			
			//判断是否结束,如果结束则不显示底部按钮
			if(patientObj.flow_link == "结束") {
				$('#bottom_btn').css('display','none');
			}
			
//			判断是否植骨
			var type;
			if(patientObj.type=="1"){type="单颗多颗"}
			else if(patientObj.type=="2"){type="Locator"}
			else if(patientObj.type=="3"){type="All-on-x"};
			$("#p_details").find("#patient_type").text(result.flowname/*type + (patientObj.bone=="是"?"植骨":"无植骨")*/);
			$("#p_details").find("#patient_status").text(patientObj.ss_status);
			
			//患者详情页信息赋值
			$("#blcode").html($("#blcode").html()+"<span class='patientInfoText'>"+patientObj.blcode+"</span>");
			$("#hzname").html($("#hzname").html()+"<span class='patientInfoText'>"+patientObj.username+"</span>");
			$("#hzphone").html($("#hzphone").html()+"<span class='patientInfoText'>"+patientObj.phonenumber1+"</span>");
			$("#hzage").html($("#hzage").html()+"<span class='patientInfoText'>"+patientObj.age+"</span>");
			$("#hzsex").html($("#hzsex").html()+"<span class='patientInfoText'>"+patientObj.sex+"</span>");
			$("#counsellor").html($("#counsellor").html()+"<span class='patientInfoText'>"+patientObj.counsellor+"</span>");
			$("#plant_physician").html($("#plant_physician").html()+"<span class='patientInfoText'>"+patientObj.plant_physician+"</span>");
			$("#repair_physician").html($("#repair_physician").html()+"<span class='patientInfoText'>"+patientObj.repair_physician+"</span>");
			$("#clinic_nurse").html($("#clinic_nurse").html()+"<span class='patientInfoText'>"+patientObj.clinic_nurse+"</span>");
			$("#customer_service").html($("#customer_service").html()+"<span class='patientInfoText'>"+patientObj.customer_service+"</span>");
			$("#plant_system").html($("#plant_system").html()+"<span class='patientInfoText'>"+patientObj.plant_system+"</span>");
			$("#crown_material").html($("#crown_material").html()+"<span class='patientInfoText'>"+patientObj.tooth_texture+"</span>");
			//console.log(JSON.stringify(patientObj)+"--------患者所有信息");
			
			//手术信息赋值
			var imageological_examination = $("#imageological_examination").find("input[type='checkbox']");
			select_imageological_examination = patientObj.imageological_examination;
			if(select_imageological_examination){
				for(var i = 0; i < select_imageological_examination.split(";").length; i++) {
					for(var j = 0; j < imageological_examination.length; j++) {
						if(select_imageological_examination.split(";")[i]) {
							if(imageological_examination[j].value == select_imageological_examination.split(";")[i]) {
								$(imageological_examination[j]).attr("checked","checked");
								break;
							}
						}
					}
				}
				$("#imageological_examination").find("input").attr("disabled","disabled");
			}
			
			//专家会诊赋值
//			var consultation = $("#consultation").find("input[type='checkbox']");
			var consultation = $("#myiframe").find("input[name='checkbox']");
//			alert(consultation);
			var select_consultation = patientObj.consultation;
//			alert(JSON.stringify(select_consultation));
			for(var i = 0; i < select_consultation.split(";").length; i++) {
				for(var j = 0; j < consultation.length; j++) {
					if(select_consultation.split(";")[i]) {
						if(consultation[j].value == select_consultation.split(";")[i]) {
							$(consultation[j]).attr("checked","checked");
							break;
						}
					}
				}
			}
			
			//咨询赋值
			var advisory = $("#advisory").find("input[type='checkbox']");
			var select_advisory = patientObj.advisory;
//			alert(JSON.stringify(select_advisory));
			for(var i = 0; i < select_advisory.split(";").length; i++) {
				for(var j = 0; j < advisory.length; j++) {
					if(select_advisory.split(";")[i]) {
						if(advisory[j].value == select_advisory.split(";")[i]) {
							$(advisory[j]).attr("checked","checked");
							break;
						}
					}
				}
			}
			
//			种植牙位图
//			左上
			var leftup=patientObj.left_up;
//			console.log(leftup.split(",")+"hha");
			if(leftup!=""){
				for (var i = 0; i < leftup.split(",").length; i++) {
					$("#zhongzhi").find(".upYa").find(".numle"+(leftup.split(",")[i])).parent().addClass("current");
				}
			}
			
//			左下
			var leftdown=patientObj.left_down;
//			console.log(leftdown.split(","));
			if(leftdown!=""){
				for (var i = 0; i < leftdown.split(",").length; i++) {
					$("#zhongzhi").find(".downYa").find(".numle"+(leftdown.split(",")[i])).parent().addClass("current");
				}
			}
			
//			右上
			var rightup=patientObj.right_up;
//			console.log(rightup.split(","));
			if(rightup!=""){
				for (var i = 0; i < rightup.split(",").length; i++) {
					$("#zhongzhi").find(".upYa").find(".numrg"+(rightup.split(",")[i])).parent().addClass("current");
				}
			}
			
//			右下
			var rightdown=patientObj.right_down;
//			console.log(rightdown);
			if(rightdown!=""){
				for (var i = 0; i < rightdown.split(",").length; i++) {
					$("#zhongzhi").find(".downYa").find(".numrg"+(rightdown.split(",")[i])).parent().addClass("current");
				}
			}
//			修复牙位图
//			左上
			var leftup=patientObj.repair_left_up;
//			console.log(leftup.split(",")+"hha");
			if(leftup!=""){
				for (var i = 0; i < leftup.split(",").length; i++) {
					$("#xiufu").find(".upYa").find(".numle"+(leftup.split(",")[i])).parent().addClass("current");
				}
			}
			
//			左下
			var leftdown=patientObj.repair_left_down;
//			console.log(leftdown.split(","));
			if(leftdown!=""){
				for (var i = 0; i < leftdown.split(",").length; i++) {
					$("#xiufu").find(".downYa").find(".numle"+(leftdown.split(",")[i])).parent().addClass("current");
				}
			}
			
//			右上
			var rightup=patientObj.repair_right_up;
//			console.log(rightup.split(","));
			if(rightup!=""){
				for (var i = 0; i < rightup.split(",").length; i++) {
					$("#xiufu").find(".upYa").find(".numrg"+(rightup.split(",")[i])).parent().addClass("current");
				}
			}
			
//			右下
			var rightdown=patientObj.repair_right_down;
//			console.log(rightdown);
			if(rightdown!=""){
				for (var i = 0; i < rightdown.split(",").length; i++) {
					$("#xiufu").find(".downYa").find(".numrg"+(rightdown.split(",")[i])).parent().addClass("current");
				}
			}
			
//			流程图
			flowlist = patientObj.nodes;
			flowname = patientObj.flowname
			nodename = patientObj.nodename
			//console.log(JSON.stringify(flowlist)+"----------流程初始化数据");
			
			//手术治疗节点：显示更改牙位按钮
			if(flowlist[1].stus=="1" || (flowname == "种植杆卡修复螺丝(术前取模)" || flowname == "种植杆卡修复基台(术前取模)" && nodename == "手术治疗")){
				$(".showToothMap").css("display","block")
				//$(".showToothMap").attr("onclick","test()")
			}else{
				$('.toothMap').removeAttr('onclick')
			}
			//手术治疗节点：显示更改牙位按钮
			/*for (var f = 0; f < flowlist.length; f++) {
				if(flowlist[f].stus==1){
					if(flowlist[f].nodeName.indexOf("手术治疗") == -1){
						$('.toothMap').removeAttr('onclick');
						break;
					}
				}
				
			}*/
			var flowString="";
			for (var i = 0; i < flowlist.length; i++) {	
//				viewUrl = "/base" + flowlist[i].viewUrl;
//				alert(viewUrl);
				flowString+="<li>";
//				console.log(flowlist[i].nodeLimit);
				var nodeLimit = "";
				if(flowlist[i].nodeLimit != 0) {//初始化每个节点的天数
//					nodeLimit = "("+flowlist[i].nodeLimit+")";
					nodeLimit = ""+flowlist[i].nodeLimit+"";
					/*if(flowlist[i].limitType == 1) {
						nodeLimit = "（"+flowlist[i].nodeLimit + "）";
					} else if (flowlist[i].limitType == 2){
						nodeLimit = "（"+flowlist[i].nodeLimit + "）";
					} else if (flowlist[i].limitType == 3) {
						nodeLimit = "（"+flowlist[i].nodeLimit + "）";
					}*/
				}
				
				// 2019/8/9 lutian 修改临床路径退回的颜色改一个其他颜色   start
				var nowIndex; // 当前操作节点下标
				if(flowlist[i].stus==1){
					nowIndex=i;
				}
				//end
				
				if(flowlist[i].stus==0){
					flowString+="<span nodestatus='"+flowlist[i].stus+"' viewurl='" + flowlist[i].viewUrl + "' id='" + flowlist[i].nodeId + "' nodeName='" + flowlist[i].nodeName + "' style='background-color: " + staticVar.flow_node_0 + ";' onclick='nodeClick(this)'>" + (i+1)+"</span>";
				}else if(flowlist[i].stus==1){
					flowString+="<div class='nodeBorder' style='border:1px solid " + staticVar.flow_node_1 + ";'><span nodestatus='"+flowlist[i].stus+"' viewurl='" + flowlist[i].viewUrl + "' id='" + flowlist[i].nodeId + "' nodeName='" + flowlist[i].nodeName +  "' style='background-color: " + staticVar.flow_node_1 + ";' class='arrow_box' onclick='nodeClick(this)'>" + (i+1)+"</span></div>";
				}else if(flowlist[i].stus==2){
					// 2019/8/9 lutian 修改临床路径退回的颜色改一个其他颜色   start
					if(i>nowIndex){
						flowString+="<span nodestatus='"+flowlist[i].stus+"' viewurl='" + flowlist[i].viewUrl + "' id='" + flowlist[i].nodeId + "' nodeName='" + flowlist[i].nodeName +  "' style='background-color: " + staticVar.flow_node_other + ";' onclick='nodeClick(this)'>" + (i+1)+"</span>";
					}else{
						flowString+="<span nodestatus='"+flowlist[i].stus+"' viewurl='" + flowlist[i].viewUrl + "' id='" + flowlist[i].nodeId + "' nodeName='" + flowlist[i].nodeName +  "' style='background-color: " + staticVar.flow_node_2 + ";' onclick='nodeClick(this)'>" + (i+1)+"</span>";
					}
					//end
					
					//flowString+="<span nodestatus='"+flowlist[i].stus+"' viewurl='" + flowlist[i].viewUrl + "' id='" + flowlist[i].nodeId + "' nodeName='" + flowlist[i].nodeName +  "' style='background-color: " + staticVar.flow_node_2 + ";' onclick='nodeClick(this)'>" + (i+1)+"</span>";
				}else if(flowlist[i].stus==3){
					flowString+="<span nodestatus='"+flowlist[i].stus+"' viewurl='" + flowlist[i].viewUrl + "' id='" + flowlist[i].nodeId + "' nodeName='" + flowlist[i].nodeName +  "' style='background-color: " + staticVar.flow_node_3 + ";' onclick='nodeClick(this)'>" + (i+1)+"</span>";
				}
//			节点文字颜色
				if(flowlist[i].stus==0){
					flowString+="<font color="+staticVar.flow_node_0+"><span class='size'>"+flowlist[i].nodeName+"</span><br/>"+nodeLimit+"</font>";
				}
				if(flowlist[i].stus==1){
					flowString+="<font color="+staticVar.flow_node_1+"><span class='size'>"+flowlist[i].nodeName+"</span><br/>"+nodeLimit+"</font>";
				}
				if(flowlist[i].stus==2&&i>nowIndex){
					if(i>nowIndex){
						flowString+="<font color="+staticVar.flow_node_other+"><span class='size'>"+flowlist[i].nodeName+"</span><br/>"+nodeLimit+"</font>";
					}else{}					
				}
				if(flowlist[i].stus==2){
					if(i>nowIndex){
						
					}else{
						flowString+="<font color="+staticVar.flow_node_2+"><span class='size'>"+flowlist[i].nodeName+"</span><br/>"+nodeLimit+"</font>";
					}				
				}
				if(flowlist[i].stus==3){
					flowString+="<font color="+staticVar.flow_node_3+"><span class='size'>"+flowlist[i].nodeName+"</span><br/>"+nodeLimit+"</font>";
				}	

				flowString+="</li>";
				$("#flow_path").html(flowString);
				
				//初始化根据流程步骤进行搜索的下拉选项
				$("#search_flowlink").append('<option value="'+flowlist[i].nodeName+'">'+flowlist[i].nodeName+'</option>');
			}
//			设置文字位置
			for(var i=0;i<$("#flow_path li").length;i++){
				if((i+1)%2==0){
					$("#flow_path li font")[i].setAttribute('style', 'margin-top: -100px !important');
				}
			}
//			手术治疗
			if(patientObj.nodename=="手术治疗" || patientObj.nodename=="手术治疗取模"){
				//隐藏掉修改牙位两个按钮
				$("#lclj_ywbd").css("display","none");
				$("#lclj_ywtj").css("display","none");
				
			$(".ToothBit_checkbox").attr("disabled",false);
			/*****************种植牙位图操作*******************/
			  /*//点击牙齿
			  $('.toothMap').on('click', 'li',
			  function() {
			      $(this).toggleClass('current');
			      getAllToothNum();
			  });
			  
//			  全选
			  $('.ToothBit_checkbox').each(function(i,obj){
			  	$(this).click(function(){
			  		console.log(i);
			  		if ($(this).is(':checked')) {
			  		 $(".ToothBit_checkbox"+(i+1)).each(function(index) {
			 	        	$(this).parent().removeClass('current');
			 	        	
			 	            $(this).parent().toggleClass('current');
			  	     	});
				      }else{
				      	 $(".ToothBit_checkbox"+(i+1)).each(function(index) {
					            $(this).parent().removeClass('current');
					    	 });
				      }
			  	});
			  });*/
			  /*****************牙位图操作 END*******************/
			}
			if(patientObj.nodename=="手术治疗"){
				$(".showToothMap").css("display","none");
			}
		}
	});
}

//获取dataId
function getDataId() {
	$.ajax({
		url: apppath + "/HUDH_FlowAct/findHadWorkByOrderNumberAndNodeId.act",
		type:"POST",
		dataType:"json",
		data: {
			"ordNumber" : ordNumber,
			"nodeId" : nodeId
		},
		async:false,
		success:function(data){
//			alert(JSON.stringify(data));
		}
	});
}

//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

//计算当前选中牙齿总颗数为牙齿总数赋值
function getAllToothNum (obj){
	var at = 0;
	obj.find('li').each(function (){
	var $span = $(this).find('span');
	if($(this).hasClass('current') && $span.hasClass('num')) {
		at ++;
		}
	});
		/*console.log(at);*/
	return at;
}

function showinput(){
	var patient_flowlink = $('#patient_flowlink').text();
	if(patient_flowlink=='结束'){
		layer.alert('当前手术流程已全部完成');
		return;
	}
	layer.open({
		type: 1,
		title: '添加备注信息',
		shadeClose: false,
		shade: 0.6,
		area: ['40%', '50%'],
		content: '<div style="margin-left: 10px;height: 25px;margin-top: 15px;">' +
					 '<span>变异情况：</span>' +
					 '<select id="variation">' + 
			    		'<option value="">- 请选择 -</option>' +
			    		'<option>正常</option>' +
			    		'<option>变异</option>' +
	    			 '</select>' +
    			 '</div>' +
				 '<div id="remark" style="margin-top: 25px;">' + 
					'<textarea id="remarks"></textarea>'+
			     '</div>',
		btn:['保存','关闭'],
		yes:function(index,layero){
			 var remarks = getFormatCode($("#remarks").val()); //处理成带有回车的字符串
			 var variation = $('#variation').val();
			 if(remarks && variation) {
				 $.ajax({
						url: apppath + "/HUDH_FlowAct/saveOptRecode.act?orderNumber=" + orderNumber + '&remarks=' + remarks + '&variation=' + variation,
						type:"POST",
						dataType:"json",
						success:function(result){
							getTabledata(apppath + "/HUDH_FlowAct/findOptRecodeList.act?orderNumber=" + orderNumber);
							layer.close(index); //再执行关闭
							layer.alert('保存成功');
						}
					});
			 }else {
				 layer.alert('备注信息不能为空');
				 layer.alert('变异情况不能为空');
			 }
		},
		btn2:function(index,layero){
            layer.close(index); //再执行关闭
		}
	});
};

//获取辅助手术
function getAssistOperation() {
    var obj = $(window.frames["myiframe"].document).find("input[name='assist_operation']");
    var assist_operation = "";
    for ( k in obj ) {
        if(obj[k].checked) {
        	assist_operation = assist_operation + obj[k].value + ';';
        }
    }
    return assist_operation;
} 
//获取基台放置信息
function abutmentStation() {
	var obj = $(window.frames["myiframe"].document).find("input[name='abutment_station']");
	var abutment_station = "";
	for ( k in obj ) {
		if(obj[k].checked) {
			abutment_station = abutment_station + obj[k].value + ';';
		}
	}
	return abutment_station;
} 
//获取愈合帽放置信息
function healingCapStation() {
	var obj = $(window.frames["myiframe"].document).find("input[name='healing_cap_station']");
	var healing_cap_station = "";
	for ( k in obj ) {
		if(obj[k].checked) {
			healing_cap_station = healing_cap_station + obj[k].value + ';';
		}
	}
	return healing_cap_station;
} 
//---------------------------------------------
//获取所有单选选项
function a() {
	var obj = $(window.frames["myiframe"].document).find("input[name='anesthesiaMethod']");
	var anesthesiaMethod = "";
	for ( k in obj ) {
		if(obj[k].checked) {
			anesthesiaMethod = anesthesiaMethod + obj[k].value + ';';
		}
	}
	return anesthesiaMethod;
	
} 
//获取拍X片
function b() {
	var obj = $(window.frames["myiframe"].document).find("input[name='intraoperativeMedication']");
	var intraoperativeMedication = "";
	for ( k in obj ) {
		if(obj[k].checked) {
			intraoperativeMedication = intraoperativeMedication + obj[k].value + ';';
		}
	}
	return intraoperativeMedication;
}
//---------------------------------------------变异-----------------------------------------//
/**
 * 设置权限的按钮方法 2019-5-31 syp
 * 
 */
var buttonFun = {
    	additionalRecording : function(thi){
            $("#lclj_tj").attr("disabled",false).css("background","#00A6C0").css("cursor","pointer");
            $("#lclj_by").attr("disabled",false).css("background","#00A6C0").css("cursor","pointer");
        },
		goRepairRecord:function(){
			//修复治疗记录
			layer.open({
				title:"修复治疗记录",
				type:2,
				closeBtn:1,
				shadeClose: false,
				shade: 0.6,
				content:contextPath + "/ZzblViewAct/toRepairAcographyInfor.act",
				area:['90%','80%'],
				cancel: function(){
				},
				end:function(){
					//window.location.reload();//刷新本页面
				}
			});
		},
		goAssessor : function(thi){
			var lcljss_Id=lcljId;
			if(!lcljss_Id||lcljss_Id==""||lcljss_Id==null) {
				layer.alert("请选择要审核的记录");
				return;
			}
			var Y=$(window).height()*0.25;
			layer.open({
				type: 2,
				title: '审核明细',
				offset:[Y,0],
				shadeClose: false,
				shade: 0.6,
				area: ['80%', '60%'],
				content: apppath + '/HUDH_LcljCheckRecordAct/toAssessor.act?lcljId='+lcljss_Id
			});
		},
		electiveOperation : function(thi){
			layer.open({
				type: 2,
				title: '预约',
				shadeClose: false,
				shade: 0.6,
				area: ['95%', '95%'],
				content: apppath + '/KQDS_Net_OrderAct/toYyzx2.act?menuId=601301'
			});
		},
		gotoNext : function(thi){
//			节点名称
			var nodeName;
			var nodeLimit;
			for(var i=0;i<flowlist.length;i++){
				if(flowlist[i].stus==1){
					nodeName=flowlist[i].nodeName;
					nodeLimit=flowlist[i].nodeLimit;
//					console.log(flowlist[i].nodeName+"-----------"+flowlist[i].nodeLimit);										
				}
			}
			var lclj_Id=lcljId;
			//最后一次挂号信息
			var lastRegistration = getLastestRegInfo(patientObj.blcode);
			var lastRegistrationCreatetime=lastRegistration.createtime;
			var lastRegistrationRegsort=lastRegistration.regsort;
			var lastRegistrationRecesort=lastRegistration.recesort;
			var lastRegistrationDoctor=lastRegistration.doctor;
//			console.log(lastRegistrationCreatetime+"------"+lastRegistrationRegsort+'----'+lastRegistrationRecesort+'-----'+lastRegistrationDoctor);
			//return;
			var isShouShu = "";
			var tooth_total = getAllToothNum($("#zhongzhi"));
			var zz_tooth_total = getAllToothNum($("#xiufu"));
			if ( nodeId == 'End') {
				layer.alert("本次临床路径已结束！");
				return;
			}
			if(nodeId == "Surtre") {
				isShouShu = "SHOUSHU";
				var new_select_imageological_examination = show();
				if(!tooth_total || tooth_total <= 0) {layer.alert('请输入种植牙齿颗数');return;}
				if(!zz_tooth_total || zz_tooth_total <= 0) {layer.alert('请输入修复牙齿颗数');return;} 
			}
			
			var left_up = getSelectToothNumb($("#zhongzhi .upYa>li>span[name='zzadultupYa1']")); //左上
			/*if(!left_up){
				layer.alert("牙齿不能为空，请填写！");
				return;
			}*/
			var left_down = getSelectToothNumb($("#zhongzhi .downYa>li>span[name='zzadultdownYa1']")); //左下
			var right_up = getSelectToothNumb($("#zhongzhi .upYa>li>span[name='zzadultupYa2']")); //右上
			//console.log(right_up+"-------种植牙位左上");
			var right_down = getSelectToothNumb($("#zhongzhi .downYa>li>span[name='zzadultdownYa2']")); //右下
			
			var repair_left_up = getSelectToothNumb($("#xiufu .upYa>li>span[name='zzadultupYa1']")); //左上
			var repair_left_down = getSelectToothNumb($("#xiufu .downYa>li>span[name='zzadultdownYa1']")); //左下
			var repair_right_up = getSelectToothNumb($("#xiufu .upYa>li>span[name='zzadultupYa2']")); //右上
			var repair_right_down = getSelectToothNumb($("#xiufu .downYa>li>span[name='zzadultdownYa2']")); //右下

			//手术时间
			var operation_time = $(window.frames["myiframe"].document).find("input[name='operation_time']").val();
			if($(window.frames["myiframe"].document).find("input[name='operation_time']").length>0){
				if(!operation_time) {
					var operation_timeTextnode = $(window.frames["myiframe"].document).find("input[name='operation_time']").parent().text();//找到其标题
					var operation_timeIndexstart=operation_timeTextnode.indexOf("*")+1;//截取标题序号
					if(operation_timeTextnode.indexOf("*")>=0){
						var operation_timeIndexover=operation_timeTextnode.indexOf("：");//截取冒号
						var operation_timeText = operation_timeTextnode.substring(operation_timeIndexstart,operation_timeIndexover);
						layer.alert(operation_timeText+" 不能为空，请填写！");
						return;
					}
				}
			}
			//复查时间
			var review_time = $(window.frames["myiframe"].document).find("input[name='review_time']").val();
			if($(window.frames["myiframe"].document).find("input[name='review_time']").length>0){
				if(!review_time) {
					var review_timeTextnode = $(window.frames["myiframe"].document).find("input[name='review_time']").parent().text();//找到其标题
					var review_timeIndexstart=review_timeTextnode.indexOf("*")+1;//截取标题序号
					if(review_timeTextnode.indexOf("*")>=0){
						var review_timeIndexover=review_timeTextnode.indexOf("：");//截取冒号
						var review_timeText = review_timeTextnode.substring(review_timeIndexstart,review_timeIndexover);
						layer.alert(review_timeText+" 不能为空，请填写！");
						return;
					}
				}
			}
			//来院时间
			var visit_time = $(window.frames["myiframe"].document).find("input[name='visit_time']").val();
			if($(window.frames["myiframe"].document).find("input[name='visit_time']").length>0){
				if(!visit_time) {
					var visit_timeTextnode = $(window.frames["myiframe"].document).find("input[name='visit_time']").parent().text();//找到其标题
					var visit_timeIndexstart=visit_timeTextnode.indexOf("*")+1;//截取标题序号
					if(visit_timeTextnode.indexOf("*")>=0){
						var visit_timeIndexover=visit_timeTextnode.indexOf("：");//截取冒号
						var visit_timeText = visit_timeTextnode.substring(visit_timeIndexstart,visit_timeIndexover);
						layer.alert(visit_timeText+" 不能为空，请填写！");
						return;
					}
					
				}
			}
			//术区清洁情况
			var observe_wound = $(window.frames["myiframe"].document).find("select[name='observe_wound']").val();
			if($(window.frames["myiframe"].document).find("select[name='observe_wound']").length>0){
				if(!observe_wound || observe_wound=="-请选择-") {
					var observe_woundTextnode = $(window.frames["myiframe"].document).find("select[name='observe_wound']").parent().text();//找到其标题
					var observe_woundIndexstart=observe_woundTextnode.indexOf("*")+1;//截取标题序号
					if(observe_woundTextnode.indexOf("*")>=0){
						var observe_woundIndexover=observe_woundTextnode.indexOf("：");//截取冒号
						if(observe_woundIndexover==-1){
							var observe_woundText = observe_woundTextnode.substring(observe_woundIndexstart);
						}else{
							var observe_woundText = observe_woundTextnode.substring(observe_woundIndexstart,observe_woundIndexover);
						}
						layer.alert(observe_woundText+" 不能为空，请填写！");
						return;
					}
				}
			}
			//检查伤口愈合情况
			var check_wound = $(window.frames["myiframe"].document).find("select[name='check_wound']").val();
			if($(window.frames["myiframe"].document).find("select[name='check_wound']").length>0){
				if(!check_wound || check_wound=="-请选择-") {
					var check_woundTextnode = $(window.frames["myiframe"].document).find("select[name='check_wound']").parent().text();//找到其标题
					var check_woundIndexstart=check_woundTextnode.indexOf("*")+1;//截取标题序号
					if(check_woundTextnode.indexOf("*")>=0){
						var check_woundIndexover=check_woundTextnode.indexOf("：");//截取冒号
						var check_woundText = check_woundTextnode.substring(check_woundIndexstart,check_woundIndexover);
						layer.alert(check_woundText+" 不能为空，请填写！");
						return;
					}
				}
			}
			//术前1小时 口服抗菌药物
			var preoperation_one_houres = $(window.frames["myiframe"].document).find("input[name='preoperation_one_houres']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='preoperation_one_houres']").length>0){
				if(!preoperation_one_houres) {
					var preoperation_one_houresIndex=$(window.frames["myiframe"].document).find("input[name='preoperation_one_houres']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='preoperation_one_houres']").parent().text().indexOf("*")>=0){
						var preoperation_one_houresText = $(window.frames["myiframe"].document).find("input[name='preoperation_one_houres']").parent().text().substring(preoperation_one_houresIndex);
						layer.alert(preoperation_one_houresText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//术前核查
			var preoperative_verification = $(window.frames["myiframe"].document).find("input[name='preoperative_verification']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='preoperative_verification']").length>0){
				if(!preoperative_verification) {
					var preoperative_verificationIndex=$(window.frames["myiframe"].document).find("input[name='preoperative_verification']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='preoperative_verification']").parent().text().indexOf("*")>=0){
						var preoperative_verificationText = $(window.frames["myiframe"].document).find("input[name='preoperative_verification']").parent().text().substring(preoperative_verificationIndex);
						layer.alert(preoperative_verificationText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//麻醉方式
			var anesthesiaMethod = a();
			var anesthesiaMethodTag= $(window.frames["myiframe"].document).find("input[name='anesthesiaMethod']");
			if(anesthesiaMethodTag.length>0){
				if(!anesthesiaMethod) {
					var anesthesiaMethodTextBefore=anesthesiaMethodTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					var anesthesiaMethodIndexstart=anesthesiaMethodTextBefore.indexOf("*")+1;//截取标题序号
					if(anesthesiaMethodTextBefore.indexOf("*")>=0){
						var anesthesiaMethodIndexover=anesthesiaMethodTextBefore.indexOf("：");//截取冒号
						var anesthesiaMethodText = anesthesiaMethodTextBefore.substring(anesthesiaMethodIndexstart,anesthesiaMethodIndexover);
						layer.alert(anesthesiaMethodText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//连桥
			var connectingBridge = $(window.frames["myiframe"].document).find("input[name='connectingBridge']:checked").val();
			//愈合帽放置
			var healing_cap_station = healingCapStation();
			var healing_cap_stationTag = $(window.frames["myiframe"].document).find("input[name='healing_cap_station']");
			if(healing_cap_stationTag.length>0){
				if(!healing_cap_station) {
					var healing_cap_stationTextBefore=healing_cap_stationTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					var healing_cap_stationIndexstart=healing_cap_stationTextBefore.indexOf("*")+1;//截取标题序号
					if(healing_cap_stationTextBefore.indexOf("*")>=0){
						var healing_cap_stationIndexover=healing_cap_stationTextBefore.indexOf("：");//截取冒号
						var healing_cap_stationText = healing_cap_stationTextBefore.substring(healing_cap_stationIndexstart,healing_cap_stationIndexover);
						layer.alert(healing_cap_stationText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//基台放置
			var abutment_station = abutmentStation();
			var abutment_stationTag = $(window.frames["myiframe"].document).find("input[name='abutment_station']");
			if(abutment_stationTag.length>0){
				if(!abutment_station || abutment_station==null) {
					var abutment_stationTextBefore=abutment_stationTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					var abutment_stationIndexstart=abutment_stationTextBefore.indexOf("*")+1;//截取标题序号
					if(abutment_stationTextBefore.indexOf("*")>=0){
						var abutment_stationIndexover=abutment_stationTextBefore.indexOf("：");//截取冒号
						var abutment_stationText = abutment_stationTextBefore.substring(abutment_stationIndexstart,abutment_stationIndexover);
						layer.alert(abutment_stationText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//漱口液含漱
			var collutory = $(window.frames["myiframe"].document).find("input[name='collutory']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='collutory']").length>0){
				if(!collutory) {
					var collutoryIndex=$(window.frames["myiframe"].document).find("input[name='collutory']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='collutory']").parent().text().indexOf("*")>=0){
						var collutoryText = $(window.frames["myiframe"].document).find("input[name='collutory']").parent().text().substring(collutoryIndex);
						layer.alert(collutoryText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//拍X片
			var assist_operation = getAssistOperation();
			var assist_operationTag = $(window.frames["myiframe"].document).find("input[name='assist_operation']");
			if(assist_operationTag.length>0){
				if(!assist_operation) {
					var assist_operationTextBefore=assist_operationTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					var assist_operationIndexstart=assist_operationTextBefore.indexOf("*")+1;//截取标题序号
					if(assist_operationTextBefore.indexOf("*")>=0){
						var assist_operationIndexover=assist_operationTextBefore.indexOf("：");//截取冒号
						var assist_operationText = assist_operationTextBefore.substring(assist_operationIndexstart,assist_operationIndexover);
						layer.alert(assist_operationText+" 不能为空，请选择！");
						return;
					}
				}
			}
			
			//交代注意事项 ①拍X片
			var intraoperativeMedication = b();
			var intraoperativeMedicationTag= $(window.frames["myiframe"].document).find("input[name='intraoperativeMedication']");
			if(intraoperativeMedicationTag.length>0){
				if(!intraoperativeMedication) {
					var intraoperativeMedicationTextBefore=intraoperativeMedicationTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					var intraoperativeMedicationIndexstart=intraoperativeMedicationTextBefore.indexOf("*")+1;//截取标题序号
					if(intraoperativeMedicationTextBefore.indexOf("*")>=0){
						var intraoperativeMedicationIndexover=intraoperativeMedicationTextBefore.indexOf("：");//截取冒号
						var intraoperativeMedicationText = intraoperativeMedicationTextBefore.substring(intraoperativeMedicationIndexstart,intraoperativeMedicationIndexover);
						layer.alert(intraoperativeMedicationText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//术后使用抗菌药物3~5天
			var postoperation_user_deugs = $(window.frames["myiframe"].document).find("input[name='postoperation_user_deugs']:checked").val();
			
			//曲面断层、小牙片
			var small_teeth = $(window.frames["myiframe"].document).find("input[name='small_teeth']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='small_teeth']").length>0){
				if(!small_teeth) {
					var small_teethIndex=$(window.frames["myiframe"].document).find("input[name='small_teeth']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='small_teeth']").parent().text().indexOf("*")>=0){
						var small_teethText = $(window.frames["myiframe"].document).find("input[name='small_teeth']").parent().text().substring(small_teethIndex);
						layer.alert(small_teethText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//交代注意事项
			var announcements = $(window.frames["myiframe"].document).find("input[name='announcements']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='announcements']").length>0){
				var announcementsTag= $(window.frames["myiframe"].document).find("input[name='announcements']");
				//var announcementsTagBeforeText= $(window.frames["myiframe"].document).find("input[name='announcements']").parent().text();
				var announcementsTagBeforeText= announcementsTag.eq(0).parents(".preparation-ul").find("span").text();
				if(!announcements) {
					if(!announcementsTagBeforeText){
						announcementsTagBeforeText=announcementsTag.parent().text();
					}
					var announcementsIndexStar=announcementsTagBeforeText.indexOf("*")+1;
					var announcementsIndexOver=announcementsTagBeforeText.indexOf("：");
					if(announcementsTagBeforeText.indexOf("*")>=0){
						if(announcementsIndexOver==-1){
							var announcementsText = announcementsTagBeforeText.substring(announcementsIndexStar);
						}else{
							var announcementsText = announcementsTagBeforeText.substring(announcementsIndexStar,announcementsIndexOver);
						}
						layer.alert(announcementsText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//术者完成手术记录
			var opration_record = $(window.frames["myiframe"].document).find("input[name='opration_record']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='opration_record']").length>0){
				if(!opration_record) {
					var opration_recordIndex=$(window.frames["myiframe"].document).find("input[name='opration_record']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='opration_record']").parent().text().indexOf("*")>=0){
						var opration_recordText = $(window.frames["myiframe"].document).find("input[name='opration_record']").parent().text().substring(opration_recordIndex);
						layer.alert(opration_recordText+" 不能为空，请选择！");
						return;
					}
				}
			}
			var remark = $(window.frames["myiframe"].document).find("textarea[name='remark']").val();
			//检查口腔清洁情况
			var checkCavityCleanSitu = $(window.frames["myiframe"].document).find("select[name='checkCavityCleanSitu']").val();
			if($(window.frames["myiframe"].document).find("select[name='checkCavityCleanSitu']").length>0){
				if(!checkCavityCleanSitu || checkCavityCleanSitu=="-请选择-") {
					var checkCavityCleanSituTextnode = $(window.frames["myiframe"].document).find("select[name='checkCavityCleanSitu']").parent().text();//找到其标题
					var checkCavityCleanSituIndexstart=checkCavityCleanSituTextnode.indexOf("*")+1;//截取标题序号
					if(checkCavityCleanSituTextnode.indexOf("*")>=0){
						var checkCavityCleanSituIndexover=checkCavityCleanSituTextnode.indexOf("：");//截取冒号
						var checkCavityCleanSituText = checkCavityCleanSituTextnode.substring(checkCavityCleanSituIndexstart,checkCavityCleanSituIndexover);
						layer.alert(checkCavityCleanSituText+" 不能为空，请填写！");
						return;
					}
				}
			}
			//患者满意度 ①检查口腔清洁情况
			var satisfactionPatients = $(window.frames["myiframe"].document).find("select[name='satisfactionPatients']").val();
			if($(window.frames["myiframe"].document).find("select[name='satisfactionPatients']").length>0){
				if(!satisfactionPatients || satisfactionPatients=="-请选择-") {
					var satisfactionPatientsTextnode = $(window.frames["myiframe"].document).find("select[name='satisfactionPatients']").parent().text();//找到其标题
					var satisfactionPatientsIndexstart=satisfactionPatientsTextnode.indexOf("*")+1;//截取标题序号
					if(satisfactionPatientsTextnode.indexOf("*")>=0){
						var satisfactionPatientsIndexover=satisfactionPatientsTextnode.indexOf("：");//截取冒号
						var satisfactionPatientsText = satisfactionPatientsTextnode.substring(satisfactionPatientsIndexstart,satisfactionPatientsIndexover);
						layer.alert(satisfactionPatientsText+" 不能为空，请填写！");
						return;
					}
				}
			}
			//检查种植区愈合情况 
			var checkPlantSituation = $(window.frames["myiframe"].document).find("select[name='checkPlantSituation']").val();
			if($(window.frames["myiframe"].document).find("select[name='checkPlantSituation']").length>0){
				if(!checkPlantSituation || checkPlantSituation=="-请选择-") {
					var checkPlantSituationTextnode = $(window.frames["myiframe"].document).find("select[name='checkPlantSituation']").parent().text();//找到其标题
					var checkPlantSituationIndexstart=checkPlantSituationTextnode.indexOf("*")+1;//截取标题序号
					if(checkPlantSituationTextnode.indexOf("*")>=0){
						var checkPlantSituationIndexover=checkPlantSituationTextnode.indexOf("：");//截取冒号
						if(checkPlantSituationIndexover==-1){
							var checkPlantSituationText = checkPlantSituationTextnode.substring(checkPlantSituationIndexstart);
						}else{
							var checkPlantSituationText = checkPlantSituationTextnode.substring(checkPlantSituationIndexstart,checkPlantSituationIndexover);
						}
						layer.alert(checkPlantSituationText+" 不能为空，请填写！");
						return;
					}
				}
			}
			//术后取模
			var postoperativeModulus = $(window.frames["myiframe"].document).find("input[name='postoperativeModulus']:checked").val();
			//拆线
			var is_stitches = $(window.frames["myiframe"].document).find("input[name='is_stitches']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='is_stitches']").length>0){
				if(!is_stitches) {
					var is_stitchesIndex=$(window.frames["myiframe"].document).find("input[name='is_stitches']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='is_stitches']").parent().text().indexOf("*")>=0){
						var is_stitchesText = $(window.frames["myiframe"].document).find("input[name='is_stitches']").parent().text().substring(is_stitchesIndex);
						layer.alert(is_stitchesText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//完成病历记录
			var complete_case_record = $(window.frames["myiframe"].document).find("input[name='complete_case_record']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='complete_case_record']").length>0){
				if(!complete_case_record) {
					var complete_case_recordIndex=$(window.frames["myiframe"].document).find("input[name='complete_case_record']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='complete_case_record']").parent().text().indexOf("*")>=0){
						var complete_case_recordText = $(window.frames["myiframe"].document).find("input[name='complete_case_record']").parent().text().substring(complete_case_recordIndex);
						layer.alert(complete_case_recordText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//拍全景片 ①取模
			var panoramic_view_piece = $(window.frames["myiframe"].document).find("input[name='panoramic_view_piece']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='panoramic_view_piece']").length>0){
				if(!panoramic_view_piece) {
					var panoramic_view_pieceIndex=$(window.frames["myiframe"].document).find("input[name='panoramic_view_piece']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='panoramic_view_piece']").parent().text().indexOf("*")>=0){
						var panoramic_view_pieceText = $(window.frames["myiframe"].document).find("input[name='panoramic_view_piece']").parent().text().substring(panoramic_view_pieceIndex);
						layer.alert(panoramic_view_pieceText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//确定咬合关系、咬合记录
			var confirmOcclusalRelationship = $(window.frames["myiframe"].document).find("input[name='confirmOcclusalRelationship']:checked").val();
			//制作过渡义齿 ①螺丝孔材料是否脱落
			var makeTransitionDenture = $(window.frames["myiframe"].document).find("input[name='makeTransitionDenture']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='makeTransitionDenture']").length>0){
				if(!makeTransitionDenture) {
					var makeTransitionDentureTag = $(window.frames["myiframe"].document).find("input[name='makeTransitionDenture']");
					var makeTransitionDentureTextBefore=makeTransitionDentureTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					var makeTransitionDentureIndexstart=makeTransitionDentureTextBefore.indexOf("*")+1;//截取标题序号
					if(makeTransitionDentureTextBefore.indexOf("*")>=0){
						var makeTransitionDentureIndexover=makeTransitionDentureTextBefore.indexOf("：");//截取冒号
						var makeTransitionDentureText = makeTransitionDentureTextBefore.substring(makeTransitionDentureIndexstart,makeTransitionDentureIndexover);
						layer.alert(makeTransitionDentureText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//试戴过渡义齿
			var tryInTransitionDenture = $(window.frames["myiframe"].document).find("input[name='tryInTransitionDenture']:checked").val();
			//取初印模做个性化托盘
			var modulusToMakePersonalizedTrays = $(window.frames["myiframe"].document).find("input[name='modulusToMakePersonalizedTrays']:checked").val();
			//颌位记录
			var jawrelationRecord = $(window.frames["myiframe"].document).find("input[name='jawrelationRecord']:checked").val();
			//试基台、内冠
			var tryBaseStationInnerCrown = $(window.frames["myiframe"].document).find("input[name='tryBaseStationInnerCrown']:checked").val();
			//二次取模
			var secondGetModulus = $(window.frames["myiframe"].document).find("input[name='secondGetModulus']:checked").val();
			//戴牙
			var wearTooth = $(window.frames["myiframe"].document).find("input[name='wearTooth']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='wearTooth']").length>0){
				if(!wearTooth) {
					var wearToothBeforeText=$(window.frames["myiframe"].document).find("input[name='wearTooth']").parent().text();
					//console.log(wearToothBeforeText+"----------患者舒适度");
					var wearToothIndex=$(window.frames["myiframe"].document).find("input[name='wearTooth']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='wearTooth']").parent().text().indexOf("*")>=0){
						var wearToothText = $(window.frames["myiframe"].document).find("input[name='wearTooth']").parent().text().substring(wearToothIndex);
						layer.alert(wearToothText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//拍照
			var isphotograph = $(window.frames["myiframe"].document).find("input[name='isphotograph']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='isphotograph']").length>0){
				if(!isphotograph) {
					var isphotographIndex=$(window.frames["myiframe"].document).find("input[name='isphotograph']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='isphotograph']").parent().text().indexOf("*")>=0){
						var isphotographText = $(window.frames["myiframe"].document).find("input[name='isphotograph']").parent().text().substring(isphotographIndex);
						layer.alert(isphotographText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//再次试戴
			var toTryAgain = $(window.frames["myiframe"].document).find("input[name='toTryAgain']:checked").val();
			//预约下次来院时间
			var next_hospital_time = $(window.frames["myiframe"].document).find("input[name='next_hospital_time']").val();
			if($(window.frames["myiframe"].document).find("input[name='next_hospital_time']").length>0){
				if(!next_hospital_time) {
					var next_hospital_timeTextnode = $(window.frames["myiframe"].document).find("input[name='next_hospital_time']").parent().text();//找到其标题
					var next_hospital_timeIndexstart=next_hospital_timeTextnode.indexOf("*")+1;//截取标题序号
					if(next_hospital_timeTextnode.indexOf("*")>=0){
						var next_hospital_timeIndexover=next_hospital_timeTextnode.indexOf("：");//截取冒号
						var next_hospital_timeText = next_hospital_timeTextnode.substring(next_hospital_timeIndexstart,next_hospital_timeIndexover);
						layer.alert(next_hospital_timeText+" 不能为空，请填写！");
						return;
					}
				}
			}
			//二期拆线时间
			var twoDateStitchesTime = $(window.frames["myiframe"].document).find("input[name='twoDateStitchesTime']").val();
			if($(window.frames["myiframe"].document).find("input[name='twoDateStitchesTime']").length>0){
				if(!twoDateStitchesTime) {
					var twoDateStitchesTimeTextnode = $(window.frames["myiframe"].document).find("input[name='twoDateStitchesTime']").parent().text();//找到其标题
					var twoDateStitchesTimeIndexstart=twoDateStitchesTimeTextnode.indexOf("*")+1;//截取标题序号
					if(twoDateStitchesTimeTextnode.indexOf("*")>=0){
						var twoDateStitchesTimeIndexover=twoDateStitchesTimeTextnode.indexOf("：");//截取冒号
						var twoDateStitchesTimeText = twoDateStitchesTimeTextnode.substring(twoDateStitchesTimeIndexstart,twoDateStitchesTimeIndexover);
						layer.alert(twoDateStitchesTimeText+" 不能为空，请填写！");
						return;
					}
				}
			}
			//二期手术并交代术后注意事项
			var twoOperationAttention = $(window.frames["myiframe"].document).find("input[name='twoOperationAttention']:checked").val();
			//完成二期手术
			var completeTwoOperation = $(window.frames["myiframe"].document).find("input[name='completeTwoOperation']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='completeTwoOperation']").length>0){
				if(!completeTwoOperation) {
					var completeTwoOperationTextnode = $(window.frames["myiframe"].document).find("input[name='completeTwoOperation']").parent().text();//找到其标题
					var completeTwoOperationIndexstart=completeTwoOperationTextnode.indexOf("*")+1;//截取标题序号
					if(completeTwoOperationTextnode.indexOf("*")>=0){
						var completeTwoOperationText = completeTwoOperationTextnode.substring(completeTwoOperationIndexstart);
						layer.alert(completeTwoOperationText+" 不能为空，请填写！");
						return;
					}
				}
			}
			//检查戴牙情况
			var checkWearToothSitu = $(window.frames["myiframe"].document).find("input[name='checkWearToothSitu']:checked").val();
			//抗菌药物使用3-5天
			var antimicrobial_use = $(window.frames["myiframe"].document).find("input[name='antimicrobial_use']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='antimicrobial_use']").length>0){
				if(!antimicrobial_use) {
					var antimicrobial_useIndex=$(window.frames["myiframe"].document).find("input[name='antimicrobial_use']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='antimicrobial_use']").parent().text().indexOf("*")>=0){
						var antimicrobial_useText = $(window.frames["myiframe"].document).find("input[name='antimicrobial_use']").parent().text().substring(antimicrobial_useIndex);
						layer.alert(antimicrobial_useText+" 不能为空，请选择！");
						return;
					}
				}
			}
			
			
			
			var wearteeth = $(window.frames["myiframe"].document).find("input[name='wearteeth']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='wearteeth']").length>0){
				if(!wearteeth) {
					var wearteethIndex=$(window.frames["myiframe"].document).find("input[name='wearteeth']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='wearteeth']").parent().text().indexOf("*")>=0){
						var wearteethText = $(window.frames["myiframe"].document).find("input[name='wearteeth']").parent().text().substring(wearteeth);
						layer.alert(wearteethText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//调和
			var accord = $(window.frames["myiframe"].document).find("input[name='accord']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='accord']").length>0){
				if(!accord) {
					var accordIndex=$(window.frames["myiframe"].document).find("input[name='accord']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='accord']").parent().text().indexOf("*")>=0){
						var accordText = $(window.frames["myiframe"].document).find("input[name='accord']").parent().text().substring(accordIndex);
						layer.alert(accordText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//取模
			var ismodule = $(window.frames["myiframe"].document).find("input[name='ismodule']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='ismodule']").length>0){
				if(!ismodule) {
					var ismoduleIndex=$(window.frames["myiframe"].document).find("input[name='ismodule']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='ismodule']").parent().text().indexOf("*")>=0){
						var ismoduleText = $(window.frames["myiframe"].document).find("input[name='ismodule']").parent().text().substring(ismoduleIndex);
						layer.alert(ismoduleText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//面弓转移，上架
			var transfer = $(window.frames["myiframe"].document).find("input[name='transfer']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='transfer']").length>0){
				if(!transfer) {
					var transferIndex=$(window.frames["myiframe"].document).find("input[name='transfer']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='transfer']").parent().text().indexOf("*")>=0){
						var transferText = $(window.frames["myiframe"].document).find("input[name='transfer']").parent().text().substring(transferIndex);
						layer.alert(transferText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//检查种植体骨结合情况
			var checkPlantBoneCombine = $(window.frames["myiframe"].document).find("select[name='checkPlantBoneCombine']").val();
			if($(window.frames["myiframe"].document).find("select[name='checkPlantBoneCombine']").length>0){
				if(!checkPlantBoneCombine || checkPlantBoneCombine=="-请选择-") {
					var checkPlantBoneCombineTextnode = $(window.frames["myiframe"].document).find("select[name='checkPlantBoneCombine']").parent().text();//找到其标题
					var checkPlantBoneCombineIndexstart=checkPlantBoneCombineTextnode.indexOf("*")+1;//截取标题序号
					if(checkPlantBoneCombineTextnode.indexOf("*")>=0){
						var checkPlantBoneCombineIndexover=checkPlantBoneCombineTextnode.indexOf("：");//截取冒号
						var checkPlantBoneCombineText = checkPlantBoneCombineTextnode.substring(checkPlantBoneCombineIndexstart,checkPlantBoneCombineIndexover);
						layer.alert(checkPlantBoneCombineText+" 不能为空，请填写！");
						return;
					}
				}
			}
			//试基底冠
			var basalCrowns = $(window.frames["myiframe"].document).find("input[name='basalCrowns']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='basalCrowns']").length>0){
				if(!basalCrowns) {
					var basalCrownsIndex=$(window.frames["myiframe"].document).find("input[name='basalCrowns']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='basalCrowns']").parent().text().indexOf("*")>=0){
						var basalCrownsText = $(window.frames["myiframe"].document).find("input[name='basalCrowns']").parent().text().substring(basalCrownsIndex);
						layer.alert(basalCrownsText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//外冠于口内粘接
			var ectolophUse = $(window.frames["myiframe"].document).find("input[name='ectolophUse']:checked").val();
			//试支架或基底冠
			var tryHolderOrBasalCrowns = $(window.frames["myiframe"].document). find("input[name='tryHolderOrBasalCrowns']:checked").val();
			//树脂封口
			var resinSealing = $(window.frames["myiframe"].document).find("input[name='resinSealing']:checked").val();
			//询问使用情况 
			var askUserSituation = $(window.frames["myiframe"].document).find("input[name='askUserSituation']:checked").val();
			//取模医生书写病程记录
			var doctorWrittenRecords = $(window.frames["myiframe"].document).find("input[name='doctorWrittenRecords']:checked").val();
			//检查咬合情况
			var checkOcclusion = $(window.frames["myiframe"].document).find("input[name='checkOcclusion']:checked").val();
			var checkOcclusionTag= $(window.frames["myiframe"].document).find("input[name='checkOcclusion']");
			if($(window.frames["myiframe"].document).find("input[name='checkOcclusion']").length>0){
				if(!checkOcclusion) {
					var checkOcclusionTextBefore=checkOcclusionTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					if(!checkOcclusionTextBefore){
						checkOcclusionTextBefore=$(window.frames["myiframe"].document).find("input[name='checkOcclusion']").parent().text();//找到其标题
					}
					var checkOcclusionIndexstart=checkOcclusionTextBefore.indexOf("*")+1;//截取标题序号
					if(checkOcclusionTextBefore.indexOf("*")>=0){
						var checkOcclusionIndexover=checkOcclusionTextBefore.indexOf("：");//截取冒号
						if(checkOcclusionTextBefore.indexOf("：")==-1){
							var checkOcclusionText = checkOcclusionTextBefore.substring(checkOcclusionIndexstart);
						}else{
							var checkOcclusionText = checkOcclusionTextBefore.substring(checkOcclusionIndexstart,checkOcclusionIndexover);
						}
					}
					layer.alert(checkOcclusionText+" 不能为空，请选择！");
					return;
				}
			}
			//比色
			var colorimetric = $(window.frames["myiframe"].document).find("input[name='colorimetric']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='colorimetric']").length>0){
				if(!colorimetric) {
					var colorimetricIndex=$(window.frames["myiframe"].document).find("input[name='colorimetric']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='colorimetric']").parent().text().indexOf("*")>=0){
						var colorimetricText = $(window.frames["myiframe"].document).find("input[name='colorimetric']").parent().text().substring(colorimetricIndex);
						layer.alert(colorimetricText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//局部连桥 需要填写 此节点
			var localBridge = $(window.frames["myiframe"].document).find("input[name='localBridge']").val();
			//试戴 ①调合
			var try_in = $(window.frames["myiframe"].document).find("input[name='try_in']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='try_in']").length>0){
				if(!try_in) {
					var try_inIndex=$(window.frames["myiframe"].document).find("input[name='try_in']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='try_in']").parent().text().indexOf("*")>=0){
						var try_inText = $(window.frames["myiframe"].document).find("input[name='try_in']").parent().text().substring(try_inIndex);
						layer.alert(try_inText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//询问使用情况
			var askAboutUsage = $(window.frames["myiframe"].document).find("select[name='askAboutUsage']").val();
			if($(window.frames["myiframe"].document).find("select[name='askAboutUsage']").length>0){
				if(!askAboutUsage || askAboutUsage=="-请选择-") {
					var askAboutUsageTextnode = $(window.frames["myiframe"].document).find("select[name='askAboutUsage']").parent().text();//找到其标题
					var askAboutUsageIndexstart=askAboutUsageTextnode.indexOf("*")+1;//截取标题序号
					if(askAboutUsageTextnode.indexOf("*")>=0){
						var askAboutUsageIndexover=askAboutUsageTextnode.indexOf("：");//截取冒号
						var askAboutUsageText = askAboutUsageTextnode.substring(askAboutUsageIndexstart,askAboutUsageIndexover);
						layer.alert(askAboutUsageText+" 不能为空，请填写！");
						return;
					}
				}
			}
			//检查术区愈合及种植体骨结合情况
			var check_surgical_healing = $(window.frames["myiframe"].document).find("input[name='check_surgical_healing']").val();
			//非微创拆线
			var nostitches = $(window.frames["myiframe"].document).find("input[name='nostitches']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='nostitches']").length>0){
				if(!nostitches) {
					var nostitchesIndex=$(window.frames["myiframe"].document).find("input[name='nostitches']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='nostitches']").parent().text().indexOf("*")>=0){
						var nostitchesText = $(window.frames["myiframe"].document).find("input[name='nostitches']").parent().text().substring(nostitchesIndex);
						layer.alert(nostitchesText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//完成临床路径录入
			var enterLclj = $(window.frames["myiframe"].document).find("input[name='enterLclj']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='enterLclj']").length>0){
				if(!enterLclj) {
					var enterLcljIndex=$(window.frames["myiframe"].document).find("input[name='enterLclj']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='enterLclj']").parent().text().indexOf("*")>=0){
						var enterLcljText = $(window.frames["myiframe"].document).find("input[name='enterLclj']").parent().text().substring(enterLcljIndex);
						layer.alert(enterLcljText+" 不能为空，请选择！");
						return;
					}
				}
			}
//			新加参数
			var operator = $(window.frames["myiframe"].document).find("input[name='operator']:checked").val();//手术
			if($(window.frames["myiframe"].document).find("input[name='operator']").length>0){
				if(!operator) {
					var operatorIndex=$(window.frames["myiframe"].document).find("input[name='operator']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='operator']").parent().text().indexOf("*")>=0){
						var operatorText = $(window.frames["myiframe"].document).find("input[name='operator']").parent().text().substring(operatorIndex);
						layer.alert(operatorText+"不能为空，请选择！");
						return;
					}
				}
			}
			var finish_norm_first = $(window.frames["myiframe"].document).find("input[name='finish_norm_first']:checked").val();//种植修复治愈标准一
			var finish_norm_second = $(window.frames["myiframe"].document).find("input[name='finish_norm_second']:checked").val();//种植修复治愈标准二
			var finish_norm_third = $(window.frames["myiframe"].document).find("input[name='finish_norm_third']:checked").val();//种植修复治愈标准三
			var finish_norm_fourth = $(window.frames["myiframe"].document).find("input[name='finish_norm_fourth']:checked").val();//种植修复治愈标准四
			var finish_norm_fifth = $(window.frames["myiframe"].document).find("input[name='finish_norm_fifth']:checked").val();//种植修复治愈标准五
			var before_Modulo_bite = $(window.frames["myiframe"].document).find("input[name='before_Modulo_bite']:checked").val();//术前取模、定咬合关系
			//术后当天取模、定咬合关系
			var after_Modulo_bite = $(window.frames["myiframe"].document).find("input[name='after_Modulo_bite']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='after_Modulo_bite']").length>0){
				if(!after_Modulo_bite) {
					var after_Modulo_biteIndex=$(window.frames["myiframe"].document).find("input[name='after_Modulo_bite']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='after_Modulo_bite']").parent().text().indexOf("*")>=0){
						var after_Modulo_biteText = $(window.frames["myiframe"].document).find("input[name='after_Modulo_bite']").parent().text().substring(after_Modulo_biteIndex);
						layer.alert(after_Modulo_biteText+"不能为空，请选择！");
						return;
					}
				}
			}
			//固定牙桥 ①邻接是否塞牙
			var fixation_tooth_bridge = $(window.frames["myiframe"].document).find("input[name='fixation_tooth_bridge']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='fixation_tooth_bridge']").length>0){
				if(!fixation_tooth_bridge) {
					var fixation_tooth_bridgeTag = $(window.frames["myiframe"].document).find("input[name='fixation_tooth_bridge']");
					var fixation_tooth_bridgeTextBefore=fixation_tooth_bridgeTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					if(!fixation_tooth_bridgeTextBefore){
						fixation_tooth_bridgeTextBefore=fixation_tooth_bridgeTag.parent().text();//找到其标题
					}
					var fixation_tooth_bridgeIndexstart=fixation_tooth_bridgeTextBefore.indexOf("*")+1;//截取标题序号
					if(fixation_tooth_bridgeTextBefore.indexOf("*")>=0){
						var fixation_tooth_bridgeIndexover=fixation_tooth_bridgeTextBefore.indexOf("：");//截取冒号
						if(fixation_tooth_bridgeIndexover==-1){
							var fixation_tooth_bridgeText = fixation_tooth_bridgeTextBefore.substring(fixation_tooth_bridgeIndexstart);
						}else{
							var fixation_tooth_bridgeText = fixation_tooth_bridgeTextBefore.substring(fixation_tooth_bridgeIndexstart,fixation_tooth_bridgeIndexover);
						}
						layer.alert(fixation_tooth_bridgeText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//定咬合 ①是否绷瓷
			var decision_bite = $(window.frames["myiframe"].document).find("input[name='decision_bite']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='decision_bite']").length>0){
				if(!decision_bite) {
					var decision_biteTag = $(window.frames["myiframe"].document).find("input[name='decision_bite']");
					var decision_biteTextBefore=decision_biteTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					if(!decision_biteTextBefore){
						decision_biteTextBefore=decision_biteTag.parent().text();//找到其标题
					}
					var decision_biteIndexstart=decision_biteTextBefore.indexOf("*")+1;//截取标题序号
					if(decision_biteTextBefore.indexOf("*")>=0){
						var decision_biteIndexover=decision_biteTextBefore.indexOf("：");//截取冒号
						if(decision_biteIndexover==-1){
							var decision_biteText = decision_biteTextBefore.substring(decision_biteIndexstart);
						}else{
							var decision_biteText = decision_biteTextBefore.substring(decision_biteIndexstart,decision_biteIndexover);
						}
						layer.alert(decision_biteText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//上颌架  ①植体是否吸收 ②有无变异
			var upper_frame = $(window.frames["myiframe"].document).find("input[name='upper_frame']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='upper_frame']").length>0){
				if(!upper_frame) {
					var upper_frameTag = $(window.frames["myiframe"].document).find("input[name='upper_frame']");
					var upper_frameTextBefore=upper_frameTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					if(!upper_frameTextBefore){
						upper_frameTextBefore=upper_frameTag.parent().text();//找到其标题
					}
					var upper_frameIndexstart=upper_frameTextBefore.indexOf("*")+1;//截取标题序号
					if(upper_frameTextBefore.indexOf("*")>=0){
						var upper_frameIndexover=upper_frameTextBefore.indexOf("：");//截取冒号
						if(upper_frameIndexover==-1){
							var upper_frameText = upper_frameTextBefore.substring(upper_frameIndexstart);
						}else{
							var upper_frameText = upper_frameTextBefore.substring(upper_frameIndexstart,upper_frameIndexover);
						}
						layer.alert(upper_frameText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//①有无变异
			var is_loose = $(window.frames["myiframe"].document).find("input[name='is_loose']:checked").val();//检查螺丝是否松动 
			if($(window.frames["myiframe"].document).find("input[name='is_loose']").length>0){
				if(!is_loose) {
					var is_looseTag = $(window.frames["myiframe"].document).find("input[name='is_loose']");
					var is_looseTextBefore=is_looseTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					var is_looseIndexstart=is_looseTextBefore.indexOf("*")+1;//截取标题序号
					if(is_looseTextBefore.indexOf("*")>=0){
						var is_looseIndexover=is_looseTextBefore.indexOf("：");//截取冒号
						var is_looseText = is_looseTextBefore.substring(is_looseIndexstart,is_looseIndexover);
						layer.alert(is_looseText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//卫生宣教 ①试戴是否正常
			var health_education = $(window.frames["myiframe"].document).find("input[name='health_education']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='health_education']").length>0){
				if(!health_education) {
					var health_educationTag = $(window.frames["myiframe"].document).find("input[name='health_education']");
					var health_educationTextBefore=health_educationTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					if(!health_educationTextBefore){
						health_educationTextBefore=health_educationTag.parent().text();//找到其标题
					}
					var health_educationIndexstart=health_educationTextBefore.indexOf("*")+1;//截取标题序号
					if(health_educationTextBefore.indexOf("*")>=0){
						var health_educationIndexover=health_educationTextBefore.indexOf("：");//截取冒号
						if(health_educationIndexover==-1){
							var health_educationText = health_educationTextBefore.substring(health_educationIndexstart);
						}else{
							var health_educationText = health_educationTextBefore.substring(health_educationIndexstart,health_educationIndexover);
						}
						layer.alert(health_educationText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//戴保护垫
			var neonychium = $(window.frames["myiframe"].document).find("input[name='neonychium']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='neonychium']").length>0){
				if(!neonychium) {
					var neonychiumIndex=$(window.frames["myiframe"].document).find("input[name='neonychium']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='neonychium']").parent().text().indexOf("*")>=0){
						var neonychiumText = $(window.frames["myiframe"].document).find("input[name='neonychium']").parent().text().substring(neonychiumIndex);
						layer.alert(neonychiumText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//清洁牙桥
			var clean_toothbridge = $(window.frames["myiframe"].document).find("input[name='clean_toothbridge']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='clean_toothbridge']").length>0){
				if(!clean_toothbridge) {
					var clean_toothbridgeIndex=$(window.frames["myiframe"].document).find("input[name='clean_toothbridge']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='clean_toothbridge']").parent().text().indexOf("*")>=0){
						var clean_toothbridgeText = $(window.frames["myiframe"].document).find("input[name='clean_toothbridge']").parent().text().substring(clean_toothbridgeIndex);
						layer.alert(clean_toothbridgeText+" 不能为空，请选择！");
						return;
					}
				}
			}
			//外提升植骨
			var outside_bone_grafting = $(window.frames["myiframe"].document).find("input[name='outside_bone_grafting']:checked").val();
			if($(window.frames["myiframe"].document).find("input[name='outside_bone_grafting']").length>0){
				if(!outside_bone_grafting) {
					var outside_bone_graftingIndex=$(window.frames["myiframe"].document).find("input[name='outside_bone_grafting']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='outside_bone_grafting']").parent().text().indexOf("*")>=0){
						var outside_bone_graftingText = $(window.frames["myiframe"].document).find("input[name='outside_bone_grafting']").parent().text().substring(outside_bone_graftingIndex);
						layer.alert(outside_bone_graftingText+" 不能为空，请选择！");
						return;
					}
				}
			}
            //种植牙手术记录
            if(patientObj.nodename=="手术治疗" || patientObj.nodename=="手术治疗取模"){

                var opration_record_new = $(window.frames["myiframe"].document).find("input[name='opration_record_new']:checked").val();
                if(!opration_record_new){
                    layer.alert("请填写种植牙手术记录！");
                    return;
                }
			}

			//植体是否松动左上
			var extractionleftup_l = $(window.frames["myiframe"].document).find("input[name='extractionleftup_l']").val();
			var extractionleftup_r = $(window.frames["myiframe"].document).find("input[name='extractionleftup_r']").val();
			var extractionleftdown_l = $(window.frames["myiframe"].document).find("input[name='extractionleftdown_l']").val();
			var extractionleftdown_r = $(window.frames["myiframe"].document).find("input[name='extractionleftdown_r']").val();
			var extractionleftup1_l = $(window.frames["myiframe"].document).find("input[name='extractionleftup1_l']").val();
			var extractionleftup1_r = $(window.frames["myiframe"].document).find("input[name='extractionleftup1_r']").val();
			var extractionleftdown1_l = $(window.frames["myiframe"].document).find("input[name='extractionleftdown1_l']").val();
			var extractionleftdown1_r = $(window.frames["myiframe"].document).find("input[name='extractionrightdown1_r']").val();

			var flag = true;
			layer.alert('确认当前节点操作已全部完成', {
				  closeBtn: 0    // 是否显示关闭按钮
				  ,anim: 1 //动画类型
				  ,btn: ['确认','关闭'] //按钮
				  ,yes:function(){
					  $.ajax({
							url: apppath + "/HUDH_FlowAct/gotoNextNode.act",
							type:"POST",
							dataType:"json",
							data : {
//								节点名称
								"nodeName":nodeName,
								"nodeLimit":nodeLimit,
//								最后一次挂号信息
								"lastRegistrationCreatetime":lastRegistrationCreatetime,
								"lastRegistrationRegway":lastRegistrationRegsort,
								"lastRegistrationRecesort":lastRegistrationRecesort,
								"lastRegistrationDoctor":lastRegistrationDoctor,
								"personId":personId,
								"lcljId":lclj_Id,
								//新增参数
								"extractionleftup_l":extractionleftup_l,
								"extractionleftup_r":extractionleftup_r,
								"extractionleftdown_l":extractionleftdown_l,
								"extractionleftdown_r":extractionleftdown_r,
								"extractionleftup1_l":extractionleftup1_l,
								"extractionleftup1_r":extractionleftup1_r,
								"extractionleftdown1_l":extractionleftdown1_l,
								"extractionleftdown1_r":extractionleftdown1_r,
								"operator":operator,
								"finish_norm_first":finish_norm_first,
								"finish_norm_second":finish_norm_second,
								"finish_norm_third":finish_norm_third,
								"finish_norm_fourth":finish_norm_fourth,
								"finish_norm_fifth":finish_norm_fifth,
								"before_Modulo_bite":before_Modulo_bite,
								"after_Modulo_bite":after_Modulo_bite,
								"fixation_tooth_bridge":fixation_tooth_bridge,
								"decision_bite":decision_bite,
								"upper_frame":upper_frame,
								"is_loose":is_loose,
								"health_education":health_education,
								"neonychium":neonychium,
								"clean_toothbridge":clean_toothbridge,
								"outside_bone_grafting":outside_bone_grafting,
								
								"orderNumber": orderNumber,
								"isShouShu":isShouShu,
								"toothTotal":tooth_total,
								"repairToothTotal":zz_tooth_total,
								"leftUp":left_up,
								"leftDown":left_down,
								"rightUp":right_up,
								"rightDown":right_down,
								"repairLeftUp":repair_left_up,
								"repairLeftDown":repair_left_down,
								"repairRightUp":repair_right_up,
								"repairRightDown":repair_right_down,
								
								"operation_time" : operation_time,
								"preoperation_one_houres" : preoperation_one_houres,
								"preoperative_verification" : preoperative_verification,
								"abutment_station" : abutment_station,
								"healing_cap_station" : healing_cap_station,
								"postoperation_user_deugs" : postoperation_user_deugs,
								"collutory" : collutory,
								"small_teeth" : small_teeth,
								"announcements" : announcements,
								"opration_record" : opration_record,
								"remark" : remark,
								"assist_operation" : assist_operation,
								"review_time" : review_time,
								"observe_wound" : observe_wound,
								"check_wound" : check_wound,
								"is_stitches" : is_stitches,
								"complete_case_record" : complete_case_record,
								"panoramic_view_piece" : panoramic_view_piece,
								"next_hospital_time" : next_hospital_time,
								"twoDateStitchesTime" : twoDateStitchesTime,
								"twoOperationAttention" : twoOperationAttention,
								"ismodule" : ismodule,
								"transfer" : transfer,
								"basalCrowns" : basalCrowns,
								"colorimetric" : colorimetric,
								"localBridge" : localBridge,
								"try_in" : try_in,
								"askAboutUsage" : askAboutUsage,
								"check_surgical_healing" : check_surgical_healing,
								"completeTwoOperation" : completeTwoOperation,
								"connectingBridge" : connectingBridge,
								"confirmOcclusalRelationship" : confirmOcclusalRelationship,
								"makeTransitionDenture" : makeTransitionDenture,
								"tryInTransitionDenture" : tryInTransitionDenture,
								"modulusToMakePersonalizedTrays" : modulusToMakePersonalizedTrays,
								"jawrelationRecord" : jawrelationRecord,
								"tryBaseStationInnerCrown" : tryBaseStationInnerCrown,
								"secondGetModulus" : secondGetModulus,
								"toTryAgain" : toTryAgain,
								"checkPlantBoneCombine" : checkPlantBoneCombine,
								"wearTooth" : wearTooth,
								"isphotograph" : isphotograph,
								"checkCavityCleanSitu" : checkCavityCleanSitu,
								"checkWearToothSitu" : checkWearToothSitu,
								"satisfactionPatients" : satisfactionPatients,
								"antimicrobial_use" : antimicrobial_use,
								"visit_time" : visit_time,
								"tryHolderOrBasalCrowns" : tryHolderOrBasalCrowns,
								"accord" : accord,
								"askUserSituation" : askUserSituation,
								"resinSealing" : resinSealing,
								"checkOcclusion" : checkOcclusion,
								"postoperativeModulus" : postoperativeModulus,
								"checkPlantSituation" : checkPlantSituation,
								"ectolophUse" : ectolophUse,
								"doctorWrittenRecords" : doctorWrittenRecords,
								"anesthesiaMethod" : anesthesiaMethod,
								"nostitches" : nostitches,
								"intraoperativeMedication" : intraoperativeMedication,
								"enterLclj" : enterLclj,
								"nodeName" : nodeName,
								"wearTeeth" : wearteeth
							},
							success:function(result){
								/*layer.alert('提交成功', function(index) {
									layer.close(index);
									location.reload();
								})*/
								if (result.retState == "0") {
									/*layer.alert('提交成功', function(index) {
										layer.close(index);
										location.reload();
									})*/
									
									layer.open({
										title:"提示",
										content:"提交成功",
										closeBtn:0,
										end:function(index){
											layer.close(index);
											location.reload();
										}
									});
						        } else {
							        layer.alert('提交失败：' + result.retMsrg, {//后台抛出的异常信息在前台展示
							            end: function() {
							            	layer.close(index);
							            	location.reload();
							            }
							        });
						        }
							}
					  });
				  }
				  ,btn2:function(){}
			})
		},
		reject : function(thi){
			if(nodeId == "End") {
				layer.alert("本次临床路径已结束");return;
			}
			layer.alert('确认退回上一步', {
				  closeBtn: 1    // 是否显示关闭按钮
				  ,anim: 1 //动画类型
				  ,btn: ['确认','关闭'] //按钮
				  ,yes:function(){
					  $.ajax({
							url: apppath + "/HUDH_FlowAct/reject.act?orderNumber=" + orderNumber,
							type:"POST",
							dataType:"json",
							success:function(result){
								/*layer.alert('退回成功', function(index) {
									layer.close(index);
									location.reload();
								})*/
								layer.open({
									title:"提示",
									content:"退回成功",
									closeBtn:0,
									end:function(index){
										layer.close(index);
										registrationStatus=0;
										location.reload();
									}
								});
							}
						});
				  }
				  ,btn2:function(){}
			})
		},
		variationSave : function(thi){
//			节点名称
			var nodeName;
			var nodeLimit;
			for(var i=0;i<flowlist.length;i++){
				if(flowlist[i].stus==1){
					nodeName=flowlist[i].nodeName;
					nodeLimit=flowlist[i].nodeLimit;
//					console.log(flowlist[i].nodeName+"-----------"+flowlist[i].nodeLimit);										
				}
			}
			var variation_node_id;
			var liList=$("#flow_path").find("li");
			for(var i=0;i<liList.length;i++){
				var nodestatus=liList.eq(i).find("span").attr("nodestatus");
				if(nodestatus==1){
					variation_node_id=liList.eq(i).find("span").attr("id");
				}
			};
			//复查时间
			var operation_time = $(window.frames["myiframe"].document).find("input[name='operation_time']").val();
			var operation_timeVal;
			if($(window.frames["myiframe"].document).find("input[name='operation_time']").length>0){
				if(operation_time) {
					var operation_timeTextnode = $(window.frames["myiframe"].document).find("input[name='operation_time']").parent().text();//找到其标题
					var operation_timeIndexstart=operation_timeTextnode.indexOf("*")+1;//截取标题序号
					if(operation_timeTextnode.indexOf("*")>=0){
						var operation_timeIndexover=operation_timeTextnode.indexOf("：");//截取冒号
						var operation_timeText = operation_timeTextnode.substring(operation_timeIndexstart,operation_timeIndexover);
						operation_timeVal=operation_timeText+":"+operation_time;
					}else{
						operation_timeVal=operation_timeTextnode;
					}
				}
			}
			
			var review_time = $(window.frames["myiframe"].document).find("input[name='review_time']").val();
			var review_timeVal;
			if($(window.frames["myiframe"].document).find("input[name='review_time']").length>0){
				if(review_time) {
					var review_timeTextnode = $(window.frames["myiframe"].document).find("input[name='review_time']").parent().text();//找到其标题
					var review_timeIndexstart=review_timeTextnode.indexOf("*")+1;//截取标题序号
					if(review_timeTextnode.indexOf("*")>=0){
						var review_timeIndexover=review_timeTextnode.indexOf("：");//截取冒号
						var review_timeText = review_timeTextnode.substring(review_timeIndexstart,review_timeIndexover);
						review_timeVal=review_timeText+":"+review_time;
					}else{
						review_timeVal=review_timeTextnode;
					}
				}
			}
			
			var visit_time = $(window.frames["myiframe"].document).find("input[name='visit_time']").val();
			var visit_timeVal;
			if($(window.frames["myiframe"].document).find("input[name='visit_time']").length>0){
				if(visit_time) {
					var visit_timeTextnode = $(window.frames["myiframe"].document).find("input[name='visit_time']").parent().text();//找到其标题
					var visit_timeIndexstart=visit_timeTextnode.indexOf("*")+1;//截取标题序号
					if(visit_timeTextnode.indexOf("*")>=0){
						var visit_timeIndexover=visit_timeTextnode.indexOf("：");//截取冒号
						var visit_timeText = visit_timeTextnode.substring(visit_timeIndexstart,visit_timeIndexover);
						visit_timeVal=visit_timeText+":"+visit_time;
					}else{
						visit_timeVal=visit_timeTextnode;
						if(!visit_timeVal){
							visit_timeVal=visit_time;
						}
					}
					
				}
			}
			
			var observe_wound = $(window.frames["myiframe"].document).find("select[name='observe_wound']").val();
			var observe_woundVal;                                                  
			if($(window.frames["myiframe"].document).find("select[name='observe_wound']").length>0){
				if(observe_wound) {
					var observe_woundTextnode = $(window.frames["myiframe"].document).find("select[name='observe_wound']").parent().text();//找到其标题
					var observe_woundIndexstart=observe_woundTextnode.indexOf("*")+1;//截取标题序号
					if(observe_woundTextnode.indexOf("*")>=0){
						var observe_woundIndexover=observe_woundTextnode.indexOf("：");//截取冒号
						if(observe_woundIndexover==-1){
							var observe_woundText = observe_woundTextnode.substring(observe_woundIndexstart);
						}else{
							var observe_woundText = observe_woundTextnode.substring(observe_woundIndexstart,observe_woundIndexover);
						}
						if(observe_wound!="-请选择-"){
							observe_woundVal=observe_woundText+":"+observe_wound;
						}
					}else{
						observe_woundVal=observe_woundTextnode;
					}
				}
			}
			
			var check_wound = $(window.frames["myiframe"].document).find("select[name='check_wound']").val();
			var check_woundVal;
			if($(window.frames["myiframe"].document).find("select[name='check_wound']").length>0){
				if(check_wound) {
					var check_woundTextnode = $(window.frames["myiframe"].document).find("select[name='check_wound']").parent().text();//找到其标题
					var check_woundIndexstart=check_woundTextnode.indexOf("*")+1;//截取标题序号
					if(check_woundTextnode.indexOf("*")>=0){
						var check_woundIndexover=check_woundTextnode.indexOf("：");//截取冒号
						var check_woundText = check_woundTextnode.substring(check_woundIndexstart,check_woundIndexover);
						if(check_wound!="-请选择-"){
							check_woundVal=check_woundText+":"+check_wound;
						}
					}else{
						check_woundVal=check_woundTextnode;
					}
				}
			}
			
			var preoperation_one_houres = $(window.frames["myiframe"].document).find("input[name='preoperation_one_houres']:checked").val();
			var preoperation_one_houresVal;
			if($(window.frames["myiframe"].document).find("input[name='preoperation_one_houres']").length>0){
				if(preoperation_one_houres) {
					var preoperation_one_houresIndex=$(window.frames["myiframe"].document).find("input[name='preoperation_one_houres']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='preoperation_one_houres']").parent().text().indexOf("*")>=0){
						var preoperation_one_houresText = $(window.frames["myiframe"].document).find("input[name='preoperation_one_houres']").parent().text().substring(preoperation_one_houresIndex);
						preoperation_one_houresVal=preoperation_one_houresText;
					}else{
						preoperation_one_houresVal=$(window.frames["myiframe"].document).find("input[name='preoperation_one_houres']").parent().text();
					}
				}
			}
			
			var preoperative_verification = $(window.frames["myiframe"].document).find("input[name='preoperative_verification']:checked").val();
			var preoperative_verificationVal;
			if($(window.frames["myiframe"].document).find("input[name='preoperative_verification']").length>0){
				if(preoperative_verification) {
					var preoperative_verificationIndex=$(window.frames["myiframe"].document).find("input[name='preoperative_verification']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='preoperative_verification']").parent().text().indexOf("*")>=0){
						var preoperative_verificationText = $(window.frames["myiframe"].document).find("input[name='preoperative_verification']").parent().text().substring(preoperative_verificationIndex);
						preoperative_verificationVal=preoperative_verificationText;
					}else{
						preoperative_verificationVal=$(window.frames["myiframe"].document).find("input[name='preoperative_verification']").parent().text();
					}
				}
			}
			
			var connectingBridge = $(window.frames["myiframe"].document).find("input[name='connectingBridge']:checked").val();
			var abutment_station = abutmentStation();
			var abutment_stationTag = $(window.frames["myiframe"].document).find("input[name='abutment_station']");
			var abutment_stationTagVal;
			if(abutment_stationTag.length>0){
				if(abutment_station) {
					var abutment_stationTextBefore=abutment_stationTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					var abutment_stationIndexstart=abutment_stationTextBefore.indexOf("*")+1;//截取标题序号
					var abutment_stationIndexover=abutment_stationTextBefore.indexOf("：");//截取冒号
					var abutment_stationText = abutment_stationTextBefore.substring(abutment_stationIndexstart,abutment_stationIndexover);
					if(abutment_stationTextBefore.indexOf("*")>=0){
						abutment_stationTagVal=abutment_stationText+":"+abutment_station;
					}else{
						abutment_stationTagVal=abutment_stationTextBefore;
						if(abutment_stationIndexover>=0){
							abutment_stationTagVal=abutment_stationText+":"+abutment_station;
						}
					}
				}
			}
			
			var assist_operation = getAssistOperation();
			var assist_operationTag = $(window.frames["myiframe"].document).find("input[name='assist_operation']");
			var assist_operationTagVal;
			if(assist_operationTag.length>0){
				if(assist_operation) {
					var assist_operationTextBefore=assist_operationTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					var assist_operationIndexstart=assist_operationTextBefore.indexOf("*")+1;//截取标题序号
					if(assist_operationTextBefore.indexOf("*")>=0){
						var assist_operationIndexover=assist_operationTextBefore.indexOf("：");//截取冒号
						var assist_operationText = assist_operationTextBefore.substring(assist_operationIndexstart,assist_operationIndexover);
						assist_operationTagVal=assist_operationText+":"+assist_operation;
					}else{
						assist_operationTagVal=assist_operationTextBefore;
					}
				}
			}
			
			var healing_cap_station = healingCapStation();
			var healing_cap_stationTag = $(window.frames["myiframe"].document).find("input[name='healing_cap_station']");
			var healing_cap_stationTagVal;
			if(healing_cap_stationTag.length>0){
				if(healing_cap_station) {
					var healing_cap_stationTextBefore=healing_cap_stationTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					var healing_cap_stationIndexstart=healing_cap_stationTextBefore.indexOf("*")+1;//截取标题序号
					var healing_cap_stationIndexover=healing_cap_stationTextBefore.indexOf("："); //冒号下标
					var healing_cap_stationText = healing_cap_stationTextBefore.substring(healing_cap_stationIndexstart,healing_cap_stationIndexover);
					if(healing_cap_stationTextBefore.indexOf("*")>=0){
						healing_cap_stationTagVal=healing_cap_stationText+":"+healing_cap_station;
					}else{
						healing_cap_stationTagVal=healing_cap_stationTextBefore;
						if(healing_cap_stationIndexover>=0){
							healing_cap_stationTagVal=healing_cap_stationText+":"+healing_cap_station;
						}
					}
				}
			}
			
			var anesthesiaMethod = a();
			var anesthesiaMethodTag= $(window.frames["myiframe"].document).find("input[name='anesthesiaMethod']");
			var anesthesiaMethodTagVal;
			if(anesthesiaMethodTag.length>0){
				if(anesthesiaMethod) {
					var anesthesiaMethodTextBefore=anesthesiaMethodTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					var anesthesiaMethodIndexstart=anesthesiaMethodTextBefore.indexOf("*")+1;//截取标题序号
					if(anesthesiaMethodTextBefore.indexOf("*")>=0){
						var anesthesiaMethodIndexover=anesthesiaMethodTextBefore.indexOf("：");//截取冒号
						var anesthesiaMethodText = anesthesiaMethodTextBefore.substring(anesthesiaMethodIndexstart,anesthesiaMethodIndexover);
						anesthesiaMethodTagVal=anesthesiaMethodText+":"+anesthesiaMethod;
					}else{
						anesthesiaMethodTagVal=anesthesiaMethodTextBefore;
					}
				}
			}
			
			var intraoperativeMedication = b();
			var intraoperativeMedicationTag= $(window.frames["myiframe"].document).find("input[name='intraoperativeMedication']");
			var intraoperativeMedicationVal;
			if(intraoperativeMedicationTag.length>0){
				if(intraoperativeMedication) {
					var intraoperativeMedicationTextBefore=intraoperativeMedicationTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					var intraoperativeMedicationIndexstart=intraoperativeMedicationTextBefore.indexOf("*")+1;//截取标题序号
					if(intraoperativeMedicationTextBefore.indexOf("*")>=0){
						var intraoperativeMedicationIndexover=intraoperativeMedicationTextBefore.indexOf("：");//截取冒号
						var intraoperativeMedicationText = intraoperativeMedicationTextBefore.substring(intraoperativeMedicationIndexstart,intraoperativeMedicationIndexover);
						intraoperativeMedicationVal=intraoperativeMedicationText+":"+intraoperativeMedication;
					}else{
						intraoperativeMedicationVal=intraoperativeMedicationTextBefore;
					}
				}
			}
			
			var postoperation_user_deugs = $(window.frames["myiframe"].document).find("input[name='postoperation_user_deugs']:checked").val();
			var collutory = $(window.frames["myiframe"].document).find("input[name='collutory']:checked").val();
			var collutoryVal;
			if($(window.frames["myiframe"].document).find("input[name='collutory']").length>0){
				if(collutory) {
					var collutoryIndex=$(window.frames["myiframe"].document).find("input[name='collutory']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='collutory']").parent().text().indexOf("*")>=0){
						var collutoryText = $(window.frames["myiframe"].document).find("input[name='collutory']").parent().text().substring(collutoryIndex);
						collutoryVal=collutoryText;
					}else{
						collutoryVal=$(window.frames["myiframe"].document).find("input[name='collutory']").parent().text();
					}
				}
			}
			
			var small_teeth = $(window.frames["myiframe"].document).find("input[name='small_teeth']:checked").val();
			var small_teethVal;
			if($(window.frames["myiframe"].document).find("input[name='small_teeth']").length>0){
				if(small_teeth) {
					var small_teethIndex=$(window.frames["myiframe"].document).find("input[name='small_teeth']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='small_teeth']").parent().text().indexOf("*")>=0){
						var small_teethText = $(window.frames["myiframe"].document).find("input[name='small_teeth']").parent().text().substring(small_teethIndex);
						small_teethVal=small_teethText;
					}else{
						small_teethVal=$(window.frames["myiframe"].document).find("input[name='small_teeth']").parent().text();
					}
				}
			}
			
			var announcements = $(window.frames["myiframe"].document).find("input[name='announcements']:checked").val();
			var announcementsVal;
			if($(window.frames["myiframe"].document).find("input[name='announcements']").length>0){
				var announcementsTag= $(window.frames["myiframe"].document).find("input[name='announcements']");
				//var announcementsTagBeforeText= $(window.frames["myiframe"].document).find("input[name='announcements']").parent().text();
				var announcementsTagBeforeText= announcementsTag.eq(0).parents(".preparation-ul").find("span").text();
				if(announcements) {
					if(!announcementsTagBeforeText){
						announcementsTagBeforeText=announcementsTag.parent().text();
					}
					var announcementsIndexStar=announcementsTagBeforeText.indexOf("*")+1;
					var announcementsIndexOver=announcementsTagBeforeText.indexOf("：");
					if(announcementsTagBeforeText.indexOf("*")>=0){
						var announcementsText;
						if(announcementsIndexOver==-1){
							announcementsText = announcementsTagBeforeText.substring(announcementsIndexStar);
							announcementsVal=announcementsText;
						}else{
							announcementsText = announcementsTagBeforeText.substring(announcementsIndexStar,announcementsIndexOver);
							announcementsVal=announcementsText+":"+announcements;
						}
					}else{
						announcementsVal=announcementsTagBeforeText;
					}
				}
			}
			
			var opration_record = $(window.frames["myiframe"].document).find("input[name='opration_record']:checked").val();
			var opration_recordVal;
			if($(window.frames["myiframe"].document).find("input[name='opration_record']").length>0){
				if(opration_record) {
					var opration_recordIndex=$(window.frames["myiframe"].document).find("input[name='opration_record']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='opration_record']").parent().text().indexOf("*")>=0){
						var opration_recordText = $(window.frames["myiframe"].document).find("input[name='opration_record']").parent().text().substring(opration_recordIndex);
						opration_recordVal=opration_recordText;
					}else{
						opration_recordVal=$(window.frames["myiframe"].document).find("input[name='opration_record']").parent().text();
					}
				}
			}
			
			var remark = $(window.frames["myiframe"].document).find("textarea[name='remark']").val();
			var checkCavityCleanSitu = $(window.frames["myiframe"].document).find("select[name='checkCavityCleanSitu']").val();
			var checkCavityCleanSituVal;
			if($(window.frames["myiframe"].document).find("select[name='checkCavityCleanSitu']").length>0){
				if(checkCavityCleanSitu) {
					var checkCavityCleanSituTextnode = $(window.frames["myiframe"].document).find("select[name='checkCavityCleanSitu']").parent().text();//找到其标题
					var checkCavityCleanSituIndexstart=checkCavityCleanSituTextnode.indexOf("*")+1;//截取标题序号
					if(checkCavityCleanSituTextnode.indexOf("*")>=0){
						var checkCavityCleanSituIndexover=checkCavityCleanSituTextnode.indexOf("：");//截取冒号
						var checkCavityCleanSituText = checkCavityCleanSituTextnode.substring(checkCavityCleanSituIndexstart,checkCavityCleanSituIndexover);
						if(checkCavityCleanSitu!="-请选择-"){
							checkCavityCleanSituVal=checkCavityCleanSituText+":"+checkCavityCleanSitu;
						}
					}else{
						checkCavityCleanSituVal=checkCavityCleanSituTextnode;
					}
				}
			}
			
			var satisfactionPatients = $(window.frames["myiframe"].document).find("select[name='satisfactionPatients']").val();
			var satisfactionPatientsVal;
			if($(window.frames["myiframe"].document).find("select[name='satisfactionPatients']").length>0){
				if(satisfactionPatients) {
					var satisfactionPatientsTextnode = $(window.frames["myiframe"].document).find("select[name='satisfactionPatients']").parent().text();//找到其标题
					var satisfactionPatientsIndexstart=satisfactionPatientsTextnode.indexOf("*")+1;//截取标题序号
					if(satisfactionPatientsTextnode.indexOf("*")>=0){
						var satisfactionPatientsIndexover=satisfactionPatientsTextnode.indexOf("：");//截取冒号
						var satisfactionPatientsText = satisfactionPatientsTextnode.substring(satisfactionPatientsIndexstart,satisfactionPatientsIndexover);
						satisfactionPatientsVal=satisfactionPatientsText+":"+satisfactionPatients;
					}else{
						satisfactionPatientsVal=satisfactionPatientsTextnode;
					}
				}
			}
			
			var checkPlantSituation = $(window.frames["myiframe"].document).find("select[name='checkPlantSituation']").val();
			var checkPlantSituationVal;
			if($(window.frames["myiframe"].document).find("select[name='checkPlantSituation']").length>0){
				if(checkPlantSituation) {
					var checkPlantSituationTextnode = $(window.frames["myiframe"].document).find("select[name='checkPlantSituation']").parent().text();//找到其标题
					var checkPlantSituationIndexstart=checkPlantSituationTextnode.indexOf("*")+1;//截取标题序号
					if(checkPlantSituationTextnode.indexOf("*")>=0){
						var checkPlantSituationIndexover=checkPlantSituationTextnode.indexOf("：");//截取冒号
						var checkPlantSituationText;
						if(checkPlantSituationIndexover==-1){
							checkPlantSituationText = checkPlantSituationTextnode.substring(checkPlantSituationIndexstart);
						}else{
							checkPlantSituationText = checkPlantSituationTextnode.substring(checkPlantSituationIndexstart,checkPlantSituationIndexover);
						}
						checkPlantSituationVal=checkPlantSituationText+":"+checkPlantSituation;
					}else{
						checkPlantSituationVal=checkPlantSituationTextnode;
					}
				}
			}
			
			var postoperativeModulus = $(window.frames["myiframe"].document).find("input[name='postoperativeModulus']:checked").val();
			var is_stitches = $(window.frames["myiframe"].document).find("input[name='is_stitches']:checked").val();
			var is_stitchesVal;
			if($(window.frames["myiframe"].document).find("input[name='is_stitches']").length>0){
				if(is_stitches) {
					var is_stitchesIndex=$(window.frames["myiframe"].document).find("input[name='is_stitches']").parent().text().indexOf("*")+1;
					var is_stitchesText;
					if($(window.frames["myiframe"].document).find("input[name='is_stitches']").parent().text().indexOf("*")>=0){
						is_stitchesText = $(window.frames["myiframe"].document).find("input[name='is_stitches']").parent().text().substring(is_stitchesIndex);
					}else{
						is_stitchesText = $(window.frames["myiframe"].document).find("input[name='is_stitches']").parent().text();
					}
					is_stitchesVal=is_stitchesText;
				}
			}
			
			var complete_case_record = $(window.frames["myiframe"].document).find("input[name='complete_case_record']:checked").val();
			var complete_case_recordVal;
			if($(window.frames["myiframe"].document).find("input[name='complete_case_record']").length>0){
				if(complete_case_record) {
					var complete_case_recordIndex=$(window.frames["myiframe"].document).find("input[name='complete_case_record']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='complete_case_record']").parent().text().indexOf("*")>=0){
						var complete_case_recordText = $(window.frames["myiframe"].document).find("input[name='complete_case_record']").parent().text().substring(complete_case_recordIndex);
						complete_case_recordVal=complete_case_recordText;
					}else{
						complete_case_recordVal=$(window.frames["myiframe"].document).find("input[name='complete_case_record']").parent().text();
					}
				}
			}
			
			var panoramic_view_piece = $(window.frames["myiframe"].document).find("input[name='panoramic_view_piece']:checked").val();
			var panoramic_view_pieceVal;
			if($(window.frames["myiframe"].document).find("input[name='panoramic_view_piece']").length>0){
				if(panoramic_view_piece) {
					var panoramic_view_pieceIndex=$(window.frames["myiframe"].document).find("input[name='panoramic_view_piece']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='panoramic_view_piece']").parent().text().indexOf("*")>=0){
						var panoramic_view_pieceText = $(window.frames["myiframe"].document).find("input[name='panoramic_view_piece']").parent().text().substring(panoramic_view_pieceIndex);
						panoramic_view_pieceVal=panoramic_view_pieceText;
					}else{
						panoramic_view_pieceVal=$(window.frames["myiframe"].document).find("input[name='panoramic_view_piece']").parent().text();
					}
				}
			}
			
			var confirmOcclusalRelationship = $(window.frames["myiframe"].document).find("input[name='confirmOcclusalRelationship']:checked").val();
			var makeTransitionDenture = $(window.frames["myiframe"].document).find("input[name='makeTransitionDenture']:checked").val();
			var makeTransitionDentureVal;
			if($(window.frames["myiframe"].document).find("input[name='makeTransitionDenture']").length>0){
				if(makeTransitionDenture) {
					var makeTransitionDentureTag = $(window.frames["myiframe"].document).find("input[name='makeTransitionDenture']");
					var makeTransitionDentureTextBefore=makeTransitionDentureTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					var makeTransitionDentureIndexstart=makeTransitionDentureTextBefore.indexOf("*")+1;//截取标题序号
					if(makeTransitionDentureTextBefore.indexOf("*")>=0){
						var makeTransitionDentureIndexover=makeTransitionDentureTextBefore.indexOf("：");//截取冒号
						var makeTransitionDentureText = makeTransitionDentureTextBefore.substring(makeTransitionDentureIndexstart,makeTransitionDentureIndexover);
						makeTransitionDentureVal=makeTransitionDentureText+":"+makeTransitionDenture;
					}else{
						makeTransitionDentureVal=makeTransitionDentureTextBefore;
					}
				}
			}
			
			var tryInTransitionDenture = $(window.frames["myiframe"].document).find("input[name='tryInTransitionDenture']:checked").val();
			var modulusToMakePersonalizedTrays = $(window.frames["myiframe"].document).find("input[name='modulusToMakePersonalizedTrays']:checked").val();
			var jawrelationRecord = $(window.frames["myiframe"].document).find("input[name='jawrelationRecord']:checked").val();
			var tryBaseStationInnerCrown = $(window.frames["myiframe"].document).find("input[name='tryBaseStationInnerCrown']:checked").val();
			var secondGetModulus = $(window.frames["myiframe"].document).find("input[name='secondGetModulus']:checked").val();
			var wearTooth = $(window.frames["myiframe"].document).find("input[name='wearTooth']:checked").val();
			var wearToothVal;
			if($(window.frames["myiframe"].document).find("input[name='wearTooth']").length>0){
				if(wearTooth) {
					var wearToothBeforeText=$(window.frames["myiframe"].document).find("input[name='wearTooth']").parent().text();
					var wearToothIndex=$(window.frames["myiframe"].document).find("input[name='wearTooth']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='wearTooth']").parent().text().indexOf("*")>=0){
						var wearToothText = $(window.frames["myiframe"].document).find("input[name='wearTooth']").parent().text().substring(wearToothIndex);
						wearToothVal=wearToothText;
					}else{
						wearToothVal=$(window.frames["myiframe"].document).find("input[name='wearTooth']").parent().text();
					}
				}
			}
			
			var isphotograph = $(window.frames["myiframe"].document).find("input[name='isphotograph']:checked").val();
			var isphotographVal;
			if($(window.frames["myiframe"].document).find("input[name='isphotograph']").length>0){
				if(isphotograph) {
					var isphotographIndex=$(window.frames["myiframe"].document).find("input[name='isphotograph']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='isphotograph']").parent().text().indexOf("*")>=0){
						var isphotographText = $(window.frames["myiframe"].document).find("input[name='isphotograph']").parent().text().substring(isphotographIndex);
						isphotographVal=isphotographText;
					}else{
						isphotographVal=$(window.frames["myiframe"].document).find("input[name='isphotograph']").parent().text();
					}
				}
			}
			
			var toTryAgain = $(window.frames["myiframe"].document).find("input[name='toTryAgain']:checked").val();
			var next_hospital_time = $(window.frames["myiframe"].document).find("input[name='next_hospital_time']").val();
			var next_hospital_timeVal;
			if($(window.frames["myiframe"].document).find("input[name='next_hospital_time']").length>0){
				if(next_hospital_time) {
					var next_hospital_timeTextnode = $(window.frames["myiframe"].document).find("input[name='next_hospital_time']").parent().text();//找到其标题
					var next_hospital_timeIndexstart=next_hospital_timeTextnode.indexOf("*")+1;//截取标题序号
					var next_hospital_timeIndexover=next_hospital_timeTextnode.indexOf("：");//截取冒号
					var next_hospital_timeText = next_hospital_timeTextnode.substring(next_hospital_timeIndexstart,next_hospital_timeIndexover);
					if(next_hospital_timeTextnode.indexOf("*")>=0){
						next_hospital_timeVal=next_hospital_timeText+":"+next_hospital_time;
					}else{
						next_hospital_timeVal=next_hospital_timeTextnode;
						if(next_hospital_timeIndexover>=0){
							next_hospital_timeVal=next_hospital_timeText+":"+next_hospital_time;
						}
					}
				}
			}
			
			var twoDateStitchesTime = $(window.frames["myiframe"].document).find("input[name='twoDateStitchesTime']").val();
			var twoDateStitchesTimeVal;
			if($(window.frames["myiframe"].document).find("input[name='twoDateStitchesTime']").length>0){
				if(twoDateStitchesTime) {
					var twoDateStitchesTimeTextnode = $(window.frames["myiframe"].document).find("input[name='twoDateStitchesTime']").parent().text();//找到其标题
					var twoDateStitchesTimeIndexstart=twoDateStitchesTimeTextnode.indexOf("*")+1;//截取标题序号
					if(twoDateStitchesTimeTextnode.indexOf("*")>=0){
						var twoDateStitchesTimeIndexover=twoDateStitchesTimeTextnode.indexOf("：");//截取冒号
						var twoDateStitchesTimeText = twoDateStitchesTimeTextnode.substring(twoDateStitchesTimeIndexstart,twoDateStitchesTimeIndexover);
						twoDateStitchesTimeVal=twoDateStitchesTimeText+":"+twoDateStitchesTime;
					}else{
						twoDateStitchesTimeVal=twoDateStitchesTimeTextnode;
					}
				}
			}
			
			var twoOperationAttention = $(window.frames["myiframe"].document).find("input[name='twoOperationAttention']:checked").val();
			var completeTwoOperation = $(window.frames["myiframe"].document).find("input[name='completeTwoOperation']:checked").val();
			var completeTwoOperationVal;
			if($(window.frames["myiframe"].document).find("input[name='completeTwoOperation']").length>0){
				if(completeTwoOperation) {
					var completeTwoOperationTextnode = $(window.frames["myiframe"].document).find("input[name='completeTwoOperation']").parent().text();//找到其标题
					var completeTwoOperationIndexstart=completeTwoOperationTextnode.indexOf("*")+1;//截取标题序号
					if(completeTwoOperationTextnode.indexOf("*")>=0){
						var completeTwoOperationText = completeTwoOperationTextnode.substring(completeTwoOperationIndexstart);
						completeTwoOperationVal=completeTwoOperationText;
					}else{
						completeTwoOperationVal=completeTwoOperationTextnode;
					}
				}
			}
			
			var checkWearToothSitu = $(window.frames["myiframe"].document).find("input[name='checkWearToothSitu']:checked").val();
			var antimicrobial_use = $(window.frames["myiframe"].document).find("input[name='antimicrobial_use']:checked").val();
			var antimicrobial_useVal;
			if($(window.frames["myiframe"].document).find("input[name='antimicrobial_use']").length>0){
				if(antimicrobial_use) {
					var antimicrobial_useIndex=$(window.frames["myiframe"].document).find("input[name='antimicrobial_use']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='antimicrobial_use']").parent().text().indexOf("*")>=0){
						var antimicrobial_useText = $(window.frames["myiframe"].document).find("input[name='antimicrobial_use']").parent().text().substring(antimicrobial_useIndex);
						antimicrobial_useVal=antimicrobial_useText;
					}else{
						antimicrobial_useVal=$(window.frames["myiframe"].document).find("input[name='antimicrobial_use']").parent().text();
					}
				}
			}
			
			
			var wearteeth = $(window.frames["myiframe"].document).find("input[name='wearteeth']:checked").val();
			var wearteethVal;
			if($(window.frames["myiframe"].document).find("input[name='wearteeth']").length>0){
				if(wearteeth) {
					var wearteethIndex=$(window.frames["myiframe"].document).find("input[name='wearteeth']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='wearteeth']").parent().text().indexOf("*")>=0){
						var wearteethText = $(window.frames["myiframe"].document).find("input[name='wearteeth']").parent().text().substring(wearteeth);
						wearteethVal=wearteethText;
					}else{
						wearteethVal=$(window.frames["myiframe"].document).find("input[name='wearteeth']").parent().text();
					}
				}
			}
			
			var accord = $(window.frames["myiframe"].document).find("input[name='accord']:checked").val();
			var accordVal;
			if($(window.frames["myiframe"].document).find("input[name='accord']").length>0){
				if(accord) {
					var accordIndex=$(window.frames["myiframe"].document).find("input[name='accord']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='accord']").parent().text().indexOf("*")>=0){
						var accordText = $(window.frames["myiframe"].document).find("input[name='accord']").parent().text().substring(accordIndex);
						accordVal=accordText;
					}else{
						accordVal=$(window.frames["myiframe"].document).find("input[name='accord']").parent().text();
					}
				}
			}
			
			var ismodule = $(window.frames["myiframe"].document).find("input[name='ismodule']:checked").val();
			var ismoduleVal;
			if($(window.frames["myiframe"].document).find("input[name='ismodule']").length>0){
				if(ismodule) {
					var ismoduleIndex=$(window.frames["myiframe"].document).find("input[name='ismodule']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='ismodule']").parent().text().indexOf("*")>=0){
						var ismoduleText = $(window.frames["myiframe"].document).find("input[name='ismodule']").parent().text().substring(ismoduleIndex);
						ismoduleVal=ismoduleText;
					}else{
						ismoduleVal=$(window.frames["myiframe"].document).find("input[name='ismodule']").parent().text();
					}
				}
			}
			
			var transfer = $(window.frames["myiframe"].document).find("input[name='transfer']:checked").val();
			var transferVal;
			if($(window.frames["myiframe"].document).find("input[name='transfer']").length>0){
				if(transfer) {
					var transferIndex=$(window.frames["myiframe"].document).find("input[name='transfer']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='transfer']").parent().text().indexOf("*")>=0){
						var transferText = $(window.frames["myiframe"].document).find("input[name='transfer']").parent().text().substring(transferIndex);
						transferVal=transferText;
					}else{
						transferVal=$(window.frames["myiframe"].document).find("input[name='transfer']").parent().text();
					}
				}
			}
			
			var checkPlantBoneCombine = $(window.frames["myiframe"].document).find("select[name='checkPlantBoneCombine']").val();
			var checkPlantBoneCombineVal;
			if($(window.frames["myiframe"].document).find("select[name='checkPlantBoneCombine']").length>0){
				if(checkPlantBoneCombine) {
					var checkPlantBoneCombineTextnode = $(window.frames["myiframe"].document).find("select[name='checkPlantBoneCombine']").parent().text();//找到其标题
					var checkPlantBoneCombineIndexstart=checkPlantBoneCombineTextnode.indexOf("*")+1;//截取标题序号
					if(checkPlantBoneCombineTextnode.indexOf("*")>=0){
						var checkPlantBoneCombineIndexover=checkPlantBoneCombineTextnode.indexOf("：");//截取冒号
						var checkPlantBoneCombineText = checkPlantBoneCombineTextnode.substring(checkPlantBoneCombineIndexstart,checkPlantBoneCombineIndexover);
						checkPlantBoneCombineVal=checkPlantBoneCombineText+":"+checkPlantBoneCombine;
					}else{
						checkPlantBoneCombineVal=checkPlantBoneCombineTextnode;
					}
				}
			}
			
			var basalCrowns = $(window.frames["myiframe"].document).find("input[name='basalCrowns']:checked").val();
			var basalCrownsVal;
			if($(window.frames["myiframe"].document).find("input[name='basalCrowns']").length>0){
				if(basalCrowns) {
					var basalCrownsIndex=$(window.frames["myiframe"].document).find("input[name='basalCrowns']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='basalCrowns']").parent().text().indexOf("*")>=0){
						var basalCrownsText = $(window.frames["myiframe"].document).find("input[name='basalCrowns']").parent().text().substring(basalCrownsIndex);
						basalCrownsVal=basalCrownsText;
					}else{
						basalCrownsVal=$(window.frames["myiframe"].document).find("input[name='basalCrowns']").parent().text();
					}
				}
			}
			
			var ectolophUse = $(window.frames["myiframe"].document).find("input[name='ectolophUse']:checked").val();
			var tryHolderOrBasalCrowns = $(window.frames["myiframe"].document). find("input[name='tryHolderOrBasalCrowns']:checked").val();
			var resinSealing = $(window.frames["myiframe"].document).find("input[name='resinSealing']:checked").val();
			var askUserSituation = $(window.frames["myiframe"].document).find("input[name='askUserSituation']:checked").val();
			var doctorWrittenRecords = $(window.frames["myiframe"].document).find("input[name='doctorWrittenRecords']:checked").val();
			var checkOcclusion = $(window.frames["myiframe"].document).find("input[name='checkOcclusion']:checked").val();
			var checkOcclusionVal;
			var checkOcclusionTag= $(window.frames["myiframe"].document).find("input[name='checkOcclusion']");
			if($(window.frames["myiframe"].document).find("input[name='checkOcclusion']").length>0){
				if(checkOcclusion) {
					var checkOcclusionTextBefore=checkOcclusionTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					if(!checkOcclusionTextBefore){
						checkOcclusionTextBefore=$(window.frames["myiframe"].document).find("input[name='checkOcclusion']").parent().text();//找到其标题
					}
					var checkOcclusionIndexstart=checkOcclusionTextBefore.indexOf("*")+1;//截取标题序号
					if(checkOcclusionTextBefore.indexOf("*")>=0){
						var checkOcclusionIndexover=checkOcclusionTextBefore.indexOf("：");//截取冒号
						var checkOcclusionText;
						if(checkOcclusionTextBefore.indexOf("：")==-1){
							checkOcclusionText = checkOcclusionTextBefore.substring(checkOcclusionIndexstart);
						}else{
							checkOcclusionText = checkOcclusionTextBefore.substring(checkOcclusionIndexstart,checkOcclusionIndexover);
						}
					}
					checkOcclusionVal=checkOcclusionText+":"+checkOcclusion;
				}
			}
			
			var colorimetric = $(window.frames["myiframe"].document).find("input[name='colorimetric']:checked").val();
			var colorimetricVal;
			if($(window.frames["myiframe"].document).find("input[name='colorimetric']").length>0){
				if(colorimetric) {
					var colorimetricIndex=$(window.frames["myiframe"].document).find("input[name='colorimetric']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='colorimetric']").parent().text().indexOf("*")>=0){
						var colorimetricText = $(window.frames["myiframe"].document).find("input[name='colorimetric']").parent().text().substring(colorimetricIndex);
						colorimetricVal=colorimetricText;
					}else{
						colorimetricVal=$(window.frames["myiframe"].document).find("input[name='colorimetric']").parent().text();
					}
				}
			}
			
			var localBridge = $(window.frames["myiframe"].document).find("input[name='localBridge']").val();
			var try_in = $(window.frames["myiframe"].document).find("input[name='try_in']").val();
			var try_inVal;
			if($(window.frames["myiframe"].document).find("input[name='try_in']").length>0){
				if(try_in) {
					var try_inIndex=$(window.frames["myiframe"].document).find("input[name='try_in']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='try_in']").parent().text().indexOf("*")>=0){
						var try_inText = $(window.frames["myiframe"].document).find("input[name='try_in']").parent().text().substring(try_inIndex);
						try_inVal=try_inText;
					}else{
						try_inVal=$(window.frames["myiframe"].document).find("input[name='try_in']").parent().text();
					}
				}
			}
			
			var askAboutUsage = $(window.frames["myiframe"].document).find("select[name='askAboutUsage']").val();
			var askAboutUsageVal;
			if($(window.frames["myiframe"].document).find("select[name='askAboutUsage']").length>0){
				if(askAboutUsage) {
					var askAboutUsageTextnode = $(window.frames["myiframe"].document).find("select[name='askAboutUsage']").parent().text();//找到其标题
					var askAboutUsageIndexstart=askAboutUsageTextnode.indexOf("*")+1;//截取标题序号
					if(askAboutUsageTextnode.indexOf("*")>=0){
						var askAboutUsageIndexover=askAboutUsageTextnode.indexOf("：");//截取冒号
						var askAboutUsageText = askAboutUsageTextnode.substring(askAboutUsageIndexstart,askAboutUsageIndexover);
						askAboutUsageVal=askAboutUsageText+":"+askAboutUsage;
					}else{
						askAboutUsageVal=askAboutUsageTextnode;
					}
				}
			}
			
			var check_surgical_healing = $(window.frames["myiframe"].document).find("input[name='check_surgical_healing']").val();
			var nostitches;
			nostitches= $(window.frames["myiframe"].document).find("input[name='nostitches']:checked").val();
			if(nostitches){
				nostitches = $(window.frames["myiframe"].document).find("input[name='nostitches']").val();
			}
			var nostitchesVal;
			if($(window.frames["myiframe"].document).find("input[name='nostitches']").length>0){
				if(nostitches) {
					var nostitchesIndex=$(window.frames["myiframe"].document).find("input[name='nostitches']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='nostitches']").parent().text().indexOf("*")>=0){
						var nostitchesText = $(window.frames["myiframe"].document).find("input[name='nostitches']").parent().text().substring(nostitchesIndex);
						nostitchesVal=nostitchesText;
					}else{
						nostitchesVal=$(window.frames["myiframe"].document).find("input[name='nostitches']").parent().text();
					}
				}
			}
			
			var enterLclj = $(window.frames["myiframe"].document).find("input[name='enterLclj']:checked").val();
			var enterLcljVal;
			if($(window.frames["myiframe"].document).find("input[name='enterLclj']").length>0){
				if(enterLclj) {
					var enterLcljIndex=$(window.frames["myiframe"].document).find("input[name='enterLclj']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='enterLclj']").parent().text().indexOf("*")>=0){
						var enterLcljText = $(window.frames["myiframe"].document).find("input[name='enterLclj']").parent().text().substring(enterLcljIndex);
						enterLcljVal=enterLcljText;
					}else{
						enterLcljVal=$(window.frames["myiframe"].document).find("input[name='enterLclj']").parent().text();
					}
				}
			}
			
//			新加参数
			var operator = $(window.frames["myiframe"].document).find("input[name='operator']:checked").val();//手术
			var operatorVal;
			if($(window.frames["myiframe"].document).find("input[name='operator']").length>0){
				if(operator) {
					var operatorIndex=$(window.frames["myiframe"].document).find("input[name='operator']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='operator']").parent().text().indexOf("*")>=0){
						var operatorText = $(window.frames["myiframe"].document).find("input[name='operator']").parent().text().substring(operatorIndex);
						operatorVal=operatorText;
					}else{
						operatorVal=$(window.frames["myiframe"].document).find("input[name='operator']").parent().text();
					}
				}
			}
			
			var finish_norm_first = $(window.frames["myiframe"].document).find("input[name='finish_norm_first']:checked").val();//种植修复治愈标准一
			var finish_norm_second = $(window.frames["myiframe"].document).find("input[name='finish_norm_second']:checked").val();//种植修复治愈标准二
			var finish_norm_third = $(window.frames["myiframe"].document).find("input[name='finish_norm_third']:checked").val();//种植修复治愈标准三
			var finish_norm_fourth = $(window.frames["myiframe"].document).find("input[name='finish_norm_fourth']:checked").val();//种植修复治愈标准四
			var finish_norm_fifth = $(window.frames["myiframe"].document).find("input[name='finish_norm_fifth']:checked").val();//种植修复治愈标准五
			var before_Modulo_bite = $(window.frames["myiframe"].document).find("input[name='before_Modulo_bite']:checked").val();//术前取模、定咬合关系
			var after_Modulo_bite = $(window.frames["myiframe"].document).find("input[name='after_Modulo_bite']:checked").val();//术后当天取模、定咬合关系
			var after_Modulo_biteVal;
			if($(window.frames["myiframe"].document).find("input[name='after_Modulo_bite']").length>0){
				if(after_Modulo_bite) {
					var after_Modulo_biteIndex=$(window.frames["myiframe"].document).find("input[name='after_Modulo_bite']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='after_Modulo_bite']").parent().text().indexOf("*")>=0){
						var after_Modulo_biteText = $(window.frames["myiframe"].document).find("input[name='after_Modulo_bite']").parent().text().substring(after_Modulo_biteIndex);
						after_Modulo_biteVal=after_Modulo_biteText;
					}else{
						after_Modulo_biteVal=$(window.frames["myiframe"].document).find("input[name='after_Modulo_bite']").parent().text();
					}
				}
			}
			
			var fixation_tooth_bridge = $(window.frames["myiframe"].document).find("input[name='fixation_tooth_bridge']:checked").val();//固定牙桥
			var fixation_tooth_bridgeVal;
			if($(window.frames["myiframe"].document).find("input[name='fixation_tooth_bridge']").length>0){
				if(fixation_tooth_bridge) {
					var fixation_tooth_bridgeTag = $(window.frames["myiframe"].document).find("input[name='fixation_tooth_bridge']");
					var fixation_tooth_bridgeTextBefore=fixation_tooth_bridgeTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					if(!fixation_tooth_bridgeTextBefore){
						fixation_tooth_bridgeTextBefore=fixation_tooth_bridgeTag.parent().text();//找到其标题
					}
					var fixation_tooth_bridgeIndexstart=fixation_tooth_bridgeTextBefore.indexOf("*")+1;//截取标题序号
					if(fixation_tooth_bridgeTextBefore.indexOf("*")>=0){
						var fixation_tooth_bridgeIndexover=fixation_tooth_bridgeTextBefore.indexOf("：");//截取冒号
						var fixation_tooth_bridgeText;
						if(fixation_tooth_bridgeIndexover==-1){
							fixation_tooth_bridgeText = fixation_tooth_bridgeTextBefore.substring(fixation_tooth_bridgeIndexstart);
						}else{
							fixation_tooth_bridgeText = fixation_tooth_bridgeTextBefore.substring(fixation_tooth_bridgeIndexstart,fixation_tooth_bridgeIndexover);
						}
						fixation_tooth_bridgeVal=fixation_tooth_bridgeText+":"+fixation_tooth_bridge;
					}else{
						fixation_tooth_bridgeVal=fixation_tooth_bridgeTextBefore;
					}
				}
			}
			
			var decision_bite = $(window.frames["myiframe"].document).find("input[name='decision_bite']:checked").val();//定咬合
			var decision_biteVal;
			if($(window.frames["myiframe"].document).find("input[name='decision_bite']").length>0){
				if(decision_bite) {
					var decision_biteTag = $(window.frames["myiframe"].document).find("input[name='decision_bite']");
					var decision_biteTextBefore=decision_biteTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					if(!decision_biteTextBefore){
						decision_biteTextBefore=decision_biteTag.parent().text();//找到其标题
					}
					var decision_biteIndexstart=decision_biteTextBefore.indexOf("*")+1;//截取标题序号
					if(decision_biteTextBefore.indexOf("*")>=0){
						var decision_biteIndexover=decision_biteTextBefore.indexOf("：");//截取冒号
						var decision_biteText;
						if(decision_biteIndexover==-1){
							decision_biteText = decision_biteTextBefore.substring(decision_biteIndexstart);
						}else{
							decision_biteText = decision_biteTextBefore.substring(decision_biteIndexstart,decision_biteIndexover);
						}
						decision_biteVal=decision_biteText+":"+decision_bite;
					}else{
						decision_biteVal=decision_biteTextBefore;
					}
				}
			}
			
			var upper_frame = $(window.frames["myiframe"].document).find("input[name='upper_frame']:checked").val();//上颌架
			var upper_frameVal;
			if($(window.frames["myiframe"].document).find("input[name='upper_frame']").length>0){
				if(upper_frame) {
					var upper_frameTag = $(window.frames["myiframe"].document).find("input[name='upper_frame']");
					var upper_frameTextBefore=upper_frameTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					if(!upper_frameTextBefore){
						upper_frameTextBefore=upper_frameTag.parent().text();//找到其标题
					}
					var upper_frameIndexstart=upper_frameTextBefore.indexOf("*")+1;//截取标题序号
					if(upper_frameTextBefore.indexOf("*")>=0){
						var upper_frameIndexover=upper_frameTextBefore.indexOf("：");//截取冒号
						var upper_frameText;
						if(upper_frameIndexover==-1){
							upper_frameText = upper_frameTextBefore.substring(upper_frameIndexstart);
						}else{
							upper_frameText = upper_frameTextBefore.substring(upper_frameIndexstart,upper_frameIndexover);
						}
						upper_frameVal=upper_frameText+":"+upper_frame;
					}else{
						upper_frameVal=upper_frameTextBefore;
					}
				}
			}
			
			var is_loose = $(window.frames["myiframe"].document).find("input[name='is_loose']:checked").val();//检查螺丝是否松动
			var is_looseVal;
			if($(window.frames["myiframe"].document).find("input[name='is_loose']").length>0){
				if(is_loose) {
					var is_looseTag = $(window.frames["myiframe"].document).find("input[name='is_loose']");
					var is_looseTextBefore=is_looseTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					var is_looseIndexstart=is_looseTextBefore.indexOf("*")+1;//截取标题序号
					if(is_looseTextBefore.indexOf("*")>=0){
						var is_looseIndexover=is_looseTextBefore.indexOf("：");//截取冒号
						var is_looseText = is_looseTextBefore.substring(is_looseIndexstart,is_looseIndexover);
						is_looseVal=is_looseText+":"+is_loose;
					}else{
						is_looseVal=is_looseTextBefore;
					}
				}
			}
			
			var health_education = $(window.frames["myiframe"].document).find("input[name='health_education']:checked").val();//卫生宣教
			var health_educationVal;
			if($(window.frames["myiframe"].document).find("input[name='health_education']").length>0){
				if(health_education) {
					var health_educationTag = $(window.frames["myiframe"].document).find("input[name='health_education']");
					var health_educationTextBefore=health_educationTag.eq(0).parents(".preparation-ul").find("span").text();//找到其标题
					if(!health_educationTextBefore){
						health_educationTextBefore=health_educationTag.parent().text();//找到其标题
					}
					var health_educationIndexstart=health_educationTextBefore.indexOf("*")+1;//截取标题序号
					if(health_educationTextBefore.indexOf("*")>=0){
						var health_educationIndexover=health_educationTextBefore.indexOf("：");//截取冒号
						var health_educationText;
						if(health_educationIndexover==-1){
							health_educationText = health_educationTextBefore.substring(health_educationIndexstart);
						}else{
							health_educationText = health_educationTextBefore.substring(health_educationIndexstart,health_educationIndexover);
						}
						health_educationVal=health_educationText+":"+health_education;
					}else{
						health_educationVal=health_educationTextBefore;
					}
				}
			}
			
			var neonychium = $(window.frames["myiframe"].document).find("input[name='neonychium']:checked").val();//戴保护垫
			var neonychiumVal;
			if($(window.frames["myiframe"].document).find("input[name='neonychium']").length>0){
				if(neonychium) {
					var neonychiumIndex=$(window.frames["myiframe"].document).find("input[name='neonychium']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='neonychium']").parent().text().indexOf("*")>=0){
						var neonychiumText = $(window.frames["myiframe"].document).find("input[name='neonychium']").parent().text().substring(neonychiumIndex);
						neonychiumVal=neonychiumText;
					}else{
						neonychiumVal=$(window.frames["myiframe"].document).find("input[name='neonychium']").parent().text();
					}
				}
			}
			
			var clean_toothbridge = $(window.frames["myiframe"].document).find("input[name='clean_toothbridge']:checked").val();//清洁牙桥
			var clean_toothbridgeVal;
			if($(window.frames["myiframe"].document).find("input[name='clean_toothbridge']").length>0){
				if(clean_toothbridge) {
					var clean_toothbridgeIndex=$(window.frames["myiframe"].document).find("input[name='clean_toothbridge']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='clean_toothbridge']").parent().text().indexOf("*")>=0){
						var clean_toothbridgeText = $(window.frames["myiframe"].document).find("input[name='clean_toothbridge']").parent().text().substring(clean_toothbridgeIndex);
						clean_toothbridgeVal=clean_toothbridgeText;
					}else{
						clean_toothbridgeVal=$(window.frames["myiframe"].document).find("input[name='clean_toothbridge']").parent().text();
					}
				}
			}
			
			var outside_bone_grafting = $(window.frames["myiframe"].document).find("input[name='outside_bone_grafting']:checked").val();//外提升植骨
			var outside_bone_graftingVal;
			if($(window.frames["myiframe"].document).find("input[name='outside_bone_grafting']").length>0){
				if(outside_bone_grafting) {
					var outside_bone_graftingIndex=$(window.frames["myiframe"].document).find("input[name='outside_bone_grafting']").parent().text().indexOf("*")+1;
					if($(window.frames["myiframe"].document).find("input[name='outside_bone_grafting']").parent().text().indexOf("*")>=0){
						var outside_bone_graftingText = $(window.frames["myiframe"].document).find("input[name='outside_bone_grafting']").parent().text().substring(outside_bone_graftingIndex);
						outside_bone_graftingVal=outside_bone_graftingText;
					}else{
						outside_bone_graftingVal=$(window.frames["myiframe"].document).find("input[name='outside_bone_grafting']").parent().text();
					}
				}
			}
			
			//牙位变异
			if($(window.frames["myiframe"].document).find("input[name='extractionleftup_l']").val()){
				var extractionleftup_l = "9、植体是否松动（左上）:" + $(window.frames["myiframe"].document).find("input[name='extractionleftup_l']").val();
			}
			if($(window.frames["myiframe"].document).find("input[name='extractionleftup_r']").val()){
				var extractionleftup_r = "9、植体是否松动（右上）:" + $(window.frames["myiframe"].document).find("input[name='extractionleftup_r']").val();
			}
			if($(window.frames["myiframe"].document).find("input[name='extractionleftdown_l']").val()){
				var extractionleftdown_l = "9、植体是否松动（左下）:" + $(window.frames["myiframe"].document).find("input[name='extractionleftdown_l']").val();
			}
			if($(window.frames["myiframe"].document).find("input[name='extractionleftdown_r']").val()){
				var extractionleftdown_r = "9、植体是否松动（右下）:" + $(window.frames["myiframe"].document).find("input[name='extractionleftdown_r']").val();
			}
			if($(window.frames["myiframe"].document).find("input[name='extractionleftup1_l']").val()){
				var extractionleftup1_l = "10、牙冠是否松动（左上）:" + $(window.frames["myiframe"].document).find("input[name='extractionleftup1_l']").val();
			}
			if($(window.frames["myiframe"].document).find("input[name='extractionleftup1_r']").val()){
				var extractionleftup1_r = "10、牙冠是否松动（右上）:" + $(window.frames["myiframe"].document).find("input[name='extractionleftup1_r']").val();
			}
			if($(window.frames["myiframe"].document).find("input[name='extractionleftdown1_l']").val()){
				var extractionleftdown1_l = "10、牙冠是否松动（左下）:" + $(window.frames["myiframe"].document).find("input[name='extractionleftdown1_l']").val();
			}
			if($(window.frames["myiframe"].document).find("input[name='extractionrightdown1_r']").val()){
				var extractionleftdown1_r = "10、牙冠是否松动（右下）:" + $(window.frames["myiframe"].document).find("input[name='extractionrightdown1_r']").val();
			}
			var flag = true;
			layer.alert('确认提交', {
				  closeBtn: 1    // 是否显示关闭按钮
				  ,anim: 1 //动画类型
				  ,btn: ['确认','关闭'] //按钮
				  ,yes:function(){
					  $.ajax({
							url: apppath + "/HUDH_LcljOperationNodeInforAberranceAct/saveOperationNodeInforAberrance.act",
							type:"POST",
							dataType:"json",
							data : {
//								节点名称
								"nodeName":nodeName,
								"nodeLimit":nodeLimit,
								//新增参数
								"extractionleftup_l":extractionleftup_l,
								"extractionleftup_r":extractionleftup_r,
								"extractionleftdown_l":extractionleftdown_l,
								"extractionleftdown_r":extractionleftdown_r,
								"extractionleftup1_l":extractionleftup1_l,
								"extractionleftup1_r":extractionleftup1_r,
								"extractionleftdown1_l":extractionleftdown1_l,
								"extractionleftdown1_r":extractionleftdown1_r,
								"operator":operatorVal,
								"finish_norm_first":finish_norm_first,
								"finish_norm_second":finish_norm_second,
								"finish_norm_third":finish_norm_third,
								"finish_norm_fourth":finish_norm_fourth,
								"finish_norm_fifth":finish_norm_fifth,
								"before_Modulo_bite":before_Modulo_bite,
								"after_Modulo_bite":after_Modulo_biteVal,
								"fixation_tooth_bridge":fixation_tooth_bridgeVal,
								"decision_bite":decision_biteVal,
								"upper_frame":upper_frameVal,
								"is_loose":is_looseVal,
								"health_education":health_educationVal,
								"neonychium":neonychiumVal,
								"clean_toothbridge":clean_toothbridgeVal,
								"outside_bone_grafting":outside_bone_graftingVal,
							
								"orderNumber": orderNumber,
								"operation_time" : operation_timeVal,
								"preoperation_one_houres" : preoperation_one_houresVal,
								"preoperative_verification" : preoperative_verificationVal,
								"abutment_station" : abutment_stationTagVal,
								"healing_cap_station" : healing_cap_stationTagVal,
								"postoperation_user_deugs" : postoperation_user_deugs,
								"collutory" : collutoryVal,
								"small_teeth" : small_teethVal,
								"announcements" : announcementsVal,
								"opration_record" : opration_recordVal,
								"remark" : remark,
								"assist_operation" : assist_operationTagVal,
								"review_time" : review_timeVal,
								"observe_wound" : observe_woundVal,
								"check_wound" : check_woundVal,
								"is_stitches" : is_stitchesVal,
								"complete_case_record" : complete_case_recordVal,
								"panoramic_view_piece" : panoramic_view_pieceVal,
								"next_hospital_time" : next_hospital_timeVal,
								"twoDateStitchesTime" : twoDateStitchesTimeVal,
								"twoOperationAttention" : twoOperationAttention,
								"ismodule" : ismoduleVal,
								"transfer" : transferVal,
								"basalCrowns" : basalCrownsVal,
								"colorimetric" : colorimetricVal,
								"localBridge" : localBridge,
								"try_in" : try_inVal,
								"askAboutUsage" : askAboutUsageVal,
								"check_surgical_healing" : check_surgical_healing,
								"completeTwoOperation" : completeTwoOperationVal,
								"connectingBridge" : connectingBridge,
								"confirmOcclusalRelationship" : confirmOcclusalRelationship,
								"makeTransitionDenture" : makeTransitionDentureVal,
								"tryInTransitionDenture" : tryInTransitionDenture,
								"modulusToMakePersonalizedTrays" : modulusToMakePersonalizedTrays,
								"jawrelationRecord" : jawrelationRecord,
								"tryBaseStationInnerCrown" : tryBaseStationInnerCrown,
								"secondGetModulus" : secondGetModulus,
								"toTryAgain" : toTryAgain,
								"checkPlantBoneCombine" : checkPlantBoneCombineVal,
								"wearTooth" : wearToothVal,
								"isphotograph" : isphotographVal,
								"checkCavityCleanSitu" : checkCavityCleanSituVal,
								"checkWearToothSitu" : checkWearToothSitu,
								"satisfactionPatients" : satisfactionPatientsVal,
								"antimicrobial_use" : antimicrobial_useVal,
								"wearTeeth":wearteethVal,
								"visit_time" : visit_timeVal,
								"tryHolderOrBasalCrowns" : tryHolderOrBasalCrowns,
								"accord" : accordVal,
								"askUserSituation" : askUserSituation,
								"resinSealing" : resinSealing,
								"checkOcclusion" : checkOcclusionVal,
								"postoperativeModulus" : postoperativeModulus,
								"checkPlantSituation" : checkPlantSituationVal,
								"ectolophUse" : ectolophUse,
								"doctorWrittenRecords" : doctorWrittenRecords,
								"anesthesiaMethod" : anesthesiaMethodTagVal,
								"nostitches" : nostitchesVal,
								"intraoperativeMedication" : intraoperativeMedicationVal,
								"enterLclj" : enterLcljVal,
								"nodeId" : variation_node_id,
							},
							success:function(result){
								/*layer.alert('提交成功', function(index) {
									layer.close(index);
									location.reload();
								})*/
								if (result.retState == "0") {
									layer.alert("提交成功！", {
							            end: function(index) {
							            	layer.close(index);
											location.reload();
							            }
							      	});
						        } else {
							        layer.alert('提交失败：' + result.retMsrg, {//后台抛出的异常信息在前台展示
							            end: function() {
							            	layer.close(index);
							            	location.reload();
							            }
							        });
						        }
							}
					  });
				  }
				  ,btn2:function(){}
			})
		},
		rejectRecord : function(thi) {//查看退回操作记录
			layer.open({
				type: 2,
				title: '退回记录',
				shadeClose: false,
				shade: 0.6,
				area: ['95%', '80%'],
				content: contextPath+'/HUDH_FlowViewAct/toOperationRejectRecord.act?orderNumber=' + orderNumber
			}); 
		},
		toothEditor : function() {
			$(".showToothMap").css("display","block"); //牙位变动按钮
			$("#lclj_ywbd").addClass("hide");
			$("#lclj_ywtj").removeClass("hide");
			$(".ToothBit_checkbox").attr("disabled",false);
			/*****************种植牙位图操作*******************/
			  //点击牙齿
			/*  $('.toothBox').on('click', 'li',
			  function() {
			      $(this).toggleClass('current');
			  });
			  
//			  全选
			  $('.ToothBit_checkbox').each(function(i,obj){
			  	$(this).click(function(){
			  		if ($(this).is(':checked')) {
			  		 $(".ToothBit_checkbox"+(i+1)).each(function(index) {
			 	        	$(this).parent().removeClass('current');
			 	        	
			 	            $(this).parent().toggleClass('current');
			  	     	});
				      }else{
				      	 $(".ToothBit_checkbox"+(i+1)).each(function(index) {
					            $(this).parent().removeClass('current');
					    	 });
				      }
			  	});
			  });*/
			
		},
		toothEditorB : function() {
			$(".showToothMap").css("display","none"); //牙位变动按钮
			$(".toothBox").attr("disabled",true);
			$("#lclj_ywtj").addClass("hide");
			$("#lclj_ywbd").removeClass("hide");
			 /*****************种植牙位图操作*******************/		
			 //点击牙齿
			 /* $('.toothBox').on('click', 'li',
			  function() {
			      $(this).toggleClass('current');
			  });*/
//			  全选
			  /*$('.ToothBit_checkbox').each(function(i,obj){
				  $(this).attr("disabled",true);
			  });*/
			 $.ajax({
				     url:contextPath+"/HUDH_FlowAct/editToothBit.act",    //请求的url地址
				     dataType:"json",   //返回格式为json
				     async:true,//请求是否异步，默认为异步，这也是ajax重要特性
				     data:{"lcljId":patientObj.id,
					 "left_up":getSelectToothNumb($("#zhongzhi>.toothMap>.upYa>li>span[name='zzadultupYa1']")),
					 "left_down":getSelectToothNumb($("#zhongzhi>.toothMap>.downYa>li>span[name='zzadultdownYa1']")),
					 "right_up":getSelectToothNumb($("#zhongzhi>.toothMap>.upYa>li>span[name='zzadultupYa2']")),
					 "right_down":getSelectToothNumb($("#zhongzhi>.toothMap>.downYa>li>span[name='zzadultdownYa2']")),
					 "repair_left_up":getSelectToothNumb($("#xiufu>.toothMap>.upYa>li>span[name='zzadultupYa1']")),
					 "repair_left_down":getSelectToothNumb($("#xiufu>.toothMap>.downYa>li>span[name='zzadultdownYa1']")),
					 "repair_right_up":getSelectToothNumb($("#xiufu>.toothMap>.upYa>li>span[name='zzadultupYa2']")),
					 "repair_right_down":getSelectToothNumb($("#xiufu>.toothMap>.downYa>li>span[name='zzadultdownYa2']")),
					 person:person,
					 },    //参数值
				     type:"POST",   //请求方式
				     success:function(data){
				         //请求成功时处理
						 layer.alert(data.retMsrg);
				     },
				 });
		},
		saveRemark : function() {
//			console.log(patientObj,+"***");
//			alert(1111);
			var remark = $(window.frames["myiframe"].document).find("textarea[name='remark']").val();
			$.ajax({
				     url:contextPath+"/HUDH_LcljOperationNodeRemarkAct/saveNodeRemark.act",    //请求的url地址
				     dataType:"json",   //返回格式为json
				     async:true,//请求是否异步，默认为异步，这也是ajax重要特性
				     data:{
					 		"lcljId":patientObj.id,
					 		"order_number":patientObj.order_number,
					 		"nodeName":patientObj.nodename,
					 		"nodeId":patientObj.nodeid,
					 		"remark":remark
					 },   			 //参数值
				     type:"POST",    //请求方式
				     success:function(data){
				         //请求成功时处理
						 layer.alert(data.retMsrg);
				     },
			});
		}
}

/*
 * 根据Value格式化为带有换行、空格格式的HTML代码
 * @param strValue {String} 需要转换的值
 * @return  {String}转换后的HTML代码
 * @example  
 * getFormatCode("测\r\n\s试")  =>  “测<br/> 试”
 */
function getFormatCode(strValue){
	return strValue.replace(/\r\n/g, '<br/>').replace(/\n/g, '<br/>').replace(/\s/g, ' ');
}

//获取影像学检查
function show(){
    var obj = document.getElementsByName("Fruit");
    var imageological_examination = "";
    for ( k in obj ) {
        if(obj[k].checked)
        //alert(obj[k].value);
        imageological_examination = imageological_examination + obj[k].value + ';';//拼接字符串
    }
    return imageological_examination;
} 

/**
 * 节点点击事件
 * @param thi
 */
function nodeClick(thi){
	if($(thi).attr("nodename")=="手术治疗"){
		$(".showToothMap").css("display","none");
	}
//	alert($(thi).attr("viewurl"));
//	alert($(thi).attr("id"));
//	alert($(thi).attr("nodestatus"));
	nodeId1 = $(thi).attr("id");
	nodeStas = $(thi).attr("nodestatus");
	nodeName = $(thi).attr("nodeName");
	getRemarkList1(nodeId1);
	//手术治疗节点：更改牙位按钮显示
	 if($(thi).text()==2 || nodeName == "手术治疗"){
	  /*$(".showToothMap").css("display","block");*/
	 }else{
	  /*$(".showToothMap").css("display","none");*/
	  $('.toothMap').removeAttr('onclick')
	 }

//	alert(nodeName);
	if (nodeStas == 0) {
		layer.alert("未达到该节点！");
		return ;
	}
	if(nodeStas==2){
		$("#lclj_tj").attr("disabled",true).css("background","#b0b0b0").css("cursor","not-allowed");
		$("#lclj_by").attr("disabled",true).css("background","#b0b0b0").css("cursor","not-allowed");
	}else{
		if(registrationStatus==0){
			$("#lclj_tj").attr("disabled",false).css("background","#00A6C0").css("cursor","pointer");
			$("#lclj_by").attr("disabled",false).css("background","#00A6C0").css("cursor","pointer");		
		}

	}
	$('#myiframe').attr('src', apppath + $(thi).attr("viewurl"));//点击当前环节切换页面
	$.ajax({
		url: apppath + "/HUDH_FlowAct/findHadWorkByOrderNumberAndNodeId.act",
		type:"POST",
		dataType:"json",
		data: {
			"ordNumber" : ordNumber,
			"nodeId" : nodeId1
		},
		success: function(data) {
			if (data.retState == "0") {
				seqId = data.selNodeWork.dataId;
	        } else {
	            /*layer.alert('未达到该节点！' + data.retMsrg, {//后台抛出的异常信息在前台展示
	            	end: function() {
	                	location.reload(); //刷新父页面
		                var frameindex = parent.layer.getFrameIndex(window.name);
		                layer.close(frameindex); //再执行关闭
	                }
	            });*/
	        }
		},
		error: function(data) {
			
		}
	});
	
	//变异内容展示
	var variation_patient_id=$("#patient_id").text();
	//console.log(variation_patient_id+"---------lalllla编号");
	//console.log(nodeId1+"-----lallallalla-----当前节点id");
	
	$.ajax({
		url: apppath + "/HUDH_LcljOperationNodeInforAberranceAct/findOperationNodeInforAberranceByOrderNumberAndNodeId.act",
		type:"POST",
		dataType:"json",
		data: {
			"order_number" : variation_patient_id,
			"nodeId" : nodeId1
		},
		success:function(data){
			//console.log(data+"-----------变异返回数据");
			showVariationInfo(data);
		}
	});
	
}

//变异内容展示方法
//变异内容展示方法
function showVariationInfo(data){
	//console.log(JSON.stringify(data)+"------------变异返回数据");
	if(data.length==0){
		//layer.alert("当前节点没有变异情况！");
		$("#variation_table").html("<span id='variation_notext' style='font-size:13px;font-weight:bold;'>当前节点暂无变异情况！</sapn>");
		return;
	}
	//console.log(JSON.stringify(data)+"-------------变异返回json数据");
	//返回有值字段集合
	var variationObjArr=[];//有值集合数组
	var variationObj={};//返回值全部有值集合
	for(var i=0;i<data.length;i++){
		for(var key in data[i]){
			if(data[i][key]){
				variationObj[key]=data[i][key];
			}
		}
		variationObjArr.push(variationObj);
		variationObj={};
	}
	//console.log(JSON.stringify(variationObjArr)+"----------------返回值全部有值集合");

	//有值去重新集合
	var takeOutrepeatArr=[];//去重后集合新数组
	var takeOutrepeat={};//去重后集合
	var repeat=true;
	for(var j=0;j<variationObjArr.length;j++){
		takeOutrepeat={};
		repeat=true;
		for(var showkey in variationObjArr[j]){
			if(JSON.stringify(takeOutrepeat)!="{}"){
				for(var rkey in takeOutrepeat){
					if(takeOutrepeat[rkey]==variationObjArr[j][showkey]){
						repeat=false;
					}else{
						repeat=true;
					}
				} 
			}
			if(repeat){
				takeOutrepeat[showkey]=variationObjArr[j][showkey];
			}
		}
		takeOutrepeatArr.push(takeOutrepeat);	
		
	};
	//console.log(JSON.stringify(takeOutrepeatArr)+"================去重后集合新数组");
	
	//循环页面所有节点，比对得到当前节点中文名称
	var nodeLiLength=$("#flow_path").find("li");
	//console.log(nodeLiLength.length);
	var nodeName_C; //节点中文名称
	for(var nodei=0; nodei<nodeLiLength.length; nodei++){
		if(nodeLiLength.eq(nodei).find("span").attr("id")==takeOutrepeatArr[0].nodeid){
			nodeName_C=nodeLiLength.eq(nodei).find("font").text();
		} 
	}
	var variation_table;//变异内容html字符串
	variation_table="<font id='"+takeOutrepeatArr[0].nodeid+"' class='node_name'>"+nodeName_C+":</font>";//变异节点中文名
	
	//展示每一次变异情况
	for(var k=0;k<takeOutrepeatArr.length;k++){
//		console.log(JSON.stringify(takeOutrepeatArr[k]));
		//不需要展示的字段
		delete takeOutrepeatArr[k].seq_id;
		delete takeOutrepeatArr[k].organization;
		delete takeOutrepeatArr[k].order_number;
		delete takeOutrepeatArr[k].createuser;
		//console.log(JSON.stringify(takeOutrepeatArr[k])+"----------删除不需要字段后的对象");
		
		variation_table+="<div class='variation_nodeinfo'>";
		variation_table+="<span class='node_time'>第"+(k+1)+"次变异：</span>";
		variation_table+="<span class='' style='font-weight: bold;margin: 30px 10px 0px -56px;'>变异时间："+takeOutrepeatArr[k].createtime+"</span>";
		variation_table+="<ul class='variation_list'>";
		
		//循环集合展示数据
		for(var tablekey in takeOutrepeatArr[k]){
			//手术治疗节点：术中用药单独处理
			if(nodeName_C=='手术治疗'){
				//术中用药
				if(tablekey=="visit_time"){
					variation_table+="<li><span style='font-weight: bold;'>术中用药:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
					continue;
				}
				if(tablekey=="before_modulo_bite"){
					continue;
				}
			}
			
			//节点名称,放在第一td,跳过
			if(tablekey=="nodeid"){
				continue;
			}
			//多选
			//变异创建时间--变异时间位置变更
			if(tablekey=="createtime"){
				variation_table+="<li style='display:none;'><span style='font-weight: bold;'>变异时间:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
				continue;
			}	
			// 隐藏变异节点名称
			if(tablekey=="nodename"){
				variation_table+="<li style='display:none;'><span style='font-weight: bold;'>节点名称:</span>"+takeOutrepeatArr[k][tablekey].nodename+"</li>";
				continue;
			}	
			if(tablekey=="nodelimit"){
				variation_table+="<li style='display:none;'><span style='font-weight: bold;'>节点名称2:</span>"+takeOutrepeatArr[k][tablekey].nodelimit+"</li>";
				continue;
			}	
			//备注
			if(tablekey=="remark"){
				variation_table+="<li><span style='font-weight: bold;'>备注:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
				continue;
			}
			//牙位图
			//植体是否松动：左上
			/*if(tablekey=="extractionleftup_l"){
				variation_table+="<li><span style='font-weight: bold;'>植体是否松动（左上）:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
				continue;
			}
			//植体是否松动：右上
			if(tablekey=="extractionleftup_r"){
				variation_table+="<li><span style='font-weight: bold;'>植体是否松动（右上）:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
				continue;
			}
			//植体是否松动：左下
			if(tablekey=="extractionleftdown_l"){
				variation_table+="<li><span style='font-weight: bold;'>植体是否松动（左下）:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
				continue;
			}
			//植体是否松动：右下
			if(tablekey=="extractionleftdown_r"){
				variation_table+="<li><span style='font-weight: bold;'>植体是否松动（右下）:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
				continue;
			}
			//牙冠是否松动：左上
			if(tablekey=="extractionleftup1_l"){
				variation_table+="<li><span style='font-weight: bold;'>牙冠是否松动（左上）:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
				continue;
			}
			//牙冠是否松动：右上
			if(tablekey=="extractionleftup1_r"){
				variation_table+="<li><span style='font-weight: bold;'>牙冠是否松动（右上）:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
				continue;
			}
			//牙冠是否松动：左下
			if(tablekey=="extractionleftdown1_l"){
				variation_table+="<li><span style='font-weight: bold;'>牙冠是否松动（左下）:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
				continue;
			}
			//牙冠是否松动：右下
			if(tablekey=="extractionleftdown1_r"){
				variation_table+="<li><span style='font-weight: bold;'>牙冠是否松动（右下）:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
				continue;
			}*/
			
			//愈合帽放置
			/*if(tablekey=="healing_cap_station"){
				variation_table+="<li><span style='font-weight: bold;'>愈合帽放置:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
				continue;
			}*/
			//麻醉方式
			/*if(tablekey=="anesthesiamethod"){
				variation_table+="<li><span style='font-weight: bold;'>麻醉方式:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
				continue;
			}*/
			//术中用药
			/*if(tablekey=="intraoperativemedication"){
				variation_table+="<li><span style='font-weight: bold;'>术中用药:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
				continue;
			}*/
			//有无变异
			/*if(tablekey=="is_loose"){
				variation_table+="<li><span style='font-weight: bold;'>有无变异:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
				continue;
			}*/
			
			//判断并截取有序号的值
			//if(takeOutrepeatArr[k][tablekey].indexOf("、")>0){
				//截取序号
				//variation_table+="<li>"+takeOutrepeatArr[k][tablekey].substring(takeOutrepeatArr[k][tablekey].indexOf("、")+1)+"</li>";
			//}else{
				//伤口愈合情况
				/*if(tablekey=="check_wound"){
					variation_table+="<li><span style='font-weight: bold;'>伤口愈合情况:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
					continue;
				}*/
				//口腔清洁情况
				/*if(tablekey=="checkcavitycleansitu"){
					variation_table+="<li><span style='font-weight: bold;'>口腔清洁情况:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
					continue;
				}*/
				//下次来院时间
				/*if(tablekey=="next_hospital_time"){
					variation_table+="<li><span style='font-weight: bold;'>下次来院时间:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
					continue;
				}*/
				//来院时间
				/*if(tablekey=="visit_time"){
					variation_table+="<li><span style='font-weight: bold;'>来院时间:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
					continue;
				}*/
				//备注
				/*if(tablekey=="remark"){
					variation_table+="<li><span style='font-weight: bold;'>备注:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
					continue;
				}*/
				//种植体骨结合情况
				/*if(tablekey=="checkplantbonecombine"){
					variation_table+="<li><span style='font-weight: bold;'>种植体骨结合情况:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
					continue;
				}*/
				//种植体情况
				/*if(tablekey=="checkplantsituation"){
					variation_table+="<li><span style='font-weight: bold;'>种植体情况:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
					continue;
				}*/
				//手术时间
				/*if(tablekey=="operation_time"){
					variation_table+="<li><span style='font-weight: bold;'>手术时间:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
					continue;
				}*/
				//复查时间
				/*if(tablekey=="review_time"){
					variation_table+="<li><span style='font-weight: bold;'>复查时间:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
					continue;
				}*/
				//术区清洁情况
				/*if(tablekey=="observe_wound"){
					variation_table+="<li><span style='font-weight: bold;'>术区清洁情况:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
					continue;
				}*/
				//询问使用满意度
				/*if(tablekey=="askaboutusage"){
					variation_table+="<li><span style='font-weight: bold;'>询问使用满意度:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
					continue;
				}*/
				//二期手术时间
				/*if(tablekey=="twodatestitchestime"){
					variation_table+="<li><span style='font-weight: bold;'>二期手术时间:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
					continue;
				}*/
				//局部连桥
				/*if(tablekey=="localbridge"){
					variation_table+="<li><span style='font-weight: bold;'>局部连桥:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
					continue;
				}*/
				//variation_table+="<li>"+takeOutrepeatArr[k][tablekey]+"</li>";
			//}		
			variation_table+="<li>"+takeOutrepeatArr[k][tablekey]+"</li>";
		} 
		variation_table+="</ul>";
		variation_table+="</div>";
	}
	//   console.log(variation_table);
	document.getElementById("variation_table").innerHTML=variation_table;
}

function getNodeId() {
	$.ajax({
		url: apppath + "/HUDH_FlowAct/findHadWorkByOrderNumberAndNodeId.act",
		type:"POST",
		dataType:"json",
		data: {
			"ordNumber" : ordNumber,
			"nodeId" : nodeId
		},
		success:function(data){
			if (data.retState == "0") {
				seqId = data.selNodeWork.dataId;//获得worklist表中的id
	        } else {
	            /*layer.alert('未达到该节点！' + data.retMsrg, {//后台抛出的异常信息在前台展示
	            	end: function() {
	                	location.reload(); //刷新父页面
		                var frameindex = parent.layer.getFrameIndex(window.name);
		                layer.close(frameindex); //再执行关闭
	                }
	            });*/
	        }
		},
		error: function(data) {
			
		}
	});
}


//获取修复对象中的选中值
function getSelectToothNumb(obje){
	var toothNum = "";
	$(obje).each(function (){
		var $li = $(this).parent('li');
		var $span = $li.find('span');
		if($li.hasClass('current') && $span.hasClass('num')){
			toothNum = toothNum+ ($(this).text()) + ",";
		}
	});
	return toothNum;
}
