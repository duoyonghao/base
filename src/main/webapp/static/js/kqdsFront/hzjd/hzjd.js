function Yztable_net() {
    $('#defaultForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            usercode: {
                message: '病历号验证失败',
                validators: {
                    notEmpty: {
                        message: '病历号不能为空'
                    },
                    stringLength: {
                        message: '选择所属门诊后，系统自动生成病历号'
                    }
                }
            },
            username: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 15,
                        message: '用户名长度必须在2到15位之间'
                    }
                }
            },
            sex: {
                validators: {
                    notEmpty: {
                        message: '请选择患者性别'
                    }
                }
            },
            birthday: {
                validators: {
                    regexp: {
                        regexp: /^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/,
                        message: '日期格式应为YYYY-MM-DD'
                    }
                }
            },
            phonenumber1: {
                message: '请填写手机号码',
                validators: {
                    notEmpty: {
                        message: '请填写手机号码'
                    },
                    remote: {
                        message: '手机号码已存在',
                        url: contextPath + '/KQDS_UserDocumentAct/checkphonenumber.act',
                        type: 'POST',
                        //请求方式
                        data: function(validator) {
                            return {
                                phonenumber1: $('[name="phonenumber1"]').val(),
                                seqId: $('[name="seqId"]').val()
                            };
                        }
                    },
                    regexp: {
                    	regexp: /^\d{11}$/,
                        message: '请输入11位手机号码1'
                    }
                }
            },
            phonenumber2: {
                validators: {
                    remote: {
                        message: '手机号码已存在',
                        url: contextPath + '/KQDS_UserDocumentAct/checkphonenumber.act',
                        type: 'POST',
                        //请求方式
                        data: function(validator) {
                            return {
                                phonenumber2: $('[name="phonenumber2"]').val(),
                                seqId: $('[name="seqId"]').val()
                            };
                        }
                    },
                    regexp: {
                        regexp: /^[0-9]*$/,
                        message: '请输入正确的电话号码'
                    }
                }
            },
            devchannel: {
                validators: {
                    notEmpty: {
                        message: '请选择患者来源'
                    }
                }
            },
            nexttype: {
                validators: {
                    notEmpty: {
                        message: '请选择患者来源下级分类'
                    }
                }
            },
            town: {
                validators: {
                    notEmpty: {
                        message: '请选择地区'
                    }
                }
            },
            accepttype: {
                validators: {
                    notEmpty: {
                        message: '请选择受理类型'
                    }
                }
            },
            accepttool: {
                validators: {
                    notEmpty: {
                        message: '请选择受理工具'
                    }
                }
            },
            askitem: {
                validators: {
                    notEmpty: {
                        message: '请选择咨询项目'
                    }
                }
            },
            askcontent: {
                validators: {
                    notEmpty: {
                        message: '请填写咨询内容'
                    }
                }
            }
        }
    });
    // 患者来源子分类的BUG修复 ，具体场景： 选择患者来源一级分类后，选择子分类，然后更改一级分类，这时子分类变为 请选择，但是 表单没有重新校验
    nextTypeBugFix();
}

// 患者来源子分类的BUG修复 ，具体场景： 选择患者来源一级分类后，选择子分类，然后更改一级分类，这时子分类变为 请选择，但是 表单没有重新校验
function nextTypeBugFix(){
    $('#devchannel').on('change',
	function() {
	    var bootstrapValidator = $("#defaultForm").data('bootstrapValidator');
	    var val = $("#nexttype").val(); // 如果为空，则让其校验不通过
	    if(!val){
	    	if (bootstrapValidator.options.fields.nexttype) {
	            bootstrapValidator.updateStatus('nexttype', 'INVALID').validateField('nexttype'); //错误提示信息变了  
	        }
	    }
	});
}

