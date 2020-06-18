<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      
<%
	String contextPath = request.getContextPath();
	if (contextPath.equals("")) {
		contextPath = "/kqds";
	}
	//当前修复方案id
	String seqidFather = request.getParameter("seqidFather");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/hudh/lclj/flowdetail/css/base.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/kqdsFront/plugin/bootstrap-datetimepicker.css" />
<link type="text/css" rel="stylesheet" href="<%=contextPath%>/static/css/kqdsFront/plantCase/repairProject.css" />

<script type="text/javascript" src="<%=contextPath%>/static/js/app/plugin/jquery.js"></script>
<script type="text/Javascript" src="<%=contextPath%>/static/js/kqdsFront/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/bootstrap/bootstrap/bootstrap-datetimepicker.zh-CN.js" charset="utf-8" ></script>
<script type="text/javascript" src="<%=contextPath%>/static/plugin/layer-v2.4/layer/layer.js"></script>
</head>
<style type="text/css">
	input::-webkit-input-placeholder{
		font-size: 14px;
		font-weight: normal;
	}
	input:-moz-placeholder{
		font-size: 14px;
		font-weight: normal;
	}
	input::-moz-placeholder{
		font-size: 14px;
		font-weight: normal;
	}
	input:-ms-input-placeholder{
		font-size: 14px;
		font-weight: normal;
	}
	img[src=""],img:not([src]){
      opacity:0;
 	}
 	#logoImg{
	    width: 10%;
	    margin:10px 0 10px 0;
	}
  /*分隔线 */
    .line {
        display: block;
        border-top:2px dotted #776c6c;
        padding:10px 0;
    }
    .gap{
     	margin:10px 0;
    }
	@page{
		size:206mm 280mm;
		margin: 0px auto;
	}
    .zl_signature>input{
        margin-right: 20px;
    }
    .btns>button:focus{
        border:0px solid red;
        outline: none;
    }
    @media print {
        .consent_remark{
            margin-top: 40px !important;
        }
        .zl_signature>span{
            margin-top: 5px;
        }
    }
</style>
<body>
<!--startprint-->
	<div id="repair_continer" class="container-fluid">
		<!-- 标题 -->
		<div class="row restore" style="border-bottom: 2px solid #776c6c;margin-top:20px;">
			<%--<img id="logoImg" src="<%=contextPath%>/static/image/kqdsFront/jiagong/logoName.png">
			<i class="line"></i>--%>
			<div style="padding-bottom: 10px;">
				<span class="bigtitle">修复方案确认单</span>
			</div>
		</div>
		<!-- 患者详细信息 -->
		<div class="row patient" style="border-bottom: 2px solid #776c6c;">
			<div class="col-md-3 col-sm-3 col-xs-3 colDefined">
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import" style="line-height: 60px;">
					<span>患者姓名：</span>
					<input id="patient_name" type="text" disabled="disabled"/>
				</div>
			</div>
			<div class="col-md-3 col-sm-3 col-xs-3 colDefined">
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import" style="line-height: 60px;">
					<span>患者编号：</span>
					<input id="patient_usercode" type="text" disabled="disabled"/>
				</div>
			</div>
			<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import" style="line-height: 60px;">
					<span>性别：</span>
					<input id="patient_sex" type="text" disabled="disabled"/>
				</div>
			</div>
			<div class="col-md-2 col-sm-2 col-xs-2 colDefined">
				<!-- 信息输入组合框 -->
				<div class="rpInfo_import" style="line-height: 60px;">
					<span>年龄：</span>
					<input id="patient_age" type="text" disabled="disabled"/>
				</div>	
			</div>
			<div class="col-md-1 col-sm-1 col-xs-1 colDefined"></div>
		</div>
		<!-- 整体计划恢复牙位 -->
		<div class="entiretybg gap">1.整体计划恢复牙位：</div>
		<div class="row map">
			<div class="col-md-6 col-sm-6 col-xs-6 colDefined">
				<!-- 牙位图 -->
				<div class="zl_toothMapdiv" style="margin:5px 10px;">
					
					<ul class="tooth_map" style="margin-left: 4%;">
						<li>
							<input id="tprgleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="tprgrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="tprgleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="tprgrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
					</ul>
				</div>
			</div>
			<div class="col-md-6 col-sm-6 col-xs-6 colDefined"></div>
		</div>
        
        <div class="overstriking gap">2.本次计划：</div>
        <div style="background: #f5f5f5;">
        <!-- 本次计划 -->
		<div class="row" style="margin:0 10px;">
		<!-- 种植牙位 -->
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
				<!-- 牙位组合 -->
				<div class="rp_toothGroup">
					<span class="overstriking">种植牙位：</span>
					<ul>
						<li>
							<!-- 上下牙位图 -->
							<div class="toothMapdiv_B">
								<span>Dentium</span>
								<ul class="tooth_map">
									<li>
										<input id="dentiumleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="dentiumrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="dentiumleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="dentiumrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
								</ul>
							</div>
						</li>
						<li>
							<!-- 上下牙位图 -->
							<div class="toothMapdiv_B">
								<span>ICX</span>
								<ul class="tooth_map">
									<li>
										<input id="icxleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="icxrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="icxleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="icxrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
								</ul>
							</div>
						</li>
						<li>
							<!-- 上下牙位图 -->
							<div class="toothMapdiv_B">
								<span>Nobel Active</span>
								<ul class="tooth_map">
									<li>
										<input id="nobelactiveleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="nobelactiverightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="nobelactiveleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="nobelactiverightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
								</ul>
							</div>
						</li>
						<li>
							<!-- 上下牙位图 -->
							<div class="toothMapdiv_B">
								<span>Camlog</span>
								<ul class="tooth_map">
									<li class="lodopPrintborder">
										<input id="camlogleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="camlogrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li class="lodopPrintborder">
										<input id="camlogleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="camlogrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
								</ul>
							</div>
						</li>
						<li>
							<!-- 上下牙位图 -->
							<div class="toothMapdiv_B">
								<span>Hiossen</span>
								<ul class="tooth_map">
									<li>
										<input id="hiossenleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="hiossenrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="hiossenleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="hiossenrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
								</ul>
							</div>
						</li>
						<li>
							<!-- 上下牙位图 -->
							<div class="toothMapdiv_B">
								<span>Nobel</span>
								<ul class="tooth_map">
									<li>
										<input id="nobelpmcleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="nobelpmcrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="nobelpmcleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="nobelpmcrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
								</ul>
							</div>
						</li>
						<li>
							<!-- 上下牙位图 -->
							<div class="toothMapdiv_B">
								<span>Zimmer</span>
								<ul class="tooth_map">
									<li>
										<input id="zimmerleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="zimmerrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="zimmerleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="zimmerrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
								</ul>
							</div>
						</li>
					</ul>
				</div>
			</div>
			</div>
		</div>
		<!-- 临时修复 -->
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
				<!-- 牙位组合 -->
				<div class="rp_toothGroup" style="margin:0 10px;">
					<span class="overstriking">临时修复：</span>
					<ul>
						<li>
							<!-- 上下牙位图 -->
							<div class="toothMapdiv_B">
								<span>胶托活动牙</span>
								<ul class="tooth_map">
									<li>
										<input id="rbrdleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="rbrdrigthup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="rbrdleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="rbrdrigthdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
								</ul>
							</div>
						</li>
						<li>
							<!-- 上下牙位图 -->
							<div class="toothMapdiv_B">
								<span>隐形义齿</span>
								<ul class="tooth_map">
									<li>
										<input id="invidenleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="invidenrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="invidenleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="invidenrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
								</ul>
							</div>
						</li>
						<li>
							<!-- 上下牙位图 -->
							<div class="toothMapdiv_B">
								<span>locator</span>
								<ul class="tooth_map">
									<li>
										<input id="locatorleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="locatorrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="locatorleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="locatorrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
								</ul>
							</div>
						</li>
						<li>
							<!-- 上下牙位图 -->
							<div class="toothMapdiv_B">
								<span>螺丝固位</span>
								<ul class="tooth_map">
									<li class="lodopPrintborder">
										<input id="srrleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="srrrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li class="lodopPrintborder">
										<input id="srrleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="srrrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
								</ul>
							</div>
						</li>
						<li>
							<!-- 上下牙位图 -->
							<div class="toothMapdiv_B">
								<span>保持器</span>
								<ul class="tooth_map">
									<li>
										<input id="retainerleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="retainerrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="retainerleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
									<li>
										<input id="retainerrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
									</li>
								</ul>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
        </div>
        <div class="overstriking gap">最终修复：</div>
		<div class="row" style="background: #f5f5f5;padding:0 16px;">
