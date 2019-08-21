/**
 * phantomJs 脚本
 */
var page = require('webpage').create(),
    system = require('system'), // system.args查询并返回命令行参数的列表
    url, 			            // 网页地址
    imgPath; 				    // 截图输出路径
if (system.args.length < 3 || system.args.length > 3) {
    // ...
    phantom.exit();
} else {
    url = system.args[1];
    imgPath = system.args[2];

    // 设置视口大小
    page.viewportSize = {
        width: screen.width,
        height: screen.height
    };

    // 打开网页地址，并执行函数
    page.open(url, function (status) {
        if (status !== 'success') {
            console.log('Unable to load the url!');
            phantom.exit();
        } else {
            setTimeout(function () {
                // 执行函数并获取返回对象
                var echarts = page.evaluate(function () {
                    // getBoundingClientRect()
                    // 获取某个元素相对于视窗的位置集合。集合中有top, right, bottom, left等属性
                    return document.getElementById('render').getBoundingClientRect();
                });
                // 定义 render 区域
                page.clipRect = {
                    top: echarts.top,
                    left: echarts.left,
                    width: echarts.width,
                    height: echarts.height
                };
                // 截图
                page.render(imgPath);
                page.close();
                phantom.exit();
            }, 3000);
        }
    });
}