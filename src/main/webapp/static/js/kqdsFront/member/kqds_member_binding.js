function Yzkaika(usercode){
	var cankaika = false;
    var url = contextPath+'/KQDS_MemberAct/YzUsercodeIsbinding.act?usercode=' +usercode;
    $.axse(url, null,
    function(data) {
   	 if(data.retState=="0"){
   		 if(data.data.length==0){
   			cankaika = true;
   		 }
   	 }else{
   		 layer.alert(data.retMsrg  );
   	 }
    },
    function() {
       layer.alert(data.retMsrg, {
             
       });
    });
    // return cankaika;
    return true;
}

//绑定IC卡，表单验证
function Yztable() {
    $('#defaultForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            icno: {
                message: 'IC卡验证失败！',
                validators: {
                    notEmpty: {
                        message: 'IC卡不能为空！'
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '请输入密码！'
                    }
                }
            },
            password2: {
                validators: {
                	notEmpty: {
                        message: '请输入确认密码！'
                    },
                    identical: {
                    	field: 'password',
                    	message: '两次输入密码不一致!'
                    }
                }
            }
        }
    });
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
    return returnbool;
}

function getDetail(seqId) {
    var baseInfo = null;
    var detailurl = contextPath + "/KQDS_MemberAct/selectDetail.act?seqId=" + seqId;
    $.axse(detailurl, null,
    function(data) {
    	baseInfo = data
    },
    function() {
        layer.alert('查询出错！'  );
    });
    return baseInfo;
}

//修改密码，表单验证
function YzEditPasstable() {
    $('#defaultForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	oldpassword: {
        		message: '请填写旧密码',
                validators: {
                    notEmpty: {
                        message: '请填写旧密码'
                    },
                    remote: {
                        message: '密码错误',
                        url: contextPath + '/KQDS_MemberAct/checkPassword.act',
                        type: 'POST',
                        data: function(validator) {
                            return {
                                password: $('[name="oldpassword"]').val(),
                                seqId: $('[name="seqId"]').val()
                            };
                        }
                    }
                }
            },
            password: {
                validators: {
                    notEmpty: {
                        message: '请输入密码！'
                    }
                }
            },
            password2: {
                validators: {
                	notEmpty: {
                        message: '请输入确认密码！'
                    },
                    identical: {
                    	field: 'password',
                    	message: '两次输入密码不一致!'
                    }
                }
            }
        }
    });
}