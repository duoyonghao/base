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
    <title>字符云</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiedai/cloudsTags.css" />
    
    <script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/echarts-all.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
	<script src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>

</head>
<body>
<div id="container">
    <div class="infoHd">患者标签</div>
    <div class="infoBd">
    <input type="hidden" id="seqId" name="seqId">
       <div id="chart" style="height: 400px;width: 100%;"></div>
    </div>
    <div class="buttonBar">
        <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="addbq()">新增</a>
    </div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<%=contextPath%>";
var onclickrowOobj = "";
var names, values;
$(function() {
    onclickrowOobj = window.parent.onclickrowOobj;
    getdata();
    var myChart = echarts.init(document.getElementById('chart'));
    option = {
        tooltip: {
            show: false
        },
        series: [{
            name: '',
            type: 'wordCloud',
            size: ['80%', '80%'],
            textRotation: [0, 45, 90, -45],
            textPadding: 0,
            autoSize: {
                enable: true,
                minSize: 14
            },
            data: function() {
                var res = [];
                if (names != null && names != "") {
                    for (var i = 0; i < names.length; i++) {
                        var name = getValueDictTB(names[i]);
                        res.push({
                            name: name,
                            value: createRandomValue(),
                            itemStyle: createRandomItemStyle()
                        });
                    }
                }
                return res;
            } ()
        }]
    };
    myChart.setOption(option);

    function createRandomItemStyle() {
        return {
            normal: {
                color: 'rgb(' + [Math.round(Math.random() * 160), Math.round(Math.random() * 160), Math.round(Math.random() * 160)].join(',') + ')'
            }
        };
    }
});
function createRandomValue() {
    return Math.round(Math.random() * 10000);

}
function getdata() {
    var detailurl = getOneByUsercode(onclickrowOobj.usercode);
    var retData = detailurl.data[0];
    $("#seqId").val(retData.seqId);
    if (retData.bq != null && retData.bq != "") {
        names = retData.bq.split(",");
    }
}
function addbq() {
    if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
        layer.alert('请选择患者' );
    } else {
        var seqId = $("#seqId").val();

        layer.open({
            type: 2,
            title: '添加标签',
            shadeClose: true,
            shade: 0.6,
            area: ['400px', '200px'],
            content: '<%=contextPath%>/KQDS_UserDocumentAct/toCloudsTagsAdd.act?seqId=' + seqId
        });
    }
}
</script>
</html>
