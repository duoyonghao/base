<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/inc/header.jsp"%>
<%
	YZPerson person = SessionUtil.getLoginPerson(request);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>常用病历库</title>
<!-- 点击右侧个人中心  中间病历 图标 页面， 进入后点击  常用病历库按钮 -->
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/fhbuttonTree.css">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
<style>
	.kuBtn{
		margin: 0 3% 0 0;
	}
	table div{
	    
	}
</style>
</head>
<body>
	<div style="float: left;width:24%;margin-left:1%;">
		<div class="btnFather" style="padding:5px 0;overflow:hidden;">
		<!-- wl 修改了下面 自用病历库  按钮 class名 重写样式 -->
			<span id="sfbz"  class="kuBtn kuBtnCurrent" >
			 自用病历库
				<!-- <label style="cursor: pointer;">自用病历库</label> -->
			</span>
			<!-- &nbsp;&nbsp;&nbsp;&nbsp; -->
		<!-- wl 修改了下面 标准病历库  按钮 class名 重写样式 -->
			<span id="sftc" class="kuBtn" >
			标准病历库
				<!-- <label style="cursor: pointer;">标准病历库</label> -->
			</span>
		</div>
		<div class="searchWrap">
	        <input type="text" placeholder="搜索" name="search" id="search" class="searchInput">
			<a href="javascript:void(0);" id="infosearch" class="hc-icon icon16 search-icon searchBtn" style="position: absolute;top: 44px;left: 200px;" onclick="treeInit()"></a>
		</div>
		<ul id="treeDemo" class="ztree" style="width:98%;margin-top:0;"></ul>
	</div>
	<div style="float: left;width:74%;margin-right:1%;">
		<div id="toolbar" style="padding:5px 0; margin-top:0;">
		<!-- wl 修改了下面按钮 class名 重写样式 -->
			<button id="parentIframe" class="confirmBtn">
				<i class="glyphicon glyphicon-plus"></i> 确认
			</button>
		</div>
		<div class="box-body" id="blcontent" style="overflow:auto;"></div>
	</div>
<script>
var perseqId = "<%=person.getSeqId()%>";
var content = "";
var mtype = "0";
var static_type = 0;
$(function() {
    zTreeInit();
    
    /*树自适应高度  */
    selfAdaptive();
});
/*树自适应高度  */
function selfAdaptive(){
	var treeHeight=$(window).height()-$(".btnFather").outerHeight()-10;
	$("#treeDemo").outerHeight(treeHeight-$(".searchWrap").outerHeight());
	$("#blcontent").outerHeight(treeHeight);
}
function treeInit(){
	if(static_type == 1){
		$("#sftc").click();
	}else{
		$("#sfbz").click();
	}
}
$("#sfbz").click(function(e){
	zTreeInit();
	/*wl添加  */
	$(e.target).addClass("kuBtnCurrent").siblings().removeClass("kuBtnCurrent");
	/*wl结束*/
	static_type = 0;
});
$("#sftc").click(function(e){
	zTreeInitTc();
	/*wl添加  */
	$(e.target).addClass("kuBtnCurrent").siblings().removeClass("kuBtnCurrent");
	/*wl结束*/
	static_type = 1;
});

function zTreeInit() {
	var search = $("#search").val();
    var zNodes;
    var url = '<%=contextPath%>/YZDictBLKAct/getSelectBlkTree.act?type=1&search='+search;
    $.axse(url, null,
    function(data) {
        zNodes = eval(data.tree);
    },
    function() {
        layer.alert('加载失败！' );
    });
    var setting = {
   		data: {
   			simpleData: {
   				enable:true,
   				idKey: "id",
   				pIdKey: "pId",
   				rootPId: "0"
   			}
   		},
   		view: {
			fontCss: getFont,
			nameIsHTML: true
		}
    };
    setting['callback'] = {
        onClick: onCheck
    };
    setting['check'] = {
        enable: false
    };
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
}

