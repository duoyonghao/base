<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>加工记录</title><!-- 首页-右侧个人中心区域-加工记录 图标进入 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiagong/search.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/lzjl/history.css">
</head>
<style type="text/css">

#container{
	padding:0 15px;
}
.fixed-table-container{
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
	border-bottom:1px solid #ddd;
	border-radius: 6px;
	/* border-top-left-radius: 6px;
	border-top-right-radius: 6px; */
	overflow: hidden;
	height: 200px !important;
}
.toothBtn{
	display: inline-block;
    box-sizing: border-box;
    height: 20px;
    line-height: 18px;
    font-size: 12px;
    background: #00a6c0;
    color: #fff;
    outline: none;
    padding: 0 10px;
    border: 1px solid #00a6c0;
    border-radius: 3px;
    margin-right: 3px;
    text-decoration: none;
    cursor: pointer;
    text-align: center;
}
#arrow ul li{
	background: url(.././static/image/kqdsFront/lzjl/icons.png) no-repeat 0 0;
}
#arrow ul .arrowup {
    background-position: 0px -26px;
    margin-bottom: 10px;
}
#arrow ul .arrowdown {
    background-position: 0px 0px;
}
#circle{
	box-sizing: content-box;
}
.year,.yearmd,.md{
	font-family: "arial","微软雅黑";
}
#history {
	margin: 25px auto 0 auto;
}
.thiscur .point b {
	box-sizing: content-box;
}
.point b{
	box-sizing: content-box;
}
</style>
<body>
<div id="container">
    <div class="main" style="padding-top:15px;">
        <div class="listWrap" >            
            <div class="listBd">
                <div class="tableBox">
                    <table id="table" class="table-striped table-condensed table-bordered" data-height="450"></table>
                </div>
                <div class="buttonArea" id="buttonArea" style="margin-left: 65%;">
                
                </div>
                <div id="history">
					<div class="title">
						<!-- <h2>流转记录</h2> -->
						<div id="circle">
							<div class="cmsk"></div>
							<div class="circlecontent">
								<div thisyear="2018" class="timeblock">
									<span class="numf"></span>
									<span class="nums"></span>
									<span class="numt"></span>
									<span class="numfo"></span>
									<div class="clear"></div>
								</div>
								<div class="timeyear">YEAR</div>
							</div>
							<a href="#" class="clock"></a>
						</div>
					</div>
					
					<div id="content" style="height: 250px!important;">
						<ul class="list" id="contentul" style="height: 250px!important;">
						</ul>
					</div>
					<div id="arrow">
						<ul>
							<li class="arrowup"></li>
							<li class="arrowdown"></li>
						</ul>
					</div>
				</div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/jgzx/jg/jg.js"></script>
</body>
<script type="text/javascript">
var listbutton = parent.listbutton;

var contextPath = '<%=contextPath%>';
var onclickrowobj = window.parent.onclickrowOobj;  //父页面传值;
var perseqId = "<%=person.getSeqId()%>";
var personrole = "<%=person.getUserPriv()%>";
var personroleother = "<%=person.getUserPrivOther()%>";
var pageurl = '<%=contextPath%>/KQDS_MACHININGAct/getProcessingRecords.act';
var onclickrow = ""; //选中行对象
var onclickrowOobj = ""; //选中行对象 lutian
var mryear = "";
$(function() {
	var baseHeight = $(window).height();  
	$("#container").outerHeight(baseHeight); //设置内容的高度
	$("#history").outerHeight(baseHeight-240); //设置流程的高度
	$(window).resize(function(){
		baseHeight = $(window).height();  
		$("#container").outerHeight(baseHeight); //设置内容的高度
		$("#history").outerHeight(baseHeight-240); //设置流程的高度
	});
    var pageurlusercode = pageurl;
    pageurlusercode = pageurlusercode + "?usercode=" + onclickrowobj.usercode;
    var pageurlsno = pageurl;
    //加载表格
    inittable(pageurlsno);
    //获取按钮
    getButtonPower();
    //导出
    $('#export').on('click',
    function() {
        var li = $(".dropdown-menu").children("li").last();
        li.trigger("click");
    });

    //计算主体的宽度
    setHeight();
    $(window).resize(function() {
        setHeight();
        /*表格载入时，设置表头的宽度 */
        setTableHeaderWidth(".tableBox");
    });
});

