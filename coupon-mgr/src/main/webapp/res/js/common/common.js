var moreparam = {};
var RecomSweepstakesTypeMap = {};
var vipCombobox = [{
    'id': '0',
    'text': '全部',
    'selected': true
}, {
    'id': '1',
    'text': '大麦VIP'
}, {
    'id': '2',
    'text': '爱奇艺VIP'
}, {
    'id': '3',
    'text': '非VIP'
}];
var payCombobox = [{
    'id': '0',
    'text': '全部',
    'selected': true
}, {
    'id': '1',
    'text': '大麦付费'
}, {
    'id': '2',
    'text': '爱奇艺付费'
}, {
    'id': '3',
    'text': '免费'
}];
var picCombobox = [{
    'id': '0',
    'text': '图片'
}, {
    'id': '1',
    'text': '视频'
}];

//初始化活动类型
function initActivitiesType() {
    $.post(ctx + "/activityFreeVip/getComboboxList.json", function (data) {
        if (data.length > 0)
            $.each(data, function (idx, item) {
                RecomSweepstakesTypeMap[item.typeCode] = item.typeName;
            });
    }, "json");
}

//初始化app类型
function initAppType() {
    $.post(ctx + "/appCategory/getList.json", {}, function (data) {
        if (data.length > 0)
            $.each(data, function (idx, item) {
                var option = $("<option value='" + item.categoryId + "'>" + item.categoryName + "</option>");
                var option1 = $("<option value='" + item.categoryId + "'>" + item.categoryName + "</option>");
                $("#app_type").append(option);
                $("#appsubject_type").append(option1);
            });
    }, "json");
}

function initTempletBindEvent() {
    var templetId = $("#templet").find("option:selected").val();
    $.ajax({
        async: true,
        type: "post",
        url: ctx + "/portal/templetApk/getList",
        dataType: "json",
        data: {"templetId": templetId},
        success: function (jsonData) {
            $('#templetApk').empty();
            var select = jsonData;
            if (select != null && select != "" && select.length > 0) {
                var tr = ""
                for (var i = 0; i < select.length; i++) {
                    tr = tr + "<option value='" + select[i].apkBagName + "'>" + select[i].apkName + "</option>";
                }
                $('#templetApk').append(tr);
            }
        }
    });
}

function initTempletApkBindEvent() {
    var templetId = $("#templet").find("option:selected").val();
    var templetApk = $("#templetApk").find("option:selected").val();
    if ("com.hiveview.premiere" == templetApk) {
        $('#jq').css('display', 'inline');
        $('#dm').css('display', 'none');
        $.ajax({
            async: false,
            type: "post",
            url: ctx + "/portal/templetHotWord/getList",
            dataType: "json",
            data: {"apkBagName": templetApk, "templetId": templetId},
            success: function (jsonData) {
                $('#templetApkHot').empty();
                var select = jsonData;
                if (select != null && select != "" && select.length > 0) {
                    var tr = ""
                    for (var i = 0; i < select.length; i++) {
                        tr = tr + "<option value='" + select[i].id + "'>" + select[i].name + "</option>";
                    }
                    $('#templetApkHot').append(tr);
                }
            }
        });
    } else {
        $('#jq').css('display', 'none');
        $('#dm').css('display', 'inline');
        $.ajax({
            async: true,
            type: "post",
            url: ctx + "/portal/templetApkChannel/getChannelTypeList",
            dataType: "json",
            data: {"apkBagName": templetApk},
            success: function (jsonData) {
                $('#templetApkChannelType').empty();
                var select = jsonData;
                if (select != null && select != "" && select.length > 0) {
                    var tr = ""
                    for (var i = 0; i < select.length; i++) {
                        tr = tr + "<option value='" + select[i].cType + "'>" + select[i].cTypeName + "</option>";
                    }
                    $('#templetApkChannelType').append(tr);
                }
                var templetApkChannelType = $("#templetApkChannelType").find("option:selected").val();
                $.ajax({
                    async: false,
                    type: "post",
                    url: ctx + "/portal/templetApkChannel/getChannelList",
                    dataType: "json",
                    data: {"apkBagName": templetApk, "cType": templetApkChannelType},
                    success: function (jsonData) {
                        $('#templetApkChannel').empty();
                        var select = jsonData;
                        if (select != null && select != "" && select.length > 0) {
                            var tr = ""
                            for (var i = 0; i < select.length; i++) {
                                tr = tr + "<option value='" + select[i].id + "'>" + select[i].cName + "</option>";
                            }
                            $('#templetApkChannel').append(tr);
                        }
                    }
                });
            }
        });
    }
}

