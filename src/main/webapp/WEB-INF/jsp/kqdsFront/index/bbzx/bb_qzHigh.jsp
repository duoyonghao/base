<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>消费分类统计</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/record.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search2.css" />


<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-3d.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/exporting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/highcharts/highcharts-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
</head>
<body>
<div id="container">
   <!--查询条件-->
     <div class="searchWrap">
            <div class="cornerBox">查询条件</div>
            <div class="formBox">
                </select>
               	<div class="kv">
               		<div class="kv">
						<label>所属门诊</label>
						<div class="kv-v">
							<select id="organization" name="organization"></select>
						</div>
					</div>
               	
               		<div class="kv">
						<label>挂号日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
                </div>
                 <div class="kv" style="width:150px; margin:0 auto;text-align:center; ">
				        <a href="javascript:void(0);" class="kqdsSearchBtn" id="query">查询</a>
			    </div>
            </div>
        </div> 
    <div class="tableHd">消费业绩</div>
    <div class="tableBox">
        <div id="containerhh" ></div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var nowday;
$(function() {
	initHosSelectList4Front('organization');// 连锁门诊下拉框	
    //时间选择
    $("#starttime").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });
    $("#endtime").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "bottom-right"
    });
    nowday = getNowFormatDate();
    $("#starttime").val(nowday);
    $("#endtime").val(nowday);
    //绑定两个时间选择框的chage时间
    $("#starttime,#endtime").change(function() {
        timeCompartAndFz("starttime", "endtime");
    });
    getdata();
});
$("#query").click(function() {
	$(this).attr("disabled","disabled").css("background-color","#c3c3c3").css("border","1px solid #c3c3c3").css("pointer-events","none"); //禁用查询按钮 lutian
	$(this).text("查询中");
    getdata();
});
function getdata() {
    var starttime = $("#starttime").val();
    var endtime = $("#endtime").val();
    var jsonData = [];
    $.ajax({
        url: contextPath + "/KQDS_CostOrder_DetailAct/selectCountBB.act?starttime=" + starttime + "&endtime=" + endtime+'&organization='+$("#organization").val(),
        context: document.body,
        success: function(data) {
			//解除查询按钮禁用 lutian
			if(data){
				$("#query").removeAttr("disabled").css("background-color","#00a6c0").css("border","1px solid #00a6c0").css("cursor","pointer").css("pointer-events","auto");
				$("#query").text("查询");
			}

            var screens = data.rows;
            var numsall = 0;
            if (screens.length > 0) {
                for (var i = 0; i < screens.length; i++) {
                    var jsonFb = [];
                    jsonFb.push(screens[i].xflx + "", parseFloat(screens[i].ys));
                    jsonData.push(jsonFb);
                    numsall += Number(screens[i].ys);
                }
                $('#containerhh').highcharts({
                    chart: {
                        type: 'pie',
                        options3d: {
                            enabled: true,
                            alpha: 45,
                            beta: 0
                        }
                    },
                    title: {
                        text: '消费分类统计（总业绩：' + Number(numsall).toFixed(2) + '）'
                    },
                    credits: {
                        enabled: false // 禁用版权信息
                    },
                    tooltip: {
                        formatter: function() {
                            return '<b>' + this.point.name + '</b>: ' + Highcharts.numberFormat(this.percentage, 1) + '% (' + Highcharts.numberFormat(this.y, 0, ',') + ' 元)';
                        }
                    },
                    plotOptions: {
                        pie: {
                            allowPointSelect: true,
                            cursor: 'pointer',
                            depth: 50,
                            dataLabels: {
                                style: {
                                    fontSize: '24px'
                                },
                                format: '{point.name}（{y}）'
                            },
                            showInLegend: true
                        }
                    },
                    series: [{
                        type: 'pie',
                        name: '应收占比',
                        data: jsonData
                    }]
                });
            }
        }

    });
}
</script>
</html>
