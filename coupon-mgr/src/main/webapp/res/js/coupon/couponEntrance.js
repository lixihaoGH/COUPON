// 定义全局变量--存储入口ID
var entranceId = '';
var update_entrance_id = '';
var update_entrance_name = '';
var delete_entrance_id = '';
var delete_entrance_policy_id = '';
$(function () {
    createNewEntrance();
    saveCouponEntranceName();
    searchByEntranceName();
    selectRelease();
    //createEntranceName();
    //initReleaseList();
    resolveMaxStockSizeError();
    onReleaseListModalClose();
    onChooseReleaseModalClose();
    releaseSearch();
    initEntranceList();
    initReleaseList();
    saveSelectFunction();

});

function initEntranceList() {

    $('#couponEntranceList').bootstrapTable('destroy');
    $('#couponEntranceList').bootstrapTable({
        url: serverDomain + '/entrance/getList.json?token=' + getMD5(),         //请求后台的URL（*）
        method: 'get',                      //请求方式（*）
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 10,                     //每页的记录行数（*）
        pageList: 'All',        //可供选择的每页的行数（*）
        minimumCountColumns: 2,             //最少允许的列数
        width: 1029,
        clickToSelect: true,                //是否启用点击选中行
        uniqueId: "id",                     //每一行的唯一标识，一般为主键列
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        queryParamsType: "undefined",
        queryParams: function (params) {
            var temp = {
                page_size: params.pageSize,   //页面大小
                page_index: params.pageNumber
            };
            return temp;
        },
        columns: [{
            field: 'id',
            title: '入口id'
        }, {
            field: 'entrance_name',
            title: '领取入口名称',
            formatter: function (value, row) {
                var str = '';
                str += '<a href="javascript:updateEntranceNameEvents(' + row.id + ',&quot;' + row.entrance_name + '&quot;)">' + row.entrance_name + '</a>';
                return str;
            }
        }, {
            field: 'goto_url',
            title: '入口跳转地址'
        }, {
            field: 'update_time',
            title: '操作时间'
        }, {
            field: 'operate',
            title: '操作',
            formatter: function (value, row) {
                var str = '';
                str += '<button class="btn btn-default btn-sm linkCouponPolicy" onclick="delete_entrance_id = ' + row.id + ';entranceOperateEvents();" >删除</button>'
                return str;
            }
        }, {
            field: 'release_list',
            title: '策略列表',
            events: linkOperateEvents,
            formatter: linkCouponPolicy
        }]
    });

}

// 页面查询框
function searchByEntranceName() {
    $('#searchEntranceName').click(function () {
        var entranceName = $('#entranceNameInput').val();
        $('#couponEntranceList').bootstrapTable('destroy');
        $('#couponEntranceList').bootstrapTable({
            url: serverDomain + '/entrance/getList.json?token=' + getMD5(),
            method: 'get',
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
            pageSize: 5,                     //每页的记录行数（*）
            pageList: 'All',        //可供选择的每页的行数（*）
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            width: 1029,
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            queryParamsType: "undefined",
            queryParams: function (params) {
                //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    page_size: params.pageSize,   //页面大小
                    page_index: params.pageNumber,
                    entrance_name: entranceName
                };
                return temp;
            },
            columns: [{
                field: 'id',
                title: '入口id'
            }, {
                field: 'entrance_name',
                title: '领取入口名称',
                formatter: function (value, row) {
                    var str = '';
                    str += '<a href="javascript:updateEntranceNameEvents(' + row.id + ',&quot;' + row.entrance_name + '&quot;)">' + row.entrance_name + '</a>'
                    return str;
                }
            }, {
                field: 'goto_url',
                title: '入口跳转地址'
            }, {
                field: 'update_time',
                title: '操作时间'
            }, {
                field: 'operate',
                title: '操作',
                formatter: function (value, row) {
                    var str = '';
                    str += '<button class="btn btn-default btn-sm linkCouponPolicy" onclick="delete_entrance_id = ' + row.id + ';entranceOperateEvents();" >删除</button>';
                    return str;
                }
            }, {
                field: 'release_list',
                title: '策略列表',
                events: linkOperateEvents,
                formatter: linkCouponPolicy
            }]
        });
    })
}