function initTempletApkChannelTypeBindEvent() {
    var templetApk = $("#templetApk").find("option:selected").val();
    var templetApkChannelType = $("#templetApkChannelType").find("option:selected").val();
    $.ajax({
        async: true,
        type: "post",
        url: ctx + "/portal/templetApkChannel/getChannelList",
        dataType: "json",
        data: {"apkBagName": templetApk, "cType": templetApkChannelType},
        success: function (jsonData) {
            $('#templetApkChannel').empty();
            var select = jsonData;
            if (select != null && select != "" && select.length > 0) {
                var tr = ""
                for (var i = 0; i < select.length; i++) {
                    tr = tr + "<option value='" + select[i].id + "'>" + select[i].cName + "</option>";
                }
                $('#templetApkChannel').append(tr);
            }
        }
    });
}

function initSubCondition() {
    $.ajax({
        async: true,
        type: "post",
        url: ctx + "/portal/templet/getList",
        dataType: "json",
        data: {},
        success: function (jsonData) {
            var select = jsonData;
            if (select != null && select != "" && select.length > 0) {//如果子列表有值则把值赋上去
                var tr = ""
                for (var i = 0; i < select.length; i++) {
                    tr = tr + "<option value='" + select[i].id + "'>" + select[i].name + "</option>";
                }
                $('#templet_subject').append(tr);
            }
            var tId = $("#templet_subject").find("option:selected").val();
            $.ajax({
                async: true,
                type: "post",
                url: ctx + "/portal/templetApk/getList",
                dataType: "json",
                data: {"templetId": tId},
                success: function (jsonData) {
                    $('#templetApk_subject').empty();
                    var select = jsonData;
                    if (select != null && select != "" && select.length > 0) {
                        var tr = ""
                        for (var i = 0; i < select.length; i++) {
                            tr = tr + "<option value='" + select[i].apkBagName + "'>" + select[i].apkName + "</option>";
                        }
                        $('#templetApk_subject').append(tr);
                    }
                }
            });
        }
    });
}

