<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/classImport.jsp" %>
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
	    contextPath = "/kqds";
	}

	String deptId = request.getParameter("deptId");
	if(deptId == null){
		deptId = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>用户列表</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/person/list.css"/>
<!-- 文件上传 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/webuploader-0.1.5/style.css">
<!-- 文件上传 END-->
</head>
<style type="text/css">
	.kqdsSearchBtn {
    	display: inline-block;
    	box-sizing: border-box;
	    height: 22px;
	    line-height: 22px;
	    background: #00a6c0;
	    color: #fff;
	    outline: none;
	    padding: 0 8px;
	    border: 1px solid #00a6c0;
	    border-radius: 3px;
	    margin-right: 3px;
	    text-decoration: none;
	    cursor: pointer;
	    text-align: center;
	    margin-left: 5px;
	}
	.organizationName{
		width: 100px;
	    padding-left: 5px;
	}
</style>
<body>
	<div style="float: left; width: 100%; margin-right: 1%; padding:0 15px;">
		<div class="col-sm-12">
			<div id="toolbar">
				<button id="newAdd" class="kqdsSearchBtn bigBtn">
					<i class="glyphicon glyphicon-plus"></i> 新增
				</button>
				<button id="cleanPassword" class="kqdsSearchBtn bigBtn"  onclick="clearPassword();">
					<i class="glyphicon glyphicon-minus"></i> 清空密码
				</button>
				<button id="deletePerson" class="kqdsSearchBtn bigBtn" onclick="deletePerson();">
					<i class="glyphicon glyphicon-remove"></i> 删除
				</button>
				<button onclick="mbxz()" class="kqdsSearchBtn bigBtn" >
					<i class="glyphicon glyphicon-circle-arrow-down"></i> 导入模板
				</button>
				<input type="hidden" placeholder="" id="imgtype" name="imgtype" value="userdept">
				<a id="filePicker"  class="kqdsSearchBtn bigBtn" style="color: #fff;vertical-align:bottom;top:-4px;"><i class="glyphicon glyphicon-save"></i> 导入</a>
				<a id="export"  class="kqdsSearchBtn bigBtn" style="color: #fff;vertical-align:bottom;top:4px;" onclick="exporttable()">导出</a>
				
				<span class="commonText">姓名：</span>
				<input type="text" name="username" id="username" placeholder="用户名/真实姓名" />
				<span class="commonText">部门：</span>
				<select class="select2" id="deptId" name="deptId"></select>
				
				<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="clearQuery()">清空</a>
				<a href="javascript:void(0);" class="kqdsCommonBtn" onclick="searchPerson()">查询</a>
			</div>
		</div>
		<div class="tableBox">
			<table id="table" data-toolbar="#toolbar" data-height="500"></table>
		</div>
	</div>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.min.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<!-- 文件上传 -->
<script type="text/javascript" src="<%=contextPath%>/static/plugin/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/upload/uploadutil.js"></script>
<!-- 文件上传 END-->
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/admin/kqds.js"></script>
<script>
var contextPath = "<%=contextPath %>";
var static_deptid = "<%=deptId %>";
var pageurl = "<%=contextPath%>/YZPersonAct/selectPage.act";
var static_deleteurl = 'YZPersonAct/delete.act?1=1';
var static_clearPasswordUrl = 'YZPersonAct/clearPassword.act?1=1';

$(function() {
	
	getSelectDeptTree("deptId"); // 部门树下拉框
	
	 // 当前门诊主键
    $("#deptId").val(static_deptid);
	
    getPageList();
    
    // 用户批量导入
    uploadfile(contextPath + "/FileUploadAct/uploadFile.act?module=userdept&organization=<%=ChainUtil.getOrganizationFromUrlCanNull(request)%>");
    
	$(window).resize(function(){
		setHeight();
	});
});

//模板下载
function mbxz() {
    location.href = contextPath + "/YZPersonAct/excelStandardTemplateOut.act";
}
// 清空
function clearQuery(){
	$("#deptId").val(static_deptid);
	$("#username").val("");
}

// 查询按钮
function searchPerson() {
	$('#table').bootstrapTable('refresh', {
        'url': pageurl
    });
}



//获取选中行的usercode
function getIdSelections() {
    return $.map($("#table").bootstrapTable('getSelections'),
    function(row) {
        return row;
    });
}
//复选框
function stateFormatter(value, row, index) {
    if (row.userId === 'admin') {
        return {
            disabled: true,
            checked: false
        };
    }
    return row;
}

function queryParams(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        limit: params.limit,
        offset: params.offset,
        username:$("#username").val(),
        deptId: $("#deptId").val()
    };
    return temp;
}

