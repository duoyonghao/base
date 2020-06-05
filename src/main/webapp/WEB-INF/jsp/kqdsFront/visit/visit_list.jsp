<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	YZPerson person = SessionUtil.getLoginPerson(request);
	// 是否可以修改别人的回访信息、和回访结果 0不可以 1可以
	String canEditOrder = UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag10_canEditOrder, request);

	String pushusercode = request.getParameter("usercode");
	String lytype = request.getParameter("type");//如果传值为isparent 为layer.open 弹出
	if (lytype == null) {
		lytype = "noparent";//如果传值不为isparent默认设为noparent 为parent.layer.open 弹出
	}
	String noadd = request.getParameter("noadd");//如果传值为1 隐藏添加按钮
	if (noadd == null) {
		noadd = "0";
	}
	
	//获取从左侧菜单点击带过来的菜单id
	String menuid = request.getParameter("menuId");

	String is_Wd_oper = request.getParameter("is_Wd_oper");//如果传值为1 隐藏添加按钮
	if (is_Wd_oper == null) {
		is_Wd_oper = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>回访列表</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/bingli.css" />
<!-- 数据表中数据的样式 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/tableData.css" />
<!--用来实现 滚动条出现时table对不齐的问题  tableHeaderWidth.js -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/index/tableHeaderWidth.css"/>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/tableHeaderWidth.js"></script>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/index/btnfuc.js"></script>
<style>
#centerWrap{
	padding:0 15px;
}
.titleDiv{
	box-sizing: border-box;
    color: #373737;
    font-family: "Microsoft YaHei";
    height: 30px;
    position: relative;
    margin-bottom: 5px;
    border-bottom:1px solid #ddd;
}
.titleDiv .title{
	font-size: 18px;
    height: 20px;
    line-height: 20px;
    float: left;
    display: block;
    margin-top: 5px;
}
.fixed-table-container{
	border-left: 1px solid #ddd;
	border-right: 1px solid #ddd;
	border-bottom:1px solid #ddd;
	border-radius: 6px;
	/* border-top-left-radius: 6px;
	border-top-right-radius: 6px; */
	overflow: hidden;
}
</style>


</head>
<body>
	<div id="centerWrap" style="padding-top:15px;">
		<!-- <div class="titleDiv">
			<span class="title"></span>
		</div>  -->
		<div class="tableBox">
			<!-- <div class="tableHeader"></div> -->
			<table id="table" class="table-striped table-condensed table-bordered" data-height="460"></table>
		</div>
	<div>	
		<!--查询条件-->
	    <div class="searchModule" style="margin-bottom:2px;text-align:right;">
	    	<header>
	    		<span>查询条件</span>
	    		<!-- <i>共有记录 <span id="total"></span> 条</i> -->
	    	</header>
	    	<section id="menzhen" style="border-bottom:none;">
	    		<div style="float:right;margin-top:2px;">
		            <a id="clearmenzhen" href="javascript:void(0);" class="kqdsCommonBtn">清空</a>
					<a id="query" onclick="query();" href="javascript:void(0);" class="kqdsSearchBtn">查询</a>
		        </div>
	    		<ul class="conditions">
					<li class="unitsDate commonUse">
			    		<span>回访时间</span>
	    				<input type="text" placeholder="回访时间" id="visittime" readonly>
	                </li>
					<li>
						<span>回访状态</span>
						<select name="status" id="status">
							<option value="">请选择</option>
		                	<option value="1">已回访</option>
		                	<option value="0">未回访</option>
						</select>
					</li>

					<li>
						<span>模糊查询</span>
						<input type="text" id="musername" name="musername"  placeholder="回访人员" />
					</li>
					<li>
						<span>回访满意度</span>
						<select name="myd" id="myd">
							<option value="">请选择</option>
						</select>
					</li>
					<li>
						<span>回访分类</span>
						<select name="hffl" id="hffl">
							<option value="">请选择</option>
						</select>
					</li>
	    		</ul>
	    	</section>
	    </div>
	</div>
		<div class="buttonBar" style="text-align:left;" >
			<a href="javascript:void(0);" id="tjhf" class="kqdsCommonBtn" onclick="goshowVisit()">详情</a>
			<%
				if (is_Wd_oper == null || "".equals(is_Wd_oper)) {
			%>
					<a href="javascript:void(0);" id="tjhf" class="kqdsCommonBtn" onclick="goAddVisit()">添加</a>
					<%
						}
					%>
			
			<a href="javascript:void(0);" id="xghf" class="kqdsCommonBtn" onclick="goEditVisit()">修改</a> 
			<a href="javascript:void(0);" id="hfjg" class="kqdsCommonBtn" onclick="goPostVisit()">回访</a>
			<a href="javascript:void(0);" id="hfjg" class="kqdsCommonBtn" onclick="delVisit()">删除</a>
			<a href="javascript:void(0);" id="jhgl" class="kqdsCommonBtn hide" onclick="planManage()">计划管理</a>
			<a href="javascript:void(0);" id="tjhfjh" class="kqdsCommonBtn hide" onclick="goAddVisitPlan()">添加回访计划</a>
		</div>
	</div>
