<%--
  Created by IntelliJ IDEA.
  User: 浩瀚
  Date: 2018/2/8
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>红包列表</title>
    <!-- Bootstrap -->
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/bootstrap/bootstrap-table.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/css/bootstrap.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/static/css/coupon/common.css">

    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>-->
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/res/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/bootstrap/bootstrap-table.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/bootstrap/bootstrap-table-zh-CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/js/coupon/couponStatistics.js"></script>

</head>
<body>
    <div class="panel-body my-panel-body" style="height: 600px; width: 850px;">
    <div class="panel panel-default">
        <div class="panel-heading">查询条件</div>
        <div class="panel-body" style="height: 80px;">
            <form id="formSearch" class="form-horizontal">
                <div>
                    策略ID:<input type="text" class="form-control coupon-input coupon-common-input" name="release_id" style="width: 270px">
                    红包名称：<input type="text" class="form-control coupon-input coupon-common-input" name="coupon_stock_name">
                    <button type="button" id="statistics_search" class="btn btn-primary coupon-button ">查询</button>
                </div>
            </form>
        </div>
    </div>
    <div style="width: 1080px;">
        <table id="coupon_statistics_list" >
        </table>
    </div>
</div>
</body>
</html>
