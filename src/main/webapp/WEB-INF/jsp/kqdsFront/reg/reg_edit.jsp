<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>挂号修改</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/reg/reg.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css"/>
<!-- select搜索筛选 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<style>
.reg_editPage .kqdsCommonBtn{
	margin-left:15px;
}
.fixed-table-container{
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
	border-bottom:1px solid #ddd;
	border-radius: 6px;
	/* border-top-left-radius: 6px;
	border-top-right-radius: 6px; */
	overflow: hidden;
}

	.askperson:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
       width: 130px;   
      }  
    .doctor:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
       width: 100px;   
      } 
    .repairDoc:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
       width: 130px;   
      }   
	.askperson>.btn { 
	    width: 130px; 
	 	height:26px; 
	 	border: solid 1px #e5e5e5; 
	}
	.doctor>.btn{
		width: 100px;
	    height:26px;
	    border: solid 1px #e5e5e5; 
	}
	.repairDoc>.btn{
		width: 130px;
	    height:26px;
	    border: solid 1px #e5e5e5; 
	}
	.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
	    color: #999;
	    height: 26px;
	}
	.pull-left {
    float: left !important;
    color: #333;
	}
	.btn-group > .btn:first-child:hover {
	    background: white;
	}
	input[type="text"].queryInp2 {
	    width: 200px;
	    border-top-left-radius: 0;
	    border-bottom-left-radius: 0;
	/*     border-left-color: rgba(255,255,255,0); */
	    float: left;
	    color: #666;
	}
</style>
</head>
<body>
	<div class="content" style="padding:18px 18px 0 18px;"><!--容器框 增加了些内边距 -->
		<header   style="display: none;">
			<table class="tableLayout"><!--.tableLayout布局 宽度撑满父元素  -->
				<tbody>
					<tr>
						<td colspan="8">
							<select id="searchField" class="querySel" ><!-- .querySel查询中 的下拉框  -->
								<option value="username">姓名</option>
								<option value="PhoneNumber1">手机号码</option>
								<option value="idcardno">身份证号</option>
								<option value="usercode">患者编号</option>
							</select>
							<input class="queryInp2" type="text" id="searchValue" name="searchValue" placeholder="患者姓名/手机号/患者编号"/>	<!--.queryInp查询中的Input样式  -->
							<a id="searchHzda" href="javascript:void(0);" class="kqdsSearchBtn queryBtn" onclick="searchHzda()">查 询</a>	<!-- .btnCommon自定义按钮通用样式 .queryBtn查询按钮样式的调整 -->
						</td>
					</tr>
				</tbody>
			</table>
		</header>
		
		<section>	<!-- 中间部分 table显示与操作区 -->
			<span class="tableTitle">查询清单</span><!--.tableTitle查询清单字样的样式  -->
			<table id="table" class="table-striped table-condensed table-bordered" data-height="180"></table> <!--无自定义样式 此行样式为框架样式   -->
			<div class="operatedDiv">	<!-- 所有下拉框的父元素 用来布局 -->
				<form id="form">
					<table class="tableLayout">	<!--.operatedDiv下的 .tableLayout有自己特有的样式-->
						<tbody>
							<tr>
								<td>
									<input type="hidden" id="seqId" name="seqId"/>
									<input type="hidden" id="status" name="status"/>
									<input type="hidden" id="cjstatus" name="cjstatus"/>
									<input type="hidden" id="usercode" name="usercode"/>
									<input type="hidden" id="username" name="username"/>
									<input type="hidden" id="ifcost" name="ifcost"/>
									<input type="hidden" id="ifmedrecord" name="ifmedrecord"/>
									<input type="hidden" id="receiveno" name="receiveno"/>
									<input type="hidden" id="del" name="del"/>
					            	<!--.commonText“就诊分类”文本的样式 提供布局  -->	<!--.colorRed 星号为红色及位置调整 -->
									<span class="commonText"><i class="colorRed">*</i>就诊分类：</span>	
								</td>
								
								<td>	<!--.dict 本身无样式 与载入数据功能有关 -->
									<select class=" dict" tig="jzfl" name="recesort" id="recesort"  data-bv-notempty data-bv-notempty-message="就诊分类不能为空">
		               				</select>
								</td>
								
								<td>	<!--.commonText“就诊分类”文本的样式 提供布局  -->
									 <span class="commonText"><i class="colorRed">*</i>挂号分类：</span>
								</td>
								
								<td>	<!--.dict 本身无样式 与载入数据功能有关 -->	
									<select class="dict" tig="ghfl" name="regsort" id="regsort" data-bv-notempty data-bv-notempty-message="挂号分类不能为空">
		                			</select>
								</td>
								
								<td> 
									 <span class="commonText"><i class="colorRed">*</i>挂号方式：</span>
								</td>
								
								<td>
									<select class="dict" tig="ghfs" name="regway" id="regway" data-bv-notempty data-bv-notempty-message="挂号方式不能为空">
									</select>
								</td>
								
							</tr>
							
							<tr>
								<td>
									 <span class="commonText"><i class="colorRed">*</i>挂号金额：</span>
								</td>
								
								<td>
									<input type="text" name="regmoney" id="regmoney" readonly data-bv-notempty data-bv-notempty-message="挂号金额不能为空"/>
								</td>
								<td>
									<span class="commonText"><i class="colorRed">*</i>咨询部门：</span>
								</td>
								<td>
									<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_0 %>" name="askpersondept" id="askpersondept" data-bv-notempty data-bv-notempty-message="咨询部门不能为空">
									</select>
								</td>
								<td>
									<span class="commonText"><i class="colorRed">*</i>咨&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;询：</span>
								</td>
								<td>
								<select class="select2 askperson" name="askperson" id="askperson" title="请选择" data-live-search="true"></select>
