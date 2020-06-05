<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	YZPerson person = SessionUtil.getLoginPerson(request);
	
	String organization = request.getParameter("organization");
	if(organization == null){
		organization = "";
	}
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>参数管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/para/para.css">
<style type="text/css">

.bootstrap-select:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn){
	width:350px;
}

select{
	width:350px;
}
.fixedBottomDiv{
	z-index:10;
}

</style>
</head>
<body>
	<div class="container"><!--提供内边距 让内容不顶头显示-->
		<form id="defaultForm"><!-- 本身无样式  defaultForm表单验证功能要用 -->
			<input type="hidden" name="seqId" id="seqId">
			<table id="table" class="tableContent" style="width:90%;"><!--tableContent的样式 -->
				<tbody>
					<tr id="paraList">
						<th width="25%" style="text-align: center;">参数名称</th>
						<th width="25%" style="text-align: center;">参数值</th>
						<th width="25%" style="text-align: center;">参数说明</th>
						<th width="25%" style="text-align: center;">操作</th>
					</tr>
				</tbody>
			</table>	
		</form>
		<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
			<div class="clear2"></div>
         	<a class="kqdsCommonBtn" id="refresh" href="javascript:void(0);" onclick="refresh();">刷新</a>
         	<a class="kqdsCommonBtn" id="newAdd" href="javascript:void(0);" onclick="newAdd();">新增</a>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds_person_select.js"></script>
<script type="text/Javascript">
var contextPath = "<%=contextPath%>";
var static_listurl = 'YZParaAct/selectList.act?1=1';
var static_deleteurl = 'YZParaAct/delete.act?1=1';
var organization = "<%=organization %>";
$(function() {
	if(organization == ""){
		layer.alert('参数错误：organization不能为空！');
		return false;
	}
	static_listurl = static_listurl + '&organization=' + organization;
    getList();
});

