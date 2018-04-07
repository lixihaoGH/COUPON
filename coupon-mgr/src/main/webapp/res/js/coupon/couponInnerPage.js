// 未填写 -- 0  填写 -- 1
//var selectedGoodsCheckboxValue = '0';
//var excludedGoodsCheckBoxValue = '0';
var unuseGoodsDivValue = '0';
var selectedScreenAddSubmit = 'false';
var excludedScreenAddSubmit = 'false';
var selectedBatchAddSubmit = 'false';
var excludedBatchAddSubmit = 'false';
var selectedTableInfoList = '';
var excludedTableInfoList = '';
var batchAddType = '';
var goodsRangeValue = '0';
$(function () {
    showCouponInfo();
    //hideGoodsDiv();
    setCommonValue();
    selecedScreenSearch();
    excludedScreenSearch();
    test();
    saveNewCouponStock();
    dateTimeBox();
    changeBatchAddType();
    selectedGoodsModal();
    excludedGoodsModal();
    $('#screenAddModalFooter').hide();
    $('#second-level').hide();
    $('#third-level').hide();
    showSelectedTable('selected');
    showSelectedTable('excluded');
    selectedAddTypeInfo();
    excludedAddTypeInfo();
    getSelectedGoodsInfo();
    getExcludedGoodsInfo();
    excludedGoodsTableBootstrap2();
    clearTime();
});

// 设置默认值或者选项
function setCommonValue() {
    // 设置margin
    $('#restriction_description').css({'margin': '10px 0px 0px 0px'});
    // “全部商品”单选框隐藏相应div
    $('input[name="goods_range"][value="0"]').attr("checked", true);
    $('#goods-div').show();
    $('#unuse-checkbox').css({'margin': '10px 0px 0px 0px'});
    // 设置高度
    //$('#createnewcoupon-div').css({'height': '350px'});
    $('#first-level').html('');
    $('#selected-first-level').combobox();
    $('#selected-second-level').combobox();
    $('#selected-third-level').combobox();
    $('#excluded-first-level').combobox();
    $('#excluded-second-level').combobox();
    $('#excluded-third-level').combobox();
}

function showSelectedTable(type) {
    var tableType = '#' + type + 'GoodsTable';
    $(tableType).bootstrapTable({
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 10,                     //每页的记录行数（*）
        pageList: 'All',        //可供选择的每页的行数（*）
        width: 600,
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        uniqueId: "goodsId",                     //每一行的唯一标识，一般为主键列
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        columns: [{
            field: 'goodsId',
            title: '分类id/SKU'
        }, {
            field: 'goodsType',
            title: '分类'
        }, {
            field: 'goodsName',
            title: '名称'
        }, {
            field: 'specifications',
            title: '规格'
        }, {
            field: 'operate',
            title: '操作',
            events: selectedOperateEvents,
            formatter: selectedOperateFormatter
        }]
    });
}

function allGoodsType() {
    goodsRangeValue = $('input[name="goods_range"]').val();
    selectedScreenAddSubmit = 'false';
    selectedBatchAddSubmit = 'false';
    //selectedGoodsCheckboxValue = '';
    $('#selectedGoodsTable').bootstrapTable('destroy');
    $('#goods-div').hide();
    $('#unuse-checkbox').css({'margin': '10px 0px 0px 0px'});
}

function partGoodsType() {
    goodsRangeValue = $('input[name="goods_range"]').val();
    $('input[name="selected_goods_category"]').attr("checked", false);
    $('#goods-div').show();
    $('#unuse-checkbox').css({'margin': '10px 0px 0px 0px'});
}

// 点击“全部商品”单选框隐藏相应div
/*function hideGoodsDiv() {
    $('input[name="goods_range"]').click(function () {

        console.log(goodsRangeValue);
        if (goodsRangeValue == '0') {
            
        } else {

            //$('#createnewcoupon-div').css({'height': '1500px'});
        }
    });
}*/

// 点击“设置不可用商品”复选框隐藏相应div
function hideUnusableDiv() {
    var unusableCheckbox = $('input[name="coupon_unuse_checkbox"]');
    if (unusableCheckbox.is(':checked')) {
        unuseGoodsDivValue = '1';
        $('#unusadle-div').show();
        $('input[name="excluded_goods_category"]').attr("checked", false);
        //$('#createnewcoupon-div').css({'height': '1500px'});
    } else {
        unuseGoodsDivValue = '0';
        excludedScreenAddSubmit = 'false';
        excludedBatchAddSubmit = 'false';
        $('#excludedGoodsTable').bootstrapTable('destroy');
        $("#unusadle-div").hide();
    }
}

/*
function selectedGoodsCheckbox() {
    var show = $('input[name="selected_goods_category"]');
    if (!show.is(':checked')) {
        //selectedGoodsCheckboxValue = '0';
        console.log(selectedGoodsCheckboxValue);
    } else {
        selectedGoodsCheckboxValue = '1';
    }
}
*/

/*
function excludedGoodsCheckBox() {
    var show = $('input[name="excluded_goods_category"]');
    if (!show.is(':checked')) {
        excludedGoodsCheckBoxValue = '0';
        console.log(excludedGoodsCheckBoxValue);
    } else {
        excludedGoodsCheckBoxValue = '1';
    }
}
*/


function selectedGoodsTableBootstrap(list, type) {
    console.log(list.toString());
    var url = serverDomain + '/yougou/check.json';
    $.post(url, {
        snList: list,
        type: type
    }, function (data) {
        if (data.return_code == 1) {
            if (type == 1) {
                console.log("type = 1: " + data.rows)
                // 只选择一级分类
                for (var i = 0; i < data.rows.length; i++) {
                    $('#selectedGoodsTable').bootstrapTable('insertRow', {
                        index: 1,
                        row: {
                            goodsId: data.rows[i].categorySn,
                            goodsType: '一级分类',
                            goodsName: data.rows[i].categoryName,
                            specifications: ''
                        }
                    });
                }
            }
            if (type == 2) {
                // 只选择二级分类
                for (var i = 0; i < data.rows.length; i++) {
                    $('#selectedGoodsTable').bootstrapTable('insertRow', {
                        index: 1,
                        row: {
                            goodsId: data.rows[i].categorySn,
                            goodsType: '二级分类',
                            goodsName: data.rows[i].categoryName,
                            specifications: ''
                        }
                    });
                }
            }
            if (type == 3) {
                for (var i = 0; i < data.rows.length; i++) {
                    $('#selectedGoodsTable').bootstrapTable('insertRow', {
                        index: 1,
                        row: {
                            goodsId: data.rows[i].categorySn,
                            goodsType: '三级分类',
                            goodsName: data.rows[i].categoryName,
                            specifications: ''
                        }
                    });
                }
            }
            if (type == null || type == '' || type == -1) {
                for (var i = 0; i < data.rows.length; i++) {
                    $('#selectedGoodsTable').bootstrapTable('insertRow', {
                        index: i + 1,
                        row: {
                            goodsId: data.rows[i].goodskuSn,
                            goodsType: '单品',
                            goodsName: data.rows[i].goodsName,
                            specifications: data.rows[i].specItemValues
                        }
                    });
                }
            }
        } else {
            alert(data.return_value + ": " + data.rows.toString());
        }
    });
}