function initCondition() {
    var vipSelects = vipCombobox;
    if (vipSelects != null && vipSelects != "" && vipSelects.length > 0) {
        var tr = ""
        for (var i = 0; i < vipSelects.length; i++) {
            tr = tr + "<option value='" + vipSelects[i].id + "'>" + vipSelects[i].text + "</option>";
        }
        $('#vip').append(tr);
    }
    var paySelects = payCombobox;
    if (paySelects != null && paySelects != "" && paySelects.length > 0) {
        var tr = ""
        for (var i = 0; i < paySelects.length; i++) {
            tr = tr + "<option value='" + paySelects[i].id + "'>" + paySelects[i].text + "</option>";
        }
        $('#pay').append(tr);
    }
    $.ajax({
        async: false,
        type: "post",
        url: ctx + "/portal/templet/getList",
        dataType: "json",
        data: {},
        success: function (jsonData) {
            var select = jsonData;
            if (select != null && select != "" && select.length > 0) {//如果子列表有值则把值赋上去
                var tr = ""
                for (var i = 0; i < select.length; i++) {
                    tr = tr + "<option value='" + select[i].id + "'>" + select[i].name + "</option>";
                }
                $('#templet').append(tr);
            }
            var templetId = $("#templet").find("option:selected").val();
            $.ajax({
                async: false,
                type: "post",
                url: ctx + "/portal/templetApk/getList",
                dataType: "json",
                data: {"templetId": templetId},
                success: function (jsonData) {
                    $('#templetApk').empty();
                    var select = jsonData;
                    if (select != null && select != "" && select.length > 0) {//如果子列表有值则把值赋上去
                        var tr = ""
                        for (var i = 0; i < select.length; i++) {
                            tr = tr + "<option value='" + select[i].apkBagName + "'>" + select[i].apkName + "</option>";
                        }
                        $('#templetApk').append(tr);
                    }
                    var templetApk = $("#templetApk").find("option:selected").val();
                    if ("com.hiveview.premiere" != templetApk) {
                        $.ajax({
                            async: true,
                            type: "post",
                            url: ctx + "/portal/templetApkChannel/getChannelTypeList",
                            dataType: "json",
                            data: {"apkBagName": templetApk},
                            success: function (jsonData) {
                                $('#templetApkChannelType').empty();
                                var select = jsonData;
                                if (select != null && select != "" && select.length > 0) {
                                    var tr = ""
                                    for (var i = 0; i < select.length; i++) {
                                        tr = tr + "<option value='" + select[i].cType + "'>" + select[i].cTypeName + "</option>";
                                    }
                                    $('#templetApkChannelType').append(tr);
                                }
                                var templetApkChannelType = $("#templetApkChannelType").find("option:selected").val();
                                $.ajax({
                                    async: true,
                                    type: "post",
                                    url: ctx + "/portal/templetApkChannel/getChannelList",
                                    dataType: "json",
                                    data: {"apkBagName": templetApk, "cType": templetApkChannelType},
                                    success: function (jsonData) {
                                        $('#templetApkChannel').empty();
                                        var select = jsonData;
                                        if (select != null && select != "" && select.length > 0) {
                                            var tr = ""
                                            for (var i = 0; i < select.length; i++) {
                                                tr = tr + "<option value='" + select[i].id + "'>" + select[i].cName + "</option>";
                                            }
                                            $('#templetApkChannel').append(tr);
                                        }
                                        // var templetApkChannel = $("#templetApkChannel").find("option:selected").val();
                                    }
                                });
                            }
                        });
                    }
                }
            });
        }
    });
}

function initCombobox() {
    initActivitiesType();
    initAppType();
    initCondition();
    initSubCondition();
}

//专题
var col0 = [{
    field: 'subjectId',
    title: '内容ID',
    width: 100,
    align: 'center',
    formatter: function (value) {
        return value;
    }
}, {
    field: 'subjectName',
    title: '专题名称',
    width: 100,
    align: 'center',
    formatter: function (value) {
        return value;
    }
}, {
    field: 'subjectPic',
    title: '图片',
    width: 120,
    align: 'center',
    formatter: function (value) {
        return "<img style='width:30px;height:30px;' src='"
            + value + "' />";
    }
}];
//专辑详情
var col1 = [{
    field: 'programsetId',
    title: '内容ID',
    width: '8%',
    align: 'center',
    formatter: function (value, row, index) {
        return value;
    }
}, {
    field: 'albumName',
    title: '专辑名称',
    width: '10%',
    align: 'center',
    formatter: function (value, row, index) {
        return value;
    }
}, {
    field: 'albumHbPic',
    title: '图片',
    width: '10%',
    align: 'center',
    formatter: function (value, row, index) {
        return "<img style='height:30px;width:30px;' src='"
            + value + "' />";
    }
}];
//应用详情
var col2 = [{
    field: 'appId',
    title: '编号',
    width: 90,
    align: 'center',
    formatter: function (value) {
        return value;
    }
}, {
    field: 'appName',
    title: '名称',
    width: 100,
    align: 'center',
    formatter: function (value) {
        return value;
    }
}, {
    field: 'appIcon',
    title: '图片',
    width: 90,
    align: 'center',
    formatter: function (value, row, index) {
        return "<img style='height:30px;width:30px;' src='"
            + value + "' />";
    }
}, {
    field: 'categoryId',
    title: '应用分类',
    width: 100,
    align: 'center',
    formatter: function (value, row, index) {
        if (value == 1) {
            return "游戏";
        }
        if (value == 2) {
            return "应用";
        }
        if (value == 3) {
            return "教育";
        }
    }
}];
//应用专题
var col3 = [{
    field: 'pubId',
    title: '编号',
    width: 90,
    align: 'center',
    formatter: function (value) {
        return value;
    }
}, {
    field: 'pubName',
    title: '名称',
    width: 100,
    align: 'center',
    formatter: function (value) {
        return value;
    }
}, {
    field: 'pubImg',
    title: '图片',
    width: 90,
    align: 'center',
    formatter: function (value, row, index) {
        return "<img style='height:30px;width:30px;' src='"
            + value + "' />";
    }
}, {
    field: 'categoryId',
    title: '应用分类',
    width: 100,
    align: 'center',
    formatter: function (value, row, index) {
        if (value == 1) {
            return "游戏";
        }
        if (value == 2) {
            return "应用";
        }
        if (value == 3) {
            return "教育";
        }
    }
}];
var col4 = [{
    field: 'activityId',
    title: '内容ID',
    width: 100,
    align: 'center'
}, {
    field: 'title',
    title: '名称',
    width: 100,
    align: 'center'
}, {
    field: 'type',
    title: '活动类型',
    width: 100,
    align: 'center',
    formatter: function (value) {
        return RecomSweepstakesTypeMap[value];
    }
}];