<!-- 		第2加select2					<select id="askperson" name="askperson" class="askperson"  data-live-search="true" title="请选择"></select> -->
<!-- 									<select name="askperson" id="askperson" > -->
<!-- 									</select> -->
								</td>
								
							</tr>
							<tr>
								<td>
									<span class="commonText">就诊科室：</span>
								</td>
								<td><!-- dept 本身不含样式  有载入选项的功能 -->
									<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1 %>" name="regdept" id="regdept" data-bv-notempty data-bv-notempty-message="就诊科室不能为空">
									</select>
								</td>
								<td>
									<span class="commonText">医&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;生：</span>
								</td>
								<td class="doctorText">	<!--.doctorText 医生 下的元素需要调整样式   -->
								 <select id="doctor" name="doctor" tig="" class="doctor"  data-live-search="true" title="请选择"></select>
<!-- 									<select  name="doctor" id="doctor"> -->
<!-- 									</select> -->
									<input type="hidden" name="checkperson" id="checkperson" placeholder="医生" title="医生"/> 
									<input type="hidden" onchange="changeAskPerson()" id="checkpersonDesc" name="checkpersonDesc" placeholder="医生" readonly ></input>	
									<a onClick="javascript:select()">&nbsp;高级</a>
								</td>
								<td>
									<span class="commonText">挂号导医：</span>
								</td> 
								<td>
									<input type="hidden" name="receiveno" id="receiveno"/> 
									<!-- .whiteInp 提供鼠标移入的小手效果  -->
									<input class="whiteInp" type="text" id="receivenoDesc" name="receivenoDesc" placeholder="挂号导医" readonly onClick="javascript:single_select_user(['receiveno', 'receivenoDesc'],'single');">
								</td>
							</tr>
							<tr>
								<td> 
									<span class="commonText">修复医生：</span>
								</td>
								<td>
									<select id="repairdoctor" name="repairdoctor" tig="" class="dict repairDoc"  data-live-search="true" title="请选择"></select>
