<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>新建红包</title>
    <!-- Bootstrap -->
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/bootstrap/bootstrap-table.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/css/bootstrap.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/static/css/coupon/common.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/static/css/coupon/createNewCoupon.css">
    <link type="text/css" rel="stylesheet"
          href="${pageContext.servletContext.contextPath}/res/css/easyui.css">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/res/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/res/js/bootstrap.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/bootstrap/bootstrap-table.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/bootstrap/bootstrap-table-zh-CN.js"></script>
    <script type="text/javascript"
            src="${pageContext.servletContext.contextPath}/res/js/coupon/createNewCoupon.js"></script>
    <script type="text/javascript"
               src="${pageContext.servletContext.contextPath}/res/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
               src="${pageContext.servletContext.contextPath}/res/js/bootstrap-datetimepicker.zh-CN.js"></script>

</head>

<body>

<div style="padding-bottom:0px; width:1100px;margin-left:25px;text-align:center">
    <div class="panel panel-default">
        <div class="panel-heading" style="text-align:left">基础属性</div>
        <div  style="height: 200px;text-align:left;">
            <div style="height:60px;">
                <label class="panel-label">
                    <span class="sign">*</span>红包名称：
                </label>
                <input type="text" name="coupon_stock_name" class="panel-input form-control coupon-common-input">
            </div>
            <div style="height:60px;">
                <label class="panel-label">
                    <span class="sign">*</span>优惠:
                </label>
                <input type="radio" name="preferential_type" value="1" checked="checked">满
                <input type="text" name="preferential_full_price" class="panel-input form-control coupon-common-input">
                减
                <input type="text" name="preferential_reduce_price"
                       class="panel-input form-control coupon-common-input">
            </div>
            <div style="height:60px;">
                <label class="panel-label">
                    <span class="sign">*</span>有效期：</label>
                <input type="radio" name="effective_time_type" value="0" checked="checked">自领取日起
                <input type="text" class="panel-input form-control coupon-common-input" name="effective_receiving_time">天内起效&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" name="effective_time_type" value="1">固定日期
                <input type="text" class="panel-input form-control coupon-common-input" id="effective_time">至
                <input type="text" class="panel-input form-control coupon-common-input" id="expired_time">
                <p style="color: #9c9c9c;margin-top: 5px;">当天算0天，0为当天0点到24点。若填写1，则为领取后下一天的23:59:59后失效。</p>
            </div>
        </div>
    </div>
</div>

<div style="padding-bottom:0px; width:1100px;margin-left: 25px;">
    <div class="panel panel-default">
        <div class="panel-heading" style="text-align:left">使用限制</div>
        <div class="panel-body" id="createnewcoupon-div">
            <div style="height:60px;">
                <label class="panel-label">
                    <span class="sign">*</span>可用商品：
                </label>
                <input type="radio" name="goods_range" value="0">
                全部商品
                <input type="radio" name="goods_range" value="1" checked="checked" style="margin-left: 20px;">部分商品
            </div>
            <div id="goods-div">
                <%--<div style="height:60px;">
                    <label class="panel-label">
                        <span class="sign">*</span>品类选择:
                    </label>
                    <input type="checkbox" name="selected_goods_category" onclick="selectedGoodsCheckbox()">优购商品
                </div>--%>
                <div>
                    <label class="panel-label">
                        <span class="sign">*</span>选择商品：
                    </label>
                    <a id="selectedScreenAdd">筛选添加</a>
                    <a id="selectedBatchAdd">批量添加</a>
                    <!-- 使用js生成 -->
                    <div class="coupon_goods_table">
                        <table id="selectedGoodsTable">

                        </table>
                    </div>
                </div>
            </div>
            <!-- 设置不可用商品 -->
            <div id="unuse-checkbox">
                <label id="panel-label">
                    <input type="checkbox" name="coupon_unuse_checkbox"
                           onclick="hideUnusableDiv()">设置不可用商品
                </label>
            </div>
            <!-- 点击后显示 默认不显示 -->
            <div id="unusadle-div">
                <%--<div style="height:60px;">
                    <label class="panel-label">
                        <span class="sign">*</span>品类选择:
                    </label>
                    <input type="checkbox" name="excluded_goods_category" onclick="excludedGoodsCheckBox()">优购商品
                </div>--%>
                <div style="height:450px;">
                    <label class="panel-label">
                        <span class="sign">*</span>选择商品：
                    </label>
                    <a id="excludedScreenAdd">筛选添加</a>
                    <a id="excludedBatchAdd">批量添加</a>
                    <!-- 使用js生成 -->
                    <div class="coupon_goods_table">
                        <table id="excludedGoodsTable">

                        </table>
                    </div>
                </div>
            </div>
            <div id="restriction_description">
                <label class="panel-label">限制描述：</label>
                <input type="text" class="panel-input form-control coupon-common-input" id="description"
                       name="restriction_description">
                <p style="color: #9c9c9c">例 限品类：仅华为购机部分商品可用；限商品：仅兰蔻小黑瓶100ml可用</p>
            </div>
            <button class="btn coupon_sumit_btn" id="new-coupon-submit">保存</button>
        </div>
    </div>
</div>