//全屏大图
var col6 = [{
    field: 'imgId',
    title: '内容ID',
    width: '8%',
    align: 'center',
    formatter: function (value, row, index) {
        return value;
    }
}, {
    field: 'imgName',
    title: '大图名称',
    width: '10%',
    align: 'center',
    formatter: function (value, row, index) {
        return value;
    }
}, {
    field: 'imgAddr',
    title: '图片',
    width: '10%',
    align: 'center',
    formatter: function (value, row, index) {
        return "<img style='height:30px;width:30px;' src='"
            + value + "' />";
    }
}];
//商品包
var col7 = [{
    field: 'id',
    title: '内容ID',
    width: '8%',
    align: 'center',
    formatter: function (value, row, index) {
        return value;
    }
}, {
    field: 'name',
    title: '商品包名称',
    width: '10%',
    align: 'center',
    formatter: function (value, row, index) {
        return value;
    }
}, {
    field: 'firstPosterPic',
    title: '图片',
    width: '10%',
    align: 'center',
    formatter: function (value, row, index) {
        return "<img style='height:30px;width:30px;' src='"
            + value + "' />";
    }
}];

function getMoreList(contentType, url) {
    initMoreTable(contentType, url);
}

//所有数据来源
function initMoreTable(contentType, url) {
    $('#content' + contentType).bootstrapTable('destroy');
    $('#content' + contentType).bootstrapTable({
        method: "get",  //使用get请求到服务器获取数据
        url: ctx + url, //获取数据的Servlet地址
        striped: false,  //表格显示条纹
        pagination: true, //启动分页
        pageSize: 3,  //每页显示的记录数
        pageNumber: 1, //当前第几页
        pageList: [2, 3],  //记录数可选列表
        sidePagination: "server", //表示服务端请求
        queryParamsType: "undefined",
        queryParams: function queryParams(params) {   //设置查询参数
            moreparam['page'] = params.pageNumber;
            moreparam['rows'] = params.pageSize;
            return moreparam;
        },
        columns: [eval("col" + contentType)],
        onClickRow: function (row) {
            $('#back_content_type').val(contentType);
            //全屏大图
            if (contentType == 6) {
                $('#editcontent_id').val(row.imgId);
                $('#txt_contentname').val(row.imgName);
                $('#txt_title').val(row.imgName);
                $('#recommendContentImg').attr('src', row.imgAddr);
                $('#back_templet_id').val('');
                $('#back_templet_apk').val('');
                $('#back_templet_channel_type').val('');
                $('#back_templet_channel').val('');
                $('#back_is_vip').val('');
                $('#back_is_pay').val('');
                $('#back_aqy_is_vip').val('');
                $('#back_aqy_is_pay').val('');
                $('#back_hot_id').val('');
                $('#back_app_style').val('');
            }
            //专辑详情
            if (contentType == 1) {
                $('#editcontent_id').val(row.programsetId);
                $('#txt_contentname').val(row.albumName);
                $('#txt_title').val(row.albumName);
                var isPlay = $("#txt_isPlay").find("option:selected").val();
                if (isPlay == 0) {
                    $('#recommendContentImg').attr('src', row.albumHbPic);
                } else {
                    $('#m3u8').val(row.preM3u8);
                }
                if ("com.hiveview.premiere" == row.apkBagName) {
                    var templetApkHotId = $("#templetApkHot").find("option:selected").val();
                    $('#back_hot_id').val(templetApkHotId);
                }
                $('#back_templet_id').val(row.templeteId);
                $('#back_templet_apk').val(row.apkBagName);
                $('#back_templet_channel_type').val(row.chnType);
                $('#back_templet_channel').val(row.chnId);
                $('#back_is_vip').val(row.isVip);
                $('#back_is_pay').val(row.chargingType);
                $('#back_aqy_is_vip').val(row.aqyIsVip);
                $('#back_aqy_is_pay').val(row.isTvod);
                $('#back_app_style').val('');
            }
            //影视专题
            if (contentType == 0) {
                $('#editcontent_id').val(row.subjectId);
                $('#txt_contentname').val(row.subjectName);
                $('#txt_title').val(row.subjectName);
                $('#recommendContentImg').attr('src', row.subjectPic);
                $('#back_templet_id').val(row.templeteId);
                $('#back_templet_apk').val(row.apkBagName);
                $('#back_templet_channel_type').val('');
                $('#back_templet_channel').val('');
                $('#back_is_vip').val('');
                $('#back_is_pay').val('');
                $('#back_aqy_is_vip').val('');
                $('#back_aqy_is_pay').val('');
                $('#back_hot_id').val('');
                $('#back_app_style').val('');
            }
            //应用详情
            if (contentType == 2) {
                $('#editcontent_id').val(row.appId);
                $('#txt_contentname').val(row.appName);
                $('#txt_title').val(row.appName);
                $('#recommendContentImg').attr('src', row.appIcon);
                $('#back_app_style').val(row.categoryId);
                $('#back_templet_id').val('');
                $('#back_templet_apk').val('');
                $('#back_templet_channel_type').val('');
                $('#back_templet_channel').val('');
                $('#back_is_vip').val('');
                $('#back_is_pay').val('');
                $('#back_aqy_is_vip').val('');
                $('#back_aqy_is_pay').val('');
                $('#back_hot_id').val('');
            }
            //应用专题
            if (contentType == 3) {
                $('#editcontent_id').val(row.pubId);
                $('#txt_contentname').val(row.pubName);
                $('#txt_title').val(row.pubName);
                $('#recommendContentImg').attr('src', row.pubImg);
                $('#back_app_style').val(row.categoryId);
                $('#back_templet_id').val('');
                $('#back_templet_apk').val('');
                $('#back_templet_channel_type').val('');
                $('#back_templet_channel').val('');
                $('#back_is_vip').val('');
                $('#back_is_pay').val('');
                $('#back_aqy_is_vip').val('');
                $('#back_aqy_is_pay').val('');
                $('#back_hot_id').val('');
            }
            //活动详情
            if (contentType == 4) {
                $('#editcontent_id').val(row.activityId);
                $('#txt_contentname').val(row.title);
                $('#txt_title').val(row.title);
                $('#recommendContentImg').attr('src', row.bgUrl);
                $('#back_templet_id').val('');
                $('#back_templet_apk').val('');
                $('#back_templet_channel_type').val('');
                $('#back_templet_channel').val('');
                $('#back_is_vip').val('');
                $('#back_is_pay').val('');
                $('#back_aqy_is_vip').val('');
                $('#back_aqy_is_pay').val('');
                $('#back_hot_id').val('');
                $('#back_app_style').val('');
            }
            //商品包
            if (contentType == 7) {
                $('#editcontent_id').val(row.id);
                $('#txt_contentname').val(row.name);
                $('#txt_title').val(row.name);
                $('#recommendContentImg').attr('src', row.firstPosterPic);
                $('#back_templet_id').val('');
                $('#back_templet_apk').val('');
                $('#back_templet_channel_type').val('');
                $('#back_templet_channel').val('');
                $('#back_is_vip').val('');
                $('#back_is_pay').val('');
                $('#back_aqy_is_vip').val('');
                $('#back_aqy_is_pay').val('');
                $('#back_hot_id').val('');
                $('#back_app_style').val('');
            }
        }
    });
}