<!-- 									<select class="dict" name=repairdoctor id="repairdoctor"></select> -->
								</td>
								<td class="editReason" colspan="8"><!--.editReason此行为1列   -->
									<span class="commonText"><i class="colorRed">*</i>修改/撤销原因：</span>
									<input class="editReasonInp" type="text" name="editreason" id="editreason" data-bv-notempty data-bv-notempty-message="修改/撤销原因不能为空" />
								</td>
							</tr>
						</tbody>
					</table>
				</form>	
			</div>
		</section>
		
		<footer class="reg_editPage" id="bottomBarDdiv" style="text-align:center;"> <!-- .reg_editPage调整footer的上内边距 -->
			<!-- .clear2 本身无样式 -->
			<div class="clear2"></div>
			<!-- 这里js生成按钮 -->
		</footer>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var listbutton = window.parent.listbutton; //父页面传值
var pageurl = '<%=contextPath%>/KQDS_REGAct/selectWithNopageReg.act';
var frameindex = parent.layer.getFrameIndex(window.name);
<%-- var username = '<%=request.getAttribute("username")%>'; --%>//######获取当前选中的患者附上姓名
// 获取父页面选中的挂号记录
var onclickrowOobj = window.parent.onclickrowOobj;
$(function() {
	$("#searchValue").val(onclickrowOobj.username);
	// 已结账的部分数据不能修改
	if(onclickrowOobj.status != 0 && onclickrowOobj.status != 1){
		noEdit();
	}
    getButtonPower();
    initDictSelectByClass(); // 就诊分类、挂号分类、挂号方式
    initSysUserByDeptId($("#repairdoctor"),"repairdoctor");//初始化修复医生    
    initDeptSelectByTypesAndClass('<%=ChainUtil.getCurrentOrganization(request)%>');
    
 // 咨询部门下拉框，目前是通过 dept_type = '0'  查询获取的
<%--      initPersonSelectByDeptType("askperson","<%=ConstUtil.DEPT_TYPE_0 %>");  	 --%>
	initPersonSelectByDept("askperson","<%=ConstUtil.DEPT_TYPE_0 %>");
    // 咨询部门变动时，人员列表随之变动  syp 2019-8-24
//     selectChangeTwo("askpersondept", "askperson");
       selectChangeTwoSearch("askpersondept", "askperson");
//     initSysUserByDeptId($("#askperson"),"consultation");
    //默认选中免费挂号  触发change事件 给挂号金额赋值
    $("#regway option:eq(0)").remove(); // 移除第一个 请选择选项
    $("#regway option:eq(0)").prop("selected", 'selected');
    $('#regway').change();
    
    //selectChangeTwo("regdept", "doctor");
    selectChangeTwoSearch("regdept", "doctor");
    initTableLsit();
    
    $('.repairDoc').selectpicker("refresh");//修复医生搜索初始化刷新--2019-10-19 licc
    $('.askperson').selectpicker("refresh");//咨询部门初始化刷新--2019-10-19 licc
});

//初始化医生、咨询--2019-10-19 licc
function initPersonSelectByDept(id, depttype) {
    var dict = $("#" + id);
    var url = contextPath + "/YZPersonAct/getPersonListByDeptType.act?depttype=" + depttype;
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null && list.length > 0) {
            dict.empty();
//             dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.userName + "</option>");
            }
            $("#"+id).selectpicker("refresh"); //select搜索初始化刷新
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}

function noEdit(){
	$("#recesort").attr("disabled","disabled");
	$("#regsort").attr("disabled","disabled");
	$("#askperson").attr("disabled","disabled");
	$("#receivenoDesc").attr("disabled","disabled");
}
function qxDisable(){
	$("#recesort").removeAttr("disabled");
	$("#regsort").removeAttr("disabled");
	$("#askperson").removeAttr("disabled");
	$("#receivenoDesc").removeAttr("disabled");
}
/**
 * 就诊分类
 * 如果是初诊，则就诊分类下拉框只显示 初诊选项
 * 如果不是初诊，则不显示初诊选项
 */
