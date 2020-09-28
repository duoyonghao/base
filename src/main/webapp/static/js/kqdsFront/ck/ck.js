//添加商品
function add() {
    layer.open({
        type: 2,
        title: '添加商品',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['520px', '390px'],
        content: contextPath + '/KQDS_Ck_GoodsAct/toSave.act?sshouse=' + sshouse
    });
    /*var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    //console.log("树========="+treeObj);
    if (treeObj.getSelectedNodes().length == 0 && treeObj.getSelectedNodes() == 'null') {
        layer.alert('请选择末级类别' );
        return;
    }
    if (treeObj.getSelectedNodes().length != 0) {
    //console.log("id====="+treeNode.getParentNode().getParentNode());
    var treeNode = treeObj.getSelectedNodes()[0];
    //console.log("id====="+JSON.stringify(treeNode));
    if(treeNode.children!=""){
    	//var sshouse = treeNode.getParentNode().getParentNode().id;
    }
    if (treeNode.isParent && treeNode.pId == null) { //仓库
        layer.alert('请选择末级类别' );
        return;
    } else if (!treeNode.isParent) { //没有下级分类--叶子节点
        var parentNode2 = treeNode.getParentNode();
        var parentNode1 = parentNode2.getParentNode();
        if (parentNode1 == null) { //一级分类
            layer.alert('请选择末级类别'  );
            return;
        } else { //二级分类
        	//console.log("最末级类型Id="+treeNode);
            goodstype = treeNode.id;
            goodstypename = treeNode.name;
            sshouse = parentNode1.id;
            sshousename = parentNode1.name;
        }
    } else { //一级分类
        layer.alert('请选择末级类别' );
        return;
    }
    layer.open({
        type: 2,
        title: '添加商品',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['520px', '360px'],
        content: contextPath + '/KQDS_Ck_GoodsAct/toSave.act?sshouse=' + sshouse + '&&goodstype=' + goodstype
    });
    }else{
    	layer.alert('请选择末级类别' );
        return;
    }*/
}

//批量添加商品   2020/03/28 lutian
function addBatch() {
    layer.open({
        type: 2,
        title: '批量添加商品',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['99%', '450px'],
        content: contextPath + '/KQDS_Ck_GoodsAct/toCkAddBatchGood.act'
    });
}


//修改商品
function edit() {
//	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
//    if (treeObj.getSelectedNodes().length == 0) {
//        layer.alert('请选择末级类别' );
//        return;
//    }
    // var treeNode = treeObj.getSelectedNodes()[0];
    //  var sshouse = treeNode.getParentNode().getParentNode().id;
    if (onclickrow == "") {
        layer.alert('请选择需修改的商品！');
        return false;
    }
    layer.open({
        type: 2,
        title: '修改商品',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        //area: ['520px', '360px'],
        area: ['520px', '390px'],
        content: contextPath + '/KQDS_Ck_GoodsAct/toEdit.act?seqId=' + onclickrow.seqId + '&sshouse=' + sshouse
    });
}

//刪除商品
function del() {
    if (onclickrow == "") {
        layer.alert('请选择需删除的商品！');
        return false;
    } else if (onclickrow.nums > 0) {
        layer.alert('商品存在库存，不能删除！');
        return false;
    } else {

        layer.confirm('确定删除？', {
                btn: ['删除', '放弃'] //按钮
            },
            function () {
                var url = contextPath + '/KQDS_Ck_Goods_DetailAct/deleteObj.act?seqId=' + onclickrow.seqId;
                $.axse(url, null,
                    function (data) {
                        if (data.retState == "0") {
                            layer.alert('删除成功');
                            refresh();
                        }
                    },
                    function () {
                        layer.alert(data.retMsrg, {});
                    });
            });
    }
}

//商品资料
function searchGoodsDetail() {
    layer.open({
        type: 2,
        title: '商品资料',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['90%', '90%'],
        content: contextPath + '/KQDS_Ck_Goods_DetailAct/toGoodsDetailSearch.act'
    });
}

