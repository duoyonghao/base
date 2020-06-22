function methodModify(seqId){
	layer.open({
        type: 2,
        title: '金额修改',
        shadeClose: false,
        shade: 0.6,
        area: ['400px', '450px'],
        content: contextPath + '/KQDS_MemberAct/toMethodModify.act?seqId='+seqId,
        //iframe的url
        end: function() {}
    });
}

function methodModifyList(seqId){
	layer.open({
        type: 2,
        title: '操作记录',
        shadeClose: false,
        shade: 0.6,
        area: ['90%', '70%'],
        content: contextPath + '/KQDS_MemberAct/toMethodModifyList.act?seqId='+seqId,
        //iframe的url
        end: function() {}
    });
}