$('#recesort').on('change',
	function() {
	
	if(!onclickrowOobj.usercode){
		layer.alert('请选择患者', {
			  
		});
		return false;
	}
	
    var ischuzhen = true;
    // 判断当前挂号是否是初诊挂号
    var url = '<%=contextPath%>/KQDS_REGAct/isFirstReg.act?usercode=' + onclickrowOobj.usercode + '&seqId=' + onclickrowOobj.seqId;
    $.axse(url, null,
    function(data) {
		ischuzhen = data.data;
    },
    function() {
        layer.alert('查询出错！'  );
    });
    
    if(ischuzhen){ // 如果是初诊，但是挂号没选初诊
    	// $("#recesort option:not(':eq(1)')").each(function(){
   		$("#recesort option").each(function(){
   			if($(this).html() != '初诊'){
   				$(this).remove();
   			}
		});
    }else{
    	$("#recesort option").each(function(){
   			if($(this).html() == '初诊'){
   				$(this).remove();
   			}
		});
    }
    
    
    return false;
    
});

//页面人员高级选择
function select() {
    //获取部门的所有id
    var jzks = $("#regdept option").map(function() {
        return $(this).val();
    }).get().join(",");
    jzks = jzks.replace(/^,+|,+$/g, '');
    deptid_select_user(['checkperson', 'checkpersonDesc'], jzks);
}

/**
 * 通过点击 医生后面的 高级 超链接，选择医生后
 * 1、就诊科室根据选择的医生联动
 * 2、给医生字段赋值
 */
function changeAskPerson() {
    var checkerson = $("#checkperson").val();
    if (checkerson) {
        //获取用户部门id
        var url = contextPath + "/YZPersonAct/getDeptByUserSeqId.act?seqId=" + checkerson;
        $.axse(url, null,
        function(data) {
            if (data.retState == "0") {
                var val = data.retData;
                //就诊科室赋值
                $("#regdept").val(val);
                if($("#regdept").val()==null){
              	  $("#regdept").val(data.xy_dept);
                }
                //手动触发 下拉选change事件
                $("#regdept").trigger("change");
                //人员下拉选赋值
//                 $("#doctor").val(checkerson);
                $('#doctor').selectpicker('val', checkerson);
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }else{
	  $("#regdept").val("");
	  $("#doctor").empty();
  }
}

function searchHzda() {
    var searchField = $("#searchField").val();
    var searchValue = $("#searchValue").val();
    if ($.trim(searchValue) == "") {
        layer.alert('请输入查询条件' );
        return false;
    }
    $('#table').bootstrapTable('refresh', {
        'url': pageurl + '?searchField=' + searchField + '&searchValue=' + searchValue
    });
}

function initTableLsit(){
	//1、表格初始化
    var url = "";
    if (onclickrowOobj != "" && onclickrowOobj.recesort != "" && onclickrowOobj.regway != "") {
        url = "<%=contextPath%>/KQDS_REGAct/selectOneByregno.act?seqId=" + onclickrowOobj.seqId + "";
    } else {
        url = pageurl;
    }
    $('#table').bootstrapTable({
        url: url,
        dataType: "json",
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
//         	console.log("挂号信息-=-=-"+JSON.stringify(data));
            // 根据父页面点击选择获取的挂号记录对象，初始化挂号信息
        	ghxx();
        	// --------------------方法块，不要拆分 -------------------/
            initDictSelect("recesort","jzfl"); // 必须请求方式为同步
         	// 根据患者是否是初次挂号，动态调整挂号分类
            $('#recesort').trigger("change");
            
         	// 编辑时，赋默认值
            if(onclickrowOobj.recesort){
        		$("#recesort").val(onclickrowOobj.recesort);
            }
         	// --------------------方法块，不要拆分 -------------------/
        },
        columns: [{
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '病人编号',
            field: 'usercode',
            align: 'center',
            valign: 'middle',
            visible: true,
            switchable: false
        },
        {
            title: '姓名',
            field: 'username',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '状态',
            field: 'cjstatus',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                var html = "";
                if (value == "0") {
                    html = '<span class="label label-danger">未成交</span>';
                } else if (value == "1") {
                    html = '<span class="label label-success">已成交</span>';
                }
                return html;
            }
        },
        {
            title: '就诊分类',
            field: 'recesort',
            align: 'center',
            valign: 'middle',
            formatter: function(value, row, index) {
                getValueDict("table" + index + "recesort", value);
                return "<span id='table" + index + "recesort' ></span>";
            }
        },
        {
            title: '挂号金额',
            field: 'regmoney',
            align: 'center',
            valign: 'middle'
        },
        {
            title: '就诊科室',
            field: 'regdept',
            align: 'center',
            formatter: function(value, row, index) {
            	 if(value != null && value != ''){
            		 getValueDept("table" + index + "regdept", value);
                     return "<span id='table" + index + "regdept' ></span>";
        		 }else{
        			 return '';
        		 }
            	
               
            }
        },
        {
            title: '咨询',
            field: 'askperson',
            align: 'center',
            formatter: function(value, row, index) {
                var pjid = "table" + index + "askperson";
                bindPerUserNameBySeqIdYB(pjid, value);
                return "<span id='" + pjid + "' class='name'></span>";
            }
        },
        {
            title: '医生',
            field: 'doctor',
            align: 'center',
            formatter: function(value, row, index) {
                var pjid = "table" + index + "doctor";
                bindPerUserNameBySeqIdYB(pjid, value);
                return "<span id='" + pjid + "' class='name'></span>";
            }
        },
        {
            title: '挂号方式',
            field: 'regway',
            align: 'center',
            formatter: function(value, row, index) {
                getValueDict("table" + index + "regway", value);
                return "<span id='table" + index + "regway' ></span>";
            }
        },
        {
            title: '挂号分类',
            field: 'regsort',
            align: 'center',
            formatter: function(value, row, index) {
                getValueDict("table" + index + "regsort", value);
                return "<span id='table" + index + "regsort' ></span>";
            }
        },
        {
            title: '挂号时间',
            field: 'createtime',
            align: 'center',
            sortable: true,
            width: 120,
            formatter: function(value, row, index) {
                html = '<span class="time">' + value.substring(5) + '</span>';
                return html;
            }
        }]
    });
    $('#table').on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrowOobj = $('#table').bootstrapTable('getData')[index];
        
     	// 根据父页面点击选择获取的挂号记录对象，初始化挂号信息
        ghxx();
     	// --------------------方法块，不要拆分 -------------------/
        initDictSelect("recesort","jzfl"); // 必须请求方式为同步
     	// 根据患者是否是初次挂号，动态调整挂号分类
        $('#recesort').trigger("change");
        
     	// 编辑时，赋默认值
        if(onclickrowOobj.recesort){
    		$("#recesort").val(onclickrowOobj.recesort);
        }
        if(onclickrowOobj.status != 0 && onclickrowOobj.status != 1){
    		noEdit();
    	}else{
    		qxDisable();
    	}

     	// --------------------方法块，不要拆分 -------------------/
    });
}

