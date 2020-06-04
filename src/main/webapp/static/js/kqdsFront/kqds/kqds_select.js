
/**
 * 根据List初始化下拉框
 */
function initSelectByList(id, list, opCode, opName, defaultVal) {
    var dict = $("#" + id);
    if (list != null && list.length > 0) {
        dict.empty();
        //dict.append("<option value=''>请选择</option>");
        for (var j = 0; j < list.length; j++) {
            var option = list[j];
            dict.append("<option value='" + option[opCode] + "'>" + option[opName] + "</option>");
        }
        if (defaultVal) {
            $(dict).val(defaultVal); // 默认选中
        }
    }
}
/**
 * 根据List初始化下拉框，包含 请选择
 */
function initSelectByListWithNull(id, list, opCode, opName, defaultVal) {
    var dict = $("#" + id);    
        dict.append("<option value=''>请选择</option>");
    if (list != null && list.length > 0) {
        dict.empty();
        dict.append("<option value=''>请选择</option>");
        for (var j = 0; j < list.length; j++) {
            var option = list[j];
            if(list.length == 1){
            	// dict.empty(); // modify by yangsen  18-01-08  这里不需要做这种优化处理
            	dict.append("<option selected='selected' value='" + option[opCode] + "'>" + option[opName] + "</option>");
            }else{
            	dict.append("<option value='" + option[opCode] + "'>" + option[opName] + "</option>");
            }
            
        }
        if (defaultVal) {
            $(dict).val(defaultVal); // 默认选中
        }
    }
}

/* #####################################下拉框初始化相关代码 start  // yangsen 17-5-4 ########################################*/

/*
 * 根据html元素的 class标签，初始化字典下拉框
 * id  下拉框的id
 * dictType 字典类型,比如 就诊分类  jzfl
 */
function initDictSelect(id, dictType) {
    var dict = $("#" + id);
    var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=" + dictType;
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

/**
 * 根据html元素的 class标签，初始化字典下拉框
 * 请求方式为 同步
 */
function initDictSelectByClassOrg(organization) {
    if ($(".dict").length > 0) {
        for (var i = 0; i < $(".dict").length; i++) {
            var dict = $(".dict").eq(i);
            // :eq() 选择器选取带有指定 index 值的元素。index值从 0 开始，所有第一个元素的 index 值是 0（不是1）。
            var type = dict.attr("tig");
            var url = contextPath + "/YZDictAct/getListByParentCodeOrg.act?parentCode=" + type;
            url += "&organization=" + organization;
            $.axse(url, null,
            function(data) {
                var list = data.list;
                if (list != null && list.length > 0) {
                    dict.empty();
                    dict.append("<option value=''>请选择</option>");
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                    }
                }
            },
            function() {
                layer.alert('查询出错！', {
                });
            });
        }
    }
}

/**
 * 根据html元素的 class标签，初始化字典下拉框
 * 请求方式为 同步
 */
function initDictSelectByClass(operFlag) {
    if ($(".dict").length > 0) {
        for (var i = 0; i < $(".dict").length; i++) {
            var dict = $(".dict").eq(i);
            // :eq() 选择器选取带有指定 index 值的元素。index值从 0 开始，所有第一个元素的 index 值是 0（不是1）。
            var type = dict.attr("tig");
            var url = contextPath + "/YZDictAct/getListByParentCode.act?parentCode=" + type;
            $.axse(url, null,
            function(data) {
                var list = data.list;
                if (list != null && list.length > 0) {
                    dict.empty();
                    dict.append("<option value=''>请选择</option>");
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                    }
                }

                if ('triggerChange' == operFlag) { // 触发Onchange事件 #### add by yangsen 
                    $(dict).trigger('change'); // 和 连锁代码片段的 $("#organization").change 配合使用
                }
            },
            function() {
                layer.alert('查询出错！'  );
            });
        }
    }
}

/**
 * 禁用的也展示
 * @param operFlag
 * @param organization
 */