//供应商按钮
function supplier() {
    layer.open({
        type: 2,
        title: '供应商信息',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '320px'],
        content: contextPath + '/KQDS_Ck_SupplierAct/toSupplier.act'
    });
}

//添加分类
function addgoodstype() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    if (treeObj.getSelectedNodes().length == 0) {
        layer.alert('请选择分类');
        return;
    }
    //console.log("Id="+JSON.stringify(treeObj.getSelectedNodes()));
    var treeNode = treeObj.getSelectedNodes()[0];
    var perid = "",
        pername = "";
    //console.log(JSON.stringify(treeNode)+"=需要数据");
    if (treeNode.isParent && treeNode.pId == null) { // 仓库
        //console.log("第一层判断。。。");
        //perid = "0";
        perid = treeNode.id;
        pername = treeNode.name;
    } else if (!treeNode.isParent) { // 没有下级分类--叶子节点
        var parentNode2 = treeNode.getParentNode();
        if (parentNode2 == null) { //仓库
            //console.log("第二层判断。。。");
            //perid = "0";
            perid = treeNode.id;
            pername = treeNode.name;
        } else {
            var parentNode1 = parentNode2.getParentNode();
            if (parentNode1 == null) { //一级分类
                perid = treeNode.id;
                pername = treeNode.name;
            } else {
                layer.alert('已是末级类别！', {});
                return;
            }
        }
    } else { // 一级分类
        perid = treeNode.id;
        pername = treeNode.name;
    }

    layer.open({
        type: 2,
        title: '添加类别',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['550px', '250px'],
        content: contextPath + '/KQDS_Ck_GoodstypeAct/toSave.act?perid=' + perid + '&pername=' + pername
    });
}

//修改分类
function editgoodstype() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    if (treeObj.getSelectedNodes().length == 0) {
        layer.alert('请选择分类');
        return;
    }
    var treeNode = treeObj.getSelectedNodes()[0];
    var seqId = "",
        pername = "";
    if (treeNode.isParent && treeNode.pId == null) { //仓库
        layer.alert('请选择分类');
        return;
    } else if (!treeNode.isParent) { // 没有下级分类--叶子节点
        var parentNode2 = treeNode.getParentNode();
        var parentNode1 = parentNode2.getParentNode();
        if (parentNode1 == null) { // 一级分类
            seqId = treeNode.id;
            pername = parentNode2.name;
        } else {
            seqId = treeNode.id;
            pername = parentNode2.name;
        }
    } else { // 一级分类
        var ck = treeNode.getParentNode();
        seqId = treeNode.id;
        pername = ck.name;
    }

    layer.open({
        type: 2,
        title: '修改类别',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['550px', '250px'],
        content: contextPath + '/KQDS_Ck_GoodstypeAct/toSave.act?seqId=' + seqId + '&pername=' + pername
    });
}

//删除分类
function delgoodstype() {
    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    if (treeObj.getSelectedNodes().length == 0) {
        layer.alert('请选择分类');
        return;
    }
    var treeNode = treeObj.getSelectedNodes()[0];
    var seqId = "";
    if (treeNode.isParent && treeNode.pId == null) { //仓库
        layer.alert('请选择分类');
        return;
    } else { //没有下级分类--叶子节点
        seqId = treeNode.id;
    }
    // 改分类下存在商品不能删除
    if (!delleteGoodsYz(seqId)) {
        layer.alert('该分类下（包括其他仓库）存在商品不能删除');
        return;
    }

    layer.confirm('确定删除分类及子分类？', {
            btn: ['删除', '放弃'] //按钮
        },
        function () {
            var url = contextPath + '/KQDS_Ck_GoodstypeAct/delete.act?seqId=' + seqId;
            msg = layer.msg('加载中', {
                icon: 16
            });
            $.axse(url, null,
                function (data) {
                    if (data.retState == "0") {
                        layer.alert('删除成功', {});
                        zTreeInit();
                        refreshCk();
                    }
                },
                function () {
                    layer.alert('删除失败！');
                });
        });
}

