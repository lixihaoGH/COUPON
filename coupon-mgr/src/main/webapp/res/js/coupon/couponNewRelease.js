var selectCouponFlag = false;
var updateSelectedCouponFlag = false;
var release_id;
var release_update_url = '';
var release_update_id = '';
//var img_webaccess_path = 'http://localhost:8082/pic/';    //开发本地路径
var img_webaccess_path = 'http://static.weixin.pthv.gitv.tv/images/coupon/';    //测试路径
//var img_webaccess_path = 'http://static.weixin.coupon.pthv.gitv.tv/images/';  //正式路径
$(function () {
    // 点击策略状态查询投放策略信息
    searchByReleaseStatus();
    // 初始化策略列表
    initReleaseListTable();
    initCouponList();
    initNewCouponList();
    initUpdateCouponList();
    // 策略名称查询按钮
    releaseSearchButton();
    // 新建投放策略按钮
    createNewReleaseBtn();
    // 新建投放策略模态框--时间日期控件
    createNewReleaseDateTimeBox();
    // 新建投放策略模态框--选择红包按钮
    createNewReleaseChooseCouponButton();
    // 选择红包页面 -- 选择红包
    searchCouponBootstrapTable();
    showSearchCouponBootstrapTable();
    // 选择红包页面 -- 保存已选红包
    saveSelectedReleaseCoupon();
    updateSelectedReleaseCoupon();
    // 上传组件 -- 顶部广告图
    uploadFile('gotoPageImg');
    uploadFile('updateGotoPageImg');
    // 新建投放策略页面 -- 点击预览按钮
    createNewReleaseShareHtmlButton();
    showNewReleaseShareHtmlButton();
    // 上传组件 -- 分享图片
    uploadFile('shareImage');
    uploadFile('showShareImage');
    // 保存新建投放策略
    saveNewRelease();
    updateNewRelease();
    // 投放策略详情模态框--时间日期控件
    showReleaseInfoDateTimeBox();
    // 投放策略详情 -- 红包列表
    showReleaseInfoCouponList();
    // 投放策略详情页 -- 选择红包页面
    showReleaseInfoCouponButton();

    // 解决退栈异常问题
    createCloseButton();
    showCloseButton();
});

// 点击策略状态查询投放策略信息
function searchByReleaseStatus() {
    $('input[name="release_status"]').click(function () {
        searchByStatus();
    });
}

function searchByStatus() {
    console.log('searchByStatus');
    $('#release-stock-name').val('');
    var releaseStatus = $('input[name="release_status"]:checked').val();
    $('#coupon-release-list').bootstrapTable('destroy');
    $('#coupon-release-list').bootstrapTable({
        url: serverDomain + '/release/getList.json',         //请求后台的URL（*）
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
        height: 500,
        clickToSelect: true,                //是否启用点击选中行
        uniqueId: "release_id",                     //每一行的唯一标识，一般为主键列
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        queryParamsType: "undefined",
        queryParams: function (params) {
            //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                page_size: params.pageSize,   //页面大小
                page_index: params.pageNumber,
                release_status: releaseStatus
            };
            return temp;
        },
        onLoadError: function () {
            alert('查询失败,请重试!');
        },
        columns: [{
            field: 'release_id',
            title: '策略id'
        }, {
            field: 'release_name',
            title: '策略名称',
            formatter: showReleaseInfoFormatter,
            events: showPolicyInfoEvents
        }, {
            field: 'release_status',
            title: '时间状态',
            formatter: queryReleaseStatusFormatter
        }, {
            field: 'releaseTime',
            title: '投放时间',
            formatter: queryReleaseTimeFormatter
        }, {
            field: 'operate',
            title: '操作',
            events: releaseLineInnerOperateEvents,
            formatter: releaseLineInnerButton
        }]
    });
}

// 策略名称查询按钮
function releaseSearchButton() {
    $('#couponPolicySearchButton').click(function () {
        searchByName();
    });
}

function searchByName() {
    console.log('searchByName');
    $('input[name="release_status"][value=""]').attr('checked', true);
    var releaseName = $('#release-stock-name').val();
    $('#coupon-release-list').bootstrapTable('destroy');
    $('#coupon-release-list').bootstrapTable({
        url: serverDomain + '/release/getList.json',         //请求后台的URL（*）
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
        height: 500,
        clickToSelect: true,                //是否启用点击选中行
        uniqueId: "release_id",                     //每一行的唯一标识，一般为主键列
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        queryParamsType: "undefined",
        queryParams: function (params) {
            //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                page_size: params.pageSize,   //页面大小
                page_index: params.pageNumber,
                release_name: releaseName
            };
            return temp;
        },
        onLoadError: function () {
            alert('查询失败,请重试!');
        },
        columns: [{
            field: 'release_id',
            title: '策略id'
        }, {
            field: 'release_name',
            title: '策略名称',
            formatter: showReleaseInfoFormatter,
            events: showPolicyInfoEvents
        }, {
            field: 'release_status',
            title: '时间状态',
            formatter: queryReleaseStatusFormatter
        }, {
            field: 'releaseTime',
            title: '投放时间',
            formatter: queryReleaseTimeFormatter
        }, {
            field: 'operate',
            title: '操作',
            events: releaseLineInnerOperateEvents,
            formatter: releaseLineInnerButton
        }]
    });
}

