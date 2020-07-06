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
	/* 诊疗方案 */
	.caseContiner{
	    width:60%;
		border: 1px solid red;
	    position: absolute;
	    top: 20px;
	    left: 30px;
	    background-color: white;
	    border: 1px solid #00A6C0;
	    border-radius: 5px;
	    padding: 2px;
	}
	.zlCases{
		overflow: hidden;
	}
	.zlCases .caseStyle{
		/* width:50%; */
		float: left;
		margin-left: 6%;
		box-sizing: border-box;
		margin-bottom: 4px;
    	height: 20px;
	}
	.zlCases .caseStyle label{
		display: inline-block;
	    border: 1px solid #00A6C0;
	    width: 14px;
	    height: 14px;
	    line-height: 13px;
	    vertical-align: middle;
	    text-align: center;
	    border-radius: 50%;
	    margin-left: 2px;
	    font-size: 12px;
	    font-weight: normal;
	}
	/* 诊疗计划 */
	.zlCasesLi{
		position: relative;
	}
	.zlCasesLi .zlCases{
		padding-top: 2px;
	    background-color: white;
	    color: #00A6C0;
	}
	.selectCases{
		margin-top: 5px;
	}
	#allCases{
		width: 85px;
	    line-height: 20px;
	    height: 22px;
	}
	.caseBtn{
		margin-left: 3px;
	    background-color: #00A6C0;
	    color: white;
	    border: 1px solid #00A6C0;
	    width: 62px;
	    line-height: 20px;
	    border-radius: 5px;
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
						<span class="commonText">术前准备：</span>
				    </td>
					<td>
						<div style="margin-left: 68px;">
							<ul>
								<li><label><input name="Consultation" type="checkbox" value="1、询问既往史" disabled="disabled" id="Consultation"/>1、询问既往史</label></li>
								<li><label><input name="Consultation" type="checkbox" value="2、口内检查" disabled="disabled" id="Consultation"/>2、口内检查</label></li>
								<li><label><input name="Consultation" type="checkbox" value="3、术前讨论" disabled="disabled" id="Consultation"/>3、术前讨论</label></li>
							</ul>
						</div>
					</td>
					<td>
						<div style="margin-left: 68px;">
							<ul>
								<li class="positionLi zlCasesLi">
									<label class="zlCasesLiText"><input name="Consultation" type="checkbox" value="制定手术方案和治疗计划" /><font class="diagnosis_case" onclick="showHiddenClick(this,'zlCasesLi');">4、制定手术方案和治疗计划</font></label>
									<div class="caseContiner" style="display:none;">
										<div class="zlCases"></div>
										<div class="selectCases">
											<select id="allCases"></select>
											<button class="caseBtn" onclick="selectCase('zlCasesLi',3);">选此方案</button>
										</div>
									</div>
								</li>
								<li><label><input name="Consultation" type="checkbox" value="5、向患者交代诊疗过程" disabled="disabled" id="Consultation"/>5、向患者交代诊疗过程</label></li>
								<li><label><input name="Consultation" type="checkbox" value="6、拍照及选择照片" disabled="disabled" id="Consultation"/>6、拍照及选择照片</label></li>
							</ul>
						</div>	
					</td>
							
					<td>
						<div style="margin-left: 68px;">
							<ul>
								<li><label><input name="Consultation" type="checkbox" value="7、办理病历" disabled="disabled" id="Consultation"/>7、办理病历</label></li>
								<li><label><input name="Consultation" type="checkbox" value="8、签署知情同意书" disabled="disabled" id="Consultation"/>8、签署知情同意书</label></li>
								<li><label><input name="Consultation" type="checkbox" value="9、检验：血常规、血糖、感染性疾病、凝血4项" disabled="disabled" id="Consultation"/>9、检验：血常规、血糖、感染性疾病、凝血4项</label></li>
							</ul>
						</div>
					</td>
					
					<td>
						<div style="margin-left: 68px;">
							<ul>
								<li><label><input name="Consultation" type="checkbox" value="10、影像学检查：拍全景片、CT、小牙片" disabled="disabled" id="Consultation"/>10、影像学检查：拍全景片、CT、小牙片</label></li>
								<li><label><input name="Consultation" type="checkbox" value="11、会诊：相对禁忌症、80岁以上患者" disabled="disabled" id="Consultation"/>11、会诊：相对禁忌症、80岁以上患者</label></li>
								<li><label><input name="Consultation" type="checkbox" value="12、口腔清洁、牙周治疗" disabled="disabled" id="Consultation"/>12、口腔清洁、牙周治疗</label></li>
							</ul>
						</div>	
					</td>
					
					<td>
						<div style="margin-left: 68px;">
							<ul>
								<li><label><input name="Consultation" type="checkbox" value="13、安排或预约手术时间" disabled="disabled" id="Consultation"/>13、安排或预约手术时间</label></li>
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
var alreadySelectZSBSId=""; //已经有选择的主诉及既往病史seq_id 页面初始化时判断状态并赋值
var alreadySelectJCZDId=""; //已经有选择的检查及诊断seq_id 页面初始化时判断状态并赋值
var alreadySelectZLFAId=""; //已经有选择的诊疗方案seq_id 页面初始化时判断状态并赋值
var alreadySelectXFFAId=""; //已经有选择的修复方案seq_id 页面初始化时判断状态并赋值
var consultAddBtn=false; //判断此页面是否为咨询填写,多方案是否加新增按钮
var alreadySelectZSBSMark="";  //记录上一次选中的主诉即既往病史是新病历还是老病历
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

	var anamnesisUrl = contextPath + '/HUDH_ZzblAskAct/findCaseHistoryById.act';
	initCaseHistory(anamnesisUrl,1);  //初始化主诉及既往病史，默认先展示老病历
    //initCaseHistory();  //初始化主诉及既往病史
    initZzblOpration(); //初始化检查及诊断
    initDiagnosisProject(); //初始化诊疗方案
    initRepairProject(); //初始化修复方案
	initSelectList("jwsLi",1); //初始化既往病史下拉框
	initSelectList("jczdLi",2); //检查即诊断下拉框
	initSelectList("zlCasesLi",3); //诊疗方案下拉框
	initSelectList("xffaLi",4); //修复方案下拉框
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
			patientObj = eval(result);
			//专家会诊赋值
			var select_Consultation = patientObj.consultation;
			var disease_id = select_Consultation.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='Consultation']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
		}
	});
}
</script>
</html>