<!-- 			<div class="col-md-12 col-sm-12 col-xs-12 colDefined">
				
			</div> -->
		
		<!-- 牙位图加多选框 -->
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12 colDefined" style="margin: 14px 7px;">
				<!-- 牙位图 -->
				<div class="zl_toothMapdiv">
				<div class="col-md-12 col-sm-12 col-xs-12 colDefined" style="width:100%;padding:0px;">
				<!-- 多选框 -->
				<ul class="moreSelect_div">
				    <li class="distance"><label><span class="overstriking">1.全口/半口半固定</span></label></li>
					<li style="width:80px;"><label><input type="checkbox" name="singlecompselect" value="塑钢牙PVC"/><span>塑钢牙PVC</span></label></li>
					<li style="width:80px;"><label><input type="checkbox" name="singlecompselect" value="树脂牙"/><span>树脂牙</span></label></li>
					<li style="width:120px;"><label><input type="checkbox" name="singlecompselect" value="钴铬支架Co-Cr"/><span>钴铬支架Co-Cr</span></label></li>
					<li style="width:80px;"><label><input type="checkbox" name="singlecompselect" value="纯钛支架"/><span>纯钛支架</span></label></li>
					<li style="width:100px;"><label><input type="checkbox" name="singlecompselect" value="维他灵支架"/><span>维他灵支架</span></label></li>
					<li style="width:80px;"><label><input type="checkbox" name="singlecompselect" value="其他"/><span>其他</span></label></li>
				</ul>
			</div>
					<!-- <div class="overstriking">1.全口/半口半固定</div> -->
					<ul class="tooth_map" style="width: 30%;margin-left: 3%;">
						<li>
							<input id="singlecompleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="singlecomprightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="singlecompleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="singlecomprightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
					</ul>
				</div>
			</div>
			<!-- <div class="col-md-6 col-sm-6 col-xs-6 colDefined" style="margin-bottom: 10px;"></div> -->

		</div>
		<!-- 牙位图加多选框 -->
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12 colDefined" style="margin-bottom: 5px;">
				<!-- 牙位图 -->
				<div class="zl_toothMapdiv">
			<div class="col-md-12 col-sm-12 col-xs-12 colDefined" style="width:100%;padding:0px;"> 
				<!-- 多选框 -->
				<ul class="moreSelect_div">
				     <li class="distance"><label><span>2.全口/半口 固定</span></label></li>
					<li><label><input type="checkbox" name="sfsincomselect" value="聚合瓷牙"/><span>聚合瓷牙</span></label></li>
					<li><label><input type="checkbox" name="sfsincomselect" value="塑钢牙PVC"/><span>塑钢牙PVC</span></label></li>
					<li><label><input type="checkbox" name="sfsincomselect" value="全瓷牙"/><span>全瓷牙</span></label></li>
					<li><label><input type="checkbox" name="sfsincomselect" value="树脂牙"/><span>树脂牙</span></label></li>
					<li><label><input type="checkbox" name="sfsincomselect" value="纯钛支架"/><span>纯钛支架</span></label></li>
					<li><label><input type="checkbox" name="sfsincomselect" value="其他"/><span>其他</span></label></li>
				</ul>
			</div>
					
					<ul class="tooth_map" style="width: 30%;margin-left: 3%; ">
						<li>
							<input id="sfsincomleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="sfsincomrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="sfsincomleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="sfsincomrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
					</ul>
				</div>
			</div>
			

		</div>
		<!-- 牙位图加多选框 -->
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12 colDefined" style="margin-bottom: 5px;">
				<!-- 牙位图 -->
				<div class="zl_toothMapdiv">
	        	<div class="col-md-12 col-sm-12 col-xs-12 colDefined" style="width:100%;padding:0px;">
				<!-- 多选框 -->
				<ul class="moreSelect_div">
				    <li class="distance"><label><span>3.局部</span></label></li>
					<li><label><input type="checkbox" name="localplantselect" value="钴铬烤瓷"/><span>钴铬烤瓷</span></label></li>
					<li><label><input type="checkbox" name="localplantselect" value="聚合瓷"/><span>聚合瓷</span></label></li>
					<li><label><input type="checkbox" name="localplantselect" value="国产氧化锆"/><span>国产氧化锆</span></label></li>
					<li><label><input type="checkbox" name="localplantselect" value="Lava"/><span>Lava</span></label></li>
					<li><label><input type="checkbox" name="localplantselect" value="泽康氧化锆"/><span>泽康氧化锆</span></label></li>
					<li><label><input type="checkbox" name="localplantselect" value="威兰德锆"/><span>威兰德锆</span></label></li>
					<li><label><input type="checkbox" name="localplantselect" value="杜莎拉姆"/><span>杜莎拉姆</span></label></li>
					<li><label><input type="checkbox" name="localplantselect" value="瑞典瓷"/><span>瑞典瓷</span></label></li>
					<li><label><input type="checkbox" name="localplantselect" value="其他"/><span>其他</span></label></li>
				</ul>
			</div>
					<ul class="tooth_map" style="width: 30%;margin-left: 3%;">
						<li>
							<input id="localplantleftup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="localplantrightup" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="localplantleftdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
						<li>
							<input id="localplantrightdown" onblur="TextLengthCheck(this.id,10);" class="tooth_input" type="text">
						</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- 修复相关需求 -->
		<div class="row">
			<!-- 修复相关需求 -->
			<div class="consent_remark">
				<div class="overstriking" style="margin: 0 10px;">修复相关需求:</div>
				<textarea id="requirerestor" rows="" cols="" autoHeight="true" style="border: 1px solid #ddd;margin:15px 10px 5px 10px;overflow-y: hidden;"></textarea>
			</div>
		</div>
		<!-- 签名 -->
		<!-- 签名 -->
		
	</div>

        <div class="row">
            <!-- <div class="col-md-3 col-sm-3 col-xs-3 colDefined">
                <div class="zl_signature">
                    <span>客服：</span>
                    <div id="servicesignature"></div>
                    <input id="servicetime" type="text" class="consent_time signature_time"/>
                </div>
            </div> -->
            <div class="col-md-4 col-sm-4 col-xs-4 colDefined">
                <div class="zl_signature" style="display: flex;justify-content: space-between;">
                    <span id="doctorSignature" style="display: inline;">手术医生：</span>
                    <img id="img" style="display: inline-block;height: 30px;"/>
                    <input id="operationdoctortime" type="text" class="consent_time signature_time inputheight2" readonly="readonly" placeholder="请选择日期" style="width:20%;min-width: 70px;"/>
                </div>
            </div>
            <div class="col-md-4 col-sm-4 col-xs-4 colDefined">
                <div class="zl_signature" style="display: flex;justify-content: space-between;">
                    <span id="repairDoctorSignature" style="display: inline;">修复医生：</span>
                    <img id="repairImg" style="display: inline-block;height: 30px;"/>
                    <input id="doctortime" type="text" class="consent_time signature_time inputheight2" readonly="readonly" placeholder="请选择日期" style="width:20%;min-width: 70px;"/>
                </div>
            </div>
            <div class="col-md-4 col-sm-4 col-xs-4 colDefined">
                <div class="zl_signature" style="display: flex;justify-content: space-between;">
                    <span id="patientSignature" style="display: inline;">患者签名：</span>
                    <img id="patientimg" style="display: inline-block;height: 30px;"/>
                    <input id="patienttime" type="text" class="consent_time signature_time inputheight2" readonly="readonly" placeholder="请选择日期" style="width:20%;min-width: 70px;"/>
                </div>
            </div>
        </div>
	</div>
	<!--endprint-->
	<!-- 按钮 -->
	<div class="btns">
		<button id="consent_saveBtn" onclick="save()">保存</button>
		<button id="consent_updateBtn" style="display: none;" class="consent_updateBtn hidden" onclick="update()">修改表单</button>
		<button id="print_Btn" onclick="myPreviewAll()">打印本页内容</button>
	</div>