function selectedGoodsModal() {
// 可用商品限制模态框
    $('#selectedScreenAdd').click(function () {
        $('#selectedAddModalFooter').hide();
        $('#selectedGoodsInfoTable').bootstrapTable('destroy');
        $('input[name="updateSelectedGoodsName"]').val('');
        $('#selected-first-level').combobox('setValue','');
        $('#selected-second-level').combobox('setValue','');
        $('#selected-third-level').combobox('setValue','');
        var dialog = $('#selectedScreenAddModal').modal({
            backdrop: 'static',
            keyboard: false/*,
            width: 750*/
        });
        dialog.modal('show');
    });
    $('#selectedScreenSubmitButton').click(function () {
        var tableInfo = $('#selectedGoodsInfoTable').bootstrapTable('getAllSelections');
        console.log(tableInfo);
        if (tableInfo.length <= 0) {
            alert("尚未选择商品");
        } else {
            selectedScreenAddSubmit = 'true';
            //$('#selectedScreenAddModal').modal('hide');
            for (var i = 0; i < tableInfo.length; i++) {
                selectedTableInfoList += tableInfo[i].goodsId + ',';
            }
            console.log('ScreenAdd -- selectedTableInfoList:' + selectedTableInfoList);
            // 存储可用商品限制
            for (var j = 0; j < tableInfo.length; j++) {
                $('#selectedGoodsTable').bootstrapTable('insertRow', {
                    index: j + 1,
                    row: {
                        goodsId: tableInfo[j].goodskuSn,
                        goodsType: '单品',
                        goodsName: tableInfo[j].goodsName,
                        specifications: tableInfo[j].specItemValues
                    }
                });
            }
            alert('添加成功');
        }
    });

    $('#selectedBatchAdd').click(function () {
        $('#selectedGoodsTypeTextArea').val('');
        var dialog = $('#selectedBatchAddModal').modal({
            backdrop: 'static',
            keyboard: false
        });
        dialog.modal('show');
        $('input[name="selectedBatchAddType"]').click(function () {
            batchAddType = $(this).val();
            console.log(batchAddType);
        });
    });
    $('#selectedBatchSubmitButton').click(function () {
        var selectGoodsList = '';
        $('#selectedBatchAddModal').modal('hide');
        var goodsList = $('#selectedGoodsTypeTextArea').val().split("\n");
        console.log('selectedBatchAdd:' + goodsList);
        if (batchAddType == '' || goodsList.length <= 0) {
            alert("数据为空，请填充");
        } else {
            var goodsTypeArray = '';
            var nary = goodsList.sort();
            for (var i = 0; i < goodsList.length; i++) {
                if (nary[i] == nary[i + 1]) {
                    goodsTypeArray += "数组重复内容：" + nary[i];
                }
            }
            if (goodsTypeArray == '') {
                selectedBatchAddSubmit = 'true';
                for (var i = 0; i < goodsList.length; i++) {
                    selectGoodsList += goodsList[i] + ',';
                }
                console.log('添加 -- ' + selectGoodsList);
                selectedTableInfoList += selectGoodsList + ',';
                selectedGoodsTableBootstrap(selectGoodsList, batchAddType);
            } else {
                alert(goodsTypeArray);
            }
        }
    });
}


function excludedGoodsTableBootstrap2() {
    $('#excludedGoodsTable').bootstrapTable('destroy');
    $('#excludedGoodsTable').bootstrapTable({
        toolbar: '#toolbar',                //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 10,                     //每页的记录行数（*）
        pageList: 'All',        //可供选择的每页的行数（*）
        width: 600,
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        uniqueId: "goodsId",                     //每一行的唯一标识，一般为主键列
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        columns: [{
            field: 'goodsId',
            title: '分类id/SKU'
        }, {
            field: 'goodsType',
            title: '分类'
        }, {
            field: 'goodsName',
            title: '名称'
        }, {
            field: 'specifications',
            title: '规格'
        }, {
            field: 'operate',
            title: '操作',
            events: excludedOperateEvents,
            formatter: excludedOperateFormatter
        }]
    });
}


function excludedGoodsTableBootstrap(list, type) {
    var url = serverDomain + '/yougou/check.json';
    $.post(url, {
        snList: list,
        type: type
    }, function (data) {
        if (data.return_code == 1) {
            if (type == 1) {
                // 只选择一级分类
                for (var i = 0; i < data.rows.length; i++) {
                    $('#excludedGoodsTable').bootstrapTable('insertRow', {
                        index: 1,
                        row: {
                            goodsId: data.rows[i].categorySn,
                            goodsType: '一级分类',
                            goodsName: data.rows[i].categoryName,
                            specifications: ''
                        }
                    });
                }
            }
            if (type == 2) {
                // 只选择二级分类
                for (var i = 0; i < data.rows.length; i++) {
                    $('#excludedGoodsTable').bootstrapTable('insertRow', {
                        index: 1,
                        row: {
                            goodsId: data.rows[i].categorySn,
                            goodsType: '二级分类',
                            goodsName: data.rows[i].categoryName,
                            specifications: ''
                        }
                    });
                }
            }
            if (type == 3) {
                for (var i = 0; i < data.rows.length; i++) {
                    $('#excludedGoodsTable').bootstrapTable('insertRow', {
                        index: 1,
                        row: {
                            goodsId: data.rows[i].categorySn,
                            goodsType: '三级分类',
                            goodsName: data.rows[i].categoryName,
                            specifications: ''
                        }
                    });
                }
            }
            if (type == null || type == '' || type == -1) {
                for (var i = 0; i < data.rows.length; i++) {
                    $('#excludedGoodsTable').bootstrapTable('insertRow', {
                        index: i + 1,
                        row: {
                            goodsId: data.rows[i].goodskuSn,
                            goodsType: '单品',
                            goodsName: data.rows[i].goodsName,
                            specifications: data.rows[i].specItemValues
                        }
                    });
                }
            }

        } else {
            alert(data.return_value + " : " + data.rows.toString());
        }
    });
}