//点击出库部门
function ckdept() {
    layer.open({
        type: 2,
        title: '出库部门',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '320px'],
        content: contextPath + '/KQDS_Ck_CkdeptAct/toCkDept.act'
    });
}

//清空仓库
function ckempty() {
    layer.confirm('确定清空仓库所有数据？', {
            btn: ['确定', '放弃'] //按钮
        },
        function () {
            var radomNum = sixRandom();
            layer.prompt({
                    title: '请输入操作确认码：' + radomNum,
                    formType: 0
                },
                function (inputNumber, index) {
                    if (inputNumber != radomNum) {
                        layer.alert('确认码输入错误！');
                        return false;
                    }

                    var url = contextPath + '/KQDS_Ck_HouseAct/emptyCK.act';
                    $.axse(url, null,
                        function (data) {
                            if (data.retState == "0") {
                                layer.alert('操作成功', {

                                    end: function () {
                                        window.location.reload();
                                    }
                                });
                            }
                        },
                        function () {
                            layer.alert('清空失败！');
                        });
                });
        });


}

//点击仓库按钮
function house() {
    layer.open({
        type: 2,
        title: '仓库信息',
        maxmin: true,
        shadeClose: true,
        //点击遮罩关闭层
        area: ['800px', '320px'],
        content: contextPath + '/KQDS_Ck_HouseAct/toCkHouse.act'
    });
}

function delleteGoodsYz(goodstype) {
    var flag = true;
    var url = contextPath + '/KQDS_Ck_Goods_DetailAct/delleteGoodsYz.act?goodstype=' + goodstype;
    $.axse(url, null,
        function (r) {
            flag = r.data;
        },
        function () {
            layer.alert('验证失败');
        });
    return flag;
}

//仓库
function getHouseSelect(name) {
    var dict = $("#" + name);
    var url = contextPath + "/KQDS_Ck_HouseAct/selectList.act";
    $.axse(url, null,
        function (data) {
            var list = data;
            if (list != null) {
                if (list.length > 0) {
                    dict.empty();
                    dict.append("<option value=''>请选择</option>");
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        if (list.length == 1) {
                            dict.append("<option value='" + optionStr.seqId + "' selected = 'selected'>" + optionStr.housename + "</option>");
                        } else {
                            dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.housename + "</option>");
                        }
                    }
                }
            }
        },
        function () {
            layer.alert('查询出错！');
        });
}

//出库部门
function getDeptSelect(name) {
    var dict = $("#" + name);
    var url = contextPath + "/KQDS_Ck_CkdeptAct/selectList.act";
    $.axse(url, null,
        function (data) {
            var list = data;
            if (list != null) {
                if (list.length > 0) {
                    dict.empty();
                    dict.append("<option value=''>请选择</option>");
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        if (list.length == 1) {
                            dict.append("<option value='" + optionStr.seqId + "' selected = 'selected'>" + optionStr.deptname + "</option>");
                        } else {
                            dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.deptname + "</option>");
                        }
                    }
                }
            }
        },
        function () {
            layer.alert('查询出错！');
        });
}

//类别
function getTypeSelect(name, id) {
    var dict = $("#" + name);
    var url = contextPath + "/KQDS_Ck_GoodstypeAct/selectList.act?perid=" + id;
    $.axse(url, null,
        function (data) {
            var list = data;
            if (list != null) {
                if (list.length > 0) {
                    dict.empty();
                    dict.append("<option value=''>请选择</option>");
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.typename + "</option>");
                    }
                }
            }
        },
        function () {
            layer.alert('查询出错！');
        });
}