//营销中心 开发人必填
function Yztable_net_yx() {
    $('#defaultForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            usercode: {
                message: '病历号验证失败',
                validators: {
                    notEmpty: {
                        message: '病历号不能为空'
                    },
                    stringLength: {
                        message: '选择所属门诊后，系统自动生成病历号'
                    }
                }
            },
            username: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 15,
                        message: '用户名长度必须在2到15位之间'
                    }
                }
            },
            sex: {
                validators: {
                    notEmpty: {
                        message: '请选择患者性别'
                    }
                }
            },
            birthday: {
                validators: {
                    regexp: {
                        regexp: /^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/,
                        message: '日期格式应为YYYY-MM-DD'
                    }
                }
            },
            phonenumber1: {
                message: '请填写手机号码',
                validators: {
                    notEmpty: {
                        message: '请填写手机号码'
                    },
                    regexp: {
                    	regexp: /^\d{11}$/,
                        message: '请输入11位手机号码'
                    },
                    remote: {
                        message: '手机号码已存在',
                        url: contextPath + '/KQDS_UserDocumentAct/checkphonenumber.act',
                        type: 'POST',
                        //请求方式
                        data: function(validator) {
                            return {
                                phonenumber1: $('[name="phonenumber1"]').val(),
                                seqId: $('[name="seqId"]').val()
                            };
                        }
                    }
                }
            },
            phonenumber2: {
                validators: {
                    remote: {
                        message: '手机号码已存在',
                        url: contextPath + '/KQDS_UserDocumentAct/checkphonenumber.act',
                        type: 'POST',
                        //请求方式
                        data: function(validator) {
                            return {
                                phonenumber2: $('[name="phonenumber2"]').val(),
                                seqId: $('[name="seqId"]').val()
                            };
                        }
                    },
                    regexp: {
                        regexp: /^[0-9]*$/,
                        message: '请输入正确的电话号码'
                    }
                }
            },
            devchannel: {
                validators: {
                    notEmpty: {
                        message: '请选择患者来源'
                    }
                }
            },
            nexttype: {
                validators: {
                    notEmpty: {
                        message: '请选择患者来源下级分类'
                    }
                }
            },
            town: {
                validators: {
                    notEmpty: {
                        message: '请选择地区'
                    }
                }
            },
            accepttype: {
                validators: {
                    notEmpty: {
                        message: '请选择受理类型'
                    }
                }
            },
            accepttool: {
                validators: {
                    notEmpty: {
                        message: '请选择受理工具'
                    }
                }
            },
            askitem: {
                validators: {
                    notEmpty: {
                        message: '请选择咨询项目'
                    }
                }
            },
            askcontent: {
                validators: {
                    notEmpty: {
                        message: '请填写咨询内容'
                    }
                }
            }
            /*,developerDesc: {
                validators: {
                    notEmpty: {
                        message: '开发人不能为空'
                    }
                }
            }*/
        }
    });
    // 患者来源子分类的BUG修复 ，具体场景： 选择患者来源一级分类后，选择子分类，然后更改一级分类，这时子分类变为 请选择，但是 表单没有重新校验
    nextTypeBugFix();
}
//营销中心 开发人必填
$('#developerDesc').on('change',
function() {
    var bootstrapValidator = $("#defaultForm").data('bootstrapValidator');

    if (bootstrapValidator.options.fields.developerDesc) {
        bootstrapValidator.updateStatus('developerDesc', 'NOT_VALIDATED').validateField('developerDesc'); //错误提示信息变了  
    }
});

