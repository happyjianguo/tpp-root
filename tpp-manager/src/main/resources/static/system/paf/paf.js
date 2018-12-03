function operate(str){
    if(str=="login"){
        $.ajax({
            async:false,
            type:"post",
            dataType:"json",
            url:"../paf/login",
            success:function(msg){
                if(msg.success){
                    $("#login").attr("disabled","true");
                }
                $("#message").css('display','block');
                $("#message_a").text(msg.message);
            }
        });
    }else if(str=="out"){
        $.ajax({
            async:false,
            type:"post",
            dataType:"json",
            url:"../paf/logout",
            success:function(msg){
                if(msg.success){
                    $("#out").attr("disabled","true");
                }
                $("#message").css('display','block');
                $("#message_a").text(msg.message);
            }
        });
    }else if(str=="accMstFileList") {
        parent.openDetial("文件列表", ['600px', '500px'], "paf/accMstFile?centerNo="+$("#centerNo").val()+"&reference="+$("#reference").val()+"&fileName="+$("#fileName").val()+"&tranDate="+$("#tranDate").val(), "operate('accMstFileListSave')");

    }else if(str=="accMstList"){
        $("#form").submit();
    }else if(str=="pushAccMst"){
        var checkStatus = table.checkStatus('table_box')
            ,data = checkStatus.data;
        var ids='';
        for(var i=0;i<data.length;i++){
            ids+=data[i].id+',';
        }
        if(data.length>0) {
            ids = ids.substr(0, ids.length - 1);
            $.ajax({
                async:false,
                type:"post",
                dataType:"json",
                url:"../paf/pushFile",
                data:{'ids':ids},
                success:function(msg){
                    alert(msg.message);
                }
            });
        }else{
            alert("请选择要重发的信息");
        }
    }else if(str=="accMstFileListSave"){
        var checkStatus = table.checkStatus('table_box')
            ,data = checkStatus.data;
        var ids='';
        for(var i=0;i<data.length;i++){
            ids+=data[i].fileName+',';
        }
        if(data.length>0) {
            ids = ids.substr(0, ids.length - 1);
            $.ajax({
                async:false,
                type:"post",
                dataType:"json",
                url:"../paf/pushFileList",
                data:{'fileNames':ids},
                success:function(msg){
                    alert(msg.message);
                }
            });
        }else{
            alert("请选择要发送的文件");
        }
    }
}