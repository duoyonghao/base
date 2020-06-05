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
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>添加回访</title>
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
		width:155px;
	}
	td, th {
    	padding: 5px;
	}
</style>
</head>
<body>
<!--添加回访弹窗-->
<div id="container">
    <div class="infoBd">
    	<form class="form-horizontal"  id="form1">
    		<input type="hidden" class="form-control" name="seqId" id="seqId" >
             <table class="tableLayout">
	         	<tbody>
	         		<tr>
	         			<td>
	         				<span class="commonText">排序号</span>
	         			</td>
	         			<td>
	         				 <input type="text" name="sortno" id="sortno" >
	         			</td>
	         			<td>
	         				<span class="commonText" style="padding-left:20px;">仓库名称</span>
	         			</td>
	         			<td>
	         				<input type="text" name="housename" id="housename" >
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

<script type="text/javascript">
var seqId = "<%=seqId%>";
var contextPath = '<%=contextPath%>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    if (seqId != null && seqId != "") {
        var detailurl = '<%=contextPath%>/KQDS_Ck_HouseAct/selectDetail.act?seqId=' + seqId;
        $.axseY(detailurl, null,
        function(data) {
            loadData(data.data);
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
});

//提交
function submitu() {
    //验证
    var sortno = $("#sortno").val();
    var housename = $("#housename").val();
    if (sortno != null && sortno != "") {
    	if(judgeSign(sortno)==false){
    		 layer.alert('排序号必须为正整数', {
    	              
    	        });
    	        return false;
    	}
    }  
    if (housename == "" || housename == null) {
        layer.alert('请填写仓库名称' );
        return false;
    }

    var param = {
        seqId: $("#seqId").val(),
        sortno: sortno,
        type: $("#type").val(),
        housename: housename
    };
    var url = '<%=contextPath%>/KQDS_Ck_HouseAct/insertOrUpdate.act';
    $.axseSubmit(url, param,
    function() {},
    function(r) {
        if (r.retState == "0") {
            layer.alert('保存成功', {
                  
                end: function() {
//                     parent.parent.zTreeInit();
		               parent.zTreeInit();
		               parent.refreshCk();
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
