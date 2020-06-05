<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
   String seqId = request.getParameter("seqId");
   String parentId = request.getParameter("parentId");
   String pername = request.getParameter("pername");
   
   if(seqId==null){
	   seqId="";
   }
   if(parentId==null){
	   parentId="";
   }
   
   YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>类型添加</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/web_info.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
</head>
<body>
<!--添加回访弹窗-->
<div id="container">
    <div class="infoBd">
    <form class="form-horizontal"  id="defaultForm">
              <div class="box-body">
                		<input type="hidden" class="form-control" name="seqId" id="seqId" >
                		<input type="hidden" class="form-control" name="" id="seqId" >
                	   <div class="kv ">
				            <label>上级类别</label>
				            <div class="kv-v">
				                <input type="hidden" name="parentId" id="parentId" >
				                <input type="text" name="pername" id="pername" readonly="readonly">
				            </div>
				        </div>
				        <div class="kv ">
				            <label>添加分类</label>
				            <div class="kv-v">
				                <input type="text" name="typename" id="typename" placeholder="添加下级分类">
				            </div>
				        </div>
				        <div class="kv ">
				            <label>创建人</label>
				            <div class="kv-v">
				                <input type="text" name="createuser" id="createuser" readonly="readonly" value="<%=person.getUserName()%>">
				            </div>
				        </div>
				        <div class="kv ">
				            <label>创建时间</label>
				            <div class="kv-v">
				                <input class="whiteInpPointer" type="text" id="createtime" name="createtime" readonly="readonly" autocomplete="off">
				            </div>
				        </div>
				         <div class="kv ">
				            <label>是否分类</label>
				            <div class="kv-v" style="margin-right: 85px;">
					        	<input type="radio" name="isCategory" id="isCategory" value="1"><label for="isvisit">是</label>
				                <input type="radio" name="isCategory" id="isCategory" value="0" checked="checked"><label for="isvisit">否</label>&nbsp;
				            </div>
				        </div>
				        <div class="kv ">
				            <label>详细说明</label>
				            <div class="kv-v">
				                <textarea class="remark" rows="3" name="remark" id="remark" placeholder="详细说明" ></textarea>
				            </div>
				        </div>
              </div>
              <div class="buttonBar" style="">
              	</br>
              	<a href="javascript:void(0);" class="kqdsSearchBtn" onclick="submitu()" id="tijiao">保存</a>
			  </div>
            </form>
            </div>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script type="text/javascript">
var seqId = "<%=seqId%>";
var parentId = "<%=parentId%>";
var pername = "<%=pername%>";
var contextPath = '<%=contextPath%>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    if (seqId != null && seqId != "") { //修改
        var detailurl = contextPath+'/KQDS_MACHININGTypeAct/selectDetail.act?seqId=' + seqId;
        $.axse(detailurl, null,
        function(data) {
            loadData(data.data);
            $("#pername").val(pername);
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
    if (parentId != null && parentId != "") { //新增
        $("#parentId").val(parentId);
        $("#pername").val(pername);
    }

    var time = new Date().Format("yyyy-MM-dd");
    $("#createtime").val(time);
    
});

//提交
function submitu() {
	//console.log("提交。。。。。。。");
    //验证
    var parentId = $("#parentId").val();
    var typename = $("#typename").val();
    if (typename == "" || typename == null) {
        layer.alert('请填写类别名称' );
        return false;
    }
    if (!YzCode(seqId,parentId,typename)) {
        layer.alert('类别名称已存在！' );
        return false;
    }
    var param = $('#defaultForm').kqds_serialize();
    //console.log(param+"----------------------param");
    var url = contextPath+'/KQDS_MACHININGTypeAct/insertOrUpdate.act';
    $.axseSubmit(url, param,
    function() {},
    function(r) {
    	//console.log("返回数据="+JSON.stringify(r));
        if (r.valid == true) {
            layer.alert('保存成功', {
                  
                end: function() {
                    parent.zTreeInit();
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
function YzCode(seqId,parentId,typename) {
    var flag = true;
    var url = contextPath + '/KQDS_Ck_GoodstypeAct/YzCode.act?seqId=' + seqId + '&parentId='+parentId+'&typename=' + typename;
    $.axse(url, null,
    function(r) {
        flag = r.data;
    },
    function() {
        layer.alert('验证失败' );
    });
    return flag;
}
</script>
</html>
