$().ready(function(){
    document.onkeydown = function(e){
        var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
            login();
        }
    }
    var userName = $.session.get('userName');
    if(!INPUT_UTIL.isNull(userName)){
        window.location.href = './main.html';
        return;
    }
    $('#login').unbind().bind('click',function(){
        login();
    });
});

function login(){
    var username = $('#username').val();
    var password = $('#password').val();
    if(username==''){
        alert('用户名不能为空!');
        return;
    }
    if(password==''){
        alert('密码不能为空!');
        return;
    }
    $.ajax({
        type: "POST",
        url: loginUrl,
        contentType:"application/x-www-form-urlencoded",
        //contentType: "application/json; charset=utf-8",
        data: {
            username:username,
            password:password
        },
        dataType: "json",
        success: function(data){
            if(data.respCode=='0000'){
                $.session.set("userName",data.userName);
                $.session.set("operNo",username);
                window.location.href = "./main.html";
            }else{
                alert(data.respDesc);
            }
        }
    });
}