// 显示新建入口模态框
function createNewEntrance() {
    $('#createNewEntranceBtn').click(function () {
        var dialog = $('#createNewEntranceModal').modal({
            backdrop: 'static',
            keyboard: false,
            width: 310,
            margin_top: 0
        });
        dialog.modal('show');
    });
}

/*
function createEntranceName() {
    $('#saveCouponEntranceName').click(function () {
        var entranceName = $('input[name="entrance_name"]').val();
        if (entranceName == '') {
            alert('入口名称不能为空，请填写后保存');
        } else {
            console.log(entranceName);
            var url = serverDomain + '/entrance/save.json';
            $.post(url, {entrance_name: entranceName},
                function (data) {
                    if (data.return_code != 1) {
                        // 不成功
                        alert('保存不成功');
                    } else {
                        // 保存成功将模态框隐藏
                        $('#createNewEntranceModal').modal('hide');
                        // 成功后主页面显示所有保存结果

                    }
                }, 'json'
            );
        }
    });
}*/

function updateEntranceFormatter(value, row) {
    return ['<a href="#" class="updateEntranceFormatter">' + row.entrance_name + '</a>'].join('');
}

function updateEntranceNameEvents(id, name) {
    var dialog = $('#updateNewEntranceModal').modal({
        backdrop: 'static',
        keyboard: false,
        width: 310
    });
    update_entrance_name = name;
    console.log(update_entrance_name);
    $('input[name="update_entrance_name"]').val(update_entrance_name);
    dialog.modal('show');
    update_entrance_id = id;
    console.log('updateEntranceNameEvents:' + update_entrance_id);
}

function saveUpdateButton() {
    console.log('saveUpdateButton:' + update_entrance_id);
    var dialog = $('#updateNewEntranceModal').modal({
        backdrop: 'static',
        keyboard: false,
        width: 310
    });
    var url = serverDomain + '/entrance/update.json?token=' + getMD5();
    var entranceName = $('input[name="update_entrance_name"]').val();
    if (entranceName == '') {
        alert('入口名称不能为空，请填写后保存');
    } else {
        $.ajax({
            type: 'POST',
            url: url,
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify({
                id: update_entrance_id,
                entrance_name: entranceName
            }),
            success: function (data) {
                if (data.return_code != 1) {
                    alert(data.return_value);
                } else {
                    alert('操作成功!');
                    dialog.modal('hide');
                    initEntranceList();
                    //location.reload();
                }
            }
        });
    }
}

// 保存新建入口信息
function saveCouponEntranceName() {
    $('#saveCouponEntranceName').click(function () {
        var entranceName = $('input[name="entrance_name"]').val();

        if (entranceName == '') {
            alert('入口名称不能为空，请填写后保存');
        } else {
            console.log(entranceName);
            var url = serverDomain + '/entrance/save.json?token=' + getMD5();
            $.ajax({
                type: 'POST',
                url: url,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify({
                    entrance_name: entranceName
                }),
                success: function (data) {
                    if (data.return_code != 1) {
                        alert(data.return_value);
                    } else {
                        var modal = $('#createNewEntranceModal').modal({
                            backdrop: 'static',
                            keyboard: false
                        });
                        modal.modal('hide');
                        initEntranceList();
                        //location.reload();
                    }
                }
            });
        }
    });
}

function entranceOperateEvents() {
    //delete_entrance_id = id;
    var dialog = $('#deletePolicy').modal({
        backdrop: 'static',
        keyboard: false,
        width: 302 + 'px'
    });
    dialog.modal('show');
}


// 创建按钮
function linkCouponPolicy(row) {
    //console.log(row.entrance_name);
    return [
        '<button type="button" class="btn btn-default btn-sm linkCouponPolicy" style="margin-right:15px;">关联策略</button>'
    ].join('');
}

// 绑定点击事件--点击按钮将显示策略列表模态框
window.linkOperateEvents = {
    'click .linkCouponPolicy': function (e, value, row) {
        entranceId = row.id;
        var dialog = $('#choosePolicyModal').modal({
            backdrop: 'static',
            keyboard: false,
            width: 1106
        });
        refreshList();
        dialog.modal('show');
        // 根据入口id查询当前入口所关联的策略列表
    }
};

function delOperateFormatter() {
    return [
        '<button type="button" class="del btn btn-default btn-sm" style="margin-right:15px;">删除</button>'
    ].join('');
}