function zTreeInitTc() {
	var search = $("#search").val();
    var zNodes;
    var url = '<%=contextPath%>/YZDictBLKAct/getSelectBlkTree.act?type=0&search='+search;
    $.axse(url, null,
    function(data) {
        zNodes = eval(data.tree);
    },
    function() {
        layer.alert('加载失败！' );
    });
    var setting = {
       		data: {
       			simpleData: {
       				enable:true,
       				idKey: "id",
       				pIdKey: "pId",
       				rootPId: "0"
       			}
       		},
       		view: {
				fontCss: getFont,
				nameIsHTML: true
			}
        };
    setting['callback'] = {
        onClick: onCheck
    };
    setting['check'] = {
        enable: false
    };
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
}
function getFont(treeId, node) {
	return node.font ? node.font : {};
}
function onCheck(e, treeId, treeNode) {
    if (treeNode.isParent) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.expandNode(treeNode);
    } else {
    	if(treeNode.level == 0){
    		layer.alert('该分类下没有病历模板！'  );
    		return false;
    	}
        treeNode.check_Focus = true;
        treeNode.checked = true;
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        treeObj.updateNode(treeNode);
        var detailurl = '<%=contextPath%>/KQDS_BLKAct/selectDetail.act?seqId=' + treeNode.id;
        $.axse(detailurl, null,
        function(data) {
            mtype = data.data.mtype;
            getblcontent(data.data.seqId, data.data.mtype);
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}

// 获取增加行这种字段的内容
function getcontent(value, name, id) {
    var blcontent = "";
    if (value == "") {
        //展示  start
        blcontent += '<tr>' + '<th rowspan="2">' + name + '<br/></th>' + '<td width="30px" rowspan="2" colspan="1">1、</td>' + '<td style="text-align:center;" width="60px"></td>' + '<td style="text-align:center;" width="60px"></td>' + '<td rowspan="2" colspan="1"><div>' + '</div></td>' + '</tr>';
        blcontent += '<tr>' + '<td style="text-align:center;" ></td>' + '<td style="text-align:center;" ></td>' + '</tr>';
        //展示  end		
    } else {
        //1;2;4;3||撒东窗事发大概|||1;2;2;4||打折的v风格|||
        var arr = value.split("|||");
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].length > 0) {
                var arrone = arr[i].split("||");
                var ywarr = arrone[0].split(";");

                if (ywarr[0] == undefined) {
                    ywarr[0] = '';
                }
                if (ywarr[1] == undefined) {
                    ywarr[1] = '';
                }
                if (ywarr[2] == undefined) {
                    ywarr[2] = '';
                }
                if (ywarr[3] == undefined) {
                    ywarr[3] = '';
                }

                var content = arrone[1];
                if (content == undefined) {
                    content = '';
                }
                if (i > 0) {
                    name = '';
                }
                //展示  start
                blcontent += '<tr>' + '<th rowspan="2">' + name + '<br/></th>' + '<td width="30px" rowspan="2" colspan="1">' + (i + 1) + '、</td>' + '<td style="text-align:center;" width="60px">' + ywarr[0] + '</td>' + '<td style="text-align:center;" width="60px">' + ywarr[1] + '</td>' + '<td rowspan="2" colspan="1"><div>' + content + '</div></td>' + '</tr>';
                blcontent += '<tr>' + '<td style="text-align:center;" >' + ywarr[2] + '</td>' + '<td style="text-align:center;">' + ywarr[3] + '</td>' + '</tr>';
                //展示  end	
            }
        }
    }
    return blcontent;
}

