<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>用户注册</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/font-awesome/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/Ionicons/ionicons.min.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/dict/AdminLTE.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/dict/all-skins.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<style>
	#yzcode i.form-control-feedback {
	    left: 116px;
	}
</style>
</head>

<body class="skin-blue sidebar-mini" style="height: auto; min-height: 100%;background-color: #ecf0f5;">
	<section class="content">
	<div class="row">
	<div class="col-md-6">
	<div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">用户注册</h3>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <form class="form-horizontal" id="defaultForm">
	              <div class="box-body">
		                <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">单位名称</label>
			                  <div class="col-sm-10">
			                    <input type="text" class="form-control" id="dwmc" name="dwmc">
			                  </div>
		                </div>
		                <div class="form-group">
			                  <label for="inputPassword3" class="col-sm-2 control-label">联系人</label>
			                  <div class="col-sm-10">
			                    <input type="text" class="form-control" id="lxr" name="lxr">
			                  </div>
		                </div>
		                <div class="form-group">
			                  <label for="inputPassword3" class="col-sm-2 control-label">手机号码</label>
			                  <div class="col-sm-10">
			                    <input type="text" class="form-control" id="sjhm" name="sjhm">
			                  </div>
		                </div>
		                <div class="form-group" style="display: none;">
			                  <div class="col-sm-10 input-group">
			                    <input type="text" class="form-control" id="receivecode" name="receivecode" value="168168">
			                  </div>
		                </div>
		                <div class="form-group col-lg-6" >
			                  <label style="display:block;padding-left:0;" for="inputPassword3" class="col-sm-2 control-label">验证码</label>
			                  <div id="yzcode" style="width:100%;" class="col-sm-10 input-group">
			                    <input style="float:none;display:inline-block;width:150px;" type="text" class="form-control" id="code" name="code">
			                    <button class="btn btn-default" type="button" id="getcode">获取验证码</button>
			                  </div>
			                 
		                </div>
	              </div>
	              <!-- /.box-body -->
	              <div class="box-footer">
		                <button type="button" id="cancel" class="btn btn-default">取消</button>
		                <button type="button" id="submit" class="btn btn-info pull-right">注册</button>
	              </div>
	              <!-- /.box-footer -->
          </form>
   </div>
   </div>
   </div>
   </section> 
</body>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
</script>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/register.js"></script>
<script type="text/javascript">
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function(){
	Yztable_net();
});

$('#submit').on('click', function(){
	 var flag = submit(); //submit()校验方法
	 if (!flag) {
        return false;
     }
	 var param = $('#defaultForm').serialize();
     var url = contextPath + '/YZRegisterAct/insert.act?' + param;
     $.axse(url, null,
     function(r) {
         if (r.retState == "0") {
            layer.alert('注册成功，请等待短信告知', {
                   
                 end: function() {
                	 parent.layer.close(frameindex); //执行关闭
                 }
             });
             return true;
         }else{
             layer.alert('注册失败', {
                   
             });
             return false;
         }
     },
     function() {
         layer.alert('注册失败', {
               
         });
         return false;
     });
});
$('#cancel').on('click', function(){
	parent.layer.close(frameindex); //执行关闭
});

</script>
</html>