function beforeAdd() {
    $('#txt_title').val('');
    $('#txt_secondTitle').val('');
    $('#txt_isPlay').val('0');
    $('#edit_id').val('');
    $('#editcontent_id').val('');
    $('#apkName').val('');
    $('#txt_contentname').val('');
    $('#recommendContentImg').attr('src', '');
}

function content_submit(imageType) {
    var t = $('#recommendContentImg')[0].src;
    var s = $('#m3u8').val();
    var recommendId = $('#editcontent_recommendId').val();
    if ($.trim(recommendId) == "") {
        $.messager.show(0, "推荐位Id不可为空！", 2000);
        return;
    }
    //内容表里的自增id
    var id = $('#edit_id').val();
    var contentId = $('#editcontent_id').val();
    var contentType = $('#contentType').val();
    var contentName = $('#txt_contentname').val();
    var title = $('#txt_title').val();
    var secTitle = $('#txt_secondTitle').val();
    var isPlay = $('#txt_isPlay').val();
    var bigPic;
    if (isPlay == 0) {
        bigPic = t;
    } else {
        bigPic = s;
    }
    var content = {
        'title': title,
        'secondTitle': secTitle,
        'isPlay': isPlay,
        'bigPic': bigPic,
        'belongRecomendId': recommendId,
        'contentId': contentId,
        'contentType': contentType,
        'imageType': imageType,
        'templetId': 1,
        'contentName': contentName,
        'apkName': $('#back_templet_apk').val(),
        'cType': $('#back_templet_channel_type').val(),
        'cid': $('#back_templet_channel').val(),
        'isVip': $('#back_is_vip').val(),
        'isPay': $('#back_is_pay').val(),
        'qiyiVip': $('#back_aqy_is_vip').val(),
        'qiyiPay': $('#back_aqy_is_pay').val(),
        'hotWordId': $('#back_hot_id').val()
    };
    if (id != null && id != '') {
        content['id'] = id;
        $.post(ctx + "/content/update", content, function (data) {
            if (data.returnValue == 0) {
                $.messager.show(0, "修改成功！", 2000);
                $("#editcontent_myModal").modal('hide');
                if (imageType == 0) {
                    $("#content").bootstrapTable('refresh', contentparam);
                    $("#portal").bootstrapTable('refresh', param);
                } else if (imageType == 1) {
                    $("#sample_arcoss").bootstrapTable('refresh', contentparam);
                    $("#sample_1").bootstrapTable('refresh', param);
                } else if (imageType == 2) {
                    $("#sample_vertical").bootstrapTable('refresh', contentparam);
                    $("#sample_1").bootstrapTable('refresh', param);
                }
            } else {
                $.messager.show(0, "修改失败！", 2000);
            }
        }, "json");

    } else {
        $.post(ctx + "/content/save", content, function (data) {
            if (data.returnValue == 0) {
                $.messager.show(0, "新增成功！", 2000);
                $("#editcontent_myModal").modal('hide');
                if (imageType == 0) {
                    $("#content").bootstrapTable('refresh', contentparam);
                    $("#portal").bootstrapTable('refresh', param);
                } else if (imageType == 1) {
                    $("#sample_arcoss").bootstrapTable('refresh', contentparam);
                    $("#sample_1").bootstrapTable('refresh', param);
                } else if (imageType == 2) {
                    $("#sample_vertical").bootstrapTable('refresh', contentparam);
                    $("#sample_1").bootstrapTable('refresh', param);
                }
            } else if (data.returnValue == -2) {
                $.messager.show(0, "此数据已经存在，不可重复添加！", 2000);
            } else {
                $.messager.show(0, "新增失败！", 2000);
            }
        }, "json");
    }
}