</body>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
var onclickrowObj = "";
var pageurl = '<%=contextPath%>/KQDS_VisitAct/selectListNotByPersonId.act';
var visitid = "<%=person.getSeqId()%>";
var lytype = "<%=lytype%>";
var onclickrow = "";
var regno = "";
var isdelreg = 0;
var pushusercode = "<%=pushusercode%>";
var noadd = "<%=noadd%>";
var listbutton = window.parent.listbutton;
var patienPageIdentifying="returnVisit"; // 2019/8/14 lutian 用于子页面标识
$(function() {
    if (pushusercode == "" || pushusercode == null || pushusercode == "null" || pushusercode == undefined) {
        //不是从	消息推送页面跳转过来的
        onclickrowObj = window.parent.onclickrowOobj;
        //如果选中的不是挂号记录
        if (onclickrowObj == "" || onclickrowObj.ifcost == null) {
            regno = onclickrowObj.regno;
        } else {
            regno = onclickrowObj.seqId;
            isdelreg = onclickrowObj.del;
        }
        if (onclickrowObj.usercode != "" && onclickrowObj.usercode != null) {
            var tableheaderstr = "";
            //展示按钮
            tableheaderstr += onclickrowObj.username;
            tableheaderstr += "(" + onclickrowObj.usercode + ")的回访记录";
            $(".title").html(tableheaderstr);
        }
        pushusercode = onclickrowObj.usercode;
    }
    //推送消息打开的页面 不显示 添加回访按钮
    if (noadd == "1") {
        $("#tjhf").hide();
    }
  //时间选择
    $(".unitsDate input").datetimepicker({
        language: 'zh-CN',
        minView: 2,
        autoclose: true,
        format: 'yyyy-mm-dd',
        pickerPosition: "top-right"
    });
    inittable();
  //查询回访分类
    $.ajax({
		type: "GET",
     	url: contextPath +"/YZDictAct/getHFFLSeqid.act",
      	dataType: "json",
      	data:{parentCode:"HFFL"},
      	success: function(data){     
      		//console.log("返回数据==="+JSON.stringify(data));
      		if(data.length>0){
	      		for (var i = 0; i < data.length; i++) {
					$("#hffl").append("<option value="+data[i].seqId+">"+data[i].dictName+"</option>");
				}
      		}
      	}
	})
	//查询回访满意度
    $.ajax({
		type: "GET",
     	url: contextPath +"/YZDictAct/getHFFLSeqid.act",
      	dataType: "json",
      	data:{parentCode:"HFMYD"},
      	success: function(data){     
      		//console.log("返回数据==="+JSON.stringify(data));
      		if(data.length>0){
	      		for (var i = 0; i < data.length; i++) {
					$("#myd").append("<option value="+data[i].seqId+">"+data[i].dictName+"</option>");
				}
      		}
      	}
	})
  //获取当前页面所有按钮 
   getButtonPower();
});

