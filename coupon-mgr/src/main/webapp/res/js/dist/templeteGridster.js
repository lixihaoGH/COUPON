$(document).ready(function () {
    var canvas = document.getElementById('a_canvas');
    drawTables(canvas, 120, 6);
    gridster = $("#gridsterLayoutFirst ul").gridster({
        avoid_overlapped_widgets: false,
        widget_margins: [0.5, 0.5],//模块的间距 [上下,左右]
        widget_base_dimensions: [40, 40],//模块的宽高 [宽,高]
        /* max_rows: 1,*/
        /* max_cols: 24,  */                           //最多创建多少列，null表示没有限制
        //拖拽时触发函数
        /*draggable : {
             start : function(event, ui) {
            },
            drag : function(event, ui) {

            },
            stop : function(event, ui) {

                console.info(event,ui);
            }
        }, */

        //设置resize句柄
        resize: {
            enabled: false,//允许缩放
            /*  axes: ['both'],
             handle:'.resize',//html标签的css类名，按住此标签可以对网格进行放缩
             //改变大小时触发函数
            start : function(event, ui, $widget) {
            },
            resize : function(event, ui, $widget) {
            },
            stop : function(event, ui, $widget) {
            } */
        },

        //$w为要输出位置的网格对象（li）, wgd为该网格对象的坐标对象，包括col，row，size
        serialize_params: function ($w, wgd) {
            var id = $w.attr("id");
            var positionSeq = $("#positionSeq" + id).val();
            return {
                id: id,
                col: wgd.col,
                row: wgd.row,
                size_x: wgd.size_x,
                size_y: wgd.size_y,
                positionSeq: positionSeq
            }
        },


        //模块重叠时触发函数
        /* collision : {
            on_overlap_start : function(collider_data) {
                console.log("on_overlap_start");
            },
            on_overlap : function(collider_data) {
                console.log("on_overlap");
            },
            on_overlap_stop : function(collider_data) {
                console.log("on_overlap_stop");
            }
        } */
    });
    $(document).on('click', '.remove', function () {
        var gridster = $("#gridsterLayoutFirst ul").gridster().data('gridster');
        gridster.remove_widget($(this).parent());
    });

});

//失效
function gridsterDisable() {
    var gridster = $("#gridsterLayoutFirst ul").gridster().data('gridster');
    gridster.disable();
}

function gridsterEnable() {
    var gridster = $("#gridsterLayoutFirst ul").gridster().data('gridster');
    gridster.enable();
}

function maxRow(uuid) {
    var row = $('#' + uuid).attr('data-row');
    var size_y = $('#' + uuid).attr('data-sizey');
    var sum = (row - 0) + (size_y - 0);
    if (sum > 7) {
        var gridster = $("#gridsterLayoutFirst ul").gridster().data('gridster');
        gridster.remove_widget($('#' + uuid));
    }
}


//从json文件里读取json并绘制出自定义布局
function getLayout(templeteId, type) {
    var gridster = $("#gridsterLayoutFirst ul").gridster().data('gridster');
    $.post(ctx + '/customRecomTempleteList/getLayout.json', {
            'templeteId': templeteId
        },
        function (data) {
            serialization = Gridster.sort_by_row_and_col_asc(data);
            gridster.remove_all_widgets();
            $.each(serialization,
                function () {
                    if (type == 1) {//预览模式
                        remove = '<span id="b' + this.layoutId + '" style="color:black;">' + this.positionSeq + '号位置</span>'
                            + '<span class="remove"></span>';
                    } else {
                        /*remove = '<input type="button" value="编辑" onclick="editTempelte('+ this.layoutId+ ')"/>'+*/
                        remove = '<button class="remove">关闭</button>';
                    }
                    var positionSeq = '<input id="positionSeq' + this.layoutId + '" value="' + this.positionSeq + '" type="hidden"/>';
                    var li = '<li class="new" id="' + this.layoutId + '">' + remove + positionSeq + '</li>';
                    gridster.add_widget(li,
                        this.layoutW,
                        this.layoutH, this.layoutX,
                        this.layoutY);
                    console.info(serialization)
                });
        });

    $(document).on('click', '.remove', function () {
        var gridster = $(".gridster ul").gridster().data('gridster');
        gridster.remove_widget($(this).parent());
    });
}