﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}

	String menuId = request.getParameter("menuId");
	if(menuId == null){
		menuId = "";
	}
	
	String priv_seqId = request.getParameter("priv_seqId");
	if(priv_seqId == null){
		priv_seqId = "";
	}
	String organization = request.getParameter("organization");
	if(organization == null){
		organization = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>按钮列表</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/priv/setpriv_list.css" />
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.min.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
</head>
<body>
	<div class="content">
	
		<div style="float: left; width: 100%; margin-right: 1%;margin-bottom:5px;">
			<div id="toolbar">
			当前角色：<span id="privNameBZ" style="color: red;font-weight: bold;"></span>
			</div>
			<input type="hidden" id="menuId" value=""></input>
			<table id="table" data-toolbar="#toolbar" data-height="300"></table>
		</div>
		
		<form id="privForm">
			<table class="optionTable">
				<tbody>
					<tr>
						<td rowspan="5" class="bdr"  ><span class="commonText" style="width:160px">权限控制</span></td>
						<td style="padding:5px 10px 0; border:1px solid #ddd;" colspan="4">最大折扣：
							<input type="text" name="maxDiscount" id="maxDiscount" value="100"> <span style="color:tomato;font-size:12px;">只支持填写整数，如85折，填写85</span>
							&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<!-- <td rowspan="3" style="border-left:1px solid #ddd;"><input type="button" value="保 存" onclick="savePersonPriv();"></td> -->
					</tr>
					<tr>
						<td style="padding:5px 10px 0; border:1px solid #ddd;" colspan="4">最大免除：
							<input type="text" name="maxVoidmoney" id="maxVoidmoney" value="0"> <span style="color:tomato;font-size:12px;">只支持填写整数，如最大免除1000元，填写1000</span>
							&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					<tr>
						<td class="optionTd" colspan="4" style="border:1px solid #ddd;">
						    <input type="checkbox" name="canEditKf" id="canEditKf" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canEditKf">修改开发人</label>
						    <input type="checkbox" name="canEditJdr" id="canEditJdr" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canEditJdr">修改建档人</label>
						    <input type="checkbox" name="canEditPhone" id="canEditPhone" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canEditPhone">修改手机号码</label>
						    <input type="checkbox" name="canEditHzly" id="canEditHzly" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canEditHzly">修改患者来源</label>
						    <input type="checkbox" name="canEditWd" id="canEditWd" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canEditWd">修改网电信息</label>
						    <input type="checkbox" name="canEditAskperson" id="canEditAskperson" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canEditAskperson">修改充值咨询</label>
						    <input type="checkbox" name="canEditCost" id="canEditCost" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canEditCost">修改他人费用单</label>
							<input type="checkbox" name="canZheKouOnly" id="canZheKouOnly" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canZheKouOnly">只能修改折扣</label>
						</td>
					</tr>
					<tr>
						<td class="optionTd" colspan="4" style="border:1px solid #ddd;">
							<input type="checkbox" name="canOrderOther" id="canOrderOther" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canOrderOther">给其他用户预约患者</label>
						    <input type="checkbox" name="canRoom" id="canRoom" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canRoom">预约手术室</label>
							<input type="checkbox" name="iszj" id="iszj" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="iszj">专家</label>
							<input type="checkbox" name="canPaiban" id="canPaiban" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canPaiban">可排班</label>
							<input type="checkbox" name="canLookPhone" id="canLookPhone" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canLookPhone">禁止查看患者手机</label>
							<input type="checkbox" name="canKd" id="canKd" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canKd">禁止开单</label>
						    <input type="checkbox" name="canHj" id="canHj" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canHj">禁止回件</label>
							<input type="checkbox" name="canEditOrder" id="canEditOrder" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canEditOrder">回访中心管理</label>
							<input type="checkbox" name="canExportPhoneNumber" id="canExportPhoneNumber" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canExportPhoneNumber">导出手机号码</label>
						</td>
					</tr>
					<tr>
						<td class="optionTd" colspan="4" style="border:1px solid #ddd;">
							<input type="checkbox" name="canDelCk" id="canDelCk" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canDelCk">允许删除仓库出入库记录</label>
							<input type="checkbox" name="canModRoom" id="canModRoom" value="0"  onclick="this.value=(this.value==0)?1:0"><label for="canModRoom">允许修改他人手术室预约</label>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		
		<form id="visualForm">
			<table class="optionTable">
				<tbody>
					<tr>
						<td rowspan="2" class="bdr" class="titleTd"><span class="commonText">可见人员设置</span></td>
						<td class="bdr">
							<span class="commonText">可见人员</span>
						</td>
						<td class="optionTd" colspan="4"  style="border:1px solid #ddd;">
							<input type="hidden" name="visualPerson" id="visualPerson" value=""/>
							<textarea rows="" cols="100" style="" name="visualPersonDesc" id="visualPersonDesc" readonly 
							onClick="javascript:multi_select_user(['visualPerson', 'visualPersonDesc']);"></textarea>
						</td>
						<!-- <td rowspan="2" style="border-left:1px solid #ddd;">
							 <input type="button" value="保 存" onclick="saveVisual();">	
						</td> -->
					</tr>
					<tr>
						<td class="bdr">
							<span class="commonText">可见部门</span>
						</td>
						<td class="optionTd" colspan="4"  style="border:1px solid #ddd;">
							<input type="hidden" name="visualDept" id="visualDept" value=""/>
							<textarea rows="" cols="100" style="" name="visualDeptDesc" id="visualDeptDesc" readonly 
							onClick="javascript:multi_select_dept(['visualDept', 'visualDeptDesc']);"></textarea>
						</td>
					</tr>
					<tr>
					</tr>
				</tbody>
			</table>
		</form>
<!--  物资管理 20200406licc -->
		<form id="orderVisualForm">
			<table class="optionTable">
				<tbody>
					<tr>
						<td rowspan="2" class="bdr"><span class="commonText">预约可见人员设置</span></td>
						<td class="bdr"><span class="commonText">可见人员</span></td>
						<td class="optionTd" colspan="4"  style="border:1px solid #ddd;">
							<input type="hidden" name="orderVisualPerson" id="orderVisualPerson" value=""/>
							<textarea rows="" cols="100" style="" name="orderVisualPersonDesc" id="orderVisualPersonDesc" readonly
								onClick="javascript:multi_select_user(['orderVisualPerson', 'orderVisualPersonDesc']);"></textarea>
						</td>
						<!-- <td rowspan="2" style="border-left:1px solid #ddd;">
							<input type="button" value="保 存" onclick="saveOrderVisual();">
						</td> -->
					</tr>
					<tr>
						<td class="bdr"><span class="commonText">可见部门</span></td>
						<td class="optionTd" colspan="4"  style="border:1px solid #ddd;">
							<input type="hidden" name="orderVisualDept" id="orderVisualDept" value=""/>
							<textarea rows="" cols="100" style="" name="orderVisualDeptDesc" id="orderVisualDeptDesc" readonly
								onClick="javascript:multi_select_dept(['orderVisualDept', 'orderVisualDeptDesc']);"></textarea>
						</td>
					</tr>
					<tr>
					</tr>
				</tbody>
			</table>
		</form>
		<form id="visitForm">
			<table class="optionTable">
				<tbody>
					<tr>
					<td rowspan="2" class="bdr" class="titleTd"><span class="commonText">可添加回访设置</span></td>
						<td class="bdr">
							<span class="commonText">可添加回访部门</span>
						</td>
						<td class="optionTd" colspan="4"  style="border:1px solid #ddd;">
							<input type="hidden" name="visitDept" id="visitDept" value=""/>
							<textarea rows="" cols="100" style="" name="visitDeptDesc" id="visitDeptDesc" readonly 
							onClick="javascript:multi_select_dept(['visitDept', 'visitDeptDesc']);"></textarea>
						</td>
					</tr>
					<tr>
					</tr>
				</tbody>
			</table>
		</form>
		<form id="ckVisualForm">
			<table class="optionTable">
				<tbody>
					<tr>
						<td rowspan="2" class="bdr"><span class="commonText">物资管理</span></td>
						<td class="bdr"><span class="commonText">一楼可见人员</span></td>
						<td class="optionTd" colspan="4"  style="border:1px solid #ddd;">
							<input type="hidden" name="CkPerson" id="CkPerson" value=""/>
							<textarea rows="" cols="100" style="" name="CkPersonDesc" id="CkPersonDesc" readonly
								onClick="javascript:multi_select_user(['CkPerson', 'CkPersonDesc']);"></textarea>
						</td>
						<!-- <td rowspan="2" style="border-left:1px solid #ddd;">
							<input type="button" value="保 存" onclick="saveOrderVisual();">
						</td> -->
					</tr>
					<tr>
						<td class="bdr"><span class="commonText">二楼可见人员</span></td>
						<td class="optionTd" colspan="4"  style="border:1px solid #ddd;">
							<input type="hidden" name="CkPerson2" id="CkPerson2" value=""/>
							<textarea rows="" cols="100" style="" name="CkPersonDesc2" id="CkPersonDesc2" readonly
								onClick="javascript:multi_select_user(['CkPerson2', 'CkPersonDesc2']);"></textarea>
						</td>
					</tr>
					<tr>
					</tr>
				</tbody>
			</table>
		</form>
		<table class="optionTable">
			<tbody>
				<tr>
					<td style="text-align:center;">
						<input type="button" value="保存设置" onclick="saveAll();">
						<input type="button" value="返回列表" onclick="goBack();">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
<script>
var contextPath = "<%=contextPath %>";
var static_menuid = "<%=menuId %>";
var static_priv_seqId = "<%=priv_seqId %>";

var $table = $('#table');
var pageurl = "<%=contextPath%>/YZButtonAct/selectList.act";
var static_checkbutton_url = "YZPrivAct/setButtonPriv.act?1=1";
var static_detailurl = 'YZPrivAct/selectDetail.act?seqId=' + static_priv_seqId;
var static_userpriv_obj = null;
var static_qxbutton_str = null;

$(function() {
    $("#menuId").val(static_menuid);

    getDetail();

    getPageList();
});

function goBack(){
	window.parent.location.href="<%=contextPath%>/YZPrivAct/toList.act?organization=<%=organization%>";
}

/**
 * 合并设置
 */
function saveAll(){
	savePersonPriv();
	saveVisual();
	saveVisit();
	saveOrderVisual();
	
	updateOnlineData(); // 实时更新权限数据
	
	layer.alert('保存成功', {
        end: function() {
		}
	});
}

/**
 * 物资管理可见人员可见人员20200406
 */
// function saveCkVisual() {
//     var param = $('#ckVisualForm').kqds_serialize();
//     param += "&privid=" + static_priv_seqId;
//     var url = 'YZPrivAct/setOrderVisal.act';
//     var serverData = getDataFromServer(url,param);
//     if (serverData) {
//         /* layer.alert('操作成功', {
              
//             end: function() {
// 			}
//         }); */
//     }
// }

/**
 * 可见人员
 */
function saveVisual() {
    var param = $('#visualForm').kqds_serialize();
    param += "&privid=" + static_priv_seqId;
    var url = 'YZPrivAct/setVisal.act';
    var serverData = getDataFromServer(url,param);
    if (serverData) {
        /* layer.alert('操作成功', {
              
            end: function() {
			}
        }); */
    }
}

function updateOnlineData(){
	var param = {};
	var url = 'YZPrivAct/updateOnlineData.act?privid=' + static_priv_seqId;
    var serverData = getDataFromServer(url,param);
    if (serverData) {
        /* layer.alert('操作成功', {
              
            end: function() {
			}
        }); */
    }
	
}

/**
 * 预约可见人员
 */
function saveOrderVisual() {
    var param = $('#orderVisualForm').kqds_serialize();
    param += "&privid=" + static_priv_seqId;
    var url = 'YZPrivAct/setOrderVisal.act';
    var serverData = getDataFromServer(url,param);
    if (serverData) {
        /* layer.alert('操作成功', {
              
            end: function() {
			}
        }); */
    }
}

/**
 * 可添加回访部门
 */
function saveVisit() {
    var param = $('#visitForm').kqds_serialize();
    param += "&privid=" + static_priv_seqId;
    var url = 'YZPrivAct/setVisitDept.act';
    var serverData = getDataFromServer(url,param);
    if (serverData) {
        /* layer.alert('操作成功', {
              
            end: function() {
			}
        }); */
    }
}

/**
 * 用户相关权限
 */
function savePersonPriv() {
    var param = $('#privForm').kqds_serialize();
    param += "&privid=" + static_priv_seqId;
    var url = 'YZPrivAct/setPersonPriv.act?' + param;
    var serverData = getDataFromServer(url);
    if (serverData) {
        /* layer.alert('操作成功', {
              
            end: function() {
			}
        }); */
    }
}

function getDetail() {
    var serverData = getDataFromServer(static_detailurl);
    if (serverData) {
        static_userpriv_obj = serverData.retData;
        static_qxbutton_str = static_userpriv_obj.funcbutton;
        loadData(serverData.retData);
        
        // 给当前编辑的角色赋值
		$("#privNameBZ").html(serverData.retData.privName);
        // 可见人员
        if ($("#visualPerson").val() != "") {
            bindPerUserNameBySeqIdTB("visualPersonDesc", $("#visualPerson").val());
        }

        // 预约可见人员
        if ($("#orderVisualPerson").val() != "") {
            bindPerUserNameBySeqIdTB("orderVisualPersonDesc", $("#orderVisualPerson").val());
        }

        if ($("#visualDept").val() != "") {
            bindDeptNameBySeqIdTB("visualDeptDesc", $("#visualDept").val());
        }
        if ($("#visitDept").val() != "") {
            bindDeptNameBySeqIdTB("visitDeptDesc", $("#visitDept").val());
        }

        if ($("#visualDept").val() != "") {
            bindDeptNameBySeqIdTB("visualDeptDesc", $("#visualDept").val());
        }

        if ($("#orderVisualDept").val() != "") {
            bindDeptNameBySeqIdTB("orderVisualDeptDesc", $("#orderVisualDept").val());
        }

        // 用户权限相关
        if ("1" == serverData.retData.canLookPhone) {
            $("#canLookPhone").attr("checked", "checked")
        } else {
            $("#canLookPhone").removeAttr("checked");
        }
        $("#canLookPhone").attr("value", serverData.retData.canLookPhone);

        if ("1" == serverData.retData.canKd) {
            $("#canKd").attr("checked", "checked")
        } else {
            $("#canKd").removeAttr("checked");
        }

        $("#canKd").attr("value", serverData.retData.canKd);

        if ("1" == serverData.retData.canHj) {
            $("#canHj").attr("checked", "checked")
        } else {
            $("#canHj").removeAttr("checked");
        }
        $("#canHj").attr("value", serverData.retData.canHj);

        if ("1" == serverData.retData.canEditKf) {
            $("#canEditKf").attr("checked", "checked")
        } else {
            $("#canEditKf").removeAttr("checked");
        }
        $("#canEditKf").attr("value", serverData.retData.canEditKf);

        if ("1" == serverData.retData.canOrderOther) {
            $("#canOrderOther").attr("checked", "checked")
        } else {
            $("#canOrderOther").removeAttr("checked");
        }
        $("#canOrderOther").attr("value", serverData.retData.canOrderOther);

        if ("1" == serverData.retData.canRoom) {
            $("#canRoom").attr("checked", "checked")
        } else {
            $("#canRoom").removeAttr("checked");
        }
        $("#canRoom").attr("value", serverData.retData.canRoom);

        if ("1" == serverData.retData.notLogin) {
            $("#notLogin").attr("checked", "checked")
        } else {
            $("#notLogin").removeAttr("checked");
        }
        $("#notLogin").attr("value", serverData.retData.notLogin);

        if ("1" == serverData.retData.iszj) {
            $("#iszj").attr("checked", "checked")
        } else {
            $("#iszj").removeAttr("checked");
        }
        $("#iszj").attr("value", serverData.retData.iszj);

        if ("1" == serverData.retData.canPaiban) {
            $("#canPaiban").attr("checked", "checked")
        } else {
            $("#canPaiban").removeAttr("checked");
        }
        $("#canPaiban").attr("value", serverData.retData.canPaiban);
        

        if ("1" == serverData.retData.canDelCk) {
            $("#canDelCk").attr("checked", "checked")
        } else {
            $("#canDelCk").removeAttr("checked");
        }
        $("#canDelCk").attr("value", serverData.retData.canDelCk);
        
        if ("1" == serverData.retData.canModRoom) {
            $("#canModRoom").attr("checked", "checked")
        } else {
            $("#canModRoom").removeAttr("checked");
        }
        $("#canModRoom").attr("value", serverData.retData.canModRoom);

        if ("1" == serverData.retData.isLeave) {
            $("#isLeave").attr("checked", "checked")
        } else {
            $("#isLeave").removeAttr("checked");
        }
        $("#isLeave").attr("value", serverData.retData.isLeave);

        if ("1" == serverData.retData.canEditWd) {
            $("#canEditWd").attr("checked", "checked")
        } else {
            $("#canEditWd").removeAttr("checked");
        }
        $("#canEditWd").attr("value", serverData.retData.canEditWd);

        if ("1" == serverData.retData.canEditCost) {
            $("#canEditCost").attr("checked", "checked")
        } else {
            $("#canEditCost").removeAttr("checked");
        }
        $("#canEditCost").attr("value", serverData.retData.canEditCost);

        if ("1" == serverData.retData.canEditOrder) {
            $("#canEditOrder").attr("checked", "checked")
        } else {
            $("#canEditOrder").removeAttr("checked");
        }
        $("#canEditOrder").attr("value", serverData.retData.canEditOrder);
        // 修改建档人
        if ("1" == serverData.retData.canEditJdr) {
            $("#canEditJdr").attr("checked", "checked")
        } else {
            $("#canEditJdr").removeAttr("checked");
        }
        $("#canEditJdr").attr("value", serverData.retData.canEditJdr);
        // 修改手机号码
        if ("1" == serverData.retData.canEditPhone) {
            $("#canEditPhone").attr("checked", "checked")
        } else {
            $("#canEditPhone").removeAttr("checked");
        }
        $("#canEditPhone").attr("value", serverData.retData.canEditPhone);
        // 修改患者来源
        if ("1" == serverData.retData.canEditHzly) {
            $("#canEditHzly").attr("checked", "checked")
        } else {
            $("#canEditHzly").removeAttr("checked");
        }
        $("#canEditHzly").attr("value", serverData.retData.canEditHzly);
     	// 只能修改折扣
        if ("1" == serverData.retData.canZheKouOnly) {
            $("#canZheKouOnly").attr("checked", "checked")
        } else {
            $("#canZheKouOnly").removeAttr("checked");
        }
        $("#canZheKouOnly").attr("value", serverData.retData.canZheKouOnly);
     	// 修改充值咨询
        if ("1" == serverData.retData.canEditAskperson) {
            $("#canEditAskperson").attr("checked", "checked")
        } else {
            $("#canEditAskperson").removeAttr("checked");
        }
        $("#canEditAskperson").attr("value", serverData.retData.canEditAskperson);
        
     	// 导出手机号码
        if ("1" == serverData.retData.canExportPhoneNumber) {
            $("#canExportPhoneNumber").attr("checked", "checked")
        } else {
            $("#canExportPhoneNumber").removeAttr("checked");
        }
        $("#canExportPhoneNumber").attr("value", serverData.retData.canExportPhoneNumber);
        
    }
}

//获取选中行的usercode
function getIdSelections() {
    return $.map($table.bootstrapTable('getSelections'),
    function(row) {
        return row.seqId;
    });
}

function getIdUnSelections() {
    return $.map($table.bootstrapTable('getData'),
    function(row) {
        return row.seqId;
    });
}

//复选框
function stateFormatter(value, row, index) {
    var checkFlag = isStrInArrayStringEach(row.seqId, static_qxbutton_str);
    return {
        disabled: false,
        checked: checkFlag
    }
}

/**
 * @flag 0 取消设置  1设置
 */
function setButton(flag, buttonid) {
	var reqUrl = static_checkbutton_url;
	reqUrl += "&checked=" + flag;
	reqUrl += "&privid=" + static_priv_seqId;
	reqUrl += "&buttonid=" + buttonid;
    var serverData = getDataFromServer(reqUrl);
    if (serverData) {
        alert('设置成功');
    }
}

function setButtonBatch(flag) {
	var seqIds = "";
	var selectList = "";
	if(flag == '0'){ // 取消设置
		selectList = getIdUnSelections();
	}else{
		selectList = getIdSelections();
	}
	
	if (selectList.length == 0) {
        layer.alert('没有可勾选的数据' );
        return false;
    }
    for (var i = 0; i < selectList.length; i++) {
        seqIds += selectList[i] + ",";
    }
    seqIds = seqIds.substring(0, seqIds.length - 1);
	
    var reqUrl = static_checkbutton_url;
    reqUrl += "&checked=" + flag;
    reqUrl += "&privid=" + static_priv_seqId;
    reqUrl += "&buttonid=" + seqIds;
    var serverData = getDataFromServer(reqUrl);
    if (serverData) {
    	alert('设置成功');
    }
}


function queryParams(params) {
    var temp = {
        menuid: static_menuid
    };
    return temp;
}
function getPageList() {
    $table.bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        toolbar: '#toolbar',
        striped: true,
        cache: false,
        onCheck: function(row) {
            setButton("1", row.seqId);
        },
        onUncheck: function(row) {
            setButton("0", row.seqId);
        },
        onCheckAll: function(row) {
        	setButtonBatch("1");
        },
        onUncheckAll: function(row) {
        	setButtonBatch("0");
        },
        //服务端处理分页
        columns: [{
            field: ' ',
            checkbox: true,
            align: 'center',
            valign: 'middle',
            formatter: stateFormatter
        },
        {
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: false,
            switchable: false
        },
        {
            title: '名称',
            field: 'name',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '权限标识',
            field: 'qxName',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '备注',
            field: 'bz',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '创建时间',
            field: 'createtime',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        }]
    });
}
</script>

</body>
</html>
