<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	String qxname = request.getParameter("qxname");
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>编辑部门</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css" rel="stylesheet" >
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/admin.css">
</head>

<body>
	
	<div class="container"><!--提供内边距 让内容不顶头显示-->
		<form id="defaultForm"><!-- 本身无样式  defaultForm表单验证功能要用 -->
			<input type="hidden" name="seqId" id="seqId">
			<input type="hidden" name="deptType" id="deptType">
			<table class="tableContent"><!--tableContent的样式 -->
				<tbody>
					<tr>
						<td>	
							<span class="comText">所属门诊</span>
						</td>
						<td>
							<!-- supportRole 框架生成 与辅助角色 一样的下拉菜单样式 使用同样式 -->
							<div class="form-group"  id="supportRole">
								<select id="organization" name="organization" onchange="pitchOn()"></select>
							</div>
						</td>
						<td style="padding-left: 5px;">	
							<span class="comText">部门类型</span>
						</td>
						<td>
							<!-- supportRole 框架生成 与辅助角色 一样的下拉菜单样式 使用同样式 -->
							<div class="form-group"  id="supportRole">
								<select id="deptTypeSelect" name="deptTypeSelect" style="width: 100px;" onchange="pitchOn()">
									  <option value="all">全部</option>
								      <option value="0" >咨询</option>
								      <option value="1" >医生</option>
								      <option value="2" >网电</option>
								      <option value="3" >营销</option>
								      <option value="4" >客服</option>
								      <option value="5" >护士</option>
								      <option value="6" >业绩科室</option>
								      <option value="" >其它</option>
								</select>
							</div>
						</td>	
						<td style="padding-left: 5px;">	
							<span class="comText">部门名称</span>
						</td>
						<td>
							<div class="form-group">
								<select id="deptSelect" name="deptSelect" class="deptSelect" multiple data-live-search="true" title="请选择"></select>
							</div>
						</td>
					</tr>
				</tbody>
			</table>	
		</form>
		<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
			<div class="clear2"></div>
         	<a class="kqdsCommonBtn" id="submit">保存</a>
         	<a class="kqdsCommonBtn" id="delete">取消</a>
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
var qxname = "<%=qxname%>";
$(function() {
    getSelectDeptTree4Manage("deptParent"); // 部门树下拉框
    $('.deptSelect').selectpicker();
    pitchOn("all");
    initHosSelectListNoCheck('organization');// 连锁门诊下拉框
});

/**
 * 部门管理员，点击赋值方法 ### 多选
 */
function manager_personTree_oncheck(e, treeId, treeNode) {
    var dataObj = getCheck4PersonTreeMulti(treeId);
    $("#managerInput").val(dataObj.selectName);
    $("#manager").val(dataObj.selectId);
}

function getDetail() {
    var serverData = getDataFromServer(static_detailurl);
    if (serverData) {
        loadData(serverData.retData);

        // 辅助角色赋值
        kqds_setMultiSelectVal("deptTypeSelect", serverData.retData.deptType);
        // 上级部门seqId
        static_deptParent = serverData.retData.deptParent;

        if ($("#manager").val() != "") {
            bindPerUserNameBySeqIdTB("managerDesc", $("#manager").val());
        }
       /*  if (static_deptParent == "0") { // 一级部门，可以修改部门编号
            $("#deptCode").removeAttr("readonly");
        } else {
            $("#deptCode").attr("readonly", "readonly");
        } */

    }
}

function pitchOn(){
	var organization=$("#organization").val();
	var deptType=$("#deptTypeSelect").val();
	if(deptType!=""&&organization!=""){
		$.ajax({
		    url:contextPath+"/YZDeptAct/findDeptByDeptType.act",    //请求的url地址
		    data:{deptType:deptType,organization:organization},
		    dataType:"json",   //返回格式为json
		    type:"post",   //请求方式
		    success:function(data){
		        //请求成功时处理
		        $("#deptSelect").html("");
		       	for (var i = 0; i < data.length; i++) {
		        	$("#deptSelect").append("<option value ="+data[i].id+">"+data[i].deptname+"</option>")
				}
		        $('.deptSelect').selectpicker('refresh'); 
		    }
		});
	}
	
} 


$('#delete').on('click',
function() {
	var frameindex= parent.layer.getFrameIndex(window.name)
 	parent.layer.close(frameindex); //执行关闭
});
$('#submit').on('click',
function() {
	 var deptSelect = $("#deptSelect").val();

	 if(deptSelect==""){
		 layer.alert('请选择部门！' );
	     return false;
	 }
	 var deptType=$("#deptTypeSelect").val();
	 
	 layer.confirm('是否保存？', {
       	 closeBtn: 0,
            btn: ['是', '否']
        },
        function() {
        	$.ajax({
    		    url:contextPath+"/SysDeptPrivAct/insertSysDeptPriv.act",
    		    data:{deptNoCompilations:JSON.stringify(deptSelect),qxname:qxname,deptType:deptType,organization:$("#organization").val()},
    		    dataType:"json",   //返回格式为json
    		    type:"post",   //请求方式
    		    success:function(data){
    		    	//console.log(data);
    		    	if(data){
    		    		layer.alert('保存成功！',{
    			            end: function() {
    			                var frameindex = parent.layer.getFrameIndex(window.name);
    			                parent.layer.close(frameindex); //再执行关闭
    			              	window.parent.location.reload(); //刷新父页面
    			            }
    		        	});
    		    	}
    		    }
    		});
        },
        function() {
        	var frameindex= parent.layer.getFrameIndex(window.name)
       	 	parent.layer.close(frameindex); //执行关闭
        });
});

</script>
</html>
