/**
 * 处理所有的按钮事件
 */
function static_btnfucDeal(name, requrl, isParentOpen) {

    if (layer) { // 主要是处理layer弹窗事件，所以这个方法调用前，必须初始化layer
        layer.config({
            skin: 'self-blue-skin' // 自定义样式demo-class
        });
    } else {
        alert("未检测到layer控件，程序执行结束。");
        return;
    }

    switch (name) {

        // 添加加工单
        case 'tjjgd': {
            if (onclickrowOobj == "" || onclickrowOobj == null) {
                layer.alert('请选择患者！', {});
                return false;
            }
            layer.open({
                type: 2,
                title: '创建加工单',
                shadeClose: false,
                shade: 0.6,
                area: ['90%', '90%'],
                content: contextPath + '/KQDS_OutProcessingSheetAct/toAdd.act?usercode=' + onclickrowOobj.usercode
            });
            break;
        }
        // 测试加工单
        case 'jgcs': {
            if (onclickrowOobj == "" || onclickrowOobj == null) {
                layer.alert('请选择患者！', {});
                return false;
            }
            layer.open({
                type: 2,
                title: '创建加工单',
                shadeClose: false,
                shade: 0.6,
                area: ['90%', '90%'],
                content: contextPath + '/KQDS_OutProcessingSheetAct/toAddWorksheet.act?usercode=' + onclickrowOobj.usercode
            });
            break;
        }
        case 'xjbl': {
            // 选中右侧病历
            $(".lookInfoWrap").find("li").each(function () {
                var tmpId = $(this).attr("id");
                if (tmpId == 'bingli') {
                    $('#bingli').attr('src', contextPath + "/KQDS_MedicalRecordAct/toMedicalrecord.act");
                    $(this).trigger("click");
                }
            });
            break;
        }
        case 'ckbl': {
            // 查看电子病历
            layer.open({
                type:
                    2,
                title: '电子病历查看',
                shadeClose: false,
                shade: 0.6,
                area: ['90%', '85%'],
                content: contextPath + '/HUDH_DzblViewAct/toCaseHistory.act'
            });
            break;
        }
        case 'xgzl': {
            if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
                layer.alert('请选择患者', {});
                return false;
            }
            // 修改资料
            layer.open({
                type: 2,
                title: '修改患者资料',
                shadeClose: false,
                shade: 0.6,
                area: ['750px', '90%'],
                content: contextPath + '/KQDS_UserDocumentAct/toHzjd_Edit.act?usercode=' + onclickrowOobj.usercode
            });
            break;
        }
        case 'fytj': {
            // 判断是否有权限开单 没有权限提示并终止
            if (canKd == 0) {
                openAddCost();
            } else {
                layer.alert('无权限，请联系管理员开通权限！', {});
            }
            break;
        }
        case 'zz': {
            if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
                layer.alert('请选择患者', {});
                return false;
            }
            layer.open({
                type: 2,
                title: '转诊咨询',
                shadeClose: false,
                shade: 0.6,
                area: ['90%', '600px'],
                content: contextPath + '/KQDS_UserDocumentAct/toZzIndex.act?usercode=' + onclickrowOobj.usercode,
                // iframe的url
                cancel: function () {
                    // 默认打开等待治疗列表
                    var li = $(".columnBd").children("ul").children("li").eq(2);
                    li.attr({
                        "class": "current"
                    });
                    li.trigger("click");
                },
                end: function () {

                }
            });
            break;
        }
        case 'addRealation': { // 患者关系
            if (onclickrowOobj.del != null && onclickrowOobj.del == 1) {
                layer.alert('撤销的挂号无法进行操作！', {});
                return false;
            }
            if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
                layer.alert('请选择患者', {});
                return;
            }
            layer.open({
                type: 2,
                title: '添加关系',
                shadeClose: false,
                shade: 0.6,
                area: ['830px', '450px'],
                content: contextPath + '/KQDS_ParticipantAct/toAdd.act?usercode=' + onclickrowOobj.usercode // iframe的url
            });
            break;
        }

        case 'hzjd': {
            // 患者建档
            layer.open({
                type:
                    2,
                title: '患者建档',
                shadeClose: false,
                shade: 0.6,
                area: ['740px', '99%'],
                closeBtn: 1,
                content: contextPath + '/KQDS_UserDocumentAct/toHzjd.act'
            });
            break;
        }
        case 'ghcz': {
            let tabId = sessionStorage.getItem('tabID')
            var orderno = "";
            /*if (onclickrowOobj != null && onclickrowOobj.ordertype != null) {
                orderno = onclickrowOobj.seqId;
            } */
            if ((tabId == '1-1' || tabId == '1-2' || tabId == '1-3') && (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined")) {
                layer.alert('请选择患者', {});
                return false;
            }
            // 就诊挂号
            layer.open({
                type:
                    2,
                title: '就诊挂号',
                shadeClose: false,
                shade: 0.6,
                area: ['950px', '480px'],
                closeBtn: 1,
                content: contextPath + '/KQDS_REGAct/toAddReg.act?orderno=' + orderno
            });
            break;
        }
        case 'ghxg': {
            if (onclickrowOobj.del != null && onclickrowOobj.del == 1) {
                layer.alert('撤销的挂号无法进行操作！', {
                    icon: 6
                });
                return false;
            }
            if (onclickrowOobj == "" || onclickrowOobj.ifcost == null) {
                layer.alert('请选择挂号记录！', {
                    icon: 6
                });
                return false;
            }
            // 挂号修改
            layer.open({
                type: 2,
                title: '挂号修改',
                shadeClose: false,
                shade: 0.6,
                area: ['1000px', '515px'],
                content: contextPath + '/KQDS_REGAct/toEditReg.act?username=' + username
            });
            break;
        }
        case 'yyzx': {
            // 预约中心
            layer.open({
                type:
                    2,
                title: '预约中心',
                shadeClose: false,
                shade: 0.6,
                area: ['990px', '90%'],
                content: contextPath + '/KQDS_Net_OrderAct/toYyzxSearch.act'
            });
            break;
        }
        case 'zlxg': {
            if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
                layer.alert('请选择患者', {});
                return false;
            }
            // 修改资料
            layer.open({
                type: 2,
                title: '修改患者资料',
                shadeClose: false,
                shade: 0.6,
                area: ['750px', '90%'],
                content: contextPath + '/KQDS_UserDocumentAct/toHzjd_Edit.act?usercode=' + onclickrowOobj.usercode
            });
            break;
        }

        case 'yjj': {
            // 预交金
            layer.open({
                type:
                    2,
                title: '预交金充值',
                shadeClose: false,
                shade: 0.6,
                area: ['900px', '360px'],
                content: contextPath + '/KQDS_MemberAct/toember_Yujiaojin.act'
            });
            break;
        }
        case 'yyrl':
            var usercode = null;
            var username = null;
            var url = '/KQDS_Net_OrderAct/toYyzx2.act';
            if (onclickrowOobj) {
                usercode = onclickrowOobj.usercode;
                username = onclickrowOobj.username;
                url = '/KQDS_Net_OrderAct/toYyzx2.act?usercode=' + usercode + '&username=' + username;
            }
        {
            // 预约日历
            layer.open({
                type:
                    2,
                title: '预约日历',
                shadeClose: false,
                shade: 0.6,
                area: ['1200px', '90%'],
                content: contextPath + url
            });
            break;
        }
        case 'jz': {

            // 结账
            if (onclickrowOobj == null || onclickrowOobj.costno == null || onclickrowOobj.costno == "undefined") {
                layer.alert('请选择等待结账的费用单！', {});
                break;
            } else if (onclickrowOobj.status != 1) {
                layer.alert('请选择等待结账的费用单！', {});
                break;
            }

            var costorderObj = getCostOrderObjBySeqId(onclickrowOobj.costno);
            //请求后台 验证改单的状态
            if (costorderObj.status == "2") {
                layer.alert('该费用单已结账');
                refresh();
            } else {
                layer.open({
                    type: 2,
                    title: '结账',
                    shadeClose: false,
                    shade: 0.6,
                    area: ['960px', '90%'],
                    content: contextPath + '/Kqds_PayCostAct/toCostListing.act?costno=' + onclickrowOobj.costno + '&usercode=' + onclickrowOobj.usercode + '&regno=' + onclickrowOobj.regno + '&username=' + onclickrowOobj.username
                });
            }
            break;
        }
        case 'tk': {
            if (onclickrowOobj.del != null && onclickrowOobj.del == 1) {
                layer.alert('撤销的挂号无法进行操作！', {});
                return false;
            }
            // 退款
            layer.open({
                type: 2,
                title: '退款',
                shadeClose: false,
                // 点击遮罩关闭层
                area: ['90%', '90%'],
                content: contextPath + '/KQDS_RefundAct/toCost_TkIndex.act'
            });
            break;
        }
        case 'bdsj': {
            // 补打收据
            if (onclickrowOobj == null || onclickrowOobj.costno == null || onclickrowOobj.costno == "undefined") {
                layer.alert('请选择补打收据的费用单！', {});
                break;
            } else if (onclickrowOobj.sfuser == null || onclickrowOobj.sfuser == "" || onclickrowOobj.sfuser == "null" || onclickrowOobj.sfuser == "undefined" || onclickrowOobj.sfuser == undefined) {
                layer.alert('请选择已结账的费用单！', {});
                break;
            }
            layer.open({
                type: 2,
                title: '补打单据',
                shadeClose: false,
                shade: 0.6,
                area: ['960px', '90%'],
                content: contextPath + '/Kqds_PayCostAct/toCostListingPrint.act?costno=' + onclickrowOobj.seqId
            });
            break;
        }
        case 'hykbl': {
            var checkIsMember = false;
            if (onclickrowOobj != "" && onclickrowOobj != null) {
                // 用户选中行 先验证选中的患者是否已经办理过会员
                var url = contextPath + '/KQDS_MemberAct/checkIsMemberByUsercode.act?usercode=' + onclickrowOobj.usercode;
                $.axse(url, null,
                    function (data) {
                        if (data.data == 0) {
                            checkIsMember = true;
                        }
                    },
                    function () {
                    });
            } else {
                // 未选中 不用验证
                checkIsMember = true;
            }
            /* if (checkIsMember) {*/
            layer.open({
                type: 2,
                title: '会员卡办理',
                shadeClose: false,
                shade: 0.6,
                area: ['800px', '420px'],
                content: requrl
            });
            /* } else {
                 layer.alert('该患者已办理过会员卡！'  );
                 return false;
             }*/
            break;
        }
        case 'xxcx': {
            // 信息查询
            window.location.href = contextPath + '/KQDS_UserDocumentAct/toXxcxCenter.act?menuId=' + menuid;
            break;
        }
        case 'jzcx': {
            // 接诊查询
            window.location.href = contextPath + '/KQDS_REGAct/toJzcxCenter.act?menuId=' + menuid;
            break;
        }
        case 'kpxxcx': {
            // 开票信息查询
            window.location.href = contextPath + '/KQDS_REGAct/toKpxxcxCenter.act?menuId=' + menuid;
            break;
        }
        case 'fycx': {
            // 费用查询
            window.location.href = contextPath + '/KQDS_CostOrderAct/toFycxCenter.act?menuId=' + menuid;
            break;
        }
        case 'mxcx': {
            // 明细查询
            window.location.href = contextPath + '/KQDS_CostOrder_DetailAct/toMxcxCenter.act?menuId=' + menuid;
            break;
        }
        case 'dzcx': {
            // 到诊查询
            window.location.href = contextPath + '/KQDS_REGAct/toDzQuery.act?menuId=' + menuid;
            break;
        }
        case 'syzs': {
            if (onclickrowOobj == "" || onclickrowOobj.ifcost == null) {
                layer.alert('请选择挂号记录！', {});
                return false;
            }
            // 使用赠送项目
            parent.layer.open({
                type: 2,
                title: '使用赠送',
                shadeClose: false,
                shade: 0.6,
                area: ['66%', '71.5%'],
                content: contextPath + '/KQDS_GiveItem_UseRecordAct/toMember_Zengsong_UserItem.act?usercode=' + onclickrowOobj.usercode + '&regno=' + onclickrowOobj.seqId
            });
            break;
        }
        case 'zxzx_zsxm': {
            if (onclickrowOobj == null || onclickrowOobj == "" || onclickrowOobj == "null" || onclickrowOobj == "undefined") {
                layer.alert('请选择患者', {});
                return false;
            }
            layer.open({
                type: 2,
                title: '赠送 - 患者编号: ' + onclickrowOobj.usercode + ' ,姓名: ' + getNameByusercodesTB(onclickrowOobj.usercode)[0].username,
                shadeClose: false,
                shade: 0.6,
                area: ['798px', '450px'],
                content: contextPath + '/KQDS_GiveItem_UseRecordAct/toMember_Zengsong4zxzx.act?usercode=' + onclickrowOobj.usercode
            });
            break;
        }
        /*
			 * ############################以下为 Parnet.layer.opern
			 * #############################################
			 */

        case 'hfzx': {
            // 回访管理
            parent.layer.open({
                type:
                    2,
                title: '回访管理',
                shadeClose: false,
                shade: 0.6,
                area: ['90%', '90%'],
                content: contextPath + '/KQDS_VisitAct/toUserVisit.act'
            });
            break;
        }
        /*case 'dzcx':
            {
                // ###################到诊查询###################
                parent.layer.open({
                    type: 2,
                    title: '到诊查询',
                    shadeClose: false,
                    shade: 0.6,
                    area: ['98%', '95%'],
                    content: contextPath + '/KQDS_REGAct/toDzQuery.act'
                });
                break;
            }*/

        // 推广计划
        case 'tgjh': {
            parent.layer.open({
                type:
                    2,
                title: '推广计划',
                shadeClose: false,
                shade: 0.6,
                area: ['80%', '90%'],
                content: contextPath + '/KQDS_ExtensionAct/toExtensionVisit.act'
            });
            break;
        }

        /*
			 * ############################以下通过传参，设置url 和 是否为 parent.layer.open
			 * #############################################
			 */

        case 'visit_post': {
            var reqparam = {
                type: 2,
                title: '回访结果',
                shadeClose: false,
                shade: 0.6,
                area: ['550px', '560px'],
                content: requrl
            };

            if (isParentOpen) { // 如果是frame的页面弹框，则调用parent的layer
                parent.layer.open(reqparam);
            } else {
                layer.open(reqparam);
            }

            break;
        }
        case 'visit_edit': {
            var reqparam = {
                type: 2,
                title: '回访修改',
                shadeClose: false,
                shade: 0.6,
                area: ['550px', '480px'],
                content: requrl
            };
            if (isParentOpen) { // 如果是frame的页面弹框，则调用parent的layer
                parent.layer.open(reqparam);
            } else {
                layer.open(reqparam);
            }

            break;
        }
        case 'rsktj': {
            layer.open({
                type:
                    2,
                title: '日收款查询',
                shadeClose: false,
                shade: 0.6,
                area: ['80%', '85%'],
                content: contextPath + '/KQDS_ScbbAct/toBb_Rsktj_Search.act?menuId=' + menuid
            });
            break;
        }
        case 'xfmx_all_ask': {
            layer.open({
                type:
                    2,
                title: '日收款明细',
                shadeClose: false,
                shade: 0.6,
                area: ['98%', '95%'],
                content: contextPath + '/KQDS_ScbbAct/toBb_Sfmx_all_Askperson.act?menuId=' + menuid
            });
            break;
        }
        case 'rsktj_ask': {
            layer.open({
                type:
                    2,
                title: '日收款查询',
                shadeClose: false,
                shade: 0.6,
                area: ['80%', '85%'],
                content: contextPath + '/KQDS_ScbbAct/toBb_Rsktj_Search_Askperson.act?menuId=' + menuid
            });
            break;
        }
        case 'lltj': {
            layer.open({
                type:
                    2,
                title: '领料统计',
                shadeClose: false,
                shade: 0.6,
                area: ['95%', '80%'],
                content: contextPath + '/KQDS_LLTJAct/toLltj.act'
            });
            break;
        }
        case 'llcx': {
            layer.open({
                type:
                    2,
                title: '领料查询',
                shadeClose: false,
                shade: 0.6,
                area: ['90%', '85%'],
                content: contextPath + '/KQDS_LLTJAct/toLltjQuery.act?menuId=' + menuid
            });
            break;
        }
        case 'ylhz_ksll': {
            // 科室领料
            window.location.href = contextPath + '/HUDH_KSllViewAct/toKsllIndex.act';
            break;
        }
        case 'cjlclj': {
            if (onclickrowOobj) {
                layer.open({
                    type: 2,
                    title: '创建手术信息',
                    shadeClose: false,
                    shade: 0.6,
                    area: ['95%', '95%'],
                    content: contextPath + '/ClinicPathControllerAct/toLcljOpreation.act'
                });
            } else {
                layer.alert("请选择患者");
            }
            break;
        }
        case 'ylhz_lclj': {
            // 临床路径（在医疗中心---》医疗会诊中---》今日患者中查看患者临床路径信息 ）
            if (onclickrowOobj) {
                if (!selectOrderNumber) {
                    layer.alert("该患者未创建临床路径！");
                } else {
                    layer.open({
                        type: 2,
                        title: '手术详情',
                        shadeClose: false,
                        shade: 0.6,
                        area: ['99%', '98%'],
                        content: contextPath + '/static/css/hudh/lclj/flowdetail/index.html?orderNumber=' + selectOrderNumber
                    });
                }
            } else {
                layer.alert("请选择患者");
            }
            break;
        }
        case 'hzjd_zxzl': {
            layer.open({
                type: 2,
                title: '创建档案',
                shadeClose: false,
                shade: 0.6,
                area: ['65%', '85%'],
                content: contextPath + '/KQDS_UserDocumentAct/toHzjd_zxzl.act'
            });
            break;
        }
        case 'zxzx_zdkf': {
            setKeFu();
            break;
        }
        default: {
            alert("没检测到与[" + name + "]指令匹配的方法。");
        }

    }

}