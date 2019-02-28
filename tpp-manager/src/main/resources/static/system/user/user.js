function operate(str,id){
    if(str=="add"){
        parent.openDetial("用户新增", ['600px', '400px'], "user/add", "operate('save')");
    }else if(str=="save"){
        var bool=pwdVerify();
        if(bool==false){
            return;
        }
        bool=formVerify();
        if(bool==false) {
            return;
        }
        $.ajax({
            async: false,
            type: "post",
            dataType: "json",
            url: "../user/save",
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

    }else if(str=="edit"){
        parent.openDetial("用户修改", ['600px', '400px'], "user/edit?id="+id, "operate('update')");
    }else if(str=="update"){
        var bool=formVerify();
        if(bool==false){
            return;
        }
        $.ajax({
            async:false,
            type:"post",
            dataType:"json",
            url:"../user/update",
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

    }else if(str=="pwd"){
        parent.openDetial("修改密码", ['600px', '400px'], "user/pwd?id="+id, "operate('updatePwd')");
    }else if(str=="updatePwd"){
        var bool=pwdVerify();
        if(bool==false){
            return;
        }
        $.ajax({
            async:false,
            type:"post",
            dataType:"json",
            url:"../user/updatePwd",
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
    }else if(str=="del"){
        layer.confirm('是否要删除用户!', {
                btn: ['确定', '取消']
            }, function (index, layero) {
                $.ajax({
                    async:false,
                    type:"post",
                    dataType:"json",
                    url:"../user/del",
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
    }else if(str=="role"){
        parent.openDetial("角色列表", ['600px', '400px'], "user/roleList?id="+id, "operate('roleSave')");
    }else if(str=="roleSave"){
        var checkStatus = treeGrid.checkStatus(tableId)
            ,data = checkStatus.data;
        var ids='';
        for(var i=0;i<data.length;i++){
            // alert(data[i].id);
            ids+=data[i].id+',';
        }
        if(data.length>0){
            ids=ids.substr(0,ids.length-1);
            $.ajax({
                async:false,
                type:"post",
                dataType:"json",
                url:"../user/roleSave",
                data:{'ids':ids,'id':$('#id').val()},
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
    }else if(str=="depart"){
        parent.openDetial("用户部门", ['600px', '600px'], "user/depart?userId="+id, "operate('saveDepart')");
    }else if(str=="saveDepart"){
        var data = treeGrid.radioStatus(tableId);
        if(data.id==undefined){
            layer.msg("请选择一个部门");
            return;
        }
        $.ajax({
            async:false,
            type:"post",
            dataType:"json",
            url:"../user/saveDepart",
            data:{'departId':data.id,'userId':$("#userId").val()},
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
}


function formVerify(){
    var username=$("#username").val();
    var name=$("#name").val();
    var password=$("#password").val();
    if(username.length==0){
        layer.msg("登录名不能为空");
        return false;
    }else if(name.length==0){
        layer.msg("昵称不能为空");
        return false;
    }else if(password.length==0){
        layer.msg("密码不能为空");
        return false;
    }
    return true;
}

function pwdVerify(){
    var password=$("#password").val();
    var password1=$("#password1").val();
    if(password!=password1){
        layer.msg("两次密码输入不一致");
        return false;
    }else if(password.length==0){
        layer.msg("密码不能为空");
        return false;
    }
    return true;
}