function queryParamsB(params) {
    var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
        username:$("#username").val(),
        deptId: $("#deptId").val()
    };
    return temp;
}

function setHeight(){
	var tableHeight=0;
	tableHeight=$(window).height()-$(".bs-bars").outerHeight()-50-$(".pagination-detail").outerHeight();
	$(".fixed-table-container").outerHeight(tableHeight);
}
function getTableHeight(){
	var tableHeight=0;
	tableHeight=$(window).height()-$(".bs-bars").outerHeight()-10-$(".pagination-detail").outerHeight();
	return tableHeight;
}
function getPageList() {
	var tableHeight=getTableHeight();
	$(".tableBox").html('<table id="table" data-toolbar="#toolbar" data-height="'+tableHeight+'"></table>');
    $("#table").bootstrapTable({
        url: pageurl,
        dataType: "json",
        queryParams: queryParams,
        pagination: true,
        //在表格底部显示分页工具栏 
        singleSelect: false,
        toolbar: '#toolbar',
        cache: false,
        striped: true,
        sidePagination: "server",
        rowStyle: function(row, index) {
            if (row.isEmptyPass == "1") { //密码为空
                return {
                    css: {
                        "color": "red"
                    }
                }
            }else{
            	return {
                    css: {
                        "color": "black"
                    }
            	}
            }
        },
        onLoadSuccess:function(data){
            $("#table").find("tbody,thead").find("tr").each(function(i,obj){
             $(this).find("td,th").eq(1).css("display","none");
            });
           },
        //服务端处理分页
        columns: [{
            field: ' ',
            checkbox: true,
            formatter: stateFormatter
        },
        {
            title: 'seqIdaa',
            field: 'seqId',
            align: 'center',
            valign: 'middle',
            visible: true,
            switchable: false,
            formatter: function(value, row, index) {
            	if (value) {
                    return "<span class='seqIdStyle'>" + value + "</span>";
                }
            }
        },
        {
            title: '排序号',
            field: 'userNo',
            align: 'center',
            valign: 'middle',
        },
        {
            title: '门诊',
            field: 'organizationname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        
        {
            title: '部门',
            field: 'deptidname',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '用户名',
            field: 'userId',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '真实姓名',
            field: 'userName',
            align: 'center',
            valign: 'middle',
            sortable: true,
            editable: true,
        },
        {
            title: '角色',
            field: 'userprivname',
            align: 'center',
            sortable: true,
            editable: true,
        },
        {
            title: '是否离职',
            field: 'isLeave',
            align: 'center',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
                if (value == "0") {
                    return "否";
                }
                if (value == "1") {
                    return "是";
                }
            }
        },{
            title: '共用部门',
            field: 'xydeptname',
            align: 'center',
            sortable: true,
            editable: true
        },{
            title: '可登录门诊',
            field: 'organization',
            align: 'center',
            sortable: true,
            editable: true,
            formatter: function(value, row, index) {
            	var seqid=row.seqId;
                if (value) {
                    return "<input class='organizationName' disabled='disabled' value=" + value + " /><button class='kqdsSearchBtn organizationUpdate' onclick='organizationUpdateSave(0,"+index+")'>修改</button><button class='kqdsSearchBtn organizationSave' style='display:none;' onclick='organizationUpdateSave(1,"+index+")'>保存</button>";
                }
            }
        }]
    });
}
//门诊修改
function organizationUpdateSave(i,index){
	var trObj=$("#table").find("tbody").find("tr").eq(index);//当前行对象
	var seqid=trObj.find("td").eq(1).find("span").text();
	if(i=="0"){ //修改
		trObj.find(".organizationName").removeAttr("disabled");
		trObj.find(".organizationUpdate").css("display","none");
		trObj.find(".organizationSave").css("display","inline-block");
	}else{ //保存
		var organizationName=trObj.find(".organizationName").val();
		var url = contextPath + '/YZPersonAct/updateOrganization.act';  
	    var params = { 
	    		organization : organizationName,
	    		seqid:seqid
	   	};

	    $.axseSubmit(url, params, function() {}, function(r) {
			layer.alert(r.retMsrg, {
	            end: function() {
	            	trObj.find(".organizationName").attr("disabled","disabled");
	            	trObj.find(".organizationUpdate").css("display","inline-block");
	            	trObj.find(".organizationSave").css("display","none");
	            }
	      });
		}, function(r) {
			layer.alert(r.retMsrg, {
	            end: function() {
	            
	            }
	      });
		});
	}
}
function refresh() {
    $("#table").bootstrapTable('refresh', {
        'url': pageurl
    });

    if (parent.leftFrame) {
        parent.leftFrame.refreshNode(static_deptid);
    }
}