function delVisit(seqId, templateId) {
    //询问框
    layer.confirm('确定要删除该回访吗？', {
        btn: ['确定', '取消'] //按钮
    },
    function() {
    	//已回访过的记录 不能再修改
        if (onclickrow.seqId == null || onclickrow.seqId == "") {
            layer.alert('请选择一条回访记录'  );
            return false;
        }
        if (onclickrow.status == "1") {
            layer.alert('您选中的记录已经回访过了,无法删除'  );
            return false;
        }
        if (visitid != onclickrow.visitor) {
        	var status=true;
        	$.ajax({
     			type: "POST",
     			async: false,
     	     	url: contextPath +"/YZPersonAct/findIsLeaveBySeqId.act",
     	      	dataType: "json",
     	      	data:{seqId:onclickrow.visitor},
     	      	success: function(data){     
     	      		if(data.retMsrg!='1'){
     	      			status=false;
     	      		}
     	      	}
     		});
        	if(!status){
	     	    layer.alert('只能操作自己的回访记录'  );
		     	return false;
        	}
        }
        var url = contextPath + '/KQDS_VisitAct/deleteObj.act?seqId=' + onclickrow.seqId;
        $.axse(url, null,
        function(r) {
            if (r.retState == "0") {
                layer.alert('删除成功', {
                      
                    end: function() {
                    	refresh();
                    }
                });
            } else {
                layer.alert(r.retMsrg  );
            }
        },
        function() {
            layer.alert('请求失败'  );
        });
    });
}

/*得到查询的参数  */
function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        usercode:pushusercode,
       /*  limit: params.limit,   //页面大小
        offset: params.offset, //页码 
        pageIndex : params.offset/params.limit + 1, //当前页面,默认是上面设置的1(pageNumber) */
        status: $("#status").val(),//已回访，未回访
        username: $("#musername").val(),//患者名称
        visittime:$("#visittime").val(),
        visitor:visitid,//回访人员
        myd:$("#myd").val(),//满意度
        hffl:$("#hffl").val()//回访分类
    };
    return temp;
   	//console.log("回访查询条件="+JSON.stringify(temp));
}


//加载表格
function inittable() {
    $('#table').bootstrapTable({
        url: pageurl,
        queryParams: queryParams,
        dataType: "json",
        cache: false,
        striped: true,
        onLoadSuccess:function(data){
        	//console.log(JSON.stringify(data)+"--------回访数据");
        	setHeight();
        	/*表格载入时，设置表头的宽度 */
            setTableHeaderWidth(".tableBox");
        
        },
        columns: [
	/* 	{
            title: '门诊',
            field: 'organization',
            align: 'center',
            valign: 'middle',
            sortable: true,
            formatter: function(value, row, index) {
                var pjid = "table" + index + "organization";
                getValueOrg(pjid, value);
                return "<span class='name' id='" + pjid + "'></span>";
            } 
        },*/
        {
            title: '回访状态',
            field: 'status',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                var htmlstr = "";
                if (row.status == "0") {
                    htmlstr = "<span style='color:red'>未回访</span>";
                } else if (row.status == "1") {
                    htmlstr = "<span style='color:green'>已回访</span>";
                }
                return htmlstr;
            }
        },
        {
            title: '回访时间',
            field: 'visittime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '回访人员',
            field: 'visitorname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value) {
                    return "<span class='name'>"+value+"</span>";
                }
            }
        },
        {
            title: '患者编号',
            field: 'usercode',
            align: 'center',
            
            visible: false,
            switchable: false
        },
        {
            title: '患者手机',
            field: 'telphone',
            align: 'center',
            
            visible: false,
            switchable: false
        },
        {
            title: '患者性别',
            field: 'sex',
            align: 'center',
            
            visible: false,
            switchable: false
        },
        {
            title: '患者姓名',
            field: 'username',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != null && value != "") {
                    return '<span class="name" title="' + value + '">' + value + '</span>';
                }
            }
        },
        {
            title: '回访分类',
            field: 'visittype',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if (value) {
                    return "<span class='name' title="+value+">"+value+"</span>";
                }else{
                	return "<span class='name' title="+row.hfflname+">"+row.hfflname+"</span>";
                }
            }
        },
        {
            title: '回访要点',
            field: 'hfyd',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '" class="remark">' + value + '</span>';
                }
            }
        },
        {
            title: '提交人员',
            field: 'postpersonname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if (value) {
                    return "<span class='name'>"+value+"</span>";
                }
            }
        },
        {
            title: '回访结果',
            field: 'hfresult',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
                if (value != "" && value != null) {
                    return '<span title="' + value + '" class="remark">' + value + '</span>';
                }
            }
        },
        {
            title: '回访结果时间',
            field: 'finishtime',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if(value == null){
            		value = "";
            	}
                return '<span title="' + value + '">' + value + '</span>';
            }
        },
        {
            title: '满意度',
            field: 'mydname',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if (value) {
                    return "<span class='name'>"+value+"</span>";
                }
            }
        },
        {
            title: '受理类型',
            field: 'accepttypename',
            align: 'center',
            
            sortable: true,
            formatter: function(value, row, index) {
            	if (value) {
                    return "<span class='name'>"+value+"</span>";
                }
            }
        }]
    }).on('click-row.bs.table',
    function(e, row, element) {
        $('.success').removeClass('success'); //去除之前选中的行的，选中样式
        $(element).addClass('success'); //添加当前选中的 success样式用于区别
        var index = $('#table').find('tr.success').data('index'); //获得选中的行
        onclickrow = $('#table').bootstrapTable('getData')[index];
    });
}
function refresh() {
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
};

