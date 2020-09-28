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
<title>挂号统计</title>
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
    <div class="tableHd">上门量统计</div>
    <div class="tableBox">
        <div id="containerhh" ></div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var nowday;
$(function() {
	$("input[type='text']").attr("autocomplete","off");  //去掉input输入框的历史记录  lutian
	initHosSelectList4Front('organization');// 连锁门诊下拉框	
    //时间选择
    $(".unitsDate input").datetimepicker({
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
    var jsonXDate = [];
    var jsonD1 = [];
    $.ajax({
        url: contextPath + "/KQDS_REGAct/selectCountBB.act?starttime=" + starttime + "&endtime=" + endtime+'&organization='+$("#organization").val(),
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
                    jsonXDate.push(screens[i].recesortDesc);
                    jsonD1.push(parseInt(screens[i].nums));
                    numsall += parseInt(screens[i].nums);
                }
                var charts = new Highcharts.Chart({
                    chart: {
                        type: 'column',
                        renderTo: "containerhh",
                        // 注意这里一定是 ID 选择器
                        options3d: {
                            enabled: true,
                            // alpha: 15,//倾斜
                            //  beta: 15,//倾斜
                            viewDistance: 25,
                            depth: 40
                        },
                        marginTop: 80,
                        marginRight: 40
                    },
                    credits: {
                        enabled: false // 禁用版权信息
                    },
                    title: {
                        text: '挂号分类统计（总挂号数：' + numsall + '人）'
                    },
                    xAxis: {
                        //categories:  ['Apples', 'Oranges', 'Pears', 'Grapes', 'Bananas'],
                        categories: jsonXDate,
                        lineWidth: 2,
                        labels: {
                            rotation: 0,
                            //字体倾斜
                            align: 'right',
                            style: {
                                font: 'normal 13px 宋体'
                            }

                        }

                    },
                    yAxis: {
                        lineWidth: 2,
                        title: {
                            text: '上门数'
                        }
                    },
                    tooltip: {
                        formatter: function() {
                            return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + Highcharts.numberFormat(this.y, 0);
                        }
                    },
                    plotOptions: {
                        column: {
                            dataLabels: {
                                enabled: true,
                                format: '{y}'
                            },
                        }
                    },
                    series: [{
                        name: '上门数',
                        //data: [80, 60, 70, 90]
                        //data: [jsonD1[0], jsonD1[1], jsonD1[2], jsonD1[3]]
                        data: jsonD1
                    }]
                });
            }
        }

    });
}
</script>
</html>
