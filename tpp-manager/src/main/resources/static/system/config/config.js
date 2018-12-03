function operate(str,id){
    if(str=="refresh"){
        $.ajax({
            async:false,
            type:"post",
            dataType:"json",
            url:"../config/refresh",
            data:$("#form").serialize(),
            success:function(msg){
                layer.msg(msg.message);
            }
        });
    }else if(str=="redis_list"){
        $("#form").submit();
    }else if(str=="redis_file_edit"){
        parent.openDetial("修改redis", ['800px', '600px'], "config/redisEdit?filePath="+id, "save_redis()");
    }else if(str=="redis_file_del"){
        layer.confirm('是否要删除信息!', {
            btn: ['确定', '取消']
            }, function (index, layero) {
                $.ajax({
                    async:false,
                    type:"post",
                    dataType:"json",
                    url:"../config/delRedisFile",
                    data:{'filePath':id},
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
    }else if(str=="redis_file_add"){
        parent.openDetial("新建redis文件", ['600px', '300px'], "config/redisFileAdd?filePath="+$("#path").val(), "operate('save_redis_file')");
    }else if(str=="save_redis_file"){
        $.ajax({
            async:false,
            type:"post",
            dataType:"json",
            url:"../config/redisFileSave",
            data:{'filePath':$("#filePath").val(),'fileName':$("#fileName").val()},
            success:function(msg){
                if(msg.success) {

                    if (msg.success) {
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        var iframeId = parent.$(".layui-tab-title .layui-this").attr("lay-id");
                        window.parent.showMessage(msg, iframeId);//访问父页面方法
                        parent.layer.close(index); //再执行关闭
                    } else {
                        alert(msg.message);
                    }
                }
            }
        });
    }

}
