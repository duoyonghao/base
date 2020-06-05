<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	
	String seqId = request.getParameter("seqId");
	if(YZUtility.isNullorEmpty(seqId)){
		seqId = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>修改缴费方式</title><!-- 客户管理菜单  点击 添加推广 按钮 进入 -->
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
							<td style="width:10%;text-align:right;"><%=SysParaUtil.getFkfsNameByMemberField(request, "xjmoney") %>：</td>
			    			<td style="width:20%;text-align:left;"> 
			    				<input type="text" name="xjmoney"  id="xjmoney" />
			                </td>
			    		</tr>
			    		<tr>
							<td style="width:10%;text-align:right;"><%=SysParaUtil.getFkfsNameByMemberField(request, "zfbmoney") %>：</td>
			    			<td style="width:20%;text-align:left;"> 
			    				<input type="text" name="zfbmoney"  id="zfbmoney" />
			                </td>
			    		</tr>
			    		<tr>
							<td style="width:10%;text-align:right;"><%=SysParaUtil.getFkfsNameByMemberField(request, "wxmoney") %>：</td>
			    			<td style="width:20%;text-align:left;"> 
			    				<input type="text" name="wxmoney"  id="wxmoney" />
			                </td>
			    		</tr>
			    		<tr>
							<td style="width:10%;text-align:right;"><%=SysParaUtil.getFkfsNameByMemberField(request, "yhkmoney") %>：</td>
			    			<td style="width:20%;text-align:left;"> 
			    				<input type="text" name="yhkmoney"  id="yhkmoney" />
			                </td>
			    		</tr>
			    		<tr>
							<td style="width:10%;text-align:right;"><%=SysParaUtil.getFkfsNameByMemberField(request, "mmdmoney") %>：</td>
			    			<td style="width:20%;text-align:left;"> 
			    				<input type="text" name="mmdmoney"  id="mmdmoney" />
			                </td>
			    		</tr>
			    		<tr>
							<td style="width:10%;text-align:right;"><%=SysParaUtil.getFkfsNameByMemberField(request, "bdfqmoney") %>：</td>
			    			<td style="width:20%;text-align:left;"> 
			    				<input type="text" name="bdfqmoney"  id="bdfqmoney" />
			                </td>
			    		</tr>
			    		<tr>
							<td style="width:10%;text-align:right;"><%=SysParaUtil.getFkfsNameByMemberField(request, "qtmoney") %>：</td>
			    			<td style="width:20%;text-align:left;"> 
			    				<input type="text" name="qtmoney"  id="qtmoney" />
			                </td>
			    		</tr>
			    		
			   </table>
			        <div class="kv-v" style="text-align:center;width:100%">
			            </br>
			            <a href="javascript:void(0);" id="tijiao" onclick="submitu()" class="kqdsSearchBtn">确定修改</a>
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
var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
var seqId = "<%=seqId %>";
var totalMoney = null;
$(function() {

    var detailurl = '<%=contextPath%>/KQDS_Member_RecordAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
    function(data) {
        loadData(data.data);
        var detail = data.data;
        totalMoney = Number(detail.xjmoney) + Number(detail.yhkmoney)+ Number(detail.qtmoney)
        	+ Number(detail.zfbmoney)+ Number(detail.wxmoney)+ Number(detail.mmdmoney)+ Number(detail.bdfqmoney); 
    },
    function() {
        layer.alert('查询出错！' );
    });
});

//提交
function submitu() {
    //验证
    var xjmoney = $("#xjmoney").val();
    var yhkmoney = $("#yhkmoney").val();
    var qtmoney = $("#qtmoney").val();
    var zfbmoney = $("#zfbmoney").val();
    var wxmoney = $("#wxmoney").val();
    var mmdmoney = $("#mmdmoney").val();
    var bdfqmoney = $("#bdfqmoney").val();

    if (xjmoney == '') {
        xjmoney = '0';
    }
    if (yhkmoney == '') {
        yhkmoney = '0';
    }
    if (qtmoney == '') {
        qtmoney = '0';
    }
    if (zfbmoney == '') {
        zfbmoney = '0';
    }
    if (wxmoney == '') {
        wxmoney = '0';
    }
    if (mmdmoney == '') {
        mmdmoney = '0';
    }
    if (bdfqmoney == '') {
        bdfqmoney = '0';
    }

    /** 输入数据格式校验 **/
    // * 0次或者多次   \d    [0-9]  **/
    // + 1次或者多次 **/
    // ? 0次或者1次 **/
    var testRegx = /^((\-?\d+(\.\d+)?)|(0+(\.0+)?))$/; // 正负数
    if (!testRegx.test(xjmoney)) {
        layer.alert('现金格式不正确' );
        return false;
    }
    if (!testRegx.test(zfbmoney)) {
        layer.alert('支付宝格式不正确' );
        return false;
    }
    if (!testRegx.test(wxmoney)) {
        layer.alert('微信格式不正确' );
        return false;
    }
    if (!testRegx.test(yhkmoney)) {
        layer.alert('银行卡格式不正确' );
        return false;
    }
    if (!testRegx.test(mmdmoney)) {
        layer.alert('么么贷格式不正确' );
        return false;
    }
    if (!testRegx.test(bdfqmoney)) {
        layer.alert('百度分期格式不正确' );
        return false;
    }
    if (!testRegx.test(qtmoney)) {
        layer.alert('其他格式不正确' );
        return false;
    }

    var xiaoji = Number(xjmoney) + Number(yhkmoney) + Number(qtmoney) + Number(zfbmoney) + Number(wxmoney) + Number(mmdmoney) + Number(bdfqmoney);
    if (xiaoji != Number(totalMoney)) {
        layer.alert("您填写的金额与总金额不一致,请核实!原总金额：" + totalMoney + ",现总金额：" + xiaoji );
        return false;
    }

    layer.confirm('确定修改吗？', {
        btn: ['确定', '取消'] //按钮
    },
    function() {
        var a = {
            xjmoney: xjmoney,
            yhkmoney: yhkmoney,
            qtmoney: qtmoney,
            zfbmoney: zfbmoney,
            wxmoney: wxmoney,
            mmdmoney: mmdmoney,
            bdfqmoney: bdfqmoney,
            seqId: seqId
        };
        var url = '<%=contextPath%>/KQDS_Member_RecordAct/methodModify.act';
        $.axseSubmit(url, a,
        function() {
            layer.msg('加载中', {
                icon: 16
            });
        },
        function(r) {
            if (r.retState == "0") {
                layer.alert('修改成功', {
                      
                    end: function() {
                    	if(parent.$('#query')){
                    		 parent.$('#query').trigger("click"); // 刷新父页面列表
                    	}
                        parent.layer.close(frameindex); //关闭当前弹层
                    }
                });
            } else {
                layer.alert('修改失败', {
                      
                });
            }
        },
        function() {
            layer.alert('修改失败'  );
        });
    });
}
</script>

</html>