function initReleaseListTable() {
    var releaseStatus = $('input[name="release_status"]:checked').val();
    var releaseName = $('#release-stock-name').val();
    if (releaseStatus != '' && releaseStatus != undefined) {
        searchByStatus();
    } else if (releaseName != '' && releaseName != undefined) {
        searchByName()
    } else {
        $('#coupon-release-list').bootstrapTable('destroy');
        $('#coupon-release-list').bootstrapTable({
            url: serverDomain + '/release/getList.json',         //请求后台的URL（*）
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
            height: 500,
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "release_id",                     //每一行的唯一标识，一般为主键列
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            queryParamsType: "undefined",
            queryParams: function (params) {
                //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                    page_size: params.pageSize,   //页面大小
                    page_index: params.pageNumber
                };
                return temp;
            },
            onLoadError: function () {
                alert('查询失败,请重试!');
            },
            columns: [{
                field: 'release_id',
                title: '策略id'
            }, {
                field: 'release_name',
                title: '策略名称',
                formatter: showReleaseInfoFormatter,
                events: showPolicyInfoEvents
            }, {
                field: 'release_status',
                title: '时间状态',
                formatter: queryReleaseStatusFormatter
            }, {
                field: 'releaseTime',
                title: '投放时间',
                formatter: queryReleaseTimeFormatter
            }, {
                field: 'operate',
                title: '操作',
                events: releaseLineInnerOperateEvents,
                formatter: releaseLineInnerButton
            }]
        });
    }
}

// 链接--查看策略详情
function showReleaseInfoFormatter(e, row, value) {
    return '<a class="policyNameLink">' + row.release_name + '</a>';
}

// 链接事件--判断策略是否可以修改
window.showPolicyInfoEvents = {
    'click .policyNameLink': function (e, value, row) {
        var release_status = row.release_status;
        if (release_status == 0) {
            $('#saveReleaseInfo').show();
            $('#show-release-coupon-btn').show();
        } else {
            $('#saveReleaseInfo').hide();
            $('#show-release-coupon-btn').hide();
        }
        releaseEvents(row.release_id);
    }
};

// 列表行内按钮 -- 根据投放状态显示不同按钮
function releaseLineInnerButton(e, row, value) {
    var stat = row.release_status;
    if (stat == "3") {
        return [
            '<span style="margin-right: 20px;">投放结束</span>'
        ].join('');
    } else if (stat == "2") {
        return [
            '<button type="button" class="btn btn-default btn-sm cancleStop" style="margin-right:15px;">取消停发</button>'
        ].join('');
    } else if (stat == "0") {
        return [
            '<button type="button" class="btn btn-default btn-sm deletePolicy" style="margin-right:15px;">删除</button>'
        ].join('');
    } else {
        return [
            '<button type="button" class="btn btn-default btn-sm stopPolicy" style="margin-right:15px;">停发</button>'
        ].join('');
    }

}

// 列表行内按钮 -- 不同按钮触发不同事件
window.releaseLineInnerOperateEvents = {
    // 删除
    'click .deletePolicy': function (e, value, row, index) {
        var bts = document.getElementsByClassName("deletePolicy");
        console.log(bts);
        var release_status = row.release_status;
        console.log(row.release_status);
        var modal = $('#errorMessageDiv').modal({
            backdrop: 'static',
            keyboard: false,
            width: 300 + 'px'
        });
        if (release_status == 0) {
            $('#errorMessageP').html('确认删除？');
            modal.modal('show');
            release_update_id = row.release_id;
            release_update_url = serverDomain + '/release/delete.json?release_id=' + release_update_id;
        } else {
            $('#errorMessageP').html('红包已投放，无法删除');
            modal.modal('show');
        }
    },
    // 停发
    'click .stopPolicy': function (e, value, row, index) {
        var bts = document.getElementsByClassName("stopPolicy");
        console.log(bts);
        var modal = $('#errorMessageDiv').modal({
            backdrop: 'static',
            keyboard: false,
            width: 300 + 'px'
        });
        release_update_id = row.release_id;
        $('#errorMessageP').html('确认暂停投放？');
        console.log('显示确认暂停投放');
        modal.modal('show');
        release_update_url = serverDomain + '/release/updateStatus.json?release_id=' + release_update_id + '&release_status=2';
    },
    // 取消停发
    'click .cancleStop': function (e, value, row, index) {
        var modal = $('#errorMessageDiv').modal({
            backdrop: 'static',
            keyboard: false,
            width: 300 + 'px'
        });
        release_update_id = row.release_id;
        release_update_url = serverDomain + '/release/updateStatus.json?release_id=' + release_update_id + '&release_status=1';
        $('#errorMessageP').html('确认取消停发？');
        modal.modal('show');
    }
};

// 展示策略详情
function releaseEvents(releaseId) {
    release_id = releaseId;
    var modal = $('#show-release-info-modal').modal({
        backdrop: 'static',
        keyboard: false,
        width: 820 + 'px'
    });
    modal.modal('show');
    var url = serverDomain + '/release/get.json?release_id=' + releaseId;
    $.get(url, function (releaseInfo) {
        console.log(releaseInfo);
        $('input[name="show_release_name"]').val(releaseInfo.release.release_name);
        $('input[name="show_release_count"]').val(releaseInfo.release.release_count);
        $('#show_release_start_time').datetimebox("setValue", releaseInfo.release.release_start_time);
        $('#show_release_end_time').datetimebox("setValue", releaseInfo.release.release_end_time);
        // 投放策略详情 -- 红包列表
        showReleaseInfoCouponList(releaseInfo.release.stock_id_list);
        // TODO 目前只有手机端可以使用红包
        if (releaseInfo.receiving_restriction.hasOwnProperty('phone_day_max')) {
            $('input[name="show_phone_day_checked"][value="0"]').attr('checked', true);
            $('input[name="update_phone_day_max"]').val(releaseInfo.receiving_restriction.phone_day_max);
        }
        if (releaseInfo.receiving_restriction.hasOwnProperty('phone_total_max')) {
            $('input[name="show_phone_total_checked"][value="1"]').attr('checked', true);
            $('input[name="update_phone_total_max"]').val(releaseInfo.receiving_restriction.phone_total_max);
        }
        $('input[name="show_page_title"]').val(releaseInfo.template.page_title);
        $('input[name="show_goto_page_url"]').val(releaseInfo.template.goto_page_url);
        $('input[name="show_page_bgcolor"]').val(releaseInfo.template.page_bgcolor);
        $('input[name="show_share_title"]').val(releaseInfo.template.share_title);
        $('textarea[name="show_share_desc"]').val(releaseInfo.template.share_desc);
        $('#showShareImage').attr('src', img_webaccess_path + releaseInfo.template.share_img_url);
        $('#updateGotoPageImg').attr('src', img_webaccess_path + releaseInfo.template.page_img_url);
    }, 'json');
}

