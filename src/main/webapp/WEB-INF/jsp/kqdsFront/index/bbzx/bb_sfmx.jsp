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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jzzx_zxzx_ylzx_union.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<!-- select搜索筛选 -->
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
<!-- select搜索筛选 -->
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>

</head>
<style type="text/css">
#gongzuol{
	margin:0;
}
 	.searchWrap .kqds_table{
		width:90%;
		align:center;
		margin-left: auto;
		margin-right: auto;
	}
	
	.kqds_table  td { 
		color: #666;
		padding: 1px 1px 1px 2px;
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
	input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:90px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
	.btnBar .aBtn{
		position:relative;
		top:-2px;
	}
	.fixed-table-container{
		border:none;
	}
	.listWrap .listBd{
		border:none;
	}
	.pagination li{
		margin-left: 0px!important;
		height:auto!important;
	}
	.dropdown-menu{
		min-width: auto!important;
	    padding: 0px 0!important;
	}
	.dropdown-menu li{
		margin-left: 0px!important;
	}
	.clearfix{
		border-bottom: 1px solid #ddd;
	}
	
	/* 搜索框select */
	.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
	       width: 110px;   
	      }  
	.searchSelect>.btn { 
		    width: 110px; 
		 	height:28px; 
		 	border: solid 1px #e5e5e5;
		}
	.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
		    color: #999;
		    height: 28px;
		}
	.pull-left {
	    float: left !important;
	    color: #666;
/* 	    margin-top: -2px; */
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
	.searchWrap , .formBox{
     overflow: visible; 
	}
	.kqds_table td{
		overflow:visible;
	}	
	
</style>

<body>
<div id="container">
    <div class="main">
        <div class="listWrap">
            <div class="columnHd">
			    <span class="title">明细查询</span>
			</div>
            <div class="listBd">
                <div class="tableBox">
                    <table id="table" class="table-striped table-condensed table-bordered" >
                    </table>
                </div>
                <div id="gongzuol">
	                <div class="columnBd">
	                	<ul class="dataCountUl" id="dataCount">
		               		<li>小计:<span id="xiaoji">0</span></li>
		               		<li>免除:<span id="mian">0</span></li>
		               		<li>应收:<span id="ying">0</span></li>
		               		<li>欠款:<span id="qian">0</span></li>
		               		<li>赠送使用:<span id="zeng">0</span></li>
		               		<li>收款:<span id="xian">0</span></li>
		               		<li><%=SysParaUtil.getFkfsNameByCostField(request, "paydjq") %>:<span id="djq">0</span></li>
		               		<li>积分:<span id="integral">0</span></li>
		               		<li>预交金退费:<span id="return">0</span></li>
		               	</ul>
	                </div>
	            </div>
            </div>
        </div>
        <!--查询条件-->
        <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            
                <div class="formBox">
	                   <table class="kqds_table">
				    		<tr>
				    			<td style="text-align:right;">所属门诊：</td>
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
								<td style="text-align:right;">建档人：</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="createuser" id="createuser"  class="form-control"  value=""/>
									     <input  type="text"  id="createuserDesc" name="createuserDesc" placeholder="建档人" readonly  onClick="javascript:single_select_user(['createuser', 'createuserDesc'],'',1);"></input>	
				    			</td>
				    			<td style="text-align:right;">消费分类：</td>
				    			<td style="text-align:left;">
				    					<select id="basetype" name="basetype" tig="" class="select2 searchSelect"  data-live-search="true" title="请选择"></select>
<!-- 										<select class="select2"  name="basetype" id="basetype"> -->
<!-- 		                 	  			 </select> -->
								</td>
				    			
				    			<td style="text-align:right;">子分类：</td>
				    			<td style="text-align:left;">
				    					<select id="nexttype" name="nexttype" tig="" class="select2 searchSelect"  data-live-search="true" title="请选择"></select>
<!-- 				    					 <select class="select2"  name="nexttype" id="nexttype"> -->
<!-- 					                       		 <option value="">请选择</option> -->
<!-- 				                 	  	 </select> -->
				    			</td>
				    			<td style="text-align:right;">消费项目：</td>
				    			<td style="text-align:left;">
				    				<input type="text" placeholder="消费项目" id="itemname" name="itemname">
				    			</td>
				    		</tr>
				    		<tr>
				    			<td style="text-align:right;">治疗状态：</td>
				    			<td style="text-align:left;">
										<select id="zlstatus" name="zlstatus">
											<option value="" selected="selected">请选择</option>
											<option value="未治疗">未治疗</option>
											<option value="已治疗">已治疗</option>
										</select>
								</td>
				    			<td style="text-align:right;">治疗时间：</td>
				    			<td style="text-align:left;"> 
				    					<span class="unitsDate">
				                            <input type="text" id="zlstarttime" name="zlstarttime" placeholder="开始日期" readonly class="birthDate">
				                        </span>
				                </td>
				    			
				    			<td style="text-align:right;">到：</td>
				    			<td style="text-align:left;">
										<span class="unitsDate">
				                            <input type="text" id="zlendtime" name="zlendtime" placeholder="结束日期" readonly class="birthDate">
				                        </span>
								</td>
				    			<td style="text-align:right;">就诊分类：</td>
				    			<td style="text-align:left;">
				    				<select class=" dict" tig="jzfl" name="recesort" id="recesort"  data-bv-notempty data-bv-notempty-message="就诊分类不能为空">
			                		</select>
				    			</td>
				    			<td style="text-align:right;">挂号分类：</td>
				    			<td style="text-align:left;">
				    				<select class=" dict" tig="ghfl" name="regsort" id="regsort">
			                		</select>
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
				    		</tr>
				    		<tr>
				    			<td style="text-align:right;">医生：</td>
				    			<td style="text-align:left;">
				    					 <input type="hidden" name="doctor" id="doctor"  class="form-control"  value=""/>
									     <input type="text"  id="doctorDesc" name="doctorDesc" placeholder="医生" readonly  onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'',1);"></input>	
				    			</td>
				    			<td style="text-align:right;">护士：</td>
				    			<td style="text-align:left;">
				    				 <input type="hidden" name="nurse" id="nurse"  class="form-control"  value=""/>
									     <input  type="text"  id="nurseDesc" name="nurseDesc" placeholder="护士" readonly  onClick="javascript:single_select_user(['nurse', 'nurseDesc'],'',1);"></input>	
				    			</td>
						    <%
							if(SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK).indexOf(SessionUtil.getLoginPerson(request).getUserPriv()) == -1) {
							%>
							
							<%}else{ %>
				    			<td style="text-align:right;">患者来源：</td>
				    			<td style="text-align:left;">
				    				<select id="devchannel" name="devchannel" tig="hzly" class="patients-source select2 dict searchSelect"  data-live-search="true" title="请选择" onchange="getSubDictSelectSearch('devchannel','nexttype1');"></select>
<!-- 				    				<select class="patients-source select2 dict" tig="hzly" name="devchannel" id="devchannel" onchange="getSubDictSelect('devchannel','nexttype1');"> -->
<!-- 									</select> -->
				    			</td>
				    			<td style="text-align:right;">子分类：</td>
				    			<td style="text-align:left;">
				    				<select id="nexttype1" name="nexttype1" tig="" class="select2 dict searchSelect"  data-live-search="true" title="请选择"></select>
<!-- 				    				<select class="select2 dict" name="nexttype1" id="nexttype1"> -->
<!-- 										<option value="">请选择</option> -->
<!-- 									</select> -->
				    			</td>
				    		<%} %>   
				    			<td style="text-align:right;">收款备注：</td>
				    			<td style="text-align:left;">
				    				<input type="text" placeholder="收款备注" id="remark" name="remark" >
				    			</td>
				    			<td style="text-align:right;">模糊查询：</td>
				    			<td style="text-align:left;">
				    				<input type="text" placeholder="患者编号/姓名/手机号" id="queryinput" name="queryinput">
				    			</td>
				    		</tr>
				    	</table>
	                    <div class="btnBar" style="text-align: center;">
	                    	<a href="javascript:void(0);" class="kqdsCommonBtn" id="clean">清空</a>
			                <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			            </div>
	                </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var $table = $('#table');
