function drawTables(a_canvas, grid_cols, grid_rows) {
    var context = a_canvas.getContext("2d");
    var cell_height = a_canvas.height / grid_rows;
    var cell_width = a_canvas.width / grid_cols;

    context.lineWidth = 1;//设置线宽
    context.strokeStyle = "#a0a0a0";//设置线的颜色

    context.beginPath();
    // 画横线
    for (var col = 0; col <= grid_cols; col++) {
        var x = col * cell_width;
        drawDashedLine(a_canvas, x, 0, x, a_canvas.height, 5);
    }
    // 画竖线
    for (var row = 0; row <= grid_rows; row++) {
        var y = row * cell_height;
        drawDashedLine(a_canvas, 0, y, a_canvas.width, y, 5);
    }
    context.stroke();
}

function drawDashedLine(a_canvas, x1, y1, x2, y2, dashLength) {
    var context = a_canvas.getContext("2d");
    dashLength = dashLength === undefined ? 5 : dashLength;
    var deltaX = x2 - x1;
    var deltaY = y2 - y1;
    var numDashes = Math.floor(
        Math.sqrt(deltaX * deltaX + deltaY * deltaY) / dashLength);
    for (var i = 0; i < numDashes; ++i) {
        context[i % 2 === 0 ? 'moveTo' : 'lineTo']
        (x1 + (deltaX / numDashes) * i, y1 + (deltaY / numDashes) * i);
    }
    context.stroke();
};