// 投放策略详情 -- 红包列表
function showReleaseInfoCouponList(stock_id_list) {
    if (stock_id_list == undefined) {
        return;
    }
    //清除上次显示时加载的数据,避免重复添加
    $('#showSaveSuccessCouponList').bootstrapTable('removeAll');
    var url = serverDomain + "/coupon_stock/get.json?coupon_stock_id=";
    var stock_ids = stock_id_list.toString().split(',');
    for (var i = 0; i < stock_ids.length; i++) {
        if (stock_ids[i] != '' && stock_ids[i] != undefined) {
            $.get(url + stock_ids[i], function (data) {
                if (data.return_code == 0) {
                    alert(data.return_value);
                } else {
                    var expiration_date;
                    if (data.yougou_restriction.hasOwnProperty('effective_duration')) {
                        expiration_date = '领取后' + data.yougou_restriction.effective_duration + '天内有效';
                    } else {
                        expiration_date = data.yougou_restriction.effective_time + ' 至 ' + data.yougou_restriction.expired_time;
                    }
                    $('#showSaveSuccessCouponList').bootstrapTable('insertRow', {
                        index: 1,
                        row: {
                            coupon_stock_id: data.coupon_stock.coupon_stock_id,
                            coupon_stock_name: data.coupon_stock.coupon_stock_name,
                            expiration_date: expiration_date
                        }
                    });
                }
            });
        }
    }
}

function initCouponList() {
    //$('#showSaveSuccessCouponList').bootstrapTable('destroy');
    $('#showSaveSuccessCouponList').bootstrapTable({
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 5,                     //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        minimumCountColumns: 2,             //最少允许的列数
        //height: 400,
        clickToSelect: true,                //是否启用点击选中行
        uniqueId: "coupon_stock_id",                     //每一行的唯一标识，一般为主键列
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        columns: [{
            field: 'coupon_stock_id',
            title: 'id'
        }, {
            field: 'coupon_stock_name',
            title: '红包名称'
        }, {
            field: 'expiration_date',
            title: '有效期'
        }, {
            field: 'operate',
            title: '操作',
            events: showDeleteReleaseCouponEvents,
            formatter: deleteReleaseOperateFormatter
        }]
    });
}

// 新建投放策略按钮
function createNewReleaseBtn() {
    $('#new-release-btn').click(function () {
        $('#saveSuccessCouponList').bootstrapTable('removeAll');
        // 开启模态框
        var dialog = $('#create-new-release-modal').modal({
            backdrop: 'static',
            keyboard: false,
            width: 820 + 'px'
        });
        dialog.modal('show');
        // 初始化--新建投放策略页面已选红包列表
        $('#saveSuccessCouponList').bootstrapTable({
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
            pageSize: 5,                     //每页的记录行数（*）
            pageList: 'All',        //可供选择的每页的行数（*）
            minimumCountColumns: 2,             //最少允许的列数
            //height: 400,
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "coupon_stock_id",                     //每一行的唯一标识，一般为主键列
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            columns: [{
                field: 'coupon_stock_id',
                title: 'id'
            }, {
                field: 'coupon_stock_name',
                title: '红包名称'
            }, {
                field: 'expiration_date',
                title: '有效期'
                // formatter: queryCouponTimeFormatter
            }, {
                field: 'operate',
                title: '操作',
                events: deleteReleaseCouponEvents,
                formatter: deleteReleaseOperateFormatter
            }]
        });

    });
}

// 新建投放策略 -- 选择红包列表 -- 删除按钮
function deleteReleaseOperateFormatter() {
    return [
        '<button type="button" class="deleteRelease btn btn-default btn-sm" style="margin-right:15px;">删除</button>'
    ].join('');
}

// 新建投放策略 -- 选择红包列表 -- 删除按钮事件
window.deleteReleaseCouponEvents = {
    'click .deleteRelease': function (e, value, row, index) {
        console.log('deleteReleaseCouponEvents');
        $('#saveSuccessCouponList').bootstrapTable('removeByUniqueId', row.coupon_stock_id);
    }
};

// 展示投放策略详情 -- 选择红包列表 -- 删除按钮事件
window.showDeleteReleaseCouponEvents = {
    'click .deleteRelease': function (e, value, row, index) {
        $('#showSaveSuccessCouponList').bootstrapTable('removeByUniqueId', row.coupon_stock_id);
    }
};

// 新建投放策略模态框时间控件
function createNewReleaseDateTimeBox() {
    function ww4(date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        var h = date.getHours();
        var mm = date.getMinutes();
        var s = date.getSeconds();
        return y + "-" + (m < 10 ? ('0' + m) : m) + "-" + (d < 10 ? ('0' + d) : d) + " " + (h < 10 ? ('0' + h) : h) + ":" + (mm < 10 ? ('0' + mm) : mm) + ":" + (s < 10 ? ('0' + s) : s);
    }

    $('#release_start_time').datetimebox({
        panelWidth: 300,
        panelHeight: 300,
        editable: true,
        currentText: 'Today',
        okText: 'Ok',
        closeText: 'Close',
        showSeconds: true,
        timeSeparator: ':',
        formatter: function (date) {
            return ww4(date);
        }
    });

    $('#release_end_time').datetimebox({
        panelWidth: 300,
        panelHeight: 300,
        editable: true,
        currentText: 'Today',
        okText: 'Ok',
        closeText: 'Close',
        showSeconds: true,
        timeSeparator: ':',
        formatter: function (date) {
            return ww4(date);
        }
    });
}

