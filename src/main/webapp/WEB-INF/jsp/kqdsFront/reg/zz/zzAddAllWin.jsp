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
<title>转诊</title><!-- 从咨询中心 > 转诊按钮 进入转诊列表 > 点击转诊按钮 进入   -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
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
</style>

</head>
<body>
	<div class="infoBd">
		<div class="formBox">
		<form  id="form1">
	   		<input type="hidden"  name="regno" id="regno" placeholder="挂号编号">																	
	        <div class="kv">
	          <label>转诊人</label>
	          <div class="kv-v">
              		<input type="hidden" name="toaskperson" id="toaskperson"   />
					<input type="text"   id="toaskpersonDesc" name="toaskpersonDesc" placeholder="转诊人" readonly style="width: 120px;"onClick="javascript:single_select_user(['toaskperson', 'toaskpersonDesc']);"  ></input>	
	    		</div>
			</div>
			<div class="kv">
			   	<label>转诊原因</label>
			   	<div class="kv-v">
			       	<textarea  style="width: 380px;" name="remark" id="remark" rows="3" placeholder="" ></textarea>	
				</div>
			</div>
		</form>
	    </div>
	     <div class="buttonBar" style="text-align: center;">
				<a href="javascript:void(0);" onclick="saveform()" class="kqdsCommonBtn">保存</a>
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
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var rows = window.parent.selectedrows;
function saveform() {
    if ($("#oldaskperson").val() == "") {
        layer.alert('请选择转诊医生' );
        return false;
    }
    if ($("#remark").val() == "") {
        layer.alert('请填写转诊原因' );
        return false;
    }
	//循环获取表格中项目
	var list = [];
	for(var i = 0 ; i < rows.length; i++){
		var seqId = rows[i].seqId;
		//循环保存
		var param = {
			seqId : seqId
		};
		list.push(param);
	}
	var data = JSON.stringify(list); 
	var a={
		data:data,
		toaskperson : $("#toaskperson").val(),
		remark : $("#remark").val()
	};
    var url = '<%=contextPath%>/KQDS_ChangeReceiveAct/zzAll.act';
    $.axseSubmit(url, a,
    function() {},
    function(r) {
        if (r.retState == "0") {
            // 加个容错处理
            layer.alert('转诊成功', {
                  
                end: function() {
                	parent.searchHzda();
                    var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(frameindex); //再执行关闭 */
                }
            });
        } else {
            layer.alert('转诊失败'  );
        }
    },
    function() {
        layer.alert('转诊失败' );
    });
}
function openLayerDialogResize(actionUrl, width, height) {
    layer.open({
        type: 2,
        title: '人员选择',
        maxmin: true,
        shadeClose: true,
        // 点击遮罩关闭层
        area: ['500px', '480px'],
        content: actionUrl
    });

}
</script>

</html>
