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
						<div style="margin-left: 40px;">
							<ul>
								<li><label><input name="Consultation" type="checkbox" value="1、询问既往史" disabled="disabled"/>1、询问既往史及体格检查</label></li>
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
								<li><label><input name="Consultation" type="checkbox" value="3、口内检查" disabled="disabled"/>3、口腔专科检查</label></li>
								<li><label><input name="Consultation" type="checkbox" value="4、会诊：内科、综合科" disabled="disabled"/>4、会诊：内科、综合科</label></li>
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
								<li><label><input name="Consultation" type="checkbox" value="5、向患者交代诊疗过程" disabled="disabled"/>5、向患者交代诊疗过程</label></li>
								<li><label><input name="Consultation" type="checkbox" value="6、拍照" disabled="disabled"/>6、拍照</label></li>
								<li><label><input name="Consultation" type="checkbox" value="7、制作导板" disabled="disabled"/>7、制作导板</label></li>
								<li><label><input name="Consultation" type="checkbox" value="8、办理病历" disabled="disabled"/>8、办理病历</label></li>
							</ul>
						</div>	
					</td>
					<td>
						<div style="margin-left: 40px;">
							<ul>
								<li><label><input name="Consultation" type="checkbox" value="9、签署知情同意书" disabled="disabled"/>9、签署知情同意书</label></li>
								<li><label><input name="Consultation" type="checkbox" value="10、安排或预约手术时间" disabled="disabled"/>10、安排或预约手术时间</label></li>
								<li class="zlCasesLi">
									<label class="zlCasesLiText"><input name="Consultation" type="checkbox" value="制定手术方案和治疗计划" /><font class="diagnosis_case">11、制定手术方案和治疗计划</font></label>
									<div class="caseContiner" style="display:none;">
										<div class="zlCases"></div>
										<div class="selectCases">
											已选方案：<select id="allCases"></select>
											<button style="display:none;" class="caseBtn" onclick="selectCase();">选此方案</button>
										</div>
									</div>
								</li>
								<li><label><input name="Consultation" type="checkbox" value="12、口腔清洁" disabled="disabled"/>12、口腔清洁</label></li>
							</ul>
						</div>
					</td>
					<td>
						<div style="margin-left: 40px;">
							<ul>
								<li><label><input name="Consultation" type="checkbox" value="13、牙周治疗" disabled="disabled"/>13、牙周治疗</label></li>
								<li><label><input name="Consultation" type="checkbox" value="14、检验：血常规、血糖、感染性疾病、凝血4项" disabled="disabled"/>14、检验：血常规、血糖、感染性疾病、凝血4项</label></li>
								<li><label><input name="before_Modulo_bite" id="before_Modulo_bite" type="checkbox" value="15、术前取模、定咬合关系" disabled="disabled"/>15、术前取模、定咬合关系</label></li>
								
								<li><label><input name="Consultation" type="checkbox" value="16、术前讨论" disabled="disabled"/>16、术前讨论</label></li>
							</ul>
						</div>
					</td>
					<td>
						<div style="margin-left: 40px;">
							<ul>
								<li>
									<div class="preparation-ul">
										<span style="font-weight: bold;">17、牙冠材质：</span>
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
    initDiagnosisProject(); //初始化诊疗方案
});

//诊疗方案点击显示隐藏
var toggleVar=true;
$(".diagnosis_case").click(function(){
	if(toggleVar){
		$(this).parents(".zlCasesLi").find(".caseContiner").css("display","block");
		toggleVar=false;
	}else{
		$(this).parents(".zlCasesLi").find(".caseContiner").css("display","none");
		toggleVar=true;
	}
});

//选择方案
function selectCase(){
	var selectCasesId=$("#allCases").val();
	var status=1;
	updateCaseStatus(selectCasesId,status);
	if(alreadySelectCaseId){
		updateCaseStatus(alreadySelectCaseId,"0");
	}
}

function updateCaseStatus(selectCasesId,status){
	var url = contextPath + '/HUDH_ZzblAct/selectedScheme.act';
    var param = {
    		id : selectCasesId,
    		status : status
    };
    //console.log(JSON.stringify(param)+"-------hahahahah");
    $.ajax({
		url: url,
		type:"POST",
		dataType:"json",
		data : param,
		success:function(result){
			layer.alert("选择此方案成功！", {
	            end: function() {
	            	//window.parent.location.reload(); //刷新父页面
	                //var frameindex = parent.layer.getFrameIndex(window.name);
	                //parent.layer.close(frameindex); //再执行关闭
	                window.location.reload();//刷新本页面
	            }
	      	});
		}
  	});
}

//初始化诊疗方案
function initDiagnosisProject(){
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
			if(result.length>=1){
				$(".diagnosis_case").prev().attr("checked","checked").attr("disabled","disabled");
			}
			var caseStyleHtml="";
			var allCasesHtml='<option value="">请选择</option>';
			for (var i =0;i<result.length;i++ ) {
				if(result[i].status=="1"){
					alreadySelectCaseId=result[i].seq_id;
					caseStyleHtml+="<span class='caseStyle' style='border-bottom:2px solid #00a6c0;' onclick='addDProject(this,"+(i+1)+");' id="+result[i].seqId+">方案<label>"+(i+1)+"</label></span>";
					allCasesHtml+='<option selected value='+result[i].seqId+'>方案'+(i+1)+'</option>';
				}else{
					caseStyleHtml+="<span class='caseStyle' onclick='addDProject(this,"+(i+1)+");' id="+result[i].seqId+">方案<label>"+(i+1)+"</label></span>";
					allCasesHtml+='<option value='+result[i].seqId+'>方案'+(i+1)+'</option>';
				}
			}
			//caseStyleHtml+="<span class='caseStyle addCaseBtn' onclick='addDProject(this);' id=''>新增<label>+</label></span>";
			$(".zlCases").html(caseStyleHtml);
			$("#allCases").html(allCasesHtml);
		}
  });
}

/* 诊疗添加与展示 */
function addDProject(thi,num){
	var nameNum="";
	if(num){
		nameNum=num;
	}
	var seqid=$(thi).attr("id");
	parent.layer.open({
		title:"诊疗方案"+nameNum,
		type:2,
		closeBtn:1,
		content:contextPath + "/ZzblViewAct/toZzllDiagnosisProject.act?seqidFather="+seqid,
		area:['100%','90%'],
		cancel: function(){
			/* console.log("点击了右上角关闭按钮！"); */     
		},
  		end:function(){
  			window.location.reload();//刷新本页面
  		}
	});
}


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
			$('textarea').attr("readonly","readonly");
//			alert(JSON.stringify(result));
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
			console.log(select_imagelogic);
			var disease_id = select_imagelogic.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='imagelogic']").each(function(){
					if($(this).val()==disease_id[i]){
					   $(this).attr("checked","checked");
					}
				})
			}
			
			//术前取模、定咬合关系
			before_Modulo_bite = data.before_Modulo_bite;
			if (before_Modulo_bite) {
				$("#before_Modulo_bite").attr("checked","checked");
			}
			
			//基台放置赋值
			var abutment_station = patientObj.abutment_station;
			console.log(abutment_station+"---------基台放置赋值");
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
			console.log(tooth_texture+"-----------牙冠材质赋值");
			var disease_id = tooth_texture.split(";");
			for(var i = 0; i < disease_id.length; i++){
				$("input[name='tooth_texture']").each(function(){
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