// 不可用商品限制模态框
function excludedGoodsModal() {
    $('#excludedScreenAdd').click(function () {
        $('#excludedAddModalFooter').hide();
        $('#excludedGoodsInfoTable').bootstrapTable('destroy');
        $('input[name="updateExcludedGoodsName"]').val('');
        $('#excluded-first-level').combobox('setValue','');
        $('#excluded-second-level').combobox('setValue','');
        $('#excluded-third-level').combobox('setValue','');
        var dialog = $('#excludedScreenAddModal').modal({
            backdrop: 'static',
            keyboard: false
        });
        dialog.modal('show');
    });
    $('#excludedSubmitButton').click(function () {
        var tableInfo = $('#excludedGoodsInfoTable').bootstrapTable('getAllSelections');
        if (tableInfo.length <= 0) {
            console.log("尚未选择商品");
        } else {
            excludedScreenAddSubmit = 'true';
            //$('#excludedScreenAddModal').modal('hide');
            for (var i = 0; i < tableInfo.length; i++) {
                excludedTableInfoList += tableInfo[i].goodsId + ',';
            }
            console.log('ScreenAdd -- excludedTableInfoList:' + excludedTableInfoList);
            // 添加成功后在主页面显示数据
            for (var j = 0; j < tableInfo.length; j++) {
                $('#excludedGoodsTable').bootstrapTable('insertRow', {
                    index: j + 1,
                    row: {
                        goodsId: tableInfo[j].goodskuSn,
                        goodsType: '单品',
                        goodsName: tableInfo[j].goodsName,
                        specifications: tableInfo[j].specItemValues
                    }
                });
            }
            alert('添加成功');
        }
    });
    $('#excludedBatchAdd').click(function () {
        $('#excludedGoodsTypeTextArea').val('');
        var dialog = $('#excludedBatchAddModal').modal({
            backdrop: 'static',
            keyboard: false
        });
        dialog.modal('show');
        $('input[name="excludedBatchAddType"]').click(function () {
            batchAddType = $(this).val();
            console.log(batchAddType);
        });
    });
    $('#excludedBatchSubmitButton').click(function () {
        var excludedGoodsList = '';
        $('#excludedBatchAddModal').modal('hide');
        var goodsList = $('#excludedGoodsTypeTextArea').val();
        goodsList = goodsList.split("\n");
        console.log('excludedBatchAdd:' + goodsList);
        if (batchAddType == '' || goodsList.length <= 0) {
            alert('数据为空，请添加数据');
        } else {
            var goodsTypeArray = '';
            var nary = goodsList.sort();
            for (var i = 0; i < goodsList.length; i++) {
                if (nary[i] == nary[i + 1]) {
                    goodsTypeArray += "数组重复内容：" + nary[i];
                }
            }
            if (goodsTypeArray == '') {
                selectedBatchAddSubmit = 'true';
                for (var i = 0; i < goodsList.length; i++) {
                    excludedGoodsList += goodsList[i] + ',';
                }
                console.log('添加 -- ' + excludedGoodsList);
                excludedTableInfoList += excludedGoodsList + ',';
                excludedGoodsTableBootstrap(excludedGoodsList, batchAddType);
            } else {
                alert(goodsTypeArray);
            }
        }
    });
}

// 可用商品限制按钮设置 -- start
function selectedOperateFormatter() {
    return [
        '<button type="button" class="selectedDelete btn btn-default btn-sm" style="margin-right:15px;" onclick="">删除</button>'
    ].join('');
}

window.selectedOperateEvents = {
    'click .selectedDelete': function (e, value, row) {
        var dataArray = $('#selectedGoodsTable').bootstrapTable('getData');
        var idList = [];
        for (var i = 0; i < dataArray.length; i++) {
            idList[i] = dataArray[i].goodsId;
        }
        console.log("删除前：" + idList);
        for (var j = 1; j <= idList.length; j++) {
            if (row.goodsId == idList[j - 1]) {
                idList.splice(j - 1, 1);
                $('#selectedGoodsTable').bootstrapTable('remove', {
                    field: 'goodsId',
                    values: [row.goodsId]
                });
                break;
            }
        }
        console.log("删除后：" + idList);
    }
};
// 可用商品限制按钮设置 -- end

// 不可用商品限制按钮设置 -- start
function excludedOperateFormatter() {
    return [
        '<button type="button" class="excludedDelete btn btn-default btn-sm" style="margin-right:15px;">删除</button>'
    ].join('');
}

window.excludedOperateEvents = {
    'click .excludedDelete': function (e, value, row) {
        var dataArray = $('#excludedGoodsTable').bootstrapTable('getData');
        var idList = [];
        for (var i = 0; i < dataArray.length; i++) {
            idList[i] = dataArray[i].goodsId;
        }
        console.log("删除前：" + idList);
        for (var j = 1; j <= idList.length; j++) {
            if (row.goodsId == idList[j - 1]) {
                idList.splice(j - 1, 1);
                $('#excludedGoodsTable').bootstrapTable('remove', {
                    field: 'goodsId',
                    values: [row.goodsId]
                });
                break;
            }
        }
        console.log("删除后：" + idList);
    }
};
// 不可用商品限制按钮设置 -- end


// 创建按钮--添加删除按钮
function delOperateFormatter() {
    return [
        '<button type="button" class="del btn btn-default btn-sm" style="margin-right:15px;">删除</button>'
    ].join('');
}

// 绑定点击事件--点击添加按钮将选中的行从右侧复制到左侧
window.deloperateEvents = {
    'click .add': function (e, value, row, index) {
        var selectedContent = $('#policyList').bootstrapTable('getSelections');
        $('#selectedPolicyList').bootstrapTable('remove', selectedContent);
    }
};

