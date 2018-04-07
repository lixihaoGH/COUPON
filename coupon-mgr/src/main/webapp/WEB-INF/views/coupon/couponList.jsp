<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>红包列表</title>
    <!-- Bootstrap -->
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/bootstrap/bootstrap-table.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/css/bootstrap.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/css/coupon/common.css">

    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>-->
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/res/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/bootstrap/bootstrap-table.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/bootstrap/bootstrap-table-zh-CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/js/coupon/couponList.js"></script>
</head>

<body>


<div class="panel-body my-panel-body" style="height: 600px; width: 800px;">
    <div class="panel panel-default">
        <div class="panel-heading">查询条件</div>
        <div class="panel-body" style="height: 80px;">
            <form id="formSearch" class="form-horizontal">
                <div>
                    红包id:<input type="text" class="form-control coupon-input coupon-common-input" name="coupon_stock_id" style="width: 270px">
                    红包名称：<input type="text" class="form-control coupon-input coupon-common-input" name="coupon_stock_name">
                    <button type="button" id="coupon_search" class="btn btn-primary coupon-button ">查询</button>
                </div>
                <%--<div class="form-group" style="margin-top:15px">
                    <div class="col-sm-3 coupon-div" id="coupon_id_div">

                    </div>
                    <label class="control-label col-sm-1" id="coupon-label-name">红包名称：</label>
                    <div class="col-sm-3 coupon-div">

                    </div>
                    <div class="col-sm-4 coupon-div" id="coupon-search-button">

                    </div>
                </div>--%>
            </form>
        </div>
    </div>
    <div >
        <table id="coupon-list-table">
        </table>
    </div>

</div>

<div id="deleteDiv" class="modal fade" tabindex="-1" role="dialog"
     aria-labelledby="deleteModal"
     aria-hidden="true" style="overflow: hidden;width: 300px;height: 205px;">
    <div class="modal-dialog" style="width:300px;top:-36px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                </button>
                <h4 class="modal-title" id="deleteModal">
                    提示信息
                </h4>
            </div>
            <div class="modal-body">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div>
                            <p id="messageP">
                                确认删除？
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="deleteModalButton" onclick="deleteCouponEvents()">确认删除
                </button>
            </div>
        </div>
    </div>
</div>

<div id="errorMessageDiv" class="modal fade" tabindex="-1" role="dialog"
     aria-labelledby="errorMessageModal"
     aria-hidden="true" style="overflow: auto;height: 250px;width: 400px;">
    <div class="modal-dialog" style="width:300px;">
        <div class="modal-content">
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
            </div>
        </div>
    </div>
</div>
<!-- 提示框弹出层 -->
</body>

</html>