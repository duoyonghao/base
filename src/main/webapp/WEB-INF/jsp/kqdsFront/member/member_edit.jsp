<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String seqId = request.getParameter("seqId");
	if(seqId == null || seqId == ""){
		seqId = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>会员卡修改</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/member/member.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
</head>
<body>	
	<div class="memberAddContainer"> <!-- .memberAddContainer 会员卡办理页面容器 -->
		<form id="defaultForm">
			<table  class="tableLayout" id="kqds_table">	<!-- .tableLayout 布局样式  -->
				<tr>
					<td> <!-- .commonText 信息文本的样式 -->
						<span class="commonText">选择患者：</span>
					</td>
					<td>	<!-- .whiteInp鼠标移入有小手效果 -->
						<input class="whiteInp" type="text" id="username" name="username" readonly/> 
					</td>
					<td>
						<span class="commonText">编<span style="visibility:hidden;">占位</span>号：</span>
					</td>
					<td>
						<input type="text" name="usercode" id="usercode" readonly/> 
					</td>
				</tr>
				
				<tr>
					<td>
						<span class="commonText">会员卡号：</span>
					</td>
					<td>
						<input type="text" id="memberno" name="memberno"> 
					</td>
					<td>
						<span class="commonText">备<span style="visibility:hidden;">占位</span>注：</span>
					</td>
					<td>
						<input type="text" id="remark" name="remark" > 
					</td>
				</tr>
				<tr>		
					<td>
						<span class="commonText">会员类型：</span>
					</td>
					<td>
						<select class="patients-source select2 dict" tig="HYKFL" name="memberlevel" id="memberlevel">
						</select> 
					</td>
					<td>
						<span class="commonText">折扣：</span>
					</td>
					<td>
						<input type="text"  id="discount" name="discount" readonly="readonly">
					</td>
				</tr>
				<tr>		
					<td>
						<span class="commonText">折扣截止日期：</span>
					</td>
					<td>
						<input type="text"  id="discountdate" name="discountdate" class="unitsDate"/>
					</td>
				</tr>
				<tr>	
					<td>
						<span class="commonText">充值金额：</span>
					</td>
					<td>
						<input type="text" id="money" name="money" value="0" readonly>
					</td>
					<td>
						<span class="commonText">赠送金额：</span>
					</td>
					<td>
						<input type="text" id="givemoney" name="givemoney" value="0" readonly> 
					</td>
				</tr>
			</table>
		</form>
	</div> <!-- .fixedBottomDiv 固定底部的三个按钮 -->
	<footer class="fixedBottomDiv">
		<div class="clear2"></div>
       	<a class="kqdsCommonBtn" id="close">取 消</a>
       	<a class="kqdsSearchBtn" style="background:#0E7CC9;color:#fff;" id="submit">确 定</a>
	</footer>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/member/kqds_member_binding.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/member/kqds_member_add.js"></script>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var seqId = '<%=seqId%>';
var frameindex= parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function(){
	 //时间选择
    $(".unitsDate").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });
	//咨询 下拉列表
	initPersonSelectByDeptType("askperson","<%=ConstUtil.DEPT_TYPE_0 %>");
	initDictSelectByClass(); // 会员类型
	var detailurl = '<%=contextPath%>/KQDS_MemberAct/selectDetail.act?seqId='+seqId;
	$.axse(detailurl,null, 
              function(data){
		  		  loadData(data.data);
              },
              function(){
            	  layer.alert('查询出错！' );
          	  }
    );
});
$('#memberlevel').change(function() {
	var data = getDictByDictSeqId($(this).val())
	if(data){ // 如果一级有值，再请求
		$("#discount").val(data.remark);
	}
});
$('#submit').on('click', function(){
	if($("#memberno").val() == "" || $("#memberno").val() == null){
		layer.alert('请输入IC卡号', {
			  
		});
		return false;
	}
	if (judgeSign($("#discount").val()) == true) {  // 验证数量是否为正数
        if(Number($("#discount").val())>100){
        	 layer.alert('折扣不能大于100' );
             return false;
        }
    } else {
        layer.alert('折扣必须为正数' );
        return false;
    }
	var param = {
		seqId:seqId,
		discount:$("#discount").val(),
		discountdate:$("#discountdate").val(),
		memberno:$("#memberno").val(),
		memberlevel:$("#memberlevel").val(),
		remark:$("#remark").val()
	}
    var url = contextPath + '/KQDS_MemberAct/editMember.act';
    $.axse(url, param,
    function(r) {
        var retdata = r.retData;
        if (r.retState == "0") {
            layer.alert('修改成功', {
                  
                end: function() {
                	 if(parent.query){
                		 parent.query();
                	 }
                     parent.layer.close(frameindex); //再执行关闭 */
                }
            });
        } else {
            layer.alert(r.retMsrg  );
        }
    },
    function() {
        layer.alert(r.retMsrg  );
    });
});
$('#close').on('click', function(){
	parent.layer.close(frameindex); //执行关闭
});
</script>
</html>