</body>

<script language="javascript"  src="<%=contextPath%>/static/js/kqdsFront/LodopFuncs.js"></script>
<script type="text/javascript">
		var repairSignature="";
		var repairdoctorstatus=true;
		var signature="";
		var doctorstatus=true;
		var patientsignature="";
		var patientstatus=true;
		var contextPath = "<%=contextPath%>";
		var id= window.parent.consultSelectPatient.seqid;	//选中患者id
		var order_number= window.parent.consultSelectPatient.orderNumber;//选中患者order_number
		var caseId=""; //已存在的病历id
		var menuid=window.parent.menuid;//左侧菜单id
		var seqidFather = "<%=seqidFather%>";
		$(function(){
			//时间选择
		    $(".consent_time").datetimepicker({
		        language:  'zh-CN',  
		   		minView: 2,
		        format: 'yyyy-mm-dd',
		   		autoclose : true,//选中之后自动隐藏日期选择框   
		   		pickerPosition: "top-right",
		   		todayBtn: true,
			   	beforeShow: function () {
			         setTimeout(
				         function () {
				             $('#ui-datepicker-div').css("z-index", 21);
				         }, 100
			         );
			    }
		    });
			
		    //患者姓名、年龄、性别赋值
			$("#patient_name").attr("value",window.parent.consultSelectPatient.username);
			$("#patient_sex").attr("value",window.parent.consultSelectPatient.sex);
			$("#patient_age").attr("value",window.parent.consultSelectPatient.age);
			$("#patient_usercode").attr("value",window.parent.consultSelectPatient.usercode);
		    /* for(var key in window.parent.consultSelectPatient){
		    	console.log(key+"------数据显示------"+window.parent.consultSelectPatient[key]);
		    } */
		    
			initZzblInfor();
			// 2019/7/24 lutian 禁止页面拖拽
			document.ondragstart = function() {
	            return false;
	        };
			//textarea高度自适应
			$.fn.autoHeight = function(){
				function autoHeight(elem){
					elem.style.height = 'auto';
					elem.scrollTop = 0; //防抖动
					elem.style.height = elem.scrollHeight + 'px';
				}
				this.each(function(){
					autoHeight(this);
					$(this).on('keyup', function(){
						autoHeight(this);
					});
				});
			}
			$('textarea[autoHeight]').autoHeight();

		});
		var doctorSignature = document.getElementById("doctorSignature");    
		doctorSignature.onclick = function(){ 
			if(doctorstatus){
				layer.open({
			        type: 2,
			        title: '签字',
			        shadeClose: true,
			        shade: 0.6,
			        area: ['70%', '65%'],
			        content: contextPath + '/SignatureAct/toSignature.act?category=种植'
			    });
			}
	   }
		function addSignature(){
			$("#img").css("display","");
			$("#img").attr('src', signature);
			if(!repairdoctorstatus&&doctorstatus){
				updateOperationDoctorsignature();
			}
		}
		var repairDoctorSignature = document.getElementById("repairDoctorSignature");    
		repairDoctorSignature.onclick = function(){ 
			if(repairdoctorstatus){
				layer.open({
			        type: 2,
			        title: '签字',
			        shadeClose: true,
			        shade: 0.6,
			        area: ['70%', '65%'],
			        content: contextPath + '/SignatureAct/toSignature.act?category=修复'
			    });
			}
	   }
		function addRepairSignature(){
			$("#repairImg").css("display","");
			$("#repairImg").attr('src', repairSignature);
			if(repairdoctorstatus&&!doctorstatus){
				updateRepairSignature();
			}
		}
		var patientSignature = document.getElementById("patientSignature");    
		patientSignature.onclick = function(){ 
			if(patientstatus){
				layer.open({
			        type: 2,
			        title: '签字',
			        shadeClose: true,
			        shade: 0.6,
			        area: ['70%', '65%'],
			        content: contextPath + '/SignatureAct/toSignature.act?category=患者'
			    });
			}
	   }
		function addPatientSignature(){
			$("#patientimg").css("display","");
			$("#patientimg").attr('src', patientsignature);
			if(!doctorstatus&&patientstatus&&!repairdoctorstatus||!doctorstatus&&patientstatus&&repairdoctorstatus||doctorstatus&&patientstatus&&!repairdoctorstatus){
				updatePatientSignature();
			}
		}
		
		
		/* 2019/7/16 lutian input文字长度校验方法   obj：元素id  textNum：限制文字长度 */
		function TextLengthCheck(obj,textNum){
			var objTextVal=$("#"+obj).val();
			var checkTitleBefore=$("#"+obj).parent(".common_style").find("span").text();//根据父元素的选择器找到标题
			var checkTitle=checkTitleBefore.substring(0,checkTitleBefore.indexOf(":")); // 校验文字长长度的标题
			if(objTextVal.length>textNum){
				$("#"+obj).attr("maxlength",textNum);
				//layer.alert(checkTitle+"文字长度不能超过"+textNum+"字!");
				layer.open({
					 title: '提示',
					 content: checkTitle+'文字长度不能超过'+textNum+'字!',
					 end:function(){
						 var inputNewVal=$("#"+obj).val();
						 $("#"+obj).val(inputNewVal.substring(0,textNum)).focus();
					 }
				});
				return;
			}
		}
		
		function initZzblInfor(){
			var url = contextPath + '/HUDH_RepairSchemeConfirmAct/findRepairInforById.act';
			$.ajax({
				url: url,
				type:"POST",
				dataType:"json",
				data : {
					 id :  id, //临床路径ID
					 order_number : order_number
				},
				success:function(result){
					//console.log(JSON.stringify(result)+"--------------添加成功后查询数据");
					//caseId=result.seqId;  //病历id
					var result;
					if(seqidFather){
						for (var i=0;i<result.length;i++) {
							if(seqidFather==result[i].seqId){
								result=result[i];
							}
						}
					}
					caseId=seqidFather;  //修改病历id
					/* 判断是否已经填写过内容 */
					if(result.id){
						$("#consent_saveBtn").css("display","none");//隐藏保存按钮
						$("#consent_updateBtn").css("display","inline-block");//显示修改按钮
						//赋值 
						for(var key in result){
							//console.log(key+"-------------"+result[key]);
							$("#"+key).attr("value",result[key]);// 填框赋值
							$("#requirerestor").text(result["requirerestor"]);//textarea赋值
							$("#requirerestor").trigger("keyup");
							if(result[key].indexOf(";")>0){
								var checkboxVal= result[key];//拼接多选框的值
								var checkboxValArr=checkboxVal.split(";");//将字符串转为数组
								for(var i=0;i<checkboxValArr.length;i++){
									$("input[name="+key+"]").each(function(){
										if($(this).val()==checkboxValArr[i]){
										   $(this).attr("checked","checked");
										}
									})
								}
							} 
							//牙位图赋值
							if(result[key].indexOf(",")>0){
								var toothPlaceVal= result[key];//拼接多选框的值
								var toothPlaceValArr=toothPlaceVal.split(",");//将字符串转为数组
								var newtoothPlaceVal=toothPlaceValArr.join("");
								//console.log(newtoothPlaceVal+"---------去掉牙位图逗号"); 
								$("#"+key).attr("value",newtoothPlaceVal);// 填框赋值
							}
						}
						//$("input").attr("disabled","disabled");//查看信息的时候禁止在填写
						signature=result.operationdoctorsignature;
						if(signature!=""){
							$("#img").attr('src', signature);
							doctorstatus=false;
						}else{
							$("#img").attr('display', 'none');
						}
						repairSignature=result.repairdoctorsignature;
						if(repairSignature!=""){
							$("#repairImg").attr('src', repairSignature);
							repairdoctorstatus=false;
						}else{
							$("#repairImg").attr('display', 'none');
						}
						patientsignature=result.patientsignature;
						if(patientsignature!=""){
							$("#patientimg").attr('src', patientsignature);
							patientstatus=false;
						}else{
							$("#patientimg").attr('display', 'none');
						}
					}
					//获取当前页面所有按钮
					getButtonAllCurPage(menuid);
				}
		  });
		}
		
		//获取url中的参数
		function getUrlParam(name) {
		    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
		    if (r != null) return unescape(r[2]); 
		    return null; //返回参数值
		}
		
		//获取全口/半口半固定
		function showSingleCompSelect() {
		    var obj = document.getElementsByName("singlecompselect");
		    //alert(obj);
		    var singleCompSelect = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	singleCompSelect = singleCompSelect + obj[k].value + ';';
		    }
		    return singleCompSelect;
		}
		
		//获取全口/半口 固定
		function showSfSinComSelect() {
		    var obj = document.getElementsByName("sfsincomselect");
		    var sfSinComSelect = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	sfSinComSelect = sfSinComSelect + obj[k].value + ';';
		    }
		    return sfSinComSelect;
		}
		
		//获取局部 
		function showLocalPlantSelect(){
		    var obj = document.getElementsByName("localplantselect");
		    var localPlantSelect = "";
		    for ( k in obj ) {
		        if(obj[k].checked)
		        	localPlantSelect = localPlantSelect + obj[k].value + ';';
		    }
		    return localPlantSelect;
		}
		//更新
		function updatePatientSignature(){
			var url = contextPath + '/HUDH_RepairSchemeConfirmAct/updateRepairInforById.act';
			var patienttime = $("#patienttime").val();//修复医生签名时间
	        var param = {
	        		seqId:  caseId, //临床路径ID
	        		patientsignature :  patientsignature,//患者签名
	        		patienttime : patienttime//患者签名时间

	        };
	        $.axseSubmit(url, param,function() {},function(r) {
	        	layer.alert("修改成功！", {
		            end: function() {
		            	window.location.reload(); //刷新父页面
		                /* var frameindex = parent.layer.getFrameIndex(window.name);
		                parent.layer.close(frameindex); //再执行关闭 */
		            }
		      	});
	        },function(r){
	        	layer.alert("修改失败！");
		    });
		}	
		//更新
		function updateRepairSignature(){
			var url = contextPath + '/HUDH_RepairSchemeConfirmAct/updateRepairInforById.act';
			var doctorTime = $("#doctortime").val();//修复医生签名时间
	        var param = {
	        		seqId :  caseId, //临床路径ID
	        		repairDoctorsignature : repairSignature,//修复医生签名
	        		doctorTime : doctorTime//修复医生签名时间

	        };
	        $.axseSubmit(url, param,function() {},function(r) {
	        	layer.alert("修改成功！", {
		            end: function() {
		            	window.location.reload(); //刷新父页面
		                /* var frameindex = parent.layer.getFrameIndex(window.name);
		                parent.layer.close(frameindex); //再执行关闭 */
		            }
		      	});
	        },function(r){
	        	layer.alert("修改失败！");
		    });
		}
		//更新
		function updateOperationDoctorsignature(){
			var url = contextPath + '/HUDH_RepairSchemeConfirmAct/updateRepairInforById.act';
			var operationDoctorTime = $("#operationdoctortime").val();//手术医生签名时间
	        var param = {
	        		seqId :  caseId, //临床路径ID
	        		operationDoctorsignature : signature,//手术医生签名
	        		operationDoctorTime : operationDoctorTime//手术医生签名时间
	        };
	        $.axseSubmit(url, param,function() {},function(r) {
	        	layer.alert("修改成功！", {
		            end: function() {
		            	window.location.reload(); //刷新父页面
		                /* var frameindex = parent.layer.getFrameIndex(window.name);
		                parent.layer.close(frameindex); //再执行关闭 */
		            }
		      	});
	        },function(r){
	        	layer.alert("修改失败！");
		    });
		}
		
		//更新
		function update(){
			var patient_name = $("#patient_name").val();//患者姓名
			var patient_sex = $("#patient_sex").val();//患者性别
			var patient_age = $("#patient_age").val();//患者年龄
			var singleCompSelect = showSingleCompSelect();
			//alert(singleCompSelect);
			var tprgLeftUp = $("#tprgleftup").val();
			var tprgRightUp = $("#tprgrightup").val();
			var tprgRightDown = $("#tprgrightdown").val();
			var tprgLeftDown = $("#tprgleftdown").val();
			var sfSinComSelect = showSfSinComSelect();
			//alert(sfSinComSelect);
//			var temporDentCondition = showTemporDentCondition();
			var dentiumLeftUp = $("#dentiumleftup").val();
			var dentiumRightUp = $("#dentiumrightup").val();
			var dentiumRightDown = $("#dentiumrightdown").val();
			var dentiumLeftDown = $("#dentiumleftdown").val();
			var localPlantSelect = showLocalPlantSelect();
			//alert(localPlantSelect);
			var icxLeftUp = $("#icxleftup").val(); 
			var icxRightUp = $("#icxrightup").val(); 
			var icxLeftDown = $("#icxleftdown").val(); 
			var icxRightDown = $("#icxrightdown").val(); 
//			var implantAssistantSurgery = showImplantAssistantSurgery();
			var nobelActiveLeftUp = $("#nobelactiveleftup").val(); 
			var nobelActiveRightUp = $("#nobelactiverightup").val(); 
			var nobelActiveLeftDown = $("#nobelactiveleftdown").val(); 
			var nobelActiveRightDown = $("#nobelactiverightdown").val(); 
//			var largeParticle = $("#largeParticle").val();
			var camlogLeftUp = $("#camlogleftup").val();
			var camlogRightUp = $("#camlogrightup").val();
			var camlogLeftDown = $("#camlogleftdown").val();
			var camlogRightDown = $("#camlogrightdown").val();
//			var smallParticle = $("#smallParticle").val();
			var hiossenLeftUp = $("#hiossenleftup").val();
			var hiossenRightUp = $("#hiossenrightup").val();
			var hiossenLeftDown = $("#hiossenleftdown").val();
			var hiossenRightDown = $("#hiossenrightdown").val();
			//Nobel-PMC
			var nobelpmcleftup = $("#nobelpmcleftup").val();
			var nobelpmcrightup = $("#nobelpmcrightup").val();
			var nobelpmcleftdown = $("#nobelpmcleftdown").val();
			var nobelpmcrightdown = $("#nobelpmcrightdown").val();
			//Zimmer
			var zimmerleftup = $("#zimmerleftup").val();
			var zimmerrightup = $("#zimmerrightup").val();
			var zimmerleftdown = $("#zimmerleftdown").val();
			var zimmerrightdown = $("#zimmerrightdown").val();
//			var boneCollagen = $("#boneCollagen").val();
			var rbrdLeftUp = $("#rbrdleftup").val();
			var rbrdRigthUp = $("#rbrdrigthup").val();
			var rbrdLeftDown = $("#rbrdleftdown").val();
			var rbrdRigthDown = $("#rbrdrigthdown").val();
//			var tianBo = $("#tianBo").val();
			var inviDenLeftUp = $("#invidenleftup").val();
			var inviDenRightUp = $("#invidenrightup").val();
			var inviDenLeftDown = $("#invidenleftdown").val();
			var inviDenRightDown = $("#invidenrightdown").val();
//			var collagenMembBig = $("#collagenMembBig").val();
			var locatorLeftUp = $("#locatorleftup").val();
			var locatorRightUp = $("#locatorrightup").val();
			var locatorRightDown = $("#locatorrightdown").val();
			var locatorLeftDown = $("#locatorleftdown").val();
			var requireRestor = $("#requirerestor").val();
			var srrLeftUp = $("#srrleftup").val();
			var srrRightUp = $("#srrrightup").val();
			var srrLeftDown = $("#srrleftdown").val();
			var srrRightDown = $("#srrrightdown").val();
//			var titaniumMesh = $("#titaniumMesh").val();
			var retainerLeftUp = $("#retainerleftup").val();
			var retainerRightUp = $("#retainerrightup").val();
			var retainerLeftDown = $("#retainerleftdown").val();
			var retainerRightDown = $("#retainerrightdown").val();
			
			//新增参数
			var servicesignature = $("#servicesignature").val();//客服签名
			var serviceTime = $("#servicetime").val();//客服签名时间
			/* var operationDoctorsignature = $("#operationDoctorsignature").val();//手术医生签名 */
			var operationDoctorTime = $("#operationdoctortime").val();//手术医生签名时间
			/* var repairDoctorsignature = $("#repairDoctorsignature").val();//修复医生签名 */
			var doctorTime = $("#doctortime").val();//修复医生签名时间
			/* var patientsignature = $("#patientsignature").val();//患者签名 */
			var patientTime = $("#patienttime").val();//患者签名时间
			
			var singleCompLeftUp=$("#singlecompleftup").val();
			var singleCompLeftDown=$("#singlecompleftdown").val();
			var singleCompRightUp=$("#singlecomprightup").val();
			var singleCompRightDown=$("#singlecomprightdown").val();
			var sfSinComLeftUp=$("#sfsincomleftup").val();
			var sfSinComLeftDown=$("#sfsincomleftdown").val();
			var sfSinComRightUp=$("#sfsincomrightup").val();
			var sfSinComRightDown=$("#sfsincomrightdown").val();
			var localPlantLeftUp=$("#localplantleftup").val();
			var localPlantLeftDown=$("#localplantleftdown").val();
			var localPlantRightUp=$("#localplantrightup").val();
			var localPlantRightDown=$("#localplantrightdown").val();
			
			var url = contextPath + '/HUDH_RepairSchemeConfirmAct/updateRepairInforById.act';
	        var param = {
	        		seqId :  caseId, //临床路径ID
	        		order_number :  order_number, //节点编号
	        		username :patient_name,
		        	sex : patient_sex,
		        	age : patient_age,
	        		tprgLeftUp : tprgLeftUp,
	        		tprgRightUp : tprgRightUp,
	        		tprgRightDown : tprgRightDown,
	        		tprgLeftDown : tprgLeftDown,
	        		requireRestor : requireRestor,
	        		singlecompselect : singleCompSelect,
	        		localPlantSelect : localPlantSelect,
	        		sfSinComSelect : sfSinComSelect,
	        		dentiumLeftUp : dentiumLeftUp,
	        		dentiumRightUp : dentiumRightUp,
	        		dentiumRightDown : dentiumRightDown,
	        		dentiumLeftDown : dentiumLeftDown,
	        		icxLeftUp : icxLeftUp,
	        		icxRightUp : icxRightUp,
	        		icxLeftDown : icxLeftDown,
	        		icxRightDown : icxRightDown,
	        		nobelActiveLeftUp : nobelActiveLeftUp,
	        		nobelActiveRightUp : nobelActiveRightUp,
	        		nobelActiveLeftDown : nobelActiveLeftDown,
	        		nobelActiveRightDown : nobelActiveRightDown,
	        		camlogLeftUp : camlogLeftUp,
	        		camlogRightUp : camlogRightUp,
	        		camlogLeftDown : camlogLeftDown,
	        		camlogRightDown : camlogRightDown,
	        		hiossenLeftUp : hiossenLeftUp,
	        		hiossenRightUp : hiossenRightUp,
	        		hiossenLeftDown : hiossenLeftDown,
	        		hiossenRightDown : hiossenRightDown,
	        		nobelpmcleftup : nobelpmcleftup,
	        		nobelpmcleftdown : nobelpmcleftdown,
	        		nobelpmcrightup : nobelpmcrightup,
	        		nobelpmcrightdown : nobelpmcrightdown,
	        		zimmerleftup : zimmerleftup,
	        		zimmerleftdown : zimmerleftdown,
	        		zimmerrightup : zimmerrightup,
	        		zimmerrightdown :  zimmerrightdown,
	        		rbrdLeftUp : rbrdLeftUp,
	        		rbrdRigthUp : rbrdRigthUp,
	        		rbrdLeftDown : rbrdLeftDown,
	        		rbrdRigthDown : rbrdRigthDown,
	        		inviDenLeftUp : inviDenLeftUp,
	        		inviDenRightUp : inviDenRightUp,
	        		inviDenLeftDown : inviDenLeftDown,
	        		inviDenRightDown : inviDenRightDown,
	        		locatorLeftUp : locatorLeftUp,
	        		locatorRightUp : locatorRightUp,
	        		locatorRightDown : locatorRightDown,
	        		locatorLeftDown : locatorLeftDown,
	        		srrLeftUp : srrLeftUp,
	        		srrRightUp : srrRightUp,
	        		srrLeftDown : srrLeftDown,
	        		srrRightDown : srrRightDown,
	        		retainerLeftUp : retainerLeftUp,
	        		retainerRightUp : retainerRightUp,
	        		retainerLeftDown : retainerLeftDown,
	        		retainerRightDown : retainerRightDown,
	        		//新增参数
	        		servicesignature : servicesignature,//客服签名
	        		serviceTime : serviceTime,//客服签名时间
	        		operationDoctorsignature : signature,//手术医生签名
	        		operationDoctorTime : operationDoctorTime,//手术医生签名时间
	        		repairDoctorsignature : repairSignature,//修复医生签名
	        		doctorTime : doctorTime,//修复医生签名时间
	        		patientsignature : patientsignature,//患者签名
	        		patientTime : patientTime,//患者签名时间
	        		
	        		singlecompleftup : singleCompLeftUp,
	        		singlecompleftdown : singleCompLeftDown,
	        		singlecomprightup : singleCompRightUp,
	        		singlecomprightdown : singleCompRightDown,
	        		sfsincomleftup : sfSinComLeftUp,
	        		sfsincomleftdown : sfSinComLeftDown,
	        		sfsincomrightup : sfSinComRightUp,
	        		sfsincomrightdown : sfSinComRightDown,
	        		localplantleftup : localPlantLeftUp,
	        		localplantleftdown : localPlantLeftDown,
	        		localplantrightup : localPlantRightUp,
	        		localplantrightdown : localPlantRightDown
	        };
	        //console.log(JSON.stringify(param)+"---------修复传参");

	        $.axseSubmit(url, param,function() {},function(r) {
	        	layer.alert("修改成功！", {
		            end: function() {
		            	//window.parent.location.reload(); //刷新父页面
		                var frameindex = parent.layer.getFrameIndex(window.name);
		                parent.layer.close(frameindex); //再执行关闭
		            }
		      	});
	        },function(r){
	        	layer.alert("修改失败！");
		    });
		}
		
		//保存
		function save() {
			var patient_name = $("#patient_name").val();//患者姓名
			var patient_sex = $("#patient_sex").val();//患者性别
			var patient_age = $("#patient_age").val();//患者年龄
			var singleCompSelect = showSingleCompSelect();
			//alert(singleCompSelect);
			var tprgLeftUp = $("#tprgleftup").val();
			var tprgRightUp = $("#tprgrightup").val();
			var tprgRightDown = $("#tprgrightdown").val();
			var tprgLeftDown = $("#tprgleftdown").val();
			var sfSinComSelect = showSfSinComSelect();
			//alert(sfSinComSelect);
//			var temporDentCondition = showTemporDentCondition();
			var dentiumLeftUp = $("#dentiumleftup").val();
			var dentiumRightUp = $("#dentiumrightup").val();
			var dentiumRightDown = $("#dentiumrightdown").val();
			var dentiumLeftDown = $("#dentiumleftdown").val();
			var localPlantSelect = showLocalPlantSelect();
			//alert(localPlantSelect);
			var icxLeftUp = $("#icxleftup").val(); 
			var icxRightUp = $("#icxrightup").val(); 
			var icxLeftDown = $("#icxleftdown").val(); 
			var icxRightDown = $("#icxrightdown").val(); 
//			var implantAssistantSurgery = showImplantAssistantSurgery();
			var nobelActiveLeftUp = $("#nobelactiveleftup").val(); 
			var nobelActiveRightUp = $("#nobelactiverightup").val(); 
			var nobelActiveLeftDown = $("#nobelactiveleftdown").val(); 
			var nobelActiveRightDown = $("#nobelactiverightdown").val(); 
//			var largeParticle = $("#largeParticle").val();
			var camlogLeftUp = $("#camlogleftup").val();
			var camlogRightUp = $("#camlogrightup").val();
			var camlogLeftDown = $("#camlogleftdown").val();
			var camlogRightDown = $("#camlogrightdown").val();
//			var smallParticle = $("#smallParticle").val();
			var hiossenLeftUp = $("#hiossenleftup").val();
			var hiossenRightUp = $("#hiossenrightup").val();
			var hiossenLeftDown = $("#hiossenleftdown").val();
			var hiossenRightDown = $("#hiossenrightdown").val();
			//Nobel-PMC
			var nobelpmcleftup = $("#nobelpmcleftup").val();
			var nobelpmcrightup = $("#nobelpmcrightup").val();
			var nobelpmcleftdown = $("#nobelpmcleftdown").val();
			var nobelpmcrightdown = $("#nobelpmcrightdown").val();
			//Zimmer
			var zimmerleftup = $("#zimmerleftup").val();
			var zimmerrightup = $("#zimmerrightup").val();
			var zimmerleftdown = $("#zimmerleftdown").val();
			var zimmerrightdown = $("#zimmerrightdown").val();
//			var boneCollagen = $("#boneCollagen").val();
			var rbrdLeftUp = $("#rbrdleftup").val();
			var rbrdRigthUp = $("#rbrdrigthup").val();
			var rbrdLeftDown = $("#rbrdleftdown").val();
			var rbrdRigthDown = $("#rbrdrigthdown").val();
//			var tianBo = $("#tianBo").val();
			var inviDenLeftUp = $("#invidenleftup").val();
			var inviDenRightUp = $("#invidenrightup").val();
			var inviDenLeftDown = $("#invidenleftdown").val();
			var inviDenRightDown = $("#invidenrightdown").val();
//			var collagenMembBig = $("#collagenMembBig").val();
			var locatorLeftUp = $("#locatorleftup").val();
			var locatorRightUp = $("#locatorrightup").val();
			var locatorRightDown = $("#locatorrightdown").val();
			var locatorLeftDown = $("#locatorleftdown").val();
			var requireRestor = $("#requirerestor").val();
			var srrLeftUp = $("#srrleftup").val();
			var srrRightUp = $("#srrrightup").val();
			var srrLeftDown = $("#srrleftdown").val();
			var srrRightDown = $("#srrrightdown").val();
//			var titaniumMesh = $("#titaniumMesh").val();
			var retainerLeftUp = $("#retainerleftup").val();
			var retainerRightUp = $("#retainerrightup").val();
			var retainerLeftDown = $("#retainerleftdown").val();
			var retainerRightDown = $("#retainerrightdown").val();
			
			//新增参数
			var servicesignature = $("#servicesignature").val();//客服签名
			var serviceTime = $("#servicetime").val();//客服签名时间
			//var operationDoctorsignature = $("#operationDoctorsignature").val();//手术医生签名
			var operationDoctorTime = $("#operationdoctortime").val();//手术医生签名时间
			//var repairDoctorsignature = $("#repairDoctorsignature").val();//修复医生签名
			var doctorTime = $("#doctortime").val();//修复医生签名时间
			/* var patientsignature = $("#patientsignature").val();//患者签名 */
			var patientTime = $("#patienttime").val();//患者签名时间
			
			var singleCompLeftUp=$("#singlecompleftup").val();
			var singleCompLeftDown=$("#singlecompleftdown").val();
			var singleCompRightUp=$("#singlecomprightup").val();
			var singleCompRightDown=$("#singlecomprightdown").val();
			var sfSinComLeftUp=$("#sfsincomleftup").val();
			var sfSinComLeftDown=$("#sfsincomleftdown").val();
			var sfSinComRightUp=$("#sfsincomrightup").val();
			var sfSinComRightDown=$("#sfsincomrightdown").val();
			var localPlantLeftUp=$("#localplantleftup").val();
			var localPlantLeftDown=$("#localplantleftdown").val();
			var localPlantRightUp=$("#localplantrightup").val();
			var localPlantRightDown=$("#localplantrightdown").val();
			
			var url = contextPath + '/HUDH_RepairSchemeConfirmAct/saveRepairSchemeConfirmInfro.act';
	        var param = {
	        		id :  id, //临床路径ID
	        		order_number :  order_number, //节点编号
	        		username :patient_name,
		        	sex : patient_sex,
		        	age : patient_age,
	        		tprgLeftUp : tprgLeftUp,
	        		tprgRightUp : tprgRightUp,
	        		tprgRightDown : tprgRightDown,
	        		tprgLeftDown : tprgLeftDown,
	        		requireRestor : requireRestor,
	        		singlecompselect : singleCompSelect,
	        		localPlantSelect : localPlantSelect,
	        		sfSinComSelect : sfSinComSelect,
	        		dentiumLeftUp : dentiumLeftUp,
	        		dentiumRightUp : dentiumRightUp,
	        		dentiumRightDown : dentiumRightDown,
	        		dentiumLeftDown : dentiumLeftDown,
	        		icxLeftUp : icxLeftUp,
	        		icxRightUp : icxRightUp,
	        		icxLeftDown : icxLeftDown,
	        		icxRightDown : icxRightDown,
	        		nobelActiveLeftUp : nobelActiveLeftUp,
	        		nobelActiveRightUp : nobelActiveRightUp,
	        		nobelActiveLeftDown : nobelActiveLeftDown,
	        		nobelActiveRightDown : nobelActiveRightDown,
	        		camlogLeftUp : camlogLeftUp,
	        		camlogRightUp : camlogRightUp,
	        		camlogLeftDown : camlogLeftDown,
	        		camlogRightDown : camlogRightDown,
	        		hiossenLeftUp : hiossenLeftUp,
	        		hiossenRightUp : hiossenRightUp,
	        		hiossenLeftDown : hiossenLeftDown,
	        		hiossenRightDown : hiossenRightDown,
	        		nobelpmcleftup : nobelpmcleftup,
	        		nobelpmcleftdown : nobelpmcleftdown,
	        		nobelpmcrightup : nobelpmcrightup,
	        		nobelpmcrightdown : nobelpmcrightdown,
	        		zimmerleftup : zimmerleftup,
	        		zimmerleftdown : zimmerleftdown,
	        		zimmerrightup : zimmerrightup,
	        		zimmerrightdown :  zimmerrightdown,
	        		rbrdLeftUp : rbrdLeftUp,
	        		rbrdRigthUp : rbrdRigthUp,
	        		rbrdLeftDown : rbrdLeftDown,
	        		rbrdRigthDown : rbrdRigthDown,
	        		inviDenLeftUp : inviDenLeftUp,
	        		inviDenRightUp : inviDenRightUp,
	        		inviDenLeftDown : inviDenLeftDown,
	        		inviDenRightDown : inviDenRightDown,
	        		locatorLeftUp : locatorLeftUp,
	        		locatorRightUp : locatorRightUp,
	        		locatorRightDown : locatorRightDown,
	        		locatorLeftDown : locatorLeftDown,
	        		srrLeftUp : srrLeftUp,
	        		srrRightUp : srrRightUp,
	        		srrLeftDown : srrLeftDown,
	        		srrRightDown : srrRightDown,
	        		retainerLeftUp : retainerLeftUp,
	        		retainerRightUp : retainerRightUp,
	        		retainerLeftDown : retainerLeftDown,
	        		retainerRightDown : retainerRightDown,
	        		//新增参数
	        		servicesignature : servicesignature,//客服签名
	        		serviceTime : serviceTime,//客服签名时间
	        		operationDoctorsignature :signature,//手术医生签名
	        		operationDoctorTime : operationDoctorTime,//手术医生签名时间
	        		repairDoctorsignature : repairSignature,//修复医生签名
	        		doctorTime : doctorTime,//修复医生签名时间
	        		patientsignature : patientsignature,//患者签名
	        		patientTime : patientTime,//患者签名时间
	        		
	        		singlecompleftup : singleCompLeftUp,
	        		singlecompleftdown : singleCompLeftDown,
	        		singlecomprightup : singleCompRightUp,
	        		singlecomprightdown : singleCompRightDown,
	        		sfsincomleftup : sfSinComLeftUp,
	        		sfsincomleftdown : sfSinComLeftDown,
	        		sfsincomrightup : sfSinComRightUp,
	        		sfsincomrightdown : sfSinComRightDown,
	        		localplantleftup : localPlantLeftUp,
	        		localplantleftdown : localPlantLeftDown,
	        		localplantrightup : localPlantRightUp,
	        		localplantrightdown : localPlantRightDown
	        };
	        //console.log(JSON.stringify(param)+"---------修复传参");
			
	       /*  保存前验证 */
	        /* 1.全口/半口半固定 多选判断 */
	        if(param.singlecompleftup || param.singlecomprightup || param.singlecompleftdown || param.singlecomprightdown){
	        	if(!param.singlecompselect){
	        		layer.alert("请选择全口/半口半固定选项！");
		        	return;
	        	}
	        }
	       /*  2.全口/半口 固定 */
	        if(param.sfsincomleftup || param.sfsincomrightup || param.sfsincomleftdown || param.sfsincomrightdown){
	        	if(!param.sfSinComSelect){
		        	layer.alert("请选择全口/半口 固定选项！");
		        	return;
	        	}
	        }
	        /*  3.局部 */
	        if(param.localplantleftup || param.localplantrightup || param.localplantleftdown || param.localplantrightdown){
	        	if(!param.localPlantSelect){
		        	layer.alert("请选择局部选项！");
		        	return;
	        	}
	        }
	        
	        $.axseSubmit(url, param,function() {},function(r) {
	        	layer.alert("保存成功！", {
		            end: function() {
		            	//window.parent.location.reload(); //刷新父页面
		                var frameindex = parent.layer.getFrameIndex(window.name);
		                parent.layer.close(frameindex); //再执行关闭
		            }
		      	});
	        },function(r){
	        	layer.alert("保存失败！");
		    });
		}
		
		
		/* 全口/半口半固定 多选判断 */
		/* function verifytoothtype(objid,objname){
			var toothValue=$("#"+objid).val();
			if(toothValue){
				var selectValue=$("input[type='checkbox'][name="+objname+"]:checked").val();
				console.log(selectValue);
				if(selectValue==null){
					
				}
			}
		} */
		
		/* 获取拼接牙位并校验 */
		function getValue(inputObj){
			var inputBool=false;
			var toothArr=[];
			var toothString="";
			//牙位输入框
			var inputVal=$("#"+inputObj).val();
			for (var i = 0; i < inputVal.length; i++) {
				if(inputVal[i]<=8 && inputVal[i]>=1){
					if(toothArr.indexOf(inputVal[i])<0){
						toothArr.push(inputVal[i]);
					}else{
						inputBool=true;
					}
				}else{
					inputBool=true;
				}
			}
			if(inputBool){
				layer.open({
					 title: '提示',
					 content: '请输入正确牙位！(牙位值为1~8,且不能重复)',
					 end:function(){
						 $("#"+inputObj).val("").focus();
						 toothString="";
					 }
				});  
			}
			toothString=toothArr.join(",");
			//console.log(toothString+"------拼接字符串");//拼接字符串
			return toothString;
		};
		
		function getButtonPower() {
		    var menubutton1 = "";
		    for (var i = 0; i < listbutton.length; i++) {
		        if (listbutton[i].qxName == "zsbs_xgbd"&&repairdoctorstatus&&doctorstatus&&patientstatus) {
		        	$("#consent_updateBtn").removeClass("hidden");
		        }else if(listbutton[i].qxName =="lclj_ban_signature"){
                    doctorstatus=false;
                    patientstatus=false;
                    repairdoctorstatus=false;
                }
		    }
		    $("#bottomBarDdiv").append(menubutton1);
		}
		
		function doPrint() {
			$("input").removeAttr("placeholder");
		    bdhtml=window.document.body.innerHTML;
		    sprnstr="<!--startprint-->";
		    eprnstr="<!--endprint-->";
		    prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
		    prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
		    var htmlStyle="<style>#repairDoctorSignature{display: inline-block;}#repairImg{width:80px !important;}#img{width:80px !important;}#operationdoctortime{display: inline-block;}button{display:none;}.distance{margin-top: 10px !important;}#repair_continer .rp_toothGroup>ul>li{margin-left: 3%;}*{font-size: 12px;line-height: 16px;}#repair_continer input[type='checkbox']{width:12px !important;height:12px !important;margin-top: 15px !important;}.lodopPrintborder{border-right: 2px solid black !important;}.patient{padding:0!important;margin:0!important;}.inputheight2{border: 1px solid transparent!important;}.consent_updateBtn{display:none!important;}#logoImg{text-align:left!important;width:27%!important;left:0%!important;top:17px!important;}#requirerestor{font-size: 12px!important;line-height: 18px!important;}#repair_continer .moreSelect_div>li{min-width:110px!important;}</style>";
		    window.document.body.innerHTML=prnhtml+htmlStyle;
		    window.print();  //打印
		    document.body.innerHTML=bdhtml; //恢复页面
		}
		
		function myPreviewAll(){
			if(doctorstatus&&signature==""){
				   $("#img").css("display","none");
			}
			if(repairdoctorstatus&&repairSignature==""){
				   $("#repairImg").css("display","none");
			}
			if(patientstatus&&patientsignature==""){
				   $("#patientimg").css("display","none");
			}
			doPrint();
			/* LODOP=getLodop();  
			LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_完整全页");
			var htmlStyle="<style>#repairDoctorSignature{display: inline-block;}#repairImg{width:80px !important;}#img{width:80px !important;}#operationdoctortime{display: inline-block;}button{display:none;}.distance{margin-top: 10px !important;}#repair_continer .rp_toothGroup>ul>li{margin-left: 3%;}*{font-size: 12px;line-height: 16px;}#repair_continer input[type='checkbox']{width:12px !important;height:12px !important;margin-top: 15px !important;}.lodopPrintborder{border-right: 2px solid black !important;}.patient{padding:0!important;margin:0!important;}.inputheight2{border: 1px solid transparent!important;}.consent_updateBtn{display:none!important;}#logoImg{text-align:left!important;width:17%!important;left:0%!important;top:17px!important;}#requirerestor{height:40px!important;}</style>";
			var html="<!DOCTYPE html>"+document.getElementsByTagName("html")[0].innerHTML+htmlStyle;
			LODOP.ADD_PRINT_HTM(0,0,"100%","100%",html);
			LODOP.PREVIEW(); */	
		};

</script>
</html>