//流转记录初始化
function getlzjl(onclickrowOobj) {
	//console.log(JSON.stringify(onclickrowOobj)+"----------onclickrowOobj");
	//console.log(onclickrowOobj.seqId+"----------onclickrowOobj.seqId");
    var pageurl1 = '<%=contextPath%>/KQDS_MACHININGAct/getFlow.act';
    var url = pageurl1 + '?worksheetId='+onclickrowOobj.seqId;
    //var url = pageurl1 + '?worksheetId=d7447f68-2327-4951-91d5-8bf48150d5fe';
    $.axse(url, null,
    function(data) {
    	//console.log(JSON.stringify(data)+"--------流转记录数据初始化");
        var content = "";
        for (var i = 0; i < data.length; i++) {
        	if(i==0){
        		mryear = data[i].createtime.substring(0,4);
        	}
       		content += ' <li>'+
       		     '<div class="liwrap">'+
       			 '<div class="lileft">'+
       			 '<div class="date">'+
       			 '<span class="year">' + data[i].createtime.substring(0,4) + '</span>'+
       			 '<span class="yearmd">' + data[i].createtime.substring(4,10) + '</span>'+
       			 '</div>'+
       			 '<div class="">'+
       			 '<span class="md">' + data[i].createtime.substring(10) + '</span>'+
       			 '</div>'+
       			 '</div>'+
				
       			 '<div class="point"><b></b></div>'+
				
       			 '<div class="liright">'+
       			 '<div class="histt">'+
       			 '<span style="font-size:14px;">'+ data[i].status + '</span><br>'+
       			 '</div>'+
       			 '<div class="hisct">'+
       			 '<span>操作人：<span style="font-size:14px;">' + data[i].createuser + '</span></span>' +
       			 '</div>'+
       			 '</div>'+
       			 '</div>'+
       			 '</li>';
        }
        $("#contentul").html("").append(content);
    },
    function() {
        layer.alert('查询出错！' );
    });
}

