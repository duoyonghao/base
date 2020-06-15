<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");
	String usercodes = request.getParameter("usercodes");
	if(usercodes == null){
		usercodes = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>报表-综合查询</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reception_center.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yiliao/bingli_search.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/Load.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/loading/DataLazyLoad.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
</head>
<style type="text/css">
.buttonBar .aBtn_big{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 80px;text-align: center;}
.buttonBar  .aBtn_big:hover{color:#fff;background: #0e7cc9;}
.buttonBar .aBtn_big2{display:inline-block;padding: 0 15px;height: 28px;border: solid 1px #0e7cc9;color: #0e7cc9;border-radius: 15px;line-height: 28px;width: 88px;text-align: center;}
.buttonBar  .aBtn_big2:hover{color:#fff;background: #0e7cc9;}
/*工作量表格 ，单独写*/
#gongzuol{border:solid 1px #c0c0c0;background: #f5f5f5;}
#gongzuol{margin-bottom: 15px;}
#gongzuol .columnHd{padding:0 15px;border-bottom:solid 1px #0e7ec6;font-size:16px;color: #373737;font-family: "Microsoft YaHei";line-height:36px;}
 .kqds_table{
		width:90%;
		align:center;
		margin-left: auto;
		margin-right: auto;
	}
	
	.kqds_table  td { 
		color: #666;
		padding: 0px 0px 1px 1px;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
		font-weight: normal;
		line-height: 28px;
	}
	
	.kqds_table  select { 
		height: 28px;
		width:90px;
		border: solid 1px #e5e5e5;
		border-radius: 3px;
	}
	.fixed-table-container{
		border-radius: 6px;
		/* border-top-left-radius: 6px;
		border-top-right-radius: 6px; */
		overflow: hidden;
	}
	input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:90px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
	.fixed-table-body{
		border:1px solid #ddd;
		border-bottom-right-radius:6px;
		border-bottom-left-radius:6px;
	}
	.searchWrap .formBox>section>ul.conditions>li>span{
		width:65px;
		text-align:right;
	}
	.searchWrap .formBox>section>ul.conditions>li{
		padding:3px 0;
	}
	.searchWrap .cornerBox {
	    position: absolute;
	    left: 0;
	    top: 0;
	    height: 30px;
	    width: 100%;
	    line-height: 30px;
	    text-align: left;
	    padding-left: 10px;
	    font-size: 14px;
	    color: #333;
	    background: #fff;
	    border-bottom: 1px solid #ddd;
	}
	.searchWrap{
		padding:40px 0 8px 0;
		border-radius:6px;
	}
/* 	 搜索框select  */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 110px;   
	      }  
	.searchSelect>.btn { 
		   width: 110px; 
		 height:26px; 
		border: solid 1px #e5e5e5; 
		}


	.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
		    color: #999;
		    height: 26px;
		}
	.pull-left {
	    float: left !important;
	    color: #666;
		}
	.bootstrap-select.btn-group .dropdown-toggle .filter-option {
	    margin-top: -3px;
	}
	.btn-group > .btn:first-child:hover {
	    background: white;
	}
	.searchWrap .text {
		    position: static !important; 
		    left: 0px;
		    top: 9px;
		    color: rgb(31, 32, 33);
	}
/*  	解決标签查询中下拉框悬浮 	  */
	.searchWrap , .formBox{
     overflow: visible; 
	}
	.searchWrap .formBox>section>ul.conditions {
	    overflow: visible;
	    margin: 0;
	    height: 96px;
	}
</style>

<body>
<div id="container">
    <div class="main">
        <div class="listWrap">
            <!-- <div class="listHd"><span class="hc-icon icon20 home-icon"></span>收费明细</div> -->
            <div class="listBd">
                <div class="tableBox">
                    <table id="table" class="table-striped table-condensed table-bordered" >
                    </table>
                </div>
                <div id="buttonBar"> 
                     <table style="width:90%;text-align: center;"> 
                 		<tr>
                			<td style="width:12%"><span style="color:#00A6C0;">收款小计:<lable id="xiaoji">0</lable></span></td>
                			<td style="width:10%"><span style="color:#00A6C0;"><%=SysParaUtil.getFkfsNameByCostField(request, "payxj") %>:<lable id="payxj">0</lable></span></td>
                			<td style="width:10%"><span style="color:#00A6C0;"><%=SysParaUtil.getFkfsNameByCostField(request, "paybank") %>:<lable id="paybank">0</lable></span></td>
                			<td style="width:10%"><span style="color:#00A6C0;"><%=SysParaUtil.getFkfsNameByCostField(request, "payyb") %>:<lable id="payyb">0</lable></span></td>
                			<td style="width:10%"><span style="color:#00A6C0;"><%=SysParaUtil.getFkfsNameByCostField(request, "paywx") %>:<lable id="paywx">0</lable></span></td>
                			<td style="width:10%"><span style="color:#00A6C0;"><%=SysParaUtil.getFkfsNameByCostField(request, "payzfb") %>:<lable id="payzfb">0</lable></span></td>
                			<td style="width:10%"><span style="color:#00A6C0;"><%=SysParaUtil.getFkfsNameByCostField(request, "paymmd") %>:<lable id="paymmd">0</lable></span></td>
                			<td style="width:10%"><span style="color:#00A6C0;"><%=SysParaUtil.getFkfsNameByCostField(request, "paybdfq") %>:<lable id="paybdfq">0</lable></span></td>
                			<td style="width:10%"><span style="color:#00A6C0;"><%=SysParaUtil.getFkfsNameByCostField(request, "payother1") %>:<lable id="payqt">0</lable></span></td>
                 		</tr> 
                 	</table> 
                </div>
            </div>
        </div>
        <!--查询条件-->
        <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
                <div class="formBox">
                	<section >
			    		<ul class="conditions ">
			    			<li>
			    				<span>消费门诊</span>
								<select id="organization" name="organization"></select>
							</li>
			    			<li class="unitsDate">
				    			<span>收费时间</span>
	                            <input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly class="birthDate">
		                    </li>
				    		<li class="unitsDate">
				    			<span>到</span>
	                            <input type="text" id="endtime" name="endtime" placeholder="结束日期" readonly class="birthDate">
							</li>
							<li class="toggleTr">
			    				<span>治疗状态</span>
								<select id="zlstatus" name="zlstatus">
									<option value="" selected="selected">请选择</option>
									<option value="未治疗">未治疗</option>
									<option value="已治疗">已治疗</option>
								</select>
							</li>
			    			<li class="unitsDate">
			    				<span>治疗时间</span>
	                            <input type="text" id="zlstarttime" name="zlstarttime" placeholder="开始日期" readonly class="birthDate">
			                </li>       
			    			<li class="unitsDate">
			    				<span>到</span>
	                            <input type="text" id="zlendtime" name="zlendtime" placeholder="结束日期" readonly class="birthDate">
			                </li> 
				    		<li>
			    				<span>消费分类</span>
<!-- 								<select class="select2"  name="basetype" id="basetype"> -->
<!--                  	  			</select> -->
                 	  			<select class="select2 searchSelect"  name="basetype" id="basetype" data-live-search="true" title="请选择">
                 	  			</select>
							</li>
				    		<li>
				    			<span>子分类</span>
<!-- 		    					<select class="select2"  name="nexttype" id="nexttype"> -->
<!-- 		                       		 <option value="">请选择</option> -->
<!-- 		                 	  	</select> -->
		                 	  	<select class="select2 searchSelect"  name="nexttype" id="nexttype" data-live-search="true" title="请选择">
		                       		 <option value="">请选择</option>
		                 	  	</select>
		                 	  	
				    		</li>
				    		<li>
				    			<span>建档人</span>
		    					<input type="hidden" name="createuser" id="createuser"  class="form-control"  value=""/>
							    <input  type="text"  id="createuserDesc" name="createuserDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['createuser', 'createuserDesc'],'',1);"></input>	
				    		</li>
				    		<li>
				    			<span>开发人</span>
		    					<input type="hidden" name="kfr" id="kfr"  class="form-control"  value=""/>
							    <input  type="text"  id="kfrDesc" name="kfrDesc" placeholder="开发人" readonly  onClick="javascript:single_select_user(['kfr', 'kfrDesc'],'',1);"></input>	
				    		</li>
				    		<li>
				    			<span>介绍人</span>
		    					<input type="hidden" name="introducer" id="introducer"  class="form-control" value=""/>
								<input type="text" id="introducerDesc" name="introducerDesc" placeholder="介绍人"  readonly onclick="getuser()"></input>
				    		</li>
				    		<li>
				    			<span>消费项目</span>
			    				<input type="text" placeholder="消费项目" id="itemname" name="itemname">
				    		</li>
				    		<li>
				    			<span>接诊咨询</span>
	    					 	<input type="hidden" name="askperson" id="askperson"  class="form-control"  value=""/>
							 	<input type="text"  id="askpersonDesc" name="askpersonDesc" placeholder="咨询" readonly  onClick="javascript:single_select_user(['askperson', 'askpersonDesc'],'',1);"></input>	
				    		</li>
				    		<li>
				    			<span>就诊科室</span>
		    					<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1 %>" name="regdept" id="regdept" ></select>
				    		</li>
				    		<li>
				    			<span>医生</span>
		    					<input type="hidden" name="doctor" id="doctor"  class="form-control"  value=""/>
							    <input type="text"  id="doctorDesc" name="doctorDesc" placeholder="医生" readonly  onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'',1);"></input>	
				    		</li>
				    		<li>
				    			<span>患者来源</span>
<!-- 			    				<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype1');"> -->
<!-- 								</select> -->
								<select class="patients-source select2 dict searchSelect" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelectSearch('devchannel','nexttype1');" data-live-search="true" title="请选择">
								</select>
				    		</li>
				    		<li>
				    			<span>子分类</span>
<!-- 			    				<select class="select2 dict" name="nexttype1" id="nexttype1"> -->
<!-- 									<option value="">请选择</option> -->
<!-- 								</select> -->
								<select class="select2 dict searchSelect" name="nexttype1" id="nexttype1" data-live-search="true" title="请选择">
								</select>
				    		</li>
				    		<li>
				    			<span>受理类型</span>
		    					<select class="dict" tig="SLLX" id="shouli" ></select>
				    		</li>
				    		<li>
				    			<span>受理工具</span>
<!-- 		    					<select class="dict" tig="SLGJ" id="gongju" ></select> -->
		    					<select class="dict searchSelect" tig="SLGJ" id="gongju"  data-live-search="true" title="请选择"></select>
				    		</li>
				    		<li>
				    			<span>收款备注</span>
			    				<input type="text" placeholder="收款备注" id="remark" name="remark" >
				    		</li>
				    		<li>
				    			<span>模糊查询</span>
			    				<input type="text" placeholder="患者编号/姓名/手机号" id="queryinput" name="queryinput" >
				    		</li>
		    			</ul>
		    		</section>
               	   	<div class="btnBar" style="text-align:center;">
	                 	<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
		                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
	                </div>
	                   <%-- <table class="kqds_table">
				    		<tr>
				    			<td style="text-align:right;">消费门诊：</td>
				    			<td style="text-align:left;">
										<select id="organization" name="organization"></select>
								</td>
				    			<td style="text-align:right;">收费时间：</td>
				    			<td style="text-align:left;"> 
				    					<span class="unitsDate">
				                            <input type="text" id="starttime" name="starttime" placeholder="开始日期" readonly class="birthDate">
				                        </span>
				                </td>
				    			
				    			<td style="text-align:right;">到：</td>
				    			<td style="text-align:left;">
										<span class="unitsDate">
				                            <input type="text" id="endtime" name="endtime" placeholder="结束日期" readonly class="birthDate">
				                        </span>
								</td>
				    			<td style="text-align:right;">消费分类：</td>
				    			<td style="text-align:left;">
										<select class="select2"  name="basetype" id="basetype">
		                 	  			 </select>
								</td>
				    			
				    			<td style="text-align:right;">子分类：</td>
				    			<td style="text-align:left;">
				    					 <select class="select2"  name="nexttype" id="nexttype">
					                       		 <option value="">请选择</option>
				                 	  	 </select>
				    			</td>
				    			
				    			<td style="text-align:right;">建档人：</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="createuser" id="createuser"  class="form-control"  value=""/>
									     <input  type="text"  id="createuserDesc" name="createuserDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['createuser', 'createuserDesc'],'',1);"></input>	
				    			</td>
				    			<td style="text-align:right;">开发人：</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="kfr" id="kfr"  class="form-control"  value=""/>
									     <input  type="text"  id="kfrDesc" name="kfrDesc" placeholder="开发人" readonly  onClick="javascript:single_select_user(['kfr', 'kfrDesc'],'',1);"></input>	
				    			</td>
				    			<td style="text-align:right;">介绍人：</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="introducer" id="introducer"  class="form-control" value=""/>
										 <input  type="text" id="introducerDesc" name="introducerDesc" placeholder="介绍人"  readonly onclick="getuser()"></input>
				    			</td>
				    			
				    		</tr>
				    		<tr>
				    			<td style="text-align:right;">消费项目：</td>
				    			<td style="text-align:left;">
				    				<input type="text" placeholder="消费项目" id="itemname" name="itemname">
				    			</td>
				    			<td style="text-align:right;">接诊咨询：</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="askperson" id="askperson"  class="form-control"  value=""/>
										 <input type="text"  id="askpersonDesc" name="askpersonDesc" placeholder="咨询" readonly  onClick="javascript:single_select_user(['askperson', 'askpersonDesc'],'',1);"></input>	
				    			</td>
				    			<td style="text-align:right;">就诊科室：</td>
				    			<td style="text-align:left;">
			    					<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1 %>" name="regdept" id="regdept" ></select>
				    			</td>
				    			<td style="text-align:right;">医生：</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="doctor" id="doctor"  class="form-control"  value=""/>
									     <input type="text"  id="doctorDesc" name="doctorDesc" placeholder="医生" readonly  onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'',1);"></input>	
				    			</td>
				    			
				    			<td style="text-align:right;">患者来源：</td>
				    			<td style="text-align:left;">
				    				<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype1');">
									</select>
				    			</td>
				    			
				    			<td style="text-align:right;">子分类：</td>
				    			<td style="text-align:left;">
				    				<select class="select2 dict" name="nexttype1" id="nexttype1">
										<option value="">请选择</option>
									</select>
				    			</td>
				    			<td style="text-align:right;">受理类型：</td>
				    			<td style="text-align:left;">
			    					 <select class="dict" tig="SLLX" id="shouli" ></select>
				    			</td>
				    			<td style="text-align:right;">受理工具：</td>
				    			<td style="text-align:left;">
			    					<select class="dict" tig="SLGJ" id="gongju" ></select>
				    			</td>
				    			
				    		</tr>
				    		<tr>
				    			
				    			<td style="text-align:right;">收款备注：</td>
				    			<td style="text-align:left;">
				    				<input type="text" placeholder="收款备注" id="remark" name="remark" >
				    			</td>
				    			<td>模糊查询：</td>
				    			<td>
				    				<input type="text" placeholder="患者编号/姓名/手机号" id="queryinput" name="queryinput" >
				    			</td>
				    		</tr>
				    	</table>
	                    <div class="btnBar" style="text-align: center;">
	                    	<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
			                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			            </div> --%>
	                </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var $table = $('#table');
var pageurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/orderAndMember.act';
var nowday;
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
var menuid = "<%=menuid%>";
var usercodes = "<%=usercodes%>";
var qxnameArr = ['hdjl_skmx_scbb'];
var func = ['exportTable'];
$(function() {
	initHosSelectListNoCheck('organization'); // 连锁门诊下拉框	
	var tmpOrganization = $("#organization").val();
    initDeptSelectByTypesAndClass(tmpOrganization);
    $("#organization").change(function() {
        var currSelect = $(this).val();
        initDeptSelectByTypesAndClass(currSelect);
    });
    initCostSortSelect1Level('basetype');
    //当前日期
    nowday = getNowFormatDate();
    initDictSelectByClass();// 挂号分类、就诊分类、患者来源
    //获取当前页面所有按钮
    getButtonPowerByPriv(qxnameArr,func,menuid);
    //时间选择
    $(".birthDate").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd',
        minView: 2,
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "top-right"
    });
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    //4、表格初始化
    getPayOrderlist(pageurl);
    //计算主体的宽度
    setWidth();
    setHeight();
    $(window).resize(function() {
        setWidth();
        setHeight();
    });
    getyjjSelect();
  $('.searchSelect').selectpicker("refresh");//初始化刷新搜索--2019.11.14--licc
});

