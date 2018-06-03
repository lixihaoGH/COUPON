<%@ page language="Java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set value="${pageContext.request.contextPath }" var="ctx"></c:set>
<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->

<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->

<!--[if !IE]><!-->
<html lang="en" class="no-js"> <!--<![endif]-->

<!-- BEGIN HEAD -->

<head>

    <meta charset="utf-8"/>

    <title>微信营销系统</title>

    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>

    <meta content="" name="description"/>

    <meta content="" name="author"/>

    <!-- BEGIN GLOBAL MANDATORY STYLES -->


    <!-- 红包系统 -->
    <%--<link href="${ctx}/res/css/bootstrap-modal.css"/>--%>
    <script type="text/javascript" src="${ctx}/res/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/res/js/coupon/serverDomain.js"></script>
    <sitemesh:write property='head'/>
    <!-- 红包系统 -->

    <link href="${ctx}/res/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

    <link href="${ctx}/res/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>

    <link href="${ctx}/res/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>

    <link href="${ctx}/res/css/style.css" rel="stylesheet" type="text/css"/>

    <link href="${ctx}/res/css/style-responsive.css" rel="stylesheet" type="text/css"/>

    <link href="${ctx}/res/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>

    <link href="${ctx}/res/css/uniform.default.css" rel="stylesheet" type="text/css"/>


    <!-- END GLOBAL MANDATORY STYLES -->

    <!-- BEGIN PAGE LEVEL STYLES -->
    <%--<script src="${ctx}/res/js/jquery-1.10.1.min.js" type="text/javascript"></script>--%><!-- 版本低 -->
    <%--<script src="${ctx}/res/bootstrap/jquery.messager.js" type="text/javascript"></script>--%>
    <link href="${ctx}/res/css/jquery.gritter.css" rel="stylesheet" type="text/css"/>

    <link href="${ctx}/res/css/daterangepicker.css" rel="stylesheet" type="text/css"/>

    <link href="${ctx}/res/css/fullcalendar.css" rel="stylesheet" type="text/css"/>

    <link href="${ctx}/res/css/jqvmap.css" rel="stylesheet" type="text/css" res="screen"/>

    <link href="${ctx}/res/css/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css" res="screen"/>

    <link href="${ctx}/res/css/navigation/font-awesome.min.css" rel="stylesheet">
    <link href="${ctx}/res/css/navigation/style.css" rel="stylesheet" type="text/css" res="screen"/>
    <script src="${ctx}/res/js/common/navigation.js" type="text/javascript"></script>

<%--    <script src="${ctx}/res/js/bootstrap-modal.js"></script>
    <script src="${ctx}/res/js/bootstrap-modalmanager.js"></script>
    <link href="${ctx}/res/css/bootstrap-modal.css">
--%>

    <!-- END PAGE LEVEL STYLES -->

    <link rel="shortcut icon" href="${ctx}/res/image/favicon.ico"/>
    <style>
        .page-sidebar {
            position: fixed;
            top: 42px;
            height: 586px;
            overflow: auto;
        }

        .navbar-fixed-top {
            background-color: RGB(61, 61, 61);
            height: 42px;
        }

        .page-sidebar ul > li > a > .arrow:before {
            margin: 0;
        }

        .footer {
            position: fixed;
            bottom: 0;
            display: block;
            width: 100%;
            background-color: rgb(61, 61, 61);
            text-align: center;
        }
    </style>
</head>

<!-- END HEAD -->

<!-- BEGIN BODY -->

<body class="page-header-fixed">

<!-- BEGIN HEADER -->

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <!-- BEGIN TOP NAVIGATION BAR -->

        <div class="navbar navbar-header">

            <!-- BEGIN LOGO -->

            <a class="brand" href="#">

                <img src="${ctx}/res/image/logo.png" alt="logo"/>

            </a>

            <!-- END LOGO -->

            <!-- BEGIN RESPONSIVE MENU TOGGLER -->

            <a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">

                <img src="${ctx}/res/image/menu-toggler.png" alt=""/>

            </a>

            <!-- END RESPONSIVE MENU TOGGLER -->

            <!-- BEGIN TOP NAVIGATION MENU -->


            <!-- END TOP NAVIGATION MENU -->

        </div>

    </div>

    <!-- END TOP NAVIGATION BAR -->

</div>

<!-- END HEADER -->

<!-- BEGIN CONTAINER -->

