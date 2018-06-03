<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>策略列表</title>
    <!-- Bootstrap -->
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->

    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/bootstrap/bootstrap-table.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/css/bootstrap.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/static/css/coupon/common.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/static/css/coupon/couponPolicy.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/css/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/res/uploadify/uploadify.css">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/res/js/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/js/coupon/couponNewRelease.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/uploadify/jquery.uploadify.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/res/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/bootstrap/bootstrap-table.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/bootstrap/bootstrap-table-zh-CN.js"></script>

    <%--
        <link href="${pageContext.servletContext.contextPath}/res/css/bootstrap-modal.css">
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/res/js/bootstrap-modal.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/res/js/bootstrap-modalmanager.js"></script>
    --%>

    <style type="text/css">
        .test_css {
            width: 10px;
        }

        #sample_1 {
            table-layout: fixed;
        }

        td {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        .fixed-table-toolbar .columns {
            top: -10px;
        }

        .fixed-table-container {
            top: -5px;
        }

        .fixed-table-toolbar {
            height: 0px;
        }

        .pull-right {
            height: 0px;
        }

        .keep-open {
            height: 0px;
        }

        .fixed-table-toolbar .btn-group > .btn-group:last-child > .btn {
            top: -47px;
        }

        .btn-xs {
            vertical-align: middle;
        }

        .fixed-table-body {
            width: 100%;
        }

        .portlet {
            margin-bottom: 52px;
        }

        #channelModal {
            width: auto;
        }
    </style>
</head>
<body>

<!-- 查询条件 -->
<div class="my-panel-body" style="width: 80.5%;height: 580px;">
    <div class="panel panel-default">
        <div style="height: 150px;">
            <form id="formSearch" class="form-horizontal">
                <div class="form-group" style="margin-top:15px">
                    <div id="" style="margin:0px 0px 30px 20px;text-align: left">
                        <label class="control-label" style="width: 100px;">投放状态：</label>
                        <input type="radio" value="" name="release_status">
                        <span class="release-checkbox">全部</span>
                        <input type="radio" value="0" name="release_status">
                        <span class="release-checkbox">未开始</span>
                        <input type="radio" value="1" name="release_status">
                        <span class="release-checkbox">投放中</span>
                        <input type="radio" value="3" name="release_status">
                        <span class="release-checkbox">投放结束</span>
                        <input type="radio" value="2" name="release_status">
                        <span class="release-checkbox">已停发</span>
                    </div>
                    <div class="release-search-div">
                        <label class="control-label" id="couponPolicyName"
                               style="float: left; margin:0px 0px 0px 20px; width: 100px;">策略名称：</label>
                        <input type="text" class="form-control coupon-input" id="release-stock-name"
                               style="float: left;">
                        <button type="button" id="couponPolicySearchButton"
                                class="btn btn-primary coupon-button release-search-button"
                                style="float: left; margin:0px">查询
                        </button>
                    </div>
                </div>
            </form>
            <div class="col-sm-4 coupon-div" style="margin-left:20px;">
                <button class="btn btn-primary btn-lg policy-create-btn" id="new-release-btn" style="margin:0px;">
                    新建投放策略
                </button>
            </div>
        </div>
    </div>
    <!-- 数据表格 -->
    <div class="coupon-common-table" style="width: 903px;transform: translate(0px, -10px);">
        <table id="coupon-release-list" style="width: 898px">
        </table>
    </div>
</div>

