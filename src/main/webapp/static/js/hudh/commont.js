/**
 * 获取当前项目根路径
 * @returns
 */
function apppath(){//获得根目录
    var strFullPath = window.document.location.href;
    var strPath = window.document.location.pathname;
    var pos = strFullPath.indexOf(strPath);
    var prePath = strFullPath.substring(0, pos);
    var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    return (prePath + postPath);	
}

/**
 * 获取url中的参数
 * @param strValue
 * @returns
 */
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); 
    return null; //返回参数值
}

//textArea换行符转<br/>
function getFormatCode(strValue){
	return strValue.replace(/\r\n/g, '<br/>').replace(/\n/g, '<br/>').replace(/\s/g, ' ');
}

//<br/> 转 textArea换行符
function decodeTextAreaString(strValue){
	var reg = new RegExp("<br>", "g");
	strValue = strValue.replace(reg, "\r\n");
    return strValue;
}

/**
 * 初始化部门下人员为下拉框选项
 * @param $obj
 * @param deptId
 * @param valueType 给下拉框赋值是value值是id还是登陆名  seqId   loginName
 */
var hudhSysDeptIdStatic = {
	doctor : "892488fc-fc8c-44aa-8f36-b7225bc771f8,256af1df-0a6e-41d4-b5c9-31a4344ca991,7806b36a-43c0-421d-9036-923e84e68c36,65823c9e-6aaf-4f9d-8952-093db230e491,420c9cba-818c-4fb4-b8f7-d24b84d37578,025d8a65-7ce8-4a21-bd95-84a9b309e57f,dcf23b37-db5f-41bc-92b6-9375585448e1,e440f58a-f48d-49d2-a7cd-6b67cb9cda99",//医生
	plantDoctor : "256af1df-0a6e-41d4-b5c9-31a4344ca991,3e77910a-0a22-44e1-8cc0-a976aeb8f20b,dcf23b37-db5f-41bc-92b6-9375585448e1,e440f58a-f48d-49d2-a7cd-6b67cb9cda99", //口腔修复科 院办
	consultation :"4b88b74c-9373-4b5f-9d53-3c115de7a7e4,015d89d6-7b32-47ec-9557-233407c7fc71,72d1324f-22a2-41a7-9739-8b64c50e7b97,3b47a915-977b-4799-acf6-540d525722f4,b4f9dc9e-d2e0-44e1-ba37-eecaddcbf93d,d915e83c-862e-40eb-a4a0-792e34db701e,65823c9e-6aaf-4f9d-8952-093db230e491,90cc3cf5-cb94-49a0-8128-6857ee76155e,03386bd7-7286-4b33-a50c-9446c3614e60,c116c6b6-f143-4c42-ba31-f2dfd3f0945f",//咨询 （现场咨询部，正畸咨询部，洁牙组，患者转移部）
	netele : "58a87944-8527-4636-98fb-ba8fb9a1d24e", //网电
	nurse : "2203a606-ed82-4dd0-8841-49a30282a45f,c72025b9-f2b3-43b0-ad32-eb327dda5257", //护士 护理部
	repairdoctor : "892488fc-fc8c-44aa-8f36-b7225bc771f8,56ffc127-823d-4500-95cd-3f6fc8c31287,7806b36a-43c0-421d-9036-923e84e68c36,025d8a65-7ce8-4a21-bd95-84a9b309e57f,e440f58a-f48d-49d2-a7cd-6b67cb9cda99", //修复医生、全科医生
	customer : "aeafb25a-ae9f-4d22-8230-3725cb942435,23fe2c29-cac3-43ab-b4ef-ef70c4ab85c5,eb47460f-e12a-4934-8e3b-bfa0df14ed23",//客服部
	consumer : "23fe2c29-cac3-43ab-b4ef-ef70c4ab85c5,658b52d6-a42a-4b3d-9a19-bd0bc77210c5,abcea651-b674-42fd-a9e1-0dbd08f61525,41f199cd-e4be-4cfc-b553-555d13605976,e6066145-dd3b-4bf9-b2aa-d63b45375930,355168c8-de68-4a7b-aa41-0b33a4c31073"//客户部	
};
function initSysUserByDeptId($obj,deptType,valueType){
	if($obj && deptType) {
		var deptId = "";
		if(deptType == "doctor") {
			deptId = hudhSysDeptIdStatic.doctor;
		}else if(deptType == "consultation"){
			deptId = hudhSysDeptIdStatic.consultation;
//			console.log("部门Id="+deptId);
		}else if(deptType == "netele"){
			deptId = hudhSysDeptIdStatic.netele;
		}else if(deptType == "nurse"){
			deptId = hudhSysDeptIdStatic.nurse;
		}else if(deptType == "repairdoctor"){
			deptId = hudhSysDeptIdStatic.repairdoctor;
		}else if(deptType == "plantDoctor"){
			deptId = hudhSysDeptIdStatic.plantDoctor;
		}else if(deptType == "customer"){
			deptId = hudhSysDeptIdStatic.customer;
		} else if(deptType == "consumer") {
			deptId = hudhSysDeptIdStatic.consumer;
		}
		$.ajax({
			url: apppath() + "/YZPersonAct/getUserListByDeptId.act",
			type:"POST",
			dataType:"json",
			data : {"deptId":deptId},
			async : false,
			success:function(result){
//				console.log("咨询="+JSON.stringify(result));
				$($obj).append('<option value="">请选择</option>');
				if(result) {
					if(valueType == "loginName") {
						for(var i in result) {
							$($obj).append('<option value="'+result[i].userId+'">'+result[i].userName+'</option>');
						}
					}else {
						for(var i in result) {
							$($obj).append('<option value="'+result[i].seqId+'">'+result[i].userName+'</option>');
						}
					}
					
				}
			}
		});
	}
}
