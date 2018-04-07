<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${template.page_title}</title>
    <meta charset="UTF-8">
    <meta name="desctiption"
          content="北京家视天下科技有限公司专注为电视台、电台等传统媒体进入移动互联网及互联网电视领域，提供基于音视频内容的领先产品技术及运营服务产品，涵盖iOS、Android、Windows等智能操作系统平台，能够覆盖Phone、Pad、TV等先进智能终端">
    <meta name="keyword" content="家视天下，电视台，电台，传统媒体，互联网电视，音视频内容，产品技术，运营服务，产品开发，智能操作，系统平台，智能终端，网络电视台，多终端，跨平台">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <link href="${ctx}/res/css/common.css" rel='stylesheet' type='text/css'/>
    <link href="${ctx}/res/css/index.css" rel='stylesheet' type='text/css'/>
    <script src="${ctx}/res/js/zepto.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>

    <script id="template" type="text/template">
        <div class="redpacket">
            <h2>#name#</h2>
            <p>#amount#</p>
            <span>#date#</span>
            <h3 class="fr"><i>¥</i>#value#</h3>
        </div>
    </script>

    <script id="template2" type="text/template">
        <p class="telephone tc" id="telephone">红包已发至手机:<span>#telephone#</span></p>
        <a javascript:void(0) id="lalala">点击再次领取(剩余#remained#次)</a>
    </script>

    <script type="text/javascript">

        //alert(location.href.split('#')[0]);

        var share_title = "${template.share_title}";
        var share_link = "${template.share_link}";
        var share_img_url = "${template.share_img_url}";
        var share_desc = "${template.share_desc}";

        wx.config({
            debug: false,
            appId: "${jssdkConfigRequest.appid}",
            timestamp: "${jssdkConfigRequest.timestamp}",
            nonceStr: "${jssdkConfigRequest.noncestr}",
            signature: "${jssdkConfigRequest.signature}",
            jsApiList: [
                'onMenuShareTimeline',
                'onMenuShareAppMessage',
                'checkJsApi'
            ]
        });

        wx.ready(function () {

            wx.onMenuShareTimeline({
                title: share_title,
                link: share_link,
                imgUrl: share_img_url,
                success: function () {
                },
                cancel: function () {
                }
            });

            wx.onMenuShareAppMessage({
                title: share_title,
                desc: share_desc,
                link: share_link,
                imgUrl: share_img_url,
                success: function () {
                },
                cancel: function () {
                },
                fail: function (res) {
                }
            });

            wx.checkJsApi({
                jsApiList: [
                    'onMenuShareTimeline',
                    'onMenuShareAppMessage'
                ],
                success: function (res) {
                }
            });
        });

        wx.error(function (res) {
        });

    </script>
    <script>
        function showErrorElement(json) {
            if (json.return_code != "SUCCESS") {
                if (json.return_code == "PARAM_ERROR" || json.return_code == "LACK_PARAMS" || json.return_code == "SYSTEMERROR" || json.return_code == "FAIL_POST_DATA_EMPTY") {
                    $("#redpacket-container").html(" ");
                    $("#lalala").html("点击再次领取(剩余" + json.coupon_detail.remaining_count + "次)");
                    var modal = function (text) {
                        $("#modal").show().text(text);
                        setTimeout(function () {
                            $("#modal").hide()
                        }, 2000);
                    };
                    modal("参数错误,请刷新重试");
                }
                if (json.return_code == "FAIL_TOTAL") {
                    $("#telephone").hide();
                    $("#lalala").hide();
                    var modal = function (text) {
                        $("#modal").show().text(text);
                        setTimeout(function () {
                            $("#modal").hide()
                        }, 2000);
                    };
                    if (json.coupon_detail.day_restriction_num) {
                        modal("领取失败,每日限领" + json.coupon_detail.day_restriction_num + "次");
                    }
                    ;
                    if (json.coupon_detail.total_restriction_num) {
                        modal("领取失败,累计限领" + json.coupon_detail.total_restriction_num + "次");
                    }
                    ;
                }
                if (json.return_code == "FAIL_RELEASE_EXPIRED") {
                    $("#redpacket-container").hide();
                    $("#useout").show();
                    $("#useout").find("span").css("left", "2.3rem");
                }
                ;
                if (json.return_code == "FAIL_RELEASE_EMPTY") {
                    $("#redpacket-container").hide();
                    $("#useout").show();
                    $("#useout").find("span").html("来迟了,红包已被抢完");
                }
                ;
            }
            ;
        }

        function showRetryElement(json) {
            if (json.return_code == "SUCCESS") {
                if (json.coupon_detail.remaining_count >= 0) {
                    $("#lalala").html("点击再次领取(剩余" + json.coupon_detail.remaining_count + "次)");
                    var modal = function (text) {
                        $("#modal").show().text(text);
                        setTimeout(function () {
                            $("#modal").hide()
                        }, 2000);
                    };
                    modal("领取成功");
                    if (json.coupon_detail.remaining_count == 0) {
                        $("#telephone").hide();
                        $("#lalala").hide();
                    }
                }
            }


        }

        function showRedPacket(json) {
            if (json.return_code == "SUCCESS" || json.return_code == "FAIL_TOTAL") {
                var redpacketContainer = $("#redpacket-container");
                for (var i = 0; i < json.coupon_detail.stock_list.length; i++) {
                    var str = $("#template").html();
                    str = str.replace('#name#', json.coupon_detail.stock_list[i].coupon_stock_name);
                    if (json.coupon_detail.stock_list[i].preferential_type == 1) {
                        str = str.replace('#amount#', "满" + json.coupon_detail.stock_list[i].reach_amount / 100 + "元可用");
                    }
                    if (json.coupon_detail.stock_list[i].effective_duration == null) {
                        str = str.replace('#date#', "有效期:" + json.coupon_detail.stock_list[i].effective_time.replace(/-/g, ".").substring(0, 10) + " - " + json.coupon_detail.stock_list[i].expired_time.replace(/-/g, ".").substring(0, 10));
                    } else {
                        str = str.replace('#date#', "有效期至:" + json.coupon_detail.stock_list[i].expired_time.replace(/-/g, ".").substring(0, 10));
                    }
                    str = str.replace('#value#', json.coupon_detail.stock_list[i].preferential_amount / 100);
                    redpacketContainer.prepend(str);
                }
                if (json.return_code != "FAIL_TOTAL") {
                    var str2 = $("#template2").html();
                    str2 = str2.replace('#telephone#', json.coupon_detail.phone_number);
                    str2 = str2.replace('#remained#', json.coupon_detail.remaining_count);
                    redpacketContainer.append(str2);
                }
            }
        }


        $(function () {
            $("body").css({
                "background": "url(${template.page_img_url}) no-repeat",
                "background-size": "10rem 11.38666667rem"
            });
            $(".transparent-wrap").on("click", function () {
                window.location.href = "${template.goto_page_url}";
            });
            $("html").css({"background": "${template.page_bgcolor}"});
            $("html").css({"fontSize": document.documentElement.offsetWidth * 75 / 750});
            $("#modal").css({"top": $(window).height() / 2 - 100});
            var device_type = "${jssdkReceivingRequest.device_type}";
            var release_id = "${jssdkReceivingRequest.release_id}";
            var phone_number = "${jssdkReceivingRequest.phone_number}";
            var user_id = "${jssdkReceivingRequest.user_id}";
            var openid = "${jssdkReceivingRequest.openid}";
            var receiveUrl = "${ctx}/coupon/receiving";
            var captchaUrl = "${ctx}/coupon/captcha";
            if ("${jssdkReceivingRequest.return_code}" == "SUCCESS") {   //查询绑定成功或者失败,如果失败什么也不做
                var status = ${jssdkReceivingRequest.isbinded};
                if (status == 1) {
                    $("#redpacket-container").show();
                    $("#register-container").hide();
                    $.post(receiveUrl, {
                        "device_type": device_type,
                        "release_id": release_id,
                        "phone_number": phone_number,
                        "user_id": user_id,
                        "openid": openid
                    }, function (json) {
                        showErrorElement(json);
                        showRedPacket(json);
                        showRetryElement(json);
                        $("#lalala").on("click", function () {
                            $.post(receiveUrl, {
                                "device_type": device_type,
                                "release_id": release_id,
                                "phone_number": phone_number,
                                "user_id": user_id,
                                "openid": openid
                            }, function (res) {
                                showErrorElement(res);
                                showRetryElement(res);
                            }, "json");
                        });
                    }, "json");

                } else {
                    $("#redpacket-container").hide();
                    $("#register-container").show();

                    var modal = function (text) {
                        $("#modal").show().text(text);
                        setTimeout(function () {
                            $("#modal").hide()
                        }, 2000);
                    };

                    $('#query').attr('disabled', "true");

                    $("#mobile").on("input", function () {
                        if ($(this).val().trim() == "") {
                            $('#query').attr('disabled', "true").removeClass("on");
                        } else {
                            $('#query').removeAttr("disabled").addClass("on");
                        }

                    });

                    $("#query").on("click", function () {

                        var mobile = $("#mobile").val().trim();
                        if (mobile.length == 0) {
                            modal("请输入手机号码!");
                            $("#mobile").focus().val("");
                            return false;
                        }

                        if (mobile.length != 11) {
                            modal("请输入有效的手机号码!");
                            $("#mobile").focus().val("");
                            return false;
                        }

                        var myreg = /^((1)(3|4|5|7|8|6|9)|(9)(2|8))\d{9}$/;
                        if (!myreg.test(mobile)) {
                            modal("请输入有效的手机号码!");
                            $("#mobile").focus().val("");
                            return false;
                        } else {
                            var phoneNumber = $("#mobile").val();
                            $.post(captchaUrl, {"phone_number": phoneNumber, "handle_type": 1}, function (json) {
                                if (json.return_code == "SUCCESS") {
                                    var count = json.intervalSecond;
                                    var timer = setInterval(function () {
                                        count--;
                                        $("#query").text("获取(" + count + "s)");
                                        $("#query").css("background-color", "grey");
                                        $("#query").attr('disabled', "true");
                                        if (count <= 0) {
                                            clearInterval(timer);
                                            $("#query").text("获取");
                                            $("#query").removeAttr("disabled");
                                            $("#query").css("background-color", "#ff3432");
                                        }
                                    }, 1000);
                                } else {
                                    if (json.reapplySecond != null) {
                                        var reapplyCount = json.reapplySecond;
                                        var timer2 = setInterval(function () {
                                            reapplyCount--;
                                            $("#query").text("获取(" + reapplyCount + "s)");
                                            $("#query").css("background-color", "grey");
                                            $("#query").attr('disabled', "true");
                                            if (reapplyCount <= 0) {
                                                clearInterval(timer2);
                                                $("#query").text("获取");
                                                $("#query").removeAttr("disabled");
                                                $("#query").css("background-color", "#ff3432");
                                            }
                                        }, 1000);
                                        modal("获取验证码频繁，请" + reapplyCount + "秒后重试");
                                    } else {
                                        modal(json.return_message);
                                    }
                                }
                            }, "json");
                        }
                    });

                    $("#captcha").bind('input propertychange', function () {
                        if ($("#captcha").val().length == 4) {
                            $("#confirm").addClass("on").removeAttr("disabled");
                        } else {
                            $("#confirm").removeClass("on").attr('disabled', "true");
                        }
                    });

                    $("#confirm").on("click", function () {
                        var cap = $("#captcha").val();
                        var phoneNumber = $("#mobile").val();
                        $.post(captchaUrl, {
                            "phone_number": phoneNumber,
                            "captcha": cap,
                            "openid": openid,
                            "handle_type": 2
                        }, function (captchaJson) {
                            if (captchaJson.return_code == "SUCCESS") {
                                $("#redpacket-container").show();
                                $("#register-container").hide();
                                $.post(receiveUrl, {
                                    "device_type": device_type,
                                    "release_id": release_id,
                                    "phone_number": phoneNumber,
                                    "openid": openid
                                }, function (json) {
                                    showErrorElement(json);
                                    showRedPacket(json);
                                    showRetryElement(json);
                                    $("#lalala").on("click", function () {
                                        $.post(receiveUrl, {
                                            "device_type": device_type,
                                            "release_id": release_id,
                                            "phone_number": phoneNumber,
                                            "openid": openid
                                        }, function (res) {
                                            showErrorElement(res);
                                            showRetryElement(res);
                                        }, "json");
                                    });
                                }, "json");
                            } else {
                                var modal = function (text) {
                                    $("#modal").show().text(text);
                                    setTimeout(function () {
                                        $("#modal").hide()
                                    }, 2000);
                                };
                                modal(captchaJson.return_message);
                            }
                            ;
                        }, "json");
                    });
                }
            } else {
                $("#redpacket-container").hide();
                $("#register-container").hide();
                $("#useout").show();
                $("#useout").find("span").css({"text-indent": "1.6rem"});
                $("#useout").find("span").html("系统错误");
            }
        });

    </script>
</head>
<body>
<div class="redpacket-container" id="redpacket-container"></div>
<div class="register-container" id="register-container">
    <div class="telephone">
        <input type="text" placeholder="请输入手机号码" id="mobile">
    </div>
    <div class="yanzhengma">
        <input type="text" placeholder="验证码" id="captcha">
        <button id="query">获取</button>
    </div>
    <div class="confirm">
        <button disabled="disabled" id="confirm">确认</button>
    </div>
</div>
<div class="useout" id="useout">
    <img src="${ctx}/res/image/useout.png" alt=""/>
    <span>暂无红包可领取</span>
</div>
<div class="instruction">
    <div></div>
    <p>1.请进入大麦盒子端个人中心</p>
    <p>2.绑定手机号</p>
    <p>3.成功后即可使用该手机号码领取红包</p>
    <span>如何使用</span>
</div>
<div class="modal" id="modal">
    请输入有效的手机号码
</div>
<p class="concentrate">
    长按下方二维码关注“家视天下”
</p>
<div class="erweima">
    <img src="${ctx}/res/image/erweima.jpg" alt=""/>
</div>

<div class="transparent-wrap">

</div>
</body>
</html>