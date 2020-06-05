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
<title>网电信息</title>
<!-- 非客户管理进入的 右侧个人中心   网电信息 Tab -->
<!-- 客服中心—客户管理页面右侧的网电信息Url和其他页面不一样 该页面不可以进行保存操作 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/yyzx/netorder.css"/>
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

<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<style>
	.fixed-table-container{
		border-left: 1px solid #ddd;
		border-right: 1px solid #ddd;
		border-radius: 6px;
	    /* border-top-left-radius: 6px;
	    border-top-right-radius: 6px; */
	    overflow: hidden;
	}
</style>
</head>
<body>
	<div class="container" style="padding-top:10px;">
		<!-- <div class="titleDiv">
			<span class="title">网电咨询信息</span>
		</div> -->
		<input type="hidden" name="seqId" id="seqId">
		<table class="tableLayout" style="margin-top:5px;">	<!-- tableLayout的布局 基本样式  bottomBorderTable提供下边框 -->
			<tr>
				<td>	<!--.contentText 该页面 信息文本的基本样式 -->
					<span class="contentText">患者姓名</span>
				</td>
				<td>
					<input type="text" id="username" name="username" value="" disabled>
				</td>
				<td>	<!--.contentText 该页面 信息文本的基本样式 -->
					<span class="contentText">创建人</span>
				</td>
				<td>
					<input type="hidden" name="createuser" id="createuser" value="" title="建档人"/>
					<input type="text" id="createuserDesc" name="createuserDesc" value="" onClick="javascript:single_select_user(['createuser', 'createuserDesc']);" disabled>
				</td>
			</tr>
			<tr>
				<td> 	<!--.contentText 该页面 信息文本的基本样式 -->
					<span class="contentText">咨询日期</span>
				</td>
				<td>
					<input type="text" id="acceptdate" name="acceptdate" value="" disabled >
				</td>
				<td>	<!--.contentText 该页面 信息文本的基本样式 -->
					<span class="contentText">预约日期</span>
				</td>
				<td>
					<input type="text" id="ordertime" name="ordertime" value="" disabled >
				</td>
			</tr>
			<tr>
				<td>
					<span class="contentText">受理类型</span>
				</td>
				<td>
					<select class="dict" tig="SLLX" name="accepttype" id="accepttype" disabled>
					</select>
				</td>
				<td>
					<span class="contentText" id="tool">受理工具</span>
				</td>
				<td>
					<select class="dict" tig="SLGJ" name="accepttool" id="accepttool" disabled>
					</select>
				</td>
			</tr>
			<tr class="textareaTr"> 	<!-- textarea所在的行加的样式   用于调整 textarea的与其它行的间距  -->
				<td>
					<span class="contentText">咨询内容</span>
				</td>
				<td colspan="3">
					<textarea  name="askcontent" id="askcontent" rows="3" readonly="readonly" ></textarea>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="contentText">咨询项目</span>
				</td>
				<td> <!--.dict本身无样式 但是有功能  -->
					<select class="dict" tig="ZXXM" name="askitem" id="askitem" disabled>
					</select>
				</td>
				<td>
					<span class="contentText">患者状态</span>
				</td>
				<td>
					<select name="doorstatus" id="doorstatus" disabled>
                	<option value="">请选择</option>
                	<option value="0">未上门</option>
                	<option value="1">已上门</option>
				</select>
				</td>
			</tr>
		</table>
		<hr>
		<table class="tableLayout ">  <!-- tableLayout  本页面table布局的基本样式 -->
			<tr>
				<td>	<!--.contentText 该页面 信息文本的基本样式 -->
					<span class="contentText">安排科室</span>
				</td>
				<td>
					<select class="dept" tag="<%=ConstUtil.DEPT_TYPE_1 %>" name="regdept" id="regdept" disabled>
					</select>
				</td>
				<td>	<!--.contentText 该页面 信息文本的基本样式 -->
					<span class="contentText">安排医生</span>
				</td>
				<td>
					<input type="hidden" name="orderperson" id="orderperson" value="" title="主治医生" class="form-control" />
					<input type="text" id="orderpersonDesc" name="orderpersonDesc" value="" onClick="javascript:single_select_user(['orderperson', 'orderpersonDesc']);" disabled>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="contentText">咨询</span>
				</td>
				<td>
					<input type="hidden" name="askperson" id="askperson" value="" title="咨询人" />
					<input type="text" id="askpersonDesc" name="askpersonDesc" value="" onClick="javascript:single_select_user(['askperson', 'askpersonDesc']);" disabled>
				</td>
				<td>
					<span class="contentText">导医</span>
				</td>
				<td>
					<input type="hidden" name="daoyi" id="daoyi" value="" title="导医" />
					<input type="text" id="daoyiDesc" name="daoyiDesc" value="" onClick="javascript:single_select_user(['daoyi', 'daoyiDesc']);" disabled>
				</td>
			</tr>
			
			<tr class="textareaTr">  <!-- textarea所在的行加的样式   用于调整 textarea的与其它行的间距  -->
				<td colspan="4">
					<label for="zxremarkck" class="zxbzSpan">
						<input style="position:relative;top:3px;height:14px;" type="checkbox" id="zxremarkck" value="咨询备注" name="zxremarkck" >咨询备注
					</label>
					<textarea  name="zxremark" id="zxremark" rows="3"></textarea>
				</td>
			</tr>
		</table>
		
		<div class="buttonBar" id="buttonBarDiv"> <!--buttonBar 按钮组的布局    -->
			
		</div>
		<div class="buttonBar"></div> <!--buttonBar 按钮组的布局    -->
		<div class="tableBox">
			<table id="table" class="table-striped table-condensed table-bordered" data-height="300"></table>
		</div>
			
	</div>