function clearPassword() {
    var selectList = getIdSelections();
    if (selectList.length == 0) {
        layer.alert('请勾选要清空密码的用户' );
        return false;
    }
    // 获取要删除的用户id，以逗号分隔
    var seqIds = "";
    for (var i = 0; i < selectList.length; i++) {
        seqIds += selectList[i].seqId + ",";
    }
    seqIds = seqIds.substring(0, seqIds.length - 1);
    //询问框
    layer.confirm('确定清空密码？', {
        btn: ['确定', '取消'] //按钮
    },
    function() {
        var reqUrl = static_clearPasswordUrl + "&seqId=" + seqIds;
        var serverData = getDataFromServer(reqUrl);
        if (serverData) {
            layer.alert('清空成功', {
                  
                end: function() {
                    refresh();
                }
            });

        }
    });
}

function deletePerson(id) {
    var selectList = getIdSelections();
    if (selectList.length == 0) {
        layer.alert('请勾选要删除的用户' );
        return false;
    }

    // 获取要删除的用户id，以逗号分隔
    var seqIds = "";
    for (var i = 0; i < selectList.length; i++) {
        seqIds += selectList[i].seqId + ",";
    }
    seqIds = seqIds.substring(0, seqIds.length - 1);
    //询问框
    layer.confirm('确定删除？', {
        btn: ['删除', '放弃'] //按钮
    },
    function() {
        var reqUrl = static_deleteurl + "&seqId=" + seqIds;
        var serverData = getDataFromServer(reqUrl);
        if (serverData) {
            layer.alert('删除成功', {
                  
                end: function() {
                    refresh();
                }
            });
        }
    });
}

//导出
function exporttable() {
		var fieldArr=[];
		var fieldnameArr=[];
		$('#table thead tr th').each(function () {
			var field = $(this).attr("data-field");
			if(field!=""){
				fieldArr.push(field);//获取字段
				fieldnameArr.push($(this).children()[0].innerText);//获取字段中文
			}
		});
		var param  = JsontoUrldata(queryParamsB());
		location.href = pageurl+"?flag=exportTable&fieldArr="+JSON.stringify(fieldArr)+"&fieldnameArr="+JSON.stringify(fieldnameArr)+"&"+param;
}

//弹出一个iframe层
$('#newAdd').on('click',
function() {
    layer.open({
        type: 2,
        title: '新建用户',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '520px'],
        content: contextPath+'/YZPersonAct/toNewAdd.act?deptId=' + static_deptid
    });
});
</script>

</body>
</html>