// 筛选添加弹出层操作
function test() {

    // 关闭
    $('#close-button').click(function () {
        $('#second-level').html('');
        $('#third-level').html('');
        $('#screenGoodsInfoTable').bootstrapTable('destroy')
    });
}

function selecedScreenSearch() {
    $('#selected-search-button').click(function () {
        $('#selectedGoodsInfoTable').bootstrapTable('destroy');
        $('#selectedAddModalFooter').show();
        var goodsName = $('input[name="updateSelectedGoodsName"]').val();
        var url = serverDomain + '/yougou/search.json?name=' + goodsName;
        $.get(url,
            function (data) {
                $.ajaxSettings.async = false;
                $('#selectedGoodsInfoTable').bootstrapTable({
                    toolbar: '#toolbar',                //工具按钮用哪个容器
                    striped: true,                      //是否显示行间隔色
                    cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true,                   //是否显示分页（*）
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
                    pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
                    pageSize: 10,                     //每页的记录行数（*）
                    pageList: 'All',        //可供选择的每页的行数（*）
                    width: 600,
                    minimumCountColumns: 2,             //最少允许的列数
                    clickToSelect: true,                //是否启用点击选中行
                    uniqueId: "id",                     //每一行的唯一标识，一般为主键列
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    search: true,
                    columns: [{
                        field: 'checked',
                        checkbox: true
                    }, {
                        field: 'goodskuSn',
                        title: '分类id/SKU'
                    }, {
                        field: 'goodsName',
                        title: '商品名称'
                    }, {
                        field: 'specItemValues',
                        title: '规格参数'
                    }, {
                        field: 'price',
                        title: '现价'
                    }, {
                        field: 'marketPrice',
                        title: '市场价'
                    }],
                    data: data.rows
                });
                fixStyle();
            }, 'json'
        );

    });
}

function excludedScreenSearch() {
    $('#excluded-search-button').click(function () {
        $('#excludedGoodsInfoTable').bootstrapTable('destroy');
        $('#excludedAddModalFooter').show();
        var goodsName = $('input[name="updateExcludedGoodsName"]').val();
        var url = serverDomain + '/yougou/search.json?name=' + goodsName;
        $.get(url,
            function (data) {
                $.ajaxSettings.async = false;
                $('#excludedGoodsInfoTable').bootstrapTable({
                    toolbar: '#toolbar',                //工具按钮用哪个容器
                    striped: true,                      //是否显示行间隔色
                    cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true,                   //是否显示分页（*）
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
                    pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
                    pageSize: 10,                     //每页的记录行数（*）
                    pageList: 'All',        //可供选择的每页的行数（*）
                    width: 600,
                    minimumCountColumns: 2,             //最少允许的列数
                    clickToSelect: true,                //是否启用点击选中行
                    uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
                    search: true,
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    columns: [{
                        field: 'checked',
                        checkbox: true
                    }, {
                        field: 'goodskuSn',
                        title: '分类id/SKU'
                    }, {
                        field: 'goodsName',
                        title: '商品名称'
                    }, {
                        field: 'specItemValues',
                        title: '规格参数'
                    }, {
                        field: 'price',
                        title: '现价'
                    }, {
                        field: 'marketPrice',
                        title: '市场价'
                    }],
                    data: data.rows
                });
                fixStyle();
            }, 'json'
        );

    });
}

function clearTime() {

    $('input[name="effective_time_type"]').click(function () {
        var test = $(this).val();
        console.log(test);
        if (test == '0') {
            $('#effective_time').datetimebox('setValue', '');
            $('#expired_time').datetimebox('setValue', '');
        } else {
            $('input[name="effective_duration"]').val('');
        }
    });
}