</body>

<script type="text/javascript">
var contextPath = '<%=contextPath%>'; // util.js中引用

//点击左侧网店预约列表传值 预约编号或者详情 
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var $table = $('#table');
var pageurl = '<%=contextPath%>/KQDS_Net_OrderAct/selectNoPage.act';
var onclickrowOobj = ""; //表格点击获取到的对象
var parentonclickrowObj = window.parent.onclickrowOobj; //父页面选中行对象
var listbutton = window.parent.listbutton; //父页面传值
var usercode = "";
var regno = "";
var isdelreg = 0;

var loadperson='<%=person.getUserPriv()%>';
var load=loadperson.split(",");//登陆这权限
var allowedperson='<%=SysParaUtil.getSysValueByName(request, SysParaUtil.ZY_LYCK)%>';
var allowed=allowedperson.split(",");//允许权限

$(function() {
  getButtonPower();
  if (parentonclickrowObj == null || parentonclickrowObj == "") {
      layer.alert('请选择患者', {
            
      });
  }
  //如果选中的不是挂号记录
  if (parentonclickrowObj == "" || parentonclickrowObj.ifcost == null) {
      regno = parentonclickrowObj.regno;
  } else {
      regno = parentonclickrowObj.seqId;
      isdelreg = parentonclickrowObj.del;
  }
  if (parentonclickrowObj != null && parentonclickrowObj != "") {
      usercode = parentonclickrowObj.usercode;
      initDictSelectByClass(); // 受理类型 受理工具 咨询项目
      initDeptSelectByTypesAndClass('<%=ChainUtil.getCurrentOrganization(request)%>'); //加载科室
      //如果父页面传值不为空 则使用父页面传值作为参数查询详情及列表
      pageurl = pageurl + '?usercode=' + usercode;
      //加载列表
      initTable();

    //判断当前人员权限是否为前台
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
	var existornot=isExist(total);
	if(existornot){
		
	}else{
		$('#accepttool').attr('style', 'display:none');
		$('#tool').attr('style', 'display:none');
	} 
  }

  //时间选择
  $("#acceptdate").datetimepicker({
      language: 'zh-CN',
      minView: 0,
      format: 'yyyy-mm-dd hh:ii',
      autoclose: true,
      //选中之后自动隐藏日期选择框   
      pickerPosition: "bottom-right"
  });
  $("#ordertime").datetimepicker({
      language: 'zh-CN',
      minView: 2,
      format: 'yyyy-mm-dd',
      autoclose: true,
      //选中之后自动隐藏日期选择框   
      pickerPosition: "bottom-right"
  });

  //checkbox 选中时间
  $("#zxremarkck").change(function() {
      if ($("#zxremarkck").prop("checked")) {
          //alert(222)
          //选中
          $("#zxremark").css("color", "red");
      } else {
          $("#zxremark").css("color", "");
      }

  })

});