//一级类别
function getBaseTypeSelect(name, sshouseid) {
    var dict = $("#" + name);
    var url = contextPath + "/KQDS_Ck_GoodstypeAct/getBaseTypeSelect.act?sshouseid=" + sshouseid;
    $.axse(url, null,
        function (data) {
            var list = data;
            //console.log("一级数据="+JSON.stringify(data));
            if (list != null) {
                if (list.length > 0) {
                    dict.empty();
                    dict.append("<option value=''>请选择</option>");
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.goodstype + "</option>");
                    }
                }
            }
        },
        function () {
            layer.alert('查询出错！');
        });
}

//二级类别
function getNextTypeSelect(name, baseid) {
    var dict = $("#" + name);
    var url = contextPath + "/KQDS_Ck_GoodstypeAct/getNextTypeSelect.act?baseid=" + baseid;
    $.axse(url, null,
        function (data) {
            var list = data;
            //console.log("二级数据="+JSON.stringify(data));
            if (list != null) {
                if (list.length > 0) {
                    dict.empty();
                    dict.append("<option value=''>请选择</option>");
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.goodstype + "</option>");
                    }
                }
            }
        },
        function () {
            layer.alert('查询出错！');
        });
}

//供货商
function getSupplierSelect(name) {
    var dict = $("#" + name);
    var url = contextPath + "/KQDS_Ck_SupplierAct/selectList.act";
    $.axse(url, null,
        function (data) {
            var list = data;
            if (list != null) {
                if (list.length > 0) {
                    dict.empty();
                    dict.append("<option value=''>请选择</option>");
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.suppliername + "</option>");
                    }
                }
            }
        },
        function () {
            layer.alert('查询出错！');
        });
}

//供货商
function getSupplierSelect2(name) {
    var dict = $("#" + name);
    var url = contextPath + "/KQDS_Ck_SupplierAct/selectList.act";
    $.axse(url, null,
        function (data) {
            var list = data;
            if (list != null) {
                if (list.length > 0) {
                    dict.empty();
                    // dict.append("<option value=''>请选择</option>");
                    var data = [];
                    data.push({id: '', text: '', title: '请选择'});
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        //dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.suppliername + "</option>");
                        var suppliername = optionStr.suppliername;
                        if (suppliername.length > 6) {
                            suppliername = suppliername.substring(0, 6) + "...";
                        }
                        var para = {
                            id: optionStr.seqId,
                            text: suppliername,
                            title: optionStr.suppliername
                        }
                        data.push(para);
                    }
                    //dict.select2();
                    dict.select2({
                        data: data,
                        placeholder: '请选择',
                        allowClear: true
                    })
                }
            }
        },
        function () {
            layer.alert('查询出错！', {
                icon: 6
            });
        });
}

//所在科室
function getSupplierSelectKeshi(name) {
    var dict = $("#" + name);
    var url = contextPath + "/KQDS_Ck_CkdeptAct/selectList.act";
    //var url = contextPath + "/KQDS_Ck_SupplierAct/selectList.act";
    $.axse(url, null,
        function (data) {
            var list = data;
            if (list != null) {
                if (list.length > 0) {
                    dict.empty();
                    // dict.append("<option value=''>请选择</option>");
                    var data = [];
                    data.push({id: '', text: '', title: '请选择'});
                    for (var j = 0; j < list.length; j++) {
                        var optionStr = list[j];
                        //dict.append("<option value='" + optionStr.seqId + "'>" + optionStr.suppliername + "</option>");
                        var deptname = optionStr.deptname;
                        if (deptname.length > 6) {
                            deptname = deptname.substring(0, 7) + "...";
                        }
                        var para = {
                            id: optionStr.seqId,
                            text: deptname,
                            title: optionStr.deptname
                        }
                        data.push(para);
                    }
                    //dict.select2();
                    dict.select2({
                        data: data,
                        placeholder: '请选择',
                        allowClear: true
                    })
                }
            }
        },
        function () {
            layer.alert('查询出错！', {
                icon: 6
            });
        });
}