// 当前页面提交
function saveNewCouponStock() {
    var url = serverDomain + '/coupon_stock/update/yougou.json';
    var effectiveTimeType = '0';
    $('input[name="goods_range"]').click(function () {
        goodsRangeValue = $(this).val();
        console.log("提交前可用商品选择：(0-全 1-部) " + goodsRangeValue);
    });
    $('#new-coupon-submit').click(function () {
            var preferential_type = $('input[name="preferential_type"]').val();
            //console.log(preferential_type);
            effectiveTimeType = $('input[name="effective_time_type"]:checked').val();
            console.log("effective_time_type" + effectiveTimeType);
            if ($('input[name="coupon_stock_name"]').val() == '') {
                alert('红包名称未填写');
            } else if ($('input[name="preferential_full_price"]').val() == '') {
                alert('满额限制未填写');
            } else if (parseInt($('input[name="preferential_full_price"]').val()) < 0) {
                alert('满额限制必须大于零');
            } else if ($('input[name="preferential_reduce_price"]').val() == '') {
                alert('减价额度未填写');
            } else if (parseInt($('input[name="preferential_reduce_price"]').val()) < 0) {
                alert('减价额度必须大于零');
            } else if (effectiveTimeType == '0' && $('input[name="effective_receiving_time"]').val() == '') {
                alert('领取期限未填写');
            } else if (effectiveTimeType == '1' && ($('input[name="effective_time"]').val() == '' || $('input[name="expired_time"]').val() == '')) {
                alert('领取时间范围未填写');
            } /*else if (goodsRangeValue == '1' && selectedGoodsCheckboxValue == '') {
                alert('没有选择可用商品品类');
            } */ else if (goodsRangeValue == '1' && (selectedScreenAddSubmit == 'false' && selectedBatchAddSubmit == 'false')) {
                alert('尚未选择可用商品');
            } /*else if (goodsRangeValue == '1' && excludedGoodsCheckBoxValue == '') {
                alert('没有选择不可用商品品类');
            } */ else if (unuseGoodsDivValue == '1' && (excludedScreenAddSubmit == 'false' && excludedBatchAddSubmit == 'false')) {
                alert('尚未选择不可用商品');
            } else if ((goodsRangeValue == '1' || unuseGoodsDivValue == '1') && $('#description').val() == '') {
                alert('限制描述未填写')
            } else {
                var selectedGoodsTableInfo = $('#selectedGoodsTable').bootstrapTable('getData');
                var selectedIdList = '';
                var selectedFirstLevelList = '';
                var selectedSecondLevelList = '';
                var selectedThirdLevelList = '';
                for (var i = 0; i < selectedGoodsTableInfo.length; i++) {
                    if (selectedGoodsTableInfo[i].goodsType == '单品') {
                        selectedIdList += selectedGoodsTableInfo[i].goodsId + ',';
                    } else if (selectedGoodsTableInfo[i].goodsType == '一级分类') {
                        selectedFirstLevelList += selectedGoodsTableInfo[i].goodsId + ',';
                    } else if (selectedGoodsTableInfo[i].goodsType == '二级分类') {
                        selectedSecondLevelList += selectedGoodsTableInfo[i].goodsId + ',';
                    } else if (selectedGoodsTableInfo[i].goodsType == '三级分类') {
                        selectedThirdLevelList += selectedGoodsTableInfo[i].goodsId + ',';
                    }
                }
                var excludedGoodsTableInfo = $('#excludedGoodsTable').bootstrapTable('getData');
                var excludedIdList = '';
                var excludedFirstLevelList = '';
                var excludedSecondLevelList = '';
                var excludedThirdLevelList = '';
                for (var j = 0; j < excludedGoodsTableInfo.length; j++) {
                    if (excludedGoodsTableInfo[j].goodsType == '单品') {
                        excludedIdList += excludedGoodsTableInfo[j].goodsId + ',';
                    } else if (excludedGoodsTableInfo[j].goodsType == '一级分类') {
                        excludedFirstLevelList += excludedGoodsTableInfo[j].goodsId + ',';
                    } else if (excludedGoodsTableInfo[j].goodsType == '二级分类') {
                        excludedSecondLevelList += excludedGoodsTableInfo[j].goodsId + ',';
                    } else if (excludedGoodsTableInfo[j].goodsType == '三级分类') {
                        excludedThirdLevelList += excludedGoodsTableInfo[j].goodsId + ',';
                    }
                }
                if (effectiveTimeType == '0') {
                    $('input[name="effective_time"]').val('');
                    $('input[name="expired_time"]').val('');
                } else {
                    $('input[name="effective_duration"]').val('')
                }
                console.log($('#effective_time').datetimebox('getValue'));
                console.log($('#expired_time').datetimebox('getValue'));
                console.log($('input[name="effective_duration"]').val());
                var preferential_amount = '';
                if (preferential_type == '1') {
                    preferential_amount = $('input[name="preferential_reduce_price"]').val();
                } else {
                    preferential_amount = '';
                }
                var jsonData = {
                    coupon_stock: {
                        'coupon_stock_id': $('#coupon_stock_id').val(),
                        'coupon_stock_name': $('input[name="coupon_stock_name"]').val(),
                        'preferential_type': preferential_type,
                        'preferential_amount': preferential_amount
                    },
                    yougou_restriction: {
                        'coupon_stock_id': $('#coupon_stock_id').val(),
                        'goods_range': goodsRangeValue,
                        'reach_amount': $('input[name="preferential_reduce_price"]').val(),
                        'effective_duration': $('input[name="effective_duration"]').val(),
                        'effective_time': $('#effective_time').datetimebox('getValue'),
                        'expired_time': $('#expired_time').datetimebox('getValue'),
                        /*'selected_goods_category': selectedGoodsCheckboxValue,
                        'excluded_goods_category': excludedGoodsCheckBoxValue,*/
                        'restriction_description': $('#description').val(),
                        'selected_goods_list': selectedIdList,
                        'excluded_goods_list': excludedIdList,
                        'selected_first_level_list': selectedFirstLevelList,
                        'selected_second_level_list': selectedSecondLevelList,
                        'selected_third_level_list': selectedThirdLevelList,
                        'excluded_first_level_list': excludedFirstLevelList,
                        'excluded_second_level_list': excludedSecondLevelList,
                        'excluded_third_level_list': excludedThirdLevelList
                    }
                };
                deleteEmptyProperty(jsonData);
                console.log(jsonData);
                $.ajax({
                    type: 'post',
                    url: url,
                    contentType: 'application/json',
                    dataType: "json",
                    //数据格式是json串，商品信息
                    data: JSON.stringify(jsonData),
                    success: function (data) {//返回json结果
                        if (data.return_code == 1) {
                            alert("修改成功!");
                        } else {
                            alert(data.return_value);
                        }

                    }
                });
            }
        }
    );
}

