function operate(str,id){
    if(str=="save"){
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
                url:"../rolePermission/save",
                data:{'ids':ids,'roleId':$('#roleId').val()},
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
}