function initDictSelectByClassALL(organization) {
    if ($(".dict").length > 0) {
        for (var i = 0; i < $(".dict").length; i++) {
            var dict = $(".dict").eq(i);
            // :eq() 选择器选取带有指定 index 值的元素。index值从 0 开始，所有第一个元素的 index 值是 0（不是1）。
            var type = dict.attr("tig");
            var url = contextPath + "/YZDictAct/getListByParentCodeALL.act?parentCode=" + type;
            url += "&organization=" + organization;
            $.axse(url, null,
            function(data) {
                var list = data.list;
                if (list != null && list.length > 0) {
                    dict.empty();
                    dict.append("<option value=''>请选择</option>");
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
                    }
                }
            },
            function() {
                layer.alert('查询出错！'  );
            });
        }
    }
}

/**
 * 禁用的也展示
 * @param operFlag
 * @param organization
 */
function initDeptSelectALL(organization) {
    if ($(".dept").length > 0) {
        for (var i = 0; i < $(".dept").length; i++) {
            var dept = $(".dept").eq(i);
            // :eq() 选择器选取带有指定 index 值的元素。index值从 0 开始，所有第一个元素的 index 值是 0（不是1）。
            var type = dept.attr("tig");
            var url = contextPath + "/YZDeptAct/getListALL.act";
            url += "?organization=" + organization;
            $.axse(url, null,
            function(data) {
                var list = data.list;
                if (list != null && list.length > 0) {
                	dept.empty();
                	dept.append("<option value=''>请选择</option>");
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dept.append("<option value='" + optionStr.seqId + "'>" + optionStr.deptName + "</option>");
                    }
                }
            },
            function() {
                layer.alert('查询出错！'  );
            });
        }
    }
}

/************************************************************************角色相关******************************************************************/

/**
 * 初始化角色下拉框
 */
