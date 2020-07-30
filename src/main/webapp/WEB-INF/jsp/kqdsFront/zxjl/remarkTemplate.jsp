<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/inc/classImport.jsp" %>
<%
    String contextPath = request.getContextPath();
    if (contextPath.equals("")) {
        contextPath = "/kqds";
    }
    YZPerson person = (YZPerson) request.getSession().getAttribute(ConstUtil.LOGIN_USER);
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8"/>
    <title>备注模板</title>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css" />
</head>
<style type="text/css">
.kqds_table td {
    color: #666;
    /*overflow: hidden;*/
    white-space: nowrap;
    text-overflow: ellipsis;
    font-weight: normal;
    line-height: 28px;
    padding: 3px 0px 5px 0px;
}
input{
    width:95%;
    height:33px;
    border:1px solid #d3d3d3;
    border-radius: 5px;
}
select{
    width:100%;
    height:27px;
}
textarea{
    width:90%;
    resize: none;
    padding: 6px 16px;
    font-size: 16px;
}
.kqds_table tr td:nth-child(odd){
    width:10%;
    text-align:right;
}
.kqds_table tr td:nth-child(even){
    width:23%;
    text-align:left;
}
.form-control {
    width: 95% !important;
}
.kqdsBtn{
    display: inline-block;
    box-sizing: border-box;
    height: 26px;
    line-height: 26px;
    background: #00a6c0;
    color: #fff;
    outline: none;
    padding: 0 15px;
    border: 1px solid #00a6c0;
    border-radius: 3px;
    margin-right: 3px;
    text-decoration: none;
    cursor: pointer;
    text-align: center;
    margin:5% 0 0 85%;
}

</style>
<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<body>
<div id="template" class="" style="padding-top:20px;">
    <form id="">
        <table class="kqds_table">
            <tr>
                <td>患者编号：</td>
                <td>
                    <input type="text" name="usercode" id="usercode" disabled="disabled" style="width:75%;text-align:center;font-size: larger;"/>
                </td>
                <td>客户姓名：</td>
                <td>
                    <input type="text" name="username" id="username" disabled="disabled" style="width:75%;text-align:center;font-size: larger;"/>
                </td>
            </tr>
            <tr>
                <td>医生：</td>
                <td>
                    <select id="doctor" name="doctor" data-live-search="true" multiple data-max-options="3" title="请选择" style="text-align:center"></select>
                </td>
            </tr>
            <tr>
                <td>主诉：</td>
                <td colspan="4">
                    <textarea class="form-control" name="main_suit" id="main_suit" rows="2"></textarea>
                </td>
            </tr>
            <tr>
                <td style="vertical-align: baseline;">方案：</td>
                <td colspan="4">
                    <textarea class="form-control" name="scheme" id="scheme" rows="2"></textarea>
                </td>
            </tr>
            <tr>
                <td style="vertical-align: baseline;">报价：</td>
                <td colspan="4">
                    <textarea class="form-control" name="price" id="price" rows="2"></textarea>
                </td>
            </tr>
            <tr>
                <td>预约金及今做项目：</td>
                <td colspan="4">
                    <input type="text" name="order_project" id="order_project">
                </td>
            </tr>
            <tr>
                <td>预约时间及下次安排：</td>
                <td colspan="4">
                    <input type="text" name="order_plan" id="order_plan">
                </td>
            </tr>
            <tr>
                <td>后期跟进注意：</td>
                <td colspan="4"><input type="text" name="follow" id="follow" ></td>
            </tr>
            <tr>
                <td>未成交原因备注：</td>
                <td colspan="4"><input type="text" name="failreason_mark" id="failreason_mark" ></td>
            </tr>
            <tr>
                <td>其他备注：</td>
                <td colspan="4"><input type="text" name="othermark" id="othermark" ></td>
            </tr>
        </table>
    </form>
    <button class="kqdsBtn" onclick="submit()">提交</button>