var pageurl = '<%=contextPath%>/KQDS_CostOrder_DetailAct/getAll.act';
var nowday;
var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
var menuid = "<%=menuid%>";
var qxnameArr = ['bbzx_xfmx_scbb'];
var func = ['exportTable'];

//登录权限licc--2020-1-8
var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
var allowed=allowedperson.split(","); //允许权限

//判断当前人员权限是否有查看患者来源和子分类等着资源
var total=load.concat(allowed);
function isExist(total) {
    var exist = {};
    for(var i in total) {
        if(exist[total[i]]) {
            return true;
        }
        exist[total[i]] = true;
    }
    return false;
}

$(function() {
	initHosSelectListNoCheck('organization');// 连锁门诊下拉框
    //initHosSelectList4Front('organization'); // 连锁门诊下拉框	
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
    
    $(window).resize(function() {
        setWidth();
        setHeight();
        /*滚动条出现时，调整表头  */
        setTableHeaderWidth(".tableBox");
    });
    $('.searchSelect').selectpicker("refresh");//初始化刷新--2019-10-29 licc    
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

    var baseHeight = $(window).height() - 15,
    optAreaHeight = $('.searchWrap').outerHeight();
    $("#table").bootstrapTable("resetView",{
    	height:$(window).outerHeight()-$(".listWrap .columnHd").outerHeight()-$(".searchWrap").outerHeight()-$("#gongzuol").outerHeight()-20
    });
    $('.tabIframe').height(baseHeight - 50);

}
$('#basetype').change(function() {
	if($(this).val()){ // 如果一级有值，再请求
// 		initCostSortSelect2Level('nexttype',this.value);
		initCostSortSelect2LevelSearch('nexttype',this.value);
	    $('.searchSelect').selectpicker("refresh");//初始化刷新--2019.10.29--licc
	}
});

//已结账
function getPayOrderlist(url) {
	/*wl----首次加载时 计算table高度————————————结束  */
	
    //$(".tableBox").html('<table id="table" class="table-striped table-condensed table-bordered" data-height="370"></table>');
    //$('#table').bootstrapTable('refresh',{'url':pageurl});
    $('#table').bootstrapTable({
        url: url,
        dataType: "json",
        queryParams: queryParamsB,
        pagination: true,//是否显示分页（*）
        pageSize: 25,
        pageList : [10, 15, 20, 25],//可以选择每页大小
        sidePagination: "server",//分页方式：client客户端分页，server服务端分页（*）
        paginationShowPageGo: true,
        onLoadSuccess: function(data) { //加载成功时执行
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
				$("#query").text("查询");
			}
//         	隐藏患者来源子分类
			var existornot=isExist(total);//资源隐藏判断条件ZY_LYCK
        	if(!existornot){
        		$('#table').bootstrapTable('hideColumn', 'devchannel');
        		$('#table').bootstrapTable('hideColumn', 'nexttype');
        	}else{
        	}  
            if(data.total>0){
       		    //maxpage = Math.floor(data.total/pagesize)+1; 
       		    $("#xiaoji").html(data.subtotal);
                $("#mian").html(data.voidmoney);
       		    $("#ying").html(data.ys);
                $("#xian").html(data.paymoney);
                $("#zeng").html(data.payother2);
                $("#qian").html(data.y2);
                $("#djq").html(data.paydjq);  
                $("#integral").html(data.payintegral); 
                $("#return").html(data.cmoney); 
	      	 }
             if(data.total == 0){
            	 $("#xiaoji").html("0");
                 $("#mian").html("0");
        		 $("#ying").html("0");
                 $("#xian").html("0");
                 $("#zeng").html("0");
                 $("#qian").html("0");
                 $("#djq").html("0");
                 $("#integral").html("0");
                 $("#return").html("0"); 
       		 }
	      	//分页加载
	      	//showdata("table",data.rows);
	      	//计算主体的宽度
	        //setWidth();
	        setHeight();
	      	/*滚动条出现时，调整表头  */
	        setTableHeaderWidth(".tableBox");
	        //付款方式赋值
	        getFkfsField();
        },
        rowStyle: function(row, index) {
            //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
            var strclass = "";
            if (row.istk == "1") {
                strclass = 'danger'; //还有一个active
            } else {
                return {};
            }
            return {
                classes: strclass
            };
        },
        columns: [{
            title : '序号',
            align: "center",
            width: 40,
            formatter: function (value, row, index) {
             /* return index + 1; */
             var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                if(row.itemname=='' || row.itemname==null){
		    		 return '<span>'+'Y_'+(pageSize * (pageNumber - 1) + index + 1)+'</span>';
		    	}else{
		    		 return '<span>'+(pageSize * (pageNumber - 1) + index + 1)+'</span>';
		    	}
            }
        },{
            title: '消费门诊',
            field: 'organization',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
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
                    return '<span>' + sftime + '</span>';
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
                return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '病历号',
            field: 'blcode',
            align: 'center',
            valign: 'middle',
            sortable: true,
            visible: false,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
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
                    return '<span>' + value + '</span>';
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
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
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
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
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
                    return '<span style="width:140px;text-align:left;" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
            }
        },
        {
            title: '单位',
            field: 'unit',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	if (value) {
            		return '<span>'+value+'</span>';
            	}else{
            		return '';
            	}
            	
            }
        },
        {
            title: '数量',
            field: 'num',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	if (value) {
            		return '<span>'+value+'</span>';
            	}else{
            		return '';
            	}
            	
            }
        },
        {
            title: '折扣',
            field: 'discount',
            align: 'center',
            
            sortable: true,
            formatter:function(value){
            	if (value) {
            		return '<span>'+value+'</span>';
            	}else{
            		return '';
            	}
            	
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
            	if(value){
            		return '<span class="money">' + value + '</span>';
            	}else {
                    return '<span class="money">0.0</span>';
                }
                
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
                } else {
                    return '<span class="money">0.0</span>';
                }
            }
        },
        {
            title: '欠费',
            field: 'y2',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if(value){
            		return '<span class="money">' + value + '</span>';
            	}else{
            		return '<span class="money">0.0</span>';
            	}
               
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
        {title: '代金券',field: 'paydjq',align: 'center',sortable: true,
			formatter:function(value,row,index){
				if(value==null){
					return '<span class="money">0.0</span>' ;
				}else{
					return '<span class="money">'+value+'</span>' ;
				}
			}
		},
		{title: '积分使用',field: 'payintegral',align: 'center',sortable: true,
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
            	if(value){
            		return '<span>'+value+'</span>';
            	}else{
            		return '';
            	}
            }
        },
        {
            title: '就诊分类',
            field: 'recesort',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if(value){
                	return '<span class="name" title="' + value + '">' + value + '</span>';
            	}else{
            		return '';
            	}
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
            	if(value){
            		return '<span>'+value+'</span>';
            	}else{
            		return '';
            	}
            }
        },
        {
            title: '第一咨询',
            field: 'faskperson',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '接诊咨询',
            field: 'askperson',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span class="name" title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '就诊科室',
            field: 'regdept',
            align: 'center',
            formatter:function(value){
            	if(value){
            		return '<span>'+value+'</span>';
            	}else{
            		return '';
            	}
            }
        },
        {
            title: '医生',
            field: 'doctor',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if(value){
            		return '<span class="name" title="' + value + '">' + value + '</span>';
            	}else{
            		return '';
            	}
                
            }
        },
        {
            title: '护士1',
            field: 'nurse',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if(value){
            		return '<span class="name" title="' + value + '">' + value + '</span>';
            	}else{
            		return '';
            	}
            }
        },
        {
            title: '护士2',
            field: 'techperson',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if(value){
            		return '<span class="name" title="' + value + '">' + value + '</span>';
            	}else{
            		return '';
            	}
            }
        },
        {
            title: '患者来源',
            field: 'devchannel',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return '<span class="source" title="' + value + '">' + value + '</span>';
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
                    return '<span class="source" title="' + value + '">' + value + '</span>';
                } else {
                    return '';
                }
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
                    return html;
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
                    html = '<span>' + value + '</span>';
                    return html;
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
                    return html;
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
                    return html;
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
                    return html;
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
                    return html;
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
                    html = '<span title="' + value + '">' + value + '</span>';
                    return html;
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
                    return html;
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
                    html = '<span class="remark">' + value + '</span>';
                    return html;
                }
            }
        },
        {
            title: '修改缴费备注',
            field: 'detailremark',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    html = '<span class="remark">' + value + '</span>';
                    return html;
                }
            }
        }]
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
function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
    	limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
        sortName:this.sortName,
        sortOrder:this.sortOrder,	
        organization: $('#organization').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        zlstarttime: $('#zlstarttime').val(),
        zlendtime: $('#zlendtime').val(),
        zlstatus: $('#zlstatus').val(),
        basetype: $('#basetype').val(),
        nexttype: $('#nexttype').val(),
        regdept:$('#regdept').val(),
        askperson: $('#askperson').val(),
        isyjjitem:'',
        //咨询
        doctor: $('#doctor').val(),
        //医生
        nurse: $('#nurse').val(),
        //护士1
        createuser: $('#createuser').val(),
        //建档人
        devchannel: $('#devchannel').val(),
        //患者来源
        nexttype1: $('#nexttype1').val(),
        //子分类
        recesort: $('#recesort').val(),
        //就诊分类
        regsort: $('#regsort').val(),
        //挂号分类
        remark : $('#remark').val(),
      	//消费项目
        itemname : $('#itemname').val(),
        queryinput: $("#queryinput").val()
    };
    if(nowday!=null){
    	temp.starttime = nowday;
    	temp.endtime = nowday;
    }
    return temp;
}