function intPrivSelectByClass() {
    for (var i = 0; i < $(".priv").length; i++) {
        var dict = $(".priv").eq(i);
        var url = contextPath + "/YZPrivAct/getSlectUserPriv.act?1=1";
        var org = $(dict).attr("org"); // 门诊编号
        if(org){
        	url += "&organization=" + org;
        }
        $.axse(url, null,
        function(data) {
        	var list = data.retData;
            if (list != null && list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.privName + "</option>");
                }
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}

/**
 * 下拉角色，带公共角色
 */
function intPrivSelectByClassWithCommon() {
    for (var i = 0; i < $(".priv").length; i++) {
        var dict = $(".priv").eq(i);
        var url = contextPath + "/YZPrivAct/getSlectUserPrivWithCommon.act?1=1";
        var org = $(dict).attr("org"); // 门诊编号
        if(org){
        	url += "&organization=" + org;
        }
        $.axse(url, null,
        function(data) {
        	var list = data.retData;
            if (list != null && list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.privName + "</option>");
                }
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}

/**
 * 下拉角色，带公共角色search
 */
function intPrivSelectByClassWithSearch() {
    for (var i = 0; i < $(".priv").length; i++) {
        var dict = $(".priv").eq(i);
        var url = contextPath + "/YZPrivAct/getSlectUserPrivWithCommon.act?1=1";
        var org = $(dict).attr("org"); // 门诊编号
        if(org){
        	url += "&organization=" + org;
        }
        $.axse(url, null,
        function(data) {
        	var list = data.retData;
            if (list != null && list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.privName + "</option>");
                }
                dict.selectpicker("refresh");
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}

/************************************************************************系统用户相关******************************************************************/

// 根据角色初始化人员下拉框
function initPersonSelectByRole(userPriv, id) {
    var dict = $("#" + id);
    var url = contextPath + "/YZPersonAct/getPersonByRole.act?userPriv=" + userPriv;
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.userName + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}

// 根据可见人员 过滤
function initPersonSelectByRoleAndVisual(organization) {
    for (var i = 0; i < $(".priv").length; i++) {
        var dict = $(".priv").eq(i);
        var userPriv = $(dict).attr("tag");
        var url = contextPath + "/YZPersonAct/getPersonListByRoleAndVisual.act?userPriv=" + userPriv;
        if(organization){
        	url += "&organization=" + organization;
        }
        $.axse(url, null,
        function(data) {
            var list = data.list;
            if (list != null && list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.userName + "</option>");
                }
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}


//搜索select初始化医生、咨询--2019-10-18 licc
function initPersonSelectByDept(id, depttype) {
var dict = $("#" + id);    
var url = contextPath + "/YZPersonAct/getPersonListByDeptType.act?depttype=" + depttype;
$.axse(url, null,
function(data) {
    var list = data.list;
    if (list != null && list.length > 0) {
        dict.empty();
        $('#'+id).selectpicker('destroy');
         dict.append("<option value=''>请选择</option>");
        for (var j = 0; j < list.length; j++) {
            var optionStr = list[j];
            dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.userName + "</option>");
        }
        $("#"+id).selectpicker("refresh"); //select搜索初始化刷新
    }
},
function() {
    layer.alert('查询出错！' );
});
}

function initPersonSelectByDeptType(id, depttype) {
    var dict = $("#" + id);
    var url = contextPath + "/YZPersonAct/getPersonListByDeptType.act?depttype=" + depttype;
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.userName + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}
//可见人员过滤
function initPersonSelectByDeptTypeAndVisual(id, depttype, organization) {
    var dict = $("#" + id);
    var url = contextPath + "/YZPersonAct/getPersonListByDeptTypeAndVisual.act?depttype=" + depttype;
    if(organization){
    	url += "&organization=" + organization;
    }
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                if (list.length == 1) {
                    dict.append("<option value='" + optionStr.seqId + "' selected='selected'>" + optionStr.userName + "</option>");
                } else {
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.userName + "</option>");
                }
            }
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}

/************************************************************************部门相关******************************************************************/

/**
 * 根据部门类型初始化部门列表，可多个
 */
function intDeptListByDeptTypes(seqId, typeIds, organization) {
    if (!typeIds) {
        layer.alert('门诊编号参数为空，无法初始化科室下拉框！' );
        return false;
    }
    if (!organization) {
        layer.alert('门诊编号参数为空，无法初始化科室下拉框！' );
        return false;
    }
    var url = "yz/dept/act/YZDeptAct/getDeptListByDeptType.act?organization=" + organization;
    url += "&typeIds=" + typeIds;

    var dict = $("#" + seqId);
    var serverData = getDataFromServer(url);
    var list = serverData.list;
    if (list != null && list.length > 0) {
        dict.empty();
        dict.append("<option value=''>请选择</option>");
        for (var j = 0; j < list.length; j++) {
            var optionStr = list[j];
            dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.deptName + "</option>");
        }
    }
}

/**
 * 初始化就诊科室下拉框
 * 部门类型可多个，如 1,2
 */
function initDeptSelectByTypesAndClass(organization) {
    if (!organization) {
        layer.alert('门诊编号参数为空，无法初始化科室下拉框！' );
        return false;
    }

    for (var i = 0; i < $(".dept").length; i++) {
        var dict = $(".dept").eq(i);
        var depttype = $(dict).attr("tag");
        var url = "YZDeptAct/getDeptListByDeptType.act?organization=" + organization + "&typeIds=" + depttype;
        // 发送请求并获取数据
        var serverData = getDataFromServer(url);
        var list = serverData.list;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.deptName + "</option>");
            }
        }
    }
}

/**
 * 获取当前门诊的所有部门列表
 */
function getAllDeptByOrganization(organization) {
    for (var i = 0; i < $(".dept").length; i++) {
        var dict = $(".dept").eq(i);
        var url = contextPath + "/YZDeptAct/getAllDeptByOrganization.act?1=1";
        if (organization) {
            url += '&organization=' + organization;
        }
        $.axse(url, null,
        function(data) {
            var list = data.list;
            if (list != null && list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.deptName + "</option>");
                }
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}

/**
 * 获取当前门诊的所有部门列表 搜索select
 */
function getAllDeptByOrganizationSearch(organization) {
    for (var i = 0; i < $(".dept").length; i++) {
        var dict = $(".dept").eq(i);
        var url = contextPath + "/YZDeptAct/getAllDeptByOrganization.act?1=1";
        if (organization) {
            url += '&organization=' + organization;
        }
        $.axse(url, null,
        function(data) {
            var list = data.list;
            if (list != null && list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.deptName + "</option>");
                }
                dict.selectpicker("refresh");
            }
        },
        function() {
            layer.alert('查询出错！'  );
        });
    }
}

/************************************************************************收费相关******************************************************************/

/**
 * 获取收费项目一级分类，根据门诊编号
 */
function initCostSortSelect1LevelOrg(selectId,organization) {
    var dict = $("#" + selectId);
    var url = contextPath + "/YZDictCostAct/getLeve1SortListOrg.act?organization=" + organization;
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.dictCode + "'>" + optionStr.dictName + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！', {
        });
    });
}