// 子页面赋值
function getcontent2(value, name, id) {
    var blcontent = "";
    if (value == "") {
        //子页面赋值 start
        blcontent += '<tr>' + '<th rowspan="2">' + name + '<br/></th>' + '<td width="30px" rowspan="2" colspan="1" onclick="openSelectTooth(this);"><input type="text" value="1、"></td>' + '<td width="60px"><input type="text"  name="' + id + '_1" value=""></td>' + '<td width="60px"><input type="text"  name="' + id + '_1" value=""></td>' + '<td rowspan="2" colspan="1" onclick="czclick($(this),\'' + name + '\');"><textarea name="' + id + '_1" ></textarea></td>' + '</tr>';
        blcontent += '<tr>' + '<td><input type="text"  name="' + id + '_1" value=""></td>' + '<td><input type="text"  name="' + id + '_1" value=""></td>' + '</tr>';
        //子页面赋值 end			
    } else {
        //1;2;4;3||撒东窗事发大概|||1;2;2;4||打折的v风格|||
        var arr = value.split("|||");
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].length > 0) {
                var arrone = arr[i].split("||");
                var ywarr = arrone[0].split(";");
                var content = arrone[1];
                if (i > 0) {
                    name = '';
                }
                //子页面赋值 start
                blcontent += '<tr>' + '<th rowspan="2">' + name + '<br/></th>' + '<td width="30px" rowspan="2" colspan="1" onclick="openSelectTooth(this);"><input type="text" value="' + (i + 1) + '、"></td>' + '<td width="60px"><input type="text"  name="' + id + '_' + (i + 1) + '" value="' + ywarr[0] + '"></td>' + '<td width="60px"><input type="text"  name="' + id + '_' + (i + 1) + '" value="' + ywarr[1] + '"></td>' + '<td rowspan="2" colspan="1" onclick="czclick($(this),\'' + id + '\');"><textarea name="' + id + '_' + (i + 1) + '" >' + content + '</textarea></td>' + '</tr>';
                blcontent += '<tr>' + '<td><input type="text"  name="' + id + '_' + (i + 1) + '" value="' + ywarr[2] + '"></td>' + '<td><input type="text"  name="' + id + '_' + (i + 1) + '" value="' + ywarr[3] + '"></td>' + '</tr>';
                //子页面赋值 end
            }
        }
    }
    return blcontent;
}