// 获取挂号信息
function ghxx() {
	
	// 根据父页面选中的挂号记录对象，初始化挂号修改页面的表单html元素
    loadDataSelect(onclickrowOobj); /** loadData为公用方法 **/
    
    /**
     * ######################################### 有以下几种方式进入该系统 #############################################
     * 1、接待中心，点击挂号按钮， 此时，Url没有传值
     * 2、预约挂号：
     *   1) 点击门诊预约记录，进入当前页面，此时，传值usercode
     *   2) 点击网电预约记录，进入当前页面，此时，传值usercode 和 网电预约记录的主键
     * 3、患者建档完成后，进入当前页面，此时，传值usercode
     * ########################################## 不管是哪种方式进入当前页面，进行挂号前，都会执行到此处 ！！！
     */
    <%-- var doc = getHzNameByusercodeTB(onclickrowOobj.usercode);
	if(doc.askperson || onclickrowOobj.askperson){ // 如果该患者档案的原始咨询已经设定，则不允许修改### 此需求待确定，因为一旦这样做之后，如果当前咨询不在班，则无法进行咨询了！！！
		$("#askperson").val(onclickrowOobj.askperson);
			
	    var ischuzhen = true;
	    // 判断当前挂号是否是初诊挂号
	    var url = '<%=contextPath%>/KQDS_REGAct/isFirstReg.act?usercode=' + onclickrowOobj.usercode + '&seqId=' + onclickrowOobj.seqId;
	    $.axse(url, null,
	    function(data) {
			ischuzhen = data.data;
	    },
	    function() {
	        layer.alert('查询出错！', {
	              
	        });
	    });
	    
	    if(ischuzhen){
	    	$("#askperson").removeAttr("disabled");
	    }else{
	    	$("#askperson").attr("disabled","disabled");
	    }
	}
	
    if(!doc.askperson){
    	$("#askperson").removeAttr("disabled");
    }else{
    	$("#askperson").attr("disabled","disabled");
    } --%>
// 	if(onclickrowOobj.askpersondept){
// 		$("#askpersondept").val(onclickrowOobj.askpersondept);
// 	}
	if(onclickrowOobj.doctor){
		$("#checkperson").val(onclickrowOobj.doctor);
		$('#checkpersonDesc').trigger("change");
	}else{
		$("#checkperson").val("");
		$('#checkpersonDesc').trigger("change");
	}
	
	if(onclickrowOobj.receiveno){ // 该字段存挂号导医，不选择就不赋值，数据库也不存值
		bindPerUserNameBySeqIdTB("receivenoDesc", onclickrowOobj.receiveno);
	}
	
	/** 注释掉下面这个判断，因为存在一个患者需要多个医生治疗的情况，这时可通过修改挂号实现  2018-2-7 **/
	/* if(onclickrowOobj.status == 1){ // 如果挂号单的状态为等待结账，那么只能改挂号分类、就诊分类和修改撤销原因
		$(".isDDZL").hide();
	}else{ // 等待治疗
		$(".isDDZL").show();
	} */
	
}