// 查看投放策略详情模态框时间控件
function showReleaseInfoDateTimeBox() {
    function ww4(date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        var h = date.getHours();
        var mm = date.getMinutes();
        var s = date.getSeconds();
        return y + "-" + (m < 10 ? ('0' + m) : m) + "-" + (d < 10 ? ('0' + d) : d) + " " + (h < 10 ? ('0' + h) : h) + ":" + (mm < 10 ? ('0' + mm) : mm) + ":" + (s < 10 ? ('0' + s) : s);
    }

    $('#show_release_start_time').datetimebox({
        panelWidth: 300,
        panelHeight: 300,
        editable: true,
        currentText: 'Today',
        okText: 'Ok',
        closeText: 'Close',
        showSeconds: true,
        timeSeparator: ':',
        formatter: function (date) {
            return ww4(date);
        }
    });

    $('#show_release_end_time').datetimebox({
        panelWidth: 300,
        panelHeight: 300,
        editable: true,
        currentText: 'Today',
        okText: 'Ok',
        closeText: 'Close',
        showSeconds: true,
        timeSeparator: ':',
        formatter: function (date) {
            return ww4(date);
        }
    });
}

// 新建投放策略模态框--选择红包按钮
function createNewReleaseChooseCouponButton() {
    $('#release-coupon-btn').click(function () {
        // 开启模态框
        $('#create-choose-release-coupon').modal({
            backdrop: 'static',
            keyboard: false
        });
        var dialog = $('#create-new-release-modal').modal({
            backdrop: 'static',
            keyboard: false,
            width: 820 + 'px'
        });
        dialog.modal('hide');
        $('#selectedCoupon').bootstrapTable('destroy');
        // 初始化“已选”部分
        selectedCouponBootstrapTable();
    });
}

// 解决退栈异常 -- start
function createCloseButton() {
    $('#create-choose-release-coupon').on('hidden.bs.modal', function () {
        var dialog = $('#create-new-release-modal').modal({
            backdrop: 'static',
            keyboard: false,
            width: 820 + 'px'
        });
        dialog.modal('show');
    });
    $('#share-html-modal').on('hidden.bs.modal', function () {
        var shareHtmlModal = $('#create-new-release-modal').modal({
            backdrop: 'static',
            keyboard: false,
            width: 820 + 'px'
        });
        shareHtmlModal.modal('show');
    });
}

function showCloseButton() {
    $('#update-choose-release-coupon').on('hidden.bs.modal', function () {
        var dialog = $('#show-release-info-modal').modal({
            backdrop: 'static',
            keyboard: false,
            width: 820 + 'px'
        });
        dialog.modal('show');
    });
    $('#update-share-html-modal').on('hidden.bs.modal', function () {
        var shareHtmlModal = $('#show-release-info-modal').modal({
            backdrop: 'static',
            keyboard: false,
            width: 820 + 'px'
        });
        shareHtmlModal.modal('show');
    });
}

// 解决退栈异常 -- end

// 展示 -- 投放策略详情页 -- 选择红包页面
function showReleaseInfoCouponButton() {
    $('#show-release-coupon-btn').click(function () {
        // 开启模态框
        var dialog = $('#update-choose-release-coupon').modal({
            backdrop: 'static',
            keyboard: false
        });
        dialog.modal('show');
        var showInfoDialog = $('#show-release-info-modal').modal({
            backdrop: 'static',
            keyboard: false,
            width: 820 + 'px'
        });
        showInfoDialog.modal('hide');
        $('#updateSelectedCoupon').bootstrapTable('destroy');
        // 初始化“已选”部分
        showSelectedCouponBootstrapTable();
    });
}

// 新建投放策略 -- 选择红包页面 -- 初始化已选
function selectedCouponBootstrapTable() {
    $('#selectedCoupon').bootstrapTable({
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 5,                     //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        minimumCountColumns: 2,             //最少允许的列数
        height: 400,
        clickToSelect: true,                //是否启用点击选中行
        uniqueId: "coupon_stock_id",                     //每一行的唯一标识，一般为主键列
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        columns: [{
            field: 'operate',
            title: '操作',
            events: deleteSelectedCouponOperateEvents,
            formatter: deleteSelectedCouponOperateFormatter
        }, {
            field: 'coupon_stock_id',
            title: 'id'
        }, {
            field: 'coupon_stock_name',
            title: '红包名称'
        }]
    });
}

// 投放策略详情 -- 选择红包页面 -- 初始化已选
function showSelectedCouponBootstrapTable() {
    $('#updateSelectedCoupon').bootstrapTable({
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 5,                     //每页的记录行数（*）
        pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
        minimumCountColumns: 2,             //最少允许的列数
        height: 400,
        clickToSelect: true,                //是否启用点击选中行
        uniqueId: "coupon_stock_id",                     //每一行的唯一标识，一般为主键列
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        columns: [{
            field: 'operate',
            title: '操作',
            events: showDeleteSelectedCouponOperateEvents,
            formatter: showDeleteSelectedCouponOperateFormatter
        }, {
            field: 'coupon_stock_id',
            title: 'id'
        }, {
            field: 'coupon_stock_name',
            title: '红包名称'
        }]
    });
}

// 点击事件--点击删除按钮将选中的行删除
window.deleteSelectedCouponOperateEvents = {
    'click .deleteSelectedCoupon': function (e, value, row, index) {
        $('#selectedCoupon').bootstrapTable('removeByUniqueId', row.coupon_stock_id);
    }
};

window.showDeleteSelectedCouponOperateEvents = {
    'click .updatedeleteSelectedCoupon': function (e, value, row, index) {
        $('#updateSelectedCoupon').bootstrapTable('removeByUniqueId', row.coupon_stock_id);
    }
};

// 行内按钮 -- 删除按钮
function deleteSelectedCouponOperateFormatter() {
    console.log(312);
    return [
        '<button type="button" class="deleteSelectedCoupon btn btn-default btn-sm" style="margin-right:15px;">删除</button>'
    ].join('');
}

