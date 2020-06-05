<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String usercode = request.getParameter("usercode");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>增加积分</title>
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
			    			<td>积分金额：</td>
			    			<td style="width:80%;text-align:left;"> 
		    					<input type="text" class="form-control" name="jfmoney" id="jfmoney">
			                </td>
			    		</tr>
			    		<tr>
			    			<td>备注：</td>
			    			<td style="width:80%;text-align:left;" colspan="3">
			    				<textarea style="width: 90%;" rows="" cols="" class="form-control" id="remark" name="remark" ></textarea>
							</td>
			    		</tr>
			   </table>
			        <div class="kv-v" style="text-align:center;width:100%">
			            </br>
		                <input type="button" class="btn btn-info" onclick="submitu()" value="确定" style="width:18%;" id="tijiao"/>
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
var contextPath = '<%=contextPath%>';
var usercode = '<%=usercode%>';
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
	initDictSelectByClass();
});

//禁用回车事件
function keydownEvent() {
    var e = window.event || arguments.callee.caller.arguments[0];
    if (e && e.keyCode == 13 ) {
    	e.preventDefault ? e.preventDefault() : e.returnValue = false;
    }
}
//提交
function submitu(){
	//验证
	var jfmoney = $("#jfmoney").val();
	if(jfmoney == "" || jfmoney == null) {
		layer.alert('请输入增加的积分金额' );
		return false;
	}
	if(judgeSign(jfmoney)!=true){
		layer.alert('输入项必须为正数' );
		return false;
	}
	var url = contextPath+'/KQDS_Integral_RecordAct/insert.act';
    var urldata = {
    		jfmoney:$("#jfmoney").val(),
    		jftype:1,
    		remark: $("#remark").val(),
    		usercode:usercode
    };
    $.axseSubmit(url, urldata,null,
    function(r) {
        if (r.retState == "0") {
        	 layer.alert('操作成功', {
	                
	              end: function() {
	            	    parent.searchHzda();
			        	parent.layer.close(frameindex); //再执行关闭 
	              }
	          });
        } else {
            layer.alert('操作失败'  );
        }
    },
    function() {
        layer.alert('操作失败' );
    });
}
</script>

</html>