/**
 * 获取收费项目二级分类，根据门诊编号
 * @param classno
 */
function initCostSortSelect2LevelOrg(selectId, parentCode,organization) {
    var dict = $("#" + selectId);
    var url = contextPath + "/YZDictCostAct/getLeve2SortListOrg.act?parentCode=" + parentCode + "&organization=" + organization;
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！', {
        });
    });
}


/**
 * 获取收费项目一级分类
 */
function initCostSortSelect1Level(selectId) {
    var dict = $("#" + selectId);
    var url = contextPath + "/YZDictCostAct/getLeve1SortList.act";
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.dictCode + "'>" + optionStr.dictName + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

/**
 * 获取收费醒目二级分类
 * @param classno
 */
function initCostSortSelect2Level(selectId, parentCode) {
    var dict = $("#" + selectId);
    var url = contextPath + "/YZDictCostAct/getLeve2SortList.act?parentCode=" + parentCode;
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

/**
 * 获取收费醒目二级分类
 * @param classno
 */
function initCostSortSelect2LevelSearch(selectId, parentCode) {
    var dict = $("#" + selectId);
    var url = contextPath + "/YZDictCostAct/getLeve2SortList.act?parentCode=" + parentCode;
    $.axse(url, null,
    function(data) {
    	if (data) {
		dict.empty();
        $('#'+selectId).selectpicker('destroy');
        dict.append("<option value=''>请选择</option>");
        var list = data.list;
        if (list != null && list.length > 0) {
            dict.empty();
            $('#'+selectId).selectpicker('destroy');
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.dictName + "</option>");
            }
            $("#" + selectId).selectpicker("refresh");
        }
    	}
    },
    function() {
        layer.alert('查询出错！'  );
    });
}


/**
 * 获取收费项目列表
 * @param basetype
 * @param nexttype
 */