function showDeleteSelectedCouponOperateFormatter() {
    return [
        '<button type="button" class="updatedeleteSelectedCoupon btn btn-default btn-sm" style="margin-right:15px;">删除</button>'
    ].join('');
}

// 选择红包页面 -- 选择红包
function searchCouponBootstrapTable() {
    $('#release-search-btn').click(function () {
        // 清除现有数据
        $('#coupon-select-table').bootstrapTable('destroy');
        // 获取查询参数
        var selectedCouponId = $('#selectedCouponId').val();
        console.log(selectedCouponId);
        var selectedCouponName = $('#selectedCouponName').val();
        console.log(selectedCouponName);
        // 发送请求查询数据
        $('#coupon-select-table').bootstrapTable({
            url: serverDomain + "/coupon_stock/getList.json",
            method: 'get',
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: true,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
            pageSize: 5,                        //每页的记录行数（*）
            pageList: 'All',        //可供选择的每页的行数（*）
            minimumCountColumns: 2,             //最少允许的列数
            height: 400,                        // table高度
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "coupon_stock_id",        //每一行的唯一标识，一般为主键列
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            queryParamsType: "undefined",
            queryParams: function (params) {
                if (selectedCouponId != '') {
                    var temp = {
                        page_size: params.pageSize,   //页面大小
                        page_index: params.pageNumber,
                        coupon_stock_id: selectedCouponId
                    };
                    return temp;
                } else {
                    if (selectedCouponName != '') {
                        var temp = {
                            page_size: params.pageSize,   //页面大小
                            page_index: params.pageNumber,
                            coupon_stock_name: selectedCouponName
                        };
                        return temp;
                    }
                }
                var temp = {
                    page_size: params.pageSize,   //页面大小
                    page_index: params.pageNumber
                };
                return temp;
            },
            columns: [{
                field: 'operate',
                title: '操作',
                events: createReleaseAddoperateEvents,
                formatter: showReleaseInfoAddOperateFormatter
            }, {
                field: 'coupon_stock_id',
                title: 'id'
            }, {
                field: 'coupon_stock_name',
                title: '红包名称',
                formatter: showCouponStockInfoFormatter
            }]
        });
    });
}

function showCouponStockInfoFormatter(e, row, value) {
    return ['<a target="_blank" href=' + '"' + '/coupon/get.json?coupon_stock_id=' + row.coupon_stock_id + '">' + row.coupon_stock_name + '</a>'
    ].join('');
}


// 投放策略详情 -- 选择红包侧 -- 查询已有红包
function showSearchCouponBootstrapTable() {
    $('#update-release-search-btn').click(function () {
            // 清除现有数据x
            $('#update-coupon-select-table').bootstrapTable('destroy');
            // 获取查询参数
            var selectedCouponId = $('#updateSelectedCouponId').val();
            console.log(selectedCouponId);
            var selectedCouponName = $('#updateSelectedCouponName').val();
            console.log(selectedCouponName);
            // 发送请求查询数据
            $('#update-coupon-select-table').bootstrapTable({
                url: serverDomain + "/coupon_stock/getList.json",
                method: 'get',
                toolbar: '#toolbar',                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   //是否显示分页（*）
                sortable: true,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
                pageSize: 5,                        //每页的记录行数（*）
                pageList: 'All',        //可供选择的每页的行数（*）
                minimumCountColumns: 2,             //最少允许的列数
                height: 400,                        // table高度
                clickToSelect: true,                //是否启用点击选中行
                uniqueId: "coupon_stock_id",        //每一行的唯一标识，一般为主键列
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                queryParamsType: "undefined",
                queryParams: function (params) {
                    if (selectedCouponId != '') {
                        var temp = {
                            page_size: params.pageSize,   //页面大小
                            page_index: params.pageNumber,
                            coupon_stock_id: selectedCouponId
                        };
                        return temp;
                    } else {
                        if (selectedCouponName != '') {
                            var temp = {
                                page_size: params.pageSize,   //页面大小
                                page_index: params.pageNumber,
                                coupon_stock_name: selectedCouponName
                            };
                            return temp;
                        }
                    }
                    var temp = {
                        page_size: params.pageSize,   //页面大小
                        page_index: params.pageNumber
                    };
                    return temp;
                },
                columns: [{
                    field: 'operate',
                    title: '操作',
                    events: showReleaseAddoperateEvents,
                    formatter: showReleaseInfoAddOperateFormatter
                }, {
                    field: 'coupon_stock_id',
                    title: 'id'
                }, {
                    field: 'coupon_stock_name',
                    title: '红包名称',
                    formatter: showCouponStockInfoFormatter
                }]
            });
        }
    );
}

function initUpdateCouponList() {
    $('#update-coupon-select-table').bootstrapTable('destroy');
    $('#update-coupon-select-table').bootstrapTable({
        url: serverDomain + "/coupon_stock/getList.json",
        method: 'get',
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 5,                        //每页的记录行数（*）
        pageList: 'All',        //可供选择的每页的行数（*）
        minimumCountColumns: 2,             //最少允许的列数
        height: 400,                        // table高度
        clickToSelect: true,                //是否启用点击选中行
        uniqueId: "coupon_stock_id",        //每一行的唯一标识，一般为主键列
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
            events: showReleaseAddoperateEvents,
            formatter: showReleaseInfoAddOperateFormatter
        }, {
            field: 'coupon_stock_id',
            title: 'id'
        }, {
            field: 'coupon_stock_name',
            title: '红包名称',
            formatter: showCouponStockInfoFormatter
        }]
    });
}

