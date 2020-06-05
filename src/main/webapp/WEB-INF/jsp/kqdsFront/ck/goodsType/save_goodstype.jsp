<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
   String seqId = request.getParameter("seqId");
   String perid = request.getParameter("perid");
   String pername = request.getParameter("pername");
   
   if(seqId==null){
	   seqId="";
   }
   if(perid==null){
	   perid="";
   }
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>商品类别</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/web_info.css" />
</head>
<body>
<!--添加回访弹窗-->
<div id="container">
    <div class="infoBd">
    <form class="form-horizontal"  id="form1">
              <div class="box-body">
                	<input type="hidden" class="form-control" name="seqId" id="seqId" >
                	   <div class="kv ">
				            <label>上级类别</label>
				            <div class="kv-v">
				                <input type="hidden" name="perid" id="perid" >
				                <input type="text" name="pername" id="pername" readonly="readonly">
				            </div>
				        </div>
				        <div class="kv ">
				            <label>分类名称</label>
				            <div class="kv-v">
				                <input type="text" name="goodstype" id="goodstype" >
				            </div>
				        </div>
				        <div class="kv ">
				            <label>排序号</label>
				            <div class="kv-v">
				                <input type="text" name="sortno" id="sortno" >
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
<script type="text/javascript">
var seqId = "<%=seqId%>";
var perid = "<%=perid%>";
var pername = "<%=pername%>";
var contextPath = '<%=contextPath%>';
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    if (seqId != null && seqId != "") { //修改
        var detailurl = contextPath+'/KQDS_Ck_GoodstypeAct/selectDetail.act?seqId=' + seqId;
        $.axse(detailurl, null,
        function(data) {
            loadData(data.data);
            $("#pername").val(pername);
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
    if (perid != null && perid != "") { //新增
        $("#perid").val(perid);
        $("#pername").val(pername);
    }
});

//提交
function submitu() {
    //验证
    var perid = $("#perid").val();
    var goodstype = $("#goodstype").val();
    if (goodstype == "" || goodstype == null) {
        layer.alert('请填写类别名称' );
        return false;
    }
    if (!YzCode(seqId,perid,goodstype)) {
        layer.alert('类别名称已存在！' );
        return false;
    }
    var sortno = $("#sortno").val();
    if (sortno != null && sortno != "") {
    	if(judgeSign(sortno)==false){
    		 layer.alert('排序号必须为正整数', {
    	              
    	        });
    	        return false;
    	}
    }  
    var param = {
        seqId: $("#seqId").val(),
        goodstype: goodstype,
        sortno: sortno,
        perid: $("#perid").val(),
        pername: $("#pername").val()
    };
    var url = contextPath+'/KQDS_Ck_GoodstypeAct/insertOrUpdate.act';
    $.axseSubmit(url, param,
    function() {},
    function(r) {
        if (r.retState == "0") {
            layer.alert('保存成功', {
                  
                end: function() {
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
function YzCode(seqId,perid,goodstype) {
    var flag = true;
    var url = contextPath + '/KQDS_Ck_GoodstypeAct/YzCode.act?seqId=' + seqId + '&perid='+perid+'&typename=' + goodstype;
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
