<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<style type="text/css" rel="stylesheel">
	/* 按钮 */
	.kqdsSearchBtn {
	    display: inline-block;
	    box-sizing: border-box;
	    height: 28px;
	    line-height: 28px;
	    background: #00a6c0;
	    color: #fff;
	    outline: none;
	    padding: 0 15px;
	    border: 1px solid #00a6c0;
	    border-radius: 3px;
	    margin-right: 3px;
	    text-decoration: none;
	    cursor: pointer;
	    text-align: center;
	}
	input{
		vertical-align: sub;
	}
	li{
		margin-bottom: 8px;
	}
	/* 牙冠材质 */
	.tooth_texture{
		display: flex;
		flex-direction: row;
		font-weight: bold;
	}
	/*选项div*/
	.tooth_texture>.options{
		position: relative;
	}
	/*选项标题*/
	.tooth_texture>.options>.options_title{
		margin-left: 20px;
	}
	/*选项*/
	.tooth_texture>.options>.options_list{
	    list-style-type:none;
	    padding: 0px;
	    display: none;
	    width: 260px;
	    position: absolute;
	    top:15px;
	    left:30px;
	}
	.tooth_texture>.options>.options_list li{
		margin-bottom: 0px;
	}
	/* 2019/7/10 lutian 滚动字体样式  */
	marquee{
		color:red;
		font-weight: bold;
		font-size: 16px;
		letter-spacing: 1px;
	}
	td
	{
 	white-space: nowrap;
	}
	/* 测试按钮样式  lutian 2020/05/29 */
	.btnTest{
		border:1px solid blue;
		padding: 20px;
	}
	.btnTest button{
		border: 1px solid black;
	    background-color: transparent;
	    color: black;
	    font-size: 16px;
	    padding: 4px 10px;
	    border-radius: 5px;
	}
	/* --------------------------- */
	/*必填*/
	.mustIn{
		color: red;
		font-size: 16px;
		margin-right: 2px;
	}
</style>
</head>
<body>
	<div>
		<!-- 测试按钮 lutian 2020/05/29  -->
		<div class="btnTest" style="display: none;">
			<button class="principleBtnGB">主诉改版</button>
			<button class="operationRecord">种植牙手术记录</button>
			<button class="repairRecord">修复治疗记录</button>
			<button class="postoperationItem">种植牙术后注意事项</button>
		</div>
		<!-- -------------------------- -->
		<table align="center"  id="operationBefore_form" style="width:100%;margin:70px auto 15px;">
			<tbody>
				<tr>
					<td style="padding-left: 5px;width: 70px;">术前准备：</td>
					<td style="vertical-align: top;width:210px;">
						<div style="margin-left: 30px;vertical-align: top;">
							<ul>
								<li class="positionLi jwsLi">
									<label><input name="Consultation" type="checkbox" disabled="disabled" value="询问既往史" /><font class="mustIn">*</font><font class="ask_Previous" onclick="showHiddenClick(this,'jwsLi');">1、询问既往史及体格检查</font></label>
									<div class="caseContiner" style="display:none;">
										<button class="btnStyle" onclick="toggleCase(this,1,1);">切换新版</button>
										<div class="zlCases"></div>
										<div class="selectCases">
											<select id="allCases"></select>
											<button class="caseBtn" onclick="selectCase('jwsLi',1);">选此病史</button>
										</div>
									</div>
									<br/>
