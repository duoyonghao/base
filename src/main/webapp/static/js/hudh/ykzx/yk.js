/**
 * 顶部按钮打开页面
 */
var topBtn = {
	typeManager : function() {
		layer.open({
			type : 2,
			title : '类别管理',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '95%', '95%' ],
			content : apppath + '/HUDH_YkzzViewAct/toTypeManger.act'
		});
	},
	manuManager : function() {
		layer.open({
			type : 2,
			title : '供应商管理',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '95%', '95%' ],
			content : apppath + '/HUDH_YkzzViewAct/toManuManger.act'
		});
	},
	manufacManager : function() {
		layer.open({
			type : 2,
			title : '生产商管理',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '95%', '95%' ],
			content : apppath + '/HUDH_YkzzViewAct/toManufacManger.act'
		});
	},
	drugsManager : function() {
		layer.open({
			type : 2,
			title : '药品添加',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '45%', '80%' ],
			content : apppath + '/HUDH_YkzzViewAct/toAddmedicines.act'
		});
	},
	updateManager : function() {
		// alert("修改药品");
		if (onclickrow == "") {
			layer.alert('请选择需修改的药品！');
			return false;
		}
		layer.open({
			type : 2,
			title : '修改药品',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '45%', '70%' ],
			content : apppath + '/HUDH_YkzzViewAct/toAddmedicines.act?seqId=' + selectId
		});
	},
	deleteManager : function() {
		// alert("删除药品");
		if (onclickrow == "") {
			layer.alert('请选择需删除的药品！');
			return false;
		} else if (onclickrow.drug_total_num > 0) {
			layer.alert('药品存在库存，不能删除！');
			return false;
		} else {
			layer.confirm('确定删除？', {
				btn : [ '删除', '放弃' ]
			// 按钮
			}, 
			function() {
				var url = contextPath + '/HUDH_YkzzAct/deleteDrugsByPrimaryId.act?';
				var id = selectId;
				//alert(id);
				var param = { id : id };
				$.axseSubmit(url, param, function() {
				}, function(r) {
					if (r.retState == "0") {
						layer.alert('删除成功', {
							end : function() {
								window.location.reload(); // 刷新父页面
								var frameindex = parent.layer.getFrameIndex(window.name);
								parent.layer.close(frameindex); // 再执行关闭
							}
						});
					} else {
						layer.alert('删除失败');
					}
				}, function() {
					layer.alert('删除失败');
				});
			});
		}
	},
	downloadTemplate : function() {
		//alert("下载模板");
		location.href = contextPath + "/HUDH_YkzzAct/excelDownloadStandardDrugsTemplateOut.act";
	},
	importDrugs : function() {
		layer.open({
			type : 2,
			title : '导入模板',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '20%', '20%' ],
			content : apppath + '/HUDH_YkzzViewAct/toUploadExcel.act' 
		});
		
		/*var url = contextPath + '/HUDH_YkzzAct/FileUploadAct';
		var formData = new FormData();
        formData.append("file",document.getElementById("upfile").files[0]);
		var param = { formData : formData };
		$.axseSubmit(url, param, function() {
		}, function(r) {
			
		}, function() {
			layer.alert('删除失败');
		});*/
		
		/*var formData = new FormData();
        formData.append("file",document.getElementById("upfile").files[0]);
        $.ajax({
            type: "GET", // 数据提交类型
            url: "/HUDH_YkzzAct/FileUploadAct", // 发送地址
            data: formData, //发送数据
            async: true, // 是否异步
            processData: false, //processData 默认为false，当设置为true的时候,jquery ajax 提交的时候不会序列化 data，而是直接使用data
            contentType: false //
        });*/
	},
	forbiddenManager : function() {
		if (onclickrow == "") {
			layer.alert('请选择需要停用的药品！');
			return false;
		}
		var url = contextPath + '/HUDH_YkzzAct/forbiddenDrugs.act?';
		var id = selectId;
		var param = { id : id };
		$.axseSubmit(url, param, function() {
		}, function(r) {
			if (r.retState == "0") {
				layer.alert('停用成功', {
					end : function() {
						window.location.reload(); // 刷新父页面
						var frameindex = parent.layer.getFrameIndex(window.name);
						parent.layer.close(frameindex); // 再执行关闭
					}
				});
			} else {
				layer.alert('停用失败');
			}
		}, function() {
			layer.alert('停用失败');
		});
	},
	recoverManager : function() {
		if (onclickrow == "") {
			layer.alert('请选择需要恢复停用的药品！');
			return false;
		}
		var url = contextPath + '/HUDH_YkzzAct/recoverDrugs.act?';
		var id = selectId;
		var param = { id : id };
		$.axseSubmit(url, param, function() {
		}, function(r) {
			if (r.retState == "0") {
				layer.alert('恢复停用成功', {
					end : function() {
						window.location.reload(); // 刷新父页面
						var frameindex = parent.layer.getFrameIndex(window.name);
						parent.layer.close(frameindex); // 再执行关闭
					}
				});
			} else {
				layer.alert('恢复停用失败');
			}
		}, function() {
			layer.alert('恢复停用失败');
		});
	}
}

