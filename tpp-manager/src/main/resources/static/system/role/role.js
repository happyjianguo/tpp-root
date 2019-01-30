function operate(str,id) {
    if(str=="add"){
        parent.openDetial("角色新增", ['600px', '400px'], "role/add", "operate('save')");
    }else if(str=="save"){
        var bool=formVerify();
        if(bool){
            $.ajax({
                async:false,
                type:"post",
                dataType:"json",
                url:"../role/save",
                data:$("#form").serialize(),
                success:function(msg){
                    if(msg.success){
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        var iframeId = parent.$(".layui-tab-title .layui-this").attr("lay-id");
                        window.parent.showMessage(msg,iframeId);//访问父页面方法
                        parent.layer.close(index); //再执行关闭
                    }else{
                        alert(msg.message);
                    }
                }
            });
        }
    }else if(str=="list"){
        $("#form").submit();
    }else if(str=="del"){
        layer.confirm('是否要删除信息!', {
                btn: ['确定', '取消']
            }, function (index, layero) {
                $.ajax({
                    async:false,
                    type:"post",
                    dataType:"json",
                    url:"../role/del",
                    data:{'id':id},
                    success:function(msg){
                       if(msg.success){
                           var iframeId = parent.$(".layui-tab-title .layui-this").attr("lay-id");
                           layer.msg(msg.message, {
                               icon: 1,
                               area: ['220px', '80px'], //宽高
                               time: 2000 //2秒关闭（如果不配置，默认是3秒）
                           }, function(){
                               window.parent.document.getElementById("iframe_"+iframeId).contentWindow.location.reload(true);
                           });

                       }else{
                           layer.msg(msg.message);
                       }
                    }
                });
            }
        );
    }else if(str=="edit"){
        parent.openDetial("角色编辑", ['600px', '400px'], "role/edit?id="+id, "operate('update')");
    }else if(str=="update"){
        var bool=formVerify();
        if(bool) {
            $.ajax({
                async: false,
                type: "post",
                dataType: "json",
                url: "../role/update",
                data: $("#form").serialize(),
                success: function (msg) {
                    if (msg.success) {
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        var iframeId = parent.$(".layui-tab-title .layui-this").attr("lay-id");
                        window.parent.showMessage(msg, iframeId);//访问父页面方法
                        parent.layer.close(index); //再执行关闭
                    } else {
                        alert(msg.message);
                    }
                }
            });
        }
    }else if(str=="read"){
        parent.openDetial("角色查看", ['600px', '400px'], "role/read?id="+id, "operate('readClose')");
    }else if(str=="readClose"){
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    }else if(str=="permission"){
        parent.openDetial("权限列表", ['700px', '500px'], "rolePermission/list?roleId="+id, "operate('save')");
    }
}

function formVerify(){
    var description=$("#description").val();
    var role=$("#role").val();
    if(description.length==0){
        layer.msg("角色名称不能为空");
        return;
    }else if(role.length==0){
        layer.msg("角色编号不能为空");
        return;
    }
    return true;
}