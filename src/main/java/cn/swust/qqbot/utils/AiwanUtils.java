package cn.swust.qqbot.utils;

import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

public class AiwanUtils {
    public static void main(String[] args) {
        GetVerImg();
    }

    public static String GetVerImg() {
        // 设置 ChromeDriver 的路径
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver.exe");

        // 创建 WebDriver 实例
        WebDriver driver = new ChromeDriver();

        try {
            // 打开网页
            driver.get("http://cocfz.com/");
            // 等待页面加载
            Thread.sleep(2000); // 可以替换为显式等待
            // 查找元素
            WebElement element = driver.findElement(By.id("verImg"));
            String verImg = element.getAttribute("src");
            System.out.println("启动码: " + verImg);
            return verImg;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 关闭浏览器
            driver.quit();
        }
        return "error";
    }

    public static String GetVerImgByHtmlUnit() throws IOException {
        final WebClient webClient = new WebClient();
        webClient.getOptions().setJavaScriptEnabled(true); // 启用 JavaScript
        webClient.getOptions().setCssEnabled(false); // 禁用 CSS（可选）
        HtmlPage page = webClient.getPage("http://cocfz.com");
        webClient.waitForBackgroundJavaScript(500);
        String verImg = page.getHtmlElementById("verImg").getAttribute("src");
        System.out.println("启动码: " + verImg);
        return verImg;
    }
}