function initSfxmSelect(selectid, basetype, nexttype) {
    var dict = $("#" + selectid);
    var url = contextPath + "/YZDictCostAct/getSfxmSelect.act?basetype=" + basetype + "&nexttype=" + nexttype;
    $.axse(url, null,
    function(data) {
        var list = data.list;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.treatitemno + "'>" + optionStr.treatitemname + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}



/**
 * @param id 下拉框id
 * @param usercode 用户编号   [该下拉框的option value值是 seqId]
 */
function intMemberCardListByUserCode(id, usercode) {
    var dict = $("#" + id);
    var url = contextPath + '/KQDS_MemberAct/getMemberCardListByUsercode.act?usercode=' + usercode;
    $.axse(url, null,
    function(data) {
        var list = data.data;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.memberno + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
/**
 * @param id 下拉框id
 * @param usercode 用户编号   [该下拉框的option value值是 seqId]
 */
function intMemberCardListByUserCodeSelect2(id, usercode) {
    var dict = $("#" + id);
    var url = contextPath + '/KQDS_MemberAct/getMemberCardListByUsercode.act?usercode=' + usercode;
    $.axse(url, null,
    function(data) {
        var list = data.data;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                if(optionStr.icno !=null && optionStr.icno!=""){
                	dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.icno + "</option>");
                }
            }
            dict.select2();
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}
/**
 * @param id 下拉框id
 * @param usercode 用户编号   [该下拉框的option value值是 memberno]
 */
function intMemberCardListByUserCode4No(id, usercode) {
    var dict = $("#" + id);
    var url = contextPath + '/KQDS_MemberAct/getMemberCardListByUsercode.act?usercode=' + usercode;
    $.axse(url, null,
    function(data) {
        var list = data.data;
        if (list != null && list.length > 0) {
            dict.empty();
            dict.append("<option value=''>请选择</option>");
            for (var j = 0; j < list.length; j++) {
                var optionStr = list[j];
                dict.append("<option value='" + optionStr.memberno + "'>" + optionStr.memberno + "</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}

/**
 * 查询加工厂，后台管理中心使用
 * @param selectId
 * @param isAdd 如果isAdd有值，说明是新增页面； 目前后台的患者来源有启用、禁用功能，新增页面，查询的列表不包含被禁用的信息
 */
function initFactorySelect4Back(selectId, isAdd, organization) {
    var dict = $("#" + selectId);
    var url = contextPath + '/KQDS_OutProcessing_UnitAct/getUnitList4Back.act?1=1';
    if (isAdd) {
        url += '&isAdd=1';
    }

    if (organization) {
        url += '&organization=' + organization;
    }
    $.axse(url, null,
    function(data) {
        var list = data.data;
        if (list != null) {
            if (list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.code + "'>" + optionStr.name + "</option>");
                }
            } else {
                dict.empty();
                dict.append("<option value=''>-- 暂无加工厂 --</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！', {
        });
    });
}

/**
 * 查询加工厂
 * @param selectId
 * @param isAdd 如果isAdd有值，说明是新增页面； 目前后台的患者来源有启用、禁用功能，新增页面，查询的列表不包含被禁用的信息
 */
function initFactorySelect(selectId, isAdd, organization) {
    var dict = $("#" + selectId);
    var url = contextPath + '/KQDS_OutProcessing_UnitAct/getUnitList.act?1=1';
    if (isAdd) {
        url += '&isAdd=1';
    }

    if (organization) {
        url += '&organization=' + organization;
    }
    $.axse(url, null,
    function(data) {
        var list = data.data;
        if (list != null) {
            if (list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.code + "'>" + optionStr.name + "</option>");
                }
            } else {
                dict.empty();
                dict.append("<option value=''>-- 暂无加工厂 --</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}
/**
 * 付款方式下拉
 * 请求方式为 同步
 */
function initFkfsSelect(selectId) {
	var dict = $("#" + selectId);
    var url = contextPath + '/YZFkfsAct/getFkfsList.act?1=1';
    $.axse(url, null,
    function(data) {
        var list = data.data;
        if (list != null) {
            if (list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.payname + "</option>");
                    //预交金Id
                    if(optionStr.ismoney == '2'){
                    	yjjId = optionStr.seqId;
                    }
                }
            } else {
                dict.empty();
                dict.append("<option value=''>-- 暂无收费方式 --</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}
/**
 * 会员卡充值付款方式下拉
 * 请求方式为 同步
 */
function initMemberFkfsSelect(selectId) {
	var dict = $("#" + selectId);
    var url = contextPath + '/YZFkfsAct/getMemberFkfsList.act?1=1';
    $.axse(url, null,
    function(data) {
        var list = data.data;
        if (list != null) {
            if (list.length > 0) {
                dict.empty();
                dict.append("<option value=''>请选择</option>");
                for (var j = 0; j < list.length; j++) {
                    var optionStr = list[j];
                    dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.payname + "</option>");
                }
            } else {
                dict.empty();
                dict.append("<option value=''>-- 暂无收费方式 --</option>");
            }
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}
/* #####################################下拉框初始化相关代码 END  // yangsen 17-9-3 ########################################*/