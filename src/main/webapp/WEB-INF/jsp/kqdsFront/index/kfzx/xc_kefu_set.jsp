<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/kqds";
  }
  String menuid = request.getParameter("menuid");
  String organization = request.getParameter("organization");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<meta charset="utf-8" />
<title>指定客服</title><!-- 从咨询中心 > 转诊按钮 进入转诊列表 > 点击转诊按钮 进入   -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css" />
<style type="text/css">

.infoBd{
	font-size: 12px;
	border-bottom: solid 0px #0e7cc9;
	padding:0 10px 10px;
}

.formBox{
	overflow: hidden;
	padding: 10px 0;
	margin-bottom:10px;
	width:500px;
	margin:0 auto;
}

.formBox .kv{float:left;margin:0 10px 10px 0;}
.kv > label{margin-right: 10px;} /* 重写style.css中的label样式 */

.aBtn{display: inline-block;margin-right:10px;padding: 0 20px;height: 28px;line-height: 28px;border: solid 1px #0e7cc7;border-radius:15px;color: #0e7cc7;}
.aBtn:hover{cursor: pointer;color: #fff;background: #117cca;text-decoration: none;}
</style>

</head>
<body>
	<div class="infoBd">
		<div class="formBox">
		<form  id="form1">
	        <div class="kv">
	          <label>客服人员</label>
	          <div class="kv-v">
              		<select id="kf_select">
              		</select>
	    		</div>
			</div>
			<div class="kv">
			   	<label>指定原因</label>
			   	<div class="kv-v">
			       	<textarea  style="width: 380px;" name="kefuremark" id="kefuremark" rows="3" placeholder="指定原因" ></textarea>	
				</div>
			</div>
		</form>
	    </div>
	     <div class="buttonBar" style="text-align: center;">
				<a href="javascript:void(0);" onclick="saveform()" class="kqdsCommonBtn">保存</a>
	      </div>
	</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/language/zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
var selectedrows = window.parent.selectedrows;
var qxname="";
$(function() {
	//初始化咨询师下拉框
    initSysUserByDeptId($("#kf_select"),"consumer","");
});
function saveform() {
    if ($("#kefu").val() == "") {
        layer.alert('请选择客服人员' );
        return false;
    }
    if ($("#kefuremark").val() == "") {
        layer.alert('请填写指定原因' );
        return false;
    }
    //循环获取表格中项目
    var list = [];
    for (var i = 0; i < selectedrows.length; i++) {
        var seqId = selectedrows[i].uid;
        //console.log("患者Id======"+seqId);
        //循环保存
        var param = {
            seqId: seqId,
            kefu: $("#kf_select").val(),
            kefuremark: $("#kefuremark").val()
        };
        list.push(param);
        //console.log(""+JSON.stringify(list));
    }
    var data = JSON.stringify(list);
    var a = {
        data: data
    };
    var url = '<%=contextPath%>/KQDS_UserDocumentAct/setKeFu.act';
    $.axseSubmit(url, a,
    function() {},
    function(r) {
        if (r.retState == "0") {
            // 加个容错处理
            layer.alert('指定成功', {
                  
                end: function() {
                	window.parent.location.reload(); //刷新父页面 
                	var frameindex = parent.layer.getFrameIndex(window.name);
	                parent.layer.close(frameindex); //再执行关闭
                   
                }
            });
        } else {
            layer.alert('指定失败'  );
        }
    },
    function() {
        layer.alert('指定失败' );
    });
}
function openLayerDialogResize(actionUrl, width, height) {
    layer.open({
        type: 2,
        title: '人员选择',
        maxmin: true,
        shadeClose: true,
        // 点击遮罩关闭层
        area: ['500px', '90%'],
        content: actionUrl
    });

}
setTimeout(findDept("<%=menuid %>"), 0);
function getKfPeople(sDeptId) {
    var url = contextPath + '/YZPersonAct/getUserListByDeptId.act?deptId=' + sDeptId;
    var kfSelect_html="<option value=''>请选择客服</option>";
    $.axseY(url, null,
    function(data) {
    	 //console.log(data+"------------啦啦啦啦啦啦");
    	 for(var a in data){
    		 console.log(data[a].seqId);
    		//console.log(data[a].userName);
    		 kfSelect_html+="<option value="+data[a].seqId+">"+data[a].userName+"</option>";
    	 }
    	 $("#kf_select").html(kfSelect_html);
        //if (data.retState == "0") {
          
            // 加载该页面的所有可操作按钮，每个页面一共有多少按钮是相对固定的，在此基础上，通过获取当前登录用户的权限按钮，来进行界面展示
        //} else {
         //   layer.alert('查询出错！'  );
        //}
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
//查询按钮标识的部门人员
function findDept(menuId){
    var url = contextPath + '/YZSystemAct/getButtonList.act?menuId=' + menuId;
    var DeptId="";
    $.axseY(url, null,
    function(data) {
        if (data.retState == "0") {
            listbutton = data.retData;
            for ( var i = 0; i < listbutton.length; i++) {
        		if(listbutton[i].qxName.search("dept")==0){
        			if(qxname==''){
	        			qxname=listbutton[i].qxName;        				
        			}else{
        				qxname=qxname+","+listbutton[i].qxName;     
        			}
        		}
        	}
            if(qxname!=("")){
        		$.ajax({
        		    url:contextPath+"/SysDeptPrivAct/findDeptNameByButtonName.act",    //请求的url地址
        		    data:{qxname:qxname,organization:"<%=organization%>"},
        		    dataType:"json",   //返回格式为json
        		    type:"post",   //请求方式
        		    success:function(data){
        		    	if(data.length==1){
        		    		DeptId=data[0].id;
        		    	}else{
	        		    	for (var i = 0; i < data.length; i++) {
	        		    		if(DeptId==""){
	        		    			DeptId=data[i].id;
	        		    		}else{
			        		    	DeptId=DeptId+','+data[i].id;
	        		    		}
	        		    	}
        		    	}
        		    	getKfPeople(DeptId);
        		    }
        		});
        	}
        } else {
            layer.alert('查询出错！'  );
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
    
}
</script>

</html>