<!-- 新建投放策略弹出层-start -->
<div id="create-new-release-modal" class="modal fade" tabindex="-1" role="dialog"
     aria-labelledby="createNewReleaseLabel"
     aria-hidden="true" style="overflow: auto;height: 670px;width: 820px;">
    <div class="modal-dialog" style="width:800px;height: 750px;">
        <div class="modal-content" style="top: -30px;bottom: 0px;left: 0px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                </button>
                <h4 class="modal-title" id="createNewReleaseLabel">
                    新建投放策略
                </h4>
            </div>
            <div>
                <div class="panel panel-default">
                    <div>
                        <div>
                            <p id="group-name">
                                <span class="sign">*</span>策略名称
                            </p>
                            <input type="text" class="form-control coupon-release-input" name="release_name">
                        </div>
                        <div>
                            <span class="sign">*</span>投放时间
                            <input type="text" class="form-control coupon-input coupon-common-input"
                                   id="release_start_time">
                            <span style="margin-left:5px;">至</span>
                            <input type="text" class="form-control coupon-input coupon-common-input"
                                   id="release_end_time" style="transform:translate(0,0);">
                        </div>
                        <div class="coupon-release-count">
                            <span class="sign">*</span>投放数量
                            <input type="text" class="form-control coupon-input coupon-common-input"
                                   name="release_count">
                        </div>
                        <div class="coupon-release-btn">
                            <span class="sign">*</span>投放红包
                            <button id="release-coupon-btn" class="btn btn-primary choose-coupon">
                                添加红包
                            </button>
                        </div>
                        <!-- 数据表格 -->
                        <table id="saveSuccessCouponList">
                        </table>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" style="text-align:left">领取限制</div>
                    <div style="height: 70px;text-align:left;">
                        <div style="height:60px;">
                            <span class="sign">*</span>用户领取限制：
                            <input type="checkbox" name="phone_day_checked" value="0" checked="checked">
                            <input type="text" name="phone_day_max" id="day-max-input"
                                   class="form-control coupon-common-input" style="width: 50px" value="1">次/人/日
                            <input type="checkbox" name="phone_total_checked" value="1">
                            总计：
                            <input type="text" name="phone_total_max" id="day-amount-input"
                                   class="form-control coupon-common-input" style="width: 50px">次
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">手机领取页面</div>
                    <div class="panel-body">
                        <div style="height:60px;">
                            页面顶部导航标题：
                            <input type="text" name="page_title" class="form-control coupon-input">
                        </div>
                        顶部广告图(宽750px，高854px)：
                        <div id="uploader" class="wu-example">
                            <!--用来存放文件信息-->
                            <img style='height: 80px; width: 80px;' id="gotoPageImg"/><br/>
                            <input type="file" id="gotoPageImg_file_upload"
                                   name="gotoPageImg_Icon_file"/>
                        </div>
                        <div style="height:60px;">
                            广告跳转链接：
                            <input type="text" name="goto_page_url"
                                   class="form-control coupon-input coupon-common-input">
                        </div>
                        <div style="height:60px;">
                            页面底色色值：
                            <input type="text" name="page_bgcolor"
                                   class="form-control coupon-input coupon-common-input">
                            <p style="color: #F00">色值数字前面请加上英文状态下的 # </p>
                        </div>
                        <button id="share-html-btn" class="btn btn-primary choose-coupon">
                            点击预览
                        </button>
                    </div>
                </div>
                <div class="panel-heading" style="background-color: #f5f5f5">分享信息</div>
                <div class="panel-body">
                    <div style="height:60px;">
                        分享标题：
                        <input type="text" name="share_title" class="form-control coupon-input coupon-common-input">
                    </div>
                    <div>
                        分享内容：
                        <textarea class="form-control coupon-textarea" rows="3" name="share_desc"></textarea>
                    </div>
                    <div>
                        图片(宽>300px，高>300px)：
                        <div class="uploader-demo">
                            <!--用来存放item-->
                            <img style='height: 80px; width: 80px;' id="shareImage"/><br/>
                            <input type="file" id="shareImage_file_upload"
                                   name="shareImage_Icon_file"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="saveCouponPolicy">
                    保存
                </button>
            </div>
        </div>

    </div>
</div>
<!-- 新建投放策略弹出层-end -->

