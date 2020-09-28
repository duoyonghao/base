<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	
	// 患者编号
	String usercode = request.getParameter("usercode");
	if(usercode == null){
		usercode="";
	} 
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>layui</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="<%=contextPath%>/static/plugin/layui/css/layui.css"  media="all">
</head>
<style>
iframe{float: left;height: 450px;}
</style>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
  <legend>合并患者</legend>
</fieldset>
<div class="layui-form-item">
		<label class="layui-form-label">患者档案1：</label>
	    <label class="layui-form-label">患者姓名</label>
	    <div class="layui-input-inline">
	      <input type="text" id="username1"  placeholder="请选择患者" autocomplete="off" readonly onclick="getuserandmemberno1()" class="layui-input">
	    </div>
	    <label class="layui-form-label">患者编号</label>
	    <div class="layui-input-inline">
	      <input type="text" id="usercode1"  readonly autocomplete="off" class="layui-input">
	    </div>
</div>
<div class="layui-form-item">
	<label class="layui-form-label">患者档案2：</label>
	    <label class="layui-form-label">患者姓名</label>
	    <div class="layui-input-inline">
	      <input type="text" id="username2"  placeholder="请选择患者" autocomplete="off" readonly onclick="getuserandmemberno2()" class="layui-input">
	    </div>
	    <label class="layui-form-label">患者编号</label>
	    <div class="layui-input-inline">
	      <input type="text" id="usercode2"  readonly autocomplete="off" class="layui-input">
	    </div>
	    <button class="layui-btn" style="background:#00A6C0 ;color:#fff;" onclick="hbhz();">合并</button>
	    <label style="color: red;">（注：由患者档案1 合并到 患者档案2）</label>
</div>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
  <legend>患者业务信息</legend>
</fieldset> 
<div class="layui-tab layui-tab-card">
  <ul class="layui-tab-title">
    <li target="tabIframe" src="<%=contextPath%>/KQDS_UserDocumentAct/toGrxx.act" class="layui-this">个人信息</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_Net_OrderAct/toNetorder.act">网电信息</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_ReceiveInfoAct/toReceive.act" >咨询记录</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_CostOrder_DetailAct/toCostDetail.act" >费用详情</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_VisitAct/toVisitList2.act" >回访管理</li>
    <li target="tabIframe" src="<%=contextPath%>/KQDS_GiveItem_UseRecordAct/toZengSongList.act" >赠送项目</li>
  </ul>
  <div class="layui-tab-content" style="height: 450px;">
    <div class="layui-tab-item layui-show">
		<iframe id="tabIframe1" src="<%=contextPath%>/KQDS_UserDocumentAct/toGrxx.act" width="50%"  frameborder="0" class="tabIframe"></iframe>
    	<iframe id="tabIframe2" src="<%=contextPath%>/KQDS_UserDocumentAct/toGrxx.act" width="50%"  frameborder="0" class="tabIframe"></iframe>
    </div>
  </div>
</div>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>        
<script src="<%=contextPath%>/static/plugin/layui/layui.js" charset="utf-8"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script>
var contextPath = "<%=contextPath %>";
var usercodechild,usernamechild;
layui.use('element', function(){
  var element = layui.element(); //Tab的切换功能，切换事件监听等，需要依赖element模块
});
//iframe 的src 设置参数
$('.layui-tab-title').on('click','li',function () {
	var usercode1 = $("#usercode1").val();
	var usercode2 = $("#usercode2").val();
	if(usercode1=="" || usercode2==""){
		layer.alert('请选择患者!' );
        return false;
	}
	$(this).addClass('layui-this').siblings('li').removeClass('layui-this');
	var src = $(this).attr('src');
	var src1 = src + "?usercode="+$("#usercode1").val();
	var src2 = src + "?usercode="+$("#usercode2").val();
	$('#tabIframe1').attr('src',src1);
	$('#tabIframe2').attr('src',src2);
});
function getuserandmemberno1(){
	//查询患者
	layer.open({
        type: 2,
        title: '选择患者',
        shadeClose: true,
        shade: 0.6,
        area: ['80%', '80%'],
        content: contextPath+'/KQDS_UserDocumentAct/toUserList.act',//iframe的url
        end: function(index, layero){
        	$("#usercode1").val(usercodechild);
        	$("#username1").val(usernamechild);
        	//清空子页面传值
        	usercodechild = "";
        	usernamechild = "";
        },
    });
}
function getuserandmemberno2(){
	//查询患者
	layer.open({
        type: 2,
        title: '选择患者',
        shadeClose: true,
        shade: 0.6,
        area: ['80%', '80%'],
        content: contextPath+'/KQDS_UserDocumentAct/toUserList.act?typechoose=1',
        end: function(index, layero){
        	$("#usercode2").val(usercodechild);
        	$("#username2").val(usernamechild);
        	//清空子页面传值
        	usercodechild = "";
        	usernamechild = "";
        },
    });
}
function hbhz(){
	var usercode1 = $("#usercode1").val();
	var usercode2 = $("#usercode2").val();
	if(usercode1=="" || usercode2==""){
		layer.alert('请选择需要合并的患者!' );
        return false;
	}
	if(usercode1==usercode2){
		layer.alert('同一患者不能合并!' );
        return false;
	}
	 var nexturl = getOneByUsercode(usercode1);
	 if( nexturl.data.length==0){
		 layer.alert('编号为'+usercode1+',患者不存在!' );
	     return false;
	 }
	 
     var nexturl2 = getOneByUsercode(usercode2);
     if( nexturl2.data.length==0){
		 layer.alert('编号为'+usercode2+',患者不存在!' );
	     return false;
	 }
     
	 var url = contextPath+'/KQDS_UserDocumentAct/hzhb.act?usercode1='+usercode1+'&usercode2='+usercode2;
	 $.axse(url,null,
              function(r){
			        if(r.retState=="0"){
			        	layer.alert('合并成功', {
			        		  
			        		end: function () {
							}
						});
			        }else{
			        	layer.alert('合并失败' );
			        }     
              },
              function(){
            	  layer.alert('合并失败' );
          	  }
        );
}
</script>

</body>
</html>