function operate(str,id) {
    if(str=="add"){
        parent.openDetial("菜单新增", ['600px', '500px'], "permission/add", "operate('save')");
    }else if(str=="save"){
        var bool=formVerify();
        if(bool) {
            $.ajax({
                async: false,
                type: "post",
                dataType: "json",
                url: "../permission/save",
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
    }else if(str=="edit"){
        parent.openDetial("菜单编辑", ['600px', '500px'], "permission/edit?id="+id, "operate('update')");
    }else if(str=="update"){
        var bool=formVerify();
        if(bool) {
        $.ajax({
                async: false,
                type: "post",
                dataType: "json",
                url: "../permission/update",
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
    }else if(str=="del"){
        layer.confirm('是否要删除信息!', {
                btn: ['确定', '取消']
            }, function (index, layero) {
                $.ajax({
                    async:false,
                    type:"post",
                    dataType:"json",
                    url:"../permission/del",
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
    }
}

function formVerify(){
    var name=$("#name").val();
    // var url=$("#url").val();
    var permission=$("#permission").val();
    if(name.length==0){
        layer.msg("菜单名称不能为空");
        return;
    // }else if(url.length==0){
    //     layer.msg("菜单路径不能为空");
    //     return;
    }else if(permission.length==0){
        layer.msg("权限名称不能为空");
        return;
    }
    return true;
}