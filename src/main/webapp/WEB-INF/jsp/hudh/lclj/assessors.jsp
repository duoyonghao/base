<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}

	String lcljId = request.getParameter("lcljId");

	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<meta charset="utf-8" />
	<title>查看每个流程具体操作项的备注信息</title>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/visit/visitTableLayout.css">
	<style type="text/css">
		.assessorBtn{
			width:30px;
			height:30px;
			font-size: 30px;
			border-radius:50%;
			padding: 0px!important;
		}
		textarea{
			min-height: 0px;
		}
		.table-bordered > tbody > tr > td{
			vertical-align: middle;
			border:1px solid transparent;
			/*     	    border:1px solid red; */
		}
		.table-bordered > thead > tr > th{
			border: aliceblue;
		}
		/* 	变异记录 */
		.panel-default>.panel-heading {
			background-color: #00A6C0!important;
		}
		.panel-group .panel-heading+.panel-collapse>.list-group, .panel-group .panel-heading+.panel-collapse>.panel-body {
			border-top: 0px solid #ddd;
		}
		.detailsBottom .panel-body {
			padding: 0px;
		}
		.variationPanel .collapse.in {
			border-radius: 0px;
			border-bottom-left-radius: 9px;
			border-bottom-right-radius: 9px;
			overflow: hidden;
		}
		.collapse.in {
			box-shadow: 0px 0px 10px 2px #ddd;
			background-color: white;
		}
		#variation_table {
			font-size: 13px;
			margin: 0px 15px;
		}
		#variation_table .node_name {
			display: none;
			width: 100%;
			font-size: 15px;
			font-weight: bold;
			margin-bottom: 10px;
			padding-bottom: 10px;
			border-bottom: 1px solid #ddd;
		}
		#variation_table .variation_nodeinfo {
			display: flex;
			margin-bottom: 10px;
			border-bottom: 1px solid #ddd;
		}
		#variation_table .variation_nodeinfo>.node_time {
			width: 8%;
			font-weight: bold;
		}
		#variation_table .variation_nodeinfo .variation_list {
			width: 85%;
			display: flex;
			flex-wrap: wrap;
		}
		#variation_table .variation_nodeinfo .variation_list>li {
			width: 25%;
			margin-bottom: 10px;
		}
		.colStyle {
			white-space: nowrap;
			overflow: hidden;
			text-overflow: ellipsis;
			display: block;
			width:100px;
		}
	</style>
</head>
<body>
<div>
	<div class="tableBox" style="overflow: hidden;border-radius:8px;border-top-left-radius: 6px;border-top-right-radius: 6px;border-left: 1px solid #ddd;border-right: 1px solid #ddd;border-bottom:1px solid #ddd;">
		<table id="assessor_table" class="table-striped table-condensed table-bordered"></table>
	</div>
	<!-- 变异记录  -->
	<div class="panel panel-default variationPanel">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-parent="" href="#collapsevariation">变异记录</a>
			</h4>
		</div>

		<div id="collapsevariation" class="panel-collapse collapse in">
			<div class="panel-body">
				<div class="widget-body" style="index:100;height:153px;overflow: scroll;">
					<div id="variation_table"></div>
				</div>
			</div>
		</div>
	</div>
</div>