<div class="page-container">

    <!-- BEGIN SIDEBAR -->

    <div id="sidebarcontainer" style="width: 200px;float: left; position: fixed">
        <ul id="accordion" class="accordion">
            <li>
                <div class="link weixin">公众号管理<i class="fa fa-chevron-down"></i></div>
                <ul class="submenu">
                    <li class="edit"><a href="${ctx}/weixin/edit">公众号管理</a></li>
                </ul>
            </li>
            <li>
                <div class="link coupon">红包管理<i class="fa fa-chevron-down"></i></div>
                <ul class="submenu">
                    <li class="create"><a href="${ctx}/coupon/create">新建红包</a></li>
                    <li class="getList"><a href="${ctx}/coupon/getList">红包列表</a></li>
                    <li class="releaseList"><a href="${ctx}/coupon/releaseList">策略列表</a></li>
                    <li class="entrance"><a href="${ctx}/coupon/entrance">入口管理</a></li>
                    <li class="statistics"><a href="${ctx}/coupon/statistics">红包统计</a></li>
                </ul>
            </li>

            <%--<li><div class="link"><i class="fa fa-globe"></i>Posicionamiento web<i class="fa fa-chevron-down"></i></div>
                        <ul class="submenu">
                            <li><a href="#">Google</a></li>
                            <li><a href="#">Bing</a></li>
                            <li><a href="#">Yahoo</a></li>
                            <li><a href="#">Otros buscadores</a></li>
                        </ul>
                    </li>
             --%>
        </ul>
    </div>


    <!-- END SIDEBAR -->

    <!-- BEGIN PAGE -->

    <div class="page-content">

        <!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->

        <div id="portlet-config" class="modal hide">

            <div class="modal-header">

                <button data-dismiss="modal" class="close" type="button"></button>

                <h3>Widget Settings</h3>

            </div>

            <div class="modal-body">

                Widget settings form goes here

            </div>

        </div>

        <!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->

        <!-- BEGIN PAGE CONTAINER-->
        <sitemesh:write property='body'/>

        <!-- END PAGE CONTAINER-->

    </div>

    <!-- END PAGE -->

</div>

<!-- END CONTAINER -->

<!-- BEGIN FOOTER -->

<div class="footer">

    <div class="text-center">

        2017 &copy; 版权所属 <a href="http://www.hiveview.com/" title="家视天下" target="_blank">家视天下</a>

    </div>

</div>

<!-- END FOOTER -->


<!-- BEGIN PAGE LEVEL SCRIPTS -->

<script src="${ctx}/res/js/app.js" type="text/javascript"></script>

<script src="${ctx}/res/js/index.js" type="text/javascript"></script>
<script type="text/javascript">
    window.onload = function () {
        console.log(document.URL);
        var nowUrl = document.URL;
        if (nowUrl.indexOf('edit') > -1) {
            $('.footer').css('display','none');
        }
    };
</script>
<!-- END PAGE LEVEL SCRIPTS -->

<%--
<script>
    var ul = document.getElementById("sidebar");
    var lis = ul.getElementsByTagName("li");
    for (var i = 0; i < lis.length; i++) {
        lis[i].onclick = function () {
            this.setAttribute("class", "start active");

        }
    }
    $('#createCoupon').click(function () {
        $(this).addClass('active');
    });

    //点击页面刷新按钮或关闭再打开页面,左侧导航栏第一个选项为默认状态,页面为默认状态


    var ul = document.getElementById("sidebar");
    var lis = ul.getElementsByTagName("li");
    var div = document.getElementById('sidebarcontainer');
    var sidebar = document.getElementById('sidebarcontainer');
    var storage = window.localStorage;
    sidebar.onscroll = function () {
        storage.scroll = this.scrollTop;
    };

    window.onload = function () {
        console.log(document.URL);
        var nowUrl = document.URL;
        if (nowUrl.indexOf('create') > -1) {
            storage.id = 1;
        } else if (nowUrl.indexOf('getList') > -1) {
            storage.id = 2;
        } else if (nowUrl.indexOf('releaseList') > -1) {
            storage.id = 3;
        } else if (nowUrl.indexOf('entrance') > -1) {
            storage.id = 4;
        } else if (nowUrl.indexOf('statistics') > -1) {
            storage.id = 5;
        } else {
            storage.id = 0;
        }
    };

    for (var i = 0; i < lis.length; i++) {
        lis[i].idx = i;
        lis[i].onclick = function (e) {
            for (var j = 0; j < lis.length; j++) {
                lis[j].setAttribute('class', '');
            }
            storage.id = this.idx;
            lis[this.idx].setAttribute('class', 'start active');
        };
    }
    lis[storage.id].setAttribute('class', 'start active');
    sidebar.scrollTop = storage.scroll;


    //左侧固定导航部分 滚动条位置调整(页面刷新时,滚动条在顶部;当滚动条滚动时,选项被点击滚动条在上次滚动到的位置)
    /*if( storage.id == 0){
        sidebar.scrollTop = 0;
        $("html,body").animate({"scrollTop":top});
    }else{
        sidebar.scrollTop = storage.scroll;
    }


    $(function () {

    App.init(); // initlayout and core plugins

    Index.init();

     Index.initJQVMAP(); // init index page's custom scripts

     Index.initCalendar(); // init index page's custom scripts

     Index.initCharts(); // init index page's custom scripts

     Index.initChat();

     Index.initMiniCharts();

     Index.initDashboardDaterange();
           alert(1);
             Index.initIntro();
         $(".page-sidebar-menu>li").on(click,function(){
               $(this).addClass("active");
           });
       });*/

</script>
--%>
<!-- END JAVASCRIPTS -->

</body>

<!-- END BODY -->

</html>