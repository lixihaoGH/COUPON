$(function () {
    searchStatics();
    initStatisticsList();
});

// 查询
function searchStatics() {
    $('#statistics_search').click(function () {
        initStatisticsList();
    });
}

// 初始化统计列表
function initStatisticsList() {
    console.log('开始执行');
    var coupon_stock_name = $('input[name="coupon_stock_name"]').val();
    console.log('coupon_stock_name:' + coupon_stock_name);
    var release_id = $('input[name="release_id"]').val();
    console.log('release_id:' + release_id);
    var url = serverDomain + '/statistics/getList.json';
    $('#coupon_statistics_list').bootstrapTable('destroy');
    $('#coupon_statistics_list').bootstrapTable({
        url: url,        //请求后台的URL（*）
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
        width: 700,
        clickToSelect: true,                //是否启用点击选中行
        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        queryParamsType: "undefined",
        queryParams: function (params) {
            if ((coupon_stock_name != undefined && coupon_stock_name != '') && (release_id == '')) {
                var temp = {
                    page_size: params.pageSize,   //页面大小
                    page_index: params.pageNumber,
                    coupon_stock_name: coupon_stock_name
                };
                return temp;
            } else if ((release_id != undefined && release_id != '') && (coupon_stock_name == '')) {
                var temp = {
                    page_size: params.pageSize,   //页面大小
                    page_index: params.pageNumber,
                    release_id: release_id
                };
                return temp;
            } else if ((release_id != undefined && release_id != '') && (coupon_stock_name != '' && coupon_stock_name != undefined)) {
                var temp = {
                    page_size: params.pageSize,   //页面大小
                    page_index: params.pageNumber,
                    release_id: release_id,
                    coupon_stock_name: coupon_stock_name
                };
                return temp;
            } else {
                var temp = {
                    page_size: params.pageSize,   //页面大小
                    page_index: params.pageNumber
                };
                return temp;
            }
        },
        onLoadError: function () {
            alert('查询失败,请重试!');
        },
        columns: [{
            field: 'coupon_stock_id',
            title: '红包id'
        }, {
            field: 'coupon_stock_name',
            title: '红包名称'
        }, {
            field: 'release_count',
            title: '投放量'
        }, {
            field: 'receiving_count',
            title: '已领取'
        }, {
            field: 'used_count',
            title: '已使用'
        }, {
            field: 'usage_rate',
            title: '已使用/投放数量'
        }, {
            field: 'total_payment_amount',
            title: '使用红包后获得收款'
        }, {
            field: 'release_id',
            title: '投放策略id'
        }]
    });
}