function operate(str,id) {
    if(str=="add"){
        parent.openDetial("部门新增", ['600px', '400px'], "depart/add", "operate('save')");
    }else if(str=="save"){
        var bool=formVerify();
        if(bool) {
            $.ajax({
                async: false,
                type: "post",
                dataType: "json",
                url: "../depart/save",
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
        parent.openDetial("部门编辑", ['600px', '400px'], "depart/edit?id="+id, "operate('update')");
    }else if(str=="update"){
        var bool=formVerify();
        if(bool) {
            $.ajax({
                async: false,
                type: "post",
                dataType: "json",
                url: "../depart/update",
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
                    url:"../depart/del",
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
    }else if(str=="permanage"){
        parent.openDetial("人员管理", ['600px', '400px'], "userdepart/userlist?departId="+id, "operate('addPerson')");
    }
}

function formVerify(){
    var name=$("#name").val();
    var code=$("#code").val();
    if(code.length==0){
        layer.msg("部门编码不能为空");
        return;
    }else if(name.length==0){
        layer.msg("部门名称不能为空");
        return;
    }
    return true;
}