<%--									<label><input name="plantRecords" type="checkbox" value="种植病历"><font class="plantRecords">新种植病历</font></label>--%>
								</li>
								<li class="positionLi jczdLi">
									<label><input name="Consultation" type="checkbox" disabled="disabled" value="口内检查" /><font class="mustIn">*</font><font class="examine_diagnose" onclick="showHiddenClick(this,'jczdLi');">2、口腔专科检查</font></label>
									<div class="caseContiner" style="display:none;">
										<div class="zlCases"></div>
										<div class="selectCases">
											<select id="allCases"></select>
											<button class="caseBtn" onclick="selectCase('jczdLi',2);">选此诊断</button>
										</div>
									</div>
								</li>
								<li><label><input name="Consultation" type="checkbox" value="术前讨论" />3、术前讨论</label></li>
								<li class="positionLi zlCasesLi">
									<label class="zlCasesLiText"><input name="Consultation" type="checkbox" disabled="disabled" value="制定手术方案和治疗计划" /><font class="mustIn">*</font><font class="diagnosis_case" onclick="showHiddenClick(this,'zlCasesLi');">4、制定手术方案和治疗计划</font></label>
									<div class="caseContiner" style="display:none;">
										<div class="zlCases"></div>
										<div class="selectCases">
											<select id="allCases"></select>
											<button class="caseBtn" onclick="selectCase('zlCasesLi',3);">选此方案</button>
										</div>
									</div>
								</li>
								<li><label><input name="Consultation" type="checkbox" value="向患者交代诊疗过程" />5、向患者交代诊疗过程</label></li>
								<li><label><input name="Consultation" type="checkbox" value="拍照" />6、拍照</label></li>
							</ul>
						</div>
					</td>
					<td style="vertical-align: top;width:200px;">
						<div style="margin-left: 30px;vertical-align: top;">
							<ul>
								<li><label><input name="Consultation" type="checkbox" value="制作导板" />7、制作导板</label></li>
								<li><label><input name="Consultation" type="checkbox" value="办理病历" />8、办理病历</label></li>
								<!-- <li><label><input name="Consultation" type="checkbox" value="签署知情同意书" /><font class="plantKnowbook">9、签署知情同意书</font></label></li> -->
								<li>
									<span style="font-weight: bold;" class=""><font class="mustIn">*</font>9、同意书：</span>
									<div>
										<label><input name="plantKnowbook" type="checkbox" value="人工种植牙知情同意书"><font class="plantKnowbook">人工种植牙知情同意书</font></label>
									</div>
									<div>
										<label><input name="plantKnowbook" type="checkbox" value="拔牙手术知情同意书"><font class="pulloutKnowbook">拔牙手术知情同意书</font></label>
									</div>
									<div>
										<label><input name="plantKnowbook" type="checkbox" value="LOCATOR覆盖义齿即刻负重(即刻用)知情同意书"><font class="locatorKnowbook">LOCATOR覆盖义齿即刻负重(即刻用)知情同意书</font></label><br/>
									</div>

								</li>
							</ul>
						</div>
					</td>
					<td style="vertical-align: top;width:270px;">
						<div style="margin-left: 30px;">
							<ul>
								<li><label>10、<input name="Consultation" type="checkbox" value="安排或预约手术时间" />安排或预约手术时间</label></li>
								<li>
									<!-- <label><input name="Consultation" type="checkbox" value="会诊：内科、综合科" />11、会诊：内科、综合科</label> -->
									<span style="font-weight: bold;" class="">11、会诊：</span>
									<label><input name="Advisory" type="checkbox" value="内科">内科</label>
									<label><input name="Advisory" type="checkbox" value="综合科">综合科</label>
									<label><input name="Advisory" type="checkbox" value="修复医生">修复医生</label><br/>
								</li>
								<li><label>12、<input name="Consultation" type="checkbox" value="口腔清洁" />口腔清洁</label></li>
								<li><label>13、<input name="Consultation" type="checkbox" value="牙周治疗" />牙周治疗</label></li>
								<li><label>14、<input name="Consultation" type="checkbox" value="检验：血常规、血糖、感染性疾病、凝血4项" />检验：血常规、血糖、感染性疾病、凝血4项</label></li>
								<!-- <li><label><input name="before_Modulo_bite" id="before_Modulo_bite" type="checkbox" value="15、术前取模、定咬合关系"/>15、术前取模、定咬合关系</label></li> -->
								<%--<li><label>222、<input name="Consultation" type="checkbox" value="人工种植牙知情同意书" /><font class="dentalImplant">人工种植牙知情同意书</font></label></li>--%>
							</ul>
						</div>
					</td>
					<td style="vertical-align: top;width:400px;">
						<div style="margin-left: 30px;">
							<ul>
								<li><label>15、<input name="Consultation" type="checkbox" value="术前取模、定咬合关系"/>术前取模、定咬合关系</label></li>
								<li><label>16、<input name="Consultation" type="checkbox" value="取研究模型，行模型分析"/>取研究模型，行模型分析</label></li>
								<li>
									<div class="preparation-ul">
										<span style="font-weight: bold;"><font class="mustIn">*</font>17、影像学检查：</span>
										<label><input name="imagelogic" type="checkbox" value="1、全景片">(1)、全景片</label>
										<label><input name="imagelogic" type="checkbox" value="2、CT">(2)、CT</label>
										<label><input name="imagelogic" type="checkbox" value="3、小牙片">(3)、小牙片</label><br/>
										<label><input name="imagelogic" type="checkbox" value="4、心电图">(4)、心电图</label>
										<label><input name="imagelogic" type="checkbox" value="5、骨密度">(5)、骨密度</label>
										<label><input name="imagelogic" type="checkbox" value="6、心脏彩超">(6)、心脏彩超</label>
										<label><input name="imagelogic" type="checkbox" value="7、B超">(7)、B超</label>
									</div>
								</li>
								<li class="positionLi xffaLi">
									<label><font class="mustIn">*</font>18、<input name="Consultation" id="" type="checkbox" disabled="disabled" value="修复方案"/><font class="xiufu_test" onclick="showHiddenClick(this,'xffaLi');">修复方案</font></label>
									<div class="caseContiner" style="display:none;">
										<div class="zlCases"></div>
										<div class="selectCases">
											<select id="allCases"></select>
											<button class="caseBtn" onclick="selectCase('xffaLi',4);">选此方案</button>
										</div>
									</div>
								</li>
							   <%--<li><label><input name="Consultation" type="checkbox" value="告知通知书" /><font class="inform">19、告知通知书</font></label></li> --%>
