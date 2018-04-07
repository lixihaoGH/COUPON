$(document).ready(function () {
    var gridster = null;
    gridster = $("#gridsterLayoutSecond ul").gridster({
        avoid_overlapped_widgets: false,

        widget_margins: [0.5, 0.5],//模块的间距 [上下,左右]

        widget_base_dimensions: [40, 40],//模块的宽高 [宽,高]

        serialize_params: function ($w, wgd) {
            var id = $w.attr("id");
            var contentImg = $("#contentImg" + id).val();
            var content = $("#span" + id).html();
            var contentId = $("#contentId" + id).val();
            var contentType = $("#contentType" + id).val();
            var contentName = $("#contentName" + id).val();
            var contentSubtitle = $("#contentSubtitle" + id).val();
            var contentOutcropImg = $("#contentOutcropImg" + id).val();
            return {
                id: id,
                col: wgd.col,
                row: wgd.row,
                size_x: wgd.size_x,
                size_y: wgd.size_y,
                contentImg: contentImg,
                content: content,
                contentId: contentId,
                contentType: contentType,
                contentName: contentName,
                contentSubtitle: contentSubtitle,
                contentOutcropImg: contentOutcropImg

            }
        },
    }).data('gridster');
    gridster.disable();
    $(document).on('click', '.remove', function () {
        var gridster = $("#gridsterLayoutSecond ul").gridster().data('gridster');
        gridster.remove_widget($(this).parent());
    });

});


function getContent(templeteId, fatherId, type) {//如果type==1 预览模式，如果type==其他，可编辑模式
    /*$("#reconent_detail_dialog").dialog('open');*/
    var gridster = $("#gridsterLayoutSecond ul").gridster().data('gridster');
    $.post(ctx + "/customRecomTempleteList/getContent.json", {"templeteId": templeteId, "fatherId": fatherId},
        function (data) {
            //add的时候templeteId = first的FatherId。edit的时候templeteId就是ID，但是没有对应的layout
            serialization = Gridster.sort_by_row_and_col_asc(data);
            gridster.remove_all_widgets();
            $.each(serialization, function () {
                var butten;
                var title;
                var contentTitle;
                if (this.contentName == "" || this.contentName == null) {
                    contentTitle = "未编辑";
                } else {
                    contentTitle = this.contentName;
                }
                var input = '<input id="contentImg' + this.layoutId + '" value="' + this.contentImg + '" type="hidden"/>';
                var contentId = '<input id="contentId' + this.layoutId + '" value="' + this.contentId + '" type="hidden"/>';
                var contentType = '<input id="contentType' + this.layoutId + '" value="' + this.contentType + '" type="hidden"/>';
                var contentName = '<input id="contentName' + this.layoutId + '" value="' + this.contentName + '" type="hidden"/>';
                var contentSubtitle = '<input id="contentSubtitle' + this.layoutId + '" value="' + this.contentSubtitle + '" type="hidden"/>';
                var contentOutcropImg = '<input id="contentOutcropImg' + this.layoutId + '" value="' + this.contentOutcropImg + '" type="hidden"/>';
                title = '<span id="span' + this.layoutId + '" style="color:black;">' + contentTitle + '</span>';
                if (type == 1) {
                    butten = "";
                } else {
                    butten = '<input id="b' + this.layoutId + '" type="button" value="编辑" onclick="editLayoutContent('
                        + this.layoutId + ')"/>';
                }

                var li = '<li class="new" id="'
                    + this.layoutId
                    + '" style="background: url('
                    + this.contentImg
                    + ') center 0 repeat;background-size:cover;color:green;">'
                    + butten + input + contentId + contentType + contentName
                    + contentSubtitle + contentOutcropImg
                    + title + '</li>';
                gridster.add_widget(li,
                    this.layoutW,
                    this.layoutH, this.layoutX,
                    this.layoutY);
            });
        }, "json");
    /*$(document).on('click', '.remove', function() {
     var gridster = $(".gridster ul").gridster().data('gridster');
     gridster.remove_widget($(this).parent());
     });*/
}
	