function initNewCouponList() {
    $('#coupon-select-table').bootstrapTable('destroy');
    $('#coupon-select-table').bootstrapTable({
        url: serverDomain + "/coupon_stock/getList.json",
        method: 'get',
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 5,                        //每页的记录行数（*）
        pageList: 'All',        //可供选择的每页的行数（*）
        minimumCountColumns: 2,             //最少允许的列数
        height: 400,                        // table高度
        clickToSelect: true,                //是否启用点击选中行
        uniqueId: "coupon_stock_id",        //每一行的唯一标识，一般为主键列
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
            events: createReleaseAddoperateEvents,
            formatter: showReleaseInfoAddOperateFormatter
        }, {
            field: 'coupon_stock_id',
            title: 'id'
        }, {
            field: 'coupon_stock_name',
            title: '红包名称',
            formatter: showCouponStockInfoFormatter
        }]
    });
}

// 行内按钮 -- 点击添加
function showReleaseInfoAddOperateFormatter() {
    return [
        '<button type="button" class="add btn btn-default btn-sm" style="margin-right:15px;">添加</button>'
    ].join('');
}

// 行内按钮 -- 点击后从右边添加到左边
window.createReleaseAddoperateEvents = {
    'click .add': function (e, value, row, index) {
        $('#selectedCoupon').bootstrapTable('insertRow', {
            index: index,
            row: {
                coupon_stock_id: row.coupon_stock_id,
                coupon_stock_name: row.coupon_stock_name
            }
        });
    }
};

window.showReleaseAddoperateEvents = {
    'click .add': function (e, value, row, index) {
        $('#updateSelectedCoupon').bootstrapTable('insertRow', {
            index: index,
            row: {
                coupon_stock_id: row.coupon_stock_id,
                coupon_stock_name: row.coupon_stock_name
            }
        });
    }
};

// 保存 -- 已选红包
function saveSelectedReleaseCoupon() {
    $('#saveSelectedBtn').click(function () {
        var data = $('#selectedCoupon').bootstrapTable('getOptions');
        console.log(data);
        if (data.data.length <= 0) {
            // 没有选择红包
            alert('请选择红包');
        } else {
            selectCouponFlag = true;
            // 获取页面所添加的红包ID数据
            var list = [];
            for (var i = 0; i < data.data.length; i++) {
                list[i] = data.data[i].coupon_stock_id
            }
            console.log(list);
            // 发送请求获取完整数据
            var url = serverDomain + '/coupon_stock/get.json?coupon_stock_id=';
            for (var i = 0; i < list.length; i++) {
                $.get(url + list[i],
                    function (result) {
                        if (result.return_code != 1) {
                            alert('查询失败');
                        } else {
                            var expiration_date;
                            if (result.yougou_restriction.hasOwnProperty('effective_duration')) {
                                expiration_date = '领取后' + result.yougou_restriction.effective_duration + '天内有效';
                            } else {
                                expiration_date = result.yougou_restriction.effective_time + ' 至 ' + result.yougou_restriction.expired_time;
                            }
                            $('#saveSuccessCouponList').bootstrapTable('insertRow', {
                                index: i + 1,
                                row: {
                                    coupon_stock_id: result.coupon_stock.coupon_stock_id,
                                    coupon_stock_name: result.coupon_stock.coupon_stock_name,
                                    expiration_date: expiration_date
                                }
                            });
                        }
                    }, 'json'
                )
            }
            $('#create-choose-release-coupon').modal('hide');
        }
    });
}

function updateSelectedReleaseCoupon() {
    $('#updateSelectedBtn').click(function () {
        $('#update-choose-release-coupon').modal('hide');
        var data = $('#updateSelectedCoupon').bootstrapTable('getData');
        console.log(data);
        if (data.length <= 0) {
            // 没有选择红包
            alert('请选择红包');
        } else {
            selectCouponFlag = true;
            updateSelectedCouponFlag = true;
            // 获取页面所添加的红包ID数据
            var list = [];
            for (var i = 0; i < data.length; i++) {
                list[i] = data[i].coupon_stock_id
            }
            console.log(list);
            // 发送请求获取完整数据
            var url = serverDomain + "/coupon_stock/get.json?coupon_stock_id=";
            for (var j = 0; j < list.length; j++) {
                $.get(url + list[j],
                    function (result) {
                        var expiration_date;
                        console.log(result);
                        if (result.yougou_restriction.hasOwnProperty('effective_duration')) {
                            expiration_date = '领取后' + result.yougou_restriction.effective_duration + '天内有效';
                        } else {
                            expiration_date = result.yougou_restriction.effective_time + ' 至 ' + result.yougou_restriction.expired_time;
                        }
                        // 请求成功之后向列表添加
                        $('#showSaveSuccessCouponList').bootstrapTable('insertRow', {
                            index: i + 1,
                            row: {
                                coupon_stock_id: result.coupon_stock.coupon_stock_id,
                                coupon_stock_name: result.coupon_stock.coupon_stock_name,
                                expiration_date: expiration_date
                            }
                        });
                    });
            }
        }
    });
}

// 新建投放策略页面 -- 点击预览按钮
function createNewReleaseShareHtmlButton() {
    // TODO 缺少顶部广告图
    $('#share-html-btn').click(function () {
        // 开启模态框
        var dialog = $('#share-html-modal').modal({
            backdrop: 'static',
            keyboard: false
        });
        dialog.modal('show');
        var dialog1 = $('#create-new-release-modal').modal({
            backdrop: 'static',
            keyboard: false,
            width: 820 + 'px'
        });
        dialog1.modal('hide');
        var share_page_title = $('input[name="page_title"]').val();
        var goto_page_bgcolor = $('input[name="page_bgcolor"]').val();
        var gotoPageImage = $('#gotoPageImg').attr('src');
        console.log(goto_page_bgcolor);
        $('#sharePageDiv').css("background-color", goto_page_bgcolor);
        $('#pageShareTitle').html(share_page_title);
        $('#share-img').attr('src', gotoPageImage);
    });
}