// 取病历内容
function getblcontent(meid, mtype) {
    var detailurl = '<%=contextPath%>/KQDS_BLKAct/selectDetailContent.act?meid=' + meid + '&mtype=' + mtype;
    $.axse(detailurl, null,
    function(data) {
        var davalul = data.data;
        var blcontent = "";
        content = "";
        if (mtype == "0") { //初诊
            //展示  start
            blcontent += '<table id="cz" width="100%" class=" table-condensed table-bordered blmb"  data-height="450">';
            blcontent += '<tr height="20px"><th width="70px"><p style="margin-top: 0px; margin-bottom: 0px; padding: 0px; white-space: normal;">主诉</p></th><td colspan="4">' + davalul.czzs + '</td></tr>';
            blcontent += '<tr><th>现病史</th><td colspan="4">' + davalul.czxbs + '</td></tr>';
            blcontent += '<tr><th>既往史</th><td colspan="4">' + davalul.czjws + '</td></tr>';
            blcontent += '<tr><th>过敏史</th><td colspan="4">' + davalul.czgms + '</td></tr>';
            blcontent += '<tr><th>家族史</th><td colspan="4">' + davalul.czjzs + '</td></tr>';
            blcontent += '<tr><th>检验结果</th><td colspan="4">' + davalul.czjyjg + '</td></tr>';
            
            blcontent += getcontent(davalul.cztgjc, "体格检查", "cztgjc");
            blcontent += getcontent(davalul.czfzjc, "辅助检查", "czfzjc");
            
            blcontent += getcontent(davalul.czzd, "诊断", "czzd");
            blcontent += getcontent(davalul.czzlfa, "治疗方案", "czzlfa");
            blcontent += getcontent(davalul.czcl, "处理", "czcl");
            blcontent += '<tr><th>医嘱</th><td colspan="4">' + davalul.czyz + '</td></tr>';
            blcontent += '<tr><th>备注</th><td colspan="4">' + davalul.czremark + '</td></tr></table>';
            //展示  end		
            //子页面赋值 start
            content += '<tr height="20px"><th width="70px"><p style="margin-top: 0px; margin-bottom: 0px; padding: 0px; white-space: normal;">主诉</p></th><td colspan="4"><input type="text" name="czzs"  id="czzs" value="' + davalul.czzs + '"></td></tr>';
            content += '<tr><th>现病史</th><td colspan="4"><input type="text" name="czxbs"  id="czxbs" value="' + davalul.czxbs + '"></td></tr>';
            content += '<tr><th>既往史</th><td colspan="4"><input type="text" name="czjws"  id="czjws" value="' + davalul.czjws + '"></td></tr>';
            content += '<tr><th>过敏史</th><td colspan="4"><input type="text" name="czgms"  id="czgms" value="' + davalul.czgms + '"></td></tr>';
            content += '<tr><th>家族史</th><td colspan="4"><input type="text" name="czjzs"  id="czjzs" value="' + davalul.czjzs + '"></td></tr>';
            content += '<tr><th>检验结果</th><td colspan="4"><textarea name="czjyjg"  id="czjyjg" >' + davalul.czjyjg + '</textarea></td></tr>';
            content += getcontent2(davalul.cztgjc, "体格检查", "cztgjc");
            content += getcontent2(davalul.czfzjc, "辅助检查", "czfzjc");
            content += getcontent2(davalul.czzd, "诊断", "czzd");
            content += getcontent2(davalul.czzlfa, "治疗方案", "czzlfa");
            content += getcontent2(davalul.czcl, "处理", "czcl");
            content += '<tr><th>医嘱</th><td colspan="4"><textarea name="czyz"  id="czyz" >' + davalul.czyz + '</textarea></td></tr>';
            content += '<tr><th>备注</th><td colspan="4"><textarea name="czremark"  id="czremark" >' + davalul.czremark + '</textarea></td></tr>';
            //子页面赋值 end
        } else { //复诊
            //展示  start
            blcontent += '<table id="fz"  width="100%" class=" table-condensed table-bordered blmb"  data-height="450">';
            blcontent += '<tr height="20px"><th width="70px"><p style="margin-top: 0px; margin-bottom: 0px; padding: 0px; white-space: normal;">主诉</p></th><td colspan="4">' + davalul.fzzs + '</td></tr>';
            blcontent += '<tr><th>检验结果</th><td colspan="4">' + davalul.fzjyjg + '</td></tr>';

            blcontent += getcontent(davalul.fzjc, "检查", "fzfzjc");
            blcontent += getcontent(davalul.fzzd, "诊断", "fzzd");
            blcontent += getcontent(davalul.fzzlfa, "治疗方案", "fzzlfa");
            blcontent += getcontent(davalul.fzcl, "处理", "fzcl");

            blcontent += '<tr><th>医嘱</th><td colspan="4">' + davalul.fzyz + '</td></tr>';
            blcontent += '<tr><th>备注</th><td colspan="4">' + davalul.fzremark + '</td></tr>';

            blcontent += '</table>';
            //展示  end		
            //子页面赋值 start
            content += '<tr height="20px"><th width="70px"><p style="margin-top: 0px; margin-bottom: 0px; padding: 0px; white-space: normal;">主诉</p></th><td colspan="4"><input type="text"  name="fzzs"  id="fzzs" value="' + davalul.fzzs + '"></td></tr>';
            content += '<tr><th>检验结果</th><td colspan="4"><textarea name="fzjyjg"  id="fzjyjg" >' + davalul.fzjyjg + '</textarea></td></tr>';

            content += getcontent2(davalul.fzjc, "检查", "fzfzjc");
            content += getcontent2(davalul.fzzd, "诊断", "fzzd");
            content += getcontent2(davalul.fzzlfa, "治疗方案", "fzzlfa");
            content += getcontent2(davalul.fzcl, "处理", "fzcl");

            content += '<tr><th>医嘱</th><td colspan="4"><textarea name="fzyz"  id="fzyz" >' + davalul.fzyz + '</textarea></td></tr>';

            content += '<tr><th>备注</th><td colspan="4"><textarea name="fzremark"  id="fzremark" >' + davalul.fzremark + '</textarea></td></tr>';

            //子页面赋值 end
        }

        $("#blcontent").html(blcontent);
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
// 弹出一个iframe层
$('#parentIframe').on('click',
function() {
    if (content == "") {
        layer.alert('请选择病历模板！'  );
        return false;
    }
    parent.$("#tabIframe")[0].contentWindow.setcontent(mtype, content);
    var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(frameindex); //再执行关闭 */
});
</script>
</body>
</html>