//计算主体的宽度
function setWidth() {
    var baseW = $(window).width() - 20;
    var innerHeight_1, innerHeight_2;
    $('.centerWrap,.lookInfoWrap').width(baseW / 2);
    $('.operateModel .optBox').width(($('.centerWrap').width() - 140) / 2); (innerHeight_1 = $('.operateModel .optBox:eq(0)').height()) > (innerHeight_2 = $('.operateModel .optBox:eq(1)').height()) ? $('.operateModel .optBox:eq(1)').height(innerHeight_1) : $('.operateModel .optBox:eq(0)').height(innerHeight_2);
}

//计算左侧表格高度保证一屏展示
function setHeight() {
    // 这个页面，屏幕小会有横向滚动条，这个滚动条的高度是15
    // 这里可以可以根据屏幕分辨率，计算是否会出现滚动条
    var scrollHeight = 0;

    var baseHeight = $(window).height() - 15;
    $("#table").bootstrapTable("resetView",{
    	height:$(window).outerHeight()-$(".searchWrap").outerHeight()-45
    });
    $('.tabIframe').height(baseHeight - 50);

}
function getyjjSelect() {
    var dict = $("#basetype");
    dict.append("<option value='预交金'>预交金</option>");
}
$('#basetype').change(function() {
	if($(this).val()){ // 如果一级有值，再请求
		$("#nexttype").empty();
		if(this.value == '预交金'){
			 var dict = $("#nexttype");
			 dict.append("<option value=''>请选择</option>");
			 dict.append("<option value='开卡'>开卡</option>");
			 dict.append("<option value='充值'>充值</option>");
			 dict.append("<option value='退费'>退费</option>");
			 dict.selectpicker("refresh");
		}else{
// 			initCostSortSelect2Level('nexttype',this.value);
			initCostSortSelect2LevelSearch('nexttype',this.value);
		}
	}
});
//已结账
function getPayOrderlist(url) {
    $('#table').bootstrapTable({
        url: url,
        dataType: "json",
        queryParams: queryParams,
        onLoadSuccess: function(data) { //加载成功时执行
        	if(nowpage == 0 && data.total>0){
       		    maxpage = Math.floor(data.total/pagesize)+1; 
	       		$("#xiaoji").html(data.realmoney);
	            $("#payxj").html(data.payxj);
	    		$("#paybank").html(data.paybank);
	            $("#payyb").html(data.payyb);
	            $("#paywx").html(data.paywx);
	            $("#payzfb").html(data.payzfb);
	            $("#paymmd").html(data.paymmd); 
	            $("#paybdfq").html(data.paybdfq); 
	            $("#payqt").html(data.payother1); 
	      	 }
            if(data.total == 0){
            	$("#xiaoji").html("0.00");
	            $("#payxj").html("0.00");
	    		$("#paybank").html("0.00");
	            $("#payyb").html("0.00");
	            $("#paywx").html("0.00");
	            $("#payzfb").html("0.00");
	            $("#paymmd").html("0.00"); 
	            $("#paybdfq").html("0.00"); 
	            $("#payqt").html("0.00"); 
    		 }
	      	//分页加载
	      	showdata("table",data.rows);
          	//计算主体的宽度
            setWidth();
            setHeight();
            /*表格出现滚动条时 调整表头*/
            setTableHeaderWidth(".tableBox");
            //付款方式赋值
	        getFkfsField();
        },
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.istk == "1" || Number(row.realmoney)<0) {
                strclass = 'danger'; //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        columns: [{
		    title: '序号',
		    field: 'Number',
		    align: 'center',
		    formatter: function(value, row, index) {
		    	if(row.itemname=='' || row.itemname==null){
		    		 return '<span>'+'Y_'+index+1+'</span>';
		    	}else{
		    		 return '<span>'+index+1+'</span>';
		    	}
            }
		 },{
            title: '消费门诊',
            field: 'organization',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="" title="' + value + '">' + value + '</span>';
            }
        },{
            title: '收费时间',
            field: 'sftime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value.indexOf("null") > -1) {
                    return "";
                } else {
                    var sftime = value.substring(0, 16);
                    return '<span class="">' + sftime + '</span>';
                }
            }
        },{
            title: '治疗状态',
            field: 'kaifa',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value=='已治疗') {
                    return "<span>已治疗</span>";
                } else {
                	return "<span>未治疗</span>";
                }
            }
        },{
            title: '治疗时间',
            field: 'zltime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    var sftime = value.substring(0, 16);
                    return '<span>' + sftime + '</span>';
                }
                return "";
            }
        },
        {
            title: '编号',
            field: 'usercode',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '电话',
            field: 'phonenumber1',
            align: 'center',
            formatter: function(value, row, index) {
                if (canlookphone == 0) {
                    return '<span class="time phone" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span>-</span>';
                }
            }
        },
        {
            title: '消费分类',
            field: 'classname',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span></span>';
                }
            }
        },
        {
            title: '二级消费分类',
            field: 'nextname',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span></span>';
                }
            }
        },
        {
            title: '消费项目',
            field: 'itemname',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="time" title="' + value + '">' + value + '</span>';
                } else {
                    return '<span></span>';
                }
            }
        },
        {
            title: '收款',
            field: 'realmoney',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '预交金-充值',
            field: 'cmoney',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '预交金-赠送',
            field: 'cgivemoney',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '预交金-小计',
            field: 'ctotal',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '单位',
            field: 'unit',
            align: 'center',
          
            sortable: true,
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '数量',
            field: 'num',
            align: 'center',
          
            sortable: true,
            formatter:function(value,row,index){
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '折扣',
            field: 'discount',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
            	return '<span>'+value+'</span>';
            }
        },
        {
            title: '小计',
            field: 'subtotal',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '免除',
            field: 'voidmoney',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '应收',
            field: 'ys',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="money">' + value + '</span>';
                } 
            }
        },
        {
            title: '欠费',
            field: 'y2',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '赠送使用',
            field: 'payother2',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {title: '代金券',field: 'paydjq',align: 'center',valign: 'middle',sortable: true,
			formatter:function(value,row,index){
				if(value==null){
					return '<span class="money">0.0</span>' ;
				}else{
					return '<span class="money">'+value+'</span>' ;
				}
			}
		},
		{title: '积分使用',field: 'payintegral',align: 'center',valign: 'middle',sortable: true,
			formatter:function(value,row,index){
				if(value==null){
					return '<span class="money">0.0</span>' ;
				}else{
					return '<span class="money">'+value+'</span>' ;
				}
			}
		},
        {
            title: '实收',
            field: 'paymoney',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="money">' + value + '</span>';
            }
        },
        {
            title: '现金',
            field: 'payxj',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {
            title: '预交金使用',
            field: 'payyjj',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {
            title: '银行卡',
            field: 'paybank',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {
            title: '医保',
            field: 'payyb',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {
            title: '微信',
            field: 'paywx',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {
            title: '支付宝',
            field: 'payzfb',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {title: '么么贷',field: 'paymmd',align: 'center',sortable: true,
			formatter:function(value,row,index){
				if(value==null){
					return '<span class="money">0.0</span>' ;
				}else{
					return '<span class="money">'+value+'</span>' ;
				}
			}
		},
		{title: '百度分期',field: 'paybdfq',align: 'center',sortable: true,
			formatter:function(value,row,index){
				if(value==null){
					return '<span class="money">0.0</span>' ;
				}else{
					return '<span class="money">'+value+'</span>' ;
				}
			}
		},
        {
            title: '其他',
            field: 'payother1',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value == null) {
                    return '<span class="money">0.0</span>';
                } else {
                    return '<span class="money">' + value + '</span>';
                }
            }
        },
        {
            title: '特殊项目',
            field: 'istsxm',
            align: 'center',
          
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '就诊分类',
            field: 'recesort',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '挂号分类',
            field: 'regsort',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '成交情况',
            field: 'cjstatus',
            align: 'center',
          
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '第一咨询',
            field: 'faskperson',
            align: 'center',
          
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '接诊咨询',
            field: 'askperson',
            align: 'center',
          
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '就诊科室',
            field: 'regdept',
            align: 'center',
          
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '医生',
            field: 'doctor',
            align: 'center',
          
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '护士1',
            field: 'nurse',
            align: 'center',
          
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '护士2',
            field: 'techperson',
            align: 'center',
          
            sortable: true,
            formatter:function(value){
            	return '<span>'+value+'</span>'
            }
        },
        {
            title: '患者来源',
            field: 'devchannel',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '来源子分类',
            field: 'nexttype',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {	
        	title: '受理类型',
            field: 'accepttype',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="source">' + value + '</span>';
         	}
        },
        {
            title: '受理工具',
            field: 'accepttool',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="source">' + value + '</span>';
         	}
        },
        {
            title: '开单人',
            field: 'kduser',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return '<span>'+html+'</span>';
                } else {
                    return "";
                }
            }
        },
        {
            title: '开单时间',
            field: 'kdtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="time">' + value + '</span>';
                    return '<span>'+html+'</span>';
                }
            }
        },
        {
            title: '介绍人',
            field: 'introducer',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return '<span>'+html+'</span>';
                } else {
                    return "";
                }
            }
        },
        {
            title: '开发人',
            field: 'developer',
            align: 'center',
          
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return '<span>'+html+'</span>';
                } else {
                    return "";
                }
            }
        },
        {
            title: '建档人',
            field: 'jduser',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return '<span>'+html+'</span>';
                } else {
                    return "";
                }
            }
        },
        {
            title: '建档导医',
            field: 'jddy',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return '<span>'+html+'</span>';
                } else {
                    return "";
                }
            }
        },
        {
            title: '建档时间',
            field: 'jdtime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    html = '<span class="time" title="' + value + '">' + value + '</span>';
                    return '<span>'+html+'</span>';
                } else {
                    return "";
                }
            }
        },
        {
            title: '收费人',
            field: 'sfuser',
            align: 'center',
            formatter: function(value, row, index) {
                if (value) {
                    html = '<span class="name" title="' + value + '">' + value + '</span>';
                    return '<span>'+html+'</span>';
                } else {
                    return "";
                }
            }
        },
        {
            title: '收款备注',
            field: 'remark',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    html = '<span class="time">' + value + '</span>';
                    return '<span>'+html+'</span>';
                }
            }
        }]
    },
    {
        title: '修改缴费备注',
        field: 'detailremark',
        align: 'center',
        sortable: true,
        formatter: function(value, row, index) {
            if (value != "" && value != null) {
                html = '<span class="time">' + value + '</span>';
                return '<span>'+html+'</span>';
            }
        }
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        //showpersoninfo(onclickrowOobj);//展示右侧个人信息
        $('#tabIframe').attr('src', $('#tabIframe').attr('src')); //个人中心
    });
}
function queryParams() {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		offset : 0,
        limit :	 pagesize,
        ispaging : 1,	
        organization: $('#organization').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        zlstarttime: $('#zlstarttime').val(),
        zlendtime: $('#zlendtime').val(),
        zlstatus: $('#zlstatus').val(),
        basetype: $('#basetype').val(),
        nexttype: $('#nexttype').val(),
        itemname : $('#itemname').val(),
        regdept:$('#regdept').val(),
        doctor: $('#doctor').val(),
        askperson: $('#askperson').val(),
        shouli: $('#shouli').val(),
        gongju: $('#gongju').val(),
        persontype:0,
        //咨询
        createuser: $('#createuser').val(),
        //建档人
        introducer: $('#introducer').val(),
        kfr:$('#kfr').val(),
        //开发人
        devchannel: $('#devchannel').val(),
        //患者来源
        nexttype1: $('#nexttype1').val(),
        //子分类
        remark : $('#remark').val(),
        queryinput: $("#queryinput").val(),
        usercodes:usercodes
    };
    if(nowday!=null){
    	temp.starttime = nowday;
    	temp.endtime = nowday;
    }
    return temp;
}
$('#query').on('click',
function() {
	loadedData = [];
	nowpage = 0;
	nowday = null;
	if (!YzSearchValue("conditions")) {
        layer.alert('请选择查询条件!' );
        return false;
    }
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
    $(".searchSelect li.selected").empty();//清空
    $('.searchSelect').selectpicker("refresh");//初始化刷新搜索--2019.11.14--licc
});
//导出
function exportTable() {
    var fieldArr = [];
    var fieldnameArr = [];
    $('#table thead tr th').each(function() {
        var field = $(this).attr("data-field");
        if (field != "") {
            fieldArr.push(field); //获取字段
            fieldnameArr.push($(this).children()[0].innerText); //获取字段中文
        }
    });
    var param = JsontoUrldata(queryParams());
    location.href = pageurl + "?flag=exportTable&fieldArr=" + JSON.stringify(fieldArr) + "&fieldnameArr=" + JSON.stringify(fieldnameArr) + "&" + param;
}
</script>
</html>
