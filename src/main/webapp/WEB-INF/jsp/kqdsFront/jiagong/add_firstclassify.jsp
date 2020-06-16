<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
   String seqId = request.getParameter("seqId");
   if(seqId == null){
	   seqId = "";
   }
   String typename = request.getParameter("typename");
   YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>添加一级</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/kqdsCss/kqdsCommon.css"/>
<style>
	table.tableLayout{/* table布局内容距离页面顶端30px */
		margin-top:30px;
	}
	.commonText{	/*数据项的样式  */
		padding: 0 10px;
	}
	table.tableLayout select,table.tableLayout input[type="text"]{
		width:150px;
	}
</style>
</head>
<body>
<!--添加一级-->
<div id="container">
    <div class="infoBd">
    	<form class="form-horizontal"  id="defaultForm">
             <table class="tableLayout">
	         	<tbody>
	         		<tr style="height: 35px;">
	         			<td>
	         				<span class="commonText" style="padding-left:20px;">类型名称</span>
	         			</td>
	         			<td>
	         				<input type="hidden" name="seqId" id="seqId" >
	         				<input type="text" name="typename" id="typename" >
	         			</td>
	         			<td>
	         				<span class="commonText" style="padding-left:20px;">显示分类</span>
	         			</td>
	         			<td>
				            <div class="kv-v" style="margin-right: 85px;">
					        	<input type="radio" name="isCategory" id="isCategory" value="1" checked="checked"><label for="isvisit">是</label>
				                <input type="radio" name="isCategory" id="isCategory" value="0" ><label for="isvisit">否</label>&nbsp;
				            </div>
				        <td>
	         		</tr>
	         		<tr style="height: 35px;">
	         			<td>
	         				<span class="commonText" style="padding-left:20px;">创建人</span>
	         			</td>
	         			<td>
	         				<input type="text" name="createuser" id="createuser" value="<%=person.getUserName()%>" readonly="readonly">
	         			</td>
	         			<td>
	         				<span class="commonText" style="padding-left:20px;">创建时间</span>
	         			</td>
	         			<td>
	         				<input type="text" name="createtime" id="createtime" readonly="readonly">
	         			</td>
	         		</tr>
	         	</tbody>
	         </table>   
         </form>
         <div class="position-bottom" >
			<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="submitu()" id="tijiao">保存</a>
		</div>
     </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>

<script type="text/javascript">
var seqId = "<%=seqId%>";
console.log(seqId+"-----");
var typename = "<%=typename%>";
var contextPath = '<%=contextPath%>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
	 var time = new Date().Format("yyyy-MM-dd hh:mm:ss");
	    $("#createtime").val(time);
	    //console.log(typename+"---typename");
	    if(typename=="null"){
	    	 $("#typename").val("");
	    }else{
	    	$("#typename").val(typename);
	    	$("#seqId").val(seqId);
	    }
	   
});

//提交
function submitu() {
    var param = $('#defaultForm').kqds_serialize(); 
//     console.log(param+"-----------param");
//     return;
    var url = '<%=contextPath%>/KQDS_MACHININGTypeAct/insertOrUpdate.act';
    $.axseSubmit(url, param,
    function() {},
    function(r) {
    	//console.log(JSON.stringify(r)+"-------------------rrr");
        if (r.valid) {
            layer.alert('保存成功', {
                  
                end: function() {
                    parent.parent.zTreeInit();
                    parent.refresh();
                    parent.layer.close(frameindex); //再执行关闭
                }
            });
        } else {
            layer.alert('保存失败'  );
        }
    },
    function() {
        layer.alert('保存失败' );
    });
}
</script>
</html>
