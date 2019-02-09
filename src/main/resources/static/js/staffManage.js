var staffId = '';
var phone = '';
var email = '';
var remark = '';
$().ready(function(){
    // 数据初始化
    getStaffInfoList();
    getDeparmentList();
    getJobList();
    var native_place_str = "";
    $.each(provinces,function(i,item){
        native_place_str += '<option value="'+item+'">'+item+'</option>';
    });
    $('#native_add').html(native_place_str);
    // 初始化完毕

    $('#searchBtn').unbind('click').bind('click',function(){
        var departmentId = $('#department').val();
        var staffName = $('#staff_name').val();
        if(departmentId=='0'){
            departmentId = '';
        }
        staffName = $.trim(staffName);
        getStaffInfoList(staffName,departmentId);
    })

    $('#staffInfoList').on('click','.staff_detail_btn',function(){
        var oper_no = $(this).parents().find('.oper_no').text();
        $('#staff_detail_modal').modal({"closeViaDimmer":false});
        $.ajax({
            type: "POST",
            url: getStaffInfoUrl,
            contentType:"application/x-www-form-urlencoded",
            data: {
                operNo:oper_no
            },
            dataType: "json",
            success: function(data){
                if(data.respCode=='0000'){
                    var staffInfo = data.staffInfo;
                    $('#oper_no').val(staffInfo.operNo);
                    $('#user_name').val(staffInfo.staffName);
                    $('#job').val(staffInfo.job);
                    $('#birthday').val(staffInfo.birthday);
                    $('#sex').val(staffInfo.gender);
                    $('#native').val(staffInfo.nativePlace);
                    $('#education').val(staffInfo.education);
                    $('#major').val(staffInfo.major);
                    $('#inductionTime').val(staffInfo.inductionTime);
                    $('#email').val(staffInfo.email);
                    $('#telNumber').val(staffInfo.telNumber);
                    $('#remark').val(staffInfo.remark);
                    $('#depart').val(staffInfo.department)
                    staffId = staffInfo.staffId;
                }else{
                    Modal.alert(data.respDesc);
                }
            }
        });
    });

    $('#staffBtn').unbind('click').bind('click',function(){
        $('.canUpdate').removeAttr("disabled");
        $(this).hide();
        $('#staffUpdateBtn').show();
    });

    // 修改按钮
    var reg_phone = /^1\d{10}$/;
    var reg_email = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
    $('#staffUpdateBtn').unbind('click').bind('click',function(){
        phone = $.trim($('#telNumber').val());
        email = $.trim($('#email').val());
        remark = $.trim($('#remark').val());
        if(!reg_phone.test(phone)){
            Modal.alert("请输入正确的手机号码！");
            return;
        }
        if(!reg_email.test(email)){
            Modal.alert("请输入正确的邮箱格式！");
            return;
        }
        if(remark.length>250){
            Modal.alert("简介字数请勿超过250个！");
            return;
        }
        Modal.confirm("确认更新资料",function () {
            var jsonParam = {
                "email":email,
                "phone":phone,
                "remark":remark,
                "staffId":staffId
            }
            $.ajax({
                type: "POST",
                url: updatePersonStaffInfo,
                contentType:"application/x-www-form-urlencoded",
                data: {
                    jsonStr:JSON.stringify(jsonParam)
                },
                dataType: "json",
                success: function(data){
                    if(data.respCode=='0000'){
                        $('#staffUpdateBtn').hide();
                        $('#staffBtn').show();
                        Modal.alert("更新个人资料成功!",function(){
                            $('#staff_detail_modal').modal('close');
                            $('#searchBtn').trigger('click');
                            $('.canUpdate').attr('disabled','disabled');
                        });
                    }else{
                        Modal.alert(data.respDesc);
                    }
                },
                error:function(){
                    Modal.alert("修改工号信息AJAX请求失败！");
                }
            });
        });
    });

    // 工号失效
    $('#staffInfoList').on('click','.staff_unable_btn',function(){
        var oper_no = $(this).parent().parent().parent().siblings('.oper_no').text();
        Modal.confirm("确认修改工号状态？",function(){
            $.ajax({
                type: "POST",
                url: updateStaffStateUrl,
                contentType:"application/x-www-form-urlencoded",
                data: {
                    operNo:oper_no,
                    state:1
                },
                dataType: "json",
                success: function(data){
                    if(data.respCode=='0000'){
                        Modal.alert("工号失效成功!",function(){
                            $('#searchBtn').trigger('click');
                        });
                    }else{
                        Modal.alert(data.respDesc);
                    }
                },
                error:function(){
                    Modal.alert("工号失效AJAX请求失败！");
                }
            });
        })

    });

    // 新增工号
    $('#staff_add_btn').unbind('click').bind('click',function(){
        $('#staff_add_modal').modal({"closeViaDimmer":false});
    });

    $('#staffAddBtn').unbind('click').bind('click',function(){
       var oper_no = $('#oper_no_add').val();
       var user_name = $('#user_name_add').val();
       var birthday = $('#birthday_add').val();
       var sex = $('#sex_add').val();
       var native = $('#native_add').val();
       var education = $('#education_add').val();
       var major = $('#major_add').val();
       var induction_time = $('#inductionTime_add').val();
       var email = $('#email_add').val();
       var tel_number = $('#telNumber_add').val();
       var job = $('#job_add').val();
       var department = $('#depart_add').val();
       if(INPUT_UTIL.isNull(oper_no)){
           Modal.alert("工号不能为空！")
           return;
       }
       if(INPUT_UTIL.isNull(user_name)){
           Modal.alert("姓名不能为空！");
           return;
       }
       if(INPUT_UTIL.isNull(birthday)){
           Modal.alert("请选择生日日期！");
           return;
       }
       if(INPUT_UTIL.isNull(major)){
           Modal.alert("专业不能为空！");
           return;
       }
       if(INPUT_UTIL.isNull(induction_time)){
           Modal.alert("请选择入职日期！");
           return;
       }
       if(INPUT_UTIL.isNull(email)){
           Modal.alert("邮箱不能为空！");
           return;
       }
       if(INPUT_UTIL.isNull(tel_number)){
           Modal.alert("手机号码不能为空！");
           return;
       }

       if(!reg_phone.test(tel_number)){
            Modal.alert("请输入正确的手机号码！");
            return;
       }
       if(!reg_email.test(email)){
           Modal.alert("请输入正确的邮箱格式！");
           return;
       }
       Modal.confirm("确认新增员工信息？",function(){
           var jsonParam = {
               "job_id":job,
               "department_id":department,
               "staff_name":user_name,
               "gender":sex,
               "birthday":birthday,
               "email":email,
               "tel_number":tel_number,
               "native_place":native,
               "education":education,
               "major":major,
               "induction_time":induction_time,
               "oper_no":oper_no
           }
           $.ajax({
               type: "POST",
               url: addStaff,
               contentType:"application/x-www-form-urlencoded",
               data: {
                   jsonStr:JSON.stringify(jsonParam)
               },
               dataType: "json",
               success: function(data){
                   if(data.respCode=='0000'){
                       $('#staffUpdateBtn').hide();
                       $('#staffBtn').show();
                       Modal.alert("新增员工信息成功!",function(){
                           $('.staff_detail_input').val('');
                           window.location.reload();
                       });
                   }else{
                       Modal.alert("",data.respDesc);
                   }
               }
           });
       });
    });
});