function showNewReleaseShareHtmlButton() {
    // TODO 缺少顶部广告图
    $('#show-share-html-btn').click(function () {
        // 开启模态框
        var showInfoModal = $('#show-release-info-modal').modal({
            backdrop: 'static',
            keyboard: false,
            width: 820 + 'px'
        });
        showInfoModal.modal('hide');
        var dialog = $('#update-share-html-modal').modal({
            backdrop: 'static',
            keyboard: false
        });
        dialog.modal('show');
        var share_page_title = $('input[name="show_page_title"]').val();
        var goto_page_bgcolor = $('input[name="show_page_bgcolor"]').val();
        var updateGotoPageImg = $('#updateGotoPageImg').attr('src');
        console.log(goto_page_bgcolor);
        $('#showSharePageDiv').css("background-color", goto_page_bgcolor);
        $('#showPageShareTitle').html(share_page_title);
        $('#showShareImg').attr('src', updateGotoPageImg);
    });
}

// 保存新建投放策略
function saveNewRelease() {
    $('#saveCouponPolicy').click(function () {
        if (!selectCouponFlag) {
            // 没有进行过红包选择不能保存
            alert("请重新添加红包");
        } else {
            var release_name = $('input[name="release_name"]').val();
            var release_start_time = $('#release_start_time').datetimebox("getValue");
            console.log(release_start_time);
            var release_end_time = $('#release_end_time').datetimebox("getValue");
            console.log(release_end_time);
            var release_count = $('input[name="release_count"]').val();
            if (release_name == '') {
                alert('请输入策略名称');
            } else if (release_start_time == '') {
                alert('请输入开始时间');
            } else if (release_end_time == '') {
                alert('请输入结束时间');
            } else if (release_start_time > release_end_time) {
                alert('请输入正确的开始、结束时间');
            } else if (release_count == '' || parseInt(release_count) <= 0) {
                alert('请输入正确的投放量数值');
            } else if ($('input[name="phone_day_checked"][value="0"]').is(':checked') && $('#day-max-input').val() < 0) {
                alert('请输入正确的领取限制次数');
            } else if ($('input[name="phone_day_checked"][value="1"]').is(':checked') && $('#day-amount-input').val() < 0) {
                alert('请输入正确的领取限制总数');
            } else {
                var phone_day_max = $('input[name="phone_day_max"]').val();
                var phone_total_max = $('input[name="phone_total_max"]').val();

                var page_title = $('input[name="page_title"]').val();
                var page_bgcolor = $('input[name="page_bgcolor"]').val();
                var goto_page_url = $('input[name="goto_page_url"]').val();
                var page_img_url = $('#gotoPageImg').attr('data-relative-path');
                var share_img_url = $('#shareImage').attr('data-relative-path');
                var share_title = $('input[name="share_title"]').val();
                var share_desc = $('textarea').val();
                var idList = $('#saveSuccessCouponList').bootstrapTable('getData');
                var couponIdList = '';
                for (var i = 0; i < idList.length; i++) {
                    couponIdList += idList[i].coupon_stock_id + ',';
                }
                var url = serverDomain + '/release/save.json';
                // 封装数据
                var jsonData = {
                    release: {
                        release_name: release_name,
                        stock_id_list: couponIdList,
                        release_start_time: release_start_time,
                        release_end_time: release_end_time,
                        release_count: release_count

                    },
                    receiving_restriction: {
                        phone_day_max: phone_day_max,
                        phone_total_max: phone_total_max
                    },
                    template: {
                        page_title: page_title,
                        page_bgcolor: page_bgcolor,
                        goto_page_url: goto_page_url,
                        page_img_url: page_img_url,
                        share_img_url: share_img_url,
                        share_title: share_title,
                        share_desc: share_desc
                    }
                };
                deleteEmptyProperty(jsonData);
                // 向后台发送请求，保存策略数据
                $.ajax({
                    type: 'post',
                    url: url,
                    contentType: 'application/json',
                    dataType: "json",
                    //数据格式是json串，商品信息
                    data: JSON.stringify(jsonData),
                    success: function (data) {
                        // 保存成功
                        if (data.return_code != 1) {
                            alert(data.return_value);
                        } else {
                            alert('保存成功!');
                            initReleaseListTable();
                        }
                    }
                });
            }
        }
    });
}

// 更新投放策略
function updateNewRelease() {
    $('#saveReleaseInfo').click(function () {

        var release_name = $('input[name="show_release_name"]').val();
        var release_start_time = $('#show_release_start_time').datetimebox('getValue');
        var release_end_time = $('#show_release_end_time').datetimebox('getValue');
        var release_count = $('input[name="show_release_count"]').val();
        var idList = $('#showSaveSuccessCouponList').bootstrapTable('getData');
        if (release_name == '') {
            alert('请输入策略名称');
        } else if (release_start_time == '') {
            alert('请输入开始时间');
        } else if (release_end_time == '') {
            alert('请输入结束时间');
        } else if (release_start_time > release_end_time) {
            alert('请输入正确的开始、结束时间');
        } else if (release_count == '' || parseInt(release_count) <= 0) {
            alert('请输入正确的投放量数值');
        } else if ($('input[name="phone_day_checked"][value="0"]').is(':checked') && $('#day-max-input').val() < 0) {
            alert('请输入正确的领取限制次数');
        } else if ($('input[name="phone_day_checked"][value="1"]').is(':checked') && $('#day-amount-input').val() < 0) {
            alert('请输入正确的领取限制总数');
        } else if (idList.length <= 0) {
            alert('请选择策略所包含的红包');
        } else {
            var phone_day_max = $('input[name="update_phone_day_max"]').val();
            var phone_total_max = $('input[name="update_phone_total_max"]').val();
            var page_title = $('input[name="show_page_title"]').val();
            var page_bgcolor = $('input[name="show_page_bgcolor"]').val();
            var goto_page_url = $('input[name="show_goto_page_url"]').val();
            var page_img_url = $('#updateGotoPageImg').attr('data-relative-path');
            var share_img_url = $('#showShareImage').attr('data-relative-path');
            var share_title = $('input[name="show_share_title"]').val();
            var share_desc = $('textarea[name="show_share_desc"]').val();
            var couponIdList = '';
            for (var i = 0; i < idList.length; i++) {
                couponIdList += idList[i].coupon_stock_id + ',';
            }
            var url = serverDomain + '/release/update.json';
            // 封装数据
            var jsonData = {
                release: {
                    release_id: release_id,
                    release_name: release_name,
                    stock_id_list: couponIdList,
                    release_start_time: release_start_time,
                    release_end_time: release_end_time,
                    release_count: release_count
                },
                receiving_restriction: {
                    phone_day_max: phone_day_max,
                    phone_total_max: phone_total_max
                },
                template: {
                    page_title: page_title,
                    page_bgcolor: page_bgcolor,
                    goto_page_url: goto_page_url,
                    page_img_url: page_img_url,
                    share_img_url: share_img_url,
                    share_title: share_title,
                    share_desc: share_desc
                }
            };
            deleteEmptyProperty(jsonData);
            // 向后台发送请求，保存策略数据
            $.ajax({
                type: 'post',
                url: url,
                contentType: 'application/json',
                dataType: "json",
                //数据格式是json串，商品信息
                data: JSON.stringify(jsonData),
                success: function (data) {
                    if (data.return_code == 0) {
                        alert(data.return_value);
                    } else {
                        alert("操作成功!");
                        initReleaseListTable();
                    }
                }
            });
        }
    });
}