//根据父页面选中的挂号记录对象，初始化挂号修改页面的表单html元素
function loadDataSelect(jsonStr) {
	//console.log(jsonStr);
    var obj = jsonStr;
    var key, value, tagName, type, arr, styles;
    for (x in obj) {
        key = x;
        value = obj[x];
        $("[name='" + key + "'],[name='" + key + "[]']").each(function() {
            tagName = $(this)[0].tagName;
            styles = $(this).attr('class');
            type = $(this).attr('type');
            if (tagName == 'INPUT') {
                if (type == 'radio') {
                    $(this).prop('checked', $(this).val() == value);
                } else if (type == 'checkbox') {
                    if (value) {
                        arr = value.toString().split(',');
                        for (var i = 0; i < arr.length; i++) {
                            if ($(this).val() == arr[i]) {
                                $(this).attr('checked', true);
                                break;
                            }
                        }
                    }
                } else if (0) {
                    // 判断是否是选人自动填充
                } else {
                    $(this).val(value);
                }
            } else if (tagName == 'SELECT' || tagName == 'TEXTAREA') {
                if (new RegExp("select2").test(styles)) {
                    $(this).val(value).trigger("change");
                    $(this).selectpicker('val', value);
                } else {
                    $(this).val(value);
                }
            }
        });
        // 2017-9-6 增加 html的赋值  yangsen
        if($("#" + key).attr("id")){
        	var tagName = $("#" + key)[0].tagName;
        	if(tagName == 'SPAN' || tagName == 'TD' || tagName == 'DIV' || tagName == 'I' ){
        		$("#" + key).html(value);
        	}
        }
    }
}