function inittable(pageurl) {
	var url=contextPath + "/KQDS_MACHININGAct/selectMachineByUsercode.act?usercode="+onclickrowobj.usercode;
    //加载表格
    $("#table").bootstrapTable({
        url: url,
        dataType: "json",
        singleSelect: false,
        cache: false,
        striped: true,
        onLoadSuccess: function(data) { //加载成功时执行
        	//console.log(JSON.stringify(data)+"----------data");
        	getlzjl(data[0]);
        	$(".timeblock .numf").css("background-position","0px "+(0-Number(mryear.substring(0,1))*24)+"px");
            $(".timeblock .nums").css("background-position","0px "+(0-Number(mryear.substring(1,2))*24)+"px");
            $(".timeblock .numt").css("background-position","0px "+(0-Number(mryear.substring(2,3))*24)+"px");
            $(".timeblock .numfo").css("background-position","0px "+(0-Number(mryear.substring(3,4))*24)+"px");    
        	if(JSON.stringify(data)=="[]"){
        		$("#history").css("display","none");
        	}
            var tableList = $('#table').bootstrapTable('getData');
            $("#total").html(tableList.length);
          	//计算主体的宽度
            setHeight();
            /*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        },
        columns: [
		{
		    title : '序号',
		    align: "center",
		    width: 40,
		    formatter: function (value, row, index) {
		     /* return index + 1; */
		     var pageSize = $('#table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
		        var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
		        return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
		    }
		   },
		{
            title: 'seqId',
            field: 'seqId',
            align: 'center',
            visible:false,
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },{
            title: '系统单号',
            field: 'systemnumber',
            visible:false,
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
                return "<span>" + value + "</span>";
            }
        },
        {
            title: '订单编号',
            field: 'ordernumber',
            visible:false,
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '状态',
            field: 'status',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	if (value == 0) {
                    t = '未发件';
                } else if (value == 1) {
                    t = '已发件';
                } else if (value == 2) {
                    t = '已回件';
                } else if (value == 3) {
                    t = '戴走';
                } else if (value == 4) {
                    t = '返工';
                } else if (value == 5) {
                    t = '作废';
                } else if (value == 6) {
                    t = '试戴中';
                }else if (value == 7) {
                    t = '已带走';
                }
            	return "<span>" + t + "</span>";
            }
        },
        {
            title: '加工单位',
            field: 'processunit',
            align: 'center',
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '医生',
            field: 'doctor',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '性别',
            field: 'sex',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '年龄',
            field: 'age',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '电话',
            field: 'phonenumber',
            visible:false,
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '收模人',
            field: 'chargemodelperson',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '取模日期',
            field: 'deliverytime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '收模日期',
            field: 'chargemodeltime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        },
        {
            title: '出货时间',
            field: 'sendouttime',
            align: 'center',
            sortable: true,
            formatter: function(value, row, index) {
            	return "<span>" + value + "</span>";
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#table').bootstrapTable('getData')[index];  
        getlzjl(onclickrow); //初始化义齿加工单流转记录
        $(".timeblock .numf").css("background-position","0px "+(0-Number(mryear.substring(0,1))*24)+"px");
        $(".timeblock .nums").css("background-position","0px "+(0-Number(mryear.substring(1,2))*24)+"px");
        $(".timeblock .numt").css("background-position","0px "+(0-Number(mryear.substring(2,3))*24)+"px");
        $(".timeblock .numfo").css("background-position","0px "+(0-Number(mryear.substring(3,4))*24)+"px"); 
    }).on('dbl-click-cell.bs.table', function (e, row, element) {
    	var index = $('#table').find('tr.success').data('index'); //获得选中的行
    	onclickrowOobj = $('#table').bootstrapTable('getData')[index];
    	 if (onclickrowOobj == "" || onclickrowOobj == null) {
             layer.alert('请选择患者！', {
                   
             });
             return false;
         }
         layer.open({
             type: 2,
             title: '加工单详细信息',
             shadeClose: false,
             shade: 0.6,
             area: ['90%', '90%'],
             content: contextPath + '/KQDS_OutProcessingSheetAct/toYcjgShow.act?id=' + onclickrowOobj.seqId
         });
    });
}

//刷新整个页面
function refresh() {
    window.location.reload();
}

//状态改变
function submitsheet(status) {
	//onclickrow = $('#table').bootstrapTable('getData')[index];
	//console.log(JSON.stringify(onclickrow));
    var paramstr = "?status=" + status;
    var url = contextPath + '/KQDS_MACHININGAct/updateStatus.act' + paramstr;
    onclickrow = JSON.stringify(onclickrow);
    var param = {onclickrow : onclickrow};
    $.axse(url, param,
    function(data) {
        if (data.retState == "0") {
            layer.alert('操作成功', {
                  
                end: function() {
                    onclickrow = "";
                    refresh();
                }
            });
        }
    },
    function() {
        layer.alert('操作失败！' );
    });
}

//试戴
function tryIn(state) {
	if (onclickrow == null || onclickrow == "") {
        layer.alert('请选择需要试戴的加工单' );
        return false;
    } else if (onclickrow.status < 2 || onclickrow.status > 2) {
        layer.alert('您的操作有误，当前加工单状态为: ' + getStatusC(onclickrow.status) + '' );
        return false;
    } else {
        layer.confirm('确定试戴吗？', {
            btn: ['确定', '放弃'] //按钮
        },
        function() {
            submitsheet(state);
        });
    }
}
//返工
function rework(state) {
    	//console.log("当前状态=" + onclickrow.status);
    	if (onclickrow == null || onclickrow == "") {
            layer.alert('请选择需要返工的加工单' );
            return false;
        } else if (onclickrow.status < 6 || onclickrow.status > 6) {
        layer.alert('您的操作有误，当前加工单状态为: ' + getStatusC(onclickrow.status) + '' );
        return false;
    } else {
        layer.confirm('确定返工吗？', {
            btn: ['确定', '放弃'] //按钮
        },
        function() {
            submitsheet(state);
        });
    }
}

//戴走
function daizou(seqId, status) {
	if (onclickrow == null || onclickrow == "") {
        layer.alert('请选择需要戴走的加工单' );
        return false;
    } else if (status < 2 || status > 2) {
        layer.alert('您的操作有误，当前加工单状态为: ' + getStatusC(onclickrow.status) + '' );
        return false;
    } else {
        layer.confirm('确定戴走吗？', {
            btn: ['确定', '放弃'] //按钮
        },
        function() {
            submitsheet(seqId, 3);
        });
    }
}

//删除
function del(seqId, status) {
	if (onclickrow == null || onclickrow == "") {
        layer.alert('请选择需要删除的加工单' );
        return false;
    } else if (status < 1 || status > 1) {
        layer.alert('您的操作有误，当前加工单状态为: ' + getStatusC(onclickrow.status) + '' );
        return false;
    } else {
        layer.confirm('确定删除吗？', {
            btn: ['确定', '放弃'] //按钮
        },
        function() {
            shanchu(onclickrow);
        });
    }
}

function shanchu(data){
	var url = contextPath + '/KQDS_MACHININGAct/delRecord.act';
	var id = onclickrow.seqId;
    var param = {id : id};
    $.axse(url, param,
    function(data) {
        if (data.retState == "0") {
            layer.alert('操作成功', {
                  
                end: function() {
                    onclickrow = "";
                    refresh();
                }
            });
        }
    },
    function() {
        layer.alert('操作失败！' );
    });
}
//按钮
function getButtonPower() {
    var menubutton ='';
    for (var i = 0; i < listbutton.length; i++) {
    	//console.log("按钮=="+listbutton[i].qxName);
        if (listbutton[i].qxName == "hz_tryIn") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="hz_tryIn"  onclick="tryIn(6)">试戴</a>';
        } else if (listbutton[i].qxName == "hz_daizou") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="hz_daizou" onclick="daizou(7)">戴牙</a>';
        } else if (listbutton[i].qxName == "fangong") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="fangong" onclick="rework(4)">返工</a>';
        }else if (listbutton[i].qxName == "shanchu") {
            menubutton += ' <a href="javascript:void(0);" class="kqdsCommonBtn" id="shanchu" onclick="del(4)">删除</a>';
        }
    }
    $("#buttonArea").prepend(menubutton);
}

//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height();
    var serachH = $('.searchWrap').outerHeight();
    $('.extraBar .extraBd').height(baseHeight - 65);
    $('#listTable').bootstrapTable('resetView', {
        height: baseHeight - serachH - 162
    });
}
</script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/lzjl/jquery.mousewheel.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/lzjl/jquery.easing.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jiagong/history.js"></script>
</html>
