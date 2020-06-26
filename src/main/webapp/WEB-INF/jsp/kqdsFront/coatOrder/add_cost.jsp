<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/inc/classImport.jsp" %>
<%
    String contextPath = request.getContextPath();
    if (contextPath.equals("")) {
        contextPath = "/kqds";
    }
    YZPerson person = SessionUtil.getLoginPerson(request);

    String static_costno = request.getParameter("costno"); // 编辑时，才会传此值
    String static_isback = request.getParameter("isback"); // 退单编辑

    if (static_costno == null) {
        static_costno = "";
    }

    if (static_isback == null) {
        static_isback = "0";
    }

    String costorder_type = "0"; // 0 新增  1 修改，如果costno参数有值，则为修改
    if (static_costno != "") {
        costorder_type = "1";
    }

    String static_usercode = request.getParameter("usercode"); //新建的时候，才会传usercode和regno参数
    if (static_usercode == null) {
        static_usercode = "";
    }

    String static_regno = request.getParameter("regno"); //新建的时候，才会传usercode和regno参数
    if (static_regno == null) {
        static_regno = "";
    }
    //获取从左侧菜单点击带过来的菜单id
    String menuid = request.getParameter("menuId");

    // 是否是修改折扣
    String static_zhekou = request.getParameter("zhekou"); // 修改时传参，为1值，只能修改折扣，其他修改不了
    if (static_zhekou == null) {
        static_zhekou = "";
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <meta charset="utf-8"/>
    <title>费用添加页面</title><!-- 从右侧个人中心  中间 图标 进入 -->
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrapValidator.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-table.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/plugin/zTreeStyle/zTreeStyle.css"
          type="text/css">
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/jiedai/add_cost.css"/>
    <!-- select搜索筛选 -->
    <link rel="stylesheet" type="text/css"
          href="<%=contextPath%>/static/css/admin/index/bower_components/select/bootstrap-select.css"/>

    <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/costOrder/add_cost.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
    <script type="text/javascript"
            src="<%=contextPath%>/static/js/bootstrap/bootstrap-table/bootstrap-table.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
    <script type="text/javascript"
            src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/bootstrap-datepicker.js"></script>
    <script type="text/javascript"
            src="<%=contextPath%>/static/js/bootstrap/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
    <script type="text/javascript"
            src="<%=contextPath%>/static/js/bootstrap/bootstrapvalidator/dist/bootstrapValidator.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/kqdsFront/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/static/js/hudh/commont.js"></script>
    <!-- select搜索筛选 -->
    <script type="text/javascript"
            src="<%=contextPath%>/static/js/bootstrap/plugins/select/bootstrap-select.js"></script>

    <style type="text/css">
        a:hover {
            text-decoration: none;
        }

        li {
            cursor: pointer;
        }

        .aBtn1 {
            display: inline-block;
            width: 115px;
            height: 30px;
            margin-right: 10px;
            line-height: 30px;
            border: 1px solid #00A6C0;
            color: #00A6C0;
            border-radius: 15px;
            text-align: center;
        }

        .aBtn1:hover {
            color: #fff;
            background: #117cca;
            text-decoration: none;
        }

        .aBtn1.gray {
            border-color: #a5a195;
            color: #a5a195;
        }

        input[type=number] {
            padding: 0 10px;
            width: 212px;
            height: 28px;
            line-height: 28px;
            border: solid 1px #e5e5e5;
            border-radius: 3px;
            -webkit-transition: all .3s;
            transition: all .3s;
        }

        #table td span.time {
            display: inline-block;
            width: 250px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        #table td span.name {
            display: inline-block;
            width: 80px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        #table td, #table th, #tableHeader th {
            border: 1px solid #ddd;
            text-align: center;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        #tableHeader th {
            height: 30px;
        }

        input[type="text"] {
            text-align: center;
        }

        #table input, #table select {
            height: 22px;
            font-size: 12px;
            padding: 0 5px;
        }

        .searchSelect:not([class*="col-"]):not([class*="form-control"]):not(.input-group-btn) {
            width: 110px;
        }

        .searchSelect > .btn {
            width: 110px;
            height: 28px;
            border: solid 1px #e5e5e5;
        }

        .bootstrap-select > .dropdown-toggle.bs-placeholder, .bootstrap-select > .dropdown-toggle.bs-placeholder:hover, .bootstrap-select > .dropdown-toggle.bs-placeholder:focus, .bootstrap-select > .dropdown-toggle.bs-placeholder:active {
            color: #999;
            height: 28px;
        }

        .pull-left {
            float: left !important;
            color: #333;
        }

        .btn-group > .btn:first-child:hover {
            background: white;
        }

        /* 	解決标签查询中下拉框悬浮 */
        .formBox > section > ul.conditions {
            overflow: visible;
            height: 40px;
        }

        .formBox {
            overflow: visible;
        }

        .ztree li span.button {
            pointer-events: none;
        }
    </style>
