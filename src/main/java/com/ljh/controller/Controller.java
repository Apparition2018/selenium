package com.ljh.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.ljh.phantomjs.PhantomJS;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Controller {

    /**
     * 使用 PhantomJs 截取页面
     */
    @RequestMapping("/screenshot")
    public String screenshot() {
        return PhantomJS.screenshot();
    }

    @RequestMapping("/getTitle")
    public String getTitle() {
        return "相思-登黄鹤楼";
    }

    /**
     * 使用 Htmlunit 爬取 ajax 之后的页面
     */
    @RequestMapping("/getPageByHtmlUnitAfterAjax")
    public void getPageByHtmlUnitAfterAjax() {
        try {
            WebClient webClient = new WebClient();
            WebClientOptions options = webClient.getOptions();
            options.setJavaScriptEnabled(true);
            options.setCssEnabled(true);
            final HtmlPage page = webClient.getPage("http://localhost:3333/static/htmlunit.html");
            // http://unnkoel.iteye.com/blog/2149455
            for (int i = 0; i < 20; i++) {
                if (!"".equals(page.getHtmlElementById("title1").asText())) {
                    break;
                }
                synchronized (page) { // ???
                    page.wait(500);
                }
            }
            HtmlDivision div = page.getHtmlElementById("htmlunit");
            System.out.println(div.asXml());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}