function Yztable() {
    $('#defaultForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                message: '用户名验证失败！',
                validators: {
                    notEmpty: {
                        message: '姓名不能为空！'
                    },
                    stringLength: {
                        min: 2,
                        max: 15,
                        message: '用户名长度必须在2到15位之间！'
                    }
                }
            },
            sex: {
                validators: {
                    notEmpty: {
                        message: '请选择患者性别！'
                    }
                }
            },
            birthday: {
                validators: {
                    regexp: {
                        regexp: /^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/,
                        message: '日期格式应为YYYY-MM-DD！'
                    }
                }
            },
            phonenumber1: {
                message: '请填写手机号码！',
                validators: {
                    notEmpty: {
                        message: '请填写手机号码！'
                    },
                    regexp: {
                    	regexp: /^\d{11}$/,
                        message: '请输入11位手机号码'
                    },
                    remote: {
                        message: '手机号码已存在！',
                        url: contextPath + '/KQDS_UserDocumentAct/checkphonenumber.act',
                        type: 'POST',
                        //请求方式
                        data: function(validator) {
//                        	console.log(validator);
                            return {
                                phonenumber1: $('[name="phonenumber1"]').val(),
                                seqId: $('[name="seqId"]').val(),
                                family : $("#family").val() //传入此标识，用于后台判断
                            };
                        }
                    }
                }
            },
            phonenumber2: {
                validators: {
                	regexp: {
                        regexp: /^[0-9]*$/,
                        message: '请输入正确的电话号码'
                    },
                    remote: {
                        message: '手机号码已存在！',
                        url: contextPath + '/KQDS_UserDocumentAct/checkphonenumber.act',
                        type: 'POST',
                        //请求方式
                        data: function(validator) {
                            return {
                                phonenumber2: $('[name="phonenumber2"]').val(),
                                seqId: $('[name="seqId"]').val()
                            };
                        }
                    }
                }
            },
            blcode: {
 	            validators: {
 	                remote:{
	                	message: '病历号已存在',
	                    url : contextPath+'/KQDS_UserDocumentAct/checkBlcode.act',
	                    type: 'POST',//请求方式
	                    data: function(validator) {
	                        return {
	                        	blcode: $('[name="blcode"]').val(),
	                        	seqId: $('[name="seqId"]').val()
	                        };
	                     }
	                }
 	            }
            },
            idcardno: {
                validators: {
                    remote: {
                        message: '身份证号码已存在！',
                        url: contextPath + '/KQDS_UserDocumentAct/checkIdCardNo.act',
                        type: 'POST',
                        //请求方式
                        data: function(validator) {
                            return {
                                idcardno: $('[name="idcardno"]').val(),
                                seqId: $('[name="seqId"]').val()
                            };
                        }
                    }
                }
            },
            devchannel: {
                validators: {
                    notEmpty: {
                        message: '请选择患者来源！'
                    }
                }
            },
            nexttype: {
                validators: {
                    notEmpty: {
                        message: '请选择患者来源下级分类！'
                    }
                }
            },
            town: {
                validators: {
                    notEmpty: {
                        message: '请选择地区'
                    }
                }
            }
        }
    });
    // 患者来源子分类的BUG修复 ，具体场景： 选择患者来源一级分类后，选择子分类，然后更改一级分类，这时子分类变为 请选择，但是 表单没有重新校验
    nextTypeBugFix();
}