function getStaffInfoList(staffName,departmentId){
    var jsonParam = {
        "pageNo":"1",
        "pageSize":"10",
        "staff_name":staffName,
        "department_id":departmentId
    }
    $.ajax({
        type: "POST",
        url: getStaffInfoListUrl,
        contentType:"application/x-www-form-urlencoded",
        //contentType: "application/json; charset=utf-8",
        data: {
            jsonStr:JSON.stringify(jsonParam)
        },
        dataType: "json",
        success: function(data){
            if(data.respCode=='0000'){
                $('#staffInfoList').empty();
                $('#page').empty();
                $('#total').text(data.total);
                if(data.total==0){
                    return;
                }
                var staffHtml = "";
                $.each(data.staffList,function(i,item){
                    staffHtml += '<tr><td class="oper_no">'+item.operNo+'</td><td>'+item.staffName+'</td><td>'+item.department+'</td><td>'+item.gender+'</td><td>'+item.inductionTime+'</td><td>'+item.education+'</td><td>'+item.telNumber+'</td><td>'+item.email+'</td>' +
                        '<td>'+item.state+'</td><td>\n' +
                        '<div class="am-btn-toolbar">\n' +
                        '<div class="am-btn-group am-btn-group-xs">\n' +
                        '<button class="am-btn am-btn-default am-btn-xs am-text-secondary staff_detail_btn"><span class="am-icon-pencil-square-o"></span> 详情</button>\n' +
                        '<button class="am-btn am-btn-default am-btn-xs am-text-danger staff_unable_btn" style="color:#dd514c;"><span class="am-icon-trash-o" style="color:#dd514c;"></span> 失效</button>\n' +
                        '</div>\n' +
                        '</div>\n' +
                        '</td></tr>';
                });
                $('#staffInfoList').html(staffHtml);
                // 分页判断 余数为0时 页数不需要加一
                var pageTotal;
                if(parseInt(data.total)%10==0){ // 10条一页
                    pageTotal = parseInt(parseInt(data.total)/10);
                }else{
                    pageTotal = parseInt(parseInt(data.total)/10)+1;
                }
                $("#page").paging({
                    pageNo:1,
                    totalPage: pageTotal,
                    totalSize: data.total,
                    callback: function(num) {
                        var jsonParam = {
                            "pageNo":num,
                            "pageSize":"10",
                            "staff_name":staffName,
                            "department_id":departmentId
                        }
                        $.ajax({
                            type: "POST",
                            url: getStaffInfoListUrl,
                            contentType:"application/x-www-form-urlencoded",
                            data:{
                                jsonStr:JSON.stringify(jsonParam)
                            },
                            dataType: "json",
                            success: function(data){
                                if(data.respCode=='0000'){
                                    var staffHtml = "";
                                    $.each(data.staffList,function(i,item){
                                        staffHtml += '<tr><td class="oper_no">'+item.operNo+'</td><td>'+item.staffName+'</td><td>'+item.department+'</td><td>'+item.gender+'</td><td>'+item.inductionTime+'</td><td>'+item.education+'</td><td>'+item.telNumber+'</td><td>'+item.email+'</td>' +
                                            '<td>'+item.state+'</td><td>\n' +
                                            '<div class="am-btn-toolbar">\n' +
                                            '<div class="am-btn-group am-btn-group-xs">\n' +
                                            '<button class="am-btn am-btn-default am-btn-xs am-text-secondary staff_detail_btn"><span class="am-icon-pencil-square-o"></span> 详情</button>\n' +
                                            '<button class="am-btn am-btn-default am-btn-xs am-text-danger staff_unable_btn" style="color:#dd514c;"><span class="am-icon-trash-o" style="color:#dd514c;"></span> 失效</button>\n' +
                                            '</div>\n' +
                                            '</div>\n' +
                                            '</td></tr>';
                                    });
                                    $('#staffInfoList').html(staffHtml);
                                }
                            }
                        });
                    }
                })
            }else{
                Modal.alert(data.respDesc);
            }
        },
        error:function(){
            Modal.alert("员工列表信息查询AJAX请求失败！");
        }
    });
}

