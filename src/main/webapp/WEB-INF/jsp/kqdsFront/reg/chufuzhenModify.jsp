<%@page import="com.kqds.util.sys.YZUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	
	String regSeqId = request.getParameter("regSeqId");
	if(YZUtility.isNullorEmpty(regSeqId)){
		regSeqId = "";
	}
	
	String recesortvalue = request.getParameter("recesortvalue");
	if(YZUtility.isNullorEmpty(recesortvalue)){
		recesortvalue = "";
	}
	
	String organization = request.getParameter("organization");
	if(YZUtility.isNullorEmpty(organization)){
		organization = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8" />
    <title>修改初复诊</title><!-- 客户管理菜单  点击 添加推广 按钮 进入 -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/web_info.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
</head>
<style type="text/css">
/*工作量表格 ，单独写*/
#gongzuol{border:solid 1px #c0c0c0;background: #f5f5f5;}
#gongzuol{margin-bottom: 10px;}
#gongzuol .columnHd{padding:0 15px;border-bottom:solid 1px #0e7ec6;font-size:16px;color: #373737;font-family: "Microsoft YaHei";line-height:36px;}
.kqds_table{
	width:90%;
	align:center;
	margin-left: auto;
	margin-right: auto;
}
	
.kqds_table  td { 
	color: #666;
	padding: 3px 2px 5px 2px;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	font-weight: normal;
	line-height: 28px;
}
	
.kqds_table  select { 
	height: 28px;
	width:120px;
	border: solid 1px #e5e5e5;
	border-radius: 3px;
}

input[type=text],.kv .kv-v input[type=text]{padding:0 10px;width:120px;height: 28px;line-height: 28px;border: solid 1px #e5e5e5;border-radius: 3px;-webkit-transition: all .3s;transition: all .3s;}
	
</style>
<body>
<!--添加回访弹窗-->
<div id="container">
    <div class="infoBd">
    <form class="form-horizontal"  id="form1">
              <div class="box-body">
              <table class="kqds_table">
			    		<tr>
							<td style="width:10%;text-align:right;">就诊分类：</td>
			    			<td style="width:20%;text-align:left;"> 
			    				<select class=" dict" tig="jzfl" name="recesort" id="recesort"  data-bv-notempty data-bv-notempty-message="就诊分类不能为空">
	                			</select>
			                </td>
			    		</tr>
			   </table>
			        <div class="kv-v" style="text-align:center;width:100%">
			            </br>
		                <input type="button" class="btn btn-info" onclick="submitu()" value="确定修改" style="width:18%;" id="tijiao"/>
		            </div>
              </div>
      </form>
      </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">

var contextPath = '<%=contextPath %>';
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var regSeqId = "<%=regSeqId %>";
var recesortvalue = "<%=recesortvalue %>";
var organization="<%=organization%>";
$(function () {
	initDictSelect2("recesort", "jzfl"); // 必须请求方式为同步，
	$("#recesort").val(recesortvalue);
});
function initDictSelect2(id, dictType) {
    var dict = $("#" + id);
    var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=" + dictType;
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];      
                if(optionStr.seqId=="20"){
                	dict.append("<option value='" + optionStr.seqId + "' disabled>" + optionStr.dictName + "</option>");
                }else{
                	dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                }  
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
//提交
function submitu(){
	//验证
	var recesort = $("#recesort").val();
	if(recesort == "" || recesort == null) {
		layer.alert('请选择就诊分类' );
		return false;
	}
	var a={
		seqId:regSeqId,
		recesort:recesort,
		organization:organization
	};
	
	layer.confirm('确定修改吗？', {
        btn: ['确定', '取消'] //按钮
    },
    function() {
    	var url = '<%=contextPath%>/KQDS_REGAct/chufuzhenModify.act';
        $.axseSubmit(url,a, 
   		function(){
   			 layer.msg('加载中', {icon: 16});
   	  	},
        function(r){
      	        if(r.retState=="0"){
      	        	layer.alert('修改成功', {  end:function(){
      	        		parent.shuaxin();
      	        		parent.layer.close(frameindex); //关闭当前弹层
      	        	}});
      	        }else{
      	        	layer.alert('修改失败' );
      	        }     
              },
              function(){
            	    layer.alert('修改失败' );
   		}
   		);
    });
	
	  
}
</script>

</html>