function Yztable_xxbb() {
    $('#defaultForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            username: {
                message: '用户名验证失败',
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    },
                    stringLength: {
                        min: 2,
                        max: 15,
                        message: '用户名长度必须在2到15位之间'
                    }
                }
            },
            sex: {
                validators: {
                    notEmpty: {
                        message: '请选择患者性别'
                    }
                }
            },
            phonenumber1: {
                message: '请填写手机号码',
                validators: {
                    notEmpty: {
                        message: '请填写手机号码'
                    },
                    regexp: {
                    	regexp: /^\d{11}$/,
                        message: '请输入11位手机号码'
                    },
                    remote: {
                        message: '手机号码已存在',
                        url: contextPath + '/KQDS_UserDocumentAct/checkphonenumber.act',
                        type: 'POST',
                        //请求方式
                        data: function(validator) {
                            return {
                                phonenumber1: $('[name="phonenumber1"]').val(),
                                seqId: $('[name="seqId"]').val()
                            };
                        }
                    }
                }
            },
            phonenumber2: {
                validators: {
                	regexp: {
                        regexp: /^[0-9]*$/,
                        message: '请输入正确的电话号码'
                    },
                    remote: {
                        message: '手机号码已存在',
                        url: contextPath + '/KQDS_UserDocumentAct/checkphonenumber.act',
                        type: 'POST',
                        //请求方式
                        data: function(validator) {
                            return {
                                phonenumber2: $('[name="phonenumber2"]').val(),
                                seqId: $('[name="seqId"]').val()
                            };
                        }
                    }
                }
            },
            idcardno: {
                validators: {
                    remote: {
                        message: '身份证号码已存在',
                        url: contextPath + '/KQDS_UserDocumentAct/checkIdCardNo.act',
                        type: 'POST',
                        //请求方式
                        data: function(validator) {
                            return {
                                idcardno: $('[name="idcardno"]').val(),
                                seqId: $('[name="seqId"]').val()
                            };
                        }
                    }
                }
            },
            devchannel: {
                validators: {
                    notEmpty: {
                        message: '请选择患者来源'
                    }
                }
            },
            nexttype: {
                validators: {
                    notEmpty: {
                        message: '请选择患者来源下级分类'
                    }
                }
            },
            town: {
                validators: {
                    notEmpty: {
                        message: '请选择地区'
                    }
                }
            }
        }
    });
    // 患者来源子分类的BUG修复 ，具体场景： 选择患者来源一级分类后，选择子分类，然后更改一级分类，这时子分类变为 请选择，但是 表单没有重新校验
    nextTypeBugFix();
}

function getDetail() {
    var baseInfo = getBaseInfoByUserCode(usercode);
//  console.log(baseInfo,+"<<<<<<<<<《《《《《《《《《******》》》》》》》》》》》》>>>>>>>>");
    if (baseInfo) {
        loadData(baseInfo);

        $(".stars-icon").each(function(i, n) {
            if ($("#important").val() == $(this).attr('value')) {
                $(this).attr('class', 'hc-icon icon20 stars-icon hc-active');
                $(this).addClass('hc-active').prevAll('span').addClass('hc-active');
            }
        });

        $("#seqId").val(baseInfo.seqId);
        $("#type").val(baseInfo.type);

        // 电话号码 去掉 左右空格
        if (baseInfo.phonenumber1) {
            $("#phonenumber1").val($.trim(baseInfo.phonenumber1));
        }
        if (baseInfo.phonenumber2) {
            $("#phonenumber2").val($.trim(baseInfo.phonenumber2));
        }

        // 放到最后执行，防止下拉框还没初始化完成，就已经执行到这了
        if (baseInfo.province != null && baseInfo.province != "") {
            $("#province").val(baseInfo.province).trigger("change");
        }
        if (baseInfo.city != null && baseInfo.city != "") {
            $("#city").val(baseInfo.city).trigger("change");
        }
        if (baseInfo.area != null && baseInfo.area != "") {
        	$("#area").val(baseInfo.area).trigger("change");
        }
        if (baseInfo.town != null && baseInfo.town != "") {
            $("#town").val(baseInfo.town);
        }
        if (baseInfo.familyship != null && baseInfo.familyship != "") {//添加亲属关系赋值  2019-7-25  syp
        	$("#familyship").val(baseInfo.familyship);
        }
        
    }

}
function submit() {
    var returnbool = true;
    var data = $('#defaultForm').data('bootstrapValidator');
    if (data) {
        // 修复记忆的组件不验
        data.validate(); // 手动对表单进行校验，经过测试，不写此方法，直接执行isValid()方法也可行 
        if (!data.isValid()) {
            returnbool = false;
        }
    }
    
    var blcode = $('[name="blcode"]').val(); // 病历号	
	if(blcode != null  && blcode != ''){	
		if(checknumber(blcode)){
			layer.alert('病历号必须为数字类型！', {});
			return false;
		}
	}
    return returnbool;
}
function jsJb() {
    //弹窗内星级评分
    $('.addVisitingDialog-content').on('click', '.starsBox span',
    function() {
        $("#important").val($(this).attr('value'));
        if ($(this).hasClass('hc-active')) {
            $(this).nextAll('span').removeClass('hc-active');
        } else {
            $(this).addClass('hc-active').prevAll('span').addClass('hc-active');
        }
    });
}