var bottomBtn = {
	//药品入库
	drugsInWare : function (){
		layer.open({
			type : 2,
			title : '药品入库',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '95%', '95%' ],
			content : apppath + '/HUDH_YkzzViewAct/toInDrugs.act'
		});
	},
	//入库审核
	drugsInWareExamine : function (){
		layer.open({
			type : 2,
			title : '入库审核',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '95%', '95%' ],
			content : apppath + '/HUDH_YkzzViewAct/toDrugsExamine.act'
		});
	},
	//药品入库
	drugsWareInSearch : function (){
		layer.open({
			type : 2,
			title : '入库查询',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '95%', '95%' ],
			content : apppath + '/HUDH_YkzzViewAct/toYkInDrugsSearch.act'
		});
	},
	//收费单出库
	drugsCostWare : function (){
		layer.open({
			type : 2,
			title : '患者领药',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '95%', '95%' ],
			content : apppath + '/HUDH_YkzzViewAct/toCostDrugs.act'
		});
	},
	//发药记录
	drugsCostWareRecord : function (){
		layer.open({
			type : 2,
			title : '领药记录',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '95%', '95%' ],
			content : apppath + '/HUDH_YkzzViewAct/toYkCostRecode.act'
		});
	},
	//患者退药
	drugsCostReturn : function (){
		layer.open({
			type : 2,
			title : '患者退药',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '95%', '95%' ],
			content : apppath + '/HUDH_YkzzViewAct/toYkCostReturn.act'
		});
	},
	//退药明细
	drugsCostReturnDetail : function (){
		layer.open({
			type : 2,
			title : '退药明细',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '95%', '95%' ],
			content : apppath + '/HUDH_YkzzViewAct/toYkCostReturnDetail.act'
		});
	},
	//药品出库
	drugsOutWare : function (){
		layer.open({
			type : 2,
			title : '药品出库',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '95%', '95%' ],
			content : apppath + '/HUDH_YkzzViewAct/toOutDrugs.act'
		});
	},
	//药品出库查询
	drugsWareOutSearch : function (){
		layer.open({
			type : 2,
			title : '出库查询',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '95%', '95%' ],
			content : apppath + '/HUDH_YkzzViewAct/toYkOutDrugsSearch.act'
		});
	},
	//发药明细
	drugsCostWareDetail : function (){
		layer.open({
			type : 2,
			title : '发药明细',
			maxmin : true,
			shadeClose : true,
			// 点击遮罩关闭层
			area : [ '95%', '95%' ],
			content : apppath + '/HUDH_YkzzViewAct/toDugsExamineDetail.act'
		});
	}
}

//初始化一级类别
function initSelectBaseType($obj){
	$.ajax({
		url: apppath + "/HUDH_YkzzTypeAct/findTypeByLevel.act?level=1",
		type:"POST",
		dataType:"json",
		async : false,
		success:function(result){
			$($obj).append('<option value="">请选择</option>');
			if(result) {
				for(var i in result) {
					$($obj).append('<option value="'+result[i].id+'">'+result[i].type_name+'</option>');
				}
			}
		}
	});
}

//初始化一级类别
function initSelectNextType($obj,baseTypeId){
	$.ajax({
		url: apppath + "/HUDH_YkzzTypeAct/findChildTypesByParentId.act",
		type:"POST",
		dataType:"json",
		async : false,
		data : {"id":baseTypeId},
		success:function(result){
			if(result) {
				$obj.empty();
				$($obj).append('<option value="">请选择</option>');
				for(var i in result) {
					$($obj).append('<option value="'+result[i].id+'">'+result[i].type_name+'</option>');
				}
			}
		}
	});
}

//初始化供应商
function initSelectsupplier($obj){
	$.ajax({
		url: apppath + "/HUDH_YkzzManuAct/findAllManu.act",
		type:"POST",
		dataType:"json",
		data : {},
		success:function(result){
			if(result) {
				$obj.empty();
				$($obj).append('<option value="">请选择</option>');
				for(var i in result) {
					$($obj).append('<option value="'+result[i].id+'">'+result[i].manu_name+'</option>');
				}
			}
		}
	});
}

function gettimestr() {
    var today = new Date();
    var year = today.getFullYear();
    var month = (today.getMonth() + 1) < 10 ? '0' + (today.getMonth() + 1) : (today.getMonth() + 1);
    var day = (today.getDate()) < 10 ? '0' + (today.getDate()) : (today.getDate());
    var hours = (today.getHours()) < 10 ? '0' + (today.getHours()) : (today.getHours());
    var minutes = (today.getMinutes()) < 10 ? '0' + (today.getMinutes()) : (today.getMinutes());
    var seconds = (today.getSeconds()) < 10 ? '0' + (today.getSeconds()) : (today.getSeconds());
    var str = "JH" + year + month + today.getDate() + hours + minutes + seconds;
    return str;

}