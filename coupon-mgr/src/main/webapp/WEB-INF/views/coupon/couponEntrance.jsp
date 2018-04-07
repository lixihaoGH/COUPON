<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>入口管理</title>
    <!-- Bootstrap -->

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/bootstrap/bootstrap-table.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/css/bootstrap.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/css/coupon/common.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/css/easyui.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/css/coupon/couponPolicy.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/res/uploadify/uploadify.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/res/css/bootstrap-modal.css">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/res/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/res/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/bootstrap/bootstrap-table.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/bootstrap/bootstrap-table-zh-CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/js/coupon/couponEntrance.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/res/js/bootstrap-modal.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/js/bootstrap-modalmanager.js"></script>
</head>

<body>

<!-- 查询条件 -->
<div class="panel-body my-panel-body" style="width: 90%;height: 90%">
    <div class="panel panel-default">
        <div style="height: 80px;">
            <form id="formSearch" class="form-horizontal">
                <div class="form-group" style="margin-top:15px">
                    <div class="entrance-search-div">
                        <label class="control-label" id="coupon-entrance-name">领取入口名称：</label>
                        <input type="text" class="form-control coupon-input coupon-common-input" id="entranceNameInput"
                        >
                        <button type="button" id="searchEntranceName"
                                class="btn btn-primary coupon-button entrance-search-button">查询
                        </button>
                    </div>

                </div>
            </form>
        </div>
    </div>
    <!-- 弹出层Button -->
    <div class="col-sm-4 coupon-div">
        <button class="btn btn-primary btn-lg policy-create-btn" id="createNewEntranceBtn">
            新建入口
        </button>
    </div>
    <!-- 数据表格 -->
    <table id="couponEntranceList">
    </table>
</div>

<!-- 新建入口模态框-start -->
<div id="createNewEntranceModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="createNewEntranceLabel"
     aria-hidden="true" style="height: 220px;overflow-y: hidden;top: 30%;">
    <div class="modal-dialog" style="margin: 0px 0px 0px 0px;width:310px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">

                </button>
                <h4 class="modal-title" id="createNewEntranceLabel">
                    新建入口
                </h4>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div>
                            <span class="sign">*</span>入口名称
                            <input type="text" class="form-control coupon-input coupon-common-input"
                                   name="entrance_name">
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="saveCouponEntranceName">
                    保存
                </button>
            </div>
        </div>
    </div>
</div>
<!-- 新建入口模态框-end -->

<!-- 修改入口模态框-start -->
<div id="updateNewEntranceModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="updateNewEntranceLabel"
     aria-hidden="true" style="height: 220px;overflow-y: hidden;top: 30%;">
    <div class="modal-dialog" style="margin: 0px 0px 0px 0px;overflow-y: hidden;margin-top: 0px;width: 310px;">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">

            </button>
            <h4 class="modal-title" id="updateNewEntranceLabel">
                修改入口
            </h4>
        </div>
        <div class="modal-body">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div>
                        <span class="sign">*</span>入口名称
                        <input type="text" class="form-control coupon-input coupon-common-input"
                               name="update_entrance_name">
                    </div>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
            </button>
            <button type="button" class="btn btn-primary" id="updateCouponEntranceName" onclick="saveUpdateButton()">
                保存
            </button>
        </div>
    </div>
</div>
<!-- 新建入口模态框-end -->
<!-- 策略列表模态框-start -->
<div id="choosePolicyModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="choosePolicyModalLabel"
     aria-hidden="true" style="top: 55%;left: 50%;width: 1090px;height: 600px;overflow-y: visible;">
    <div class="modal-dialog my-modal-dialog" style="height: 600px;margin: 0px;width: 1087px;overflow-y: visible;">
        <div class="modal-content" style="overflow-y: visible;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">

                </button>
                <h4 class="modal-title" id="choosePolicyModalLabel">
                    策略列表
                </h4>
            </div>
            <div style="height: 550px;max-height: 600px;overflow-y: visible;">
                <div class="col-sm-4 coupon-div">
                    <button class="btn btn-primary btn-lg policy-create-btn" id="chooseNewReleaseBtn">
                        选择投放策略
                    </button>
                    <p style="color: #ff0000;width: 500px;">每个入口最多只能同时生效一条策略。排序：按投放开始时间倒序排序</p>
                </div>
                <!-- 数据表格 -->
                <table id="couponPolicyList">
                </table>
            </div>
        </div>
    </div>


