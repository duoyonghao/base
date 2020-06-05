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
<meta charset="utf-8">
<title>人物关系图</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiedai/cloudsTags.css" />
</head>
<body>
<div id = "relationChart" ></div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/echarts-all.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var onclickrowOobj = "";
onclickrowOobj = window.parent.onclickrowOobj;
var nodesArr = [];
var linksArr = [];
var myChart;
$(function() {
    //计算chart高度
    setChartHeight();
    if (onclickrowOobj != null && onclickrowOobj != "" && onclickrowOobj != "null" && onclickrowOobj != "undefined") {
        var usercode = onclickrowOobj.usercode;
        var username = onclickrowOobj.username;
        //设置患者
        var user = {
            category: 0,
            name: username,
            value: 10,
            label: username,
            draggable: true,
            itemStyle: {
                normal: {
                    label: {
                        textStyle: {
                            color: '#333'
                        }
                    }
                }
            }
        };
        nodesArr.push(user);
        //设置 患者  医生  护士 技工
        var detailurl = '<%=contextPath%>/Kqds_PayCostAct/selectDetailByUsercode.act?usercode=' + usercode;
        $.axse(detailurl, null,
        function(data) {
            var list = data.data;
            if (list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    //医生
                    if (list[i].doctor != "") {
                        var doctorname = getPerUserNameBySeqIdTB(list[i].doctor);
                        var doctor = {
                            category: 1,
                            name: doctorname,
                            value: list[i].doctor
                        };
                        var doctorlink = {
                            source: doctorname,
                            target: username,
                            weight: 1,
                            name: '医生',
                            itemStyle: {
                                normal: {
                                    text: '医生',
                                    textColor: '#666',
                                    textFont: '12px verdana',
                                    textPosition: 'inside'
                                }
                            }
                        };
                        nodesArr.push(doctor);
                        linksArr.push(doctorlink);
                    }

                    //护士
                    if (list[i].nurse != "") {
                        var nursename = getPerUserNameBySeqIdTB(list[i].nurse);
                        var nurse = {
                            category: 1,
                            name: nursename,
                            value: list[i].nurse
                        };
                        var nurselink = {
                            source: nursename,
                            target: username,
                            weight: 2,
                            name: '护士',
                            itemStyle: {
                                normal: {
                                    text: '护士',
                                    textColor: '#666',
                                    textFont: '12px verdana',
                                    textPosition: 'inside'
                                }
                            }
                        };
                        nodesArr.push(nurse);
                        linksArr.push(nurselink);
                    }
                    //技工
                    if (list[i].techperson != "") {
                        var techpersonname = getPerUserNameBySeqIdTB(list[i].techperson);
                        var techperson = {
                            category: 1,
                            name: techpersonname,
                            value: list[i].techperson
                        };
                        var techpersonlink = {
                            source: techpersonname,
                            target: username,
                            weight: 1,
                            name: '技工',
                            itemStyle: {
                                normal: {
                                    text: '技工',
                                    textColor: '#666',
                                    textFont: '12px verdana',
                                    textPosition: 'inside'
                                }
                            }
                        };
                        nodesArr.push(techperson);
                        linksArr.push(techpersonlink);
                    }
                    //咨询
                    if (list[i].askperson != "") {
                        var askpersonname = getPerUserNameBySeqIdTB(list[i].askperson);
                        var askperson = {
                            category: 1,
                            name: askpersonname,
                            value: list[i].askperson
                        };
                        var askpersonlink = {
                            source: askpersonname,
                            target: username,
                            weight: 3,
                            name: '咨询',
                            itemStyle: {
                                normal: {
                                    text: '咨询',
                                    textColor: '#666',
                                    textFont: '12px verdana',
                                    textPosition: 'inside'
                                }
                            }
                        };
                        nodesArr.push(askperson);
                        linksArr.push(askpersonlink);
                    }
                }
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
        var qyurl = '<%=contextPath%>/KQDS_ParticipantAct/selectNoPage.act?usercode=' + usercode;
        $.axse(qyurl, null,
        function(data) {
            var list = data;
            if (list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    //医生
                    var per = {
                        category: 1,
                        name: list[i].participant,
                        value: 1
                    };

                    if (list[i].relation == "") { // 容错处理,这个关系必须要有值
                        continue;
                    }

                    var real = getValueDictTB(list[i].relation);
                    var perlink = {
                        source: list[i].participant,
                        target: username,
                        weight: 3,
                        name: real,
                        itemStyle: {
                            normal: {
                                text: real,
                                textColor: '#666',
                                textFont: '12px verdana',
                                textPosition: 'inside'
                            }
                        }
                    };
                    nodesArr.push(per);
                    linksArr.push(perlink);

                }
            }
        },
        function() {
            alert(2);
            layer.alert('查询出错！'  );
        });

        myChart = echarts.init(document.getElementById('relationChart'));
        option = {
            tooltip: {
                trigger: 'item',
                formatter: '{a} : {b}'
            },
            series: [{
                type: 'force',
                name: "人物",
                ribbonType: false,
                categories: [
                /*    {
			                        name: '人物'   ,
			                        symbol: 'circle',
			                        itemStyle:{
			                            normal:{
			                                color:'#c3c3c3'
			                            }
			                        }
			                    },
			                    {
			                        name: '家人',
			                        symbol: 'rectangle',
			                        itemStyle:{
			                            normal:{
			                                color:'#c3c3c3'
			                            }
			                        }
			                    }, */
                {
                    name: '朋友',
                    symbol: 'rectangle',
                    itemStyle: {
                        color: '#c3c3c3'
                    }
                }],
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            textStyle: {
                                color: '#333'
                            }
                        },
                        nodeStyle: {
                            brushType: 'both',
                            borderColor: '#c3c3c3',
                            borderWidth: 1
                        },
                        linkStyle: {
                            type: 'line',
                            color: '#c3c3c3'
                        }
                    },
                    emphasis: {
                        label: {
                            show: false
                            // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
                        },
                        nodeStyle: {
                            //r: 30
                        },
                        linkStyle: {}
                    }
                },
                useWorker: false,
                minRadius: 25,
                maxRadius: 35,
                gravity: 1,
                scaling: 1.1,
                roam: 'move',
                nodes: nodesArr,
                links: linksArr
            }]
        };
        myChart.setOption(option);
        function focus(param) {
            var data = param.data;
            var links = option.series[0].links;
            var nodes = option.series[0].nodes;
            if (data.source !== undefined && data.target !== undefined) { //点击的是边
                var sourceNode = nodes.filter(function(n) {
                    return n.name == data.source
                })[0];
                var targetNode = nodes.filter(function(n) {
                    return n.name == data.target
                })[0];
            }
        }
        myChart.on(echarts.config.EVENT.CLICK, focus)

        myChart.on(echarts.config.EVENT.FORCE_LAYOUT_END,
        function() {});
    }

    $(window).resize(function() {
        setChartHeight();
        if (myChart) {
            myChart.resize();
        }

    });

    //计算chart高度
    function setChartHeight() {
        var baseH = $(window).height() - 20;
        $('#relationChart').height(baseH)
    }
});
</script>
</html>