package net.eren.crawler;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.regex.Pattern;

public class Crawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp3|zip|gz))$");


    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()

                && href.startsWith("http://www.ics.uci.edu/");
//&& href.startsWith("http://www.kodcu.com/");
    }


//    @Override
//    public boolean shouldVisit(Page referringPage, WebURL url) {
//        String href = url.getURL().toLowerCase();
////        return url.getURL().startsWith("http://www.kodcu.com");
//        return href.startsWith("http://www.kodcu.com");
//    }


    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            System.out.println("Text length: " + text.length());
            System.out.println("Html length: " + html.length());
            System.out.println("Number of outgoing links: " + links.size());

            try {

                Document document = Jsoup.connect(url).get();
            // get the first <h1> HTML element in the page source code
            Element titleElement = document.getElementsByTag("h1").first();
            // print title text
                try{
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!" + titleElement.ownText());
                }catch (Exception e){
                    System.out.println("h1 yok sanirim "+ e.toString());
                }

                File file = new File("/Users/eren/crawler/testdata/dosya.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bWriter = new BufferedWriter(fileWriter);
                bWriter.write("/\n /\n Url " + url);
                bWriter.write("/\n Text length: " + text.length());
                bWriter.write("/\n Html length: " + html.length());
                bWriter.write("/\n Number of outgoing links: " + links.size());
                bWriter.close();
                fileWriter.close();

            } catch (Exception e) {
                System.out.println("/\n yazamadim ya la");
            }


        }
    }


//    @Override
//    public void visit(Page page) {
//        String url = page.getWebURL().getURL();
//        try {
//            Document document = Jsoup.connect(url).get();
//            // get the first <h1> HTML element in the page source code
//            Element titleElement = document.getElementsByTag("h1").first();
//            // print title text
//            System.out.println(titleElement.ownText());
//
//            File file = new File("/Users/eren/crawler/testdata/dosya.txt");
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//
//            FileWriter fileWriter = new FileWriter(file, false);
//            BufferedWriter bWriter = new BufferedWriter(fileWriter);
//            bWriter.write(titleElement.ownText().toString());
//            bWriter.close();
//        }
//        catch (IOException e) {
//            System.out.println("visitin icinde hata verdim ahanda mesaj = "+ e.toString());
//        }
//    }
//    public void visit(Page page) {
//        String url = page.getWebURL().getURL();
//        System.out.println("URL: " + url);
//    }
}