</div>
</body>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
<script type="text/javascript">
var onclickrowOobj = "";
var parent_doc=$(window.parent.frames["tabIframe"].document);
var parent_pre=parent_doc.find("#detaildesc");
var parent_doctor=parent_doc.find("#doctors");//医生
var parent_main_suit=parent_doc.find("#main_suit");//主诉
var parent_scheme=parent_doc.find("#scheme");//方案
var parent_price=parent_doc.find("#price");//报价
var parent_order_project=parent_doc.find("#order_project");//预约金及今做项目
var parent_order_plan=parent_doc.find("#order_plan");//预约时间及下次安排
var parent_follow=parent_doc.find("#follow");//后期跟进注意
var parent_failreason_mark=parent_doc.find("#failreason_mark");//未成交原因备注
var parent_othermark=parent_doc.find("#othermark");//其他备注
var doctorList;
var tempData=window.parent.frames["tabIframe"].onclickrowOobj2;
var depttype=<%=ConstUtil.DEPT_TYPE_1 %>;
var index = parent.layer.getFrameIndex(window.name);
$(function(){
    onclickrowOobj = window.parent.onclickrowOobj;
    $("#usercode").val(onclickrowOobj.usercode);
    $("#username").val(onclickrowOobj.username);
    $('#doctor').selectpicker("refresh");
    initPersonSelectTemp("doctor",depttype);
    // initTemp();//初始化当前模板信息
})
var doc=["4db6e6c8-b736-4ae3-a8ea-d5f667644a29","56ffc127-823d-4500-95cd-3f6fc8c31287"]
function initTemp(){
    if(tempData.detaildesc!=""){
        $('#doctor').selectpicker('val', doc);
        // $('#main_suit').val(tempData.main_suit);
        // $('#scheme').val(tempData.scheme);
        // $('#price').val(tempData.price);
        // $('#order_project').val(tempData.order_project);
        // $('#order_plan').val(tempData.order_plan);
        // $('#follow').val(tempData.follow);
        // $('#failreason_mark').val(tempData.failreason_mark);
        // $('#othermark').val(tempData.othermark);
    }
}
// console.log(JSON.stringify(tempData)+'----tempData');
function initPersonSelectTemp(id, depttype) {
    var dict = $("#" + id);
    var url = contextPath + "/YZPersonAct/getPersonListByDeptType.act?depttype=" + depttype;
    $.axse(url, null,
        function(data) {
            var list = data.list;
            if (list != null && list.length > 0) {
                doctorList=list;
                dict.empty();
                $('#'+id).selectpicker('destroy');
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

// 查询userName值
function initPersonSelectName(ids,datalist) {
    for (var i = 0; i < datalist.length; i++) {
        var doctorInfo = datalist[i];
        for (var k = 0; k < ids.length; k++) {
            for (var j in doctorInfo) {
                if (ids[k]==doctorInfo[j]) {
                    ids[k] = doctorInfo["userName"];
                }
            }

        }
    }
}

function submit(){
    var doctors=$("#doctor").val();
    if(doctors){
        initPersonSelectName(doctors,doctorList);
    }else{
        doctors=""
    }
    var main_suit=$("#main_suit").val();
    var scheme=$("#scheme").val();
    var price=$("#price").val();
    var order_project=$("#order_project").val();
    var order_plan=$("#order_plan").val();
    var follow=$("#follow").val();
    var failreason_mark=$("#failreason_mark").val();
    var othermark=$("#othermark").val();
    var template="";
    template+="[医生]:"+doctors+"\n";
    template+="[主诉]:"+main_suit+"\n";
    template+="[方案]:"+scheme+"\n";
    template+="[报价]:"+price+"\n";
    template+="[预约金及今做项目]:"+order_project+"\n";
    template+="[预约时间及下次安排]:"+order_plan+"\n";
    template+="[后期跟进注意]:"+follow+"\n";
    template+="[未成交原因备注]:"+failreason_mark+"\n";
    template+="[其他备注]:"+othermark+"\n";
    if(parent_pre.val()){
        parent_pre.val("");
    }
    if(parent_doctor){
        parent_doctor.val("");
    }
    if(parent_main_suit){
        parent_main_suit.val("");
    }
    if(parent_scheme){
        parent_scheme.val("");
    }
    if(parent_price){
        parent_price.val("");
    }
    if(parent_order_project){
        parent_order_project.val("");
    }
    if(parent_order_plan){
        parent_order_plan.val("");
    }
    if(parent_follow){
        parent_follow.val("");
    }
    if(parent_failreason_mark){
        parent_failreason_mark.val("");
    }
    if(parent_othermark){
        parent_othermark.val("");
    }
    parent_doctor.val(doctors);//添加医生id-hidden
    parent_main_suit.val(main_suit);//添加主诉-hidden
    parent_scheme.val(scheme);//添加方案-hidden
    parent_price.val(price);//添加报价-hidden
    parent_order_project.val(order_project);//添加预约金及今做项目-hidden
    parent_order_plan.val(order_plan);//添加预约时间及下次安排-hidden
    parent_follow.val(follow);//添加后期跟进注意-hidden
    parent_failreason_mark.val(failreason_mark);//添加未成交原因备注-hidden
    parent_othermark.val(othermark);//添加其他备注-hidden
    parent_pre.val(template);//添加模板--block
    setTimeout(function () {
        parent.layer.close(index);
    },50);
}
</script>
</html>
