<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	  contextPath = "/kqds";
	}
  
	String usercode = request.getParameter("usercode");
	if(usercode == null){
		usercode="";
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
<title>网电信息</title>
<!-- 非客户管理进入的 右侧个人中心   网电信息 Tab -->
<!-- 客服中心—客户管理页面右侧的网电信息Url和其他页面不一样 该页面不可以进行保存操作 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/web_info.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<style>
.child-inline-block-top > * {
	display: inline-block;
	vertical-align: top;
}
.bootstrap-table .table thead > tr > th,.bootstrap-table .table thead > tr > td{
	border:none;
}
input[readonly], html input[disabled], select[disabled], select[disabled], textarea[disabled] {
    background: rgba(246, 246, 246, 0.42);
    cursor: not-allowed;
    color: #777;
}
.fixed-table-container thead th .sortable{
	padding-right:8px;
}
</style>
</head>
<body>
<div id="container">
    <form id="form1"  class="form-horizontal">
    <div class="infoHd">网电咨询信息</div>
    
    <div class="infoBd">
    <div  style="width:500px;margin:0 auto;">
    <input type="hidden" class="form-control" name="seqId" id="seqId">
    	<div class="kv">
            <label>患者姓名</label>
            <div class="kv-v">
                <input type="text" id="username" name="username" value="" disabled>
            </div>
        </div>
        <div class="kv">
            <label>创建人</label>
            <div class="kv-v">
                <input type="hidden" name="createuser" id="createuser" value="" title="建档人" class="form-control" />
				<input type="text" id="createuserDesc" name="createuserDesc" value="" onClick="javascript:single_select_user(['createuser', 'createuserDesc']);" disabled></input>	
            </div>
        </div>
        <div class="kv">
            <label>咨询日期</label>
            <div class="kv-v">
                <input type="text" id="acceptdate" name="acceptdate" value="" disabled >
            </div>
        </div>
        <div class="kv">
            <label>预约日期</label>
            <div class="kv-v">
                <input type="text" id="ordertime" name="ordertime" value="" disabled >
            </div>
        </div>
        <div class="kv">
            <label>受理类型</label>
            <div class="kv-v">
                <select class="dict" tig="SLLX" name="accepttype" id="accepttype" disabled>
				</select>
            </div>	
        </div>
        <div class="kv" id="tool">
            <label>受理工具</label>
            <div class="kv-v">
                <select class="dict" tig="SLGJ" name="accepttool" id="accepttool" disabled>
				</select>
            </div>
        </div>
        <div class="kv singleKv child-inline-block-top">
            <label>咨询内容</label>
            <div class="kv-v">
                <textarea class="form-control" name="askcontent" id="askcontent" rows="3" readonly="readonly" style="min-width:390px; width: 79%;background:#FBFBFB;"></textarea>
            </div>
        </div>
        <div class="kv">
            <label>咨询项目</label>
            <div class="kv-v">
                <select class="dict" tig="ZXXM" name="askitem" id="askitem" disabled>
				</select>
            </div>
        </div>
        <div class="kv">
            <label>患者状态</label>
            <div class="kv-v">
                <select name="doorstatus" id="doorstatus" disabled>
                	<option value="">请选择</option>
                	<option value="0">未上门</option>
                	<option value="1">已上门</option>
				</select>
            </div>
        </div>
        
        <div class="buttonBar" id="buttonBarDiv">
            <hr/>
        </div>
        
        <div class="kv">
            <label>安排科室</label>
            <div class="kv-v">
                <select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1 %>" name="regdept" id="regdept" disabled>
				</select>
            </div>
        </div>
        <div class="kv">
            <label>安排医生</label>
            <div class="kv-v">
				<input type="hidden" name="orderperson" id="orderperson" value="" title="主治医生" class="form-control" />
				<input type="text" id="orderpersonDesc" name="orderpersonDesc" value="" onClick="javascript:single_select_user(['orderperson', 'orderpersonDesc']);" disabled></input>	
            </div>
        </div>
        <div class="kv">
        	<label>咨询</label>
            <div class="kv-v">
                <input type="hidden" name="askperson" id="askperson" value="" title="咨询人" class="form-control"  />
				<input type="text" id="askpersonDesc" name="askpersonDesc" value="" onClick="javascript:single_select_user(['askperson', 'askpersonDesc']);" disabled></input>	
            </div>
        </div>
        <div class="kv">
        	<label>导医</label>
            <div class="kv-v">
                <input type="hidden" name="daoyi" id="daoyi" value="" title="导医" class="form-control" />
				<input type="text" id="daoyiDesc" name="daoyiDesc" value="" onClick="javascript:single_select_user(['daoyi', 'daoyiDesc']);" disabled></input>	
            </div>
        </div>
        <div class="kv singleKv">
          
            <label style=" width:64px;">
            	<input type="checkbox" id="zxremarkck" value="咨询备注" name="zxremarkck" style="border: 1px solid #fd8845;">咨询备注
            </label>
            <div class="kv">
<!--                 <input id="zxremark" name="zxremark" type="text" size="14" style="width:400px;" class="form-control" > -->
                <textarea class="form-control" name="zxremark" id="zxremark" rows="3"  style="min-width:390px;background:#FBFBFB;"></textarea>
            </div>
        </div>
        
        <div class="buttonBar">
        </div>
        
        </div>
        
        <div class="tableHd">网电咨询记录</div>
        <div class="tableBox">
            <table id="table" class="table-striped table-condensed table-bordered" data-height="300"></table>
        </div>
    </div>
    </form>
</div>
</body>

<script type="text/javascript">
var contextPath = '<%=contextPath%>'; // util.js中引用
var pageurl = '<%=contextPath%>/KQDS_Net_OrderAct/selectNoPage.act';
var onclickrowOobj = ""; //表格点击获取到的对象
var usercode = "<%=usercode%>";

var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
var allowed=allowedperson.split(",");//允许权限

$(function() {
	 if(usercode == ''){
		 layer.alert('请选择患者', {
	           
	     },function(){
	    	 return false;
	     });
	 }else{
		pageurl = pageurl + '?usercode=' + usercode;
		// 加载列表
		initTable();
		
		 initDictSelectByClass(); // 受理类型、受理工具、咨询项目
	     initDeptSelectByTypesAndClass('<%=ChainUtil.getCurrentOrganization(request)%>'); //加载科室
	 }
});

//判断当前人员权限
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
/* 判断登录 */
var existornot=isExist(total);
if(existornot){
	
}else{
	$('#tool').attr('style', 'display:none');
}

//加载表格
function initTable() {
  $('#table').bootstrapTable({
      url: pageurl,
      dataType: "json",
      singleSelect: false,
      striped: true,
      onLoadSuccess: function(data) { //加载成功时执行
          var allTableData = $("#table").bootstrapTable('getData'); //获取表格的所有内容行
          if (allTableData != null && allTableData.length > 0) {
              showorderdetail(allTableData[0]); //表格加载成功后加载详情
          }
      },
      columns: [
      {
          title: '患者姓名',
          field: 'username',
          align: 'center',
          valign: 'middle',
          sortable: true,
          formatter:function(value){
        	  return '<span>'+value+'</span>'
          }
      },
      {
          title: '预约时间',
          field: 'ordertime',
          align: 'center',
          valign: 'middle',
          sortable: true,
          formatter:function(value){
        	  return '<span>'+value+'</span>'
          }
      },
      {
          title: '到诊时间',
          field: 'guidetime',
          align: 'center',
          valign: 'middle',
          sortable: true,
          formatter: function(value, row, index) {
              if (value != "" && value != null) {
                  return '<span class="time" title="' + value + '">' + value + '</span>';
              } else {
                  return '<span>-</span>';
              }
          }
      },
      {
          title: '本次上门状态',
          field: 'doorstatus',
          align: 'center',
          valign: 'middle',
          sortable: true,
          formatter: function(value, row, index) {
              if (value == 1) {
                  return '<span style="color:green">已上门</span>';
              } else {
                  return '<span style="color:red">未上门</span>';
              }
          }
      },
      {
          title: '本次成交状态',
          field: 'cjstatus',
          align: 'center',
          valign: 'middle',
          sortable: true,
          formatter: function(value, row, index) {
              if (value == 1) {
                  return '<span style="color:green">已成交</span>';
              } else {
                  return '<span style="color:red">未成交</span>';
              }
          }
      },
      {
          title: '咨询项目',
          field: 'askitemname',
          align: 'center',
          valign: 'middle',
          sortable: true,
          formatter: function(value, row, index) {
              if (value) {
                  return '<span>'+value+'</span>';
              }
          }
      },
      {
          title: '网电咨询内容',
          field: 'askcontent',
          align: 'center',
          valign: 'middle',
          sortable: true,
          formatter: function(value, row, index) {
              if (value != "" && value != null) {
                  return '<span  class="time" title="' + value + '">' + value + '</span>';
              }
          }
      },
      {
          title: '网电备注',
          field: 'remark',
          align: 'center',
          valign: 'middle',
          sortable: true,
          formatter: function(value, row, index) {
              if (value != "" && value != null) {
                  return '<span  class="time" title="' + value + '">' + value + '</span>';
              }
          }
      },
      {
          title: '建裆人备注',
          field: 'zxremark',
          align: 'center',
          valign: 'middle',
          sortable: true,
          formatter: function(value, row, index) {
              if (value != "" && value != null) {
                  return '<span class="time" title="' + value + '">' + value + '</span>';
              } else {
                  return "";
              }
          }
      },
      {
          title: '创建人',
          field: 'createusername',
          align: 'center',
          valign: 'middle',
          sortable: true,
          formatter: function(value, row, index) {
              if (value) {
                  return "<span class='name'>"+value+"</span>";
              }
          }
      },
      {
          title: '创建时间',
          field: 'createtime',
          align: 'center',
          valign: 'middle',
          sortable: true,
          formatter: function(value, row, index) {
              if (value != "" && value != null) {
                  return '<span class="time" title="' + value + '">' + value + '</span>';
              } else {
                  return '';
              }
          }
      }]
  }).on('click-row.bs.table',
  function(e, row, element) {
      $('.success').removeClass('success'); //去除之前选中的行的，选中样式
      $(element).addClass('success'); //添加当前选中的 success样式用于区别
      var index = $('#table').find('tr.success').data('index'); //获得选中的行
      onclickrowOobj = $('#table').bootstrapTable('getData')[index];

      showorderdetail(onclickrowOobj); //展示上方预约详情
  });
}
//根据选中行的seqId  查询详情并填充上方预约详情
function showorderdetail(obj) {
  if (obj == "" || obj == null) {
      layer.alert('请选择一条预约记录', {
            
      });
      return;
  }
  var detailurl = '<%=contextPath%>/KQDS_Net_OrderAct/selectDetail.act?seqId=' + obj.seqId;
  $.axse(detailurl, null,
  function(data) {

      //清空原有内容
      clearorderinfo();

      if (data.data.zxremark != "" && data.data.zxremark != null && data.data.zxremark != "null" && data.data.zxremark != "undefined" && data.data.zxremark != undefined) {
          //选中复选框
          if (!$("#zxremarkck").prop("checked")) {
              $("#zxremarkck").trigger("click");
          }
          $("#zxremark").css("color", "red");
      } else {
          $("#zxremark").css("color", "");
          $("#zxremarkck").attr("checked", false);
      }

      //填充
      loadData(data.data);

      //详情页面展示选择的用户
      if (document.getElementById("askperson").value.trim() != "") {
          bindPerUserNameBySeqIdTB("askpersonDesc", document.getElementById("askperson").value);
      }
      if (document.getElementById("orderperson").value.trim() != "") {
          bindPerUserNameBySeqIdTB("orderpersonDesc", document.getElementById("orderperson").value);
      }
      if (document.getElementById("daoyi").value.trim() != "") {
          bindPerUserNameBySeqIdTB("daoyiDesc", document.getElementById("daoyi").value);
      }
      if (document.getElementById("createuser").value.trim() != "") {
          bindPerUserNameBySeqIdTB("createuserDesc", document.getElementById("createuser").value);
      }
  },
  function() {
      layer.alert('查询出错！', {
            
      });
  });
}

//清空上方预约详情内容
function clearorderinfo() {
  $("#seqId").val("");
  $("#username").val("");
  $("#acceptdate").val("");
  $("#ordertime").val("");
  $("#accepttype").val("");
  $("#accepttool").val("");
  $("#askcontent").val("");
  $("#askitem").val("");
  $("#askperson").val("");
  $("#askpersonDesc").val("");
  //$("#jzxx").val("");
  $("#regdept").val("");
  $("#orderperson").val("");
  $("#orderpersonDesc").val("");
  $("#daoyi").val("");
  $("#doorstatus").val("");
  $("#zxremark").val("");
}
</script>
</html>