function initReleaseList() {
    $('#couponPolicyList').bootstrapTable('destroy');
    $('#couponPolicyList').bootstrapTable({
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 10,                     //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        minimumCountColumns: 2,             //最少允许的列数
        height: 800,
        clickToSelect: true,                //是否启用点击选中行
        uniqueId: "release_id",                     //每一行的唯一标识，一般为主键列
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        rowStyle:function(row,index){
            if (row.release_id === data.activated_release_id){
                return {css:{"color":"red"}}
            }else{
                return {css:{"color":"black"}}
            }
        },
        columns: [{
            field: 'release_id',
            title: '策略id'
        }, {
            field: 'release_name',
            title: '策略名称'
        }, {
            field: 'release_status',
            title: '时间状态',
            formatter: queryEntranceReleaseStatusFormatter
        }, {
            field: 'releaseTime',
            title: '投放时间',
            formatter: queryEntranceReleaseTimeFormatter
        }, {
            field: 'operate',
            title: '操作',
            events: policyOperateEvents,
            formatter: policyButton
        }]
    });
}

function deleteEntrance() {
    var url = serverDomain + '/entrance/delete.json?id=' + delete_entrance_id + '&token=' + getMD5();
    var dialog = $('#deletePolicy').modal({
        backdrop: 'static',
        keyboard: false,
        width: 280
    });
    $.get(url, function (data) {
        if (data.return_code == 1) {
            dialog.modal('hide');
            initEntranceList();
            //location.reload();
        } else {
            alert(data.return_value);
        }
    });
}

function selectRelease() {
    $('#chooseNewReleaseBtn').click(function () {
        /*$('#policyList').bootstrapTable('destroy');*/
        $('#selectedPolicyList').bootstrapTable('destroy');
        var dialog = $('#chooseReleasePolicy').modal({
            backdrop: 'static',
            keyboard: false,
            width: 1100
        });
        dialog.modal('show');
        /*var dialog1 = $('#choosePolicyModal').modal({
            backdrop: 'static',
            keyboard: false
        });
        dialog1.modal('hide');*/
        $('#selectedPolicyList').bootstrapTable({
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
            pageSize: 5,                     //每页的记录行数（*）
            minimumCountColumns: 2,             //最少允许的列数
            height: 400,
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "release_id",                     //每一行的唯一标识，一般为主键列
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            columns: [{
                field: 'operate',
                title: '操作',
                events: entrancePolicyDeleteEvents,
                formatter: delOperateFormatter
            }, {
                field: 'release_time',
                title: '投放时间'
            }, {
                field: 'release_name',
                title: '策略名称'
            }]
        });
    });
}

function saveSelectFunction() {
    $('#saveSelectedPolicy').click(function () {
        var flag = confirm("是否确认所选和已选策略的投放时间不存在重叠?");
        if (flag) {
            $('#chooseReleasePolicy').modal('hide');
            // 获取所有已选策略信息
            var data = $('#selectedPolicyList').bootstrapTable('getData');
            var releaseData = $('#couponPolicyList').bootstrapTable('getData');
            console.log(releaseData);
            var release_id_list = '';
            for (var i = 0; i < data.length; i++) {
                if (i == data.length - 1) {
                    release_id_list = release_id_list + data[i].release_id;
                } else {
                    release_id_list = release_id_list + data[i].release_id + ',';
                }
            }
            var old_release_id_list = '';
            for (var j = 0; j < releaseData.length; j++) {
                if (releaseData[j].release_id === undefined || releaseData[j].release_id === ''
                    || releaseData[j].release_id === null) {
                    continue;
                } else {
                    old_release_id_list = old_release_id_list + releaseData[j].release_id + ',';
                }
            }
            console.log('add: release_id_list:' + release_id_list);
            release_id_list = old_release_id_list + release_id_list;
            console.log('release_id_list:' + release_id_list);
            var url = serverDomain + "/entrance/update.json?token=" + getMD5();
            $.ajax({
                type: 'POST',
                url: url,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify({
                    id: entranceId,
                    release_id_list: release_id_list
                }),
                success: function (data) {
                    if (data.return_code == 1) {
                        alert('操作成功!');
                        refreshList();
                        var modal = $('#choosePolicyModal').modal({
                            backdrop: 'static',
                            keyboard: false,
                            width: 1106
                        });
                        modal.modal('show');
                    } else {
                        alert(data.return_value);
                    }
                }
            });
        }
    });

}