function getList() {
    var serverData = getDataFromServer(static_listurl);
    if (serverData) {
    	var static_shichangPriv = null; // 市场角色主键值
    	var static_commonalityPriv = null; // 可观回访角色主键值
    	//var static_worksheetPriv = null; // 加工单角色主键值
    	var static_dept = null; // 回访角色主键值
    	var static_askPriv = null; // 咨询角色主键值
    	var static_yuanzhangPriv = null; // 院长角色主键值
    	var static_doctorPriv = null; // 院长角色主键值
    	var static_shoufeiPriv = null; // 收费角色主键值
    	var static_zongjingliPriv = null; // 总经理角色主键值
    	var static_wdPriv = null; // 网电角色主键值
    	var static_ckPriv = null; // 仓库角色主键值
    	var static_dzblOpt = null; // 电子病历操作权限
    	var static_nopwd = null; // 会员卡免密支付
    	var static_hykbind = null; // 会员卡绑定
    	var static_itemsort = null; // 三大成交项目分类
    	var static_ifmajors = null; // 是否专业版
    	var static_ifpaiban = null; // 是否开启排班
    	var static_regsort = null; // 挂号分类
    	var static_resources = null;//资源查看
    	
    	var static_zhongzhi = null; // 种植病历
    	var static_chain = null; // 是否开启连锁
    	var static_noshowdict = null; // 不在字典管理中显示的分类
    	var static_chuzhen = null; // 就诊分类-初诊
    	
    	var static_yjj = null; // 付款方式-预交金主键值
    	var static_yyflChuzhen = null; // 预约分类-初诊-主键值
    	
        var paralist = serverData.retData;
        var htmlstr = "";
        for (var i = 0; i < paralist.length; i++) {
            htmlstr += "<tr>";
            htmlstr += "<td width='25%'><input type='text' name='paraName' value='" + paralist[i].paraName + "' readonly='readonly'></td>";
            
            if("PRIV_SHICHANG_SEQID" == paralist[i].paraName){ // 市场角色主键值
            	var innerHtml = '<select multiple name="paraValue" id="shichangPrivSelect"></select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_shichangPriv = paralist[i].paraValue;
            }else if("PRIV_ASK_SEQID" == paralist[i].paraName){ // 咨询角色主键值
            	var innerHtml = '<select multiple name="paraValue" id="askPrivSelect"></select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_askPriv = paralist[i].paraValue;
            }else if("PRIV_YUANZHANG_SEQID" == paralist[i].paraName){ // 院长角色主键值
            	var innerHtml = '<select multiple name="paraValue" id="yuanzhangPrivSelect"></select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_yuanzhangPriv = paralist[i].paraValue;
            }else if("PRIV_DOCTOR_SEQID" == paralist[i].paraName){ // 医生角色主键值
            	var innerHtml = '<select multiple name="paraValue" id="doctorPrivSelect"></select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_doctorPriv = paralist[i].paraValue;
            }else if("PRIV_SHOUFEI_SEQID" == paralist[i].paraName){ // 收费角色主键值
            	var innerHtml = '<select multiple name="paraValue" id="shoufeiPrivSelect"></select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_shoufeiPriv = paralist[i].paraValue;
            }else if("PRIV_ZONGJINGLI_SEQID" == paralist[i].paraName){ // 总经理角色主键值
            	var innerHtml = '<select multiple name="paraValue" id="zongjingliPrivSelect"></select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_zongjingliPriv = paralist[i].paraValue;
            }else if("PRIV_WANGDIAN_SEQID" == paralist[i].paraName){ // 网电角色主键值
            	var innerHtml = '<select multiple name="paraValue" id="wdPrivSelect"></select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_wdPriv = paralist[i].paraValue;
            }else if("PRIV_CK_SEQID" == paralist[i].paraName){ // 仓库角色主键值
            	var innerHtml = '<select multiple name="paraValue" id="ckPrivSelect"></select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_ckPriv = paralist[i].paraValue;
            }else if("DZBL_OPT_AUTH" == paralist[i].paraName){ // 仓库角色主键值
            	var innerHtml = '<select multiple name="paraValue" id="dzblOptSelect"></select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_dzblOpt = paralist[i].paraValue;
            }else if("HYK_NO_PASSWORD" == paralist[i].paraName){ // 会员卡免密支付
            	var innerHtml = '<select name="paraValue" id="nopwdSelect">';
            	innerHtml += '<option value="0">免密</option>';
            	innerHtml += '<option value="1">需输密码</option>';
            	innerHtml += '</select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_nopwd = paralist[i].paraValue;
            }else if("HYK_BINDING" == paralist[i].paraName){ // 会员卡免密支付
            	var innerHtml = '<select name="paraValue" id="hykbindSelect">';
            	innerHtml += '<option value="0">不绑</option>';
            	innerHtml += '<option value="1">绑定</option>';
            	innerHtml += '</select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_hykbind = paralist[i].paraValue;
            }else if("majors" == paralist[i].paraName){ // 是否专业版
            	var innerHtml = '<select name="paraValue" id="majorSelect">';
            	innerHtml += '<option value="1">专业版</option>';
            	innerHtml += '</select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_ifmajors = paralist[i].paraValue;
            }else if("IS_OPEN_PAIBAN_ORDER_FUNC" == paralist[i].paraName){ // 是否开启排班
            	var innerHtml = '<select name="paraValue" id="paibanSelect">';
            	innerHtml += '<option value="0">关闭排班</option>';
            	innerHtml += '<option value="1">开启排班</option>';
            	innerHtml += '</select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_ifpaiban = paralist[i].paraValue;
            }else if("COST_SUCCESS_ITEM_SORT" == paralist[i].paraName){ // 三大成交项目
            	var innerHtml = '<select multiple name="paraValue" id="itemsortSelect" class="dict" tig="COSTITEM_SORT">';
            	innerHtml += '</select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_itemsort = paralist[i].paraValue;
            }else if("REG_SUCCESS_ITEM_SORT" == paralist[i].paraName){ // 挂号成交分类
            	var innerHtml = '<select multiple name="paraValue" id="regsortSelect" class="dict" tig="GHFL">';
            	innerHtml += '</select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_regsort = paralist[i].paraValue;
            }else if("COST_SUCCESS_ITEM" == paralist[i].paraName){ // 三大成交项目子分类
            	var innerHtml = '<input type="hidden" name="paraValue" id="costItem" value="' + paralist[i].paraValue + '" parentCode="COSTITEM_SORT"/>';
            	innerHtml += '<textarea rows="" cols="50" style="width:350px;" name="costItemDesc" id="costItemDesc" readonly onClick="javascript:multi_select_dict([\'costItem\', \'costItemDesc\']);"></textarea>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            } else if("IS_OPEN_ZHONGZHI_BINGLI" == paralist[i].paraName){ // 是否种植病历
            	var innerHtml = '<select name="paraValue" id="zhongzhiSelect">';
            	innerHtml += '<option value="0">关闭</option>';
            	innerHtml += '<option value="1">开启</option>';
            	innerHtml += '</select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_zhongzhi = paralist[i].paraValue;
            }else if("IS_OPEN_CHAIN_FUNC" == paralist[i].paraName){ // 是否开启连锁
            	var innerHtml = '<select name="paraValue" id="chainSelect">';
            	innerHtml += '<option value="0">关闭</option>';
            	innerHtml += '<option value="1">开启</option>';
            	innerHtml += '</select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_chain = paralist[i].paraValue;
            }else if("NOT_SHOW_KIND_DICTS" == paralist[i].paraName){ // 不在字典管理中显示的分类
            	var innerHtml = '<select multiple name="paraValue" id="noshowdictSelect" class="dict" tig="0">';
            	innerHtml += '</select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_noshowdict = paralist[i].paraValue;
            }else if("JZFL_CHUZHEN_SEQID" == paralist[i].paraName){ // 就诊分类-初诊
            	var innerHtml = '<select  name="paraValue" id="chuzhenSelect" class="dict" tig="JZFL">';
            	innerHtml += '</select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_chuzhen = paralist[i].paraValue;
            }else if("YYFL_CHUZHEN_SEQID" == paralist[i].paraName){ // 预约分类-初诊-主键值
            	var innerHtml = '<select  name="paraValue" id="yyflChuzhenSelect" class="dict" tig="YYFL">';
            	innerHtml += '</select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_yyflChuzhen = paralist[i].paraValue;
            }else if("SYS_POSITION" == paralist[i].paraName){
            	var innerHtml = '<select multiple name="paraValue" id="commonalityPrivSelect"></select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_commonalityPriv = paralist[i].paraValue;
            }else if("SYS_DEPT" == paralist[i].paraName){ // 回访可观部门
            	var innerHtml = '<select multiple name="paraValue" id="deptSelect" class="dept">';
            	innerHtml += '</select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_dept = paralist[i].paraValue;
            }/*else if("MACHINING_CENTER" == paralist[i].paraName){
            	var innerHtml = '<select multiple name="paraValue" id="worksheetPrivSelect"></select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_worksheetPriv = paralist[i].paraValue;
            }*/
            else if("ZY_LYCK" == paralist[i].paraName){
            	var innerHtml = '<select multiple name="paraValue" id="resourcesPrivSelect"></select>';
            	htmlstr += "<td width='25%'>" + innerHtml + "</td>";
            	static_resources = paralist[i].paraValue;
            }else{
            	htmlstr += "<td width='25%'><input type='text' style='width:350px;' name='paraValue' value='" + paralist[i].paraValue + "'></td>";
            }
            
            
            
            htmlstr += "<td width='25%'><input type='text' name='remark' value='" + paralist[i].remark + "'></td>";
            htmlstr += "<td width='25%'><a class='btn btn-xs btn-primary' href='javascript:void(0);' onclick=\"singleSave('" + paralist[i].seqId + "',this)\" >保存</a></td>";
            // &nbsp;&nbsp;<a class='btn btn-xs btn-danger' href='javascript:void(0);' onclick=\"singleDelete('" + paralist[i].seqId + "',this)\" >删除</a>
            htmlstr += "</tr>";
        }
        $("#paraList").after(htmlstr);
        
        /*** 角色相关  **/
        getSlectUserPrivNoOrg("shichangPrivSelect"); // 市场角色主键值
        getSlectUserPrivNoOrg("commonalityPrivSelect"); // 公用角色主键值
        getSlectUserPrivNoOrg("resourcesPrivSelect"); // 资源来源主键值
        //getSlectUserPrivNoOrg("worksheetPrivSelect"); // 公用角色主键值
        getSlectUserPrivNoOrg("askPrivSelect"); // 市场角色主键值
        getSlectUserPrivNoOrg("yuanzhangPrivSelect"); // 院长角色主键值
        getSlectUserPrivNoOrg("doctorPrivSelect"); // 医生角色主键值
        getSlectUserPrivNoOrg("shoufeiPrivSelect"); // 收费角色主键值
        getSlectUserPrivNoOrg("zongjingliPrivSelect"); // 总经理角色主键值
        getSlectUserPrivNoOrg("wdPrivSelect"); // 网电角色主键值
        getSlectUserPrivNoOrg("ckPrivSelect"); // 仓库角色主键值
        getSlectUserPrivNoOrg("dzblOptSelect"); // 电子病历操作色主键值
        jQuery('#shichangPrivSelect').selectpicker({title: "请选择"});
        jQuery('#commonalityPrivSelect').selectpicker({title: "请选择"});
        jQuery('#resourcesPrivSelect').selectpicker({title: "请选择"});
        //jQuery('#worksheetPrivSelect').selectpicker({title: "请选择"});
        jQuery('#askPrivSelect').selectpicker({title: "请选择"});
        jQuery('#yuanzhangPrivSelect').selectpicker({title: "请选择"});
        jQuery('#doctorPrivSelect').selectpicker({title: "请选择"});
        jQuery('#shoufeiPrivSelect').selectpicker({title: "请选择"});
        jQuery('#zongjingliPrivSelect').selectpicker({title: "请选择"});
        jQuery('#wdPrivSelect').selectpicker({title: "请选择"});
        jQuery('#ckPrivSelect').selectpicker({title: "请选择"});
        jQuery('#dzblOptSelect').selectpicker({title: "请选择"});
        kqds_setMultiSelectVal("shichangPrivSelect", static_shichangPriv);
        kqds_setMultiSelectVal("commonalityPrivSelect", static_commonalityPriv);
        kqds_setMultiSelectVal("resourcesPrivSelect", static_resources);
//      kqds_setMultiSelectVal("worksheetPrivSelect", static_worksheetPriv);
        kqds_setMultiSelectVal("askPrivSelect", static_askPriv);
        kqds_setMultiSelectVal("yuanzhangPrivSelect", static_yuanzhangPriv);
        kqds_setMultiSelectVal("doctorPrivSelect", static_doctorPriv);
        kqds_setMultiSelectVal("shoufeiPrivSelect", static_shoufeiPriv);
        kqds_setMultiSelectVal("zongjingliPrivSelect", static_zongjingliPriv);
        kqds_setMultiSelectVal("wdPrivSelect", static_wdPriv);
        kqds_setMultiSelectVal("ckPrivSelect", static_ckPriv);
        kqds_setMultiSelectVal("dzblOptSelect", static_dzblOpt);
        /*** 角色相关 end...  **/
        
        $("#nopwdSelect").val(static_nopwd); // 会员卡支付是否需要输入密码
        $("#hykbindSelect").val(static_hykbind); // 是否需要绑定会员卡
        $("#majorSelect").val(static_ifmajors); // 是否专业版
        $("#paibanSelect").val(static_ifpaiban); // 是否开启排班
        $("#zhongzhiSelect").val(static_zhongzhi); // 是否开启种植
        $("#chainSelect").val(static_chain); // 是否开启连锁
        
        
        initDictSelectByClassALL(organization); // 三大成交项目分类
        jQuery('#itemsortSelect').selectpicker({title: "请选择"});
        kqds_setMultiSelectVal("itemsortSelect", static_itemsort);
        jQuery('#regsortSelect').selectpicker({title: "请选择"});
        kqds_setMultiSelectVal("regsortSelect", static_regsort);
        
        initDeptSelectALL(organization);//回访可观部门
        jQuery('#deptSelect').selectpicker({title: "请选择"});
        kqds_setMultiSelectVal("deptSelect", static_dept);
        
        // 不在字典管理中显示的分类
        jQuery('#noshowdictSelect').selectpicker({title: "请选择"});
        kqds_setMultiSelectVal("noshowdictSelect", static_noshowdict);
        
        // 就诊分类-初诊
        $("#chuzhenSelect").val(static_chuzhen);
        // 付款方式-预交金主键值
        $("#yjjSelect").val(static_yjj);
        // 预约分类-初诊-主键值
        $("#yyflChuzhenSelect").val(static_yyflChuzhen);
        
        // 消费成功项目子分类
        if ($("#costItem").val() != "") {
        	bindDictNameByCodesTB("costItemDesc", $("#costItem").val());
        }
        
    }
}

