/**
 * phantomJs 脚本
 */
var
    page = require('webpage').create(),
    system = require('system'),
    address = system.args[1];

page.open(address, function (status) {
    if (status !== 'success') {
        console.log("fail to load the address");
        phantom.exit();
    } else {
        setTimeout(function() {
            var base64 = page.evaluate(function() {
                return document.getElementById('base64').textContent;
            });
            console.log(base64);

            // console.log(page.content);

            phantom.exit();
        }, 7000);
    }

});