function initReleaseList() {
    $('#policyList').bootstrapTable('destroy');
    $('#policyList').bootstrapTable({
        url: serverDomain + "/release/getList.json",
        method: 'get',
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 5,                     //每页的记录行数（*）
        pageList: "All",        //可供选择的每页的行数（*）
        minimumCountColumns: 2,             //最少允许的列数
        height: 400,
        clickToSelect: true,                //是否启用点击选中行
        uniqueId: "release_id",                     //每一行的唯一标识，一般为主键列
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        queryParamsType: "undefined",
        queryParams: function (params) {
            var temp = {
                page_size: params.pageSize,   //页面大小
                page_index: params.pageNumber
            };
            return temp;
        },
        columns: [{
            field: 'operate',
            title: '操作',
            events: entrancePolicyAddEvents,
            formatter: add_operateFormatter()
        }, {
            field: 'release_id',
            title: '策略id'
        }, {
            field: 'release_name',
            title: '策略名称'
        }, {
            field: 'release_status',
            title: '时间状态',
            formatter: queryEntranceReleaseStatusFormatter
        }]
    });

}

function releaseSearch() {
    $('#policySearchButton').click(function () {
        var policyNameInput = $('#policyNameInput').val();
        var releaseSearchUrl = serverDomain + '/release/getList.json';
        $('#policyList').bootstrapTable('destroy');
        $('#policyList').bootstrapTable({
            url: releaseSearchUrl,
            method: 'get',
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
            pageSize: 5,                     //每页的记录行数（*）
            pageList: "All",        //可供选择的每页的行数（*）
            minimumCountColumns: 2,             //最少允许的列数
            height: 400,
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "release_id",                     //每一行的唯一标识，一般为主键列
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            queryParamsType: "undefined",
            queryParams: function (params) {
                if (policyNameInput != '') {
                    var temp = {
                        page_size: params.pageSize,   //页面大小
                        page_index: params.pageNumber,
                        release_name: policyNameInput
                    };
                } else {
                    var temp = {
                        page_size: params.pageSize,   //页面大小
                        page_index: params.pageNumber
                    };
                }
                return temp;
            },
            columns: [{
                field: 'operate',
                title: '操作',
                events: entrancePolicyAddEvents,
                formatter: add_operateFormatter()
            }, {
                field: 'release_id',
                title: '策略id'
            }, {
                field: 'release_name',
                title: '策略名称'
            }, {
                field: 'release_status',
                title: '时间状态',
                formatter: queryEntranceReleaseStatusFormatter
            }]
        });


    });
}


function refreshList() {
    $('#couponPolicyList').bootstrapTable('destroy');
    var getEntranceUrl = serverDomain + '/entrance/get.json?id=' + entranceId + '&token=' + getMD5();
    $.get(getEntranceUrl, function (data) {
        if (!data.entrance.hasOwnProperty('release_id_list')) {

        } else {
            var getListUrl = serverDomain + "/release/getList.json";
            $('#couponPolicyList').bootstrapTable({
                url: getListUrl,
                method: 'post',
                toolbar: '#toolbar',                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   //是否显示分页（*）
                sortable: true,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
                pageSize: 8,                     //每页的记录行数（*）
                pageList: 'All',        //可供选择的每页的行数（*）
                minimumCountColumns: 2,             //最少允许的列数
                height: 400,
                clickToSelect: true,                //是否启用点击选中行
                uniqueId: "release_id",                     //每一行的唯一标识，一般为主键列
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                queryParams: function (params) {
                    var temp = {
                        release_ids: data.entrance.release_id_list
                    };
                    return temp;
                },
                onLoadError: function () {
                    alert('加载失败,请稍后重试!');
                },
                rowStyle:function(row,index){
                    if (row.release_id === data.activated_release_id){
                        return {css:{"color":"red"}}
                    }else{
                        return {css:{"color":"black"}}
                    }
                },
                columns: [{
                    field: 'release_id',
                    title: '策略id'
                }, {
                    field: 'release_name',
                    title: '策略名称'
                }, {
                    field: 'release_status',
                    title: '时间状态',
                    formatter: queryEntranceReleaseStatusFormatter
                }, {
                    field: 'releaseTime',
                    title: '投放时间',
                    formatter: queryEntranceReleaseTimeFormatter
                }, {
                    field: 'operate',
                    title: '操作',
                    events: policyOperateEvents,
                    formatter: policyButton
                }]
            });
        }
    });

}