<%--							    <li><label><input name="Consultation" type="checkbox" value="诊疗方案" /><font class="case">20、诊疗方案</font></label></li>--%>
							</ul>
						</div>
					</td>
					<td style="">
						<!-- <div style="margin-left: 30px;border:1px solid red">
							 <ul>
								<li>
									<div class="preparation-ul">
										<span style="font-weight: bold;">19、牙冠材质：</span>
										<label><input name="tooth_texture" type="checkbox" value="局部-德国GEGO牙冠" >(1)、局部-德国GEGO牙冠</label>
										<label><input name="tooth_texture" type="checkbox" value="局部-氧化锆牙冠" >(2)、局部-氧化锆牙冠</label><br/>
										<label><input name="tooth_texture" type="checkbox" value="局部-德国Weiland牙冠" >(3)、局部-德国Weiland牙冠</label>
										<label><input name="tooth_texture" type="checkbox" value="局部-泽康全瓷牙冠" >(4)、局部-泽康全瓷牙冠</label><br/>
										<label><input name="tooth_texture" type="checkbox" value="局部-美国Lava牙冠" >(5)、局部-美国Lava牙冠</label>
										<label><input name="tooth_texture" type="checkbox" value="局部-瑞典Procera牙冠" >(6)、局部-瑞典Procera牙冠</label><br/>
										<label><input name="tooth_texture" type="checkbox" value="半口-可摘型德国GEGO牙桥" >(7)、半口-可摘型德国GEGO牙桥</label>
										<label><input name="tooth_texture" type="checkbox" value="半口-可摘型维他灵牙桥" >(8)、半口-可摘型维他灵牙桥</label><br/>
										<label><input name="tooth_texture" type="checkbox" value="半口-可摘型纯钛牙桥" >(9)、半口-可摘型纯钛牙桥</label>
										<label><input name="tooth_texture" type="checkbox" value="半口-固定版树脂牙桥" >(10)、半口-固定版树脂牙桥</label><br/>
										<label><input name="tooth_texture" type="checkbox" value="半口-固定版氧化锆牙桥" >(11)、半口-固定版氧化锆牙桥</label>
									</div>
								</li>
							</ul>
						</div> -->
						<div class='tooth_texture' style="margin-bottom: 135px;">
					    	<span><font class="mustIn">*</font>19、牙冠材质:</span>
					        <div class="options">
					            <div class='options_title'>
					                <input id="texture_topo" type="checkbox" class='options_titleText'><label for="texture_topo">局部</label>
					            </div>
					            <ul class='options_list'>
					            	<li>
						                <input id="topo_gego" name="tooth_texture" type="checkbox" value="局部-德国GEGO牙冠"><label for="topo_gego">(1)、局部-德国GEGO牙冠</label>
					                </li>
					                <li>
						                <input id="topo_zirconia" name="tooth_texture" type="checkbox" value="局部-氧化锆牙冠"><label for="topo_zirconia">(2)、局部-氧化锆牙冠</label>
					                </li>
					                <li>
					                	<input id="topo_weiland" name="tooth_texture" type="checkbox" value="局部-德国Weiland牙冠"><label for="topo_weiland">(3)、局部-德国Weiland牙冠</label>
					            	</li>
					            	<li>
						                <input id="topo_allCeramic" name="tooth_texture" type="checkbox" value="局部-泽康全瓷牙冠"><label for="topo_allCeramic">(4)、局部-泽康全瓷牙冠</label>
					                </li>
					                <li>
						                <input id="topo_lava" name="tooth_texture" type="checkbox" value="局部-美国Lava牙冠"><label for="topo_lava">(5)、局部-美国Lava牙冠</label>
					                </li>
					                <li>
					                	<input id="topo_procera" name="tooth_texture" type="checkbox" value="局部-瑞典Procera牙冠"><label for="topo_procera">(6)、局部-瑞典Procera牙冠</label>
					            	</li>
					            	<li>
					                	<input id="topo_pro" name="tooth_texture" type="checkbox" value="局部-塑钢牙pvc"><label for="topo_pro">(7)、局部-塑钢牙pvc</label>
					            	</li>
									<li>
										<input id="topo_abutment" name="tooth_texture" type="checkbox" value="局部-塑钢牙pvc"><label for="topo_abutment">(8)、局部-纯钛基台一体冠</label>
									</li>
					            </ul>
					        </div>
					        <div class="options">
					            <div class='options_title'>
					                <input id="texture_half" type="checkbox" class='options_titleText'><label for="texture_half">半口</label>
					            </div>
					            <ul class='options_list'>
					            	<li>
					                	<input id="half_gego" name="tooth_texture" type="checkbox" value="半口-可摘型德国GEGO牙桥"><label for="half_gego">(1)、半口-可摘型德国GEGO牙桥</label>
					                </li>
					                <li>
					                	<input id="half_dHisspirit" name="tooth_texture" type="checkbox" value="半口-可摘型维他灵牙桥"><label for="half_dHisspirit">(2)、半口-可摘型维他灵牙桥</label>
					                </li>
					                <li>
					                	<input id="half_titanium" name="tooth_texture" type="checkbox" value="半口-可摘型纯钛牙桥"><label for="half_titanium">(3)、半口-可摘型纯钛牙桥</label>
					                </li>
					                <li>
					                	<input id="half_resin" name="tooth_texture" type="checkbox" value="半口-固定版树脂牙桥"><label for="half_resin">(4)、半口-固定版树脂牙桥</label>
					                </li>
					                <li>
					                	<input id="half_zirconia" name="tooth_texture" type="checkbox" value="半口-固定版氧化锆牙桥"><label for="half_zirconia">(5)、半口-固定版氧化锆牙桥</label>
					                </li>
					                <li>
					                	<input id="half_pro" name="tooth_texture" type="checkbox" value="半口-塑钢牙pvc"><label for="half_pro">(6)、半口-塑钢牙pvc</label>
					            	</li>
					            </ul>
					        </div>
					    </div>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
		            	 <!--.commonText“就诊分类”文本的样式 提供布局  -->	<!--.colorRed 星号为红色及位置调整 -->
						 <span class="commonText"></i>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</span>
					</td>
					<td colspan="3">	<!--.dict 本身无样式 与载入数据功能有关 -->
						<!-- <input class="whiteInp" type="text" id="counsellor" name="counsellor" placeholder="咨询师" readonly onClick="javascript:single_select_user(['receiveno', 'counsellor'],'single');" />-->
						 <input style="width:100%;" name="remark" id="remark" type="text" data-bv-notempty data-bv-notempty-message="备注信息"  autocomplete="off" >
					</td>
				</tr>
			</tbody>
		</table>

		<!-- 2019/7/10 lutian 电子病历提示滚动文字 -->
		<marquee direction="left" behavior="scroll" scrolldelay="1000" loop="-1" scrollamount="70">请优先录入5个表单（1、既往史；2、口腔检查；4、手术方案；9、知情书；18、修复方案）。</marquee>

		<footer style="padding-bottom: 10px">
			<!-- .clear2 本身无样式 -->
			<div class="clear2"></div>
			<!--.btnCommon自定义的按钮常规样式   -->
			<a class="kqdsSearchBtn bigBtn" style="margin-top: 119px;margin-left: 49%;" onclick="createSSInfo()">提交</a>
		</footer>
	</div>


	<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
	<script type="text/javascript">
	var contextPath = "<%=contextPath %>";
	var id= window.parent.consultSelectPatient.seqid;	//选中患者id
	var order_number= window.parent.consultSelectPatient.orderNumber;//选中患者order_number
	var plantPhysician= window.parent.consultSelectPatient.plantPhysician;	//选中患者种植医师
	var repairPhysician= window.parent.consultSelectPatient.repairPhysician;	//选中患者修复医师
	var menuid=window.parent.menuid;//左侧菜单id
	var alreadySelectZSBSId=""; //已经有选择的主诉及既往病史seq_id 页面初始化时判断状态并赋值
	var alreadySelectJCZDId=""; //已经有选择的检查及诊断seq_id 页面初始化时判断状态并赋值
	var alreadySelectZLFAId=""; //已经有选择的诊疗方案seq_id 页面初始化时判断状态并赋值
	var alreadySelectXFFAId=""; //已经有选择的修复方案seq_id 页面初始化时判断状态并赋值
	var consultAddBtn=true; //判断此页面是否为咨询填写,多方案是否加新增按钮
    var alreadySelectZSBSMark="";  //记录上一次选中的主诉即既往病史是新病历还是老病历
	var notification = 0;
	$(function(){
		checkOptions();//判断要填写的选项是否已填写并选中
		titleclick();//牙冠材质移入移除显示隐藏
        checkFather();//根据父元素判断子元素选中状态
        checkOne();//根据子元素判断父元素选中状态

     	// 2019/7/24 lutian 禁止页面拖拽
        document.ondragstart = function() {
            return false;
        };

		var anamnesisUrl = contextPath + '/HUDH_ZzblAskAct/findCaseHistoryById.act';
        initCaseHistory(anamnesisUrl,1);  //初始化主诉及既往病史，默认先展示老病历
        initZzblOpration(); //初始化检查及诊断
        initDiagnosisProject(); //初始化诊疗方案
        initRepairProject(); //初始化修复方案
       	//dProjectclick(); //诊疗方案移入移除显示隐藏
        initSelectList("jwsLi",1); //初始化既往病史下拉框
        initSelectList("jczdLi",2); //检查即诊断下拉框
        initSelectList("zlCasesLi",3); //诊疗方案下拉框
        initSelectList("xffaLi",4); //修复方案下拉框

       	//全局监听
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

	});

	/* 测试按钮js lutian 2020/05/29 */

	//主诉改版
	$(".principleBtnGB").click(function(){
	  	parent.layer.open({
	  		title:"主诉改版",
	  		type:2,
	  		closeBtn:1,
	  		content:contextPath + "/ZzblViewAct/toAnamnesisThirdInfor.act",
			area:userAgent.indexOf("iPad") > -1 ? ['100%','95%'] : ['90%','80%'],
	  		cancel: function(){
	  		},
	  		end:function(){
	  			window.location.reload();//刷新本页面
	  		}
	  	});
	  });
	
	//种植牙手术记录
	$(".operationRecord").click(function(){
		var plant_physician,clinic_nurse; //种植医生，配台护士  lutian 2020/05/29
		// 查询手术医生，配台护士
		$.ajax({
			url: contextPath + "/HUDH_FlowAct/findLcljOrderTrsackById.act",
			type:"POST",
			dataType:"json",
			async: false,
			data:{
				"id":id
			},
			success:function(data){
				//console.log(JSON.stringify(data)+"----------查询患者信息");
				plant_physician=data.plant_physician; //种植医生
				clinic_nurse=data.clinic_nurse; //配台护士
			}
		});
		if(plant_physician=="" || clinic_nurse==""){
			editLclj("该患者种植医师、修复医师、诊室护士未填写，是否进行编辑?");
			return false;
		}
	  	parent.layer.open({
	  		title:"种植牙手术记录",
	  		type:2,
	  		closeBtn:1,
	  		content:contextPath + "/ZzblViewAct/toPlantOperationRecordInfor.act?plantPhysician="+plant_physician+"&&clinicNurse="+clinic_nurse,
	  		area:['90%','80%'],
	  		cancel: function(){
	  		},
	  		end:function(){
	  			window.location.reload();//刷新本页面
	  		}
	  	});
	  });

	//修复治疗记录
	$(".repairRecord").click(function(){
	  	parent.layer.open({
	  		title:"修复治疗记录",
	  		type:2,
	  		closeBtn:1,
	  		content:contextPath + "/ZzblViewAct/toRepairAcographyInfor.act",
	  		area:['90%','80%'],
	  		cancel: function(){
	  		},
	  		end:function(){
	  			window.location.reload();//刷新本页面
	  		}
	  	});
	  });

	//种植术后注意事项
	$(".postoperationItem").click(function(){
	  	parent.layer.open({
	  		title:"种植术后注意事项",
	  		type:2,
	  		closeBtn:1,
	  		content:contextPath + "/ZzblViewAct/toPostoperativePrecautionsInfor.act",
	  		area:['90%','80%'],
	  		cancel: function(){
	  		},
	  		end:function(){
	  			window.location.reload();//刷新本页面
	  		}
	  	});
	  });

	/* ------------------------------------- */

	function editLclj(hint){
		layer.confirm(hint,{
			   btn: ['确认', '取消'],
			   closeBtn:0
		}, function (index) {
			layer.open({
				type: 2,
				title: '修改临床路径',
				shadeClose: false,
				shade: 0.6,
				area: ['95%', '60%'],
				content: contextPath+'/ClinicPathControllerAct/toEditLcljOpreation.act?id=' +id
			});
			layer.close(index);
		}, function(index){
			layer.close(index);
		});
	}

	var count=0;//选中的input个数
    //牙冠材质移入移除显示隐藏
    function  titleclick(){
        var sc = document.getElementsByClassName('options');
        for (var i = 0; i < sc.length; i++) {
            sc[i].addEventListener("mouseenter",function(){
                $(this).children("ul").css("display","block");
            });
            sc[i].addEventListener("mouseleave",function(){
                $(this).children("ul").css("display","none");
            });
        }
    }
    //根据父元素判断子元素选中状态
    function checkFather(){
    	$(".options").find(".options_titleText").each(function(i,obj){
    		$(this).change(function(){
    			if($(this).is(":checked")){
        			var optionList=$(this).parents(".options").find(".options_list").find("input");
        			for(var i=0;i<optionList.length;i++){
        				optionList.eq(i).prop("checked","checked");
        			}
        			count=optionList.length;
    			}else{
    				var optionList=$(this).parents(".options").find(".options_list").find("input");
        			for(var i=0;i<optionList.length;i++){
        				optionList.eq(i).removeAttr("checked");
        			}
        			count=0;
    			}
    		});

    	});
    }
    //根据子元素判断父元素选中状态
    function checkOne(){
    	$(".options_list").find("input").each(function(i,obj){
    		$(this).change(function(){
    			count=$(this).parents(".options_list").find("input[checked='checked']").length;//  赋值时判断已经选中个数
    			if($(this).is(":checked")){
    				count++;
    			}else{
    				count--;
    			}
    			if(count>0){
        			$(this).parents(".options").find(".options_titleText").prop("checked","true");
        		}else{
        			$(this).parents(".options").find(".options_titleText").removeAttr("checked");
        		}
    		});

    	});
    }

	//判断要填写的选项是否已填写并选中
	function checkOptions(){
		//console.log(id+"---------------"+order_number);
		/* 判断主诉及既往病史 */
//		var askPreviousurl = contextPath + '/HUDH_ZzblAskAct/findCaseH	istoryById.act';
		var askPreviousurl = contextPath + '/HUDH_FlowAct/findLcljOrderTrsackById.act';
		$.ajax({
			url: askPreviousurl,
			type:"POST",
			dataType:"json",
			data : {
				 id :  id, //临床路径ID
				 order_number : order_number
			},
			success:function(result){
					// console.log(JSON.stringify(result)+"--------aaaaa");
					/* if(result.seqId){
						$(".ask_Previous").prev().attr("checked","checked").attr("disabled","disabled");
					}   */
//					$('input').attr("disabled",true)
					$("#remark").val(result.remark);

					//基台放置信息赋值
					var select_abutment_station = result.abutment_station;
					if(select_abutment_station){
						var disease_id = select_abutment_station.split(";");
						for(var i = 0; i < disease_id.length; i++){
							$("input[name='abutment_station']").each(function(){
								if($(this).val()==disease_id[i]){
								   $(this).attr("checked","checked").attr("disabled","disabled");
								}
							})
						}
					}

					//牙冠材质信息赋值
					var select_tooth_texture = result.tooth_texture;
					if(select_tooth_texture){
						var disease_id = select_tooth_texture.split(";");
						for(var i = 0; i < disease_id.length; i++){
							if(disease_id[i].indexOf("局部")>=0){
								$("#texture_topo").attr("checked","true").attr("disabled","disabled");
							}
							if(disease_id[i].indexOf("半口")>=0){
								$("#texture_half").attr("checked","true").attr("disabled","disabled");
							}
							$("input[name='tooth_texture']").each(function(){
								if($(this).val()==disease_id[i]){
								   $(this).attr("checked","checked").attr("disabled","disabled");
								}
							})
						}
					}

					//基台放置信息赋值
				    var select_imagelogic = result.imageological_examination;
					if(select_imagelogic){
						var disease_id = select_imagelogic.split(/、|;/);
						for(var i = 0; i < disease_id.length; i++){
							$("input[name='imagelogic']").each(function(){
								if($(this).val()==disease_id[i] || $(this).val().indexOf(disease_id[i])>0){
									   $(this).attr("checked","checked").attr("disabled","disabled");
									}
							})
						}
					}

					//其他信息赋值
					var select_consultation = result.consultation;
					if(select_consultation){
						var disease_id = select_consultation.split(";");
						for(var i = 0; i < disease_id.length; i++){
							$("input[name='Consultation']").each(function(){
								if($(this).val()==disease_id[i]){
								   $(this).attr("checked","checked").attr("disabled","disabled");
								}
							})
						}
					}

			}
	  });
		/* 判断新种植病历情况记录*/
		var plantRecordsurl =  contextPath + '/HUDH_MedicalRecordsAct/selectdata.act';
		$.ajax({
			url: plantRecordsurl,
			type:"POST",
			dataType:"json",
			data : {
				lcljId:id
			},
			success:function(result) {
				// console.log(JSON.stringify(result)+'----result');
				if(result.length>0){
					$(".plantRecords").prev().attr("checked","checked").attr("disabled","disabled");
				}
			}
		});
		/* 判断人工种植牙知情同意书*/
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
		var plantKnowbookurl = contextPath + '/HUDH_ZzblAskAct/findLocatorFamiliar.act';
		$.ajax({
			url: plantKnowbookurl,
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
		var plantKnowbookurl = contextPath + '/HUDH_ZzblAskAct/findLocatorFamiliar.act';
		$.ajax({
			url: plantKnowbookurl,
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

		//告知通知书
		var gztzsurl = contextPath + '/HUDH_NotificationAct/findNotificationByLcljId.act';
		$.ajax({
			url: gztzsurl,
			type:"POST",
			dataType:"json",
			data : {
				LcljId :  id,
				LcljNum : order_number
			},
			success:function(result){
				if(result!=null&&result.lcljId){
					notification=1;
					$(".inform").prev().attr("checked","checked").attr("disabled","disabled");
				}
			}

	  });



		//诊疗方案
		// var zlfaurl = contextPath + '/HUDH_LcljCaseAct/select.act';
		// $.ajax({
		// 	url: zlfaurl,
		// 	type:"POST",
		// 	dataType:"json",
		// 	data : {
		// 		id : id
		// 	},
		// 	success:function(result){
		// 		if(result!=null){
		// 			$(".case").prev().attr("checked","checked").attr("disabled","disabled");
		// 		}
		// 	}
	  // });

		//新 人工种植牙知情同意书
		var consentBookUrl = contextPath + '/HUDH_ZzblAskAct/findFamiliarBookById.act';
		$.ajax({
			url: consentBookUrl,
			type:"POST",
			dataType:"json",
			data : {
				id : id
			},
			success:function(result){
				if(result!=null){
					$(".dentalImplant").prev().attr("checked","checked").attr("disabled","disabled");
				}
			}
		});

}

	//告知通知书
	  $(".inform").click(function(){
	  	parent.layer.open({
	  		title:"告知通知书",
	  		type:2,
	  		closeBtn:1,
	  		content:contextPath + "/ZzblViewAct/toExamineDiagnoseInform.act?status="+notification,
	  		area:['70%','80%'],
	  		cancel: function(){
	  		},
	  		end:function(){
	  			window.location.reload();//刷新本页面
	  		}
	  	});
	  });

	//诊疗方案
	  $(".case").click(function(){
	  	parent.layer.open({
	  		title:"诊疗方案",
	  		type:2,
	  		closeBtn:1,
	  		content:contextPath + "/ZzblViewAct/toExamineDiagnoseCase.act?status="+notification,
	  		area:['100%','90%'],
	  		cancel: function(){
	  		},
	  		end:function(){
	  			window.location.reload();//刷新本页面
	  		}
	  	});
	  });


	//人工种植牙知情同意书
	  $(".dentalImplant").click(function(){
	  	parent.layer.open({
	  		title:"人工种植牙知情同意书",
	  		type:2,
	  		closeBtn:1,
	  		content:contextPath + "/ZzblViewAct/toExamineDentalImplant.act",
	  		area:['70%','80%'],
	  		cancel: function(){
	  		},
	  		end:function(){
	  			window.location.reload();//刷新本页面
	  		}
	  	});
	  });

	//点击弹出层
	var userAgent = navigator.userAgent;
	//签署种植牙知情同意书     ZzblViewAct.java
	  $(".plantKnowbook").click(function(){
			parent.layer.open({
		  		title:"种植牙知情同意书",
		  		type:2,
		  		closeBtn:1,
		  		content:contextPath + "/ZzblViewAct/toPlantKnowbookInfor.act",
		  		area:userAgent.indexOf("iPad") > -1 ? ['100%','95%'] : ['80%','80%'],
		  		cancel: function(){
		  		},
		  		end:function(){
		  			window.location.reload();//刷新本页面
		  		}
		  	});
	  });
	//拔牙手术知情同意书     ZzblViewAct.java
	  $(".pulloutKnowbook").click(function(){
			parent.layer.open({
		  		title:"拔牙手术知情同意书",
		  		type:2,
		  		closeBtn:1,
		  		content:contextPath + "/ZzblViewAct/topulloutKnowbook.act",
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
		  		content:contextPath + "/ZzblViewAct/tolocatorKnowbook.act",
		  		area:userAgent.indexOf("iPad") > -1 ? ['100%','95%'] : ['80%','80%'],
		  		cancel: function(){
		  		},
		  		end:function(){
			  		window.location.reload();//刷新本页面
			  	}
			});
	  });
	//新种植病历
	  $(".plantRecords").click(function(){
		  	parent.layer.open({
		  		title:"种植病历",
		  		type:2,
		  		closeBtn:1,
		  		content:contextPath+'/ZzblViewAct/toDentalExamination.act',
		  		area:userAgent.indexOf("iPad") > -1 ? ['100%','95%'] : ['80%','80%'],
		  		cancel: function(){
		  		},
		  		end:function(){
		  			window.location.reload();//刷新本页面
		  		}
		  	});

		  });
	//修复方案
	 /*  $(".xiufu_test").click(function(){
		  if(plantPhysician==''&&repairPhysician==''){
				editLclj("该患者种植医师和修复医师未填写，是否进行编辑?");
			}else if(plantPhysician==''&&repairPhysician!=''){
				editLclj("该患者种植医师未填写，是否进行编辑?");

			}else if(plantPhysician!=''&&repairPhysician==''){
				editLclj("该患者修复医师未填写，是否进行编辑?");
			}else{
				parent.layer.open({
			  		title:"修复方案确认单",
			  		type:2,
			  		closeBtn:1,
			  		content:contextPath + "/ZzblViewAct/toRepairProjectInfor.act",
			  		area:['100%','90%'],
			  		cancel: function(){
			  		},
			  		end:function(){
			  			window.location.reload();//刷新本页面
			  		}
			  	});
			}
	  }); */

	  function createSSInfo() {
		  if(plantPhysician==''&&repairPhysician==''){
				editLclj("该患者种植医师和修复医师未填写，是否进行编辑?");
			}else if(plantPhysician==''&&repairPhysician!=''){
				editLclj("该患者种植医师未填写，是否进行编辑?");
			}else if(plantPhysician!=''&&repairPhysician==''){
				editLclj("该患者修复医师未填写，是否进行编辑?");
			}else{
				submit();
			}
		}
	  function submit(){
//			var seqId = onclickrowOobj.seqId;
			var imageological_examination = showImagelogic();
			var consultation = showConsultation();
			var abutment_station = abutmentStation(); //基台放置
			var tooth_texture = toothTexture(); //牙冠材质
			var remark = $("#remark").val();
			var advisory=showAdvisory(); //会诊
			var id = '<%=request.getAttribute("id")%>';
			if(!consultation) {layer.alert('请勾选术前准备选项！');return;}
			if(!imageological_examination) {layer.alert('请勾选影像学检查！');return;}
			/* if(!abutment_station) {layer.alert('请勾选基台放置！');return;} */
			if(!tooth_texture) {layer.alert('请勾选牙冠材质！');return;}
			//创建手术
			var param = {
					abutment_station : abutment_station,
					tooth_texture : tooth_texture,
					imageological_examination : imageological_examination,
					consultation : consultation,
					id : id,
					remark : remark,
					advisory : advisory
			};

			var url = '<%=contextPath%>/HUDH_FlowAct/updateLcljOrderTrackById.act';
			$.axseSubmit(url, param, function() {}, function(r) {
				//console.log(JSON.stringify(r)+"--------------返回数据一");
				layer.alert(r.retMsrg, {
		            end: function() {
		          	  //window.parent.location.reload(); //刷新父页面
		                //var frameindex = parent.layer.getFrameIndex(window.name);
		                //parent.layer.close(frameindex); //再执行关闭
		            }
		        });
				if(r.retMsrg=="操作成功"){
					layer.alert(r.retMsrg, {
						end: function() {
							window.parent.location.reload(); //刷新父页面
							var frameindex = parent.layer.getFrameIndex(window.name);
							parent.layer.close(frameindex); //再执行关闭
						}
					});
				}
			}, function(r) {
				//console.log(JSON.stringify(r)+"--------------返回数据二");
				layer.alert(r.retMsrg, {
		            end: function() {
		            	window.parent.location.reload(); //刷新父页面
		                var frameindex = parent.layer.getFrameIndex(window.name);
		                parent.layer.close(frameindex); //再执行关闭
		            }
		        });
			});
	  }
	//获取影像学检查
	  function showImagelogic(){
	      var obj = document.getElementsByName("imagelogic");
	      var imageological_examination = "";
	      for ( k in obj ) {
	          if(obj[k].checked)
	          //alert(obj[k].value);
	          imageological_examination = imageological_examination + obj[k].value + ';';//拼接字符串
	      }
	      return imageological_examination;
	  }
	  //获取专家会诊
	  function showConsultation(){
	      var obj = document.getElementsByName("Consultation");
	      var consultation = "";
	      for ( k in obj ) {
	          if(obj[k].checked)
	          	//consultation.push(obj[k].value);
	          consultation = consultation + obj[k].value + ';';
	      }
	      return consultation;
	  }
	  //获取咨询
	  /* function showAdvisory(){
	      var obj = document.getElementsByName("Advisory");
	      var advisory = "";
	      for ( k in obj ) {
	          if(obj[k].checked)
	          	//advisory.push(obj[k].value);
	          	advisory = advisory + obj[k].value + ';';
	      }
	      return advisory;
	  } */

	//获取会诊
	  function showAdvisory(){
	      var obj = document.getElementsByName("Advisory");
	      var advisory = "";
	      for ( k in obj ) {
	          if(obj[k].checked)
	          	//advisory.push(obj[k].value);
	          	advisory = advisory + obj[k].value + ';';
	      }
	      return advisory;
	  }

	  //获取基台放置信息
	  function abutmentStation(){
	      var obj = document.getElementsByName("abutment_station");
	      var abutment_station = "";
	      for ( k in obj ) {
	          if(obj[k].checked)
	          //alert(obj[k].value);
	          	abutment_station = abutment_station + obj[k].value + ';';//拼接字符串
	      }
	      return abutment_station;
	  }

	  //获取牙冠材质
	  function toothTexture(){
	      var obj = document.getElementsByName("tooth_texture");
	      var tooth_texture = "";
	      for ( k in obj ) {
	          if(obj[k].checked)
	          //alert(obj[k].value);
	          	tooth_texture = tooth_texture + obj[k].value + ';';//拼接字符串
	      }
	      return tooth_texture;
	  }
	</script>
</body>
</html>