function getDeparmentList(){
    var appendStr = "<option value='0'>全部</option>";
    var appendStrForAdd = "";
    $.ajax({
        type: "POST",
        url: getStaticInfoUrl,
        async:false,
        contentType:"application/x-www-form-urlencoded",
        data: {
            entityName:"Department"
        },
        dataType: "json",
        success: function(data){
            if(data.respCode=='0000'){
                if(!INPUT_UTIL.isNull(data.args)){
                    $.each(data.args,function(i,item){
                        appendStr += "<option value='"+item.departmentId+"'>"+item.departmentName+"</option>";
                        appendStrForAdd += "<option value='"+item.departmentId+"'>"+item.departmentName+"</option>";
                    });
                }
            }else{
                Modal.alert(data.respDesc);
            }
        },
        error:function(){
            Modal.alert("静态数据查询AJAX请求失败！");
        }
    });
    $('#department').html(appendStr);
    $('#depart_add').html(appendStrForAdd);
}

function getJobList(){
    var appendStr = "<option value='0'>全部</option>";
    var appendStrForAdd = "";
    $.ajax({
        type: "POST",
        url: getStaticInfoUrl,
        async:false,
        contentType:"application/x-www-form-urlencoded",
        data: {
            entityName:"Job"
        },
        dataType: "json",
        success: function(data){
            if(data.respCode=='0000'){
                if(!INPUT_UTIL.isNull(data.args)){
                    $.each(data.args,function(i,item){
                        appendStr += "<option value='"+item.jobId+"'>"+item.jobName+"</option>";
                        appendStrForAdd += "<option value='"+item.jobId+"'>"+item.jobName+"</option>";
                    });
                }
            }else{
                Modal.alert(data.respDesc);
            }
        },
        error:function(){
            Modal.alert("静态数据查询AJAX请求失败！");
        }
    });
    $('#job_add').html(appendStrForAdd);
}