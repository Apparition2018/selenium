package com.ljh.phantomjs;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Slf4j
public class PhantomJS {

    @SneakyThrows
    public static String screenshot() {
        String pjsPath = "src/main/resources/phantomjs/bin/phantomjs.exe";  // PhantomJS 程序路径
        String jsPath = "src/main/resources/phantomjs/exec/screenshot.js";  // JavaScript 路径
        String url = "http://localhost:3333/static/phantomjs.html"; // 网页路径
        String imgPath = "src/main/java/com/ljh/phantomjs/screenshot.png";      // 截图输出路径

        // 获取Runtime运行时，并执行命令
        Runtime.getRuntime().exec(pjsPath + " " + jsPath + " " + url + " " + imgPath);

        File file = new File(imgPath);

        // 每5秒检测一次是否截图成功，共50秒
        int count = 10;
        do {
            log.info("~~> {}", count);
            if (--count < 0) {
                throw new RuntimeException("截图失败");
            }
            for (int i = 5; i > 0; i--) {
                Thread.sleep(1000);
            }
        } while (!file.exists());
        log.info("截图成功");

        InputStream is = new FileInputStream(imgPath);
        byte[] data = new byte[is.available()];
        int size = is.read(data);
        log.info(String.valueOf(size));

        // 加密
        return Base64.encodeBase64String(data);
    }

}
