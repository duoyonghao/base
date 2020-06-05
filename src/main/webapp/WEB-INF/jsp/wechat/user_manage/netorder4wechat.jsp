<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	String wxOrderSeqId = request.getParameter("wxOrderSeqId");
	if(wxOrderSeqId == null ){
		wxOrderSeqId = "";  
	}
	
	String usercode = request.getParameter("usercode");
	if(usercode == null ){
		usercode = "";  
	}
%>
<!DOCTYPE html>
<html>
<!-- 本页面已优化（输入框需要再次优化）鲍3-26 -->
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>微信预约-审核</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/hzjd/hzjd.css">
</head>
<body>
<div class="containerNet">	<!-- .containerNet网电建档容器样式 -->
	<form id="defaultForm">
		<table class="tableContent">	<!--tableContent 布局样式  -->
			<!-- <tr>
				<td colspan="4" class="relative">		分隔线 
					<hr class="line">
				</td>
			</tr> -->
			<tr>
				<td>
					<span class="impText">受理类型*</span>
				</td>
				<td>
					<div class="form-group">
						<select class="dict" tig="SLLX" name="accepttype" id="accepttype" >
						</select>
					</div>
				</td>
				<td>
					<span class="impText">受理工具</span>
				</td>
				<td>
					<div class="form-group">
						<select class="dict" tig="SLGJ" name="accepttool" id="accepttool" >
						</select>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
					<span class="impText">咨询项目*</span>
				</td>
				<td>
					<div class="form-group">
						<select class="dict" tig="ZXXM" name="askitem" id="askitem" >
						</select>
					</div>
				</td>
				<td>
					<span class="comText">预约日期</span>
				</td>
				<td>
					<div class="form-group">
						<input type="text" id="ordertime" name="ordertime" value=""  >
					</div>
				</td>
			</tr>
			
			<tr class="textareaTr3" style="height:204px;"><!-- textareaTr3 调整该行的行高   -->
				<td>
					<span class="impText">咨询内容*</span>
				</td>
				<td colspan="3">
					<div class="form-group">
						<textarea style="height:180px" name="askcontent" id="askcontent" rows="3" ></textarea>
					</div>
				</td>
			</tr>
		</table>
	</form>
	<div class="fixedBottomDiv"><!--底部三个按钮所在父元素 固定在底部样式  -->
		<div class="clear2"></div>
       	<a class="kqdsCommonBtn" id="submit">确 定</a>
	</div>
</div>
</body>

<script type="text/javascript">
//### 这个变量声明在下面，hzjd.js调用不到
var contextPath = '<%=contextPath%>';
</script>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/area.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/city/location.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/index/bower_components/jquery-ui/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/hzjd/hzjd.js"></script>

<script type="text/javascript">
var intoPageDateTime = new Date();
var wxOrderSeqId = '<%=wxOrderSeqId %>';
var usercode = '<%=usercode %>';
//信息报备权限，即是否具备修改开发人员的权限  0可以 1不可以
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
$(function() {
    /** ########################################################### 连锁相关  **/
    $("#ordertime").datetimepicker({
        language: 'zh-CN',
        minView: 0,
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        //选中之后自动隐藏日期选择框   
        pickerPosition: "bottom-right"
    });
    /** ########################################################### 连锁相关  end **/

    initDictSelectByClass(); // 受理类型、受理工具、咨询项目、患者来源、职业
    Yztable_net();

    //获取微信注册患者信息
    getWxDetail();
    submit();
});

/** 本页面单独调用 **/
function Yztable_net() {
    $('#defaultForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            accepttype: {
                validators: {
                    notEmpty: {
                        message: '请选择受理类型'
                    }
                }
            },
            accepttool: {
                validators: {
                    notEmpty: {
                        message: '请选择受理工具'
                    }
                }
            },
            askitem: {
                validators: {
                    notEmpty: {
                        message: '请选择咨询项目'
                    }
                }
            },
            ordertime: {
                validators: {
                    notEmpty: {
                        message: '请填写预约日期'
                    }
                }
            },
            askcontent: {
                validators: {
                    notEmpty: {
                        message: '请填写咨询内容'
                    }
                }
            }
        }
    });
}

function getBaseInfoByWxId(wxOrderSeqId) {
    var baseInfo = null;
    var detailurl = contextPath + "/WXOrderAct/selectDetail.act?seqId=" + wxOrderSeqId;
    $.axse(detailurl, null,
    function(data) {
        baseInfo = data.data;
    },
    function() {
        layer.alert('查询出错！'  );
    });
    return baseInfo;
}
function getWxDetail() {
    var baseInfo = getBaseInfoByWxId(wxOrderSeqId);
    if (baseInfo) {
        loadData(baseInfo);
        $('#accepttool option:contains(公众微信号)').each(function() {
            if ($(this).text() == '公众微信号') {
                $(this).attr('selected', true);
            }
        });
        $('#accepttype option:contains(网络咨询)').each(function() {
            if ($(this).text() == '网络咨询') {
                $(this).attr('selected', true);
            }
        });

        /** 手工赋值 **/
        if (baseInfo.ordertime) {
            var time2 = (baseInfo.ordertime).split("~")[0]
            var ordertime = baseInfo.orderdate + " " + time2;
            $("#ordertime").val(ordertime);
        }
    }
}
$('#submit').on('click',
function() {
    var flag = submit(); //submit()校验方法
    if (!flag) {
        return false;
    }
    if ($("#ordertime").val() != "") {
        var ordertimeObj = new Date($("#ordertime").val() + ':00'); // 2017-05-04 17:10
        if (intoPageDateTime.getTime() >= ordertimeObj.getTime()) {
            layer.alert('预约日期不得早于操作时间'  );
            return false;
        }
    }
    var param = $('#defaultForm').serialize();
    param = param + '&wxOrderSeqId=' + wxOrderSeqId + '&usercode=' + usercode;
    var url = contextPath + '/WXUserDocAct/insertNetOrder.act?' + param;
    $.axse(url, null,
    function(r) {
        if (r.retState == "0") {
            layer.alert('操作成功', {
                  
                end: function() {
                    parent.refresh();
                    var frameindex = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(frameindex); //再执行关闭
                }
            });
            return true;
        } else {
        	if(r.retMsrg){
        		layer.alert(r.retMsrg, {
                      
                });
        	}else{
        		layer.alert('操作失败', {
                      
                });
        	}
            return false;
        }
    },
    function() {
        layer.alert('操作成功' );
        return false;
    });
});
$('#close').on('click',
function() {
    parent.layer.close(frameindex); //执行关闭
});
</script>
<style type="text/css">
/* 这里写样式是因为出生年月，后面增加了日期图标，如果不写下面的样式，出生年月会占两行 */
button{
    font: normal;
    line-height:normal;
}
</style>
</html>