//保存预约信息
function baocun() {
  //验证
  var seqId = $("#seqId").val();
  var username = $("#username").val();
  var acceptdate = $("#acceptdate").val();
  var ordertime = $("#ordertime").val();
  var accepttype = $("#accepttype").val();
  var accepttool = $("#accepttool").val();
  var askcontent = $("#askcontent").val();
  var askitem = $("#askitem").val();
  var doorstatus = $("#doorstatus").val();

  var regdept = $("#regdept").val();
  var orderperson = $("#orderperson").val();
  // 	var orderpersonDesc = $("#orderpersonDesc").val("");
  var askperson = $("#askperson").val();
  // 	var askpersonDesc = $("#askpersonDesc").val("");
  var daoyi = $("#daoyi").val();

  var param;
  if ($("#zxremarkck").prop("checked")) {
      //选中
      param = {
          seqId: seqId,
          usercode: usercode,
          username: username,
          acceptdate: acceptdate,
          ordertime: ordertime,
          accepttype: accepttype,
          accepttool: accepttool,
          askcontent: askcontent,
          askitem: askitem,
          doorstatus: doorstatus,

          regdept: regdept,
          orderperson: orderperson,
          askperson: askperson,
          daoyi: daoyi,
          zxremark: $("#zxremark").val()
      };
  } else {
      param = {
          seqId: seqId,
          usercode: usercode,
          username: username,
          acceptdate: acceptdate,
          ordertime: ordertime,
          accepttype: accepttype,
          accepttool: accepttool,
          askcontent: askcontent,
          askitem: askitem,
          doorstatus: doorstatus,

          regdept: regdept,
          orderperson: orderperson,
          askperson: askperson,
          daoyi: daoyi
      };
  }

  if (seqId == "" || seqId == null) {
      layer.alert('请选择一条预约记录再修改', {
            
      });
      return false;
  }
  if (username == "" || username == null) {
      layer.alert('请选择患者', {
            
      });
      return false;
  }

  var submiturl = '<%=contextPath%>/KQDS_Net_OrderAct/insertOrUpdate.act';
  var msg;
  $.axseSubmit(submiturl, param,
  function() {
      msg = layer.msg('加载中', {
          icon: 16
      });
  },
  function(r) {
      if (r.retState == "0") {
          layer.alert('提交成功', {
                
              end: function() {
                  window.location.reload();
                  parent.layer.close(frameindex); //再执行关闭
              }
          });
      } else {
          layer.alert('提交失败', {
                
          });
      }
  },
  function() {
      layer.alert('提交失败', {
            
      });
  });

}
//加载表格
function initTable() {
  $table.bootstrapTable({
      url: pageurl,
      dataType: "json",
      singleSelect: false,
      striped: true,
      onLoadSuccess: function(data) { //加载成功时执行
          var allTableData = $("#table").bootstrapTable('getData'); //获取表格的所有内容行
          if (allTableData != null && allTableData.length > 0) {
              showorderdetail(allTableData[0]); //表格加载成功后加载详情
          }
          /*表格载入时，设置表头的宽度 */
          setTableHeaderWidth(".tableBox");
      },
      rowStyle: function(row, index) {
          //这里有5个取值代表5中颜色['active', 'success', 'info', 'warning', 'danger'];
          var strclass = "";
          if (row.isdelete == "1") {
              strclass = 'danger'; //还有一个active
          } else {
              return {};
          }
          return {
              classes: strclass
          };
      },
      columns: [
      {
          title: '患者姓名',
          field: 'username',
          align: 'center',
          
          sortable: true,
          formatter:function(value){
        	  return '<span class="name">'+value+'</span>'
          }
      },
      {
          title: '预约时间',
          field: 'ordertime',
          align: 'center',
          
          sortable: true,
          formatter: function(value, row, index) {
              if (value != "" && value != null) {
                  return '<span title="' + value + '">' + value + '</span>';
              } else {
                  return '';
              }
          }
      },
      {
          title: '到诊时间',
          field: 'guidetime',
          align: 'center',
          
          sortable: true,
          formatter: function(value, row, index) {
              if (value != "" && value != null) {
                  return '<span title="' + value + '">' + value + '</span>';
              } else {
                  return '';
              }
          }
      },
      {
          title: '本次上门状态',
          field: 'doorstatus',
          align: 'center',
          
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
          
          sortable: true,
          formatter: function(value, row, index) {
              if (value) {
                  return '<span class="source" >'+value+'</span>';
              }
          }
      },
      {
          title: '是否取消',
          field: 'isdelete',
          align: 'center',
          sortable: true,
          formatter: function(value, row, index) {
              if (value) {
            	  str = (value=="0"?"正常":"已取消");
                  return '<span class="source" >'+str+'</span>';
              }
          }
      },
      {
          title: '网电咨询内容',
          field: 'askcontent',
          align: 'center',
          
          sortable: true,
          formatter: function(value, row, index) {
              if (value != "" && value != null) {
                  return '<span  class="remark" title="' + value + '">' + value + '</span>';
              }
          }
      },
      {
          title: '网电备注',
          field: 'remark',
          align: 'center',
          
          sortable: true,
          formatter: function(value, row, index) {
              if (value != "" && value != null) {
                  return '<span  class="remark" title="' + value + '">' + value + '</span>';
              }
          }
      },
      {
          title: '建裆人备注',
          field: 'zxremark',
          align: 'center',
          
          sortable: true,
          formatter: function(value, row, index) {
              if (value != "" && value != null) {
                  return '<span class="remark" title="' + value + '">' + value + '</span>';
              } else {
                  return "";
              }
          }
      },
      {
          title: '创建人',
          field: 'createusername',
          align: 'center',
          
          sortable: true,
          formatter: function(value, row, index) {
              if (value) {
                  return "<span  class='name'>"+value+"</span>";
              }
          }
      },
      {
          title: '创建时间',
          field: 'createtime',
          align: 'center',
          
          sortable: true,
          formatter: function(value, row, index) {
              if (value != "" && value != null) {
                  return '<span title="' + value + '">' + value + '</span>';
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
function getButtonPower() {
  var menubutton1 = "";
  for (var i = 0; i < listbutton.length; i++) {
      if (listbutton[i].qxName == "wd_bc" && isdelreg == 0) {
    	  menubutton1 += '  <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="clearorderinfo()">清 空</a>';
          menubutton1 += '  <a href="javascript:void(0);" class="kqdsSearchBtn" onclick="baocun()">保 存</a>';
      }
  }
  $("#buttonBarDiv").append(menubutton1);
}
</script>
</html>
