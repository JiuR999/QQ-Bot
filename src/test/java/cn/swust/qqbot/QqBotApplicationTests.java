package cn.swust.qqbot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class QqBotApplicationTests {

    @Test
    void contextLoads() {

        try {
            Document document = Jsoup.connect("cocfz.com").get();
            System.out.println(document.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