//收货仓库
function getHouse(seqId) {
    var housename = "";
    var detailurl = contextPath + '/KQDS_Ck_HouseAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
        function (data) {
            housename = data.data.housename;
        },
        function () {
            layer.alert('查询出错！');
        });
    return housename;
}

//供应商
function getSupplier(seqId) {
    var suppliername = "";
    var detailurl = contextPath + '/KQDS_Ck_SupplierAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
        function (data) {
            if (data.data != null) {
                suppliername = data.data.suppliername;
            }
        },
        function () {
            layer.alert('查询出错！');
        });
    return suppliername;
}

//根据商品编号查询商品
function getGoodsname(seqIds) {
    var suppliername = "";
    var detailurl = contextPath + '/KQDS_Ck_Goods_DetailAct/selectDetailAll.act?seqIds=' + seqIds;
    $.axse(detailurl, null,
        function (data) {
            if (data.data != null) {
                suppliername = data.data;
            }
        },
        function () {
            layer.alert('查询出错！');
        });
    return suppliername;
}

//根据商品类别编号查询商品类别
function getGoodsType(seqId) {
    var googtype;
    var detailurl = contextPath + '/KQDS_Ck_GoodstypeAct/selectDetail.act?seqId=' + seqId;
    $.axse(detailurl, null,
        function (data) {
            if (data.data != null) {
                googtype = data.data;
            }
        },
        function () {
            layer.alert('查询出错！');
        });
    return googtype;
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

//报警提醒
function saveGoodsGjTx() {
    var detailurl = contextPath + '/KQDS_Ck_Goods_DetailAct/saveGoodsGjTx.act';
    $.axseY(detailurl, null, function (data) {
    }, function () {
    });
}

//商品导入，附件上传初始化
function yxzl() {
    /******************************************上传文件start******************************************************/
    //初始化方法，填入页面隐藏的 附件id的id值 
    /*initParam("", "");*/
    uploadfile(contextPath + "/FileUploadAct/uploadFile.act?module=evidence");
    /******************************************上传文件end******************************************************/
}

//模板下载
function mbxz() {
    location.href = contextPath + "/KQDS_Ck_Goods_DetailAct/excelStandardTemplateOut.act";
}

//计算主体的宽度
function setWidth() {
    var innerHeight_1, innerHeight_2;
    (innerHeight_1 = $('.operateModel .optBox:eq(0)').height()) > (innerHeight_2 = $('.operateModel .optBox:eq(1)').height()) ? $('.operateModel .optBox:eq(1)').height(innerHeight_1) : $('.operateModel .optBox:eq(0)').height(innerHeight_2);
}

//计算左侧表格高度保证一屏展示
function setHeight() {
    var baseHeight = $(window).height() - 15,
        boxhAreaHeight = $('.formBox').outerHeight();

    $(".costWrap").height(baseHeight - boxhAreaHeight - 10);
    $(".costWrap .costBd").height($(".costWrap").height() - $(".costWrap .costHd").height());
    $(".costWrap .costBd .ztreeWrap").height($(".costWrap .costBd").height());
    $(".costWrap .costBd .ztreeWrap .ztree").height($(".costWrap .costBd .ztreeWrap").height() - 43); // 30 是搜索框的高度
    $(".tableBox").height($(".costWrap .costBd").height()); //设置了talbe的父元素高度
    $(".fixed-table-container").height($(".costWrap .costBd").height());
}

function getButtonPower() {
    var menubutton1 = "",
        menubuttonexp = "",
        menubutton2 = "",
        menubutton3 = "",
        menubutton_cklb = "";
    for (var i = 0; i < listbutton.length; i++) {
        if (listbutton[i].qxName == "addgoodstype") {
            menubutton_cklb += '<label id="addgoodstype" onclick="addgoodstype()" style="cursor:pointer">添加类别</label>';
        } else if (listbutton[i].qxName == "editgoodstype") {
            menubutton_cklb += '<label id="editgoodstype" onclick="editgoodstype()" style="cursor:pointer">修改类别</label>';
        } else if (listbutton[i].qxName == "delgoodstype") {
            menubutton_cklb += '<label id="delgoodstype" onclick="delgoodstype()" style="cursor:pointer">删除类别</label>';
        } else if (listbutton[i].qxName == "add") {
            menubutton1 += '<label id="add" onclick="add()" style="cursor:pointer">添加商品</label>';
        } else if (listbutton[i].qxName == "ck_baseinfo_sppltj") {
            menubutton1 += '<label id="addBatch" onclick="addBatch()" style="cursor:pointer">批量添加商品</label>';  //2020/03/28	 批量添加商品    lutian
        } else if (listbutton[i].qxName == "edit") {
            menubutton1 += '<label id="edit" onclick="edit()" style="cursor:pointer">修改商品</label>';
        } else if (listbutton[i].qxName == "del") {
            menubutton1 += '<label id="del" onclick="del()" style="cursor:pointer">删除商品</label>';
            menubutton1 += '<label id="del" onclick="searchGoodsDetail()" style="cursor:pointer">商品资料</label>';
        } else if (listbutton[i].qxName == "open") {
            /*menubutton1 += '<label class="btns" onclick="unallowable(0)">启用</label>';*/
            menubutton1 += '<label class="btns" onclick="unallowable(1)">禁用</label>';
        } else if (listbutton[i].qxName == "house") {
            menubutton1 += '<label id="house" onclick="house()" style="cursor:pointer">仓库</label>';
        } else if (listbutton[i].qxName == "supplier") {
            menubutton1 += '<label id="supplier" onclick="supplier()" style="cursor:pointer">供应商</label>';
        } else if (listbutton[i].qxName == "ckdept") {
            menubutton1 += '<label id="ckdept" onclick="ckdept()" style="cursor:pointer">出库部门</label>';
        } else if (listbutton[i].qxName == "ckempty") {
            menubutton_cklb += '<label id="ckempty" onclick="ckempty()" style="cursor:pointer">清空仓库</label>';
        } else if (listbutton[i].qxName == "ck_dbck") {
            menubuttonexp += '<label id="del" onclick="allocatingOutbound()" style="cursor:pointer">调拨出库</label>';
        }  else if (listbutton[i].qxName == "mbxz") {
            menubuttonexp += '<label><a id="mbxz" onclick="mbxz();" class="webuploader-pick" style="cursor:pointer">模板下载</a></label>';
        } else if (listbutton[i].qxName == "sqdr") {
            menubuttonexp += '<label>' + '<input type="hidden" placeholder="" id="imgtype" name="imgtype" value="goods">' + '<div id="uploader-demo">	' + '<div id="fileList" class="uploader-list"></div>' + '<div id="filePicker">商品导入</div>' + '</div></label>';
        } else if (listbutton[i].qxName == "jhsqd") {
            menubutton2 += '<li name="jhsqd"><img src="' + contextPath + '/static/image/kqdsFront/img/icon/book2.png">商品入库</li>';
        } else if (listbutton[i].qxName == "rksh") {//添加入库审核
            menubutton2 += '<li name="rksh"><img src="' + contextPath + '/static/image/kqdsFront/img/icon/book2.png">入库审核</li>';//添加入库审核
        } else if (listbutton[i].qxName == "jhcx") {
            menubutton2 += '<li name="jhcx"><img src="' + contextPath + '/static/image/kqdsFront/img/icon/book2.png">入库查询</li>';
        } else if (listbutton[i].qxName == "jhmx") {
            menubutton2 += '<li name="jhmx"><img src="' + contextPath + '/static/image/kqdsFront/img/icon/book2.png">入库明细</li>';
        } else if (listbutton[i].qxName == "dqkc") {
            menubutton3 += '<li name="dqkc"><img src="' + contextPath + '/static/image/kqdsFront/img/icon/book2.png">当前库存</li>';
        } else if (listbutton[i].qxName == "lysqd") {
            menubutton2 += '<li name="lysqd"><img src="' + contextPath + '/static/image/kqdsFront/img/icon/book2.png">商品出库</li>';
        } else if (listbutton[i].qxName == "lycx") {
            menubutton2 += '<li name="lycx"><img src="' + contextPath + '/static/image/kqdsFront/img/icon/book2.png">出库查询</li>';
        } else if (listbutton[i].qxName == "lymx") {
            menubutton2 += '<li name="lymx"><img src="' + contextPath + '/static/image/kqdsFront/img/icon/book2.png">出库明细</li>';
        } else if (listbutton[i].qxName == "sfccx") {
            menubutton3 += '<li name="sfccx"><img src="' + contextPath + '/static/image/kqdsFront/img/icon/book2.png">收发存查询</li>';
        } else if (listbutton[i].qxName == "gyscx") {
            menubutton3 += '<li name="gyscx"><img src="' + contextPath + '/static/image/kqdsFront/img/icon/book2.png">供应商查询</li>';
        } else if (listbutton[i].qxName == "bj_search") {
            menubutton3 += '<li name="bj_search"><img src="' + contextPath + '/static/image/kqdsFront/img/icon/book2.png">报警查询</li>';
        } else if (listbutton[i].qxName == "ck_ksll") {
            menubutton3 += '<li name="ck_ksll"><img src="' + contextPath + '/static/image/kqdsFront/img/icon/book2.png">科室领料</li>';
        } else if (listbutton[i].qxName == "ck_ksth") {
            menubutton3 += '<li name="ck_ksth"><img src="' + contextPath + '/static/image/kqdsFront/img/icon/book2.png">科室退货</li>';
        }
    }
    $("#cklb").append(menubutton_cklb);
    $("#jcxx").append(menubutton1);
    $("#jcxx").append(menubuttonexp);
    $("#ul1").append(menubutton2);
    $("#ul1").append(menubutton3);
    yxzl(); // 商品导入功能，涉及附件上传
    //计算主体的宽度，需要在页面加载完成后，再执行计算操作，否则页面会出问题
    setWidth();
    setHeight();
}

//layer打开弹窗
$('.operateModel .optBox').on('click', 'li',
    function () {
        var name = $(this).attr('name');
        switch (name) {
            case 'jhsqd': {
                layer.open({
                    type: 2,
                    title: '商品入库',
                    shade: 0.6,
                    area: ['97%', '90%'],
                    content: contextPath + '/KQDS_Ck_Goods_InAct/toCkGoodsIn.act'
                });
                break;
            }
            case 'lysqd': {
                layer.open({
                    type: 2,
                    title: '商品出库',
                    shade: 0.6,
                    area: ['90%', '90%'],
                    content: contextPath + '/KQDS_Ck_Goods_OutAct/toCkGoodsOut.act'
                });
                break;
            }
            case 'lycx': {
                layer.open({
                    type:
                        2,
                    title: '出库查询',
                    shade: 0.6,
                    area: ['90%', '90%'],
                    content: contextPath + '/KQDS_Ck_Goods_OutAct/toCkOutGoodsSearch.act'
                });

                break;
            }
            case 'jhcx': {
                layer.open({
                    type:
                        2,
                    title: '入库查询',
                    shade: 0.6,
                    area: ['90%', '90%'],
                    content: contextPath + '/KQDS_Ck_Goods_InAct/toCkInGoodsSearch.act'
                });

                break;
            }
            case 'sfccx': {
                layer.open({
                    type:
                        2,
                    title: '收发存查询',
                    maxmin: true,
                    shadeClose: false,
                    area: ['90%', '90%'],
                    content: contextPath + '/KQDS_Ck_GoodsAct/toCkSfcSearch.act'
                });
                break;
            }
            case 'jhmx': {
                layer.open({
                    type:
                        2,
                    title: '入库明细',
                    maxmin: true,
                    shadeClose: false,
                    area: ['90%', '90%'],
                    content: contextPath + '/KQDS_Ck_Goods_In_DetailAct/toCkInGoodsDetailSearch.act'
                });
                break;
            }
            case 'lymx': {
                layer.open({
                    type:
                        2,
                    title: '出库明细',
                    maxmin: true,
                    shadeClose: false,
                    area: ['90%', '90%'],
                    content: contextPath + '/KQDS_Ck_Goods_Out_DetailAct/toCkOutGoodsDetailSearch.act'
                });
                break;
            }
            case 'dqkc': {
                layer.open({
                    type:
                        2,
                    title: '当前库存',
                    maxmin: true,
                    shadeClose: false,
                    area: ['90%', '90%'],
                    content: contextPath + '/KQDS_Ck_GoodsAct/toCkGoodsSearch.act'
                });
                break;
            }
            case 'gyscx': {
                layer.open({
                    type:
                        2,
                    title: '供应商查询',
                    maxmin: true,
                    shadeClose: false,
                    area: ['90%', '90%'],
                    content: contextPath + '/KQDS_Ck_SupplierAct/toCkSupplierSearch.act'
                });
                break;
            }
            case 'bj_search': {
                layer.open({
                    type:
                        2,
                    title: '报警查询',
                    maxmin: true,
                    shadeClose: false,
                    area: ['90%', '90%'],
                    content: contextPath + '/KQDS_Ck_GoodsAct/toCkGjSearch.act'
                });
                break;
            }
            case 'ck_ksll': {
                layer.open({
                    type:
                        2,
                    title: '科室领料',
                    maxmin: true,
                    shadeClose: false,
                    area: ['90%', '90%'],
                    content: contextPath + '/HUDH_KSllViewAct/toKsllCostColor.act'
                });
                break;
            }
            case 'ck_ksth': {
                layer.open({
                    type:
                        2,
                    title: '科室退货',
                    maxmin: true,
                    shadeClose: false,
                    area: ['90%', '90%'],
                    content: contextPath + '/HUDH_KSllViewAct/toKsllCKReplacement.act'
                });
                break;
            }
            /**
             * 添加入库审核
             */
            case 'rksh': {
                // 进货审核
                layer.open({
                    type:
                        2,
                    title: '入库审核',
                    shade: 0.6,
                    area: ['90%', '90%'],
                    content: contextPath + '/HUDH_Ck_Goods_In_CheckAct/toGoodsInCheck.act'
                });
                break;
            }
            /*  case 'lysh':
            {
                // 领用审核
                layer.open({
                    type:
                    2,
                    title: '出库审核',
                    shade: 0.6,
                    area: ['90%', '90%'],
                    content: contextPath + '/kqdsFront/index/ck/out_goods_sh.jsp'
                });
                break;
            } */
            /*  case 'clck':
                 {
                     //材料入库
                     layer.open({
                         type:
                         2,
                         title: '材料出库',
                         shade: 0.6,
                         area: ['90%', '90%'],
                         content: contextPath + '/kqdsFront/index/ck/out_goods_rk.jsp'
                     });

                     break;
                 } */
            /*  case 'jhsh':
            {
                // 进货审核
                layer.open({
                    type:
                    2,
                    title: '入库审核',
                    shade: 0.6,
                    area: ['90%', '90%'],
                    content: contextPath + '/kqdsFront/index/ck/in_goods_sh.jsp'
                });
                break;
            } */
            /*  case 'clrk':
                 {
                     // 材料入库
                     layer.open({
                         type:
                         2,
                         title: '材料入库',
                         shade: 0.6,
                         area: ['90%', '90%'],
                         content: contextPath + '/kqdsFront/index/ck/in_goods_rk.jsp'
                     });
                     break;
                 } */
        }
    });