// 创建按钮
function policyButton(row, value, index) {
    return [
        '<button type="button" class="btn btn-default btn-sm deletePolicy"  style="margin-right:15px;">删除</button>'
    ].join('');
}

// 绑定点击事件

window.policyOperateEvents = {
    'click .deletePolicy': function (e, value, row, index) {
        delete_entrance_policy_id = row.release_id;
        $('#errorMessageP').html('确认删除？');
        var deletePolicyModal1 = $('#errorMessageDiv').modal({
            backdrop: 'static',
            keyboard: false,
            width: 280
        });
        deletePolicyModal1.show();
        $('#entranceErrorModalButton').show();
    }
};

function entranceErrorModalButton() {
    // 确认删除时，获取当前列表信息，删除当前选择项，向后台发送请求，刷新页面
    console.log('delete_entrance_policy_id: ' + delete_entrance_policy_id);
    var releaseData = $('#couponPolicyList').bootstrapTable('getData');
    console.log(releaseData);
    for (var i = 0; i < releaseData.length; i++) {
        if (releaseData[i].release_id == delete_entrance_policy_id) {
            releaseData.splice(i, 1);
            break;
        }
    }
    console.log(releaseData);
    var release_id_list = '';
    if (releaseData.length < 1) {
        release_id_list = '';
    } else {
        for (var i = 0; i < releaseData.length; i++) {
            release_id_list += releaseData[i].release_id + ',';
        }
    }
    console.log(release_id_list);
    var url = serverDomain + '/entrance/update.json?token=' + getMD5();
    $.ajax({
        type: 'POST',
        url: url,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify({
            id: entranceId,
            release_id_list: release_id_list.toString()
        }),
        success: function (data) {
            if (data.return_code == 1) {
                alert('操作成功!');
                refreshList();
            } else {
                alert(data.return_value);
            }
        }
    });
    $('#errorMessageDiv').modal('hide');
}


function delOperateFormatter() {
    return [
        '<button type="button" class="del btn btn-default btn-sm" style="margin-right:15px;">删除</button>'
    ].join('');
}

function add_operateFormatter() {
    return [
        '<button type="button" class="add btn btn-default btn-sm" style="margin-right:15px;">添加</button>'
    ].join('');
}


window.entrancePolicyAddEvents = {
    'click .add': function (e, value, row, index) {
        console.log(row);
        //var selectedContent = $('#coupon-select-table').bootstrapTable('getSelections');
        $('#selectedPolicyList').bootstrapTable('insertRow', {
            index: index,
            row: {
                release_id: row.release_id,
                release_time: row.release_start_time + '至' + row.release_end_time,
                release_name: row.release_name
            }
        });
    }
};

window.entrancePolicyDeleteEvents = {
    'click .del': function (e, value, row, index) {
        $('#selectedPolicyList').bootstrapTable('removeByUniqueId', row.release_id);
    }
};

// 策略列表策略投放时间
function queryEntranceReleaseTimeFormatter(e, row, value) {
    return row.release_start_time + ' 至 ' + row.release_end_time;
}

// 策略列表策略投放状态
function queryEntranceReleaseStatusFormatter(e, row, value) {
    var statusArr = ['未开始', '开始中', '暂停投放', '已结束'];
    return statusArr[row.release_status];

}

function resolveMaxStockSizeError() {
    $('#chooseReleasePolicy').on('hidden.bs.modal', function () {
        var dialog = $('#choosePolicyModal').modal({
            backdrop: 'static',
            keyboard: false,
            width: 1100
        });
        dialog.modal('show');
    });
}

function onReleaseListModalClose() {
    $('#choosePolicyModal').on('hidden.bs.modal', function () {
        initEntranceList();
        //location.reload();
    });
}

function onChooseReleaseModalClose() {
    $('#choosePolicyModal').on('hidden.bs.modal', function () {
        //refreshList();
    });
}

function getMD5() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();
    var hours = date.getHours();
    if(month<10) {
        month= "" + 0 + month;
    }
    if (day.length < 2) {
        day = '0' + day;
    }
    var dateStr = "" + year + month + day + hours;
    console.log(dateStr);
    var str = date + "hiveview-weixinapi";
    var md5 = hex_md5(str);
    console.log(md5);
    return md5;
}