$("#birthday").datepicker({
    changeMonth: true,
    changeYear: true,
    showOn: "button",
    buttonImage: contextPath + "/static/image/image/calendar.gif",
    yearRange: "1900:2019"

});

//校验日期格式是否合法
function IsDate(str) {
    //我们将在这个方法里面进行日期格式的检测
    //正则表达式
    var reg = /^(\d{4})-(\d{2})-(\d{2})$/;
    //用正则表达匹配
    var arr = reg.exec(str);
    if (str == "") {
        return true;
    } else if (reg.test(str) && RegExp.$2 <= 12 && RegExp.$3 <= 31) {
        return true;
    } else {
        return false;
    }
}

//判断是否具备修改开发人员的权限
function xxbbValidate(userPriv, userSeqId) {
	var createuser = $("#createuser").val();
    if (createuser) { // 存在，表示编辑患者档案
        if (userPriv && userSeqId) { // 编辑的时候，这两个参数必须存在
            if ('1' == userPriv) { // 系统管理员，有权限编辑开发人员
                single_select_user(['developer', 'developerDesc']);
            } else {
            	if (canEditKf == 1) { // 有权限的可以编辑
            		single_select_user(['developer', 'developerDesc']);
                } else {
                	if (createuser == userSeqId) { // 可以编辑自己创建的患者
                		single_select_user(['developer', 'developerDesc']);
                	}else{
                		layer.alert('无权限！', {
                              
                        });
                	}
                }
            }
        } else {
            layer.alert('系统错误，参数不存在！'  );
        }
    } else { // 新建的时候，谁都可以填
        single_select_user(['developer', 'developerDesc']);
    }
}

/**
 * 跳转到患者标签界面
 * @param usercode
 */
function openPatientTag() {
    parent.layer.open({
        type: 2,
        title: '患者标签',
        shadeClose: true,
        shade: 0.6,
        area: ['680px', '450px'],
        content: contextPath + '/KQDS_UserDocumentAct/toOpenPatientTag.act?frameSelfindex='+frameSelfindex
    });
    
}

$('#town').on('change', function(){
	 var bootstrapValidator = $("#defaultForm").data('bootstrapValidator');  
	 bootstrapValidator.updateStatus('town', 'NOT_VALIDATED').validateField('town'); //错误提示信息变了  
});
//选择患者生日 自动计算年龄
function changeAge() {
    var birdate = $('#birthday').val();
    //console.log(birdate,"birdate")
    if (birdate != "") {
        $('#age').val(getAges(birdate));
    }
}
//检测年龄0-10岁提示
function ageHint(){
    var age=$("#age").val();
	if(age>0 && age<=10){
		$(".ageText").css("display","block");
	}else{
		$(".ageText").css("display","none");
	}
}


//参数为"yyyy-MM-dd" 计算年龄
function getAges(str) {
    var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
    if (r == null) {
        return '';
    }
    var d = new Date(r[1], r[3] - 1, r[4]);
    if (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d.getDate() == r[4]) {
        var Y = new Date().getFullYear();
        return ((Y - r[1]));
    }

    return '';

}

//获取病人编号
function getUserCode() {
    var detailurl = contextPath + '/KQDS_UserDocumentAct/getUserCode.act';
    $.axse(detailurl, null,
    function(data) {
        //修改与后台交互，根据状态赋值
        if (data.retState == "0") {
            $("#usercode").val(data.retData);
        } else {
            layer.alert('查询出错！'  );
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}

//获取病人编号
function getUserCode(organization) {

    var detailurl = contextPath + '/KQDS_UserDocumentAct/getUserCode.act';
    $.axse(detailurl, {organization:organization},
    function(data) {
        //修改与后台交互，根据状态赋值
        if (data.retState == "0") {
            $("#usercode").val(data.retData);
        } else {
            layer.alert('查询出错！'  );
        }
    },
    function() {
        layer.alert('查询出错！' );
    });
}