function queryParams() {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的	
        organization: $('#organization').val(),
        starttime: $('#starttime').val(),
        endtime: $('#endtime').val(),
        zlstarttime: $('#zlstarttime').val(),
        zlendtime: $('#zlendtime').val(),
        zlstatus: $('#zlstatus').val(),
        basetype: $('#basetype').val(),
        nexttype: $('#nexttype').val(),
        regdept:$('#regdept').val(),
        askperson: $('#askperson').val(),
        isyjjitem:'',
        //咨询
        doctor: $('#doctor').val(),
        //医生
        nurse: $('#nurse').val(),
        //护士1
        createuser: $('#createuser').val(),
        //建档人
        devchannel: $('#devchannel').val(),
        //患者来源
        nexttype1: $('#nexttype1').val(),
        //子分类
        recesort: $('#recesort').val(),
        //就诊分类
        regsort: $('#regsort').val(),
        //挂号分类
        remark : $('#remark').val(),
      	//消费项目
        itemname : $('#itemname').val(),
        queryinput: $("#queryinput").val()
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
	var starttime = $('#starttime').val();
	var endtime = $('#endtime').val();
	var zlstarttime = $('#zlstarttime').val();
	var zlendtime = $('#zlendtime').val();
	var zlstatus = $('#zlstatus').val();
	var basetype = $('#basetype').val();
	var nexttype = $('#nexttype').val();
    var askperson = $('#askperson').val();
    var regdept = $('#regdept').val();
    var doctor = $('#doctor').val();
    var createuser = $('#createuser').val();
    var devchannel = $('#devchannel').val();
    var nexttype = $('#nexttype').val();
    var recesort = $('#recesort').val();
    var regsort = $('#regsort').val();
    var remark = $('#remark').val();
    var itemname = $('#itemname').val(); // 消费项目
    var queryinput = $('#queryinput').val();
    if (zlstarttime == "" && zlendtime == "" && zlstatus == "" && regdept == "" && starttime == "" && endtime == "" &&  
    		basetype == "" && nexttype == "" && queryinput == "" && askperson == "" && doctor == "" && createuser == "" && 
    		devchannel == "" && recesort == "" && regsort == "" && remark == "" && itemname == "") {
        layer.alert('请选择查询条件!' );
        return false;
    }
	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(this).text("查询中");
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
});
$('#clean').on('click',
function() {
    $(".formBox :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
    $("#organization").val("<%=ChainUtil.getCurrentOrganization(request)%>").trigger("change");
	$(".searchSelect li.selected").empty();//清空
 	$('.searchSelect').selectpicker("refresh");//初始化刷新--2019.10.29--licc
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