</head>
<body>
<div id="container">
    <!-- <div class="costHd2">
        <ul>
            <li id="jbxxli" class="current">基本信息</li>
        </ul>
        <ul>
            <li id="fyxqli">历史费用</li>费用详情
        </ul>
    </div> -->
    <ul class="headerUl">
        <li id="jbxxli" class="current">基本信息</li>
        <li id="fyxqli">历史费用</li>
    </ul>
    <div id="jbxx">
        <form class="form-horizontal" id="form1">
            <input type="hidden" class="form-control" name="seqId" id="seqId">
            <div class="formBox">
                <section>
                    <ul class="conditions" style="height: 90px">
                        <li>
                            <span>姓名</span>
                            <input type="text" id="username" name="username" readonly>
                        </li>
                        <li>
                            <span>年龄</span>
                            <input type="text" id="age" name="age" readonly>
                        </li>
                        <li>
                            <span>性别</span>
                            <select id="sex" name="sex" disabled>
                                <option value="">请选择</option>
                                <option value="男">男</option>
                                <option value="女">女</option>
                            </select>
                        </li>
                        <li>
                            <span>联系方式</span>
                            <input type="text" id="phonenumber1" name="phonenumber1" readonly>
                        </li>
                        <li>
                            <span>介绍人</span>
                            <input type="hidden" name="introducer" id="introducer" class="form-control" value=""/>
                            <input type="text" id="introducerDesc" name="introducerDesc" placeholder="介绍人"
                                   readonly></input>
                        </li>
                        <!--<li>
                            <span>会员信息</span>
                             <select class="dict" tig="HYKFL" name="memberlevel" id="memberlevel" disabled="disabled"></select>
                        </li>-->
                        <li>
                            <span>余额</span>
                            <input type="text" id="ye" name="ye" readonly>
                        </li>
                        <!-- <div class="kv">
                            <label>积分</label>
                            <div class="kv-v">
                                <input type="text" id="jf" name="jf" readonly>
                            </div>
                        </div> -->
                        <li>
                            <span>患者第一句话</span>
                            <input style="width:360px;text-align:left;" type="text" name="firstword" id="firstword">
                        </li>
                        <li>
                            <a href="javascript:void(0);" class="kqdsCommonBtn" onclick="saveform1()">保存资料</a>
                            <!-- 调用add_cost.js中的方法 -->
                            <a href="javascript:void(0);" class="kqdsCommonBtn" id="lzjl">流转记录</a>
                            <a href="javascript:void(0);" class="kqdsCommonBtn" id="wdjl">网电记录</a>
                            <a href="javascript:void(0);" class="kqdsCommonBtn" id="hfjl">回访记录</a>
                            <a href="javascript:void(0);" class="kqdsCommonBtn" id="lsbl">病历记录</a>
                        </li>
                    </ul>
                </section>
                <!-- <div class="kv">
                    <label>姓名</label>
                    <div class="kv-v">
                        <input type="text" id="username" name="username" readonly>
                    </div>
                </div>
                <div class="kv">
                    <label>年龄</label>
                    <div class="kv-v">
                        <input type="text" id="age" name="age" readonly>
                    </div>
                </div>
                <div class="kv">
                    <label>性别</label>
                    <div class="kv-v">
                        <select id="sex" name="sex" disabled>
                            <option value="">请选择</option>
                            <option value="男">男</option>
                            <option value="女">女</option>
                        </select>
                    </div>
                </div>
                <div class="kv">
                    <label>联系方式</label>
                    <div class="kv-v">
                        <input type="text" id="phonenumber1" name="phonenumber1" readonly>
                    </div>
                </div>
                <div class="kv">
                    <label>介绍人</label>
                    <div class="kv-v">
                        <input type="hidden" name="introducer" id="introducer"  class="form-control" value=""/>
                        <input  type="text" id="introducerDesc" name="introducerDesc" placeholder="介绍人"  readonly onclick="getuser()"></input>
                    </div>
                </div>
                <div class="kv">
                    <label>会员信息</label>
                    <div class="kv-v">
                        <select class="dict" tig="HYKFL" name="memberlevel" id="memberlevel"></select>
                    </div>
                </div>
                <div class="kv">
                    <label>余额</label>
                    <div class="kv-v">
                        <input type="text" id="ye" name="ye" readonly>
                    </div>
                </div>
                <div class="kv">
                    <label>积分</label>
                    <div class="kv-v">
                        <input type="text" id="jf" name="jf" readonly>
                    </div>
                </div>
                <div class="kv long">
                    <label>患者的第一句话</label>
                    <div class="kv-v">
                        <input type="text" name="firstword" id="firstword" >
                    </div>
                </div> -->
            </div>
        </form>
        <!-- <div class="buttonBar">

        </div> -->
    </div>

    <!--<div id="hkjh"></div> -->

    <div id="fyxq"> <!-- 费用详情 -->
        <div class="tableHd"
             style="padding: 0 20px;line-height: 40px;font-size: 14px;color: #FFFFFF;background: #00A6C0 ;">费用详情
        </div>
        <div class="tableBox">
            <table id="tablefyxq" class="table-striped table-condensed table-bordered"></table>
        </div>
        <div id="xqxmqd"><!-- 费用详情对应的费用明细 -->
            <div class="tableHd"
                 style="padding: 0 20px;line-height: 40px;font-size: 14px;color: #FFFFFF;background: #00A6C0 ;">详细项目清单
            </div>
            <div class="tableBox" id="divkdxm">
                <table id="dykdxm" class="table-striped table-condensed table-bordered"></table>
            </div>
        </div>
    </div>
    <div id="fytjDiv"><!-- ###费用添加start -->
        <div class="costWrap">
            <div class="costHd"> <!-- Header -->
                <label><input type="radio" name="treetype" id="sfbz" checked="checked">收费标准</label>
                <label><input type="radio" name="treetype" id="sftc">收费套餐</label>
                <ul>
                    <li>明细</li>
                </ul>
                <div class="kv">
                    <label style="margin:0 5px 0 0;">会员卡</label>
                    <!-- <div class="kv-v"> -->
                    <select name="hyxxSelect" id="hyxxSelect" style="color:#333;">
                        <option>请选择</option>
                    </select>
                    <select class="dict" tig="HYKFL" name="memberlevel" id="memberlevel" disabled="disabled"></select>
                    <input type="text" id="hyzk" value="无折扣" style="line-height:22px;width:105px; color:#333;"
                           readonly/>
                    <input type="text" id="yue" value="余额：0 ￥" style="line-height:22px; width:125px; color:#333;"
                           readonly/>
                    <!-- </div> -->
                </div>
            </div>
            <div class="costBd" style="position:relative;"><!-- Body -->
                <div class="ztreeWrap">
                    <div class="searchWrap">
                        <!-- <input type="text" placeholder="搜索" name="search" id="search" class="searchInput" style="width: 192px;"> -->
                        <input type="text" placeholder="搜索" name="search" id="search" class="searchInput">
                        <a href="javascript:void(0);" id="infosearch" class="hc-icon icon16 search-icon searchBtn"
                           onclick="zTreeInit()"></a>
                    </div>
                    <div class="ztree" style="overflow-y:auto;">
                        <ul id="treeDemo" class="ztree">
                        </ul>
                    </div>
                </div>
                <div class="tableBody"
                     style="position:relative;/* overflow-y:auto; */border:1px solid #ddd;border-radius:8px;">
                    <div id="tableHeader"
                         style="z-index:1;margin-right:15px;overflow:hidden;height:31px;position:relative;">
                        <table id="fixedHeadertable" class="tableHeader" style="width:100%;">
                            <thead style="background: #00A6C0 ;color:white;">
                            <tr>
                                <th style="width:30px;">操作</th>
                                <th style="display:none;width:80px;">医生</th>
                                <th style="width:70px;">特殊项目</th>
                                <th style="width:160px;">治疗项目</th>
                                <th style="width:30px;">单位</th>
                                <th style="width:100px;">单价</th>
                                <th style="width:70px;">数量</th>
                                <th style="width:70px;">折扣%</th>
                                <th style="width:100px;">小计</th>
                                <th style="width:100px;">欠费金额</th>
                                <th style="width:100px;">缴费金额</th>
                                <th style="width:100px;">免除</th>
                                <th style="width:100px;">分类</th>  <!-- 添加药品分类展示 -->
                            </tr>
                            </thead>
                        </table>
                    </div>
                    <div id="tableContent" style="margin-top:-31px;overflow-x:auto;overflow-y:scroll;">
                        <table id="table" style="width:100%;" class="table-striped table-condensed table-bordered"
                               data-height="250" style="width: 100%;text-align: center;">
                            <thead style="background: #00A6C0 ;color:white;">
                            <tr>
                                <th style="width:30px;height:30px;">操作</th>
                                <th style="display:none;width:80px;">医生</th>
                                <th style="width:70px;">特殊项目</th>
                                <th style="width:160px;">治疗项目</th>
                                <th style="width:30px;">单位</th>
                                <th style="width:100px;">单价</th>
                                <th style="width:70px;">数量</th>
                                <th style="width:70px;">折扣%</th>
                                <th style="width:100px;">小计</th>
                                <th style="width:100px;">欠费金额</th>
                                <th style="width:100px;">缴费金额</th>
                                <th style="width:100px;">免除</th>
                                <th style="width:100px;">分类</th>  <!-- 添加药品分类展示 -->
                            </tr>
                            </thead>
                            <tbody id="costitemlistBody" style="background-color: #F0FFFF;text-align: center;"></tbody>
                        </table>
                    </div>
                </div>
                <div class="formBox money" style="clear:both;">
                    <section>
                        <ul class="conditions">
                            <li>
                                <span>折扣额</span>
                                <input type="text" id="discountmoney" readonly="readonly">
                            </li>
                            <li>
                                <span>合计</span>
                                <input type="text" id="totalcost" readonly="readonly">
                            </li>
                            <li>
                                <span>免除</span>
                                <input type="text" id="voidmoney" readonly="readonly">
                            </li>
                            <li>
                                <span>应收</span>
                                <input type="text" id="shouldmoney" readonly="readonly">
                            </li>
                            <li>
                                <span>欠费</span>
                                <input type="text" id="arrearmoney" readonly="readonly">
                            </li>
                            <li>
                                <span>缴费</span>
                                <input type="text" id="actualmoney" readonly="readonly">
                            </li>
                        </ul>
                    </section>
                </div>
                <div class="formBox person" style="clear:both;">
                    <section>
                        <ul class="conditions">
                            <li>
                                <span class="searchTitle">咨询</span>
                                <select name="askperson" id="askperson" class="searchSelect"
                                        data-live-search="true"></select>
                                <!-- 							<select name="askperson" id="askperson" ></select> -->
                            </li>
                            <li>
                                <span class="searchTitle">医生</span>
                                <select name="doctor" id="doctor" class="searchSelect" data-live-search="true"></select>
                                <!-- 							<select name="doctor" id="doctor" ></select> -->
                                <!-- <input type="hidden" name="doctor" id="doctor"  class="form-control" />
                                <input type="text" id="doctorDesc" name="doctorDesc" placeholder="医生" readonly onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'single','0','1,4');"  > -->
                                <!-- 只显示医生、护士部门的，洗牙有时候是护士洗的 -->
                            </li>
                            <li>
                                <span class="searchTitle">修复医生</span>
                                <select name="repair" id="repair" class="searchSelect" data-live-search="true"></select>
                                <!-- 							<select name="repair" id="repair" ></select> -->
                                <!-- <input type="hidden" name="repaire" id="repaire"  class="form-control" />
                                <input type="text" id="doctorDesc" name="doctorDesc" placeholder="医生" readonly onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'single','0','1,4');"  > -->
                                <!-- 只显示医生、护士部门的，洗牙有时候是护士洗的 -->
                            </li>
                            <li>
                                <span class="searchTitle">护士1</span>
                                <select name="nurse" id="nurse" class="searchSelect" data-live-search="true"></select>
                                <!-- 							<select id="nurse" name="nurse"></select> -->
                            </li>
                            <li>
                                <span class="searchTitle">护士2</span>
                                <select name="techperson" id="techperson" class="searchSelect"
                                        data-live-search="true"></select>
                                <!-- 							<select id="techperson" name="techperson"></select> -->
                            </li>
                            <li>
                                <label style="font-weight: normal;vertical-align: middle;"><input type="checkbox"
                                                                                                  id="yhstatus"
                                                                                                  name="yhstatus">项目信息</label>
                                <!-- 勾选状态后，分别点击不同的收费项目，会展示不同的项目介绍和优惠信息 -->
                            </li>
                        </ul>
                    </section>
                </div>
            </div>
        </div>

        <div class="formBox_center" style="margin-left:214px;"> <!-- 按钮组 -->
            <!-- <div class="kv">
                <label style="width: 50px;">折扣额</label>
                <div class="kv-v">
                    <input style="width: 90px;" type="text" id="discountmoney" readonly="readonly">
                </div>
            </div>
            <div class="kv">
                <label style="width: 50px;">合计</label>
                <div class="kv-v">
                    <input  style="width: 90px;" type="text" id="totalcost" readonly="readonly">
                </div>
            </div>
            <div class="kv">
                <label style="width: 50px;">免除</label>
                <div class="kv-v">
                    <input  style="width: 90px;" type="text" id="voidmoney" readonly="readonly">
                </div>
            </div>
            <div class="kv">
                <label style="width: 50px;">应收</label>
                <div class="kv-v">
                    <input  style="width: 90px;" type="text" id="shouldmoney" readonly="readonly">
                </div>
            </div>
            <div class="kv">
                <label style="width: 50px;">欠费</label>
                <div class="kv-v">
                    <input  style="width: 90px;" type="text" id="arrearmoney" readonly="readonly">
                </div>
            </div>
            <div class="kv">
                <label style="width: 50px;">缴费</label>
                <div class="kv-v">
                    <input  style="width: 90px;" type="text" id="actualmoney" readonly="readonly">
                </div>
            </div> -->


            <div class="kv" id="xmjsDiv">
                <label>项目介绍</label>
                <div class="kv-v">
                    <textarea id="xmjs"></textarea>
                </div>
            </div>
            <div class="kv" id="yhxxDiv">
                <label>优惠信息</label>
                <div class="kv-v">
                    <textarea id="yhxx"></textarea>
                </div>
            </div>
            <!-- <div class="btnBox">
                <a href="javascript:void(0);" class="kqdsCommonBtn"  id="lctc" >另存套餐</a>
                <a href="javascript:void(0);" class="kqdsCommonBtn"  id="deltc">删除套餐</a>
                <a href="javascript:void(0);" class="kqdsCommonBtn"  id="tydz">统一打折</a>
                <div class="kv">
                    <label>咨询</label>
                    <div class="kv-v">
                        <select style="width: 80px;" name="askperson" id="askperson" ></select>
                    </div>
                </div>
                <div class="kv">
                    <label>医生</label>
                    <div class="kv-v">
                        <input type="hidden" name="doctor" id="doctor"  class="form-control" />
                        <input type="text"   id="doctorDesc" name="doctorDesc" placeholder="医生" readonly style="width: 80px;" onClick="javascript:single_select_user(['doctor', 'doctorDesc'],'single');"  ></input>
                    </div>
                </div>
                            <div class="kv">
                    <label style="width: 50px;">护士1</label>
                    <div class="kv-v">
                        <select id="nurse" name="nurse" style="width:60px;"></select>
                    </div>
                </div>
                <div class="kv">
                    <label style="width: 50px;">护士2</label>
                    <div class="kv-v">
                        <select id="techperson" name="techperson" style="width:60px;"></select>
                    </div>
                </div>
            </div> -->
        </div>

        <div class="footBar">
            <a href="javascript:void(0);" class="kqdsCommonBtn" id="tydz">统一打折</a>
            <a href="javascript:void(0);" class="kqdsCommonBtn" id="lctc">另存套餐</a>
            <a href="javascript:void(0);" class="kqdsCommonBtn" id="deltc">删除套餐</a>
            <!-- 		<a href="javascript:void(0);" class="aBtn1" id="printzqtys" >打印知情同意书</a> -->
            <a href="javascript:void(0);" class="kqdsCommonBtn" id="tjfyjh">添加费用计划</a>
            <a href="javascript:void(0);" class="kqdsCommonBtn" id="qxhj">取消划价</a>
            <a href="javascript:void(0);" class="kqdsSearchBtn" id="qrhj">确认划价</a>
        </div>
    </div> <!-- ###费用添加end -->
