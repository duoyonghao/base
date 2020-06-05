<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/kqds";
  }
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>修改建档人</title><!-- 从咨询中心 > 转诊按钮 进入转诊列表 > 点击转诊按钮 进入   -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
<style type="text/css">

.infoBd{
	font-size: 12px;
	border-bottom: solid 0px #0e7cc9;
	padding:0 10px 10px;
}

.formBox{
	overflow: hidden;
	padding: 10px 0;
	margin-bottom:10px;
	width:500px;
	margin:0 auto;
}

.formBox .kv{float:left;margin:0 10px 10px 0;}
.kv > label{margin-right: 10px;} /* 重写style.css中的label样式 */

.aBtn{display: inline-block;margin-right:10px;padding: 0 20px;height: 28px;line-height: 28px;border: solid 1px #0e7cc7;border-radius:15px;color: #0e7cc7;}
.aBtn:hover{cursor: pointer;color: #fff;background: #117cca;text-decoration: none;}
.searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {  
       width:212px;   
    }  
.searchSelect>.btn { 
	   width: 212px; 
	   height:28px; 
	   border: solid 1px #e5e5e5; 
	}
.bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
	    color: #999;
	    height: 28px;
	}
.btn-group > .btn:first-child:hover {
	    background: white;
	}
.pull-left {
    float: left !important;
    color: #666;
	}
.formBox{
	overflow: visible;
}	
.button{
	margin-top: 188px;
    margin-left: -50%;;
	}
</style>

</head>
<body>
	<div class="infoBd">
		<div class="formBox">
		<form  id="form1">
	        <div class="kv">
	          <label>建档人</label>
	          	<div class="kv-v">
<!--               		<select id="jdr_select"></select> -->
              		<select id="jdr_select" data-live-search="true" title="请选择" class="searchSelect"></select>
	    		</div>
			</div>
			<div class="kv">
			   	<label>指定原因</label>
			   	<div class="kv-v">
			       	<textarea  style="width: 380px;" name="jdRremark" id="jdRremark" rows="3" placeholder="指定原因" ></textarea>	
				</div>
			</div>
		</form>
	    </div>
	     <div class="buttonBar" style="text-align: center;">
				<a href="javascript:void(0);" onclick="saveform()" class="kqdsCommonBtn button">保存</a>
	     </div>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var selectedrows = window.parent.selectedrows;
function saveform() {
//     if ($("#createuser").val() == "") {
//         layer.alert('请选择建档人员' );
//         return false;
//     }
    if ($('#jdr_select').selectpicker('val') == "") {
        layer.alert('请选择建档人员' );
        return false;
    }
    if ($("#jdRremark").val() == "") {
        layer.alert('请填写指定原因' );
        return false;
    }
    //循环获取表格中项目
    var list = [];
    for (var i = 0; i < selectedrows.length; i++) {
    	//console.log("指定建档==="+JSON.stringify(selectedrows));
        var seqId = selectedrows[i].seq_id;
        //console.log("患者Id======"+seqId);
        //循环保存
        var param = {
            seqId: seqId,
            createuser: $("#jdr_select").val(),
            jdRremark: $("#jdRremark").val()
        };
        list.push(param);
        //console.log("list="+JSON.stringify(list));
    }
    var data = JSON.stringify(list);
    //console.log("组合后信息="+JSON.stringify(data));
    var a = {
        data: data
    };
    var url = '<%=contextPath%>/KQDS_UserDocumentAct/setInputtingPerson.act';
    $.axseSubmit(url, a,
    function() {},
    function(r) {
        if (r.retState == "0") {
            // 加个容错处理
            layer.alert('指定成功', {
                  
                end: function() {
                	window.parent.location.reload(); //刷新父页面 
                	var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭
                   
                }
            });
        } else {
            layer.alert('指定失败'  );
        }
    },
    function() {
        layer.alert('指定失败' );
    });
}
function openLayerDialogResize(actionUrl, width, height) {
    layer.open({
        type: 2,
        title: '人员选择',
        maxmin: true,
        shadeClose: true,
        // 点击遮罩关闭层
        area: ['500px', '90%'],
        content: actionUrl
    });

}
setTimeout(getKfPeople(), 0);
function getKfPeople() {
	//console.log("进入aa方法");
	var sDeptId = "58a87944-8527-4636-98fb-ba8fb9a1d24e,f7857eb9-6b51-469b-a338-7365c9b4abe5,41a669d3-fe87-4732-822b-dae6b168f4bf,aeafb25a-ae9f-4d22-8230-3725cb942435";
    var url = contextPath + '/YZPersonAct/getUserListByDeptId.act?deptId=' + sDeptId;
    var jdrSelect_html="<option value=''>请选择</option>";
    $.axseY(url, null,
    function(data) {
    	 //console.log(data+"------------啦啦啦啦啦啦");
    	 for(var a in data){
    		 //console.log(data[a].seqId);
    		//console.log(data[a].userName);
    		 jdrSelect_html+="<option value="+data[a].seqId+">"+data[a].userName+"</option>";
    	 }
    	 $("#jdr_select").html(jdrSelect_html);
      	 $('#jdr_select').selectpicker("refresh");//初始化刷新--2019-11.14 licc
        //if (data.retState == "0") {
          
            // 加载该页面的所有可操作按钮，每个页面一共有多少按钮是相对固定的，在此基础上，通过获取当前登录用户的权限按钮，来进行界面展示
        //} else {
         //   layer.alert('查询出错！'  );
        //}
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
</script>

</html>
