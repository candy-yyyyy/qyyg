$().ready(function(){
    getStaffInfoList();

    getDeparmentList();

    $('#searchBtn').unbind().bind('click',function(){
        var departmentId = $('#department').val();
        if(departmentId=='0'){
            departmentId = '';
        }
        getStaffInfoList('',departmentId);
    })
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
                $('#total').text(data.total);
                var staffHtml = "";
                $.each(data.staffList,function(i,item){
                    staffHtml += '<tr><td>'+item.operNo+'</td><td>'+item.staffName+'</td><td>'+item.department+'</td><td>'+item.gender+'</td><td>'+item.inductionTime+'</td><td>'+item.education+'</td><td>'+item.telNumber+'</td><td>'+item.email+'</td>' +
                        '<td>\n' +
                        '<div class="am-btn-toolbar">\n' +
                        '<div class="am-btn-group am-btn-group-xs">\n' +
                        '<button class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span> 详情</button>\n' +
                        '<button class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span> 失效</button>\n' +
                        '</div>\n' +
                        '</div>\n' +
                        '</td></tr>';
                });
                $('#staffInfoList').html(staffHtml);
                // 分页判断 余数为0时 页数不需要加一
                var pageTotal;
                if(parseInt(data.total)%3==0){
                    pageTotal = parseInt(parseInt(data.total)/3);
                }else{
                    pageTotal = parseInt(parseInt(data.total)/3)+1;
                }
                $("#page").paging({
                    pageNo:1,
                    totalPage: pageTotal,
                    totalSize: data.total,
                    callback: function(num) {
                        if(num==1){
                            return;
                        }
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
                                        staffHtml += '<tr><td>'+item.operNo+'</td><td>'+item.staffName+'</td><td>'+item.department+'</td><td>'+item.gender+'</td><td>'+item.inductionTime+'</td><td>'+item.education+'</td><td>'+item.telNumber+'</td><td>'+item.email+'</td>' +
                                            '<td>\n' +
                                            '<div class="am-btn-toolbar">\n' +
                                            '<div class="am-btn-group am-btn-group-xs">\n' +
                                            '<button class="am-btn am-btn-default am-btn-xs am-text-secondary"><span class="am-icon-pencil-square-o"></span> 详情</button>\n' +
                                            '<button class="am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only"><span class="am-icon-trash-o"></span> 失效</button>\n' +
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
}