</div>
</body>
<script type="text/javascript">
    //获取父页面权限
    var listbutton = window.parent.listbutton;
    var contextPath = "<%=contextPath%>";
    // 判断是否为 前台进行打折操作，如果是，则值为1
    var static_zhekou = "<%=static_zhekou %>";
    var perseqId = "<%=person.getSeqId()%>";
    var canlookphone = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag1_canLookPhone, request) %>';
    var canKd = '<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag2_canKd, request)%>';
    var maxDiscount = "<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag0_maxDiscount, request) %>";
    var maxVoidmoney = "<%=UserPrivUtil.getPrivValueByKey(UserPrivUtil.qxFlag19_maxVoidmoney, request) %>"; // 最大免除金额
    var list = "";//定义权限菜单集合
    var frameindex = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引

    var tdindex = 1000; //表格里各类标签的id 动态添加时使用 数字随便从几开始
    /**
     * 此页面需要的参数包括：
     * 1、挂号单主键  2、该患者的咨询（只作展示） 3、医生：先从订单表中取，如果没有，再从挂号表中取 4、usercode
     */
    var static_costno = "<%=static_costno%>";    // ### 修改
    var static_isback = "<%=static_isback%>";    // ### 退单
    var static_usercode = "<%=static_usercode%>";// ### 新建
    var static_regno = "<%=static_regno%>";// ### 新建
    var static_doctor = "";
    var static_Hk_doctor = "";
    var static_doctorname = "";
    var static_askperson = ""; // 根据患者编号，查询患者对象后获取
    var static_nurse = "";
    var static_repair = "";// 2019/8/4 lutian 新增修复医生变量
    //医生 下拉列表
    var doctype = "<%=ConstUtil.DEPT_TYPE_1 %>";
    // var static_yjjiem = 0;//预交金项目
    // var static_ghiem = 1;//挂号项目
    var delete_item = "";// 存储修改费用单时删除的 收费项目

    var onclickrowOobjfyxq = null;
    var costorder_type = '<%=costorder_type%>'; // 0 新增  1 修改

    var static_costorderObj = null; // 消费单对象
    var static_userObj = null; // 患者对象
    var static_regObj = null;
    var hykdiscount = 100;
    $(function () {
        // 隐藏 项目介绍和优惠信息
        $("#xmjsDiv").hide();
        $("#yhxxDiv").hide();
        getButtonPower();//初始化权限
        //咨询 下拉列表
        initPersonSelectByDeptType("askperson", "<%=ConstUtil.DEPT_TYPE_0 %>");
        /** 当前部门的  **/
        initPersonListByDeptTypeWithNull("nurse", "4"); // 初始化护士下拉框
        initPersonListByDeptTypeWithNull("techperson", "4");
        initSysUserByDeptId($("#doctor"), "doctor", "seqId");
        initSysUserByDeptId($("#repair"), "repairdoctor", "seqId");

        if (static_costno != "") { //修改费用单
            static_costorderObj = getCostOrderObjBySeqId(static_costno);
            static_usercode = static_costorderObj.usercode;
            static_doctor = static_costorderObj.doctor;
            static_nurse = static_costorderObj.nurse;
            // 根据订单号加载费用清单
            getOrderDetailList(static_costno);
            // 修改费用单，也应该带出最新的欠费单
            getQfDetailList(static_usercode);
            $("#doctor").val(static_costorderObj.doctor);  //医生赋值

        } else { // 新建费用单
            // 根据患者编号查询欠费记录
            getQfDetailList(static_usercode);
        }

        if (static_zhekou == "1") { // 如果是打折操作，则不允许修改 数量、缴费金额，免除金额
            $("input[id^='num']").attr("readonly", "readonly");
            $("input[id^='paymoney']").attr("readonly", "readonly");
            $("input[id^='voidmoney']").attr("readonly", "readonly");
            // 折扣，只能修改折扣
            $("#doctorDesc").removeAttr("onClick");
            $("#askperson").attr("disabled", "disabled");
            $("#doctor").attr("disabled", "disabled");
            $("#nurse").attr("disabled", "disabled");
            $("#techperson").attr("disabled", "disabled");
        }

        static_userObj = getHzNameByusercodeTB(static_usercode); // 根据患者编号查询患者对象

        if (static_regno == "") { // 挂号单主键，新建收费单时通过Url传值，修改收费单时直接从收费单中获取
            static_regno = static_costorderObj.regno;
        }
        static_regObj = getRegObjBySeqId(static_regno); // ### 根据主键获取挂号单对象
        //console.log(JSON.stringify(static_costorderObj)+"--------收费单对象");
        //console.log(JSON.stringify(static_userObj)+"--------患者对象");
        //console.log(JSON.stringify(static_regObj)+"--------挂号单对象");

        // 展示咨询，由于cost order中没存咨询，所以该代码放在此处，新增、修改订单时，都执行
        //static_askperson = static_userObj.askperson; // ###用户表中还没设定咨询时，从挂号单中获取
        if (static_askperson == "") {
            static_askperson = static_regObj.askperson;
        }
        if (static_askperson != null && static_askperson != "") {
            $("#askperson").val(static_askperson); //展示咨询
        }


        // 2019/8/4 lutian 修复医生选项框赋值
        if (static_repair == "") {
            if (static_costorderObj) {
                static_repair = static_costorderObj.repairdoctor;
                if (static_repair != null && static_repair != "") {
                    $("#repair").val(static_repair); //展示咨询
                }
            }
        }

        //医生 护士赋值   去掉医生、修复医生、护士带出  2019-09-17 duoyh
        //setdoctor();
        initDictSelectByClass(); // 会员卡下拉框
        getJbxx();     // 基本信息
        intMemberCardListByUserCode("hyxxSelect", static_usercode); // 初始化会员下拉
        $("#hyxxSelect option").each(function () {
            if ($(this).html() == static_usercode) {
                $(this).attr("selected", true);
                $(this).trigger("change"); // 触发change事件，调用 $('#hyxxSelect').on('change'... 方法进行赋值
            }
        });
        var baseinfo = getBaseInfoByUserCode(static_usercode);
        /** 该请求和上面的getHzNameByusercodeTB()方法存在重复，可优化！  **/
        hykdiscount = baseinfo.discount;
        var zong = Number(baseinfo.money) + Number(baseinfo.givemoney);
        /** 这儿是该患者对应所有会员卡的金额 **/
        $('#ye').val(zong);
        zTreeInit();
        $('#fyxq').hide();
        $('#yhstatus').on('click',
            function () {
                if ($('#yhstatus').is(':checked')) {
                    $("#xmjsDiv").show();
                    $("#yhxxDiv").show();
                    $("#container").scrollTop(60);
                } else {
                    $("#xmjsDiv").hide();
                    $("#yhxxDiv").hide();
                }
            });

        $('li').on('click',
            function () {
                $(this).addClass('current'); // 设置被点击元素为红色
                $(this).siblings('li').removeClass('current'); // 去除所有同胞元素的红色样式
                //切换对应栏目下的表格
            });

        //数据表table滚动时  固定的表头也要跟着滚动
        $("#tableContent").scroll(function () {
            /* console.log("aaa");
            console.log("滚动："+$("#tableHeader").scrollLeft()); */
            $("#tableHeader").scrollLeft($(this).scrollLeft());
        })
        setHeight();
        $(window).resize(function () {
            setHeight();
        });

        /** 判断是否存在过期项目，并给予提示 **/
        judgeIfExistExpireItem();

        //后期添加保证每次选完医生后更新所选项目的医生
        $("#doctor").on("change", function () {
            $("select[id^='doctor']").val($("#doctor").val());
        });
        $('.searchSelect').selectpicker("refresh");//初始化刷新--2019.10.11--licc

    });
    //去掉医生、修复医生、护士带出  2019-09-17 duoyh
    /* function setdoctor(){
        //直接写 trim 如果 doctor为空会报错
        if (static_doctor != null && static_doctor != "" && yzUser(static_doctor)) {
            $("#doctor").val(static_doctor);
            bindPerUserNameBySeqIdTB("doctorDesc", static_doctor);
        }else{
             //默认带入还款的所属医生
            if(static_Hk_doctor != null && static_Hk_doctor != "" && yzUser(static_Hk_doctor)){
                $("#doctor").val(static_Hk_doctor);
                bindPerUserNameBySeqIdTB("doctorDesc", static_Hk_doctor);
            }else{
                // 医生这个字段，收费单里没有值时，再从患者档案表获取
                static_doctor = static_userObj.doctor;
                if (static_doctor != null && static_doctor != "" && yzUser(static_doctor)) { //直接写 trim 如果 doctor为空会报错
                    $("#doctor").val(static_doctor);
                    bindPerUserNameBySeqIdTB("doctorDesc", static_doctor);
                }
            }
        }

        if (static_nurse != null && static_nurse != "") {
            $("#nurse").val(static_nurse);
        }
        if(static_costorderObj!=null){
            if (static_costorderObj.techperson != null && static_costorderObj.techperson.trim() != "") {
                $("#techperson").val(static_costorderObj.techperson);
            }
        }
        //修复医生
        if(static_regObj!=null){
            if (static_regObj.repairdoctor != null && static_regObj.repairdoctor.trim() != "") {
                $("#repair").val(static_regObj.repairdoctor);
            }
        }

    } */
    //挂号时选择了医生，此处作提醒   2019-09-17 防止开单医生与挂号医生不一致 挂号医生被覆盖 duoyh
    $('#doctorDesc').on('change',
        function () {
            //设置明细表医生
            $('#table').find('tbody').each(function () {
                $(this).find('tr').each(function () {
                    $(this).find('td').each(function () {
                        $(this).find("select").val($("#doctor").val());
                    });
                });
            });

            // 2019-09-17 防止开单医生与挂号医生不一致 挂号医生被覆盖
            /* if (static_doctor != null && static_doctor != "") {
                if (static_doctor != $('#doctor').val()) {
                    layer.confirm('所选医生与挂号医生不一致，会覆盖更改挂号时的医生？', {
                        btn: ['确定', '取消'] //按钮
                    },
                    function() {
                        layer.closeAll('dialog');
                    },
                    function() {
                        setdoctor();
                        layer.closeAll('dialog');
                    });
                }
            }  */
        });

    /*** Tab 切换 ***/
    $('#jbxxli').on('click',
        function () {
            $('#jbxx').show();
            $('#fytjDiv').show(); /* 显示费用添加表格 */
            $('#fyxq').hide();
            $("#xqxmqd").hide();

            $(this).addClass('current');
            $("#fyxqli").removeClass("current");
        });
    $('#fyxqli').on('click',
        function () {
            $('#jbxx').hide();
            $('#fytjDiv').hide(); /* 隐藏费用添加表格 */
            $('#fyxq').show();
            $("#xqxmqd").show();

            $(this).addClass('current');
            $("#jbxxli").removeClass("current");

            // 获取费用详情列表
            getlist();

        });/*** Tab 切换  END ...***/

    $("#sfbz").click(function () { // 收费项目和收费套餐，共用一个div
        $('#treeDemo li').remove();
        $("#search").show();//展示搜索框
        $("#infosearch").show();//展示放大镜图片
        $(".ztree").outerHeight($(".ztreeWrap").outerHeight() - 24);//树的高度设置
        zTreeInit();
    });
    $("#sftc").click(function () {
        $('#treeDemo li').remove();
        $("#search").hide();	//隐藏搜索框
        $("#infosearch").hide();//隐藏放大镜图片
        $(".ztree").outerHeight($(".ztreeWrap").outerHeight());//树的高度设置
        zTreeInitTc();
    });
    $('#qrhj').on('click',
        function () {
            /** 判断是否存在过期项目，并给予提示 **/
            judgeIfExistExpireItem();

            var doctor = $('#doctor').val();
            var nurse = $('#nurse').val();
            var repair = $('#repair').val();
            var isExistYYJItem = isExistTreatItemSort('1'); // 1 是预交金
            var isExistGHItem = isExistTreatItemSort('2');  // 2是护士
            if ((isExistYYJItem != true) && isExistGHItem != true) { // 当不存在预交金和挂号项目时
                // if(static_yjjiem != 1 && static_ghiem !=1){
                if (doctor == "") {
                    layer.alert('请选择医生');
                    return false;
                }
                /* if (nurse == "") {
                    layer.alert('请选择护士'  );
                    return false;
                } */
            }
            if (isExistYYJItem == true) { // 当不存在预交金和挂号项目时
                if (doctor == "") {
                    layer.alert('请选择医生');
                    return false;
                }
            }
            //判断当前挂号如果是种植，必须填写修复医生
            if (static_regObj.regsort && static_regObj.regsort == "18") {
                if (!repair) {
                    layer.alert("种植患者必须填写修复医生");
                    return;
                }
            }
            //判断是否有权限开单 没有权限提示并终止
            if (canKd == 0) {
                if ($("#table tr").length > 1) {
                    layer.confirm('是否已签署知情同意书？', {
                            btn: ['是', '否'] //按钮
                        },
                        function () {
                            editOrder(3);
                        },
                        function () {
                            editOrder(1);
                        });
                } else {
                    layer.alert('请选择收费项目');
                }
            } else {
                layer.alert('无权限，请联系管理员开通权限！');
            }
        });
    $('#tjfyjh').on('click',
        function () {
            /** 判断是否存在过期项目，并给予提示 **/
            judgeIfExistExpireItem();

            if ($("#table tr").length > 1) {
                //费用计划 注：不可添加新的欠费项目；可以保存实际的欠费项目
                var flag = true;
                $('#table').find('tbody').each(function () {
                    $(this).find('tr').each(function () {
                        $(this).find('td').each(function () {
                            if ($(this).index() == 10) {
                                //欠费金额
                                if ($(this).find("span").attr("isqfreal") != "1" && $(this).find("span").html() > 0) {
                                    flag = false;
                                }
                            }
                        });
                    });
                });
                if (flag) {
                    editOrder(0); //正常添加费用计划
                } else {
                    layer.alert('添加费用计划不可添加新的欠费项目！');
                }
            } else {
                layer.alert('请选择收费项目');
            }
        });
    $('#printzqtys').on('click', /** 暂时废弃不用 **/
    function () {
        layer.confirm('确定打印知情同意书并划价？', {
                btn: ['确认', '放弃'] //按钮
            },
            function () {
                editOrder(3);
            });

    });
    $('#qxhj').on('click', // 取消划价
        function () {
            parent.layer.close(frameindex); //再执行关闭 */
        });
    $('#hyxxSelect').on('change',
        function () {
            var seqIdHy = $('#hyxxSelect').val();
            if (seqIdHy != "") {
                var detailurl = contextPath + '/KQDS_MemberAct/selectDetail.act?seqId=' + seqIdHy;
                $.axse(detailurl, null,
                    function (data) {
                        if (data.retState == "0") {
                            var obj = data.data;
                            var zong = Number(obj.money) + Number(obj.givemoney);
                            $('#memberlevel').val(obj.memberlevel);
                            $('#hyzk').val("折扣：" + obj.discount);
                            $('#yue').val("余额：￥" + zong);
                            //getjf(obj.memberno);
                        }
                    },
                    function () {
                        layer.alert('删除失败！');
                    });
            } else {
                $('#hyzk').val("无折扣");
            }

        });
    $('#lzjl').on('click',
        function () {
            layer.open({
                type: 2,
                title: '流转记录',
                maxmin: true,
                shadeClose: true,
                //点击遮罩关闭层
                area: ['80%', '364px'],
                content: contextPath + '/KQDS_BCJLAct/toLzjlWin.act?usercode=' + static_usercode
            });
        });
    $('#wdjl').on('click',
        function () {
            layer.open({
                type: 2,
                title: '网电记录',
                maxmin: true,
                shadeClose: true,
                //点击遮罩关闭层
                area: ['70%', '364px'],
                content: contextPath + '/KQDS_Net_OrderAct/toNetorderList.act?usercode=' + static_usercode
            });
        });
    $('#hfjl').on('click',
        function () {
            layer.open({
                type: 2,
                title: '回访记录',
                maxmin: true,
                shadeClose: true,
                //点击遮罩关闭层
                area: ['70%', '364px'],
                content: contextPath + '/KQDS_VisitAct/toVisitWin.act?usercode=' + static_usercode
            });
        });
    $('#lsbl').on('click',
        function () {
            layer.open({
                type: 2,
                title: '历史病历',
                maxmin: true,
                shadeClose: true,
                //点击遮罩关闭层
                area: ['70%', '364px'],
                content: contextPath + '/KQDS_MedicalRecordAct/toLsblWin.act?usercode=' + static_usercode + '&status=2' // 2是提交病历  1 是暂存病历
            });
        });
    //另存套餐
    $('#lctc').on('click',
        function () {
            /** 判断是否存在过期项目，并给予提示 **/
            judgeIfExistExpireItem();

            var isExistYYJItem = isExistTreatItemSort('1');
            if (isExistYYJItem == true) {
                // if(static_yjjiem == 1){
                layer.alert('包含预交金，不能添加套餐！', {});
                return false;
            }
            var flag = true;
            var qfflag = true;
            var flagzlf = true;
            //循环获取表格中项目
            $('#table').find('tbody').each(function () {
                $(this).find('tr').each(function () {
                    $(this).find('td').each(function () {
                        if ($(this).index() == 11) {
                            if ($(this).find("input").attr("isqfreal") == "1") {
                                flag = false;
                            }
                        }
                        if ($(this).index() == 10) {
                            if (Number($(this).find("span").html()) > 0) {
                                qfflag = false;
                            }
                        }
                        if ($(this).index() == 4) {
                            if ($(this).find("span").html() == "治疗费") {
                                flagzlf = false;
                            }
                        }
                    });
                });
            });
            if (!qfflag) {
                layer.alert('包含欠费单，不能添加套餐！');
                return false;
            }
            if (!flag) {
                layer.alert('包含还款单，不能添加套餐！');
                return false;
            }
            if (!flagzlf) {
                layer.alert('包含治疗费，不能添加套餐！');
                return false;
            }
            layer.prompt({
                    title: '输入套餐类型，并确认',
                    formType: 0
                },
                function (pass, index) {
                    layer.close(index);
                    layer.prompt({
                            title: '套餐类型名称，并确认',
                            formType: 0
                        },
                        function (text, index) {
                            layer.close(index);
                            //判断该套餐类型下是否存在 该套餐
                            if (!YzTcname(pass, text)) {
                                layer.alert('该套餐类型下已存在该套餐名称！', {});
                                return false;
                            }
                            var list = [];
                            $('#table').find('tbody').each(function () {
                                $(this).find('tr').each(function () {
                                    var params = {};
                                    $(this).find('td').each(function () {
                                        if ($(this).index() == 1) {
                                            params.itemno = $(this).text();
                                        } else if ($(this).index() == 3) {
                                            params.istsxm = $(this).text();
                                        } else if ($(this).index() == 4) {
                                            params.itemname = $(this).find("span").html();
                                        } else if ($(this).index() == 5) {
                                            params.unit = $(this).text();
                                        } else if ($(this).index() == 6) {
                                            params.unitprice = $(this).find("input").val();
                                        } else if ($(this).index() == 7) {
                                            params.num = Number($(this).find("input").val());
                                        } else if ($(this).index() == 8) {
                                            params.discount = Number($(this).find("input").val());
                                        } else if ($(this).index() == 9) {
                                            params.subtotal = Number($(this).find("span").html());
                                        } else if ($(this).index() == 10) {
                                            params.arrearmoney = Number($(this).find("span").html());
                                        } else if ($(this).index() == 11) {
                                            params.paymoney = Number($(this).find("input").val());
                                        } else if ($(this).index() == 12) {
                                            params.voidmoney = Number($(this).find("input").val());
                                        }
                                    });
                                    list.push(params);
                                });
                            });
                            var data = JSON.stringify(list);
                            //data = encodeURIComponent(data); //编码 参数里有特殊符号时需要编码
                            //保存
                            var param = {
                                tctype: pass,
                                tcname: text,
                                params: data
                            };
                            var url = contextPath + '/KQDS_TreatItem_TcAct/insertList.act';
                            $.axse(url, param,
                                function (data) {
                                    if (data.retState == "0") {
                                        layer.alert('添加成功');
                                    }
                                },
                                function () {
                                    layer.alert('添加失败！', {});
                                });
                        });
                });
        });

    function YzTcname(tctype, tcname) {
        var param = {
            tctype: tctype,
            tcname: tcname
        }
        var flag = true;
        var url = contextPath + '/KQDS_TreatItem_TcAct/YzCode.act';
        $.axse(url, param,
            function (r) {
                flag = r.data;
            },
            function () {

            });
        return flag;
    }

    /**
     * 删除选中的子节点----删除套餐
     */
    $('#deltc').on('click',
        function () {
            var istc = $('input:radio[id="sftc"]').is(":checked");
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            //选中节点
            var treeNode = treeObj.getCheckedNodes(true);
            if (treeNode && treeNode.length > 0 && istc) {
                layer.confirm('确定删除' + treeNode[0].name + '吗？', {
                        btn: ['删除', '放弃'] //按钮
                    },
                    function () {
                        for (var i = 0; i < treeNode.length; i++) {
                            var treeInfoId = treeNode[i].id;
                            var url = '';
                            if (treeNode[i].isParent) {
                                // 什么都不做
                            } else {
                                url = contextPath + '/KQDS_TreatItem_TcAct/deleteObjBytctype.act?tcnameid=' + treeInfoId;
                                if (!checkUser(treeInfoId)) {
                                    layer.alert('您没有删除权限！', {});
                                    return;
                                }
                            }
                            //判断是否自己创建或者公开套餐
                            //msg=layer.msg('加载中', {icon: 16});
                            $.axse(url, null,
                                function (data) {
                                    if (data.retState == "0") {
                                        layer.alert('删除成功', {});
                                        zTreeInitTc();
                                    }
                                },
                                function () {
                                    layer.alert('删除失败！', {});
                                });
                        }
                    });
            } else {
                layer.alert('请选择需要删除的套餐！');
            }

        });
    //统一打折
    $('#tydz').on('click',
        function () {
            layer.prompt({
                    title: '输入统一折扣，并确认',
                    formType: 0
                },
                function (discount, index) {
                    layer.close(index);
                    //循环获取表格中项目
                    $('#table').find('tbody').each(function () {
                        $(this).find('tr').each(function () {
                            $(this).find('td').each(function () {
                                if ($(this).index() == 8) {
                                    if ($(this).find("input").attr("readonly") != "readonly") {
                                        /** 欠费等特殊项目，不进行打折 **/
                                        //触发change事件
                                        $(this).find("input").val(discount);
                                        $(this).find("input").change();
                                    }
                                }
                            });
                        });
                    });
                });
        });

    function expandTreeNode() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");

        var nodes = $.fn.zTree.getZTreeObj("treeDemo").getNodes();
        for (var i = 0; i < nodes.length; i++) {
            if (nodes[i].open) {
                zTree.expandNode(nodes[i]);
            }
        }
    }

    $(".ztreeWrap").on("click",
        function () {
            event.stopPropagation();
            return false;
        });
    $(document.body).on("click", expandTreeNode);

    //开单权限
    function getButtonPower() {
        for (var i = 0; i < listbutton.length; i++) {
            //console.log("key" + listbutton[i].qxName);
            list += listbutton[i].qxName + ",";
        }
        if (list.length > 0) {
            list = list.substr(0, list.length - 1);
        }
        //console.log(list + "list");
    }
</script>
</html>
