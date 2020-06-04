<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
	String usercode = request.getParameter("usercode");
	if(usercode == null ){
	 usercode = "";  // 配合js 的非空判断，这里不这样写，则js 需进行  usercode != "null"的判断
	}
	//网电预约Id 挂号时选择的时网电预约记录，挂号后把该预约设为已上门
	String netorderid = request.getParameter("netorderid");
	if(netorderid == null ){
	 netorderid = "";  
	}
	String orderno = request.getParameter("orderno");
	if(orderno == null ){
		orderno = "";  
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/tableExport.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<style type="text/css">
	.a{
		border-radius: 6px;
	}
	.b{
		z-index:100000;
	}
	input{
		vertical-align: text-top;
	}
	.preparation-ul label{
		margin-bottom: 10px;
		margin-right: 5px;
	}
	table li{
		margin-top: 10px;
	}
</style>
</head>
<body>
	<div style="margin-top: 5px;">
	<ul>
		<table align="center">
			<tbody>
				<tr>
					<td>
						<div style="margin-left: 40px;">
							<ul>
								<!-- <li><label><input name="Consultation" type="checkbox" value="询问既往史" disabled="disabled"/><font class="ask_Previous">1、询问既往史及体格检查</font></label></li> -->
								<li class="positionLi jwsLi">
									<label><input name="Consultation" type="checkbox" value="询问既往史" disabled="disabled"/><font class="ask_Previous" onclick="showHiddenClick(this,'jwsLi');">1、询问既往史及体格检查</font></label>
									<div class="caseContiner" style="display:none;">
										<div class="zlCases"></div>
										<div class="selectCases">
											<select id="allCases"></select>
											<button class="caseBtn" onclick="selectCase('jwsLi',1);">选此病史</button>
										</div>
									</div>
								</li>
								<li>
										<span style="font-weight: bold;" class="">2、影像学检查：</span>
										<label><input name="imagelogic" type="checkbox" value="1、心电图" disabled="disabled">1、心电图</label>
										<label><input name="imagelogic" type="checkbox" value="2、骨密度" disabled="disabled">2、骨密度</label>
										<label><input name="imagelogic" type="checkbox" value="3、B超" disabled="disabled">3、B超</label><br/>
										<label><input name="imagelogic" type="checkbox" value="4、心脏彩超" disabled="disabled">4、心脏彩超</label>
										<label><input name="imagelogic" type="checkbox" value="5、全景片" disabled="disabled">5、全景片</label>
										<label><input name="imagelogic" type="checkbox" value="6、CT" disabled="disabled">6、CT</label>
										<label><input name="imagelogic" type="checkbox" value="7、小牙片" disabled="disabled">7、小牙片</label>
								</li>
								<!-- <li><label><input name="Consultation" type="checkbox" value="口内检查" disabled="disabled"/><font class="examine_diagnose">3、口腔专科检查</font></label></li> -->
								<li class="positionLi jczdLi">
									<label><input name="Consultation" type="checkbox" value="口内检查" disabled="disabled"/><font class="examine_diagnose" onclick="showHiddenClick(this,'jczdLi');">3、口腔专科检查</font></label>
									<div class="caseContiner" style="display:none;">
										<div class="zlCases"></div>
										<div class="selectCases">
											<select id="allCases"></select>
											<button class="caseBtn" onclick="selectCase('jczdLi',2);">选此诊断</button>
										</div>
									</div>
								</li>
								<li><label><input name="Consultation" type="checkbox" value="会诊：内科、综合科" disabled="disabled"/>4、会诊：内科、综合科</label></li>
							</ul>
						</div>	
					</td>
				
					<!-- 
					<td>
						<span class="commonText">咨询：</span>
					</td> -->
					<td>
						<div style="margin-left: 40px;">
							<ul>
								<li><label><input name="Consultation" type="checkbox" value="向患者交代诊疗过程" disabled="disabled"/><font class="">5、向患者交代诊疗过程</font></label></li>
								<li><label><input name="Consultation" type="checkbox" value="拍照" disabled="disabled"/>6、拍照</label></li>
								<li><label><input name="Consultation" type="checkbox" value="制作导板" disabled="disabled"/>7、制作导板</label></li>
								<li><label><input name="Consultation" type="checkbox" value="办理病历" disabled="disabled"/>8、办理病历</label></li>
								<!-- <li><label><input name="Consultation" id="" type="checkbox" value="修复方案"/><font class="xiufu_test">9、修复方案</font></label></li> -->
								<li class="positionLi xffaLi">
									<label><input name="Consultation" id="" type="checkbox" value="修复方案"/><font class="xiufu_test" onclick="showHiddenClick(this,'xffaLi');">9、修复方案</font></label>
									<div class="caseContiner" style="display:none;">
										<div class="zlCases"></div>
										<div class="selectCases">
											<select id="allCases"></select>
											<button class="caseBtn" onclick="selectCase('xffaLi',4);">选此方案</button>
										</div>
									</div>
								</li>
							</ul>
						</div>	
					</td>
					<td>
						<div style="margin-left: 40px;">
							<ul>
								<li><label><input name="Consultation" type="checkbox" value="签署知情同意书" disabled="disabled"/><font class="plantKnowbook">10、签署知情同意书</font></label></li>
								<li><label><input name="Consultation" type="checkbox" value="安排或预约手术时间" disabled="disabled"/>11、安排或预约手术时间</label></li>
								<li class="positionLi zlCasesLi">
									<label class="zlCasesLiText"><input name="Consultation" type="checkbox" value="制定手术方案和治疗计划" /><font class="diagnosis_case" onclick="showHiddenClick(this,'zlCasesLi');">12、制定手术方案和治疗计划</font></label>
									<div class="caseContiner" style="display:none;">
										<div class="zlCases"></div>
										<div class="selectCases">
											<select id="allCases"></select>
											<button class="caseBtn" onclick="selectCase('zlCasesLi',3);">选此方案</button>
										</div>
									</div>
								</li>
								<li><label><input name="Consultation" type="checkbox" value="口腔清洁" disabled="disabled"/>13、口腔清洁</label></li>
							</ul>
						</div>
					</td>
					<td>
						<div style="margin-left: 40px;">
							<ul>
								<li><label><input name="Consultation" type="checkbox" value="牙周治疗" disabled="disabled"/>14、牙周治疗</label></li>
								<li><label><input name="Consultation" type="checkbox" value="检验：血常规、血糖、感染性疾病、凝血4项" disabled="disabled"/>15、检验：血常规、血糖、感染性疾病、凝血4项</label></li>
								<li><label><input name="Consultation" id="before_Modulo_bite" type="checkbox" value="术前取模、定咬合关系" disabled="disabled"/>16、术前取模、定咬合关系</label></li>
								
								<li><label><input name="Consultation" type="checkbox" value="术前讨论" disabled="disabled"/>17、术前讨论</label></li>
							</ul>
						</div>
					</td>
					<td>
						<div style="margin-left: 40px;">
							<ul>
								<li>
									<div class="preparation-ul">
										<span style="font-weight: bold;">18、牙冠材质：</span>
										<label><input name="tooth_texture" type="checkbox" value="1、局部-德国GEGO牙冠" >1、局部-德国GEGO牙冠</label>
										<label><input name="tooth_texture" type="checkbox" value="2、局部-氧化锆牙冠" >2、局部-氧化锆牙冠</label><br/>
										<label><input name="tooth_texture" type="checkbox" value="3、局部-德国Weiland牙冠" >3、局部-德国Weiland牙冠</label>
										<label><input name="tooth_texture" type="checkbox" value="4、局部-泽康全瓷牙冠" >4、局部-泽康全瓷牙冠</label><br/>
										<label><input name="tooth_texture" type="checkbox" value="5、局部-美国Lava牙冠" >5、局部-美国Lava牙冠</label>
										<label><input name="tooth_texture" type="checkbox" value="6、局部-瑞典Procera牙冠" >6、局部-瑞典Procera牙冠</label><br/>
										<label><input name="tooth_texture" type="checkbox" value="7、半口-可摘型德国GEGO牙桥" >7、半口-可摘型德国GEGO牙桥</label>
										<label><input name="tooth_texture" type="checkbox" value="8、半口-可摘型维他灵牙桥" >8、半口-可摘型维他灵牙桥</label><br/>
										<label><input name="tooth_texture" type="checkbox" value="9、半口-可摘型纯钛牙桥" >9、半口-可摘型纯钛牙桥</label>
										<label><input name="tooth_texture" type="checkbox" value="10、半口-固定版树脂牙桥" >10、半口-固定版树脂牙桥</label><br/>
										<label><input name="tooth_texture" type="checkbox" value="11、半口-固定版氧化锆牙桥" >11、半口-固定版氧化锆牙桥</label>
									</div>
								</li>
							</ul>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</ul>
	</div>
</body>
<script type="text/javascript">
var orderNumber = parent.orderNumber;
var contextPath = '<%=contextPath%>';
var id = parent.patientObj.id;
var order_number= window.parent.patientObj.orderNumber;//选中患者order_number
var username=parent.patientObj.username; //患者姓名
var sex=parent.patientObj.sex; //患者性别
var age=parent.patientObj.age; //患者年龄
var alreadySelectZSBSId=""; //已经有选择的主诉及既往病史seq_id 页面初始化时判断状态并赋值
var alreadySelectJCZDId=""; //已经有选择的检查及诊断seq_id 页面初始化时判断状态并赋值
var alreadySelectZLFAId=""; //已经有选择的诊疗方案seq_id 页面初始化时判断状态并赋值
var alreadySelectXFFAId=""; //已经有选择的修复方案seq_id 页面初始化时判断状态并赋值
var consultAddBtn=false; //判断此页面是否为咨询填写,多方案是否加新增按钮
$(function(){
	//时间选择
    $("#visit_time").datetimepicker({
        language:  'zh-CN',  
   		minView:2,
        format: 'yyyy-mm-dd',
   		autoclose : true,//选中之后自动隐藏日期选择框   
   		pickerPosition: "bottom-right"
    });
	
    initFlow();
    checkOptions();//判断要填写的选项是否已填写并选中
    
    initCaseHistory();  //初始化主诉及既往病史
    initZzblOpration(); //初始化检查及诊断
    initDiagnosisProject(); //初始化诊疗方案
    initRepairProject(); //初始化修复方案
});

//判断要填写的选项是否已填写并选中
function checkOptions(){
	//console.log(id+"---------------"+order_number);
	/* 判断知情同意书*/
	var plantKnowbookurl = contextPath + '/HUDH_ZzblAskAct/findFamiliarBookById.act';
	$.ajax({
		url: plantKnowbookurl,
		type:"POST",
		dataType:"json",
		data : {
			 id :  id, //临床路径ID
			 order_number : order_number
		},
		success:function(result){
			//console.log(JSON.stringify(result)+"--------------添加成功后查询数据");
			/* 判断是否已经填写过内容 */
			if(result.seqId){
				$(".plantKnowbook").prev().attr("checked","checked").attr("disabled","disabled");
			} 
		}
  });
	/* 判断拔牙手术知情同意书*/
	var pulloutKnowbookurl = contextPath + '/HUDH_ZzblAskAct/findLocatorFamiliar.act';
	$.ajax({
		url: pulloutKnowbookurl,
		type:"POST",
		dataType:"json",
		data : {
			 id :  id, //临床路径ID
			 order_number : order_number,
			 classify:1
		},
		success:function(result){
			//console.log(JSON.stringify(result)+"--------------添加成功后查询数据");
			/* 判断是否已经填写过内容 */
			if(result.seqId){
				$(".pulloutKnowbook").prev().attr("checked","checked").attr("disabled","disabled");
			} 
		}
  });
	/* 判断LOCATOR覆盖义齿即刻负重(即刻用)知情同意书*/
	var locatorKnowbookurl = contextPath + '/HUDH_ZzblAskAct/findLocatorFamiliar.act';
	$.ajax({
		url: locatorKnowbookurl,
		type:"POST",
		dataType:"json",
		data : {
			 id :  id, //临床路径ID
			 order_number : order_number,
			 classify:0
		},
		success:function(result){
			//console.log(JSON.stringify(result)+"--------------添加成功后查询数据");
			/* 判断是否已经填写过内容 */
			if(result.seqId){
				$(".locatorKnowbook").prev().attr("checked","checked").attr("disabled","disabled");
			} 
		}
  });
}

var userAgent = navigator.userAgent;
//签署知情同意书     ZzblViewAct.java
$(".plantKnowbook").click(function(){
	parent.layer.open({
		title:"签署知情同意书",
		type:2,
		closeBtn:1,
		content:contextPath + "/ZzblViewAct/toZzllPlantKnowbook.act",
		area:userAgent.indexOf("iPad") > -1 ? ['100%','95%'] : ['80%','80%'],
		cancel: function(){ 
			/* console.log("点击了右上角关闭按钮！"); */     
		}
	});
	
});
//拔牙手术知情同意书     ZzblViewAct.java
$(".pulloutKnowbook").click(function(){
	parent.layer.open({
		title:"拔牙手术知情同意书",
		type:2,
		closeBtn:1,
		content:contextPath + "/ZzblViewAct/topulloutKnowbookInfor.act",
		area:userAgent.indexOf("iPad") > -1 ? ['100%','95%'] : ['80%','80%'],
		cancel: function(){ 
		},
		end:function(){
			window.location.reload();//刷新本页面
		}
	});
	
});
//locator覆盖义齿即刻负重（即刻用）知情同意书     ZzblViewAct.java
$(".locatorKnowbook").click(function(){
	parent.layer.open({
		title:"LOCATOR覆盖义齿即刻负重（即刻用）知情同意书",
		type:2,
		closeBtn:1,
		content:contextPath + "/ZzblViewAct/tolocatorKnowbookInfor.act",
		area:userAgent.indexOf("iPad") > -1 ? ['100%','95%'] : ['80%','80%'],
		area:['80%','80%'],
		cancel: function(){
		}
	}); 
}); 

//初始化流程
function initFlow() {
	$.ajax({
		url: contextPath + "/HUDH_FlowAct/findOrderTrackInfo.act?orderNumber=" + orderNumber,
		type:"POST",
		dataType:"json",
		async:false,
		success:function(result){
			$('input').attr("disabled",true);
			$('select').attr("disabled",true);
			$(".positionLi").each(function(i,obj){
				$(this).find("#allCases").removeAttr("disabled");
			});
			$('textarea').attr("readonly","readonly");
			//console.log(JSON.stringify(result)+"------------hahhah");
			patientObj = eval(result);
			//专家会诊赋值
			var select_Consultation = patientObj.consultation;
			/* console.log(select_Consultation); */
			var disease_id = select_Consultation.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='Consultation']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			//影像学检查赋值
			var select_imagelogic = patientObj.imageologicalExamination;
			//console.log(select_imagelogic);
			var disease_id = select_imagelogic.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='imagelogic']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			
			//术前取模、定咬合关系
			before_Modulo_bite = patientObj.before_Modulo_bite;
			if (before_Modulo_bite) {
				$("#before_Modulo_bite").attr("checked","checked");
			}
			
			//基台放置赋值
			var abutment_station = patientObj.abutment_station;
			//console.log(abutment_station+"---------基台放置赋值");
			var disease_id = abutment_station.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='abutment_station']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			
			//牙冠材质赋值
			var tooth_texture = patientObj.tooth_texture;
			//console.log(tooth_texture+"-----------牙冠材质赋值");
			var disease_id = tooth_texture.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='tooth_texture']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					   $(this).attr("disabled","disabled");
					}
				})
			}
			
			
		}
	});
}
</script>
</html>