// 上传图片
function initUploadJs(file_id) {
    var file_upload = '#' + file_id + 'Url';
    $(file_upload).uploadify({
        'swf': ctx + '/res/js/plugin/uploadify/uploadify.swf',
        'uploader': ctx + '/upload/init.json', // fileUpload/newIcpUpload.json
        'height': 25,
        'whith': 120,
        'auto': true,
        'fileSizeLimit': '5120KB',
        'fileDataName': 'file',
        'buttonText': '浏览...',
        'fileTypeExts': '*.gif; *.jpg; *.png',
        'multi': false,
        'method': 'post',
        'debug': false,
        'queueSizeLimit': 1,
        'removeTimeout': 1,
        'onSelect': function (file) {
            var myself = this;
            var size = file.size

            if (file_id == "recommendContentImg") {
                if (file.type != ".jpg" && file.type != ".png"
                    && file.type != ".gif") {
                    alert(titleInfo, '上传文件错误，请选择合法图片！');
                    $(file_upload).uploadify('cancel', '*');
                }
            }

            if (file.type == ".jpg" || file.type == ".png") {
                if (size > 1024 * 1024 * 1) {
                    alert(titleInfo, '图片大小超出1MB！');
                    myself.cancelUpload(file.id);
                    $('#' + file.id).remove();
                }
            } else {
                if (size > 1024 * 1024 * 200) {
                    alert(titleInfo, '视频大小超出200MB！');
                    myself.cancelUpload(file.id);
                    $('#' + file.id).remove();
                }
            }
        },
        'onUploadStart': function (file) {// 传参
            var param = {};
            param.picHref = $(file_upload).val();
            $(file_upload).uploadify("settings", "formData", param);
        },
        'onUploadSuccess': function (file, data, response) {
            var imgId = '#' + file_id;
            $(imgId).attr("src", data);// 预览
        },
        'onUploadError': function (file, errorCode, errorMsg, errorString) {
            var errMsg = 'The file ' + file.name + ' could not be uploaded: '
                + errorString
            alert(errMsg);
        }
    });
}