// 清空新建投放策略页面
function clearCreateReleaseInfo() {
    $('input[name="release_name"]').val('');
    $('#release_start_time').datetimebox('setValue', '');
    $('#release_end_time').datetimebox('setValue', '');
    $('input[name="release_count"]').val('');
    $('#day-max-input').val('1');
    $('#day-amount-input').val('1');
    $('input[name="share_page_title"]').val('');
    $('input[name="goto_page_bgcolor"]').val('');
    $('input[name="goto_page_url"]').val('');
    $('input[name="share_title"]').val('');
    $('textarea[name="share_desc"]').val('');
    $('#saveSuccessCouponList').bootstrapTable('');
}

//上传图片
function uploadFile(file_id) {
    var file_upload = '#' + file_id + '_file_upload';
    var file_type = '';
    $(file_upload).uploadify(
        {
            'swf': '/res/uploadify/uploadify.swf',
            'uploader': '/action/upload',
            'height': 25,
            'width': 120,
            'auto': true,
            'fileSizeLimit': '209715200',
            'fileDataName': 'file',
            'buttonText': '浏览...',
            'fileTypeExts': '*.gif; *.jpg; *.png;*.bmp',
            'multi': false,
            'method': 'post',
            'debug': false,
            'queueSizeLimit': 1,
            'removeTimeout': 1,
            'onSelect': function (file) {
                var myself = this;
                var size = file.size;
                if (file.type != ".jpg" && file.type != ".png"
                    && file.type != ".gif") {
                    alert('上传文件错误，请选择合法图片！');
                    $(file_upload).uploadify('cancel', '*');
                }
                if (file.type == ".jpg" || file.type == ".png"
                    || file.type == ".gif") {
                    if (size > 1024 * 1024 * 1) {
                        alert('图片大小超出1MB！');
                        myself.cancelUpload(file.id);
                        $('#' + file.id).remove();
                        $(file_upload).uploadify('cancel', '*');
                    }
                    file_type = 'img';
                }
            },
            'onUploadStart': function (file) {// 传参
                var param = {};
                param.picHref = $(file_upload).val();
                $(file_upload).uploadify("settings", "formData", param);
            },
            'onUploadSuccess': function (file, data, response) {
                var json = $.parseJSON(data);
                var imgId = '#' + file_id;
                console.log(json.result);
                if (json.result != null && json.code == '1') {
                    if (file_type == 'img') {
                        $(imgId).attr('src', img_webaccess_path + json.result);// 图片预览
                        $(imgId).attr('data-relative-path', json.result);// 保存图片相对目录
                    }
                }
            },
            'onUploadError': function (file, errorCode, errorMsg, errorString) {
                var errMsg = 'The file ' + file.name
                    + ' could not be uploaded: ' + errorString;
                alert(errMsg);
            }
        });
}

// 信息提示弹框及执行操作， 包含错误信息提示、删除提示、停发提示、取消停发提示
function operateButton() {
    $.get(release_update_url, function (data) {
        console.log('operateButton----3');
        if (data.return_code != 1) {
            $('#errorMessageP').html(data.return_value);
            $('#errorMessageDiv').modal('hide');
        } else {
            $('#errorMessageDiv').modal('hide');
            initReleaseListTable();
        }
    });
   /* console.log(new Date().getTime());
    console.log('operateButton----2');
    if (cnt === 0) { //莫名的会多次执行click事件的方法,随着表格初始化次数的增加而增加次数,
        cnt++;       //(猜测是模态框使用方式的问题,再次初始化未销毁上次生成的,导致实际存在多个),以此解决

    }*/
    /*var cnt = 0;
    // 点击确认时，向后台发送请求
    console.log('operateButton----1');
    $('#errorModalButton').click(function () {

    });*/
}

// 查询投放策略列表时间
function queryReleaseTimeFormatter(e, row, value) {
    return row.release_start_time + ' 至 ' + row.release_end_time;
}

// 查询投放策略投放状态
function queryReleaseStatusFormatter(e, row, value) {
    var statusArr = ['未开始', '开始中', '暂停投放', '已结束'];
    return statusArr[row.release_status];

}

function deleteEmptyProperty(object) {
    for (var i in object) {
        var value = object[i];
        if (typeof value === 'object') {
            if (Array.isArray(value)) {
                if (value.length == 0) {
                    delete object[i];
                    continue;
                }
            }
            deleteEmptyProperty(value);
            if (isEmpty(value)) {
                delete object[i];
            }
        } else {
            if (value === '' || value === ' ' || value === null || value === undefined) {
                delete object[i];
            } else {
            }
        }
    }
}

function isEmpty(object) {
    for (var name in object) {
        return false;
    }
    return true;
}
