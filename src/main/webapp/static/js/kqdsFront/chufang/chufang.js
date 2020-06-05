function editChuFang() {
    //费用单 数据
    var paramOrder = {
        costno: static_costno,
        seqId: static_seqId,
        remark: $("#remark").val()
    };
    //费用详情 数据
    //循环获取表格中项目
    var list = [];
    var qfId = "";
    $('#table').find('tbody').each(function() {
        $(this).find('tr').each(function() {
            var param = {};
            $(this).find('td').each(function() {
                if ($(this).index() == 1) {
                    //项目编号
                    param.itemno = $(this).text();
                } else if ($(this).index() == 2) {
                    //项目名称
                    param.itemname = $(this).find("span").html();
                } else if ($(this).index() == 3) {
                    param.num = $(this).html();
                } else if ($(this).index() == 4) {
                    param.unit = $(this).html();
                } else if ($(this).index() == 5) {
                    //用法
                    param.cfusage = $(this).find("select").val();
                } else if ($(this).index() == 6) {
                    //用量
                    param.cfuselevel = $(this).find("input").val();
                } else if ($(this).index() == 7) {
                    //使用方式
                    param.cfusemethod = $(this).find("select").val();
                /*} else if ($(this).index() == 8) {
                	//药品规格
                	param.pharm_spec = $(this).html();*/
                } else if ($(this).index() == 8) {
                    if (static_seqId) { // 如果是编辑处方单
                        param.seqId = $(this).text();
                    }
                }
                param.costno = static_costno;
            });
            list.push(param);
        });
    });

    var data = JSON.stringify(list);
    paramOrder.listDetail = data;
    var url = contextPath + '/KQDS_ChuFangAct/insertOrUpdate.act';
    $.axse(url, paramOrder,
    function(r) {
        if (r.retState == "0") {
            layer.alert('操作成功', {
                  
                end: function() {
                    parent.fyxqClick();
                    if (typeof(parent.initclick) != "undefined") {
                        parent.initclick();
                    }
                    parent.layer.close(frameindex); //再执行关闭 */
                }
            });
        } else {
            layer.alert(r.retMsrg, {
                  
                end: function() {
                    parent.fyxqClick();
                    if (typeof(parent.initclick) != "undefined") {
                        parent.initclick();
                    }
                    parent.layer.close(frameindex); //再执行关闭 */
                }
            });
        }
    },
    function() {
        layer.alert('请求失败' );
    });
}
//删除行
function deltr(tr, isqfreal, id) {
    var i = tr.parentNode.parentNode.rowIndex;
    document.getElementById('table').deleteRow(i);
}
//修改
function getOrderDetailList(costno) {
    var pageurl = contextPath + '/KQDS_CostOrder_DetailAct/selectNoPage.act?costno=' + costno;
    $.axse(pageurl, null,
    function(data) {
        for (var i = 0; i < data.length; i++) {
            tdindex++;
            var tablehtml = "";
            var tabledata = data[i];
            tablehtml += "<tr style=''>";
            //删除按钮0
            tablehtml += '<td style="height:30px;"><a href="javascript:void(0);" mce_href="javascript:void(0);" onclick="deltr(this,\'' + tabledata.isqfreal + '\',\'' + tabledata.seqId + '\')"><span style="color:red;">删除</span></a></td>';

            //项目编号1
            tablehtml += '<td style="display:none;">' + tabledata.itemno + '</td>';
            //治疗项目2
            tablehtml += '<td style=""><span style="text-align:left;width:220px;" class="time" title=' + tabledata.itemname + '>' + tabledata.itemname + '</span></td>';
            //数量3
            tablehtml += '<td style="">' + tabledata.num + '</td>';
            //单位4
            tablehtml += '<td style="">' + tabledata.unit + '</td>';
            //用法5
            tablehtml += '<td style="">' + static_cfusage_select_html + '</td>';
            //用量6
            tablehtml += '<td style=""><input  type="text" min="1" style="width:180px;float:left; text-align:center;"  id="cfuselevel' + tdindex + '" value="" ></td>';
            //用药途径7
            tablehtml += '<td style="">' + static_cfyytj_select_html + '</td>';
            //药品规格8
            //tablehtml += '<td style="">' + tabledata.pharmSpec + '</td>';
            //ID  9
            tablehtml += '<td style="display:none;">' + tabledata.seqId + '</td>';
            tablehtml += "</tr>";

            $("#table").find('tbody').append(tablehtml);
        }
    },
    function() {
        layer.alert('查询出错！'  );
    });
}