function dateTimeBox() {
    function ww4(date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        var d = date.getDate();
        var h = date.getHours();
        var mm = date.getMinutes();
        var s = date.getSeconds();
        return y + "-" + (m < 10 ? ('0' + m) : m) + "-" + (d < 10 ? ('0' + d) : d) + " " + (h < 10 ? ('0' + h) : h) + ":" + (mm < 10 ? ('0' + mm) : mm) + ":" + (s < 10 ? ('0' + s) : s);
    }

    $('#effective_time').datetimebox({
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

    $('#expired_time').datetimebox({
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

function changeBatchAddType() {
    $('input[name="batchAddType"]').click(function () {
        $('#goodsTypeTextArea').val('');
    });
}

// 展示红包信息
function showCouponInfo() {
    var coupon_stock_id = $('#coupon_stock_id').val();
    $.get(serverDomain + '/coupon_stock/checkReleaseStatus?coupon_stock_id=' + coupon_stock_id,
        function (data) {
            if (data.return_code == 0) {
                // 说明已加入投放策略
                var modal = $('#errorMessageDiv').modal({
                    backdrop: 'static',
                    keyboard: false
                });
                $('#errorMessageP').html(data.return_value);
                modal.modal('show');
                $('#new-coupon-submit').hide();
            }
        }, 'json');
    $.get(serverDomain + '/coupon_stock/get?coupon_stock_id=' + coupon_stock_id,
        function (data) {
            // 赋值
            console.log(data);
            $('input[name="coupon_stock_name"]').val(data.coupon_stock.coupon_stock_name);
            $('input[name="reach_amount"]').val(data.yougou_restriction.reach_amount);
            $('input[name="preferential_amount"]').val(data.coupon_stock.preferential_amount);
            if (data.yougou_restriction.hasOwnProperty('effective_duration')) {
                $('input[name="effective_time_type"][value="0"]').attr('checked', true);
                $('input[name="effective_duration"]').val(data.yougou_restriction.effective_duration);
            }
            if (data.yougou_restriction.hasOwnProperty('effective_time')) {
                $('input[name="effective_time_type"][value="1"]').attr('checked', true);
                $('#effective_time').datetimebox('setValue', data.yougou_restriction.effective_time);
                $('#expired_time').datetimebox('setValue', data.yougou_restriction.expired_time);
            }
            if (data.yougou_restriction.goods_range == '1') {
                // 选择部分商品
                $('input[name="goods_range"][value="1"]').attr('checked', true);
                if (data.yougou_restriction.selected_goods_category == '1') {
                    $('input[name="selected_goods_category"]').attr('checked', true);
                }
                if (data.yougou_restriction.hasOwnProperty('selected_first_level_list')) {
                    initialSelectedTypeInfo(data.yougou_restriction.selected_first_level_list, 1);
                    // 作为后面修改时添加的初始值
                    selectedTableInfoList = data.yougou_restriction.selected_first_level_list + ',';
                }
                if (data.yougou_restriction.hasOwnProperty('selected_second_level_list')) {
                    initialSelectedTypeInfo(data.yougou_restriction.selected_second_level_list, 2);
                    // 作为后面修改时添加的初始值
                    selectedTableInfoList = data.yougou_restriction.selected_second_level_list + ',';
                }
                if (data.yougou_restriction.hasOwnProperty('selected_third_level_list')) {
                    initialSelectedTypeInfo(data.yougou_restriction.selected_third_level_list, 3);
                    // 作为后面修改时添加的初始值
                    selectedTableInfoList = data.yougou_restriction.selected_third_level_list + ',';
                }
                if (data.yougou_restriction.hasOwnProperty('selected_goods_list')) {
                    initialSelectedTypeInfo(data.yougou_restriction.selected_goods_list, null);
                    // 作为后面修改时添加的初始值
                    selectedTableInfoList = data.yougou_restriction.selected_goods_list + ',';
                }

            } else if (data.yougou_restriction.goods_range == '0') {
                $('#goods-div').hide();
            }
            if (data.yougou_restriction.hasOwnProperty('excluded_goods_category') && data.yougou_restriction.excluded_goods_category != 0) {
                $('input[name="coupon_unuse_checkbox"]').attr('checked', true);
                if (data.yougou_restriction.excluded_goods_category == '1') {
                    $('input[name="excluded_goods_category"]').attr('checked', true);
                }
                if (data.yougou_restriction.hasOwnProperty('excluded_first_level_list')) {
                    initialExcludeTypeInfo(data.yougou_restriction.excluded_first_level_list, 1);
                    excludedTableInfoList = data.yougou_restriction.excluded_first_level_list;
                }
                if (data.yougou_restriction.hasOwnProperty('excluded_second_level_list')) {
                    initialExcludeTypeInfo(data.yougou_restriction.excluded_second_level_list, 2);
                    excludedTableInfoList = data.yougou_restriction.excluded_second_level_list;
                }
                if (data.yougou_restriction.hasOwnProperty('excluded_third_level_list')) {
                    initialExcludeTypeInfo(data.yougou_restriction.excluded_third_level_list, 3);
                    excludedTableInfoList = data.yougou_restriction.excluded_third_level_list;
                }
                if (data.yougou_restriction.hasOwnProperty('excluded_goods_list')) {
                    initialExcludeTypeInfo(data.yougou_restriction.excluded_goods_list, null);
                    excludedTableInfoList = data.yougou_restriction.excluded_goods_list;
                }
            } else {
                $('#unusadle-div').hide();
            }

            $('input[name="restriction_description"]').val(data.yougou_restriction.restriction_description);
        }, 'json'
    );
}


// 添加可用商品分类信息
function selectedAddTypeInfo() {
    $('#selectedAddTypeInfo').click(function () {
        selectedScreenAddSubmit = 'true';
        var firstLevelInfo = $('#selected-first-level').combobox('getValue');
        var secondLevelInfo = $('#selected-second-level').combobox('getValue');
        var thirdLevelInfo = $('#selected-third-level').combobox('getValue');
        if (firstLevelInfo != '' && secondLevelInfo == '') {
            // 只选择一级分类
            $('#selectedGoodsTable').bootstrapTable('insertRow', {
                index: 1,
                row: {
                    goodsId: $('#selected-first-level').combobox('getValue'),
                    goodsType: '一级分类',
                    goodsName: $('#selected-first-level').combobox('getText'),
                    specifications: ''
                }
            });
            alert('添加成功');
        }
        if (secondLevelInfo != '' && thirdLevelInfo == '') {
            // 只选择二级分类
            $('#selectedGoodsTable').bootstrapTable('insertRow', {
                index: 1,
                row: {
                    goodsId: $('#selected-second-level').combobox('getValue'),
                    goodsType: '二级分类',
                    goodsName: $('#selected-second-level').combobox('getText'),
                    specifications: ''
                }
            });
            alert('添加成功');
        }
        if (thirdLevelInfo != '') {
            var selectionsGoodsInfo = $('#selectedGoodsInfoTable').bootstrapTable('getAllSelections');
            console.log(selectionsGoodsInfo.length);
            if (selectionsGoodsInfo.length <= 0) {
                $('#selectedGoodsTable').bootstrapTable('insertRow', {
                    index: 1,
                    row: {
                        goodsId: $('#selected-third-level').combobox('getValue'),
                        goodsType: '三级分类',
                        goodsName: $('#selected-third-level').combobox('getText'),
                        specifications: ''
                    }
                });
                alert('添加成功');
            } else {
                for (var i = 0; i < selectionsGoodsInfo.length; i++) {
                    $('#selectedGoodsTable').bootstrapTable('insertRow', {
                        index: i + 1,
                        row: {
                            goodsId: selectionsGoodsInfo[i].goodskuSn,
                            goodsType: '单品',
                            goodsName: selectionsGoodsInfo[i].goodsName,
                            specifications: selectionsGoodsInfo[i].specItemValues
                        }
                    });
                }
                alert('添加成功');
            }
        }
    });
}

// 添加不可用商品分类信息
function excludedAddTypeInfo() {
    $('#excludedAddTypeInfo').click(function () {
        excludedScreenAddSubmit = 'true';
        var firstLevelInfo = $('#excluded-first-level').combobox('getValue');
        var secondLevelInfo = $('#excluded-second-level').combobox('getValue');
        var thirdLevelInfo = $('#excluded-third-level').combobox('getValue');
        if (firstLevelInfo != '' && secondLevelInfo == '') {
            // 只选择一级分类
            $('#excludedGoodsTable').bootstrapTable('insertRow', {
                index: 1,
                row: {
                    goodsId: $('#excluded-first-level').combobox('getValue'),
                    goodsType: '一级分类',
                    goodsName: $('#excluded-first-level').combobox('getText'),
                    specifications: ''
                }
            });
            alert('添加成功');
        } else if (secondLevelInfo != '' && thirdLevelInfo == '') {
            // 只选择二级分类
            $('#excludedGoodsTable').bootstrapTable('insertRow', {
                index: 1,
                row: {
                    goodsId: $('#excluded-second-level').combobox('getValue'),
                    goodsType: '二级分类',
                    goodsName: $('#excluded-second-level').combobox('getText'),
                    specifications: ''
                }
            });
            alert('添加成功');
        } else if (thirdLevelInfo != '') {
            var selectionsGoodsInfo = $('#excludedGoodsInfoTable').bootstrapTable('getAllSelections');
            console.log(selectionsGoodsInfo.length);
            if (selectionsGoodsInfo.length <= 0) {
                $('#excludedGoodsTable').bootstrapTable('insertRow', {
                    index: 1,
                    row: {
                        goodsId: $('#excluded-third-level').combobox('getValue'),
                        goodsType: '三级分类',
                        goodsName: $('#excluded-third-level').combobox('getText'),
                        specifications: ''
                    }
                });
                alert('添加成功');
            } else {
                for (var i = 0; i < selectionsGoodsInfo.length; i++) {
                    $('#excludedGoodsTable').bootstrapTable('insertRow', {
                        index: i + 1,
                        row: {
                            goodsId: selectionsGoodsInfo[i].goodskuSn,
                            goodsType: '单品',
                            goodsName: selectionsGoodsInfo[i].goodsName,
                            specifications: selectionsGoodsInfo[i].specItemValues
                        }
                    });
                }
                alert('添加成功');
            }
        }
    });
}


// 初始化可用商品分类信息

function initialSelectedTypeInfo(list, type) {
    selectedScreenAddSubmit = 'true';
    var url = serverDomain + "/yougou/check.json";
    console.log("selected:" + list + '|' + type);
    $.post(url, {
        snList: list,
        type: type
    }, function (data) {
        if (data.return_code == 1) {
            if (type == 1) {
                // 只选择一级分类
                for (var i = 0; i < data.rows.length; i++) {
                    $('#selectedGoodsTable').bootstrapTable('insertRow', {
                        index: 1,
                        row: {
                            goodsId: data.rows[i].categorySn,
                            goodsType: '一级分类',
                            goodsName: data.rows[i].categoryName,
                            specifications: ''
                        }
                    });
                }
            }
            if (type == 2) {
                // 只选择二级分类
                for (var i = 0; i < data.rows.length; i++) {
                    $('#selectedGoodsTable').bootstrapTable('insertRow', {
                        index: 1,
                        row: {
                            goodsId: data.rows[i].categorySn,
                            goodsType: '二级分类',
                            goodsName: data.rows[i].categoryName,
                            specifications: ''
                        }
                    });
                }
            }
            if (type == 3) {
                for (var i = 0; i < data.rows.length; i++) {
                    $('#selectedGoodsTable').bootstrapTable('insertRow', {
                        index: 1,
                        row: {
                            goodsId: data.rows[i].categorySn,
                            goodsType: '三级分类',
                            goodsName: data.rows[i].categoryName,
                            specifications: ''
                        }
                    });
                }
            }
            if (type == null || type == '') {
                for (var i = 0; i < data.rows.length; i++) {
                    $('#selectedGoodsTable').bootstrapTable('insertRow', {
                        index: i + 1,
                        row: {
                            goodsId: data.rows[i].goodskuSn,
                            goodsType: '单品',
                            goodsName: data.rows[i].goodsName,
                            specifications: data.rows[i].specItemValues
                        }
                    });
                }
            }
        } else {
            alert(data.return_value + ' : ' + data.rows);
        }
    });

}

// 初始化不可用商品分类信息

function initialExcludeTypeInfo(list, type) {
    excludedScreenAddSubmit = 'true';
    var url = serverDomain + "/yougou/check.json";
    console.log("excluded:" + list + '|' + type);
    $.post(url, {
        snList: list,
        type: type
    }, function (data) {
        console.log(data.rows)
        if (data.return_code == 1) {
            if (type == 1) {
                // 只选择一级分类
                for (var i = 0; i < data.rows.length; i++) {
                    $('#excludedGoodsTable').bootstrapTable('insertRow', {
                        index: 1,
                        row: {
                            goodsId: data.rows[i].categorySn,
                            goodsType: '一级分类',
                            goodsName: data.rows[i].categoryName,
                            specifications: ''
                        }
                    });
                }
            }
            if (type == 2) {
                // 只选择二级分类
                for (var i = 0; i < data.rows.length; i++) {
                    $('#excludedGoodsTable').bootstrapTable('insertRow', {
                        index: 1,
                        row: {
                            goodsId: data.rows[i].categorySn,
                            goodsType: '二级分类',
                            goodsName: data.rows[i].categoryName,
                            specifications: ''
                        }
                    });
                }
            }
            if (type == 3) {
                for (var i = 0; i < data.rows.length; i++) {
                    $('#excludedGoodsTable').bootstrapTable('insertRow', {
                        index: 1,
                        row: {
                            goodsId: data.rows[i].categorySn,
                            goodsType: '三级分类',
                            goodsName: data.rows[i].categoryName,
                            specifications: ''
                        }
                    });
                }
            }
            if (type == null || type == '') {
                for (var i = 0; i < data.rows.length; i++) {
                    $('#excludedGoodsTable').bootstrapTable('insertRow', {
                        index: i + 1,
                        row: {
                            goodsId: data.rows[i].goodskuSn,
                            goodsType: '单品',
                            goodsName: data.rows[i].goodsName,
                            specifications: data.rows[i].specItemValues
                        }
                    });
                }
            }

        } else {
            alert(data.return_value + ' : ' + data.rows);
        }
    });
}

<!-- 获取可用商品类型信息-->
function getSelectedGoodsInfo() {
    var url = serverDomain + '/yougou/getCategory.json';
    $('#selected-first-level').combobox({
        url: url,
        method: 'get',
        textField: 'name',
        valueField: 'categorySn',
        width: 175,
        onSelect: function () {
            $('#selected-second-level').combobox('clear');
            $('#selected-third-level').combobox('clear');
            $('#selected-second-level').combobox({
                url: serverDomain + '/yougou/getCategory.json?parentSn=' + $('#selected-first-level').combobox('getValue'),
                method: 'get',
                valueField: 'categorySn',
                textField: 'name',
                width: 175,
                onSelect: function () {
                    $.ajaxSettings.async = false;
                    $('#selected-third-level').combobox({
                        url: serverDomain + '/yougou/getCategory.json?parentSn=' + $('#selected-second-level').combobox('getValue'),
                        method: 'get',
                        valueField: 'categorySn',
                        textField: 'name',
                        width: 175,
                        onSelect: function () {
                            $('#selectedAddModalFooter').show();
                            $('#selectedGoodsInfoTable').bootstrapTable('destroy');
                            $.get(serverDomain + '/yougou/getGoodsSku.json?categorySn=' + $('#selected-third-level').combobox('getValue'),
                                function (data) {
                                    if (data.return_code == 1) {
                                        $.ajaxSettings.async = false;
                                        $('#selectedGoodsInfoTable').bootstrapTable({
                                            toolbar: '#toolbar',                //工具按钮用哪个容器
                                            striped: true,                      //是否显示行间隔色
                                            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                                            pagination: true,                   //是否显示分页（*）
                                            sortable: true,                     //是否启用排序
                                            sortOrder: "asc",                   //排序方式
                                            sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
                                            pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
                                            pageSize: 10,                     //每页的记录行数（*）
                                            pageList: 'All',        //可供选择的每页的行数（*）
                                            width: 600,
                                            minimumCountColumns: 2,             //最少允许的列数
                                            clickToSelect: true,                //是否启用点击选中行
                                            uniqueId: "goodskuSn",                     //每一行的唯一标识，一般为主键列
                                            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                                            data: data.rows,
                                            search: true,
                                            columns: [{
                                                field: 'checked',
                                                title: '全选',
                                                checkbox: true
                                            }, {
                                                field: 'goodskuSn',
                                                title: 'SKU'
                                            }, {
                                                field: 'goodsName',
                                                title: '商品名称'
                                            }, {
                                                field: 'specItemValues',
                                                title: '规格参数'
                                            }, {
                                                field: 'price',
                                                title: '现价'
                                            }, {
                                                field: 'marketPrice',
                                                title: '市场价'
                                            }]
                                        });
                                        fixStyle();
                                    } else {
                                        alert(data.return_value);
                                    }
                                });
                        }
                    });
                }
            });
        }
    });
}

// 获取不可用商品分类数据
function getExcludedGoodsInfo() {
    var url = serverDomain + '/yougou/getCategory.json';
    $('#excluded-first-level').combobox({
        url: url,
        method: 'get',
        valueField: 'categorySn',
        textField: 'name',
        panelWidth: 175,
        matchFieldWidth: true,
        onSelect: function () {
            $('#excluded-second-level').combobox('clear');
            $('#excluded-third-level').combobox('clear');
            $('#excluded-second-level').combobox({
                url: serverDomain + '/yougou/getCategory.json?parentSn=' + $('#excluded-first-level').combobox('getValue'),
                method: 'get',
                valueField: 'categorySn',
                textField: 'name',
                panelWidth: 175,
                matchFieldWidth: true,
                onSelect: function () {
                    $.ajaxSettings.async = false;
                    $('#excluded-third-level').combobox({
                        url: serverDomain + '/yougou/getCategory.json?parentSn=' + $('#excluded-second-level').combobox('getValue'),
                        method: 'get',
                        valueField: 'categorySn',
                        textField: 'name',
                        panelWidth: 175,
                        matchFieldWidth: true,
                        onSelect: function () {
                            $('#excludedAddModalFooter').show();
                            $('#excludedGoodsInfoTable').bootstrapTable('destroy');
                            $.get(serverDomain + '/yougou/getGoodsSku.json?categorySn=' + $('#excluded-third-level').combobox('getValue'), function (data) {
                                if (data.return_code == 1) {
                                    $('#excludedGoodsInfoTable').bootstrapTable({
                                        toolbar: '#toolbar',                //工具按钮用哪个容器
                                        striped: true,                      //是否显示行间隔色
                                        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                                        pagination: true,                   //是否显示分页（*）
                                        sortable: true,                     //是否启用排序
                                        sortOrder: "asc",                   //排序方式
                                        sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
                                        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
                                        pageSize: 10,                     //每页的记录行数（*）
                                        pageList: 'All',        //可供选择的每页的行数（*）
                                        width: 600,
                                        minimumCountColumns: 2,             //最少允许的列数
                                        clickToSelect: true,                //是否启用点击选中行
                                        uniqueId: "goodskuSn",                     //每一行的唯一标识，一般为主键列
                                        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                                        data: data.rows,
                                        search: true,
                                        columns: [{
                                            field: 'checked',
                                            title: '全选',
                                            checkbox: true
                                        }, {
                                            field: 'goodskuSn',
                                            title: 'SKU'
                                        }, {
                                            field: 'goodsName',
                                            title: '商品名称'
                                        }, {
                                            field: 'specItemValues',
                                            title: '规格参数'
                                        }, {
                                            field: 'price',
                                            title: '现价'
                                        }, {
                                            field: 'marketPrice',
                                            title: '市场价'
                                        }]
                                    });
                                    fixStyle();
                                } else {
                                    alert(data.return_value);
                                }
                            });
                        }
                    });
                }
            });
        }
    });
}

function deleteEmptyProperty(object) {
    for (var i in object) {
        var value = object[i];
        // sodino.com
        // console.log('typeof object[' + i + ']', (typeof value));
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

function fixStyle() {

    $('.search>input.form-control').before("<span style='position: relative;top: 35px;'>当前结果下搜索:</span>").css({
        position: 'relative',
        left: '117px',
        width: '185px'
    });
    $('.search>input.form-control').parent().removeClass("pull-right").addClass("pull-left");
}