<!-- 投放策略详情 -- 弹出层-start -->
<div id="show-release-info-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="showReleaseLabel"
     aria-hidden="true" style="overflow: auto; height: 670px;width:820px;">
    <div class="modal-dialog" style="width:800px;height: 750px;">
        <div class="modal-content" style="top: -30px;bottom: 0px;left: 0px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">

                </button>
                <h4 class="modal-title" id="showReleaseLabel">
                    投放策略详情
                </h4>
            </div>
            <div>
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div>
                            <p id="show_release_name">
                                <span class="sign">*</span>策略名称
                            </p>
                            <input type="text" class="form-control coupon-release-input" name="show_release_name">
                        </div>
                        <div>
                            <span class="sign">*</span>投放时间
                            <input type="text" class="form-control coupon-input coupon-common-input"
                                   id="show_release_start_time">
                            <span style="margin-left:5px;">至</span>
                            <input type="text" class="form-control coupon-input coupon-common-input"
                                   id="show_release_end_time" style="transform:translate(0,0);">
                        </div>
                        <div class="coupon-release-count">
                            <span class="sign">*</span>投放数量
                            <input type="text" class="form-control coupon-input coupon-common-input"
                                   name="show_release_count">
                        </div>
                        <div id="coupon-release-btn">
                            <span class="sign">*</span>投放红包
                            <button id="show-release-coupon-btn" class="btn btn-primary choose-coupon">
                                添加红包
                            </button>
                        </div>
                        <!-- 数据表格 -->
                        <table id="showSaveSuccessCouponList">
                        </table>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" style="text-align:left">领取限制</div>
                    <div style="height: 70px;text-align:left;margin-top: 30px;">
                        <div style="height:60px;">
                            <span class="sign">*</span>用户领取限制：
                            <input type="checkbox" name="show_phone_day_checked" value="0">
                            <input type="text" name="update_phone_day_max"
                                   class="form-control coupon-common-input" style="width: 50px">次/人/日
                            <input type="checkbox" name="show_phone_total_checked" value="1">
                            总计：
                            <input type="text" name="update_phone_total_max"
                                   class="form-control coupon-common-input" style="width: 50px">次
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading">手机领取页面</div>
                    <div class="panel-body">
                        <div style="height:60px;">
                            页面顶部导航标题：
                            <input type="text" name="show_page_title" class="form-control coupon-input">
                        </div>
                        顶部广告图(宽750px，高854px)：
                        <div class="wu-example">
                            <!--用来存放文件信息-->
                            <img src="" style='height: 80px;' id="updateGotoPageImg"/><br/>
                            <input type="file" id="updateGotoPageImg_file_upload"
                                   name="topAd_Icon_file"/>
                        </div>
                        <div style="height:60px;">
                            广告跳转链接：
                            <input type="text" name="show_goto_page_url"
                                   class="form-control coupon-input coupon-common-input">
                        </div>
                        <div style="height:60px;">
                            页面底色色值：
                            <input type="text" name="show_page_bgcolor"
                                   class="form-control coupon-input coupon-common-input">
                            <p style="color: #f00">色值数字前面请加上英文状态下的 # </p>
                        </div>
                        <button id="show-share-html-btn" class="btn btn-primary choose-coupon">
                            点击预览
                        </button>
                    </div>
                    <div class="panel-heading">分享信息</div>
                    <div class="panel-body">
                        <div style="height:60px;">
                            分享标题：
                            <input type="text" name="show_share_title"
                                   class="form-control coupon-input coupon-common-input">
                        </div>
                        <div>
                            分享内容：
                            <textarea class="form-control coupon-textarea" rows="3" name="show_share_desc"></textarea>
                        </div>
                        <div>
                            图片(宽>300px，高>300px)：
                            <div class="uploader-demo">
                                <!--用来存放item-->
                                <img src="" style='height: 80px;' id="showShareImage"/><br/>
                                <input type="file" id="showShareImage_file_upload"
                                       name="showShareImage_Icon_file"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%--<div class="panel panel-default">

            </div>--%>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="saveReleaseInfo">
                    保存
                </button>
            </div>
        </div>

    </div>
</div>
<!-- 新建投放策略弹出层-end -->

<!-- 新建投放策略 -- 选择红包弹出层-start -->
<div id="create-choose-release-coupon" class="modal fade" role="dialog" aria-labelledby="createChooseReleaseCoupon"
     aria-hidden="true" style="width: 1052px;height: 635px;">
    <div class="modal-dialog my-modal-dialog" style="width:1050px;">
        <div class="modal-content" style="top: -30px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                </button>
                <h4 class="modal-title" id="createChooseReleaseCoupon">
                    选择红包
                </h4>
            </div>
            <div class="modal-body my-modal-dialog" style="max-height: 600px;overflow-y: visible;">
                <div class="test-modal-body test-modal-body-left" style="width: 450px;">
                    <div class="panel panel-default">
                        <div class="panel-heading">已选</div>
                        <div class="panel-body">
                            <div style="height: 350px;width: 400px;">
                                <table id="selectedCoupon">
                                </table>
                            </div>
                        </div>
                        <button type="button" class="btn btn-default btn-primary"
                                style="margin: 30px 0px 0px 100px;width: 250px;"
                                id="saveSelectedBtn">保存
                        </button>

                    </div>
                </div>
                <div class="test-modal-body test-modal-body-right" style="width: 550px;">
                    <div class="panel panel-default">
                        <div class="panel-heading">选择红包</div>
                        <div class="panel-body" style="height: 500px;width: 550px;">
                            <div style="margin:0px 0px 10px 0px;">
                                <span>红包id:</span>
                                <input type="text" class="form-control coupon-input coupon-common-input"
                                       id="selectedCouponId">
                                <span>红包名称:</span>
                                <input type="text" class="form-control coupon-input coupon-common-input"
                                       id="selectedCouponName">
                                <button type="button" class="btn btn-default btn-primary" id="release-search-btn">查询
                                </button>
                            </div>
                            <div style="overflow: auto;">
                                <table style="margin-top:10px;" id="coupon-select-table">
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<!-- 选择红包弹出层-end -->