//添加回访
function goAddVisit() {
    if (isdelreg == "1") {
        layer.alert('撤销的挂号无法添加回访！' );
        return false;
    }
    if (pushusercode == null || pushusercode == "") {
        layer.alert('请选择患者'  );
        return false;
    }
    layer.open({
        type: 2,
        title: '添加回访',
        shadeClose: false,
        shade: 0.6,
        area: ['550px', '480px'],
        content: '<%=contextPath%>/KQDS_VisitAct/toVisitAdd.act?lytype='+lytype+'&usercode=' + pushusercode + '&regno=' + regno //iframe的url
    });
}


//计划管理 
function planManage(){
	parent.layer.open({
     type: 2,
     title: '回访计划管理',
     shadeClose: false,
     shade: 0.6,
     area: ['850px', '620px'],
     content: '<%=contextPath%>/KQDS_VisitAct/toVisitPlanManage.act'
 });
} 

//添加回访计划
function goAddVisitPlan() {
    if (isdelreg == "1") {
        layer.alert('撤销的挂号无法添加回访！' );
        return false;
    }
    if (pushusercode == null || pushusercode == "") {
        layer.alert('请选择患者'  );
        return false;
    }
    parent.layer.open({
        type: 2,
        title: '添加回访计划',
        shadeClose: false,
        shade: 0.6,
        area: ['850px', '580px'],
        content: '<%=contextPath%>/KQDS_VisitAct/toVisitPlanAdd.act?lytype='+ lytype+'&usercode=' + pushusercode + '&regno=' + regno  //iframe的url
    });
}

//修改回访
function goEditVisit() {
    //已回访过的记录 不能再修改
    if (onclickrow.seqId == null || onclickrow.seqId == "") {
        layer.alert('请选择一条回访记录'  );
        return false;
    }
    if (onclickrow.status == "1") {
        layer.alert('您选中的记录已经回访过了,无法修改'  );
        return false;
    }
    if ('<%=canEditOrder%>' != '1' && visitid != onclickrow.visitor) {
    	var status=true;
    	$.ajax({
 			type: "POST",
 			async: false,
 	     	url: contextPath +"/YZPersonAct/findIsLeaveBySeqId.act",
 	      	dataType: "json",
 	      	data:{seqId:onclickrow.visitor},
 	      	success: function(data){     
 	      		if(data.retMsrg!='1'){
 	      			status=false;
 	      		}
 	      	}
 		});
    	if(!status){
     	    layer.alert('只能操作自己的回访记录'  );
	     	return false;
    	}
    }
    var url = '<%=contextPath%>/KQDS_VisitAct/toVisitEdit.act?type=' + lytype + '&seqId=' + onclickrow.seqId;
    /* if (lytype == "isparent") {
        static_btnfucDeal('visit_edit', url, false);
    } else {
        static_btnfucDeal('visit_edit', url, true);
    } */
    layer.open({
        type: 2,
        title: '修改回访',
        shadeClose: false,
        shade: 0.6,
        area: ['550px', '480px'],
        content: url
    });
    
}