function refresh() {
    window.location.reload();
}

function newAdd() {
    var htmlstr = "";
    htmlstr += "<tr>";
    htmlstr += "<td width='25%'><input type='text' name='paraName' value=''></td>";
    htmlstr += "<td width='25%'><input style='width:350px;' type='text' name='paraValue' value=''></td>";
    htmlstr += "<td width='25%'><input type='text' name='remark' value=''></td>";
    /* htmlstr += "<td width='10%'><span></span></td>";
    htmlstr += "<td width='16%'><span></span></td>"; */
    htmlstr += "<td width='25%'><a class='btn btn-xs btn-primary' href='javascript:void(0);' onclick=\"singleSave('',this)\" >保存</a>&nbsp;&nbsp;<a class='btn btn-xs btn-danger' href='javascript:void(0);' onclick=\"singleDelete('',this)\" >删除</a></td>";
    htmlstr += "</tr>";
    $("#paraList").after(htmlstr);
}

function singleDelete(seqId, thisobj) {
    var trObj = $(thisobj).parent().parent();
    if (seqId) {
        layer.confirm('确定删除？', {
            btn: ['确定', '取消'] //按钮
        },
        function() {
            var reqUrl = static_deleteurl + "&seqId=" + seqId;
            var serverData = getDataFromServer(reqUrl);
            if (serverData) {
                layer.alert('删除成功', {
                      
                    end: function() {
                        refresh();
                    }
                });
            }
        });
    } else {
        $(trObj).remove();
    }
}

function singleSave(seqId, thisobj) {
    var trObj = $(thisobj).parent().parent();
    var paraObj = new Object();
    var paramList = $(trObj).find('input,select');
    for (var i = 0; i < paramList.length; i++) {
    	var currObj = paramList[i];
    	if("select" == currObj.localName && "select-multiple" == currObj.type){
    		var selectedArray = $(currObj).val();
    		var selectedStr = "";
    		$.each(selectedArray,function(index){
    			selectedStr += (selectedArray[index] + ",");
    		});
    		paraObj[$(currObj).attr("name")] = selectedStr;
    	}else{
    		paraObj[$(currObj).attr("name")] = $(currObj).val();
    	}
        
    }
    if (seqId) {
        paraObj['seqId'] = seqId;
    } else {
        paraObj['seqId'] = '';
    }
    
    // 门诊编号
    paraObj['organization'] = organization;
    
    var url = 'YZParaAct/insertOrUpdate.act';
    var serverData = getDataFromServer(url, paraObj);
    if (serverData) {
        layer.alert('操作成功', {
              
            end: function() {
                refresh();
            }
        });
    }
}
</script>
</html>