function dataSource() {
    var contentType = $("#contentType").find("option:selected").val();
    if (contentType == 6) {
        moreparam = {};
        moreparam['imgName'] = $("#name_6").val();
        $('#div_' + 0).css('display', 'none');
        $('#div_' + 1).css('display', 'none');
        $('#div_' + 2).css('display', 'none');
        $('#div_' + 3).css('display', 'none');
        $('#div_' + 4).css('display', 'none');
        $('#div_' + 6).css('display', 'block');
        $('#div_' + 7).css('display', 'none');
        getMoreList(6, "/bigPics/getList");
    }
    //专辑详情
    if (contentType == 1) {
        moreparam = {};
        //查询参数
        var templet = $("#templet").find("option:selected").val();
        var templetApk = $("#templetApk").find("option:selected").val();
        var templetApkChannelType = $("#templetApkChannelType").find("option:selected").val();
        var templetApkChannel = $("#templetApkChannel").find("option:selected").val();
        var templetApkHot = $("#templetApkHot").find("option:selected").val();
        var vip = $("#vip").find("option:selected").val();
        var charge = $("#pay").find("option:selected").val();
        if (vip == '1') {
            moreparam['isVip'] = 1;
        } else if (vip == '0') {
        } else if (vip == '3') {
            moreparam['isVip'] = 3;
        } else {
            moreparam['aqyIsVip'] = 1;
        }
        if (charge == '1') {
            moreparam['chargingType'] = 1;
        } else if (charge == '0') {
        } else if (charge == '3') {
            moreparam['chargingType'] = 3;
        } else {
            moreparam['isTvod'] = 1;
        }
        moreparam['albumName'] = $("#name_1").val();
        moreparam['apkBagName'] = templetApk;
        moreparam['templetId'] = templet;
        if ("com.hiveview.premiere" == templetApk) {
            moreparam['hotId'] = templetApkHot;
        } else {
            moreparam['chnType'] = templetApkChannelType;
            moreparam['chnId'] = templetApkChannel;
        }
        $('#div_' + 0).css('display', 'none');
        $('#div_' + 1).css('display', 'block');
        $('#div_' + 2).css('display', 'none');
        $('#div_' + 3).css('display', 'none');
        $('#div_' + 4).css('display', 'none');
        $('#div_' + 6).css('display', 'none');
        $('#div_' + 7).css('display', 'none');
        getMoreList(1, "/portal/newContentChan/getNewContentChanPageList");
    }
    //影视专题
    if (contentType == 0) {
        moreparam = {};
        moreparam['subjectName'] = $("#name_0").val();
        var templet = $("#templet_subject").find("option:selected").val();
        var templetApk = $("#templetApk_subject").find("option:selected").val();
        moreparam['apkBagName'] = templetApk;
        moreparam['templetId'] = templet;
        moreparam['isOnline'] = 1;
        $('#div_' + 0).css('display', 'block');
        $('#div_' + 1).css('display', 'none');
        $('#div_' + 2).css('display', 'none');
        $('#div_' + 3).css('display', 'none');
        $('#div_' + 4).css('display', 'none');
        $('#div_' + 6).css('display', 'none');
        $('#div_' + 7).css('display', 'none');
        getMoreList(0, "/compositeSubject/getPageList");
    }
    //应用详情
    if (contentType == 2) {
        moreparam = {};
        var appType = $("#app_type").find("option:selected").val();
        moreparam['appName'] = $("#name_2").val();
        moreparam['categoryId'] = appType;
        moreparam['state'] = 1;
        $('#div_' + 0).css('display', 'none');
        $('#div_' + 1).css('display', 'none');
        $('#div_' + 2).css('display', 'block');
        $('#div_' + 3).css('display', 'none');
        $('#div_' + 4).css('display', 'none');
        $('#div_' + 6).css('display', 'none');
        $('#div_' + 7).css('display', 'none');
        getMoreList(2, "/app/getPageList");
    }
    //应用专题
    if (contentType == 3) {
        moreparam = {};
        var appType = $("#appsubject_type").find("option:selected").val();
        moreparam['subjectName'] = $("#name_3").val();
        moreparam['categoryId'] = appType;
        moreparam['isEffective'] = 1;
        $('#div_' + 0).css('display', 'none');
        $('#div_' + 1).css('display', 'none');
        $('#div_' + 2).css('display', 'none');
        $('#div_' + 3).css('display', 'block');
        $('#div_' + 4).css('display', 'none');
        $('#div_' + 6).css('display', 'none');
        $('#div_' + 7).css('display', 'none');
        getMoreList(3, "/cloudAppSubject/getPageList");
    }
    //活动详情
    if (contentType == 4) {
        moreparam = {};
        moreparam['title'] = $("#name_4").val();
        $('#div_' + 0).css('display', 'none');
        $('#div_' + 1).css('display', 'none');
        $('#div_' + 2).css('display', 'none');
        $('#div_' + 3).css('display', 'none');
        $('#div_' + 4).css('display', 'block');
        $('#div_' + 6).css('display', 'none');
        $('#div_' + 7).css('display', 'none');
        getMoreList(4, "/activityFreeVip/getPageList");
    }
    //商品包
    if (contentType == 7) {
        moreparam = {};
        moreparam['title'] = $("#name_7").val();
        moreparam['isEffective'] = 1;
        $('#div_' + 0).css('display', 'none');
        $('#div_' + 1).css('display', 'none');
        $('#div_' + 2).css('display', 'none');
        $('#div_' + 3).css('display', 'none');
        $('#div_' + 4).css('display', 'none');
        $('#div_' + 6).css('display', 'none');
        $('#div_' + 7).css('display', 'block');
        getMoreList(7, "/portal/onlineGoods/getPageList");
    }
    $('#editcontent_myModal').modal({backdrop: 'static', keyboard: false});
}

function typeChange() {
    var isPlay = $("#txt_isPlay").find("option:selected").val();
    if (isPlay == 0) {
        $('#t').css('display', 'block');
        $('#s').css('display', 'none');
    } else {
        $('#t').css('display', 'none');
        $('#s').css('display', 'block');
    }
}

function close_contentdiaglog() {
    beforeAdd();
    $("#editcontent_myModal").modal('hide');
}