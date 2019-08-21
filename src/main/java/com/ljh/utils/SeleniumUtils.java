package com.ljh.utils;

import com.ljh.selenium.PageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * SeleniumUtils
 *
 * @author ljh
 * created on 2019/8/21 15:36
 */
public class SeleniumUtils {

    // 使用 selenium 获取 ECharts Base64码
    public static String getECharts(String url) {
        // 开启个浏览器并且输入链接
        WebDriver driver = PageUtils.getChromeDriver(url);
        // 最长等待10秒
        WebDriverWait wait = new WebDriverWait(driver, 10);
        // 等待id='base64'的元素出现
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("end")));
        // 得到id为base64的标签
        WebElement submitElement = driver.findElement(By.id("base64"));
        // 获取内容
        String rtn = submitElement.getAttribute("innerHTML");
        // 关闭浏览器 下面是关闭所有标签页，还有一个代码是 driver.close();, 关闭当前标签页
        driver.quit();

        return rtn;
    }

    // 使用 phantomjs 抓取JS动态生成的页面内容
    public static String getECharts2(String url) throws Exception {

        String pjsPath;
        String jsPath;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            pjsPath = "C:\\Users\\234607\\IdeaProjects\\com.dayang.subscription\\src\\main\\webapp\\WEB-INF\\phantomjs\\bin\\phantomjs.exe";
            jsPath = "C:\\Users\\234607\\IdeaProjects\\com.dayang.subscription\\src\\main\\webapp\\WEB-INF\\phantomjs\\exec\\codes.js";
        } else {
            pjsPath = "/usr/local/phantomjs/bin/phantomjs";
            jsPath = "/usr/local/phantomjs/exec/codes.js";
        }
        Process p = Runtime.getRuntime().exec(pjsPath + " " + jsPath + " " + url);
        Thread.sleep(8000);
        InputStream is = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sbf = new StringBuilder();
        String tmp;
        while ((tmp = br.readLine()) != null) {
            sbf.append(tmp);
        }

        return sbf.toString();
    }
}