</div>
<!-- 策略列表模态框-end-->

<!-- 选择策略弹出层-start -->
<div id="chooseReleasePolicy" class="modal fade in" role="dialog" aria-labelledby="chooseReleasePolicyModal"
     aria-hidden="true" style="top: 50%;left: 50%;height: 650px;overflow-y: visible">
    <div class="modal-dialog my-modal-dialog" style="margin: 0px;height: 600px;">
        <%--<div class="modal-content" style="height: 690px;">--%>
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">

            </button>
            <h4 class="modal-title" id="chooseReleasePolicyModal">
                选择策略
            </h4>
        </div>
        <div class="my-modal-dialog" style="overflow-y: visible;">
            <div class="test-modal-body test-modal-body-left" style="width: 450px;">
                <div class="panel panel-default">
                    <div class="panel-heading">已选</div>
                    <div class="panel-body" style="height: 540px">
                        <table id="selectedPolicyList">
                        </table>
                        <button type="button" class="btn btn-default btn-primary" id="saveSelectedPolicy">保存
                        </button>
                    </div>
                </div>
            </div>
            <div class="test-modal-body test-modal-body-right" style="width: 500px;">
                <div class="panel panel-default" style="width: 630px;">
                    <div class="panel-heading">选择策略</div>
                    <div class="panel-body" style="height: 540px;width: 630px;">
                        <div style="margin:0px 0px 10px 0px;">
                            <span>策略名称:</span>
                            <input type="text" class="form-control coupon-input coupon-common-input"
                                   id="policyNameInput">
                            <button type="button" class="btn btn-default btn-primary" id="policySearchButton">查询
                            </button>
                        </div>
                        <table id="policyList" style="margin-top:10px;">
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <%--</div>--%>
    </div>
    <!-- 选择红包弹出层-end -->
</div>

<!-- 删除提示框弹出层 -->
<div id="deletePolicy" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="deletePolicyModal"
     aria-hidden="true" style="overflow: auto;height: 221px;top: 20%;">
    <div class="modal-dialog" style="width:300px;height: 218px;margin: 0;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">

                </button>
                <h4 class="modal-title" id="deletePolicyModal">
                    是否删除
                </h4>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div>
                            <p>
                                <span></span>是否删除？
                            </p>

                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="deleteCouponPolicy" onclick="deleteEntrance()">
                    确定
                </button>
            </div>
        </div>
    </div>
</div>

<div id="deleteReleasedPolicy" class="modal fade" tabindex="-1" role="dialog"
     aria-labelledby="deleteReleasedPolicyModal"
     aria-hidden="true" style="overflow: auto">
    <div class="modal-dialog" style="width:300px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">

                </button>
                <h4 class="modal-title" id="deleteReleasedPolicyModal">
                    是否删除
                </h4>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div>
                            <p>
                                <span></span>已被投放，无法删除
                            </p>

                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>
<!-- 删除删除提示框弹出层 -->


<div id="errorMessageDiv" class="modal" tabindex="-1" role="dialog"
     aria-labelledby="errorMessageModal"
     aria-hidden="true" style="height: 207px;top: 30%;left: 50%;">
    <div class="modal-dialog" style="width:280px;height: 140px;">
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
                <button type="button" class="btn btn-primary" id="entranceErrorModalButton"
                        onclick="entranceErrorModalButton()">
                    确定
                </button>
            </div>
        </div>
    </div>
</div>

</body>

</html>