<!-- 投放策略详情 -- 选择红包弹出层-start -->
<div id="update-choose-release-coupon" class="modal fade" role="dialog" aria-labelledby="chooseReleaseCoupon"
     aria-hidden="true" style="width: 1052px;height: 635px;">
    <div class="modal-dialog my-modal-dialog" style="width:1050px;">
        <div class="modal-content" style="top:-30px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                </button>
                <h4 class="modal-title" id="chooseReleaseCoupon">
                    选择红包
                </h4>
            </div>
            <div class="modal-body my-modal-dialog" style="max-height: 600px;overflow-y: visible;">
                <div class="test-modal-body test-modal-body-left" style="width: 450px;">
                    <div class="panel panel-default">
                        <div class="panel-heading">已选</div>
                        <div class="panel-body">
                            <div style="height: 350px;width: 400px;">
                                <table id="updateSelectedCoupon">
                                </table>
                            </div>
                        </div>
                        <button type="button" class="btn btn-default btn-primary"
                                style="margin: 30px 0px 0px 100px;width: 250px;"
                                id="updateSelectedBtn">保存
                        </button>

                    </div>
                </div>
                <div class="test-modal-body test-modal-body-right" style="width: 400px;">
                    <div class="panel panel-default" style="width: 520px;">
                        <div class="panel-heading">选择红包</div>
                        <div class="panel-body" style="height: 500px;width: 550px;">
                            <div style="margin:0px 0px 10px 0px;">
                                <span>红包id:</span>
                                <input type="text" class="form-control coupon-input coupon-common-input"
                                       id="updateSelectedCouponId">
                                <span>红包名称:</span>
                                <input type="text" class="form-control coupon-input coupon-common-input"
                                       id="updateSelectedCouponName">
                                <button type="button" class="btn btn-default btn-primary"
                                        id="update-release-search-btn">查询
                                </button>
                            </div>
                            <div style="overflow: auto;">
                                <table style="margin-top:10px;" id="update-coupon-select-table">
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<!-- 选择红包弹出层-end -->

<!-- 新建投放策略 -- 页面预览-start -->
<div id="share-html-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1"
     aria-hidden="true" style="height: 435px;width: 600px;">
    <div class="modal-dialog">
        <div class="modal-content" style="top: -30px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                </button>
                <h4 class="modal-title" id="myModalLabel1">
                    页面预览
                </h4>
            </div>
            <div class="modal-body">
                <div class="show-share-html" id="sharePageDiv">
                    <label class="share-title" id="pageShareTitle"></label>
                    <img id="share-img" src=""/>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>

            </div>
        </div>
    </div>
</div>
<!-- 页面预览-end -->

<!-- 投放策略详情 -- 页面预览-start -->
<div id="update-share-html-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="updateShareHtmlModal"
     aria-hidden="true" style="height: 435px;width: 600px;">
    <div class="modal-dialog">
        <div class="modal-content" style="top:-30px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                </button>
                <h4 class="modal-title" id="updateShareHtmlModal">
                    页面预览
                </h4>
            </div>
            <div class="modal-body">
                <div class="show-share-html" id="showSharePageDiv">
                    <label class="share-title" id="showPageShareTitle"></label>
                    <img id="showShareImg" src=""/>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>
<!-- 页面预览-end -->

<div id="errorMessageDiv" class="test_css modal fade" tabindex="-1" role="dialog"
     aria-labelledby="errorMessageModal"
     aria-hidden="true" style="height: 206px;width: 300px;left: 60%;">
    <div class="modal-dialog" style="width:300px;height: 0px;">
        <div class="modal-content" style="top: -30px;bottom: 0px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                </button>
                <h4 class="modal-title" id="errorMessageModal">

                </h4>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div>
                            <p id="errorMessageP">

                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="errorModalButton" onclick="operateButton()">
                    确定
                </button>
            </div>
        </div>
    </div>
</div>
</body>

</html>