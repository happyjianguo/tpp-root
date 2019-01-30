function operate(str){
    if(str=="refresh")
    $.ajax({
        async:false,
        type:"post",
        dataType:"json",
        url:"../config/refresh",
        data:$("#form").serialize(),
        success:function(msg){
            $("#message").css('visibility','visible');
            $("#message_a").text(msg.message);
        }
    });
}