</body>

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-export.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript">
    var lcljId = '<%=lcljId%>';
    var person = '<%=person.getUserName()%>';
    // console.log(person+"--------person");
    // console.log(lcljId+"--------lcljId");
    var pageurl = '<%=contextPath%>/HUDH_LcljCheckRecordAct/getCheckRecord.act'
    $(function() {
        inittable();
        showVariation();
    });

    function queryParams(params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            LcljId:lcljId
        };
        return temp;
    }

    function inittable(){
        $("#assessor_table").attr("data-height", $(window).height() - 55);
        $("#assessor_table").bootstrapTable({
            url: pageurl,
            dataType: "json",
            contentType : "application/x-www-form-urlencoded",   //必须有
            pagination: true,//是否显示分页（*）
            sidePagination : 'client',//server:服务器端分页|client：前端分页
            queryParams: queryParams,
            pageSize: 10,
            pageList : [10, 15],
            paginationShowPageGo: true,
            onLoadSuccess: function(data) { //加载成功时执行\
//		        	console.log(JSON.stringify(data)+"-------data");
            },
            columns: [
                {
                    title : '序号',
                    align: "center",
                    width: 40,
                    formatter: function (value, row, index) {
// 		        		return index + 1;
                        var pageSize = $('#assessor_table').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                        var pageNumber = $('#assessor_table').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                        return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
                    }
                },
                {
                    title: '节点名称',
                    field: 'name',
                    align: 'center',
                    width: 80,
                    formatter: function(value, row, index) {
                        if (value) {
                            return '<span class="">' + value + '</span>';
                        }
                    }
                },
                {
                    title: '手术类型',
                    field: 'flowname',
                    align: 'center',
                    width: 150,
                    formatter: function(value, row, index) {
                        if (value) {
                            return '<span class="">' + value + '</span>';
                        }
                    }
                },
                {
                    title: '节点时间',
                    field: 'nodetime',
                    align: 'center',
                    width: 150,
                    sortable: true,
                    formatter: function(value, row, index) {
                        if (value) {
                            return '<span class="">' + value + '</span>';
                        }
                    }
                },
                {
                    title: '挂号时间',
                    field: 'regtime',
                    align: 'center',
                    width: 150,
                    sortable: true,
                    editable: true,
                    formatter: function(value, row, index) {
                        return '<span class="">' + value + '</span>';
                    }
                },
                {
                    title: '预约下一次时间',
                    field: 'nexttime',
                    align: 'center',
                    width: 100,
                    sortable: true,
                    formatter: function(value, row, index) {
                        return '<span class="">' + value + '</span>';
                    }
                },
                {
                    title: '就诊分类',
                    field: 'recesortname',
                    align: 'center',
                    width: 100,
                    formatter: function(value, row, index) {
                        return '<span class="">' + value + '</span>';
                    }
                },
                {
                    title: '挂号分类',
                    field: 'regsortname',
                    align: 'center',
                    width: 100,
                    formatter: function(value, row, index) {
                        return '<span class="">' + value + '</span>';
                    }
                },
                {
                    title: '挂号医生',
                    field: 'regdoctorname',
                    align: 'center',
                    width: 100,
                    formatter: function(value, row, index) {
                        return '<span class="">' + value + '</span>';
                    }
                },
                {
                    title: '提交人',
                    field: 'createuser',
                    align: 'center',
                    width: 80,
                    formatter: function(value, row, index) {
                        return '<span class="">' + value + '</span>';
                    }
                },
                {
                    title: '提交时间',
                    field: 'updatetime',
                    align: 'center',
                    width: 100,
                    formatter: function(value, row, index) {
                        return '<span class="">' + value + '</span>';
                    }
                },
                {
                    title: '节点备注',
                    field: 'lcljremark',
                    align: 'center',
                    width: 100,
                    formatter: function(value, row, index) {
                        return '<span class="colStyle" title="' + value + '">' + value + '</span>';
                    }
                },
                {
                    title: '备注',
                    field: 'remark',
                    align: 'center',
                    width: 200,
                    clickEdit: true,
                    formatter: function(value, row, index) {
                        if(value==""){
                            return '<textarea name="remark" rows="1" cols="20">' + value + '</textarea>';
                        }else{
                            return '<div style="width:200px;">' + value + '</div>';
                        }
                    }
                },
                {
                    title: '状态',
                    field: 'status',
                    align: 'center',
                    width: 90,
                    sortable: true,
                    formatter: function(value, row, index) {
// 		            	console.log(JSON.stringify(row.lcljid)+"--------row");
                        if (row.status == "0") {
                            return '<input class="kqdsCommonBtn assessorBtn" id="pass" statusid="1" seqId="'+row.seqId+'" lcljid="'+row.lcljid+'" style="color:green;" value="√" type="button" onclick="passBtn(this)"><input class="kqdsCommonBtn assessorBtn" id="veto" statusid="2" seqId="'+row.seqId+'" lcljid="'+row.lcljid+'" style="color:red;" value="×" type="button" onclick="vetoBtn(this)">';
                        }else if(row.status == "1"){
                            return '<span style="color:green;">审核通过<span>'
                        }else if(row.status == "2"){
                            return '<span style="color:red;">未通过<span>'
                        }else if(row.status == "3") {
                            return '<span style="color:Goldenrod;">退回操作审核记录<span>'
                        }

                    }
                }]
        }).on('click-row.bs.table',
            function(e, row, element) {
                $('.success').removeClass('success'); //去除之前选中的行的，选中样式
                $(element).addClass('success'); //添加当前选中的 success样式用于区别
                var index = $('#assessor_table').find('tr.success').data('index'); //获得选中的行
                onclickrowOobj = $('#assessor_table').bootstrapTable('getData')[index];
            }).on('dbl-click-row.bs.table', function (e, row, ele,field) {});

    }

    function passBtn(thi){
// 		审核时间
        var submittime = new Date().Format("yyyy-MM-dd hh:mm:ss");
// 		当前备注
        var remark=$(thi).parent().parent().find("textarea[name='remark']").val();
        if(remark==""||remark==null){
            layer.alert("请输入审核备注！");
            return false;
        }
        var statusId=$(thi).attr("statusid"); //当前状态ID
        var seqId=$(thi).attr("seqId");//当前ID
        var LcljssId=$(thi).attr("Lcljid")//当前临床路径ID
        var remark=remark;
// 		console.log(seqId+"--------------"+statusId+"-------"+LcljssId);
        var params = {
            seqId:seqId,
            status:statusId,
            lcljid:LcljssId,
            remark:remark,
            updatetime:submittime,
            updateuser:person,
        };
        var url = contextPath + "/HUDH_LcljCheckRecordAct/insertOrUpdate.act";
        $.axse(url, params,
            function(data) {
                if(data.retState=="0"){
                    layer.alert("审核完成！", {
                        end: function() {
                            refresh();
// 				   	            	location.reload();  //刷新页面
                        }
                    });
                }else{
                    layer.alert('审核失败！', {
                    });
                }
            },
            function() {
                layer.alert('加载失败！', {
                });
            });

    }
    function vetoBtn(thi){
// 		审核时间
        var submittime = new Date().Format("yyyy-MM-dd hh:mm:ss");
// 		当前备注
        var remark=$(thi).parent().parent().find("textarea[name='remark']").val();
        if(remark==""||remark==null){
            layer.alert("请输入审核备注！");
            return false;
        }
        var statusId=$(thi).attr("statusid"); //当前状态ID
        var seqId=$(thi).attr("seqId");//当前ID
        var LcljssId=$(thi).attr("Lcljid")//当前临床路径ID
        var remark=remark;
        var params = {
            seqId:seqId,
            status:statusId,
            lcljid:LcljssId,
            remark:remark,
            updatetime:submittime,
            updateuser:person,
        };
        var url = contextPath + "/HUDH_LcljCheckRecordAct/insertOrUpdate.act";
        $.axse(url, params,
            function(data) {
                if(data.retState=="0"){
                    layer.alert("审核完成！", {
                        end: function() {
                            refresh();
// 				   	            	location.reload();
                        }
                    });
                }else{
                    layer.alert('审核失败！', {
                    });
                }
            },
            function() {
                layer.alert('加载失败！', {
                });
            });
    }
    function refresh() {
        $('#assessor_table').bootstrapTable('refresh', {
            'url': pageurl
        });
    }
    // 	变异记录展示
    var variationNum=$(window.parent.document).find("#patient_id").text();
    function showVariation(){
        var params = {
            "order_number" : variationNum,//手术状态的编号
        };
        var url = contextPath + "/HUDH_LcljOperationNodeInforAberranceAct/findOperationNodeInforAberranceByOrderNumberAndNodeId.act";
        $.axse(url, params,
            function(data) {
// 				  		 console.log(JSON.stringify(data)+"------------data");
                if(data.length==0){
                    $("#variation_table").html("<span id='variation_notext' style='font-size:13px;font-weight:bold;'>当前暂无变异情况！</sapn>");
                    return;
                }
                var variationObjArr=[];//有值集合数组
                var variationObj={};//返回值全部有值集合
                for(var i=0;i<data.length;i++){
                    for(var key in data[i]){
                        if(data[i][key]){
                            variationObj[key]=data[i][key];
                        }
                    }
                    variationObjArr.push(variationObj);
                    variationObj={};
                }

                //有值去重新集合
                var takeOutrepeatArr=[];//去重后集合新数组
                var takeOutrepeat={};//去重后集合
                var repeat=true;
                for(var j=0;j<variationObjArr.length;j++){
                    takeOutrepeat={};
                    repeat=true;
                    for(var showkey in variationObjArr[j]){
                        if(JSON.stringify(takeOutrepeat)!="{}"){
                            for(var rkey in takeOutrepeat){
                                if(takeOutrepeat[rkey]==variationObjArr[j][showkey]){
                                    repeat=false;
                                }else{
                                    repeat=true;
                                }
                            }
                        }
                        if(repeat){
                            takeOutrepeat[showkey]=variationObjArr[j][showkey];
                        }
                    }
                    takeOutrepeatArr.push(takeOutrepeat);

                };
                //循环页面所有节点，比对得到当前节点中文名称
                var nodeLiLength=$(window.parent.document).find("#flow_path").find("li");
// 						console.log(nodeLiLength.length);
                var nodeName_C; //节点中文名称
                for(var nodei=0; nodei<nodeLiLength.length; nodei++){
                    if(nodeLiLength.eq(nodei).find("span").attr("id")==takeOutrepeatArr[0].nodeid){
                        nodeName_C=nodeLiLength.eq(nodei).find("font").text();
                    }
                }
                var variation_table;//变异内容html字符串
                variation_table="<font id='"+takeOutrepeatArr[0].nodeid+"' class='node_name'>"+nodeName_C+":</font>";//变异节点中文名

                //展示每一次变异情况
                for(var k=0;k<takeOutrepeatArr.length;k++){
//							console.log(JSON.stringify(takeOutrepeatArr[k]));
                    //不需要展示的字段
                    delete takeOutrepeatArr[k].seq_id;
                    delete takeOutrepeatArr[k].organization;
                    delete takeOutrepeatArr[k].order_number;
                    delete takeOutrepeatArr[k].createuser;
                    //console.log(JSON.stringify(takeOutrepeatArr[k])+"----------删除不需要字段后的对象");

                    variation_table+="<div class='variation_nodeinfo'>";
                    variation_table+="<span class='node_time' style='margin-top:20px;'>第"+(k+1)+"次变异：</span>";
                    variation_table+="<span class='' style='font-weight: bold;margin-left:-103px;width: 20%;'>"+takeOutrepeatArr[k].nodename+"&nbsp;"+takeOutrepeatArr[k].nodelimit+"</span>";
                    variation_table+="<span class='' style='font-weight: bold;margin: 40px 0px 0px -220px;display: inline-block;width: 14%;'>变异时间："+takeOutrepeatArr[k].createtime+"</span>";
                    variation_table+="<ul class='variation_list'>";

                    //循环集合展示数据
                    for(var tablekey in takeOutrepeatArr[k]){
                        //手术治疗节点：术中用药单独处理
                        if(nodeName_C=='手术治疗'){
                            //术中用药
                            if(tablekey=="visit_time"){
                                variation_table+="<li><span style='font-weight: bold;'>术中用药:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
                                continue;
                            }
                            if(tablekey=="before_modulo_bite"){
                                continue;
                            }
                        }

                        //节点名称,放在第一td,跳过
                        if(tablekey=="nodeid"){
                            continue;
                        }
                        //多选
                        //变异创建时间--变异时间位置变更
                        if(tablekey=="createtime"){
                            variation_table+="<li style='display:none;'><span style='font-weight: bold;'>变异时间:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
                            continue;
                        }
                        // 隐藏变异节点名称
                        if(tablekey=="nodename"){
                            variation_table+="<li style='display:none;'><span style='font-weight: bold;'>节点名称:</span>"+takeOutrepeatArr[k][tablekey].nodename+"</li>";
                            continue;
                        }
                        if(tablekey=="nodelimit"){
                            variation_table+="<li style='display:none;'><span style='font-weight: bold;'>节点名称2:</span>"+takeOutrepeatArr[k][tablekey].nodelimit+"</li>";
                            continue;
                        }
                        //备注
                        if(tablekey=="remark"){
                            variation_table+="<li><span style='font-weight: bold;'>备注:</span>"+takeOutrepeatArr[k][tablekey]+"</li>";
                            continue;
                        }
                        variation_table+="<li>"+takeOutrepeatArr[k][tablekey]+"</li>";
                    }
                    variation_table+="</ul>";
                    variation_table+="</div>";
                }
                //   console.log(variation_table);
                document.getElementById("variation_table").innerHTML=variation_table;

            },
            function() {
                layer.alert('查询失败！', {
                });
            });
    }
</script>
</html>