//清空门诊查询条件
$('#clearmenzhen').on('click',
   function() {
      $("#menzhen :input").not(":button, :submit, :reset").val("").removeAttr("checked").remove("selected"); //核心
       /**********************默认初始化门诊查询条件****************************/
      static_organization = '<%=ChainUtil.getCurrentOrganization(request)%>';
      initHosSelectListNoCheck('organization_mz', static_organization)
   /**********************默认初始化门诊查询条件END****************************/

});

function query() {
    $('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}

//回访结果
function goPostVisit() {
    //判断选中数据是否已回访 如果已回访 则提示已经回访过了 不跳转页面
    if (onclickrow.seqId == null || onclickrow.seqId == "") {
        layer.alert('请选择一条回访记录'  );
        return false;
    }
    var nowdate = getNowFormatDate();
	// 预约时间不是今天或者之后的日期  则提示不能填写回访结果
	/* if(checkdate(nowdate,onclickrow.visittime.substring(0,10)) == 1){ // 0是当前日期大于回访日期  1 当前日期小于回访日期  2是当前日期等于回访日期
		layer.alert('未到回访时间，不能填写回访结果', {});
		return false;
	} */
	if(onclickrow.status == "1"){
		layer.alert('您选中的回访记录已经回访过了' );
		return false;
	}
	//查询回访人员是否离职
	if('<%=canEditOrder%>' != '1' && visitid != onclickrow.visitor){
		var status=true;
    	$.ajax({
 			type: "POST",
 			async: false,
 	     	url: contextPath +"/YZPersonAct/findIsLeaveBySeqId.act",
 	      	dataType: "json",
 	      	data:{seqId:onclickrow.visitor},
 	      	success: function(data){     
 	      		if(data.retMsrg!='1'){
 	      			status=false;
 	      		}
 	      	}
 		});
    	if(!status){
     	    layer.alert('只能操作自己的回访记录'  );
	     	return false;
    	}
	}
    // layer 弹框
    var requrl = '<%=contextPath%>/KQDS_VisitAct/toVisitPost.act?type=' + lytype + '&seqId=' + onclickrow.seqId;
    layer.open({
	          type: 2,
	          title: '回访结果',
	          shadeClose: false,
	          shade: 0.6,
	          area: ['75%', '98%'],
	          content: requrl 
	      	 });
    /* if (lytype == "isparent") {
        static_btnfucDeal('visit_post', requrl, false);
    } else {
        static_btnfucDeal('visit_post', requrl, true);
    } */

}
function goshowVisit(){
	 if (onclickrow.seqId == null || onclickrow.seqId == "") {
	        layer.alert('请选择一条回访记录', {
	              
	        });
	        return false;
	 }
	 var index = parent.layer.open({
        type: 2,
        title: '回访详情',
        maxmin: true,
        shadeClose: true,
        // 点击遮罩关闭层
        area: ['550px', '520px'],
        content: '<%=contextPath%>/KQDS_VisitAct/toVisitDetail.act?seqId=' + onclickrow.seqId
     });
}

/**
 *  设置按钮权限操作 
 */
function getButtonPower(){
	var menubutton1 = "";
	// 创建一个数组，存放listbutton的qxName 
	var listbuttonArray = new Array();
	for ( var i = 0; i < listbutton.length; i++) {
		//console.log("获取到的按钮="+listbutton[i].qxName);
		listbuttonArray[i] = listbutton[i].qxName;
	}
	/* 按钮 */
	var btnList =  '[';
		btnList	+= '{"qx":"jhgl","name":"计划管理"},'; // 最后一个不要逗号
		btnList	+= '{"qx":"tjhfjh","name":"添加回访计划"}'; // 最后一个不要逗号
	    btnList	+= ']';
	    var jsonbtnList = jQuery.parseJSON( btnList );
		for( var i = 0; i < jsonbtnList.length; i++){
			// 判断当前用户具备的按钮权限
			var index = jQuery.inArray(jsonbtnList[i].qx, listbuttonArray);
			// index = -1 时，表示当前用户没有此按钮的操作权限
			if( index == -1 ){//无权限的展示
			} else {//有权限的展示
				$("#"+jsonbtnList[i].qx).removeClass("hide");
			}
		}
}
function setHeight(){
	$("#table").bootstrapTable("resetView",{
		height:$(window).outerHeight()-$(".buttonBar").outerHeight()-50
	})
}
</script>
</html>