<!-- 添加商品限制-start -->
<div id="selectedScreenAddModal" class="modal fade newModalStyle" tabindex="-1" role="dialog"
     aria-labelledby="selectedScreenAddModalLabel"
     aria-hidden="true" style="width: 800px;height: 530px;">
    <div class="modal-dialog newModalDialog">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                </button>
                <h4 class="modal-title" id="selectedScreenAddModalLabel">
                    筛选添加
                </h4>
            </div>
            <div class="modal-body" style="overflow:auto;">
                <div style="margin:0px 0px 10px 0px;">
                    <form>
                        <span>全优购商城搜索:</span>
                        <input type="text" class="form-control coupon-input coupon-common-input" name="selectedGoodsName">
                        <button type="button" class="btn btn-default btn-primary" id="selected-search-button">查询
                        </button>

                    </form>
                </div>
                <div style="margin:0px 0px 10px 0px">
                    <input id="selected-first-level">
                    <input id="selected-second-level">
                    <input id="selected-third-level">
                    <button id="selectedAddTypeInfo" type="button" class="btn btn-default btn-primary">添加分类信息</button>
                </div>
                <div >
                    <table id="selectedGoodsInfoTable" >

                    </table>
                </div>

            </div>
            <div class="modal-footer" id="selectedAddModalFooter">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="selected-close-button">关闭
                </button>
                <button type="button" class="btn btn-primary" id="selectedScreenSubmitButton">
                    保存
                </button>
            </div>
    </div>
</div>

<div id="excludedScreenAddModal" class="modal fade" tabindex="-1" role="dialog"
     aria-labelledby="excludedScreenModalLabel"
     aria-hidden="true" style="width: 800px;height: 530px;">
    <div class="modal-dialog newModalDialog">
        <div>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                </button>
                <h4 class="modal-title" id="excludedScreenModalLabel">
                    筛选添加
                </h4>
            </div>
            <div class="modal-body" style="overflow:auto;">
                <div style="margin:0px 0px 10px 0px;">
                    <form>
                        <span>全优购商城搜索:</span>
                        <input type="text" class="form-control coupon-input coupon-common-input" name="excludedGoodsName">
                        <button type="button" class="btn btn-default btn-primary" id="excluded-search-button">查询
                        </button>
                    </form>
                </div>
                <div style="margin:0px 0px 10px 0px">
                    <input id="excluded-first-level">
                    <input id="excluded-second-level">
                    <input id="excluded-third-level">
                    <button id="excludedAddTypeInfo" type="button" class="btn btn-default btn-primary">添加分类信息</button>
                </div>
                <div>
                    <table id="excludedGoodsInfoTable">

                    </table>
                </div>

            </div>
            <div class="modal-footer" id="excludedAddModalFooter">
                <button type="button" class="btn btn-default" data-dismiss="modal" id="close-button">关闭
                </button>
                <button type="button" class="btn btn-primary" id="excludedSubmitButton">
                    保存
                </button>
            </div>
        </div>
    </div>
</div>

<div id="selectedBatchAddModal" style="width: 600px;height: 460px;" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="selectedBatchAddLabel"
     aria-hidden="true">
    <div class="modal-dialog" style="width: 600px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                </button>
                <h4 class="modal-title" id="selectedBatchAddLabel">
                    批量添加
                </h4>
            </div>
            <div class="modal-body">
                <div style="margin:0px 0px 10px 0px;">
                    <input type="radio" name="selectedBatchAddType" value="-1">
                    <span>SKU</span>
                    <input type="radio" name="selectedBatchAddType" value="1"
                           style="margin:0px 0px 0px 20px">
                    <span>一级分类ID</span>
                    <input type="radio" name="selectedBatchAddType" value="2"
                           style="margin:0px 0px 0px 20px">
                    <span>二级分类ID</span>
                    <input type="radio" name="selectedBatchAddType" value="3"
                           style="margin:0px 0px 0px 20px">
                    <span>三级分类ID</span>
                </div>
                <!-- 批量添加id列表 -->
                <textarea class="form-control coupon-textarea" rows="3" style="height: 260px;"
                          name="goodsTypeList" id="selectedGoodsTypeTextArea"></textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="selectedBatchSubmitButton">
                    保存
                </button>
            </div>
    </div>
</div>

<div id="excludedBatchAddModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="excludedBatchAddLabel"
     aria-hidden="true" style="width: 600px;height: 460px;">
    <div class="modal-dialog" style="width: 600px;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                </button>
                <h4 class="modal-title" id="excludedBatchModalLabel">
                    批量添加
                </h4>
            </div>
            <div class="modal-body">
                <div style="margin:0px 0px 10px 0px;">
                    <input type="radio" name="excludedBatchAddType" value="-1">
                    <span>SKU</span>
                    <input type="radio" name="excludedBatchAddType" value="1"
                           style="margin:0px 0px 0px 20px">
                    <span>一级分类ID</span>
                    <input type="radio" name="excludedBatchAddType" value="2"
                           style="margin:0px 0px 0px 20px">
                    <span>二级分类ID</span>
                    <input type="radio" name="excludedBatchAddType" value="3"
                           style="margin:0px 0px 0px 20px">
                    <span>三级分类ID</span>
                </div>
                <!-- 批量添加id列表 -->
                <textarea class="form-control coupon-textarea" style="height: 260px;" rows="3"
                          name="goodsTypeList" id="excludedGoodsTypeTextArea"></textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" class="btn btn-primary" id="excludedBatchSubmitButton">
                    保存
                </button>
            </div>
    </div>
</div>
<!-- 添加商品限制-end -->

</body>

</html>