//更改挂号
function updateSubmit() {
    if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
        layer.alert('请选择患者' );
        return false;
    }
    var data = $('#form').data('bootstrapValidator');
    var jzfl = $("#recesort").val();
    if (jzfl == "") {
        layer.alert('请选择就诊分类' );
        return false;
    }
    var regsort = $("#regsort").val();
    if (regsort == "") {
        layer.alert('请选择挂号分类' );
        return false;
    }
    var regway = $("#regway").val();
    if (regway == "") {
        layer.alert('请选择挂号方式' );
        return false;
    }
    var regmoney = $("#regmoney").val();
    if (regmoney == "") {
        layer.alert('挂号金额不能为空' );
        return false;
    }
    var askperson = $("#askperson").val();
    var doctor = $("#doctor").val();
    if ((askperson == "" || askperson == null) && (doctor == "" || doctor == null)) {
        layer.alert('请选择咨询或医生' );
        return false;
    }
    var editreason = $("#editreason").val();
    if (editreason == "") {
        layer.alert('修改/撤销原因不能为空' );
        return false;
    }
    //移除只读属性 不然后台取不到值
	$("#askperson").removeAttr("disabled");
    var param = $('#form').serialize();
    var url = '<%=contextPath%>/KQDS_REGAct/insertOrUpdate.act?' + param;
    var msg;
    $.axseSubmit(url, null,
    function() {
        msg = layer.msg('加载中', {
            icon: 16
        });
    },
    function(r) {
        layer.close(msg);
        if (r.retState == "0") {
            layer.alert('更改成功', {
                  
                end: function() {
                    parent.initclick();
                    parent.layer.close(frameindex);
                }
            });
        } else {
            layer.alert('更改失败'  );
        }
    },
    function() {
        layer.alert('更改失败' );
    });
}
//修改患者资料
$('#editUserDocument').on('click',
function() {
    if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
        layer.alert('请选择患者' );
        return false;
    }
    layer.open({
        type: 2,
        title: '修改患者资料',
        shadeClose: true,
        shade: 0.6,
        area: ['750px', '90%'],
        content: contextPath + '/KQDS_UserDocumentAct/toHzjd_Edit.act?usercode=' + onclickrowOobj.usercode 
    });
});

//撤销挂号
function delSubmit() {
    if (checkIfCanDelReg()) {
        var seqId = $("#form input[name=seqId]").val();
        layer.confirm('确定撤销？', {
            btn: ['撤销', '放弃'] //按钮
        },
        function() {
            var editreason = $("#editreason").val();
            if (editreason == "") {
                layer.alert('修改/撤销原因不能为空', {
                      
                });
                return false;
            }
            var param = $('#form').serialize();
            var url = '<%=contextPath%>/KQDS_REGAct/cancelReg.act?' + param;
            msg = layer.msg('加载中', {
                icon: 16
            });
            $.axse(url, null,
            function(data) {
                if (data.retState == "0") {
                    layer.alert('撤销成功', {
                          
                        end: function() {
                            $('#table').bootstrapTable('refresh', {
                                'url': pageurl
                            });
                            parent.initclick();
                            parent.layer.close(frameindex);
                        }
                    });
                }
            },
            function() {
                layer.alert('撤销失败！', {
                      
                });
            });
        });
    }
}
function checkIfCanDelReg() {
    var flag = true;
    var checkCost = contextPath + '/KQDS_REGAct/checkIfCanDelReg.act?regno=' + onclickrowOobj.seqId + '&usercode=' + onclickrowOobj.usercode;
    $.axse(checkCost, null,
    function(data) {
        if (data.data > 0) {
            layer.alert(data.retMsrg, {
            }); //图标  0-6   0:感叹号   2 错误    6 笑脸,成功    //http://www.layui.com/doc/modules/layer.html#icon
            flag = false;
        }
    },
    function() {
        flag = false;
    });
    return flag;
}

$('#regway').on('change',
	function() {
    var value = $("#regway").find("option:selected").text();
    if (value.indexOf("[") < 0) {
        $('#regmoney').val(0);
    } else {
        value = value.substring(value.indexOf("[") + 1, value.indexOf("]"));
        $('#regmoney').val(value);
    }
});

function getButtonPower() {
    var menubutton1 = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "reg_edit") {
            menubutton1 += '<a class="kqdsCommonBtn" onclick="updateSubmit();">更改挂号</a>';
        } else if (listbutton[i].qxName == "reg_del") {
            menubutton1 += '<a class="kqdsCommonBtn" onclick="delSubmit();">撤销挂号</a>';
        } else if (listbutton[i].qxName == "reg_update_jurisdiction") {
            updateJurisdiction();
        }
    }
    $("#bottomBarDdiv").append(menubutton1);
}
function updateJurisdiction(){
    $("#recesort").attr("disabled","disabled");
    $("#regsort").attr("disabled","disabled");
    $("#regway").attr("disabled","disabled");
    $("#askpersondept").attr("disabled","disabled");
    $("#askperson").attr("disabled","disabled");
    $("#receivenoDesc").attr("disabled","disabled");
}
</script>
</html>
