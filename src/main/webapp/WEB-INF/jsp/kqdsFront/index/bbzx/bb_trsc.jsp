<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	String seqIds = request.getParameter("seqIds");
	if(seqIds == null){
		seqIds = "";
	}
	String table = request.getParameter("table");
	if(table == null){
		table = "";
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
						<label>日期</label>
						<div class="kv-v">
							<span class="unitsDate">
								<input type="text" placeholder="开始日期" id="starttime" /> <span>到</span>
								<input type="text" placeholder="结束日期" id="endtime" />
							</span>
						</div>
					</div>
                </div>
                 <div class="kv" style="width:150px; margin:0 auto;text-align:center; ">
				        <a href="javascript:void(0);" class="aBtn search" id="query">查询</a>
			    </div>
            </div>
        </div> 
    <div class="tableHd">活动投入产出情况</div>
    <div class="tableBox" style="width: 98%;">
        <div id="containerhh" ></div>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var seqIds = "<%=seqIds%>";
var table = "<%=table%>";
var pageurl = '';
if(table == "KQDS_ACTIVITY_RECORD"){
	pageurl = '<%=contextPath%>/KQDS_Activity_RecordAct/selectTrscColumn.act';
}else if(table == "KQDS_DEINDUSTRYRECORD"){
	pageurl = '<%=contextPath%>/Kqds_DeindustryRecordAct/selectTrscColumn.act';
}else if(table == "KQDS_MEDIA_RECORD"){
	pageurl = '<%=contextPath%>/Kqds_MediaRecordAct/selectTrscColumn.act';
}
var nowday;
$(function() {
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
    getdata();
});
function getdata() {
    var starttime = $("#starttime").val();
    var endtime = $("#endtime").val();
    var jsonXDate = [];//活动
    var jsonD1 = [];//投入
    var jsonD2 = [];//产出
    $.ajax({
        url: pageurl + "?starttime=" + starttime + "&endtime=" + endtime+'&seqIds='+seqIds,
        context: document.body,
        success: function(data) {
            var screens = data.rows;
            var numsall = 0;
            if (screens.length > 0) {
            	for (var i = 0; i < screens.length; i++) {
                    jsonXDate.push(screens[i].jlname);
                    jsonD1.push(parseInt(screens[i].outmoney));
                    jsonD2.push(parseInt(screens[i].skje));
                }
                var charts = new Highcharts.Chart({
                    chart: {
                        type: 'column',
                        renderTo: "containerhh"
                        //marginTop: 50,
                        //marginRight: 10
                    },
                    credits: {
                        enabled: false // 禁用版权信息
                    },
                    title: {
                        text: '活动投入产出'
                    },
                    xAxis: {
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
                            text: '金额（元）'
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
                        name: '投入',
                        //data: [80, 60, 70, 90]
                        //data: [jsonD1[0], jsonD1[1], jsonD1[2], jsonD1[3]]
                        data: jsonD1
                    },{
	                    name: '产出',
	                    //data: [80, 60, 70, 90]
	                    //data: [jsonD1[0], jsonD1[1], jsonD1[2], jsonD1[3]]
	                    data: jsonD2
	                }]
                });
            }
        }
    });
}
</script>
</html>