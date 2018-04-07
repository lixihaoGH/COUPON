// 定义全局变量 -- 删除时使用的id
var delete_coupon_id = '';
$(function () {
    coupon_stock_search();
    initTable();
});

// 查询
function coupon_stock_search() {
    $('#coupon_search').click(function () {
        var stock_id = $('input[name="coupon_stock_id"]').val();
        var stock_name = $('input[name="coupon_stock_name"]').val();
        initTable(stock_id, stock_name);
    });
}

function initTable(stock_id, stock_name) {
    var url = serverDomain + "/coupon_stock/getList.json";

    console.log(stock_id + '----' + stock_name);
    $('#coupon-list-table').bootstrapTable('destroy');
    $('#coupon-list-table').bootstrapTable({
        url: url,
        method: 'get',
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        showPaginationSwitch: false,         //是否显示条数选择框
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 10,                       //每页的记录行数（*）
        pageList: 'All',        //可供选择的每页的行数（*）
        minimumCountColumns: 2,             //最少允许的列数
        //clickToSelect: true,              //是否启用点击选中行
        uniqueId: "coupon_stock_id",        //每一行的唯一标识，一般为主键列
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        height: 500,
        queryParamsType: "undefined",
        queryParams: function (params) {
            if (stock_id != undefined && stock_id != '') {
                var temp = {
                    page_size: params.pageSize,   //页面大小
                    page_index: params.pageNumber,
                    coupon_stock_id: stock_id
                };
                return temp;
            } else {
                if (stock_name != undefined && stock_name != '') {
                    var temp = {
                        page_size: params.pageSize,   //页面大小
                        page_index: params.pageNumber,
                        coupon_stock_name: stock_name
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
        onLoadError: function () {
            alert('查询失败,请重试!');
        },
        columns: [{
            field: 'coupon_stock_id',
            title: 'id'
        }, {
            field: 'coupon_stock_name',
            title: '红包名称',
            formatter: innerPageFormatter
        }, {
            field: 'expiration_date',
            title: '有效期',
            formatter: queryFormatter
        }, {
            field: 'operate',
            title: '操作',
            align: 'center',
            formatter: function (value, row) {
                var str = '';
                str += '<button onclick="javascript:delete_coupon_id =&quot;'+row.coupon_stock_id.toLocaleString() + '&quot;;deleteButton();" type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px; ">删除</button>';
                return str;
            }
        }]
    });

}

function queryFormatter(e, row, value) {

    var timeStr = '';
    if ((row.hasOwnProperty('effective_time') && (row.hasOwnProperty('expired_time')))) {
        timeStr = row.effective_time + ' 至 ' + row.expired_time;
    } else {
        timeStr = '领取后' + row.effective_duration + '天内有效';
    }
    return timeStr.toString();
}

function deleteButton() {
    console.log('deleteButton:' + delete_coupon_id);
    var deleteDiv = $('#deleteDiv').modal({
        backdrop: 'static',
        keyboard: false,
        left: 60 + '%'
    });
    deleteDiv.modal('show');
}

function deleteCouponEvents() {
    var deleteDiv = $('#deleteDiv').modal({
        backdrop: 'static',
        keyboard: false,
        left: 60 + '%'
    });
    deleteDiv.modal('hide');
    var dataArray = $('#coupon-list-table').bootstrapTable('getData');
    var idList = [];
    for (var i = 0; i < dataArray.length; i++) {
        idList.push(dataArray[i].coupon_stock_id);
    }
    console.log("删除前：" + idList);
    for (var j = 1; j <= idList.length; j++) {
        if (delete_coupon_id == idList[j - 1]) {
            console.log('321');
            idList.splice(j - 1, 1);
            var url = serverDomain + '/coupon_stock/delete.json?coupon_stock_id=';
            $.get(url + delete_coupon_id,
                function (data) {
                    if (data.return_code == 1) {
                        $('#coupon-list-table').bootstrapTable('remove', {
                            field: 'coupon_stock_id',
                            values: [delete_coupon_id]
                        });
                        alert("删除成功!");
                    } else {
                        alert(data.return_value);
                    }
                }
            );
            break;
        }
    }
}

/* $('#deleteModalButton').click(function () {

    });*/


function innerPageFormatter(row, value) {
    return [
        '<a target="_blank" href=' + '"' + '/coupon/get.json?coupon_stock_id=' + value.coupon_stock_id + '">' + value.coupon_stock_name + '</a>'
    ].join('');
}

